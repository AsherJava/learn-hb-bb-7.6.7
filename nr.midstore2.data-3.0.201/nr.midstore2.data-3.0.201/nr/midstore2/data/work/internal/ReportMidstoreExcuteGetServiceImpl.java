/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  com.jiuqi.nr.time.setting.de.FillDataType
 *  com.jiuqi.nr.time.setting.de.FillTimeDataSetting
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.MidstoreExecutionException
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultSourceData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType
 *  com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType
 *  com.jiuqi.nvwa.midstore.param.service.IMidstoreMappingService
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService
 *  nr.midstore2.core.dataset.IMidstoreBatchImportDataService
 *  nr.midstore2.core.dataset.IMidstoreDataSet
 *  nr.midstore2.core.dataset.MidsotreTableContext
 *  nr.midstore2.core.util.IMidstoreAttachmentService
 */
package nr.midstore2.data.work.internal;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import com.jiuqi.nr.time.setting.de.FillDataType;
import com.jiuqi.nr.time.setting.de.FillTimeDataSetting;
import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.MidstoreExecutionException;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultSourceData;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType;
import com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType;
import com.jiuqi.nvwa.midstore.param.service.IMidstoreMappingService;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import nr.midstore2.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.core.dataset.MidsotreTableContext;
import nr.midstore2.core.util.IMidstoreAttachmentService;
import nr.midstore2.data.bean.MidstoreGetCancledResultParam;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.param.IReportMidstoreMappingService;
import nr.midstore2.data.param.IReportMidstoreParamService;
import nr.midstore2.data.util.IReportMidstoreConditonService;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import nr.midstore2.data.util.auth.IReportMidstoreFormDataAccess;
import nr.midstore2.data.work.IReportMidstoreExcuteGetService;
import nr.midstore2.data.work.extend.IReportMidstoreDataGetPreprocessService;
import nr.midstore2.data.work.fix.IReportMidstoreFixDataService;
import nr.midstore2.data.work.internal.floating.MidstoreFloatTableDataHandlerImpl;
import nr.midstore2.data.work.internal.thread.TableShareManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreExcuteGetServiceImpl
implements IReportMidstoreExcuteGetService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreExcuteGetServiceImpl.class);
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IMidstoreBatchImportDataService batchImportService;
    @Autowired
    private IReportMidstoreDimensionService dimensionService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired
    private IMidstoreAttachmentService attachmentService;
    @Autowired
    private IReportMidstoreFixDataService fixDataService;
    @Autowired
    private IReportMidstoreConditonService conditionService;
    @Autowired
    private IReportMidstoreParamService midstoreParamService;
    private static final String TEMP_TABLE = "TempAssistantTable";
    @Autowired(required=false)
    private IReportMidstoreFormDataAccess formAccessService;
    @Autowired
    private IReportMidstoreMappingService reportMappingService;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired(required=false)
    private IReportMidstoreDataGetPreprocessService preprocessService;
    @Autowired
    private FillTimeDataSetting fillTimeDataSetting;
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Value(value="${jiuqi.nvwa.midstore.getData.usethread:true}")
    private boolean dataGetUseThread;
    @Value(value="${jiuqi.nvwa.midstore.getData.threadcount:3}")
    private int dataGetTreadCount;
    @Value(value="${jiuqi.nvwa.midstore.getData.threadtablecount:2}")
    private int threadTablecount;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    private static final String LOG_TITLE = "\u4e2d\u95f4\u5e93\u6570\u636e\u63a5\u6536";

    @Override
    public MidstoreResultObject readFieldDataFromMidstore(MidstoreExeContext context, IDataExchangeTask dataExchangeTask) throws MidstoreExecutionException {
        ReportMidstoreContext reportContext = this.midstoreParamService.getReportContext(context);
        this.midstoreParamService.doCheckParamsBeforeGetData(reportContext);
        this.reportMappingService.initZbMapping(reportContext);
        this.reportMappingService.initPeriodMapping(reportContext);
        this.midstoreMappingService.initOrgMapping((MidstoreContext)reportContext);
        this.midstoreMappingService.initEnumMapping((MidstoreContext)reportContext);
        String nrPeriodCode = this.dimensionService.getExcuteNrPeriod(reportContext);
        if (StringUtils.isNotEmpty((String)nrPeriodCode) && StringUtils.isEmpty((String)reportContext.getExcuteNrPeriod())) {
            reportContext.setExcuteNrPeriod(nrPeriodCode);
        }
        this.midstoreParamService.checkAndSetContextEntity(reportContext);
        if (this.formAccessService != null) {
            reportContext.info(logger, "\u666e\u901a\u7528\u6237\uff0c\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
            try {
                Map<DimensionValueSet, List<String>> unitFormKeys;
                if (reportContext.getExcuteParams().containsKey("UNITFORMKEYS")) {
                    unitFormKeys = (Map<DimensionValueSet, List<String>>)reportContext.getExcuteParams().get("UNITFORMKEYS");
                } else {
                    unitFormKeys = this.midstoreParamService.getUnitFormKeysByType(reportContext, FormAccessType.FORMACCESS_WRITE, MidstoreOperateType.OPERATETYPE_GET);
                    reportContext.getExcuteParams().put("UNITFORMKEYS", unitFormKeys);
                }
                reportContext.info(logger, "\u8bfb\u53d6\u6743\u9650\u8bbe\u7f6e\u7684\u5355\u4f4d\u6570\uff1a" + unitFormKeys.size());
                if (unitFormKeys.isEmpty()) {
                    return new MidstoreResultObject(false, "\u6ca1\u6709\u6ee1\u8db3\u6761\u4ef6\u7684\u5355\u4f4d");
                }
                this.midstoreParamService.tranUnitFormsToTables(reportContext, unitFormKeys);
            }
            catch (MidstoreException e) {
                reportContext.error(logger, e.getMessage(), e);
            }
        } else {
            reportContext.info(logger, "\u666e\u901a\u7528\u6237\uff0c\u672a\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
            try {
                this.midstoreParamService.doLoadFormScheme(reportContext, true);
            }
            catch (MidstoreException e) {
                reportContext.error(logger, e.getMessage(), e);
            }
        }
        FillInAutomaticallyDue fillInAutomaticallyDue = reportContext.getTaskDefine().getFillInAutomaticallyDue();
        boolean isCheckFillDate = false;
        if (fillInAutomaticallyDue != null) {
            int type = fillInAutomaticallyDue.getType();
            if (FillInAutomaticallyDue.Type.CLOSE.getValue() != type) {
                isCheckFillDate = true;
            }
        }
        if (isCheckFillDate || !FillDateType.NONE.equals((Object)reportContext.getTaskDefine().getFillingDateType())) {
            Calendar instance = Calendar.getInstance();
            FillDataType fillTimeData = this.fillTimeDataSetting.fillTimeData(reportContext.getTaskDefine().getKey(), nrPeriodCode, instance.getTime());
            if (!FillDataType.SUCCESS.equals((Object)fillTimeData)) {
                reportContext.info(logger, "\u4efb\u52a1\u8bbe\u8ba1\u586b\u62a5\u671f:" + fillTimeData.getMessage());
            }
            String entityDimName = this.entityMetaService.getDimensionName(reportContext.getTaskDefine().getDw());
            ArrayList<String> unitCodes = new ArrayList<String>();
            Map unitFormKeys = (Map)reportContext.getExcuteParams().get("UNITFORMKEYS");
            HashMap<String, ArrayList<DimensionValueSet>> unitDimesions = new HashMap<String, ArrayList<DimensionValueSet>>();
            if (unitFormKeys != null) {
                for (Map.Entry entry : unitFormKeys.entrySet()) {
                    DimensionValueSet set = (DimensionValueSet)entry.getKey();
                    String unitCode = (String)set.getValue(entityDimName);
                    if (!StringUtils.isNotEmpty((String)unitCode)) continue;
                    ArrayList<DimensionValueSet> unitDims = (ArrayList<DimensionValueSet>)unitDimesions.get(unitCode);
                    if (unitDims == null) {
                        unitCodes.add(unitCode);
                        unitDims = new ArrayList<DimensionValueSet>();
                        unitDimesions.put(unitCode, unitDims);
                    }
                    unitDims.add(set);
                }
            }
            HashMap<String, DimensionValue> masterDims = new HashMap<String, DimensionValue>();
            DimensionValue unitValue = new DimensionValue();
            unitValue.setName(entityDimName);
            unitValue.setValue(StringUtils.join(unitCodes.iterator(), (String)";"));
            masterDims.put(entityDimName, unitValue);
            String periodDimName = this.periodAdapter.getPeriodDimensionName();
            DimensionValue periodValue = new DimensionValue();
            periodValue.setName(periodDimName);
            periodValue.setValue(nrPeriodCode);
            masterDims.put(periodDimName, periodValue);
            DimensionCollection masterKey = DimensionValueSetUtil.buildDimensionCollection(masterDims, (String)reportContext.getFormSchemeKey());
            DimensionValueSet dataSets = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)masterKey);
            Map stringMsgReturnMap = this.deSetTimeProvide.batchCompareSetTime(reportContext.getFormSchemeKey(), dataSets);
            for (Map.Entry entry : stringMsgReturnMap.entrySet()) {
                String unitKey = (String)entry.getKey();
                MsgReturn msgReturn = (MsgReturn)entry.getValue();
                if (!msgReturn.isDisabled()) continue;
                reportContext.info(logger, "\u68c0\u67e5\u586b\u62a5\u671f:" + msgReturn.getMsg() + "," + unitKey);
                List unitDims = (List)unitDimesions.get(unitKey);
                for (DimensionValueSet dim : unitDims) {
                    unitFormKeys.remove(dim);
                }
            }
            if (unitFormKeys.size() == 0) {
                return new MidstoreResultObject(false, "\u672a\u5728\u586b\u62a5\u671f");
            }
        }
        MidstoreWorkResultSourceData sourceResult = this.recordSourceResult(context);
        reportContext.setSourceResult(sourceResult);
        if (this.preprocessService != null) {
            this.preprocessService.preprocessDataToMidstore(reportContext, dataExchangeTask);
        }
        if (reportContext.getExchangeEnityCodes().size() > 499 && reportContext.getExchangeEnityCodes().size() < 5000) {
            this.dimensionService.createTempTable(reportContext);
        }
        try {
            List tableModes = dataExchangeTask.getAllTableModel();
            ArrayList<DETableModel> fixTableModes = new ArrayList<DETableModel>();
            ArrayList<DETableModel> floatTableModes = new ArrayList<DETableModel>();
            for (DETableModel tableModel : tableModes) {
                DETableInfo tableInfo = tableModel.getTableInfo();
                if (StringUtils.isNotEmpty((String)tableInfo.getCategory()) && !tableInfo.getCategory().equalsIgnoreCase(reportContext.getExeContext().getSourceTypeId())) continue;
                TableType tableType = tableInfo.getType();
                if (tableType == TableType.ZB || tableType == TableType.MDZB) {
                    fixTableModes.add(tableModel);
                    continue;
                }
                if (tableType != TableType.BIZ) continue;
                floatTableModes.add(tableModel);
            }
            ArrayList<DETableModel> threadTableModes = new ArrayList<DETableModel>();
            if (!fixTableModes.isEmpty()) {
                threadTableModes.addAll(fixTableModes);
            }
            if (!floatTableModes.isEmpty()) {
                threadTableModes.addAll(floatTableModes);
            }
            if (this.dataGetUseThread && tableModes.size() >= this.threadTablecount) {
                int threadNum = this.dataGetTreadCount;
                if (threadNum > tableModes.size()) {
                    threadNum = tableModes.size();
                }
                if (threadNum < 0) {
                    threadNum = 3;
                } else if (threadNum > 100) {
                    threadNum = 100;
                }
                reportContext.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u7ebf\u7a0b\u6570\uff1a" + String.valueOf(threadNum));
                String mainTheadName = Thread.currentThread().getName();
                TableShareManager queueManager = new TableShareManager();
                for (int i = 0; i < threadTableModes.size(); ++i) {
                    DETableModel shareItem = (DETableModel)threadTableModes.get(i);
                    queueManager.getQueue().offer(shareItem);
                }
                ArrayList<Future> resList = new ArrayList<Future>();
                int i = 0;
                while (i < threadNum) {
                    int threadId = i++;
                    Future threadResult = this.npApplication.asyncRun(() -> {
                        String threadInfo = "\u6570\u636e\u63a5\u6536\uff1a\u63a8\u9001\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + "";
                        reportContext.info(logger, threadInfo + ",\u5f00\u59cb");
                        while (!queueManager.isThreadQueueEmpty()) {
                            DETableModel tableModel = queueManager.getThreadFormsAsyn();
                            if (tableModel == null) {
                                reportContext.info(logger, threadInfo + ",\u7ebf\u7a0b\u961f\u5217\u5df2\u7a7a");
                                continue;
                            }
                            DETableInfo tableInfo = tableModel.getTableInfo();
                            TableType tableType = tableInfo.getType();
                            if (tableType == TableType.ZB || tableType == TableType.MDZB) {
                                reportContext.info(logger, threadInfo + ",\u8868\uff0c" + tableInfo.getName());
                                this.fixDataService.readFixFieldDataFromMidstore(reportContext, dataExchangeTask, tableModel);
                            } else if (tableType == TableType.BIZ) {
                                reportContext.info(logger, threadInfo + ",\u8868\uff0c" + tableInfo.getName());
                                this.readFloatFieldDataFromMidstore(reportContext, dataExchangeTask, tableModel);
                            }
                            reportContext.updatDoTableNumAsyn(1);
                            if (!context.getMonitor().isCanceled()) continue;
                            reportContext.info(logger, threadInfo + ",\u5df2\u53d6\u6d88");
                            break;
                        }
                        reportContext.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u63a5\u6536\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + ",\u5b8c\u6210");
                        return 1;
                    });
                    resList.add(threadResult);
                }
                for (i = 0; i < resList.size(); ++i) {
                    try {
                        Future res = (Future)resList.get(i);
                        res.get();
                        reportContext.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u63a5\u6536\u7ebf\u7a0b" + mainTheadName + "_SUB" + i + ",\u8fd4\u56de");
                        continue;
                    }
                    catch (InterruptedException | ExecutionException e) {
                        reportContext.error(logger, e.getMessage(), e);
                        throw e;
                    }
                }
                reportContext.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u63a5\u6536\u7ebf\u7a0b\u7ebf\u7a0b\uff0c\u6240\u6709\u7ebf\u7a0b\u8fd4\u56de\u7ed3\u679c");
            } else {
                for (int i = 0; i < threadTableModes.size(); ++i) {
                    DETableModel tableModel = (DETableModel)threadTableModes.get(i);
                    DETableInfo tableInfo = tableModel.getTableInfo();
                    TableType tableType = tableInfo.getType();
                    if (tableType == TableType.ZB || tableType == TableType.MDZB) {
                        reportContext.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u8868\uff0c" + tableInfo.getName());
                        this.fixDataService.readFixFieldDataFromMidstore(reportContext, dataExchangeTask, tableModel);
                    } else if (tableType == TableType.BIZ) {
                        reportContext.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u8868\uff0c" + tableInfo.getName());
                        this.readFloatFieldDataFromMidstore(reportContext, dataExchangeTask, tableModel);
                    }
                    reportContext.updatDoTableNumAsyn(1);
                    if (!context.getMonitor().isCanceled()) continue;
                    reportContext.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u5df2\u53d6\u6d88");
                    break;
                }
            }
            for (String orgCode : reportContext.getWorkResult().getMidstoreTableUnits()) {
                Set toList = context.getMidstoreContext().getWorkResult().getMidstoreTableUnits();
                if (toList.contains(orgCode)) continue;
                toList.add(orgCode);
            }
            sourceResult.getSoruceDTO().setTotalObjectSize(sourceResult.getObjectCodes().size());
            sourceResult.getSoruceDTO().setErrorObjectSize(sourceResult.getErrorObjectCodes().size());
            if (context.getMidstoreContext().getAsyncMonitor().isCancel()) {
                MidstoreGetCancledResultParam cancelParam = new MidstoreGetCancledResultParam(String.valueOf(reportContext.getHasDoTableNum()));
                context.getMidstoreContext().getAsyncMonitor().canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                this.recordDataGetTablesToLog(reportContext);
                reportContext.info(logger, "\u6570\u636e\u63a5\u6536\u5df2\u53d6\u6d88");
                MidstoreResultObject midstoreResultObject = new MidstoreResultObject(false, "\u6570\u636e\u63a5\u6536\u5df2\u53d6\u6d88");
                return midstoreResultObject;
            }
        }
        catch (DataExchangeException | MidstoreException e) {
            reportContext.error(logger, e.getMessage(), e);
            throw new MidstoreExecutionException(e.getMessage(), e);
        }
        catch (InterruptedException e) {
            reportContext.error(logger, e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new MidstoreExecutionException(e.getMessage(), (Throwable)e);
        }
        catch (Exception e) {
            reportContext.error(logger, e.getMessage(), e);
            throw new MidstoreExecutionException(e.getMessage(), (Throwable)e);
        }
        finally {
            if (reportContext.getIntfObjects().containsKey(TEMP_TABLE)) {
                this.dimensionService.closeTempTable(reportContext);
            }
        }
        return new MidstoreResultObject(true, "");
    }

    private void readFloatFieldDataFromMidstore(ReportMidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        Map dimNameValueList;
        String deTableCode;
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDlIST");
        DETableInfo tableInfo = tableModel.getTableInfo();
        String nrTableCode = deTableCode = tableInfo.getName();
        String tablePrefix = context.getMidstoreScheme().getTablePrefix();
        if (StringUtils.isNotEmpty((String)tablePrefix) && nrTableCode.startsWith(tablePrefix + "_")) {
            nrTableCode = nrTableCode.substring(tablePrefix.length() + 1, nrTableCode.length());
        }
        if (tableFieldList != null && tableFieldList.size() > 0 && !tableFieldList.containsKey(nrTableCode)) {
            return;
        }
        DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
        if (dataTable == null) {
            context.info(logger, "\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff0c\u4e0d\u63a5\u6536\uff1a" + nrTableCode);
            return;
        }
        Map<String, DimensionValue> dimSetMap2 = this.dimensionService.getDimSetMap(context);
        HashMap<String, DimensionValue> dimSetMap = new HashMap<String, DimensionValue>();
        for (String dimName : dimSetMap2.keySet()) {
            DimensionValue value = dimSetMap2.get(dimName);
            if (StringUtils.isNotEmpty((String)value.getValue())) {
                dimSetMap.put(dimName, value);
                continue;
            }
            context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u60c5\u666f\u4e3a\u7a7a\uff0c" + dimName);
        }
        String nrPeriodCode = context.getExcuteNrPeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            nrPeriodCode = dataTime;
        }
        String dePeriodCode = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), nrPeriodCode);
        if (context.getMappingCache().getPeriodMappingInfos().containsKey(nrPeriodCode)) {
            dePeriodCode = ((PeriodMapingInfo)context.getMappingCache().getPeriodMappingInfos().get(nrPeriodCode)).getPeriodMapCode();
        }
        if (context.getWorkResult() != null) {
            context.getWorkResult().setPeriodCode(nrPeriodCode);
            context.getWorkResult().setPeriodTitle(this.dimensionService.getPeriodTitle(context.getTaskDefine().getDateTime(), nrPeriodCode));
            context.getWorkResult().setMidstorePeriodCode(dePeriodCode);
        }
        context.info(logger, "\u6570\u636e\u63a5\u6536\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
        List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
        Map<String, DataField> dataFieldMap = dataFieldList.stream().collect(Collectors.toMap(Basic::getCode, dataField -> dataField));
        HashMap<String, Map<String, DataField>> nrDataTableFields = new HashMap<String, Map<String, DataField>>();
        nrDataTableFields.put(nrTableCode, dataFieldMap);
        HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
        for (Object dataField2 : dataFieldList) {
            if (!StringUtils.isNotEmpty((String)dataField2.getRefDataEntityKey())) continue;
            String baseDataCode = dataField2.getRefDataEntityKey();
            baseDataCode = EntityUtils.getId((String)baseDataCode);
            nrFieldMapBaseDatas.put(dataField2.getCode(), baseDataCode);
        }
        if (context.getExcuteParams().containsKey("DIMNAMEVALUELIST") && (dimNameValueList = (Map)context.getExcuteParams().get("DIMNAMEVALUELIST")) != null) {
            for (String dimName : dimNameValueList.keySet()) {
                if (StringUtils.isEmpty((String)dimName) || dimName.equalsIgnoreCase(context.getEntityTypeName()) || dimName.equalsIgnoreCase(context.getDateTypeName())) continue;
                Set dimValues = (Set)dimNameValueList.get(dimName);
                StringBuilder sp = new StringBuilder();
                for (String dimValue : dimValues) {
                    sp.append(dimValue).append(',');
                }
                if (sp.length() == 0) continue;
                sp.delete(sp.length() - 1, sp.length());
                if (dimSetMap.containsKey(dimName)) {
                    ((DimensionValue)dimSetMap.get(dimName)).setValue(sp.toString());
                    continue;
                }
                DimensionValue otherDim = new DimensionValue();
                otherDim.setType(0);
                otherDim.setName(dimName);
                otherDim.setValue(sp.toString());
                dimSetMap.put(dimName, otherDim);
            }
        }
        MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getProgressID());
        tableContext.setFormSchemeKey(context.getFormSchemeKey());
        if (context.getIntfObjects().containsKey(TEMP_TABLE)) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get(TEMP_TABLE);
            tableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
        }
        tableContext.setFloatImpOpt(2);
        ArrayList<String> nrFieldsArr = new ArrayList<String>();
        ArrayList<String> deFieldsArr = new ArrayList<String>();
        ArrayList<String> dimFields = new ArrayList<String>();
        for (DimensionValue dim : dimSetMap.values()) {
            nrFieldsArr.add(dim.getName());
            dimFields.add(dim.getName());
        }
        ArrayList<String> deFieldNames = new ArrayList<String>();
        HashMap<String, DEFieldInfo> nrFieldMapDes = new HashMap<String, DEFieldInfo>();
        HashMap<String, String> deFieldMapNrs = new HashMap<String, String>();
        HashMap<String, String> nrFieldMapDables = new HashMap<String, String>();
        List fieldInfos = tableModel.getFields();
        for (DEFieldInfo deField : fieldInfos) {
            DataField field;
            String deFieldName = deField.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            String mapCode1 = nrTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            String nrFieldName = deFieldName;
            if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                field = this.dataSchemeSevice.getDataField(((ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(mapCode)).getFieldKey());
                nrFieldName = field.getCode();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode1)) {
                field = this.dataSchemeSevice.getDataField(((ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(mapCode1)).getFieldKey());
                nrFieldName = field.getCode();
            } else if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                ZBMappingInfo mapInfo = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName);
                if (dataTable.getCode().equalsIgnoreCase(mapInfo.getTable())) {
                    DataField field2 = this.dataSchemeSevice.getDataField(mapInfo.getFieldKey());
                    nrFieldName = field2.getCode();
                }
            } else if ("MDCODE".equalsIgnoreCase(deFieldName)) {
                nrFieldName = "MDCODE";
            } else if ("TIMEKEY".equalsIgnoreCase(deFieldName)) {
                nrFieldName = "DATATIME";
            }
            if (dataFieldMap.containsKey(nrFieldName)) {
                DataField dataField3 = dataFieldMap.get(nrFieldName);
                if (dataTable.getDataTableType() == DataTableType.ACCOUNT && dataField3.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
            }
            if (!("ORGCODE".equalsIgnoreCase(nrFieldName) || "MDCODE".equalsIgnoreCase(nrFieldName) || "DATATIME".equalsIgnoreCase(nrFieldName) || nrFieldsArr.contains(nrFieldName))) {
                nrFieldsArr.add(nrFieldName);
            }
            deFieldsArr.add(deFieldName);
            nrFieldMapDes.put(deFieldName, deField);
            deFieldMapNrs.put(deFieldName, nrFieldName);
        }
        if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
            context.info(logger, "\u6570\u636e\u63a5\u6536\u53f0\u5361\u8868\uff1a" + dataTable.getCode());
        }
        MidstoreWorkResultTableData tableResult = this.recordTableResult(context, dataTable);
        IMidstoreDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey(), nrFieldsArr);
        try {
            String contition = this.conditionService.getCondtionSQl(context, dePeriodCode);
            MidstoreFloatTableDataHandlerImpl dataHandler = new MidstoreFloatTableDataHandlerImpl(context, bathDataSet, nrPeriodCode, nrTableCode, deTableCode, nrFieldsArr, nrFieldMapDes, nrDataTableFields, dimSetMap, nrFieldMapBaseDatas, dimFields, nrFieldMapDables, deFieldMapNrs, this.dataSchemeSevice, this.attachmentService);
            context.info(logger, "\u6570\u636e\u63a5\u6536\uff1a" + deTableCode + "\uff0c\u6761\u4ef6\uff0c" + contition);
            dataExchangeTask.readTableData(deTableCode, deFieldsArr, contition, (ITableDataHandler)dataHandler);
        }
        catch (Exception e) {
            if (bathDataSet != null) {
                bathDataSet.close();
            }
            context.error(logger, e.getMessage(), e);
            this.resultService.addTableErrorInfo((MidstoreContext)context, context.getWorkResult(), "\u5176\u4ed6", e.getMessage(), nrTableCode, "");
            this.resultService.addTableErrorInfo(context.getExeContext().getMidstoreContext(), context.getExeContext().getMidstoreContext().getWorkResult(), "\u5176\u4ed6", e.getMessage(), nrTableCode, "");
            context.getWorkResult().getPeriodResult().getErrorWorkTableKeys().add(dataTable.getKey());
            throw new MidstoreException("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
        }
        finally {
            this.recordUpdateTableResult(tableResult);
            if (bathDataSet != null) {
                bathDataSet.close();
            }
        }
    }

    private MidstoreWorkResultTableData recordTableResult(ReportMidstoreContext context, DataTable dataTable) {
        return context.recordTableResult(dataTable);
    }

    private void recordUpdateTableResult(MidstoreWorkResultTableData tableResult) {
        tableResult.getTableDTO().setObjectErrorCount(tableResult.getErrorObjectCodes().size());
        tableResult.getTableDTO().setObjectItemCount(tableResult.getObjectResults().size());
        if (tableResult.getTableDTO().getObjectErrorCount() > 0 || tableResult.getTableDTO().getErrorRecordSize() > 0) {
            tableResult.getTableDTO().setStauts(MidstoreStatusType.STATUS_ERROR);
        } else {
            tableResult.getTableDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
        }
        for (MidstoreWorkResultObjectData objectResult : tableResult.getObjectResults()) {
            if (objectResult.getObjectDTO().getErrorRecordSize() > 0) {
                objectResult.getObjectDTO().setStauts(MidstoreStatusType.STATUS_ERROR);
                continue;
            }
            objectResult.getObjectDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
        }
    }

    private MidstoreWorkResultSourceData recordSourceResult(MidstoreExeContext context) {
        MidstoreWorkResultSourceData sourceResult = new MidstoreWorkResultSourceData();
        sourceResult.getSoruceDTO().setKey(UUID.randomUUID().toString());
        sourceResult.getSoruceDTO().setErrorObjectSize(0);
        sourceResult.getSoruceDTO().setExtendData(null);
        sourceResult.getSoruceDTO().setResultKey(context.getMidstoreContext().getWorkResult().getPeriodResult().getWorkResultDTO().getKey());
        sourceResult.getSoruceDTO().setSourceType(context.getSourceTypeId());
        sourceResult.getSoruceDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
        sourceResult.getSoruceDTO().setTotalObjectSize(0);
        context.getMidstoreContext().getWorkResult().getPeriodResult().getSoruceResults().add(sourceResult);
        return sourceResult;
    }

    private void recordDataGetTablesToLog(ReportMidstoreContext context) {
        UnitReportLog unitReportLog = this.dataServiceLoggerFactory.getUnitReportLog();
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger(LOG_TITLE, OperLevel.USER_OPER);
        for (MidstoreWorkResultTableData tableResult : context.getWorkResult().getPeriodResult().getTableResults()) {
            for (String unitcode : context.getExchangeEnityCodes()) {
                unitReportLog.addTableToUnit(unitcode, tableResult.getTableDTO().getSourceTableCode());
            }
        }
        String nrPeriodCode = context.getExcuteNrPeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            nrPeriodCode = dataTime;
        }
        LogDimensionCollection dimensionCollection = null;
        if (context.getTaskDefine() != null && context.getExchangeEnityCodes() != null && StringUtils.isNotEmpty((String)nrPeriodCode)) {
            Object[] dwArr = new String[context.getExchangeEnityCodes().size()];
            int i = 0;
            for (String entityCode : context.getExchangeEnityCodes()) {
                dwArr[i] = entityCode;
                ++i;
            }
            Arrays.sort(dwArr);
            dimensionCollection = new LogDimensionCollection();
            dimensionCollection.setPeriod(context.getTaskDefine().getDateTime(), nrPeriodCode);
            dimensionCollection.setDw(context.getTaskDefine().getDw(), (String[])dwArr);
        }
        logHelper.info(context.getTaskKey(), dimensionCollection, unitReportLog, "\u4e2d\u95f4\u5e93\u6570\u636e\u63a5\u6536\u5df2\u53d6\u6d88", "\u4e2d\u95f4\u5e93\u6570\u636e\u63a5\u6536\u5df2\u53d6\u6d88\uff0c\u5df2\u63a5\u6536" + context.getHasDoTableNum() + "\u4e2a\u6307\u6807\u8868");
    }
}

