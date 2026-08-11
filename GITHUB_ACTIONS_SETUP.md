# GitHub Actions APK Build & Sign 设置指南

## 🎯 快速开始

### 1. 生成签名密钥（首次）

如果你还没有签名密钥，用以下命令生成一个：

```bash
keytool -genkey -v -keystore upload-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias upload-key \
  -storepass YOUR_KEYSTORE_PASSWORD \
  -keypass YOUR_KEY_PASSWORD \
  -dname "CN=AI-Live-Overflow, O=AI-Overlay, C=CN"
```

这会生成 `upload-key.jks` 文件。

### 2. Base64编码密钥文件

```bash
cat upload-key.jks | base64 -w 0 > keystore-base64.txt
cat keystore-base64.txt  # 复制这个长字符串
```

### 3. 在GitHub Secrets中存储密钥

打开 https://github.com/nfy1599/AI-Live-Overflow/settings/secrets/actions

添加以下Secrets：

| 名称 | 值 |
|------|-----|
| `KEYSTORE_BASE64` | 上面复制的Base64编码字符串 |
| `KEYSTORE_PASSWORD` | 密钥库密码（生成时用的YOUR_KEYSTORE_PASSWORD） |
| `KEY_ALIAS` | `upload-key` |
| `KEY_PASSWORD` | 密钥密码（生成时用的YOUR_KEY_PASSWORD） |

### 4. 手动触发编译

进入 GitHub Actions 页面：https://github.com/nfy1599/AI-Live-Overflow/actions

1. 点击左侧 "Build Signed APK" workflow
2. 点击 "Run workflow" 按钮
3. 选择编译类型（debug 或 release）
4. 点击 "Run workflow"

等待5-10分钟编译完成。

### 5. 下载编译好的APK

编译完成后，点击Run，向下滚动找到 "Artifacts" 部分，下载 `signed-apk-XXXXX.zip`

解压后得到已签名的APK文件。

---

## ⚙️ 工作流说明

**触发方式**：`workflow_dispatch`（手动触发）
- ✅ 不自动编译，不浪费Actions配额
- ✅ 只在你需要时手动触发
- ✅ 公共仓库Actions是免费的

**签名方式**：v2/v3签名（Android 30+兼容）
- ✅ 符合Android 11+要求
- ✅ 比v1签名更安全

**密钥安全**：GitHub Secretsᖠ密存储
- ✅ 密钥从不存储在仓库中
- ✅ 只在Action运行时解密使用
- ✅ 每次编译完后临时文件会被清除

---

## 🔐 安全提示

⚠️ **重要**：
- 不要把 `upload-key.jks` 文件提交到GitHub
- 不要在代码中硬编码密码
- 定期检查 `.gitignore` 确保密钥文件被忽略
- 如果密钥泄露，可以在GitHub上重新生成新的Secrets

---

## 📱 安装编译好的APK

编译完APK后，通过以下方式安装到手机：

```bash
# 使用adb（如果有USB连接）
adb install -r signed-apk.apk

# 或者通过super_admin:shell
pm install /sdcard/Download/signed-apk.apk

# 或者直接用文件管理器在手机上点击APK
```

---

## 🐛 故障排除

**问题**：Actions显示 "Keystore not found"
- **解决**：检查KEYSTORE_BASE64是否正确设置在Secrets中

**问题**：编译失败 "apksigner not found"
- **解决**：检查build-tools版本是否正确（34.0.0）

**问题**：APK安装失败
- **解决**：确保APK已正确签名（verify step 保訪自动编译복缛上点击长字穦串
---

## 📚 相关文档

- [GitHub Actions 官方文档](https://docs.github.com/en/actions)
- [Android 签名指南](https://developer.android.com/studio/publish/app-signing)
- [Gradle 构建配置](https://developer.android.com/build)