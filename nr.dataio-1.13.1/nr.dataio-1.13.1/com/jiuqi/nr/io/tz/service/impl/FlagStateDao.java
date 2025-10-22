/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.tz.exception.TzStateException;
import com.jiuqi.nr.io.tz.service.IFlagStateDao;
import com.jiuqi.nr.io.tz.service.impl.CopyDelData2TempTable;
import com.jiuqi.nr.io.tz.service.impl.CopyRptBizkeyOrder;
import com.jiuqi.nr.io.tz.service.impl.CopySbid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

@Repository
public class FlagStateDao
implements IFlagStateDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value(value="#{T(java.lang.Integer).parseInt('${nr.io.tz.transactionSize:0}')}")
    private Integer transactionSize;
    private static final Logger logger = LoggerFactory.getLogger(FlagStateDao.class);
    private static final String FLAG_SQL = "UPDATE %s T0 SET T0.%s = %s WHERE T0.%s IS NOT NULL AND T0.%s IN (%s) AND T0.%s = %s ";
    private static final String JOIN_SQL = "SELECT T2.%s FROM %s T1 INNER JOIN %s T2 ON T1.%s = T2.%s ";
    private static final String FLAG_SQL_1 = "UPDATE %s T0 SET T0.%s = %s WHERE T0.%s = %s AND T0.%s IN (%s) AND T0.%s = %s ";

    @Override
    public void copySbId(TzParams tzParams) {
        boolean isFull = "F".equals(tzParams.getFullOrAdd());
        if (isFull) {
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u5168\u91cf\u7b97\u6cd5\u6807\u8bb0");
        } else {
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u589e\u91cf\u7b97\u6cd5\u6807\u8bb0");
        }
        try {
            StopWatch copyWatch = new StopWatch();
            copyWatch.start();
            CopySbid copySbid = new CopySbid(this.jdbcTemplate, tzParams, this.transactionSize);
            copySbid.execute();
            copyWatch.stop();
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e00\u6b65,\u590d\u5236 SBID \u5230\u6269\u5c55\u8868 \u8017\u65f6: {} \u79d2", (Object)copyWatch.getTotalTimeSeconds());
        }
        catch (TableLoaderException e) {
            throw new TzCopyDataException("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u5c06\u539f\u8868 SBID \u66f4\u65b0\u5230\u8f85\u52a9\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
        if (!isFull) {
            return;
        }
        try {
            StopWatch delWatch = new StopWatch();
            delWatch.start();
            CopyDelData2TempTable copyDelData2TempTable = new CopyDelData2TempTable(this.jdbcTemplate, tzParams);
            copyDelData2TempTable.execute();
            delWatch.stop();
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e8c\u6b65,\u590d\u5236\u5220\u9664\u72b6\u6001\u7684\u6570\u636e\u5230\u6269\u5c55\u8868 \u8017\u65f6: {} \u79d2", (Object)delWatch.getTotalTimeSeconds());
        }
        catch (TableLoaderException e) {
            throw new TzCopyDataException("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u5c06\u539f\u8868 \u8981\u5220\u9664\u7684\u6570\u636e\u63d2\u5165\u8f85\u52a9\u4e34\u65f6\u8868\u6807\u8bb0 \u5931\u8d25", e);
        }
    }

    @Override
    public void copyRptBizKeyOrder(TzParams tzParams) {
        DataSchemeTmpTable tmpTable = tzParams.getTmpTable();
        if (tmpTable.getPeriodicDeploys().isEmpty()) {
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e8c\u6b65,\u53f0\u8d26\u62a5\u8868\u5168\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001-\u65e0\u65f6\u671f\u6570\u636e\u6307\u6807\u65e0\u9700\u6bd4\u8f83");
            return;
        }
        try {
            StopWatch copyWatch = new StopWatch();
            copyWatch.start();
            CopyRptBizkeyOrder copyRptBizkeyOrder = new CopyRptBizkeyOrder(this.jdbcTemplate, tzParams, this.transactionSize);
            copyRptBizkeyOrder.execute();
            copyWatch.stop();
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e8c\u6b65,\u590d\u5236 rptBizkeyOrder \u5230\u6269\u5c55\u8868 \u8017\u65f6: {} \u79d2", (Object)copyWatch.getTotalTimeSeconds());
        }
        catch (TableLoaderException e) {
            throw new TzCopyDataException("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e8c\u6b65,\u590d\u5236 rptBizKeyOrder \u5230\u6269\u5c55\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public int allFieldUpdateState(TzParams param) {
        DataSchemeTmpTable tmpTable = param.getTmpTable();
        String innerTable = String.format(JOIN_SQL, "SBID", param.getTempTableName(), tmpTable.getTzTableName(), "SBID", "SBID");
        List fields = tmpTable.getTimePointDeploys().stream().map(DataFieldDeployInfo::getFieldName).map(r -> " (T1." + r + "!=T2." + r + " OR T1." + r + " IS NULL AND T2." + r + " IS NOT NULL OR T1." + r + " IS NOT NULL AND T2." + r + " IS NULL) ").collect(Collectors.toList());
        if (fields.isEmpty()) {
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e09\u6b65,\u5168\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001-\u65e0\u65f6\u70b9\u6570\u636e\u6307\u6807\u65e0\u9700\u6bd4\u8f83");
            return 0;
        }
        String innerSql = "SELECT T3.SBID FROM (" + innerTable + " WHERE (" + String.join((CharSequence)" OR ", fields) + ")) T3";
        String sql = String.format(FLAG_SQL, param.getTempTableName(), "OPT", (byte)2, "SBID", "SBID", innerSql, "OPT", (byte)0);
        logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e09\u6b65,\u5168\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001,\u6267\u884cSQL:{}", (Object)sql);
        try {
            return this.jdbcTemplate.update(sql);
        }
        catch (DataAccessException e) {
            throw new TzStateException("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e09\u6b65,\u5168\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001\u5931\u8d25", e);
        }
    }

    @Override
    public int notRecordChangeState(TzParams param) {
        DataSchemeTmpTable tmpTable = param.getTmpTable();
        String innerTable = String.format(JOIN_SQL, "SBID", param.getTempTableName(), tmpTable.getTzTableName(), "SBID", "SBID");
        Set gVKeySet = tmpTable.getTimePointFields().stream().filter(DataField::isGenerateVersion).map(Basic::getKey).collect(Collectors.toSet());
        List gVDe = tmpTable.getTimePointDeploys().stream().filter(r -> gVKeySet.contains(r.getDataFieldKey())).collect(Collectors.toList());
        List fields = gVDe.stream().map(DataFieldDeployInfo::getFieldName).map(r -> " (T1." + r + "=T2." + r + " OR T1. " + r + " IS NULL AND T2." + r + " IS NULL) ").collect(Collectors.toList());
        String fieldEqual = "";
        if (!fields.isEmpty()) {
            fieldEqual = "OR ((" + String.join((CharSequence)" AND ", fields) + "))";
        }
        String innerSql = "SELECT T3.SBID FROM (" + innerTable + " WHERE T2." + "VALIDDATATIME" + " = ? " + fieldEqual + ") T3";
        String sql = String.format(FLAG_SQL_1, param.getTempTableName(), "OPT", (byte)3, "OPT", (byte)2, "SBID", innerSql, "OPT", (byte)2);
        logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u56db\u6b65,\u90e8\u5206\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001,\u6267\u884cSQL:{}", (Object)sql);
        try {
            return this.jdbcTemplate.update(sql, new Object[]{param.getDatatime()});
        }
        catch (DataAccessException e) {
            throw new TzStateException("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u56db\u6b65,\u90e8\u5206\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001\u5931\u8d25", e);
        }
    }

    @Override
    public int addChangeState(TzParams params) {
        String uuid = DataEngineUtil.buildcreateUUIDSql((IDatabase)params.getiDatabase(), (boolean)true);
        String sql = "UPDATE " + params.getTempTableName() + " SET " + "OPT" + " = " + 4 + "," + "SBID" + " = " + uuid + " WHERE " + "SBID" + " IS NULL";
        logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e94\u6b65,\u6807\u8bb0\u65b0\u589e\u6761\u76ee\u6570\u636e\u6267\u884cSQL:{}", (Object)sql);
        try {
            return this.jdbcTemplate.update(sql);
        }
        catch (DataAccessException e) {
            throw new TzStateException("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u4e94\u6b65,\u6807\u8bb0\u65b0\u589e\u6761\u76ee\u6570\u636e\u5931\u8d25", e);
        }
    }

    @Override
    public int allRptFieldUpdateState(TzParams param) {
        DataSchemeTmpTable tmpTable = param.getTmpTable();
        if (tmpTable.getPeriodicDeploys().isEmpty()) {
            logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u516d\u6b65,\u53f0\u8d26\u62a5\u8868\u5168\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001-\u65e0\u65f6\u671f\u6570\u636e\u6307\u6807\u65e0\u9700\u6bd4\u8f83");
            return 0;
        }
        List fields = tmpTable.getPeriodicDeploys().stream().map(DataFieldDeployInfo::getFieldName).map(r -> " (T1." + r + "!=T2." + r + " OR T1." + r + " IS NULL AND T2." + r + " IS NOT NULL OR T1." + r + " IS NOT NULL AND T2." + r + " IS NULL) ").collect(Collectors.toList());
        StringBuilder sqlBuild = new StringBuilder();
        sqlBuild.append("UPDATE %s T0 SET T0.%s = %s ");
        sqlBuild.append("WHERE T0.%s IN (");
        sqlBuild.append("\tSELECT T3.%s FROM (");
        sqlBuild.append("\t\tSELECT T2.%s FROM %s T1 INNER JOIN %s T2");
        sqlBuild.append("\t\tON T1.%s = T2.%s ");
        sqlBuild.append("\t\tWHERE (%s)");
        sqlBuild.append("\t) T3");
        sqlBuild.append(")");
        ArrayList<Object> placeholders = new ArrayList<Object>();
        placeholders.add(tmpTable.getTempTableName());
        placeholders.add("RPT_OPT");
        placeholders.add((byte)2);
        placeholders.add("BIZKEYORDER");
        placeholders.add("BIZKEYORDER");
        placeholders.add("BIZKEYORDER");
        placeholders.add(tmpTable.getTempTableName());
        placeholders.add(tmpTable.getTzTableName() + "_RPT");
        placeholders.add("BIZKEYORDER");
        placeholders.add("BIZKEYORDER");
        placeholders.add(String.join((CharSequence)" OR ", fields));
        String sql = String.format(sqlBuild.toString(), placeholders.toArray());
        logger.info("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u516d\u6b65,\u53f0\u8d26\u62a5\u8868\u5168\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001,\u6267\u884cSQL:{}", (Object)sql);
        try {
            return this.jdbcTemplate.update(sql);
        }
        catch (DataAccessException e) {
            throw new TzStateException("\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u7b2c\u516d\u6b65,\u53f0\u8d26\u62a5\u8868\u5168\u5b57\u6bb5\u6bd4\u8f83\u6807\u8bb0\u66f4\u65b0\u72b6\u6001\u5931\u8d25", e);
        }
    }

    @Override
    public int countByState(TzParams params, byte state) {
        String sql = "SELECT COUNT(1) FROM " + params.getTempTableName() + " WHERE " + "OPT" + " = ?";
        Optional<Object> max = Optional.ofNullable(this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[]{state}));
        return (Integer)max.orElse(0);
    }

    @Override
    public int countByRptState(TzParams params, byte state) {
        String sql = "SELECT COUNT(1) FROM " + params.getTempTableName() + " WHERE " + "RPT_OPT" + " = ?";
        Optional<Object> max = Optional.ofNullable(this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[]{state}));
        return (Integer)max.orElse(0);
    }
}

