package imgui.example.nodeeditor.blueprintv2;

import com.badlogic.gdx.graphics.Color;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.example.nodeeditor.demo.BlueprintV2Example;

import java.util.Objects;
import java.util.stream.Collectors;

import static imgui.ImGuiCol.ImGuiCol_HeaderActive;
import static imgui.ImGuiSelectableFlags.ImGuiSelectableFlags_SpanAllColumns;
import static imgui.ImGuiWindowFlags.*;

public class EditorInfoPane {

    private static final String LIB_REPO = "thedmd/imgui-node-editor";
    private static final String LIB_EXAMPLES_URL = "https://github.com/thedmd/imgui-node-editor/tree/master/examples";

    private final BlueprintV2Example editor;
    private final EditorSession session;

    private int selectionChangeCount;

    public EditorInfoPane(BlueprintV2Example editor) {
        this.editor = editor;
        this.session = editor.session;
    }

    public void update(float delta) {
        if (session.hasSelectionChanged()) {
            selectionChangeCount++;
        }
    }

    public void render() {
        var style = Objects.requireNonNull(ImGui.GetStyle());
        var col = style.get_Colors(ImGuiCol_HeaderActive);
        var activeHeaderColor = Color.rgba8888(col.get_x(), col.get_y(), col.get_z(), col.get_w());
        int windowFlags = ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoTitleBar;

        if (ImGui.Begin("Information", null, windowFlags)) {
            // tool buttons section
            {
                float spacing = style.get_ItemInnerSpacing().get_x();
                float padding = style.get_FramePadding().get_x();
                float buttonWidth = (ImGui.GetWindowWidth() - (3 * spacing) - (2 * padding)) / 2f;

                // new row for load/save buttons
                if (ImGui.Button(String.format("%sLoad ", Icons.FolderOpen), ImVec2.TMP_1.set(buttonWidth, 0))) {
                    editor.load();
                }
                ImGui.SameLine(0, spacing);
                if (ImGui.Button(String.format("%sSave ", Icons.Save), ImVec2.TMP_1.set(buttonWidth, 0))) {
                    editor.save();
                }

                // new row for zoom and flow buttons
                if (ImGui.Button(String.format("%sZoom ", Icons.SearchLocation), ImVec2.TMP_1.set(buttonWidth, 0))) {
                    editor.navigateToContent();
                }
                ImGui.SameLine(0, spacing);
                if (ImGui.Button(String.format("%sShow flow ", Icons.FillDrip), ImVec2.TMP_1.set(buttonWidth, 0))) {
                    for (var link : session.links) {
                        editor.showFlow(link);
                    }
                }

                // new row for repo link
                float buttonFullWidth = ImGui.GetWindowWidth() - (2 * spacing) - (2 * padding);
                var libraryRepoUrl = String.format("%s%s%s", Icons.ExternalLinkAlt, Icons.CodeBranch, LIB_REPO);
                if (ImGui.Button(libraryRepoUrl, ImVec2.TMP_1.set(buttonFullWidth, 0))) {
                    EditorUtil.openUrl(LIB_EXAMPLES_URL);
                }

                // new rows for setting toggles
                ImGui.Spacing();
                ImGui.Checkbox("Show ordinals", session.showOrdinals);
            } // end section: tool buttons

            ImGui.Spacing();
            ImGui.Separator();

            // node detail rows section
            {
                // heading
                EditorUtil.rectFilled(ImGui.GetContentRegionAvail().get_x(), ImGui.GetTextLineHeight(), activeHeaderColor, 0.25f);
                ImGui.Spacing();
                ImGui.SameLine();
                ImGui.Text("Nodes");

                ImGui.Indent();
                for (var node : session.nodes) {
                    renderNodeDetailRow(node);
                }
                ImGui.Unindent();
            } // end section: node detail rows

            ImGui.Spacing();
            ImGui.Separator();

            // selection change details
            {
                // heading
                EditorUtil.rectFilled(ImGui.GetContentRegionAvail().get_x(), ImGui.GetTextLineHeight(), activeHeaderColor, 0.25f);
                ImGui.Spacing();
                ImGui.SameLine();
                ImGui.Text("Selection");

                if (ImGui.Button("Deselect All", ImVec2.TMP_1.set(ImGui.GetContentRegionAvail().get_x(), 0))) {
                    session.clearSelection();
                }

                // selection change details
                var plural = (selectionChangeCount == 1) ? "" : "s";
                var text = String.format("Current selection: (changed %d time%s)", selectionChangeCount, plural);
                ImGui.Text(text);

                ImGui.Indent();
                {
                    session.getSelectedNodes().forEach(node -> {
                        ImGui.BulletText(String.format("%s (%s)", node.toString(), node.globalId));
                        if (ImGui.IsItemHovered()) {
                            ImGui.SetTooltip(String.format("Node: '%s_%d#%d'\nInputs:\n\t%s\nOutputs:\n\t%s",
                                    node.label, node.objectId, node.globalId,
                                    node.inputs.stream().map(Object::toString).collect(Collectors.joining("\r\n  ")),
                                    node.outputs.stream().map(Object::toString).collect(Collectors.joining("\r\n  "))
                            ));
                        }
                    });
                    session.getSelectedLinks().forEach(link -> {
                        ImGui.BulletText(String.format("%s (%d)", link, link.globalId));
                        if (ImGui.IsItemHovered()) {
                            ImGui.SetTooltip(String.format("Link: '%s'_%d#%d\nSource: %s\nTarget: %s",
                                    link, link.objectId, link.globalId,
                                    link.src.toString(), link.dst.toString()
                            ));
                        }
                    });
                }
                ImGui.Unindent();
            } // end section: selection change details

            // metrics window toggle
            {
                // vertical gap to bottom-align the checkbox
                float itemSpace = style.get_ItemSpacing().get_y() + style.get_ItemInnerSpacing().get_y();
                float frameSpace = style.get_FramePadding().get_y() + style.get_FrameBorderSize();
                ImGui.Dummy(ImVec2.TMP_1.set(0, ImGui.GetContentRegionAvail().get_y() - (ImGui.GetTextLineHeight() + itemSpace + frameSpace)));

                ImGui.Checkbox("Show metrics window", session.showMetricsWindow);
                if (session.showMetricsWindow.getValue()) {
                    ImGui.ShowMetricsWindow();
                }
            } // end section: metrics window toggle
        }
        ImGui.End();
    }

//    // TODO(brian): this needs work
//    private void renderNodeDetailRow(Node node) {
//        ImGui.PushID(node.globalId);
//        var drawList = ImGui.GetWindowDrawList();
//        var start = ImGui.GetCursorScreenPos();
//        var width = ImGui.GetContentRegionAvail().get_x();
//        var lineHeight = ImGui.GetTextLineHeight();
//        var style = ImGui.GetStyle();
//        var iconPanelPos = new ImVec2();
//
//        // node detail row: show 'just touched' indicator indicator that fades over time
//        float progress = session.getTouchProgress(node);
//        if (progress > 0) {
//            drawList.AddLine(
//                    ImVec2.TMP_1.set(start.get_x() - 8f, start.get_y()),
//                    ImVec2.TMP_2.set(start.get_x() - 8f, start.get_y() + lineHeight),
//                    Color.rgba8888(1f, 0f, 0f, 1f - progress), 4f);
//        }
//
//        // node detail row: name and handle selection input, including multi-select
//        var isSelected = session.isSelected(node);
//        if (ImGui.Selectable(node.toString(), isSelected, ImGuiSelectableFlags_SpanAllColumns, ImVec2.TMP_1.set(0, 0))) {
//            // TODO(brian): how do we get individual key status?
////            if (ImGui.GetIO().GetKeyCtrl()) {
////                if (isSelected) {
////                    session.deselect(node);
////                } else {
////                    session.select(node, true);
////                }
////            } else {
//                session.select(node);
////            }
//
//            // focus on the selection in the editor
//            editor.navigateToSelection();
//        }
//
//        // node detail row: on hover - show node state in tooltip
////        if (ImGui.IsItemHovered() && node.hasState()) {
////            ImGui.SetTooltip("State: \{node.getState()}");
////        }
//
//        // node detail row: pointer id text
//        int numIcons = 2;
//        var iconSize = lineHeight;
//        var globalIdText = String.format("(%d)", node.globalId);
//        var globalIdTextSize = ImGui.CalcTextSize(globalIdText);
//        var edgeIconBottom = (lineHeight - iconSize) / 2f;
//        var styleSpace = style.get_IndentSpacing() + style.get_ItemInnerSpacing().get_x();
//        var edgeIconLeft = width - styleSpace - (numIcons * iconSize);
//        iconPanelPos.set(start.get_x() + edgeIconLeft, start.get_y() + edgeIconBottom);
//
//        float edgeTextLeft = iconPanelPos.x - globalIdTextSize.get_x() - style.get_ItemInnerSpacing().get_x();
//        drawList.AddText(ImVec2.TMP_1.set(edgeTextLeft, start.get_y()), Color.WHITE.toIntBits(), globalIdText);
//
//        float iconLeftSpace = style.get_ItemSpacing().get_x();
//        float indentWidth = style.get_IndentSpacing();
//        float offsetFromX = edgeTextLeft + globalIdTextSize.get_x() + iconLeftSpace;
//        ImGui.SameLine(offsetFromX, 0);
//        iconPanelPos.set(ImGui.GetCursorScreenPos());
//        iconPanelPos.x -= indentWidth / 2f;
//        renderIcon(Icons.Save, "save", iconPanelPos, node, () -> {});//node::updateSavedState);
//
//        float iconWidth = ImGui.CalcTextSize(Icons.Save).get_x();
//        ImGui.SameLine(offsetFromX - indentWidth + iconWidth, iconWidth);
//        iconPanelPos.set(ImGui.GetCursorScreenPos());
//        iconPanelPos.x -= indentWidth / 2f;
//        renderIcon(Icons.RedoAlt, "restore", iconPanelPos, node, () -> {
////            node.restoreSavedState();
////            editor.restoreNodeState(node);
////            node.clearSavedState();
//        });
//
//        ImGui.PopID();
//    }

