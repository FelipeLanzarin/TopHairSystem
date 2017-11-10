package fx.tools.mask;

import java.util.ArrayList;

import fx.tools.controller.GenericController;
import javafx.scene.control.TextField;

/**
 *
 * @author David Augusto
 * @version 1.0
 * 
 * Email: davidaug23.7@gmail.com
 * Site: www.david.blog.br
 * 
 * Please read the readme in 
 * 
 * Refatorado por Felipe Lanzarin.
 * 
 * Os caracteres fixos não precisam mais serem digitados. 
 * O próprio componente irá acrescentálos.
 * 
 * OBS: NÃO FUNCIONA COM MAIS DE UM CARACTER FIXO EM SEQUENCIA.
 * Ex: NN..NN, NN123NN
 * 
 * Set the controll for alert of wrong
 * 
 * 
 * 
 */
public class MaskTextField extends TextField {

	private static final String CHARACTERS = "*SPMANLUl";
	
	private String mask;
	private String originalMask;
    private ArrayList<String> patterns;
    private GenericController genericController;
    
    public MaskTextField() {
        super();
        patterns = new ArrayList<String>();
    }

    public MaskTextField(String text) {
        super(text);
        patterns = new ArrayList<String>();
    }

    @Override
    public void replaceText(int start, int end, String text) {

        String tempText = this.getText() + text;
        if(mask == null || mask.length() == 0){
            super.replaceText(start, end, text);
        }else if (tempText.matches(this.mask) || tempText.length() == 0 || text.length() == 0) {        //text.length == 0 representa o delete ou backspace

            super.replaceText(start, end, text);

        } else {
        	boolean isCrtlV = false;
        	if(text.length() == 1){//nao eh ctrl+v
	        	text = acresCaracter(tempText, this.getText(), text);
	        	tempText = this.getText() + text;
        	}else{// eh ctrl+v
        		isCrtlV = true;
        		tempText = formatCrtlV(tempText);
        		text = tempText.replace(this.getText(), "");
        	}
            String tempP = "^";
            
            boolean invalid = true;
            for (String patt : this.patterns) {

                tempP += patt;

                if (tempText.matches(tempP)) {

                    super.replaceText(start, end, text);
                    invalid = false;
                    break;

                }

            }
            if(invalid && isCrtlV){
            	genericController.alert("Formato Invalido para colar no campo "+this.getId().replaceAll("text", ""));
            }

        }

    }
    
    private String formatCrtlV(String text){
    	 
    	String textReturn = text;
    	for (int i = 0; i < originalMask.length(); ++i) {//percorre a mascara
            String temp = originalMask.charAt(i)+"";
            if(textReturn.length() > i){
            	if(!CHARACTERS.contains(temp)){// nesse indice tem um caractere fixo
            		String c = textReturn.charAt(i)+"";
            		if(!temp.equals(c)){//se o caractere fixo nao estiver no indice, o sistema coloca 
            			if(textReturn.length()>1){
            				String preString = textReturn.substring(0, i);
            				String postString = textReturn.substring(i, textReturn.length());
            				textReturn = preString+ temp + postString;
            			}else{
            				textReturn = temp +textReturn;
            			}
            		}
            	}
            }else{
            	break;
            }
    	}
    	
    	return textReturn;
    }

    /**
     * @return the Regex Mask
     */
    public String getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(String mask) {
    	
    	originalMask = mask;
        String tempMask = "^";

        for (int i = 0; i < mask.length(); ++i) {

            char temp = mask.charAt(i);
            String regex;
            int counter = 1;
            int step = 0;

            if (i < mask.length() - 1) {
                for (int j = i + 1; j < mask.length(); ++j) {
                    if (temp == mask.charAt(j)) {
                        ++counter;
                        step = j;
                    } else if (mask.charAt(j) == '!') {
                        counter = -1;
                        step = j;
                    } else {
                        break;
                    }
                }
            }
            switch (temp) {

                case '*':
                    regex = ".";
                    break;
                case 'S':
                    regex = "[^\\s]";
                    break;
                case 'P':
                    regex = "[A-z.]";
                    break;
                case 'M':
                    regex = "[0-z.]";
                    break;
                case 'A':
                    regex = "[0-z]";
                    break;
                case 'N':
                    regex = "[0-9]";
                    break;
                case 'L':
                    regex = "[A-z]";
                    break;
                case 'U':
                    regex = "[A-Z]";
                    break;
                case 'l':
                    regex = "[a-z]";
                    break;
                case '.':
                    regex = "\\.";
                    break;
                default:
                    regex = Character.toString(temp);
                    break;

            }

            if (counter != 1) {

                this.patterns.add(regex);

                if (counter == -1) {
                    regex += "+";
                    this.patterns.add(regex);
                } else {
                    String tempRegex = regex;
                    for (int k = 1; k < counter; ++k) {
                        regex += tempRegex;
                        this.patterns.add(tempRegex);
                    }
                }

                i = step;

            } else {
                this.patterns.add(regex);
            }

            tempMask += regex;

        }

        this.mask = tempMask + "$";

    }
    
    private String acresCaracter(String text, String lastText, String newText){
    	if(text != null && text.length() > 0){
    		if(originalMask.length()>=text.length()){
				char c = originalMask.charAt(text.length()-1);
				String a = c+"";
				if(!CHARACTERS.contains(a)){
					return a+newText;
				}
    		}
		}
    	
    	return newText;
    }

	public GenericController getGenericController() {
		return genericController;
	}

	public void setGenericController(GenericController genericController) {
		this.genericController = genericController;
	}
    
}