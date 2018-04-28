package com.amd.documentclassification.Document;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@XmlRootElement(name = "document")
@XmlType(propOrder = {"doc_class","confidence"})
@JsonPropertyOrder({"prediction","confidence"})
public class Document {
	private String words;
	private String doc_class;
	private double confidence;
	public Document() {
		super();
	}
	
	@JsonIgnore
	@XmlTransient
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	
	
	@XmlElement(name="prediction")
	@JsonProperty(value="prediction")
	public String getDoc_class() {
		return doc_class;
	}
	public void setDoc_class(String doc_class) {
		this.doc_class = doc_class;
	}
	
	@XmlElement(name="confidence")
	@JsonProperty(value="confidence")
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	
}
