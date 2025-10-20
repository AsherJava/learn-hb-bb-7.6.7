/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.integration.execute.impl.basedatasync.dao.BaseDataSyncDao;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDataSyncDaoImpl
extends BaseDataCenterDaoImpl
implements BaseDataSyncDao {
    @Override
    public int insertOrgData(String tableName, List<OrgDO> addOrgList, List<String> columnList) {
        if (CollectionUtils.isEmpty(addOrgList)) {
            return 0;
        }
        if (!CollectionUtils.isEmpty(columnList)) {
            StringJoiner joiner = new StringJoiner(",");
            ArrayList paramList = CollectionUtils.newArrayList();
            for (String column : columnList) {
                joiner.add("?");
            }
            for (OrgDO addOrg : addOrgList) {
                paramList.add(columnList.stream().map(e -> {
                    Object o = addOrg.get((Object)e.toLowerCase());
                    if (o instanceof List) {
                        return String.join((CharSequence)";", (List)o);
                    }
                    if (o instanceof UUID) {
                        return o.toString();
                    }
                    return o;
                }).toArray());
            }
            String excuteSql = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)", tableName, String.join((CharSequence)",", columnList), joiner);
            return OuterDataSourceUtils.getJdbcTemplate().batchUpdate(excuteSql, (List)paramList).length;
        }
        return 0;
    }

    @Override
    public int insertBaseData(String tableName, List<BaseDataDO> addBaseDataList, List<String> columnList) {
        if (CollectionUtils.isEmpty(addBaseDataList)) {
            return 0;
        }
        if (!CollectionUtils.isEmpty(columnList)) {
            StringJoiner joiner = new StringJoiner(",");
            ArrayList paramList = CollectionUtils.newArrayList();
            for (String column : columnList) {
                joiner.add("?");
            }
            for (BaseDataDO addOrg : addBaseDataList) {
                paramList.add(columnList.stream().map(e -> {
                    Object o = addOrg.get((Object)e.toLowerCase());
                    if (o instanceof List) {
                        return String.join((CharSequence)";", (List)o);
                    }
                    if (o instanceof UUID) {
                        return o.toString();
                    }
                    return o;
                }).toArray());
            }
            String excuteSql = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)", tableName, String.join((CharSequence)",", columnList), joiner);
            return OuterDataSourceUtils.getJdbcTemplate().batchUpdate(excuteSql, (List)paramList).length;
        }
        return 0;
    }

    @Override
    public int deleteBaseData(String tableName, List<String> codeList) {
        if (CollectionUtils.isEmpty(codeList)) {
            return 0;
        }
        String excuteSql = String.format("DELETE FROM %1$s WHERE %2$s", tableName, SqlBuildUtil.getStrInCondi((String)"CODE", codeList));
        return OuterDataSourceUtils.getJdbcTemplate().update(excuteSql);
    }

    @Override
    public void truncateTable(String baseDataCode) {
        OuterDataSourceUtils.getJdbcTemplate().update(String.format("TRUNCATE TABLE %1$s", baseDataCode));
    }

    @Override
    public Map<String, String> prepareTempOrgData(String tableName, List<OrgDO> addOrgList, List<String> columnList) {
        if (CollectionUtils.isEmpty(addOrgList)) {
            return CollectionUtils.newHashMap();
        }
        StringJoiner joiner = new StringJoiner(",");
        StringJoiner columnJoiner = new StringJoiner(",");
        ArrayList paramList = CollectionUtils.newArrayList();
        int index = 1;
        HashMap columnMap = CollectionUtils.newHashMap();
        for (int i = 0; i < columnList.size(); ++i) {
            joiner.add("?");
            if (Objects.equals("CODE", columnList.get(i)) || Objects.equals("NAME", columnList.get(i)) || Objects.equals("ID", columnList.get(i))) {
                columnJoiner.add(columnList.get(i));
                columnMap.put(columnList.get(i), columnList.get(i));
                continue;
            }
            String columnName = "EXT_STR" + index++;
            columnJoiner.add(columnName);
            columnMap.put(columnList.get(i), columnName);
        }
        joiner.add("?");
        columnJoiner.add("BASEDATACODE");
        for (OrgDO addOrg : addOrgList) {
            ArrayList param = CollectionUtils.newArrayList();
            for (int i = 0; i < columnList.size(); ++i) {
                Object o = addOrg.get((Object)columnList.get(i).toLowerCase());
                if (o instanceof List) {
                    param.add(String.join((CharSequence)";", (List)o));
                    continue;
                }
                if (o instanceof UUID) {
                    param.add(o.toString());
                    continue;
                }
                param.add(o);
            }
            param.add(tableName);
            paramList.add(param.toArray());
        }
        String excuteSql = String.format("INSERT INTO DC_TEMP_BASEDATASYNC (%1$s) VALUES (%2$s)", columnJoiner, joiner);
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(excuteSql, (List)paramList);
        return columnMap;
    }

    @Override
    public Map<String, String> prepareTempTableData(String tableName, List<BaseDataDO> addOrgList, List<String> columnList) {
        if (CollectionUtils.isEmpty(addOrgList)) {
            return CollectionUtils.newHashMap();
        }
        StringJoiner joiner = new StringJoiner(",");
        StringJoiner columnJoiner = new StringJoiner(",");
        ArrayList paramList = CollectionUtils.newArrayList();
        int index = 1;
        HashMap columnMap = CollectionUtils.newHashMap();
        for (int i = 0; i < columnList.size(); ++i) {
            joiner.add("?");
            if (Objects.equals("CODE", columnList.get(i)) || Objects.equals("NAME", columnList.get(i)) || Objects.equals("ID", columnList.get(i))) {
                columnJoiner.add(columnList.get(i));
                columnMap.put(columnList.get(i), columnList.get(i));
                continue;
            }
            String columnName = "EXT_STR" + index++;
            columnJoiner.add(columnName);
            columnMap.put(columnList.get(i), columnName);
        }
        joiner.add("?");
        columnJoiner.add("BASEDATACODE");
        for (BaseDataDO addOrg : addOrgList) {
            ArrayList param = CollectionUtils.newArrayList();
            for (int i = 0; i < columnList.size(); ++i) {
                Object o = addOrg.get((Object)columnList.get(i).toLowerCase());
                if (o instanceof List) {
                    param.add(String.join((CharSequence)";", (List)o));
                    continue;
                }
                if (o instanceof UUID) {
                    param.add(o.toString());
                    continue;
                }
                param.add(o);
            }
            param.add(tableName);
            paramList.add(param.toArray());
        }
        String excuteSql = String.format("INSERT INTO DC_TEMP_BASEDATASYNC (%1$s) VALUES (%2$s)", columnJoiner, joiner);
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(excuteSql, (List)paramList);
        return columnMap;
    }

    @Override
    public int insertTableData(String tableName, Map<String, String> tableColumnMap) {
        StringJoiner selectColumn = new StringJoiner(",");
        StringJoiner insertColumn = new StringJoiner(",");
        for (Map.Entry<String, String> entry : tableColumnMap.entrySet()) {
            insertColumn.add(entry.getKey());
            selectColumn.add(String.format("%1$s AS %2$s", entry.getValue(), entry.getKey()));
        }
        String excuteSql = String.format("INSERT INTO %1$s (%2$s) SELECT %3$s FROM DC_TEMP_BASEDATASYNC TEMP WHERE NOT EXISTS (SELECT 1 FROM %1$s T WHERE TEMP.CODE = T.CODE)", tableName, insertColumn, selectColumn);
        return OuterDataSourceUtils.getJdbcTemplate().update(excuteSql);
    }

    @Override
    public int updateTableData(String tableName, Map<String, String> columnMap) {
        StringJoiner selectColumn = new StringJoiner(",");
        StringJoiner insertColumn = new StringJoiner(",");
        for (Map.Entry<String, String> entry : columnMap.entrySet()) {
            insertColumn.add(entry.getKey());
            selectColumn.add(String.format("%1$s AS %2$s", entry.getValue(), entry.getKey()));
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %1$s T  \n");
        sql.append("   SET (%2$s) =  \n");
        sql.append("       (SELECT %3$s  \n");
        sql.append("          FROM DC_TEMP_BASEDATASYNC TEMP  \n");
        sql.append("         WHERE TEMP.BASEDATACODE = ?  \n");
        sql.append("           AND TEMP.CODE = T.CODE)  \n");
        sql.append(" WHERE EXISTS (SELECT 1  \n");
        sql.append("          FROM DC_TEMP_BASEDATASYNC TEMP  \n");
        sql.append("         WHERE TEMP.BASEDATACODE = ?  \n");
        sql.append("           AND TEMP.CODE = T.CODE)  \n");
        String excuteSql = String.format(sql.toString(), tableName, insertColumn, selectColumn);
        return OuterDataSourceUtils.getJdbcTemplate().update(excuteSql, new Object[]{tableName, tableName});
    }

    @Override
    public void clearTempData(String tableName) {
        OuterDataSourceUtils.getJdbcTemplate().update("DELETE FROM DC_TEMP_BASEDATASYNC WHERE BASEDATACODE = ?", new Object[]{tableName});
    }
}

