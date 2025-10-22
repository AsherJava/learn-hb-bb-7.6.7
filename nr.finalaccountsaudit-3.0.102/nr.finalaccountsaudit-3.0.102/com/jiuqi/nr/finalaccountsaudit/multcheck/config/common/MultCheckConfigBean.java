/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.common;

import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel;
import java.util.HashMap;

public class MultCheckConfigBean
implements IConfigModel {
    private boolean multCheckEnable;
    private boolean recordEnable;
    private HashMap multCheckMap;

    public boolean getMultCheckEnable() {
        return this.multCheckEnable;
    }

    public void setMultCheckEnable(boolean multCheckEnable) {
        this.multCheckEnable = multCheckEnable;
    }

    public boolean getRecordEnable() {
        return this.recordEnable;
    }

    public void setRecordEnable(boolean recordEnable) {
        this.recordEnable = recordEnable;
    }

    public HashMap getMultCheckMap() {
        return this.multCheckMap;
    }

    public void setMultCheckMap(HashMap multCheckMap) {
        this.multCheckMap = multCheckMap;
    }

    public boolean isEnable() {
        return this.multCheckEnable;
    }

    public void setEnable(boolean enable) {
        this.multCheckEnable = enable;
    }

    public static class MultCheckTableDataItem<T> {
        private int index;
        private String key;
        private String multCheckKey;
        private String multCheckType;
        private String multCheckName;
        private T auditScope;

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }

        public void setMultCheckName(String multCheckName) {
            this.multCheckName = multCheckName;
        }

        public String getMultCheckName() {
            return this.multCheckName;
        }

        public void setAuditScope(T auditScope) {
            this.auditScope = auditScope;
        }

        public T getAuditScope() {
            return this.auditScope;
        }

        public String getMultCheckType() {
            return this.multCheckType;
        }

        public void setMultCheckType(String multCheckType) {
            this.multCheckType = multCheckType;
        }

        public String getMultCheckKey() {
            return this.multCheckKey;
        }

        public void setMultCheckKey(String multCheckKey) {
            this.multCheckKey = multCheckKey;
        }
    }
}

