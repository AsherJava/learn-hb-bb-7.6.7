/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import java.util.List;

public class DataModelTemplateEntity {
    private List<DataModelColumn> templateFields;
    private List<DataModelIndex> templateIndexs;

    public List<DataModelColumn> getTemplateFields() {
        return this.templateFields;
    }

    public void setTemplateFields(List<DataModelColumn> templateFields) {
        this.templateFields = templateFields;
    }

    public List<DataModelIndex> getTemplateIndexs() {
        return this.templateIndexs;
    }

    public void setTemplateIndexs(List<DataModelIndex> templateIndexs) {
        this.templateIndexs = templateIndexs;
    }
}

