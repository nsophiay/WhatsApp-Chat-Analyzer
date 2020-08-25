
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.*;

public class WhatsAppAnalyzer {

	public static void main(String[] args) {

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
		
		long startTime = System.nanoTime();
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
		
		input.useDelimiter(",\\s\\d{1,2}:\\d{1,2}\\s[A-Za-z.]{4}\\s-\\s");
		
		
		int messageCount = 0, max = 0;
		String fullDate = input.next().substring(0,10), finalDate = "";
		String initialDay = fullDate.substring(8);
		int dayCount = 1;
		
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
				dayCount++;
				messageCount = 0;
				initialDay = word.substring(word.length()-2);
			}
			word = word.substring(0,word.length()-10);
			if(word.contains(n1)){
				p1words.add(word.substring(name1.length()+2));
			}
			else if(word.contains(n2)){
				p2words.add(word.substring(name2.length()+2));
			}
		}
		
		long p1messages = p1words.size();
		long p2messages = p2words.size();
		long sum = 0;
		long p1average = 0;
		long p2average = 0;
		String[] words = null;
		String[] words2 = null;

		Heap<Integer,String> h = new Heap<Integer,String>();
		Heap<Integer,String> h2 = new Heap<Integer,String>();
		

		// add words to arraylist
		for(int i = 0; i < p1messages; i++){
			words = p1words.get(i).split("([,\\?]?\\s+)+");
			sum += words.length;
			for(int j = 0; j < words.length; j++){
				if(!isCommonWord(words[j])){
					if(h.findDuplicate(words[j])>=0){
						int dupPosition = h.findDuplicate(words[j]);
						int newKey = h.heap.get(dupPosition).getKey()+1;
						h.heap.get(dupPosition).setKey(newKey);
						if(h.hasLeft(dupPosition)){
							if(newKey > h.heap.get(h.left(dupPosition)).getKey())
								h.upheap(dupPosition);
							else
								h.downheap(dupPosition);
						}
						else
							h.upheap(dupPosition);
						
					}
					else{
						h.insert(1, words[j]);
					}
				}
			}
		}
	
		
		//for person 2
		for(int i = 0; i < p2messages; i++){
			words = p2words.get(i).split("([,\\?]?\\s+)+");
			sum += words.length;
			for(int j = 0; j < words.length; j++){
				if(!isCommonWord(words[j]))
					if(h2.findDuplicate(words[j])>=0){
						int dupPosition = h2.findDuplicate(words[j]);
						int newKey = h2.heap.get(dupPosition).getKey()+1;
						h2.heap.get(dupPosition).setKey(newKey);
						if(h2.hasLeft(dupPosition)){
							if(newKey > h2.heap.get(h2.left(dupPosition)).getKey())
								h2.upheap(dupPosition);
							else
								h2.downheap(dupPosition);
						}
						else{
							h2.upheap(dupPosition);
						}
					}
					else{
						h2.insert(1, words[j]);
					}
			}
		}
		
		p1average = sum/p1messages;
		sum = 0;
		
		for(int i = 0; i < p2messages; i++){
			words2 = p2words.get(i).split(" ");
			sum += words2.length;
		}
		p2average = sum/p2messages;
		

		
		long endTime = System.nanoTime();
		
		
		System.out.println("\n--------------------RESULTS " + (endTime-startTime)/1000000 + " ms-----------------------\n");
		System.out.println(name1 + " has sent " + p1words.size() + " messages, compared to " + p2words.size() + " messages sent by " + name2);
		System.out.println((p1messages>p2messages)?name1+" has sent more messages than "+name2:name2+" has sent more messages than "+name1);
		System.out.println("The average word count of each of " + name1 + "'s messages is: " + p1average);
		System.out.println("The average word count of each of " + name2 + "'s messages is: " + p2average);
		System.out.println(name1 + " sent " + (p1words.size()/dayCount) + " messages per day.");
		System.out.println(name2 + " sent " + (p2words.size()/dayCount) + " messages per day.");
		System.out.println("In total, " + ((p1words.size()+p2words.size())/dayCount) + " messages were sent per day.");
		System.out.println("The most messages sent in one day was " + max + " on " + finalDate);
		System.out.println("\n-------------------------------------------\n");
		System.out.println("Most frequent words used by " + name1 + ":");
		// Print words
		for(int i = 0; i < 30; i++){
			System.out.println((i+1) + "\t\t\t" + h.max().getKey() + "\t\t\t" + h.removeMax().getValue());

		}
		System.out.println("\nMost frequent words used by " + name2 + ":");
		// Print words
		for(int i = 0; i < 30; i++)
			System.out.println((i+1) + "\t\t\t" + h2.max().getKey() + "\t\t\t" + h2.removeMax().getValue());
	
		input.close();
	
	}
	
	public static boolean isCommonWord(String a){
		
		String[] french = {"te", "ma", "?", "plus", "dit", "bon", "sur", "haha,", "n'est", "quand", "tout", "ont", "Oui", "cette", "as", "ça", "à", "n'ai", "ces", "par", "ils", "elles", "si", "dans", "oui,", "qui", "ou", "beaucoup", "me", "au", "j'ai", "c'est", "avec", "y", "ce", "sont", "on", "suis", "pour", "il", "comme", "mais", "du", "en", "est", "de", "je", "un", "une", "la", "le", "les", "des", "pas", "ne", "que", "et", "tu", "a"};
		String[] english = {"they","can","an","yes","one","?", "the", "to", "i", "I", "and", "you","is","of","that","it","in","he","for","be","at","have","not","but","we","me","this","was","my","with","about","what","it's","are","get","if","or","do"};

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
