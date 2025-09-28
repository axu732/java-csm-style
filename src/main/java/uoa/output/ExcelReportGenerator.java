package uoa.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uoa.analysis.ViolationRecord;

/**
 * Generates Excel reports from Checkstyle violation data with CSM principle mappings. Creates
 * detailed reports showing File, Checkstyle rule, CSM Principle, and Line Number.
 */
public class ExcelReportGenerator {
  private static final Logger logger = LoggerFactory.getLogger(ExcelReportGenerator.class);

  private static final String[] COLUMN_HEADERS = {
    "File",
    "File Prefix",
    "Checkstyle Rule",
    "CSM Principle",
    "Line Number",
    "Severity",
    "Message",
    "Line Snippet"
  };

  private static final int[] COLUMN_WIDTHS = {
    8000, // File
    3000, // File Prefix
    6000, // Checkstyle Rule
    5000, // CSM Principle
    3000, // Line Number
    3000, // Severity
    12000, // Message
    10000 // Line Snippet
  };

  /**
   * Generate an Excel report from violation data.
   *
   * @param violations List of violation records
   * @param outputPath Path where the Excel file should be saved
   * @throws IOException if file writing fails
   */
  public void generateReport(List<ViolationRecord> violations, String outputPath)
      throws IOException {
    logger.info("Generating Excel report with {} violations to: {}", violations.size(), outputPath);

    try (Workbook workbook = new XSSFWorkbook()) {
      // Create main violations sheet
      createViolationsSheet(workbook, violations);

      // Create summary sheets
      createSummaryByFileSheet(workbook, violations);
      createSummaryByPrincipleSheet(workbook, violations);
      createSummaryByRuleSheet(workbook, violations);

      // Write to file
      try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
        workbook.write(outputStream);
      }

      logger.info("Excel report generated successfully: {}", outputPath);
    }
  }

  /** Create the main violations sheet with all violation details. */
  private void createViolationsSheet(Workbook workbook, List<ViolationRecord> violations) {
    Sheet sheet = workbook.createSheet("Violations");

    // Create header row
    Row headerRow = sheet.createRow(0);
    CellStyle headerStyle = createHeaderStyle(workbook);

    for (int i = 0; i < COLUMN_HEADERS.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(COLUMN_HEADERS[i]);
      cell.setCellStyle(headerStyle);
      sheet.setColumnWidth(i, COLUMN_WIDTHS[i]);
    }

    // Create data rows
    CellStyle dataStyle = createDataStyle(workbook);
    CellStyle centerStyle = createCenterStyle(workbook);

    int rowNum = 1;
    for (ViolationRecord violation : violations) {
      Row row = sheet.createRow(rowNum++);

      Cell fileCell = row.createCell(0);
      fileCell.setCellValue(violation.getFileName());
      fileCell.setCellStyle(dataStyle);

      Cell prefixCell = row.createCell(1);
      prefixCell.setCellValue(violation.getFilePrefix());
      prefixCell.setCellStyle(centerStyle);

      Cell ruleCell = row.createCell(2);
      ruleCell.setCellValue(violation.getCheckstyleRule());
      ruleCell.setCellStyle(dataStyle);

      Cell principleCell = row.createCell(3);
      principleCell.setCellValue(violation.getCsmPrinciple());
      principleCell.setCellStyle(dataStyle);

      Cell lineCell = row.createCell(4);
      lineCell.setCellValue(violation.getLineNumber());
      lineCell.setCellStyle(centerStyle);

      Cell severityCell = row.createCell(5);
      severityCell.setCellValue(violation.getSeverity());
      severityCell.setCellStyle(centerStyle);

      Cell messageCell = row.createCell(6);
      messageCell.setCellValue(violation.getMessage());
      messageCell.setCellStyle(dataStyle);

      Cell snippetCell = row.createCell(7);
      snippetCell.setCellValue(violation.getLineSnippet());
      snippetCell.setCellStyle(dataStyle);
    }

    // Add metadata
    addMetadata(sheet, violations.size(), rowNum + 2);
  }

  /** Create summary sheet grouped by file. */
  private void createSummaryByFileSheet(Workbook workbook, List<ViolationRecord> violations) {
    Sheet sheet = workbook.createSheet("Summary by File");

    Map<String, Long> violationsByFile =
        violations.stream()
            .collect(Collectors.groupingBy(ViolationRecord::getFileName, Collectors.counting()));

    createSummarySheet(sheet, workbook, violationsByFile, "File", "Violation Count");
  }

  /** Create summary sheet grouped by CSM principle. */
  private void createSummaryByPrincipleSheet(Workbook workbook, List<ViolationRecord> violations) {
    Sheet sheet = workbook.createSheet("Summary by CSM Principle");

    Map<String, Long> violationsByPrinciple =
        violations.stream()
            .collect(
                Collectors.groupingBy(ViolationRecord::getCsmPrinciple, Collectors.counting()));

    createSummarySheet(sheet, workbook, violationsByPrinciple, "CSM Principle", "Violation Count");
  }

  /** Create summary sheet grouped by Checkstyle rule. */
  private void createSummaryByRuleSheet(Workbook workbook, List<ViolationRecord> violations) {
    Sheet sheet = workbook.createSheet("Summary by Rule");

    Map<String, Long> violationsByRule =
        violations.stream()
            .collect(
                Collectors.groupingBy(ViolationRecord::getCheckstyleRule, Collectors.counting()));

    createSummarySheet(sheet, workbook, violationsByRule, "Checkstyle Rule", "Violation Count");
  }

  /** Create a generic summary sheet. */
  private void createSummarySheet(
      Sheet sheet,
      Workbook workbook,
      Map<String, Long> data,
      String keyHeader,
      String valueHeader) {
    // Create header row
    Row headerRow = sheet.createRow(0);
    CellStyle headerStyle = createHeaderStyle(workbook);

    Cell keyHeaderCell = headerRow.createCell(0);
    keyHeaderCell.setCellValue(keyHeader);
    keyHeaderCell.setCellStyle(headerStyle);
    sheet.setColumnWidth(0, 8000);

    Cell valueHeaderCell = headerRow.createCell(1);
    valueHeaderCell.setCellValue(valueHeader);
    valueHeaderCell.setCellStyle(headerStyle);
    sheet.setColumnWidth(1, 4000);

    // Create data rows, sorted by violation count descending
    CellStyle dataStyle = createDataStyle(workbook);
    CellStyle numberStyle = createCenterStyle(workbook);

    // Use an array to hold the current row number so it can be modified in the lambda
    final int[] rowNum = {1};
    data.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .forEach(
            entry -> {
              Row row = sheet.createRow(rowNum[0]);

              Cell keyCell = row.createCell(0);
              keyCell.setCellValue(entry.getKey());
              keyCell.setCellStyle(dataStyle);

              Cell valueCell = row.createCell(1);
              valueCell.setCellValue(entry.getValue());
              valueCell.setCellStyle(numberStyle);

              rowNum[0]++; // Increment row number for next iteration
            });
  }

  /** Add metadata information to the sheet. */
  private void addMetadata(Sheet sheet, int totalViolations, int startRow) {
    CellStyle boldStyle = createBoldStyle(sheet.getWorkbook());

    Row metadataRow1 = sheet.createRow(startRow);
    Cell metadataCell1 = metadataRow1.createCell(0);
    metadataCell1.setCellValue(
        "Report Generated: "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    metadataCell1.setCellStyle(boldStyle);

    Row metadataRow2 = sheet.createRow(startRow + 1);
    Cell metadataCell2 = metadataRow2.createCell(0);
    metadataCell2.setCellValue("Total Violations: " + totalViolations);
    metadataCell2.setCellStyle(boldStyle);
  }

  /** Create header cell style. */
  private CellStyle createHeaderStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();

    Font font = workbook.createFont();
    font.setBold(true);
    font.setColor(IndexedColors.WHITE.getIndex());

    style.setFont(font);
    style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setBorderTop(BorderStyle.THIN);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setAlignment(HorizontalAlignment.CENTER);

    return style;
  }

  /** Create data cell style. */
  private CellStyle createDataStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();

    style.setBorderTop(BorderStyle.THIN);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setWrapText(true);

    return style;
  }

  /** Create center-aligned data cell style. */
  private CellStyle createCenterStyle(Workbook workbook) {
    CellStyle style = createDataStyle(workbook);
    style.setAlignment(HorizontalAlignment.CENTER);
    return style;
  }

  /** Create bold text style. */
  private CellStyle createBoldStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();

    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);

    return style;
  }
}
