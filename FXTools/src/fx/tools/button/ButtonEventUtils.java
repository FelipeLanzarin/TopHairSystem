package fx.tools.button;

import fx.tools.mouse.MouseEnventControler;
import javafx.scene.control.Button;

public class ButtonEventUtils {
	
	public static void setEvents(Button button) {
		button.setOnMouseEntered(MouseEnventControler.getMouseEntered(null));
		button.setOnMouseExited(MouseEnventControler.getMouseExited(null));
	}
}
