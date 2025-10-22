/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

public class DataDockingBlockVO {
    private List<String> fieldCodes;
    private List<List<String>> fieldValues;
    private Boolean floatBlock;
    private List<FieldDefine> fieldDefines;

    public List<String> getFieldCodes() {
        return this.fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }

    public Boolean getFloatBlock() {
        return this.floatBlock;
    }

    public void setFloatBlock(Boolean floatBlock) {
        this.floatBlock = floatBlock;
    }

    public List<List<String>> getFieldValues() {
        return this.fieldValues;
    }

    public void setFieldValues(List<List<String>> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public List<FieldDefine> getFieldDefines() {
        return this.fieldDefines;
    }

    public void setFieldDefines(List<FieldDefine> fieldDefines) {
        this.fieldDefines = fieldDefines;
    }
}

