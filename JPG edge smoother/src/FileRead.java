import java.io.*;
import java.util.ArrayList;
import cs1.*;
class FileRead{
	public FileRead(){

	}
	public ArrayList<String> readFile(ArrayList<String> Paths){
		String Path;
		ArrayList<String> output = new ArrayList<String>();
		//this code is designed to work on different systems so because path information is just a String, the program
		//runs through a list of options, trying out each one
		for(int i = 0; i < Paths.size(); i++){
			Path = Paths.get(i);
			try{
				// Create a new FileInoutStream, with the path from the Paths ArrayList
				FileInputStream fstream = new FileInputStream(Path);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				//buffer the file with an InputStreamReader
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				//Read File Line By Line from the buffered state
				while ((strLine = br.readLine()) != null)   {
					//add each line of text to the output ArrayList of Strings
					output.add(strLine);
				}
				//Close the input stream - good for safety
				in.close();
				return output;
			}catch (Exception e){/*System.err.println("Error: " + e.getMessage());*/}
		}
		return output;
	}
	public String writeFile(ArrayList<String> Paths, String outputText){

		String endPath  = "";
		boolean error = true;
		//System.out.println("location 1");
		for(int i = 0; i < Paths.size() && error; i++){
			endPath = Paths.get(i);
			try{
				File f;
				f=new File(endPath);
				if(!f.exists()){
					f.createNewFile();
					System.out.println("Please note, a new settings file was created at " + endPath + " using default values\nThe program will now quit so that" +
							" you may enter the desired values, the defaults are there to serve only as examples");
				}
			} catch (Exception e){//Catch exception if any
				//System.err.println("Error: " + e.getMessage());
			}
			//endPath = Paths.get(i);
			//System.out.println("location 2");
			try{
				// Create file 
				FileWriter fstream = new FileWriter(endPath);
				BufferedWriter out = new BufferedWriter(fstream);
				//System.out.println(endPath.charAt(0));
				if(endPath.charAt(0) == 'c'){
					PrintWriter Pout = new PrintWriter(fstream);
					writeForWindows(outputText,Pout);
				}else{out.write(outputText);}
				error = false;
				//System.out.println("location 3");
				//Close the output stream
				out.close();
			}catch (Exception e){//Catch exception if any
				//System.err.println("Error: " + e.getMessage());
			}
			if(i+1==Paths.size() && error){ //hrm, I was thinking about this and I can solve it, let the user enter in the program a new file path and then use
				//the last option as an 'extra file path' variable and then append the new file path, but each time they run the program they'll need to renter the path
				System.out.println("No preferences file was found and the Program was unable to generate a valid file. \n" +
						"There are possible reasons for this: \nthe jar file was not run from the super user, which can be solved by adding a \"sudo\" in front of the " +
						"command\nYour system does not support the file system presets of this program, which can be solved by adding a new file path in the source" +
						"code, and then recompiling \n\n");
				while (error){
					System.out.println("For the present, please enter a file path in your system and the program will try and write to it, but" +
							" every time this program is run, you will have to enter the location again");
					String newPath = Keyboard.readString();
					try{
						FileInputStream fstream = new FileInputStream(newPath);
						DataInputStream in = new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						while (( br.readLine()) != null) {
							//just wanted to force it to read the lines here to ensure it's a file
						}
						in.close();
						System.out.println("A file was detected at the entered location; the program will procede with values drawn from it");
						return "-99" +newPath;
					}catch (Exception e){//Catch exception if any
						//System.err.println("Error: " + e.getMessage());
					}
					try{
						File f;
						f=new File(newPath);
						f.createNewFile();
						System.out.println("Please note, a new settings file was created at " + newPath + " using default values\nThe program will now quit so that" +
								" you may enter the desired values, the defaults are there to serve only as examples");
						FileWriter fstream = new FileWriter(newPath);
						BufferedWriter out = new BufferedWriter(fstream);
						if(newPath.charAt(0) == 'c'){
							PrintWriter Pout = new PrintWriter(fstream);
							writeForWindows(outputText, Pout);
						}
						out.write(outputText);
						error = false;
						out.close();
						return "-98" + newPath;
					}catch (Exception e){error = true;System.out.println("Not a valid path");}}
			}
		}
		return endPath;

	}
	public String writeFile(ArrayList<String> Paths, ArrayList<String> outputText){

		String endPath  = "";
		boolean error = true;
		//System.out.println("location 1");
		for(int i = 0; i < Paths.size() && error; i++){
			endPath = Paths.get(i);
			try{
				File f;
				f=new File(endPath);
				if(!f.exists()){
					f.createNewFile();
					System.out.println("Please note, a new settings file was created at " + endPath + " using default values\nThe program will now quit so that" +
							" you may enter the desired values, the defaults are there to serve only as examples");
				}
			} catch (Exception e){//Catch exception if any
				//System.err.println("Error: " + e.getMessage());
			}
			String printText = "";
			if(endPath.charAt(0) =='c'){
				for(int j = 0; j<outputText.size();j++){
					printText +=  outputText.get(j) + "\r\n";
				}
			}else{
				for(int j = 0; j<outputText.size();j++){
					printText +=  outputText.get(j) +"\n";
				}
			}
			try{
				// Create file 
				FileWriter fstream = new FileWriter(endPath);
				BufferedWriter out = new BufferedWriter(fstream);
				System.out.println(endPath.charAt(0));
								out.write(printText);
								error = false;
				//System.out.println("location 3");
				//Close the output stream
				out.close();
			}catch (Exception e){//Catch exception if any
				//System.err.println("Error: " + e.getMessage());
			}
			if(i+1==Paths.size() && error){ //hrm, I was thinking about this and I can solve it, let the user enter in the program a new file path and then use
				//the last option as an 'extra file path' variable and then append the new file path, but each time they run the program they'll need to renter the path
				System.out.println("No preferences file was found and the Program was unable to generate a valid file. \n" +
						"There are possible reasons for this: \nthe jar file was not run from the super user, which can be solved by adding a \"sudo\" in front of the " +
						"command\nYour system does not support the file system presets of this program, which can be solved by adding a new file path in the source" +
						"code, and then recompiling \n\n");
				while (error){
					System.out.println("For the present, please enter a file path in your system and the program will try and write to it, but" +
							" every time this program is run, you will have to enter the location again");
					String newPath = Keyboard.readString();
					try{
						FileInputStream fstream = new FileInputStream(newPath);
						DataInputStream in = new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						while (( br.readLine()) != null) {
							//just wanted to force it to read the lines here to ensure it's a file
						}
						in.close();
						System.out.println("A file was detected at the entered location; the program will procede with values drawn from it");
						return "-99" +newPath;
					}catch (Exception e){//Catch exception if any
						//System.err.println("Error: " + e.getMessage());
					}
					try{
						File f;
						f=new File(newPath);
						f.createNewFile();
						System.out.println("Please note, a new settings file was created at " + newPath + " using default values\nThe program will now quit so that" +
								" you may enter the desired values, the defaults are there to serve only as examples");
						FileWriter fstream = new FileWriter(newPath);
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(printText);
						error = false;
						out.close();
						return "-98" + newPath;
					}catch (Exception e){error = true;System.out.println("Not a valid path");}}
			}
		}
		return endPath;

	}
	public String convertForWindows(String s){
		//I have some safety issues here, it might be too grabby and change the wrong text
		s.replace("\\n", "Target Text, this is where a new line is being generated and for safety this is long, unlikely piece of text, if you read this, you are either very" +
				"lucky or very unlucky. Also the program is broken and you should tell Sam");
		s.replace("Target Text, this is where a new line is being generated and for safety this is long, unlikely piece of text, if you read this, you are either very" +
				"lucky or very unlucky. Also the program is broken and you should tell Sam", "\\r\\n");
		return s;
	}
	public void writeForWindows(String s, PrintWriter out){
		int lastvalue =0;
		for(int i = 0 ; i < s.length()-2; i++){
			if(s.charAt(i) == '\\' && s.charAt(i+1) == 'n'){
				out.println(s.substring(lastvalue, i-1));
				out.write(s.substring(lastvalue, i -1));
				lastvalue = i + 1;
			}
		}
		out.close();
	}

}