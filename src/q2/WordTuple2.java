package q2;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;


public class WordTuple2 { 
	private ArrayList<Integer> sentenceList;
	private boolean namedEntity;
	
	public ArrayList<Integer> getSentenceList() {
		return sentenceList;
	}
	@XmlElement
	public void setSentenceList(ArrayList<Integer> sentenceList) {
		this.sentenceList = sentenceList;
	}
	public boolean isNamedEntity() {
		return namedEntity;
	}
	@XmlElement
	public void setNamedEntity(boolean namedEntity) {
		this.namedEntity = namedEntity;
	}	
	
}
