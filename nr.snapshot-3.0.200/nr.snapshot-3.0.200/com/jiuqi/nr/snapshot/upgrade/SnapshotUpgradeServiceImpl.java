/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.io.common.ExtConstants
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.snapshot.impl.DataInitUtil
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.snapshot.upgrade;

import com.csvreader.CsvWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.snapshot.deploy.SnapshotTableUtils;
import com.jiuqi.nr.snapshot.impl.DataInitUtil;
import com.jiuqi.nr.snapshot.input.UpgradeContext;
import com.jiuqi.nr.snapshot.message.SnapshotFileInfo;
import com.jiuqi.nr.snapshot.message.UpgradeInfo;
import com.jiuqi.nr.snapshot.message.VertionData;
import com.jiuqi.nr.snapshot.output.PeriodObj;
import com.jiuqi.nr.snapshot.output.TaskObj;
import com.jiuqi.nr.snapshot.upgrade.SnapshotUpgradeService;
import com.jiuqi.nr.snapshot.upgrade.SnapshotUpgradeTableUtil;
import com.jiuqi.nr.snapshot.utils.SnapshotFileTool;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.sql.DataSource;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class SnapshotUpgradeServiceImpl
implements SnapshotUpgradeService {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotUpgradeServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TaskObj> getUpgradeTasks() {
        List taskDefines = this.runTimeViewController.getAllTaskDefines();
        ArrayList<TaskObj> taskObjs = new ArrayList<TaskObj>();
        if (taskDefines != null) {
            Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            try {
                for (TaskDefine taskDefine : taskDefines) {
                    List<String> taskDatatime;
                    String isOpen;
                    boolean canWrite;
                    boolean canRead = this.definitionAuthorityProvider.canReadTask(taskDefine.getKey());
                    if (!canRead || !(canWrite = this.definitionAuthorityProvider.canWriteTask(taskDefine.getKey())) || "0".equals(isOpen = this.taskOptionController.getValue(taskDefine.getKey(), "DATA_VERSION")) || (taskDatatime = this.getTaskDatatime(connection, taskDefine.getKey())).isEmpty()) continue;
                    TaskObj taskObj = new TaskObj();
                    taskObj.setKey(taskDefine.getKey());
                    taskObj.setTitle(taskDefine.getTitle());
                    taskObj.setDataSchemeKey(taskDefine.getDataScheme());
                    if (1 == taskDatatime.size()) {
                        taskObj.setFromPeriod(taskDatatime.get(0));
                        taskObj.setToPeriod(taskDatatime.get(0));
                    } else {
                        String fromPeriod = taskDatatime.get(0);
                        String toPeriod = taskDatatime.get(0);
                        for (int i = 0; i < taskDatatime.size() - 1; ++i) {
                            for (int j = i + 1; j < taskDatatime.size(); ++j) {
                                if (PeriodUtils.comparePeriod((String)fromPeriod, (String)taskDatatime.get(j)) >= 0) {
                                    fromPeriod = taskDatatime.get(j);
                                }
                                if (PeriodUtils.comparePeriod((String)toPeriod, (String)taskDatatime.get(j)) > 0) continue;
                                toPeriod = taskDatatime.get(j);
                            }
                        }
                        taskObj.setFromPeriod(fromPeriod);
                        taskObj.setToPeriod(toPeriod);
                    }
                    IPeriodEntity iPeriodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime());
                    PeriodObj periodObj = new PeriodObj();
                    PeriodType periodType = iPeriodEntity.getPeriodType();
                    if (periodType == PeriodType.CUSTOM) {
                        periodObj.setPeriodType(periodType.type());
                        periodObj.setDefaultPeriod(false);
                        List iPeriodRowList = this.periodEngineService.getPeriodAdapter().getPeriodProvider(iPeriodEntity.getKey()).getPeriodItems();
                        periodObj.setCustomPeriodDataList(iPeriodRowList);
                    } else {
                        periodObj.setPeriodType(periodType.type());
                        periodObj.setDefaultPeriod(true);
                    }
                    taskObj.setPeriodObj(periodObj);
                    taskObjs.add(taskObj);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                if (null != connection) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
                }
            }
        }
        return taskObjs;
    }

    private List<String> getTaskDatatime(Connection connection, String taskKey) {
        SnapshotUpgradeTableUtil snapshotUpgradeTableUtil = new SnapshotUpgradeTableUtil();
        boolean haveUpgradeTable = snapshotUpgradeTableUtil.haveTable();
        if (!haveUpgradeTable) {
            snapshotUpgradeTableUtil.doObserver();
        }
        ArrayList<String> taskDatatime = new ArrayList<String>();
        try {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                StringBuilder sql = new StringBuilder();
                sql.append("select DISTINCT t1.").append(PERIOD_FIELD).append(" as datatime ");
                sql.append("from ").append("SYS_VER_REL_").append(formSchemeDefine.getFormSchemeCode()).append(" t1 ");
                sql.append("where (select count(1) as num from ").append("NR_SNAPSHOT_UP").append(" t2 ");
                sql.append("where t1.").append(PERIOD_FIELD).append(" = t2.").append("DATATIME");
                sql.append(" and t2.").append("TASKKEY").append(" = '").append(taskKey).append("' and ");
                sql.append("t2.").append("FORMSCHEMEKEY").append(" = '").append(formSchemeDefine.getKey()).append("') = 0");
                try {
                    PreparedStatement statement = connection.prepareStatement(sql.toString());
                    Throwable throwable = null;
                    try {
                        ResultSet rs = statement.executeQuery();
                        Throwable throwable2 = null;
                        try {
                            while (rs.next()) {
                                taskDatatime.add(rs.getString("datatime"));
                            }
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (rs == null) continue;
                            if (throwable2 != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            rs.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (statement == null) continue;
                        if (throwable != null) {
                            try {
                                statement.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        statement.close();
                    }
                }
                catch (SQLException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return taskDatatime;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doUpgrade(UpgradeContext upgradeContext, AsyncTaskMonitor monitor) {
        double progress = 0.2;
        monitor.progressAndMessage(progress, "");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(upgradeContext.getTaskKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
        SnapshotUpgradeTableUtil snapshotUpgradeTableUtil = new SnapshotUpgradeTableUtil();
        try {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            double step = 0.75 / (double)formSchemeDefines.size();
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                if (null != formSchemeDefine.getToPeriod() && PeriodUtils.comparePeriod((String)upgradeContext.getFromPeriod(), (String)formSchemeDefine.getToPeriod()) > 0) {
                    monitor.progressAndMessage(progress += step, "");
                    continue;
                }
                if (null == formSchemeDefine.getToPeriod()) {
                    IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
                    String[] periodCodeRegion = periodProvider.getPeriodCodeRegion();
                    if (PeriodUtils.comparePeriod((String)upgradeContext.getFromPeriod(), (String)periodCodeRegion[1]) > 0) {
                        monitor.progressAndMessage(progress += step, "");
                        continue;
                    }
                }
                List formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeDefine.getKey());
                Map<String, List<UpgradeInfo>> formKeyUpgradeInfoMap = this.constructUpgradeInfoConditions(formKeys);
                ArrayList periods = PeriodUtil.getPeriodCodes((String)upgradeContext.getFromPeriod(), (String)upgradeContext.getToPeriod(), (PeriodType)formSchemeDefine.getPeriodType());
                boolean haveUpgradeTable = snapshotUpgradeTableUtil.haveTable();
                if (!haveUpgradeTable) {
                    snapshotUpgradeTableUtil.doObserver();
                } else {
                    List<String> upgradeperiods = this.getUpgradeStatus(connection, upgradeContext.getTaskKey(), formSchemeDefine.getKey(), periods);
                    periods.removeAll(upgradeperiods);
                    if (periods.isEmpty()) continue;
                }
                List<VertionData> vertionDatas = this.searchVersionData(connection, DataInitUtil.getSysVersionTableName((FormSchemeDefine)formSchemeDefine), DataInitUtil.getSysVersionRelationTableName((FormSchemeDefine)formSchemeDefine), periods);
                for (VertionData vertionData : vertionDatas) {
                    String snapshotFileKey = this.getSnapshotFileKey(formKeyUpgradeInfoMap, vertionData.getVersionId());
                    if (!StringUtils.isNotEmpty((String)snapshotFileKey)) continue;
                    TableModelDefine snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
                    TableModelDefine snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
                    if (null == snapshotTable || null == snapshotRelTable) {
                        SnapshotTableUtils snapshotTableUtils = new SnapshotTableUtils();
                        snapshotTableUtils.doObserver(dataScheme);
                        snapshotTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_" + dataScheme.getBizCode());
                        snapshotRelTable = this.dataModelService.getTableModelDefineByName("NR_SNAPSHOT_REL_" + dataScheme.getBizCode());
                    }
                    DataAccessContext context = new DataAccessContext(this.dataModelService);
                    if (null == vertionData.getVersionId() || null == vertionData.getTitle() || null == vertionData.getDw() || null == vertionData.getPeriod()) continue;
                    String id = UUID.randomUUID().toString();
                    this.insertSnapshotInfoTable(snapshotTable, context, id, vertionData, taskDefine.getKey(), formSchemeDefine.getKey());
                    this.insertSnapshotRelTable(snapshotRelTable, context, id, snapshotFileKey, vertionData);
                }
                this.insertUpgradeTable(connection, upgradeContext.getTaskKey(), formSchemeDefine.getKey(), periods);
                monitor.progressAndMessage(progress += step, "");
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (null != connection) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
            monitor.finish("success", null);
        }
    }

    private List<String> getUpgradeStatus(Connection connection, String taskKey, String formSchemeKey, List<String> periods) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append("DATATIME");
        sql.append(" from ").append("NR_SNAPSHOT_UP");
        sql.append(" where ").append("TASKKEY").append(" = '").append(taskKey).append("' and ");
        sql.append("FORMSCHEMEKEY").append(" = '").append(formSchemeKey).append("' and ");
        sql.append("DATATIME").append(" in (");
        for (String period : periods) {
            sql.append("'").append(period).append("'").append(", ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(")");
        ArrayList<String> upgradePeriods = new ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql.toString());
             ResultSet rs = statement.executeQuery();){
            while (rs.next()) {
                upgradePeriods.add(rs.getString("DATATIME"));
            }
        }
        catch (SQLException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return upgradePeriods;
    }

    private void insertUpgradeTable(Connection connection, String taskKey, String formSchemeKey, List<String> periods) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append("NR_SNAPSHOT_UP");
        sql.append("(").append("ID").append(", ");
        sql.append("TASKKEY").append(", ");
        sql.append("FORMSCHEMEKEY").append(", ");
        sql.append("DATATIME").append(") ");
        sql.append("values (").append("?").append(",").append("?").append(",").append("?").append(",").append("?").append(")");
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (String period : periods) {
            Object[] objects = new Object[4];
            String id = UUID.randomUUID().toString();
            objects[0] = id;
            objects[1] = taskKey;
            objects[2] = formSchemeKey;
            objects[3] = period;
            batchValues.add(objects);
        }
        try {
            DataEngineUtil.batchUpdate((Connection)connection, (String)sql.toString(), batchValues);
        }
        catch (SQLException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private Map<String, List<UpgradeInfo>> constructUpgradeInfoConditions(List<String> formKeys) {
        HashMap<String, List<UpgradeInfo>> formKeyUpgradeInfoMap = new HashMap<String, List<UpgradeInfo>>();
        for (String formKey : formKeys) {
            List dataRegions = this.runTimeViewController.getAllRegionsInForm(formKey);
            ArrayList<UpgradeInfo> upgradeInfos = new ArrayList<UpgradeInfo>();
            try {
                for (DataRegionDefine dataRegion : dataRegions) {
                    UpgradeInfo upgradeInfo = new UpgradeInfo();
                    upgradeInfo.setDataRegionDefine(dataRegion);
                    List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(dataRegion.getKey());
                    if (null != fieldKeys && !fieldKeys.isEmpty()) {
                        FieldDefine bizKeyFieldDefine;
                        List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)fieldKeys);
                        TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(((FieldDefine)fieldDefines.get(0)).getOwnerTableKey());
                        String[] bizKeyFieldsID = tableDefine.getBizKeyFieldsID();
                        boolean haveBizKey = false;
                        for (String fieldKey : bizKeyFieldsID) {
                            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                            if ("BIZKEYORDER".equals(fieldDefine.getCode())) {
                                haveBizKey = true;
                            }
                            upgradeInfo.getFieldCodes().add(fieldDefine.getCode());
                            upgradeInfo.getDataTableKeys().add(fieldDefine.getOwnerTableKey());
                        }
                        FieldDefine floatOrderFieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", ((FieldDefine)fieldDefines.get(0)).getOwnerTableKey());
                        if (null != floatOrderFieldDefine) {
                            upgradeInfo.getFieldCodes().add(floatOrderFieldDefine.getCode());
                            upgradeInfo.getDataTableKeys().add(floatOrderFieldDefine.getOwnerTableKey());
                        }
                        if (!haveBizKey && null != (bizKeyFieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("BIZKEYORDER", ((FieldDefine)fieldDefines.get(0)).getOwnerTableKey()))) {
                            upgradeInfo.getFieldCodes().add(bizKeyFieldDefine.getCode());
                            upgradeInfo.getDataTableKeys().add(bizKeyFieldDefine.getOwnerTableKey());
                        }
                        for (FieldDefine fieldDefine : fieldDefines) {
                            upgradeInfo.getFieldCodes().add(fieldDefine.getCode());
                            upgradeInfo.getDataTableKeys().add(fieldDefine.getOwnerTableKey());
                        }
                    }
                    upgradeInfos.add(upgradeInfo);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            formKeyUpgradeInfoMap.put(formKey, upgradeInfos);
        }
        return formKeyUpgradeInfoMap;
    }

    private List<VertionData> searchVersionData(Connection connection, String verTableName, String verRelTableName, List<String> periods) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t1.").append("VERSIONID").append(", ");
        sql.append("t1.").append("TITLE").append(", ");
        sql.append("t1.").append("DESCRIBE_").append(", ");
        sql.append("t1.").append("CREATTIME").append(", ");
        sql.append("t1.").append("CREATUSER").append(", ");
        sql.append("t1.").append("ISAUTOCREATED").append(", ");
        sql.append("t2.").append(DW_FIELD).append(", ");
        sql.append("t2.").append(PERIOD_FIELD).append(" ");
        sql.append("from ").append(verTableName).append(" t1 ");
        sql.append("left join ").append(verRelTableName).append(" t2 ");
        sql.append("on t1.").append("VERSIONID").append(" = t2.").append("VERSIONID").append(" ");
        sql.append("where t2.").append(PERIOD_FIELD).append(" in (");
        for (String period : periods) {
            sql.append("'").append(period).append("'").append(", ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(")");
        ArrayList<VertionData> vertionDatas = new ArrayList<VertionData>();
        try (PreparedStatement statement = connection.prepareStatement(sql.toString());
             ResultSet rs = statement.executeQuery();){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rs.next()) {
                VertionData vertionData = new VertionData();
                vertionData.setVersionId(rs.getString("VERSIONID"));
                vertionData.setTitle(rs.getString("TITLE"));
                vertionData.setDescribe(rs.getString("DESCRIBE_"));
                Date date = null;
                String dataStr = rs.getString("CREATTIME");
                if (StringUtils.isNotEmpty((String)dataStr)) {
                    date = dateFormat.parse(dataStr);
                }
                vertionData.setCreatTime(date);
                vertionData.setCreatUser(rs.getString("CREATUSER"));
                vertionData.setDw(rs.getString(DW_FIELD));
                vertionData.setPeriod(rs.getString(PERIOD_FIELD));
                vertionData.setIsAutoCreate(rs.getString("ISAUTOCREATED"));
                vertionDatas.add(vertionData);
            }
        }
        catch (SQLException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return vertionDatas;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getSnapshotFileKey(Map<String, List<UpgradeInfo>> formKeyUpgradeInfoMap, String versionId) {
        List<UpgradeInfo> upgradeInfos = this.getUpgradeInfos(formKeyUpgradeInfoMap, versionId);
        String snapshotFileKey = "";
        String path = this.createSnapshotFile(upgradeInfos);
        File file = null;
        SnapshotFileTool snapshotFileTool = null;
        try {
            file = new File(FilenameUtils.normalize(path));
            try (FileInputStream fileStream = new FileInputStream(file);){
                snapshotFileTool = new SnapshotFileTool();
                SnapshotFileInfo snapshotFileInfo = snapshotFileTool.upload(fileStream);
                snapshotFileKey = snapshotFileInfo.getKey();
            }
            catch (FileNotFoundException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (null != file && !file.delete()) {
                logger.error("\u5feb\u7167\u4e34\u65f6\u6587\u4ef6\u672a\u5220\u9664\u6210\u529f");
            }
            if (null != snapshotFileTool) {
                try {
                    snapshotFileTool.close();
                }
                catch (ObjectStorageException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        return snapshotFileKey;
    }

    @NotNull
    private List<UpgradeInfo> getUpgradeInfos(Map<String, List<UpgradeInfo>> formKeyUpgradeInfoMap, String versionId) {
        ArrayList<UpgradeInfo> allUpgradeInfos = new ArrayList<UpgradeInfo>();
        Set<String> formKeys = formKeyUpgradeInfoMap.keySet();
        for (String formKey : formKeys) {
            List<UpgradeInfo> upgradeInfos = formKeyUpgradeInfoMap.get(formKey);
            for (UpgradeInfo upgradeInfo : upgradeInfos) {
                upgradeInfo.getDatass().clear();
            }
            allUpgradeInfos.addAll(upgradeInfos);
            FileInfo dataVerFileInfo = this.fileInfoService.getFileInfo(versionId + formKey, "DataVer", FileStatus.AVAILABLE);
            if (null == dataVerFileInfo) continue;
            byte[] bs = this.fileService.area("DataVer").download(dataVerFileInfo.getKey());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String result = null;
            try {
                out.write(bs);
                result = new String(out.toByteArray());
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (result == null) continue;
            List formList = new ArrayList();
            try {
                formList = (List)this.objectMapper.readValue(result, Object.class);
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            for (UpgradeInfo upgradeInfo : upgradeInfos) {
                List<String> dataTableKeys = upgradeInfo.getDataTableKeys();
                for (int k = 0; k < dataTableKeys.size(); ++k) {
                    Object object;
                    Map o;
                    List maps = null;
                    Iterator iterator = formList.iterator();
                    while (iterator.hasNext() && null == (maps = (List)(o = (Map)(object = iterator.next())).get(dataTableKeys.get(k)))) {
                    }
                    ArrayList<String> datas = new ArrayList<String>();
                    for (LinkedHashMap map : maps) {
                        Object dataObject = map.get(upgradeInfo.getFieldCodes().get(k));
                        datas.add(null == dataObject ? null : (String)dataObject);
                    }
                    upgradeInfo.getDatass().add(datas);
                }
            }
        }
        return allUpgradeInfos;
    }

    private void insertSnapshotInfoTable(TableModelDefine snapshotTable, DataAccessContext context, String id, VertionData vertionData, String taskKey, String formSchemeKey) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List snapshotFields = this.dataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (ColumnModelDefine snapshotField : snapshotFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(snapshotField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List columns = queryModel.getColumns();
        try {
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            for (int i = 0; i < columns.size(); ++i) {
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("ID")) {
                    iNvwaDataRow.setValue(i, (Object)id);
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("TITLE")) {
                    iNvwaDataRow.setValue(i, (Object)vertionData.getTitle());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("DESCRIBTION")) {
                    iNvwaDataRow.setValue(i, (Object)vertionData.getDescribe());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("CREATTIME")) {
                    iNvwaDataRow.setValue(i, (Object)vertionData.getCreatTime());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("CREATUSERID")) {
                    iNvwaDataRow.setValue(i, (Object)vertionData.getCreatUser());
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("CREATUSERNAME")) {
                    SystemUser systemUser;
                    String userName = "";
                    User user = this.userService.get(vertionData.getCreatUser());
                    userName = null != user ? user.getFullname() : (null == (systemUser = (SystemUser)this.systemUserService.get(vertionData.getCreatUser())) ? "" : (systemUser.getFullname() == null ? systemUser.getNickname() : systemUser.getFullname()));
                    iNvwaDataRow.setValue(i, (Object)userName);
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("TASKKEY")) {
                    iNvwaDataRow.setValue(i, (Object)taskKey);
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FORMSCHEMEKEY")) {
                    iNvwaDataRow.setValue(i, (Object)formSchemeKey);
                    continue;
                }
                if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("ISAUTOCREATED")) continue;
                iNvwaDataRow.setValue(i, (Object)vertionData.getIsAutoCreate());
            }
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private void insertSnapshotRelTable(TableModelDefine snapshotRelTable, DataAccessContext context, String id, String snapshotFileKey, VertionData vertionData) {
        NvwaQueryModel relQueryModel = new NvwaQueryModel();
        List snapshotRelFields = this.dataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
        for (ColumnModelDefine snapshotRelField : snapshotRelFields) {
            relQueryModel.getColumns().add(new NvwaQueryColumn(snapshotRelField));
        }
        INvwaUpdatableDataAccess relUpdatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(relQueryModel);
        List relColumns = relQueryModel.getColumns();
        try {
            INvwaDataUpdator iNvwaDataUpdator = relUpdatableDataAccess.openForUpdate(context);
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            for (int i = 0; i < relColumns.size(); ++i) {
                if (((NvwaQueryColumn)relColumns.get(i)).getColumnModel().getCode().equals("SNAPSHOTID")) {
                    iNvwaDataRow.setValue(i, (Object)id);
                    continue;
                }
                if (((NvwaQueryColumn)relColumns.get(i)).getColumnModel().getCode().equals("SSFILEKEY")) {
                    iNvwaDataRow.setValue(i, (Object)snapshotFileKey);
                    continue;
                }
                if (((NvwaQueryColumn)relColumns.get(i)).getColumnModel().getCode().equals(DW_FIELD)) {
                    iNvwaDataRow.setValue(i, (Object)vertionData.getDw());
                    continue;
                }
                if (((NvwaQueryColumn)relColumns.get(i)).getColumnModel().getCode().equals("DATATIME")) {
                    iNvwaDataRow.setValue(i, (Object)vertionData.getPeriod());
                    continue;
                }
                if (!((NvwaQueryColumn)relColumns.get(i)).getColumnModel().getCode().equals("ADJUST")) continue;
                iNvwaDataRow.setValue(i, (Object)"0");
            }
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String createSnapshotFile(List<UpgradeInfo> allUpgradeInfos) {
        String path = ExtConstants.ROOTPATH + "/" + UUID.randomUUID().toString() + File.separator + "SnapshotDatas.zip";
        CsvWriter csvWriter = null;
        try {
            File file = FileUtil.createIfNotExists((String)path);
            try (FileOutputStream fos = new FileOutputStream(file);
                 ZipOutputStream zipos = new ZipOutputStream(fos);){
                zipos.setMethod(8);
                csvWriter = new CsvWriter((OutputStream)zipos, new Character(',').charValue(), Charset.forName("UTF-8"));
                this.initZipFile(allUpgradeInfos, zipos, csvWriter);
            }
            catch (Exception e) {
                logger.info("\u6587\u4ef6\u521b\u5efa\u51fa\u9519{}".concat(e.getMessage()));
            }
        }
        catch (IOException e) {
            logger.info("\u6587\u4ef6\u521b\u5efa\u51fa\u9519{}".concat(e.getMessage()));
        }
        finally {
            if (null != csvWriter) {
                csvWriter.close();
            }
        }
        return path;
    }

    /*
     * WARNING - void declaration
     */
    private void initZipFile(List<UpgradeInfo> allUpgradeInfos, ZipOutputStream zipos, CsvWriter csvWriter) {
        for (UpgradeInfo allUpgradeInfo : allUpgradeInfos) {
            if (null == allUpgradeInfo.getFieldCodes() || allUpgradeInfo.getFieldCodes().isEmpty()) continue;
            String fileName = "";
            try {
                ArrayList<String> fieldCode = new ArrayList<String>();
                fieldCode.add("SnapshotGatherFlag");
                fieldCode.add("SnapshotGatherGroupKey");
                fieldCode.add("isFilledRow");
                HashMap<String, String> keyCode = new HashMap<String, String>();
                List<String> dataTableKeys = allUpgradeInfo.getDataTableKeys();
                for (String dataTableKey : dataTableKeys) {
                    String code = (String)keyCode.get(dataTableKey);
                    if (!StringUtils.isEmpty((String)code)) continue;
                    DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
                    keyCode.put(dataTableKey, dataTable.getCode());
                }
                List<String> fieldCodes = allUpgradeInfo.getFieldCodes();
                for (int i = 0; i < fieldCodes.size(); ++i) {
                    String tableCode = (String)keyCode.get(dataTableKeys.get(i));
                    fieldCode.add(tableCode + "." + fieldCodes.get(i));
                }
                DataRegionDefine dataRegionDefine = allUpgradeInfo.getDataRegionDefine();
                FormDefine formDefine = this.runTimeViewController.queryFormById(dataRegionDefine.getFormKey());
                fileName = formDefine.getFormCode();
                if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_ROW_LIST)) {
                    fileName = fileName + "_F" + dataRegionDefine.getRegionTop();
                }
                fileName = fileName + ".csv";
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipos.putNextEntry(zipEntry);
                String[] fieldDefineArray = new String[fieldCode.size()];
                for (int i = 0; i < fieldCode.size(); ++i) {
                    fieldDefineArray[i] = (String)fieldCode.get(i);
                }
                csvWriter.writeRecord(fieldDefineArray);
                ArrayList rowsDatas = new ArrayList();
                List<List<String>> datass = allUpgradeInfo.getDatass();
                if (!datass.isEmpty()) {
                    void var18_23;
                    int size = datass.get(0).size();
                    boolean bl = false;
                    while (var18_23 < size) {
                        ArrayList<String> rowsData = new ArrayList<String>();
                        rowsData.add("-1");
                        rowsData.add("-1");
                        rowsData.add("0");
                        for (int j = 0; j < fieldCodes.size(); ++j) {
                            rowsData.add(datass.get(j).get((int)var18_23));
                        }
                        rowsDatas.add(rowsData);
                        ++var18_23;
                    }
                }
                for (List list : rowsDatas) {
                    if (list.size() == 0) continue;
                    String[] dataArray = new String[list.size()];
                    list.toArray(dataArray);
                    csvWriter.writeRecord(dataArray);
                }
                csvWriter.flush();
                zipos.closeEntry();
            }
            catch (IOException e) {
                logger.info("\u5199\u6570\u636e\u51fa\u9519{}", e);
            }
        }
    }
}

