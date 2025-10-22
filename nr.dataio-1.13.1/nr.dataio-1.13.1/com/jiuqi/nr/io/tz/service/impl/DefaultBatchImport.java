/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.util.DateUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.io.common.DataIOTempTableDefine;
import com.jiuqi.nr.io.sb.bean.MdCodeTempTableInfo;
import com.jiuqi.nr.io.sb.bean.RowDimValue;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.CheckState;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.FlagState;
import com.jiuqi.nr.io.tz.bean.MdCodeDataTime;
import com.jiuqi.nr.io.tz.bean.StateTempTableInfo;
import com.jiuqi.nr.io.tz.bean.TzDataImportRes;
import com.jiuqi.nr.io.tz.bean.TzFzTempTableInfo;
import com.jiuqi.nr.io.tz.exception.ParamCheckException;
import com.jiuqi.nr.io.tz.exception.TzCopyDataException;
import com.jiuqi.nr.io.tz.exception.TzDataCheckException;
import com.jiuqi.nr.io.tz.service.BatchImportService;
import com.jiuqi.nr.io.tz.service.IFlagStateDao;
import com.jiuqi.nr.io.tz.service.TzChangeService;
import com.jiuqi.nr.io.tz.service.TzSaveService;
import com.jiuqi.nr.io.tz.service.impl.CopyInputData2TempTable;
import com.jiuqi.nr.io.tz.service.impl.StateDataTmpTable;
import com.jiuqi.nr.io.tz.service.impl.TzFzTempTableDao;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.util.DateUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

