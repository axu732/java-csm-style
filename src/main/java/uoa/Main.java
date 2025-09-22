package uoa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uoa.analysis.CheckstyleAnalyzer;
import uoa.analysis.ViolationRecord;
import uoa.mapping.CSMPrincipleMapper;
import uoa.output.ExcelReportGenerator;

/**
 * Main application class for Java CSM Style Analysis.
 *
 * <p>This application analyzes Java files using Checkstyle's Google Java conventions and maps
 * violations to CSM (Clean Software Methodology) principles, then generates Excel reports showing
 * the analysis results.
 */
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Starting Java CSM Style Analysis Tool");

    try {
      if (args.length > 0) {
        // Command line mode
        runCommandLineMode(args);
      } else {
        // Interactive mode
        runInteractiveMode();
      }
    } catch (Exception e) {
      logger.error("Application failed with error: {}", e.getMessage(), e);
      System.err.println("Error: " + e.getMessage());
      System.exit(1);
    }

    logger.info("Analysis completed successfully");
  }

  /**
   * Run in command line mode with provided arguments. Usage: java -jar app.jar <directory_path>
   * [output_file]
   */
  private static void runCommandLineMode(String[] args) throws Exception {
    if (args.length < 1) {
      printUsage();
      return;
    }

    String directoryPath = args[0];
    String outputFile = args.length > 1 ? args[1] : generateDefaultOutputPath();

    performAnalysis(directoryPath, outputFile);
  }

  /** Run in interactive mode, prompting user for input. */
  private static void runInteractiveMode() throws Exception {
    Scanner scanner = new Scanner(System.in);

    System.out.println("=== Java CSM Style Analysis Tool ===");
    System.out.println();

    // Get directory path
    System.out.print("Enter the path to the Java source directory to analyze: ");
    String directoryPath = scanner.nextLine().trim();

    if (directoryPath.isEmpty()) {
      System.out.println("No directory specified. Exiting.");
      return;
    }

    // Get output file path
    System.out.print("Enter output Excel file path (press Enter for default): ");
    String outputFile = scanner.nextLine().trim();

    if (outputFile.isEmpty()) {
      outputFile = generateDefaultOutputPath();
    }

    // Show mapping customization option
    System.out.println();
    System.out.print("Do you want to customize CSM principle mappings? (y/n): ");
    String customizeResponse = scanner.nextLine().trim().toLowerCase();

    CSMPrincipleMapper mapper = new CSMPrincipleMapper();

    if (customizeResponse.equals("y") || customizeResponse.equals("yes")) {
      customizeMappings(scanner, mapper);
    }

    // Perform analysis
    performAnalysis(directoryPath, outputFile, mapper);

    scanner.close();
  }

  /** Allow user to customize CSM principle mappings. */
  private static void customizeMappings(Scanner scanner, CSMPrincipleMapper mapper) {
    System.out.println();
    System.out.println("Current CSM Principles:");
    for (CSMPrincipleMapper.CSMPrinciple principle : CSMPrincipleMapper.CSMPrinciple.values()) {
      System.out.println("  - " + principle.getDisplayName());
    }

    System.out.println();
    System.out.println("Current mappings (showing first 10):");
    mapper.getMappedRules().stream()
        .limit(10)
        .forEach(
            rule -> System.out.printf("  %s -> %s%n", rule, mapper.getPrincipleDisplayName(rule)));

    System.out.println("  ... and " + (mapper.getMappingCount() - 10) + " more mappings");

    System.out.println();
    System.out.println("Mapping customization options:");
    System.out.println("1. Add new mapping");
    System.out.println("2. Remove existing mapping");
    System.out.println("3. View all mappings");
    System.out.println("4. Continue with current mappings");

    while (true) {
      System.out.print("Enter choice (1-4): ");
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          addNewMapping(scanner, mapper);
          break;
        case "2":
          removeMapping(scanner, mapper);
          break;
        case "3":
          mapper.printMappings();
          break;
        case "4":
          return;
        default:
          System.out.println("Invalid choice. Please enter 1-4.");
      }
    }
  }

  /** Add a new mapping between a Checkstyle rule and CSM principle. */
  private static void addNewMapping(Scanner scanner, CSMPrincipleMapper mapper) {
    System.out.print("Enter Checkstyle rule name: ");
    String rule = scanner.nextLine().trim();

    if (rule.isEmpty()) {
      System.out.println("Rule name cannot be empty.");
      return;
    }

    System.out.println("Available CSM Principles:");
    CSMPrincipleMapper.CSMPrinciple[] principles = CSMPrincipleMapper.CSMPrinciple.values();
    for (int i = 0; i < principles.length; i++) {
      System.out.printf("  %d. %s%n", i + 1, principles[i].getDisplayName());
    }

    System.out.print("Enter principle number (1-" + principles.length + "): ");
    try {
      int principleIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
      if (principleIndex >= 0 && principleIndex < principles.length) {
        mapper.addMapping(rule, principles[principleIndex]);
        System.out.printf(
            "Added mapping: %s -> %s%n", rule, principles[principleIndex].getDisplayName());
      } else {
        System.out.println("Invalid principle number.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid number format.");
    }
  }

  /** Remove an existing mapping. */
  private static void removeMapping(Scanner scanner, CSMPrincipleMapper mapper) {
    System.out.print("Enter Checkstyle rule name to remove: ");
    String rule = scanner.nextLine().trim();

    if (mapper.hasMapping(rule)) {
      mapper.removeMapping(rule);
      System.out.println("Removed mapping for: " + rule);
    } else {
      System.out.println("No mapping found for: " + rule);
    }
  }

  /** Perform the main analysis with default mapper. */
  private static void performAnalysis(String directoryPath, String outputFile) throws Exception {
    performAnalysis(directoryPath, outputFile, new CSMPrincipleMapper());
  }

  /** Perform the main analysis with custom mapper. */
  private static void performAnalysis(
      String directoryPath, String outputFile, CSMPrincipleMapper mapper) throws Exception {
    System.out.println();
    System.out.println("Starting analysis...");
    System.out.println("Directory: " + directoryPath);
    System.out.println("Output: " + outputFile);

    // Create analyzer with custom mapper
    CheckstyleAnalyzer analyzer = new CheckstyleAnalyzer(mapper);

    // Analyze directory
    System.out.println("Analyzing Java files...");
    List<ViolationRecord> violations = analyzer.analyzeDirectory(directoryPath);

    if (violations.isEmpty()) {
      System.out.println(
          "No violations found! Your code follows Google Java conventions perfectly.");
      return;
    }

    // Generate Excel report
    System.out.println("Generating Excel report...");
    ExcelReportGenerator reportGenerator = new ExcelReportGenerator();
    reportGenerator.generateReport(violations, outputFile);

    // Display summary
    System.out.println();
    System.out.println("=== Analysis Summary ===");
    System.out.printf("Total violations found: %d%n", violations.size());
    System.out.printf("Excel report generated: %s%n", outputFile);

    // Show top violation types
    System.out.println();
    System.out.println("Top 5 most common violations:");
    violations.stream()
        .collect(
            java.util.stream.Collectors.groupingBy(
                ViolationRecord::getCheckstyleRule, java.util.stream.Collectors.counting()))
        .entrySet()
        .stream()
        .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
        .limit(5)
        .forEach(
            entry -> System.out.printf("  %s: %d occurrences%n", entry.getKey(), entry.getValue()));

    // Show top CSM principle violations
    System.out.println();
    System.out.println("Top 5 most violated CSM principles:");
    violations.stream()
        .collect(
            java.util.stream.Collectors.groupingBy(
                ViolationRecord::getCsmPrinciple, java.util.stream.Collectors.counting()))
        .entrySet()
        .stream()
        .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
        .limit(5)
        .forEach(
            entry -> System.out.printf("  %s: %d violations%n", entry.getKey(), entry.getValue()));

    // Show unmapped rules if any
    java.util.Set<String> unmappedRules =
        violations.stream()
            .filter(v -> "Unmapped".equals(v.getCsmPrinciple()))
            .map(ViolationRecord::getCheckstyleRule)
            .collect(java.util.stream.Collectors.toSet());

    if (!unmappedRules.isEmpty()) {
      System.out.println();
      System.out.println("Unmapped Checkstyle rules found:");
      unmappedRules.stream()
          .sorted()
          .forEach(
              rule -> {
                long count =
                    violations.stream()
                        .filter(
                            v ->
                                rule.equals(v.getCheckstyleRule())
                                    && "Unmapped".equals(v.getCsmPrinciple()))
                        .count();
                System.out.printf("  %s: %d violations%n", rule, count);
              });
    }
  }

  /** Generate a default output file path with timestamp. */
  private static String generateDefaultOutputPath() {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    return "csm_analysis_report_" + timestamp + ".xlsx";
  }

  /** Print usage information. */
  private static void printUsage() {
    System.out.println("Usage:");
    System.out.println("  java -jar java-csm-style.jar");
    System.out.println("    Run in interactive mode");
    System.out.println();
    System.out.println("  java -jar java-csm-style.jar <directory_path> [output_file]");
    System.out.println("    Run in command line mode");
    System.out.println();
    System.out.println("Examples:");
    System.out.println("  java -jar java-csm-style.jar /path/to/java/source");
    System.out.println("  java -jar java-csm-style.jar /path/to/java/source my_report.xlsx");
  }
}
