package imgui.example.nodeeditor.blueprintv2;

public class PinDesc {
    public final int kind;
    public final PinType type;

    public String label;

    public PinDesc(int kind, PinType type) {
        this(kind, type, type.name());
    }

    public PinDesc(int kind, PinType type, String label) {
        this.kind = kind;
        this.type = type;
        this.label = label;
    }
}
