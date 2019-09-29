#include "imgui.h"
#include "imgui_internal.h"
#include "imgui_layout_widget.h"

static ImVector<ImGuiLayout*> layoutStack;
static ImVector<ImGuiAlign *> alignStack;

static ImGuiLayout* createOrFind(const char* id) {
    ImGuiContext& g = *GImGui;
    ImGuiLayout* childLayout = NULL;
    ImGuiID hashID = ImHashStr(id);
    childLayout = (ImGuiLayout*)g.WindowsById.GetVoidPtr(hashID);
    if (childLayout == NULL) {
        childLayout = IM_NEW(ImGuiLayout)(id);
        g.WindowsById.SetVoidPtr(childLayout->id, childLayout);
    }
    return childLayout;
}

static ImGuiLayout* pushLayout(const char * id) {
    ImGuiLayout* parentLayout = NULL;
    if(!layoutStack.empty())
        parentLayout = layoutStack.back();
    ImGuiLayout* childLayout = createOrFind(id);
    childLayout->parentLayout = parentLayout;
    layoutStack.push_back(childLayout);
    return childLayout;
}

ImGuiLayout* ImGui::GetCurrentLayout() {
    if (layoutStack.empty())
        return NULL;
    return layoutStack.back();
}

static void popLayout() {
    layoutStack.pop_back();
}

static ImGuiAlign* createOrFindAlign(const char* id) {
    ImGuiContext& g = *GImGui;
    ImGuiAlign* childAlign = NULL;
    ImGuiID hashID = ImHashStr(id);
    childAlign = (ImGuiAlign*)g.WindowsById.GetVoidPtr(hashID);
    if (childAlign == NULL) {
        childAlign = IM_NEW(ImGuiAlign)(id);
        g.WindowsById.SetVoidPtr(childAlign->id, childAlign);
    }
    return childAlign;
}

static ImGuiAlign* pushAlign(const char* id) {
    ImGuiAlign* parentAlign = NULL;
    if (!alignStack.empty())
        parentAlign = alignStack.back();
    ImGuiAlign* childAlign = createOrFindAlign(id);
    childAlign->parentAlign = parentAlign;
    alignStack.push_back(childAlign);
    return childAlign;
}

ImGuiAlign* ImGui::GetCurrentAlign() {
    if (alignStack.empty())
        return NULL;
    return alignStack.back();
}

static void popAlgin() {
    alignStack.pop_back();
}


void ImGui::ShowLayoutDebug() {
    ImGuiLayout* curLayout = GetCurrentLayout();
    if (curLayout != NULL) {
        curLayout->debug = true;
    }
}

void ImGui::ShowAlignDebug() {
    ImGuiAlign* curAlign = GetCurrentAlign();
    if (curAlign != NULL) {
        curAlign->debug = true;
    }
}

