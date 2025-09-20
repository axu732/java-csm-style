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

  /** Initialize default mappings between common Checkstyle rules and CSM principles. */
  private void initializeDefaultMappings() {
    // Clear Layout principles
    addMapping("Indentation", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("LineLength", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("WhitespaceAfter", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("WhitespaceAround", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("EmptyLineSeparator", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("NoWhitespaceAfter", CSMPrinciple.CLEAR_LAYOUT);
    addMapping("NoWhitespaceBefore", CSMPrinciple.CLEAR_LAYOUT);

    // Explanatory Language principles
    addMapping("JavadocMethod", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("JavadocType", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("JavadocVariable", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("MissingJavadocMethod", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("MissingJavadocType", CSMPrinciple.EXPLANATORY_LANGUAGE);
    addMapping("NonEmptyAtclauseDescription", CSMPrinciple.EXPLANATORY_LANGUAGE);

    // Simple Constructs principles
    addMapping("CyclomaticComplexity", CSMPrinciple.SIMPLE_CONSTRUCTS);
    addMapping("NPathComplexity", CSMPrinciple.SIMPLE_CONSTRUCTS);
    addMapping("NestedForDepth", CSMPrinciple.SIMPLE_CONSTRUCTS);
    addMapping("NestedIfDepth", CSMPrinciple.SIMPLE_CONSTRUCTS);
    addMapping("NestedTryDepth", CSMPrinciple.SIMPLE_CONSTRUCTS);
    addMapping("SimplifyBooleanExpression", CSMPrinciple.SIMPLE_CONSTRUCTS);
    addMapping("SimplifyBooleanReturn", CSMPrinciple.SIMPLE_CONSTRUCTS);

    // Be Consistent principles
    addMapping("NeedBraces", CSMPrinciple.BE_CONSISTENT);
    addMapping("LeftCurly", CSMPrinciple.BE_CONSISTENT);
    addMapping("RightCurly", CSMPrinciple.BE_CONSISTENT);
    addMapping("TypeName", CSMPrinciple.BE_CONSISTENT);
    addMapping("MethodName", CSMPrinciple.BE_CONSISTENT);
    addMapping("VariableName", CSMPrinciple.BE_CONSISTENT);
    addMapping("PackageName", CSMPrinciple.BE_CONSISTENT);
    addMapping("ConstantName", CSMPrinciple.BE_CONSISTENT);
    addMapping("LocalVariableName", CSMPrinciple.BE_CONSISTENT);
    addMapping("MemberName", CSMPrinciple.BE_CONSISTENT);
    addMapping("ParameterName", CSMPrinciple.BE_CONSISTENT);

    // No Unused Content principles
    addMapping("UnusedImports", CSMPrinciple.NO_UNUSED_CONTENT);
    addMapping("RedundantImport", CSMPrinciple.NO_UNUSED_CONTENT);
    addMapping("UnusedLocalVariable", CSMPrinciple.NO_UNUSED_CONTENT);
    addMapping("EmptyBlock", CSMPrinciple.NO_UNUSED_CONTENT);
    addMapping("EmptyStatement", CSMPrinciple.NO_UNUSED_CONTENT);

    // Avoid Duplication principles
    addMapping("StringLiteralEquality", CSMPrinciple.AVOID_DUPLICATION);
    addMapping("RedundantModifier", CSMPrinciple.AVOID_DUPLICATION);
    addMapping("UnnecessaryParentheses", CSMPrinciple.AVOID_DUPLICATION);

    // Modular Structure principles
    addMapping("OneTopLevelClass", CSMPrinciple.MODULAR_STRUCTURE);
    addMapping("OuterTypeFilename", CSMPrinciple.MODULAR_STRUCTURE);
    addMapping("ClassDataAbstractionCoupling", CSMPrinciple.MODULAR_STRUCTURE);
    addMapping("ClassFanOutComplexity", CSMPrinciple.MODULAR_STRUCTURE);
    addMapping("HideUtilityClassConstructor", CSMPrinciple.MODULAR_STRUCTURE);

    // Congruent Implementation principles
    addMapping("OverloadMethodsDeclarationOrder", CSMPrinciple.CONGRUENT_IMPLEMENTATION);
    addMapping("DeclarationOrder", CSMPrinciple.CONGRUENT_IMPLEMENTATION);
    addMapping("ModifierOrder", CSMPrinciple.CONGRUENT_IMPLEMENTATION);
    addMapping("VisibilityModifier", CSMPrinciple.CONGRUENT_IMPLEMENTATION);
    addMapping("FinalClass", CSMPrinciple.CONGRUENT_IMPLEMENTATION);
    addMapping("DesignForExtension", CSMPrinciple.CONGRUENT_IMPLEMENTATION);
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
