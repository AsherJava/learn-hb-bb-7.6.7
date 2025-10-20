/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.gcreport.billextract.impl.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import com.jiuqi.gcreport.billextract.impl.dao.BillExtractSettingDao;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class BillExtractSettingDaoImpl
implements BillExtractSettingDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> selectBills(String masterTableName, BillExtractLisDTO queryCondi) {
        String SQL = "SELECT * FROM %1$s WHERE 1=1 AND UNITCODE = ? AND BILLDATE >= ?  AND BILLDATE <= ? %2$s";
        String billCodeSql = "";
        if (!CollectionUtils.isEmpty((Collection)queryCondi.getBillCodeList())) {
            billCodeSql = "AND " + SqlBuildUtil.getStrInCondi((String)"BILLCDE", (List)queryCondi.getBillCodeList());
        }
        String querySql = String.format("SELECT * FROM %1$s WHERE 1=1 AND UNITCODE = ? AND BILLDATE >= ?  AND BILLDATE <= ? %2$s", masterTableName, billCodeSql);
        return (List)this.jdbcTemplate.query(querySql, this.getBillDataResultSetExtractor(), new Object[]{queryCondi.getUnitCode(), new Date(DateUtils.parse((String)queryCondi.getStartDate(), (String)DateUtils.DEFAULT_DATE_FORMAT).getTime()), new Date(DateUtils.parse((String)queryCondi.getEndDate(), (String)DateUtils.DEFAULT_DATE_FORMAT).getTime())});
    }

    private ResultSetExtractor<List<Map<String, Object>>> getBillDataResultSetExtractor() {
        return new ResultSetExtractor<List<Map<String, Object>>>(){

            public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList result = CollectionUtils.newArrayList();
                HashMap<String, Object> rowData = null;
                ArrayList<String> columns = new ArrayList<String>(rs.getMetaData().getColumnCount());
                for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
                    columns.add(rs.getMetaData().getColumnLabel(colIdx).toUpperCase());
                }
                while (rs.next()) {
                    rowData = new HashMap<String, Object>(columns.size());
                    String column = "";
                    for (int idx = 1; idx <= columns.size(); ++idx) {
                        column = (String)columns.get(idx - 1);
                        if (rs.getObject(idx) == null) continue;
                        if (rs.getObject(idx) instanceof BigDecimal) {
                            rowData.put(column, new BigDecimal(rs.getObject(idx).toString()));
                            continue;
                        }
                        if (rs.getObject(idx) instanceof Integer) {
                            rowData.put(column, Integer.valueOf(rs.getObject(idx).toString()));
                            continue;
                        }
                        rowData.put(column, rs.getObject(idx).toString());
                    }
                    result.add(rowData);
                }
                return result;
            }
        };
    }

    @Override
    public Map<String, Object> selectBill(String masterTableName, String billCode) {
        String SQL = "SELECT * FROM %1$s WHERE 1 = 1 AND BILLCODE = ?";
        List billDataList = (List)this.jdbcTemplate.query(String.format("SELECT * FROM %1$s WHERE 1 = 1 AND BILLCODE = ?", masterTableName, billCode), this.getBillDataResultSetExtractor(), new Object[]{billCode});
        return CollectionUtils.isEmpty((Collection)billDataList) ? null : (Map)billDataList.get(0);
    }
}

