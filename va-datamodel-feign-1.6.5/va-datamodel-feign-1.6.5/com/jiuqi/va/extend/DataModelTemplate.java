/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.List;

public interface DataModelTemplate {
    public String getName();

    public String getTitle();

    default public DataModelType.BizType getBizType() {
        return DataModelType.BizType.valueOf(this.getName());
    }

    default public int getSubBizType() {
        return this.getName().hashCode();
    }

    default public int getOrdinal() {
        return this.getSubBizType();
    }

    public List<DataModelColumn> getTemplateFields();

    public List<DataModelIndex> getTemplateIndexs(String var1);

    default public boolean isShowSubBizType() {
        return true;
    }
}

