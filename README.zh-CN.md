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
- 当器械被占、不可用、动作疼痛或技术不稳定时，提供 Exercise Substitution Coach：推荐同目标肌群、同动作模式的 primaryOption 和 secondaryOptions，同时尽量保持原计划次数范围、RIR 意图、疲劳成本和 visual guide ID 连续性，让用户不用因为器械问题中断训练。
- 为动作名称提供新手友好的器械/动作上下文：例如 VG-01 Smith machine、VG-02 Cable station、VG-03 Dumbbells、VG-04 Barbell、VG-05 Machine、VG-10 Bodyweight/open station 等统一示意类别，并通过 Unified Exercise Visual Atlas 配套视觉图例编号、中文器械名、简洁统一实例图、三步识别、quick visual cue、find-equipment cue、movement path cue、动作路径提示、新手识别提示、器械标志点、常见动作、示例动作和 look-for 识别提示，帮助用户知道这个动作大概对应哪种器械和动作路径。
- 提供 `Exercise Visual Legend / 统一动作图例`：用 VG-01 到 VG-10 的统一简洁实例图做一个小图鉴，让不是专业 pro 的用户先看图标、中文器械名、真实器械标志点和动作路径，再去理解 Incline Smith Press、Cable Lateral Raise 这类专业动作名。
- 在计划日和今日训练中提供器械/动作 Visual Map 和动作卡片缩略图标题，让用户不用先懂专业动作名，也能一眼知道今天要找哪类器械、训练站、自由重量或动作路径。
- 支持器械识别和动作质量分析：通过器械照片、动作照片或视频帧判断可能的器械/动作、目标肌群、常见错误、替代动作和安全注意点。
- 支持 Photo Evidence 照片证据分类：食物/标签照片、动作帧、器械照片、体型进度照片、菜单/营养标签和其他 check-in 照片会记录用途、文件名、备注、MIME 类型和时间；AI 复盘收到图片时也会知道这张图应该用于饮食估算、动作质量、器械识别还是体型对比。
- 支持食物照片营养估算：通过餐盘、包装标签、菜单或食物照片估算食物类型、份量、热量、蛋白质、脂肪、碳水和纤维，并说明不确定性。
- 支持训练和饮食联动 check-in：把动作质量、训练容量、食物照片、营养估算、当天目标和恢复数据放在一起判断，再决定训练或饮食怎么调整。
- 指导饮食：热量、蛋白质、脂肪、碳水、纤维、水分、餐次安排、补剂边界和调整规则。
- 根据体重趋势、围度、照片、训练表现、睡眠、步数、饥饿感、疲劳和执行率做阶段化调整。
- 记录 bodybuilding 相关体型维度：腰围、胸围、肩围、臀围、左右手臂、左右大腿、颈围，并用 Physique Measurement Summary 判断肩腰比、手臂/大腿左右对称、腰围控制、弱项响应和围度追踪质量。
- 覆盖恢复与伤病风险控制：睡眠、疲劳、疼痛规则、热身、活动度、deload 和回归训练。
- 在用户需要时提供比赛相关框架：contest prep、stage conditioning、posing practice、show logistics、peak week 边界和 post-show recovery。

## Android App

原生 Android companion app 位于 [`android-app/`](android-app/)。它会把这个 skill 打包进 Android assets，并提供日常训练、日常饮食、身体指标、照片分析和 AI 复盘工作流。

当前 app 已支持：

