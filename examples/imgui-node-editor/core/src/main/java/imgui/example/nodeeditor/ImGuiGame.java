package imgui.example.nodeeditor;

import com.badlogic.gdx.Game;

public class ImGuiGame extends Game {

    @Override
    public void create() {
        setScreen(new ImGuiInitScreen(this));
    }
}
