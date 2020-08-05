package learning;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;

public class WhatsApp {

	private JFrame frmWhatsappChatAnalyzer;
	private JPanel panel;
	private JTextArea textArea;
	private JLabel label_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhatsApp window = new WhatsApp();
					window.frmWhatsappChatAnalyzer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WhatsApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmWhatsappChatAnalyzer = new JFrame();
		frmWhatsappChatAnalyzer.getContentPane().setFont(new Font("Arial", Font.PLAIN, 13));
		frmWhatsappChatAnalyzer.getContentPane().setBackground(Color.WHITE);
		frmWhatsappChatAnalyzer.setTitle("WhatsApp Chat Analyzer");
		frmWhatsappChatAnalyzer.setSize(650, 800);
		frmWhatsappChatAnalyzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWhatsappChatAnalyzer.getContentPane().setLayout(new BoxLayout(frmWhatsappChatAnalyzer.getContentPane(), BoxLayout.Y_AXIS));
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmWhatsappChatAnalyzer.getContentPane().add(panel);
		
		String fileName = JOptionPane.showInputDialog(null, "Enter the path of your file:");
		
		String name1 = null, name2 = null;
		ArrayList<String> p1words = new ArrayList<String>();
		ArrayList<String> p2words = new ArrayList<String>();
		
		Scanner input = null;
		
