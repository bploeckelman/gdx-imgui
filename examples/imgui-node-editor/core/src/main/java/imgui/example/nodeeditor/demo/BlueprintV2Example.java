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

import java.util.Objects;

import static imgui.ImGuiStyleVar.*;
import static imgui.ImGuiWindowFlags.*;

public class BlueprintV2Example {

    private static final String TAG = BlueprintV2Example.class.getSimpleName();
    private static final int MAIN_WINDOW_FLAGS = ImGuiWindowFlags_NoBringToFrontOnFocus | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize;

    public final EditorSession session;

    private final EditorInfoPane infoPane;
    private final EditorNodePane nodePane;

    public BlueprintV2Example() {
        this.session = new EditorSession(this);
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

    public void update() {
        var io = Objects.requireNonNull(ImGui.GetIO());
        var delta = io.get_DeltaTime();
        session.update(delta);
        infoPane.update(delta);
        nodePane.update(delta);
    }

    public void render(EditorContext context) {
        ImGui.Begin("Blueprint V2", null, MAIN_WINDOW_FLAGS);
        ImGui.PushStyleVar(ImGuiStyleVar_WindowRounding, 0f);
        ImGui.PushStyleVar(ImGuiStyleVar_WindowTitleAlign, new ImVec2(0.5f, 0.5f));

        NodeEditor.SetCurrentEditor(context);
        NodeEditor.Begin("Node Editor");

        var cursorScreenPos = Objects.requireNonNull(ImGui.GetCursorScreenPos());
        var availableSize = Objects.requireNonNull(ImGui.GetContentRegionAvail());

        float infoPaneWidth = (1 / 4f) * availableSize.get_x();
        float nodePaneWidth = availableSize.get_x() - infoPaneWidth;
        float paneHeight = availableSize.get_y();

        ImGui.SetNextWindowPos(cursorScreenPos);
        ImGui.SetNextWindowSize(ImVec2.TMP_1.set(infoPaneWidth, paneHeight));
        infoPane.render();

        ImGui.SameLine();

        ImGui.SetNextWindowPos(ImVec2.TMP_2.set(cursorScreenPos.get_x() + infoPaneWidth, cursorScreenPos.get_y()));
        ImGui.SetNextWindowSize(ImVec2.TMP_3.set(nodePaneWidth, paneHeight), ImGuiCond.ImGuiCond_Always);
        nodePane.render();

        // TODO(brian): native crash here, not sure the cause yet
        NodeEditor.End();

        ImGui.PopStyleVar(2);
        ImGui.End();
    }
}
