// Core
include(":imgui:imgui-build")
include(":imgui:imgui-base")
include(":imgui:imgui-core")
include(":imgui:imgui-desktop")
include(":imgui:imgui-teavm")
include(":imgui:imgui-android")

include(":imgui-ext:ext-build")
include(":imgui-ext:ext-core")
include(":imgui-ext:ext-desktop")
include(":imgui-ext:ext-teavm")

// Implementation
include(":gdx:gdx-impl")
//include(":gdx:lwjgl3-impl")

// Extension ImLayout
include(":extensions:imlayout:imlayout-build")
include(":extensions:imlayout:imlayout-base")
include(":extensions:imlayout:imlayout-core")
include(":extensions:imlayout:imlayout-desktop")
include(":extensions:imlayout:imlayout-teavm")

// Extension ImGuiColorTextEdit
include(":extensions:ImGuiColorTextEdit:textedit-build")
include(":extensions:ImGuiColorTextEdit:textedit-base")
include(":extensions:ImGuiColorTextEdit:textedit-core")
include(":extensions:ImGuiColorTextEdit:textedit-desktop")
include(":extensions:ImGuiColorTextEdit:textedit-teavm")

// Extension imgui-node-editor
//include(":extensions:node-editor:editor-build")
//include(":extensions:node-editor:editor-base")
//include(":extensions:node-editor:editor-core")
//include(":extensions:node-editor:editor-desktop")
//include(":extensions:node-editor:editor-teavm")

// Examples
include(":examples:basic:base")
include(":examples:basic:core")
include(":examples:basic:desktop")
include(":examples:basic:teavm")
include(":examples:basic:android")

include(":examples:imlayout:core")
include(":examples:imlayout:desktop")
include(":examples:imlayout:teavm")
//
//include(":examples:node-editor:core")
//include(":examples:node-editor:desktop")
//include(":examples:node-editor:teavm")

//include ":examples:gdx-tests:core"
//include ":examples:gdx-tests:desktop"
//includeBuild('D:\\Dev\\Projects\\java\\libgdx') {
//}

//includeBuild("E:\\Dev\\Projects\\java\\gdx-teavm") {
//    dependencySubstitution {
//        substitute(module("com.github.xpenatan.gdx-teavm:backend-teavm")).using(project(":backends:backend-teavm"))
//    }
//}
//
//includeBuild("E:\\Dev\\Projects\\java\\jParser") {
//    dependencySubstitution {
//        substitute(module("com.github.xpenatan.jParser:base")).using(project(":jParser:base"))
//        substitute(module("com.github.xpenatan.jParser:builder")).using(project(":jParser:builder"))
//        substitute(module("com.github.xpenatan.jParser:core")).using(project(":jParser:core"))
//        substitute(module("com.github.xpenatan.jParser:cpp")).using(project(":jParser:cpp"))
//        substitute(module("com.github.xpenatan.jParser:idl")).using(project(":jParser:idl"))
//        substitute(module("com.github.xpenatan.jParser:teavm")).using(project(":jParser:teavm"))
//        substitute(module("com.github.xpenatan.jParser:loader-core")).using(project(":jParser:loader:loader-core"))
//        substitute(module("com.github.xpenatan.jParser:loader-teavm")).using(project(":jParser:loader:loader-teavm"))
//    }
//}