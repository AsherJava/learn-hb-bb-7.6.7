/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.io.tz.bean;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.bean.BaseTempTableInfo;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JioTempTableInfo
extends BaseTempTableInfo {
    private final List<LogicField> logicFields = new ArrayList<LogicField>();
    private final List<String> primaryKeyFields = new ArrayList<String>();

    public JioTempTableInfo(DataSchemeTmpTable params) {
        DataField dataField;
        LogicField dimField;
        LogicField code = new LogicField();
        code.setFieldName("MDCODE");
        code.setSize(params.getMdCode().getPrecision().intValue());
        code.setDataType(6);
        this.logicFields.add(code);
        for (DataFieldDeployInfo dimDeploy : params.getDimDeploys()) {
            dimField = new LogicField();
            dimField.setFieldName(dimDeploy.getFieldName());
            dataField = params.getFieldMap().get(dimDeploy.getDataFieldKey());
            this.parseFieldType(dataField, dimField);
            dimField.setNullable(false);
            this.logicFields.add(dimField);
        }
        for (DataFieldDeployInfo tableDimField : params.getTableDimDeploys()) {
            dimField = new LogicField();
            dimField.setFieldName(tableDimField.getFieldName());
            dataField = params.getFieldMap().get(tableDimField.getDataFieldKey());
            this.parseFieldType(dataField, dimField);
            dimField.setNullable(true);
            this.logicFields.add(dimField);
        }
        Stream.of(params.getTimePointDeploys(), params.getPeriodicDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(f -> {
            LogicField field = new LogicField();
            field.setFieldName(f.getFieldName());
            DataField dataField = params.getFieldMap().get(f.getDataFieldKey());
            this.parseFieldType(dataField, field);
            field.setNullable(true);
            return field;
        }).collect(Collectors.toCollection(() -> this.logicFields));
        LogicField id = new LogicField();
        id.setFieldName("ID");
        id.setDataType(5);
        id.setScale(10);
        this.logicFields.add(id);
        this.primaryKeyFields.add("ID");
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.primaryKeyFields;
    }
}

