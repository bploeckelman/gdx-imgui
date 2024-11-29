package imgui.example.nodeeditor.blueprintv2;

import com.badlogic.gdx.graphics.Color;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.example.nodeeditor.demo.BlueprintV2Example;
import imgui.extension.nodeeditor.NodeEditor;
import imgui.extension.nodeeditor.PinKind;
import imgui.idl.helper.IDLInt;

import static imgui.ImGuiStyleVar.*;
import static imgui.ImGuiWindowFlags.*;
import static imgui.extension.nodeeditor.StyleColor.*;
import static imgui.extension.nodeeditor.StyleVar.*;

public class EditorNodePane {

    private final BlueprintV2Example editor;
    private final EditorSession session;


    public EditorNodePane(BlueprintV2Example editor) {
        this.editor = editor;
        this.session = editor.session;
    }

    public void update(float delta) {
    }

    public void render() {
        int flags = ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoTitleBar;
        if (ImGui.Begin("Editor", null, flags)) {
            NodeEditor.Begin("Scene Editor");
            setStyle();

            session.nodes.forEach(Node::render);
            session.links.forEach(Link::render);

            createLinks();
            deleteObjects();
            contextMenu();

            unsetStyle();
            NodeEditor.End();
        }
        ImGui.End();
    }

    private void setStyle() {
        NodeEditor.PushStyleColor(StyleColor_NodeBg,        new ImVec4(1f, 1f, 1f, 0.0f));
        NodeEditor.PushStyleColor(StyleColor_NodeBorder,    new ImVec4( 0.6f,  0.6f,  0.6f, 0.8f));
        NodeEditor.PushStyleColor(StyleColor_PinRect,       new ImVec4( 0.24f, 0.6f, 1f, 0.6f));
        NodeEditor.PushStyleColor(StyleColor_PinRectBorder, new ImVec4( 0.24f, 0.6f, 1f, 0.6f));

        NodeEditor.PushStyleVar(StyleVar_NodePadding,             new ImVec4(6, 6, 6, 6));
        NodeEditor.PushStyleVar(StyleVar_NodeRounding,            5f);
        NodeEditor.PushStyleVar(StyleVar_NodeBorderWidth,         1.5f);
        NodeEditor.PushStyleVar(StyleVar_HoveredNodeBorderWidth,  2.5f);
        NodeEditor.PushStyleVar(StyleVar_SelectedNodeBorderWidth, 3.5f);
        NodeEditor.PushStyleVar(StyleVar_PinBorderWidth,          2f);
        NodeEditor.PushStyleVar(StyleVar_PinRadius,               10f);
        NodeEditor.PushStyleVar(StyleVar_LinkStrength,            250f);
        NodeEditor.PushStyleVar(StyleVar_SourceDirection,         new ImVec2( 1.0f, 0.0f));
        NodeEditor.PushStyleVar(StyleVar_TargetDirection,         new ImVec2(-1.0f, 0.0f));
    }

    private void unsetStyle() {
        NodeEditor.PopStyleVar(10);
        NodeEditor.PopStyleColor(4);
    }

    private void createLinks() {
        if (NodeEditor.BeginCreate(Colors.createLink, 2f)) {
            var a = new IDLInt();
            var b = new IDLInt();

            if (NodeEditor.QueryNewLink(a, b)) {
                var aPinId = a.getValue();
                var bPinId = b.getValue();

                var srcPin = session.findPin(aPinId);
                var dstPin = session.findPin(bPinId);
                if (srcPin.isPresent() && dstPin.isPresent()) {
                    var src = srcPin.get();
                    var dst = dstPin.get();

                    // ensure the pins are connected in the correct direction
                    // if (src.kind == PinKind.Output && dst.kind == PinKind.Input) {
                    //     // already correct, src(out) -> dst(in)
                    // } else
                    if (src.kind == PinKind.Input && dst.kind == PinKind.Output) {
                        // reversed, src(in) <- dst(out); swap src/dst pins
                        var temp = src;
                        src = dst;
                        dst = temp;
                    }

                    // reject incompatible pins, otherwise create a new link if accepted
                    if (src == dst) {
                        showLabel.apply("Cannot link to self", Colors.rejectLabelBackground);
                        NodeEditor.RejectNewItem(Colors.rejectLink, 2f);
                    } else if (src.kind == dst.kind) {
                        showLabel.apply("Incompatible pin kinds", Colors.rejectLabelBackground);
                        NodeEditor.RejectNewItem(Colors.rejectLink, 2f);
                    } else if (src.type != dst.type) {
                        showLabel.apply("Incompatible pin types", Colors.rejectLabelBackground);
                        NodeEditor.RejectNewItem(Colors.rejectLink, 2f);
                    } else if (src.node.globalId == dst.node.globalId) {
                        showLabel.apply("Cannot link pins in same node", Colors.rejectLabelBackground);
                        NodeEditor.RejectNewItem(Colors.rejectLink, 2f);
                    } else {
                        showLabel.apply("Create link", Colors.acceptLabelBackground);
                        if (NodeEditor.AcceptNewItem(Colors.acceptLink, 4f)) {
                            var link = new Link(src, dst);
                            session.addLink(link);
                        }
                    }
                }
            }
        }
        // NOTE: this needs to be called outside the if block,
        //  unlike some other ImGui begin/end pairs
        NodeEditor.EndCreate();
    }

