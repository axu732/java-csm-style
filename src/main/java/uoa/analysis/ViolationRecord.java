package uoa.analysis;

/** Represents a single Checkstyle violation with associated CSM principle mapping. */
public class ViolationRecord {
  private final String fileName;
  private final String checkstyleRule;
  private final String csmPrinciple;
  private final int lineNumber;
  private final String message;
  private final String severity;

  public ViolationRecord(
      String fileName,
      String checkstyleRule,
      String csmPrinciple,
      int lineNumber,
      String message,
      String severity) {
    this.fileName = fileName;
    this.checkstyleRule = checkstyleRule;
    this.csmPrinciple = csmPrinciple;
    this.lineNumber = lineNumber;
    this.message = message;
    this.severity = severity;
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

  @Override
  public String toString() {
    return String.format(
        "ViolationRecord{file='%s', rule='%s', principle='%s', line=%d, severity='%s'}",
        fileName, checkstyleRule, csmPrinciple, lineNumber, severity);
  }
}
