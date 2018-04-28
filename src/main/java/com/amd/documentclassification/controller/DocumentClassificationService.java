package com.amd.documentclassification.controller;

import java.io.File;

import org.springframework.stereotype.Service;

import com.amd.documentclassification.Document.Document;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.*;

@Service
public class DocumentClassificationService {

	private FilteredClassifier fc;

	public Document getClass(Document d) {
		try {
			fc = (FilteredClassifier) weka.core.SerializationHelper
					.read(System.getProperty("user.dir") +File.separator+"classifier.model");
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
		Attribute class_attr = new Attribute("class", fvNominalVal);
		// Create list of instances with one element
		FastVector fvWekaAttributes = new FastVector(2);
		fvWekaAttributes.addElement(text_attr);
		fvWekaAttributes.addElement(class_attr);
		Instances instances = new Instances("Test relation", fvWekaAttributes, 0);
		// Set class index
		instances.setClassIndex(1);
		// Create and add the instance
		DenseInstance instance = new DenseInstance(2);
		instance.setValue(text_attr, d.getWords());
		// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
		instances.add(instance);

		double pred = fc.classifyInstance(instances.instance(0));
		
		d.setDoc_class(instances.classAttribute().value((int) pred));
		double prediction[] = fc.distributionForInstance(instances.instance(0));
		d.setConfidence(prediction[(int) pred]);
		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return d;
	}

}
