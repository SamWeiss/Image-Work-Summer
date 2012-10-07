import cs1.Keyboard;
public class Main {
	public static void main ( String[] args){
		int selection = 0;
		String word;
		String key;
		String outcome;
		System.out.println("Welcome to this program, a simple enrcypter/decrypter for strings!");
		System.out.println("To encrypt, please enter \"1\" to encrypt, and \"2\" to decrypt");
		//while (!(selection != 1 || selection != 2)){
			selection = Keyboard.readInt();
		//}
		if (selection == 1){
			System.out.println("Please enter the word or phrase you wish to encrypt");
			word = Keyboard.readString();
			System.out.println("Please enter the key with which you wish to encrypt, or enter \"no key\" if you want a random key ");
			key = Keyboard.readString();
			if ((key.compareTo("no key")) == 0){
				key = "";
			}
			encrypter bot = new encrypter(word , key);
			key = bot.checkkey();
			outcome = bot.encrypt();
			System.out.println("your encrypted string is " + outcome + " and the key is " + key);
		}
		if (selection == 2){
			System.out.println("Please enter the word or phrase you wish to decrypt");
			word = Keyboard.readString();
			System.out.println("Please enter the key with which you wish to decrypt");
			key = Keyboard.readString();
			decrypter bot = new decrypter(word , key);
			outcome = bot.decrypt();
			System.out.println("your encrypted string is " + outcome + " and the key is " + key);
		}if (selection == 3){
			//use this area to test and see if the decrypter is working, especially the subtractthrough subroutine
		}
		
	}
}
