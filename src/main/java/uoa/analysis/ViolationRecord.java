package uoa.analysis;

/** Represents a single Checkstyle violation with associated CSM principle mapping. */
public class ViolationRecord {
  private final String fileName;
  private final String checkstyleRule;
  private final String csmPrinciple;
  private final int lineNumber;
  private final String message;
  private final String severity;
  private final String filePrefix;
  private final String lineSnippet;

  public ViolationRecord(
      String fileName,
      String checkstyleRule,
      String csmPrinciple,
      int lineNumber,
      String message,
      String severity,
      String filePrefix,
      String lineSnippet) {
    this.fileName = fileName;
    this.checkstyleRule = checkstyleRule;
    this.csmPrinciple = csmPrinciple;
    this.lineNumber = lineNumber;
    this.message = message;
    this.severity = severity;
    this.filePrefix = filePrefix;
    this.lineSnippet = lineSnippet;
  }

  public String getFileName() {
    return fileName;
  }

  public String getCheckstyleRule() {
    return checkstyleRule;
  }

  public String getCsmPrinciple() {
    return csmPrinciple;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public String getMessage() {
    return message;
  }

  public String getSeverity() {
    return severity;
  }

  public String getFilePrefix() {
    return filePrefix;
  }

  public String getLineSnippet() {
    return lineSnippet;
  }

  @Override
  public String toString() {
    return String.format(
        "ViolationRecord{file='%s', rule='%s', principle='%s', line=%d, severity='%s'}",
        fileName, checkstyleRule, csmPrinciple, lineNumber, severity);
  }
}
