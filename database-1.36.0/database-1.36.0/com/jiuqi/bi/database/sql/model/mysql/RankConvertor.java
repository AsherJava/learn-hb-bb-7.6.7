/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.NameProvider
 */
package com.jiuqi.bi.database.sql.model.mysql;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.fields.RankField;
import com.jiuqi.bi.database.sql.model.fields.RefField;
import com.jiuqi.bi.database.sql.model.mysql.ParamTable;
import com.jiuqi.bi.database.sql.model.tables.JoinMode;
import com.jiuqi.bi.database.sql.model.tables.JoinedTable;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import com.jiuqi.bi.util.NameProvider;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public final class RankConvertor {
    private ISQLTable table;
    private Map<ISQLField, ISQLField> fieldMaps;
    private NameProvider nameProvider;
    private final IDatabase mysql;
    private static final String MEASURE_PREFIX = "VAL";
    private static final String INNER_TABLE_PREFIX = "X";
    private static final String RANK_PARAM = "RANK";
    private static final String NUM_PARAM = "NUM";
    private static final String FIELD_PREFIX = "F";

    public RankConvertor(ISQLTable table) {
        this(table, new NameProvider());
    }

    public RankConvertor(ISQLTable table, NameProvider nameProvider) {
        this.table = table;
        this.fieldMaps = new HashMap<ISQLField, ISQLField>();
        this.nameProvider = nameProvider;
        this.mysql = DatabaseManager.getInstance().findDatabaseByName("MYSQL");
    }

    public JoinedTable convert() throws SQLModelException {
        RankField rankField = this.fetchRankField();
        this.resetOrderBys(rankField);
        Map<ISQLField, ISQLField> measureMaps = this.createMeasures(rankField);
        JoinedTable rankTable = this.wrapperTable(measureMaps);
        this.createRankFields(rankTable, rankField, measureMaps);
        return rankTable;
    }

    private RankField fetchRankField() throws SQLModelException {
        RankField rankField = null;
        Iterator<ISQLField> i = this.table.fields().iterator();
        while (i.hasNext()) {
            ISQLField field = i.next();
            if (field instanceof RankField) {
                if (rankField == null) {
                    rankField = (RankField)field;
                    i.remove();
                } else {
                    throw new SQLModelException("MySQL\u67e5\u8be2\u4e2d\u65e0\u6cd5\u5728\u5355\u4e2a\u67e5\u8be2\u4e2d\u8fd4\u56de\u591a\u4e2a\u6392\u540d\u5b57\u6bb5\u3002");
                }
            }
            this.nameProvider.useName(field.fieldName());
        }
        if (rankField == null) {
            throw new SQLModelException("\u5f53\u524d\u8868\u4e2d\u6ca1\u6709\u4efb\u4f55\u6392\u540d\u5b57\u6bb5\u3002");
        }
        return rankField;
    }

    private void resetOrderBys(RankField rankField) throws SQLModelException {
        if (!this.table.sortFields().isEmpty()) {
            throw new SQLModelException("\u8f6c\u5316\u7684\u67e5\u8be2\u8868\u4e0d\u80fd\u4f7f\u7528\u6392\u5e8f\u3002");
        }
        for (ISQLField field : rankField.partitionFields()) {
            SortField sortField = new SortField(this.table, field);
            this.table.sortFields().add(sortField);
        }
        this.table.sortFields().addAll(rankField.orderFields());
    }

    private Map<ISQLField, ISQLField> createMeasures(RankField rankField) {
        HashMap<ISQLField, ISQLField> maps = new HashMap<ISQLField, ISQLField>();
        for (SortField sortField : rankField.orderFields()) {
            ISQLField field = sortField.field();
            ISQLField newField = field.owner() == this.table ? field : new RefField(this.table, field);
            newField.setAlias(this.nameProvider.nameOf(MEASURE_PREFIX));
            this.table.fields().add(newField);
            maps.put(field, newField);
        }
        return maps;
    }

    private JoinedTable wrapperTable(Map<ISQLField, ISQLField> measureMaps) {
        JoinedTable rankTable = new JoinedTable(this.table.alias());
        this.table.setAlias(this.nameProvider.nameOf(INNER_TABLE_PREFIX));
        HashSet<ISQLField> measures = new HashSet<ISQLField>(measureMaps.values());
        rankTable.setMainTable(this.table);
        for (ISQLField field : this.table.fields()) {
            if (measures.contains(field)) continue;
            RefField newField = new RefField(rankTable, field);
            rankTable.fields().add(newField);
            this.nameProvider.useName(newField.fieldName());
            this.fieldMaps.put(field, newField);
        }
        return rankTable;
    }

    private void createRankFields(JoinedTable rankTable, RankField rankField, Map<ISQLField, ISQLField> measureMaps) throws SQLModelException {
        MySQLParams params = this.initParams(rankTable, rankField, measureMaps);
        this.createNumField(rankTable, rankField, params);
        this.createRankField(rankTable, rankField, measureMaps, params);
        this.createPartitionFields(rankTable, rankField, params);
        this.createValueFields(rankTable, rankField, measureMaps, params);
    }

    private MySQLParams initParams(JoinedTable rankTable, RankField rankField, Map<ISQLField, ISQLField> measureMaps) throws SQLModelException {
        MySQLParams params = new MySQLParams();
        ParamTable paramTable = new ParamTable(this.nameProvider.nameOf(INNER_TABLE_PREFIX));
        params.rank = this.nameProvider.uniqueOf(RANK_PARAM);
        paramTable.params().put(params.rank, 0);
        if (!rankField.isDense()) {
            params.num = this.nameProvider.uniqueOf(NUM_PARAM);
            paramTable.params().put(params.num, 0);
        }
        for (ISQLField field : rankField.partitionFields()) {
            ISQLField groupField = this.findGroupField(field);
            String fieldName = this.nameProvider.uniqueOf(groupField.fieldName());
            params.dimNames.put(groupField.fieldName(), fieldName);
            paramTable.params().put(fieldName, null);
        }
        for (SortField sortField : rankField.orderFields()) {
            ISQLField field = measureMaps.get(sortField.field());
            paramTable.params().put(field.fieldName(), null);
        }
        rankTable.subTables().add(new SubTable(paramTable, JoinMode.INNER));
        return params;
    }

    private ISQLField findGroupField(ISQLField field) throws SQLModelException {
        for (ISQLField curField : this.table.fields()) {
            if (curField == field) {
                return curField;
            }
            if (!(curField instanceof RefField) || ((RefField)curField).field() != field) continue;
            return curField;
        }
        throw new SQLModelException("\u65e0\u6cd5\u5b9a\u4f4d\u5206\u7ec4\u5b57\u6bb5\uff1a" + field.toString());
    }

    private void createNumField(JoinedTable rankTable, RankField rankField, MySQLParams params) throws SQLModelException {
        if (rankField.isDense()) {
            return;
        }
        StringBuilder buffer = new StringBuilder();
        if (rankField.partitionFields().isEmpty()) {
            this.appendNumExpression(buffer, params);
        } else {
            buffer.append("CASE WHEN ");
            this.appendPartitionCondition(buffer, rankField, params);
            buffer.append(" THEN ");
            this.appendNumExpression(buffer, params);
            buffer.append(" ELSE @").append(params.num).append(":=1 END");
        }
        EvalField numField = new EvalField(rankTable, buffer.toString(), this.nameProvider.nameOf(FIELD_PREFIX));
        rankTable.fields().add(numField);
    }

    private void appendNumExpression(StringBuilder buffer, MySQLParams params) {
        buffer.append('@').append(params.num).append(":=@").append(params.num).append("+1");
    }

    private void createRankField(JoinedTable rankTable, RankField rankField, Map<ISQLField, ISQLField> measureMaps, MySQLParams params) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        buffer.append("CONVERT(");
        if (rankField.partitionFields().isEmpty()) {
            this.appendRankExpression(buffer, rankField, measureMaps, params);
        } else {
            buffer.append("CASE WHEN ");
            this.appendPartitionCondition(buffer, rankField, params);
            buffer.append(" THEN ");
            this.appendRankExpression(buffer, rankField, measureMaps, params);
            buffer.append(" ELSE @").append(params.rank).append(":=1 END");
        }
        buffer.append(",SIGNED)");
        EvalField field = new EvalField(rankTable, buffer.toString(), rankField.alias());
        rankTable.fields().add(field);
        this.fieldMaps.put(rankField, field);
    }

    private void appendPartitionCondition(StringBuilder buffer, RankField rankField, MySQLParams params) throws SQLModelException {
        boolean started = false;
        for (ISQLField field : rankField.partitionFields()) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            ISQLField groupField = this.findGroupField(field);
            String paramName = params.dimNames.get(groupField.fieldName());
            RefField refField = new RefField(null, groupField);
            buffer.append('@').append(paramName).append('=');
            refField.toSQL(buffer, this.mysql, 1);
        }
    }

    private void appendRankExpression(StringBuilder buffer, RankField rankField, Map<ISQLField, ISQLField> measureMaps, MySQLParams params) throws SQLModelException {
        buffer.append('@').append(params.rank).append(":=IF(");
        this.appendValueCondition(buffer, rankField, measureMaps);
        buffer.append(",IF(@").append(params.rank).append("=0,1,@").append(params.rank).append("),");
        if (rankField.isDense()) {
            buffer.append('@').append(params.rank).append("+1");
        } else {
            buffer.append('@').append(params.num);
        }
        buffer.append(')');
    }

    private void appendValueCondition(StringBuilder buffer, RankField rankField, Map<ISQLField, ISQLField> measureMaps) throws SQLModelException {
        if (rankField.orderFields().size() == 1) {
            this.appendValueCondtion(buffer, rankField.orderFields().get(0), measureMaps);
        } else {
            boolean started = false;
            for (SortField sortField : rankField.orderFields()) {
                if (started) {
                    buffer.append(" AND ");
                } else {
                    started = true;
                }
                buffer.append('(');
                this.appendValueCondtion(buffer, sortField, measureMaps);
                buffer.append(')');
            }
        }
    }

    private void appendValueCondtion(StringBuilder buffer, SortField sortField, Map<ISQLField, ISQLField> measureMaps) throws SQLModelException {
        ISQLField field = measureMaps.get(sortField.field());
        RefField refField = new RefField(null, field);
        String paramName = field.fieldName();
        buffer.append('@').append(paramName).append('=');
        refField.toSQL(buffer, this.mysql, 1);
        buffer.append(" OR (");
        buffer.append('@').append(paramName).append(" IS NULL AND ");
        refField.toSQL(buffer, this.mysql, 1);
        buffer.append(" IS NULL)");
    }

    private void createPartitionFields(JoinedTable rankTable, RankField rankField, MySQLParams params) throws SQLModelException {
        for (ISQLField field : rankField.partitionFields()) {
            ISQLField groupField = this.findGroupField(field);
            String paramName = params.dimNames.get(groupField.fieldName());
            RefField refField = new RefField(rankTable, groupField);
            StringBuilder buffer = new StringBuilder();
            buffer.append('@').append(paramName).append(":=");
            refField.toSQL(buffer, this.mysql, 1);
            EvalField evalField = new EvalField(rankTable, buffer.toString(), this.nameProvider.nameOf(FIELD_PREFIX));
            rankTable.fields().add(evalField);
        }
    }

    private void createValueFields(JoinedTable rankTable, RankField rankField, Map<ISQLField, ISQLField> measureMaps, MySQLParams params) throws SQLModelException {
        for (SortField sortField : rankField.orderFields()) {
            ISQLField field = measureMaps.get(sortField.field());
            RefField refField = new RefField(rankTable, field);
            StringBuilder buffer = new StringBuilder();
            buffer.append('@').append(field.fieldName()).append(":=");
            refField.toSQL(buffer, this.mysql, 1);
            EvalField evalField = new EvalField(rankTable, buffer.toString(), this.nameProvider.nameOf(FIELD_PREFIX));
            rankTable.fields().add(evalField);
        }
    }

    public Map<ISQLField, ISQLField> getFieldMaps() {
        return this.fieldMaps;
    }

    public static JoinedTable convert(ISQLTable table) throws SQLModelException {
        return RankConvertor.convert(table, null);
    }

    public static JoinedTable convert(ISQLTable table, Map<ISQLField, ISQLField> fieldMaps) throws SQLModelException {
        RankConvertor convertor = new RankConvertor(table);
        JoinedTable mysqlTable = convertor.convert();
        if (fieldMaps != null) {
            fieldMaps.putAll(convertor.getFieldMaps());
        }
        return mysqlTable;
    }

    private static final class MySQLParams {
        public String rank;
        public String num;
        public Map<String, String> dimNames = new HashMap<String, String>();

        private MySQLParams() {
        }
    }
}

