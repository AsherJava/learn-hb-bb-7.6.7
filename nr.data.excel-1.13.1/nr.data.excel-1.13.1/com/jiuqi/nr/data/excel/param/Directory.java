/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.param.Excel;
import java.util.List;

public class Directory {
    private String directory;
    private List<Excel> excels;

    public Directory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public List<Excel> getExcels() {
        return this.excels;
    }

    public void setExcels(List<Excel> excels) {
        this.excels = excels;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        return String.valueOf(this.directory);
    }

    public boolean equals(Object obj) {
        return obj != null && obj.hashCode() == this.hashCode();
    }
}

