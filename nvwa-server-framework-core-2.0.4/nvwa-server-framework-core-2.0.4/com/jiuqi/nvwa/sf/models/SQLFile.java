/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.Version
 */
package com.jiuqi.nvwa.sf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.Version;
import java.io.Serializable;
import java.net.URL;

public class SQLFile
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String filename;
    private Version version;
    private String tag;
    private String shortModule;
    private String belongsModuleId;
    private String legacyModuleId;
    @JsonIgnore
    private transient URL url;
    private String sql;
    public static final String SQL_FILE_EXTENSION = ".sqlx";

    public String getBelongsModuleId() {
        return this.belongsModuleId;
    }

    public void setBelongsModuleId(String belongsModuleId) {
        this.belongsModuleId = belongsModuleId;
    }

    public void setLegacyModuleId(String legacyModuleId) {
        this.legacyModuleId = legacyModuleId;
    }

    public String getLegacyModuleId() {
        return this.legacyModuleId;
    }

    public boolean isFull() {
        return "full".equalsIgnoreCase(this.tag);
    }

    public String getShortModule() {
        return this.shortModule;
    }

    public void setShortModule(String shortModule) {
        this.shortModule = shortModule;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Version getVersion() {
        return this.version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public static void fillProperties(SQLFile sqlFile, String filename, String shortName) {
        if (filename == null) {
            throw new IllegalArgumentException("\u975e\u6cd5\u7684SQL\u6587\u4ef6\u540d\uff0cnull");
        }
        if (!filename.toLowerCase().endsWith(SQL_FILE_EXTENSION)) {
            throw new IllegalArgumentException("\u975e\u6cd5\u7684SQL\u6587\u4ef6\u540d\uff0c" + filename);
        }
        filename = filename.substring(0, filename.length() - SQL_FILE_EXTENSION.length());
        String[] fields = filename.split("-");
        switch (fields.length) {
            case 1: {
                if (shortName != null && shortName.equalsIgnoreCase(fields[0])) {
                    sqlFile.shortModule = shortName;
                    sqlFile.version = new Version("0.0.0");
                    break;
                }
                try {
                    sqlFile.version = new Version(fields[0]);
                    break;
                }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("\u975e\u6cd5\u7684SQL\u6587\u4ef6\u540d\uff0c" + filename, e);
                }
            }
            case 2: {
                try {
                    sqlFile.version = new Version(fields[0]);
                    sqlFile.tag = fields[1];
                    break;
                }
                catch (IllegalArgumentException e) {
                    try {
                        sqlFile.version = new Version(fields[1]);
                        sqlFile.shortModule = fields[0];
                        break;
                    }
                    catch (IllegalArgumentException e2) {
                        throw new IllegalArgumentException("\u975e\u6cd5\u7684SQL\u6587\u4ef6\u540d\uff0c" + filename);
                    }
                }
            }
            case 3: {
                try {
                    sqlFile.version = new Version(fields[1]);
                    sqlFile.shortModule = fields[0];
                    sqlFile.tag = fields[2];
                    break;
                }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("\u975e\u6cd5\u7684SQL\u6587\u4ef6\u540d\uff0c" + filename, e);
                }
            }
            default: {
                throw new IllegalArgumentException("\u975e\u6cd5\u7684SQL\u6587\u4ef6\u540d\uff0c" + filename);
            }
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.belongsModuleId).append("-").append(this.version).append(this.isFull() ? "*" : "");
        return sb.toString();
    }
}

