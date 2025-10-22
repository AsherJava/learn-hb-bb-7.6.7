/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.io.tz.bean;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.io.tz.bean.BaseTempTableInfo;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class StateTempTableInfo
extends BaseTempTableInfo {
    private final List<LogicField> logicFields = new ArrayList<LogicField>();
    private final List<String> primaryKeyFields = new ArrayList<String>();

    public StateTempTableInfo(DataSchemeTmpTable params) {
        LogicField sbId = new LogicField();
        sbId.setFieldName("SBID");
        sbId.setSize(50);
        sbId.setDataType(6);
        this.logicFields.add(sbId);
        Stream.of(params.getTimePointDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(f -> {
            LogicField field = new LogicField();
            field.setFieldName(f.getFieldName());
            DataField dataField = params.getFieldMap().get(f.getDataFieldKey());
            this.parseFieldType(dataField, field);
            field.setNullable(true);
            return field;
        }).forEach(this.logicFields::add);
        LogicField validDatatime = new LogicField();
        validDatatime.setFieldName("VALIDDATATIME");
        validDatatime.setSize(10);
        validDatatime.setDataType(6);
        validDatatime.setNullable(true);
        this.logicFields.add(validDatatime);
        LogicField modifyTime = new LogicField();
        modifyTime.setFieldName("MODIFYTIME");
        modifyTime.setDataType(2);
        modifyTime.setNullable(true);
        modifyTime.setDataTypeName("timestamp");
        this.logicFields.add(modifyTime);
        this.primaryKeyFields.add("SBID");
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.primaryKeyFields;
    }
}

