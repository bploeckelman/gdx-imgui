package imgui.example.nodeeditor.blueprintv2;

import com.badlogic.gdx.Gdx;
import imgui.ImGui;
import imgui.ImVec2;

public class EditorUtil {

    private static final String TAG = EditorUtil.class.getSimpleName();

    public static void rectFilled(float width, float height, int color, float roundingPercent) {
        var drawList = ImGui.GetWindowDrawList();
        var cursor = ImGui.GetCursorScreenPos();
        var lineHeight = ImGui.GetTextLineHeight();

        drawList.AddRectFilled(
                ImVec2.TMP_1.set(cursor.get_x(), cursor.get_y()),
                ImVec2.TMP_2.set(cursor.get_x() + width, cursor.get_y() + height),
                color, lineHeight * roundingPercent);
    }

    public static void openUrl(String url) {
        try {
            // Use libGDX's built-in browser opener
            Gdx.net.openURI(url);
        } catch (Exception e) {
            // Fallback method using Java's desktop integration
            try {
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                    if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                        desktop.browse(new java.net.URI(url));
                    }
                }
            } catch (Exception ex) {
                Gdx.app.error(TAG, String.format("Failed to open URL '%s': %s", url, ex.getMessage()));
            }
        }
    }
}
