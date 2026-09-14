const STATUSES = ["TODO", "DOING", "DONE", "BLOCKED"];
const DB_NAME = "hbm-taskboard";
const STORE = "handles";

let fileHandle = null;
let lines = [];
let tasks = [];
let filter = "ALL";

/* ================= 解析 / 序列化 ================= */

function parseTasks(rawLines) {
  const result = [];
  rawLines.forEach((line, idx) => {
    const t = line.trim();
    if (!t || t.startsWith("#")) return;
    const m = t.match(
      /^\[(TODO|DOING|DONE|BLOCKED)\]\s*@([^-]+?)\s*-\s*(.+?)\s*-\s*(.+?)\s*-\s*(.+?)\s*-\s*(.+?)(?:\s*::\s*(.*))?$/
    );
    if (!m) return;
    result.push({
      lineIndex: idx,
      status: m[1],
      who: m[2].trim(),
      name: m[3].trim(),
      origin: m[4].trim(),
      target: m[5].trim(),
      dep: m[6].trim(),
      note: (m[7] || "").trim(),
    });
  });
  return result;
}

function taskToLine(t) {
  return `[${t.status}]@${t.who} - ${t.name} - ${t.origin} - ${t.target} - ${t.dep} :: ${t.note}`;
}

function rebuildLines() {
  for (const t of tasks) lines[t.lineIndex] = taskToLine(t);
}

/* ================= 渲染 ================= */

function esc(s) {
  return String(s).replace(/[&<>"]/g, (c) =>
    ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" }[c])
  );
}

function render() {
  const counts = { TODO: 0, DOING: 0, DONE: 0, BLOCKED: 0 };
  tasks.forEach((t) => counts[t.status]++);

  document.getElementById("stats").innerHTML =
    STATUSES.map((s) => `<span>${s}: ${counts[s]}</span>`).join("") +
    `<span>总计: ${tasks.length}</span>`;

  for (const s of STATUSES) {
    document.getElementById("c-" + s).textContent = counts[s];
    const box = document.getElementById("l-" + s);
    box.innerHTML = "";
    const list = tasks.filter((t) => t.status === s);
    if (!list.length) {
      box.innerHTML = '<div class="empty">无</div>';
      continue;
    }
    for (const t of list) {
      const card = document.createElement("div");
      card.className = "card " + s.toLowerCase();
      card.innerHTML = `
        <div class="name">${esc(t.name)}</div>
        <div class="who">@${esc(t.who)}</div>
        <div class="path">${esc(t.origin)} → ${esc(t.target)}</div>
        ${t.dep && t.dep !== "无" ? `<div class="dep">依赖: ${esc(t.dep)}</div>` : ""}
        ${t.note ? `<div class="note">${esc(t.note)}</div>` : ""}
        <div class="actions"></div>
      `;
      const actions = card.querySelector(".actions");

      if (t.status === "TODO" || t.who === "未认领") {
        const b = mkBtn("认领", () =>
          openModal("认领任务", [
            { key: "who", label: "你的名字", value: "" },
          ], (v) => {
            if (!v.who.trim()) return false;
            t.who = v.who.trim();
            t.status = "DOING";
            rebuildLines();
            render();
            return true;
          })
        );
        actions.appendChild(b);
      }

      if (t.status !== "DONE") {
        actions.appendChild(
          mkBtn("标记完成", () => {
            t.status = "DONE";
            rebuildLines();
            render();
          })
        );
      }

      if (t.status !== "BLOCKED") {
        actions.appendChild(
          mkBtn("标记阻塞", () =>
            openModal("标记阻塞", [
              { key: "note", label: "阻塞原因", value: t.note, multiline: true },
            ], (v) => {
              t.status = "BLOCKED";
              if (v.note.trim()) t.note = v.note.trim();
              rebuildLines();
              render();
              return true;
            })
          )
        );
      }

      box.appendChild(card);
    }
  }
}

function mkBtn(text, fn) {
  const b = document.createElement("button");
  b.textContent = text;
  b.onclick = fn;
  return b;
}

/* ================= 弹窗 ================= */

let modalCallback = null;

function openModal(title, fields, onOk) {
  document.getElementById("modalTitle").textContent = title;
  const body = document.getElementById("modalBody");
  body.innerHTML = fields
    .map((f) => {
      const input = f.multiline
        ? `<textarea id="mf-${f.key}">${esc(f.value || "")}</textarea>`
        : `<input id="mf-${f.key}" value="${esc(f.value || "")}">`;
      return `<label>${esc(f.label)}</label>${input}`;
    })
    .join("");

  modalCallback = () => {
    const values = {};
    for (const f of fields) values[f.key] = document.getElementById("mf-" + f.key).value;
    if (onOk(values) !== false) closeModal();
  };

  document.getElementById("modalMask").classList.add("show");
  const first = body.querySelector("input, textarea");
  if (first) setTimeout(() => first.focus(), 50);
}

function closeModal() {
  document.getElementById("modalMask").classList.remove("show");
  modalCallback = null;
}

document.getElementById("modalCancel").onclick = closeModal;
document.getElementById("modalOk").onclick = () => modalCallback && modalCallback();
document.getElementById("modalMask").onclick = (e) => {
  if (e.target.id === "modalMask") closeModal();
};

/* ================= IndexedDB 存文件句柄 ================= */

function idbOpen() {
  return new Promise((resolve, reject) => {
    const req = indexedDB.open(DB_NAME, 1);
    req.onupgradeneeded = () => req.result.createObjectStore(STORE);
    req.onsuccess = () => resolve(req.result);
    req.onerror = () => reject(req.error);
  });
}

async function idbSet(key, val) {
  const db = await idbOpen();
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE, "readwrite");
    tx.objectStore(STORE).put(val, key);
    tx.oncomplete = resolve;
    tx.onerror = () => reject(tx.error);
  });
}