bool ImGui::BeginLayout(const char* strID, float sizeX, float sizeY, float paddingLeft, float paddingRight, float paddingTop, float paddingBottom)
{
    ImGuiContext& g = *GImGui;
    ImGuiWindow* window = g.CurrentWindow;
    ImGuiLayout* parentLayout = GetCurrentLayout();
    ImGuiID id = ImHashStr(strID);
    char title[256];
    if (parentLayout)
        ImFormatString(title, IM_ARRAYSIZE(title), "%s/%s_%08X", parentLayout->idStr, strID, id);
    else
        ImFormatString(title, IM_ARRAYSIZE(title), "%s/%08X", strID, id);

    ImGuiLayout* curLayout = pushLayout(title);
    bool ret = true;
    // Update layout

    // Backup windows data
    curLayout->DC = window->DC;
    curLayout->WorkRect = window->WorkRect;
    curLayout->skipping = window->SkipItems;
    curLayout->AutoFitChildAxises = window->AutoFitChildAxises;
    curLayout->Pos = window->Pos;
    curLayout->ContentsRegionRect = window->ContentsRegionRect;
    // ******** End Backup windows data

    curLayout->sizeParam.x = sizeX;
    curLayout->sizeParam.y = sizeY;

    curLayout->paddingLeft = paddingLeft;
    curLayout->paddingRight = paddingRight;
    curLayout->paddingTop = paddingTop;
    curLayout->paddingBottom = paddingBottom;


    curLayout->position = window->DC.CursorPos;

    const ImVec2 content_avail = GetContentRegionAvail();

    ImVec2 sizeItem = ImFloor(curLayout->sizeParam);

    if (curLayout->sizeParam.x < 0.0f) {
        float sizeX = ImMax(content_avail.x + sizeItem.x, 4.0f);
        curLayout->size.x = sizeX;
    }
    if (curLayout->sizeParam.y < 0.0f) {
        float sizeY = ImMax(content_avail.y + sizeItem.y, 4.0f);
        curLayout->size.y = sizeY;
    }

    // ***** End Update Layout

    // Write to window object

    ImVec2 contentPosition = curLayout->getPositionPadding();

    window->Pos.x = contentPosition.x;
    window->Pos.y = contentPosition.y;
    window->DC.Indent.x = 0;

    window->DC.CursorMaxPos.x = contentPosition.x + curLayout->size.x - curLayout->paddingLeft - curLayout->paddingRight;
    window->DC.CursorMaxPos.y = contentPosition.y + curLayout->size.y - curLayout->paddingTop - curLayout->paddingBottom;

    window->DC.CursorStartPos.x = contentPosition.x;
    window->DC.CursorStartPos.y = contentPosition.y;

    window->DC.CurrLineSize.y = 0; // necessary to keep position inside layout
    window->DC.CurrLineTextBaseOffset = 0;
  
    window->DC.CursorPos.x = contentPosition.x;
    window->DC.CursorPos.y = contentPosition.y;

    window->WorkRect.Min.x = contentPosition.x;
    window->WorkRect.Min.y = contentPosition.y;
    window->WorkRect.Max.x = contentPosition.x + curLayout->size.x - curLayout->paddingLeft - curLayout->paddingRight;
    window->WorkRect.Max.y = contentPosition.y + curLayout->size.y - curLayout->paddingTop - curLayout->paddingBottom;

    window->ContentsRegionRect.Min.x = contentPosition.x;
    window->ContentsRegionRect.Min.y = contentPosition.y;
    window->ContentsRegionRect.Max.x = contentPosition.x + curLayout->size.x - curLayout->paddingLeft - curLayout->paddingRight;
    window->ContentsRegionRect.Max.y = contentPosition.y + curLayout->size.y - curLayout->paddingTop - curLayout->paddingBottom;

    // ***** End Write to window object

    if (curLayout->clipping)
        ImGui::PushClipRect(curLayout->getPositionPadding(), curLayout->getAbsoluteSizePadding(), true);

    bool skip_items = false;
    if (window->Collapsed || !window->Active || window->Hidden)
       /* if (window->AutoFitFramesX <= 0 && window->AutoFitFramesY <= 0 && window->HiddenFramesCannotSkipItems <= 0)*/
            skip_items = true;
    window->SkipItems = skip_items;
    ret = !skip_items;
    return true;
};

void ImGui::EndLayout()
{
    ImGuiContext& g = *GImGui;
    ImGuiWindow* window = g.CurrentWindow;

    ImGuiLayout* curLayout = GetCurrentLayout();

    if (curLayout->clipping)
        ImGui::PopClipRect();

    float x = window->DC.CursorPos.x;
    float y = window->DC.CursorPos.y;

    curLayout->sizeContents.x = window->DC.CursorMaxPos.x - x;
    curLayout->sizeContents.y = y - curLayout->position.y - g.Style.ItemSpacing.y;

    // Restore windows data
    window->DC = curLayout->DC;
    window->WorkRect = curLayout->WorkRect;
    window->SkipItems = curLayout->skipping;
    window->AutoFitChildAxises = curLayout->AutoFitChildAxises;
    window->Pos = curLayout->Pos;
    window->ContentsRegionRect = curLayout->ContentsRegionRect;
    // ********************

    //const ImVec2 content_avail = GetContentRegionAvail();
    ImVec2 sizeItem = curLayout->sizeParam;

    curLayout->error = false;

    if (sizeItem.x < 0.0f) {
        sizeItem.x = curLayout->size.x;
        if (curLayout->size.x < curLayout->sizeContents.x || curLayout->size.x > curLayout->sizeContents.x) {
            //special case where dev used MATCH_CONTENT and WRAP_CONTENT wrong. The parent have a WRAP_CONTENT y size and the child have MATCH_CONTENT y size.
            /* curLayout->error = true;
            sizeItem.x = 10;*/
        }
    }
    else if (sizeItem.x == 0.0f)
        sizeItem.x = curLayout->sizeContents.x + curLayout->paddingLeft + curLayout->paddingRight;

    if (sizeItem.y < 0.0f) {
        sizeItem.y = curLayout->size.y;
        if (curLayout->size.y < curLayout->sizeContents.y) {
            //special case where dev used MATCH_CONTENT and WRAP_CONTENT wrong. The parent have a WRAP_CONTENT y size and the child have MATCH_CONTENT y size.
               //curLayout->error = true;
            //sizeItem.y = curLayout->sizeContents.y;
        }
    }
    else if (sizeItem.y == 0.0f)
        sizeItem.y = curLayout->sizeContents.y + curLayout->paddingBottom;

    if (curLayout->error) {
        curLayout->size.x = ImMax(curLayout->size.x, 14.0f);
        curLayout->size.y = ImMax(curLayout->size.y, 14.0f);
    }

    curLayout->size = sizeItem;

    ImGui::ItemSize(sizeItem);

    if (curLayout->debug) {
        curLayout->drawContentDebug();
        curLayout->drawPaddingAreaDebug();
        curLayout->drawSizeDebug();
        curLayout->debug = false;
    }

    if(curLayout->error)
        curLayout->drawError();

    popLayout();
};

