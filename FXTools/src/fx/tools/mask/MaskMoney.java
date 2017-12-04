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
public class MaskMoney extends TextField {

	private static final String CHARACTERS = "*SPMANLUl";
	
	private String mask;
	private String originalMask;
	private String componentName;
    private ArrayList<String> patterns;
    private GenericController genericController;
    private String backupMask;
    private Boolean addSimbol;
    private Boolean deleteSimbol;
    
    public MaskMoney() {
        super();
        patterns = new ArrayList<String>();
    }

    public MaskMoney(String text) {
        super(text);
        patterns = new ArrayList<String>();
    }

    @Override
    public void replaceText(int start, int end, String text) {
    	
    	String tempText = this.getText() + text;
    	if(originalMask.length() < tempText.length()){
    		return;
    	}
        String oldText = this.getText();
    	addSimbol = false;
    	deleteSimbol = false;
        if(mask == null || mask.length() == 0){
            super.replaceText(start, end, text);
        }else if (tempText.matches(this.mask) || tempText.length() == 0 || text.length() == 0) {        //text.length == 0 representa o delete ou backspace
        	this.setText(pastSimbolToLeftIndex(this.getText()));
        	if(deleteSimbol){//tamanho da string diminuiu
        		start--;
        		end--;
        	}
            super.replaceText(start, end, text);
        } else {
        	if(text.length() == 1){//nao eh ctrl+v
        		oldText = pastSimbolToNextIndex(this.getText());
    			oldText = acresCaracterRightToLeft(tempText,  oldText);
    			this.setText(oldText);
    			tempText = oldText + text;
        	}else{// eh ctrl+v
         		genericController.alert("Operação não permitida");
         		return;
         	}
         	String tempP = "^";
             
         	backupMask = originalMask;
         	int init = originalMask.length() - tempText.length();
         	String newMask = originalMask.substring(init,  originalMask.length());
         	setMask(newMask);
            for (String patt : this.patterns) {

                tempP += patt;
                if (tempText.matches(tempP)) {
                	if(addSimbol){
                		 end++;
                		 start++;
                	}
                    super.replaceText(start, end, text);
                    break;

                }

            }
            setMask(backupMask);
       }
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
        this.patterns.clear();

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
                case '(':
                    regex = "\\(";
                    break;
                case ')':
                    regex = "\\)";
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
    
    private String acresCaracterRightToLeft(String text, String lastText){
    	if(text != null && text.length() > 0){
    		if(originalMask.length()>=text.length()){
				int ind = originalMask.length() - lastText.length() -1;
				char c = originalMask.charAt(ind); //rever isso aqui quando rightToLeft is true
				String a = c+"";
				if(!CHARACTERS.contains(a)){
					int end = lastText.length();
					String sub = lastText.substring(1,end);
					char pre = lastText.charAt(0);
					addSimbol = true;
					return pre+a+sub;
				}
    		}
		}
    	
    	return lastText;
    }
    
    private String pastSimbolToNextIndex(String text){
    	if(text.contains(",") || text.contains(".")){
    		for (int i=0 ; i<text.length() ; i++) {
				char word = text.charAt(i);
    			if(word == ',' || word == '.'){//passa os simbolos uma casa para a direita
    				char word2 = text.charAt(i+1);
    				StringBuilder stringBuilder = new StringBuilder(text);
    				stringBuilder.setCharAt(i, word2);
    				stringBuilder.setCharAt(i+1, word);
    				text = stringBuilder.toString();
    				i++;
				}
			}
    	}
    	return text;
    	
    }
    
    private String pastSimbolToLeftIndex(String text){
    	if(text.contains(",") || text.contains(".")){
    		for (int i=0 ; i<text.length() ; i++) {
				char word = text.charAt(i);
    			if(word == ',' || word == '.'){
    				if(i!=1){//passa os simbolos uma casa para a esquerda
	    				char word2 = text.charAt(i-1);
	    				StringBuilder stringBuilder = new StringBuilder(text);
	    				stringBuilder.setCharAt(i, word2);
	    				stringBuilder.setCharAt(i-1, word);
	    				text = stringBuilder.toString();
	    				i++;
    				}else{//remove o simbolo para ele nao ser o primeiro caractere da string
    					StringBuilder stringBuilder = new StringBuilder(text);
	    				stringBuilder.deleteCharAt(i);
	    				text = stringBuilder.toString();
	    				deleteSimbol = true;
    				}
				}
			}
    	}
    	return text;
    	
    }
    

	public GenericController getGenericController() {
		return genericController;
	}

	public void setGenericController(GenericController genericController) {
		this.genericController = genericController;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
    
}