/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.IndexField
 *  com.jiuqi.nr.common.temptable.IndexMeta
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.io.tz.bean;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.IndexField;
import com.jiuqi.nr.common.temptable.IndexMeta;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.bean.BaseTempTableInfo;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TzFzTempTableInfo
extends BaseTempTableInfo {
    private final List<LogicField> logicFields = new ArrayList<LogicField>();
    private final List<String> primaryKeyFields = new ArrayList<String>();
    private final List<IndexMeta> tempIndices = new ArrayList<IndexMeta>();

    public TzFzTempTableInfo(DataSchemeTmpTable params) {
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
        LogicField modifyTime = new LogicField();
        modifyTime.setFieldName("MODIFYTIME");
        modifyTime.setDataType(2);
        modifyTime.setNullable(true);
        modifyTime.setDataTypeName("timestamp");
        this.logicFields.add(modifyTime);
        LogicField ordinal = new LogicField();
        ordinal.setFieldName("ORDINAL");
        ordinal.setDataType(6);
        ordinal.setSize(10);
        this.logicFields.add(ordinal);
        LogicField mdOrdinal = new LogicField();
        mdOrdinal.setFieldName("MD_ORDINAL");
        mdOrdinal.setDataType(5);
        mdOrdinal.setSize(10);
        mdOrdinal.setScale(10);
        mdOrdinal.setDefaultValue("0");
        mdOrdinal.setNullable(true);
        this.logicFields.add(mdOrdinal);
        LogicField sbid = new LogicField();
        sbid.setFieldName("SBID");
        sbid.setSize(50);
        sbid.setDataType(6);
        sbid.setNullable(true);
        this.logicFields.add(sbid);
        LogicField opt = new LogicField();
        opt.setFieldName("OPT");
        opt.setScale(1);
        opt.setDataType(5);
        opt.setNullable(true);
        opt.setDefaultValue("0");
        this.logicFields.add(opt);
        this.primaryKeyFields.add("MDCODE");
        Stream.of(params.getDimDeploys(), params.getTableDimDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(this.primaryKeyFields::add);
        this.primaryKeyFields.add("ORDINAL");
        IndexField indexField = new IndexField();
        IndexMeta sbIndex = new IndexMeta();
        indexField.setFieldName("SBID");
        sbIndex.setIndexFields(Collections.singletonList(indexField));
        this.tempIndices.add(sbIndex);
        IndexMeta sbOptIndex = new IndexMeta();
        indexField = new IndexField();
        indexField.setFieldName("SBID");
        sbOptIndex.getIndexFields().add(indexField);
        indexField = new IndexField();
        indexField.setFieldName("OPT");
        sbOptIndex.getIndexFields().add(indexField);
        this.tempIndices.add(sbOptIndex);
        IndexMeta optIndex = new IndexMeta();
        indexField = new IndexField();
        indexField.setFieldName("OPT");
        optIndex.getIndexFields().add(indexField);
        this.tempIndices.add(optIndex);
        IndexMeta tableDimIndex = new IndexMeta();
        for (DataFieldDeployInfo tableDimDeploy : params.getTableDimDeploys()) {
            indexField = new IndexField();
            indexField.setFieldName(tableDimDeploy.getFieldName());
            tableDimIndex.getIndexFields().add(indexField);
        }
        this.tempIndices.add(tableDimIndex);
        if (!params.isFull() && !params.getPeriodicDeploys().isEmpty()) {
            LogicField rptBizKeyOrder = new LogicField();
            rptBizKeyOrder.setFieldName("BIZKEYORDER");
            rptBizKeyOrder.setSize(50);
            rptBizKeyOrder.setDataType(6);
            rptBizKeyOrder.setNullable(true);
            this.logicFields.add(rptBizKeyOrder);
            LogicField rptOpt = new LogicField();
            rptOpt.setFieldName("RPT_OPT");
            rptOpt.setScale(1);
            rptOpt.setDataType(5);
            rptOpt.setNullable(true);
            rptOpt.setDefaultValue("0");
            this.logicFields.add(rptOpt);
            IndexMeta bizKeyOrderOptIndex = new IndexMeta();
            indexField = new IndexField();
            indexField.setFieldName("BIZKEYORDER");
            bizKeyOrderOptIndex.getIndexFields().add(indexField);
            indexField = new IndexField();
            indexField.setFieldName("RPT_OPT");
            bizKeyOrderOptIndex.getIndexFields().add(indexField);
            this.tempIndices.add(bizKeyOrderOptIndex);
            IndexMeta rptOptIndex = new IndexMeta();
            indexField = new IndexField();
            indexField.setFieldName("RPT_OPT");
            rptOptIndex.getIndexFields().add(indexField);
            this.tempIndices.add(rptOptIndex);
        }
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.primaryKeyFields;
    }

    public List<IndexMeta> getTempIndices() {
        return this.tempIndices;
    }
}

