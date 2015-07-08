package q1;


import java.io.File;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class testNLPq1  {
   static ArrayList<Integer> findSentenceBoundaries(String target, BreakIterator iterator) {
	   
	   	  ArrayList<Integer> sentenceBoundaryList = new ArrayList<Integer>();
	      iterator.setText(target);
	      int boundary = iterator.first();

	      while (boundary != BreakIterator.DONE) {
	         boundary = iterator.next();
	         if(boundary != -1){
	        	 sentenceBoundaryList.add(boundary);
	         }
	      }
	      
	      return sentenceBoundaryList;
	} 
   
   static HashMap<String,WordTuple> findWordsInSentences(String target, BreakIterator wordIterator, ArrayList<Integer> sentences) {
	   
		 HashMap<String,WordTuple> wordMap = new HashMap<String,WordTuple>();
		 
	      wordIterator.setText(target);
	      int start = wordIterator.first();
	      int end = wordIterator.next();

	      while (end != BreakIterator.DONE) {
	         String word = target.substring(start,end);
	         if (Character.isLetterOrDigit(word.charAt(0))) {
	            //System.out.println(word);
	            //System.out.println(start + "-" + end);
	            //check which sentence the word is in by comparing end with values in sentences
	            int sentenceNo = 0;
	            for(int i=0; i<sentences.size(); i++){
	            	if(end <= sentences.get(i)){
	            		sentenceNo = i;
	            		break;
	            	}
	            }
	            //lowercase the word
	            String wordLc = word.toLowerCase();
	            //check if word exists in hashmap
	            if(wordMap.containsKey(wordLc)){
	            	//if exists, add sentence number to word's list in hashmap
	            	WordTuple wordTuple = wordMap.get(wordLc);
	            	ArrayList<Integer> sentenceList = wordTuple.getSentenceList();
	            	sentenceList.add(sentenceNo);
	            	wordMap.put(wordLc, wordTuple);
	            }else{
	            	//if it does not exist, create list, add sentence number to list, and add list to hashmap with word as key
	            	ArrayList<Integer> sentenceList = new ArrayList<Integer>();	            	
	            	sentenceList.add(sentenceNo);
	            	WordTuple wordTuple = new WordTuple();
	            	wordTuple.setSentenceList(sentenceList);	            	
	            	wordMap.put(wordLc, wordTuple);
	            }	         
	         }
	         start = end;
	         end = wordIterator.next();
	      }
	      return wordMap;
	   } 
   
      
   static void printProcessedData(ArrayList<Integer> sentenceBoundaryList, HashMap<String,WordTuple> wordList){
	   try {
		   
		   ProcessedData pd = new ProcessedData();
		   pd.setSentenceBoundaryList(sentenceBoundaryList);
		   pd.setWordList(wordList);
		   
		   JAXBContext jaxbContext = JAXBContext.newInstance(ProcessedData.class);
		   Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
		   jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
		   jaxbMarshaller.marshal(pd, System.out);
	 
      } catch (JAXBException e) {
			e.printStackTrace();
      }
	 
   }
   
   static void parseWordDataQ1(String inputText) {

	      Locale currentLocale = new Locale ("en","US");
	      BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(currentLocale);
	      	          	            
	      ArrayList<Integer> alist = findSentenceBoundaries(inputText, sentenceIterator);
	      
	      BreakIterator wordIterator = BreakIterator.getWordInstance(currentLocale);
	   
	      HashMap<String,WordTuple> wordList = findWordsInSentences(inputText, wordIterator, alist);
	      printProcessedData(alist, wordList);	      
	   }

   

   
   static public void main(String[] args) {

      if(args.length < 1){
		  System.out.println("Usage:");
		  System.out.println("java testNLPq1.java <file location>");
		  System.exit(-1);
	  }
	    
	  String inputText = null;
	  
	  try {		  		  
		  inputText = new Scanner( new File(args[0])).useDelimiter("\\A").next();		  
      } catch (FileNotFoundException e) {
    	  System.err.println(System.getProperty("user.dir"));    	  
    	  e.printStackTrace();
      }
	  	  	  
      System.out.println();      
      parseWordDataQ1(inputText);
      System.out.println();   
      
   }

} 