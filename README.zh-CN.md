# I Want to be an IFBB PRO Skill 中文说明

`I Want to be an IFBB PRO` 是一个开源 AI skill，用于专业级个性化体型训练规划，也可以理解为专业级个人定制化体型训练规划。这个名字是一个抽象的目标形象：用 IFBB PRO 代表职业级严谨度、体型标准、训练执行、营养管理、恢复管理和长期数据纪律，而不是默认要求使用者真的去拿职业卡。

它可以帮助用户创建新的训练计划，也可以优化现有训练计划；可以用于增肌、减脂、重组、维持、deload、训练恢复、备赛灵感的体脂控制、posing 练习、多模态照片/视频分析和阶段化 check-in。核心目标是把“我想练到非常专业的体型”拆成可执行、可追踪、可调整的长期系统。

本项目不隶属于 IFBB Pro League、NPC 或 NPC Worldwide，也不是任何官方赛事资源。如果用户真的要参赛，组别、规则、报名、晋级路径和赛事细节必须以官方组织者和赛事网站的最新信息为准。

## 能做什么

- 根据目标、身体数据、训练年龄、器械条件、训练频率、恢复能力和限制条件，创建个性化训练计划。
- 优化现有训练计划，而不是盲目推翻重做。
- 支持增肌期、减脂期、体型重组、维持期、deload、恢复训练和长期弱项补强。
- 将职业级体型目标拆成可执行的阶段：肌肉量、比例、对称性、体脂控制、动作执行、posing 和数据纪律。
- 细化每次训练：动作、目标肌群、组数、次数、RIR/RPE、休息、技术要点、替代动作和记录字段。
- 追踪训练质量：hard sets、tonnage、单组表现、目标肌肉刺激、疼痛标记、动作技术和进步决策。
- 为动作名称提供新手友好的器械/动作上下文：例如 Smith machine、Cable station、Dumbbells、Barbell、Machine、Bodyweight/open station 等统一示意类别，并配套简洁实例图、示例动作和 look-for 识别提示，帮助用户知道这个动作大概对应哪种器械和动作路径。
- 支持器械识别和动作质量分析：通过器械照片、动作照片或视频帧判断可能的器械/动作、目标肌群、常见错误、替代动作和安全注意点。
- 支持食物照片营养估算：通过餐盘、包装标签、菜单或食物照片估算食物类型、份量、热量、蛋白质、脂肪、碳水和纤维，并说明不确定性。
- 支持训练和饮食联动 check-in：把动作质量、训练容量、食物照片、营养估算、当天目标和恢复数据放在一起判断，再决定训练或饮食怎么调整。
- 指导饮食：热量、蛋白质、脂肪、碳水、纤维、水分、餐次安排、补剂边界和调整规则。
- 根据体重趋势、围度、照片、训练表现、睡眠、步数、饥饿感、疲劳和执行率做阶段化调整。
- 覆盖恢复与伤病风险控制：睡眠、疲劳、疼痛规则、热身、活动度、deload 和回归训练。
- 在用户需要时提供比赛相关框架：contest prep、stage conditioning、posing practice、show logistics、peak week 边界和 post-show recovery。

## Android App

原生 Android companion app 位于 [`android-app/`](android-app/)。它会把这个 skill 打包进 Android assets，并提供日常训练、日常饮食、身体指标、照片分析和 AI 复盘工作流。

当前 app 已支持：

