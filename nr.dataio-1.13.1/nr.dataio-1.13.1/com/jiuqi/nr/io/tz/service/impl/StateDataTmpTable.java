/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.TempIndex;
import com.jiuqi.nr.io.tz.service.impl.BaseTmpTableCreateDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class StateDataTmpTable
extends BaseTmpTableCreateDao {
    @Override
    protected List<LogicField> buildTmpFields(DataSchemeTmpTable params) {
        ArrayList<LogicField> tmpFields = new ArrayList<LogicField>();
        LogicField sbId = new LogicField();
        sbId.setFieldName("SBID");
        sbId.setSize(50);
        sbId.setDataType(6);
        tmpFields.add(sbId);
        Stream.of(params.getTimePointDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(f -> {
            LogicField field = new LogicField();
            field.setFieldName(f.getFieldName());
            DataField dataField = params.getFieldMap().get(f.getDataFieldKey());
            this.parseFieldType(dataField, field);
            field.setNullable(true);
            return field;
        }).forEach(tmpFields::add);
        LogicField validDatatime = new LogicField();
        validDatatime.setFieldName("VALIDDATATIME");
        validDatatime.setSize(10);
        validDatatime.setDataType(6);
        validDatatime.setNullable(true);
        tmpFields.add(validDatatime);
        LogicField modifyTime = new LogicField();
        modifyTime.setFieldName("MODIFYTIME");
        modifyTime.setDataType(2);
        modifyTime.setNullable(true);
        modifyTime.setDataTypeName("timestamp");
        tmpFields.add(modifyTime);
        return tmpFields;
    }

    @Override
    protected Set<TempIndex> getIndexFields(DataSchemeTmpTable tmpTable) {
        return null;
    }

    @Override
    protected List<String> pkFieldName(DataSchemeTmpTable tmpTable) {
        ArrayList<String> pk = new ArrayList<String>();
        pk.add("SBID");
        return pk;
    }

    @Override
    protected List<LogicField> buildExtTmpFields(DataSchemeTmpTable tmpTable) {
        return null;
    }
}

