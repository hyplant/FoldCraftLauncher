#!/data/data/com.termux/files/usr/bin/bash
cd "$(dirname "$(readlink -f "${0}")")"
export ScriptDir="`pwd`"
cd "$(dirname "$ScriptDir")"
pwd

echo -e "\e[93m开始替换图标\e[0m"
for iconFile in `cat "$ScriptDir/listIcon.txt"`; do
  replaceFile=${ScriptDir}/icon64.png
  fileHint=""
  if [ -f "${iconFile}_replace.png" ]; then
    replaceFile=${iconFile}_replace.png
    fileHint="(专用文件)"
  fi
  cp -f "${replaceFile}" "${iconFile}"
  if [ $? -eq 0 ]; then
    echo -e "\e[92m已覆盖\e[35m${fileHint}\e[96m：\e[0m${iconFile}"
  else
    echo -e "\e[31m覆盖失败\e[35m${fileHint}\e[96m：\e[0m${iconFile}"
  fi
done

echo -e "\e[93m开始替换截图\e[0m"
for screenshotFile in `cat "$ScriptDir/listScreenshot.txt"`; do
  replaceFile=${ScriptDir}/icon1024.png
  fileHint=""
  if [ -f "${screenshotFile}_replace.png" ]; then
    replaceFile=${screenshotFile}_replace.png
    fileHint="(专用文件)"
  fi
  cp -f "${replaceFile}" "${screenshotFile}"
  if [ $? -eq 0 ]; then
    echo -e "\e[92m已覆盖\e[35m${fileHint}\e[96m：\e[0m${screenshotFile}"
  else
    echo -e "\e[31m覆盖失败\e[35m${fileHint}\e[96m：\e[0m${screenshotFile}"
  fi
done
