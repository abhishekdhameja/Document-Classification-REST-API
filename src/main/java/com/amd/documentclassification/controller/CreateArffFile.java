package com.amd.documentclassification.controller;
import org.springframework.util.FileSystemUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.TextDirectoryLoader;

public class CreateArffFile {


	public CreateArffFile() {
		
	}
	
	
	public String createArff(String csvfilepath,String name) {
		try {
			FastVector fvNominalVal = new FastVector(14);

			fvNominalVal.addElement("APPLICATION");
			fvNominalVal.addElement("BILL");
			fvNominalVal.addElement("BILL BINDER");
			fvNominalVal.addElement("BINDER");
			fvNominalVal.addElement("CANCELLATION NOTICE");
			fvNominalVal.addElement("CHANGE ENDORSEMENT");
			fvNominalVal.addElement("DECLARATION");
			fvNominalVal.addElement("DELETION OF INTEREST");
			fvNominalVal.addElement("EXPIRATION NOTICE");
			fvNominalVal.addElement("INTENT TO CANCEL NOTICE");
			fvNominalVal.addElement("NON-RENEWAL NOTICE");
			fvNominalVal.addElement("POLICY CHANGE");
			fvNominalVal.addElement("REINSTATEMENT NOTICE");
			fvNominalVal.addElement("RETURNED CHECK");
			Attribute text_attr = new Attribute("text", (FastVector) null);
			Attribute class_attr = new Attribute("@@class@@", fvNominalVal);
			// Create list of instances with one element
			FastVector fvWekaAttributes = new FastVector(2);
			fvWekaAttributes.addElement(text_attr);
			fvWekaAttributes.addElement(class_attr);
			Instances instances = new Instances("Test relation", fvWekaAttributes, 0);
			// Set class index
			instances.setClassIndex(1);
			BufferedReader input = new BufferedReader(new FileReader(csvfilepath));
			String newDoc;
			int i = 1;
			while ((newDoc = input.readLine()) != null) {
				// System.out.println(i);
				String data[] = newDoc.split(",");
				DenseInstance instance = new DenseInstance(2);
				if (data.length < 2) {
					instance.setValue(text_attr, "");
					instance.setValue(class_attr, data[0]);
				} else {
					instance.setValue(text_attr, data[1]);
					instance.setValue(class_attr, data[0]);
				}
				i++;
				instances.add(instance);
			}
			input.close();
			ArffSaver saver = new ArffSaver();
			saver.setInstances(instances);
			saver.setFile(new File(System.getProperty("user.dir")+File.separator+name));
			saver.writeBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return System.getProperty("user.dir")+File.separator+name;
		
	}

	
}
