package imgui.example.nodeeditor.blueprintv2;

import com.badlogic.gdx.graphics.Color;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeeditor.NodeEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node extends EditorObject {

    public enum Section { NODE, HEADER, CONTENT, INPUTS, MIDDLE, OUTPUTS }

    public String label;
    public int color;

    public final NodeProperties props;
    public final List<Pin> inputs = new ArrayList<>();
    public final List<Pin> outputs = new ArrayList<>();
    public final Map<Section, Bounds> bounds = new HashMap<>();

    public Node(NodeDesc desc) {
        super(Type.NODE);

        this.label = desc.type;
        this.color = desc.color;
        this.props = desc.props.copy();
        for (var pinDesc : desc.inputs) {
            inputs.add(new Pin(this, pinDesc));
        }
        for (var pinDesc : desc.outputs) {
            outputs.add(new Pin(this, pinDesc));
        }
        for (var section : Section.values()) {
            bounds.put(section, new Bounds());
        }
    }

    public void render() {
        NodeEditor.BeginNode(globalId);
        ImGui.PushID(globalId);
        ImGui.BeginGroup();

        // header -----------------------------------------
        ImGui.BeginGroup();
        ImGui.Text(label);
        ImGui.Spacing();
        ImGui.Spacing();
        ImGui.EndGroup();
        bounds.get(Section.HEADER).setFromItemRect();

        // content ----------------------------------------
        ImGui.BeginGroup();
        {
//            ImGui.spacing();

            // inputs
            ImGui.BeginGroup();
            inputs.forEach(Pin::render);
            ImGui.EndGroup();
            bounds.get(Section.INPUTS).setFromItemRect();

            ImGui.SameLine();

            // middle
            ImGui.BeginGroup();
            for (var prop : props.strings.entrySet()) {
                var key = prop.getKey();
//                if (key.equals("Text")) {
//                    case "Text" -> {
//                        var inputKey = key.toLowerCase().replaceAll(" ", "_");
//                        var inputLabel = String.format("##prop-%s-%s", inputKey, toLabel());
//                        var inputString = new ImGuiString(prop.getValue());
//                        var inputCallback = ImGuiInputTextCallbackData
//                            @Override
//                            public void accept(ImGuiInputTextCallbackData data) {
//                                prop.setValue(data.GetBuf());
//                            }
//                        };
//
//                        var size = ImGui.CalcTextSize(prop.getValue());
//                        var margin = size.get_x() * 0.5f;
//                        ImGui.PushItemWidth(size.get_x() + margin);
//                        ImGui.InputText(inputLabel, inputString);//, ImGuiInputTextFlags.ImGuiInputTextFlags_CallbackEdit, inputCallback);
//                        ImGui.PopItemWidth();
//                    }
//                } else {
                    ImGui.Text(key);
                    ImGui.SameLine();
                    ImGui.Text(prop.getValue());
//                }
            }
            ImGui.EndGroup();
            bounds.get(Section.MIDDLE).setFromItemRect();

            ImGui.SameLine();

            // outputs
            ImGui.BeginGroup();
            outputs.forEach(Pin::render);
            ImGui.EndGroup();
            bounds.get(Section.OUTPUTS).setFromItemRect();
        }
        ImGui.EndGroup();
        bounds.get(Section.CONTENT).setFromItemRect();


        ImGui.EndGroup();
        ImGui.PopID();
        NodeEditor.EndNode();
        bounds.get(Section.NODE).setFromItemRect();

        renderBackgrounds();
    }

    private void renderBackgrounds() {
//        var draw = NodeEditor.GetNodeBackgroundDrawList(globalId);
//        var style = NodeEditor.GetStyle();
//        var padding = style.getNodePadding();
//        var rounding = style.getNodeRounding() - 1f;
//        var borderWidth = style.getNodeBorderWidth();
//        var borderWidthHover = style.getHoveredNodeBorderWidth();
//        var borderWidthSelect = style.getSelectedNodeBorderWidth();
//        var isHovered = NodeEditor.getHoveredNode() == globalId;
//        var isSelected = NodeEditor.isNodeSelected(globalId);
//        var border = borderWidth;
//
//        // header background - with adjustments to fit the full node width
//        // NOTE: min/max are top-left/bottom-right corners in screen space
//        float headerMinX = bounds.get(Section.NODE).min.x + border;
//        float headerMaxX = bounds.get(Section.NODE).max.x - border;
//        float headerMinY = bounds.get(Section.NODE).min.y + border;
//        float headerMaxY = headerMinY + (bounds.get(Section.HEADER).max.y - bounds.get(Section.HEADER).min.y);
//
//        draw.addRectFilled(
//            headerMinX, headerMinY, headerMaxX, headerMaxY,
//            Color.headerBackground, rounding, ImDrawFlags.RoundCornersTop);
//
//        // content background
//        draw.addRectFilled(
//            bounds.get(Section.NODE).min.x + border, headerMaxY,
//            bounds.get(Section.NODE).max.x - border,
//            bounds.get(Section.NODE).max.y - border,
//            Color.contentBackground, rounding, ImDrawFlags.RoundCornersBottom);
//
//        // header/content separator - draw after backgrounds so it overlaps
//        // must be some sampling thing going on here that we have to adjust by 0.5f
//        draw.addLine(
//            headerMinX - 0.5f, headerMaxY,
//            headerMaxX - 0.5f, headerMaxY,
//            Color.separator, 1);
    }

    public String toLabel() {
        return String.format("Node%d#%d", objectId, globalId);
    }

    @Override
    public String toString() {
        return String.format("%s: '%s'", toLabel(), label);
    }

    public static class Bounds {
        public final ImVec2 min = new ImVec2();
        public final ImVec2 max = new ImVec2();

        public Bounds() {}

        public void set(ImVec2 min, ImVec2 max) {
            this.min.set(min);
            this.max.set(max);
        }

        public void setFromItemRect() {
            set(ImGui.GetItemRectMin(), ImGui.GetItemRectMax());
        }
    }

    private static class Colors {
        private static final int headerBackground = Color.valueOf("#23531cff").toIntBits();
        private static final int contentBackground = Color.valueOf("#282c27ff").toIntBits();
        private static final int separator= Color.valueOf("#8ea687").toIntBits();
    }
}
