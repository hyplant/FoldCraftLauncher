#!/data/data/com.termux/files/usr/bin/bash
cd "$(dirname "$(readlink -f "${0}")")"
cd "$(dirname "`pwd`")"
pwd

echo -e "\e[32m开始替换图标\e[0m"
exec 100<"remove_5bqf54mpCg/listIcon.txt"
mapfile -t -u 100 iconList
exec 100<&-
for iconFile in "${iconList[@]}"; do
    cp -f "remove_5bqf54mpCg/icon64.png" "${iconFile}"
    if [ $? -eq 0 ]; then
        echo "已覆盖: ${iconFile}"
    else
        echo -e "\e[31m覆盖失败: \e[0m${iconFile}"
    fi
done

echo -e "\e[32m开始替换截图\e[0m"
exec 100<"remove_5bqf54mpCg/listScreenshot.txt"
mapfile -t -u 100 screenshotList
exec 100<&-
for screenshotFile in "${screenshotList[@]}"; do
    cp -f "remove_5bqf54mpCg/icon1024.png" "${screenshotFile}"
    if [ $? -eq 0 ]; then
        echo "已覆盖: ${screenshotFile}"
    else
        echo -e "\e[31m覆盖失败: \e[0m${screenshotFile}"
    fi
done
