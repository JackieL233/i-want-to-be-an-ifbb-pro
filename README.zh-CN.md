# I Want to be an IFBB PRO Skill 中文说明

`I Want to be an IFBB PRO` 是一个开源 AI skill，用于专业级个性化体型训练规划。这个名字是一个抽象的目标形象：用 IFBB PRO 代表职业级严谨度、体型标准、训练执行、营养管理、恢复管理和长期数据追踪，而不是默认要求使用者真的去拿职业卡。

它可以帮助用户创建新的训练计划，也可以优化现有训练计划；可以用于增肌、减脂、重组、维持、deload、训练恢复、备赛灵感的体脂控制、posing 练习和阶段化 check-in。核心目标是把“我想练到非常专业的体型”拆成可执行、可追踪、可调整的长期系统。

本项目不是 IFBB Pro League、NPC 或 NPC Worldwide 的官方资源，也不代表任何官方背书。如果用户真的要参加比赛，规则、组别、报名、晋级路径和赛事细节必须以官方组织者和赛事网站的最新信息为准。

## 能做什么

- 根据目标、身体数据、训练年龄、器械条件、训练频率、恢复能力和限制条件，创建个性化训练计划。
- 审核并优化已有训练计划，而不是直接推翻重做。
- 支持增肌期、减脂期、体型重组、维持期、deload、恢复训练和长期弱项补强。
- 将 “I Want to be an IFBB PRO” 拆解为职业级体型标准：肌肉量、比例、对称性、体脂控制、动作执行、posing 和数据纪律。
- 细化每次训练：动作、目标肌群、组数、次数、RIR/RPE、休息、技术要点、替代动作和记录字段。
- 追踪训练质量：hard sets、tonnage、effective reps、目标肌肉刺激、疼痛标记、动作技术和进步决策。
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
    pro-level-physique-roadmap.md
    plan-optimization.md
    training-programming.md
    exercise-library.md
    phase-templates.md
    session-execution-and-volume.md
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

如果用户有疼痛、受伤、胸痛、晕厥、呼吸异常、严重疲劳、进食障碍风险、极端减重目标、妊娠或产后情况、未控制的疾病，应该优先寻求合格专业人士帮助。

这个 skill 不提供 anabolic steroids、growth hormone、diuretics、SARMs、甲状腺药物、兴奋剂或其他药物的周期、剂量、来源、protocol 或 peak week 危险操作。

## 测试

运行：

```bash
python -m unittest tests.test_skill_completeness tests.test_scripts_smoke
python scripts/validate_skill.py skills/i-want-to-be-an-ifbb-pro
```

## GitHub

仓库地址：

https://github.com/JackieL233/i-want-to-be-an-ifbb-pro
