# Changelog

All notable changes to lib-input. Versions follow [Semantic Versioning](https://semver.org/)
(0.x releases may change anything).

## [0.1.0]

### Added
- `:pads`: `PadFamily` and `PadFamilies.classify`, the face-button family of a pad from its vendor and product id
  (SDL's `SdlControllerIds`, generated from SDL commit 4c2d9014) with its name as the fallback. droidtop's
  `ControllerClassifier` and Enginehost's `PadFamilies` were this code twice, line for line, each with its own
  copy of the generator; the generator and its zlib notice moved here from Enginehost's
  `third_party/sdl_controller_list`, and their tests with them. `PadFamily.fromId` reads Enginehost's stored ids
  and droidtop's enum names alike. Droidtop/tracker#867 D4.
