# Java CSM Style Analysis Tool

Java code analysis tool that uses Checkstyle's Google Java conventions to analyze Java code and maps violations to Code Style Model (CSM) principles. The tool generates Excel reports showing which CSM principles are violated and in which files.

## Requirements

- Java 17 or higher

## Building the Application

```bash
./mvnw clean compile
```

## Running the Application

### Interactive Mode

```bash
./mvnw exec:java -Dexec.mainClass="uoa.Main"
```

### Command Line Mode

```bash
# Analyze a directory with default output file (Unix/Linux/Mac)
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="/path/to/java/source"

# Analyze a directory with custom output file (Unix/Linux/Mac)
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="/path/to/java/source custom_report.xlsx"
```

```powershell
# For Windows PowerShell users:
.\mvnw exec:java `-Dexec.mainClass=uoa.Main `-Dexec.args=path\to\java\source

# With custom output file
.\mvnw exec:java `-Dexec.mainClass=uoa.Main `-Dexec.args="path\to\java\source custom_report.xlsx"
```

## Choosing a Folder to Analyze

### Option 1: Interactive Mode (Recommended for beginners)

Run the application in interactive mode and it will prompt you for the folder path:

```bash
./mvnw exec:java -Dexec.mainClass="uoa.Main"
```

When you run this, the application will ask:

```
Enter the path to the Java source directory to analyze:
```

You can then type the path to your Java source folder.

### Option 2: Command Line Mode (For automation/scripting)

Pass the folder path as an argument:

```bash
# Analyze a specific folder with default output filename
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="path/to/java/source"

# Analyze a folder and specify custom output filename
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="path/to/java/source my_report.xlsx"
```

### Common Examples

#### Analyzing Your Current Project:

```bash
# Unix/Linux/Mac
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="src/main/java"
```

```powershell
# Windows PowerShell
.\mvnw exec:java `-Dexec.mainClass=uoa.Main `-Dexec.args=src\main\java
```

#### Analyzing Another Java Project:

```bash
# Unix/Linux/Mac - Absolute path
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="/home/username/projects/SomeJavaProject/src/main/java"

# Unix/Linux/Mac - Relative path
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="../other-project/src/main/java"
```

```powershell
# Windows PowerShell - Absolute path
.\mvnw exec:java `-Dexec.mainClass=uoa.Main `-Dexec.args="C:\Users\YourName\Documents\SomeJavaProject\src\main\java"

# Windows PowerShell - Relative path
.\mvnw exec:java `-Dexec.mainClass=uoa.Main `-Dexec.args=..\other-project\src\main\java
```

#### Testing with Sample Code:

```bash
# Unix/Linux/Mac
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="src/main/java/uoa/sample"
```

```powershell
# Windows PowerShell
.\mvnw exec:java `-Dexec.mainClass=uoa.Main `-Dexec.args=src\main\java\uoa\sample
```

### What the Tool Does

The tool will automatically:

1. Find all `.java` files in the specified folder and its subdirectories
2. Run Checkstyle analysis on them using Google Java conventions
3. Map violations to CSM principles
4. Generate an Excel report with detailed analysis

## Customizing Mappings

Modify the `CSMPrincipleMapper` class to change default mappings:

```java
// Add a new mapping
mapper.addMapping("CustomRule", CSMPrinciple.CLEAR_LAYOUT);

// Remove an existing mapping
mapper.removeMapping("UnwantedRule");

// Check if a mapping exists
if (mapper.hasMapping("SomeRule")) {
    // Do something
}
```

## Output Format

The Excel report contains the following sheets:

1. **Violations**: Detailed list of all violations with:

   - File name
   - File prefix (e.g., [1-1] for assignment folders)
   - Checkstyle rule name
   - Mapped CSM principle
   - Line number
   - Severity level
   - Violation message
   - Line snippet (the actual code that was flagged)

2. **Summary by File**: Count of violations per file, sorted by violation count

3. **Summary by CSM Principle**: Count of violations per CSM principle, sorted by violation count

4. **Summary by Rule**: Count of violations per Checkstyle rule, sorted by violation count

## Example Usage

### Analyzing Project

```bash
# Navigate to your project directory
cd /path/to/your/java/project

# Run analysis on src/main/java (interactive mode)
./mvnw exec:java -Dexec.mainClass="uoa.Main"

# Or run with command line arguments
./mvnw exec:java -Dexec.mainClass="uoa.Main" -Dexec.args="src/main/java"

# This will generate: csm_analysis_report_YYYYMMDD_HHMMSS.xlsx
```

## Configuration

### Checkstyle Configuration

The tool uses Google Java style conventions by default. The configuration is in `src/main/resources/google_checks.xml`.

### Logging

Logging configuration is in `src/main/resources/logback.xml`. Logs are written to both console and `csm-analysis.log` file.

### Suppressions

You can suppress certain checks for specific files using `src/main/resources/checkstyle-suppressions.xml`.

## Dependencies

- **Checkstyle**: For code analysis using Google Java conventions
- **Apache POI**: For Excel file generation
- **Logback**: For logging
- **Jackson**: For JSON configuration handling
