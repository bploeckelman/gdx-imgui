package imgui.example.nodeeditor.blueprintv2;

public abstract class EditorObject {

    public enum Type { NODE, PIN, LINK }

    private static int NEXT_NODE_ID = 1;
    private static int NEXT_PIN_ID = 1;
    private static int NEXT_LINK_ID = 1;
    private static int NEXT_GLOBAL_ID = 1;

    public final Type objectType;
    public final int objectId;
    public final int globalId;

    public EditorObject(Type objectType) {
        this.objectType = objectType;
        switch (objectType) {
            case NODE: this.objectId = NEXT_NODE_ID++; break;
            case PIN: this.objectId = NEXT_PIN_ID++; break;
            case LINK: this.objectId = NEXT_LINK_ID++; break;
            default: {
                throw new IllegalArgumentException("Unknown object type: " + objectType);
            }
        }
        this.globalId = NEXT_GLOBAL_ID++;
    }
}
