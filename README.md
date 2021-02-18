# License Reporter Tool

## Description

This script is used to generate license reports for the applications currently based on the gradle build tools.

## Pre-requisites
1. All the applications should be gradle based projects.
   

2. Update the build.gradle file in each of the project with the following entry
    ```shell script
    import com.github.jk1.license.render.JsonReportRenderer
    
    plugins {
        id 'com.github.jk1.dependency-license-report' version '1.16'
    }
    
    licenseReport {
        renderers = [new JsonReportRenderer()]
    }
    ```

## Instructions
1. Configure the following variables before starting the script execution
   ```shell script
   REPO_DIR=# path to root repository folder
   NON_GRADLE_REPOS=("") # array of non gradle repos.Example ("repo1" "repo2")
   ```
2. Run the script :)
    ```shell script
    ./generate-license_report_service.sh
    ```

3. The approximate time to generate a single report within 15-20 seconds. The scripts output the information on the console and is fail first.


4. The report is consolidated internally and stored at the location mentioned the console

## Output:
A sample output from the report
   ```json
   {
      "dependencies": [
         {
            "moduleName": "sopme.module.name",
            "moduleUrl": "https://test.com/someUrl",
            "moduleVersion": "1.11.415",
            "moduleLicense": "Apache License, Version 2.0",
            "moduleLicenseUrl": "https://aws.amazon.com/apache2.0"
         }
      ]
   }
   ```

## Other notes
Work in progress for the maven repos.
