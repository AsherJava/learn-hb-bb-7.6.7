/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.SQLModelException
 *  com.jiuqi.bi.database.sql.model.SortField
 *  com.jiuqi.bi.database.sql.model.fields.RankField
 *  com.jiuqi.bi.database.sql.model.tables.InnerTable
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.RankField;
import com.jiuqi.bi.database.sql.model.tables.InnerTable;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.tz.service.impl.BaseInsertLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.JdbcTemplate;

public class CopyInputData2TempTable
extends BaseInsertLoader {
    private final TzParams tzParams;

    public CopyInputData2TempTable(JdbcTemplate jdbcTemplate, TzParams params) {
        super(jdbcTemplate);
        this.tzParams = params;
    }

    @Override
    protected void loadSrc() {
        try {
            ISQLTable table = this.buildSrcTable(this.tzParams);
            this.loader.setSourceTable(table);
        }
        catch (SQLModelException e) {
            throw new TzCopyDataException("\u5c06\u6765\u6e90\u8868\u6570\u636e\u590d\u5236\u5230\u8f85\u52a9\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
    }

    @Override
    protected void loadDest() {
        SimpleTable table = this.buildDestTable(this.tzParams);
        this.loader.setDestTable(table);
    }

    @Override
    protected void loadFieldMaps() {
        ISQLTable srcTable = this.loader.getSourceTable();
        SimpleTable dTable = this.loader.getDestTable();
        List fieldMaps = this.loader.getFieldMaps();
        List fields = srcTable.fields();
        List dFields = dTable.fields();
        for (int i = 0; i < fields.size(); ++i) {
            LoadFieldMap map = new LoadFieldMap((ISQLField)fields.get(i), (ISQLField)dFields.get(i), false, true);
            fieldMaps.add(map);
        }
    }

    private ISQLTable buildSrcTable(TzParams params) throws SQLModelException {
        DataSchemeTmpTable tmpTable = params.getTmpTable();
        ArrayList<String> nvlFields = new ArrayList<String>(tmpTable.getTableDimDeploys().size());
        boolean toChar = this.database.isDatabase("ORACLE") || this.database.isDatabase("Informix");
        for (DataFieldDeployInfo tableDimDeploy : tmpTable.getTableDimDeploys()) {
            StringBuilder elseChar = new StringBuilder(tableDimDeploy.getFieldName());
            for (DataField tableDimField : tmpTable.getTableDimFields()) {
                String nvlField;
                if (!tableDimDeploy.getDataFieldKey().equals(tableDimField.getKey())) continue;
                if (tableDimField.getDataFieldType().equals((Object)DataFieldType.STRING)) {
                    if (toChar) {
                        elseChar = new StringBuilder("to_char(" + elseChar + ")");
                    }
                    nvlField = "case when " + tableDimDeploy.getFieldName() + " is null or " + tableDimDeploy.getFieldName() + " = '' then '" + "-" + "' else " + elseChar + " end as " + tableDimDeploy.getFieldName();
                    nvlFields.add(nvlField);
                    continue;
                }
                nvlField = "T0." + tableDimDeploy.getFieldName();
                nvlFields.add(nvlField);
            }
        }
        List fieldsStr = Stream.of(tmpTable.getDimDeploys(), tmpTable.getTimePointDeploys(), tmpTable.getPeriodicDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).map(r -> "T0." + r).collect(Collectors.toList());
        fieldsStr.addAll(nvlFields);
        String innerSql = "SELECT T0.MDCODE," + String.join((CharSequence)",", fieldsStr) + ",T0." + "ID" + " FROM " + params.getSourceData() + " T0 WHERE T0." + "MDCODE" + " IN (SELECT " + "MDCODE" + " FROM " + params.getMdCodeTable() + ")";
        InnerTable innerTable = new InnerTable(innerSql, "T1");
        ISQLField id = innerTable.addField("ID");
        QueryTable queryTable = new QueryTable((ISQLTable)innerTable, "T2");
        ISQLField mdCode = queryTable.addField("MDCODE");
        RankField rankField = new RankField((ISQLTable)queryTable, "ORDINAL");
        RankField mdRankField = new RankField((ISQLTable)queryTable, "MD_ORDINAL");
        rankField.partitionFields().add(mdCode);
        mdRankField.partitionFields().add(mdCode);
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).forEach(r -> {
            ISQLField mField = queryTable.addField(r.getFieldName());
            rankField.partitionFields().add(mField);
        });
        rankField.orderFields().add(new SortField((ISQLTable)queryTable, id));
        mdRankField.orderFields().add(new SortField((ISQLTable)queryTable, id));
        Stream.of(tmpTable.getTimePointDeploys(), tmpTable.getPeriodicDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(arg_0 -> ((QueryTable)queryTable).addField(arg_0));
        queryTable.addField((ISQLField)rankField);
        queryTable.addField((ISQLField)mdRankField);
        return queryTable;
    }

    private SimpleTable buildDestTable(TzParams params) {
        DataSchemeTmpTable tmpTable = params.getTmpTable();
        String tempTableName = params.getTempTableName();
        SimpleTable dTable = new SimpleTable(tempTableName, "FT");
        dTable.addField("MDCODE");
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys(), tmpTable.getTimePointDeploys(), tmpTable.getPeriodicDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(arg_0 -> ((SimpleTable)dTable).addField(arg_0));
        dTable.addField("ORDINAL");
        dTable.addField("MD_ORDINAL");
        return dTable;
    }
}