- 简洁 Apple-inspired 浅色界面：安静背景、蓝色主操作、底部导航、分组卡片和清晰层级。
- `Today` Command Center：首页显示 readiness、下一步行动、训练、饮食、恢复和体成分卡片，让新手知道先做什么，也让进阶用户快速扫关键数据。
- 本地每日历史记录和 7 天趋势卡片：追踪体重、热量、蛋白质、睡眠、步数、hard sets 和训练量，并把趋势行加入 AI 复盘，避免只根据某一天数据做过度调整。
- AI 复盘结果本地保存：最新建议会显示在 `Today`，最近几次复盘可在 `AI Coach` 回看，让 app 更像持续跟进的每日教练，而不是一次性的聊天回答。
- Athlete Profile 个人档案：在 `Plan` 中记录长期体型目标、当前阶段、训练经验、目标体重/体脂、每周训练天数、器械条件、弱项、饮食偏好和限制条件；这些信息会进入每日 AI 复盘，让训练计划和饮食建议更贴合个人目标。
- 每周训练计划：在 `Plan` 中设置阶段目标、训练日、计划动作、组数、次数、RIR、休息时间和备注。
- 今日训练执行：点击 `Apply today` 将某一天计划转换成今天的 set-level 训练日志。
- 统一动作示意：计划动作卡片和今日训练卡片都会显示简洁的器械/动作示意图；`Plan` 页还提供 Exercise Visual Library 图例库，集中展示 Smith 机、绳索、哑铃、杠铃、固定器械、自重/开放空间动作的实例图、示例动作和识别提示。
- 单组记录：每一组都可以记录重量、次数、RIR、是否完成、休息时间和备注。
- Exercise History：重复动作会对比上一次记录，显示上次/今天训练量、最佳重量、最佳次数、完成组数和平均 RIR，让用户知道这次是真进步、持平还是需要先稳住。
- Progression Cue：每个训练动作会根据完成组数、实际次数、RIR 和疼痛/动作备注，提示下次应该加次数、加重量、保持、修改动作或先完成基础组数。
- 休息倒计时：点击一组 `Complete` 后自动启动休息倒计时。
- Nutrition Pacing：显示今天热量、蛋白质、碳水、脂肪、纤维还剩多少或已经超出多少，给出宏量营养执行分数和下一餐建议，让用户知道接下来该优先吃什么。
- Body Composition Guidance：根据当前阶段、体重趋势、平均热量、平均蛋白质和训练输出，判断热量目标应该保持、小幅上调还是小幅下调，避免只凭一天体重或一天饮食做过度调整。
- 饮食记录：设置热量和宏量营养目标，记录每餐热量、蛋白质、碳水、脂肪、纤维，并可上传食物照片让 AI 估算。
- 身体与恢复指标：体重、体脂、瘦体重、腰围、睡眠、步数、静息心率、总消耗热量、饥饿感、疲劳、酸痛、压力和每日反思。
- Health Connect 同步：用户授权后读取体重、体脂、瘦体重、步数、睡眠、静息心率和总消耗热量。
- AI 复盘：把周计划、今日实际训练、每组表现、Exercise History、Progression Cue、Nutrition Pacing、Body Composition Guidance、饮食、照片、健康数据和反思一起发送给模型，判断下一次训练是否加重量、加次数、保持、减少容量、替换动作、deload，或者调整热量、碳水、蛋白质、脂肪、纤维、水分和餐次安排。

## 小米、华为和手机健康数据

可以做到，但正确方式不是绕过系统权限直接读取厂商私有数据库，而是采用分层接入：

1. 第一层：Android Health Connect。
   app 已加入 Health Connect 读取能力，可以读取用户授权后的体重、体脂、瘦体重、步数、睡眠、静息心率和总消耗热量。

2. 小米/华为/体脂秤/手表数据。
   如果对应的厂商 App 或设备 App 会把数据写入或同步到 Health Connect，并且用户授权本 app 读取，那么这些数据就能进入 `Metrics`，再进入 AI 复盘。也就是说，小米/Mi Fitness、华为健康、体脂秤、手表、手机记录的数据是否能被本 app 读到，关键取决于它们是否把相应记录开放到 Health Connect。

3. 华为深度接入。
   如果需要更完整的华为生态数据，可以后续增加 Huawei Health Kit 作为独立 provider 模块。这样不会影响其他 Android 用户，也能保持 Health Connect 作为统一入口。这个属于下一层厂商 SDK 集成，通常还需要华为开发者配置、权限申请和真机验证。

4. 兜底方式。
   如果 Health Connect 不可用、用户拒绝权限，或者厂商 App 没有导出对应数据，app 仍然支持手动输入；后续也可以增加 CSV、截图/OCR 或厂商导出文件导入。

当前实现状态：已经实现 Health Connect 只读同步；华为 Health Kit 深度 provider 和小米专用 provider 还没有接入，但架构上可以继续扩展。只要健康数据进入 Health Connect，本 app 就可以在用户授权后读取并用于 AI 分析。

