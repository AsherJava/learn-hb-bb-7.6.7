/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.np.definition.internal.dao;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.internal.dao.TableDefineItem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeRevertToDesignDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final int MAXINCOUNT = 900;
    private static final String DES_TABLE_DEFINE = "des_sys_tabledefine";
    private static final String TABLE_KEY = "td_key";
    private static final String TABLE_INDEX = "td_index_keys";
    private static final String TABLE_IS_AUTO = "td_is_auto";
    private static final String DES_VIEW_DEFINE = "des_sys_entityviewdefine";
    private static final String VIEW_KEY = "ev_key";
    private static final String DES_FIELD_DEFINE = "des_sys_fielddefine";
    private static final String FIELD_TABLE_KEY = "fd_own_table";
    private static final String DES_INDEX_DEFINE = "des_sys_tableindex";
    private static final String INDEX_KEY = "ti_key";
    private static final String SPLIT_CHAR = ";";

    public void deleteDesTableDefines(Set<String> desTableKeys) {
        this.deleteObjects(DES_TABLE_DEFINE, TABLE_KEY, desTableKeys);
    }

    private void deleteObjects(String tableName, String fieldName, Set<String> fieldValues) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(fieldName);
        deleteSql.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(fieldValues);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private List<Object[]> getBatchArgs(Set<String> formKeys) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String formKey : formKeys) {
            String[] params = new String[]{formKey};
            batchArgs.add(params);
        }
        return batchArgs;
    }

    public void deleteDesViewDefines(Set<String> desViewKeys) {
        this.deleteObjects(DES_VIEW_DEFINE, VIEW_KEY, desViewKeys);
    }

    public void deleteTableIndexs(Set<String> tableKeys) {
        Set<String> indexs = this.getIndexs(tableKeys, DES_TABLE_DEFINE);
        this.deleteIndexs(indexs);
    }

    private Set<String> getIndexs(Set<String> tableKeys, String tableName) {
        Set<TableDefineItem> tableDefineItems = this.getTableDefineItems(tableKeys, tableName);
        HashSet<String> indexKeys = new HashSet<String>();
        for (TableDefineItem tableDefineItem : tableDefineItems) {
            String[] indexKeyArray;
            if (tableDefineItem.getTableIndex() == null) continue;
            for (String tka : indexKeyArray = tableDefineItem.getTableIndex().split(SPLIT_CHAR)) {
                indexKeys.add(tka);
            }
        }
        return indexKeys;
    }

    public Set<TableDefineItem> getTableDefineItems(Set<String> tableKeys) {
        Set<TableDefineItem> tableDefineItems = this.getTableDefineItems(tableKeys, DES_TABLE_DEFINE);
        return tableDefineItems;
    }

    private Set<TableDefineItem> getTableDefineItems(Set<String> tableKeys, String tableName) {
        if (tableKeys.size() > 900) {
            return this.batchGetIndexKeys(tableKeys, tableName);
        }
        StringBuilder tableKeyBuilder = new StringBuilder();
        Object[] argValue = new Object[tableKeys.size()];
        int index = 0;
        for (String tk : tableKeys) {
            tableKeyBuilder.append("?,");
            argValue[index] = tk;
            ++index;
        }
        tableKeyBuilder.setLength(tableKeyBuilder.length() - 1);
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" select ");
        selectSql.append(TABLE_INDEX);
        selectSql.append(",");
        selectSql.append(TABLE_IS_AUTO);
        selectSql.append(" from ");
        selectSql.append(tableName);
        selectSql.append(" where ");
        selectSql.append(TABLE_KEY);
        selectSql.append(" in (");
        selectSql.append((CharSequence)tableKeyBuilder);
        selectSql.append(")");
        List rows = this.jdbcTemplate.queryForList(selectSql.toString(), argValue);
        Iterator it = rows.iterator();
        HashSet<TableDefineItem> tableDefineItems = new HashSet<TableDefineItem>();
        while (it.hasNext()) {
            Map userMap = (Map)it.next();
            TableDefineItem tableDefineItem = new TableDefineItem();
            String tk = (String)userMap.get(TABLE_INDEX);
            String isAuto = userMap.get(TABLE_IS_AUTO).toString();
            if (!StringUtils.isEmpty(tk)) {
                tableDefineItem.setTableIndex(tk);
            }
            tableDefineItem.setAuto(isAuto.equals("1"));
            tableDefineItems.add(tableDefineItem);
        }
        return tableDefineItems;
    }

    private Set<TableDefineItem> batchGetIndexKeys(Set<String> tableKeys, String tableName) {
        int loopCount = tableKeys.size() % 900 == 0 ? tableKeys.size() / 900 : tableKeys.size() / 900 + 1;
        HashSet<TableDefineItem> resultSet = new HashSet<TableDefineItem>();
        ArrayList<String> tableList = new ArrayList<String>(tableKeys.size());
        tableList.addAll(tableKeys);
        for (int index = 0; index < loopCount; ++index) {
            Set<String> childKeys = this.getLoopKeys(tableList, index * 900, 900);
            resultSet.addAll(this.getTableDefineItems(childKeys, tableName));
        }
        return resultSet;
    }

    private void deleteIndexs(Set<String> indexKeys) {
        if (indexKeys.size() > 900) {
            this.batchDeleteIndexs(indexKeys);
        }
        StringBuilder indexKeyBuilder = new StringBuilder();
        Object[] argValue = new Object[indexKeys.size()];
        int index = 0;
        for (String tk : indexKeys) {
            indexKeyBuilder.append("?,");
            argValue[index] = tk;
            ++index;
        }
        if (indexKeyBuilder.length() < 1) {
            return;
        }
        indexKeyBuilder.setLength(indexKeyBuilder.length() - 1);
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(DES_INDEX_DEFINE);
        deleteSql.append(" where ");
        deleteSql.append(INDEX_KEY);
        deleteSql.append(" in (");
        deleteSql.append((CharSequence)indexKeyBuilder);
        deleteSql.append(")");
        this.jdbcTemplate.update(deleteSql.toString(), argValue);
    }

    private void batchDeleteIndexs(Set<String> indexKeys) {
        int loopCount = indexKeys.size() % 900 == 0 ? indexKeys.size() / 900 : indexKeys.size() / 900 + 1;
        ArrayList<String> indexList = new ArrayList<String>(indexKeys.size());
        indexList.addAll(indexKeys);
        for (int index = 0; index < loopCount; ++index) {
            Set<String> childKeys = this.getLoopKeys(indexList, index * 900, 900);
            this.deleteIndexs(childKeys);
        }
    }

    public void deleteFieldsByTable(Set<String> tableKeys) {
        if (tableKeys.size() > 900) {
            this.batchDeleteFields(tableKeys);
        }
        StringBuilder tableKeyBuilder = new StringBuilder();
        Object[] argValue = new Object[tableKeys.size()];
        int index = 0;
        for (String tk : tableKeys) {
            tableKeyBuilder.append("?,");
            argValue[index] = tk;
            ++index;
        }
        tableKeyBuilder.setLength(tableKeyBuilder.length() - 1);
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(DES_FIELD_DEFINE);
        deleteSql.append(" where ");
        deleteSql.append(FIELD_TABLE_KEY);
        deleteSql.append(" in (");
        deleteSql.append((CharSequence)tableKeyBuilder);
        deleteSql.append(")");
        this.jdbcTemplate.update(deleteSql.toString(), argValue);
    }

    private void batchDeleteFields(Set<String> tableKeys) {
        int loopCount = tableKeys.size() % 900 == 0 ? tableKeys.size() / 900 : tableKeys.size() / 900 + 1;
        ArrayList<String> tableList = new ArrayList<String>(tableKeys.size());
        tableList.addAll(tableKeys);
        for (int index = 0; index < loopCount; ++index) {
            Set<String> childKeys = this.getLoopKeys(tableList, index * 900, 900);
            this.deleteFieldsByTable(childKeys);
        }
    }

    private Set<String> getLoopKeys(List<String> tableList, int startIndex, int count) {
        HashSet<String> resultSet = new HashSet<String>();
        int tableSize = tableList.size();
        for (int index = 0; index < count && startIndex < tableSize; ++index, ++startIndex) {
            resultSet.add(tableList.get(startIndex));
        }
        return resultSet;
    }
}

