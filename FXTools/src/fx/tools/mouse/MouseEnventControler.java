package fx.tools.mouse;

import fx.tools.action.EventAction;
import fx.tools.mouse.events.MouseClicked;
import fx.tools.mouse.events.MouseEntered;
import fx.tools.mouse.events.MouseExited;
import javafx.scene.Node;

/**
 * 
 * @author Felipe Lanzarin
 * 
 * Classe responsavel por retornar os enventos de um mouse
 *
 */
public class MouseEnventControler{
	
	/**
	 * Metodo responsavel quando o mouse passar por cima do objeto
	 * 
	 * Obs: Irá mudar o cursor para o Cursor.HAND.
	 * 
	 * @param eventAction - crie uma classe que implemente a classe {@link EventAction} e implemente a ação do evento no metodo action       
	 * @return
	 */
	public static MouseEntered getMouseEntered(EventAction eventAction) {
		return new MouseEntered(eventAction);
	}
	
	/**
	 * Metodo responsavel quando o mouse sair de cima do objeto
	 * 
	 * Obs: Irá mudar o cursor para o Cursor.DEFAULT.
	 * 
	 * @param eventAction - crie uma classe que implemente a classe {@link EventAction} e implemente a ação do evento no metodo action       
	 * @return
	 */
	public static MouseExited getMouseExited(EventAction eventAction) {
		return new MouseExited(eventAction);
	}
	
	/**
	 * Metodo responsavel quando o mouse sair de cima do objeto
	 * 
	 * Obs: Irá mudar o cursor para o Cursor.DEFAULT.
	 * 
	 * @param eventAction - crie uma classe que implemente a classe {@link EventAction} e implemente a ação do evento no metodo action       
	 * @return
	 */
	public static MouseClicked getMouseCliked(EventAction eventAction) {
		return new MouseClicked(eventAction);
	}
	
	
	//usar como exemplo para cada tipo de item
	public static void setEvents(Node node){
		node.setOnMouseEntered(getMouseEntered(null));
		node.setOnMouseExited(getMouseExited(null));
	}

}
