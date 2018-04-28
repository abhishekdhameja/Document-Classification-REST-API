package com.amd.documentclassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amd.documentclassification.controller.CreateArffFile;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

@SpringBootApplication
public class DocumentClassificationAPI {

	public static void main(String[] args) throws Exception {
		try {
		CreateArffFile arff=new CreateArffFile();
		String train_arff=arff.createArff(System.getProperty("user.dir")+File.separator+args[0], "fulltrain.arff");
		
		//read train data to train model
		BufferedReader input=new BufferedReader(new FileReader(train_arff));
		ArffReader read=new ArffReader(input);
		Instances dataRaw = read.getData();
		dataRaw.setClassIndex(dataRaw.numAttributes()-1);
		
		//create filter for data
		StringToWordVector filter = new StringToWordVector(); //convert words to vector features
	    filter.setIDFTransform(true); //TF*IDF
	    filter.setWordsToKeep(150); //keep max 150 words as features
	    filter.setOutputWordCounts(true);
	    filter.setInputFormat(dataRaw);
	    Instances dataFiltered = Filter.useFilter(dataRaw, filter);
	    
//	    //making the classifier
	    FilteredClassifier fc=new FilteredClassifier();
	    J48 classifier = new J48();
	    fc.setFilter(filter);   
	    fc.setClassifier(classifier);
	    System.out.println("Building classifier...");
	    fc.buildClassifier(dataRaw);
	    System.out.println("Build complete, saving model..");
	    weka.core.SerializationHelper.write(System.getProperty("user.dir") +File.separator +"classifier.model", fc);
	    input.close();
	    Files.deleteIfExists(Paths.get(train_arff));
	  //model built, start service.
	  		SpringApplication.run(DocumentClassificationAPI.class, args);
		}
		catch(Exception e) {
			System.out.println("Model build error, make sure " +args[0]+" is present in current directory.");
		}
	   
	}

}
