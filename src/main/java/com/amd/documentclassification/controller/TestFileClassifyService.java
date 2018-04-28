package com.amd.documentclassification.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

@Service
public class TestFileClassifyService {

	
	public Evaluation classifyFile(String testfilePath) {
		Evaluation eval=null;
		try {
		Classifier fc = (FilteredClassifier) weka.core.SerializationHelper.read(System.getProperty("user.dir")+File.separator+"classifier.model");
	    BufferedReader input=new BufferedReader(new FileReader(testfilePath));
	    ArffReader reader=new ArffReader(input);
	    Instances testData=reader.getData();
	    testData.setClassIndex(testData.numAttributes() - 1);
	    
	    eval = new Evaluation(testData);
	    eval.evaluateModel(fc, testData);
	    input.close();
	    }
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return eval;
	}
	
	
}
