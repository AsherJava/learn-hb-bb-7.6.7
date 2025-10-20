/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.dc.integration.execute.impl.dao.impl;

import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.integration.execute.impl.dao.OdsMdOrgDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class OdsMdOrgDaoImpl
extends BaseDataCenterDaoImpl
implements OdsMdOrgDao {
    @Override
    public Map<String, Map<String, Object>> selectOdsMdOrg(String dataSchemeCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, CODE, NAME, PARENTCODE, PARENTS");
        sql.append("  FROM ODS_MD_ORG");
        sql.append(" WHERE DATASCHEMECODE = ?");
        sql.append(" ORDER BY PARENTS");
        LinkedHashMap<String, Map<String, Object>> results = new LinkedHashMap<String, Map<String, Object>>();
        OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (rs, rowNum) -> {
            int resultSize = rs.getMetaData().getColumnCount();
            HashMap<String, Object> row = new HashMap<String, Object>();
            for (int i = 1; i <= resultSize; ++i) {
                row.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
            }
            String code = (String)row.get("CODE");
            results.put(code, row);
            return row;
        }, new Object[]{dataSchemeCode});
        return results;
    }

    @Override
    public void insertOdsMdOrg(List<Map<String, Object>> insertOrgList) {
        UUIDUtils.newUUIDStr();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ODS_MD_ORG \n");
        sql.append("     ( ID, NAME, CODE, DATASCHEMECODE, PARENTCODE, PARENTS) \n");
        sql.append("VALUES (?, ?, ?, ?, ?, ?)");
        ArrayList<Object[]> paramList = new ArrayList<Object[]>();
        for (Map<String, Object> insertOrg : insertOrgList) {
            Object[] params = new Object[6];
            int index = 0;
            params[index++] = UUIDUtils.newUUIDStr();
            params[index++] = insertOrg.get("NAME");
            params[index++] = insertOrg.get("CODE");
            params[index++] = insertOrg.get("DATASCHEMECODE");
            params[index++] = insertOrg.get("PARENTCODE");
            params[index++] = insertOrg.get("PARENTS");
            paramList.add(params);
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), paramList);
    }

    @Override
    public void updateOdsMdOrg(List<Map<String, Object>> upDateOrgList) {
        UUIDUtils.newUUIDStr();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ODS_MD_ORG \n");
        sql.append("   SET NAME = ?, CODE = ?, DATASCHEMECODE = ?,  ");
        sql.append("       PARENTCODE = ?, PARENTS = ?  ");
        sql.append(" WHERE ID = ?  ");
        ArrayList<Object[]> paramList = new ArrayList<Object[]>();
        for (Map<String, Object> upDateOrg : upDateOrgList) {
            Object[] params = new Object[6];
            int index = 0;
            params[index++] = upDateOrg.get("NAME");
            params[index++] = upDateOrg.get("CODE");
            params[index++] = upDateOrg.get("DATASCHEMECODE");
            params[index++] = upDateOrg.get("PARENTCODE");
            params[index++] = upDateOrg.get("PARENTS");
            params[index++] = upDateOrg.get("ID");
            paramList.add(params);
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), paramList);
    }
}

