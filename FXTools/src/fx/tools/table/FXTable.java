package fx.tools.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


/**
 * Classe para facilitar a criacao de tabelas e manipular de uma maneira melhor
 * @author Felipe Lanazarin
 *
 */

public class FXTable {
	private List<Pane[]>rowns;
	private Pane paneTable;
	private Double layoutXInit;
	private Double layoutYInit;
	private Double sizeRowns;
	private Double[] sizeColumns;
	private String style;
	private String font;
	private Double sizeFont;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public FXTable() {
		rowns = new ArrayList<>();
	}
	
	/**
	 * Classe responsavel por escrever uma linha na tabela
	 * @param columns array de colunas
	 * @param event cada coluna pode ter eventos, crie um map contendo os eventos e adicione no item correspondente
	 */
	public void addRown(Object[] columns, Map<String,EventHandler<Event>>[] event ){
		Double layoutY = 0.0;
		if(rowns.size()>0){
			Double lastLayoutY = rowns.get(rowns.size()-1)[0].getLayoutY();
			Double lastSizeRown = rowns.get(rowns.size()-1)[0].getPrefHeight();
			layoutY = (lastLayoutY + lastSizeRown) - 1;
		}else{
			layoutY = layoutYInit;
		}
		Pane[] columnsPane = new Pane[columns.length];
		System.out.println("Start for = " +  sdf.format(new Date()));
		for(int i=0; i < columns.length; i++){
			Pane pane= null;
			if(i==0){
				pane = newColumn(columns[i], layoutXInit, layoutY, sizeColumns[i]);
			}else{
				Double layoutX = defineLayoutX(columnsPane[i-1].getLayoutX(), columnsPane[i-1].getPrefWidth());
				pane = newColumn(columns[i], layoutX, layoutY, sizeColumns[i]);
			}
			pane.setVisible(true);
			paneTable.getChildren().add(pane);
			columnsPane[i] = pane;
		}
		System.out.println("Finshi for = " +  sdf.format(new Date()));
		rowns.add(columnsPane);
		paneTable.setPrefHeight(layoutY+sizeRowns+20.0);
	}
	
	private Double defineLayoutX(Double layoutXBefore, Double widthBefore){
		return layoutXBefore + widthBefore - 1;
	}
	
	/**
	 * Cria uma coluna especifica e devolve o pane com aquela coluna
	 * @param column
	 * @param layoutX
	 * @param layoutY
	 * @param width
	 * @return
	 */
	private Pane newColumn(Object column, Double layoutX, Double layoutY, Double width){
		System.out.println("Start newColumn = " +  sdf.format(new Date()));
		Pane pane = new Pane();
		pane.setLayoutX(layoutX);
		pane.setLayoutY(layoutY);
		pane.setPrefHeight(sizeRowns);
		pane.setPrefWidth(width);
		pane.setStyle(style);
		if(column instanceof String){
			Label l = new Label();
			l.setLayoutX(10);
			l.setPrefHeight(sizeRowns);
			l.setPrefWidth(width);
			l.setText(column.toString());
			if(font != null){
				Double sFont = 0.0;
				if(sFont != null){
					sFont = sizeFont;
				}
				Font f = new Font(font, sFont);
				l.setFont(f);
			}
			pane.getChildren().add(l);
		}else if(column instanceof Button[]){
			Button[] butons = (Button[]) column;
			for (Button b : butons) {
				pane.getChildren().add(b);
			}
		}else if(column instanceof ImageView){
			ImageView img = (ImageView) column;
			img.setLayoutX(10);
			img.setLayoutY(10.0);
			pane.getChildren().add(img);
		}
		System.out.println("Finish newColumn = " + sdf.format(new Date()));
		return pane;
	}
	
	
	public List<Pane[]> getRowns() {
		return rowns;
	}

	public void setRowns(List<Pane[]> rowns) {
		this.rowns = rowns;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Double getSizeFont() {
		return sizeFont;
	}

	public void setSizeFont(Double sizeFont) {
		this.sizeFont = sizeFont;
	}

	public Pane getPaneTable() {
		return paneTable;
	}
	public void setPaneTable(Pane paneTable) {
		this.paneTable = paneTable;
	}
	public Double getLayoutXInit() {
		return layoutXInit;
	}
	public void setLayoutXInit(Double layoutXInit) {
		this.layoutXInit = layoutXInit;
	}
	public Double getLayoutYInit() {
		return layoutYInit;
	}
	public void setLayoutYInit(Double layoutYInit) {
		this.layoutYInit = layoutYInit;
	}
	public Double getSizeRowns() {
		return sizeRowns;
	}
	public void setSizeRowns(Double sizeRowns) {
		this.sizeRowns = sizeRowns;
	}
	public Double[] getSizeColumns() {
		return sizeColumns;
	}
	public void setSizeColumns(Double[] sizeColumns) {
		this.sizeColumns = sizeColumns;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
}
