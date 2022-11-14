package com.github.xpenatan.imgui.core.generate;

import com.github.xpenatan.jparser.core.JParser;
import com.github.xpenatan.jparser.core.idl.IDLFile;
import com.github.xpenatan.jparser.core.idl.IDLParser;
import com.github.xpenatan.jparser.cpp.CPPBuildHelper;
import java.io.File;

public class BuildCore {

    public static void main(String[] args) throws Exception {
        String path = "..\\imgui-core-build\\src\\main\\resources\\imgui.idl";
        IDLFile idlFile = IDLParser.parseFile(path);

        String classpathStr = System.getProperty("java.class.path") + File.pathSeparator;

        String cppPath = new File("../imgui-core/").getCanonicalPath();
        String teaVMPath = new File("../imgui-core-teavm/").getCanonicalPath();

        String jniDir = cppPath + "/jni";
        String sourceDir = "../imgui-core-base/src/main/java/";
        String cppGenDir = cppPath + "/src/main/java/";
        String teaVMGenDir = teaVMPath + "/src/main/java/";

        ImGuiCppParser cppParser = new ImGuiCppParser(idlFile, classpathStr, jniDir);
        ImGuiTeaVMParser teaVMParser = new ImGuiTeaVMParser(idlFile);

        JParser.generate(cppParser, sourceDir, cppGenDir);
        JParser.generate(teaVMParser, sourceDir, teaVMGenDir);

        String imguiCppBase = new File("../../../imgui-cpp").getCanonicalPath();

//        CPPBuildHelper.build("imgui-core", cppPath, imguiCppBase, "imgui-cpp64");
        CPPBuildHelper.build("imgui-core", cppPath);
    }
}