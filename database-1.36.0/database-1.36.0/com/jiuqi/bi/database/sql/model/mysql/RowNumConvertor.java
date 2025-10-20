/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.mysql;

import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.fields.RefField;
import com.jiuqi.bi.database.sql.model.fields.RowNumField;
import com.jiuqi.bi.database.sql.model.mysql.ParamTable;
import com.jiuqi.bi.database.sql.model.tables.JoinedTable;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RowNumConvertor {
    private ISQLTable table;
    private Map<ISQLField, ISQLField> fieldMaps = new HashMap<ISQLField, ISQLField>();
    private static final String PARAM_NAME = "rownum";
    private static final String EXPR_ROWNUM = "@rownum := @rownum + 1";
    private static final String EXPR_RENUM = "@rownum";

    public RowNumConvertor(ISQLTable table) {
        this.table = table;
    }

    public ISQLTable convert() throws SQLModelException {
        int iRowNum = this.locateRowNum();
        if (iRowNum == -1) {
            this.mapSelf(this.table);
            return this.table;
        }
        if (this.table instanceof JoinedTable) {
            return this.convertJoined((JoinedTable)this.table);
        }
        return this.convertDefault(this.table);
    }

    private void mapSelf(ISQLTable table) {
        for (ISQLField field : table.fields()) {
            this.fieldMaps.put(field, field);
        }
    }

    private ISQLTable convertDefault(ISQLTable table) {
        if (StringUtils.isEmpty((String)table.alias())) {
            table.setAlias("M");
        }
        JoinedTable joinedTable = new JoinedTable(table.alias());
        boolean found = false;
        joinedTable.setMainTable(table);
        Iterator<ISQLField> i = table.fields().iterator();
        while (i.hasNext()) {
            Cloneable newField;
            ISQLField rawField = i.next();
            if (rawField instanceof RowNumField) {
                newField = new EvalField(joinedTable, found ? EXPR_RENUM : EXPR_ROWNUM, rawField.alias());
                found = true;
                i.remove();
            } else {
                newField = new RefField(joinedTable, rawField);
            }
            joinedTable.fields().add((ISQLField)((Object)newField));
            this.fieldMaps.put(rawField, (ISQLField)((Object)newField));
        }
        this.joinParam(joinedTable);
        return joinedTable;
    }

    private ISQLTable convertJoined(JoinedTable joinedTable) {
        this.mapSelf(joinedTable);
        boolean found = false;
        for (int i = 0; i < joinedTable.fields().size(); ++i) {
            if (!(joinedTable.fields().get(i) instanceof RowNumField)) continue;
            RowNumField rawField = (RowNumField)joinedTable.fields().get(i);
            EvalField rowNumField = new EvalField(joinedTable, found ? EXPR_RENUM : EXPR_ROWNUM, rawField.alias());
            joinedTable.fields().set(i, rowNumField);
            this.fieldMaps.put(rawField, rowNumField);
            found = true;
        }
        this.joinParam(joinedTable);
        return joinedTable;
    }

    private void joinParam(JoinedTable joinedTable) {
        ParamTable paramTable = new ParamTable("RN");
        paramTable.params().put(PARAM_NAME, 0);
        SubTable subTable = new SubTable(paramTable);
        joinedTable.subTables().add(subTable);
    }

    private int locateRowNum() {
        for (int i = 0; i < this.table.fields().size(); ++i) {
            if (!(this.table.fields().get(i) instanceof RowNumField)) continue;
            return i;
        }
        return -1;
    }

    public Map<ISQLField, ISQLField> getFieldMaps() {
        return this.fieldMaps;
    }
}

