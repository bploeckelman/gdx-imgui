package imgui.extension.textedit.example;

import com.badlogic.gdx.Game;

public class ImGuiGame extends Game {

    @Override
    public void create() {
        setScreen(new ImGuiInitScreen(this));
    }
}
