package com.github.xpenatan.imgui.core.generate;

import com.github.xpenatan.jparser.core.JParser;
import com.github.xpenatan.jparser.core.idl.IDLFile;
import com.github.xpenatan.jparser.core.idl.IDLParser;
import com.github.xpenatan.jparser.cpp.CPPBuildHelper;
import java.io.File;

public class BuildCore {

    public static void main(String[] args) throws Exception {
        String libName = "imgui-core";

        String path = "..\\core-build\\src\\main\\resources\\imgui.idl";
        IDLFile idlFile = IDLParser.parseFile(path);

        String cppPath = new File("../core/").getCanonicalPath();
        String teaVMPath = new File("../core-teavm/").getCanonicalPath();

        String jniDir = cppPath + "/build/c++/";
        String sourceDir = "../core-base/src/main/java/";
        String cppGenDir = cppPath + "/src/main/java/";
        String teaVMGenDir = teaVMPath + "/src/main/java/";
        String imguiCppBase = new File("../../cpp/build/c++").getCanonicalPath();

        //Generate CPP
        String classPaths = ImGuiCppParser.getClassPath("core", "gdx-1", "gdx-jnigen-loader", "jParser");
        ImGuiCppParser cppParser = new ImGuiCppParser(idlFile, classPaths, jniDir);
        JParser.generate(cppParser, sourceDir, cppGenDir);
//        CPPBuildHelper.DEBUG_BUILD = true;
        CPPBuildHelper.build(libName, jniDir, null, imguiCppBase, "imgui-cpp64", true);

        //Generate Javascript
        ImGuiTeaVMParser teaVMParser = new ImGuiTeaVMParser(idlFile);
        JParser.generate(teaVMParser, sourceDir, teaVMGenDir);
    }
}