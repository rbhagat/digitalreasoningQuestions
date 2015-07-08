package q2;


import java.util.*;
import java.text.*;
import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class testNLPq2  {

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
   
  
   static HashMap<String,WordTuple2> findNER(String target, BreakIterator wordIterator, ArrayList<Integer> sentences, HashSet<String> namedEntities) {
	   
		 HashMap<String,WordTuple2> wordMap = new HashMap<String,WordTuple2>();
		 
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
	            	WordTuple2 wordTuple = wordMap.get(wordLc);	            	
	            	ArrayList<Integer> sentenceList = wordTuple.getSentenceList();
	            	sentenceList.add(sentenceNo);
	            	if(namedEntities.contains(wordLc)){
	            		wordTuple.setNamedEntity(true);
	            	}else{
	            		wordTuple.setNamedEntity(false);
	            	}
	            	//sentenceList.setList(sentenceArrayList);
	            	wordMap.put(wordLc, wordTuple);
	            }else{
	            	//if it does not exist, create list, add sentence number to list, and add list to hashmap with word as key
	            	WordTuple2 wordTuple = new WordTuple2();
	            	ArrayList<Integer> sentenceList = new ArrayList<Integer>();	            	
	            	sentenceList.add(sentenceNo);
	            	wordTuple.setSentenceList(sentenceList);
	            	if(namedEntities.contains(wordLc)){
	            		wordTuple.setNamedEntity(true);
	            	}else{
	            		wordTuple.setNamedEntity(false);
	            	}
	            	wordMap.put(wordLc, wordTuple);
	            }	         
	         }
	         start = end;
	         end = wordIterator.next();
	      }
	      return wordMap;
	   } 

   
     
   static void printWordTupleData(ArrayList<Integer> sentenceBoundaryList, HashMap<String,WordTuple2> wordList){
	   try {
		   
		   ProcessedData2 pd2 = new ProcessedData2();
		   pd2.setSentenceBoundaryList(sentenceBoundaryList);
		   pd2.setWordList(wordList);
		   
		   JAXBContext jaxbContext = JAXBContext.newInstance(ProcessedData2.class);
		   Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
		   jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	 
		   //jaxbMarshaller.marshal(customer, file);
		   jaxbMarshaller.marshal(pd2, System.out);
	 
      } catch (JAXBException e) {
			e.printStackTrace();
      }
	 
   }
   static void parseWordDataQ2(String inputText, HashSet<String> nerSet2) {

	      Locale currentLocale = new Locale ("en","US");
	      BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(currentLocale);
	      
	      //String someText = "The term \"First World War\" was first used in September 1914 by the German philosopher Ernst Haeckel, who claimed that \"there is no doubt that the course and character of the feared 'European War' ... will become the first world war in the full sense of the word.\" Why did the war begin? The immediate trigger for war was the 28 June 1914 assassination of Archduke Franz Ferdinand of Austria, heir to the throne of Austria-Hungary, by Yugoslav nationalist Gavrilo Princip (19 years old at the time) in Sarajevo. This set off a diplomatic crisis when Austria-Hungary delivered an ultimatum to the Kingdom of Serbia, and entangled international alliances formed over the previous decades were invoked. Within weeks, the major powers were at war and the conflict soon spread around the world.";
	      
	      HashSet<String> nerSet = new HashSet<String>();
	      nerSet.add("ernst haeckel");
	      nerSet.add("franz ferdinand");
	      nerSet.add("gavrilo princip");
	      nerSet.add("germany");
	      nerSet.add("austria-hungary");
	      nerSet.add("yugoslavia");
	      nerSet.add("serbia");
	      nerSet.add("sarajevo");
	      nerSet.add("europe");
	      nerSet.add("euclid");
	      nerSet.add("elements");
	      nerSet.add("venice");
	      nerSet.add("carl benjamin boyer");
	      nerSet.add("bible");
	      nerSet.add("broyden-fletcher-goldfarb-shanno");
	      nerSet.add("newton");
	      nerSet.add("bfgs");
	      nerSet.add("montgomery castle");
	      nerSet.add("powys");
	      nerSet.add("wales");
	      nerSet.add("england");
	      nerSet.add("roger de montgomery");
	      nerSet.add("robert of belleme");
	      nerSet.add("king henry III");
	      nerSet.add("llywelyn ap gruffudd");
	      nerSet.add("prince of wales");
	      nerSet.add("shrewsbury");
	      nerSet.add("antikythera");
	      nerSet.add("olympic games");
	      nerSet.add("japan");
	      nerSet.add("north america");
	      nerSet.add("sun microsystems");
	      nerSet.add("sun");
	      nerSet.add("oracle corporation");
	      nerSet.add("apollo 11");
	      nerSet.add("neil armstrong");
	      nerSet.add("buzz aldrin");
	      nerSet.add("michael collins");
	      nerSet.add("moon");
	      nerSet.add("sea of tranquility");
	      nerSet.add("earth");
	      nerSet.add("pacific ocean");
	      nerSet.add("james clerk maxwell");
	      nerSet.add("isaac newton");
	      nerSet.add("albert einstein");

	      
	      ArrayList<Integer> alist = findSentenceBoundaries(inputText, sentenceIterator);
	      
	      BreakIterator wordIterator = BreakIterator.getWordInstance(currentLocale);
	   	      
	      HashMap<String,WordTuple2> wordTupleList = findNER(inputText, wordIterator, alist, nerSet2);
	      printWordTupleData(alist, wordTupleList);
	   }

   
   

   static public void main(String[] args) {

      if(args.length < 1){
		  System.out.println("Usage:");
		  System.out.println("java testNLPq2.java <input file location> <Named Entity Recognition file location>");
		  System.exit(-1);
	  }
	    
	  //String someText = "The term \"First World War\" was first used in September 1914 by the German philosopher Ernst Haeckel, who claimed that \"there is no doubt that the course and character of the feared 'European War' ... will become the first world war in the full sense of the word.\" Why did the war begin? The immediate trigger for war was the 28 June 1914 assassination of Archduke Franz Ferdinand of Austria, heir to the throne of Austria-Hungary, by Yugoslav nationalist Gavrilo Princip (19 years old at the time) in Sarajevo. This set off a diplomatic crisis when Austria-Hungary delivered an ultimatum to the Kingdom of Serbia, and entangled international alliances formed over the previous decades were invoked. Within weeks, the major powers were at war and the conflict soon spread around the world.";
	  String someText2 = null;
	  String nerText2 = null;
	  try {
		  		  
		  someText2 = new Scanner( new File(args[0])).useDelimiter("\\A").next();
		  nerText2 = new Scanner( new File(args[1])).useDelimiter("\\A").next();
      } catch (FileNotFoundException e) {
    	  System.err.println(System.getProperty("user.dir"));    	  
    	  e.printStackTrace();
      }
	  
	  System.out.println();
	  System.out.println("nerText2:");
	  System.out.println();
	  System.out.println(nerText2);
	  
	  HashSet<String> nerSet2 = new HashSet<String>();
	  
	  //String[] tokens = nerText2.split("\\s*\\Q\\n\\E\\s*");
	  String[] tokens = nerText2.split("\\n");
	  for(String t: tokens){
		  System.out.println("Tokens Output: " + t);
		  nerSet2.add(t.toLowerCase());		  
	  }
	  
	  	  
      System.out.println();
      parseWordDataQ2(someText2, nerSet2);
      System.out.println();      
      
   }

} 