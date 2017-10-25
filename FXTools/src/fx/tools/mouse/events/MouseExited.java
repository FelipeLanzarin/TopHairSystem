package fx.tools.mouse.events;

import fx.tools.action.EventAction;
import fx.tools.utils.FXUtilsControl;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class MouseExited implements EventHandler<Event>{
	
	private EventAction eventAction;
	
	public  MouseExited(EventAction eventAction) {
		this.eventAction = eventAction;
	}
	
	@Override
	public void handle(Event event) {
		if(event.getEventType().equals(MouseEvent.MOUSE_EXITED)){
			Scene scene = FXUtilsControl.getScene();
			if(scene != null){
				scene.setCursor(Cursor.DEFAULT);
				if(eventAction != null){
					eventAction.action();
				}
			}else{
				System.out.println("scene null");
			}
		}else{
			System.err.println("This event not is MOUSE_ENTERED. This event is "+ event.getEventType());
		}
		
	}

}