    private void deleteObjects() {
        // handle deleting nodes and links
        if (NodeEditor.BeginDelete()) {
            var globalId = new IDLInt();
            while (NodeEditor.QueryDeletedNode(globalId)) {
                if (NodeEditor.AcceptDeletedItem()) {
                    session.findNode(globalId.getValue())
                            .ifPresent(session::removeNode);
                }
            }
            while (NodeEditor.QueryDeletedLink(globalId)) {
                if (NodeEditor.AcceptDeletedItem()) {
                    session.findLink(globalId.getValue())
                            .ifPresent(session::removeLink);
                }
            }
        }
        // NOTE: this needs to be called outside the if block,
        //  unlike some other ImGui begin/end pairs
        NodeEditor.EndDelete();
    }

    private void contextMenu() {
        var openPopupPosition = ImGui.GetMousePos();
        var contextMenu = session.contextMenu;

        // NOTE: about reference frame switching:
        //  popup windows are not done in graph space, they're done in screen space.
        //  `suspend()` changes the positioning reference frame from "graph" to "screen"
        //  then all following calls are in screen space and `resume()` returns to reference frame
        NodeEditor.Suspend();
        ImGui.PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2.TMP_1.set(8f, 8f));

        // open the appropriate popup, if any, depending on the right-click context
        if (NodeEditor.ShowNodeContextMenu(contextMenu.nodeGlobalId)) {
            ImGui.OpenPopup(PopupIds.NODE);
        } else if (NodeEditor.ShowPinContextMenu(contextMenu.pinGlobalId)) {
            ImGui.OpenPopup(PopupIds.PIN);
        } else if (NodeEditor.ShowLinkContextMenu(contextMenu.linkGlobalId)) {
            ImGui.OpenPopup(PopupIds.LINK);
        } else if (NodeEditor.ShowBackgroundContextMenu()) {
            ImGui.OpenPopup(PopupIds.NODE_NEW);
            contextMenu.newNodeLinkPin = null;
        }

        // node context popup -------------------------------------------------
        if (ImGui.BeginPopup(PopupIds.NODE)) {
            ImGui.Text("Node Context Menu");
            ImGui.Separator();

            var nodeGlobalId = contextMenu.nodeGlobalId.getValue();
            editor.session.findNode(nodeGlobalId)
                    .ifPresentOrElse(node -> {
                        ImGui.Text(String.format("ID: %d", node.globalId));
                        ImGui.Text(String.format("Node: %s", node.label));
                        ImGui.Text(String.format("Inputs: %d", node.inputs.size()));
                        ImGui.Text(String.format("Outputs: %d", node.outputs.size()));
                        ImGui.Separator();

                        if (ImGui.MenuItem("Delete")) {
                            editor.session.removeNode(node);
                        }
                    }, () -> ImGui.Text(String.format("Unknown node: %d", nodeGlobalId)));

            ImGui.EndPopup();
        }

        // pin context popup --------------------------------------------------
        if (ImGui.BeginPopup(PopupIds.PIN)) {
            ImGui.Text("Pin Context Menu");
            ImGui.Separator();

            var pinGlobalId = contextMenu.pinGlobalId.getValue();
            editor.session.findPin(pinGlobalId)
                    .ifPresentOrElse(pin -> {
                        ImGui.Text(String.format("ID: %d", pin.globalId));
                        ImGui.Text(String.format("Pin: %s", pin.label));
                        ImGui.Text(String.format("Node: %d '%s'", pin.node.globalId, pin.node.label));
                        ImGui.Separator();

                        if (ImGui.MenuItem("Delete")) {
                            editor.session.removePin(pin);
                        }
                    }, () -> ImGui.Text(String.format("Unknown pin: %d", pinGlobalId)));

            ImGui.EndPopup();
        }

