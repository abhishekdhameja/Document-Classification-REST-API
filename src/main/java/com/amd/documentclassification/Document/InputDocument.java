package com.amd.documentclassification.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="document")
public class InputDocument {

	private String words;

	public InputDocument() {
		super();
	}

	public InputDocument(String words) {
		super();
		this.words = words;
	}

	public String getWords() {
		return words;
	}
	
	@XmlElement(name="words")
	public void setWords(String words) {
		this.words = words;
	}
	
	
}
