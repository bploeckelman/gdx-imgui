package imgui.example.nodeeditor.blueprintv2;

import imgui.ImGui;
import imgui.extension.nodeeditor.NodeEditor;

public class Pin extends EditorObject {

    public final Node node;
    public final int kind;
    public final PinType type;
    public final String label;

    public Pin(Node node, PinDesc desc) {
        super(Type.PIN);
        this.node = node;
        this.kind = desc.kind;
        this.type = desc.type;
        this.label = desc.label;
    }

    public void render() {
        NodeEditor.BeginPin(globalId, kind);
        ImGui.PushID(globalId);
        ImGui.BeginGroup();

        ImGui.Text(label);

        ImGui.EndGroup();
        ImGui.PopID();
        NodeEditor.EndPin();
    }

    public Link connectTo(Pin other) {
        return new Link(this, other);
    }

    @Override
    public String toString() {
        return String.format("Pin%d#%d: '%s' (%s, %s)", objectId, globalId, label, type, kind);
    }
}
