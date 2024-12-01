package imgui.example.nodeeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import imgui.example.nodeeditor.demo.BlueprintExample;
import imgui.example.nodeeditor.demo.BlueprintV2Example;
import imgui.example.renderer.ImGuiRenderer;
import imgui.extension.nodeeditor.Config;
import imgui.extension.nodeeditor.EditorContext;
import imgui.extension.nodeeditor.LoadSaveSettingsListener;
import imgui.extension.nodeeditor.NodeEditor;
import imgui.idl.helper.IDLString;

public class NodeEditorExample extends ImGuiRenderer {

    private EditorContext editorContext;

    private BlueprintExample blueprintExample;
    private BlueprintV2Example blueprintV2Example;

    @Override
    public void show() {
        super.show();
        Config config  = new Config();
        editorContext = NodeEditor.CreateEditor(new LoadSaveSettingsListener() {
            @Override
            public void onLoad(IDLString data) {
//                Preferences preferences = Gdx.app.getPreferences("NodeEditorData");
//                String jsonData = preferences.getString("jsonData", "");
//                data.append(jsonData);
            }

            @Override
            public boolean onSave(IDLString data, int reason) {
                Preferences preferences = Gdx.app.getPreferences("NodeEditorData");
                String str = data.c_str();
                preferences.putString("jsonData", str);
                preferences.flush();
                return true;
            }
        });

//        blueprintExample = new BlueprintExample();
        blueprintV2Example = new BlueprintV2Example(editorContext);
    }

    @Override
    public void renderImGui() {
//        SimpleExample.render(editorContext);
//        blueprintExample.render(editorContext);

        blueprintV2Example.update();
        blueprintV2Example.render();
    }

    @Override
    public void dispose() {
        super.dispose();

        NodeEditor.DestroyEditor(editorContext);
    }
}