@Service
public class DefaultBatchImport
implements BatchImportService {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    protected TzFzTempTableDao tzFzTempTableDao;
    @Autowired
    protected StateDataTmpTable stateDataTmpTable;
    @Autowired
    protected IFlagStateDao flagStateDao;
    @Autowired
    private TzSaveService tzSaveService;
    @Autowired
    private TzChangeService tzChangeService;
    @Autowired
    private ITempTableManager tempTableManager;
    private static final Logger logger = LoggerFactory.getLogger(DefaultBatchImport.class);

    @Override
    public TzDataImportRes batchImport(TzParams param, AsyncTaskMonitor monitor) {
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u5bfc\u5165\u53f0\u8d26\u8868\u6570\u636e\uff0c\u76ee\u6807\u8868 key " + param.getDestForm() + "");
        StopWatch allTimeWatch = new StopWatch();
        allTimeWatch.start();
        try {
            TzDataImportRes res = new TzDataImportRes();
            this.init(param, monitor);
            monitor.progressAndMessage(0.0, "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u53c2\u6570\u521d\u59cb\u5316\u5b8c\u6210\uff0c\u53f0\u8d26\u4fe1\u606f\u8868\u6807\u8bc6 " + param.getTmpTable().getTzTableName() + "");
            CheckState checkState = this.check(param, monitor);
            if (checkState.getMdCodeCount() == 0) {
                monitor.finish("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u6ca1\u6709\u5355\u4f4d\u6570\u636e,\u4e2d\u6b62\u5bfc\u5165", null);
                TzDataImportRes tzDataImportRes = res;
                return tzDataImportRes;
            }
            if (checkState.getFailCodeLog() != null) {
                res.setStatus(1);
                res.setMdCodeLog(checkState.getFailCodeLog());
            }
            this.createTmpTable(param, monitor);
            this.checkData(param, monitor);
            FlagState flagState = this.flagState(param, monitor);
            if (flagState == null) {
                monitor.finish("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u672a\u8ffd\u8e2a\u5230\u6570\u636e\u6807\u8bb0\u72b6\u6001\uff0c\u4e2d\u6b62\u5bfc\u5165", null);
                TzDataImportRes tzDataImportRes = res;
                return tzDataImportRes;
            }
            if (!flagState.isHaveData()) {
                monitor.finish("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u65e0\u6570\u636e\uff0c\u4e2d\u6b62\u5bfc\u5165", null);
                TzDataImportRes tzDataImportRes = res;
                return tzDataImportRes;
            }
            this.setModifyTime(param, flagState);
            this.tzChangeService.flagAfter(param, flagState, monitor);
            this.tzSaveService.save(param, flagState, monitor);
            this.tzChangeService.saveAfter(param, flagState, monitor);
            TzDataImportRes tzDataImportRes = res;
            return tzDataImportRes;
        }
        catch (Exception e) {
            DataSchemeTmpTable tmpTable = param.getTmpTable();
            String tzTableName = "";
            if (tmpTable != null) {
                tzTableName = tmpTable.getTzTableName();
            }
            monitor.error("\u5bfc\u5165\u5931\u8d25\uff0c\u6765\u6e90\u8868: " + param.getSourceData() + ", \u76ee\u6807\u8868: " + tzTableName + "\u3010" + param.getDestForm() + "\u3011", (Throwable)e);
            throw e;
        }
        finally {
            this.postprocessor(param, monitor);
            allTimeWatch.stop();
            monitor.finish("\u6279\u91cf\u5bfc\u5165\u53f0\u8d26\u8868\u6570\u636e:\u6240\u6709\u6d41\u7a0b\u6267\u884c\u5b8c\u6bd5: \u603b\u5171\u8017\u65f6 " + allTimeWatch.getTotalTimeSeconds() + "\u79d2", null);
        }
    }

    private void setModifyTime(TzParams param, FlagState flagState) {
        Timestamp now = Timestamp.from(Instant.now());
        if (flagState.isContainsAdd()) {
            this.updateTableModifyTimeByOpt(param.getTempTable().getTableName(), now, 4);
        }
        if (flagState.isContainsRecordUpdate()) {
            this.updateTableModifyTimeByOpt(param.getTempTable().getTableName(), now, 2);
        }
        if (flagState.isContainsNoRecordUpdate()) {
            this.updateTableModifyTimeByOpt(param.getTempTable().getTableName(), now, 3);
        }
    }

    private void updateTableModifyTimeByOpt(String table, Timestamp now, int opt) {
        String sql = "update " + table + " set " + "MODIFYTIME" + " = ? where  " + "OPT" + " = ?";
        logger.info("\u6570\u636e\u5904\u7406\u8fc7\u7a0b\uff1a\u5c06\u4e34\u65f6\u8868\u91cc\u7684\u66f4\u65b0\u65f6\u95f4\u8bbe\u7f6e\u4e3a\u5f53\u524d\u65f6\u95f4,\u6267\u884cSQL:{} \u6761\u4ef6: {}", (Object)sql, (Object)opt);
        this.jdbcTemplate.update(sql, new Object[]{now, opt});
    }

    public CheckState check(TzParams param, AsyncTaskMonitor monitor) {
        String datatime = param.getDatatime();
        String fullOrAdd = param.getFullOrAdd();
        if (fullOrAdd != null) {
            if (fullOrAdd.equals("A")) {
                monitor.progressAndMessage(0.0, "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b:\u589e\u91cf\u5bfc\u5165");
            } else if (fullOrAdd.equals("F")) {
                monitor.progressAndMessage(0.0, "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b:\u5168\u91cf\u5bfc\u5165");
            }
        } else {
            monitor.error("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b:\u5168\u91cf\u5bfc\u5165", null);
            throw new ParamCheckException("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u5168\u91cf\u3001\u589e\u91cf\u53c2\u6570\u4e0d\u5339\u914d");
        }
        if (datatime == null || datatime.length() != 9) {
            monitor.error("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b:\u65f6\u671f\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", null);
            throw new ParamCheckException("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u65f6\u671f\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        CheckState checkState = new CheckState();
        this.checkSourceData(param, monitor);
        this.initMdCodeTable(param, checkState, monitor);
        this.checkPeriod(param, checkState, monitor);
        return checkState;
    }

    private void checkSourceData(TzParams param, AsyncTaskMonitor monitor) {
        List<Object> fieldsStr;
        DataSchemeTmpTable tmpTable;
        String sourceData;
        block14: {
            sourceData = param.getSourceData();
            tmpTable = param.getTmpTable();
            if (!tmpTable.getTable().isRepeatCode()) {
                fieldsStr = new ArrayList<String>();
                fieldsStr.add("MDCODE");
                fieldsStr.addAll(Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).collect(Collectors.toList()));
                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append("select count(1),").append(String.join((CharSequence)",", fieldsStr));
                sqlBuilder.append(" from ").append(sourceData);
                sqlBuilder.append(" group by ").append(String.join((CharSequence)",", fieldsStr));
                sqlBuilder.append(" having count(1) > ?");
                String sql = sqlBuilder.toString();
                try {
                    ArrayList<DataField> dimFields = new ArrayList<DataField>();
                    dimFields.addAll(tmpTable.getDimFields());
                    dimFields.addAll(tmpTable.getTableDimFields());
                    logger.info("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u6267\u884cSQL\uff1a{}", (Object)sql);
                    List dims = (List)this.jdbcTemplate.query(sql, rs -> {
                        ArrayList list = new ArrayList();
                        while (rs.next()) {
                            RowDimValue rowDimValue = new RowDimValue();
                            String mdCode = rs.getString(1);
                            rowDimValue.setMdCode(mdCode);
                            for (int i = 0; i < dimFields.size(); ++i) {
                                DataField dataField = (DataField)dimFields.get(i);
                                DataFieldType dataFieldType = dataField.getDataFieldType();
                                if (dataFieldType == DataFieldType.STRING) {
                                    String dimValue = rs.getString(i + 2);
                                    rowDimValue.getDim().add(dimValue);
                                    continue;
                                }
                                if (dataFieldType == DataFieldType.DATE || dataFieldType == DataFieldType.DATE_TIME) {
                                    Date date = rs.getDate(i + 2);
                                    if (rs.wasNull()) {
                                        rowDimValue.getDim().add(null);
                                        continue;
                                    }
                                    rowDimValue.getDim().add(DateUtil.convertDateToString((java.util.Date)date));
                                    continue;
                                }
                                throw new ParamCheckException("\u4e0d\u652f\u6301\u7684\u8868\u5185\u7ef4\u5ea6\u7c7b\u578b:" + dataFieldType.getTitle());
                            }
                        }
                        return list;
                    }, new Object[]{1});
                    if (!CollectionUtils.isEmpty(dims)) {
                        StringBuilder msg = new StringBuilder();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        for (RowDimValue dim : dims) {
                            ArrayList<String> dimStr = new ArrayList<String>();
                            for (Object o : dim.getDim()) {
                                if (o instanceof java.util.Date) {
                                    dimStr.add(dateFormat.format(o));
                                    continue;
                                }
                                if (o != null) {
                                    dimStr.add(o.toString());
                                    continue;
                                }
                                dimStr.add("");
                            }
                            msg.append("[").append(dim.getMdCode());
                            msg.append(String.join((CharSequence)",", dimStr));
                            msg.append("]\n");
                            logger.warn("\u8868\u5185\u7ef4\u5ea6\u91cd\u590d:" + dim);
                        }
                        throw new TzDataCheckException("\u8868\u5185\u7ef4\u5ea6\u91cd\u590d:" + msg);
                    }
                    break block14;
                }
                catch (ParamCheckException e) {
                    monitor.error("\u4e0d\u652f\u6301\u7684\u8868\u5185\u7ef4\u5ea6\u7c7b\u578b", (Throwable)e);
                    throw e;
                }
                catch (TzDataCheckException e) {
                    monitor.error("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u8868\u5185\u7ef4\u5ea6\u91cd\u590d\uff01", (Throwable)e);
                    throw e;
                }
                catch (Exception e) {
                    monitor.error("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e34\u65f6\u8868\u4e0d\u5b58\u5728\uff01", (Throwable)e);
                    throw new ParamCheckException("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e34\u65f6\u8868\u4e0d\u5b58\u5728\uff01", e);
                }
            }
            String sql = MessageFormat.format("select 1 from {0} where 1!=1", sourceData);
            try {
                logger.info("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u6267\u884cSQL\uff1a{}", (Object)sql);
                this.jdbcTemplate.execute(sql);
            }
            catch (Exception e) {
                monitor.error("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e34\u65f6\u8868\u4e0d\u5b58\u5728\uff01", (Throwable)e);
                throw new ParamCheckException("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e34\u65f6\u8868\u4e0d\u5b58\u5728\uff01", e);
            }
        }
        fieldsStr = Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys(), tmpTable.getTimePointDeploys(), tmpTable.getPeriodicDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).collect(Collectors.toList());
        fieldsStr.add("ID");
        fieldsStr.add("MDCODE");
        StringBuilder checkTmpTableColumns = new StringBuilder("select ");
        checkTmpTableColumns.append(String.join((CharSequence)",", fieldsStr));
        checkTmpTableColumns.append(" from ").append(sourceData).append(" where 1!=1");
        try {
            logger.info("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u6267\u884cSQL\uff1a{}", (Object)checkTmpTableColumns);
            this.jdbcTemplate.execute(checkTmpTableColumns.toString());
        }
        catch (Exception e) {
            monitor.error("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e34\u65f6\u8868\u5b57\u6bb5\u4e0d\u5339\u914d\uff01", (Throwable)e);
            throw new ParamCheckException("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e34\u65f6\u8868\u5b57\u6bb5\u4e0d\u5339\u914d\uff01", e);
        }
    }

    private void checkPeriod(TzParams param, CheckState checkState, AsyncTaskMonitor monitor) {
        String datatime = param.getDatatime();
        String mdCodeTable = param.getMdCodeTable();
        String tzTableName = param.getTmpTable().getTzTableName();
        String maxTimeSql = MessageFormat.format("SELECT T1.{0},MAX(T1.{1}) FROM {2} T0 INNER JOIN {3} T1 ON T0.{4} = T1.{5} GROUP BY T1.{6}", "MDCODE", "VALIDDATATIME", mdCodeTable, tzTableName, "MDCODE", "MDCODE", "MDCODE");
        monitor.progressAndMessage(0.0, "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u6267\u884cSQL\uff1a" + maxTimeSql);
        List mdCodeDataTimes = (List)this.jdbcTemplate.query(maxTimeSql, rs -> {
            ArrayList<MdCodeDataTime> list = new ArrayList<MdCodeDataTime>();
            while (rs.next()) {
                MdCodeDataTime mdCodeDataTime = new MdCodeDataTime();
                mdCodeDataTime.setMdCode(rs.getString(1));
                mdCodeDataTime.setValidDataTime(rs.getString(2));
                list.add(mdCodeDataTime);
            }
            return list;
        });
        checkState.setCodeDataTimes(mdCodeDataTimes);
        HashMap<String, List<MdCodeDataTime>> failCodeLog = new HashMap<String, List<MdCodeDataTime>>();
        int fail = 0;
        if (!CollectionUtils.isEmpty(mdCodeDataTimes)) {
            for (MdCodeDataTime mdCodeDataTime : mdCodeDataTimes) {
                List list;
                String period = mdCodeDataTime.getValidDataTime();
                if (period.length() != 9) {
                    list = failCodeLog.computeIfAbsent("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u53f0\u8d26\u4fe1\u606f\u8868\u4e2d\u751f\u6548\u65f6\u95f4\u65f6\u671f\u503c\u4e0d\u6b63\u786e", k -> new ArrayList());
                    list.add(mdCodeDataTime);
                    ++fail;
                    continue;
                }
                if (PeriodConsts.codeToType((int)datatime.charAt(4)) != PeriodConsts.codeToType((int)period.charAt(4))) {
                    list = failCodeLog.computeIfAbsent("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u65f6\u671f\u53c2\u6570\u4e0d\u6b63\u786e", k -> new ArrayList());
                    list.add(mdCodeDataTime);
                    ++fail;
                    continue;
                }
                if (PeriodUtils.comparePeriod((String)datatime, (String)period) >= 0) continue;
                list = failCodeLog.computeIfAbsent("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e0d\u80fd\u5bfc\u5165\u5386\u53f2\u671f\u6570\u636e", k -> new ArrayList());
                list.add(mdCodeDataTime);
                ++fail;
            }
        }
        if (!failCodeLog.isEmpty()) {
            List mdCode = failCodeLog.values().stream().filter(Objects::nonNull).flatMap(Collection::stream).filter(Objects::nonNull).map(r -> "'" + r.getMdCode() + "'").collect(Collectors.toList());
            if (!mdCode.isEmpty()) {
                String delMdCodeSql = MessageFormat.format("DELETE {0} WHERE {1} IN ( {2} )", mdCodeTable, "MDCODE", String.join((CharSequence)",", mdCode));
                monitor.progressAndMessage(0.0, "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u5904\u7406\u8df3\u8fc7\u5bfc\u5165\u7684\u5355\u4f4d,\u6267\u884cSQL\uff1a" + delMdCodeSql);
                this.jdbcTemplate.execute(delMdCodeSql);
            }
            checkState.setFailCodeLog(failCodeLog);
            monitor.progressAndMessage(0.0, "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u8df3\u8fc7\u5bfc\u5165\u7684\u5355\u4f4d" + failCodeLog);
        } else {
            checkState.setFailCodeLog(null);
        }
        if (fail != 0 && checkState.getMdCodeCount() == fail) {
            monitor.error("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e0d\u80fd\u5bfc\u5165\u5386\u53f2\u671f\u6570\u636e\uff01", null);
            throw new ParamCheckException("\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u4e0d\u80fd\u5bfc\u5165\u5386\u53f2\u671f\u6570\u636e");
        }
    }

    public void init(TzParams param, AsyncTaskMonitor monitor) {
        this.initDataSchemeInfo(param);
        this.initDataBase(param);
    }

    private void initMdCodeTable(TzParams param, CheckState checkState, AsyncTaskMonitor monitor) {
        if (param.getMdCodeTable() == null && param.getSourceType().equals("1")) {
            try {
                ITempTable mdCodeTempTable;
                DataIOTempTableDefine mdCodeTempTableDefine = new DataIOTempTableDefine(new MdCodeTempTableInfo());
                try {
                    mdCodeTempTable = this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)mdCodeTempTableDefine);
                    param.setMdCodeTempTable(mdCodeTempTable);
                    param.setMdCodeTable(mdCodeTempTable.getTableName());
                    String sql = "insert into " + mdCodeTempTable.getTableName() + " select DISTINCT " + "MDCODE" + " FROM " + param.getSourceData();
                    this.jdbcTemplate.execute(sql);
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String mdCodeCount = MessageFormat.format("select count(1) from {0}", mdCodeTempTable.getTableName());
                Integer count = (Integer)this.jdbcTemplate.query(mdCodeCount, rs -> {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return 0;
                });
                checkState.setMdCodeCount(count == null ? 0 : count);
                monitor.progressAndMessage(0.0, "\u51c6\u5907\u9636\u6bb5\uff1a\u5bfc\u5165\u5355\u4f4d\u8303\u56f4\u8868\u4e2d\u5355\u4f4d\u6570\u91cf\uff1a" + checkState.getMdCodeCount());
            }
            catch (Exception e) {
                throw new ParamCheckException("\u521d\u59cb\u5316\u5bfc\u5165\u5355\u4f4d\u8303\u56f4\u8868\u5931\u8d25");
            }
        }
    }

    private void initDataBase(TzParams param) {
        Connection connection = null;
        try {
            connection = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            param.setiDatabase(database);
        }
        catch (SQLException e) {
            throw new ParamCheckException("\u51c6\u5907\u9636\u6bb5\uff1a\u521d\u59cb\u5316\u5931\u8d25");
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    public void checkData(TzParams param, AsyncTaskMonitor monitor) {
        String tempTableName = param.getTempTableName();
        List<DataFieldDeployInfo> tableDimDeploys = param.getTmpTable().getTableDimDeploys();
        ArrayList<String> dimWhere = new ArrayList<String>();
        block0: for (DataFieldDeployInfo tableDimDeploy : tableDimDeploys) {
            for (DataField tableDimField : param.getTmpTable().getTableDimFields()) {
                String s;
                if (!tableDimDeploy.getDataFieldKey().equals(tableDimField.getKey())) continue;
                if (tableDimField.getDataFieldType().equals((Object)DataFieldType.STRING)) {
                    s = "(" + tableDimDeploy.getFieldName() + " = '" + "-" + "' OR " + tableDimDeploy.getFieldName() + " = '')";
                    dimWhere.add(s);
                    continue block0;
                }
                s = tableDimDeploy.getFieldName() + " is null ";
                dimWhere.add(s);
                continue block0;
            }
        }
        String sql = MessageFormat.format("SELECT COUNT(1) FROM {0} WHERE {1}", tempTableName, String.join((CharSequence)" AND ", dimWhere));
        monitor.progressAndMessage(0.0, "\u6570\u636e\u68c0\u67e5\u8fc7\u7a0b\uff1a\u6267\u884cSQL\uff1a" + sql);
        Integer count = (Integer)this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
        if (count != null && count > 0) {
            monitor.error("\u6570\u636e\u68c0\u67e5\u8fc7\u7a0b\uff1a\u5bfc\u5165\u6570\u636e\u4e2d\u5b58\u5728\u8868\u5185\u7ef4\u5ea6\u5168\u4e3a\u7a7a\u7684\u6570\u636e", null);
            throw new ParamCheckException("\u6570\u636e\u68c0\u67e5\u8fc7\u7a0b\uff1a\u5bfc\u5165\u6570\u636e\u4e2d\u5b58\u5728\u8868\u5185\u7ef4\u5ea6\u5168\u4e3a\u7a7a\u7684\u6570\u636e");
        }
    }

    public void createTmpTable(TzParams param, AsyncTaskMonitor monitor) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        param.getTmpTable().setFull("F".equals(param.getFullOrAdd()));
        param.getTmpTable().setTempTableName(null);
        DataIOTempTableDefine stateDataTempTableDefine = new DataIOTempTableDefine(new StateTempTableInfo(param.getTmpTable()));
        try {
            ITempTable stateDataTempTable = this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)stateDataTempTableDefine);
            param.setStateTempTable(stateDataTempTable);
            param.setStateTableName(stateDataTempTable.getTableName());
            param.getTmpTable().setTempTableName(stateDataTempTable.getTableName());
            monitor.progressAndMessage(0.0, "\u521b\u5efa\u72b6\u6001\u4e2d\u8f6c\u8868\u3010" + stateDataTempTable.getTableName() + "\u3011\u6210\u529f");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        param.getTmpTable().setTempTableName(null);
        DataIOTempTableDefine tzFzTempTableDefine = new DataIOTempTableDefine(new TzFzTempTableInfo(param.getTmpTable()));
        try {
            ITempTable tzFzTempTable = this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)tzFzTempTableDefine);
            param.setTempTable(tzFzTempTable);
            param.setTempTableName(tzFzTempTable.getTableName());
            param.getTmpTable().setTempTableName(tzFzTempTable.getTableName());
            monitor.progressAndMessage(0.0, "\u521b\u5efa\u6807\u8bb0\u4e34\u65f6\u8868\u3010" + tzFzTempTable.getTableName() + "\u3011\u6210\u529f");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CopyInputData2TempTable insertLoader = new CopyInputData2TempTable(this.jdbcTemplate, param);
        try {
            insertLoader.execute();
        }
        catch (TableLoaderException e) {
            throw new TzCopyDataException("\u5c06\u6765\u6e90\u8868\u6570\u636e\u590d\u5236\u5230\u8f85\u52a9\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
        stopWatch.stop();
        monitor.progressAndMessage(0.0, "\u51c6\u5907\u9636\u6bb5\uff1a\u6839\u636e\u6765\u6e90\u6570\u636e\u751f\u6210\u4e34\u65f6\u8868\u8017\u65f6: " + stopWatch.getTotalTimeSeconds() + " \u79d2");
    }

    public FlagState flagState(TzParams param, AsyncTaskMonitor monitor) {
        boolean rptProcess;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u521d\u59cb\u5316\u5b8c\u6bd5\uff0c\u5f00\u59cb\u6807\u8bb0\u8fc7\u7a0b");
        this.flagStateDao.copySbId(param);
        int rptUpdate = 0;
        boolean bl = rptProcess = !"F".equals(param.getFullOrAdd()) && !param.getTmpTable().getPeriodicDeploys().isEmpty();
        if (rptProcess) {
            this.flagStateDao.copyRptBizKeyOrder(param);
        }
        this.flagStateDao.allFieldUpdateState(param);
        this.flagStateDao.notRecordChangeState(param);
        this.flagStateDao.addChangeState(param);
        if (rptProcess) {
            this.flagStateDao.allRptFieldUpdateState(param);
            rptUpdate = this.flagStateDao.countByRptState(param, (byte)2);
            monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u53f0\u8d26\u62a5\u8868 " + rptUpdate + " \u6761\u6570\u636e\u88ab\u6807\u8bb0\u53d1\u751f\u53d8\u5316");
        }
        int add = this.flagStateDao.countByState(param, (byte)4);
        monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u53f0\u8d26\u4fe1\u606f\u8868 " + add + "\u6761\u6570\u636e\u88ab\u6807\u8bb0\u4e3a\u65b0\u589e");
        int update = this.flagStateDao.countByState(param, (byte)2);
        monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u53f0\u8d26\u4fe1\u606f\u8868 " + update + "\u6761\u6570\u636e\u88ab\u6807\u8bb0\u4e3a\u66f4\u65b0");
        int update1 = this.flagStateDao.countByState(param, (byte)3);
        monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u53f0\u8d26\u4fe1\u606f\u8868 " + update1 + "\u6761\u6570\u636e\u88ab\u6807\u8bb0\u4e3a\u66f4\u65b0\u4f46\u4e0d\u9700\u8981\u8bb0\u5f55\u53d8\u66f4");
        int del = 0;
        if ("F".equals(param.getFullOrAdd())) {
            del = this.flagStateDao.countByState(param, (byte)-1);
            monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u53f0\u8d26\u4fe1\u606f\u8868 " + del + "\u6761\u6570\u636e\u88ab\u6807\u8bb0\u4e3a\u5220\u9664");
        }
        int none = this.flagStateDao.countByState(param, (byte)0);
        monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u53f0\u8d26\u4fe1\u606f\u8868 " + none + "\u6761\u6570\u636e\u88ab\u6807\u8bb0\u672a\u53d1\u751f\u53d8\u5316");
        stopWatch.stop();
        monitor.progressAndMessage(0.0, "\u6807\u8bb0\u8fc7\u7a0b\uff1a\u6807\u8bb0\u9636\u6bb5\u603b\u8017\u65f6" + stopWatch.getTotalTimeSeconds() + "\u79d2");
        FlagState flagState = new FlagState(add, update, update1, del, none);
        flagState.setRptAdd(add);
        flagState.setRptUpdate(rptUpdate);
        return flagState;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void postprocessor(TzParams param, AsyncTaskMonitor monitor) {
        try {
            monitor.progressAndMessage(0.0, "\u6e05\u7406\u9636\u6bb5\uff1a\u5220\u9664\u4e34\u65f6\u8868: " + param.getTempTable().getTableName());
            param.getTempTable().close();
            return;
        }
        catch (IOException e) {
            logger.error("\u6e05\u7406\u9636\u6bb5\uff1a\u5220\u9664\u4e34\u65f6\u8868" + param.getTempTable().getTableName() + "\u51fa\u9519\uff01" + e.getMessage());
            return;
        }
        finally {
            try {
                monitor.progressAndMessage(0.0, "\u6e05\u7406\u9636\u6bb5\uff1a\u5220\u9664\u4e34\u65f6\u8868: " + param.getMdCodeTempTable().getTableName());
                param.getMdCodeTempTable().close();
            }
            catch (IOException e) {
                logger.error("\u6e05\u7406\u9636\u6bb5\uff1a\u5220\u9664\u4e34\u65f6\u8868" + param.getMdCodeTempTable().getTableName() + "\u51fa\u9519\uff01" + e.getMessage());
            }
            finally {
                try {
                    monitor.progressAndMessage(0.0, "\u6e05\u7406\u9636\u6bb5\uff1a\u5220\u9664\u4e34\u65f6\u8868: " + param.getStateTempTable().getTableName());
                    param.getStateTempTable().close();
                }
                catch (IOException e) {
                    logger.error("\u6e05\u7406\u9636\u6bb5\uff1a\u5220\u9664\u4e34\u65f6\u8868" + param.getStateTempTable().getTableName() + "\u51fa\u9519\uff01" + e.getMessage());
                }
            }
        }
    }

    private void initDataSchemeInfo(TzParams params) {
        DataSchemeTmpTable tmpTable = this.tzFzTempTableDao.dataFieldInit(params.getDestForm());
        params.setTmpTable(tmpTable);
    }
}

