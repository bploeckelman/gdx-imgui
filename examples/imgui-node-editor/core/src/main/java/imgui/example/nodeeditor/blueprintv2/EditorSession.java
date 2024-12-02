package imgui.example.nodeeditor.blueprintv2;

import com.badlogic.gdx.Gdx;
import imgui.example.nodeeditor.demo.BlueprintV2Example;
import imgui.extension.nodeeditor.EditorContext;
import imgui.extension.nodeeditor.NodeEditor;
import imgui.idl.helper.IDLBool;
import imgui.idl.helper.IDLInt;
import imgui.idl.helper.IDLIntArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EditorSession {

    private static final String TAG = EditorSession.class.getSimpleName();

    public static class ContextMenu {
        IDLInt nodeGlobalId = new IDLInt();
        IDLInt pinGlobalId = new IDLInt();
        IDLInt linkGlobalId = new IDLInt();
        Pin newNodeLinkPin;
    }

    private final Map<Integer, EditorObject> objectByGlobalId = new HashMap<>();
    private final Map<Integer, Float> nodeTouchTime = new TreeMap<>(Integer::compare);
    private final float touchTime = 1f;

    public final BlueprintV2Example editor;
    public final EditorContext editorContext;
    public final List<Node> nodes = new ArrayList<>();
    public final List<Link> links = new ArrayList<>();
    public final IDLBool showOrdinals = new IDLBool(false);
    public final IDLBool showMetricsWindow = new IDLBool(false);
    public final NodeRegistry nodeRegistry = new NodeRegistry();
    public final ContextMenu contextMenu = new ContextMenu();

    public IDLIntArray selectedNodes = new IDLIntArray(0);
    public IDLIntArray selectedLinks = new IDLIntArray(0);
    public int numSelectedNodes;
    public int numSelectedLinks;
    public int selectionChangeCount;

    public EditorSession(BlueprintV2Example editor, EditorContext context) {
        this.editor = editor;
        this.editorContext = context;

        nodeRegistry.register(
            NodeFactory.text(),
            NodeFactory.displayText()
        );
    }

    public void update(float delta) {
        updateTouch(delta);
        updateSelections();
    }

    // Editor object management -----------------------------------------------

    public void addNode(Node node) {
        var _existingNode = findNode(node.globalId);
        if (_existingNode.isPresent()) {
            var existingNode = _existingNode.get();
            Gdx.app.log(TAG, String.format("Failed to add node, already exists: %s", existingNode));
            return;
        }

        nodes.add(node);
        objectByGlobalId.put(node.globalId, node);

        node.inputs.forEach(this::addPin);
        node.outputs.forEach(this::addPin);
    }

    public void addPin(Pin pin) {
        objectByGlobalId.put(pin.globalId, pin);
    }

    public void addLink(Link link) {
        links.add(link);
        objectByGlobalId.put(link.globalId, link);
    }

    public void removeNode(Node node) {
        node.inputs.forEach(this::removePin);
        node.outputs.forEach(this::removePin);

        nodes.remove(node);
        objectByGlobalId.remove(node.globalId);
    }

    public void removePin(Pin pin) {
        objectByGlobalId.remove(pin.globalId);
    }

    public void removeLink(Link link) {
        links.remove(link);
        objectByGlobalId.remove(link.globalId);
    }

    // Query methods ----------------------------------------------------------

    public Optional<Node> findNode(int globalId) {
        var object = objectByGlobalId.get(globalId);
        return Optional.ofNullable(object)
                .filter(Node.class::isInstance)
                .map(Node.class::cast);
    }

    public Optional<Link> findLink(int globalId) {
        var object = objectByGlobalId.get(globalId);
        return Optional.ofNullable(object)
                .filter(Link.class::isInstance)
                .map(Link.class::cast);
    }

    public Optional<Pin> findPin(int globalId) {
        var object = objectByGlobalId.get(globalId);
        return Optional.ofNullable(object)
                .filter(Pin.class::isInstance)
                .map(Pin.class::cast);
    }

    // Selection methods ------------------------------------------------------

    public Stream<Node> getSelectedNodes() {
        return IntStream.range(0, selectedNodes.getSize())
                .map(selectedNodes::getValue)
                .peek(id -> Gdx.app.log(TAG, String.format("Selected node id: %d", id)))
                .mapToObj(this::findNode)
                .flatMap(Optional::stream);
    }

    public Stream<Link> getSelectedLinks() {
        return IntStream.range(0, selectedLinks.getSize())
                .map(selectedLinks::getValue)
                .peek(id -> Gdx.app.log(TAG, String.format("Selected link id: %d", id)))
                .mapToObj(this::findLink)
                .flatMap(Optional::stream);
    }

    /**
     * Update the selected state for nodes and links
     * NOTE: There's a problem here when multi-selecting via a click+drag rectangle.
     *   The selection id values are not being correctly written to the selectedNodes IDLIntArray
     *   in NodeEditor.GetSelectedNodes() somewhere through the chain of jni / native calls.
     *   This ends up causing a crash at some arbitrary spot after the selection change
     *   possibly because there's a mismatch of strings between the jvm and native code
     *   such that when the native code tries to free strings in calls like ImGui.Button(label, size)
     *   it's trying to free memory that's not valid anymore.
     *   I've tried a couple variations of how to specify the IDLIntArrays for selection ids here
     *   and it doesn't seem to make much of a difference between the current version which is
     *   very close to the imgui-node-editor blueprint example usage here:
     *   https://github.com/thedmd/imgui-node-editor/blob/master/examples/blueprints-example/blueprints-example.cpp#L732
     */
    public void updateSelections() {
        // selected objects are tracked together in native code, so the selected id arrays
        // are always the same size to start with which can be more than the actual counts for either type
        int totalCount = NodeEditor.GetSelectedObjectCount();
        selectedNodes.resize(totalCount);
        selectedLinks.resize(totalCount);

        // populate the arrays with the selected object ids and get the counts by type
        numSelectedNodes = NodeEditor.GetSelectedNodes(selectedNodes, totalCount);
        numSelectedLinks = NodeEditor.GetSelectedLinks(selectedLinks, totalCount);

        // resize the arrays to the actual counts for each type
        selectedNodes.resize(numSelectedNodes);
        selectedLinks.resize(numSelectedLinks);

        // update selection change count
        if (hasSelectionChanged()) {
            selectionChangeCount++;
        }
    }

    public boolean isSelected(Node node) {
        for (int i = 0; i < numSelectedNodes; i++) {
            if (selectedNodes.getValue(i) == node.globalId) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(Link link) {
        for (int i = 0; i < numSelectedLinks; i++) {
            if (selectedLinks.getValue(i) == link.globalId) {
                return true;
            }
        }
        return false;
    }

    public void select(Node node) {
        select(node, false);
    }

    public void select(Node node, boolean append) {
        NodeEditor.SelectNode(node.globalId, append);
    }

    public void deselect(Node node) {
        NodeEditor.DeselectNode(node.globalId);
    }

    public boolean hasSelectionChanged() {
        return NodeEditor.HasSelectionChanged();
    }

    public void clearSelection() {
        NodeEditor.ClearSelection();
    }

    // Touch handling ---------------------------------------------------------

    public void touchNode(Node node) {
        nodeTouchTime.put(node.globalId, touchTime);
    }

    /**
     * Convert touch time for the specified node to a percent: 0..1
     */
    public float getTouchProgress(Node node) {
        float time = nodeTouchTime.getOrDefault(node.globalId, 0f);
        if (time > 0f) {
            return (touchTime - time) / touchTime;
        }
        return time;
    }

    public void updateTouch(float dt) {
        // java's map interface doesn't allow direct modification
        // of values while iterating, so we'll do it this way...
        nodeTouchTime.replaceAll((id, time) -> (time > 0f) ? (time - dt) : time);
    }
}
