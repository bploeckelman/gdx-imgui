package com.github.xpenatan.imgui.example.basic;

import com.badlogic.gdx.ScreenAdapter;
import imgui.ImGuiLoader;

public class ImGuiInitScreen extends ScreenAdapter {

    private ImGuiGame game;

    private boolean init = false;

    public ImGuiInitScreen(ImGuiGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        ImGuiLoader.init(() -> init = true);
    }

    @Override
    public void render(float delta) {
        if(init) {
            init = false;
            game.setScreen(new BasicExample());
        }
    }
}
