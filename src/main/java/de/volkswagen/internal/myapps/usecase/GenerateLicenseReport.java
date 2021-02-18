package de.volkswagen.internal.myapps.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.volkswagen.internal.myapps.entity.AppModuleInfo;
import de.volkswagen.internal.myapps.entity.ModuleInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class GenerateLicenseReport {
    private final Logger LOG = LoggerFactory.getLogger(GenerateLicenseReport.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    public void getConsolidatedReport(String reportDirectory) {
        LOG.info("Generating reports for directory: {}", reportDirectory);
        Set<ModuleInfo> moduleInfos = new HashSet<>();
        //Creating a File object for directory
        File directoryPath = new File(reportDirectory);
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        if(null == filesList || filesList.length == 0) {
            LOG.error("No files found... Please run the 'generate-license_report_service' script from the scripts folder..");
            return;
        }
        for(File file : filesList) {
            moduleInfos.addAll(mergeReports(file));
        }
        saveReport(toAppModuleInfo(moduleInfos), reportDirectory);
    }


    private Set<ModuleInfo> mergeReports(File appReport) {
        AppModuleInfo appModuleInfo = new AppModuleInfo();
        try {
            appModuleInfo = objectMapper.readValue(appReport, AppModuleInfo.class);
            appReport.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appModuleInfo.getDependencies();
    }

    private void saveReport(AppModuleInfo appModuleInfo, String reportDirectory) {
        File reportFile = new File(reportDirectory + "/all-in-one.json");
        try {
            objectMapper.writeValue(reportFile, appModuleInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(reportFile.exists()) {
            LOG.info("File generated successfully at the location :: {}", reportFile.getAbsolutePath());
        }
    }

    private AppModuleInfo toAppModuleInfo(Set<ModuleInfo> moduleInfoSet) {
        AppModuleInfo appModuleInfo = new AppModuleInfo();
        appModuleInfo.setDependencies(moduleInfoSet);
        return appModuleInfo;
    }
}
