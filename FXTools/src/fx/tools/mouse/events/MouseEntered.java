package fx.tools.mouse.events;



import fx.tools.action.EventAction;
import fx.tools.utils.FXUtilsControl;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


/**
 * 
 * Classe responsável por obter a acao MouseEntered e acionar o action da classe implementada passada por parametro no metodo construtor
 * 
 * @author Felipe Lanzarin
 *
 */
public class MouseEntered implements EventHandler<Event>{
	
	private EventAction eventAction;
	
	public  MouseEntered(EventAction eventAction) {
		this.eventAction = eventAction;
	}
	
	@Override
	public void handle(Event event) {
		if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
			Scene scene = FXUtilsControl.getScene();
			if(scene != null){
				scene.setCursor(Cursor.HAND);
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
