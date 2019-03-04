package learning;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.*;

public class WhatsAppAnalyzer {

	public static void main(String[] args) {
		
		Locale aLocale = new Locale.Builder().setLanguage("fr").setRegion("CA").build();
		System.out.println("Enter the path of the WhatsApp .txt file you want to analyse: ");
		Scanner input = new Scanner(System.in);
		String path = input.nextLine();
		String name1 = null, name2 = null;
		ArrayList<String> p1words = new ArrayList<String>();
		ArrayList<String> p2words = new ArrayList<String>();
		
		
		try {
			input = new Scanner(new FileInputStream(path),"UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
		}
		
		// Get names
		while(input.hasNext()){
			String word = input.next();
			if(word.matches("[A-Za-z]+:") && name1==null){
				name1 = word.substring(0, word.length()-1);
			}
			else if(word.matches("[A-Za-z]+:") && name1!=null && !word.equals(name1+":")){
				name2 = word.substring(0, word.length()-1);
				break;
			}
		}
		
		CharSequence n1 = name1;
		CharSequence n2 = name2;
		
		try {
			input = new Scanner(new FileInputStream(path), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
		}
		
		input.useDelimiter(",\\s\\d{1,2}:\\d{1,2}\\s[A-Za-z.]{4}\\s-\\s");
		
		
		int messageCount = 0, max = 0;
		String fullDate = input.next().substring(0,10), finalDate = "";
		String initialDay = fullDate.substring(8);
		
		while(input.hasNextLine()){
			String word = input.next();
			if(word.substring(word.length()-10).substring(8).equals(initialDay)){
				messageCount++;
				if(Character.isDigit(word.substring(word.length()-10).charAt(0)))
					fullDate = word.substring(word.length()-10);
			}
			else if(!word.substring(word.length()-10).substring(8).equals(initialDay)){
				if(messageCount > max){
					max = messageCount;
					finalDate = fullDate;
				}
				messageCount = 0;
				initialDay = word.substring(word.length()-2);
			}
			word = word.substring(0,word.length()-10);
			if(word.contains(n1))
				p1words.add(word.substring(name1.length()+2));
			else if(word.contains(n2))
				p2words.add(word.substring(name2.length()+2));
		}
		
		long p1messages = p1words.size();
		long p2messages = p2words.size();
		long sum = 0;
		long p1average = 0;
		long p2average = 0;
		String[] words = null;
		String[] words2 = null;
		ArrayList<String>allwords = new ArrayList<String>();
		ArrayList<String>allwords2 = new ArrayList<String>();
		
		// add words to arraylist
		for(int i = 0; i < p1messages; i++){
			words = p1words.get(i).split(" ");
			sum += words.length;
			for(int j = 0; j < words.length; j++){
				if(!isCommonWord(words[j]))
					allwords.add(words[j]);
			}
		}
	
		
		//for person 2
		for(int i = 0; i < p2messages; i++){
			words = p2words.get(i).split(" ");
			sum += words.length;
			for(int j = 0; j < words.length; j++){
				if(!isCommonWord(words[j]))
				allwords2.add(words[j]);
			}
		}
		
		p1average = sum/p1messages;
		sum = 0;
		
		for(int i = 0; i < p2messages; i++){
			words2 = p2words.get(i).split(" ");
			sum += words2.length;
		}
		p2average = sum/p2messages;
		
		int frequency = 0;
		String searchKey;
		ArrayList<Integer> freq = new ArrayList<Integer>(); // ArrayList to store the frequency of each word
		// Count frequency of each word
		for(int i = 0; i < allwords.size(); i++){

			frequency = 0; // Reset frequency
			searchKey = allwords.get(i).toLowerCase();

			// If the searchKey is found, increment the frequency
			for(int j = 0; j < allwords.size(); j++){
				if(allwords.get(j).toLowerCase().equals(searchKey)){
					frequency++;
					// If already found, remove element from the array so it can be printed without duplicates later
					if(frequency > 1)
						allwords.remove(j);
				}
			}

			// Once done counting, add to the frequency ArrayList
			freq.add(frequency);

		}


		// Sort ArrayList
		for(int i = 0; i < freq.size(); i++){
			for(int j = 0; j < freq.size(); j++){
				// Sort words by frequency in descending order
				if(freq.get(i) > freq.get(j)){
					int temp = freq.get(i);
					freq.set(i, freq.get(j));
					freq.set(j, temp);
					String temp2 = allwords.get(i);
					allwords.set(i, allwords.get(j));
					allwords.set(j, temp2);
				}
				// Sort words with the same frequency alphabetically
				if(freq.get(i) == freq.get(j)){
					if(allwords.get(i).compareTo(allwords.get(j))<0){
						String temp2 = allwords.get(i);
						allwords.set(i, allwords.get(j));
						allwords.set(j, temp2);
					}
				}
			}
		}
		
		// For person 2
		ArrayList<Integer> freq2 = new ArrayList<Integer>(); // ArrayList to store the frequency of each word
		// Count frequency of each word
		frequency = 0;
		for(int i = 0; i < allwords2.size(); i++){

			frequency = 0; // Reset frequency
			searchKey = allwords2.get(i);

			// If the searchKey is found, increment the frequency
			for(int j = 0; j < allwords2.size(); j++){
				if(allwords2.get(j).equals(searchKey)){
					frequency++;
					// If already found, remove element from the array so it can be printed without duplicates later
					if(frequency > 1)
						allwords2.remove(j);
				}
			}

			// Once done counting, add to the frequency ArrayList
			freq2.add(frequency);

		}


		// Sort ArrayList
		for(int i = 0; i < freq2.size()-1; i++){
			for(int j = 0; j < freq2.size(); j++){
				// Sort words by frequency in descending order
				if(freq2.get(i) > freq2.get(j)){
					int temp = freq2.get(i);
					freq2.set(i, freq2.get(j));
					freq2.set(j, temp);
					String temp2 = allwords2.get(i);
					allwords2.set(i, allwords2.get(j));
					allwords2.set(j, temp2);
				}
				// Sort words with the same frequency alphabetically
				if(freq2.get(i) == freq2.get(j)){
					if(allwords2.get(i).compareTo(allwords2.get(j))<0){
						String temp2 = allwords2.get(i);
						allwords2.set(i, allwords2.get(j));
						allwords2.set(j, temp2);
					}
				}
			}
		}
		
		
		System.out.println("\n--------------------RESULTS-----------------------\n");
		System.out.println(name1 + " has sent " + p1words.size() + " messages, compared to " + p2words.size() + " messages sent by " + name2);
		System.out.println((p1messages>p2messages)?name1+" has sent more messages than "+name2:name2+" has sent more messages than "+name1);
		System.out.println("The average word count of each of " + name1 + "'s messages is: " + p1average);
		System.out.println("The average word count of each of " + name2 + "'s messages is: " + p2average);
		System.out.println("The most messages sent in one day was " + max + " on " + finalDate);
		System.out.println("\n-------------------------------------------\n");
		System.out.println("Most frequent words used by " + name1 + ":");
		// Print words
		for(int i = 0; i < 30; i++)
			System.out.println((i+1) + "\t\t\t" + freq.get(i) + "\t\t\t" + allwords.get(i));
		
		System.out.println("\nMost frequent words used by " + name2 + ":");
		// Print words
		for(int i = 0; i < 30; i++)
			System.out.println((i+1) + "\t\t\t" + freq2.get(i) + "\t\t\t" + allwords2.get(i));
	

	}
	
	public static boolean isCommonWord(String a){
		
		String[] french = {"plus", "dit", "bon", "sur", "haha,", "n'est", "quand", "tout", "ont", "Oui", "cette", "as", "ça", "à", "n'ai", "ces", "par", "ils", "elles", "si", "dans", "oui,", "qui", "ou", "beaucoup", "me", "au", "j'ai", "c'est", "avec", "y", "ce", "sont", "on", "suis", "pour", "il", "comme", "mais", "du", "en", "est", "de", "je", "un", "une", "la", "le", "les", "des", "pas", "ne", "que", "et", "tu", "a"};
		String[] english = {"the", "to", "i", "I", "and", "you","is","of","that","it","in","he","for","be","at","have","not","but","we","me","this","was","my","with","about","what","it's","are","get","if","or","do"};

		for(int i = 0; i < french.length; i++){
			if(a.toLowerCase().equals(french[i]))
				return true;
		}
		for(int i = 0; i < english.length; i++){
			if(a.toLowerCase().equals(english[i]))
				return true;
		}
		return false;
		
	}
	

}
