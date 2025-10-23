/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeployFixDetailsDTO {
    private final String dataTableKey;
    private final List<String> tableModelKeys;
    private final Map<String, String> backupTableNames;

    public DeployFixDetailsDTO(String dataTableKey) {
        this.dataTableKey = dataTableKey;
        this.tableModelKeys = new ArrayList<String>();
        this.backupTableNames = new HashMap<String, String>();
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public List<String> getTableModelKeys() {
        return this.tableModelKeys;
    }

    public Map<String, String> getBackupTableNames() {
        return this.backupTableNames;
    }
}

