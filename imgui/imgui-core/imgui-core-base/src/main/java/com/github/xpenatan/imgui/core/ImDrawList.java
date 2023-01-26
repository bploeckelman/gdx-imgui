package com.github.xpenatan.imgui.core;

import com.github.xpenatan.imgui.core.enums.ImDrawCornerFlags;
import com.github.xpenatan.imgui.core.jnicode.ImGuiNative;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImDrawList extends ImGuiBase {

    /*[-C++;-NATIVE]
        #include "imgui.h"
    */

    public final static int VTX_BUFFER_SIZE = 8+8+4;// = ImVec2 + ImVec2 + ImU32;
    public final static int IDX_BUFFER_SIZE = 2; // short
    public final static int cmdBufferSize = (1 + 4 + 1) * 4; // = ImVec4 + ImTextureID + int + int + int
    private static final int RESIZE_FACTOR = 5_000;

    public final static int TYPE_DEFAULT = 0;
    public final static int TYPE_BACKGROUND = 1;
    public final static int TYPE_FOREGROUND = 2;

    private int type = TYPE_DEFAULT;

    public ByteBuffer vtxByteBuffer =  ByteBuffer.allocateDirect(25000).order(ByteOrder.nativeOrder());
    public ByteBuffer idxByteBuffer =  ByteBuffer.allocateDirect(25000).order(ByteOrder.nativeOrder());


    private ImDrawCmd imDrawCmd = new ImDrawCmd(false);

    public ImDrawList(int type) {
        this.type = type;
    }

    public ImDrawList(boolean cMemoryOwn) {
    }

    public int getFlags() {
        return getFlagsNATIVE(getCPointer());
    }

    /*[-teaVM;-NATIVE]
        var nativeObject = ImGui.wrapPointer(addr, ImGui.ImDrawList);
        return nativeObject.get_Flags();
    */
    /*[-C++;-NATIVE]
        ImDrawList* nativeObject = (ImDrawList*)addr;
        return nativeObject->Flags;
    */
    private static native int getFlagsNATIVE(long addr);

    public ImDrawCmd getCmdBuffer(int index) {
        long pointer = getCmdBufferNATIVE(getCPointer(), index);
        imDrawCmd.setPointer(pointer);
        return imDrawCmd;
    }

    /*[-teaVM;-NATIVE]
        var nativeObject = ImGui.wrapPointer(addr, ImGui.ImDrawList);
        var jsObj = nativeObject.get_CmdBuffer.Data[index];
        return ImGui.getPointer(jsObj);
    */
    /*[-C++;-NATIVE]
        ImDrawList* nativeObject = (ImDrawList*)addr;
        return (jlong)&nativeObject->CmdBuffer.Data[index];
    */
    private static native long getCmdBufferNATIVE(long addr, int index);

    public int getCmdBufferSize() {
        return getCmdBufferSizeNATIVE(getCPointer());
    }

    /*[-teaVM;-NATIVE]
        var nativeObject = ImGui.wrapPointer(addr, ImGui.ImDrawList);
        return nativeObject.CmdBuffer.size();
    */
    /*[-C++;-NATIVE]
        ImDrawList* nativeObject = (ImDrawList*)addr;
        return nativeObject->CmdBuffer.size();
    */
    private static native int getCmdBufferSizeNATIVE(long addr);

    public ByteBuffer getIdxBufferData() {
        int vtxBufferSize = getIdxBufferSizeNATIVE(getCPointer());
        int vtxBufferCapacity = vtxBufferSize * IDX_BUFFER_SIZE;
        if (idxByteBuffer.capacity() < vtxBufferCapacity) {
            idxByteBuffer.clear();
            idxByteBuffer = ByteBuffer.allocateDirect(vtxBufferCapacity + RESIZE_FACTOR).order(ByteOrder.nativeOrder());
        }
        getIdxBufferDataNATIVE(getCPointer(), idxByteBuffer, vtxBufferCapacity);
        idxByteBuffer.position(0);
        idxByteBuffer.limit(vtxBufferCapacity);
        return idxByteBuffer;
    }

    /*[-teaVM;-NATIVE]
        var nativeObject = ImGui.wrapPointer(addr, ImGui.ImDrawList);
    */
    /*[-C++;-NATIVE]
        ImDrawList* nativeObject = (ImDrawList*)addr;
        memcpy(buffer, nativeObject->IdxBuffer.Data, bufferCapacity);
    */
    private static native void getIdxBufferDataNATIVE(long addr, ByteBuffer buffer, int bufferCapacity);

    /*[-teaVM;-NATIVE]
        var nativeObject = ImGui.wrapPointer(addr, ImGui.ImDrawList);
        return nativeObject.IdxBuffer.size();
    */
    /*[-C++;-NATIVE]
        ImDrawList* nativeObject = (ImDrawList*)addr;
        return nativeObject->IdxBuffer.size();
    */
    private static native int getIdxBufferSizeNATIVE(long addr);

    public ByteBuffer getVtxBufferData() {
        int vtxBufferSize = getVtxBufferSizeNATIVE(getCPointer());
        int vtxBufferCapacity = vtxBufferSize * VTX_BUFFER_SIZE;
        if (vtxByteBuffer.capacity() < vtxBufferCapacity) {
            vtxByteBuffer.clear();
            vtxByteBuffer = ByteBuffer.allocateDirect(vtxBufferCapacity + RESIZE_FACTOR).order(ByteOrder.nativeOrder());
        }
        getVtxBufferDataNATIVE(getCPointer(), vtxByteBuffer, vtxBufferCapacity);
        vtxByteBuffer.position(0);
        vtxByteBuffer.limit(vtxBufferCapacity);
        return vtxByteBuffer;
    }

    /*[-teaVM;-NATIVE]
        var nativeObject = ImGui.wrapPointer(addr, ImGui.ImDrawList);
    */
    /*[-C++;-NATIVE]
        ImDrawList* nativeObject = (ImDrawList*)addr;
        memcpy(buffer, nativeObject->VtxBuffer.Data, bufferCapacity);
    */
    private static native void getVtxBufferDataNATIVE(long addr, ByteBuffer buffer, int bufferCapacity);

    /*[-teaVM;-NATIVE]
        var nativeObject = ImGui.wrapPointer(addr, ImGui.ImDrawList);
        return nativeObject.VtxBuffer.size();
    */
    /*[-C++;-NATIVE]
        ImDrawList* nativeObject = (ImDrawList*)addr;
        return nativeObject->VtxBuffer.size();
    */
    private static native int getVtxBufferSizeNATIVE(long addr);

    /*[-C++;-NATIVE]
        return (int)sizeof(ImDrawIdx);
    */
    private static native int getDrawVertSizeNATIVE();

    public void AddLine(float a_x, float a_y, float b_x, float b_y, int col) {
        ImGuiNative.AddLine(type, a_x, a_y, b_x, b_y, col);
    }

    public void AddLine(float a_x, float a_y, float b_x, float b_y, int col, float thinkness) {
        ImGuiNative.AddLine(type, a_x, a_y, b_x, b_y, col, thinkness);
    }

    public void AddRect(float a_x, float a_y, float b_x, float b_y, int col) {
        ImGuiNative.AddRect(type, a_x, a_y, b_x, b_y, col);
    }

    public void AddRect(float a_x, float a_y, float b_x, float b_y, int col, float rounding) {
        ImGuiNative.AddRect(type, a_x, a_y, b_x, b_y, col, rounding);
    }

    public void AddRect(float a_x, float a_y, float b_x, float b_y, int col, float rounding, ImDrawCornerFlags rounding_corners_flags) {
        ImGuiNative.AddRect(type, a_x, a_y, b_x, b_y, col, rounding, rounding_corners_flags.getValue());
    }

    public void AddRect(float a_x, float a_y, float b_x, float b_y, int col, float rounding, ImDrawCornerFlags rounding_corners_flags, float thickness) {
        ImGuiNative.AddRect(type, a_x, a_y, b_x, b_y, col, rounding, rounding_corners_flags.getValue(), thickness);
    }

    public void AddRectFilled(float a_x, float a_y, float b_x, float b_y, int col) {
        ImGuiNative.AddRectFilled(type, a_x, a_y, b_x, b_y, col);
    }

    public void AddRectFilled(float a_x, float a_y, float b_x, float b_y, int col, float rounding) {
        ImGuiNative.AddRectFilled(type, a_x, a_y, b_x, b_y, col, rounding);
    }

    public void AddRectFilled(float a_x, float a_y, float b_x, float b_y, int col, float rounding, ImDrawCornerFlags rounding_corners_flags) {
        ImGuiNative.AddRectFilled(type, a_x, a_y, b_x, b_y, col, rounding, rounding_corners_flags.getValue());
    }

    public void AddRectFilledMultiColor(float a_x, float a_y, float b_x, float b_y, int col_upr_left, float col_upr_right, int col_bot_right, int col_bot_left) {
        ImGuiNative.AddRectFilledMultiColor(type, a_x, a_y, b_x, b_y, col_upr_left, col_upr_right, col_bot_right, col_bot_left);
    }

    public void AddQuad(float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col) {
        ImGuiNative.AddQuad(type, a_x, a_y, b_x, b_y, c_x, c_y, d_x, d_y, col);
    }

    public void AddQuad(float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col, float thickness) {
        ImGuiNative.AddQuad(type, a_x, a_y, b_x, b_y, c_x, c_y, d_x, d_y, col, thickness);
    }

    public void AddQuadFilled(float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, float d_x, float d_y, int col) {
        ImGuiNative.AddQuadFilled(type, a_x, a_y, b_x, b_y, c_x, c_y, d_x, d_y, col);
    }

    public void AddTriangle(float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col) {
        ImGuiNative.AddTriangle(type, a_x, a_y, b_x, b_y, c_x, c_y, col);
    }

    public void AddTriangle(float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col, float thickness) {
        ImGuiNative.AddTriangle(type, a_x, a_y, b_x, b_y, c_x, c_y, col, thickness);
    }

    public void AddTriangleFilled(float a_x, float a_y, float b_x, float b_y, float c_x, float c_y, int col) {
        ImGuiNative.AddTriangleFilled(type, a_x, a_y, b_x, b_y, c_x, c_y, col);
    }

    public void AddCircle(float centre_x, float centre_y, float radius, float col, int num_segments, float thickness) {
        ImGuiNative.AddCircle(type, centre_x, centre_y, radius, col, num_segments, thickness);
    }

    public void AddCircleFilled(float centre_x, float centre_y, float radius, float col) {
        ImGuiNative.AddCircleFilled(type, centre_x, centre_y, radius, col);
    }

    public void AddCircleFilled(float centre_x, float centre_y, float radius, float col, int num_segments) {
        ImGuiNative.AddCircleFilled(type, centre_x, centre_y, radius, col, num_segments);
    }

    public void AddText(float pos_x, float pos_y, int col, String text_begin) {
        ImGuiNative.AddText(type, pos_x, pos_y, col, text_begin);
    }

    public void AddText(float pos_x, float pos_y, int col, String text_begin, String text_end) {
        ImGuiNative.AddText(type, pos_x, pos_y, col, text_begin, text_end);
    }

//TODO	IMGUI_API void  AddText(const ImFont* font, float font_size, const ImVec2& pos, ImU32 col, const char* text_begin, const char* text_end = NULL, float wrap_width = 0.0f, const ImVec4* cpu_fine_clip_rect = NULL);
//TODO	IMGUI_API void  AddPolyline(const ImVec2* points, int num_points, ImU32 col, ImDrawFlags flags, float thickness);
//TODO	IMGUI_API void  AddConvexPolyFilled(const ImVec2* points, int num_points, ImU32 col); // Note: Anti-aliased filling requires points to be in clockwise order.
//TODO	IMGUI_API void  AddBezierCubic(const ImVec2& p1, const ImVec2& p2, const ImVec2& p3, const ImVec2& p4, ImU32 col, float thickness, int num_segments = 0); // Cubic Bezier (4 control points)
//TODO	IMGUI_API void  AddBezierQuadratic(const ImVec2& p1, const ImVec2& p2, const ImVec2& p3, ImU32 col, float thickness, int num_segments = 0);               // Quadratic Bezier (3 control points)

    public void AddImage(int textureID, float a_x, float a_y, float b_x, float b_y) {
        ImGuiNative.AddImage(type, textureID, a_x, a_y, b_x, b_y);
    }

    public void AddImage(int textureID, float a_x, float a_y, float b_x, float b_y, float uv_a_x, float uv_a_y, float uv_b_x, float uv_b_y) {
        ImGuiNative.AddImage(type, textureID, a_x, a_y, b_x, b_y, uv_a_x, uv_a_y, uv_b_x, uv_b_y);
    }

//TODO	IMGUI_API void  AddImageQuad(ImTextureID user_texture_id, const ImVec2& p1, const ImVec2& p2, const ImVec2& p3, const ImVec2& p4, const ImVec2& uv1 = ImVec2(0, 0), const ImVec2& uv2 = ImVec2(1, 0), const ImVec2& uv3 = ImVec2(1, 1), const ImVec2& uv4 = ImVec2(0, 1), ImU32 col = IM_COL32_WHITE);
//TODO	IMGUI_API void  AddImageRounded(ImTextureID user_texture_id, const ImVec2& p_min, const ImVec2& p_max, const ImVec2& uv_min, const ImVec2& uv_max, ImU32 col, float rounding, ImDrawFlags flags = 0);

    // Stateful path API, add points then finish with PathFillConvex() or PathStroke()

    public void PathClear() {
        ImGuiNative.PathClear(type);
    }

    public void PathLineTo(float pos_x, float pos_y) {
        ImGuiNative.PathLineTo(type, pos_x, pos_y);
    }

//TODO	inline    void  PathLineToMergeDuplicate(const ImVec2& pos)                 { if (_Path.Size == 0 || memcmp(&_Path.Data[_Path.Size - 1], &pos, 8) != 0) _Path.push_back(pos); }
//TODO	inline    void  PathFillConvex(ImU32 col)                                   { AddConvexPolyFilled(_Path.Data, _Path.Size, col); _Path.Size = 0; }  // Note: Anti-aliased filling requires points to be in clockwise order.

    public void PathStroke(int col) {
        ImGuiNative.PathStroke(type, col);
    }

    public void PathStroke(int col, int flags) {
        ImGuiNative.PathStroke(type, col, flags);
    }

    public void PathStroke(int col, int flags, float thickness) {
        ImGuiNative.PathStroke(type, col, flags, thickness);
    }

//TODO	IMGUI_API void  PathArcTo(const ImVec2& center, float radius, float a_min, float a_max, int num_segments = 0);
//	IMGUI_API void  PathArcToFast(const ImVec2& center, float radius, int a_min_of_12, int a_max_of_12);                // Use precomputed angles for a 12 steps circle
//TODO	IMGUI_API void  PathBezierCubicCurveTo(const ImVec2& p2, const ImVec2& p3, const ImVec2& p4, int num_segments = 0); // Cubic Bezier (4 control points)
//TODO	IMGUI_API void  PathBezierQuadraticCurveTo(const ImVec2& p2, const ImVec2& p3, int num_segments = 0);               // Quadratic Bezier (3 control points)
//TODO	IMGUI_API void  PathRect(const ImVec2& rect_min, const ImVec2& rect_max, float rounding = 0.0f, ImDrawFlags flags = 0);

    public void ChannelsSplit(int count) {
        ImGuiNative.ChannelsSplit(type, count);
    }

    public void ChannelsMerge() {
        ImGuiNative.ChannelsMerge(type);
    }

    public void ChannelsSetCurrent(int n) {
        ImGuiNative.ChannelsSetCurrent(type, n);
    }
}
