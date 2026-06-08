# I Want to be an IFBB PRO Skill 中文说明

`I Want to be an IFBB PRO` 是一个开源 AI skill，用于专业级个性化体型训练规划。这个名字是一个抽象的目标形象：用 IFBB PRO 代表职业级严谨度、体型标准、训练执行、营养管理、恢复管理和长期数据追踪，而不是默认要求使用者真的去拿职业卡。

它可以帮助用户创建新的训练计划，也可以优化现有训练计划；可以用于增肌、减脂、重组、维持、deload、训练恢复、备赛灵感的体脂控制、posing 练习、多模态照片/视频分析和阶段化 check-in。核心目标是把“我想练到非常专业的体型”拆成可执行、可追踪、可调整的长期系统。

本项目不是 IFBB Pro League、NPC 或 NPC Worldwide 的官方资源，也不代表任何官方背书。如果用户真的要参加比赛，规则、组别、报名、晋级路径和赛事细节必须以官方组织者和赛事网站的最新信息为准。

## 能做什么

- 根据目标、身体数据、训练年龄、器械条件、训练频率、恢复能力和限制条件，创建个性化训练计划。
- 审核并优化已有训练计划，而不是直接推翻重做。
- 支持增肌期、减脂期、体型重组、维持期、deload、恢复训练和长期弱项补强。
- 将 “I Want to be an IFBB PRO” 拆解为职业级体型标准：肌肉量、比例、对称性、体脂控制、动作执行、posing 和数据纪律。
- 细化每次训练：动作、目标肌群、组数、次数、RIR/RPE、休息、技术要点、替代动作和记录字段。
- 追踪训练质量：hard sets、tonnage、effective reps、目标肌肉刺激、疼痛标记、动作技术和进步决策。
- 支持器械识别和动作质量分析：通过器械照片、动作照片或视频帧判断可能的器械/动作、目标肌群、常见错误、替代动作和安全注意点。
- 支持食物照片营养估算：通过餐盘、包装标签、菜单或食物照片估算食物类型、份量、热量、蛋白质、脂肪、碳水、纤维，并说明不确定性。
- 支持训练和饮食联动 check-in：把动作质量、训练容量、食物照片、营养估算、当天目标和恢复数据放在一起判断，再决定训练或饮食怎么调整。
- 指导饮食：热量、蛋白质、脂肪、碳水、纤维、水分、餐次安排、补剂边界和调整规则。
- 根据体重趋势、围度、照片、训练表现、睡眠、饥饿感、疲劳和执行率做阶段化调整。
- 覆盖恢复与伤病风险控制：睡眠、疲劳、疼痛规则、热身、活动度、deload 和回归训练。
- 在用户需要时提供比赛相关框架：contest prep、stage conditioning、posing practice、show logistics、peak week 边界和 post-show recovery。

## 安装

把 skill 文件夹复制到 Codex skills 目录：

```bash
cp -R skills/i-want-to-be-an-ifbb-pro ~/.codex/skills/
```

调用名是：

```text
$i-want-to-be-an-ifbb-pro
```

示例：

```text
Use $i-want-to-be-an-ifbb-pro to create a long-term bodybuilding roadmap toward an IFBB PRO-inspired physique goal.
```

## 示例用法

创建长期体型路线：

```text
Use $i-want-to-be-an-ifbb-pro 帮我制定一个 2-3 年的职业级体型发展路线，包括增肌期、减脂期、弱项补强、posing 和 check-in 指标。
```

创建新的训练计划：

```text
Use $i-want-to-be-an-ifbb-pro 我每周可以训练 5 天，目标是增肌并改善肩宽、背宽和腿部比例，请给我一个 12 周训练计划。
```

优化现有计划：

```text
Use $i-want-to-be-an-ifbb-pro 帮我审核现在的 push/pull/legs 计划，检查每个肌群 weekly hard sets、动作顺序、疲劳管理和进步规则。
```

分析训练记录：

```text
Use $i-want-to-be-an-ifbb-pro 分析我的 session-log.csv，告诉我哪些动作应该加次数、加重量、维持、减少容量、deload 或替换。
```

多模态训练和饮食联动：

```text
Use $i-want-to-be-an-ifbb-pro 帮我一起看今天的深蹲视频帧和食物照片：识别动作问题、估算这餐营养，并判断是训练动作、热量、碳水安排还是恢复需要调整。
```

阶段化减脂和 posing：

```text
Use $i-want-to-be-an-ifbb-pro 帮我安排 20 周减脂期，包括热量调整、训练容量、posing 练习、stage conditioning 指标和 peak week 安全边界。
```

## 项目结构