		// Get file name
		try {
			input = new Scanner(new FileInputStream(fileName),"UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
			System.exit(0);
		}
		
		long startTime = System.nanoTime(); // Calculate time taken
		
		// Get names
		/*while(input.hasNext()){
			String word = input.next();
			if(word.matches("[A-Za-z]+:") && name1==null){
				name1 = word.substring(0, word.length()-1);
			}
			else if(word.matches("[A-Za-z]+:") && name1!=null && !word.equals(name1+":")){
				name2 = word.substring(0, word.length()-1);
				break;
			}
		}*/
		
		
		// input.useDelimiter(",\\s\\d{1,2}:\\d{1,2}\\s[A-Za-z.]{4}\\s-\\s");
		input.useDelimiter("\\s((p.m.)|(a.m.))\\s-\\s");
		
		
		int messageCount = 1, max = 0;
		String fullDate = "", finalDate = "";
		String initialDay = "";
		String time = "";
		int dayCount = 1;
		int p1Initiating = 1, p2Initiating = 0;
		int timeSubstring = 0;
		int currentTime = 0, lastTime = 0, currentHour = 0, lastHour = 0;
		
		String pattern = "[A-Za-z]+:";
		Pattern patt = Pattern.compile(pattern);
		Matcher matcher;
		
		String name = "";
		// Extract date and message count information
		
		while(input.hasNextLine()){
			String word = input.next();
			System.out.println("word: " + word);
			matcher = patt.matcher(word);
			
			// Find names
			if(matcher.find()){
				name = matcher.group();
				name = name.substring(0, name.length()-1);
				if(name1==null){
					name1 = name;
				}
				else if(name2==null && !name.equals(name1)){
					name2 = name;
				}
			}

			// Make sure word starts with the message and ends with the time and date info
			if((word.charAt(word.length()-1) >= 48 && word.charAt(word.length()-1) <= 57)){
				
				// Extract time
				time = word.substring(word.length()-6, word.length());
				System.out.println("time in the messed up part: " + time);
				
				if(time.charAt(0) == ','){
					timeSubstring = 16;
					time = time.substring(2);
					currentHour = Integer.parseInt(time.substring(0,1));
				}else{
					timeSubstring = 17;
					time = time.trim();
					currentHour = Integer.parseInt(time.substring(0,2));
				};
				
				currentTime = Integer.parseInt(time.substring(time.length()-2)); 
				
				System.out.println("minutes: " + currentTime);
				
				String date = word.substring(word.length()-timeSubstring).substring(0,10);
				System.out.println("The substring: " + date);
				if(date.equals(initialDay)){
					messageCount++;
				}
				else if(!date.equals(initialDay)){
	
					if(messageCount > max){
						max = messageCount;
						finalDate = initialDay;
					}
					dayCount++;
					messageCount = 1;
					initialDay = word.substring(word.length()-timeSubstring).substring(0,10);
	
				}
			word = word.substring(0,word.length()-timeSubstring);
			System.out.println("Word being used for heap stuff: " + word);
			}
			
			
			
			
				if(name1 != null && name.equals(name1)){
					System.out.println("I got in here");
					p1words.add(word.substring(name1.length()+2));
					if(lastTime <= 30 && ((currentTime-lastTime)>30 || currentHour != lastHour || ((currentTime-lastTime)<0&&(currentTime-lastTime)>-30))){
						p1Initiating++;
						System.out.println("increment");
					}
					else if(lastTime > 30 && ((currentTime-lastTime)>30 || ((currentTime-lastTime)<0&&(currentTime-lastTime)>-30))&& currentHour != (lastHour+1)){
						p1Initiating++;
						System.out.println("increment");
					}
				}
				else if(name2 != null && name.equals(name2)){
					p2words.add(word.substring(name2.length()+2));
					if(lastTime <= 30 && ((currentTime-lastTime)>30 || currentHour != lastHour || ((currentTime-lastTime)<0&&(currentTime-lastTime)>-30))){
						p2Initiating++;
						System.out.println("increment");
					}
					else if(lastTime > 30 && ((currentTime-lastTime)>30 || ((currentTime-lastTime)<0&&(currentTime-lastTime)>-30))&& currentHour != (lastHour+1)){
						p2Initiating++;
						System.out.println("increment");
					}
			}
			lastTime = currentTime;
			lastHour = currentHour;
		}
		System.out.println("p1initi: " + p1Initiating);
		System.out.println("p2initi: " + p2Initiating);
		long p1messages = p1words.size();
		long p2messages = p2words.size();
		long sum = 0;
		long p1average = 0;
		long p2average = 0;
		String[] words = null;
		String[] words2 = null;
		
		// Create a heap for each person
		Heap<Integer,String> h = new Heap<Integer,String>();
		Heap<Integer,String> h2 = new Heap<Integer,String>();
		

		// Add words to the heap
		for(int i = 0; i < p1messages; i++){
			words = p1words.get(i).split("([,\\?]?\\s+)+");
			sum += words.length;
			for(int j = 0; j < words.length; j++){
				if(!isCommonWord(words[j]) && !(words[j].equals(name1))){
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
	
		
		// Repeat for person 2
		for(int i = 0; i < p2messages; i++){
			words = p2words.get(i).split("([,\\?]?\\s+)+");
			sum += words.length;
			for(int j = 0; j < words.length; j++){
				if(!isCommonWord(words[j]) && !(words[j].equals(name2)))
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
		
		JTextArea tx = new JTextArea("\r\n--------------------RESULTS " + (endTime-startTime)/1000000 + "ms -----------------------\r\n");
		panel.add(tx);
		
	
		// Print statistics
		tx.append("\n" + name1 + " has sent " + p1words.size() + " messages, compared to " + p2words.size() + " messages sent by " + name2 + "\n");
		tx.append((p1messages>p2messages)?name1 + " has sent more messages than "+name2:name2+" has sent more messages than "+name1+"\n");
		tx.append((p1Initiating>p2Initiating)?name1 + " has started a conversation more often than "+name2:name2+" has started a conversation more often than "+name1);
		tx.append("\nThe average word count of each of " + name1 + "'s messages is: " + p1average);
		tx.append("\nThe average word count of each of " + name2 + "'s messages is: " + p2average);
		tx.append("\n" + name1 + " sent " + (p1words.size()/dayCount) + " messages per day.");
		tx.append("\n" + name2 + " sent " + (p2words.size()/dayCount) + " messages per day.");
		tx.append("\nIn total, " + ((p1words.size()+p2words.size())/dayCount) + " messages were sent per day.");
		tx.append("\nThe most messages sent in one day was " + max + " on " + finalDate.substring(0,10) + "\n");
		
		JLabel label = new JLabel("Most frequent words used by " + name1 + ":");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		JPanel wordList = new JPanel();
		wordList.setBorder(null);
		wordList.setBackground(Color.WHITE);	
		wordList.setLayout(new BoxLayout(wordList, BoxLayout.Y_AXIS));
		
		textArea = new JTextArea();
		wordList.add(label);
		wordList.add(textArea);
		
		JScrollPane scroll = new JScrollPane(textArea);
		wordList.add(scroll);
		
		frmWhatsappChatAnalyzer.getContentPane().add(wordList);
		
		// Print most frequently used words
		for(int i = 0; i < 30; i++){
			textArea.append((i+1) + "\t\t" + h.max().getKey() + "\t\t" + h.removeMax().getValue() + "\n");
		}

		
		label_1 = new JLabel("\nMost frequent words used by " + name2 + ":");
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		textArea = new JTextArea();
		wordList.add(label_1);
		wordList.add(textArea);
		
		scroll = new JScrollPane(textArea);
		wordList.add(scroll);
		
		for(int i = 0; i < 30; i++){
			textArea.append((i+1) + "\t\t" + h2.max().getKey() + "\t\t" + h2.removeMax().getValue() + "\n");
		}
		
		
	}
	
	/**
	 * Method to remove unmeaningful words
	 * @param a	An input string
	 * @return	Whether or not the string is a common word or not
	 */
	
	public static boolean isCommonWord(String a){
		
		String[] french = {"te", "ma", "?", "plus", "dit", "bon", "sur", "haha,", "n'est", "quand", "tout", "ont", "Oui", "cette", "as", "ça", "à", "n'ai", "ces", "par", "ils", "elles", "si", "dans", "oui,", "qui", "ou", "beaucoup", "me", "au", "j'ai", "c'est", "avec", "y", "ce", "sont", "on", "suis", "pour", "il", "comme", "mais", "du", "en", "est", "de", "je", "un", "une", "la", "le", "les", "des", "pas", "ne", "que", "et", "tu", "a"};
		String[] english = {"so","they","can","an","yes","one","?", "the", "to", "i", "I", "and", "you","is","of","that","it","in","he","for","be","at","have","not","but","we","me","this","was","my","with","about","what","it's","are","get","if","or","do"};

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
