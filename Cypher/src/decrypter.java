
public class decrypter {
	String word;
	String key;
	double num;
	int sum;
	public decrypter(String s, String k){
		word = s;
		key = k;
	}
	public String decrypt(){
		String outcode = "";
		int[] wcode;
		int[] kcode;
		wcode = new int[word.length()];
		wcode = convertchar(word);
		kcode = new int[key.length()];
		kcode = convertchar(key);
		for(int i = 0 ; i<key.length();i++){
			wcode = subtractthrough(wcode);
			for(int k = 0; k<word.length();k++){
				wcode[k] -= kcode[i];
			}
		}
		for(int i = 0; i<word.length();i++){
			while (wcode[i]<0){
				wcode[i] = wcode[i] + 37;
			}
		}
		for(int i = 0; i<word.length();i++){
			outcode += convertint(wcode[i]);
		}
		return outcode;
	}
	public int[] subtractthrough(int[] a){
		for(int i = 1; i<= (a.length - 1 );i++){
			a[i] = a[i] - a[i-1];
		}
		return a;
	}
	public int[] convertchar(String s){
		int[] array;
		array = new int[s.length()];
		for(int i=0; i< s.length();i++){
			if (s.charAt(i) == '1'){
				array[i] = 1;
			}else if (s.charAt(i) == '2'){
				array[i] = 2;
			}else if (s.charAt(i) == '3'){
				array[i] = 3;
			}else if (s.charAt(i) == '4'){
				array[i] = 4;
			}else if (s.charAt(i) == '5'){
				array[i] = 5;
			}else if (s.charAt(i) == '6'){
				array[i] = 1;
			}else if (s.charAt(i) == '7'){
				array[i] = 7;
			}else if (s.charAt(i) == '8'){
				array[i] = 8;
			}else if (s.charAt(i) == '9'){
				array[i] = 9;
			}else if (s.charAt(i) == '0'){
				array[i] = 10;
			}else if (s.charAt(i) == 'a'){
				array[i] = 11;
			}else if (s.charAt(i) == 'b'){
				array[i] = 12;
			}else if (s.charAt(i) == 'c'){
				array[i] = 13;
			}else if (s.charAt(i) == 'd'){
				array[i] = 14;
			}else if (s.charAt(i) == 'e'){
				array[i] = 15;
			}else if (s.charAt(i) == 'f'){
				array[i] = 16;
			}else if (s.charAt(i) == 'g'){
				array[i] = 17;
			}else if (s.charAt(i) == 'h'){
				array[i] = 18;
			}else if (s.charAt(i) == 'i'){
				array[i] = 19;
			}else if (s.charAt(i) == 'j'){
				array[i] = 20;
			}else if (s.charAt(i) == 'k'){
				array[i] = 21;
			}else if (s.charAt(i) == 'l'){
				array[i] = 22;
			}else if (s.charAt(i) == 'm'){
				array[i] = 23;
			}else if (s.charAt(i) == 'n'){
				array[i] = 24;
			}else if (s.charAt(i) == 'o'){
				array[i] = 25;
			}else if (s.charAt(i) == 'p'){
				array[i] = 26;
			}else if (s.charAt(i) == 'q'){
				array[i] = 27;
			}else if (s.charAt(i) == 'r'){
				array[i] = 28;
			}else if (s.charAt(i) == 's'){
				array[i] = 29;
			}else if (s.charAt(i) == 't'){
				array[i] = 30;
			}else if (s.charAt(i) == 'u'){
				array[i] = 31;
			}else if (s.charAt(i) == 'v'){
				array[i] = 32;
			}else if (s.charAt(i) == 'w'){
				array[i] = 33;
			}else if (s.charAt(i) == 'x'){
				array[i] = 34;
			}else if (s.charAt(i) == 'y'){
				array[i] = 35;
			}else if (s.charAt(i) == 'z'){
				array[i] = 36;
			}else if (s.charAt(i) == ' '){
				array[i] = 37;
			}
		}
		return array;
	}
	public String convertint(int i){
		if(i==1){
			return "1";
		}else if(i==2){
			return "2";
		}else if(i==3){
			return "3";
		}else if(i==4){
			return "4";
		}else if(i==5){
			return "5";
		}else if(i==6){
			return "6";
		}else if(i==7){
			return "7";
		}else if(i==8){
			return "8";
		}else if(i==9){
			return "9";
		}else if(i==10){
			return "0";
		}else if(i==11){
			return "a";
		}else if(i==12){
			return "b";
		}else if(i==13){
			return "c";
		}else if(i==14){
			return "d";
		}else if(i==15){
			return "e";
		}else if(i==16){
			return "f";
		}else if(i==17){
			return "g";
		}else if(i==18){
			return "h";
		}else if(i==19){
			return "i";
		}else if(i==20){
			return "j";
		}else if(i==21){
			return "k";
		}else if(i==22){
			return "l";
		}else if(i==23){
			return "m";
		}else if(i==24){
			return "n";
		}else if(i==25){
			return "o";
		}else if(i==26){
			return "p";
		}else if(i==27){
			return "q";
		}else if(i==28){
			return "r";
		}else if(i==29){
			return "s";
		}else if(i==30){
			return "t";
		}else if(i==31){
			return "u";
		}else if(i==32){
			return "v";
		}else if(i==33){
			return "w";
		}else if(i==34){
			return "x";
		}else if(i==35){
			return "y";
		}else if(i==36){
			return "z";
		}else if(i==37){
			return " ";
		}
		return "j";
	}
	
}