async function idbGet(key) {
  const db = await idbOpen();
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE, "readonly");
    const req = tx.objectStore(STORE).get(key);
    req.onsuccess = () => resolve(req.result);
    req.onerror = () => reject(req.error);
  });
}

/* ================= 文件操作 ================= */

async function loadFromHandle(handle, needPermission = true) {
  if (needPermission) {
    const perm = await handle.queryPermission({ mode: "readwrite" });
    if (perm !== "granted") {
      const req = await handle.requestPermission({ mode: "readwrite" });
      if (req !== "granted") {
        setStatus("未授权读写");
        return;
      }
    }
  }
  const file = await handle.getFile();
  const text = await file.text();
  lines = text.split(/\r?\n/);
  tasks = parseTasks(lines);
  fileHandle = handle;
  await idbSet("lastFile", handle);
  setStatus("已打开: " + file.name);
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnAdd").disabled = false;
  render();
}

function setStatus(t) {
  document.getElementById("fileStatus").textContent = t;
}

async function openFile() {
  if (!window.showOpenFilePicker) {
    alert("请用 Chrome / Edge 打开本页面。");
    return;
  }
  try {
    const [handle] = await window.showOpenFilePicker({
      types: [{ description: "TASK.txt", accept: { "text/plain": [".txt"] } }],
    });
    await loadFromHandle(handle, false);
  } catch (e) {
    if (e.name !== "AbortError") console.error(e);
  }
}

async function saveFile() {
  if (!fileHandle) return;
  rebuildLines();
  const writable = await fileHandle.createWritable();
  await writable.write(lines.join("\n"));
  await writable.close();
  setStatus("已保存 ✓ " + new Date().toLocaleTimeString());
}

/* ================= 新增任务 ================= */

function addTask() {
  openModal(
    "新增任务",
    [
      { key: "name", label: "任务名", value: "" },
      { key: "origin", label: "原版包名", value: "com.hbm." },
      { key: "target", label: "目标包名", value: "com.xxx." },
      { key: "dep", label: "依赖（没有填 无）", value: "无" },
      { key: "note", label: "备注（可空）", value: "", multiline: true },
    ],
    (v) => {
      if (!v.name.trim()) return false;
      const newLine = `[TODO]@未认领 - ${v.name.trim()} - ${v.origin.trim()} - ${v.target.trim()} - ${v.dep.trim()} :: ${v.note.trim()}`;
      let insertAt = lines.length;
      for (let i = 5; i < lines.length; i++) {
        if (lines[i].trim().startsWith("# ===")) {
          insertAt = i;
          break;
        }
      }
      lines.splice(insertAt, 0, newLine);
      tasks = parseTasks(lines);
      render();
      return true;
    }
  );
}

/* ================= 初始化 ================= */

async function init() {
  document.getElementById("btnOpen").onclick = openFile;
  document.getElementById("btnSave").onclick = saveFile;
  document.getElementById("btnAdd").onclick = addTask;

  document.querySelectorAll("button[data-filter]").forEach((btn) => {
    btn.onclick = () => {
      filter = btn.dataset.filter;
      document.querySelectorAll("button[data-filter]").forEach((b) => b.classList.remove("active"));
      btn.classList.add("active");
      document.querySelectorAll(".col").forEach((col) => {
        col.style.display = filter === "ALL" || col.dataset.col === filter ? "" : "none";
      });
    };
  });

  // 尝试取回上次的文件句柄
  try {
    const handle = await idbGet("lastFile");
    if (handle) {
      const btn = document.getElementById("btnReconnect");
      btn.disabled = false;
      btn.textContent = "重连: " + handle.name;
      btn.onclick = async () => {
        try {
          await loadFromHandle(handle, true);
          btn.disabled = true;
        } catch (e) {
          console.error(e);
          setStatus("重连失败，请重新打开文件");
        }
      };
      setStatus("检测到上次文件，点击「重连」继续");
    }
  } catch (e) {
    console.error("IndexedDB 读取失败", e);
  }

  render();
}

init();