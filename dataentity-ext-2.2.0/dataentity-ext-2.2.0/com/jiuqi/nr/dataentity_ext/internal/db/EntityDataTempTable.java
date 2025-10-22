/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nvwa.sf.models.IModuleTempTable
 */
package com.jiuqi.nr.dataentity_ext.internal.db;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nvwa.sf.models.IModuleTempTable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityDataTempTable
implements IModuleTempTable {
    private static final List<LogicField> fields = new ArrayList<LogicField>(10);
    private static final List<String> primaryKeys = new ArrayList<String>(1);
    private static final List<List<String>> indexes = new ArrayList<List<String>>(1);
    private final String tableName;
    public static final String FIELD_KEY = "E_KEY";
    public static final String FIELD_CODE = "E_CODE";
    public static final String FIELD_TITLE = "E_TITLE";
    public static final String FIELD_PARENT = "E_PARENT";
    public static final String FIELD_PATH = "E_PATH";
    public static final String FIELD_TYPE = "E_TYPE";
    public static final String FIELD_ORDER = "E_ORDER";
    public static final String FIELD_LEAF = "E_LEAF";
    public static final String FIELD_CHILD_COUNT = "E_CHILDCOUNT";
    public static final String FIELD_ALL_CHILD_COUNT = "E_ALLCHILDCOUNT";

    public EntityDataTempTable(boolean createTableName) {
        this.tableName = createTableName ? this.createTableName() : null;
    }

    public String getModuleName() {
        return "NR\u6570\u636e\u670d\u52a1\u81ea\u5b9a\u4e49\u5b9e\u4f53\u6570\u636e\u4e34\u65f6\u8868";
    }

    public String getTempTableNameRule() {
        return "T_" + this.getType() + "_%";
    }

    public String getDesc() {
        return this.getModuleName();
    }

    public String getType() {
        return "NRDE";
    }

    public List<LogicField> getLogicFields() {
        return fields;
    }

    public List<String> getPrimaryKeyFields() {
        return primaryKeys;
    }

    public List<List<String>> getIndexes() {
        return indexes;
    }

    private String createTableName() {
        StringBuilder tableName = new StringBuilder("T_");
        String type = this.getType();
        if (type.length() <= 10) {
            tableName.append(type.toUpperCase()).append("_");
        }
        tableName.append(OrderGenerator.newOrder());
        SecureRandom rand = new SecureRandom();
        int tableIndex = rand.nextInt(10000);
        tableName.append(tableIndex);
        return tableName.toString();
    }

    public String getTableName() {
        return this.tableName;
    }

    static {
        LogicField keyField = new LogicField();
        keyField.setFieldName(FIELD_KEY);
        keyField.setSize(50);
        keyField.setDataType(6);
        fields.add(keyField);
        LogicField codeField = new LogicField();
        codeField.setFieldName(FIELD_CODE);
        codeField.setSize(50);
        codeField.setDataType(6);
        codeField.setNullable(true);
        fields.add(codeField);
        LogicField titleField = new LogicField();
        titleField.setFieldName(FIELD_TITLE);
        titleField.setSize(50);
        titleField.setDataType(6);
        titleField.setNullable(true);
        fields.add(titleField);
        LogicField parentField = new LogicField();
        parentField.setFieldName(FIELD_PARENT);
        parentField.setSize(50);
        parentField.setDataType(6);
        parentField.setNullable(true);
        fields.add(parentField);
        LogicField pathField = new LogicField();
        pathField.setFieldName(FIELD_PATH);
        pathField.setSize(610);
        pathField.setDataType(6);
        pathField.setNullable(true);
        fields.add(pathField);
        LogicField typeField = new LogicField();
        typeField.setFieldName(FIELD_TYPE);
        typeField.setDataType(5);
        typeField.setSize(1);
        typeField.setNullable(true);
        fields.add(typeField);
        LogicField orderField = new LogicField();
        orderField.setFieldName(FIELD_ORDER);
        orderField.setDataType(3);
        orderField.setPrecision(20);
        orderField.setScale(6);
        orderField.setNullable(true);
        fields.add(orderField);
        LogicField leafField = new LogicField();
        leafField.setFieldName(FIELD_LEAF);
        leafField.setDataType(5);
        leafField.setSize(1);
        leafField.setNullable(true);
        fields.add(leafField);
        LogicField childCountField = new LogicField();
        childCountField.setFieldName(FIELD_CHILD_COUNT);
        childCountField.setDataType(5);
        childCountField.setSize(10);
        childCountField.setNullable(true);
        fields.add(childCountField);
        LogicField allChildCountField = new LogicField();
        allChildCountField.setFieldName(FIELD_ALL_CHILD_COUNT);
        allChildCountField.setDataType(5);
        allChildCountField.setSize(10);
        allChildCountField.setNullable(true);
        fields.add(allChildCountField);
        primaryKeys.add(FIELD_KEY);
        indexes.add(Collections.singletonList(FIELD_PARENT));
    }
}

