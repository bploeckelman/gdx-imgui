package imgui.example.nodeeditor.blueprintv2;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class NodeDesc {

    public String type;
    public int color = Color.WHITE.toIntBits();
    public List<PinDesc> inputs = new ArrayList<>();
    public List<PinDesc> outputs = new ArrayList<>();
    public NodeProperties props = new NodeProperties();

}
