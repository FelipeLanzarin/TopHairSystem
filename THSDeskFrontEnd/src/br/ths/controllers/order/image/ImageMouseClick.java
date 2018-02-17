package br.ths.controllers.order.image;

import br.ths.beans.Image;
import br.ths.utils.THSFrontUtils;
import fx.tools.action.EventAction;
import javafx.scene.layout.Pane;

public class ImageMouseClick implements EventAction{
	
	private static String STYLE = "-fx-border-color: red; -fx-border-radius: 4; -fx-border-width: 5";
	private Pane pane;
	private ControllerImageRelationManager controller;
	private Image image;
	private Boolean after;
	@Override
	public void action() {
		if(pane != null){
			Pane lastPane = THSFrontUtils.getPaneMouseClicked();
			if(lastPane != null){
				lastPane.setStyle("");
			}
			if(after){
				controller.setSelectedImageAfter(image);
				controller.setSelectedImageBefore(null);
				controller.disableButtonAfter(false);
				controller.disableButtonBefore(true);
			}else{
				controller.setSelectedImageBefore(image);
				controller.setSelectedImageAfter(null);
				controller.disableButtonAfter(true);
				controller.disableButtonBefore(false);
			}
			pane.setStyle(STYLE);
			THSFrontUtils.setPaneMouseClicked(pane);
		}
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public ControllerImageRelationManager getController() {
		return controller;
	}

	public void setController(ControllerImageRelationManager controller) {
		this.controller = controller;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Boolean getAfter() {
		return after;
	}

	public void setAfter(Boolean after) {
		this.after = after;
	}
	
	
}
