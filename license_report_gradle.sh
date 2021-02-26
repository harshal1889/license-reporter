.#!/usr/bin/env bash

echo '***************************************************'
echo '********** LICENSE REPORT TOOL SERVICE ************'
echo '***************************************************'
REPOSITORY_ROOT="$PWD"
TMP_FOLDER="$REPOSITORY_ROOT/tmp"
REPO_DIR=
NON_GRADLE_REPOS=("") # array of non gradle repos.Example ("repo1" "repo2")

initializeDirectories() {
  rm -r $TMP_FOLDER
  mkdir "$TMP_FOLDER" >&/dev/null || true
  mkdir -p $REPO_DIR
}

validateInputs() {
    if [ -z "$REPO_DIR" ]; then
      echo "The home directory cannot be null. Specify the same on line number 8 as 'REPO_DIR' param"
      exit 1
  fi
}

killProcess() {
  processIds=$(ps -ef | grep java | grep org.gradle.launcher.daemon.bootstrap.GradleDaemon | awk '{print $2}')
  for processId in $processIds; do
    kill "$processId" || true
  done
}

doesReportExistForApp() {
  local thisAppName=$1
  if [ -f "$REPO_DIR/$thisAppName/build/reports/dependency-license/index.json" ]; then
    return 0
  else
    return 1
  fi
}

copyReportToTempFolder() {
  local thisAppName=$1
  cp "$REPO_DIR/$thisAppName/build/reports/dependency-license/index.json" "$TMP_FOLDER/$app-license-info.json"
}

containsElement() {
  local e match="$1"
  shift
  for e; do [[ "$e" == "$match" ]] && return 0; done
  return 1
}

generateReports() {
  cd $REPO_DIR
  appArray=$(ls -d * | awk '{print $1}')
  for app in $appArray; do
    if doesReportExistForApp $app; then
      echo "Skipping report generation for app $app since report is already available"
      copyReportToTempFolder "$app"
      continue
    fi

    #Skip non development apps
    if containsElement "$app" "${NON_GRADLE_REPOS[@]}"; then
      echo "Skipping non gradle apps $app"
      continue
    fi
    cd $app
    echo "Started report generation for  $app"
    chmod +x gradlew
    ./gradlew clean
    ./gradlew generateLicenseReport
    if [ $? -eq 0 ]; then
      echo "Generated license report for $app" &&
        copyReportToTempFolder "$app"
    else
      echo "Error Generating license report for $app"
      echo "In case if it is a non gradle repo then add it to the 'NON_GRADLE_REPOS' array at line 9"
      exit 1
    fi
    killProcess
    cd $REPO_DIR
  done
  echo "Report generation for all apps completed.Consolidating all reports...."
}

consolidateReports() {
  # shellcheck disable=SC2016
  cd "$REPOSITORY_ROOT"
  ./gradlew bootRun --args="$TMP_FOLDER"
}

validateInputs
initializeDirectories
generateReports
consolidateReports