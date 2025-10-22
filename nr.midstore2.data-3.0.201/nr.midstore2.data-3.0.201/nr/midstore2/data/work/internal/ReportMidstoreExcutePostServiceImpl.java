/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.MidstoreExecutionException
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultSourceData
 *  com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType
 *  com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType
 *  com.jiuqi.nvwa.midstore.param.service.IMidstoreMappingService
 */
package nr.midstore2.data.work.internal;

import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.MidstoreExecutionException;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultSourceData;
import com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType;
import com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType;
import com.jiuqi.nvwa.midstore.param.service.IMidstoreMappingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import nr.midstore2.data.bean.MidstorePostCancledResultParam;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.param.IReportMidstoreMappingService;
import nr.midstore2.data.param.IReportMidstoreParamService;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import nr.midstore2.data.util.auth.IReportMidstoreFormDataAccess;
import nr.midstore2.data.work.IReportMidstoreExcutePostService;
import nr.midstore2.data.work.extend.IReportMidstoreDataPostLaterProcessService;
import nr.midstore2.data.work.fix.IReportMidstorePostFixDataService;
import nr.midstore2.data.work.floating.IReportMidstorePostFloatDataService;
import nr.midstore2.data.work.internal.thread.TableShareManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreExcutePostServiceImpl
implements IReportMidstoreExcutePostService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreExcutePostServiceImpl.class);
    @Autowired
    private IReportMidstoreMappingService reportMappingService;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IReportMidstoreDimensionService dimensionService;
    @Autowired
    private IReportMidstoreParamService midstoreParamService;
    @Autowired(required=false)
    private IReportMidstoreFormDataAccess formAccessService;
    @Autowired(required=false)
    private IReportMidstoreDataPostLaterProcessService laterProcessService;
    @Autowired
    private IReportMidstorePostFixDataService postFixDataService;
    @Autowired
    private IReportMidstorePostFloatDataService postFloatDataService;
    @Value(value="${jiuqi.nvwa.midstore.postData.usethread:true}")
    private boolean postUseThread;
    @Value(value="${jiuqi.nvwa.midstore.postData.threadcount:3}")
    private int postDataTreadCount;
    @Value(value="${jiuqi.nvwa.midstore.postData.threadtablecount:2}")
    private int threadTablecount;
    @Autowired
    private NpApplication npApplication;

    @Override
    public MidstoreResultObject writeFieldDataFromMidstore(MidstoreExeContext context, IDataExchangeTask dataExchangeTask) throws MidstoreExecutionException {
        context.getMidstoreContext().info(logger, context.getMidstoreScheme().getTitle() + "\u6570\u636e\u63d0\u4f9b\u6307\u6807\u6570\u636e");
        ReportMidstoreContext reportContext = this.midstoreParamService.getReportContext(context);
        this.midstoreParamService.doCheckParamsBeforePostData(reportContext);
        this.reportMappingService.initZbMapping(reportContext);
        this.reportMappingService.initPeriodMapping(reportContext);
        this.midstoreMappingService.initOrgMapping((MidstoreContext)reportContext);
        this.midstoreMappingService.initEnumMapping((MidstoreContext)reportContext);
        String nrPeriodCode = this.dimensionService.getExcuteNrPeriod(reportContext);
        if (StringUtils.isNotEmpty((String)nrPeriodCode) && StringUtils.isEmpty((String)reportContext.getExcuteNrPeriod())) {
            reportContext.setExcuteNrPeriod(nrPeriodCode);
        }
        if (!reportContext.isPostNullValue()) {
            reportContext.info("\u6570\u636e\u63d0\u4f9b\uff1a\u56fa\u5b9a\u6307\u6807\u4e0d\u63a8\u9001\u7a7a\u503c");
        }
        if (!reportContext.isPostZeroValue()) {
            reportContext.info("\u6570\u636e\u63d0\u4f9b\uff1a\u56fa\u5b9a\u6307\u6807\u4e0d\u63a8\u9001\u96f6\u503c");
        }
        this.midstoreParamService.checkAndSetContextEntity(reportContext);
        if (this.formAccessService != null) {
            reportContext.info(logger, "\u666e\u901a\u7528\u6237\uff0c\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
            try {
                Map<DimensionValueSet, List<String>> unitFormKeys = reportContext.getExcuteParams().containsKey("UNITFORMKEYS") ? (Map<DimensionValueSet, List<String>>)reportContext.getExcuteParams().get("UNITFORMKEYS") : this.midstoreParamService.getUnitFormKeysByType(reportContext, FormAccessType.FORMACCESS_READ, MidstoreOperateType.OPERATETYPE_POST);
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
        MidstoreWorkResultSourceData sourceResult = this.recordSourceResult(context);
        reportContext.setSourceResult(sourceResult);
        if (reportContext.getExchangeEnityCodes().size() > 499 && reportContext.getExchangeEnityCodes().size() < 5000) {
            this.dimensionService.createTempTable(reportContext);
        }
        try {
            List tableModes = dataExchangeTask.getAllTableModel();
            ArrayList<DETableModel> fixTableModes = new ArrayList<DETableModel>();
            ArrayList<DETableModel> floatTableModes = new ArrayList<DETableModel>();
            for (DETableModel tableModel : tableModes) {
                DETableInfo tableInfo = tableModel.getTableInfo();
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
            if (this.postUseThread && tableModes.size() >= this.threadTablecount) {
                int threadNum = this.postDataTreadCount;
                if (threadNum > tableModes.size()) {
                    threadNum = tableModes.size();
                }
                if (threadNum < 0) {
                    threadNum = 3;
                } else if (threadNum > 100) {
                    threadNum = 100;
                }
                reportContext.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u7ebf\u7a0b\u6570\uff1a" + String.valueOf(threadNum));
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
                        String threadInfo = "\u6570\u636e\u63d0\u4f9b\uff1a\u63a8\u9001\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + "";
                        reportContext.info(logger, threadInfo + ",\u5f00\u59cb");
                        while (!queueManager.isThreadQueueEmpty()) {
                            DETableModel tableModel = queueManager.getThreadFormsAsyn();
                            if (tableModel == null) {
                                reportContext.info(logger, threadInfo + ",\u7ebf\u7a0b\u961f\u5217\u8fd4\u56de\u4e3a\u7a7a");
                                continue;
                            }
                            DETableInfo tableInfo = tableModel.getTableInfo();
                            TableType tableType = tableInfo.getType();
                            if (tableType == TableType.ZB || tableType == TableType.MDZB) {
                                reportContext.info(logger, threadInfo + ",\u8868\uff0c" + tableInfo.getName());
                                this.postFixDataService.writeFixFieldDataToMidstore(reportContext, dataExchangeTask, tableModel);
                            } else if (tableType == TableType.BIZ) {
                                reportContext.info(logger, threadInfo + ",\u8868\uff0c" + tableInfo.getName());
                                this.postFloatDataService.writeFloatFieldDataToMidstore(reportContext, dataExchangeTask, tableModel);
                            }
                            if (!context.getMonitor().isCanceled()) continue;
                            reportContext.info(logger, threadInfo + ",\u5df2\u53d6\u6d88");
                            break;
                        }
                        reportContext.info(logger, threadInfo + ",\u5b8c\u6210");
                        return 1;
                    });
                    resList.add(threadResult);
                }
                for (i = 0; i < resList.size(); ++i) {
                    try {
                        Future res = (Future)resList.get(i);
                        res.get();
                        reportContext.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u63a8\u9001\u7ebf\u7a0b" + mainTheadName + "_SUB" + i + ",\u8fd4\u56de");
                        continue;
                    }
                    catch (InterruptedException | ExecutionException e) {
                        reportContext.error(logger, e.getMessage(), e);
                        throw e;
                    }
                }
                reportContext.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u63a8\u9001\u7ebf\u7a0b\u7ebf\u7a0b\uff0c\u6240\u6709\u7ebf\u7a0b\u8fd4\u56de\u7ed3\u679c");
            } else {
                for (int i = 0; i < threadTableModes.size(); ++i) {
                    DETableModel tableModel = (DETableModel)threadTableModes.get(i);
                    DETableInfo tableInfo = tableModel.getTableInfo();
                    TableType tableType = tableInfo.getType();
                    if (tableType == TableType.ZB || tableType == TableType.MDZB) {
                        reportContext.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u8868\uff0c" + tableInfo.getName());
                        this.postFixDataService.writeFixFieldDataToMidstore(reportContext, dataExchangeTask, tableModel);
                    } else if (tableType == TableType.BIZ) {
                        reportContext.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u8868\uff0c" + tableInfo.getName());
                        this.postFloatDataService.writeFloatFieldDataToMidstore(reportContext, dataExchangeTask, tableModel);
                    }
                    reportContext.updatDoTableNumAsyn(1);
                    if (!context.getMonitor().isCanceled()) continue;
                    reportContext.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u5df2\u53d6\u6d88");
                    break;
                }
            }
            sourceResult.getSoruceDTO().setTotalObjectSize(sourceResult.getObjectCodes().size());
            sourceResult.getSoruceDTO().setErrorObjectSize(sourceResult.getErrorObjectCodes().size());
            if (context.getMidstoreContext().getAsyncMonitor().isCancel()) {
                MidstorePostCancledResultParam cancelParam = new MidstorePostCancledResultParam(String.valueOf(reportContext.getHasDoTableNum()));
                context.getMidstoreContext().getAsyncMonitor().canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                MidstoreResultObject midstoreResultObject = new MidstoreResultObject(false, "\u6570\u636e\u63d0\u4f9b\u5df2\u53d6\u6d88");
                return midstoreResultObject;
            }
            if (this.laterProcessService != null) {
                reportContext.info(logger, "\u62a5\u8868\u6570\u636e\u63d0\u4f9b\u5b8c\u6210\uff0c\u8c03\u7528\u540e\u5904\u7406\u6269\u5c55\u63a5\u53e3");
                this.laterProcessService.laterProcessDataToMidstore(reportContext, dataExchangeTask);
            }
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
            this.dimensionService.closeTempTable(reportContext);
        }
        return new MidstoreResultObject(true, "");
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
}

