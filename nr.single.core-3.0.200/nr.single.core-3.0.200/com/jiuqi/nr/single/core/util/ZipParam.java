/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZipParam {
    private Set<String> filterFileNames;
    private List<String> fileNames;
    private List<String> pathNames;

    public List<String> getFileNames() {
        if (this.fileNames == null) {
            this.fileNames = new ArrayList<String>();
        }
        return this.fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<String> getPathNames() {
        if (this.pathNames == null) {
            this.pathNames = new ArrayList<String>();
        }
        return this.pathNames;
    }

    public void setPathNames(List<String> pathNames) {
        this.pathNames = pathNames;
    }

    public Set<String> getFilterFileNames() {
        if (this.filterFileNames == null) {
            this.filterFileNames = new HashSet<String>();
        }
        return this.filterFileNames;
    }

    public void setFilterFileNames(Set<String> filterFileNames) {
        this.filterFileNames = filterFileNames;
    }
}

