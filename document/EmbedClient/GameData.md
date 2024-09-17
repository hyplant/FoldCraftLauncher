# 更改游戏数据目录

FCL 提供了**版本隔离**功能，但隔离后，游戏数据仍然在`.minecraft`文件夹中。如果直装版更新客户端，本地存档等数据就会被覆盖。因此建议使用本文的分离数据目录方式，可以保证本地数据不受影响。

## client.json

详见[Minecraft Wiki 客户端清单文件格式](https://zh.minecraft.wiki/w/%E5%AE%A2%E6%88%B7%E7%AB%AF%E6%B8%85%E5%8D%95%E6%96%87%E4%BB%B6%E6%A0%BC%E5%BC%8F)  
`client.json`中保存了客户端的参数。

### 基本格式

<details markdown='1'>

```json
{
  "arguments": {
    "game": [
      "--username",
      "${auth_player_name}",
      "--version",
      "${version_name}",
      "--gameDir",
      "${game_directory}",
      "--assetsDir",
      "${assets_root}",
      "--assetIndex",
      "${assets_index_name}",
      "--uuid",
      "${auth_uuid}",
      "--accessToken",
      "${auth_access_token}",
      "--clientId",
      "${clientid}",
      "--xuid",
      "${auth_xuid}",
      "--userType",
      "${user_type}",
      "--versionType",
      "${version_type}",
      {
        "rules": [
          {
            "action": "allow",
            "features": {
              "is_demo_user": true
            }
          }
        ],
        "value": [
          "--demo"
        ]
      },
      {
        "rules": [
          {
            "action": "allow",
            "features": {
              "has_custom_resolution": true
            }
          }
        ],
        "value": [
          "--width",
          "${resolution_width}",
          "--height",
          "${resolution_height}"
        ]
      },
      {
        "rules": [
          {
            "action": "allow",
            "features": {
              "has_quick_plays_support": true
            }
          }
        ],
        "value": [
          "--quickPlayPath",
          "${quickPlayPath}"
        ]
      },
      {
        "rules": [
          {
            "action": "allow",
            "features": {
              "is_quick_play_singleplayer": true
            }
          }
        ],
        "value": [
          "--quickPlaySingleplayer",
          "${quickPlaySingleplayer}"
        ]
      },
      {
        "rules": [
          {
            "action": "allow",
            "features": {
              "is_quick_play_multiplayer": true
            }
          }
        ],
        "value": [
          "--quickPlayMultiplayer",
          "${quickPlayMultiplayer}"
        ]
      },
      {
        "rules": [
          {
            "action": "allow",
            "features": {
              "is_quick_play_realms": true
            }
          }
        ],
        "value": [
          "--quickPlayRealms",
          "${quickPlayRealms}"
        ]
      }
    ],
    "jvm": []
  },
  "id": "1.21.1",
  "libraries": []
}
```

<summary>客户端启动参数示例</summary>
</details>

<div><br></div>

## 修改

通过修改`client.json`可以实现修改默认游戏数据目录，而不需要开启版本隔离。

### 找到游戏目录参数

在文件中找到：  
`arguments.game`  
其中找到：  
`"--gameDir"`  
例如上方示例的第8行。

### 更改参数值

在下方有一项：  
`"${game_directory}"`  
对应的目录是`.minecraft`。  

对其进行修改，例如改为：  
`"${game_directory}/../minecraft"`  
此时游戏目录被改为与`.minecraft`同目录中的`minecraft`文件夹。  
  
这样在使用直装版更新客户端时，本地游戏数据就不会被清除了。
