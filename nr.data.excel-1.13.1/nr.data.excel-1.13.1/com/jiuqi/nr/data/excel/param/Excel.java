/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.param.Directory;
import com.jiuqi.nr.data.excel.param.Sheet;
import java.util.List;

public class Excel {
    private Directory directory;
    private String fileName;
    private List<Sheet> sheets;

    public Excel(Directory directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    public Directory getDirectory() {
        return this.directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Sheet> getSheets() {
        return this.sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        return this.directory + this.fileName;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.hashCode() == this.hashCode();
    }
}

