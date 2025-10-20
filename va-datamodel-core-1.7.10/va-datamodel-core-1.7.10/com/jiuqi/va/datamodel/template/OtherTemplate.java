/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.StorageFieldConsts
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.DataModelTemplate
 */
package com.jiuqi.va.datamodel.template;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.domain.common.StorageFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="vaDataModelOtherTemplate")
public class OtherTemplate
implements DataModelTemplate {
    public DataModelType.BizType getBizType() {
        return DataModelType.BizType.OTHER;
    }

    public String getName() {
        return DataModelType.BizType.OTHER.toString();
    }

    public String getTitle() {
        return DataModelCoreI18nUtil.getMessage("datamodel.attribute.biztype.other", new Object[0]);
    }

    public List<DataModelColumn> getTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle(StorageFieldConsts.getFtID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        return columns;
    }

    public List<DataModelIndex> getTemplateIndexs(String tableName) {
        return new ArrayList<DataModelIndex>();
    }
}