static bool renderFrameArrow(bool* value, int arrowColor, int arrowBackgroundHoveredColor, int arrowBackgroundClickedColor)
{
    ImGuiContext& g = *GImGui;
    ImGuiWindow* window = g.CurrentWindow;

    ImDrawList* drawList = ImGui::GetWindowDrawList();
    float getFrameHeight = ImGui::GetFrameHeight();
    float removedSize = 5.5f; // reduce few pixels
    float halfSize = (getFrameHeight) / 2.0f - removedSize;

    ImVec2 vec = ImGui::GetCursorScreenPos();

    float screenPosX = vec.x;
    float screenPosY = vec.y;
    float arrowPaddingLeft = 6;

    float x = screenPosX + halfSize + arrowPaddingLeft;
    float y = screenPosY + getFrameHeight / 2.0f;

    bool hovered = ImGui::IsMouseHoveringRect(ImVec2(x - halfSize - removedSize, y - halfSize - removedSize), ImVec2(x + halfSize + removedSize, y + halfSize + removedSize));
    ImU32 hoveredColor = arrowBackgroundHoveredColor;
    bool isWindowHovered = ImGui::IsWindowHovered();

    if (isWindowHovered) {
        if (hovered) {
            if (ImGui::IsMouseDown(0))
                hoveredColor = arrowBackgroundClickedColor;
            if (ImGui::IsMouseReleased(0))
                * value = !*value;
        }

        if (hovered)
            drawList->AddCircleFilled(ImVec2(x, y), halfSize * 2, hoveredColor);
    }

    float triA_X = 0;
    float triA_Y = 0;
    float triB_X = 0;
    float triB_Y = 0;
    float triC_X = 0;
    float triC_Y = 0;

    if (*value) {
        // arrow down
        float offset = -0.5f;
        triA_X = x - halfSize + offset;
        triA_Y = y - halfSize;
        triB_X = x + halfSize + offset;
        triB_Y = y - halfSize;
        triC_X = x + offset;
        triC_Y = y + halfSize;
    }
    else {
        // arrow right
        triA_X = x - halfSize;
        triA_Y = y - halfSize;
        triB_X = x + halfSize;
        triB_Y = y;
        triC_X = x - halfSize;
        triC_Y = y + halfSize;
    }

    drawList->AddTriangleFilled(ImVec2(triA_X, triA_Y), ImVec2(triB_X, triB_Y), ImVec2(triC_X, triC_Y), arrowColor);

    float bk = g.Style.ItemSpacing.y;
    g.Style.ItemSpacing.y = 0;
    ImGui::ItemSize(ImVec2(halfSize * 2 + 3, getFrameHeight));
    g.Style.ItemSpacing.y = bk;

    return *value;
}


