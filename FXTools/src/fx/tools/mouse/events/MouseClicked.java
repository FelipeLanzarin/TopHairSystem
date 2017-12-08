package fx.tools.mouse.events;

import fx.tools.action.EventAction;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseClicked implements EventHandler<Event>{
	
	private EventAction eventAction;
	
	public  MouseClicked(EventAction eventAction) {
		this.eventAction = eventAction;
	}
	
	@Override
	public void handle(Event event) {
		if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
			if(eventAction != null){
				eventAction.action();
			}
		}else{
			System.err.println("This event not is MOUSE_ENTERED. This event is "+ event.getEventType());
		}
		
	}

}
