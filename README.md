
```text
  controllerRepo 文件夹会不定期同步官方控制器
    如果你不希望你在官方仓库中提交的控制器被直装版收录
    请在 issues 中提出

  由于某些原因，某些特定的控制器图标和截图可能会被移除
    如果你不希望你的控制器发生这种情况
    可以选择通过 pull request 提交符合要求的图像
    图标文件: _/assets/<控制器id>/icon.png
    截图文件: _/assets/<控制器id>/<截图id>.png

  你也可以通过 pull request 向直装版控制器仓库 (controllerRepoCN) 提交控制器
```

</br>

# <h1 align="center">FCL-Docs</h1>
<p>这个分支会保存启动器内的公告、更新等需要在线获取文件，让启动器通过链接获取。</p>
<p>在这里可以找到文件的历史记录。</p>

## 保存的文件:

### 直装版使用的在线文件

- 公告文件 `announcement`
- 用户协议文件 `eula`
- 检查更新文件 `version_map`

### 启动器帮助页面文档

- 文档 `document`

### 直装版控制器仓库

- 控制器仓库1（对应启动器中的GITHUB源） `controllerRepo`
  - 不定期同步官方仓库更新，但移除某些图像（见最上方文本框）
- 控制器仓库2（对应启动器中的国内源） `controllerRepoCN`
  - 一些适用于服务器或整合包直装版的控制器

### 其他资源

- 文件夹 `_` 中：
- 控制器仓库相关脚本：
  - 官方控制器仓库（controllerRepo）更新脚本 `pull.sh`
  - 用于移除特定图像的脚本 `remove.sh`
  - 待替换的图标和截图
    - 图标 `listIcon.txt`
    - 截图 `listScreenshot.txt`
- 控制器仓库相关资源
  - 用于替换的默认文件
    - 图标 `_/assets/icon.png`
    - 截图 `_/assets/screenshot.png`
  - 适用于某个控制器的特定文件
    - 图标 `_/assets/<控制器id>/icon.png`
    - 截图 `_/assets/<控制器id>/<截图id>.png`
