#!/data/data/com.termux/files/usr/bin/bash
cd "$(dirname "$(readlink -f "${0}")")"
cd "$(dirname "`pwd`")"
pwd

echo -e "\e[32m清除本地的官方仓库镜像\e[0m"
rm -rf "controllerRepo"
git add -A

echo -e "\e[32m屏蔽当前仓库git\e[0m"
mv ".git" ".git_bak"

echo -e "\e[32m从官方控制器仓库拉取更新\e[0m"
git clone "https://github.com/FCL-Team/FCL-Controllers.git" "controllerRepo"
if [ "$?" != "0" ]; then
    echo -e "\e[31m拉取失败\e[0m"
    exit
fi

echo -e "\e[32m屏蔽控制器仓库git\e[0m"
mv "controllerRepo/.git" "controllerRepo/.git_bak"

echo -e "\e[32m还原当前仓库git\e[0m"
mv ".git_bak" ".git"
