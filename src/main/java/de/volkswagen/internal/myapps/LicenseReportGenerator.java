package de.volkswagen.internal.myapps;

import de.volkswagen.internal.myapps.usecase.GenerateLicenseReport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LicenseReportGenerator {

    public static void main(String[] args) {
        GenerateLicenseReport generateLicenseReport = new GenerateLicenseReport();
        generateLicenseReport.getConsolidatedReport(args[0]);
    }

}
