/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.result;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class EntityDataRow {
    private String recKey;
    private String entityKeyData;
    private String title;
    private String parentId;
    private Map<String, Object> rowData;
    private String tempId;
    private boolean checkFailed;
    private boolean needSync;
    private boolean ignoreCodeCheck;

    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntityKeyData() {
        return this.entityKeyData;
    }

    public void setEntityKeyData(String entityKeyData) {
        this.entityKeyData = entityKeyData;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Map<String, Object> getRowData() {
        return this.rowData;
    }

    public void setRowData(Map<String, Object> rowData) {
        this.rowData = rowData;
    }

    public EntityDataRow putRowData(String key, Object value) {
        if (CollectionUtils.isEmpty(this.rowData)) {
            this.rowData = new HashMap<String, Object>(16);
        }
        this.rowData.put(key, value);
        return this;
    }

    public Object getValue(String key) {
        if (CollectionUtils.isEmpty(this.rowData)) {
            this.rowData = new HashMap<String, Object>(16);
        }
        return this.rowData.get(key);
    }

    public String getTempId() {
        return this.tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public boolean isCheckFailed() {
        return this.checkFailed;
    }

    public void setCheckFailed(boolean checkFailed) {
        this.checkFailed = checkFailed;
    }

    public boolean isNeedSync() {
        return this.needSync;
    }

    public void setNeedSync(boolean needSync) {
        this.needSync = needSync;
    }

    public boolean isIgnoreCodeCheck() {
        return this.ignoreCodeCheck;
    }

    public void setIgnoreCodeCheck(boolean ignoreCodeCheck) {
        this.ignoreCodeCheck = ignoreCodeCheck;
    }
}

