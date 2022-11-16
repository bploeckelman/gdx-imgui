package com.github.xpenatan.imgui.core.jnicode;

import com.github.xpenatan.imgui.core.ImDrawData;
import com.github.xpenatan.imgui.core.ImGuiIO;
import com.github.xpenatan.imgui.core.ImGuiInputTextData;
import com.github.xpenatan.imgui.core.ImGuiStyle;
import com.github.xpenatan.imgui.core.ImGuiViewport;
import com.github.xpenatan.imgui.core.ImVec2;
import com.github.xpenatan.imgui.core.TexDataRGBA32;
import java.nio.Buffer;

public class ImGuiNative {
    /*[-cpp;-NATIVE]
        #include "imgui_helper.h"

        #include <imgui.h>
        #include <cstring>
        #if defined(_MSC_VER) && _MSC_VER <= 1500 // MSVC 2008 or earlier
        #include <stddef.h>     // intptr_t
        #else
        #include <stdint.h>     // intptr_t
        #endif

        static jobject jViewport;

        // ImGuiIO

        jfieldID WantCaptureMouseID;
        jfieldID WantCaptureKeyboardID;
        jfieldID WantTextInputID;
        jfieldID WantSetMousePosID;
        jfieldID WantSaveIniSettingsID;
        jfieldID NavActiveID;
        jfieldID NavVisibleID;
        jfieldID FramerateID;
        jfieldID MetricsRenderVerticesID;
        jfieldID MetricsRenderIndicesID;
        jfieldID MetricsRenderWindowsID;
        jfieldID MetricsActiveWindowsID;
        jfieldID MetricsActiveAllocationsID;
        jfieldID MouseDeltaXID;
        jfieldID MouseDeltaYID;

        // ImGuiStyle

        jfieldID FramePaddingXID;
        jfieldID FramePaddingYID;
        jfieldID ItemInnerSpacingXID;
        jfieldID ItemInnerSpacingYID;

        jfieldID imTextInputDataSizeID;
        jfieldID imTextInputDataIsDirtyID;

        static int DRAWLIST_TYPE_DEFAULT = 0;
        static int DRAWLIST_TYPE_BACKGROUND = 1;
        static int DRAWLIST_TYPE_FOREGROUND = 2;
    */

    /*[-cpp;-NATIVE]
        ImGuiHelper::Init(env);
        jclass jImGuiIOClass = env->FindClass("com/github/xpenatan/imgui/core/ImGuiIO");
        jclass jImGuiStyleClass = env->FindClass("com/github/xpenatan/imgui/core/ImGuiStyle");
        jclass jImVec4Class = env->FindClass("com/github/xpenatan/imgui/core/ImVec4");
        jclass jImInputTextDataClass = env->FindClass("com/github/xpenatan/imgui/core/ImGuiInputTextData");

        // ImGuiIO Prepare IDs

        WantCaptureMouseID = env->GetFieldID(jImGuiIOClass, "WantCaptureMouse", "Z");
        WantCaptureKeyboardID = env->GetFieldID(jImGuiIOClass, "WantCaptureKeyboard", "Z");
        WantTextInputID = env->GetFieldID(jImGuiIOClass, "WantTextInput", "Z");
        WantSetMousePosID = env->GetFieldID(jImGuiIOClass, "WantSetMousePos", "Z");
        WantSaveIniSettingsID = env->GetFieldID(jImGuiIOClass, "WantSaveIniSettings", "Z");
        NavActiveID = env->GetFieldID(jImGuiIOClass, "NavActive", "Z");
        NavVisibleID = env->GetFieldID(jImGuiIOClass, "NavVisible", "Z");
        FramerateID = env->GetFieldID(jImGuiIOClass, "Framerate", "F");
        MetricsRenderVerticesID = env->GetFieldID(jImGuiIOClass, "MetricsRenderVertices", "I");
        MetricsRenderIndicesID = env->GetFieldID(jImGuiIOClass, "MetricsRenderIndices", "I");
        MetricsRenderWindowsID = env->GetFieldID(jImGuiIOClass, "MetricsRenderWindows", "I");
        MetricsActiveWindowsID = env->GetFieldID(jImGuiIOClass, "MetricsActiveWindows", "I");
        MetricsActiveAllocationsID = env->GetFieldID(jImGuiIOClass, "MetricsActiveAllocations", "I");
        MouseDeltaXID = env->GetFieldID(jImGuiIOClass, "MouseDeltaX", "F");
        MouseDeltaYID = env->GetFieldID(jImGuiIOClass, "MouseDeltaY", "F");

        // ImGuiStyle Prepare IDs

        FramePaddingXID = env->GetFieldID(jImGuiStyleClass, "FramePaddingX", "F");
        FramePaddingYID = env->GetFieldID(jImGuiStyleClass, "FramePaddingY", "F");
        ItemInnerSpacingXID = env->GetFieldID(jImGuiStyleClass, "ItemInnerSpacingX", "F");
        ItemInnerSpacingYID = env->GetFieldID(jImGuiStyleClass, "ItemInnerSpacingY", "F");

        imTextInputDataSizeID = env->GetFieldID(jImInputTextDataClass, "size", "I");
        imTextInputDataIsDirtyID = env->GetFieldID(jImInputTextDataClass, "isDirty", "Z");

        jViewport = env->NewGlobalRef(ImGuiHelper::CreateJImGuiViewport(env));
    */
    public static native void init();

    /*[-cpp;-NATIVE]
        ImGui::CreateContext();
        if(saveIni == false) {
            ImGui::GetIO().IniFilename = NULL;
        }
        ImGui::GetIO().BackendFlags |= ImGuiBackendFlags_HasMouseCursors;
    */
    public static native void CreateContext(boolean saveIni);

    /*[-cpp;-NATIVE]
        ImGui::DestroyContext();
    */
    public static native void DestroyContext();

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.AddKeyEvent((ImGuiKey)imGuiKey, down);
    */
    public static native void AddKeyEvent(int imGuiKey, boolean down);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.AddMousePosEvent(x, y);
    */
    public static native void AddMousePosEvent(float x, float y);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.AddMouseButtonEvent(button, down);
    */
    public static native void AddMouseButtonEvent(int button, boolean down);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.AddMouseWheelEvent(xOffset, yOffset);
    */
    public static native void AddMouseWheelEvent(float xOffset, float yOffset);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.ConfigFlags = flag;
    */
    public static native void SetConfigFlags(int flag);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        int newFlag = io.ConfigFlags & flag;
        return newFlag == flag;
    */
    public static native boolean ContainsConfigFlags(int flag);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.ConfigDockingNoSplit = ConfigDockingNoSplit;
        io.ConfigDockingWithShift = ConfigDockingWithShift;
        io.ConfigDockingAlwaysTabBar = ConfigDockingAlwaysTabBar;
        io.ConfigDockingTransparentPayload = ConfigDockingTransparentPayload;
    */
    public static native void SetDockingFlags(boolean ConfigDockingNoSplit, boolean ConfigDockingWithShift, boolean ConfigDockingAlwaysTabBar, boolean ConfigDockingTransparentPayload);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();

        io.DisplaySize = ImVec2(width, height);
        if (width > 0 && height > 0)
            io.DisplayFramebufferScale = ImVec2((float)display_w / width, (float)display_h / height);
        io.DeltaTime = deltaTime;

        ImGui::NewFrame();

        // Update ImGuiIO
        env->SetBooleanField (jImguiIO, WantCaptureMouseID, io.WantCaptureMouse);
        env->SetBooleanField (jImguiIO, WantCaptureKeyboardID, io.WantCaptureKeyboard);
        env->SetBooleanField (jImguiIO, WantTextInputID, io.WantTextInput);
        env->SetBooleanField (jImguiIO, WantSetMousePosID, io.WantSetMousePos);
        env->SetBooleanField (jImguiIO, WantSaveIniSettingsID, io.WantSaveIniSettings);
        env->SetBooleanField (jImguiIO, NavActiveID, io.NavActive);
        env->SetBooleanField (jImguiIO, NavVisibleID, io.NavVisible);
        env->SetFloatField (jImguiIO, FramerateID, io.Framerate);
        env->SetIntField (jImguiIO, MetricsRenderVerticesID, io.MetricsRenderVertices);
        env->SetIntField (jImguiIO, MetricsRenderIndicesID, io.MetricsRenderIndices);
        env->SetIntField (jImguiIO, MetricsRenderWindowsID, io.MetricsRenderWindows);
        env->SetIntField (jImguiIO, MetricsActiveWindowsID, io.MetricsActiveWindows);
        env->SetIntField (jImguiIO, MetricsActiveAllocationsID, io.MetricsActiveAllocations);
        env->SetFloatField (jImguiIO, MouseDeltaXID, io.MouseDelta.x);
        env->SetFloatField (jImguiIO, MouseDeltaYID, io.MouseDelta.y);

        // Update ImGuiStyle

        ImGuiStyle & style = ImGui::GetStyle();

        env->SetFloatField (jImguiStyle, FramePaddingXID, style.FramePadding.x);
        env->SetFloatField (jImguiStyle, FramePaddingYID, style.FramePadding.y);

        env->SetFloatField (jImguiStyle, ItemInnerSpacingXID, style.ItemInnerSpacing.x);
        env->SetFloatField (jImguiStyle, ItemInnerSpacingYID, style.ItemInnerSpacing.y);
    */
    public static native void UpdateDisplayAndInputAndFrame(ImGuiIO jImguiIO, ImGuiStyle jImguiStyle, float deltaTime, int width, int height, int display_w, int display_h);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        if (c > 0 && c < 0x10000)
            io.AddInputCharacter((unsigned short)c);
    */
    public static native void updateKeyTyped(int c);

    /*[-cpp;-NATIVE]
        ImGui::Render();
    */
    public static native void Render();

    /*[-cpp;-NATIVE]
        ImGui::ShowStyleEditor();
    */
    public static native void ShowStyleEditor();

    /*[-cpp;-NATIVE]
        ImGui::ShowDemoWindow();
    */
    public static native void ShowDemoWindow();

    /*[-cpp;-NATIVE]
        bool toOpen = open;
        ImGui::ShowDemoWindow(&toOpen);
    */
    public static native void ShowDemoWindow(boolean open);

    /*[-cpp;-NATIVE]
        ImGui::ShowMetricsWindow();
    */
    public static native void ShowMetricsWindow();

