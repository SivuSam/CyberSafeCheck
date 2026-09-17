# CyberSafeCheck - Milestone 1 (Chapters 9-10: Fragments & RecyclerView)

## What this is
A complete, buildable Android Studio project implementing the Milestone 1
brief for CSIQ 6823: a scrollable checklist of digital-habit risk questions,
each tappable into a read-only detail screen.

## How to open it
1. Android Studio -> File -> Open -> select the `CyberSafeCheck` folder.
2. Let Gradle sync. This project ships with its own Gradle wrapper
   (matching your Android Studio "Quail" / AGP 9.3.2 setup), so it should
   sync without prompting you to fix versions.
3. Run on an emulator or device.

## Toolchain note: AGP 9.3.2's built-in Kotlin
Your Android Studio version's default template no longer applies a separate
Kotlin Gradle plugin - Kotlin compilation is built directly into
`com.android.application` (AGP 9.3+). That's why this project's
`app/build.gradle.kts` only declares `android.application` and
`kotlin.compose` as plugins, matching your own project's template exactly.
Milestone 1 has no reason to need Room/KSP yet, so it's been left out
entirely here (it caused a real error the first time round - see the
Milestone 2 README for details, since that's where Room actually gets
added back in).

## What's implemented against the brief
- `RiskCategory.kt` - enum: PASSWORDS, SOCIAL_MEDIA, SCAMS, CYBERBULLYING
- `RiskItem.kt` - plain data class (id, category, question, explanation, isFlagged)
- `RiskLab.kt` - singleton seeded with 10 realistic habit questions
- `activity_main.xml` - empty FrameLayout; `MainActivity` hosts
  `ChecklistFragment` on first launch only (see the guard note below)
- `fragment_checklist.xml` (RecyclerView) + `list_item_risk.xml`
  (question text + Switch)
- `ChecklistFragment` + `RiskListAdapter` (RiskHolder/RiskAdapter)
- Tapping the question TEXT opens `RiskDetailFragment` (manual
  `FragmentManager.commit` - Navigation component arrives in Milestone 3);
  tapping the SWITCH just records yes/no in `RiskLab`
- `RiskDetailFragment` - read-only, shows category/question/explanation,
  using a `newInstance(UUID)` factory (the standard BNR idiom)

## One deliberate adaptation from the literal brief
Your course's actual project template (seen in your CriminalIntent2026
exports) hosts fragments through a Compose `Scaffold` +
`AndroidViewBinding(ActivityMainBinding::inflate)` shell, not a plain
`setContentView(R.layout.activity_main)` Activity. `activity_main.xml` is
still exactly the "empty FrameLayout" the brief asks for - it's just
inflated via ViewBinding inside Compose instead of directly.

The "guard against duplicating on rotation" requirement is implemented as:

```kotlin
if (supportFragmentManager.findFragmentById(root.id) == null) {
    supportFragmentManager.commit { add(root.id, ChecklistFragment()) }
}
```

This is the Compose-interop equivalent of the book's
`if (savedInstanceState == null)` check - `AndroidViewBinding`'s content
lambda can re-run on recomposition, so checking "is a fragment already
attached to this container" (rather than checking savedInstanceState
directly) is the correct guard for this hosting style. Functionally it does
exactly the same job: the fragment survives rotation via the
FragmentManager's own state restoration and is never added twice.

## Demo checklist (from the brief)
- [x] App opens into a scrollable checklist of seeded questions
- [x] Toggling a switch records the answer in RiskLab
- [x] Tapping question text opens the correct detail explanation
- [ ] `git tag milestone-1` <- do this yourself once you've verified it runs

## Suggested git workflow
```bash
git init
git add .
git commit -m "Milestone 1: checklist + detail via RiskLab"
git tag milestone-1
```
