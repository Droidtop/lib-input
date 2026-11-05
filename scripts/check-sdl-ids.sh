#!/usr/bin/env bash
# SdlControllerIds.kt is generated from SDL's controller_list.h at the commit its header names
# (third_party/sdl_controller_list/README.md). This regenerates it and fails if the file differs, so a
# hand edit, or a regeneration from another commit without its header saying so, cannot pass.
set -euo pipefail
cd "$(dirname "$0")/.."
file=pads/src/main/kotlin/dev/droidtop/lib/input/SdlControllerIds.kt
commit="$(grep -o 'commit [0-9a-f]\{40\}' "$file" | head -1 | cut -d' ' -f2)"
[ -n "$commit" ] || { echo "$file names no SDL commit" >&2; exit 1; }
list="$(mktemp)"; out="$(mktemp)"
curl -fsS --retry 3 -o "$list" "https://raw.githubusercontent.com/libsdl-org/SDL/$commit/src/joystick/controller_list.h"
python3 -I third_party/sdl_controller_list/gen_sdl_controller_families.py "$list" "$commit" > "$out"
if cmp -s "$out" "$file"; then echo "$file is SDL $commit's, regenerated"; else diff "$out" "$file" >&2 || true; echo "$file is not what the generator makes from SDL $commit" >&2; exit 1; fi