    /*[-cpp;-NATIVE]
        bool toOpen = open;
        ImGui::ShowMetricsWindow(&toOpen);
    */
    public static native void ShowMetricsWindow(boolean open);

    /*[-cpp;-NATIVE]
        jclass jTexDataClass = env->GetObjectClass(jTexData);
            if(jTexDataClass == NULL)
                return;

        jfieldID widthID = env->GetFieldID(jTexDataClass, "width", "I");
        jfieldID heightID = env->GetFieldID(jTexDataClass, "height", "I");

        unsigned char* pixels;
        int width, height;

        ImGuiIO& io = ImGui::GetIO();
        io.Fonts->GetTexDataAsRGBA32(&pixels, &width, &height);

        env->SetIntField (jTexData, widthID, width);
        env->SetIntField (jTexData, heightID, height);

        memcpy(pixelBuffer, pixels, width * height * 4);
    */
    public static native void GetTexDataAsRGBA32(TexDataRGBA32 jTexData, Buffer pixelBuffer);

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.Fonts->TexID = (ImTextureID)id;
    */
    public static native void SetFontTexID(int id);

    /*[-cpp;-NATIVE]
        ImDrawData * drawData = ImGui::GetDrawData();
        ImGuiHelper::SetImDrawData(env, drawData, jDrawData);
    */
    public static native void GetDrawData(ImDrawData jDrawData);

    /*[-cpp;-NATIVE]
        ImGui::StyleColorsDark();
    */
    public static native void StyleColorsDark();

    /*[-cpp;-NATIVE]
        ImGui::StyleColorsClassic();
    */
    public static native void StyleColorsClassic();

    /*[-cpp;-NATIVE]
        ImGui::StyleColorsLight();
    */
    public static native void StyleColorsLight();

    /*[-cpp;-NATIVE]
        return ImGui::Begin(title);
    */
    public static native boolean Begin(String title);

    /*[-cpp;-NATIVE]
        return ImGui::Begin(title, NULL, imGuiWindowFlags);
    */
    public static native boolean Begin(String title, int imGuiWindowFlags);

    /*[-cpp;-NATIVE]
        return ImGui::Begin(title, &p_open[0], imGuiWindowFlags);
    */
    public static native boolean Begin(String title, boolean[] p_open, int imGuiWindowFlags);

    /*[-cpp;-NATIVE]
        ImGui::End();
    */
    public static native void End();

    /*[-cpp;-NATIVE]
        return ImGui::BeginChild(str_id);
    */
    public static native boolean BeginChild(String str_id);

    /*[-cpp;-NATIVE]
        return ImGui::BeginChild(str_id, ImVec2(width, height));
    */
    public static native boolean BeginChild(String str_id, float width, float height);

    /*[-cpp;-NATIVE]
        return ImGui::BeginChild(str_id, ImVec2(width, height), border);
    */
    public static native boolean BeginChild(String str_id, float width, float height, boolean border);

