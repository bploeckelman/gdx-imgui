package com.xpenatan.imgui.enums;

/**
 * User fill ImGuiIO.KeyMap[] array with indices into the ImGuiIO.KeysDown[512] array
 */
public enum ImGuiKey {
	Tab(0),
	LeftArrow(1),
	RightArrow(2),
	UpArrow(3),
	DownArrow(4),
	PageUp(5),
	PageDown(6),
	Home(7),
	End(8),
	Insert(9),
	Delete(10),
	Backspace(11),
	Space(12),
	Enter(13),
	Escape(14),
	A(15),
	C(16),
	V(17),
	X(18),
	Y(19),
	Z(20),
	COUNT(21);

	private final int code;

	private ImGuiKey(int code) {
		this.code = code;
	}

	public int toInt() {
		return code;
	}
}
