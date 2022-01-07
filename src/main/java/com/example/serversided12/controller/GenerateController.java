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

  // endpoint to forward to a generate.html page. root of the path is set,
  // whenever user accesses this web app, the app defaults to this method
  @GetMapping("/")
  public String showGenerateForm(Model model) {
    // instantiate Generate class which represents the form fo the generate html
    // page
    Generate generate = new Generate();
    generate.setNumberVal(10);
    // "generate" ties to the html object of the page
    // set the generate class into the page scope of the generate html
    // model carries anything that is passed to the page
    model.addAttribute("generateObj", generate);
    // generate.html is returned as endpoint
    return "generatePage";
  }

  // this end point is used to handle the form submission
  // @ModelAttribute is used in the parameter when you are referencing to the form
  // object binded in the previous end point
  @PostMapping("/result")
  public String generateNumbers(@ModelAttribute Generate generate, Model model) {
    try {
      logger.info("From the form:" + generate.getNumberVal());
      // if user inputs a number higher than the threshold the programme is meant to
      // generate
      if (generate.getNumberVal() > 10) {
        throw new NumNotFoundException();
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
      model.addAttribute("numberOfNumbers", generate.getNumberVal());
      model.addAttribute("randNumResult", selectedImg.toArray());
    } catch (NumNotFoundException e) {
      // once we catch the above error, error message is poopulated on the model for
      // error page to display
      model.addAttribute("errorMessage", "Please only input an integer less than 10.");
      return "error";
    }

    return "result";
  }
}