void ImGui::BeginCollapseLayoutEx(bool* isOpen, const char* title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options)
{
      ImGuiContext& g = *GImGui;
      ImGuiWindow* window = g.CurrentWindow;
      ImDrawList* drawList = window->DrawList;

      float frameHeight = ImGui::GetFrameHeight();

      sizeY = *isOpen ? sizeY : ImLayout::WRAP_PARENT;

      ImGui::BeginLayout(title, sizeX, sizeY, 1, 1, 1, 1);
      ImGuiLayout* rootLayout = GetCurrentLayout();

	  rootLayout->map.SetFloat(120, options.paddingLeft);
	  rootLayout->map.SetFloat(121, options.paddingRight);
	  rootLayout->map.SetFloat(122, options.paddingTop);
	  rootLayout->map.SetFloat(123, options.paddingBottom);

      ImGui::BeginLayout("frame", ImLayout::MATCH_PARENT, frameHeight, 0, 0, 0, 0);
      ImGuiLayout* frameLayout = GetCurrentLayout();

      ImVec2 mousePos = ImGui::GetMousePos();

      drawList->AddRectFilled(rootLayout->position, ImVec2(rootLayout->getAbsoluteSize().x, frameLayout->getAbsoluteSize().y), options.frameColor, options.borderRound, options.roundingCorners);

      renderFrameArrow(isOpen, options.arrowColor, options.arrowBackgroundHoveredColor, options.arrowBackgroundClickedColor);

      ImGui::SameLine();

      ImGui::BeginAlign("align", ImLayout::WRAP_PARENT, ImLayout::MATCH_PARENT, -1, 0.5f, -1, 0.5f, 0, 0);

      ImGui::Text(title);

      ImGui::EndAlign();
	  
	  ImGui::SameLine();
}

void ImGui::BeginCollapseLayout(bool* isOpen, const char* title, float sizeX, float sizeY, ImGuiCollapseLayoutOptions options)
{
	ImGui::BeginCollapseLayoutEx(isOpen, title, sizeX, sizeY, options);
	ImGui::EndCollapseFrameLayout();
}

void ImGui::EndCollapseFrameLayout()
{
    ImGuiContext& g = *GImGui;
    float bk = g.Style.ItemSpacing.y;

    g.Style.ItemSpacing.y = 0;
    ImGui::EndLayout(); // end frame
    g.Style.ItemSpacing.y = bk;
	ImGuiLayout* rootLayout = GetCurrentLayout();
	
	float paddingLeft = rootLayout->map.GetFloat(120, 0.0f);
	float paddingRight = rootLayout->map.GetFloat(121, 0.0f);
	float paddingTop = rootLayout->map.GetFloat(122, 0.0f);
	float paddingBottom = rootLayout->map.GetFloat(123, 0.0f);

    ImGui::BeginLayout("content", ImLayout::MATCH_PARENT, ImLayout::WRAP_PARENT, paddingLeft, paddingRight, paddingTop, paddingBottom);
    ImGuiLayout* collapseLayout = GetCurrentLayout();
}

void ImGui::EndCollapseLayout()
{
    ImGuiContext& g = *GImGui;
    ImGuiWindow* window = g.CurrentWindow;
    ImDrawList* drawList = window->DrawList;

    ImU32 borderColor = ImGui::GetColorU32(ImVec4(0x40 / 255.0f, 0x40 / 255.0f, 0x49 / 255.0f, 255 / 255.0f));
    int borderRound = 4;
    int roundingCorners = ImDrawCornerFlags_TopLeft | ImDrawCornerFlags_TopRight;

    ImGui::EndLayout(); // end content

    ImGuiLayout* rootLayout = GetCurrentLayout();

    ImGui::EndLayout(); // end root

    ImVec2 borderPosition = rootLayout->position;
    ImVec2 borderSize = rootLayout->getAbsoluteSize();

    drawList->AddRect(borderPosition, borderSize, borderColor, borderRound, roundingCorners, 1.0f);
};


