package fx.tools.button;

import fx.tools.mouse.MouseEnventControler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class ButtonEventUtils {
	
	public static void setEvents(Button button, Scene scene) {
		button.setOnMouseEntered(MouseEnventControler.getMouseEntered(null, scene));
		button.setOnMouseExited(MouseEnventControler.getMouseExited(null,scene));
	}
}