这些健康数据会被标记来源和同步时间。AI 复盘会把它们当作近似信号，而不是对单日体脂、手表热量消耗或睡眠估算做过度反应。

## API 设置

API key、base URL 和 model 都在 app 内配置，凭据只保存在本机 SharedPreferences，不写入源代码。

默认网络请求形态兼容 OpenAI Responses API：

```text
{base URL}/responses
```

它会发送：

- `instructions`：内置 skill prompt 和相关 reference。
- `input_text`：用户问题、训练日志、饮食日志、健康数据和 AI 复盘上下文。
- `input_image`：可选照片，包括动作帧、器械照片、食物照片、包装标签、菜单和进度照片。

## 安装 Skill

将 skill 文件夹复制到 Codex skills 目录：

```bash
cp -R skills/i-want-to-be-an-ifbb-pro ~/.codex/skills/
```

调用名：

```text
$i-want-to-be-an-ifbb-pro
```

示例：

```text
Use $i-want-to-be-an-ifbb-pro 帮我制定一个 2 年职业级体型发展路线，包括增肌期、减脂期、弱项补强、posing 和 check-in 指标。
```

## 模板

- `intake-form.md`：用于收集目标、身体数据、训练历史、器械、饮食、恢复和安全信息。
- `plan-template.md`：用于输出结构化训练、饮食、恢复和追踪计划。
- `check-in-form.md`：用于每周或每两周复盘体重、围度、训练表现、饥饿、疲劳和执行率。
- `session-log.csv`：用于记录每次训练的动作、组数、重量、次数、RIR/RPE、疼痛和目标肌群刺激。
- `tracking-log.csv`：用于记录长期体重、围度、照片、睡眠、步数、热量、宏量营养和恢复趋势。

## 证据基础

这个 skill 包含 [evidence-map.md](skills/i-want-to-be-an-ifbb-pro/references/evidence-map.md)，说明训练、营养、恢复、图像辅助饮食评估和数据调整能力分别由哪些 position stand、systematic review、meta-analysis、实验研究或验证研究支持。

证据来源包括：

- WHO 身体活动指南。
- ACSM 阻力训练和运动筛查相关指南。
- ISSN 关于蛋白质、饮食与体成分、肌酸的立场声明。
- Schoenfeld 等关于训练容量、频率、负荷和肌肥大的系统综述与 meta-analysis。
- Morton 等关于蛋白质摄入与阻力训练适应的 meta-analysis/meta-regression。
- Helms、Aragon、Fitschen 等关于自然健美备赛营养的证据综述。
- Boushey/mobile food record 以及图像辅助饮食评估相关验证研究。
- IFBB Pro League / NPC / NPC Worldwide 公开资源，用于真实参赛场景下的规则核验提示。

这些证据用于支持默认建议，但不会被当成绝对答案。最终仍需要结合用户的训练年龄、恢复能力、身体数据、照片、训练日志、饮食记录和阶段性反馈来调整。

## 测试

运行：

```bash
python -m unittest tests.test_android_app_structure tests.test_skill_completeness tests.test_scripts_smoke
python scripts/validate_skill.py skills/i-want-to-be-an-ifbb-pro
```

## 安全边界

这个 skill 和 Android app 提供训练、营养和体型管理的教育与规划支持，不是医疗诊断、物理治疗、临床营养治疗、药物指导或官方比赛规则来源。

照片/视频动作分析和食物照片营养估算只能作为近似判断，不能替代线下教练、医生、物理治疗师、注册营养师，也不能替代称重、食品标签和真实饮食数据。

如果用户有疼痛、受伤、胸痛、晕厥、呼吸异常、严重疲劳、进食障碍风险、极端减重目标、妊娠或产后情况、未控制疾病，应优先寻求合格专业人士帮助。

本项目不提供 anabolic steroids、growth hormone、diuretics、SARMs、甲状腺药物、兴奋剂或其他药物的周期、剂量、来源、protocol 或 peak week 危险操作。

## GitHub

仓库地址：

https://github.com/JackieL233/i-want-to-be-an-ifbb-pro
