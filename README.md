# gdx-imgui
![Deploy Libs](https://github.com/xpenatan/gdx-imgui/workflows/Deploy%20Libs/badge.svg)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/r/com.github.xpenatan.gdx-imgui/core?nexusVersion=2&server=https%3A%2F%2Foss.sonatype.org&label=release)](https://repo.maven.apache.org/maven2/com/github/xpenatan/gdx-imgui/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.github.xpenatan.gdx-imgui/core?server=https%3A%2F%2Foss.sonatype.org&label=snapshot)](https://oss.sonatype.org/content/repositories/snapshots/com/github/xpenatan/gdx-imgui/)


gdx-imgui is a java binding for C++ [dear-imgui](https://github.com/ocornut/imgui). <br>
It's meant to be small and close 1-1 to C++. 

<p align="center"><img src="https://i.imgur.com/rXk4Aq0.gif"/></p>

## Build

To build from source you need mingw-w64 and maven ant. Emscripten if you want to build javascript file.

When project is ready, run ```./gradlew buildImGuiNatives``` and test the examples.



### Gradle

Only SNAPSHOTS are currently available. Release will be ready when ImGui docking api goes to master.

    gdxVersion = "1.11.0"
    gdxImguiVersion = "1.0.0-SNAPSHOT"
    imlayoutVersion = "1.0.0-SNAPSHOT"


# Repo libs Deprecated. Will update it later.

```groovy

// Add repository to Root gradle
repositories {
    mavenLocal()
    mavenCentral()
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    maven { url "https://oss.sonatype.org/content/repositories/releases/" }
}

dependencies {
    implementation "com.badlogicgames.gdx:gdx-platform:$project.gdxVersion:natives-desktop"
    implementation "com.badlogicgames.gdx:gdx-backend-lwjgl3:$project.gdxVersion"

    // Required
    implementation "com.github.xpenatan.gdx-imgui:core:$project.gdxImguiVersion"
    implementation "com.github.xpenatan.gdx-imgui:gdx:$project.gdxImguiVersion"

    // Required Platform Natives
    implementation "com.github.xpenatan.gdx-imgui:imgui-desktop:$project.gdxImguiVersion"
    //implementation "com.github.xpenatan.gdx-imgui:imgui-android:$project.gdxImguiVersion"
    //implementation "com.github.xpenatan.gdx-imgui:imgui-ios:$project.gdxImguiVersion"
    implementation "com.github.xpenatan.gdx-imgui:imgui-teavm:$project.gdxImguiVersion"

    // Optional
//    implementation "com.github.xpenatan.gdx-imgui:lwjgl3:$project.gdxImguiVersion"

    // Optional - ImLayout extension
    implementation "com.github.xpenatan.gdx-imgui:imlayout-core:$project.imlayoutVersion"
    implementation "com.github.xpenatan.gdx-imgui:imlayout-desktop:$project.imlayoutVersion"

    // Optional
    implementation "com.github.xpenatan.gdx-imgui:gdx-frame-viewport:$project.gdxImguiVersion"
}
```