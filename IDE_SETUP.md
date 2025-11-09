# IDE Setup Instructions

## Issue: Spring Boot Dependencies Not Found

If you're seeing compilation errors like:
```
error: package org.springframework.boot does not exist
error: cannot find symbol: class SpringBootApplication
```

This means your IDE hasn't loaded the Maven dependencies. Follow these steps:

## Solution for IntelliJ IDEA

1. **Reload Maven Project:**
   - Right-click on `pom.xml` → `Maven` → `Reload Project`
   - Or click the Maven tool window (usually on the right) → Click the refresh icon

2. **Invalidate Caches:**
   - Go to `File` → `Invalidate Caches...`
   - Check "Clear file system cache and Local History"
   - Click "Invalidate and Restart"

3. **Check Project Structure:**
   - Go to `File` → `Project Structure` (Ctrl+Alt+Shift+S)
   - Under `Project`, make sure:
     - SDK: Java 17
     - Language level: 17

4. **Check Maven Settings:**
   - Go to `File` → `Settings` → `Build, Execution, Deployment` → `Build Tools` → `Maven`
   - Make sure Maven home directory is correct
   - Check "Use Maven wrapper" if available

5. **Reimport Dependencies:**
   - Open Maven tool window
   - Click on your project
   - Right-click → `Reload`

## Solution for Eclipse

1. **Refresh Maven Project:**
   - Right-click on project → `Maven` → `Update Project`
   - Check "Force Update of Snapshots/Releases"
   - Click OK

2. **Clean and Build:**
   - Right-click on project → `Clean...`
   - Select your project and click OK

3. **Check Java Build Path:**
   - Right-click on project → `Properties` → `Java Build Path`
   - Under `Libraries`, you should see Maven Dependencies
   - If not, go to `Maven` → `Update Project`

## Solution for VS Code

1. **Reload Window:**
   - Press `Ctrl+Shift+P` (or `Cmd+Shift+P` on Mac)
   - Type "Java: Clean Java Language Server Workspace"
   - Select it and reload

2. **Check Java Extension:**
   - Make sure "Extension Pack for Java" is installed
   - Restart VS Code

## Manual Fix (If Above Doesn't Work)

1. **Delete IDE Cache:**
   - Close your IDE
   - Delete `.idea` folder (IntelliJ) or `.settings` folder (Eclipse)
   - Reopen the project

2. **Rebuild from Terminal:**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Verify Dependencies:**
   ```bash
   mvn dependency:tree
   ```

## Verify Setup

After reloading, verify that:
1. No compilation errors in `AuthCheckApplication.java`
2. Spring Boot imports are recognized
3. Maven dependencies are visible in IDE

## Still Having Issues?

1. Check Java version: `java -version` (should be 17 or higher)
2. Check Maven version: `mvn -version`
3. Verify `pom.xml` is in the project root
4. Make sure you're opening the correct project folder

---

**Note:** The project is correctly configured. The issue is just that your IDE needs to reload the Maven dependencies.

