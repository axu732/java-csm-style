package uoa.sample;

import java.util.*;

public class SampleCode {
  private int unusedField;

  // No javadoc comment
  public void badlyFormattedMethod(String param1, int param2) {
    if (param1 != null) {
      System.out.println("Bad formatting");
    }

    // Unused variable
    String unusedVar = "test";

    // Long line that exceeds 100 characters limit according to Google Java style guide conventions
    String longString =
        "This is a very long string that definitely exceeds the 100 character limit and should"
            + " trigger a line length violation according to Google Java conventions";

    // Complex boolean expression
    if (param1 != null && param2 > 0 && param1.length() > 5 && param2 < 100) {
      System.out.println("Complex condition");
    }
  }

  // Method without javadoc
  public String getString() {
    return "test";
  }

  // Inconsistent brace style
  public void anotherMethod() {
    // Empty block
    if (true) {}

    // Missing braces
    if (false) System.out.println("No braces");
  }

  // Redundant modifier
  public final void finalMethod() {
    // Some implementation
  }
}
