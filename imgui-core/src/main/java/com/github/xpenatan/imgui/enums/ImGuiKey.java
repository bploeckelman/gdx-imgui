package com.github.xpenatan.imgui.enums;

/**
 * User fill ImGuiIO.KeyMap[] array with indices into the ImGuiIO.KeysDown[512] array
 */
public class ImGuiKey {
    private static int ID = 512;

    // Keyboard
    public static int None = 0,
            Tab = ID++,             // == ImGuiKey_NamedKey_BEGIN
            LeftArrow = ID++,
            RightArrow = ID++,
            UpArrow = ID++,
            DownArrow = ID++,
            PageUp = ID++,
            PageDown = ID++,
            Home = ID++,
            End = ID++,
            Insert = ID++,
            Delete = ID++,
            Backspace = ID++,
            Space = ID++,
            Enter = ID++,
            Escape = ID++,
            LeftCtrl = ID++,
            LeftShift = ID++,
            LeftAlt = ID++,
            LeftSuper = ID++,
            RightCtrl = ID++,
            RightShift = ID++,
            RightAlt = ID++,
            RightSuper = ID++,
            Menu = ID++,
            NUM_0 = ID++, NUM_1 = ID++, NUM_2 = ID++, NUM_3 = ID++, NUM_4 = ID++, NUM_5 = ID++, NUM_6 = ID++, NUM_7 = ID++, NUM_8 = ID++, NUM_9 = ID++,
            A = ID++, B = ID++, C = ID++, D = ID++, E = ID++, F = ID++, G = ID++, H = ID++, I = ID++, J = ID++,
            K = ID++, L = ID++, M = ID++, N = ID++, O = ID++, P = ID++, Q = ID++, R = ID++, S = ID++, T = ID++,
            U = ID++, V = ID++, W = ID++, X = ID++, Y = ID++, Z = ID++,
            F1 = ID++, F2 = ID++, F3 = ID++, F4 = ID++, F5 = ID++, F6 = ID++,
            F7 = ID++, F8 = ID++, F9 = ID++, F10 = ID++, F11 = ID++, F12 = ID++,
            Apostrophe = ID++,        // '
            Comma = ID++,             // ,
            Minus = ID++,             // -
            Period = ID++,            // .
            Slash = ID++,             // /
            Semicolon = ID++,         // ;
            Equal = ID++,             // =
            LeftBracket = ID++,       // [
            Backslash = ID++,         // \ (this text inhibit multiline comment caused by backslash)
            RightBracket = ID++,      // ]
            GraveAccent = ID++,       // `
            CapsLock = ID++,
            ScrollLock = ID++,
            NumLock = ID++,
            PrintScreen = ID++,
            Pause = ID++,
            Keypad0 = ID++, Keypad1 = ID++, Keypad2 = ID++, Keypad3 = ID++, Keypad4 = ID++,
            Keypad5 = ID++, Keypad6 = ID++, Keypad7 = ID++, Keypad8 = ID++, Keypad9 = ID++,
            KeypadDecimal = ID++,
            KeypadDivide = ID++,
            KeypadMultiply = ID++,
            KeypadSubtract = ID++,
            KeypadAdd = ID++,
            KeypadEnter = ID++,
            KeypadEqual = ID++,

    // Gamepad (some of those are analog values, 0.0f to 1.0f)
    GamepadStart = ID++,          // Menu (Xbox)          + (Switch)   Start/Options (PS) // --
            GamepadBack = ID++,           // View (Xbox)          - (Switch)   Share (PS)         // --
            GamepadFaceUp = ID++,         // Y (Xbox)             X (Switch)   Triangle (PS)      // -> ImGuiNavInput_Input
            GamepadFaceDown = ID++,       // A (Xbox)             B (Switch)   Cross (PS)         // -> ImGuiNavInput_Activate
            GamepadFaceLeft = ID++,       // X (Xbox)             Y (Switch)   Square (PS)        // -> ImGuiNavInput_Menu
            GamepadFaceRight = ID++,      // B (Xbox)             A (Switch)   Circle (PS)        // -> ImGuiNavInput_Cancel
            GamepadDpadUp = ID++,         // D-pad Up                                             // -> ImGuiNavInput_DpadUp
            GamepadDpadDown = ID++,       // D-pad Down                                           // -> ImGuiNavInput_DpadDown
            GamepadDpadLeft = ID++,       // D-pad Left                                           // -> ImGuiNavInput_DpadLeft
            GamepadDpadRight = ID++,      // D-pad Right                                          // -> ImGuiNavInput_DpadRight
            GamepadL1 = ID++,             // L Bumper (Xbox)      L (Switch)   L1 (PS)            // -> ImGuiNavInput_FocusPrev + ImGuiNavInput_TweakSlow
            GamepadR1 = ID++,             // R Bumper (Xbox)      R (Switch)   R1 (PS)            // -> ImGuiNavInput_FocusNext + ImGuiNavInput_TweakFast
            GamepadL2 = ID++,             // L Trigger (Xbox)     ZL (Switch)  L2 (PS) [Analog]
            GamepadR2 = ID++,             // R Trigger (Xbox)     ZR (Switch)  R2 (PS) [Analog]
            GamepadL3 = ID++,             // L Thumbstick (Xbox)  L3 (Switch)  L3 (PS)
            GamepadR3 = ID++,             // R Thumbstick (Xbox)  R3 (Switch)  R3 (PS)
            GamepadLStickUp = ID++,       // [Analog]                                             // -> ImGuiNavInput_LStickUp
            GamepadLStickDown = ID++,     // [Analog]                                             // -> ImGuiNavInput_LStickDown
            GamepadLStickLeft = ID++,     // [Analog]                                             // -> ImGuiNavInput_LStickLeft
            GamepadLStickRight = ID++,    // [Analog]                                             // -> ImGuiNavInput_LStickRight
            GamepadRStickUp = ID++,       // [Analog]
            GamepadRStickDown = ID++,     // [Analog]
            GamepadRStickLeft = ID++,     // [Analog]
            GamepadRStickRight = ID++,    // [Analog]

    // Keyboard Modifiers
// - This is mirroring the data also written to io.KeyCtrl, io.KeyShift, io.KeyAlt, io.KeySuper, in a format allowing
//   them to be accessed via standard key API, allowing calls such as IsKeyPressed(), IsKeyReleased(), querying duration etc.
// - Code polling every keys (e.g. an interface to detect a key press for input mapping) might want to ignore those
//   and prefer using the real keys (e.g. ImGuiKey_LeftCtrl, ImGuiKey_RightCtrl instead of ImGuiKey_ModCtrl).
// - In theory the value of keyboard modifiers should be roughly equivalent to a logical or of the equivalent left/right keys.
//   In practice: it's complicated; mods are often provided from different sources. Keyboard layout, IME, sticky keys and
//   backends tend to interfere and break that equivalence. The safer decision is to relay that ambiguity down to the end-user...
    ModCtrl = ID++,
            ModShift = ID++,
            ModAlt = ID++,
            ModSuper = ID++,

    COUNT = ID++;                 // No valid ImGuiKey is ever greater than this value

    private final int code;

    private ImGuiKey(int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }
}
