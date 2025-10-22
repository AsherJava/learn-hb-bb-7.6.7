/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.util.DateUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.io.sb.ISBImportActuator;
import com.jiuqi.nr.io.sb.ImportMode;
import com.jiuqi.nr.io.sb.JIOSBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorFactory;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.sb.bean.RowDimValue;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.ParamCheckException;
import com.jiuqi.nr.io.tz.exception.TzDataCheckException;
import com.jiuqi.nr.io.tz.service.TzBatchImportService;
import com.jiuqi.nr.io.tz.service.impl.TzFzTempTableDao;
import com.jiuqi.util.DateUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

@Service
public class TzBatchImportServiceImpl
implements TzBatchImportService {
    private static final Logger logger = LoggerFactory.getLogger(TzBatchImportServiceImpl.class);
    @Autowired
    private TzFzTempTableDao tzFzTempTableDao;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private SBImportActuatorFactory sbImportActuatorFactory;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void batchImport(TzParams tzParams, AsyncTaskMonitor monitor) {
        monitor.progressAndMessage(0.0, "\u5f00\u59cb\u5bfc\u5165\u53f0\u8d26\u8868\u6570\u636e\uff0c\u76ee\u6807\u8868 key " + tzParams.getDestForm() + "");
        StopWatch allTimeWatch = new StopWatch();
        allTimeWatch.start();
        ISBImportActuator sbImportActuator = null;
        try {
            this.init(tzParams, monitor);
            this.check(tzParams, monitor);
            sbImportActuator = this.structureActuator(tzParams);
            this.executeImport(tzParams, sbImportActuator, monitor);
            if (null != sbImportActuator) {
                sbImportActuator.close();
            }
        }
        catch (Exception e) {
            try {
                DataSchemeTmpTable tmpTable = tzParams.getTmpTable();
                String tzTableName = "";
                if (tmpTable != null) {
                    tzTableName = tmpTable.getTzTableName();
                }
                monitor.error("\u5bfc\u5165\u5931\u8d25\uff0c\u6765\u6e90\u8868: " + tzParams.getSourceData() + ", \u76ee\u6807\u8868: " + tzTableName + "\u3010" + tzParams.getDestForm() + "\u3011", (Throwable)e);
                throw e;
            }
            catch (Throwable throwable) {
                if (null != sbImportActuator) {
                    sbImportActuator.close();
                }
                String dropSql = MessageFormat.format("DROP TABLE {0}", tzParams.getSourceData());
                this.jdbcTemplate.execute(dropSql);
                allTimeWatch.stop();
                monitor.finish("\u6279\u91cf\u5bfc\u5165\u53f0\u8d26\u8868\u6570\u636e:\u6240\u6709\u6d41\u7a0b\u6267\u884c\u5b8c\u6bd5: \u603b\u5171\u8017\u65f6 " + allTimeWatch.getTotalTimeSeconds() + "\u79d2", null);
                throw throwable;
            }
        }
        String dropSql = MessageFormat.format("DROP TABLE {0}", tzParams.getSourceData());
        this.jdbcTemplate.execute(dropSql);
        allTimeWatch.stop();
        monitor.finish("\u6279\u91cf\u5bfc\u5165\u53f0\u8d26\u8868\u6570\u636e:\u6240\u6709\u6d41\u7a0b\u6267\u884c\u5b8c\u6bd5: \u603b\u5171\u8017\u65f6 " + allTimeWatch.getTotalTimeSeconds() + "\u79d2", null);
    }

    private void init(TzParams tzParams, AsyncTaskMonitor monitor) {
        DataSchemeTmpTable tmpTable = this.tzFzTempTableDao.dataFieldInit(tzParams.getDestForm());
        tzParams.setTmpTable(tmpTable);
        monitor.progressAndMessage(0.0, "\u53c2\u6570\u68c0\u67e5\u8fc7\u7a0b\uff1a\u53c2\u6570\u521d\u59cb\u5316\u5b8c\u6210\uff0c\u53f0\u8d26\u4fe1\u606f\u8868\u6807\u8bc6 " + tzParams.getTmpTable().getTzTableName() + "");
    }

    private void check(TzParams tzParams, AsyncTaskMonitor monitor) {
        String datatime = tzParams.getDatatime();
        String fullOrAdd = tzParams.getFullOrAdd();
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
        this.checkSourceData(tzParams, monitor);
    }

    private void checkSourceData(TzParams tzParams, AsyncTaskMonitor monitor) {
        List<Object> fieldsStr;
        DataSchemeTmpTable tmpTable;
        String sourceData;
        block14: {
            sourceData = tzParams.getSourceData();
            tmpTable = tzParams.getTmpTable();
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

    private ISBImportActuator structureActuator(TzParams tzParams) {
        DataSchemeTmpTable tmpTable = tzParams.getTmpTable();
        JIOSBImportActuatorConfig cfg = new JIOSBImportActuatorConfig();
        cfg.configItems().put("TYPE", (Object)SBImportActuatorType.BUF_DB);
        cfg.configItems().put("DEST_TABLE", tzParams.getDestForm());
        cfg.configItems().put("DEST_PERIOD", tzParams.getDatatime());
        if ("F".equals(tzParams.getFullOrAdd())) {
            cfg.configItems().put("IMPORT_MODE", (Object)ImportMode.ALL);
        } else {
            cfg.configItems().put("IMPORT_MODE", (Object)ImportMode.INCREMENT);
        }
        HashMap<String, String> zbDimMap = new HashMap<String, String>();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        DataField mdCode = tmpTable.getMdCode();
        FieldDefine mdFieldDefine = (FieldDefine)mdCode;
        String mdDimName = dataAssist.getDimensionName(mdFieldDefine);
        zbDimMap.put(mdFieldDefine.getCode(), mdDimName);
        DataField period = tmpTable.getPeriod();
        FieldDefine periodFieldDefine = (FieldDefine)period;
        String periodDimName = dataAssist.getDimensionName(periodFieldDefine);
        zbDimMap.put(periodFieldDefine.getCode(), periodDimName);
        List<DataField> dimFields = tmpTable.getDimFields();
        for (DataField dimField : dimFields) {
            FieldDefine dimFieldDefine = (FieldDefine)dimField;
            String dimDimName = dataAssist.getDimensionName(dimFieldDefine);
            zbDimMap.put(dimFieldDefine.getCode(), dimDimName);
        }
        cfg.configItems().put("ZB_DIM_MAPPING", zbDimMap);
        return this.sbImportActuatorFactory.getImportActuator(cfg);
    }

    private void executeImport(TzParams tzParams, ISBImportActuator sbImportActuator, AsyncTaskMonitor monitor) {
        monitor.progressAndMessage(0.0, "\u6570\u636e\u6b63\u5728\u5bfc\u5165");
        DataSchemeTmpTable tmpTable = tzParams.getTmpTable();
        ArrayList<DataField> listField = new ArrayList<DataField>();
        listField.add(tmpTable.getMdCode());
        block0: for (DataFieldDeployInfo dimDeploy : tmpTable.getDimDeploys()) {
            for (DataField dimField : tmpTable.getDimFields()) {
                if (!dimDeploy.getDataFieldKey().equals(dimField.getKey())) continue;
                listField.add(dimField);
                continue block0;
            }
        }
        block2: for (DataFieldDeployInfo tableDimDeploy : tmpTable.getTableDimDeploys()) {
            for (DataField tableDimField : tmpTable.getTableDimFields()) {
                if (!tableDimDeploy.getDataFieldKey().equals(tableDimField.getKey())) continue;
                listField.add(tableDimField);
                continue block2;
            }
        }
        block4: for (DataFieldDeployInfo timePointDeploy : tmpTable.getTimePointDeploys()) {
            for (DataField timePointField : tmpTable.getTimePointFields()) {
                if (!timePointDeploy.getDataFieldKey().equals(timePointField.getKey())) continue;
                listField.add(timePointField);
                continue block4;
            }
        }
        block6: for (DataFieldDeployInfo periodicDeploy : tmpTable.getPeriodicDeploys()) {
            for (DataField periodicField : tmpTable.getPeriodicFields()) {
                if (!periodicDeploy.getDataFieldKey().equals(periodicField.getKey())) continue;
                listField.add(periodicField);
                continue block6;
            }
        }
        sbImportActuator.setDataFields(listField);
        sbImportActuator.prepare();
        ArrayList<String> col = new ArrayList<String>();
        LinkedHashMap<Integer, Integer> colIndexType = new LinkedHashMap<Integer, Integer>();
        colIndexType.put(col.size(), 8);
        for (DataField dataField : listField) {
            String name = dataField.getCode();
            colIndexType.put(col.size(), dataField.getDataFieldType().getValue());
            col.add("T0." + name);
        }
        String select = MessageFormat.format("SELECT {0} FROM {1} T0 ORDER BY {2}", String.join((CharSequence)",", col), tzParams.getSourceData(), "ID");
        this.jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(select, 1003, 1007);
            preparedStatement.setFetchSize(1000);
            preparedStatement.setFetchDirection(1000);
            return preparedStatement;
        }, rs -> {
            ArrayList<Object> dataRow = new ArrayList<Object>();
            for (Integer integer : colIndexType.keySet()) {
                Object val = this.readValue(integer + 1, rs, (Integer)colIndexType.get(integer), listField);
                dataRow.add(val);
            }
            sbImportActuator.put(dataRow);
        });
        sbImportActuator.commit();
    }

    private Object readValue(int dataIndex, ResultSet resultSet, int dataType, List<DataField> listField) throws SQLException {
        switch (dataType) {
            case 1: {
                boolean value = resultSet.getBoolean(dataIndex);
                return resultSet.wasNull() ? null : Boolean.valueOf(value);
            }
            case 2: {
                Date value = resultSet.getDate(dataIndex);
                if (resultSet.wasNull()) {
                    return null;
                }
                Calendar date = Calendar.getInstance();
                date.setTime(value);
                return date;
            }
            case 3: {
                if (resultSet.wasNull()) {
                    return null;
                }
                return resultSet.getDouble(dataIndex);
            }
            case 5: 
            case 8: {
                long value = resultSet.getLong(dataIndex);
                return resultSet.wasNull() ? null : Long.valueOf(value);
            }
            case 6: {
                String value = resultSet.getString(dataIndex);
                return resultSet.wasNull() ? null : value;
            }
            case 9: {
                byte[] value = resultSet.getBytes(dataIndex);
                return resultSet.wasNull() ? null : value;
            }
            case 10: {
                BigDecimal value = resultSet.getBigDecimal(dataIndex);
                if (resultSet.wasNull()) {
                    return null;
                }
                int scale = listField.get(dataIndex - 1).getDecimal();
                value = value.setScale(scale, RoundingMode.HALF_UP);
                return value;
            }
        }
        throw new SQLException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b\uff1a" + dataType);
    }
}

