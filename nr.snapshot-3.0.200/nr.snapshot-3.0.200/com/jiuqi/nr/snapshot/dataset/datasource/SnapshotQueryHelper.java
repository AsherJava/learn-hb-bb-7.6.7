/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.snapshot.dataset.datasource;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.snapshot.dataset.datasource.NrSnapshotDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class SnapshotQueryHelper {
    private JdbcTemplate jdbcTemplate;
    private IRunTimeViewController runTimeViewController;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private static final String MDCODE = "MDCode";
    private static final String DATATIME = "dataTime";

    public SnapshotQueryHelper(JdbcTemplate jdbcTemplate, IRunTimeViewController runTimeViewController, IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.jdbcTemplate = jdbcTemplate;
        this.runTimeViewController = runTimeViewController;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    private String toNRPeriod(String dateStr, String type) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date data = simpleDateFormat.parse(dateStr);
        PeriodType periodType = PeriodUtils.periodOfType((String)type);
        return PeriodUtils.getPeriodFromDate((int)periodType.type(), (Date)data);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ParameterResultset getFirstValue(ParameterModel model, ParameterCalculator calculator, List<String> appointKeyValues) throws ParameterException {
        NrSnapshotDataSourceModel snapshotModel = (NrSnapshotDataSourceModel)model.getDatasource();
        String taskKey = snapshotModel.getTaskKey();
        if (StringUtils.isEmpty((String)taskKey)) {
            throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u4e2d\uff0c\u672a\u9009\u62e9\u4efb\u52a1");
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        HashMap<String, String> dimMap = new HashMap<String, String>();
        try {
            Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            try {
                this.getDimMap(calculator, taskDefine, dimMap);
                if (StringUtils.isEmpty((String)((String)dimMap.get(MDCODE)))) throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u672a\u5173\u8054\u5355\u4f4d\u6216\u65f6\u671f");
                if (StringUtils.isEmpty((String)((String)dimMap.get(DATATIME)))) {
                    throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u672a\u5173\u8054\u5355\u4f4d\u6216\u65f6\u671f");
                }
                if (null == appointKeyValues || appointKeyValues.isEmpty()) {
                    ParameterResultItem lastPeriodItem = new ParameterResultItem((Object)"LAST_PERIOD", "\u4e0a\u671f\u6570");
                    ParameterResultset parameterResultset = new ParameterResultset(lastPeriodItem);
                    return parameterResultset;
                }
                String appointKeyValue = appointKeyValues.get(0);
                if ("LAST_PERIOD".equals(appointKeyValue)) {
                    ParameterResultItem lastPeriodItem = new ParameterResultItem((Object)"LAST_PERIOD", "\u4e0a\u671f\u6570");
                    ParameterResultset parameterResultset = new ParameterResultset(lastPeriodItem);
                    return parameterResultset;
                }
                if ("LASTYEAR_SAMEPERIOD".equals(appointKeyValue)) {
                    ParameterResultItem lastYearItem = new ParameterResultItem((Object)"LASTYEAR_SAMEPERIOD", "\u4e0a\u5e74\u540c\u671f\u6570");
                    ParameterResultset parameterResultset = new ParameterResultset(lastYearItem);
                    return parameterResultset;
                }
                DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                StringBuilder sql = this.getSQLBuilder((String)dimMap.get(MDCODE), (String)dimMap.get(DATATIME), dataScheme);
                sql.append(" and a.").append("ID").append(" = '").append(appointKeyValue).append("'");
                try (PreparedStatement statement = connection.prepareStatement(sql.toString());
                     ResultSet rs = statement.executeQuery();){
                    if (rs.next()) {
                        String code = rs.getString("code");
                        String name = rs.getString("name");
                        ParameterResultItem item = new ParameterResultItem((Object)code, name);
                        ParameterResultset parameterResultset = new ParameterResultset(item);
                        return parameterResultset;
                    }
                }
                ParameterResultset parameterResultset = ParameterResultset.EMPTY_RESULTSET;
                return parameterResultset;
            }
            finally {
                if (null != connection) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
                }
            }
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private void getDimMap(ParameterCalculator calculator, TaskDefine taskDefine, Map<String, String> dimMap) throws ParameterException, ParseException {
        List parameterModels = calculator.getParameterModels();
        for (ParameterModel parameterModel : parameterModels) {
            ParameterResultset value;
            if ("P_MD_ORG".equals(parameterModel.getName()) || "P_MD_ORG".equals(parameterModel.getMessageAlias())) {
                value = calculator.getValue(parameterModel.getName());
                if (value.isEmpty()) continue;
                String MDCode = (String)value.get(0).getValue();
                dimMap.put(MDCODE, MDCode);
                continue;
            }
            if (!"P_DATATIME".equals(parameterModel.getName()) && !"P_DATATIME".equals(parameterModel.getMessageAlias()) || (value = calculator.getValue(parameterModel.getName())).isEmpty()) continue;
            String dataTime = (String)value.get(0).getValue();
            if (dataTime.contains(taskDefine.getDateTime())) {
                dimMap.put(DATATIME, (String)value.get(0).getValue());
                continue;
            }
            dataTime = this.toNRPeriod(dataTime, taskDefine.getDateTime());
            dimMap.put(DATATIME, dataTime);
        }
    }

    @NotNull
    private StringBuilder getSQLBuilder(String MDCode, String dataTime, DataScheme dataScheme) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.").append("ID").append(" as code, a.").append("TITLE").append(" as name from ");
        sql.append("NR_SNAPSHOT_").append(dataScheme.getBizCode()).append(" a left join ").append("NR_SNAPSHOT_REL_").append(dataScheme.getBizCode());
        sql.append(" b on a.").append("ID").append(" = b.").append("SNAPSHOTID");
        sql.append(" where b.").append("MDCODE").append(" = '").append(MDCode).append("'");
        sql.append(" and b.").append("DATATIME").append(" = '").append(dataTime).append("'");
        return sql;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ParameterResultset getFilterValue(ParameterModel model, ParameterCalculator calculator, List<String> appointKeyValues) throws ParameterException {
        NrSnapshotDataSourceModel snapshotModel = (NrSnapshotDataSourceModel)model.getDatasource();
        String taskKey = snapshotModel.getTaskKey();
        if (StringUtils.isEmpty((String)taskKey)) {
            throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u4e2d\uff0c\u672a\u9009\u62e9\u4efb\u52a1");
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        HashMap<String, String> dimMap = new HashMap<String, String>();
        try {
            Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            try {
                this.getDimMap(calculator, taskDefine, dimMap);
                if (StringUtils.isEmpty((String)((String)dimMap.get(MDCODE)))) throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u672a\u5173\u8054\u5355\u4f4d\u6216\u65f6\u671f");
                if (StringUtils.isEmpty((String)((String)dimMap.get(DATATIME)))) {
                    throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u672a\u5173\u8054\u5355\u4f4d\u6216\u65f6\u671f");
                }
                if (null == appointKeyValues || appointKeyValues.isEmpty()) {
                    DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                    StringBuilder sql = this.getSQLBuilder((String)dimMap.get(MDCODE), (String)dimMap.get(DATATIME), dataScheme);
                    ArrayList<ParameterResultItem> result = new ArrayList<ParameterResultItem>();
                    ParameterResultItem lastPeriodItem = new ParameterResultItem((Object)"LAST_PERIOD", "\u4e0a\u671f\u6570");
                    result.add(lastPeriodItem);
                    ParameterResultItem lastYearItem = new ParameterResultItem((Object)"LASTYEAR_SAMEPERIOD", "\u4e0a\u5e74\u540c\u671f\u6570");
                    result.add(lastYearItem);
                    this.getResult(connection, result, sql);
                    ParameterResultset parameterResultset2 = result.size() > 0 ? new ParameterResultset(result) : ParameterResultset.EMPTY_RESULTSET;
                    return parameterResultset2;
                }
                ArrayList<ParameterResultItem> result = new ArrayList<ParameterResultItem>();
                ArrayList<String> snapshotIDs = new ArrayList<String>();
                for (String appointKeyValue : appointKeyValues) {
                    if ("LAST_PERIOD".equals(appointKeyValue)) {
                        ParameterResultItem lastPeriodItem = new ParameterResultItem((Object)"LAST_PERIOD", "\u4e0a\u671f\u6570");
                        result.add(lastPeriodItem);
                        continue;
                    }
                    if ("LASTYEAR_SAMEPERIOD".equals(appointKeyValue)) {
                        ParameterResultItem lastYearItem = new ParameterResultItem((Object)"LASTYEAR_SAMEPERIOD", "\u4e0a\u5e74\u540c\u671f\u6570");
                        result.add(lastYearItem);
                        continue;
                    }
                    snapshotIDs.add(appointKeyValue);
                }
                if (!snapshotIDs.isEmpty()) {
                    DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                    StringBuilder sql = this.getSQLBuilder((String)dimMap.get(MDCODE), (String)dimMap.get(DATATIME), dataScheme);
                    sql.append(" and a.").append("ID").append(" in (");
                    for (String snapshotID : snapshotIDs) {
                        sql.append("'").append(snapshotID).append("',");
                    }
                    sql.delete(sql.length() - 1, sql.length());
                    sql.append(")");
                    this.getResult(connection, result, sql);
                }
                ParameterResultset parameterResultset = result.size() > 0 ? new ParameterResultset(result) : ParameterResultset.EMPTY_RESULTSET;
                return parameterResultset;
            }
            finally {
                if (null != connection) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
                }
            }
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private void getResult(Connection connection, List<ParameterResultItem> result, StringBuilder sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql.toString());
             ResultSet rs = statement.executeQuery();){
            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                ParameterResultItem item = new ParameterResultItem((Object)code, name);
                result.add(item);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ParameterResultset getSearchValue(ParameterModel model, ParameterCalculator calculator, List<String> appointKeyValues, List<String> searchValues) throws ParameterException {
        NrSnapshotDataSourceModel snapshotModel = (NrSnapshotDataSourceModel)model.getDatasource();
        String taskKey = snapshotModel.getTaskKey();
        if (StringUtils.isEmpty((String)taskKey)) {
            throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u4e2d\uff0c\u672a\u9009\u62e9\u4efb\u52a1");
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        HashMap<String, String> dimMap = new HashMap<String, String>();
        try {
            Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            try {
                Iterator<String> sql2;
                ArrayList<String> snapshotIDs;
                ArrayList<ParameterResultItem> result;
                this.getDimMap(calculator, taskDefine, dimMap);
                if (StringUtils.isEmpty((String)((String)dimMap.get(MDCODE)))) throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u672a\u5173\u8054\u5355\u4f4d\u6216\u65f6\u671f");
                if (StringUtils.isEmpty((String)((String)dimMap.get(DATATIME)))) {
                    throw new ParameterException("\u62a5\u8868\u5feb\u7167\u7684\u53c2\u6570\u672a\u5173\u8054\u5355\u4f4d\u6216\u65f6\u671f");
                }
                if (null != appointKeyValues && !appointKeyValues.isEmpty()) {
                    result = new ArrayList<ParameterResultItem>();
                    snapshotIDs = new ArrayList<String>();
                    sql2 = appointKeyValues.iterator();
                } else {
                    Object lastYearItem;
                    ArrayList<ParameterResultItem> result2 = new ArrayList<ParameterResultItem>();
                    for (String searchValue : searchValues) {
                        if ("\u4e0a\u671f\u6570".contains(searchValue)) {
                            ParameterResultItem lastPeriodItem = new ParameterResultItem((Object)"LAST_PERIOD", "\u4e0a\u671f\u6570");
                            result2.add(lastPeriodItem);
                            continue;
                        }
                        if (!"\u4e0a\u5e74\u540c\u671f\u6570".contains(searchValue)) continue;
                        lastYearItem = new ParameterResultItem((Object)"LASTYEAR_SAMEPERIOD", "\u4e0a\u5e74\u540c\u671f\u6570");
                        result2.add((ParameterResultItem)lastYearItem);
                    }
                    DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                    StringBuilder sql2 = this.getSQLBuilder((String)dimMap.get(MDCODE), (String)dimMap.get(DATATIME), dataScheme);
                    sql2.append(" and (");
                    for (String searchValue : searchValues) {
                        sql2.append("a.").append("TITLE").append(" like '%").append(searchValue).append("%' ").append("or ");
                    }
                    sql2.delete(sql2.length() - 4, sql2.length());
                    sql2.append(")");
                    this.getResult(connection, result2, sql2);
                    lastYearItem = result2.size() > 0 ? new ParameterResultset(result2) : ParameterResultset.EMPTY_RESULTSET;
                    return lastYearItem;
                }
                block8: while (sql2.hasNext()) {
                    String appointKeyValue = sql2.next();
                    if ("LAST_PERIOD".equals(appointKeyValue)) {
                        for (String searchValue : searchValues) {
                            if (!"\u4e0a\u671f\u6570".contains(searchValue)) continue;
                            ParameterResultItem lastPeriodItem = new ParameterResultItem((Object)"LAST_PERIOD", "\u4e0a\u671f\u6570");
                            result.add(lastPeriodItem);
                            continue block8;
                        }
                        continue;
                    }
                    if ("LASTYEAR_SAMEPERIOD".equals(appointKeyValue)) {
                        for (String searchValue : searchValues) {
                            if (!"\u4e0a\u5e74\u540c\u671f\u6570".contains(searchValue)) continue;
                            ParameterResultItem lastYearItem = new ParameterResultItem((Object)"LASTYEAR_SAMEPERIOD", "\u4e0a\u5e74\u540c\u671f\u6570");
                            result.add(lastYearItem);
                            continue block8;
                        }
                        continue;
                    }
                    snapshotIDs.add(appointKeyValue);
                }
                if (!snapshotIDs.isEmpty()) {
                    DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                    StringBuilder sql3 = this.getSQLBuilder((String)dimMap.get(MDCODE), (String)dimMap.get(DATATIME), dataScheme);
                    sql3.append(" and a.").append("ID").append(" in (");
                    for (String snapshotID : snapshotIDs) {
                        sql3.append("'").append(snapshotID).append("',");
                    }
                    sql3.delete(sql3.length() - 1, sql3.length());
                    sql3.append(")");
                    sql3.append(" and (");
                    for (String searchValue : searchValues) {
                        sql3.append("a.").append("TITLE").append(" like '%").append(searchValue).append("%' ").append("or ");
                    }
                    sql3.delete(sql3.length() - 4, sql3.length());
                    sql3.append(")");
                    this.getResult(connection, result, sql3);
                }
                ParameterResultset parameterResultset = result.size() > 0 ? new ParameterResultset(result) : ParameterResultset.EMPTY_RESULTSET;
                return parameterResultset;
            }
            finally {
                if (null != connection) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
                }
            }
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), (Throwable)e);
        }
    }
}

