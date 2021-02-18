package de.volkswagen.internal.myapps.entity;

import java.util.HashSet;
import java.util.Set;

public class AppModuleInfo {
    private Set<ModuleInfo> dependencies = new HashSet<>();

    public AppModuleInfo() {
    }

    public Set<ModuleInfo> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<ModuleInfo> dependencies) {
        this.dependencies = dependencies;
    }
}
