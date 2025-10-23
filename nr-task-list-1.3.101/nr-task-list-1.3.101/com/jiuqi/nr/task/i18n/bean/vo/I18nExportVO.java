/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.bean.vo;

import java.util.List;

public class I18nExportVO {
    private List<String> exportTasks;
    private List<Integer> exportResourceTypes;

    public List<String> getExportTasks() {
        return this.exportTasks;
    }

    public void setExportTasks(List<String> exportTasks) {
        this.exportTasks = exportTasks;
    }

    public List<Integer> getExportResourceTypes() {
        return this.exportResourceTypes;
    }

    public void setExportResourceTypes(List<Integer> exportResourceTypes) {
        this.exportResourceTypes = exportResourceTypes;
    }
}

