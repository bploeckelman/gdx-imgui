package imgui.example.nodeeditor.blueprintv2;

import java.util.HashMap;
import java.util.Map;

public class NodeProperties {

    public final Map<String, String> strings = new HashMap<>();
    public final Map<String, Integer> ints = new HashMap<>();
    public final Map<String, Float> floats = new HashMap<>();
    public final Map<String, Boolean> bools = new HashMap<>();

    public NodeProperties() {}

    public NodeProperties copy() {
        var props = new NodeProperties();
        props.strings.putAll(strings);
        props.ints.putAll(ints);
        props.floats.putAll(floats);
        props.bools.putAll(bools);
        return props;
    }
}
