/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.SortField
 *  com.jiuqi.bi.database.sql.model.fields.RankField
 *  com.jiuqi.bi.database.sql.model.fields.SQLField
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageCommonParam
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.TempTableActuator
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$NoAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.datastatus.facade.obj.RollbackStatusPar
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.datastatus.internal.impl.DataStatusServiceImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.io.tz.listener.ChangeInfo
 *  com.jiuqi.nr.io.tz.listener.ColumnData
 *  com.jiuqi.nr.io.tz.listener.DataRecord
 *  com.jiuqi.nr.io.tz.listener.TzDataChangeListener
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.dataengine.util.DataEngineUtil
 *  com.jiuqi.nvwa.dataengine.util.DataValueUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.RankField;
import com.jiuqi.bi.database.sql.model.fields.SQLField;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageCommonParam;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.TempTableActuator;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.internal.service.util.AccountRollBackTableUtil;
import com.jiuqi.nr.dataentry.paramInfo.AccountRollBackParam;
import com.jiuqi.nr.dataentry.paramInfo.AsyncTaskLog;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.RollbackAccountDataResult;
import com.jiuqi.nr.dataentry.service.IBatchRollbackAccountDataService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.datastatus.facade.obj.RollbackStatusPar;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.impl.DataStatusServiceImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.io.tz.listener.ChangeInfo;
import com.jiuqi.nr.io.tz.listener.ColumnData;
import com.jiuqi.nr.io.tz.listener.DataRecord;
import com.jiuqi.nr.io.tz.listener.TzDataChangeListener;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.dataengine.util.DataEngineUtil;
import com.jiuqi.nvwa.dataengine.util.DataValueUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class BatchRollbackAccountDataService
implements IBatchRollbackAccountDataService {
    private static final Logger logger = LoggerFactory.getLogger(BatchRollbackAccountDataService.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private FormGroupProvider formGroupProvider;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AccountRollBackTableUtil accountRollBackTableUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired(required=false)
    private List<TzDataChangeListener> dataChangeListener;
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Lazy
    @Autowired
    private MessageCommonParam messageCommonParam;
    private static final String MODIFYTIME = "MODIFYTIME";
    private static final String VALIDDATATIME = "VALIDDATATIME";
    private static final String INVALIDDATATIME = "INVALIDDATATIME";
    private static final String SBID = "SBID";
    private static final String BIZKEYORDER = "BIZKEYORDER";
    private static final ThreadLocal<Map<String, String>> dimFields = ThreadLocal.withInitial(() -> new HashMap());
    private static List<String> uploadMsg = Arrays.asList("\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91", "\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91", "\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91", "\u586b\u62a5\u65f6\u95f4\u5df2\u7ed3\u675f");
    private RowMapper<String> dataTimeRowMapper = (rs, rowNum) -> {
        String dataTime = rs.getString(1);
        return dataTime;
    };
    private static final RowMapper<DimensionValueSet> ROW_MAPPER = (rs, rowNum) -> {
        Map<String, String> dimensionFieldsMap = dimFields.get();
        DimensionValueSet rowKey = new DimensionValueSet();
        for (Map.Entry<String, String> dimMap : dimensionFieldsMap.entrySet()) {
            rowKey.setValue(dimMap.getKey(), (Object)rs.getString(dimMap.getValue()));
        }
        return rowKey;
    };

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void rollbackAccountData(AccountRollBackParam accountRollBackParam) throws Exception {
        Assert.notNull((Object)accountRollBackParam.getFormKeys(), "formKey must not be null!");
        JtableContext jtableContext = accountRollBackParam.getContext();
        String actTableName = this.accountRollBackTableUtil.getAccTableName(jtableContext.getFormKey());
        if (!StringUtils.hasLength(actTableName)) {
            logger.debug("\u672a\u627e\u5230\u53f0\u8d26\u4fe1\u606f\u8868\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff01");
            return;
        }
        String hisTableName = this.accountRollBackTableUtil.getAccHiTableName(actTableName);
        String rptTableName = this.accountRollBackTableUtil.getAccRptTableName(actTableName);
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
        }
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        Map dimensionSet = jtableContext.getDimensionSet();
        String periodCode = ((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).getValue();
        String entityCode = ((DimensionValue)dimensionSet.get(targetEntityInfo.getDimensionName())).getValue();
        String maxPeriod = this.getCurrentPeriod(actTableName, com.jiuqi.nr.jtable.util.DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext));
        PeriodWrapper currentPeriod = new PeriodWrapper(maxPeriod);
        PeriodWrapper startPeriod = new PeriodWrapper(periodCode);
        ArrayList periodList = PeriodUtil.getPeiodStrList((PeriodWrapper)startPeriod, (PeriodWrapper)currentPeriod);
        StringBuffer msg = new StringBuffer();
        for (String period : periodList) {
            ((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).setValue(period);
            jtableContext.setDimensionSet(dimensionSet);
            String formKeyStr = accountRollBackParam.getFormKeys();
            List<String> formKeys = Arrays.asList(formKeyStr.split(";"));
            AccessFormParam accessFormParam = new AccessFormParam();
            accessFormParam.setCollectionMasterKey(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)dimensionSet, (String)jtableContext.getFormSchemeKey()));
            accessFormParam.setTaskKey(jtableContext.getTaskKey());
            accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
            accessFormParam.setFormKeys(formKeys);
            accessFormParam.getIgnoreAccessItems().add("accountData");
            accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
            DimensionAccessFormInfo accessFormInfo = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
            List accessInfos = accessFormInfo.getAccessForms();
            Optional noAccess = accessFormInfo.getNoAccessForms().stream().findFirst();
            if (!CollectionUtils.isEmpty(accessInfos) || !noAccess.isPresent()) continue;
            String reson = ((DimensionAccessFormInfo.NoAccessFormInfo)noAccess.get()).getReason();
            if (uploadMsg.contains(reson)) {
                DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
                ActionStateBean uploadState = this.batchQueryUploadStateService.queryUploadState(masterKey, jtableContext.getFormKey(), jtableContext.getFormGroupKey(), formScheme);
                msg.append(AccountRollBackTableUtil.periodToString(new PeriodWrapper(period)).concat(String.format("\u6570\u636e%s\uff0c\u4e0d\u53ef\u56de\u6eda\uff01", uploadState.getTitile())));
            } else {
                msg.append(AccountRollBackTableUtil.periodToString(new PeriodWrapper(period)).concat(reson).concat("\uff0c\u4e0d\u53ef\u56de\u6eda\uff01"));
            }
            logger.error(msg.toString());
            return;
        }
        ((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).setValue(periodCode);
        DimensionValueSet masterKey = com.jiuqi.nr.jtable.util.DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        Map res = this.batchQueryUploadStateService.queryUploadAfterState(masterKey, maxPeriod, formScheme);
        if (!CollectionUtils.isEmpty(res)) {
            DimensionValueSet dim = (DimensionValueSet)res.keySet().stream().findFirst().get();
            ActionStateBean uploadState = (ActionStateBean)res.get(dim);
            String period = String.valueOf(dim.getValue("DATATIME"));
            msg.setLength(0);
            msg.append(AccountRollBackTableUtil.periodToString(new PeriodWrapper(period)).concat(String.format("\u6570\u636e%s\uff0c\u4e0d\u53ef\u56de\u6eda\uff01", uploadState.getTitile())));
            logger.error(msg.toString());
            return;
        }
        List<ColumnModelDefine> columns = this.findActColumns(actTableName);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            ChangeInfo changeInfo = new ChangeInfo();
            DataTable dataTable = this.accountRollBackTableUtil.getAcctDataTable(actTableName);
            if (dataTable != null) {
                List<DataField> dataFields = this.accountRollBackTableUtil.getAllDataFields(dataTable.getKey());
                changeInfo.setFieldMap(dataFields.stream().collect(Collectors.toMap(Basic::getKey, Function.identity(), (o, n) -> n)));
            }
            changeInfo.setTable(dataTable);
            logger.debug("\u67e5\u8be2\u6700\u8fd1\u5386\u53f2\u7248\u672c\u6570\u636e".concat("-").concat(hisTableName).concat("-").concat(masterKey.toString()));
            Map<String, List<String>> rollBackIds = this.rollBackRecordList(masterKey, hisTableName);
            if (CollectionUtils.isEmpty((Collection)rollBackIds.get(SBID)) || CollectionUtils.isEmpty((Collection)rollBackIds.get(BIZKEYORDER))) {
                List<DataRecord> deleteReocrds = this.queryDeleteData(actTableName, masterKey);
                changeInfo.setDeleteRecords(deleteReocrds);
                this.updateActTableEmptyHis(actTableName, rptTableName, masterKey);
                this.delOldHisLaterData(masterKey, hisTableName);
                this.onchangeListener(changeInfo);
                logger.debug("\u65e0\u5386\u53f2\u7248\u672c\u6570\u636e\uff0c\u76f4\u63a5\u56de\u6eda\uff01");
                RollbackStatusPar rollbackStatusPar = new RollbackStatusPar();
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                for (Map.Entry e : dimensionSet.entrySet()) {
                    dimensionCombinationBuilder.setValue(((DimensionValue)e.getValue()).getName(), (Object)((DimensionValue)e.getValue()).getValue());
                }
                rollbackStatusPar.setRollbackDim(dimensionCombinationBuilder.getCombination());
                rollbackStatusPar.setFormKey(jtableContext.getFormKey());
                rollbackStatusPar.setFormSchemeKey(formScheme.getKey());
                this.dataStatusService.rollbackDataStatus(rollbackStatusPar);
                return;
            }
            logger.debug("\u66f4\u65b0\u4fe1\u606f\u8868\u6570\u636e".concat("-").concat(actTableName).concat("-").concat(masterKey.toString()));
            List<DataRecord> newRecords = this.queryInsertData(rollBackIds.get(BIZKEYORDER), hisTableName, columns, connection);
            changeInfo.setInsertRecords(newRecords);
            List<DataRecord> deleteRecords = this.queryDeleteData(actTableName, masterKey);
            changeInfo.setDeleteRecords(deleteRecords);
            this.updateActTable(masterKey, rollBackIds.get(SBID), rollBackIds.get(BIZKEYORDER), actTableName, hisTableName, columns, connection);
            logger.debug("\u5220\u9664\u5386\u53f2\u8868\u540e\u7eed\u5386\u53f2\u7248\u672c\u6570\u636e".concat("-").concat(hisTableName).concat("-").concat(masterKey.toString()));
            this.delHisLaterData(masterKey, rollBackIds.get(BIZKEYORDER), hisTableName, connection);
            logger.debug("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u6570\u636e".concat("-").concat(rptTableName).concat("-").concat(masterKey.toString()));
            this.delRptDataTimeData(masterKey, rptTableName);
            this.onchangeListener(changeInfo);
        }
        catch (Exception e) {
            String message = String.format("\u5355\u4f4d\uff1a%s\uff0c\u65f6\u671f\uff1a%s\uff0c\u62a5\u8868\uff1a%s", entityCode, periodCode, accountRollBackParam.getFormKeys());
            logger.error("\u56de\u6eda\u5931\u8d25\uff01".concat(message));
            throw e;
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        RollbackStatusPar rollbackStatusPar = new RollbackStatusPar();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        for (Map.Entry e : dimensionSet.entrySet()) {
            dimensionCombinationBuilder.setValue(((DimensionValue)e.getValue()).getName(), (Object)((DimensionValue)e.getValue()).getValue());
        }
        rollbackStatusPar.setRollbackDim(dimensionCombinationBuilder.getCombination());
        rollbackStatusPar.setFormKey(jtableContext.getFormKey());
        rollbackStatusPar.setFormSchemeKey(formScheme.getKey());
        this.dataStatusService.rollbackDataStatus(rollbackStatusPar);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void asyncRollbackAccountData(AccountRollBackParam accountRollBackParam, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        if (asyncTaskMonitor.isCancel()) {
            asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
            LogHelper.info((String)"\u53f0\u8d26\u6570\u636e\u56de\u6eda", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
            return;
        }
        boolean chinese = DataEntryUtil.isChinese();
        String accountDataRollBackStart = this.i18nMessage(chinese, "\u53f0\u8d26\u8868\u6570\u636e\u56de\u6eda\u5f00\u59cb");
        String paramAnalysis = this.i18nMessage(chinese, "\u6b63\u5728\u8fdb\u884c\u53c2\u6570\u5206\u6790");
        String nofindAccountData = this.i18nMessage(chinese, "\u672a\u627e\u5230\u53f0\u8d26\u4fe1\u606f\u8868\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff01");
        String rollBackSuccess = this.i18nMessage(chinese, "\u56de\u6eda\u6210\u529f\uff01");
        String rollBackFail = this.i18nMessage(chinese, "\u56de\u6eda\u5931\u8d25\uff01");
        Assert.notNull((Object)accountRollBackParam.getFormKeys(), "formKey must not be null!");
        asyncTaskMonitor.progressAndMessage(0.1, accountDataRollBackStart);
        JtableContext jtableContext = accountRollBackParam.getContext();
        AsyncTaskLog asyncTaskLog = new AsyncTaskLog();
        asyncTaskMonitor.progressAndMessage(0.2, paramAnalysis);
        String actTableName = this.accountRollBackTableUtil.getAccTableName(jtableContext.getFormKey());
        if (!StringUtils.hasLength(actTableName)) {
            logger.error("\u672a\u627e\u5230\u53f0\u8d26\u4fe1\u606f\u8868\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff01");
            rollBackFail = chinese ? "\u672a\u627e\u5230\u53f0\u8d26\u4fe1\u606f\u8868\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff01" : "Ledger information table not found, please check the parameters!";
            asyncTaskMonitor.error(rollBackFail, null, nofindAccountData);
        }
        String hisTableName = this.accountRollBackTableUtil.getAccHiTableName(actTableName);
        String rptTableName = this.accountRollBackTableUtil.getAccRptTableName(actTableName);
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
        }
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        Map dimensionSet = jtableContext.getDimensionSet();
        String periodCode = ((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).getValue();
        String entityCode = ((DimensionValue)dimensionSet.get(targetEntityInfo.getDimensionName())).getValue();
        String maxPeriod = this.getCurrentPeriod(actTableName, com.jiuqi.nr.jtable.util.DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext));
        PeriodWrapper currentPeriod = new PeriodWrapper(maxPeriod);
        String authJudge = this.i18nMessage(chinese, "\u6b63\u5728\u8fdb\u884c\u6743\u9650\u5224\u65ad");
        asyncTaskMonitor.progressAndMessage(0.25, authJudge);
        PeriodWrapper startPeriod = new PeriodWrapper(periodCode);
        ArrayList periodList = PeriodUtil.getPeiodStrList((PeriodWrapper)startPeriod, (PeriodWrapper)currentPeriod);
        StringBuffer msg = new StringBuffer();
        for (String period : periodList) {
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                LogHelper.info((String)"\u53f0\u8d26\u6570\u636e\u56de\u6eda", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                return;
            }
            ((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).setValue(period);
            jtableContext.setDimensionSet(dimensionSet);
            String formKeyStr = accountRollBackParam.getFormKeys();
            List<String> formKeys = Arrays.asList(formKeyStr.split(";"));
            AccessFormParam accessFormParam = new AccessFormParam();
            accessFormParam.setCollectionMasterKey(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)dimensionSet, (String)jtableContext.getFormSchemeKey()));
            accessFormParam.setTaskKey(jtableContext.getTaskKey());
            accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
            accessFormParam.setFormKeys(formKeys);
            accessFormParam.getIgnoreAccessItems().add("accountData");
            accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
            DimensionAccessFormInfo batchDimensionValueFormInfo = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
            List accessInfos = batchDimensionValueFormInfo.getAccessForms();
            Optional noAccess = batchDimensionValueFormInfo.getNoAccessForms().stream().findFirst();
            if (!CollectionUtils.isEmpty(accessInfos) || !noAccess.isPresent()) continue;
            String reson = ((DimensionAccessFormInfo.NoAccessFormInfo)noAccess.get()).getReason();
            if (uploadMsg.contains(reson)) {
                DimensionValueSet masterKey = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
                ActionStateBean uploadState = this.batchQueryUploadStateService.queryUploadState(masterKey, jtableContext.getFormKey(), jtableContext.getFormGroupKey(), formScheme);
                String uploadTitle = uploadState.getTitile(formScheme.getKey());
                msg.append(this.messageCommonParam.date(formScheme.getKey(), period).concat(chinese ? String.format("\u6570\u636e%s\uff0c\u4e0d\u53ef\u56de\u6eda\uff01", uploadTitle) : String.format("data %s\uff0cnon-rollback\uff01", uploadTitle)));
            } else {
                msg.append(this.messageCommonParam.date(formScheme.getKey(), period).concat(chinese ? "\u65e0\u6743\u9650!" : "no authority\uff01").concat(chinese ? "\u4e0d\u53ef\u56de\u6eda\uff01" : "non-rollback\uff01"));
            }
            asyncTaskLog.setMessage(msg.toString());
            logger.error(msg.toString());
            asyncTaskMonitor.error(msg.toString(), null, JsonUtil.objectToJson((Object)asyncTaskLog));
            return;
        }
        ((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).setValue(periodCode);
        DimensionValueSet masterKey = com.jiuqi.nr.jtable.util.DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        Map res = this.batchQueryUploadStateService.queryUploadAfterState(masterKey, periodCode, formScheme);
        if (!CollectionUtils.isEmpty(res)) {
            DimensionValueSet dim = (DimensionValueSet)res.keySet().stream().findFirst().get();
            ActionStateBean uploadState = (ActionStateBean)res.get(dim);
            Object period = dim.getValue("DATATIME");
            msg.setLength(0);
            String uploadTitle = uploadState.getTitile(formScheme.getKey());
            msg.append(this.messageCommonParam.date(formScheme.getKey(), period.toString()).concat(chinese ? String.format("\u6570\u636e%s\uff0c\u4e0d\u53ef\u56de\u6eda\uff01", uploadTitle) : String.format(" data %s\uff0cnon-rollback\uff01", uploadTitle)));
            asyncTaskLog.setMessage(msg.toString());
            logger.error(msg.toString());
            asyncTaskMonitor.error(msg.toString(), null, JsonUtil.objectToJson((Object)asyncTaskLog));
            return;
        }
        String paramQuery = this.i18nMessage(chinese, "\u6b63\u5728\u8fdb\u884c\u53c2\u6570\u67e5\u8be2");
        asyncTaskMonitor.progressAndMessage(0.3, paramQuery);
        List<ColumnModelDefine> columns = this.findActColumns(actTableName);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            ChangeInfo changeInfo = new ChangeInfo();
            DataTable dataTable = this.accountRollBackTableUtil.getAcctDataTable(actTableName);
            if (dataTable != null) {
                List<DataField> dataFields = this.accountRollBackTableUtil.getAllDataFields(dataTable.getKey());
                changeInfo.setFieldMap(dataFields.stream().collect(Collectors.toMap(Basic::getKey, Function.identity(), (o, n) -> n)));
            }
            changeInfo.setTable(dataTable);
            String historyVersionQuery = this.i18nMessage(chinese, "\u6b63\u5728\u8fdb\u884c\u5386\u53f2\u7248\u672c\u67e5\u8be2");
            asyncTaskMonitor.progressAndMessage(0.4, historyVersionQuery);
            logger.debug("\u67e5\u8be2\u6700\u8fd1\u5386\u53f2\u7248\u672c\u6570\u636e".concat("-").concat(hisTableName).concat("-").concat(masterKey.toString()));
            Map<String, List<String>> rollBackIds = this.rollBackRecordList(masterKey, hisTableName);
            if (CollectionUtils.isEmpty((Collection)rollBackIds.get(SBID)) || CollectionUtils.isEmpty((Collection)rollBackIds.get(BIZKEYORDER))) {
                List<DataRecord> deleteReocrds = this.queryDeleteData(actTableName, masterKey);
                changeInfo.setDeleteRecords(deleteReocrds);
                this.updateActTableEmptyHis(actTableName, rptTableName, masterKey);
                this.delOldHisLaterData(masterKey, hisTableName);
                logger.debug("\u65e0\u5386\u53f2\u7248\u672c\u6570\u636e\uff0c\u76f4\u63a5\u56de\u6eda\uff01");
                if (!asyncTaskMonitor.isFinish()) {
                    asyncTaskMonitor.finish(rollBackSuccess, (Object)JsonUtil.objectToJson((Object)asyncTaskLog));
                }
                this.onchangeListener(changeInfo);
                RollbackStatusPar rollbackStatusPar = new RollbackStatusPar();
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                for (Map.Entry e : dimensionSet.entrySet()) {
                    dimensionCombinationBuilder.setValue(((DimensionValue)e.getValue()).getName(), (Object)((DimensionValue)e.getValue()).getValue());
                }
                rollbackStatusPar.setRollbackDim(dimensionCombinationBuilder.getCombination());
                rollbackStatusPar.setFormKey(jtableContext.getFormKey());
                rollbackStatusPar.setFormSchemeKey(formScheme.getKey());
                this.dataStatusService.rollbackDataStatus(rollbackStatusPar);
                return;
            }
            String messageDataUpdate = this.i18nMessage(chinese, "\u6b63\u5728\u8fdb\u884c\u4fe1\u606f\u8868\u6570\u636e\u66f4\u65b0");
            asyncTaskMonitor.progressAndMessage(0.8, messageDataUpdate);
            logger.debug("\u66f4\u65b0\u4fe1\u606f\u8868\u6570\u636e".concat("-").concat(actTableName).concat("-").concat(masterKey.toString()));
            List<DataRecord> deleteReocrds = this.queryDeleteData(actTableName, masterKey);
            changeInfo.setDeleteRecords(deleteReocrds);
            List<DataRecord> newRecords = this.queryInsertData(rollBackIds.get(BIZKEYORDER), hisTableName, columns, connection);
            changeInfo.setInsertRecords(newRecords);
            this.updateActTable(masterKey, rollBackIds.get(SBID), rollBackIds.get(BIZKEYORDER), actTableName, hisTableName, columns, connection);
            String messageDataClear = this.i18nMessage(chinese, "\u6b63\u5728\u8fdb\u884c\u5386\u53f2\u7248\u672c\u6570\u636e\u6e05\u9664");
            asyncTaskMonitor.progressAndMessage(0.6, messageDataClear);
            logger.debug("\u5220\u9664\u5386\u53f2\u8868\u540e\u7eed\u5386\u53f2\u7248\u672c\u6570\u636e".concat("-").concat(hisTableName).concat("-").concat(masterKey.toString()));
            this.delHisLaterData(masterKey, rollBackIds.get(BIZKEYORDER), hisTableName, connection);
            String accountDataClear = this.i18nMessage(chinese, "\u6b63\u5728\u8fdb\u884c\u53f0\u8d26\u62a5\u8868\u6570\u636e\u6e05\u9664");
            asyncTaskMonitor.progressAndMessage(1.0, accountDataClear);
            logger.debug("\u5220\u9664\u53f0\u8d26\u62a5\u8868\u6570\u636e".concat("-").concat(rptTableName).concat("-").concat(masterKey.toString()));
            this.delRptDataTimeData(masterKey, rptTableName);
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish(rollBackSuccess, (Object)JsonUtil.objectToJson((Object)asyncTaskLog));
            }
            this.onchangeListener(changeInfo);
        }
        catch (Exception e) {
            String message = String.format("\u56de\u6eda\u5931\u8d25\uff01\u5355\u4f4d\uff1a%s\uff0c\u65f6\u671f\uff1a%s\uff0c\u62a5\u8868\uff1a%s", entityCode, periodCode, accountRollBackParam.getFormKeys());
            asyncTaskLog.setMessage(message);
            rollBackFail = chinese ? "\u540e\u53f0\u5f02\u5e38\uff0c\u56de\u6eda\u5931\u8d25\uff01" : "Background exception, rollback failed!";
            asyncTaskMonitor.error(rollBackFail, (Throwable)e, JsonUtil.objectToJson((Object)asyncTaskLog));
            throw e;
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        RollbackStatusPar rollbackStatusPar = new RollbackStatusPar();
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        for (Map.Entry e : dimensionSet.entrySet()) {
            dimensionCombinationBuilder.setValue(((DimensionValue)e.getValue()).getName(), (Object)((DimensionValue)e.getValue()).getValue());
        }
        rollbackStatusPar.setRollbackDim(dimensionCombinationBuilder.getCombination());
        rollbackStatusPar.setFormKey(jtableContext.getFormKey());
        rollbackStatusPar.setFormSchemeKey(formScheme.getKey());
        this.dataStatusService.rollbackDataStatus(rollbackStatusPar);
    }

    private void onchangeListener(ChangeInfo changeInfo) {
        if (CollectionUtils.isEmpty(this.dataChangeListener)) {
            logger.warn("\u65e0\u56de\u6eda\u540e\u76d1\u542c...");
            return;
        }
        for (TzDataChangeListener tzDataChangeListener : this.dataChangeListener) {
            if (tzDataChangeListener instanceof DataStatusServiceImpl) continue;
            tzDataChangeListener.onDataChange(changeInfo);
        }
    }

    private List<String> periodList(DimensionValueSet masterKey, String rptTable) {
        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT DISTINCT DATATIME FROM ").append(rptTable).append(" WHERE ");
        DimensionSet dimesions = masterKey.getDimensionSet();
        boolean addFlag = false;
        int index = 0;
        Object[] args = new Object[dimesions.size() - 1];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimension = dimesions.get(i);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimension);
            if (addFlag) {
                querySql.append(" AND ");
            }
            if (dimension.equals("DATATIME")) continue;
            querySql.append(dimensionField).append("=?");
            args[index] = masterKey.getValue(dimension);
            addFlag = true;
            ++index;
        }
        List res = this.jdbcTemplate.query(querySql.toString(), this.dataTimeRowMapper, args);
        return res;
    }

    private String getCurrentPeriod(String actTable, DimensionValueSet masterKey) {
        StringBuffer selectSql = new StringBuffer();
        selectSql.append("SELECT MAX(").append(VALIDDATATIME).append(")").append(" FROM ");
        selectSql.append(actTable).append(" WHERE ");
        DimensionSet dimesions = masterKey.getDimensionSet();
        Object[] args = new Object[dimesions.size() - 1];
        boolean addFlag = false;
        int index = 0;
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimension = dimesions.get(i);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimension);
            if (dimension.equals("DATATIME")) continue;
            if (addFlag) {
                selectSql.append(" AND ");
            }
            selectSql.append(dimensionField).append("=?");
            args[index] = masterKey.getValue(dimension);
            ++index;
            addFlag = true;
        }
        String maxPeriod = (String)this.jdbcTemplate.queryForObject(selectSql.toString(), String.class, args);
        return maxPeriod;
    }

    public void delHisLaterData(DimensionValueSet masterKey, List<String> bizKeys, String tableName, Connection connection) throws Exception {
        StringBuilder delSql = new StringBuilder();
        delSql.append("DELETE FROM ");
        delSql.append(tableName).append(" t1 ").append(" WHERE ");
        DimensionSet dimesions = masterKey.getDimensionSet();
        boolean addFlag = false;
        ArrayList<Object> args = new ArrayList<Object>();
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimension = dimesions.get(i);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimension);
            if (addFlag) {
                delSql.append(" AND ");
            }
            if (dimension.equals("DATATIME")) {
                delSql.append("t1.").append(VALIDDATATIME).append(">=?");
            } else {
                delSql.append("t1.").append(dimensionField).append("=?");
            }
            args.add(masterKey.getValue(dimension));
            addFlag = true;
        }
        delSql.append(" OR ");
        try (ITempTable oneKeyTempTable = TempTableActuator.getOneKeyTempTable((Connection)connection);){
            IDatabase database = DatabaseInstance.getDatabase();
            if (bizKeys.size() >= DataEngineUtil.getMaxInSize((IDatabase)database)) {
                delSql.append(" t1.BIZKEYORDER in  ").append("(Select TEMP_KEY From ").append(oneKeyTempTable.getTableName()).append(" ) ");
                ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
                for (String filterValue : bizKeys) {
                    Object resultValue = DataValueUtils.formatData((int)ColumnModelType.STRING.getValue(), (Object)filterValue);
                    Object[] batchArray = new Object[]{resultValue};
                    batchValues.add(batchArray);
                }
                oneKeyTempTable.insertRecords(batchValues);
            } else {
                if (bizKeys.size() == 1) {
                    delSql.append("t1.BIZKEYORDER").append("=? ");
                } else {
                    delSql.append("t1.BIZKEYORDER").append(" in(");
                    for (String ignored : bizKeys) {
                        delSql.append("?,");
                    }
                    delSql.setLength(delSql.length() - 1);
                    delSql.append(")");
                }
                args.addAll(bizKeys);
            }
            this.jdbcTemplate.update(delSql.toString(), args.toArray());
        }
    }

    public void delOldHisLaterData(DimensionValueSet masterKey, String tableName) {
        StringBuffer delSql = new StringBuffer();
        delSql.append("DELETE FROM ");
        delSql.append(tableName).append(" WHERE ");
        DimensionSet dimesions = masterKey.getDimensionSet();
        boolean addFlag = false;
        int index = 0;
        Object[] args = new Object[dimesions.size()];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimension = dimesions.get(i);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimension);
            if (addFlag) {
                delSql.append(" AND ");
            }
            if (dimension.equals("DATATIME")) {
                delSql.append(VALIDDATATIME).append(">=?");
            } else {
                delSql.append(dimensionField).append("=?");
            }
            args[index] = masterKey.getValue(dimension);
            addFlag = true;
            ++index;
        }
        this.jdbcTemplate.update(delSql.toString(), args);
    }

    private void updateActTableEmptyHis(String actTable, String rptTable, DimensionValueSet masterKey) {
        StringBuffer updateSql = new StringBuffer();
        updateSql.append("DELETE FROM ").append(actTable);
        updateSql.append(" WHERE ");
        DimensionSet dimesions = masterKey.getDimensionSet();
        boolean addFlag = false;
        Object[] args = new Object[dimesions.size()];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimName = dimesions.get(i);
            Object mastValue = masterKey.getValue(dimName);
            if (addFlag) {
                updateSql.append(" AND ");
            }
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimName);
            if (dimName.equalsIgnoreCase("DATATIME")) {
                updateSql.append(VALIDDATATIME).append(" > ?");
            } else {
                updateSql.append(dimensionField).append("=?");
            }
            args[i] = mastValue;
            addFlag = true;
        }
        if (!dimesions.contains("DATATIME")) {
            throw new RuntimeException("\u672a\u627e\u5230\u65f6\u671f\u7ef4\u5ea6\uff01");
        }
        this.jdbcTemplate.update(updateSql.toString(), args);
        if (Objects.nonNull(rptTable)) {
            String delSql = updateSql.toString().replace(actTable, rptTable).replace(VALIDDATATIME, "DATATIME");
            this.jdbcTemplate.update(delSql, args);
        }
    }

    private void updateActTable(DimensionValueSet masterKey, List<String> sbIds, final List<String> bizKeys, String actTable, String hisActTable, List<ColumnModelDefine> columns, Connection connection) throws Exception {
        this.updateActTableEmptyHis(actTable, null, masterKey);
        StringBuilder insertSql = new StringBuilder();
        StringBuilder selectFields = new StringBuilder();
        insertSql.append("INSERT INTO ");
        insertSql.append(actTable).append(" (");
        boolean addFlag = false;
        for (ColumnModelDefine columnModelDefine : columns) {
            if (addFlag) {
                insertSql.append(",");
                selectFields.append(",");
            }
            insertSql.append(columnModelDefine.getName());
            selectFields.append("t1.").append(columnModelDefine.getName());
            addFlag = true;
        }
        insertSql.append(") ");
        insertSql.append(" select ").append((CharSequence)selectFields).append(" FROM ");
        insertSql.append(hisActTable).append(" t1 ").append(" where ");
        Throwable throwable = null;
        try (ITempTable oneKeyTempTable = TempTableActuator.getOneKeyTempTable((Connection)connection);){
            IDatabase database = DatabaseInstance.getDatabase();
            if (bizKeys.size() >= DataEngineUtil.getMaxInSize((IDatabase)database)) {
                insertSql.append(" exists ").append("(Select 1 From ").append(oneKeyTempTable.getTableName()).append(" where ").append("TEMP_KEY").append("=").append("t1.BIZKEYORDER").append(")");
                ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
                for (String filterValue : bizKeys) {
                    Object resultValue = DataValueUtils.formatData((int)ColumnModelType.STRING.getValue(), (Object)filterValue);
                    Object[] batchArray = new Object[]{resultValue};
                    batchValues.add(batchArray);
                }
                oneKeyTempTable.insertRecords(batchValues);
                this.jdbcTemplate.update(insertSql.toString());
            } else {
                if (bizKeys.size() == 1) {
                    insertSql.append("t1.BIZKEYORDER").append("=? ");
                } else {
                    insertSql.append("t1.BIZKEYORDER").append(" in(");
                    for (String ignored : bizKeys) {
                        insertSql.append("?,");
                    }
                    insertSql.setLength(insertSql.length() - 1);
                    insertSql.append(")");
                }
                this.jdbcTemplate.update(insertSql.toString(), bizKeys.toArray());
            }
        }
        catch (Throwable throwable2) {
            Throwable throwable3 = throwable2;
            throw throwable2;
        }
        String updateSql = "update " + actTable + " set " + MODIFYTIME + " = ? where " + BIZKEYORDER + " = ?";
        final Timestamp timestamp = Timestamp.from(Instant.now());
        this.jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setTimestamp(1, timestamp);
                ps.setString(2, (String)bizKeys.get(i));
            }

            public int getBatchSize() {
                return bizKeys.size();
            }
        });
    }

    private List<DataRecord> queryDeleteData(String actTable, DimensionValueSet masterKey) {
        ArrayList<DataRecord> records = new ArrayList<DataRecord>();
        StringBuffer updateSql = new StringBuffer();
        updateSql.append("SELECT * FROM ").append(actTable);
        updateSql.append(" WHERE ");
        DimensionSet dimesions = masterKey.getDimensionSet();
        boolean addFlag = false;
        Object[] args = new Object[dimesions.size()];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimName = dimesions.get(i);
            Object mastValue = masterKey.getValue(dimName);
            if (addFlag) {
                updateSql.append(" AND ");
            }
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimName);
            if (dimName.equalsIgnoreCase("DATATIME")) {
                updateSql.append(VALIDDATATIME).append(" > ?");
            } else {
                updateSql.append(dimensionField).append("=?");
            }
            args[i] = mastValue;
            addFlag = true;
        }
        if (!dimesions.contains("DATATIME")) {
            throw new RuntimeException("\u672a\u627e\u5230\u65f6\u671f\u7ef4\u5ea6\uff01");
        }
        List res = this.jdbcTemplate.queryForList(updateSql.toString(), args);
        for (Map re : res) {
            DataRecord dataRecord = new DataRecord();
            HashMap columnDataMap = new HashMap();
            re.forEach((k, v) -> {
                ColumnData columnData = new ColumnData();
                columnData.setOldValue(v);
                columnDataMap.put(k, columnData);
            });
            dataRecord.setColumnData(columnDataMap);
            records.add(dataRecord);
        }
        return records;
    }

    private List<DataRecord> queryInsertData(List<String> bizKeys, String hisActTable, List<ColumnModelDefine> columns, Connection connection) throws Exception {
        List res;
        ArrayList<DataRecord> records = new ArrayList<DataRecord>();
        StringBuilder selectSql = new StringBuilder();
        StringBuilder selectFields = new StringBuilder();
        boolean addFlag = false;
        for (ColumnModelDefine column : columns) {
            if (addFlag) {
                selectFields.append(",");
            }
            selectFields.append("t1.").append(column.getName());
            addFlag = true;
        }
        selectSql.append(" select ").append((CharSequence)selectFields).append(" FROM ");
        selectSql.append(hisActTable).append(" t1 ").append(" where ");
        try (ITempTable oneKeyTempTable = TempTableActuator.getOneKeyTempTable((Connection)connection);){
            IDatabase database = DatabaseInstance.getDatabase();
            if (bizKeys.size() >= DataEngineUtil.getMaxInSize((IDatabase)database)) {
                selectSql.append(" exists ").append("(Select 1 From ").append(oneKeyTempTable.getTableName()).append(" where ").append("TEMP_KEY").append("=").append("t1.BIZKEYORDER").append(")");
                ArrayList batchValues = new ArrayList();
                for (String filterValue : bizKeys) {
                    Object resultValue = DataValueUtils.formatData((int)ColumnModelType.STRING.getValue(), (Object)filterValue);
                    Object[] batchArray = new Object[]{resultValue};
                    batchValues.add(batchArray);
                }
                oneKeyTempTable.insertRecords((List)batchValues);
                res = this.jdbcTemplate.queryForList(selectSql.toString());
            } else {
                if (bizKeys.size() == 1) {
                    selectSql.append("t1.BIZKEYORDER").append("=? ");
                } else {
                    selectSql.append("t1.BIZKEYORDER").append(" in(");
                    for (String ignored : bizKeys) {
                        selectSql.append("?,");
                    }
                    selectSql.setLength(selectSql.length() - 1);
                    selectSql.append(")");
                }
                res = this.jdbcTemplate.queryForList(selectSql.toString(), bizKeys.toArray());
            }
        }
        for (Map re : res) {
            DataRecord dataRecord = new DataRecord();
            HashMap columnDataMap = new HashMap();
            re.forEach((k, v) -> {
                ColumnData columnData = new ColumnData();
                columnData.setValue(v);
                columnDataMap.put(k, columnData);
            });
            dataRecord.setColumnData(columnDataMap);
            records.add(dataRecord);
        }
        return records;
    }

    private void dropTmpTable(String actTable) {
        StringBuffer delSql = new StringBuffer();
        delSql.append("DROP TABLE ").append(actTable.concat("_TMP"));
        this.jdbcTemplate.update(delSql.toString());
    }

    private void deleteAct(List<String> sbIds, String actTable) {
        StringBuffer updateSql = new StringBuffer();
        updateSql.append("DELETE FROM ").append(actTable);
        updateSql.append(" WHERE ").append(SBID).append(" IN (");
        Object[] args = new Object[sbIds.size()];
        for (int index = 0; index < sbIds.size(); ++index) {
            if (index > 0) {
                updateSql.append(",");
            }
            updateSql.append("?");
            args[index] = sbIds.get(index);
        }
        updateSql.append(")");
        this.jdbcTemplate.update(updateSql.toString(), args);
    }

    private void delRptDataTimeData(DimensionValueSet masterKey, String rptTable) {
        StringBuffer updateSql = new StringBuffer();
        updateSql.append("DELETE FROM ").append(rptTable);
        updateSql.append(" WHERE ");
        DimensionSet dimesions = masterKey.getDimensionSet();
        boolean addFlag = false;
        Object[] args = new Object[dimesions.size()];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimName = dimesions.get(i);
            Object mastValue = masterKey.getValue(dimName);
            if (addFlag) {
                updateSql.append(" AND ");
            }
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimName);
            if (dimName.equalsIgnoreCase("DATATIME")) {
                updateSql.append("DATATIME").append(" > ?");
            } else {
                updateSql.append(dimensionField).append("=?");
            }
            args[i] = mastValue;
            addFlag = true;
        }
        if (!dimesions.contains("DATATIME")) {
            throw new RuntimeException("\u672a\u627e\u5230\u65f6\u671f\u7ef4\u5ea6\uff01");
        }
        this.jdbcTemplate.update(updateSql.toString(), args);
    }

    private void backHisData(DimensionValueSet masterKey, String actTable, String hisActTable, List<ColumnModelDefine> columns) {
        StringBuffer sql = new StringBuffer("CREATE TABLE ");
        sql.append(actTable.concat("_TMP"));
        StringBuffer selectSql = new StringBuffer();
        StringBuffer selectFields = new StringBuffer();
        boolean addFlag = false;
        for (ColumnModelDefine column : columns) {
            if (addFlag) {
                selectFields.append(",");
            }
            selectFields.append(column.getName());
            addFlag = true;
        }
        selectSql.append(" SELECT ").append(selectFields);
        selectSql.append(" FROM ").append(hisActTable);
        selectSql.append(" WHERE ");
        addFlag = false;
        DimensionSet dimesions = masterKey.getDimensionSet();
        Object[] args = new Object[dimesions.size()];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimension = dimesions.get(i);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimension);
            if (addFlag) {
                selectSql.append(" AND ");
            }
            if (dimension.equals("DATATIME")) {
                selectSql.append(" ").append(VALIDDATATIME).append("<= ?");
            } else {
                selectSql.append(dimensionField).append("=?");
            }
            args[i] = masterKey.getValue(dimension);
            addFlag = true;
        }
        selectSql.append(" GROUP BY SBID ORDER BY VALIDDATATIME DESC ");
        sql.append(selectSql.toString());
        this.jdbcTemplate.update(sql.toString(), args);
    }

    private Map<String, List<String>> rollBackRecordList(DimensionValueSet masterKey, String tableName) throws Exception {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ").append("SQ.").append(SBID).append(",").append("SQ.").append(BIZKEYORDER);
        StringBuffer sqlWhere = new StringBuffer();
        DimensionSet dimesions = masterKey.getDimensionSet();
        boolean addFlag = false;
        int index = 0;
        Object[] args = new Object[dimesions.size() + 1];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimension = dimesions.get(i);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimension);
            if (addFlag) {
                sqlWhere.append(" AND ");
            }
            if (dimension.equals("DATATIME")) {
                sqlWhere.append(" T.").append(VALIDDATATIME).append("<= ?").append(" AND ");
                sqlWhere.append(" T.").append(INVALIDDATATIME).append("> ?");
                args[index] = masterKey.getValue(dimension);
                ++index;
            } else {
                sqlWhere.append("T.").append(dimensionField).append("=?");
            }
            args[index] = masterKey.getValue(dimension);
            addFlag = true;
            ++index;
        }
        sql.append(" FROM ( ");
        SimpleTable table = new SimpleTable(tableName, "T");
        RankField rankField = new RankField((ISQLTable)table, "NUM");
        IDatabase dataBase = this.getDatabase();
        SQLField sqlField = new SQLField((ISQLTable)table, VALIDDATATIME);
        SortField sortFields = new SortField((ISQLTable)table, (ISQLField)sqlField, 1);
        SQLField sbField = new SQLField((ISQLTable)table, SBID);
        rankField.orderFields().add(sortFields);
        rankField.partitionFields().add(sbField);
        String rankSql = rankField.toSQL(dataBase, 0);
        sql.append(" SELECT ").append(SBID).append(",").append(BIZKEYORDER).append(",").append(rankSql);
        sql.append(" FROM ").append(tableName).append(" T ").append(" WHERE ").append(sqlWhere);
        sql.append(" ) SQ WHERE SQ.NUM < 2");
        List res = this.jdbcTemplate.queryForList(sql.toString(), args);
        ArrayList<String> sbIds = new ArrayList<String>();
        ArrayList<String> bizKeys = new ArrayList<String>();
        for (Map re : res) {
            sbIds.add(re.get(SBID).toString());
            bizKeys.add(re.get(BIZKEYORDER).toString());
        }
        result.put(SBID, sbIds);
        result.put(BIZKEYORDER, bizKeys);
        return result;
    }

    private Object[] sourceSql(StringBuffer sql, DimensionValueSet masterKey, String tableName, List<ColumnModelDefine> columns) throws Exception {
        StringBuffer selectFields = new StringBuffer();
        boolean addFlag = false;
        for (ColumnModelDefine column : columns) {
            if (addFlag) {
                selectFields.append(",");
            }
            selectFields.append(column.getName());
            addFlag = true;
        }
        sql.append(" SELECT ");
        StringBuffer sqlWhere = new StringBuffer();
        DimensionSet dimesions = masterKey.getDimensionSet();
        addFlag = false;
        Object[] args = new Object[dimesions.size()];
        for (int i = 0; i < dimesions.size(); ++i) {
            String dimension = dimesions.get(i);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimension);
            if (addFlag) {
                sqlWhere.append(" AND ");
            }
            if (dimension.equals("DATATIME")) {
                sqlWhere.append(" T.").append(VALIDDATATIME).append("<= ?");
            } else {
                sqlWhere.append("T.").append(dimensionField).append("=?");
            }
            args[i] = masterKey.getValue(dimension);
            addFlag = true;
        }
        sql.append(selectFields).append(" FROM ( ");
        SimpleTable table = new SimpleTable(tableName, "T");
        RankField rankField = new RankField((ISQLTable)table, "NUM");
        IDatabase dataBase = this.getDatabase();
        SQLField sqlField = new SQLField((ISQLTable)table, VALIDDATATIME);
        SortField sortFields = new SortField((ISQLTable)table, (ISQLField)sqlField, 1);
        SQLField sbField = new SQLField((ISQLTable)table, SBID);
        rankField.orderFields().add(sortFields);
        rankField.partitionFields().add(sbField);
        String rankSql = rankField.toSQL(dataBase, 0);
        sql.append(" SELECT ").append(selectFields).append(",").append(rankSql);
        sql.append(" FROM ").append(tableName).append(" T ").append(" WHERE ").append(sqlWhere);
        sql.append(" )  WHERE NUM < 2");
        return args;
    }

    private List<ColumnModelDefine> findActColumns(String actTable) {
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(actTable);
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        List<ColumnModelDefine> res = columns.stream().filter(e -> !e.getName().equalsIgnoreCase(MODIFYTIME)).collect(Collectors.toList());
        return res;
    }

    @Override
    public RollbackAccountDataResult batchRollbackAccountData(AccountRollBackParam accountRollBackParam, AsyncTaskMonitor asyncTaskMonitor) {
        RollbackAccountDataResult rollbackAccountDataResult = new RollbackAccountDataResult();
        String summaryStart = "summary_start";
        asyncTaskMonitor.progressAndMessage(0.01, summaryStart);
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        JtableContext jtableContext = accountRollBackParam.getContext();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
        }
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        Map dimensionSet = jtableContext.getDimensionSet();
        String periodCode = ((DimensionValue)dimensionSet.get(periodEntityInfo.getDimensionName())).getValue();
        String authFliter = "summary_auth_fliter";
        asyncTaskMonitor.progressAndMessage(0.05, authFliter);
        String formKeyStr = accountRollBackParam.getFormKeys();
        List<String> formKeys = Arrays.asList(formKeyStr.split(";"));
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)dimensionSet, (String)jtableContext.getFormSchemeKey()));
        accessFormParam.setTaskKey(jtableContext.getTaskKey());
        accessFormParam.getIgnoreAccessItems().add("accountData");
        accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
        DimensionAccessFormInfo accessFormInfo = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List acessFormInfos = accessFormInfo.getAccessForms();
        if (acessFormInfos.size() <= 0) {
            String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
            String noRollBack = "no_rollback";
            asyncTaskMonitor.error(noRollBack, null, objectToJson);
            return rollbackAccountDataResult;
        }
        String summarying = "summary_ing";
        asyncTaskMonitor.progressAndMessage(0.1, summarying);
        for (int formInfoIndex = 0; formInfoIndex < acessFormInfos.size(); ++formInfoIndex) {
            if (asyncTaskMonitor.isCancel()) {
                return rollbackAccountDataResult;
            }
            DimensionAccessFormInfo.AccessFormInfo dimensionValueFormInfo = (DimensionAccessFormInfo.AccessFormInfo)acessFormInfos.get(formInfoIndex);
            Map dimensionValue = dimensionValueFormInfo.getDimensions();
            DimensionValueSet dimensionValueSet = com.jiuqi.nr.jtable.util.DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
            List forms = dimensionValueFormInfo.getFormKeys();
            List subDimensionValueList = com.jiuqi.nr.jtable.util.DimensionValueSetUtil.getDimensionValueSetList((Map)dimensionValue);
            double formStartProgress = (double)formInfoIndex / (double)acessFormInfos.size() * 0.9 + 0.1;
            double formEndProgress = (double)(formInfoIndex + 1) / (double)acessFormInfos.size() * 0.9 + 0.1;
            for (String form : forms) {
                String tableName = this.accountRollBackTableUtil.getAccHiTableName(form);
                List<DimensionValueSet> canRollBack = this.canRollBack(dimensionValueSet, tableName);
                canRollBack = this.distinctCanRollBack(canRollBack);
            }
        }
        String rollBackSuccess = "\u56de\u6eda\u6210\u529f\uff01";
        String rollBackFail = "\u56de\u6eda\u5931\u8d25\uff01";
        if (asyncTaskMonitor != null) {
            batchReturnInfo.setStatus(0);
            String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
            if (batchReturnInfo.getErrcnt() == 0) {
                if (!asyncTaskMonitor.isFinish()) {
                    asyncTaskMonitor.finish(rollBackSuccess, (Object)objectToJson);
                }
            } else {
                asyncTaskMonitor.error(rollBackFail, null, objectToJson);
            }
        }
        return rollbackAccountDataResult;
    }

    private List<DimensionValueSet> distinctCanRollBack(List<DimensionValueSet> canRollBack) {
        HashSet<String> keys = new HashSet<String>();
        ArrayList<DimensionValueSet> res = new ArrayList<DimensionValueSet>();
        DimensionValueSet newDim = null;
        for (DimensionValueSet dimension : canRollBack) {
            newDim = new DimensionValueSet(dimension);
            newDim.clearValue("DATATIME");
            String dimStr = newDim.toString();
            if (keys.contains(dimStr)) {
                newDim = null;
                continue;
            }
            keys.add(dimStr);
            res.add(dimension);
        }
        return res;
    }

    private void delHisData(DimensionValueSet dimensionValueSet, String tableName) {
    }

    private List<DimensionValueSet> canRollBack(DimensionValueSet dimensionValueSet, String tableName) {
        DimensionSet dimesions = dimensionValueSet.getDimensionSet();
        StringBuffer querySql = new StringBuffer();
        StringBuffer groupSql = new StringBuffer();
        StringBuffer selectSql = new StringBuffer();
        boolean addFalag = false;
        HashMap<String, String> dimensionFieldsMap = new HashMap<String, String>();
        Object[] args = new Object[dimesions.size()];
        for (int index = 0; index < dimesions.size(); ++index) {
            String dimesion = dimesions.get(index);
            Object value = dimensionValueSet.getValue(dimesion);
            String dimensionField = this.accountRollBackTableUtil.parseDimField(dimesion);
            if (addFalag) {
                selectSql.append(" and ");
                groupSql.append(",");
            }
            if (dimesion.equals("DATATIME")) {
                selectSql.append("VALIDDATATIME<=?");
                groupSql.append(VALIDDATATIME);
                args[index] = value;
            } else {
                if (value instanceof List) {
                    selectSql.append(dimensionField).append(" in (?)");
                    StringBuffer argValue = new StringBuffer();
                    List values = (List)value;
                    boolean addFlag = false;
                    for (Object o : values) {
                        if (addFlag) {
                            argValue.append(",");
                        }
                        argValue.append("'").append(o).append("'");
                        addFlag = true;
                    }
                    args[index] = argValue.toString();
                } else {
                    selectSql.append(dimensionField).append("=?");
                    args[index] = value;
                }
                groupSql.append(dimensionField);
            }
            dimensionFieldsMap.put(dimesion, dimensionField);
            addFalag = true;
        }
        selectSql.append(" group by ").append(groupSql);
        selectSql.append(" order by VALIDDATATIME desc");
        querySql.append("select ").append(groupSql.toString()).append(" from ").append(tableName);
        querySql.append(" where ").append(selectSql.toString());
        dimFields.set(dimensionFieldsMap);
        List res = this.jdbcTemplate.query(querySql.toString(), ROW_MAPPER, args);
        dimFields.remove();
        return res;
    }

    private boolean needTempTable(Map<String, DimensionValue> dimensionSet, EntityViewData targetEntityInfo) {
        String[] unitAry;
        String values = dimensionSet.get(targetEntityInfo.getDimensionName()).getValue();
        return Objects.nonNull(values) && (unitAry = values.split(";")).length > 500;
    }

    private boolean isMysql() {
        return true;
    }

    private IDatabase getDatabase() {
        return DatabaseInstance.getDatabase();
    }

    private String i18nMessage(boolean chinese, String message) {
        if (chinese) {
            return message;
        }
        switch (message) {
            case "\u53f0\u8d26\u8868\u6570\u636e\u56de\u6eda\u5f00\u59cb": {
                return "Ledger table data rollback begins";
            }
            case "\u6b63\u5728\u8fdb\u884c\u53c2\u6570\u5206\u6790": {
                return "Parameter analysis is under way";
            }
            case "\u672a\u627e\u5230\u53f0\u8d26\u4fe1\u606f\u8868\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff01": {
                return "Ledger information table not found, please check the parameters!";
            }
            case "\u6b63\u5728\u8fdb\u884c\u53c2\u6570\u67e5\u8be2": {
                return "Parameter query is being performed";
            }
            case "\u6b63\u5728\u8fdb\u884c\u6743\u9650\u5224\u65ad": {
                return "Checking permissions";
            }
            case "\u6b63\u5728\u8fdb\u884c\u5386\u53f2\u7248\u672c\u67e5\u8be2": {
                return "Querying historical versions. Procedure";
            }
            case "\u6b63\u5728\u8fdb\u884c\u4fe1\u606f\u8868\u6570\u636e\u66f4\u65b0": {
                return "Information table data update in progress";
            }
            case "\u6b63\u5728\u8fdb\u884c\u5386\u53f2\u7248\u672c\u6570\u636e\u6e05\u9664": {
                return "Historical version data is being cleared";
            }
            case "\u6b63\u5728\u8fdb\u884c\u53f0\u8d26\u62a5\u8868\u6570\u636e\u6e05\u9664": {
                return "Clearing ledger report data";
            }
            case "\u56de\u6eda\u5931\u8d25\uff01": {
                return "The rollback failed!";
            }
            case "\u56de\u6eda\u6210\u529f\uff01": {
                return "The rollback is successful!";
            }
        }
        return message;
    }

    static class HisRecord {
        DimensionValueSet rowKey;
        Map<String, Object> rowDatas;

        HisRecord() {
        }

        public DimensionValueSet getRowKey() {
            return this.rowKey;
        }

        public void setRowKey(DimensionValueSet rowKey) {
            this.rowKey = rowKey;
        }

        public Map<String, Object> getRowDatas() {
            return this.rowDatas;
        }

        public void setRowDatas(Map<String, Object> rowDatas) {
            this.rowDatas = rowDatas;
        }
    }
}

