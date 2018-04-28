package com.amd.documentclassification.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amd.documentclassification.Document.Document;

import weka.classifiers.Evaluation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

@Controller
public class FileUploadController {

	@Autowired
	private TestFileClassifyService testclassify;
	

    @GetMapping("/")
    public String index(Model model) {
    	model.addAttribute("document", new Document());
        return "document";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model) {
    	System.out.println("Uploading : "+file.getOriginalFilename()+"to "+System.getProperty("user.dir"));
        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(System.getProperty("user.dir")+File.separator+"test.csv");
            Files.write(path, bytes);
            CreateArffFile arff=new CreateArffFile();
            String arfffilePath=arff.createArff(System.getProperty("user.dir")+File.separator+"test.csv","test.arff");
            DecimalFormat df=new DecimalFormat("#.###");
            Evaluation eval=testclassify.classifyFile(arfffilePath);
            
            File f=new File("results.txt");
            FileWriter fw=new FileWriter(f);
            fw.write(eval.toClassDetailsString());
            fw.write("\n\n");
            fw.write(eval.toMatrixString());
            fw.close();

            model.addAttribute("incorrect",Integer.toString((int)eval.incorrect()));
            model.addAttribute("kappa",df.format(eval.kappa()));
            model.addAttribute("mae",df.format(eval.meanAbsoluteError()));
            model.addAttribute("rmse",df.format(eval.rootMeanSquaredError()));
            model.addAttribute("rae",df.format(eval.relativeAbsoluteError()));
            model.addAttribute("rrse",df.format(eval.rootRelativeSquaredError()));
            model.addAttribute("total",(int)eval.numInstances());
            model.addAttribute("correct_percentage",df.format(eval.correct()*100/eval.numInstances())+"%");
            model.addAttribute("correct", Integer.toString((int)eval.correct()));
            model.addAttribute("incorrect_percentage",df.format(eval.incorrect()*100/eval.numInstances())+"%");
            
            Files.deleteIfExists(Paths.get(arfffilePath));
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir")+File.separator+"test.csv"));
            
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error","Error evaluating file, please try again.");
        }

        return "uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}