    /*[-cpp;-NATIVE]
        return ImGui::BeginChild(str_id, ImVec2(width, height), border, flags);
    */
    public static native boolean BeginChild(String str_id, float width, float height, boolean border, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::BeginChild(imGuiID);
    */
    public static native boolean BeginChild(int imGuiID);

    /*[-cpp;-NATIVE]
        return ImGui::BeginChild(imGuiID, ImVec2(width, height), border);
    */
    public static native boolean BeginChild(int imGuiID, float width, float height, boolean border);

    /*[-cpp;-NATIVE]
        return ImGui::BeginChild(imGuiID, ImVec2(width, height), border, flags);
    */
    public static native boolean BeginChild(int imGuiID, float width, float height, boolean border, int flags);

    /*[-cpp;-NATIVE]
        ImGui::EndChild();
    */
    public static native void EndChild();

    /*[-cpp;-NATIVE]
        return ImGui::IsWindowAppearing();
    */
    public static native boolean IsWindowAppearing();

    /*[-cpp;-NATIVE]
        return ImGui::IsWindowCollapsed();
    */
    public static native boolean IsWindowCollapsed();

    /*[-cpp;-NATIVE]
        return ImGui::IsWindowFocused();
    */
    public static native boolean IsWindowFocused();

    /*[-cpp;-NATIVE]
        return ImGui::IsWindowFocused(flags);
    */
    public static native boolean IsWindowFocused(int flags);

    /*[-cpp;-NATIVE]
        return ImGui::IsWindowHovered();
    */
    public static native boolean IsWindowHovered();

    /*[-cpp;-NATIVE]
        return ImGui::IsWindowHovered(flags);
    */
    public static native boolean IsWindowHovered(int flags);

    // DrawList

    /*[-cpp;-NATIVE]
        ImDrawList * getDrawList(int type) {
            ImDrawList* drawList = NULL;
            if(type == DRAWLIST_TYPE_DEFAULT)
                drawList = ImGui::GetWindowDrawList();
            else if(type == DRAWLIST_TYPE_BACKGROUND)
                drawList = ImGui::GetBackgroundDrawList();
            else if(type == DRAWLIST_TYPE_FOREGROUND)
                drawList = ImGui::GetForegroundDrawList();
            return drawList;
        }
    */

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddLine(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col);
    */
    public static native void AddLine(int type, float a_x, float a_y, float b_x, float b_y, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddLine(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, thinkness);
    */
    public static native void AddLine(int type, float a_x, float a_y, float b_x, float b_y, int col, float thinkness);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col);
    */
    public static native void AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding);
    */
    public static native void AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding, rounding_corners_flags);
    */
    public static native void AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding, int rounding_corners_flags);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRect(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding, rounding_corners_flags, thickness);
    */
    public static native void AddRect(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding, int rounding_corners_flags, float thickness);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRectFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col);
    */
    public static native void AddRectFilled(int type, float a_x, float a_y, float b_x, float b_y, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRectFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding);
    */
    public static native void AddRectFilled(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRectFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col, rounding, rounding_corners_flags);
    */
    public static native void AddRectFilled(int type, float a_x, float a_y, float b_x, float b_y, int col, float rounding, int rounding_corners_flags);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddRectFilledMultiColor(ImVec2(a_x, a_y), ImVec2(b_x, b_y), col_upr_left, col_upr_right, col_bot_right, col_bot_left);
    */
    public static native void AddRectFilledMultiColor(int type, float a_x, float a_y, float b_x, float b_y, int col_upr_left, float col_upr_right, int col_bot_right, int col_bot_left);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddQuad(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), ImVec2(d_x, d_y), col);
    */
    public static native void AddQuad(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddQuad(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), ImVec2(d_x, d_y), col, thickness);
    */
    public static native void AddQuad(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col, float thickness);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddQuadFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), ImVec2(d_x, d_y), col);
    */
    public static native void AddQuadFilled(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddTriangle(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), col);
    */
    public static native void AddTriangle(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddTriangle(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), col, thickness);
    */
    public static native void AddTriangle(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col, float thickness);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddTriangleFilled(ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(c_x, c_y), col);
    */
    public static native void AddTriangleFilled(int type, float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddCircle(ImVec2(centre_x, centre_y), radius, col);
    */
    public static native void AddCircle(int type, float centre_x, float centre_y, float radius, float col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddCircle(ImVec2(centre_x, centre_y), radius, col, num_segments, thickness);
    */
    public static native void AddCircle(int type, float centre_x, float centre_y, float radius, float col, int num_segments, float thickness);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddCircleFilled(ImVec2(centre_x, centre_y), radius, col);
    */
    public static native void AddCircleFilled(int type, float centre_x, float centre_y, float radius, float col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddCircleFilled(ImVec2(centre_x, centre_y), radius, col, num_segments);
    */
    public static native void AddCircleFilled(int type, float centre_x, float centre_y, float radius, float col, int num_segments);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddText(ImVec2(pos_x, pos_y), col, text_begin);
    */
    public static native void AddText(int type, float pos_x, float pos_y, int col, String text_begin);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddText(ImVec2(pos_x, pos_y), col, text_begin, text_end);
    */
    public static native void AddText(int type, float pos_x, float pos_y, int col, String text_begin, String text_end);

//TODO	IMGUI_API void  AddText(const ImFont* font, float font_size, const ImVec2& pos, ImU32 col, const char* text_begin, const char* text_end = NULL, float wrap_width = 0.0f, const ImVec4* cpu_fine_clip_rect = NULL);
//TODO	IMGUI_API void  AddPolyline(const ImVec2* points, int num_points, ImU32 col, ImDrawFlags flags, float thickness);
//TODO	IMGUI_API void  AddConvexPolyFilled(const ImVec2* points, int num_points, ImU32 col); // Note: Anti-aliased filling requires points to be in clockwise order.
//TODO	IMGUI_API void  AddBezierCubic(const ImVec2& p1, const ImVec2& p2, const ImVec2& p3, const ImVec2& p4, ImU32 col, float thickness, int num_segments = 0); // Cubic Bezier (4 control points)
//TODO	IMGUI_API void  AddBezierQuadratic(const ImVec2& p1, const ImVec2& p2, const ImVec2& p3, ImU32 col, float thickness, int num_segments = 0);               // Quadratic Bezier (3 control points)

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddImage((void *)textureID, ImVec2(a_x, a_y), ImVec2(b_x, b_y));
    */
    public static native void AddImage(int type, int textureID, float a_x, float a_y, float b_x, float b_y);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->AddImage((void *)textureID, ImVec2(a_x, a_y), ImVec2(b_x, b_y), ImVec2(uv_a_x, uv_a_y), ImVec2(uv_b_x, uv_b_y));
    */
    public static native void AddImage(int type, int textureID, float a_x, float a_y, float b_x, float b_y, float uv_a_x, float uv_a_y, float uv_b_x, float uv_b_y);

//TODO	IMGUI_API void  AddImageQuad(ImTextureID user_texture_id, const ImVec2& p1, const ImVec2& p2, const ImVec2& p3, const ImVec2& p4, const ImVec2& uv1 = ImVec2(0, 0), const ImVec2& uv2 = ImVec2(1, 0), const ImVec2& uv3 = ImVec2(1, 1), const ImVec2& uv4 = ImVec2(0, 1), ImU32 col = IM_COL32_WHITE);
//TODO	IMGUI_API void  AddImageRounded(ImTextureID user_texture_id, const ImVec2& p_min, const ImVec2& p_max, const ImVec2& uv_min, const ImVec2& uv_max, ImU32 col, float rounding, ImDrawFlags flags = 0);

    // Stateful path API, add points then finish with PathFillConvex() or PathStroke()

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->PathClear();
    */
    public static native void PathClear(int type);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->PathLineTo(ImVec2(pos_x, pos_y));
    */
    public static native void PathLineTo(int type, float pos_x, float pos_y);

//TODO	inline    void  PathLineToMergeDuplicate(const ImVec2& pos)                 { if (_Path.Size == 0 || memcmp(&_Path.Data[_Path.Size - 1], &pos, 8) != 0) _Path.push_back(pos); }
//TODO	inline    void  PathFillConvex(ImU32 col)                                   { AddConvexPolyFilled(_Path.Data, _Path.Size, col); _Path.Size = 0; }  // Note: Anti-aliased filling requires points to be in clockwise order.

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->PathStroke(col);
    */
    public static native void PathStroke(int type, int col);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->PathStroke(col, flags);
    */
    public static native void PathStroke(int type, int col, int flags);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->PathStroke(col, flags, thickness);
    */
    public static native void PathStroke(int type, int col, int flags, float thickness);
//TODO	inline    void  PathStroke(ImU32 col, ImDrawFlags flags = 0, float thickness = 1.0f) { AddPolyline(_Path.Data, _Path.Size, col, flags, thickness); _Path.Size = 0; }

//TODO	IMGUI_API void  PathArcTo(const ImVec2& center, float radius, float a_min, float a_max, int num_segments = 0);
//TODO	IMGUI_API void  PathArcToFast(const ImVec2& center, float radius, int a_min_of_12, int a_max_of_12);                // Use precomputed angles for a 12 steps circle
//TODO	IMGUI_API void  PathBezierCubicCurveTo(const ImVec2& p2, const ImVec2& p3, const ImVec2& p4, int num_segments = 0); // Cubic Bezier (4 control points)
//TODO	IMGUI_API void  PathBezierQuadraticCurveTo(const ImVec2& p2, const ImVec2& p3, int num_segments = 0);               // Quadratic Bezier (3 control points)
//TODO	IMGUI_API void  PathRect(const ImVec2& rect_min, const ImVec2& rect_max, float rounding = 0.0f, ImDrawFlags flags = 0);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->ChannelsSplit(count);
    */
    public static native void ChannelsSplit(int type, int count);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->ChannelsMerge();
    */
    public static native void ChannelsMerge(int type);

    /*[-cpp;-NATIVE]
        ImDrawList* drawList = getDrawList(type);
        drawList->ChannelsSetCurrent(n);
    */
    public static native void ChannelsSetCurrent(int type, int n);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetWindowPos();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetWindowPos(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        return ImGui::GetWindowPos().x;
    */
    public static native float GetWindowPosX();

    /*[-cpp;-NATIVE]
        return ImGui::GetWindowPos().y;
    */
    public static native float GetWindowPosY();

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetWindowSize();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetWindowSize(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        return ImGui::GetWindowWidth();
    */
    public static native float GetWindowWidth();

    /*[-cpp;-NATIVE]
        return ImGui::GetWindowHeight();
    */
    public static native float GetWindowHeight();

    /*[-cpp;-NATIVE]
        ImGuiViewport* viewport = ImGui::GetWindowViewport();
        ImGuiHelper::SetImGuiViewport(env, viewport, jViewport);
        return jViewport;
    */
    public static native ImGuiViewport GetWindowViewport();

    // Prefer using SetNextXXX functions (before Begin) rather that SetXXX functions (after Begin).

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowPos(ImVec2(x, y));
    */
    public static native void SetNextWindowPos(float x, float y);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowPos(ImVec2(x, y), cond);
    */
    public static native void SetNextWindowPos(float x, float y, int cond);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowPos(ImVec2(x, y), cond, ImVec2(pivot_x, pivot_y));
    */
    public static native void SetNextWindowPos(float x, float y, int cond, float pivot_x, float pivot_y);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowSize(ImVec2(width, height));
    */
    public static native void SetNextWindowSize(float width, float height);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowSize(ImVec2(width, height), cond);
    */
    public static native void SetNextWindowSize(float width, float height, int cond);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowSizeConstraints(ImVec2(min_width, min_height), ImVec2(max_width, max_height));
    */
    public static native void SetNextWindowSizeConstraints(float min_width, float min_height, float max_width, float max_height);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowContentSize(ImVec2(width, height));
    */
    public static native void SetNextWindowContentSize(float width, float height);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowCollapsed(collapsed);
    */
    public static native void SetNextWindowCollapsed(boolean collapsed);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowCollapsed(collapsed, cond);
    */
    public static native void SetNextWindowCollapsed(boolean collapsed, int cond);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowFocus();
    */
    public static native void SetNextWindowFocus();

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowBgAlpha(alpha);
    */
    public static native void SetNextWindowBgAlpha(float alpha);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowPos(ImVec2(x, y));
    */
    public static native void SetWindowPos(float x, float y);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowPos(ImVec2(x, y), cond);
    */
    public static native void SetWindowPos(float x, float y, int cond);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowSize(ImVec2(width, height));
    */
    public static native void SetWindowSize(float width, float height);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowSize(ImVec2(width, height), cond);
    */
    public static native void SetWindowSize(float width, float height, int cond);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowCollapsed(collapsed, cond);
    */
    public static native void SetWindowCollapsed(boolean collapsed, int cond);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowFocus();
    */
    public static native void SetWindowFocus();

    /*[-cpp;-NATIVE]
        ImGui::SetWindowFontScale(scale);
    */
    public static native void SetWindowFocus(float scale);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowPos(name, ImVec2(x, y));
    */
    public static native void SetWindowPos(String name, float x, float y);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowPos(name, ImVec2(x, y), cond);
    */
    public static native void SetWindowPos(String name, float x, float y, int cond);

    /*[-cpp;-NATIVE]
        bool flag = collapsed;
        ImGui::SetWindowCollapsed(name, flag);
    */
    public static native void SetWindowCollapsed(String name, boolean collapsed);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowCollapsed(name, collapsed, cond);
    */
    public static native void SetWindowCollapsed(String name, boolean collapsed, int cond);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowFocus(name);
    */
    public static native void SetWindowFocus(String name);

    /*[-cpp;-NATIVE]
        ImGui::SetWindowFocus(NULL);
    */
    public static native void RemoveWindowFocus();

    // Content region
    // - Those functions are bound to be redesigned soon (they are confusing, incomplete and return values in local window coordinates which increases confusion)

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetContentRegionMax();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetContentRegionMax(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetContentRegionAvail();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetContentRegionAvail(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetWindowContentRegionMin();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetWindowContentRegionMin(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetWindowContentRegionMax();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetWindowContentRegionMax(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        return ImGui::GetWindowContentRegionWidth();
    */
    public static native float GetWindowContentRegionWidth();

    // Windows Scrolling

    /*[-cpp;-NATIVE]
        return ImGui::GetScrollX();
    */
    public static native float GetScrollX();

    /*[-cpp;-NATIVE]
        return ImGui::GetScrollY();
    */
    public static native float GetScrollY();

    /*[-cpp;-NATIVE]
        return ImGui::GetScrollMaxX();
    */
    public static native float GetScrollMaxX();

    /*[-cpp;-NATIVE]
        return ImGui::GetScrollMaxY();
    */
    public static native float GetScrollMaxY();

    /*[-cpp;-NATIVE]
        ImGui::SetScrollX(scroll_x);
    */
    public static native void SetScrollX(float scroll_x);

    /*[-cpp;-NATIVE]
        ImGui::SetScrollY(scroll_y);
    */
    public static native void SetScrollY(float scroll_y);

    /*[-cpp;-NATIVE]
        ImGui::SetScrollFromPosY(local_y);
    */
    public static native void SetScrollFromPosY(float local_y);

    /*[-cpp;-NATIVE]
        ImGui::SetScrollFromPosY(local_y, center_y_ratio);
    */
    public static native void SetScrollFromPosY(float local_y, float center_y_ratio);

    // Parameters stacks (shared)

    //TODO impl
    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        ImFontAtlas* atlas = io.Fonts;
        ImFont* font = atlas->Fonts[index];
        ImGui::PushFont(font);
    */
    public static native void PushFont(int index);

    /*[-cpp;-NATIVE]
        ImGui::PopFont();
    */
    public static native void PopFont();

    /*[-cpp;-NATIVE]
        ImGui::PushStyleColor(idx, col);
    */
    public static native void PushStyleColor(int idx, int col);

    /*[-cpp;-NATIVE]
        ImGui::PushStyleColor(idx, ImVec4(r, g, b, a));
    */
    public static native void PushStyleColor(int idx, float r, float g, float b, float a);

    /*[-cpp;-NATIVE]
        ImGui::PopStyleColor();
    */
    public static native void PopStyleColor();

    /*[-cpp;-NATIVE]
        ImGui::PopStyleColor(count);
    */
    public static native void PopStyleColor(int count);

    /*[-cpp;-NATIVE]
        ImGui::PushStyleVar(idx, val);
    */
    public static native void PushStyleVar(int idx, float val);

    /*[-cpp;-NATIVE]
        ImGui::PushStyleVar(idx, ImVec2(val_x, val_y));
    */
    public static native void PushStyleVar(int idx, float val_x, float val_y);

    /*[-cpp;-NATIVE]
        ImGui::PopStyleVar();
    */
    public static native void PopStyleVar();

    /*[-cpp;-NATIVE]
        ImGui::PopStyleVar(count);
    */
    public static native void PopStyleVar(int count);

    /*[-cpp;-NATIVE]
        ImVec4 val = ImGui::GetStyleColorVec4(idx);
        value[0] = val.x;
        value[1] = val.y;
        value[2] = val.z;
        value[3] = val.w;
    */
    public static native void GetStyleColorVec4(int idx, float[] value);

    //TODO impl
    /*[-cpp;-NATIVE]
        ImFont * font = ImGui::GetFont();
    */
    public static native void GetFont();

    /*[-cpp;-NATIVE]
        return ImGui::GetFontSize();
    */
    public static native int GetFontSize();

    /*[-cpp;-NATIVE]
        ImVec2 val = ImGui::GetFontTexUvWhitePixel();
        value[0] = val.x;
        value[1] = val.y;
    */
    public static native void GetFontTexUvWhitePixel(float[] value);

    /*[-cpp;-NATIVE]
        return ImGui::GetColorU32((ImGuiCol)idx);
    */
    public static native int GetColorU32(int idx);

    /*[-cpp;-NATIVE]
        return ImGui::GetColorU32(idx, alpha_mul);
    */
    public static native int GetColorU32(int idx, float alpha_mul);

    /*[-cpp;-NATIVE]
        return ImGui::GetColorU32(ImVec4(col_x, col_y, col_z, col_w));
    */
    public static native int GetColorU32(float col_x, float col_y, float col_z, float col_w);

    // Parameters stacks (current window)

    /*[-cpp;-NATIVE]
        ImGui::PushItemWidth(item_width);
    */
    public static native void PushItemWidth(float item_width);

    /*[-cpp;-NATIVE]
        ImGui::PopItemWidth();
    */
    public static native void PopItemWidth();

    /*[-cpp;-NATIVE]
        ImGui::SetNextItemWidth(item_width);
    */
    public static native void SetNextItemWidth(float item_width);

    /*[-cpp;-NATIVE]
        return ImGui::CalcItemWidth();
    */
    public static native float CalcItemWidth();

    /*[-cpp;-NATIVE]
        ImGui::PushTextWrapPos(wrap_local_pos_x);
    */
    public static native void PushTextWrapPos(float wrap_local_pos_x);

    /*[-cpp;-NATIVE]
        ImGui::PushTextWrapPos();
    */
    public static native void PushTextWrapPos();

    /*[-cpp;-NATIVE]
        ImGui::PopTextWrapPos();
    */
    public static native void PopTextWrapPos();

    /*[-cpp;-NATIVE]
        ImGui::PushAllowKeyboardFocus(allow_keyboard_focus);
    */
    public static native void PushAllowKeyboardFocus(boolean allow_keyboard_focus);

    /*[-cpp;-NATIVE]
        ImGui::PopAllowKeyboardFocus();
    */
    public static native void PopAllowKeyboardFocus();

    /*[-cpp;-NATIVE]
        ImGui::PushButtonRepeat(repeat);
    */
    public static native void PushButtonRepeat(boolean repeat);

    /*[-cpp;-NATIVE]
        ImGui::PopButtonRepeat();
    */
    public static native void PopButtonRepeat();

    // Cursor / Layout
    // - By "cursor" we mean the current output position.
    // - The typical widget behavior is to output themselves at the current cursor position, then move the cursor one line down.

    /*[-cpp;-NATIVE]
        ImGui::Separator();
    */
    public static native void Separator();

    /*[-cpp;-NATIVE]
        ImGui::SameLine();
    */
    public static native void SameLine();

    /*[-cpp;-NATIVE]
        ImGui::SameLine(offsetFromStartX);
    */
    public static native void SameLine(float offsetFromStartX);

    /*[-cpp;-NATIVE]
        ImGui::SameLine(offsetFromStartX, spacing);
    */
    public static native void SameLine(float offsetFromStartX, float spacing);

    /*[-cpp;-NATIVE]
        ImGui::NewLine();
    */
    public static native void NewLine();

    /*[-cpp;-NATIVE]
        ImGui::Spacing();
    */
    public static native void Spacing();

    /*[-cpp;-NATIVE]
        ImGui::Dummy(ImVec2(width, height));
    */
    public static native void Dummy(float width, float height);

    /*[-cpp;-NATIVE]
        ImGui::Indent();
    */
    public static native void Indent();

    /*[-cpp;-NATIVE]
        ImGui::Indent(indentW);
    */
    public static native void Indent(float indentW);

    /*[-cpp;-NATIVE]
        ImGui::Unindent();
    */
    public static native void Unindent();

    /*[-cpp;-NATIVE]
        ImGui::Unindent(indentW);
    */
    public static native void Unindent(float indentW);

    /*[-cpp;-NATIVE]
        ImGui::BeginGroup();
    */
    public static native void BeginGroup();

    /*[-cpp;-NATIVE]
        ImGui::EndGroup();
    */
    public static native void EndGroup();

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetCursorPos();
        vec2[0] = vec.x;
        vec2[1] = vec.y;
    */
    public static native void GetCursorPos(float[] vec2);

    /*[-cpp;-NATIVE]
        return ImGui::GetCursorPosX();
    */
    public static native float GetCursorPosX();

    /*[-cpp;-NATIVE]
        return ImGui::GetCursorPosY();
    */
    public static native float GetCursorPosY();

    /*[-cpp;-NATIVE]
        ImGui::SetCursorPos(ImVec2(x, y));
    */
    public static native void SetCursorPos(float x, float y);

    /*[-cpp;-NATIVE]
        ImGui::SetCursorPosX(x);
    */
    public static native void SetCursorPosX(float x);

    /*[-cpp;-NATIVE]
        ImGui::SetCursorPosY(y);
    */
    public static native void SetCursorPosY(float y);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetCursorStartPos();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetCursorStartPos(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetCursorScreenPos();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
     */
    public static native void GetCursorScreenPos(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImGui::SetCursorScreenPos(ImVec2(x, y));
    */
    public static native void SetCursorScreenPos(float x, float y);

    /*[-cpp;-NATIVE]
        ImGui::AlignTextToFramePadding();
    */
    public static native void AlignTextToFramePadding();

    /*[-cpp;-NATIVE]
        return ImGui::GetTextLineHeight();
    */
    public static native float GetTextLineHeight();

    /*[-cpp;-NATIVE]
        return ImGui::GetTextLineHeightWithSpacing();
    */
    public static native float GetTextLineHeightWithSpacing();

    /*[-cpp;-NATIVE]
        return ImGui::GetFrameHeight();
    */
    public static native float GetFrameHeight();

    /*[-cpp;-NATIVE]
        return ImGui::GetFrameHeightWithSpacing();
    */
    public static native float GetFrameHeightWithSpacing();

    // ID stack/scopes
    // - Read the FAQ for more details about how ID are handled in dear imgui. If you are creating widgets in a loop you most
    //   likely want to push a unique identifier (e.g. object pointer, loop index) to uniquely differentiate them.
    // - The resulting ID are hashes of the entire stack.
    // - You can also use the "Label##foobar" syntax within widget label to distinguish them from each others.
    // - In this header file we use the "label"/"name" terminology to denote a string that will be displayed and used as an ID,
    //   whereas "str_id" denote a string that is only used as an ID and not normally displayed.

    /*[-cpp;-NATIVE]
        ImGui::PushID(str_id);
    */
    public static native void PushID(String str_id);

    /*[-cpp;-NATIVE]
        ImGui::PushID(str_id_begin, str_id_end);
    */
    public static native void PushID(String str_id_begin, String str_id_end);

    /*[-cpp;-NATIVE]
        ImGui::PushID(int_id);
    */
    public static native void PushID(int int_id);

    /*[-cpp;-NATIVE]
        ImGui::PopID();
    */
    public static native void PopID();

    /*[-cpp;-NATIVE]
        return ImGui::GetID(str_id);
    */
    public static native int GetID(String str_id);

    /*[-cpp;-NATIVE]
        return ImGui::GetID(str_id_begin, str_id_end);
    */
    public static native int GetID(String str_id_begin, String str_id_end);

    // Widgets: Text

    /*[-cpp;-NATIVE]
        ImGui::TextUnformatted(text);
    */
    public static native void TextUnformatted(String text);

    /*[-cpp;-NATIVE]
        ImGui::TextUnformatted(text, text_end);
    */
    public static native void TextUnformatted(String text, String text_end);

    /*[-cpp;-NATIVE]
        ImGui::Text(text);
    */
    public static native void Text(String text);

    /*[-cpp;-NATIVE]
        ImGui::Text(text);
    */
    public static native void Text(byte[] text);

    /*[-cpp;-NATIVE]
        ImGui::TextColored(ImVec4(r, g, b, a), text);
    */
    public static native void TextColored(float r, float g, float b, float a, String text);

    /*[-cpp;-NATIVE]
        ImGui::TextDisabled(text);
    */
    public static native void TextDisabled(String text);

    /*[-cpp;-NATIVE]
        ImGui::TextWrapped(text);
    */
    public static native void TextWrapped(String text);

    /*[-cpp;-NATIVE]
        ImGui::LabelText(label, text);
    */
    public static native void LabelText(String label, String text);

    /*[-cpp;-NATIVE]
        ImGui::BulletText(text);
    */
    public static native void BulletText(String text);

    // Widgets: Main
    // - Most widgets return true when the value has been changed or when pressed/selected

    /*[-cpp;-NATIVE]
        return ImGui::Button(label);
    */
    public static native boolean Button(String label);

    /*[-cpp;-NATIVE]
        return ImGui::Button(label, ImVec2(width, height));
    */
    public static native boolean Button(String label, float width, float height);

    /*[-cpp;-NATIVE]
        return ImGui::SmallButton(label);
    */
    public static native boolean SmallButton(String label);

    /*[-cpp;-NATIVE]
        return ImGui::InvisibleButton(strId, ImVec2(width, height));
    */
    public static native boolean InvisibleButton(String strId, float width, float height);

    /*[-cpp;-NATIVE]
        return ImGui::ArrowButton(strId, dir);
    */
    public static native boolean ArrowButton(String strId, int dir);

    /*[-cpp;-NATIVE]
        ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY));
    */
    public static native void Image(int textureID, float sizeX, float sizeY);

    /*[-cpp;-NATIVE]
        ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y));
    */
    public static native void Image(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y);

    /*[-cpp;-NATIVE]
        ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), ImVec4(tint_color_r, tint_color_g, tint_color_b, tint_color_a), ImVec4(border_col_r, border_col_g, border_col_b, border_col_a));
    */
    public static native void Image(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, float tint_color_r, float tint_color_g, float tint_color_b, float tint_color_a, float border_col_r, float border_col_g, float border_col_b, float border_col_a);

    /*[-cpp;-NATIVE]
        return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY));
    */
    public static native boolean ImageButton(int textureID, float sizeX, float sizeY);

    /*[-cpp;-NATIVE]
        return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), frame_padding);
    */
    public static native boolean ImageButton(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, int frame_padding);

    /*[-cpp;-NATIVE]
        return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), frame_padding, ImVec4(bg_color_r, bg_color_g, bg_color_b, bg_color_a), ImVec4(tint_col_r, tint_col_g, tint_col_b, tint_col_a));
    */
    public static native boolean ImageButton(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, int frame_padding, float bg_color_r, float bg_color_g, float bg_color_b, float bg_color_a, float tint_col_r, float tint_col_g, float tint_col_b, float tint_col_a);

    /*[-cpp;-NATIVE]
        return ImGui::Checkbox(label, &data[0]);
    */
    public static native boolean Checkbox(String label, boolean[] data);

    //TODO check if its working
    /*[-cpp;-NATIVE]
        return ImGui::CheckboxFlags(label, (unsigned int*)&data[0], flagsValue);
    */
    public static native boolean CheckboxFlags(String label, int[] data, int flagsValue);

    /*[-cpp;-NATIVE]
        return ImGui::RadioButton(label, active);
    */
    public static native boolean RadioButton(String label, boolean active);

    /*[-cpp;-NATIVE]
        return ImGui::RadioButton(label, &data[0], v_button);
    */
    public static native boolean RadioButton(String label, int[] data, int v_button);

    /*[-cpp;-NATIVE]
        ImGui::ProgressBar(fraction);
    */
    public static native void ProgressBar(float fraction);

    /*[-cpp;-NATIVE]
        ImGui::ProgressBar(fraction, ImVec2(size_arg_x, size_arg_y));
    */
    public static native void ProgressBar(float fraction, float size_arg_x, float size_arg_y);

    /*[-cpp;-NATIVE]
        ImGui::Bullet();
    */
    public static native void Bullet();

    // Widgets: Combo Box
    // - The new BeginCombo()/EndCombo() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() items.
    // - The old Combo() api are helpers over BeginCombo()/EndCombo() which are kept available for convenience purpose.

    /*[-cpp;-NATIVE]
        return ImGui::BeginCombo(label, preview_value);
    */
    public static native boolean BeginCombo(String label, String preview_value);

    /*[-cpp;-NATIVE]
        return ImGui::BeginCombo(label, preview_value, flags);
    */
    public static native boolean BeginCombo(String label, String preview_value, int flags);

    /*[-cpp;-NATIVE]
        ImGui::EndCombo();
    */
    public static native void EndCombo();

    /*[-cpp;-NATIVE]
        const char* listbox_items[items_count];
        for(int i = 0; i < items_count; i++) {
            jstring string = (jstring) (env->GetObjectArrayElement(items, i));
            const char *rawString = env->GetStringUTFChars(string, 0);
            listbox_items[i] = rawString;
        }
        return ImGui::Combo(label, &current_item[0], listbox_items, items_count);
    */
    public static native boolean Combo(String label, int[] current_item, String[] items, int items_count);

    /*[-cpp;-NATIVE]
        const char* listbox_items[items_count];
        for(int i = 0; i < items_count; i++) {
            jstring string = (jstring) (env->GetObjectArrayElement(items, i));
            const char *rawString = env->GetStringUTFChars(string, 0);
            listbox_items[i] = rawString;
        }
        return ImGui::Combo(label, &current_item[0], listbox_items, items_count, popup_max_height_in_items);
    */
    public static native boolean Combo(String label, int[] current_item, String[] items, int items_count, int popup_max_height_in_items);

    /*[-cpp;-NATIVE]
        return ImGui::Combo(label, &current_item[0], items_separated_by_zeros);
    */
    public static native boolean Combo(String label, int[] current_item, String items_separated_by_zeros);

    /*[-cpp;-NATIVE]
        return ImGui::Combo(label, &current_item[0], items_separated_by_zeros, popup_max_height_in_items);
    */
    public static native boolean Combo(String label, int[] current_item, String items_separated_by_zeros, int popup_max_height_in_items);

    // Widgets: Drags
    // - CTRL+Click on any drag box to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - For all the Float2/Float3/Float4/Int2/Int3/Int4 versions of every functions, note that a 'float v[X]' function argument is the same as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible. You can pass address of your first element out of a contiguous set, e.g. &myvector.x
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
    // - Speed are per-pixel of mouse movement (v_speed=0.2f: mouse needs to move by 5 pixels to increase value by 1). For gamepad/keyboard navigation, minimum speed is Max(v_speed, minimum_step_at_given_precision).

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat(label, &v[0]);
    */
    public static native boolean DragFloat(String label, float[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat(label, &v[0], v_speed);
    */
    public static native boolean DragFloat(String label, float[] v, float v_speed);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat(label, &v[0], v_speed, v_min, v_max, format);
    */
    public static native boolean DragFloat(String label, float[] v, float v_speed, float v_min, float v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat(label, &v[0], v_speed, v_min, v_max, format, power);
    */
    public static native boolean DragFloat(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat2(label, v);
    */
    public static native boolean DragFloat2(String label, float[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat2(label, v, v_speed, v_min, v_max, format, power);
    */
    public static native boolean DragFloat2(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat3(label, v);
    */
    public static native boolean DragFloat3(String label, float[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat3(label, v, v_speed, v_min, v_max, format, power);
    */
    public static native boolean DragFloat3(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat4(label, v);
    */
    public static native boolean DragFloat4(String label, float[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloat4(label, v, v_speed, v_min, v_max, format, power);
    */
    public static native boolean DragFloat4(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0]);
    */
    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max);

    /*[-cpp;-NATIVE]
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format, format_max, power);
    */
    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max, float v_speed, float v_min, float v_max, String format, String format_max, float power);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt(label, &v[0]);
    */
    public static native boolean DragInt(String label, int[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt(label, &v[0], v_speed);
    */
    public static native boolean DragInt(String label, int[] v, float v_speed);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt(label, &v[0], v_speed, v_min, v_max, format);
    */
    public static native boolean DragInt(String label, int[] v, float v_speed, float v_min, float v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt2(label, v);
    */
    public static native boolean DragInt2(String label, int[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt2(label, v, v_speed, v_min, v_max, format);
    */
    public static native boolean DragInt2(String label, int[] v, float v_speed, float v_min, float v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt2(label, v);
    */
    public static native boolean DragInt3(String label, int[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt2(label, v, v_speed, v_min, v_max, format);
    */
    public static native boolean DragInt3(String label, int[] v, float v_speed, float v_min, float v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt4(label, v);
    */
    public static native boolean DragInt4(String label, int[] v);

    /*[-cpp;-NATIVE]
        return ImGui::DragInt4(label, v, v_speed, v_min, v_max, format);
    */
    public static native boolean DragInt4(String label, int[] v, float v_speed, float v_min, float v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0]);
    */
    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max);

    /*[-cpp;-NATIVE]
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format, format_max);
    */
    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max, float v_speed, float v_min, float v_max, String format, String format_max);

    //TODO impl other types
    /*[-cpp;-NATIVE]
        return ImGui::DragScalar(label, data_type, &v[0], v_speed);
    */
    public static native boolean DragScalar(String label, int data_type, int[] v, float v_speed);

    //TODO impl other types
    /*[-cpp;-NATIVE]
        return ImGui::DragScalar(label, data_type, &v[0], v_speed, &v_min, &v_max, format, power);
    */
    public static native boolean DragScalar(String label, int data_type, int[] v, float v_speed, float v_min, float v_max, String format, float power);

    // Widgets: Sliders
    // - CTRL+Click on any slider to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat(label, &v[0],v_min, v_max);
    */
    public static native boolean SliderFloat(String label, float[] v, float v_min, float v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat(label, &v[0], v_min, v_max, format);
    */
    public static native boolean SliderFloat(String label, float[] v, float v_min, float v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat(label, &v[0], v_min, v_max, format, power);
    */
    public static native boolean SliderFloat(String label, float[] v, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat2(label, v, v_min, v_max);
    */
    public static native boolean SliderFloat2(String label, float[] v, float v_min, float v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat2(label, v, v_min, v_max, format, power);
    */
    public static native boolean SliderFloat2(String label, float[] v, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat3(label, v, v_min, v_max);
    */
    public static native boolean SliderFloat3(String label, float[] v, float v_min, float v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat3(label, v, v_min, v_max, format, power);
    */
    public static native boolean SliderFloat3(String label, float[] v, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat4(label, v, v_min, v_max);
    */
    public static native boolean SliderFloat4(String label, float[] v, float v_min, float v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderFloat4(label, v, v_min, v_max, format, power);
    */
    public static native boolean SliderFloat4(String label, float[] v, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::SliderAngle(label, &v_rad[0]);
    */
    public static native boolean SliderAngle(String label, float[] v_rad);

    /*[-cpp;-NATIVE]
        return ImGui::SliderAngle(label, &v_rad[0], v_degrees_min, v_degrees_max, format);
    */
    public static native boolean SliderAngle(String label, float[] v_rad, float v_degrees_min, float v_degrees_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt(label, &v[0], v_min, v_max);
    */
    public static native boolean SliderInt(String label, int[] v, int v_min, int v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt(label, &v[0], v_min, v_max, format);
    */
    public static native boolean SliderInt(String label, int[] v, int v_min, int v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt2(label, v, v_min, v_max);
    */
    public static native boolean SliderInt2(String label, int[] v, int v_min, int v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt2(label, v, v_min, v_max, format);
    */
    public static native boolean SliderInt2(String label, int[] v, int v_min, int v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt3(label, v, v_min, v_max);
    */
    public static native boolean SliderInt3(String label, int[] v, int v_min, int v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt3(label, v, v_min, v_max, format);
    */
    public static native boolean SliderInt3(String label, int[] v, int v_min, int v_max, String format);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt4(label, v, v_min, v_max);
    */
    public static native boolean SliderInt4(String label, int[] v, int v_min, int v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderInt4(label, v, v_min, v_max, format);
    */
    public static native boolean SliderInt4(String label, int[] v, int v_min, int v_max, String format);

    //TODO impl other types
    /*[-cpp;-NATIVE]
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
    */
    public static native boolean SliderScalar(String label, int data_type, int[] v, int v_min, int v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
    */
    public static native boolean SliderScalar(String label, int data_type, int[] v, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
    */
    public static native boolean SliderScalar(String label, int data_type, float[] v, float v_min, float v_max);

    /*[-cpp;-NATIVE]
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
    */
    public static native boolean SliderScalar(String label, int data_type, float[] v, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max);
    */
    public static native boolean VSliderFloat(String label, float sizeX, float sizeY, float[] v, float v_min, float v_max);

    /*[-cpp;-NATIVE]
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max, format, power);
    */
    public static native boolean VSliderFloat(String label, float sizeX, float sizeY, float[] v, float v_min, float v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max);
    */
    public static native boolean VSliderInt(String label, float sizeX, float sizeY, int[] v, int v_min, int v_max);

    /*[-cpp;-NATIVE]
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max, format);
    */
    public static native boolean VSliderInt(String label, float sizeX, float sizeY, int[] v, int v_min, int v_max, String format);

    //TODO impl other types
    /*[-cpp;-NATIVE]
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
    */
    public static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, int[] v, int v_min, int v_max);

    /*[-cpp;-NATIVE]
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
    */
    public static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, int[] v, int v_min, int v_max, String format, float power);

    /*[-cpp;-NATIVE]
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
    */
    public static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, float[] v, float v_min, float v_max);

    /*[-cpp;-NATIVE]
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
    */
    public static native boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, float[] v, float v_min, float v_max, String format, float power);

    // Widgets: Input with Keyboard
    // - If you want to use InputText() with a dynamic string type such as std::string or your own, see misc/cpp/imgui_stdlib.h
    // - Most of the ImGuiInputTextFlags flags are only useful for InputText() and not for InputFloatX, InputIntX, InputDouble etc.

    /*[-cpp;-NATIVE]
        struct InputTextCallback_UserData {
            jobject* textInputData;
            JNIEnv* env;
            int maxChar;
            char * allowedChar;
            int allowedCharLength;
            int maxSize;
            int curSize;
        };

        static int TextEditCallbackStub(ImGuiInputTextCallbackData* data) {
            InputTextCallback_UserData* userData = (InputTextCallback_UserData*)data->UserData;

            if (data->EventFlag == ImGuiInputTextFlags_CallbackCharFilter) {
                if(userData->allowedCharLength > 0) {
                    bool found = false;
                    for(int i = 0; i < userData->allowedCharLength; i++) {
                        if(userData->allowedChar[i] == data->EventChar) {
                            found = true;
                            break;
                        }
                    }
                    return found ? 0 : 1;
                }
            }
            return 0;
        }
    */

    /*[-cpp;-NATIVE]
        int size = (int)strlen(buff);
        InputTextCallback_UserData cb_user_data;
        cb_user_data.textInputData = &textInputData;
        cb_user_data.env = env;
        cb_user_data.curSize = size;
        cb_user_data.maxSize = maxSize;
        cb_user_data.maxChar = maxChar;
        cb_user_data.allowedChar = allowedChar;
        cb_user_data.allowedCharLength = allowedCharLength;

        char tempArray [maxSize];
        memset(tempArray, 0, sizeof(tempArray));
        memcpy(tempArray, buff, size);
        if(maxChar >= 0 && maxChar < maxSize)
            maxSize = maxChar;
        bool flag = ImGui::InputText(label, tempArray, maxSize, flags  | ImGuiInputTextFlags_CallbackCharFilter, &TextEditCallbackStub, &cb_user_data);
        if(flag) {
            size = (int)strlen(tempArray);
            env->SetIntField (textInputData, imTextInputDataSizeID, size);
            env->SetBooleanField (textInputData, imTextInputDataIsDirtyID, true);
            memset(buff, 0, maxSize);
            memcpy(buff, tempArray, size);
        }
        return flag;
    */
    public static native boolean InputText(String label, byte[] buff, int maxSize, int flags, ImGuiInputTextData textInputData, int maxChar, String allowedChar, int allowedCharLength);

    /*[-cpp;-NATIVE]
        return ImGui::InputFloat(label, &v[0]);
    */
    public static native boolean InputFloat(String label, float[] v);

    /*[-cpp;-NATIVE]
        return ImGui::InputFloat(label, &v[0], step, step_fast, format);
    */
    public static native boolean InputFloat(String label, float[] v, float step, float step_fast, String format);

    /*[-cpp;-NATIVE]
        return ImGui::InputFloat(label, &v[0], step, step_fast, format, flags);
    */
    public static native boolean InputFloat(String label, float[] v, float step, float step_fast, String format, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::InputInt(label, &v[0]);
    */
    public static native boolean InputInt(String label, int[] v);

    /*[-cpp;-NATIVE]
        return ImGui::InputInt(label, &v[0], step, step_fast);
    */
    public static native boolean InputInt(String label, int[] v, float step, float step_fast);

    /*[-cpp;-NATIVE]
        return ImGui::InputInt(label, &v[0], step, step_fast, flags);
    */
    public static native boolean InputInt(String label, int[] v, float step, float step_fast, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::InputDouble(label, &v[0]);
    */
    public static native boolean InputDouble(String label, double[] v);

    /*[-cpp;-NATIVE]
        return ImGui::InputDouble(label, &v[0], step, step_fast, format);
    */
    public static native boolean InputDouble(String label, double[] v, float step, float step_fast, String format);

    /*[-cpp;-NATIVE]
        return ImGui::InputDouble(label, &v[0], step, step_fast, format, flags);
    */
    public static native boolean InputDouble(String label, double[] v, float step, float step_fast, String format, int flags);

    // Widgets: Color Editor/Picker (tip: the ColorEdit* functions have a little color square that can be left-clicked to open a picker, and right-clicked to open an option menu.)
    // - Note that in C++ a 'float v[X]' function argument is the _same_ as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible.
    // - You can pass the address of a first float element out of a contiguous structure, e.g. &myvector.x

    /*[-cpp;-NATIVE]
        return ImGui::ColorEdit3(label, col, flags);
    */
    public static native boolean ColorEdit3(String label, float[] col, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::ColorEdit4(label, col, flags);
    */
    public static native boolean ColorEdit4(String label, float[] col, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::ColorPicker3(label, col, flags);
    */
    public static native boolean ColorPicker3(String label, float[] col, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::ColorPicker4(label, col, flags);
    */
    public static native boolean ColorPicker4(String label, float[] col, int flags);

    // Widgets: Trees
    // - TreeNode functions return true when the node is open, in which case you need to also call TreePop() when you are finished displaying the tree node contents.

    /*[-cpp;-NATIVE]
        return ImGui::TreeNode(label);
    */
    public static native boolean TreeNode(String label);

    /*[-cpp;-NATIVE]
        return ImGui::TreeNode(str_id, label);
    */
    public static native boolean TreeNode(String str_id, String label);

    /*[-cpp;-NATIVE]
        return ImGui::TreeNode((void*)(intptr_t)ptr_id, label);
    */
    public static native boolean TreeNode(int ptr_id, String label);

    /*[-cpp;-NATIVE]
        return ImGui::TreeNodeEx(label);
    */
    public static native boolean TreeNodeEx(String label);

    /*[-cpp;-NATIVE]
        return ImGui::TreeNodeEx(label, flags);
    */
    public static native boolean TreeNodeEx(String label, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::TreeNodeEx(str_id, flags, label);
    */
    public static native boolean TreeNodeEx(String str_id, int flags, String label);

    /*[-cpp;-NATIVE]
        return ImGui::TreeNodeEx((void*)(intptr_t)ptr_id, flags, label);
    */
    public static native boolean TreeNodeEx(int ptr_id, int flags, String label);

    /*[-cpp;-NATIVE]
        ImGui::TreePush(str_id);
    */
    public static native void TreePush(String str_id);

    /*[-cpp;-NATIVE]
        ImGui::TreePush((void*)(intptr_t)ptr_id);
    */
    public static native void TreePush(int ptr_id);

    /*[-cpp;-NATIVE]
        ImGui::TreePop();
    */
    public static native void TreePop();

    /*[-cpp;-NATIVE]
        return ImGui::GetTreeNodeToLabelSpacing();
    */
    public static native float GetTreeNodeToLabelSpacing();

    /*[-cpp;-NATIVE]
        return ImGui::CollapsingHeader(label);
    */
    public static native boolean CollapsingHeader(String label);

    /*[-cpp;-NATIVE]
        return ImGui::CollapsingHeader(label, flags);
    */
    public static native boolean CollapsingHeader(String label, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::CollapsingHeader(label, p_open);
    */
    public static native boolean CollapsingHeader(String label, boolean[] p_open);

    /*[-cpp;-NATIVE]
        return ImGui::CollapsingHeader(label, p_open, flags);
    */
    public static native boolean CollapsingHeader(String label, boolean[] p_open, int flags);

    // Widgets: Selectables
    // - A selectable highlights when hovered, and can display another color when selected.
    // - Neighbors selectable extend their highlight bounds in order to leave no gap between them.

    /*[-cpp;-NATIVE]
        return ImGui::Selectable(label);
    */
    public static native boolean Selectable(String label);

    /*[-cpp;-NATIVE]
        return ImGui::Selectable(label, selected);
    */
    public static native boolean Selectable(String label, boolean selected);

    /*[-cpp;-NATIVE]
        return ImGui::Selectable(label, selected, flags, ImVec2(sizeX, sizeY));
    */
    public static native boolean Selectable(String label, boolean selected, int flags, float sizeX, float sizeY);

    /*[-cpp;-NATIVE]
        return ImGui::Selectable(label, &selected[0]);
    */
    public static native boolean Selectable(String label, boolean[] selected);

    /*[-cpp;-NATIVE]
        return ImGui::Selectable(label,  &selected[0], flags, ImVec2(sizeX, sizeY));
    */
    public static native boolean Selectable(String label, boolean[] selected, int flags, float sizeX, float sizeY);

    // Widgets: List Boxes
    // - FIXME: To be consistent with all the newer API, ListBoxHeader/ListBoxFooter should in reality be called BeginListBox/EndListBox. Will rename them.

    /*[-cpp;-NATIVE]
        const char* listbox_items[items_count];
        for(int i = 0; i < items_count; i++) {
            jstring string = (jstring) (env->GetObjectArrayElement(items, i));
            const char *rawString = env->GetStringUTFChars(string, 0);
            listbox_items[i] = rawString;
        }
        return ImGui::ListBox(label, &current_item[0], listbox_items, items_count);
    */
    public static native boolean ListBox(String label, int[] current_item, String[] items, int items_count);

    /*[-cpp;-NATIVE]
        return ImGui::ListBoxHeader(label);
    */
    public static native boolean ListBoxHeader(String label);

    /*[-cpp;-NATIVE]
        return ImGui::ListBoxHeader(label, ImVec2(sizeX, sizeY));
    */
    public static native boolean ListBoxHeader(String label, float sizeX, float sizeY);

    /*[-cpp;-NATIVE]
        return ImGui::ListBoxHeader(label, items_count);
    */
    public static native boolean ListBoxHeader(String label, int items_count);

    /*[-cpp;-NATIVE]
        return ImGui::ListBoxHeader(label, items_count, height_in_items);
    */
    public static native boolean ListBoxHeader(String label, int items_count, int height_in_items);

    /*[-cpp;-NATIVE]
        ImGui::ListBoxFooter();
    */
    public static native void ListBoxFooter();

    // Widgets: Menus

    /*[-cpp;-NATIVE]
        return ImGui::BeginMainMenuBar();
    */
    public static native boolean BeginMainMenuBar();

    /*[-cpp;-NATIVE]
        ImGui::EndMainMenuBar();
    */
    public static native void EndMainMenuBar();

    /*[-cpp;-NATIVE]
        return ImGui::BeginMenuBar();
    */
    public static native boolean BeginMenuBar();

    /*[-cpp;-NATIVE]
        ImGui::EndMenuBar();
    */
    public static native void EndMenuBar();

    /*[-cpp;-NATIVE]
        return ImGui::BeginMenu(label);
    */
    public static native boolean BeginMenu(String label);

    /*[-cpp;-NATIVE]
        return ImGui::BeginMenu(label, enabled);
    */
    public static native boolean BeginMenu(String label, boolean enabled);

    /*[-cpp;-NATIVE]
        ImGui::EndMenu();
    */
    public static native void EndMenu();

    /*[-cpp;-NATIVE]
        return ImGui::MenuItem(label);
    */
    public static native boolean MenuItem(String label);

    /*[-cpp;-NATIVE]
        return ImGui::MenuItem(label, NULL, selected);
    */
    public static native boolean MenuItem(String label, boolean selected);

    /*[-cpp;-NATIVE]
        return ImGui::MenuItem(label, NULL, selected, enabled);
    */
    public static native boolean MenuItem(String label, boolean selected, boolean enabled);

    /*[-cpp;-NATIVE]
        return ImGui::MenuItem(label, shortcut, selected);
    */
    public static native boolean MenuItem(String label, String shortcut, boolean selected);

    /*[-cpp;-NATIVE]
        return ImGui::MenuItem(label, shortcut, selected, enabled);
    */
    public static native boolean MenuItem(String label, String shortcut, boolean selected, boolean enabled);

    /*[-cpp;-NATIVE]
        return ImGui::MenuItem(label, shortcut, &p_selected[0]);
    */
    public static native boolean MenuItem(String label, String shortcut, boolean[] p_selected);

    /*[-cpp;-NATIVE]
        return ImGui::MenuItem(label, shortcut, &p_selected[0], enabled);
    */
    public static native boolean MenuItem(String label, String shortcut, boolean[] p_selected, boolean enabled);

    // Tooltips

    /*[-cpp;-NATIVE]
        ImGui::BeginTooltip();
    */
    public static native void BeginTooltip();

    /*[-cpp;-NATIVE]
        ImGui::EndTooltip();
    */
    public static native void EndTooltip();

    /*[-cpp;-NATIVE]
        ImGui::SetTooltip(text);
    */
    public static native void SetTooltip(String text);

    // Popups, Modals
    // The properties of popups windows are:
    // - They block normal mouse hovering detection outside them. (*)
    // - Unless modal, they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
    // - Their visibility state (~bool) is held internally by imgui instead of being held by the programmer as we are used to with regular Begin() calls.
    //   User can manipulate the visibility state by calling OpenPopup().
    // (*) One can use IsItemHovered(ImGuiHoveredFlags_AllowWhenBlockedByPopup) to bypass it and detect hovering even when normally blocked by a popup.
    // Those three properties are connected. The library needs to hold their visibility state because it can close popups at any time.

    /*[-cpp;-NATIVE]
        ImGui::OpenPopup(str_id);
    */
    public static native void OpenPopup(String str_id);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopup(str_id);
    */
    public static native boolean BeginPopup(String str_id);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopup(str_id, flags);
    */
    public static native boolean BeginPopup(String str_id, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupContextItem();
    */
    public static native boolean BeginPopupContextItem();

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupContextItem(str_id, mouse_button);
    */
    public static native boolean BeginPopupContextItem(String str_id, int mouse_button);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupContextWindow();
    */
    public static native boolean BeginPopupContextWindow();

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupContextWindow(str_id, flags);
    */
    public static native boolean BeginPopupContextWindow(String str_id, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupContextVoid();
    */
    public static native boolean BeginPopupContextVoid();

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupContextVoid(str_id, mouse_button);
    */
    public static native boolean BeginPopupContextVoid(String str_id, int mouse_button);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupModal(name);
    */
    public static native boolean BeginPopupModal(String name);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupModal(name, NULL, flags);
    */
    public static native boolean BeginPopupModal(String name, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::BeginPopupModal(name, &p_open[0], flags);
    */
    public static native boolean BeginPopupModal(String name, boolean[] p_open, int flags);

    /*[-cpp;-NATIVE]
        ImGui::EndPopup();
    */
    public static native void EndPopup();

    /*[-cpp;-NATIVE]
        ImGui::OpenPopupOnItemClick();
    */
    public static native void OpenPopupOnItemClick();

    /*[-cpp;-NATIVE]
        ImGui::OpenPopupOnItemClick(str_id, mouse_button);
    */
    public static native void OpenPopupOnItemClick(String str_id, int mouse_button);

    /*[-cpp;-NATIVE]
        return ImGui::IsPopupOpen(str_id);
    */
    public static native boolean IsPopupOpen(String str_id);

    /*[-cpp;-NATIVE]
        ImGui::CloseCurrentPopup();
    */
    public static native void CloseCurrentPopup();

    // Tables

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count);
    */
    public static native boolean BeginTable(String id, int columns_count);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count, flags);
    */
    public static native boolean BeginTable(String id, int columns_count, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count, flags, ImVec2(outer_sizeX, outer_sizeY));
    */
    public static native boolean BeginTable(String id, int columns_count, int flags, float outer_sizeX, float outer_sizeY);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count, flags, ImVec2(outer_sizeX, outer_sizeY), inner_width);
    */
    public static native boolean BeginTable(String id, int columns_count, int flags, float outer_sizeX, float outer_sizeY, float inner_width);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count);
    */
    public static native boolean BeginTable(byte[] id, int columns_count);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count, flags);
    */
    public static native boolean BeginTable(byte[] id, int columns_count, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count, flags, ImVec2(outer_sizeX, outer_sizeY));
    */
    public static native boolean BeginTable(byte[] id, int columns_count, int flags, float outer_sizeX, float outer_sizeY);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTable(id, columns_count, flags, ImVec2(outer_sizeX, outer_sizeY), inner_width);
    */
    public static native boolean BeginTable(byte[] id, int columns_count, int flags, float outer_sizeX, float outer_sizeY, float inner_width);

    /*[-cpp;-NATIVE]
        ImGui::EndTable();
    */
    public static native void EndTable();

    /*[-cpp;-NATIVE]
        ImGui::TableNextRow();
    */
    public static native void TableNextRow();

    /*[-cpp;-NATIVE]
        ImGui::TableNextRow(row_flags);
    */
    public static native void TableNextRow(int row_flags);

    /*[-cpp;-NATIVE]
        ImGui::TableNextRow(row_flags, min_row_height);
    */
    public static native void TableNextRow(int row_flags, float min_row_height);

    /*[-cpp;-NATIVE]
        return ImGui::TableNextColumn();
    */
    public static native boolean TableNextColumn();

    /*[-cpp;-NATIVE]
        return ImGui::TableSetColumnIndex(column_n);
    */
    public static native boolean TableSetColumnIndex(int column_n);

    /*[-cpp;-NATIVE]
        return ImGui::TableGetColumnIndex();
    */
    public static native int TableGetColumnIndex();

    /*[-cpp;-NATIVE]
        return ImGui::TableGetRowIndex();
    */
    public static native int TableGetRowIndex();

    // Tables: Headers & Columns declaration

    /*[-cpp;-NATIVE]
        ImGui::TableSetupColumn(label);
    */
    public static native void TableSetupColumn(String label);

    /*[-cpp;-NATIVE]
        ImGui::TableSetupColumn(label, flags);
    */
    public static native void TableSetupColumn(String label, int flags);

    /*[-cpp;-NATIVE]
        ImGui::TableSetupColumn(label, flags);
    */
    public static native void TableSetupColumn(byte[] label, int flags);

    /*[-cpp;-NATIVE]
        ImGui::TableSetupColumn(label, flags, init_width_or_weight);
    */
    public static native void TableSetupColumn(String label, int flags, float init_width_or_weight);

    /*[-cpp;-NATIVE]
        ImGui::TableSetupColumn(label, flags, init_width_or_weight, user_id);
    */
    public static native void TableSetupColumn(String label, int flags, float init_width_or_weight, int user_id);

    /*[-cpp;-NATIVE]
        ImGui::TableHeadersRow();
    */
    public static native void TableHeadersRow();

    /*[-cpp;-NATIVE]
        ImGui::TableHeader(label);
    */
    public static native void TableHeader(String label);

    // Tables: Miscellaneous functions

//TODO Fix return string
//	public static native char[] TableGetColumnName(); /*[-cpp;-NATIVE]
//		return ImGui::TableGetColumnName();
//	*/

    /*[-cpp;-NATIVE]
        return ImGui::TableGetColumnCount();
    */
    public static native int TableGetColumnCount();

    // Tab Bars, Tabs
    // [BETA API] API may evolve!

    /*[-cpp;-NATIVE]
        return ImGui::BeginTabBar(str_id);
    */
    public static native boolean BeginTabBar(String str_id);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTabBar(str_id, flags);
    */
    public static native boolean BeginTabBar(String str_id, int flags);

    /*[-cpp;-NATIVE]
        ImGui::EndTabBar();
    */
    public static native void EndTabBar();

    /*[-cpp;-NATIVE]
        return ImGui::BeginTabItem(label);
    */
    public static native boolean BeginTabItem(String label);

    /*[-cpp;-NATIVE]
        return ImGui::BeginTabItem(label, &p_open[0], flags);
    */
    public static native boolean BeginTabItem(String label, boolean[] p_open, int flags);

    /*[-cpp;-NATIVE]
        ImGui::EndTabItem();
    */
    public static native void EndTabItem();

    /*[-cpp;-NATIVE]
        ImGui::SetTabItemClosed(tab_or_docked_window_label);
    */
    public static native void SetTabItemClosed(String tab_or_docked_window_label);

    // Docking
    // [BETA API] Enable with io.ConfigFlags |= ImGuiConfigFlags_DockingEnable.
    // Note: you DO NOT need to call DockSpace() to use most Docking facilities!
    // To dock windows: if io.ConfigDockingWithShift == false: drag window from their title bar.
    // To dock windows: if io.ConfigDockingWithShift == true: hold SHIFT anywhere while moving windows.
    // Use DockSpace() to create an explicit dock node _within_ an existing window. See Docking demo for details.

    /*[-cpp;-NATIVE]
        return ImGui::DockSpace(id);
    */
    public static native int DockSpace(int id);

    /*[-cpp;-NATIVE]
        return ImGui::DockSpace(id, ImVec2(sizeX, sizeY));
    */
    public static native int DockSpace(int id, float sizeX, float sizeY);

    /*[-cpp;-NATIVE]
        return ImGui::DockSpace(id, ImVec2(sizeX, sizeY), flags);
    */
    public static native int DockSpace(int id, float sizeX, float sizeY, int flags);

    /*[-cpp;-NATIVE]
        return ImGui::DockSpaceOverViewport();
    */
    public static native int DockSpaceOverViewport();

    /*[-cpp;-NATIVE]
        ImGuiViewport tmp = ImGuiViewport();
        ImGuiHelper::SetImGuiViewport(env, viewport, &tmp);
        return ImGui::DockSpaceOverViewport(&tmp, flags);
    */
    public static native int DockSpaceOverViewport(ImGuiViewport viewport, int flags);

    /*[-cpp;-NATIVE]
        ImGui::SetNextWindowDockID(dock_id, cond);
    */
    public static native void SetNextWindowDockID(int dock_id, int cond);

    /*[-cpp;-NATIVE]
        return ImGui::GetWindowDockID();
    */
    public static native int GetWindowDockID();

    /*[-cpp;-NATIVE]
        return ImGui::IsWindowDocked();
    */
    public static native boolean IsWindowDocked();

    // Drag and Drop

    /*[-cpp;-NATIVE]
        return ImGui::BeginDragDropSource(flags);
    */
    public static native boolean BeginDragDropSource(int flags);

    /*[-cpp;-NATIVE]
        char myByte = 0;
        return ImGui::SetDragDropPayload(type, &myByte, 1);
    */
    public static native boolean SetDragDropPayload(String type);

    /*[-cpp;-NATIVE]
        ImGui::EndDragDropSource();
    */
    public static native void EndDragDropSource();

    /*[-cpp;-NATIVE]
        return ImGui::BeginDragDropTarget();
    */
    public static native boolean BeginDragDropTarget();

    /*[-cpp;-NATIVE]
        return ImGui::AcceptDragDropPayload(type, flags) != NULL;
    */
    public static native boolean AcceptDragDropPayload(String type, int flags);

    /*[-cpp;-NATIVE]
        ImGui::EndDragDropTarget();
    */
    public static native void EndDragDropTarget();

    /*[-cpp;-NATIVE]
        return ImGui::GetDragDropPayload()->Data != NULL;
    */
    public static native boolean HasDragDropPayloadData();

    /*[-cpp;-NATIVE]
        ImGui::BeginDisabled(disabled);
    */
    public static native void BeginDisabled(boolean disabled);

    /*[-cpp;-NATIVE]
        ImGui::EndDisabled();
    */
    public static native void EndDisabled();

    // Focus, Activation
    // - Prefer using "SetItemDefaultFocus()" over "if (IsWindowAppearing()) SetScrollHereY()" when applicable to signify "this is the default item"

    /*[-cpp;-NATIVE]
        ImGui::SetItemDefaultFocus();
    */
    public static native void SetItemDefaultFocus();

    /*[-cpp;-NATIVE]
        ImGui::SetKeyboardFocusHere();
    */
    public static native void SetKeyboardFocusHere();

    /*[-cpp;-NATIVE]
        ImGui::SetKeyboardFocusHere(offset);
    */
    public static native void SetKeyboardFocusHere(int offset);

    // Item/Widgets Utilities
    // - Most of the functions are referring to the last/previous item we submitted.
    // - See Demo Window under "Widgets->Querying Status" for an interactive visualization of most of those functions.

    /*[-cpp;-NATIVE]
        return ImGui::IsItemHovered();
    */
    public static native boolean IsItemHovered();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemHovered(flags);
    */
    public static native boolean IsItemHovered(int flags);

    /*[-cpp;-NATIVE]
        return ImGui::IsItemActive();
    */
    public static native boolean IsItemActive();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemFocused();
    */
    public static native boolean IsItemFocused();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemClicked();
    */
    public static native boolean IsItemClicked();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemClicked(mouse_button);
    */
    public static native boolean IsItemClicked(int mouse_button);

    /*[-cpp;-NATIVE]
        return ImGui::IsItemVisible();
    */
    public static native boolean IsItemVisible();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemEdited();
    */
    public static native boolean IsItemEdited();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemActivated();
    */
    public static native boolean IsItemActivated();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemDeactivated();
    */
    public static native boolean IsItemDeactivated();

    /*[-cpp;-NATIVE]
        return ImGui::IsItemDeactivatedAfterEdit();
    */
    public static native boolean IsItemDeactivatedAfterEdit();

    /*[-cpp;-NATIVE]
        return ImGui::IsAnyItemHovered();
    */
    public static native boolean IsAnyItemHovered();

    /*[-cpp;-NATIVE]
        return ImGui::IsAnyItemActive();
    */
    public static native boolean IsAnyItemActive();

    /*[-cpp;-NATIVE]
        return ImGui::IsAnyItemFocused();
    */
    public static native boolean IsAnyItemFocused();

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetItemRectMin();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetItemRectMin(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetItemRectMax();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetItemRectMax(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImVec2 vec = ImGui::GetItemRectSize();
        ImGuiHelper::SetImVec2(env, vec, jImVec2);
    */
    public static native void GetItemRectSize(ImVec2 jImVec2);

    /*[-cpp;-NATIVE]
        ImGui::SetItemAllowOverlap();
    */
    public static native void SetItemAllowOverlap();

    // Viewports
    // - Currently represents the Platform Window created by the application which is hosting our Dear ImGui windows.
    // - In 'docking' branch with multi-viewport enabled, we extend this concept to have multiple active viewports.
    // - In the future we will extend this concept further to also represent Platform Monitor and support a "no main platform window" operation mode.

    /*[-cpp;-NATIVE]
        ImGuiViewport* viewport = ImGui::GetMainViewport();
        ImGuiHelper::SetImGuiViewport(env, viewport, jViewport, updateDrawData);
        return jViewport;
    */
    public static native ImGuiViewport GetMainViewport(boolean updateDrawData);

    // Miscellaneous Utilities

    /*[-cpp;-NATIVE]
        return ImGui::GetFrameCount();
    */
    public static native int GetFrameCount();

    /*[-cpp;-NATIVE]
        return ImGui::BeginChildFrame(id, ImVec2(width, height));
    */
    public static native boolean BeginChildFrame(int id, float width, float height);

    /*[-cpp;-NATIVE]
        return ImGui::BeginChildFrame(id, ImVec2(width, height), flags);
    */
    public static native boolean BeginChildFrame(int id, float width, float height, int flags);

    /*[-cpp;-NATIVE]
        ImGui::EndChildFrame();
    */
    public static native void EndChildFrame();

    // Inputs Utilities

    /*[-cpp;-NATIVE]
        return ImGui::IsMouseDown(button);
    */
    public static native boolean IsMouseDown(int button);

    /*[-cpp;-NATIVE]
        return ImGui::IsMouseClicked(button);
    */
    public static native boolean IsMouseClicked(int button);

    /*[-cpp;-NATIVE]
        bool flag = repeat;
        return ImGui::IsMouseClicked(button, flag);
    */
    public static native boolean IsMouseClicked(int button, boolean repeat);

    /*[-cpp;-NATIVE]
        return ImGui::IsMouseReleased(button);
    */
    public static native boolean IsMouseReleased(int button);

    /*[-cpp;-NATIVE]
        return ImGui::IsMouseDragging(button);
    */
    public static native boolean IsMouseDragging(int button);

    /*[-cpp;-NATIVE]
        return ImGui::IsMouseDragging(button, lock_threshold);
    */
    public static native boolean IsMouseDragging(int button, float lock_threshold);

    /*[-cpp;-NATIVE]
        return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY));
    */
    public static native boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY);

    /*[-cpp;-NATIVE]
        return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY), clip);
    */
    public static native boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY, boolean clip);

    // (Optional) Platform/OS interface for multi-viewport support

    /*[-cpp;-NATIVE]
        ImGui::UpdatePlatformWindows();
    */
    public static native void UpdatePlatformWindows();

    /*[-cpp;-NATIVE]
        ImGui::RenderPlatformWindowsDefault();
    */
    public static native void RenderPlatformWindowsDefault();

    /*[-cpp;-NATIVE]
        ImGui::DestroyPlatformWindows();
    */
    public static native void DestroyPlatformWindows();

    // Custom search because handle is int64 pointer.
    /*[-cpp;-NATIVE]
        int64_t handle = platformHandle;
        ImGuiContext& g = *GImGui;
        for (int i = 0; i != g.Viewports.Size; i++) {
            ImGuiViewport* viewport = g.Viewports[i];
            if(viewport->PlatformHandle != NULL) {
                int64_t viewportHandle = *(int64_t*)viewport->PlatformHandle;
                if (viewportHandle == handle) {
                    ImGuiHelper::SetImGuiViewport(env, viewport, jViewport, updateDrawData);
                    return jViewport;
                }
            }
        }
        return NULL;
    */
    public static native ImGuiViewport FindViewportByPlatformHandle(long platformHandle, boolean updateDrawData);

    // ImGuiIO setters

    /*[-cpp;-NATIVE]
        ImGuiIO& io = ImGui::GetIO();
        io.FontGlobalScale = scale;
    */
    public static native void SetFontGlobalScale(float scale);

    private ImGuiNative() {
    }
}