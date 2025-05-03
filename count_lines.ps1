# Script to count lines of code in the project
$totalLines = 0
$fileCount = 0
$fileTypes = @("*.java", "*.yml", "*.properties", "*.sql", "*.xml")
$excludeDirs = @("*\target\*")

# Create a hashtable to store counts by file type
$linesByType = @{}
foreach ($type in $fileTypes) {
    $linesByType[$type] = @{Count = 0; Files = 0}
}

# Get all files of specified types, excluding target directories
$files = Get-ChildItem -Path . -Recurse -Include $fileTypes | Where-Object {
    $exclude = $false
    foreach ($dir in $excludeDirs) {
        if ($_.FullName -like $dir) {
            $exclude = $true
            break
        }
    }
    -not $exclude
}

# Count lines in each file
foreach ($file in $files) {
    $content = Get-Content -Path $file.FullName
    $lineCount = $content.Count
    $totalLines += $lineCount
    $fileCount++
    
    # Update counts by file type
    foreach ($type in $fileTypes) {
        if ($file.Name -like $type) {
            $linesByType[$type].Count += $lineCount
            $linesByType[$type].Files++
            break
        }
    }
    
    Write-Output "File: $($file.FullName) - $lineCount lines"
}

# Display summary
Write-Output "`n--- Summary ---"
Write-Output "Total files: $fileCount"
Write-Output "Total lines of code: $totalLines"

# Display breakdown by file type
Write-Output "`n--- Breakdown by File Type ---"
foreach ($type in $fileTypes) {
    $count = $linesByType[$type].Count
    $fileTypeCount = $linesByType[$type].Files
    if ($fileTypeCount -gt 0) {
        Write-Output "$type - $count lines in $fileTypeCount files"
    }
}