/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.Version
 */
package com.jiuqi.nvwa.sf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.Version;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.legacy.LegacyModule;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.models.SQLFile;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ModuleWrapper
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String MODULE_STATUS_STARTED = "started";
    public static final String MODULE_STATUS_TOBEUPDATE = "tobeupdate";
    public static final String MODULE_STATUS_PREUPDATE = "preupdate";
    public static final String MODULE_STATUS_UPDATED = "updated";
    public static final String MODULE_STATUS_UNMATCH = "unmatch";
    private ModuleDescriptor module;
    private Version moduleVersion;
    private Version dbVersion;
    private String status;
    @JsonIgnore
    private transient URL url;
    private List<LegacyModule> legacies = new ArrayList<LegacyModule>();
    private List<SQLFile> sqlFiles = new ArrayList<SQLFile>();

    public ModuleWrapper(ModuleDescriptor module) {
        this.module = module;
        this.moduleVersion = new Version(module.getVersion());
        this.status = MODULE_STATUS_TOBEUPDATE;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public ModuleDescriptor getModule() {
        return this.module;
    }

    public void setModule(ModuleDescriptor module) {
        this.module = module;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public List<LegacyModule> getLegacies() {
        return this.legacies;
    }

    public List<SQLFile> getSqlFiles() {
        return this.sqlFiles;
    }

    public void setDbVersion(Version dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Version getDbVersion() {
        return this.dbVersion == null ? Framework.NON_VERSION : this.dbVersion;
    }

    public Version getModuleVersion() {
        return this.moduleVersion == null ? Framework.NON_VERSION : this.moduleVersion;
    }
}

