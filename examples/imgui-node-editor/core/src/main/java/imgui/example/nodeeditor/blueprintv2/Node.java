package imgui.example.nodeeditor.blueprintv2;

import com.badlogic.gdx.graphics.Color;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeeditor.NodeEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static imgui.ImDrawFlags.*;

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

    // TODO: it doesn't look like there's a way to get at node-editor style var values currently because
    //  `Style` returned by `NodeEditor.GetStyle()` doesn't have `get_StyleVarX()` methods like `ImGuiStyle` does.
    //  So these values are copied over from EditorNodePane for the time being
    private static final float  styleNodeRounding = 5f;
    private static final float  styleNodeBorderWidth = 1.5f;
    private static final float  styleHoveredNodeBorderWidth = 2.5f;
    private static final float  styleSelectedNodeBorderWidth = 3.5f;

    private void renderBackgrounds() {
        var drawList = NodeEditor.GetNodeBackgroundDrawList(globalId);
        var rounding = styleNodeRounding - 1f;
        var border = styleNodeBorderWidth;
        if (NodeEditor.GetHoveredNode() == globalId) {
            border = styleHoveredNodeBorderWidth;
        } else if (NodeEditor.IsNodeSelected(globalId)) {
            border = styleSelectedNodeBorderWidth;
        }

        // header background - with adjustments to fit the full node width
        // NOTE: min/max are top-left/bottom-right corners in screen space
        float headerHeight = bounds.get(Section.HEADER).max.get_y() - bounds.get(Section.HEADER).min.get_y();
        float headerMinX = bounds.get(Section.NODE).min.get_x() + border;
        float headerMaxX = bounds.get(Section.NODE).max.get_x() - border;
        float headerMinY = bounds.get(Section.NODE).min.get_y() + border;
        float headerMaxY = headerMinY + headerHeight;

        drawList.AddRectFilled(
                ImVec2.TMP_1.set(headerMinX, headerMinY),
                ImVec2.TMP_2.set(headerMaxX, headerMaxY),
                Colors.headerBackground, rounding, ImDrawFlags_RoundCornersTop);

        // content background
        drawList.AddRectFilled(
                ImVec2.TMP_1.set(bounds.get(Section.NODE).min.get_x() + border, headerMaxY),
                ImVec2.TMP_2.set(
                        bounds.get(Section.NODE).max.get_x() - border,
                        bounds.get(Section.NODE).max.get_y() - border),
            Colors.contentBackground, rounding, ImDrawFlags_RoundCornersBottom);

        // header/content separator - draw after backgrounds so it overlaps
        // must be some sampling thing going on here that we have to adjust by 0.5f
        drawList.AddLine(
            ImVec2.TMP_1.set(headerMinX - 0.5f, headerMaxY),
            ImVec2.TMP_2.set(headerMaxX - 0.5f, headerMaxY),
            Colors.separator, 1);
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