    // TODO(brian): needs work
    private void renderNodeDetailRow(Node node) {
        ImGui.PushID(node.globalId);
        var drawList = ImGui.GetWindowDrawList();
        var start = ImGui.GetCursorScreenPos();
        var width = ImGui.GetContentRegionAvail().get_x();
        var lineHeight = ImGui.GetTextLineHeight();
        var style = ImGui.GetStyle();
        var iconPanelPos = new ImVec2();

        // node detail row: show 'just touched' indicator indicator that fades over time
        float progress = session.getTouchProgress(node);
        if (progress > 0) {
            drawList.AddLine(
                    ImVec2.TMP_1.set(start.get_x() - 8f, start.get_y()),
                    ImVec2.TMP_2.set(start.get_x() - 8f, start.get_y() + lineHeight),
                    Color.rgba8888(1f, 0f, 0f, 1f - progress), 4f);
        }

        // node detail row: name and handle selection input, including multi-select
        var isSelected = session.isSelected(node);
        if (ImGui.Selectable(node.toString(), isSelected, ImGuiSelectableFlags_SpanAllColumns, ImVec2.TMP_1.set(0, 0))) {
            // TODO(brian): how do we get individual key status?
//            if (ImGui.GetIO().GetKeyCtrl()) {
//                if (isSelected) {
//                    session.deselect(node);
//                } else {
//                    session.select(node, true);
//                }
//            } else {
                session.select(node);
//            }

            // focus on the selection in the editor
            editor.navigateToSelection();
        }

        // node detail row: on hover - show node state in tooltip
//        if (ImGui.IsItemHovered() && node.hasState()) {
//            ImGui.SetTooltip("State: \{node.getState()}");
//        }

        // node detail row: pointer id text
        int numIcons = 2;
        var iconSize = lineHeight;
        var globalIdText = String.format("(%d)", node.globalId);
        var globalIdTextSize = ImGui.CalcTextSize(globalIdText);
        var edgeIconBottom = (lineHeight - iconSize) / 2f;
        var styleSpace = style.get_IndentSpacing() + style.get_ItemInnerSpacing().get_x();
        var edgeIconLeft = width - styleSpace - (numIcons * iconSize);
        iconPanelPos.set(start.get_x() + edgeIconLeft, start.get_y() + edgeIconBottom);

        float edgeTextLeft = iconPanelPos.get_x() - globalIdTextSize.get_x() - style.get_ItemInnerSpacing().get_x();
        drawList.AddText(ImVec2.TMP_1.set(edgeTextLeft, start.get_y()), Color.GRAY.toIntBits(), globalIdText);

        float iconLeftSpace = style.get_ItemSpacing().get_x();
        float indentWidth = style.get_IndentSpacing();
        float offsetFromX = edgeTextLeft + globalIdTextSize.get_x() + iconLeftSpace;
        ImGui.SameLine(offsetFromX, 0);
        iconPanelPos.set(ImGui.GetCursorScreenPos().get_x() - indentWidth / 2f, ImGui.GetCursorScreenPos().get_y());
        renderIcon(Icons.Save, "save", iconPanelPos, node, () -> {});//node::updateSavedState);

        float iconWidth = ImGui.CalcTextSize(Icons.Save).get_x();
        ImGui.SameLine(offsetFromX - indentWidth + iconWidth, iconWidth);
        iconPanelPos.set(ImGui.GetCursorScreenPos().get_x() - indentWidth / 2f, ImGui.GetCursorScreenPos().get_y());
        renderIcon(Icons.Restore, "restore", iconPanelPos, node, () -> {
//            node.restoreSavedState();
//            editor.restoreNodeState(node);
//            node.clearSavedState();
        });

        ImGui.PopID();
    }

