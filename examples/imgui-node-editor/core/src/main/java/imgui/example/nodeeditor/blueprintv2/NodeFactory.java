package imgui.example.nodeeditor.blueprintv2;

import imgui.extension.nodeeditor.PinKind;

public class NodeFactory {

    public static PinDesc flowPin(int nodeEditorPinKind) {
        return new PinDesc(nodeEditorPinKind, PinType.FLOW);
    }

    public static PinDesc stringInPin(String label) {
        return new PinDesc(PinKind.Input, PinType.STRING, label);
    }

    public static PinDesc stringOutPin(String label) {
        return new PinDesc(PinKind.Output, PinType.STRING, label);
    }

    public static NodeDesc text() {
        var node = new NodeDesc();

        node.type = "Text";
        node.props.strings.put("Text", "Hello, World!");

        node.outputs.add(stringOutPin("text >"));

        return node;
    }

    public static NodeDesc displayText() {
        var node = new NodeDesc();

        node.type = "Display Text";

        node.inputs.add(flowPin(PinKind.Input));
        node.outputs.add(flowPin(PinKind.Output));

        node.inputs.add(stringInPin("> text"));

        return node;
    }
}
