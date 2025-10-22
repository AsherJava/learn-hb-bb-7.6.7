/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.dao.DataIntegrityViolationException
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import com.jiuqi.nr.io.tz.service.IDataSaveDao;
import com.jiuqi.nr.io.tz.service.impl.AddData2SbTableModel;
import com.jiuqi.nr.io.tz.service.impl.TempData2RtpTable;
import com.jiuqi.nr.io.tz.service.impl.TzData2HisModel;
import com.jiuqi.nr.io.tz.service.impl.UpdateTzData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataSaveDao
implements IDataSaveDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DataSaveDao.class);

    @Override
    public void handleAddStateData(TzParams params) {
        AddData2SbTableModel sbTableSql = new AddData2SbTableModel(this.jdbcTemplate, params);
        String sql = sbTableSql.buildSql();
        try {
            logger.info("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u65b0\u589e\u7684\u6570\u636e\u4ece\u8f85\u52a9\u4e34\u65f6\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u8868,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (DataIntegrityViolationException e) {
            logger.error("\u4e1a\u52a1\u4e3b\u952e\u91cd\u590d", e);
            throw new TzImportException("\u4e1a\u52a1\u4e3b\u952e\u91cd\u590d", e);
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u65b0\u589e\u7684\u6570\u636e\u4ece\u8f85\u52a9\u4e34\u65f6\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u8868", e);
            throw new TzImportException("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u65b0\u589e\u7684\u6570\u636e\u4ece\u8f85\u52a9\u4e34\u65f6\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u8868", e);
        }
    }

    @Override
    public void handleDelStateData2His(TzParams params) {
        logger.info("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u53d8\u66f4\u8868");
        TzData2HisModel tzData2His = new TzData2HisModel(this.jdbcTemplate, params, -1);
        tzData2His.validDataTimeFilter();
        String insertSql = tzData2His.buildInsertSql();
        String sql = tzData2His.buildSql();
        try {
            this.delStateTableData(params);
            logger.info("\u6267\u884cSQL:{}", (Object)insertSql);
            this.jdbcTemplate.update(insertSql);
            logger.info("\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql, new Object[]{params.getDatatime()});
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u53d8\u66f4\u8868\u5931\u8d25", e);
            throw new TzImportException("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u53d8\u66f4\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public void handleDelStateData(TzParams params) {
        String sql = "DELETE FROM " + params.getTmpTable().getTzTableName() + " T0 WHERE T0." + "SBID" + " IN (SELECT T1." + "SBID" + " FROM " + params.getTempTableName() + " T1 WHERE T1." + "OPT" + " =? )";
        try {
            logger.info("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u5220\u9664,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql, new Object[]{(byte)-1});
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u5220\u9664\u5931\u8d25", e);
            throw new TzImportException("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u5220\u9664\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u5220\u9664\u5931\u8d25", e);
        }
    }

    @Override
    public void handleUpdateRecordStateData2His(TzParams params) {
        logger.info("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u4ece\u53f0\u8d26\u4fe1\u606f\u8868\u65b0\u589e\u5230\u53f0\u8d26\u4fe1\u606f\u53d8\u66f4\u8868");
        TzData2HisModel tzData2His = new TzData2HisModel(this.jdbcTemplate, params, 2);
        String insertSql = tzData2His.buildInsertSql();
        String sql = tzData2His.buildSql();
        try {
            this.delStateTableData(params);
            logger.info("\u6267\u884cSQL:{}", (Object)insertSql);
            this.jdbcTemplate.update(insertSql);
            logger.info("\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u5230\u53f0\u8d26\u53d8\u66f4\u8868\u5931\u8d25", e);
            throw new TzImportException("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u5230\u53f0\u8d26\u53d8\u66f4\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public void handleUpdateRecordStateData(TzParams params) {
        logger.info("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u66f4\u65b0\u56de\u53f0\u8d26\u4fe1\u606f\u8868");
        UpdateTzData updateTzData = new UpdateTzData(this.jdbcTemplate, params, 2);
        String insertSql = updateTzData.buildInsertSql();
        String sql = updateTzData.buildSql();
        try {
            this.delStateTableData(params);
            logger.info("\u6267\u884cSQL:{}", (Object)insertSql);
            this.jdbcTemplate.update(insertSql);
            logger.info("\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (Exception e) {
            logger.error("\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u66f4\u65b0\u56de\u53f0\u8d26\u4fe1\u606f\u8868\u5931\u8d25", e);
            throw new TzImportException("\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u66f4\u65b0\u56de\u53f0\u8d26\u4fe1\u606f\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public void handleUpdateNotRecordStateData(TzParams params) {
        logger.info("\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u4e0d\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u66f4\u65b0\u56de\u53f0\u8d26\u4fe1\u606f\u8868");
        UpdateTzData updateTzData = new UpdateTzData(this.jdbcTemplate, params, 3);
        String insertSql = updateTzData.buildInsertSql();
        String sql = updateTzData.buildSql();
        try {
            this.delStateTableData(params);
            logger.info("\u6267\u884cSQL:{}", (Object)insertSql);
            this.jdbcTemplate.update(insertSql);
            logger.info("\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (Exception e) {
            logger.error("\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u4e0d\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u66f4\u65b0\u56de\u53f0\u8d26\u4fe1\u606f\u8868\u5931\u8d25", e);
            throw new TzImportException("\u5c06\u6807\u8bb0\u4e3a\u66f4\u65b0\u4e14\u4e0d\u9700\u8981\u8bb0\u5f55\u53d8\u66f4\u7684\u6570\u636e\u8bb0\u5f55\u66f4\u65b0\u56de\u53f0\u8d26\u4fe1\u606f\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public void delStateRptData(TzParams param) {
        String sql = "DELETE FROM " + param.getTmpTable().getTzTableName() + "_RPT" + " WHERE " + "MDCODE" + " IN ( SELECT " + "MDCODE" + " FROM " + param.getMdCodeTable() + ") AND " + "DATATIME" + " = ?";
        try {
            logger.info("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql, new Object[]{param.getDatatime()});
        }
        catch (DataAccessException e) {
            logger.error("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e\u5931\u8d25", e);
            throw new TzCopyDataException("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e\u5931\u8d25", e);
        }
    }

    @Override
    public void delStateRptDataByRptBizKeyOrder(TzParams param) {
        String sql = "DELETE FROM " + param.getTmpTable().getTzTableName() + "_RPT" + " WHERE " + "BIZKEYORDER" + " IN ( SELECT " + "BIZKEYORDER" + " FROM " + param.getTempTableName() + " WHERE " + "BIZKEYORDER" + " IS NOT NULL )";
        try {
            logger.info("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (DataAccessException e) {
            logger.error("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e\u5931\u8d25", e);
            throw new TzCopyDataException("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e\u5931\u8d25", e);
        }
    }

    @Override
    public void handleAddAllRptData(TzParams param) {
        TempData2RtpTable data2RtpTable = new TempData2RtpTable(this.jdbcTemplate, param);
        String sql = data2RtpTable.buildSql();
        try {
            logger.info("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (Exception e) {
            logger.error("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
            throw new TzImportException("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public void handleRptAddStateData(TzParams param) {
        TempData2RtpTable data2RtpTable = new TempData2RtpTable(this.jdbcTemplate, param, "OPT", "=", 4);
        String sql = data2RtpTable.buildSql();
        try {
            logger.info("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u65b0\u589e\u72b6\u6001\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (Exception e) {
            logger.error("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
            throw new TzImportException("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public void handleRptUpdateStateData(TzParams param) {
        TempData2RtpTable data2RtpTable = new TempData2RtpTable(this.jdbcTemplate, param, "RPT_OPT", "=", 2);
        String sql = data2RtpTable.buildSql();
        try {
            logger.info("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u66f4\u65b0\u72b6\u6001\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (Exception e) {
            logger.error("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
            throw new TzImportException("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
        }
    }

    private void delStateTableData(TzParams params) {
        String del = "DELETE FROM " + params.getStateTableName();
        logger.info("\u6267\u884cSQL:{}", (Object)del);
        this.jdbcTemplate.update(del);
    }

    @Override
    public void delRptUpdateStateData(TzParams param) {
        String sql = "DELETE FROM " + param.getTmpTable().getTzTableName() + "_RPT" + " WHERE " + "BIZKEYORDER" + " IN ( SELECT " + "BIZKEYORDER" + " FROM " + param.getTempTableName() + " WHERE " + "RPT_OPT" + " = " + 2 + " )";
        try {
            logger.info("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (DataAccessException e) {
            logger.error("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e\u5931\u8d25", e);
            throw new TzCopyDataException("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u4e2d\u5f53\u524d\u671f\u6570\u636e\u5931\u8d25", e);
        }
    }

    @Override
    public void handleRptBizKeyOrderIsNUllData(TzParams param) {
        TempData2RtpTable data2RtpTable = new TempData2RtpTable(this.jdbcTemplate, param, "BIZKEYORDER IS NULL");
        String sql = data2RtpTable.buildSql();
        try {
            logger.info("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u65b0\u589e\u72b6\u6001\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868,\u6267\u884cSQL:{}", (Object)sql);
            this.jdbcTemplate.update(sql);
        }
        catch (Exception e) {
            logger.error("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
            throw new TzImportException("\u5c06\u8f85\u52a9\u4e34\u65f6\u8868\u4e2d\u7684\u65f6\u671f\u6570\u636e\u5168\u90e8\u63d2\u5165\u53f0\u8d26\u62a5\u8868\u5931\u8d25", e);
        }
    }
}