        // link context popup -------------------------------------------------
        if (ImGui.BeginPopup(PopupIds.LINK)) {
            ImGui.Text("Link Context Menu");
            ImGui.Separator();

            var linkGlobalId = contextMenu.linkGlobalId.getValue();
            editor.session.findLink(linkGlobalId)
                    .ifPresentOrElse(link -> {
                        ImGui.Text(String.format("ID: %d", link.globalId));
                        ImGui.Text(String.format("Source Pin: %d '%s'", link.src.globalId, link.src.label));
                        ImGui.Text(String.format("Target Pin: %s", link.dst.label));
                        ImGui.Separator();

                        if (ImGui.MenuItem("Delete")) {
                            editor.session.removeLink(link);
                        }
                    }, () -> ImGui.Text(String.format("Unknown link: %s", linkGlobalId)));

            ImGui.EndPopup();
        }

        // create new node popup ----------------------------------------------
        if (ImGui.BeginPopup(PopupIds.NODE_NEW)) {
            ImGui.Text(PopupIds.NODE_NEW);
            ImGui.Separator();

            // spawn a node if user selects a type from the popup
            Node newNode = null;
            for (var desc : session.nodeRegistry.orderedNodes) {
                if (ImGui.MenuItem(desc.type)) {
                    newNode = new Node(desc);
                }
            }

            // if a new node was spawned, add it to the editor
            if (newNode != null) {
                editor.session.addNode(newNode);

                // position the new node near the right-click position
                NodeEditor.SetNodePosition(newNode.globalId, openPopupPosition);

                // TODO: auto-connect a pin in the new node to a link that was dragged out
            }

            ImGui.EndPopup();
        }

        ImGui.PopStyleVar();
        NodeEditor.Resume();
    }

    /**
     * String identifiers for each right-click context menu popup
     */
    private static class PopupIds {
        static final String NODE_NEW = "Create New Node";
        static final String NODE = "Node Context Menu";
        static final String PIN = "Pin Context Menu";
        static final String LINK = "Link Context Menu";
    }

    /**
     * Simple interface to allow for different ways of showing a simple label
     */
    @FunctionalInterface
    private interface ShowLabel {
        void apply(String label, int color);
    }

    /**
     * Show a label with a colored background behind it, used for right-click context menu labels
     * for displaying temporary messages to the user about whether the current action is valid
     * or invalid, and why.
     */
    private final ShowLabel showLabel = (label, color) -> {
        ImGui.SetCursorPosY(ImGui.GetCursorPosY() - ImGui.GetTextLineHeight());

        var scale = 1.5f;
        var textSize = ImGui.CalcTextSize(label);
        var size = new ImVec2(textSize.get_x() * scale, textSize.get_y() * scale);
        var padding = ImGui.GetStyle().get_FramePadding();
        var spacing = ImGui.GetStyle().get_ItemSpacing();
        var cursorPos = ImGui.GetCursorPos();
        ImGui.SetCursorPos(ImVec2.TMP_1.set(
                cursorPos.get_x() + spacing.get_x(),
                cursorPos.get_y() - spacing.get_y()));

        var cursorScreenPos = ImGui.GetCursorScreenPos();
        var rectMin = new ImVec2(cursorScreenPos.get_x() - padding.get_x(), cursorScreenPos.get_y() - padding.get_y());
        var rectMax = new ImVec2(cursorScreenPos.get_x() + size.get_x() + padding.get_x(), cursorScreenPos.get_y() + size.get_y() + padding.get_y());

        var drawList = ImGui.GetWindowDrawList();
        var font = ImGui.GetFont();
        var fontSize = (int) Math.floor(font.get_ConfigData().get_SizePixels() * scale);
        drawList.AddRectFilled(rectMin, rectMax, color, size.get_y() * 0.25f);
        drawList.AddText(font, fontSize, ImVec2.TMP_2.set(
                rectMin.get_x() + padding.get_x(),
                rectMin.get_y() + padding.get_y()),
                Colors.labelTextColor, label);
    };

    private static class Colors {
        static final ImVec4 createLink = new ImVec4(0.24f, 0.6f, 1f, 0.8f);
        static final ImVec4 rejectLink = new ImVec4(1f, 0f, 0f, 0.8f);
        static final ImVec4 acceptLink = new ImVec4(0f, 1f, 0f, 0.8f);
        static final int rejectLabelBackground = Color.valueOf("#2d2020ff").toIntBits();
        static final int acceptLabelBackground = Color.valueOf("#202d20ff").toIntBits();
        static final int labelTextColor = Color.valueOf("#ffffffff").toIntBits();
    }
}
