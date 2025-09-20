package uoa.analysis;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uoa.mapping.CSMPrincipleMapper;

/**
 * Analyzes Java files using Checkstyle with Google Java conventions and collects violations for CSM
 * principle mapping.
 */
public class CheckstyleAnalyzer {
  private static final Logger logger = LoggerFactory.getLogger(CheckstyleAnalyzer.class);

  private final CSMPrincipleMapper principleMapper;
  private final List<ViolationRecord> violations;
  private final String configPath;

  public CheckstyleAnalyzer() {
    this.principleMapper = new CSMPrincipleMapper();
    this.violations = new ArrayList<>();
    this.configPath = "/google_checks.xml";
  }

  public CheckstyleAnalyzer(CSMPrincipleMapper customMapper) {
    this.principleMapper = customMapper;
    this.violations = new ArrayList<>();
    this.configPath = "/google_checks.xml";
  }

  /**
   * Analyze a directory containing Java files.
   *
   * @param directoryPath Path to the directory to analyze
   * @return List of violation records found
   * @throws CheckstyleException if analysis fails
   * @throws IOException if file system access fails
   */
  public List<ViolationRecord> analyzeDirectory(String directoryPath)
      throws CheckstyleException, IOException {
    Path dir = Paths.get(directoryPath);

    if (!Files.exists(dir)) {
      throw new IllegalArgumentException("Directory does not exist: " + directoryPath);
    }

    if (!Files.isDirectory(dir)) {
      throw new IllegalArgumentException("Path is not a directory: " + directoryPath);
    }

    List<File> javaFiles = findJavaFiles(dir);

    if (javaFiles.isEmpty()) {
      logger.warn("No Java files found in directory: {}", directoryPath);
      return Collections.emptyList();
    }

    logger.info("Found {} Java files to analyze", javaFiles.size());

    return analyzeFiles(javaFiles);
  }

  /**
   * Analyze a single Java file.
   *
   * @param filePath Path to the Java file to analyze
   * @return List of violation records found
   * @throws CheckstyleException if analysis fails
   */
  public List<ViolationRecord> analyzeFile(String filePath) throws CheckstyleException {
    File file = new File(filePath);

    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist: " + filePath);
    }

    if (!file.getName().endsWith(".java")) {
      throw new IllegalArgumentException("File is not a Java file: " + filePath);
    }

    return analyzeFiles(Collections.singletonList(file));
  }

  /**
   * Analyze a list of Java files.
   *
   * @param files List of Java files to analyze
   * @return List of violation records found
   * @throws CheckstyleException if analysis fails
   */
  private List<ViolationRecord> analyzeFiles(List<File> files) throws CheckstyleException {
    violations.clear();

    // Load Checkstyle configuration
    Configuration config = loadConfiguration();

    // Create Checker instance
    Checker checker = new Checker();
    checker.setModuleClassLoader(Checker.class.getClassLoader());
    checker.configure(config);

    // Add our custom audit listener to collect violations
    ViolationCollector violationCollector = new ViolationCollector();
    checker.addListener(violationCollector);

    try {
      // Process files
      checker.process(files);
    } finally {
      checker.destroy();
    }

    logger.info("Analysis completed. Found {} violations", violations.size());

    return new ArrayList<>(violations);
  }

  /** Load Checkstyle configuration from resources. */
  private Configuration loadConfiguration() throws CheckstyleException {
    try {
      // Use the built-in Google configuration path
      String configLocation = getClass().getResource(configPath).toExternalForm();
      Properties properties = new Properties();
      return ConfigurationLoader.loadConfiguration(
          configLocation, new PropertiesExpander(properties));
    } catch (Exception e) {
      throw new CheckstyleException("Failed to load configuration", e);
    }
  }

  /** Find all Java files in a directory recursively. */
  private List<File> findJavaFiles(Path directory) throws IOException {
    List<File> javaFiles = new ArrayList<>();

    try (Stream<Path> paths = Files.walk(directory)) {
      paths
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith(".java"))
          .map(Path::toFile)
          .forEach(javaFiles::add);
    }

    return javaFiles;
  }

  /** Get the current CSM principle mapper. */
  public CSMPrincipleMapper getPrincipleMapper() {
    return principleMapper;
  }

  /** Custom AuditListener to collect Checkstyle violations. */
  private class ViolationCollector implements AuditListener {

    @Override
    public void auditStarted(AuditEvent event) {
      // No action needed
    }

    @Override
    public void auditFinished(AuditEvent event) {
      // No action needed
    }

    @Override
    public void fileStarted(AuditEvent event) {
      // No action needed
    }

    @Override
    public void fileFinished(AuditEvent event) {
      // No action needed
    }

    @Override
    public void addError(AuditEvent event) {
      if (event.getFileName() != null) {
        String fileName = new File(event.getFileName()).getName();
        String ruleName = extractRuleName(event.getSourceName());
        String csmPrinciple = principleMapper.getPrincipleDisplayName(ruleName);

        ViolationRecord violation =
            new ViolationRecord(
                fileName,
                ruleName,
                csmPrinciple,
                event.getLine(),
                event.getMessage(),
                event.getSeverityLevel().getName());

        violations.add(violation);

        logger.debug("Found violation: {} in {} at line {}", ruleName, fileName, event.getLine());
      }
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
      logger.error(
          "Exception during analysis of {}: {}",
          event.getFileName(),
          throwable.getMessage(),
          throwable);
    }

    /** Extract the rule name from the fully qualified source name. */
    private String extractRuleName(String sourceName) {
      if (sourceName == null) {
        return "Unknown";
      }

      // Extract just the class name from the fully qualified name
      int lastDotIndex = sourceName.lastIndexOf('.');
      String className =
          lastDotIndex >= 0 && lastDotIndex < sourceName.length() - 1
              ? sourceName.substring(lastDotIndex + 1)
              : sourceName;

      // Remove "Check" suffix if present to match our mapping keys
      if (className.endsWith("Check")) {
        return className.substring(0, className.length() - 5);
      }

      return className;
    }
  }
}
