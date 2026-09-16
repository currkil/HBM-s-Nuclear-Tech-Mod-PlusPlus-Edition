const STATUSES = ["TODO", "DOING", "DONE", "BLOCKED"];
const DB_NAME = "hbm-taskboard";
const STORE = "handles";
const WARN_MARK = "# === WARNINGS ===";

let fileHandle = null;
let lines = [];
let tasks = [];
let warnings = [];
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
    let note = (m[7] || "").trim();
    let anno = "";
    const annoMatch = note.match(/@(即将过时|已过时|实验性|待审查)\s*$/);
    if (annoMatch) {
      anno = annoMatch[1];
      note = note.replace(/@(即将过时|已过时|实验性|待审查)\s*$/, "").trim();
    }
    result.push({
      lineIndex: idx,
      status: m[1],
      who: m[2].trim(),
      name: m[3].trim(),
      origin: m[4].trim(),
      target: m[5].trim(),
      dep: m[6].trim(),
      note,
      anno,
    });
  });
  return result;
}

function parseWarnings(rawLines) {
  const result = [];
  let inWarn = false;
  rawLines.forEach((line) => {
    const t = line.trim();
    if (t === WARN_MARK) { inWarn = true; return; }
    if (!inWarn) return;
    if (!t || t.startsWith("#")) return;
    const m = t.match(/^\[(WARN|INFO|ERROR)\]\s*(.+?)(?:\s*::\s*(.*))?$/);
    if (!m) return;
    result.push({
      level: m[1],
      text: m[2].trim(),
      target: (m[3] || "").trim(),
    });
  });
  return result;
}

function taskToLine(t) {
  let note = t.note || "";
  if (t.anno) note = (note ? note + " " : "") + "@" + t.anno;
  return `[${t.status}]@${t.who} - ${t.name} - ${t.origin} - ${t.target} - ${t.dep} :: ${note}`;
}

function warningToLine(w) {
  return `[${w.level}] ${w.text}${w.target ? " :: " + w.target : ""}`;
}

function rebuildLines() {
  for (const t of tasks) lines[t.lineIndex] = taskToLine(t);

  // 重建 WARNINGS 区
  const warnIdx = lines.findIndex((l) => l.trim() === WARN_MARK);
  if (warnIdx === -1) {
    lines.push("");
    lines.push(WARN_MARK);
    lines.push("# 格式：[警告等级] 内容 :: 影响的任务名（可选）");
    for (const w of warnings) lines.push(warningToLine(w));
  } else {
    // 删除旧警告行（WARN_MARK 后的非注释行）
    let end = warnIdx + 1;
    while (end < lines.length) {
      const t = lines[end].trim();
      if (t === "" || t.startsWith("#") || /^\[(WARN|INFO|ERROR)\]/.test(t)) end++;
      else break;
    }
    const head = lines.slice(0, warnIdx + 1).filter((l, i) => {
      if (i <= warnIdx) return true;
      return false;
    });
    const headerComments = ["# 格式：[警告等级] 内容 :: 影响的任务名（可选）"];
    const warnLines = warnings.map(warningToLine);
    const tail = lines.slice(end);
    lines = [...lines.slice(0, warnIdx + 1), ...headerComments, ...warnLines, ...tail];
  }
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
        ${t.anno ? `<div class="anno">@${esc(t.anno)}</div>` : ""}
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

      actions.appendChild(
        mkBtn("注解", () =>
          openModal("设置注解", [
            { key: "anno", label: "注解（留空取消）", value: t.anno || "" },
          ], (v) => {
            t.anno = v.anno.trim();
            rebuildLines();
            render();
            return true;
          })
        )
      );

      box.appendChild(card);
    }
  }

  renderWarnings();
}

function renderWarnings() {
  const box = document.getElementById("warnList");
  if (!box) return;
  if (!warnings.length) {
    box.innerHTML = '<div class="empty">无警告</div>';
    return;
  }
  box.innerHTML = warnings
    .map(
      (w, i) => `
      <div class="warn-item ${w.level.toLowerCase()}">
        <span class="warn-level">${esc(w.level)}</span>
        <span class="warn-text">${esc(w.text)}</span>
        ${w.target ? `<span class="warn-target">→ ${esc(w.target)}</span>` : ""}
        <button class="warn-del" data-i="${i}">删除</button>
      </div>`
    )
    .join("");
  box.querySelectorAll(".warn-del").forEach((btn) => {
    btn.onclick = () => {
      warnings.splice(Number(btn.dataset.i), 1);
      rebuildLines();
      render();
    };
  });
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
  warnings = parseWarnings(lines);
  fileHandle = handle;
  await idbSet("lastFile", handle);
  setStatus("已打开: " + file.name);
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnAdd").disabled = false;
  document.getElementById("btnAddWarn").disabled = false;
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

/* ================= 新增任务 / 警告 ================= */

function addTask() {
  openModal(
    "新增任务",
    [
      { key: "name", label: "任务名", value: "" },
      { key: "origin", label: "原版包名", value: "com.hbm." },
      { key: "target", label: "目标包名", value: "com.xxx." },
      { key: "dep", label: "依赖（没有填 无）", value: "无" },
      { key: "note", label: "备注（可空）", value: "", multiline: true },
      { key: "anno", label: "注解（可空，如 即将过时 / 已过时）", value: "" },
    ],
    (v) => {
      if (!v.name.trim()) return false;
      const newLine = `[TODO]@未认领 - ${v.name.trim()} - ${v.origin.trim()} - ${v.target.trim()} - ${v.dep.trim()} :: ${v.note.trim()}${v.anno.trim() ? " @" + v.anno.trim() : ""}`;
      let insertAt = lines.length;
      const warnIdx = lines.findIndex((l) => l.trim() === WARN_MARK);
      if (warnIdx !== -1) insertAt = warnIdx;
      lines.splice(insertAt, 0, newLine);
      tasks = parseTasks(lines);
      warnings = parseWarnings(lines);
      render();
      return true;
    }
  );
}

function addWarning() {
  openModal(
    "新增警告",
    [
      { key: "level", label: "等级（WARN / INFO / ERROR）", value: "WARN" },
      { key: "text", label: "内容", value: "" },
      { key: "target", label: "影响任务（可空）", value: "" },
    ],
    (v) => {
      if (!v.text.trim()) return false;
      warnings.push({
        level: v.level.trim().toUpperCase(),
        text: v.text.trim(),
        target: v.target.trim(),
      });
      rebuildLines();
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
  document.getElementById("btnAddWarn").onclick = addWarning;

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