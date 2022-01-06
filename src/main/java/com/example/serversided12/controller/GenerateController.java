package com.example.serversided12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.example.serversided12.exception.NumNotFoundException;
import com.example.serversided12.model.Generate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class GenerateController {
  private Logger logger = LoggerFactory.getLogger(GenerateController.class);

  @GetMapping("/")
  public String showGenerateForm(Model model) {
    Generate generate = new Generate();
    generate.setNumberVal(10);
    // "generate" ties to the html object of the page
    model.addAttribute("generate", generate);
    return "generate";
  }

  // @ModelAttribute is used in the parameter when you are referencing to the form
  // object binded in the previous end point
  @PostMapping("/generate")
  public String generateNumbers(@ModelAttribute Generate generate, Model model) {
    logger.info("From the form:" + generate.getNumberVal());
    // input validation, show error page is input is invalid
    if (generate.getNumberVal() > 10) {
      // throw new NumNotFoundException();
      model.addAttribute("errorMessage", "OMG exceed 10 already!");
      return "error";
    }
    // init string containing name of images
    String[] imgNumbers = {
        "number1.jpg", "number2.jpg", "number3.jpg", "number4.jpg", "number5.jpg", "number6.jpg", "number7.jpg",
        "number8.jpg", "number9.jpg", "number10.jpg"
    };

    Random randNum = new Random();
    // init set to contain random numbers
    Set<Integer> uniquelyGeneratedSet = new LinkedHashSet<Integer>();
    // if set is smaller than indicated number
    while (uniquelyGeneratedSet.size() < generate.getNumberVal()) {
      // add a random number into the set
      Integer resultOfRandomNumbers = randNum.nextInt(10);
      uniquelyGeneratedSet.add(resultOfRandomNumbers);
    }

    // init selectedImg to contain images
    List<String> selectedImg = new ArrayList<String>();
    // init iterator to be used on set
    Iterator<Integer> it = uniquelyGeneratedSet.iterator();
    Integer currentElem = null;
    while (it.hasNext()) {
      currentElem = it.next();
      logger.info("currentElem: " + currentElem);
      // add random images to selected img
      selectedImg.add(imgNumbers[currentElem.intValue()]);
    }
    // add array of images to model so that html file can call the images
    model.addAttribute("randNumResult", selectedImg.toArray());
    return "result";
  }
}
