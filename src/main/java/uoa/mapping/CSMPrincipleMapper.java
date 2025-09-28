package uoa.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Maps Checkstyle rules to CSM (Clean Software Methodology) principles. Provides easy functionality
 * to add, remove, and query mappings.
 */
public class CSMPrincipleMapper {

  public enum CSMPrinciple {
    EXPLANATORY_LANGUAGE("Explanatory Language"),
    CLEAR_LAYOUT("Clear Layout"),
    SIMPLE_CONSTRUCTS("Simple Constructs"),
    BE_CONSISTENT("Be Consistent"),
    NO_UNUSED_CONTENT("No Unused Content"),
    AVOID_DUPLICATION("Avoid Duplication"),
    CONGRUENT_IMPLEMENTATION("Congruent Implementation"),
    MODULAR_STRUCTURE("Modular Structure");

    private final String displayName;

    CSMPrinciple(String displayName) {
      this.displayName = displayName;
    }

    public String getDisplayName() {
      return displayName;
    }
  }

  private final Map<String, CSMPrinciple> ruleMappings;

  public CSMPrincipleMapper() {
    this.ruleMappings = new HashMap<>();
    initializeDefaultMappings();
  }

  /**
   * Initialize default mappings between common Checkstyle rules and CSM principles. NOTE: ALL RULES
   * HAVE BEEN TAKEN FROM THE GOOGLE DOC, FIRST MENTION IS WHAT IS IMPLEMENTED
   */
  private void initializeDefaultMappings() {
    // Google Java Style Guide mappings to Explanatory Language principle

    // 2.1 File name - Files must be properly named and use appropriate encoding
    addMapping("OuterTypeFilename", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 2.3.3 Non-ASCII characters - Proper handling of non-ASCII characters
    addMapping("AvoidEscapedUnicodeCharacters", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 3.2 Package statement - Package statements should not be line-wrapped
    addMapping("NoLineWrap", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 3.3.1 No wildcard imports - Avoid wildcard imports for clarity
    addMapping("AvoidStarImport", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 3.3.2 No line-wrapping - Import statements should not be line-wrapped
    addMapping("NoLineWrap", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 4.8.3.2 No C-style array declarations - Use Java-style array declarations
    addMapping("ArrayTypeStyle", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 4.8.4.2 Fall-through: commented - Fall-through cases must be commented
    addMapping("FallThrough", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 4.8.6.2 TODO comments - TODO comments should follow proper format
    addMapping("TodoComment", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 4.8.8 Numeric Literals - Proper formatting of numeric literals
    addMapping("UpperEll", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.1 Rules common to all identifiers - General identifier naming rules
    addMapping("CatchParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.2.1 Package names - Package names should follow naming conventions
    addMapping("PackageName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.2.2 Class names - Class names should follow naming conventions
    addMapping("TypeName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.2.3 Method names - Method names should follow naming conventions
    addMapping("MethodName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.2.5 Non-constant field names - Field names should follow naming conventions
    addMapping("MemberName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.2.6 Parameter Names
    addMapping("ParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("CatchParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("LambdaParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("RecordComponentName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.2.7 Local variable names - Local variable names should follow naming conventions
    addMapping("LocalVariableName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.2.8 Type variable names - Type variable names should follow naming conventions
    addMapping("ClassTypeParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("MethodTypeParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("InterfaceTypeParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("RecordTypeParameterName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 5.3 Camel case: defined - Proper camel case usage
    addMapping("AbbreviationAsWordInName", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 7.2 The summary fragment - Javadoc summary should be properly formatted
    addMapping("SummaryJavadoc", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 7.3 Where Javadoc is used - General Javadoc usage requirements
    addMapping("MissingJavadocType", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("MissingJavadocMethod", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("JavadocMethod", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 7.3.1 Exception: self-explanatory members - Some members don't need Javadoc
    addMapping("MissingJavadocMethod", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 7.3.2 Exception: overrides - Override methods may not need Javadoc
    addMapping("MissingJavadocMethod", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // 7.3.4 Non-required Javadoc - Rules for when Javadoc is not required
    addMapping("MissingJavadocType", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // Google Java Style Guide mappings to Clear Layout principle

    // 2.3.1 Whitespace characters - Proper use of whitespace characters
    addMapping("FileTabCharacter", CSMPrinciple.CLEAR_LAYOUT);

    // 2.3.2 Special escape sequences - Proper use of escape sequences
    addMapping("IllegalTokenText", CSMPrinciple.CLEAR_LAYOUT);

    // 3.3.3 Ordering and spacing - Import statement ordering and spacing
    addMapping("CustomImportOrder", CSMPrinciple.CLEAR_LAYOUT);

    // 3.4.1 Exactly one top-level class declaration - One class per file
    addMapping("OneTopLevelClass", CSMPrinciple.CLEAR_LAYOUT);

    // 3.4.2.1 Overloads: never split - Keep overloaded methods together
    addMapping("OverloadMethodsDeclarationOrder", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("ConstructorsDeclarationGrouping", CSMPrinciple.CLEAR_LAYOUT);

    // 4.1.1 Use of optional braces - Braces should be used consistently
    addMapping("NeedBraces", CSMPrinciple.CLEAR_LAYOUT);

    // 4.1.2 Nonempty blocks: K & R style - Brace placement style
    addMapping("LeftCurly", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("RightCurly", CSMPrinciple.CLEAR_LAYOUT);

    // 4.1.3 Empty blocks: may be concise - Empty block formatting
    addMapping("RegexpSinglelineJava", CSMPrinciple.CLEAR_LAYOUT);

    // 4.2 Block indentation: +2 spaces - Consistent indentation
    addMapping("Indentation", CSMPrinciple.CLEAR_LAYOUT);

    // 4.3 One statement per line - Each statement on its own line
    addMapping("OneStatementPerLine", CSMPrinciple.CLEAR_LAYOUT);

    // 4.4 Column limit: 100 - Line length limit
    addMapping("LineLength", CSMPrinciple.CLEAR_LAYOUT);

    // 4.5.1 Where to break - Line wrapping rules
    addMapping("OperatorWrap", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("SeparatorWrap", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("MethodParamPad", CSMPrinciple.CLEAR_LAYOUT);

    // 4.5.2 Indent continuation lines at least +4 spaces - Continuation indentation
    addMapping("Indentation", CSMPrinciple.CLEAR_LAYOUT);

    // 4.6.1 Vertical Whitespace - Vertical spacing rules
    addMapping("EmptyLineSeparator", CSMPrinciple.CLEAR_LAYOUT);

    // 4.6.2 Horizontal whitespace - Horizontal spacing rules
    addMapping("WhitespaceAround", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("GenericWhitespace", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("MethodParamPad", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("ParenPad", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("WhitespaceAfter", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("NoWhitespaceBefore", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("NoWhitespaceBeforeCaseDefaultColon", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("MatchXpath", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.2.1 One variable per declaration - Separate variable declarations
    addMapping("MultipleVariableDeclarations", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.2.2 Declared when needed - Variable declaration placement
    addMapping("VariableDeclarationUsageDistance", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.4.1 Indentation - Switch statement indentation
    addMapping("Indentation", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.5.2 Class annotations - Class annotation formatting
    addMapping("AnnotationLocation", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("InvalidJavadocPosition", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.5.3 Method and constructor annotations - Method annotation formatting
    addMapping("AnnotationLocation", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("InvalidJavadocPosition", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.5.4 Field annotations - Field annotation formatting
    addMapping("AnnotationLocation", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("InvalidJavadocPosition", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.6.1 Block comment style - Block comment formatting
    addMapping("CommentsIndentation", CSMPrinciple.CLEAR_LAYOUT);

    // 4.8.7 Modifiers - Modifier ordering
    addMapping("ModifierOrder", CSMPrinciple.CLEAR_LAYOUT);

    // 7.1.1 General form - General Javadoc formatting
    addMapping("JavadocStyle", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("InvalidJavadocPosition", CSMPrinciple.CLEAR_LAYOUT);

    // 7.1.2 Paragraphs - Javadoc paragraph formatting
    addMapping("JavadocParagraph", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("RequireEmptyLineBeforeBlockTagGroup", CSMPrinciple.CLEAR_LAYOUT);

    // 7.1.3 Block tags - Javadoc block tag formatting
    addMapping("JavadocTagContinuationIndentation", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("AtclauseOrder", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("NonEmptyAtclauseDescription", CSMPrinciple.CLEAR_LAYOUT);

    // Be Consistent
    // 4.8.4.3 Exhaustiveness and presence of the default label
    addMapping("MissingSwitchDefault", CSMPrinciple.BE_CONSISTENT);
  }

  /** Add a new mapping between a Checkstyle rule and a CSM principle. */
  public void addMapping(String checkstyleRule, CSMPrinciple principle) {
    ruleMappings.put(checkstyleRule, principle);
  }

  /** Remove a mapping for a specific Checkstyle rule. */
  public void removeMapping(String checkstyleRule) {
    ruleMappings.remove(checkstyleRule);
  }

  /** Get the CSM principle for a given Checkstyle rule. Returns null if no mapping exists. */
  public CSMPrinciple getPrinciple(String checkstyleRule) {
    return ruleMappings.get(checkstyleRule);
  }

  /**
   * Get the display name of the CSM principle for a given Checkstyle rule. Returns "Unmapped" if no
   * mapping exists.
   */
  public String getPrincipleDisplayName(String checkstyleRule) {
    CSMPrinciple principle = getPrinciple(checkstyleRule);
    return principle != null ? principle.getDisplayName() : "Unmapped";
  }

  /** Check if a mapping exists for a specific Checkstyle rule. */
  public boolean hasMapping(String checkstyleRule) {
    return ruleMappings.containsKey(checkstyleRule);
  }

  /** Get all mapped Checkstyle rules. */
  public Set<String> getMappedRules() {
    return ruleMappings.keySet();
  }

  /** Get the total number of mappings. */
  public int getMappingCount() {
    return ruleMappings.size();
  }

  /** Clear all mappings. */
  public void clearAllMappings() {
    ruleMappings.clear();
  }

  /** Print all current mappings for debugging purposes. */
  public void printMappings() {
    System.out.println("Current Checkstyle Rule to CSM Principle Mappings:");
    ruleMappings.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(
            entry ->
                System.out.printf(
                    "  %s -> %s%n", entry.getKey(), entry.getValue().getDisplayName()));
  }
}
