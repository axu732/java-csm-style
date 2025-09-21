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
    // Clear Layout principles
    addMapping("NoLineWrap", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("CustomImportOrder", CSMPrinciple.CLEAR_LAYOUT); // 3.3.3 Ordering and spacing
    addMapping(
        "OneTopLevelClass",
        CSMPrinciple.CLEAR_LAYOUT); // 3.4.1 Exactly one top-level class declaration
    addMapping(
        "OverloadMethodsDeclarationOrder",
        CSMPrinciple.CLEAR_LAYOUT); // 3.4.2.1 Overloads: never split
    addMapping(
        "ConstructorsDeclarationGrouping",
        CSMPrinciple.CLEAR_LAYOUT); // 3.4.2.1 Overloads: never split
    addMapping(
        "RegexpSinglelineJava", CSMPrinciple.CLEAR_LAYOUT); // 4.1.3 Empty blocks: may be concise
    addMapping("LineLength", CSMPrinciple.CLEAR_LAYOUT); // 4.4 Column limit: 100
    addMapping("OperatorWrap", CSMPrinciple.CLEAR_LAYOUT); // 4.5.1 Where to break
    addMapping("SeparatorWrap", CSMPrinciple.CLEAR_LAYOUT); // 4.5.1 Where to break
    addMapping("MethodParamPad", CSMPrinciple.CLEAR_LAYOUT); // 4.5.1 Where to break
    addMapping(
        "EmptyLineSeparator",
        CSMPrinciple.CLEAR_LAYOUT); // 4.6.1 Vertical Whitespace + Consistent Design
    addMapping(
        "VariableDeclarationUsageDistance",
        CSMPrinciple.CLEAR_LAYOUT); // 4.8.2.2 Declared when needed

    // 4.8.5.3 Method and constructor annotations and 4.8.5.4 Field annotations also covered in
    // these two rules
    addMapping(
        "AnnotationLocation",
        CSMPrinciple
            .CLEAR_LAYOUT); // 	4.8.5.2 Class, package, and module annotations + Consistent Design
    addMapping(
        "InvalidJavadocPosition",
        CSMPrinciple
            .CLEAR_LAYOUT); // 	4.8.5.2 Class, package, and module annotations + Consistent Design

    addMapping("CommentsIndentation", CSMPrinciple.CLEAR_LAYOUT); // 4.8.6.1 Block comment style
    addMapping("PackageName", CSMPrinciple.CLEAR_LAYOUT); // 5.2.1 Package names
    addMapping("TypeName", CSMPrinciple.CLEAR_LAYOUT); // 5.2.2 Class names
    addMapping("MethodName", CSMPrinciple.CLEAR_LAYOUT); // 5.2.3 Method names

    // Explanatory Language principles
    addMapping("OuterTypeFilename", CSMPrinciple.EXPLANATORY_LANGUAGE); // 2.1 File name
    addMapping(
        " FileTabCharacter",
        CSMPrinciple.EXPLANATORY_LANGUAGE); // 2.3.1 Whitespace Characters. + Clear Layout
    addMapping(
        "IllegalTokenText", CSMPrinciple.EXPLANATORY_LANGUAGE); // 2.3.2 Special escape sequences
    addMapping(
        "AvoidEscapedUnicodeCharacters",
        CSMPrinciple.EXPLANATORY_LANGUAGE); // 2.3.3 Non-ASCII characters
    addMapping("AvoidStarImport", CSMPrinciple.EXPLANATORY_LANGUAGE); // 3.3.1 No wildcard imports
    addMapping(
        "MultipleVariableDeclarations",
        CSMPrinciple.EXPLANATORY_LANGUAGE); // 4.8.2.1 One variable per declaration
    addMapping("FallThrough", CSMPrinciple.EXPLANATORY_LANGUAGE); // 4.8.4.2 Fall-through: commented
    addMapping("TodoComment", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("UpperEll", CSMPrinciple.EXPLANATORY_LANGUAGE); // 4.8.8 Numeric Literals
    addMapping(
        "CatchParameterName",
        CSMPrinciple.EXPLANATORY_LANGUAGE); // 5.1 Rules common to all identifiers
    addMapping("TypeName", CSMPrinciple.EXPLANATORY_LANGUAGE); // 5.2.2 Class names
    addMapping("MethodName", CSMPrinciple.EXPLANATORY_LANGUAGE); // 5.2.3 Method names

    // Simple Constructs principles
    addMapping(
        "MultipleVariableDeclarations",
        CSMPrinciple.SIMPLE_CONSTRUCTS); // 4.8.2.1 One variable per declaration

    // Be Consistent principles

    addMapping("EmptyLineSeparator", CSMPrinciple.BE_CONSISTENT); // 3 Source file structure

    // Line Length and No Line Wrape corresponds to 3.2 Package Statement but is covered by 4.4
    // Column Length and 3.3.2 No Line-Wrapping

    addMapping(
        "NeedBraces",
        CSMPrinciple.BE_CONSISTENT); // 4.1.1 Use of optional braces + No Unused Content

    addMapping("LeftCurly", CSMPrinciple.BE_CONSISTENT); // 4.1.2 Nonempty blocks: K & R style
    addMapping("RightCurly", CSMPrinciple.BE_CONSISTENT); // 4.1.2 Nonempty blocks: K & R style

    addMapping("Indentation", CSMPrinciple.BE_CONSISTENT); // 4.2 Block indentation

    addMapping("OneStatementPerLine", CSMPrinciple.BE_CONSISTENT); // 4.3 One statement per line

    // 4.4 Column limit: 100 + Clear Layout

    // 4.5.2 Indent continuation lines at least +4 Spaces is Covered by 4.2 Block Indentation with
    // Indentation

    // 4.6.1 Vertical Whitespace + Clear Layout

    addMapping("WhitespaceAround", CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace
    addMapping("GenericWhitespace", CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace
    addMapping("MethodParamPad", CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace
    addMapping("ParenPad", CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace
    addMapping("WhitespaceAfter", CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace
    addMapping("NoWhitespaceBefore", CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace
    addMapping(
        "NoWhitespaceBeforeCaseDefaultColon",
        CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace
    addMapping("MatchXpath", CSMPrinciple.BE_CONSISTENT); // 4.6.2 Horizontal whitespace

    addMapping(
        "ArrayTypeStyle", CSMPrinciple.BE_CONSISTENT); // 4.8.3.2 No C-style array declarations

    // 4.8.4.1 Switch Statement Indentation is Covered by Indentation already.

    // 4.8.5.2 Class annotations, 4.8.5.3 Method and constructor annotations, 4.8.5.4 Field
    // annotations already covered in Clear Layout

    addMapping("ModifierOrder", CSMPrinciple.BE_CONSISTENT); // 4.8.7 Modifiers

    // 5.1 Rules common to all identifiers covered in Explanatory language

    // 5.2.2 Class Names and 5.2.3 Method Names are in Clear Layout

    // No Unused Content principles

    // 4.1.1 Use of optional Braces is covered in Consistent Design

    // Avoid Duplication principles

    // Modular Structure principles

    // Congruent Implementation principles
    addMapping(
        "EmptyCatchBlock",
        CSMPrinciple.CONGRUENT_IMPLEMENTATION); // 6.2 Caught exceptions: not ignored
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
