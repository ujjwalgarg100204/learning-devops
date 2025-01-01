package com.ujjwalgarg.learningdevops.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  // Complex code issue
  @GetMapping("/complex")
  public String complexMethod() {
    int value = 5;
    String result;
    if (value > 5) {
      result = "Greater than 5";
    } else if (value == 5) {
      result = "Equal to 5";
    } else {
      result = "Less than 5";
    }
    return result; // Cyclomatic complexity issue
  }

  // Duplicate code issue
  @GetMapping("/duplicate1")
  public String duplicateMethod1() {
    return "This is duplicate";
  }

  @GetMapping("/duplicate2")
  public String duplicateMethod2() {
    return "This is duplicate"; // Same logic
  }

  // Hardcoded values
  @GetMapping("/hardcoded")
  public String hardcoded() {
    return "Hardcoded String"; // Issue: Hardcoded value
  }
}
