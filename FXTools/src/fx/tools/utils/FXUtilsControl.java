package fx.tools.utils;

import javafx.scene.Scene;

public class FXUtilsControl {
	
	private static Scene scene;

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		FXUtilsControl.scene = scene;
	}
}
