package imgui.example.nodeeditor.demo;

import com.badlogic.gdx.Gdx;
import imgui.ImGui;
import imgui.ImGuiCond;
import imgui.ImVec2;
import imgui.example.nodeeditor.blueprintv2.EditorInfoPane;
import imgui.example.nodeeditor.blueprintv2.EditorNodePane;
import imgui.example.nodeeditor.blueprintv2.EditorSession;
import imgui.example.nodeeditor.blueprintv2.Link;
import imgui.extension.nodeeditor.EditorContext;
import imgui.extension.nodeeditor.NodeEditor;

import static imgui.ImGuiStyleVar.ImGuiStyleVar_WindowRounding;
import static imgui.ImGuiStyleVar.ImGuiStyleVar_WindowTitleAlign;
import static imgui.ImGuiWindowFlags.*;

public class BlueprintV2Example {

    private static final String TAG = BlueprintV2Example.class.getSimpleName();

    public final EditorSession session;

    private final EditorInfoPane infoPane;
    private final EditorNodePane nodePane;

    public BlueprintV2Example(EditorContext context) {
        this.session = new EditorSession(this, context);
        this.infoPane = new EditorInfoPane(this);
        this.nodePane = new EditorNodePane(this);
    }

    // TODO(brian): dispose()?
    // TODO(brian): resize()?

    public void navigateToContent() {
        NodeEditor.NavigateToContent(1);
    }

    public void navigateToSelection() {
        NodeEditor.NavigateToSelection(true);
    }

    public void showFlow(Link link) {
        Gdx.app.error(TAG, "showFlow() not yet implemented");
    }

    public void load() {
        Gdx.app.error(TAG, "load() not yet implemented");
    }

    public void save() {
        Gdx.app.error(TAG, "save() not yet implemented");
    }

    public void render() {
        NodeEditor.SetCurrentEditor(session.editorContext);

        // update all the editor components before rendering
        var delta = ImGui.GetIO().get_DeltaTime();
        session.update(delta);
        infoPane.update(delta);
        nodePane.update(delta);

        ImGui.SetNextWindowPos(ImVec2.TMP_1.set(0, 0));
        ImGui.SetNextWindowSize(ImGui.GetMainViewport().get_Size());

        int flags = ImGuiWindowFlags_NoBringToFrontOnFocus | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize;
        ImGui.Begin("Blueprint V2", null, flags);
        ImGui.PushStyleVar(ImGuiStyleVar_WindowRounding, 0f);
        ImGui.PushStyleVar(ImGuiStyleVar_WindowTitleAlign, ImVec2.TMP_1.set(0.5f, 0.5f));

        var availableSize = ImGui.GetContentRegionAvail();
        float infoPaneWidth = (1 / 4f) * availableSize.get_x();
        float nodePaneWidth = availableSize.get_x() - infoPaneWidth;
        float paneHeight = availableSize.get_y();

        ImGui.SetNextWindowPos(ImGui.GetCursorScreenPos());
        ImGui.SetNextWindowSize(ImVec2.TMP_1.set(infoPaneWidth, paneHeight));
        infoPane.render();

        ImGui.SameLine();

        var innerSpacing = ImGui.GetStyle().get_ItemInnerSpacing().get_x();
        var nodePanePos = ImVec2.TMP_1.set(
                ImGui.GetCursorScreenPos().get_x() + infoPaneWidth - innerSpacing,
                ImGui.GetCursorScreenPos().get_y());
        ImGui.SetNextWindowPos(nodePanePos);
        ImGui.SetNextWindowSize(ImVec2.TMP_1.set(nodePaneWidth, paneHeight), ImGuiCond.ImGuiCond_Always);
        nodePane.render();

        ImGui.PopStyleVar(2);
        ImGui.End();

        NodeEditor.SetCurrentEditor(null);
    }
}