void ImGui::BeginAlign(const char* strID, float sizeX, float sizeY, float alignX, float alignY, float contentAlignX, float contentAlignY, float paddingX, float paddingY) {
    ImGuiContext& g = *GImGui;
    ImGuiWindow* window = g.CurrentWindow;

    ImGuiLayout* curLayout = GetCurrentLayout();

    ImGuiAlign* parentAlign = GetCurrentAlign();
    ImGuiID id = ImHashStr(strID);
    char title[256];
    if (parentAlign)
        ImFormatString(title, IM_ARRAYSIZE(title), "%s/%s_%08X", parentAlign->idStr, strID, id);
    else
        ImFormatString(title, IM_ARRAYSIZE(title), "%s/%08X", strID, id);

    ImGuiAlign* curAlign = pushAlign(title);

    // Backup windows data
    curAlign->DC = window->DC;
    curAlign->WorkRect = window->WorkRect;
    curAlign->Pos = window->Pos;
    curAlign->ContentsRegionRect = window->ContentsRegionRect;
    // ******** End Backup windows data

    curAlign->sizeParam.x = sizeX;
    curAlign->sizeParam.y = sizeY;
    curAlign->position = window->DC.CursorPos;
    curAlign->positionContents = window->DC.CursorPos;

    ImVec2 regionAvail = ImGui::GetContentRegionAvail();
    ImVec2 contentPosition = curAlign->position;
    float totalX = regionAvail.x;
    float totalY = regionAvail.y;
    if (sizeX > 0.0f)
        totalX = sizeX;
    if (sizeY > 0.0f)
        totalY = sizeY;

    curAlign->size.x = totalX;
    curAlign->size.y = totalY;

    if (alignX >= 0.0f && curAlign->sizeParam.x != ImLayout::WRAP_PARENT) {
        
        float addX = ImFloor(totalX * alignX);
        float contentAddX = ImFloor(curAlign->sizeContents.x * contentAlignX);
        float newX = curAlign->position.x + addX - contentAddX + paddingX;
        contentPosition.x = newX;
        curAlign->positionContents.x = newX;
    }

    if (alignY >= 0.0f && curAlign->sizeParam.y != ImLayout::WRAP_PARENT) {
        float addY = ImFloor(totalY * alignY);
        float contentAddY = ImFloor(curAlign->sizeContents.y * contentAlignY);
        float newY = curAlign->position.y + addY - contentAddY + paddingY;
        contentPosition.y = newY;
        curAlign->positionContents.y = newY;
    }
    window->DC.CursorMaxPos.x = contentPosition.x;
    window->DC.CursorMaxPos.y = contentPosition.y;

    window->Pos.x = contentPosition.x;
    window->Pos.y = contentPosition.y;
    window->DC.Indent.x = 0;

    window->DC.CursorStartPos.x = contentPosition.x;
    window->DC.CursorStartPos.y = contentPosition.y;

    window->DC.CurrLineSize.x = 0;
    window->DC.CurrLineSize.y = 0;
    window->DC.CurrLineTextBaseOffset = 0;

    window->DC.CursorPos.x = contentPosition.x;
    window->DC.CursorPos.y = contentPosition.y;

    window->WorkRect.Min.x = contentPosition.x;
    window->WorkRect.Min.y = contentPosition.y;

    window->ContentsRegionRect.Min.x = contentPosition.x;
    window->ContentsRegionRect.Min.y = contentPosition.y;

	if (curAlign->clipping)
		ImGui::PushClipRect(curAlign->position, ImVec2(curAlign->position.x + curAlign->size.x, curAlign->position.y + curAlign->size.y), true);
}

void ImGui::EndAlign() {
    ImGuiContext& g = *GImGui;
    ImGuiWindow* window = g.CurrentWindow;
     
    ImGuiAlign* curAlign = GetCurrentAlign();


	if (curAlign->clipping)
		ImGui::PopClipRect();

    float x = window->DC.CursorPos.x;
    float y = window->DC.CursorPos.y;

    curAlign->sizeContents.x = window->DC.CursorMaxPos.x - curAlign->positionContents.x;
    curAlign->sizeContents.y = y - curAlign->positionContents.y - g.Style.ItemSpacing.y / 2.0f ;

    if (curAlign->sizeParam.x == ImLayout::WRAP_PARENT)
        curAlign->size.x = curAlign->sizeContents.x;
    if (curAlign->sizeParam.y == ImLayout::WRAP_PARENT)
        curAlign->size.y = curAlign->sizeContents.y;

    if (curAlign->debug) {
        curAlign->drawContentDebug();
        curAlign->drawSizeDebug();
        curAlign->debug = false;
    }

    // Restore windows data
    window->DC = curAlign->DC;
    window->WorkRect = curAlign->WorkRect;
    window->Pos = curAlign->Pos;
    window->ContentsRegionRect = curAlign->ContentsRegionRect;
    // ********************

    ImGui::ItemSize(curAlign->size);


    popAlgin();
}
