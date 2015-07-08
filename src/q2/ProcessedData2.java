package q2;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcessedData2 {

	private ArrayList<Integer> sentenceBoundaryList;
	//@XmlElementWrapper(name = "stringMap")
	private HashMap<String,WordTuple2> wordList;
	
	public ArrayList<Integer> getSentenceBoundaryList() {
		return sentenceBoundaryList;
	}
	
	@XmlElement
	public void setSentenceBoundaryList(ArrayList<Integer> sentenceBoundaryList) {
		this.sentenceBoundaryList = sentenceBoundaryList;
	}
	
	//@XmlElementWrapper(name="MyList")
	//@XmlElement
	public HashMap<String, WordTuple2> getWordList() {
		return wordList;
	}
	
	//@XmlElementWrapper(name = "stringMap")
	@XmlElement
	public void setWordList(HashMap<String, WordTuple2> wList) {
		this.wordList = wList;
	}
	
	
}
