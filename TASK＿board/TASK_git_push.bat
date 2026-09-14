@echo off
git pull --rebase origin master || (echo 拉取失败，请手动解决冲突 && exit /b 1)
git add .
git diff --cached --quiet || git commit -m "TASK_PUSH"
git push origin master