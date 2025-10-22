/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.io.params.output;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.io.params.output.ImportInformations;
import java.util.List;

public class ImportParameters
extends ImportInformations {
    private static final long serialVersionUID = 5797319529006441826L;
    private List<FieldDefine> bizFields;
    private List<DimensionValueSet> bizFieldValues;

    public ImportParameters(String formKey, String formCode, String formTitle, String message) {
        super(formKey, formCode, formTitle, message, "");
    }

    public List<FieldDefine> getBizFields() {
        return this.bizFields;
    }

    public void setBizFields(List<FieldDefine> bizFields) {
        this.bizFields = bizFields;
    }

    public List<DimensionValueSet> getBizFieldValues() {
        return this.bizFieldValues;
    }

    public void setBizFieldValues(List<DimensionValueSet> bizFieldValues) {
        this.bizFieldValues = bizFieldValues;
    }
}

