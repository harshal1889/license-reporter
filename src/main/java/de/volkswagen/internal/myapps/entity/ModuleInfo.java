package de.volkswagen.internal.myapps.entity;

import java.util.Objects;

public class ModuleInfo {
    private String moduleName;
    private String moduleUrl;
    private String moduleVersion;
    private String moduleLicense;
    private String moduleLicenseUrl;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public String getModuleLicense() {
        return moduleLicense;
    }

    public void setModuleLicense(String moduleLicense) {
        this.moduleLicense = moduleLicense;
    }

    public String getModuleLicenseUrl() {
        return moduleLicenseUrl;
    }

    public void setModuleLicenseUrl(String moduleLicenseUrl) {
        this.moduleLicenseUrl = moduleLicenseUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleInfo that = (ModuleInfo) o;
        return moduleName.equals(that.moduleName) && moduleVersion.equals(that.moduleVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleName, moduleVersion);
    }
}
