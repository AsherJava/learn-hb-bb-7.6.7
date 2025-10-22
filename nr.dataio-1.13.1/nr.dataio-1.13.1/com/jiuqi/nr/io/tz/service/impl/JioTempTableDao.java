/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.TempIndex;
import com.jiuqi.nr.io.tz.service.impl.BaseTmpTableCreateDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class JioTempTableDao
extends BaseTmpTableCreateDao {
    @Override
    protected Set<TempIndex> getIndexFields(DataSchemeTmpTable tmpTable) {
        return null;
    }

    @Override
    protected List<String> pkFieldName(DataSchemeTmpTable tmpTable) {
        return Collections.singletonList("ID");
    }

    @Override
    protected List<LogicField> buildExtTmpFields(DataSchemeTmpTable tmpTable) {
        ArrayList<LogicField> list = new ArrayList<LogicField>();
        LogicField id = new LogicField();
        id.setFieldName("ID");
        id.setDataType(5);
        id.setScale(10);
        list.add(id);
        return list;
    }

    @Override
    protected List<LogicField> buildTmpFields(DataSchemeTmpTable params) {
        DataField dataField;
        LogicField dimField;
        ArrayList<LogicField> tmpFields = new ArrayList<LogicField>();
        LogicField code = new LogicField();
        code.setFieldName("MDCODE");
        code.setSize(params.getMdCode().getPrecision().intValue());
        code.setDataType(6);
        tmpFields.add(code);
        for (DataFieldDeployInfo dimDeploy : params.getDimDeploys()) {
            dimField = new LogicField();
            dimField.setFieldName(dimDeploy.getFieldName());
            dataField = params.getFieldMap().get(dimDeploy.getDataFieldKey());
            this.parseFieldType(dataField, dimField);
            dimField.setNullable(false);
            tmpFields.add(dimField);
        }
        for (DataFieldDeployInfo tableDimField : params.getTableDimDeploys()) {
            dimField = new LogicField();
            dimField.setFieldName(tableDimField.getFieldName());
            dataField = params.getFieldMap().get(tableDimField.getDataFieldKey());
            this.parseFieldType(dataField, dimField);
            dimField.setNullable(true);
            tmpFields.add(dimField);
        }
        Stream.of(params.getTimePointDeploys(), params.getPeriodicDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(f -> {
            LogicField field = new LogicField();
            field.setFieldName(f.getFieldName());
            DataField dataField = params.getFieldMap().get(f.getDataFieldKey());
            this.parseFieldType(dataField, field);
            field.setNullable(true);
            return field;
        }).collect(Collectors.toCollection(() -> tmpFields));
        return tmpFields;
    }
}

