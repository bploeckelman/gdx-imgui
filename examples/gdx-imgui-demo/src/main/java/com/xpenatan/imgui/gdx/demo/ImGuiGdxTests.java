package com.xpenatan.imgui.gdx.demo;

import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.tests.utils.GdxTests;
import com.xpenatan.imgui.DrawData;
import com.xpenatan.imgui.ImGui;
import com.xpenatan.imgui.ImGuiInt;
import com.xpenatan.imgui.gdx.ImGuiGdxImpl;
import com.xpenatan.imgui.gdx.ImGuiGdxInput;
import com.xpenatan.imgui.gdx.ImGuiGdxInputMultiplexer;
import com.xpenatan.imgui.gdx.utils.EmuFrameBuffer;
import com.xpenatan.imgui.gdx.widgets.ImGuiGdxGameWindow;

/**
 *
 * Requires Gdx-test
 *
 */
public class ImGuiGdxTests implements ApplicationListener, InputProcessor {


	public static void main(String[] args) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 900;
		config.title = "ImGui-Gdx-tests";
		config.vSyncEnabled = true;
		new LwjglApplication(new ImGuiGdxTests(), config);
	}

	ImGuiGdxImpl impl;

	ImGuiGdxGameWindow gameWindow;

	int i = 0;

	boolean gdxTestInit = false;

	List<String> names;
	ImGuiInt listSelected = new ImGuiInt();
	int selected = -1;
	@Override
	public void create() {
		ImGui.init();

		ImGuiGdxInput input = new ImGuiGdxInput();
		impl = new ImGuiGdxImpl(input);
		ImGuiGdxInputMultiplexer inputMultiplexer = new ImGuiGdxInputMultiplexer(input);

		Gdx.input.setInputProcessor(input);

		EmuFrameBuffer.setDefaultFramebufferHandleInitialized(false);

		gameWindow = new ImGuiGdxGameWindow(400, 400);

		gameWindow.setName("Game");

		inputMultiplexer.addProcessor(gameWindow.getInput());

		InputMultiplexer inputt = new InputMultiplexer();

		inputt.addProcessor(inputMultiplexer);
		inputt.addProcessor(this);

		Gdx.input.setInputProcessor(inputt);

		names = GdxTests.getNames();
	}


	private void drawTestListWindow() {
		if(!gdxTestInit) {
			gdxTestInit = true;
			ImGui.SetNextWindowSize(200, 500);
		}
		ImGui.Begin("GdxTests");
		ImGui.BeginChildFrame(313, 0f, 0f);
		for(int i = 0; i < names.size(); i++) {
			String testName = names.get(i);
			boolean isSelected = selected == i;
			if(ImGui.Selectable(testName, isSelected)) {
				if(selected != i) {
					selected = i;
					GdxTest newTest = GdxTests.newTest(testName);
					gameWindow.setApplicationListener(newTest);
				}
			}
		}
		ImGui.EndChildFrame();
		ImGui.End();
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		int backBufferWidth = Gdx.graphics.getBackBufferWidth();
		int backBufferHeight = Gdx.graphics.getBackBufferHeight();

		boolean mouseDown0 = Gdx.input.isButtonPressed(Buttons.LEFT);
		boolean mouseDown1 = Gdx.input.isButtonPressed(Buttons.RIGHT);
		boolean mouseDown2 = Gdx.input.isButtonPressed(Buttons.MIDDLE);

		ImGui.UpdateDisplayAndInputAndFrame(Gdx.graphics.getDeltaTime(), width, height, backBufferWidth, backBufferHeight,
				Gdx.input.getX(), Gdx.input.getY(), mouseDown0, mouseDown1, mouseDown2);


		drawTestListWindow();
		gameWindow.render();

		ImGui.End();

		ImGui.Render();
		DrawData drawData = ImGui.GetDrawData();
		impl.render(drawData);
	}


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}


	@Override
	public boolean keyDown(int keycode) {


		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
