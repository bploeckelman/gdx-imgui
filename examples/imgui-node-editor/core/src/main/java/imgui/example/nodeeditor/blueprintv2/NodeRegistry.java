package imgui.example.nodeeditor.blueprintv2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeRegistry {

    public final Map<String, NodeDesc> nodes = new HashMap<>();
    public final List<NodeDesc> orderedNodes = new ArrayList<>();

    public void register(NodeDesc... descs) {
        for (var desc : descs) {
            if (nodes.containsKey(desc.type)) {
                throw new IllegalArgumentException("Node type already registered: " + desc.type);
            }
            nodes.put(desc.type, desc);
            orderedNodes.add(desc);
        }
    }
}
