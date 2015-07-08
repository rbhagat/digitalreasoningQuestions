package q1;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcessedData {

	private ArrayList<Integer> sentenceBoundaryList;
	//@XmlElementWrapper(name = "stringMap")
	private HashMap<String,WordTuple> wordList;
	
	public ArrayList<Integer> getSentenceBoundaryList() {
		return sentenceBoundaryList;
	}
	
	@XmlElement
	public void setSentenceBoundaryList(ArrayList<Integer> sentenceBoundaryList) {
		this.sentenceBoundaryList = sentenceBoundaryList;
	}
	
	//@XmlElementWrapper(name="MyList")
	//@XmlElement
	public HashMap<String, WordTuple> getWordList() {
		return wordList;
	}
	
	//@XmlElementWrapper(name = "stringMap")
	@XmlElement
	public void setWordList(HashMap<String, WordTuple> wList) {
		this.wordList = wList;
	}
	
	
}
