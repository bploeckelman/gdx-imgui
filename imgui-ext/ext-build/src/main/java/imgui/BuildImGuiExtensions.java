package imgui;

import com.github.xpenatan.jparser.builder.BuildMultiTarget;
import com.github.xpenatan.jparser.builder.targets.EmscriptenTarget;
import com.github.xpenatan.jparser.builder.targets.LinuxTarget;
import com.github.xpenatan.jparser.builder.targets.MacTarget;
import com.github.xpenatan.jparser.builder.targets.WindowsTarget;
import com.github.xpenatan.jparser.builder.tool.BuildToolListener;
import com.github.xpenatan.jparser.builder.tool.BuildToolOptions;
import com.github.xpenatan.jparser.builder.tool.BuilderTool;
import com.github.xpenatan.jparser.idl.IDLReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BuildImGuiExtensions {

    public static void main(String[] args) throws Exception {

        String libName = "imgui";
        String basePackage = "imgui";
        String modulePrefix = "imgui";
        String sourceDir =  "";  // Ext dont need source
        BuildToolOptions op = new BuildToolOptions(libName, basePackage, modulePrefix, sourceDir, args);
        String imguiPath = new File("./../../imgui").getCanonicalPath().replace("\\", "/");
        String extensionsPath = new File("./../../extensions").getCanonicalPath().replace("\\", "/");

        op.libPath = imguiPath;

        // Don't generate anything because it already generated by extensions and imgui build modules.
        op.generateCPP = false;
        op.generateTeaVM = false;

        BuilderTool.build(op, new BuildToolListener() {
            @Override
            public void onAddTarget(BuildToolOptions op, IDLReader idlReader, ArrayList<BuildMultiTarget> targets) {
                if(op.teavm) {
                    targets.add(getTeaVMTarget(op, idlReader, extensionsPath));
                }
                if(op.windows64) {
                    targets.add(getWindowTarget(op, extensionsPath));
                }
                if(op.linux64) {
                    targets.add(getLinuxTarget(op, extensionsPath));
                }
                if(op.mac64) {
                    targets.add(getMacTarget(op, false, extensionsPath));
                }
                if(op.macArm) {
                    targets.add(getMacTarget(op, true, extensionsPath));
                }
//                if(op.android) {
//                    targets.add(getAndroidTarget(op, extensionsPath));
//                }
//                if(op.iOS) {
//                    targets.add(getIOSTarget(op));
//                }
            }
        });
    }

    private static BuildMultiTarget getWindowTarget(BuildToolOptions op, String extensionsPath) {
        String libBuildCPPPath = op.getModuleBuildCPPPath();

        BuildMultiTarget multiTarget = new BuildMultiTarget();

        WindowsTarget glueTarget = new WindowsTarget();
        glueTarget.libDirSuffix += "ext/";
        glueTarget.addJNIHeaders();
        glueTarget.headerDirs.add("-I" + libBuildCPPPath + "/src/imgui/");
        glueTarget.headerDirs.add("-I" + libBuildCPPPath + "/src/jniglue");
        glueTarget.linkerFlags.add(libBuildCPPPath + "/libs/windows/imgui64.a");
        glueTarget.cppInclude.add(libBuildCPPPath + "/src/jniglue/JNIGlue.cpp");

        {
            // ImLayout extension
            String imlayoutCPPPath = extensionsPath + "/imlayout/imlayout-build/build/c++";
            glueTarget.headerDirs.add("-I" + imlayoutCPPPath + "/src/imlayout/");
            glueTarget.headerDirs.add("-I" + imlayoutCPPPath + "/src/jniglue");
            glueTarget.linkerFlags.add(imlayoutCPPPath + "/libs/windows/imlayout64.a");
            glueTarget.headerDirs.add("-include" + imlayoutCPPPath + "/src/jniglue/JNIGlue.h");
        }
        {
            // ImGuiColorTextEdit extension
            String textEditCPPPath = extensionsPath + "/ImGuiColorTextEdit/textedit-build/build/c++";
            glueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/textedit/");
            glueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/textedit/vendor/regex/include");
            glueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/jniglue");
            glueTarget.linkerFlags.add(textEditCPPPath + "/libs/windows/textedit64.a");
            glueTarget.headerDirs.add("-include" + textEditCPPPath + "/src/jniglue/JNIGlue.h");
        }
        {
            // imgui-node-editor extension
            String nodeeditorCPPPath = extensionsPath + "/imgui-node-editor/nodeeditor-build/build/c++";
            glueTarget.headerDirs.add("-I" + nodeeditorCPPPath + "/src/nodeeditor/");
            glueTarget.headerDirs.add("-I" + nodeeditorCPPPath + "/src/jniglue");
            glueTarget.linkerFlags.add(nodeeditorCPPPath + "/libs/windows/nodeeditor64.a");
            glueTarget.headerDirs.add("-include" + nodeeditorCPPPath + "/src/jniglue/JNIGlue.h");
        }

        multiTarget.add(glueTarget);
        return multiTarget;
    }

    private static BuildMultiTarget getLinuxTarget(BuildToolOptions op, String extensionsPath) {
        String libBuildCPPPath = op.getModuleBuildCPPPath();

        BuildMultiTarget multiTarget = new BuildMultiTarget();

        LinuxTarget glueTarget = new LinuxTarget();
        glueTarget.libDirSuffix += "ext/";
        glueTarget.addJNIHeaders();
        glueTarget.headerDirs.add("-I" + libBuildCPPPath + "/src/imgui/");
        glueTarget.headerDirs.add("-I" + libBuildCPPPath + "/src/jniglue");
        glueTarget.linkerFlags.add(libBuildCPPPath + "/libs/linux/libimgui64.a");

        glueTarget.cppInclude.add(libBuildCPPPath + "/src/jniglue/JNIGlue.cpp");

        {
            // ImLayout extension
            String imlayoutCPPPath = extensionsPath + "/imlayout/imlayout-build/build/c++";
            glueTarget.headerDirs.add("-I" + imlayoutCPPPath + "/src/imlayout/");
            glueTarget.headerDirs.add("-I" + imlayoutCPPPath + "/src/jniglue");
            glueTarget.linkerFlags.add(imlayoutCPPPath + "/libs/linux/libimlayout64.a");
            glueTarget.headerDirs.add("-include" + imlayoutCPPPath + "/src/jniglue/JNIGlue.h");
        }
        {
            // ImGuiColorTextEdit extension
            String textEditCPPPath = extensionsPath + "/ImGuiColorTextEdit/textedit-build/build/c++";
            glueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/textedit/");
            glueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/textedit/vendor/regex/include");
            glueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/jniglue");
            glueTarget.linkerFlags.add(textEditCPPPath + "/libs/linux/libtextedit64.a");
            glueTarget.headerDirs.add("-include" + textEditCPPPath + "/src/jniglue/JNIGlue.h");
        }
        {
            // imgui-node-editor extension
            String nodeeditorCPPPath = extensionsPath + "/imgui-node-editor/nodeeditor-build/build/c++";
            glueTarget.headerDirs.add("-I" + nodeeditorCPPPath + "/src/nodeeditor/");
            glueTarget.headerDirs.add("-I" + nodeeditorCPPPath + "/src/jniglue");
            glueTarget.linkerFlags.add(nodeeditorCPPPath + "/libs/linux/libnodeeditor64.a");
            glueTarget.headerDirs.add("-include" + nodeeditorCPPPath + "/src/jniglue/JNIGlue.h");
        }

        multiTarget.add(glueTarget);
        return multiTarget;
    }

    private static BuildMultiTarget getMacTarget(BuildToolOptions op, boolean isArm, String extensionsPath) {
        String libBuildCPPPath = op.getModuleBuildCPPPath();

        BuildMultiTarget multiTarget = new BuildMultiTarget();

        MacTarget macGlueTarget = new MacTarget(isArm);
        macGlueTarget.libDirSuffix += "ext/";
        macGlueTarget.addJNIHeaders();
        macGlueTarget.headerDirs.add("-I" + libBuildCPPPath + "/src/imgui/");
        macGlueTarget.headerDirs.add("-I" + libBuildCPPPath + "/src/jniglue");
        if(isArm) {
            macGlueTarget.linkerFlags.add(libBuildCPPPath + "/libs/mac/arm/libimgui64.a");
        }
        else {
            macGlueTarget.linkerFlags.add(libBuildCPPPath + "/libs/mac/libimgui64.a");
        }

        macGlueTarget.cppInclude.add(libBuildCPPPath + "/src/jniglue/JNIGlue.cpp");

        {
            // ImLayout extension
            String imlayoutCPPPath = extensionsPath + "/imlayout/imlayout-build/build/c++";
            macGlueTarget.headerDirs.add("-I" + imlayoutCPPPath + "/src/imlayout/");
            macGlueTarget.headerDirs.add("-I" + imlayoutCPPPath + "/src/jniglue");
            if(isArm) {
                macGlueTarget.linkerFlags.add(imlayoutCPPPath + "/libs/mac/arm/libimlayout64.a");
            }
            else {
                macGlueTarget.linkerFlags.add(imlayoutCPPPath + "/libs/mac/libimlayout64.a");
            }
            macGlueTarget.headerDirs.add("-include" + imlayoutCPPPath + "/src/jniglue/JNIGlue.h");
        }
        {
            // ImGuiColorTextEdit extension
            String textEditCPPPath = extensionsPath + "/ImGuiColorTextEdit/textedit-build/build/c++";
            macGlueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/textedit/");
            macGlueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/textedit/vendor/regex/include");
            macGlueTarget.headerDirs.add("-I" + textEditCPPPath + "/src/jniglue");
            if(isArm) {
                macGlueTarget.linkerFlags.add(textEditCPPPath + "/libs/mac/arm/libtextedit64.a");
            }
            else {
                macGlueTarget.linkerFlags.add(textEditCPPPath + "/libs/mac/libtextedit64.a");
            }
            macGlueTarget.headerDirs.add("-include" + textEditCPPPath + "/src/jniglue/JNIGlue.h");
        }
        {
            // imgui-node-editor extension
            String nodeeditorCPPPath = extensionsPath + "/imgui-node-editor/nodeeditor-build/build/c++";
            macGlueTarget.headerDirs.add("-I" + nodeeditorCPPPath + "/src/nodeeditor/");
            macGlueTarget.headerDirs.add("-I" + nodeeditorCPPPath + "/src/jniglue");
            if(isArm) {
                macGlueTarget.linkerFlags.add(nodeeditorCPPPath + "/libs/mac/arm/libnodeeditor64.a");
            }
            else {
                macGlueTarget.linkerFlags.add(nodeeditorCPPPath + "/libs/mac/libnodeeditor64.a");
            }
            macGlueTarget.headerDirs.add("-include" + nodeeditorCPPPath + "/src/jniglue/JNIGlue.h");
        }

        multiTarget.add(macGlueTarget);

        return multiTarget;
    }

    private static BuildMultiTarget getTeaVMTarget(BuildToolOptions op, IDLReader idlReaderCombined, String extensionsPath) {
        String libBuildCPPPath = op.getModuleBuildCPPPath();

        BuildMultiTarget multiTarget = new BuildMultiTarget();

        // Compile glue code and link to make js file
        EmscriptenTarget linkTarget = new EmscriptenTarget(idlReaderCombined);
        linkTarget.libDirSuffix += "ext/";
        linkTarget.headerDirs.add("-I" + libBuildCPPPath + "/src/imgui");
        linkTarget.headerDirs.add("-include" + libBuildCPPPath + "/src/imgui/ImGuiCustom.h");
        linkTarget.linkerFlags.add(libBuildCPPPath + "/libs/emscripten/imgui.a");

        {
            // ImLayout extension
            String imlayoutCppPath = extensionsPath + "/imlayout/imlayout-build/build/c++";
            String imlayoutIdlPath = extensionsPath + "/imlayout/imlayout-build/src/main/cpp/imlayout.idl";
            IDLReader.addIDL(idlReaderCombined, imlayoutIdlPath);
            linkTarget.headerDirs.add("-I" + imlayoutCppPath + "/src/imlayout");
            linkTarget.headerDirs.add("-include" + imlayoutCppPath + "/src/imlayout/ImLayoutCustom.h");
            linkTarget.linkerFlags.add(imlayoutCppPath + "/libs/emscripten/imlayout.a");
        }
        {
            // ImGuiColorTextEdit extension
            String texteditCppPath = extensionsPath + "/ImGuiColorTextEdit/textedit-build/build/c++";
            String texteditIdlPath = extensionsPath + "/ImGuiColorTextEdit/textedit-build/src/main/cpp/ColorTextEdit.idl";
            IDLReader.addIDL(idlReaderCombined, texteditIdlPath);
            linkTarget.headerDirs.add("-I" + texteditCppPath + "/src/textedit");
            linkTarget.headerDirs.add("-I" + texteditCppPath + "/src/textedit/vendor/regex/include");
            linkTarget.headerDirs.add("-include" + texteditCppPath + "/src/textedit/TextEditCustom.h");
            linkTarget.linkerFlags.add(texteditCppPath + "/libs/emscripten/textedit.a");
        }
        {
            // imgui-node-editor extension
            String nodeeditorCppPath = extensionsPath + "/imgui-node-editor/nodeeditor-build/build/c++";
            String nodeeditorIdlPath = extensionsPath + "/imgui-node-editor/nodeeditor-build/src/main/cpp/nodeeditor.idl";
            IDLReader.addIDL(idlReaderCombined, nodeeditorIdlPath);
            linkTarget.headerDirs.add("-I" + nodeeditorCppPath + "/src/nodeeditor");
            linkTarget.headerDirs.add("-include" + nodeeditorCppPath + "/src/nodeeditor/NodeEditorCustom.h");
            linkTarget.linkerFlags.add(nodeeditorCppPath + "/libs/emscripten/nodeeditor.a");
        }
        multiTarget.add(linkTarget);

        return multiTarget;
    }
}