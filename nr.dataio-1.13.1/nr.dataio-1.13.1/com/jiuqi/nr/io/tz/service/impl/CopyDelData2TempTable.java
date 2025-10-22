/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.SortField
 *  com.jiuqi.bi.database.sql.model.fields.EvalField
 *  com.jiuqi.bi.database.sql.model.fields.RankField
 *  com.jiuqi.bi.database.sql.model.filters.IsNullFilter
 *  com.jiuqi.bi.database.sql.model.filters.NotExistsFilter
 *  com.jiuqi.bi.database.sql.model.tables.FieldMap
 *  com.jiuqi.bi.database.sql.model.tables.InnerTable
 *  com.jiuqi.bi.database.sql.model.tables.JoinMode
 *  com.jiuqi.bi.database.sql.model.tables.JoinedTable
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.bi.database.sql.model.tables.SubTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.fields.RankField;
import com.jiuqi.bi.database.sql.model.filters.IsNullFilter;
import com.jiuqi.bi.database.sql.model.filters.NotExistsFilter;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.InnerTable;
import com.jiuqi.bi.database.sql.model.tables.JoinMode;
import com.jiuqi.bi.database.sql.model.tables.JoinedTable;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.service.impl.BaseInsertLoader;
import com.jiuqi.nr.io.tz.service.impl.TableHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

public class CopyDelData2TempTable
extends BaseInsertLoader {
    private final TzParams tzParams;

    public CopyDelData2TempTable(JdbcTemplate jdbcTemplate, TzParams params) {
        super(jdbcTemplate);
        this.tzParams = params;
    }

    @Override
    protected void loadSrc() {
        SimpleTable left = TableHelper.buildTempSimpleTable(this.tzParams.getTmpTable(), this.tzParams.getTempTableName());
        left.setAlias("LT");
        ISQLTable right = TableHelper.buildRankSbTable(this.tzParams);
        right.setAlias("RT");
        DataSchemeTmpTable tmpTable = this.tzParams.getTmpTable();
        JoinedTable joinedTable = new JoinedTable("JT");
        joinedTable.setMainTable((ISQLTable)left);
        SubTable subTable = new SubTable(right, JoinMode.RIGHT);
        joinedTable.subTables().add(subTable);
        QueryTable temp = new QueryTable(right.alias(), "");
        joinedTable.addField(temp.createField("MDCODE"));
        for (DataFieldDeployInfo dimDeploy : tmpTable.getDimDeploys()) {
            joinedTable.addField(temp.createField(dimDeploy.getFieldName()));
        }
        for (DataFieldDeployInfo tableDimField : tmpTable.getTableDimDeploys()) {
            joinedTable.addField(temp.createField(tableDimField.getFieldName()));
        }
        joinedTable.addField(temp.createField("ORDINAL"));
        joinedTable.addField(temp.createField("SBID"));
        joinedTable.addField((ISQLField)new EvalField((ISQLTable)joinedTable, String.valueOf(-1), "OPT"));
        ISQLField sbField = joinedTable.createField("SBID");
        IsNullFilter nullFilter = new IsNullFilter(sbField);
        joinedTable.whereFilters().add(nullFilter);
        this.buildJoinFieldMap(subTable.fieldMaps(), right, left);
        this.loader.setSourceTable((ISQLTable)joinedTable);
    }

    @Override
    protected void loadDest() {
        SimpleTable dt = TableHelper.buildTempSimpleTable(this.tzParams.getTmpTable(), this.tzParams.getTempTableName());
        dt.addField("OPT");
        this.loader.setDestTable(dt);
    }

    @Override
    protected void loadFieldMaps() {
        List fieldMaps = this.loader.getFieldMaps();
        List fields = this.loader.getSourceTable().fields();
        List destFields = this.loader.getDestTable().fields();
        Assert.isTrue(fields.size() == destFields.size(), "\u6e90\u8868\u548c\u4e34\u65f6\u8868\u5339\u914d\u9519\u8bef");
        for (int i = 0; i < destFields.size(); ++i) {
            ISQLField isqlField = (ISQLField)destFields.get(i);
            boolean isKey = !"SBID".equals(isqlField.fieldName()) && !"OPT".equals(isqlField.fieldName());
            fieldMaps.add(new LoadFieldMap((ISQLField)fields.get(i), isqlField, isKey));
        }
    }

    private void buildJoinFieldMap(List<FieldMap> fieldMaps, ISQLTable src, SimpleTable dest) {
        List fields = src.fields();
        List destFields = dest.fields();
        Assert.isTrue(fields.size() == destFields.size(), "\u6e90\u8868\u548c\u4e34\u65f6\u8868\u5339\u914d\u9519\u8bef");
        for (int i = 0; i < fields.size(); ++i) {
            if (i == fields.size() - 1) continue;
            fieldMaps.add(new FieldMap((ISQLField)fields.get(i), (ISQLField)destFields.get(i)));
        }
    }

    public void loadExistsSrc() {
        DataSchemeTmpTable dataSchemeTmpTable = this.tzParams.getTmpTable();
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("MDCODE");
        fields.add("SBID");
        Stream.of(dataSchemeTmpTable.getDimDeploys(), dataSchemeTmpTable.getTableDimDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(fields::add);
        fields.add("FLOATORDER");
        String sql = "SELECT " + String.join((CharSequence)",", fields) + " FROM " + dataSchemeTmpTable.getTzTableName() + " T0 WHERE T0." + "MDCODE" + " IN (SELECT T1." + "MDCODE" + " FROM " + this.tzParams.getMdCodeTable() + " T1)";
        InnerTable innerTable = new InnerTable(sql, "T2");
        ISQLField floatOrder = innerTable.addField("FLOATORDER");
        QueryTable queryTable = new QueryTable((ISQLTable)innerTable, "T3");
        ISQLField mdCode = queryTable.addField("MDCODE");
        RankField rankField = new RankField((ISQLTable)queryTable, "ORDINAL");
        rankField.partitionFields().add(mdCode);
        Stream.of(dataSchemeTmpTable.getDimDeploys(), dataSchemeTmpTable.getTableDimDeploys()).flatMap(Collection::stream).forEach(r -> {
            ISQLField mField = queryTable.addField(r.getFieldName());
            rankField.partitionFields().add(mField);
        });
        rankField.orderFields().add(new SortField((ISQLTable)queryTable, floatOrder));
        queryTable.addField((ISQLField)rankField);
        queryTable.addField("SBID");
        Collection isqlFilters = queryTable.whereFilters();
        SimpleTable tzExTable = new SimpleTable(this.tzParams.getTempTableName(), "T4");
        NotExistsFilter filter = new NotExistsFilter((ISQLTable)tzExTable);
        isqlFilters.add(filter);
        filter.fieldMaps().add(new FieldMap(tzExTable.addField("MDCODE"), innerTable.findField("MDCODE")));
        Stream.of(dataSchemeTmpTable.getDimDeploys(), dataSchemeTmpTable.getTableDimDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(f -> filter.fieldMaps().add(new FieldMap(tzExTable.addField(f), innerTable.findField(f))));
        filter.fieldMaps().add(new FieldMap(tzExTable.addField("ORDINAL"), (ISQLField)rankField));
    }
}

