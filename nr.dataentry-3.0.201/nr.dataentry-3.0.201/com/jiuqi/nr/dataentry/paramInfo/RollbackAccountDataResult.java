/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;

public class RollbackAccountDataResult {
    private int totalCount;
    private int errorCount;
    private List<RollbackAccountDataItem> rollbackAccountDataItems;

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public List<RollbackAccountDataItem> getRollbackAccountDataItems() {
        return this.rollbackAccountDataItems;
    }

    public void setRollbackAccountDataItems(List<RollbackAccountDataItem> rollbackAccountDataItems) {
        this.rollbackAccountDataItems = rollbackAccountDataItems;
    }

    class RollbackAccountDataItem {
        private Map<Integer, String> messages;
        private List<String> formKeys;
        private DimensionValueSet errorDimension;

        RollbackAccountDataItem() {
        }

        public Map<Integer, String> getMessages() {
            return this.messages;
        }

        public void setMessages(Map<Integer, String> messages) {
            this.messages = messages;
        }

        public List<String> getFormKeys() {
            return this.formKeys;
        }

        public void setFormKeys(List<String> formKeys) {
            this.formKeys = formKeys;
        }

        public DimensionValueSet getErrorDimension() {
            return this.errorDimension;
        }

        public void setErrorDimension(DimensionValueSet errorDimension) {
            this.errorDimension = errorDimension;
        }
    }
}