```text
skills/i-want-to-be-an-ifbb-pro/
  SKILL.md
  agents/openai.yaml
  references/
    safety-screening.md
    intake-assessment.md
    anatomy-and-movement.md
    goal-decision-system.md
    evidence-map.md
    pro-level-physique-roadmap.md
    plan-optimization.md
    training-programming.md
    exercise-library.md
    phase-templates.md
    session-execution-and-volume.md
    visual-analysis-and-food-estimation.md
    contest-prep-and-posing.md
    nutrition-body-composition.md
    recovery-injury-risk.md
    data-tracking-adjustment.md
    adaptation-playbook.md
    model-adaptation.md
    sources.md
  assets/templates/
    intake-form.md
    check-in-form.md
    plan-template.md
    session-log.csv
    tracking-log.csv
  scripts/
    estimate_targets.py
    analyze_checkin.py
    analyze_training_volume.py
```

## Android App

原生 Android companion app 位于 [`android-app/`](android-app/)。它会把这个 skill 打包进 Android assets，并提供日常训练、日常饮食、身体指标、照片分析和 AI 复盘工作流。

当前 app 已支持每周训练计划和训练执行级记录：先在 `Plan` 里建立 weekly training plan，设置阶段目标、训练日、计划动作、组数、次数、RIR、休息时间和备注；再点击 `Apply today` 把某一天计划转成今天的 set-level 可执行训练。每个动作可以拆成每一组，记录重量、次数、RIR、是否完成、休息时间和备注；点击一组 `Complete` 后会启动休息倒计时。AI 复盘会把周计划、当天实际单组表现、饮食、食物照片、动作/器械照片、体重、腰围、睡眠、步数、饥饿感、疲劳、酸痛、压力一起发送给模型，用于判断下一次训练是加重量、加次数、维持、减少容量、替换动作、deload，还是调整热量、碳水、蛋白质、脂肪、纤维、补水或餐次安排。

API key、base URL 和 model 都在 app 内配置，凭据只保存在本机 SharedPreferences，不写入源码。构建说明见 [`android-app/README.md`](android-app/README.md)。

## 模板

- `intake-form.md`：用于收集目标、身体数据、训练历史、器械、饮食、恢复和安全信息。
- `plan-template.md`：用于输出结构化训练、饮食、恢复和追踪计划。
- `check-in-form.md`：用于每周或每两周复盘体重、围度、训练表现、饥饿、疲劳和执行率。
- `session-log.csv`：用于记录每次训练的动作、组数、重量、次数、RIR/RPE、疼痛和目标肌群刺激。
- `tracking-log.csv`：用于记录长期体重、围度、照片、睡眠、步数、热量、宏量营养和恢复趋势。

## 脚本

估算热量和宏量营养起点：

```bash
python skills/i-want-to-be-an-ifbb-pro/scripts/estimate_targets.py --sex male --age 30 --height-cm 178 --weight-kg 80 --activity moderate --goal fat-loss
```

分析 check-in 趋势：

```bash
python skills/i-want-to-be-an-ifbb-pro/scripts/analyze_checkin.py --input-json examples/sample-checkin.json
```

分析训练容量和动作表现：

```bash
python skills/i-want-to-be-an-ifbb-pro/scripts/analyze_training_volume.py --session-csv examples/sample-session-log.csv
```

## 安全边界

这个 skill 提供训练、营养和体型管理的教育与规划支持，不是医疗诊断、物理治疗、临床营养治疗、药物指导或官方比赛规则来源。

照片/视频动作分析和食物照片营养估算只能作为近似判断，不能替代线下教练、医生、物理治疗师、注册营养师，也不能替代称重、标签和真实食谱数据。

如果用户有疼痛、受伤、胸痛、晕厥、呼吸异常、严重疲劳、进食障碍风险、极端减重目标、妊娠或产后情况、未控制的疾病，应该优先寻求合格专业人士帮助。

这个 skill 不提供 anabolic steroids、growth hormone、diuretics、SARMs、甲状腺药物、兴奋剂或其他药物的周期、剂量、来源、protocol 或 peak week 危险操作。

## 证据基础

这个 skill 新增了 [evidence-map.md](skills/i-want-to-be-an-ifbb-pro/references/evidence-map.md)，明确说明每一类能力由哪些论文、实验类型、position stand、systematic review、meta-analysis 或 validation study 支撑。

它覆盖的证据包括：ACSM 阻力训练指南、WHO 身体活动指南、ISSN 蛋白质/体成分/肌酸立场声明、Schoenfeld 等关于训练容量和肌肥大的系统综述与 meta-analysis、Morton 等关于蛋白质补充和阻力训练适应的 meta-analysis/meta-regression、Helms/Aragon/Fitschen 关于自然健美备赛营养的证据综述，以及 Boushey/mobile food record 和图像辅助饮食评估相关研究。

这些证据会支持 skill 的默认建议，但不会被当成绝对答案；最终仍需要结合用户的训练年龄、恢复能力、身体数据、照片、训练日志、饮食记录和阶段性反馈来调整。

## 测试

运行：

```bash
python -m unittest tests.test_skill_completeness tests.test_scripts_smoke
python scripts/validate_skill.py skills/i-want-to-be-an-ifbb-pro
```

## GitHub

仓库地址：

https://github.com/JackieL233/i-want-to-be-an-ifbb-pro
