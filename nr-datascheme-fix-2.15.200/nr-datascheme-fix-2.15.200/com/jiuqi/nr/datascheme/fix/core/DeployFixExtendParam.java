/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.core;

import java.util.Map;
import java.util.Set;

public class DeployFixExtendParam {
    private String dataSchemeKey;
    private String dataTableKey;
    private Set<String> tableModelKey;
    private boolean tableNeedBackUp;
    private Map<Object, Object> fixContext;

    public DeployFixExtendParam() {
    }

    public DeployFixExtendParam(String dataSchemeKey, String dataTableKey, Set<String> tableModelKey, boolean tableNeedBackUp) {
        this.dataSchemeKey = dataSchemeKey;
        this.dataTableKey = dataTableKey;
        this.tableModelKey = tableModelKey;
        this.tableNeedBackUp = tableNeedBackUp;
    }

    public DeployFixExtendParam(String dataTableKey, Set<String> tableModelKey, boolean tableNeedBackUp, Map<Object, Object> fixInfo) {
        this.dataTableKey = dataTableKey;
        this.tableModelKey = tableModelKey;
        this.tableNeedBackUp = tableNeedBackUp;
        this.fixContext = fixInfo;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public boolean isTableNeedBackUp() {
        return this.tableNeedBackUp;
    }

    public void setTableNeedBackUp(boolean tableNeedBackUp) {
        this.tableNeedBackUp = tableNeedBackUp;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public boolean isDeleteLogicTable() {
        return this.tableNeedBackUp;
    }

    public void setDeleteLogicTable(boolean deleteLogicTable) {
        this.tableNeedBackUp = deleteLogicTable;
    }

    public Set<String> getTableModelKey() {
        return this.tableModelKey;
    }

    public void setTableModelKey(Set<String> tableModelKey) {
        this.tableModelKey = tableModelKey;
    }

    public Map<Object, Object> getFixContext() {
        return this.fixContext;
    }

    public void setFixContext(Map<Object, Object> fixInfo) {
        this.fixContext = fixInfo;
    }
}

