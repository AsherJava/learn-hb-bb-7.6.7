/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import java.util.List;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;

public class SingleCompareDTO {
    private CompareDataType compareDataType;
    private List<String> compareDataKeys;
    private String compareKey;
    private CompareContextType compareContextType;
    private CompareUpdateType updateType;

    public List<String> getCompareDataKeys() {
        return this.compareDataKeys;
    }

    public void setCompareDataKeys(List<String> compareDataKeys) {
        this.compareDataKeys = compareDataKeys;
    }

    public String getCompareKey() {
        return this.compareKey;
    }

    public void setCompareKey(String compareKey) {
        this.compareKey = compareKey;
    }

    public CompareDataType getCompareDataType() {
        return this.compareDataType;
    }

    public void setCompareDataType(CompareDataType compareDataType) {
        this.compareDataType = compareDataType;
    }

    public CompareContextType getCompareContextType() {
        return this.compareContextType;
    }

    public void setCompareContextType(CompareContextType compareContextType) {
        this.compareContextType = compareContextType;
    }

    public CompareUpdateType getUpdateType() {
        return this.updateType;
    }

    public void setUpdateType(CompareUpdateType updateType) {
        this.updateType = updateType;
    }
}