    private void renderIcon(String icon, String buttonId, ImVec2 iconPanelPos, Node node, Runnable onClick) {
        var drawList = ImGui.GetWindowDrawList();
        var iconSize = ImGui.CalcTextSize(icon);

        ImGui.SetNextItemAllowOverlap();
//        if (node.hasSavedState()) {
//            if (ImGui.InvisibleButton(buttonId, iconSize)) {
//                onClick.run();
//            }
//
//            var color = Colors.iconColorNormal;
//            if      (ImGui.IsItemActive())  color = Colors.iconActive;
//            else if (ImGui.IsItemHovered()) color = Colors.iconHover;
//
//            drawList.addText(iconPanelPos, color, icon);
////            ImGui.SameLine();
////            ImGui.TextColored(color, icon);
//        } else {
        // Placeholder for non-clickable state
        ImGui.Dummy(ImVec2.TMP_1.set(0, 0));

        drawList.AddText(iconPanelPos, Colors.iconDim, icon);
//            ImGui.SameLine();
//            ImGui.TextColored(color, icon);
//        }
    }

    // TODO(brian): do we have font awesome icons in this version of the lib?
    private static class Icons {
        static final String Save = "+";
        static final String Delete = "-";
        static final String Restore = "~";
        static final String RedoAlt = ">>";
        static final String FolderOpen = "f";
        static final String SearchLocation = "/";
        static final String FillDrip = "o";
        static final String ExternalLinkAlt = "->";
        static final String CodeBranch = "y";
    }

    private static class Colors {
        static final int iconNormal = Color.valueOf("#ffffff88").toIntBits();
        static final int iconDim = Color.valueOf("#ffffff33").toIntBits();
        static final int iconActive = Color.valueOf("#ffff00ff").toIntBits();
        static final int iconHover = Color.valueOf("#ffff0088").toIntBits();
    }
}
