# gdx-imgui

![Build](https://github.com/xpenatan/gdx-imgui/actions/workflows/release.yml/badge.svg)
![Build](https://github.com/xpenatan/gdx-imgui/actions/workflows/snapshot.yml/badge.svg)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/releases/com.github.xpenatan.gdx-imgui/imgui-core?nexusVersion=2&server=https%3A%2F%2Foss.sonatype.org&label=release)](https://repo.maven.apache.org/maven2/com/github/xpenatan/gdx-imgui/)
[![Static Badge](https://img.shields.io/badge/snapshot---SNAPSHOT-red)](https://oss.sonatype.org/content/repositories/snapshots/com/github/xpenatan/gdx-imgui/)



gdx-imgui is a java binding for C++ [dear-imgui](https://github.com/ocornut/imgui). <br>
It uses webidl file to generate java methods with the help of [jParser](https://github.com/xpenatan/jParser). <br>
It's meant to be small and 1-1 to C++. ImGui::Begin() is ImGui.Begin() and so on.

<p align="center"><img src="https://i.imgur.com/rXk4Aq0.gif"/></p>

## Supported extensions:
[imgui-node-editor](https://github.com/thedmd/imgui-node-editor) <br>
[ImGuiColorTextEdit](https://github.com/santaclose/ImGuiColorTextEdit/) <br>
[ImLayout](https://github.com/xpenatan/gdx-imgui/tree/master/extensions/imlayout) <br>

## Examples
* [basic](https://xpenatan.github.io/gdx-imgui/basic/)

### ImGui current state:

| Emscripten | Windows | Linux | Mac | Android | iOS |
|:----------:|:-------:|:-----:|:---:|:-------:|:---:|
|     ✅      | ✅ | ✅ |  ✅  | ⚠️ | ❌ |

* ✅: Have a working build.
* ⚠️: Have a working build, but it's not ready yet.
* ❌: Build not ready.

Note: 
```
* Only snapshot builds are currently available. 
* It's best to try the examples first to see how it works before adding to your project.
* There are 2 ImGui builds. The first contains ImGui only. The second (Ext) contains ImGui with extensions. 
```

## How to run examples
There are two ways to run examples. 
* Build the source and run:
```./gradlew :examples:basic:desktop:basic-run-desktop```
* Change LibExt.exampleUseRepoLibs to true in Dependencies.kt and that will make all examples use snapshot from repository

## Setup

    gdxVersion = "1.12.1"
    gdxImguiVersion = "-SNAPSHOT"

```groovy
// Add repository to Root gradle
repositories {
    mavenLocal()
    mavenCentral()
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    maven { url "https://oss.sonatype.org/content/repositories/releases/" }
}
```

### Core module
```groovy
dependencies {
    implementation("com.github.xpenatan.gdx-imgui:gdx-impl:$project.gdxImguiVersion")
    implementation("com.github.xpenatan.gdx-imgui:imgui-core:$project.gdxImguiVersion")

    // Or the extension build
    implementation "com.github.xpenatan.gdx-imgui:imgui-ext-core:$project.gdxImguiVersion"
}
```

### Desktop module
```groovy
dependencies {
    implementation("com.github.xpenatan.gdx-imgui:imgui-desktop:$project.gdxImguiVersion")

    // Or the extension build
    implementation "com.github.xpenatan.gdx-imgui:imgui-ext-desktop:$project.gdxImguiVersion"
}
```

### TeaVM module
```groovy
dependencies {
    implementation("com.github.xpenatan.gdx-imgui:imgui-teavm:$project.gdxImguiVersion")

    // Or the extension build
    implementation "com.github.xpenatan.gdx-imgui:imgui-ext-teavm:$project.gdxImguiVersion"
}
```

## Build source

* Requirements: Java 11, mingw64 and emscripten
* Windows only for now [Visual Studio 2022](https://visualstudio.microsoft.com/downloads/?cid=learn-onpage-download-install-visual-studio-page-cta).

### Windows build instructions
```powershell
##
## Ensure that vcvarsall.bat is available for the build commands:
##

# Specify the path to vcvarsall.bat, this is an example yours may be different
$vcvarsPath = "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build"

# Add the path to vcvarsall.bat to the PATH temporary for the current session
$env:PATH += ";$vcvarsPath"

# (Alternative 1) add the path to vcvarsall.bat to the PATH permanently for the current user
# [Environment]::SetEnvironmentVariable("PATH", $env:PATH + ";$vcvarsPath", [EnvironmentVariableTarget]::User)
# (Alternative 2) add the path to vcvarsall.bat to the PATH permanently for all users (requires admin)
# [Environment]::SetEnvironmentVariable("PATH", $env:PATH + ";$vcvarsPath", [EnvironmentVariableTarget]::Machine)

##
## Build Windows natives for the project libraries
##
.\gradlew.bat download_all_sources
.\gradlew.bat :imgui:imgui-build:build_project_windows64
.\gradlew.bat :extensions:imlayout:imlayout-build:build_project_windows64
.\gradlew.bat :extensions:ImGuiColorTextEdit:textedit-build:build_project_windows64
.\gradlew.bat :extensions:imgui-node-editor:nodeeditor-build:build_project_windows64
.\gradlew.bat :imgui-ext:ext-build:build_project_windows64

##
## Verify that the natives are built
##
ls .\imgui\imgui-build\build\c++\libs\windows\vc
# imgui64_.lib
# imgui64.dll
# imgui64.exp
# imgui64.lib
# 👍

## Run an example project
.\gradlew.bat :examples:basic:desktop:basic-run-desktop
```

### Old build instructions
```
##### ImGui
./gradlew :imgui:imgui-build:download_source
./gradlew :imgui:imgui-build:build_project
```
```
##### ImGui with extensions. Need to build ImGui first
./gradlew :extensions:imlayout:imlayout-build:build_project
./gradlew :extensions:ImGuiColorTextEdit:textedit-build:download_source
./gradlew :extensions:ImGuiColorTextEdit:textedit-build:build_project
./gradlew :extensions:imgui-node-editor:nodeeditor-build:download_source
./gradlew :extensions:imgui-node-editor:nodeeditor-build:build_project
./gradlew :imgui:imgui-ext-build:build_project
```