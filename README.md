# lib-input

Controllers for droidtop, Enginehost and Pythia: one of the shared library families of Droidtop/tracker#867.
The libraries belong to no program; each program has its own scaffold and pins a tagged release of each
library it uses (#867 D2). The owner's decision for this one (D4): "one shared controller model, with each
app's own vocabulary on top".

| Module | Holds |
|---|---|
| [`:pads`](pads) | `PadFamily` (what a pad's face buttons print: Xbox, PlayStation or Nintendo) and `PadFamilies.classify`, from SDL's vendor and product ids (`SdlControllerIds`, generated) and the rules SDL applies around them. Plain JVM |

`:pads` is droidtop's `ControllerClassifier` and Enginehost's `PadFamilies.classify` with their generated
`SdlControllerIds`, which were the same code twice. Each program keeps its own names (`GlyphFamily`,
`PadFamily`) over it. Next, as #867 step 10 has it: GameControllerDB, the face-layout resolver and a neutral
binding model, with each program's vocabulary (droidtop's Steam-style layouts, Enginehost's per-engine actions,
Pythia's pad actions) on top.

Licence: GPL-3.0 (see `LICENSE`); SDL's ids are zlib (`third_party/sdl_controller_list`).

## Build and test

```bash
./gradlew test
scripts/check-sdl-ids.sh    # SdlControllerIds.kt is what the generator makes from the SDL commit it names
```

CI (`.github/workflows/ci.yml`) runs both. Each program that uses this library gets a job there that builds and
tests it against the commit (#867 D2); the commit that moves a program onto lib-input adds it. Work lands on
`main` from `ci/*` branches once CI is green.

## Versions

The library's version is `lib.version` in `gradle.properties`. A release is a tag `vX.Y.Z`: CI creates it on a
green `main` whose version is not yet tagged, so to release, raise the version (and the CHANGELOG) in the change
that needs it. Each program pins a tag.
