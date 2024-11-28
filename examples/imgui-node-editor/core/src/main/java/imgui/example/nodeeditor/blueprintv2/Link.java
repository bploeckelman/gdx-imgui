package imgui.example.nodeeditor.blueprintv2;

import imgui.ImVec4;
import imgui.extension.nodeeditor.NodeEditor;

public class Link extends EditorObject {

    public final Pin src;
    public final Pin dst;

    public ImVec4 color = new ImVec4(0f, 1f, 0f, 1f);
    public float thickness = 2f;

    public Link(Pin src, Pin dst) {
        super(Type.LINK);
        this.src = src;
        this.dst = dst;
    }

    public void render() {
        NodeEditor.Link(globalId, src.globalId, dst.globalId, color, thickness);
    }

    @Override
    public String toString() {
        return String.format("Link%d#%d: %s -> %s", objectId, globalId, src, dst);
    }
}
