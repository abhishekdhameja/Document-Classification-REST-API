package com.amd.documentclassification.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amd.documentclassification.Document.Document;
import com.amd.documentclassification.Document.InputDocument;



@Controller
public class DocumentClassificationController {
	@Autowired
	private DocumentClassificationService docservice;
	
	 @GetMapping("/documentclassification")
	    public String documentForm(Model model, @RequestParam(value="words") Optional<String> words) {
		 	if(words.isPresent()) {
		 		//System.out.println("in Get 1");
		 		Document d=new Document();
		 		d.setWords(words.get());
				d=docservice.getClass(d);
		 		model.addAttribute("document", d);
		 		return "result";	
		 	}
	        model.addAttribute("document", new Document());
	        return "document";
	    }
	 
	 

	    @PostMapping("/documentclassification")
	    public String documentSubmit(@ModelAttribute Document document, @RequestBody String words) {
	    	//System.out.println("in POST 1");
	    	if(document.getWords()==null) {
	    		document.setWords(words);
	    	}
	  
	    	System.out.println(document.getWords());
	    	document=docservice.getClass(document);
	        return "result";
	    }
	    
	    @RequestMapping(value = "/documentclassification", consumes = "application/json",produces="application/json")
	    @ResponseBody
		public Document getDocumentClassJSON(@RequestParam("words") String words) {
			//System.out.println("getDocumentclass json");
			Document d=new Document();
			d.setWords(words);

			d=docservice.getClass(d);
			
			return d;
		}
	    
	    @RequestMapping(value = "/documentclassification", consumes = "application/xml",produces="application/xml")
	    @ResponseBody
		public Document getDocumentClassXML(@RequestParam("words") String words) {
			//System.out.println("getDocumentclass xml");
			Document d=new Document();
			d.setWords(words);
			d=docservice.getClass(d);
			
			
			return d;
		}
	    
	    
	    @RequestMapping(value = "/documentclassification",method=RequestMethod.POST, consumes = "application/json",produces="application/json")
	    @ResponseBody
		public Document getDocumentJSON(@RequestBody InputDocument input) {
			//System.out.println("getDocumentJson");
			Document d=new Document();
			d.setWords(input.getWords());
//			d.setConfidence(651.645);
//			d.setDoc_class("class json");
			d=docservice.getClass(d);
			return d;
		}
	    
	    @RequestMapping(value = "/documentclassification", method= RequestMethod.POST, consumes = "application/xml",produces="application/xml")
	    @ResponseBody
		public Document getDocumentXML(@RequestBody InputDocument input) {
			//System.out.println("getDocumentXML");
			Document d=new Document();
			d.setWords(input.getWords());
//			d.setConfidence(651.645);
//			d.setDoc_class("class xml");
			d=docservice.getClass(d);
			return d;
		}
	    
}
