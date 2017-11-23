package br.ths.tools.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogTools {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final SimpleDateFormat sdfName = new SimpleDateFormat("dd-MM-yyyy");
	private static final String ROOT_PATCH = "logs/";
	
	public static void logDebug(String log){
		String debug = generateLog() + " Debug ** " + log;
		writeLog(debug);
	}
	
	public static void logError(String log){
		String error = generateLog() + " Error ** " + log;
		writeLog(error);
	}
	
	public static void logError(Exception e){
		StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    e.printStackTrace(pw);
	    sw.toString();
	    String error = generateLog() + " Error ** " + sw.toString();
	    writeLog(error);
	}
	
	public static void logWarning(String log){
		String warning = generateLog() + " Warning ** " + log;
		writeLog(warning);
	}
	
	private static String generateLog(){
		String log = new String();
		Date data = new Date();
		log= sdf.format(data);
		return log;
	}
	
	private static List<String>read(){
		Date data = new Date();
		
		File files = new File(ROOT_PATCH);
		if (!files.exists()) {
			if (!files.mkdirs()) {
				return null;
			}
		}
		
		String name= ROOT_PATCH+"log " + sdfName.format(data)+".log";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(name), "UTF-8"));
			List<String> listLog = new ArrayList<>();
			while(br.ready()){ 
		   		String linha = br.readLine(); 
		   		listLog.add(linha);
			}
			return listLog;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private static void writeLog(String log){
        try {
        	System.out.println(log);
        	Date data = new Date();
    		String name= "logs/log " + sdfName.format(data)+".log";
    		List<String> logs = read();
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name),"UTF-8"));
            if(logs != null){
            	logs.add(log);
            	for (String string : logs) {
					fw.write(string);
					fw.write("\n");
				}
            }else{
            	fw.write(log);
            }
            fw.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