- 简洁 Apple-inspired 浅色界面：安静背景、蓝色主操作、底部导航、分组卡片和清晰层级。
- 支持 EN/中文界面切换：通过 `AppLanguage`、`updateLanguage`、`loadLanguage` 和本地 SharedPreferences 保存语言偏好，让顶部导航、底部导航、今日流程、休息倒计时和安全提示可以在英文与中文之间切换。
- `Start Here / 从这里开始` 每日闭环：`Today` 第一屏先显示今日进度、readiness、下一步和一个主按钮，用 `DailyStartStep`、`StartHereCoachCard`、`StartHereStepRow` 引导用户按“计划 -> 训练 -> 饮食 -> 身体数据 -> AI 复盘”执行，再进入更专业的数据卡片。
- `Workout Flow Coach / 训练执行教练`：`Training` 第一屏只突出下一步动作，例如载入计划、热身、完成下一组、开始休息、训练收尾或运行 AI 复盘；正常训练时用户主要点击 `Complete set + start rest`，不用先读完所有分析模块。
- `Professional detail layers / 专业细节层`：Training Readiness Builder、Warm-up Ramp Plan、Next Set Coach、Session Quality Dashboard 和 Training Closeout Coach 默认收在专业细节层里，需要理解原因或调整策略时再展开，避免新手一打开训练页就被信息淹没。
- `AI Setup & Review Readiness / AI 设置与复盘就绪状态`：用 `AiSetupStatus`、`AiSetupStatusCard` 和 `AiSettings.isConfigured()` 检查 API key、base URL、model 和照片上下文；如果设置缺失，每日闭环会先引导用户去 `AI Coach` 设置，而不是到最后一步才报 API 错误，训练、饮食、身体数据和照片仍会继续本地保存。
- `Today` Command Center：首页显示 readiness、下一步行动、训练、饮食、恢复和体成分卡片，让新手知道先做什么，也让进阶用户快速扫关键数据。
- Daily Execution Plan：在 `Today` 中把当天训练执行、饮食缺口、恢复状态、数据完整度和 AI 复盘时机合成一个主决策层，显示 priority focus、primary action、training decision、nutrition decision、recovery decision、data quality gate、AI review gate 和 plan adjustment signal，让用户每天打开 app 后知道第一件事该做什么。
- Tomorrow Coach Brief：在 `Today` 中把周计划、训练质量、身体趋势、饮食完成度和恢复信号合成明天的可执行 brief，明确明天 plan day、训练重点、热量/蛋白目标、readiness gate、恢复动作、追踪动作和明天打开 app 的第一步，避免 AI 复盘只停留在一段文字建议。
- Weekly Check-in：在 `Today` 中用 7-14 天数据检查训练完成率、平均热量、平均蛋白、体重趋势、恢复均分、弱项重点、数据质量和下一周动作；AI 在改变全周训练容量或热量目标前，必须先看这个周级闸门，避免因为一天表现就大改计划。
- Daily Coach Checklist：在 `Today` 中把每天必须完成的闭环变成可点击清单，包括 `Plan prepared`、`Training executed`、`Food logged`、`Metrics synced` 和 `AI review locked`，让用户可以照着 app 完成训练、饮食、健康数据和 AI 复盘。
- 本地每日历史记录和 7 天趋势卡片：追踪体重、热量、蛋白质、睡眠、步数、hard sets 和训练量，并把趋势行加入 AI 复盘，避免只根据某一天数据做过度调整。
- AI 复盘结果本地保存：最新建议会显示在 `Today`，最近几次复盘可在 `AI Coach` 回看，让 app 更像持续跟进的每日教练，而不是一次性的聊天回答。
- AI Review Action Queue：在 `Today` 和 `AI Coach` 中把最新保存的 AI 复盘转换成可点击的训练行动、饮食行动、恢复行动、追踪行动和计划行动，并显示 sourceLabel、confidenceLabel、primaryAction、actionLabel 和 evidence cue；这样 AI 建议不会只是留在一段文字里，而是能直接跳到 Training、Nutrition、Metrics、Plan 或 AI Coach 继续执行。
- Athlete Profile 个人档案：在 `Plan` 中记录长期体型目标、当前阶段、训练经验、目标体重/体脂、每周训练天数、器械条件、弱项、饮食偏好和限制条件；这些信息会进入每日 AI 复盘，让训练计划和饮食建议更贴合个人目标。
- Plan Templates 训练模板：内置 Beginner Full Body、4-Day Hypertrophy 和 5-Day Physique Priority，用户可以一键生成可直接训练的周计划，再按器械、弱项、恢复和个人偏好微调。
- 每周训练计划：在 `Plan` 中设置阶段目标、训练日、计划动作、组数、次数、RIR、休息时间和备注。
- 今日训练执行：点击 `Apply today` 将某一天计划转换成今天的 set-level 训练日志。
- 训练照片入口：`Training` 页可以直接添加 form photo 和 equipment photo，用于动作质量、目标肌群刺激、疼痛标记、器械识别和 visual guide ID 对照。
- Training Readiness Builder：在 `Training` 页把 Recovery Guidance 转换成训练前执行闸门，包括热身策略、ramp-up 提示、第一组工作组选择、容量调整和停止规则，让用户进健身房时知道今天该怎么开始、什么时候不该硬推。
- Warm-up Ramp Plan：在 Training Readiness Builder 和 Next Set Coach 之间，把当前动作、计划重量、目标次数、RIR、恢复状态和 visual guide ID 转成可直接照做的热身递增组清单，包括 planned load percentage、每个 ramp set 的次数/努力程度、final ramp set quality、first working set gate 和 stop rule；这样用户不只知道要找哪台器械，也知道从多轻开始、怎么递增、什么时候可以进入第一组 working set。
- Next Set Coach：在 `Training` 页自动找到第一组还没完成的 working set，并把当前动作、下一组目标、建议重量、目标次数、RIR、休息秒数、停止规则、完成后记录提示和对应的器械/动作实例图放在同一张卡片里；用户不懂动作名时，也能通过 VG 图例编号、中文器械名、统一实例图、quick visual cue、find-equipment cue、movement path cue 和 look-for cue 知道该找哪台器械或哪个动作路径。
- Session Quality Dashboard：在 `Training` 页汇总整节训练质量，包括 completion rate、logged set rate、average RIR、completed volume、muscle-volume distribution、pain flags、technique flags、quality cue、capacity cue 和 risk cue，让用户训练中途或结束后知道这节课是否足够可靠，可以不可以作为下一次加重量/加次数/调整容量的依据。
- Training Closeout Coach：在 Session Quality Dashboard 之后做训练收尾检查，确认 completed sets、missing set logs、pain/technique flags、form/equipment photo evidence、post-workout nutrition cue、metrics sync cue、session notes、closeout score 和 AI review readiness。也就是说，统一器械/动作实例图、训练照片、器械照片、饮食照片/餐食记录、Health Connect 或手动健康数据会在 AI 复盘前被放到同一条证据链里；如果缺少关键 set log、动作照片、训练后饮食或身体恢复指标，app 会先提示补齐，再让 AI 改明天的训练、容量、重量或饮食。
- Today Visual Primer：在 `Today` 页训练开始前展示当天动作的统一简洁器械/动作实例图、visual-map thumbnails、VG 编号、中文器械名和新手识别提示；用户不需要先记住 Incline Smith Press、Cable Lateral Raise 这类动作名，也能先知道今天要找史密斯机、龙门架、哑铃、杠铃、固定器械、训练凳、引体/双杠站、弹力带、腿举/哈克机或自重开放空间。
- 统一动作示意：计划动作卡片和今日训练卡片都会显示简洁的器械/动作示意图；动作卡片标题区直接带缩略图、VG 编号、中文器械名和 look-for cue；`Plan` 页有 Unified Exercise Visual Atlas、`Exercise Visual Legend / 统一动作图例`、Selected Day Visual Map，`Training` 页有 Today's Exercise Visual Map，可以把当天所有动作先汇总成器械/动作速览；`Add Planned Exercise` 和 `Add Exercise` 会在输入动作名称时实时显示 live equipment/action preview，告诉用户这个动作大概率对应哪一个 VG 视觉图例编号、哪类器械、中文叫什么、常见动作有哪些；每张图例都用同一套三步识别：先看简洁实例图、再找真实器械标志点、最后确认动作路径；`Plan` 页还提供 Exercise Visual Library 图例库，先用紧凑的 VG-01 到 VG-10 图鉴建立视觉记忆，再集中展示 Smith 机、绳索、哑铃、杠铃、固定器械、可调训练凳、引体/双杠站、弹力带、腿举/哈克深蹲、自重/开放空间动作的统一实例图、quick visual cue、find-equipment cue、movement path cue、实例图提示、动作路径提示、新手识别提示、器械标志点、示例动作、常见动作和识别提示，帮助不是 pro 的用户直观看懂动作名称对应哪类器械或动作路径。
- 单组记录：每一组都可以记录重量、次数、RIR、是否完成、休息时间和备注。
- Exercise History：重复动作会对比上一次记录，显示上次/今天训练量、最佳重量、最佳次数、完成组数和平均 RIR，让用户知道这次是真进步、持平还是需要先稳住。
- Progression Cue：每个训练动作会根据完成组数、实际次数、RIR 和疼痛/动作备注，提示下次应该加次数、加重量、保持、修改动作或先完成基础组数。
- Exercise Substitution Coach：每个训练动作都会给出临场替代建议；如果器械被占、不可用、动作疼痛、动作技术不稳定或用户档案里的器械条件不匹配，App 会推荐一个 primaryOption 和多个 secondaryOptions，并标明 trigger reason、same target muscle、same movement pattern、preserve rep range、fatigue cost、keepIntentCue、loadAdjustmentCue 和 visual guide ID。它不会静默改掉训练日志，而是帮助用户在训练现场保持同样训练意图继续练，并让 AI 复盘知道这次替代为什么发生。
- 休息倒计时：点击一组 `Complete` 后自动启动休息倒计时。
- Nutrition Pacing：显示今天热量、蛋白质、碳水、脂肪、纤维还剩多少或已经超出多少，给出宏量营养执行分数和下一餐建议，让用户知道接下来该优先吃什么。
- Next Meal Builder：把剩余热量、蛋白质、碳水、脂肪、纤维和训练需求转换成下一餐可执行目标，包含下一餐大概 P/C/F/纤维克数、meal timing cue、portion uncertainty cue 和照片/标签提示。
- Meal Assembly Guide：把 Next Meal Builder 的宏量目标翻译成新手可以直接照做的一盘饭，包括 plate structure、protein anchor、carb anchor、fat control、fiber/micros、shopping cue、prep cue、photo/logging cue 和 avoid cue，帮助用户知道下一餐该选什么食材、怎么控制油和酱料、什么时候需要拍照给 AI 估算。
- Meal Templates 餐食模板：内置 Lean Protein Bowl、Pre-Workout Carbs、Low-Fat Protein Fix、Salmon Potato Plate 和 Fiber + Micronutrient Add，用户可以快速添加常见高蛋白餐，再根据实际份量、油、酱料、标签或照片修正。
- Body Composition Guidance：根据当前阶段、体重趋势、平均热量、平均蛋白质和训练输出，判断热量目标应该保持、小幅上调还是小幅下调，避免只凭一天体重或一天饮食做过度调整。
- Conditioning & Hydration：记录 step goal、cardio minutes/type/intensity、水分 L、sodium mg、caffeine mg、alcohol servings、digestion 和备注，让 AI 区分真正需要改热量/训练，还是只是 NEAT、有氧、水钠、咖啡因、酒精、消化和 scale-weight noise 造成的波动。
- Recovery Guidance：根据睡眠、疲劳、酸痛、压力、静息心率、计划/完成组数和近期趋势，判断今天训练应该继续推进、保持训练压力、减少容量，还是进入 deload 检查。
- 饮食记录：设置热量和宏量营养目标，记录每餐热量、蛋白质、碳水、脂肪、纤维，并可上传食物照片让 AI 估算。
- 食物照片证据：`Nutrition` 页上传的 meal photo 会作为 Photo Evidence 保存，连同餐名、份量不确定性、油、酱料、标签、菜单和备注一起进入 AI 饮食分析。
- 身体与恢复指标：体重、体脂、瘦体重、腰围、胸围、肩围、臀围、左右手臂、左右大腿、颈围、睡眠、步数、静息心率、总消耗热量、饥饿感、疲劳、酸痛、压力和每日反思。
- 体型进度照片：`Metrics` 页可以上传 progress photo，并提示尽量保持相同光线、距离、姿势和 pump 状态；AI 会把照片和围度、体重、体脂、训练量一起看，而不是只看单张照片。
- Physique Measurement Summary：把围度数据转换成体型比例与对称性提示，包括 shoulder-to-waist ratio、waist change、chest change、shoulder change、hip change、left/right arm、left/right thigh、arm symmetry、thigh symmetry、V-taper direction 和 weekly tape-measure check-in，帮助 AI 判断体重变化是更接近有效增肌、腰围失控，还是需要调整弱项训练。
- Health Connect 同步：用户授权后读取体重、体脂、瘦体重、步数、睡眠、静息心率和总消耗热量。
- AI 复盘：把周计划、今日实际训练、Daily Execution Plan、priority focus、primary action、data quality gate、AI review gate、plan adjustment signal、AI Review Action Queue、sourceLabel、confidenceLabel、primaryAction、actionLabel、training action、nutrition action、recovery action、tracking action、plan action、Training Readiness Builder、Warm-up Ramp Plan、ramp set checklist、planned load percentage、final ramp set quality、first working set gate、Next Set Coach、current exercise、next set target、load cue、reps cue、RIR cue、rest cue、stop cue、after-set logging cue、Tomorrow Coach Brief、Weekly Check-in、training completion、average calories/protein、weight trend、recovery average、weak-point focus、next-week action、tomorrow training focus、tomorrow nutrition target、tomorrow recovery gate、tomorrow tracking action、readiness gate、Session Quality Dashboard、completion rate、logged set rate、average RIR、muscle-volume distribution、pain/technique flags、热身策略、第一组工作组、容量调整、停止规则、每组表现、Exercise History、Progression Cue、Exercise Substitution Coach、equipment unavailable、trigger reason、primaryOption、secondaryOptions、same target muscle、same movement pattern、preserve rep range、fatigue cost、keepIntentCue、loadAdjustmentCue、Exercise visual guide、Unified Exercise Visual Atlas、Exercise Visual Legend、统一动作图例、视觉图例编号、中文器械名、统一实例图、三步识别、quick visual cue、find-equipment cue、movement path cue、动作路径提示、新手识别提示、器械标志点、实例图提示、常见动作、Nutrition Pacing、Conditioning & Hydration、step goal、cardio minutes、cardio type、cardio intensity、water liters、sodium mg、caffeine mg、alcohol servings、digestion notes、scale-weight noise、Next Meal Builder、Meal Assembly Guide、plate structure、protein anchor、carb anchor、fat control、shopping/prep cue、Body Composition Guidance、Physique Measurement Summary、waistCm、chestCm、shoulderCm、hipCm、leftArmCm、rightArmCm、leftThighCm、rightThighCm、neckCm、shoulder-to-waist ratio、arm symmetry、thigh symmetry、Recovery Guidance、饮食、照片、健康数据和反思一起发送给模型，判断下一次训练是否加重量、加次数、保持、减少容量、替换动作、deload，或者调整热量、碳水、蛋白质、脂肪、纤维、水分、钠、有氧、步数、咖啡因和餐次安排；保存后的复盘会再进入 AI Review Action Queue，变成可以点击执行的下一步。

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
