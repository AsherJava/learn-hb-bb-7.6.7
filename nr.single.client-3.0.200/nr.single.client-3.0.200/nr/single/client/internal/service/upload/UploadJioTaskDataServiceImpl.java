/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.dataentry.bean.JIOFormImportResult
 *  com.jiuqi.nr.dataentry.bean.JIOUnitImportResult
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.sb.bean.ImportInfo
 *  com.jiuqi.nr.jtable.dataset.IRegionImportDataSet
 *  com.jiuqi.nr.jtable.dataset.IReportImportDataSet
 *  com.jiuqi.nr.jtable.dataset.impl.RegionImportDataSetImpl
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  javax.annotation.Resource
 *  nr.single.data.datain.service.ITaskFileBatchImportDataService
 *  nr.single.data.datain.service.ITaskFileImportDataService
 *  nr.single.data.system.SingleDataOptionsService
 *  nr.single.data.util.TaskFileDataCommonService
 *  nr.single.data.util.TaskFileDataOperateUtil
 *  nr.single.map.configurations.bean.DataImportRule
 *  nr.single.map.configurations.bean.UnitState
 *  nr.single.map.data.CheckDataUtil
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleDataError
 *  nr.single.map.data.SingleFieldFileInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.bean.RepeatEntityNode
 *  nr.single.map.data.bean.RepeatFormNode
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 *  nr.single.map.data.service.SingleDimissionServcie
 *  nr.single.map.data.service.TaskDataService
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.dataentry.bean.JIOFormImportResult;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import com.jiuqi.nr.jtable.dataset.IRegionImportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportImportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.RegionImportDataSetImpl;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.bean.JIOImportResultPeriodObject;
import nr.single.client.internal.service.upload.UploadJioDataUtil;
import nr.single.client.service.entity.SingleEntityTypeService;
import nr.single.client.service.querycheck.ISingleImportQueryCheckService;
import nr.single.client.service.upload.IUploadDataCheckResult;
import nr.single.client.service.upload.IUploadDataLogService;
import nr.single.client.service.upload.IUploadEntityCheckService;
import nr.single.client.service.upload.IUploadFileService;
import nr.single.client.service.upload.IUploadJioDataService;
import nr.single.client.service.upload.IUploadJioTaskDataService;
import nr.single.client.service.upload.bean.FormShareItem;
import nr.single.client.service.upload.bean.FormShareManager;
import nr.single.client.service.upload.bean.JioImportCancledResultParam;
import nr.single.client.service.upload.bean.SingleUploadLogManager;
import nr.single.client.service.upload.notice.IUploadJioNoticeManageService;
import nr.single.data.datain.service.ITaskFileBatchImportDataService;
import nr.single.data.datain.service.ITaskFileImportDataService;
import nr.single.data.system.SingleDataOptionsService;
import nr.single.data.util.TaskFileDataCommonService;
import nr.single.data.util.TaskFileDataOperateUtil;
import nr.single.map.configurations.bean.DataImportRule;
import nr.single.map.configurations.bean.UnitState;
import nr.single.map.data.CheckDataUtil;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.bean.RepeatEntityNode;
import nr.single.map.data.bean.RepeatFormNode;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import nr.single.map.data.service.SingleDimissionServcie;
import nr.single.map.data.service.TaskDataService;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadJioTaskDataServiceImpl
implements IUploadJioTaskDataService {
    private static final Logger logger = LoggerFactory.getLogger(UploadJioTaskDataServiceImpl.class);
    @Autowired
    private IJtableResourceService jtableResourceService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private ITaskFileImportDataService jioImportService;
    @Autowired
    private ITaskFileBatchImportDataService batchImportService;
    @Autowired
    private TaskFileDataCommonService commonImportService;
    @Resource
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private TaskDataService taskDataService;
    @Value(value="${jiuqi.nr.jio.dataauth:true}")
    private boolean jioDataUseAuth;
    @Value(value="${jiuqi.nr.jio.datacheck:true}")
    private boolean jioUseCheckData;
    @Value(value="${jiuqi.nr.jio.dataimport.checktree:false}")
    private boolean importFmdmCheckTree;
    @Value(value="${jiuqi.product.name:nr}")
    private String productName;
    @Autowired
    private IDataentryFlowService flowService;
    @Autowired
    private IUploadDataCheckResult singleDataCheckService;
    @Autowired
    private IUploadEntityCheckService singleEntityCheckService;
    @Autowired
    private SingleEntityTypeService entityTypeService;
    @Autowired
    private IUploadDataLogService uploadLogService;
    @Autowired
    private IUploadJioDataService uploadJioDataService;
    @Autowired
    private SingleDimissionServcie singleDimService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private SingleDataOptionsService singleOptionService;
    @Autowired
    private IUploadJioNoticeManageService noticeService;
    @Autowired
    private ISingleImportQueryCheckService uploadQueryCheckService;

    @Override
    public JIOImportResultObject uploadJioTaskData(String taskFilePath, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span) throws SingleDataException {
        TaskDataContext context = new TaskDataContext();
        try {
            logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u5f00\u59cb\u51c6\u5907,\u65f6\u95f4:" + new Date().toString());
            context.getDataOption().setUploadCheckData(this.jioUseCheckData);
            context.setTaskDocPath(this.getUploadTaskDocDir(param));
            context.setTaskTxtPath(this.getUploadTaskTxtDir(param));
            context.setTaskImgPath(this.getUploadTaskImgDir(param));
            context.setTaskRptPath(this.getUploadTaskRptDir(param));
            if (null != asyncTaskMonitor) {
                context.setProgress(begin + 0.01);
                context.setNextProgressLen(0.04);
                context.setAsyncTaskMonitor(asyncTaskMonitor);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u5bfc\u5165\u51c6\u5907");
            }
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u89e3\u6790JIO\u4efb\u52a1\u76ee\u5f55,\u65f6\u95f4:" + new Date().toString() + "\uff0c\u4efb\u52a1\u76ee\u5f55\uff1a" + taskFilePath);
            SingleFile singleFile = this.jioImportService.readSingleFileFromPath(context, taskFilePath);
            if (null != asyncTaskMonitor) {
                context.setProgress(begin + 0.05);
                context.setNextProgressLen(0.03);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u5bfc\u5165\u51c6\u5907");
            }
            return this.uploadJioTaskData(context, taskFilePath, singleFile, param, asyncTaskMonitor, begin, span);
        }
        catch (Exception ex) {
            context.error(logger, ex.getMessage(), (Throwable)ex);
            throw ex;
        }
    }

    @Override
    public JIOImportResultObject uploadJioTaskData(TaskDataContext context, String taskFilePath, SingleFile singleFile, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span) throws SingleDataException {
        JIOImportResultObject result = new JIOImportResultObject();
        try {
            context.setNeedChangeUpper(this.singleOptionService.isUploadChangeUpper());
            String taskDataPath = PathUtil.createNewPath((String)taskFilePath, (String)"DATA");
            String uuFormSchemeKey = param.getFormSchemeKey();
            String uuTaskKey = param.getTaskKey();
            FormDefine fmdmForm = this.viewController.queryFormByCodeInScheme(uuFormSchemeKey, "FMDM");
            String fmdmFormKey = null;
            if (null != fmdmForm) {
                fmdmFormKey = fmdmForm.getKey();
            } else {
                List forms = this.viewController.queryAllFormDefinesByFormScheme(uuFormSchemeKey);
                for (FormDefine form : forms) {
                    if (form.getFormType() != FormType.FORM_TYPE_NEWFMDM) continue;
                    fmdmForm = form;
                    fmdmFormKey = fmdmForm.getKey();
                    break;
                }
            }
            context.setTaskKey(uuTaskKey);
            this.entityTypeService.getEntityType(context, uuFormSchemeKey, param.getDimensionSet());
            this.taskDataService.initContext(context, uuTaskKey, uuFormSchemeKey, param.getConfigKey());
            context.setVariableMap(param.getVariableMap());
            if (param.isDeleteData()) {
                String message = "\u5bfc\u5165JIO\u6570\u636e\uff1a\u542f\u7528\u7a7a\u8868\u5355\u4f4d\u5220\u9664\u6570\u636e\u6a21\u5f0f";
                context.info(logger, message);
                context.setDeleteEmptyData(param.isDeleteData());
            } else {
                context.setDeleteEmptyData(false);
            }
            context.setFmdmFormKey(fmdmFormKey);
            context.setNeedCreateDBF(false);
            context.setTaskDataPath(taskDataPath);
            context.setTaskFilePath(taskFilePath);
            context.setNeedDownLoad(false);
            if (context.getMapingCache().isMapConfig()) {
                context.setCurrentTaskTitle(context.getMapingCache().getMapConfig().getTaskInfo().getSingleTaskTitle());
            } else {
                TaskDefine task = this.runtimeView.queryTaskDefine(uuTaskKey);
                if (null != task) {
                    context.setCurrentTaskTitle(task.getTitle());
                }
            }
            if (null != asyncTaskMonitor) {
                context.setProgress(begin + 0.08);
                context.setNextProgressLen(0.02);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u5bfc\u5165\u51c6\u5907");
            }
            context.setNeedCheckAuth(this.jioDataUseAuth);
            context.setNeedCheckCorpTree(this.importFmdmCheckTree);
            context.setProductName(this.productName);
            this.uploadJioDataService.setImportTypeByParam(context, param);
            this.noticeService.doBeforeImportNotice(param.getTaskKey(), param.getFormSchemeKey(), param);
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u7f13\u5b58\u51fa\u9519\u8bf4\u660e,\u65f6\u95f4:" + new Date().toString());
            if (context.getDataOption().isUploadCheckData() && context.isNeedCheckInfo()) {
                if (singleFile != null && singleFile.getInOutData().indexOf(InOutDataType.SHSM) > 0) {
                    context.setCheckInfos(CheckDataUtil.loadDataCheckInfos((String)taskDataPath));
                } else {
                    context.setNeedCheckInfo(false);
                    context.setCheckInfos(new HashMap());
                }
            } else {
                context.setCheckInfos(new HashMap());
            }
            boolean hasDataImport = false;
            ArrayList<String> netImportPeriods = new ArrayList<String>();
            List singlePeriods = this.jioImportService.getSinglePeriods(context, taskFilePath);
            if (singlePeriods.isEmpty()) {
                context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u65e0\u5355\u673a\u7248\u65f6\u671f\uff0c\u53ef\u80fd\u65e0\u5c01\u9762\u4ee3\u7801\u6570\u636e\uff0c\u6216\u8005\u65f6\u671f\u5b57\u6bb5\u4e3a\u7a7a\uff0c\u5c06\u65e0\u6cd5\u7ee7\u7eed\u5bfc\u5165\uff0c\u8bf7\u68c0\u67e5");
            }
            int pIndex = 0;
            int pCount = singlePeriods.size();
            if (pCount <= 0) {
                pCount = 1;
            }
            for (String singlePeroid : singlePeriods) {
                ++pIndex;
                if (null != asyncTaskMonitor) {
                    context.setProgress(begin + 0.19 + 0.6 * (double)(pIndex - 1) / (double)pCount);
                    context.setNextProgressLen(0.1 / (double)pCount);
                    if (singlePeriods.size() > 1) {
                        asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u5bfc\u5165\u65f6\u671f");
                    }
                }
                context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5904\u7406\u65f6\u671f:" + singlePeroid + ",\u65f6\u95f4:" + new Date().toString());
                DimensionValue dateDim = (DimensionValue)param.getDimensionSet().get(context.getEntityDateType());
                if (null != dateDim && StringUtils.isNotEmpty((String)dateDim.getValue())) {
                    String singlePeriod1 = singlePeroid;
                    if (StringUtils.isNotEmpty((String)singlePeroid) && !singlePeroid.contains("@")) {
                        singlePeriod1 = context.getSingleTaskYear() + "@" + singlePeroid;
                    }
                    context.setNetPeriodCode(this.taskDataService.getNetPeriodCode(context, singlePeriod1));
                    context.setMapNetPeriodCode(context.getNetPeriodCode());
                    context.setCurrentPeriod(singlePeroid);
                    dateDim.setValue(context.getNetPeriodCode());
                }
                JIOImportResultPeriodObject periodResult = new JIOImportResultPeriodObject();
                periodResult.setSinglePeriodCode(singlePeroid);
                periodResult.setNetPeriodCode(context.getNetPeriodCode());
                result.setCurPeriodResult(periodResult);
                result.getJioPeriodResults().add(periodResult);
                if (!this.uploadJioDataService.checkNetPeriodValid(param.getFormSchemeKey(), context.getNetPeriodCode())) {
                    result.setSuccess(false);
                    result.setMessage("\u4e0d\u662f\u6709\u6548\u65f6\u671f\u4ee3\u7801\uff01");
                    periodResult.setSuccess(false);
                    periodResult.setMessage("\u4e0d\u662f\u6709\u6548\u65f6\u671f\u4ee3\u7801\uff01");
                    context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u4e0d\u662f\u6709\u6548\u65f6\u671f\u4ee3\u7801\uff0c\u5904\u7406\u65f6\u671f:" + singlePeroid + ",\u7f51\u62a5\u65f6\u671f\uff1a" + context.getNetPeriodCode());
                } else if (this.uploadJioDataService.isCurrentPeriodCanWrite(param.getFormSchemeKey(), context.getNetPeriodCode())) {
                    this.importSinglePeriodData(context, singlePeroid, taskFilePath, result, param, asyncTaskMonitor, begin, span, pIndex, pCount);
                    periodResult.setSuccess(result.isSuccess());
                    periodResult.setMessage(result.getMessage());
                    periodResult.setSuccesssReportNum(result.getSuccesssReportNum());
                    periodResult.setSuccesssUnitNum(result.getSuccesssUnitNum());
                    periodResult.setAllSuccesssUnitNum(result.getAllSuccesssUnitNum());
                    periodResult.setErrorReportNum(result.getErrorReportNum());
                    periodResult.setErrorUnitNum(result.getErrorUnitNum());
                    periodResult.setErrorLevel(result.getErrorLevel());
                    if (!result.isSuccess()) {
                        if (result.getErrorLevel() == 2) continue;
                        if (result.getErrorLevel() == 3) break;
                    }
                    hasDataImport = true;
                    netImportPeriods.add(context.getMapNetPeriodCode());
                } else {
                    result.setSuccess(false);
                    result.setMessage("\u4e0d\u5728\u586b\u62a5\u65f6\u95f4\u5185\uff01");
                    periodResult.setSuccess(false);
                    periodResult.setMessage("\u4e0d\u5728\u586b\u62a5\u65f6\u95f4\u5185\uff01");
                    context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u4e0d\u5728\u586b\u62a5\u65f6\u95f4\u5185\u4e0d\u5bfc\u5165\uff0c\u5904\u7406\u65f6\u671f:" + singlePeroid + ",\u7f51\u62a5\u65f6\u671f\uff1a" + context.getNetPeriodCode());
                }
                if (null == asyncTaskMonitor) continue;
                context.setProgress(begin + 0.19 + 0.6 * (double)(pIndex - 1) / (double)pCount + 0.4 / (double)pCount);
                context.setNextProgressLen((double)(0 / pCount));
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
                if (!asyncTaskMonitor.isCancel()) continue;
                JioImportCancledResultParam cancelParam = new JioImportCancledResultParam(String.valueOf(context.getImportedFormNum()));
                asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                result.setSuccess(false);
                result.setMessage("JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                context.info(logger, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                this.uploadLogService.recordImportFormsToLog(context, result);
                this.uploadLogService.clearSuccessForms(context, result);
                return result;
            }
            if (context.isNeedSelectImport() && context.getJioSelectImportResult() != null) {
                result.setJioSelectParm(context.getJioSelectImportResult());
                result.setSuccess(true);
                result.setSuccesssUnitNum(0);
                result.setMessage("\u5b58\u5728\u8981\u9009\u62e9\u7684\u5355\u4f4d\uff01");
                context.info(logger, "\u5b58\u5728\u9009\u62e9\u7684\u5355\u4f4d\uff01\u4e2a\u6570\uff1a" + context.getJioSelectImportResult().getEntityNodes().size());
                if (null != asyncTaskMonitor) {
                    asyncTaskMonitor.progressAndMessage(1.0, "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
                    context.setProgress(1.0);
                    context.setNextProgressLen(0.02);
                }
                return result;
            }
            if (context.isNeedCheckRepeat() && context.getJioRepeatResult() != null && context.getRepeatImportType() == 2) {
                result.setJioRepeatParm(context.getJioRepeatResult());
                result.setSuccess(true);
                result.setSuccesssUnitNum(0);
                result.setMessage("\u5b58\u5728\u91cd\u7801\u5355\u4f4d\uff01");
                context.info(logger, "\u5b58\u5728\u91cd\u7801\u5355\u4f4d\uff01\u4e2a\u6570\uff1a" + context.getJioRepeatResult().getEntityNodes().size());
                if (null != asyncTaskMonitor) {
                    asyncTaskMonitor.progressAndMessage(1.0, "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
                    context.setProgress(1.0);
                    context.setNextProgressLen(0.02);
                }
                return result;
            }
            if (null != asyncTaskMonitor) {
                context.setProgress(0.8);
                context.setNextProgressLen(0.05);
                asyncTaskMonitor.progressAndMessage(0.8, "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
            }
            if (hasDataImport && result.getSuccesssUnitNum() > 0 && context.getFjUploadMode() == 1) {
                if (null != asyncTaskMonitor) {
                    asyncTaskMonitor.progressAndMessage(0.81, "\u9644\u4ef6\u5bfc\u5165");
                    context.setProgress(0.81);
                    context.setNextProgressLen(0.09);
                }
                context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u4e8c\u6b21\u4e0a\u4f20\u9644\u4ef6,\u65f6\u95f4:" + new Date().toString());
                this.uploadJioDataService.uploadSingleFiles(context, result, asyncTaskMonitor);
                if (context.getLogs().containsKey("importSecordForm")) {
                    this.uploadLogService.recordFormErrors(context, result, "importSecordForm");
                }
                if (null != asyncTaskMonitor) {
                    asyncTaskMonitor.progressAndMessage(0.9, "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
                    context.setProgress(0.9);
                    context.setNextProgressLen(0.01);
                }
            }
            result.setAttachFileNum(context.getAttachFileNum());
            context.info(logger, "\u5bfc\u5165\u7684\u6570\u636e\u9644\u4ef6\u6570\uff1a" + context.getAttachFileNum());
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.91, "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
                context.setProgress(0.91);
                context.setNextProgressLen(0.01);
            }
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u6e05\u7406\u6210\u529f\u5355\u4f4d\u7684\u8868\u5355\u4fe1\u606f,\u65f6\u95f4:" + new Date().toString());
            this.uploadLogService.clearSuccessForms(context, result);
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.92, "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
                context.setProgress(0.92);
                context.setNextProgressLen(0.01);
            }
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5220\u9664\u4efb\u52a1\u76ee\u5f55,\u65f6\u95f4:" + new Date().toString());
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5f02\u6b65\u5220\u9664\u88ab\u6807\u8bb0\u7684\u9644\u4ef6,\u65f6\u95f4:" + new Date().toString());
            this.uploadJioDataService.doDeleteNetMarkFiles(context.getDataSchemeKey());
            this.noticeService.doAfterImportNotice(context, result, netImportPeriods);
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString());
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.93, "\u751f\u6210\u7ed3\u679c\u4fe1\u606f");
                context.setProgress(0.93);
                context.setNextProgressLen(0.01);
            }
        }
        catch (Exception ex) {
            context.error(logger, ex.getMessage(), (Throwable)ex);
            throw new SingleDataException(ex.getMessage(), (Throwable)ex);
        }
        return result;
    }

    private void importSinglePeriodData(TaskDataContext context, String singlePeroid, String taskFilePath, JIOImportResultObject result, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span, int pIndex, int pCount) throws Exception {
        JioImportCancledResultParam cancelParam;
        String taskDataPath;
        this.uploadJioDataService.readUnitCount(context, param);
        context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5bfc\u5165\u5b9e\u4f53\u6570\u636e,\u65f6\u95f4:" + new Date().toString());
        try {
            this.jioImportService.importSingleEnityData(context, taskFilePath, asyncTaskMonitor);
        }
        catch (Exception ex) {
            context.error(logger, ex.getMessage(), (Throwable)ex);
            String message = this.uploadLogService.handException(ex);
            Map uploadEntityZdmKeyMap = context.getUploadEntityZdmKeyMap();
            if (uploadEntityZdmKeyMap != null && uploadEntityZdmKeyMap.size() > 0) {
                message = this.getUnitErrorMessage(message, uploadEntityZdmKeyMap.size());
                for (Map.Entry entry : uploadEntityZdmKeyMap.entrySet()) {
                    String netEntityKey = (String)uploadEntityZdmKeyMap.get(entry.getKey());
                    DataEntityInfo entiyInfo = context.getEntityCache().findEntity(netEntityKey);
                    JIOUnitImportResult unitImportResult = new JIOUnitImportResult();
                    unitImportResult.setUnitKey(netEntityKey);
                    if (entiyInfo != null) {
                        unitImportResult.setUnitCode(entiyInfo.getEntityCode());
                        unitImportResult.setUnitTitle(entiyInfo.getEntityTitle());
                    }
                    List formResults = unitImportResult.getFormResults();
                    JIOFormImportResult jioFormImportResult = new JIOFormImportResult();
                    jioFormImportResult.setFormCode("FMDM");
                    jioFormImportResult.setFormTitle("\u5c01\u9762\u4ee3\u7801");
                    jioFormImportResult.setMessage(message);
                    formResults.add(jioFormImportResult);
                    result.getErrorUnits().add(unitImportResult);
                }
            } else {
                message = this.getUnitErrorMessage(message, 0);
                JIOUnitImportResult unitImportResult = new JIOUnitImportResult();
                unitImportResult.setUnitKey("\u8be5\u6279\u6b21\u5355\u4f4d\u4e2d");
                List formResults = unitImportResult.getFormResults();
                JIOFormImportResult jioFormImportResult = new JIOFormImportResult();
                jioFormImportResult.setFormCode("FMDM");
                jioFormImportResult.setFormTitle("\u5c01\u9762\u4ee3\u7801");
                jioFormImportResult.setMessage(message);
                formResults.add(jioFormImportResult);
                result.getErrorUnits().add(unitImportResult);
            }
            result.setErrorLevel(2);
            result.getSuccessUnits().clear();
            result.setSuccess(false);
            result.setMessage(message);
            result.setSuccesssReportNum(0);
            result.setSuccesssUnitNum(0);
            result.setAllSuccesssUnitNum(0);
            result.setUploadUnitNum(context.getSingleCorpCount());
            return;
        }
        result.setNetUnitNum(context.getNetCorpCount());
        if (context.isNeedSelectImport() && context.getJioSelectImportResult() != null) {
            return;
        }
        if (context.isNeedCheckRepeat() && context.getJioRepeatResult() != null && context.getRepeatImportType() == 2) {
            return;
        }
        if (result.getErrorUnits().size() == 0) {
            this.uploadLogService.recordFmdmSuccess(context, result);
        }
        if (context.getLogs().containsKey("FMDM")) {
            this.uploadLogService.recordFmdmErrors(context, result);
        }
        if (null != asyncTaskMonitor) {
            if (asyncTaskMonitor.isCancel()) {
                JioImportCancledResultParam cancelParam2 = new JioImportCancledResultParam(String.valueOf(context.getImportedFormNum()));
                asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelParam2), null);
                result.setSuccess(false);
                result.setMessage("JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                context.info(logger, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                return;
            }
            context.setProgress(begin + 0.19 + 0.6 * (double)(pIndex - 1) / (double)pCount + 0.2 / (double)pCount);
            context.setNextProgressLen(0.4 / (double)pCount);
            asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165\uff0c\u6570\u636e\u66f4\u65b0");
        }
        context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5904\u7406\u5355\u4f4d,\u65f6\u95f4:" + new Date().toString());
        DimensionValue corpDim = (DimensionValue)param.getDimensionSet().get(context.getEntityCompanyType());
        if (null != corpDim) {
            String corpCode;
            if (StringUtils.isEmpty((String)corpDim.getValue())) {
                corpCode = "";
                if (context.getEntityFieldIsCode()) {
                    if (context.getEntityCache().getEntityList().size() > 0) {
                        corpCode = ((DataEntityInfo)context.getEntityCache().getEntityList().get(0)).getEntityCode();
                    }
                } else if (context.getEntityKeyZdmMap().size() > 0) {
                    String[] keys = context.getEntityKeyZdmMap().keySet().toArray(new String[0]);
                    corpCode = keys[0];
                }
                if (StringUtils.isNotEmpty((String)corpCode)) {
                    corpDim.setValue(corpCode);
                }
            } else {
                corpCode = corpDim.getValue();
                corpDim.setValue(this.taskDataService.getNetCompanyKey(context, corpCode));
            }
        }
        context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5904\u7406\u5355\u4f4d\u6743\u9650\u548c\u4e0a\u62a5\u72b6\u6001,\u65f6\u95f4:" + new Date().toString());
        this.uploadJioDataService.recordUnitState(context, result, param);
        context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5bfc\u5165\u62a5\u8868\u6570\u636e,\u65f6\u95f4:" + new Date().toString());
        if (context.getDataOption().isUploadReportData()) {
            Map uploadEntityZdmKeyMap = context.getUploadEntityZdmKeyMap();
            if (uploadEntityZdmKeyMap.size() > 0) {
                this.importSingleReportToNetData(context, taskFilePath, param, asyncTaskMonitor, result);
            } else {
                context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u9700\u8981\u5bfc\u5165\u62a5\u8868\u6570\u636e\u7684\u5355\u4f4d\u6570\u4e3a0");
                result.setErrorUnitNum(result.getErrorUnits().size());
                List errorUnits = result.getErrorUnits();
                for (JIOUnitImportResult jioUnitImportResult : errorUnits) {
                    Map formMapResults = jioUnitImportResult.getFormMapResults();
                    List formResults = jioUnitImportResult.getFormResults();
                    for (Map.Entry entry : formMapResults.entrySet()) {
                        formResults.add(entry.getValue());
                    }
                    jioUnitImportResult.setFormMapResults(new HashMap());
                    result.setErrorReportNum(result.getErrorReportNum() + formResults.size());
                }
                result.setSuccess(false);
                result.setSuccesssReportNum(0);
                result.setSuccesssUnitNum(0);
                result.setAllSuccesssUnitNum(0);
                result.setUploadUnitNum(context.getSingleCorpCount());
            }
        }
        if (StringUtils.isEmpty((String)(taskDataPath = context.getTaskDataPath()))) {
            taskDataPath = PathUtil.createNewPath((String)taskFilePath, (String)"DATA");
        }
        if (context.getDataOption().isUploadCheckData() && context.isNeedCheckInfo() && result.getSuccesssUnitNum() > 0) {
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5bfc\u5165\u51fa\u9519\u8bf4\u660e,\u65f6\u95f4:" + new Date().toString());
            if (asyncTaskMonitor != null) {
                if (asyncTaskMonitor.isCancel()) {
                    cancelParam = new JioImportCancledResultParam(String.valueOf(context.getImportedFormNum()));
                    asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                    result.setSuccess(false);
                    result.setMessage("JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    context.info(logger, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    return;
                }
                context.setProgress(context.getProgress() + 0.01);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u51fa\u9519\u8bf4\u660e\u5bfc\u5165");
            }
            this.singleDataCheckService.ImportCheckDataFromCache(context, taskDataPath, param.getDimensionSet(), null, null, asyncTaskMonitor);
        }
        if (context.getDataOption().isUploadEntityCheck() && result.getSuccesssUnitNum() > 0) {
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5bfc\u5165\u6237\u6570\u6838\u5bf9,\u65f6\u95f4:" + new Date().toString());
            if (asyncTaskMonitor != null) {
                if (asyncTaskMonitor.isCancel()) {
                    cancelParam = new JioImportCancledResultParam(String.valueOf(context.getImportedFormNum()));
                    asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                    result.setSuccess(false);
                    result.setMessage("JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    context.info(logger, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    return;
                }
                context.setProgress(context.getProgress() + 0.01);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u6237\u6570\u6838\u5bf9\u5bfc\u5165");
            }
            this.singleEntityCheckService.importEntityCheckResult(context, taskDataPath, param.getDimensionSet(), asyncTaskMonitor);
        }
        if (context.getDataOption().isUploadQueryCheck() && result.getSuccesssUnitNum() > 0) {
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5bfc\u5165\u67e5\u8be2\u6a21\u7248\u5ba1\u6838,\u65f6\u95f4:" + new Date().toString());
            if (asyncTaskMonitor != null) {
                if (asyncTaskMonitor.isCancel()) {
                    cancelParam = new JioImportCancledResultParam(String.valueOf(context.getImportedFormNum()));
                    asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                    result.setSuccess(false);
                    result.setMessage("JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    context.info(logger, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    return;
                }
                context.setProgress(context.getProgress() + 0.01);
                asyncTaskMonitor.progressAndMessage(context.getProgress(), "\u67e5\u8be2\u6a21\u7248\u5ba1\u6838\u8bf4\u660e\u5bfc\u5165");
            }
            this.uploadQueryCheckService.importQueryCheckResult(context, taskDataPath, param.getDimensionSet(), asyncTaskMonitor);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public JIOImportResultObject importSingleReportToNetData(TaskDataContext importContext, String path, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, JIOImportResultObject resObject) throws Exception {
        String dataPath;
        Map<String, JIOUnitImportResult> successMap;
        Map<String, JIOUnitImportResult> errorMap;
        block16: {
            SingleUploadLogManager uploadLog = new SingleUploadLogManager();
            errorMap = uploadLog.getErrorUnits();
            successMap = uploadLog.getSuccessUnits();
            for (JIOUnitImportResult jioUnitImportResult : resObject.getSuccessUnits()) {
                successMap.put(null != jioUnitImportResult.getUnitKey() ? jioUnitImportResult.getUnitKey() : jioUnitImportResult.getUnitTitle(), jioUnitImportResult);
            }
            for (JIOUnitImportResult jioUnitImportResult : resObject.getErrorUnits()) {
                errorMap.put(null != jioUnitImportResult.getUnitKey() ? jioUnitImportResult.getUnitKey() : jioUnitImportResult.getUnitTitle(), jioUnitImportResult);
            }
            dataPath = PathUtil.createNewPath((String)path, (String)"DATA");
            resObject.setSuccess(true);
            List singleTables = null;
            if (null != importContext.getMapingCache().getMapConfig()) {
                singleTables = importContext.getMapingCache().getMapConfig().getTableInfos();
            }
            if (singleTables == null) {
                return resObject;
            }
            importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u8868\u5355:" + new Date().toString());
            List forms = this.runtimeView.queryAllFormDefinesByFormScheme(importContext.getFormSchemeKey());
            if (forms.isEmpty()) {
                return resObject;
            }
            Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
            TaskFileDataOperateUtil.maintainSingleDbfs((TaskDataContext)importContext, (String)dataPath, (Map)uploadEntityZdmKeyMap);
            importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u6839\u636eDBF\u8fc7\u6ee4\u65e0\u6570\u636e\u7684\u8868\u5355:" + new Date().toString());
            List dataforms = TaskFileDataOperateUtil.getExistSingleDataForms((TaskDataContext)importContext, (String)dataPath, (List)forms);
            if (dataforms.isEmpty()) {
                return resObject;
            }
            ArrayList<String> formKeys = new ArrayList<String>();
            for (FormDefine formDefine : dataforms) {
                formKeys.add(formDefine.getKey());
            }
            double addProgress = 0.0;
            if (formKeys.size() > 0) {
                addProgress = importContext.getNextProgressLen() / (double)formKeys.size();
            }
            importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3b\u4f53:" + new Date().toString());
            importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u83b7\u53d6\u6743\u9650\u8bbe\u7f6e:" + new Date().toString());
            HashMap<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap = new HashMap<String, Map<String, IAccessResult>>();
            if (this.jioDataUseAuth) {
                this.uploadJioDataService.initReportAccessAuthCache(importContext, param, formKeys, entityKeyFormReadWritesMap, uploadEntityZdmKeyMap);
            } else {
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u672a\u542f\u7528\u6743\u9650\u5224\u65ad");
                this.singleDimService.judgeAndUseTempTable(importContext);
            }
            try {
                int importFormTreadCorpCount = this.singleOptionService.getUploadTreadStartOrgCount();
                if (uploadEntityZdmKeyMap.size() > importFormTreadCorpCount && dataforms.size() > 1) {
                    this.importSingleFormsToNetDataByThreas(importContext, dataPath, dataforms, addProgress, uploadLog, entityKeyFormReadWritesMap, param, asyncTaskMonitor, resObject);
                    break block16;
                }
                for (FormDefine formDefine : dataforms) {
                    if (null != asyncTaskMonitor) {
                        importContext.addProgress(addProgress);
                        asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "\u62a5\u8868\u6570\u636e\u5bfc\u5165");
                    }
                    this.importSingleFormToNetData(importContext, dataPath, formDefine, uploadLog, entityKeyFormReadWritesMap, param, asyncTaskMonitor, resObject);
                    if (null == asyncTaskMonitor || !asyncTaskMonitor.isCancel()) continue;
                    JioImportCancledResultParam cancelParam = new JioImportCancledResultParam(String.valueOf(importContext.getImportedFormNum()));
                    asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                    importContext.info(logger, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    resObject.setSuccess(false);
                    resObject.setMessage("JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                    break;
                }
            }
            finally {
                this.singleDimService.judgeAndFreeTempTable(importContext);
            }
        }
        this.uploadLogService.setAfterImportSingleReportLog(importContext, resObject, dataPath, successMap, errorMap, param.getDimensionSet());
        errorMap = null;
        errorMap = null;
        return resObject;
    }

    private void importSingleFormsToNetDataByThreas(TaskDataContext importContext, String dataPath, List<FormDefine> dataforms, double addProgress, SingleUploadLogManager uploadLog, Map<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, JIOImportResultObject resObject) throws Exception {
        Iterator<Map.Entry<String, Map<String, IAccessResult>>> iterator = entityKeyFormReadWritesMap.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Map<String, IAccessResult>> entry = iterator.next();
            IAccessResult access = entry.getValue().get(dataforms.get(0).getKey());
            access.haveAccess();
        }
        List<FormShareItem> shareFormItems = this.getFormShareList(importContext, dataforms);
        int threadNum = this.singleOptionService.getUploadTreadCount();
        if (threadNum > shareFormItems.size()) {
            threadNum = shareFormItems.size();
        }
        if (threadNum < 0) {
            threadNum = 3;
        } else if (threadNum > 100) {
            threadNum = 100;
        }
        importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u8868\u5355\u5bfc\u5165\u7ebf\u7a0b\u6570\uff1a" + String.valueOf(threadNum));
        String mainTheadName = Thread.currentThread().getName();
        FormShareManager queueManager = new FormShareManager();
        for (int i = 0; i < shareFormItems.size(); ++i) {
            FormShareItem shareItem = shareFormItems.get(i);
            queueManager.getQueue().offer(shareItem);
        }
        ArrayList<Future> resList = new ArrayList<Future>();
        int i = 0;
        while (i < threadNum) {
            int threadId = i++;
            Future threadResult = this.npApplication.asyncRun(() -> {
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u8868\u5355\u5bfc\u5165\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + ",\u5f00\u59cb");
                block0: while (!queueManager.getQueue().isEmpty()) {
                    List<FormDefine> threadForms = queueManager.getThreadFormsAsyn();
                    for (FormDefine formDefine : threadForms) {
                        importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u8868\u5355\u5bfc\u5165\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + ",\u8868\u5355\u6807\u8bc6\uff1a" + formDefine.getFormCode());
                        this.updateProgressAsyn(importContext, asyncTaskMonitor, addProgress);
                        this.importSingleFormToNetData(importContext, dataPath, formDefine, uploadLog, entityKeyFormReadWritesMap, param, asyncTaskMonitor, resObject);
                        if (asyncTaskMonitor == null || !asyncTaskMonitor.isCancel()) continue;
                        importContext.info(logger, "JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                        JioImportCancledResultParam cancelParam = new JioImportCancledResultParam(String.valueOf(importContext.getImportedFormNum()));
                        asyncTaskMonitor.canceled(JsonUtil.objectToJson((Object)cancelParam), null);
                        resObject.setSuccess(false);
                        resObject.setMessage("JIO\u6570\u636e\u5bfc\u5165\u5df2\u53d6\u6d88");
                        continue block0;
                    }
                }
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u8868\u5355\u5bfc\u5165\u7ebf\u7a0b " + mainTheadName + "_SUB" + threadId + ",\u5b8c\u6210");
                return 1;
            });
            resList.add(threadResult);
        }
        for (i = 0; i < resList.size(); ++i) {
            try {
                Future res = (Future)resList.get(i);
                res.get();
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u8868\u5355\u5bfc\u5165\u7ebf\u7a0b" + mainTheadName + "_SUB" + i + ",\u8fd4\u56de");
                continue;
            }
            catch (Exception e) {
                importContext.error(logger, e.getMessage(), (Throwable)e);
                throw e;
            }
        }
        importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u8868\u5355\u5bfc\u5165\u7ebf\u7a0b\uff0c\u6240\u6709\u7ebf\u7a0b\u8fd4\u56de\u7ed3\u679c");
    }

    private void updateProgressAsyn(TaskDataContext importContext, AsyncTaskMonitor asyncTaskMonitor, double addProgress) {
        importContext.updateProgressAsyn(importContext, asyncTaskMonitor, addProgress);
    }

    private void importSingleFormToNetData(TaskDataContext importContext, String dataPath, FormDefine formDefine, SingleUploadLogManager uploadLog, Map<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, JIOImportResultObject resObject) {
        block28: {
            Map<String, JIOUnitImportResult> successMap = uploadLog.getSuccessUnits();
            Map<String, JIOUnitImportResult> errorMap = uploadLog.getErrorUnits();
            importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a===\u8868\u5355:" + formDefine.getFormCode() + "," + formDefine.getTitle() + "," + Math.round(importContext.getProgress() * 100.0) + "%,\u65f6\u95f4:" + new Date().toString());
            Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
            if (formDefine.getKey().equals(importContext.getFmdmFormKey())) {
                boolean fmdmIsData = formDefine.getFormType() == FormType.FORM_TYPE_FIX || StringUtils.isEmpty((String)formDefine.getMasterEntitiesKey());
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(importContext.getFormSchemeKey());
                if (formScheme != null && StringUtils.isNotEmpty((String)formScheme.getMasterEntitiesKey()) && StringUtils.isNotEmpty((String)formDefine.getMasterEntitiesKey())) {
                    fmdmIsData = formScheme.getMasterEntitiesKey().equalsIgnoreCase(formDefine.getMasterEntitiesKey());
                }
                if (!fmdmIsData) {
                    return;
                }
                if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                    return;
                }
            } else if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                return;
            }
            String netFormCode = formDefine.getFormCode();
            if (!importContext.getMapingCache().getNetFieldMap().containsKey(netFormCode)) {
                return;
            }
            RepeatFormNode selectFormNode = (RepeatFormNode)importContext.getSelectFormNodes().get(formDefine.getKey());
            if (selectFormNode != null ? selectFormNode.getRepeatMode() == 0 : !importContext.getSelectFormNodes().isEmpty()) {
                return;
            }
            RepeatFormNode repeatFormNode = (RepeatFormNode)importContext.getRepeatFormNodes().get(formDefine.getKey());
            if (repeatFormNode != null && repeatFormNode.getRepeatMode() == 0) {
                return;
            }
            List regions = this.jtableParamService.getRegions(formDefine.getKey());
            try {
                RegionData fixRegionData = null;
                ArrayList<RegionData> FloatReigonDataList = new ArrayList<RegionData>();
                for (RegionData aRegion : regions) {
                    if (aRegion.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        fixRegionData = aRegion;
                        continue;
                    }
                    FloatReigonDataList.add(aRegion);
                }
                if (FloatReigonDataList.size() > 1) {
                    Collections.sort(FloatReigonDataList, new Comparator<RegionData>(){

                        @Override
                        public int compare(RegionData o1, RegionData o2) {
                            int comValue = 0;
                            if (o1.getRegionTop() > o2.getRegionTop()) {
                                comValue = 1;
                            } else if (o1.getRegionTop() < o2.getRegionTop()) {
                                comValue = -1;
                            }
                            return comValue;
                        }
                    });
                }
                boolean isImportByUnitStatus = false;
                DataImportRule dataStatusRule = this.getDataImportRuleByFlowType(importContext);
                if (dataStatusRule != null) {
                    isImportByUnitStatus = dataStatusRule.isEnable();
                }
                if (uploadEntityZdmKeyMap.size() > 0 || formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT || isImportByUnitStatus) {
                    RegionData regionData = fixRegionData;
                    this.importSingleRegionToNetDataBatch(uploadLog, importContext, formDefine, regionData, dataPath, param, entityKeyFormReadWritesMap, asyncTaskMonitor);
                    for (int j = 0; j < FloatReigonDataList.size(); ++j) {
                        RegionData subRegionData = (RegionData)FloatReigonDataList.get(j);
                        this.importSingleRegionToNetDataBatch(uploadLog, importContext, formDefine, subRegionData, dataPath, param, entityKeyFormReadWritesMap, asyncTaskMonitor);
                    }
                    importContext.updatImportedFormNumAsyn(1);
                } else {
                    Map<String, DimensionValue> dimensionValueSet = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
                    DimensionValue entityDim = dimensionValueSet.get(importContext.getEntityCompanyType());
                    String entityKey = null;
                    boolean formHasAuth = false;
                    if (uploadEntityZdmKeyMap.size() > 0) {
                        for (Map.Entry entityEntry : uploadEntityZdmKeyMap.entrySet()) {
                            String netEntityKey = (String)entityEntry.getValue();
                            if (importContext.getDimEntityCache().getEntitySingleDimList().size() > 0 && importContext.getDimEntityCache().getEntitySingleDimList().containsKey(netEntityKey)) {
                                Map singleUnitDim = (Map)importContext.getDimEntityCache().getEntitySingleDimList().get(netEntityKey);
                                for (String dimName : importContext.getDimEntityCache().getEntitySingleDims()) {
                                    DimensionValue setValue = dimensionValueSet.get(dimName);
                                    DimensionValue getValue = (DimensionValue)singleUnitDim.get(dimName);
                                    if (getValue == null || setValue == null) continue;
                                    setValue.setValue(getValue.getValue());
                                }
                            }
                            if (!this.judgeformAuth(uploadLog, formDefine, netEntityKey, entityKeyFormReadWritesMap, dataStatusRule, false)) continue;
                            formHasAuth = true;
                            entityKey = (String)entityEntry.getValue();
                            break;
                        }
                    }
                    if (formHasAuth) {
                        if (entityDim != null && StringUtils.isNotEmpty((String)entityDim.getValue()) && StringUtils.isNotEmpty(entityKey) && !entityDim.getValue().equalsIgnoreCase(entityKey)) {
                            entityDim.setValue(entityKey);
                        }
                        JtableContext jtableContext = new JtableContext();
                        jtableContext.setTaskKey(param.getTaskKey());
                        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
                        jtableContext.setDimensionSet(dimensionValueSet);
                        jtableContext.setFormKey(formDefine.getKey());
                        Map variableMap = jtableContext.getVariableMap();
                        variableMap.put("isAppending", param.isAppending());
                        IReportImportDataSet reportImportData = this.jtableResourceService.getReportImportData(jtableContext);
                        RegionData regionData = fixRegionData;
                        this.importSingleRegionToNetData(uploadLog, importContext, formDefine, reportImportData, regionData, dataPath, param, entityKeyFormReadWritesMap);
                        for (int j = 0; j < FloatReigonDataList.size(); ++j) {
                            RegionData subRegionData = (RegionData)FloatReigonDataList.get(j);
                            this.importSingleRegionToNetData(uploadLog, importContext, formDefine, reportImportData, subRegionData, dataPath, param, entityKeyFormReadWritesMap);
                        }
                        importContext.updatImportedFormNumAsyn(1);
                    } else {
                        importContext.info(logger, "\u5bfc\u5165\u6570\u636e\uff1a\u8868\u5355\u65e0\u6743\u9650\u5199\u5165");
                    }
                }
                if (!importContext.getLogs().containsKey(formDefine.getFormCode()) || "FMDM".equalsIgnoreCase(formDefine.getFormCode())) break block28;
                List errList = (List)importContext.getLogs().get(formDefine.getFormCode());
                for (SingleDataError error : errList) {
                    if (!"importFileFail".equalsIgnoreCase(error.getErrorCode())) continue;
                    uploadLog.addErrorForm(formDefine, error.getCompanyKey(), null, error.getErrorInfo());
                }
            }
            catch (Exception e) {
                String message = this.uploadLogService.handException(e);
                if (!StringUtils.isEmpty((String)message) && message.contains("\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6")) break block28;
                importContext.error(logger, "\u5bfc\u5165\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
                message = this.getUnitErrorMessage(message, uploadEntityZdmKeyMap.size());
                for (Map.Entry entry : uploadEntityZdmKeyMap.entrySet()) {
                    String netEntityKey = (String)uploadEntityZdmKeyMap.get(entry.getKey());
                    uploadLog.addErrorForm(formDefine, netEntityKey, null, message);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void importSingleRegionToNetData(SingleUploadLogManager uploadLog, TaskDataContext importContext, FormDefine formDefine, IReportImportDataSet reportImportData, RegionData regionData, String dbfFilePath, UploadParam param, Map<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap) throws Exception {
        Map<String, JIOUnitImportResult> successMap = uploadLog.getSuccessUnits();
        Map<String, JIOUnitImportResult> errorMap = uploadLog.getErrorUnits();
        Map netFieldMaps = importContext.getMapingCache().getNetFieldMap();
        Map netFieldMap = null;
        IRegionImportDataSet regionImportDataSet = reportImportData.getRegionImportDataSet(regionData);
        RegionImportDataSetImpl newRegionImportDataSet = (RegionImportDataSetImpl)regionImportDataSet;
        newRegionImportDataSet.setSaveFileGroupKey(true);
        List oldFieldDefineList = regionImportDataSet.getFieldDataList();
        List oldFieldLinks = regionImportDataSet.getLinkDataList();
        List fieldDefineList = this.commonImportService.copyFieldsFromLink(oldFieldDefineList, oldFieldLinks);
        if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM && StringUtils.isNotEmpty((String)importContext.getDwEntityId())) {
            String entityName = EntityUtils.getId((String)importContext.getDwEntityId());
            for (FieldData field : fieldDefineList) {
                if (!StringUtils.isEmpty((String)field.getTableName())) continue;
                field.setTableName(entityName);
            }
        }
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        if (!fieldDefineList.isEmpty()) {
            ReportRegionDataSetList olddataSetList;
            if (null != reportImportData && netFieldMaps.containsKey(reportImportData.getFormData().getCode())) {
                netFieldMap = (Map)netFieldMaps.get(reportImportData.getFormData().getCode());
            }
            if (netFieldMap == null) {
                return;
            }
            int floatingRow = regionData.getRegionTop();
            if (regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                floatingRow = -1;
            }
            try (ReportRegionDataSetList dataSetList = olddataSetList = TaskFileDataOperateUtil.getRegionDataSetList((TaskDataContext)importContext, (String)dbfFilePath, (String)reportImportData.getFormData().getCode(), (List)fieldDefineList, (int)floatingRow);){
                if (olddataSetList.isVirtualFloat()) {
                    if (dataSetList.isDataEmpty() && !importContext.isDeleteEmptyData()) {
                        return;
                    }
                    dataSetList = olddataSetList.getVirtualDatasets();
                    TaskFileDataOperateUtil.ConvertDataSetListTovirtual((ReportRegionDataSetList)olddataSetList);
                }
                if (dataSetList.isDataEmpty() && !importContext.isDeleteEmptyData()) {
                    return;
                }
                HashMap<String, FieldData> fieldDefineMap = new HashMap<String, FieldData>();
                for (FieldData netField : fieldDefineList) {
                    String fieldFlag = netField.getTableName() + "." + netField.getFieldCode();
                    fieldDefineMap.put(fieldFlag, netField);
                }
                Map repeatEntityNodes = importContext.getRepeatEntityNodes();
                Map repeatFormNodes = importContext.getRepeatFormNodes();
                RepeatFormNode repeatFormNode = (RepeatFormNode)repeatFormNodes.get(formDefine.getKey());
                if (repeatFormNode == null || floatingRow > 0) {
                    // empty if block
                }
                List<SingleFieldFileInfo> fjFieldList = this.uploadJioDataService.getFieldFileInfosFromDataSet(importContext, dataSetList, fieldDefineMap);
                DataImportRule dataStatusRule = this.getDataImportRuleByFlowType(importContext);
                ArrayList<String> deleteRegionUnitCodes = new ArrayList<String>();
                CaseInsensitiveMap<String, Map<String, List<Map<String, DimensionValue>>>> zdmFloatCodeDims = new CaseInsensitiveMap<String, Map<String, List<Map<String, DimensionValue>>>>();
                MemoryDataSet dataRowSet = new MemoryDataSet();
                this.batchMarkDelete(importContext, uploadLog, formDefine, entityKeyFormReadWritesMap, dataStatusRule, fjFieldList, fieldDefineMap, param.getDimensionSet(), dataSetList);
                for (String zdm : uploadEntityZdmKeyMap.keySet()) {
                    String netEntityKey = (String)uploadEntityZdmKeyMap.get(zdm);
                    try {
                        String bjZdm;
                        RepeatEntityNode entityReetNode;
                        logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u8868\u5355:" + formDefine.getFormCode() + "," + formDefine.getTitle() + ",\u5b9e\u4f53" + netEntityKey + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                        if (repeatEntityNodes != null && repeatEntityNodes.containsKey(zdm) && ((entityReetNode = (RepeatEntityNode)repeatEntityNodes.get(zdm)).getRepeatMode() == 0 || repeatFormNode != null && repeatFormNode.getRepeatMode() == 0) || !this.judgeformAuth(uploadLog, formDefine, netEntityKey, entityKeyFormReadWritesMap, dataStatusRule, true)) continue;
                        Map<String, DimensionValue> dimensionValueSet = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
                        this.setSingleDimsByEntityKey(importContext, dimensionValueSet, netEntityKey);
                        SingleFieldFileInfo formFileInfo = new SingleFieldFileInfo();
                        formFileInfo.setDataSchemeKey(importContext.getDataSchemeKey());
                        formFileInfo.setFormKey(formDefine.getKey());
                        formFileInfo.setFormCode(formDefine.getFormCode());
                        formFileInfo.setFormSchemeKey(importContext.getFormSchemeKey());
                        formFileInfo.setDimensionSet(UploadJioDataUtil.getNewDimensionSet(dimensionValueSet));
                        formFileInfo.setTaskKey(importContext.getTaskKey());
                        formFileInfo.setEntityKey(netEntityKey);
                        JtableContext jtableContext = new JtableContext();
                        Map variableMap = jtableContext.getVariableMap();
                        variableMap.put("isAppending", param.isAppending());
                        jtableContext.setTaskKey(param.getTaskKey());
                        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
                        jtableContext.setDimensionSet(dimensionValueSet);
                        jtableContext.setFormKey(formDefine.getKey());
                        reportImportData = this.jtableResourceService.getReportImportData(jtableContext);
                        regionImportDataSet = reportImportData.getRegionImportDataSet(regionData);
                        newRegionImportDataSet = (RegionImportDataSetImpl)regionImportDataSet;
                        newRegionImportDataSet.setSaveFileGroupKey(true);
                        oldFieldDefineList = regionImportDataSet.getFieldDataList();
                        fieldDefineList = this.commonImportService.copyFields(oldFieldDefineList);
                        ArrayList<String> uploadZdms = new ArrayList<String>();
                        uploadZdms.add(zdm);
                        if (floatingRow > 0 && importContext.getEntityMergeZdmMap().containsKey(zdm)) {
                            uploadZdms.addAll((Collection)importContext.getEntityMergeZdmMap().get(zdm));
                        } else if (floatingRow <= 0 && importContext.getEntityMergeBenJiMap().containsKey(zdm) && StringUtils.isNotEmpty((String)(bjZdm = (String)importContext.getEntityMergeBenJiMap().get(zdm)))) {
                            uploadZdms.clear();
                            uploadZdms.add((String)importContext.getEntityMergeBenJiMap().get(zdm));
                        }
                        HashSet<String> rowEmptyZdms = new HashSet<String>();
                        for (String dbfZdm : uploadZdms) {
                            ArrayList floatCodeValueList = new ArrayList();
                            ArrayList<String> floatOrderList = new ArrayList<String>();
                            dataSetList.locateDataRowByZdm(dbfZdm);
                            if (!dataSetList.getDataRowIsNew()) {
                                for (int i = 0; i < dataSetList.getFloatRowsCount(); ++i) {
                                    dataSetList.locateDataRowByFloatIndex(i);
                                    if (floatingRow > 0) {
                                        floatCodeValueList.add(((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getFloatCodeValues());
                                        floatOrderList.add(((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getFloatOrder());
                                    }
                                    Object[] rowData = new Object[fieldDefineList.size()];
                                    try {
                                        this.uploadJioDataService.setDataBeforeImport(importContext, dataSetList, dbfZdm, formFileInfo, fieldDefineMap);
                                        UploadJioDataUtil.setImportRowValueByFields(importContext, fieldDefineList, rowData, dataSetList, dbfZdm);
                                        dataRowSet.add(rowData);
                                        continue;
                                    }
                                    catch (DataSetException e) {
                                        importContext.error(logger, e.getMessage(), (Throwable)e);
                                        throw e;
                                    }
                                }
                            } else if (importContext.isDeleteEmptyData()) {
                                rowEmptyZdms.add(dbfZdm);
                                if (floatingRow < 0) {
                                    Object[] rowData = new Object[fieldDefineList.size()];
                                    for (int k = 0; k < rowData.length; ++k) {
                                        rowData[k] = "";
                                    }
                                    UploadJioDataUtil.setImportRowValueByFields(importContext, fieldDefineList, rowData, dataSetList, dbfZdm);
                                    dataRowSet.add(rowData);
                                }
                            }
                            if (dataRowSet.size() <= 0) continue;
                            ImportResultRegionObject importResultRegionObject = regionImportDataSet.importDataRowSet(dataRowSet);
                            if (importResultRegionObject != null && !importResultRegionObject.getImportErrorDataInfoList().isEmpty()) {
                                ImportErrorDataInfo errorDataInfo = (ImportErrorDataInfo)importResultRegionObject.getImportErrorDataInfoList().get(0);
                                uploadLog.addErrorForm(formDefine, netEntityKey, null, errorDataInfo.getDataError().getErrorInfo());
                            } else {
                                regionImportDataSet.commitRangeData();
                                uploadLog.addSuccessForm(formDefine, netEntityKey);
                            }
                            dataRowSet.clear();
                            if (floatingRow <= 0) continue;
                            List recResult = importResultRegionObject.getImportDataDimensionValues();
                            CaseInsensitiveMap floatCodeDims = new CaseInsensitiveMap();
                            List<Map> floatCodeDimList = null;
                            for (int i = 0; i < recResult.size(); ++i) {
                                String floatCode = (String)floatCodeValueList.get(i);
                                String floatOrder = (String)floatOrderList.get(i);
                                String floatUnitCode = "";
                                if (StringUtils.isEmpty((String)floatCode)) {
                                    floatUnitCode = dbfZdm + "_" + floatOrder;
                                    if (StringUtils.isEmpty((String)floatOrder)) {
                                        floatUnitCode = dbfZdm + "_" + i + "";
                                    }
                                } else {
                                    floatUnitCode = dbfZdm + "_" + floatCode + '_' + floatOrder;
                                }
                                if (floatCodeDims.containsKey(floatUnitCode)) {
                                    floatCodeDimList = (List)floatCodeDims.get(floatUnitCode);
                                } else {
                                    floatCodeDimList = new ArrayList();
                                    floatCodeDims.put(floatUnitCode, floatCodeDimList);
                                }
                                Map floatDim = (Map)recResult.get(i);
                                floatCodeDimList.add(floatDim);
                            }
                            zdmFloatCodeDims.put(dbfZdm, floatCodeDims);
                        }
                        if (importContext.isDeleteEmptyData() && rowEmptyZdms.size() == uploadZdms.size() && floatingRow > 0) {
                            deleteRegionUnitCodes.add(netEntityKey);
                        }
                        if (floatingRow <= 0 || !param.isAppending() || fjFieldList.isEmpty()) continue;
                        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                        Map<String, DimensionValue> dimensionSet = dimensionValueSet;
                        for (String key : dimensionSet.keySet()) {
                            dimensionCombinationBuilder.setValue(key, (Object)dimensionSet.get(key).getValue());
                        }
                        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
                        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(UUID.randomUUID().toString(), this.cacheObjectResourceRemote);
                        this.uploadFileService.incrementDeleteMarkFile(importContext.getDataSchemeKey(), dimensionCombination, regionData.getKey(), (AsyncTaskMonitor)asyncTaskMonitor);
                    }
                    catch (Exception e) {
                        importContext.error(logger, e.getMessage(), (Throwable)e);
                        String message = this.uploadLogService.handException(e);
                        message = this.getUnitErrorMessage(message, 0);
                        uploadLog.addErrorForm(formDefine, netEntityKey, null, message);
                    }
                }
                if (importContext.getDataOption().isUploadCheckData() && importContext.isNeedCheckInfo()) {
                    this.singleDataCheckService.ImportCheckDataFromCache(importContext, dbfFilePath, param.getDimensionSet(), formDefine.getKey(), zdmFloatCodeDims, null);
                }
                this.uploadJioDataService.deleteFloatRegionData(importContext, param, deleteRegionUnitCodes, formDefine.getKey(), regionData.getKey(), floatingRow);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void importSingleRegionToNetDataBatch(SingleUploadLogManager uploadLog, TaskDataContext importContext, FormDefine formDefine, RegionData regionData, String dbfFilePath, UploadParam param, Map<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        Map<String, JIOUnitImportResult> successMap = uploadLog.getSuccessUnits();
        Map<String, JIOUnitImportResult> errorMap = uploadLog.getErrorUnits();
        Map netFieldMaps = importContext.getMapingCache().getNetFieldMap();
        Map netFieldMap = null;
        List fieldDefineList = this.commonImportService.getFieldDatasByRegion(regionData.getKey());
        HashMap<String, TableDefine> fieldDefinMapTables = new HashMap<String, TableDefine>();
        HashMap<String, FieldData> fieldDefinMapFields = new HashMap<String, FieldData>();
        HashMap<String, FieldData> fieldDefinMapFields2 = new HashMap<String, FieldData>();
        HashMap<String, TableDefine> tableMaps = new HashMap<String, TableDefine>();
        for (FieldData fieldData : fieldDefineList) {
            String fieldFlag = fieldData.getTableName() + "." + fieldData.getFieldCode();
            TableDefine table = null;
            if (tableMaps.containsKey(fieldData.getOwnerTableKey())) {
                table = (TableDefine)tableMaps.get(fieldData.getOwnerTableKey());
            } else {
                table = this.dataRuntimeController.queryTableDefine(fieldData.getOwnerTableKey());
                tableMaps.put(fieldData.getOwnerTableKey(), table);
            }
            String fieldFlag2 = fieldFlag;
            if (table != null) {
                fieldFlag2 = table.getCode() + "." + fieldData.getFieldCode();
                fieldData.setTableName(table.getCode());
                fieldDefinMapTables.put(fieldFlag2, table);
                fieldDefinMapFields2.put(fieldFlag2, fieldData);
            }
            fieldDefinMapFields.put(fieldFlag, fieldData);
        }
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        if (!fieldDefineList.isEmpty()) {
            ReportRegionDataSetList olddataSetList;
            if (null != formDefine && netFieldMaps.containsKey(formDefine.getFormCode())) {
                netFieldMap = (Map)netFieldMaps.get(formDefine.getFormCode());
            }
            if (netFieldMap == null) {
                return;
            }
            int floatingRow = regionData.getRegionTop();
            if (regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                floatingRow = -1;
            }
            ReportRegionDataSetList dataSetList = olddataSetList = TaskFileDataOperateUtil.getRegionDataSetList((TaskDataContext)importContext, (String)dbfFilePath, (String)formDefine.getFormCode(), (List)fieldDefineList, (int)floatingRow);
            if (olddataSetList.isVirtualFloat()) {
                if (dataSetList.isDataEmpty() && !importContext.isDeleteEmptyData()) {
                    return;
                }
                dataSetList = olddataSetList.getVirtualDatasets();
                TaskFileDataOperateUtil.ConvertDataSetListTovirtual((ReportRegionDataSetList)olddataSetList);
            }
            if (dataSetList.isDataEmpty() && !importContext.isDeleteEmptyData()) {
                return;
            }
            if (dataSetList.getDataSetList().size() > 0 && ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getDataSet().getDataRowCount() > 10000) {
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u52a0\u8f7dDBF\u6570\u636e");
            } else if (dataSetList.getDataSetList().size() == 0) {
                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5b58\u5728\u9519\u8bef\u7684\u6307\u6807\u6620\u5c04\uff0c\u8bf7\u6838\u5b9e\uff0c" + String.valueOf(floatingRow));
            }
            HashMap<String, FieldData> fieldDefineMap = new HashMap<String, FieldData>();
            for (FieldData netField : fieldDefineList) {
                String fieldFlag = netField.getTableName() + "." + netField.getFieldCode();
                fieldDefineMap.put(fieldFlag, netField);
            }
            ArrayList<String> fieldsArr = new ArrayList<String>();
            ArrayList<String> dimFields = new ArrayList<String>();
            ArrayList<FieldData> fieldDefineList2 = new ArrayList<FieldData>();
            for (DimensionValue dim : param.getDimensionSet().values()) {
                fieldsArr.add(dim.getName());
                dimFields.add(dim.getName());
            }
            Iterator<Object> iterator = dataSetList.getFieldMap().keySet().iterator();
            while (iterator.hasNext()) {
                FieldData oldFieldData;
                String fieldFlag;
                String fieldCode = fieldFlag = (String)iterator.next();
                String tableCode = "";
                int id = fieldFlag.indexOf(".");
                if (id > 0) {
                    tableCode = fieldFlag.substring(0, id);
                    fieldCode = fieldFlag.substring(id + 1, fieldFlag.length());
                }
                FieldData fieldData = new FieldData();
                fieldData.setTableName(tableCode);
                fieldData.setFieldCode(fieldCode);
                fieldData.setFieldName(fieldCode);
                fieldDefineList2.add(fieldData);
                if (fieldDefinMapTables.containsKey(fieldFlag)) {
                    TableDefine table = (TableDefine)fieldDefinMapTables.get(fieldFlag);
                    String fieldFalg2 = table.getCode() + "." + fieldCode;
                    fieldsArr.add(fieldFalg2);
                } else {
                    fieldsArr.add(fieldFlag);
                }
                if (fieldDefinMapFields2.containsKey(fieldFlag)) {
                    oldFieldData = (FieldData)fieldDefinMapFields2.get(fieldFlag);
                    fieldData.setFieldType(oldFieldData.getFieldType());
                    fieldData.setFieldSize(oldFieldData.getFieldSize());
                    fieldData.setFractionDigits(oldFieldData.getFractionDigits());
                    continue;
                }
                if (!fieldDefinMapFields.containsKey(fieldFlag)) continue;
                oldFieldData = (FieldData)fieldDefinMapFields.get(fieldFlag);
                fieldData.setFieldType(oldFieldData.getFieldType());
                fieldData.setFieldSize(oldFieldData.getFieldSize());
                fieldData.setFractionDigits(oldFieldData.getFractionDigits());
            }
            Map<String, DimensionValue> dimensionValueMap = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
            if (importContext.getDimEntityCache().getEntitySingleDimValues().size() > 0) {
                this.singleDimService.setDimensionByUnitSingleDim(importContext, dimensionValueMap);
            }
            TableContext tableContext = this.batchImportService.getTableContex(dimensionValueMap, param.getTaskKey(), param.getFormSchemeKey(), formDefine.getKey(), asyncTaskMonitor.getTaskId());
            if (importContext.getIntfObjects().containsKey("TempAssistantTable")) {
                TempAssistantTable tempTable = (TempAssistantTable)importContext.getIntfObjects().get("TempAssistantTable");
                tableContext.setTempAssistantTable(importContext.getEntityCompanyType(), tempTable);
            }
            if (param.isAppending()) {
                tableContext.setFloatImpOpt(0);
            }
            Map repeatEntityNodes = importContext.getRepeatEntityNodes();
            Map repeatFormNodes = importContext.getRepeatFormNodes();
            RepeatFormNode repeatFormNode = (RepeatFormNode)repeatFormNodes.get(formDefine.getKey());
            if (repeatFormNode != null && floatingRow > 0) {
                if (repeatFormNode.getRepeatMode() == 2) {
                    tableContext.setFloatImpOpt(0);
                } else if (repeatFormNode.getRepeatMode() == 1) {
                    tableContext.setFloatImpOpt(2);
                }
            }
            DataImportRule dataStatusRule = this.getDataImportRuleByFlowType(importContext);
            int floatNum = regionData.getRegionTop();
            if (regionData.getType() == 2) {
                floatNum = regionData.getRegionLeft();
            }
            IRegionDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(tableContext, floatNum, fieldsArr);
            try {
                ArrayList<String> deleteRegionUnitCodes = new ArrayList<String>();
                CaseInsensitiveMap<String, Map<String, List<Map<String, DimensionValue>>>> zdmFloatCodeDims = new CaseInsensitiveMap<String, Map<String, List<Map<String, DimensionValue>>>>();
                ArrayList<Map<String, DimensionValue>> allfloatDim = new ArrayList<Map<String, DimensionValue>>();
                HashMap<String, String> entityKeyAndZdms = new HashMap<String, String>();
                int importRowCount = 0;
                boolean dataIsError = false;
                List<SingleFieldFileInfo> fjFieldList = this.uploadJioDataService.getFieldFileInfosFromDataSet(importContext, dataSetList, fieldDefineMap);
                this.batchMarkDelete(importContext, uploadLog, formDefine, entityKeyFormReadWritesMap, dataStatusRule, fjFieldList, fieldDefineMap, param.getDimensionSet(), dataSetList);
                ArrayList<Map<String, DimensionValue>> importZdmDims = new ArrayList<Map<String, DimensionValue>>();
                for (String string : uploadEntityZdmKeyMap.keySet()) {
                    RepeatEntityNode entityReetNode;
                    String netEntityKey = (String)uploadEntityZdmKeyMap.get(string);
                    if (entityKeyAndZdms.containsKey(netEntityKey)) {
                        importContext.info(logger, string + ",\u5bf9\u5e94\u7f51\u62a5\u5355\u4f4d:" + netEntityKey + ",\u5df2\u5b58\u5728\u6620\u5c04\u7684\u4e3b\u4ee3\u7801\uff1a" + (String)entityKeyAndZdms.get(netEntityKey));
                        continue;
                    }
                    entityKeyAndZdms.put(netEntityKey, string);
                    if (repeatEntityNodes != null && repeatEntityNodes.containsKey(string) && ((entityReetNode = (RepeatEntityNode)repeatEntityNodes.get(string)).getRepeatMode() == 0 || repeatFormNode != null && repeatFormNode.getRepeatMode() == 0)) continue;
                    try {
                        String bjZdm;
                        if (!this.judgeformAuth(uploadLog, formDefine, netEntityKey, entityKeyFormReadWritesMap, dataStatusRule, true)) continue;
                        Map<String, DimensionValue> dimensionValueSet = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
                        this.setSingleDimsByEntityKey(importContext, dimensionValueSet, netEntityKey);
                        SingleFieldFileInfo formFileInfo = new SingleFieldFileInfo();
                        formFileInfo.setDataSchemeKey(importContext.getDataSchemeKey());
                        formFileInfo.setFormKey(formDefine.getKey());
                        formFileInfo.setFormCode(formDefine.getFormCode());
                        formFileInfo.setFormSchemeKey(importContext.getFormSchemeKey());
                        formFileInfo.setDimensionSet(UploadJioDataUtil.getNewDimensionSet(dimensionValueSet));
                        formFileInfo.setTaskKey(importContext.getTaskKey());
                        formFileInfo.setEntityKey(netEntityKey);
                        importZdmDims.add(dimensionValueSet);
                        ArrayList<String> uploadZdms = new ArrayList<String>();
                        uploadZdms.add(string);
                        if (floatingRow > 0 && importContext.getEntityMergeZdmMap().containsKey(string)) {
                            uploadZdms.addAll((Collection)importContext.getEntityMergeZdmMap().get(string));
                        } else if (floatingRow <= 0 && importContext.getEntityMergeBenJiMap().containsKey(string) && StringUtils.isNotEmpty((String)(bjZdm = (String)importContext.getEntityMergeBenJiMap().get(string)))) {
                            uploadZdms.clear();
                            uploadZdms.add((String)importContext.getEntityMergeBenJiMap().get(string));
                        }
                        HashSet<String> rowEmptyZdms = new HashSet<String>();
                        for (String dbfZdm : uploadZdms) {
                            CaseInsensitiveMap floatCodeDims = new CaseInsensitiveMap();
                            List<Map<String, DimensionValue>> floatCodeDimList = null;
                            dataSetList.locateDataRowByZdm(dbfZdm);
                            boolean isFloatEmpty = false;
                            if (floatingRow > 0 && regionData.getType() > 1 && dataSetList.getDataSetList().size() > 0 && ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getIsNewRow()) {
                                isFloatEmpty = true;
                            }
                            if (!dataSetList.getDataRowIsNew() && !isFloatEmpty) {
                                for (int i = 0; i < dataSetList.getFloatRowsCount(); ++i) {
                                    dataSetList.locateDataRowByFloatIndex(i);
                                    String floatCode = "";
                                    String floatOrder = "";
                                    String floatUnitCode = "";
                                    if (floatingRow > 0) {
                                        floatCode = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getFloatCodeValues();
                                        floatOrder = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getFloatOrder();
                                    }
                                    Object[] rowData = new Object[fieldDefineList2.size()];
                                    ArrayList<Object> listRow = new ArrayList<Object>();
                                    this.uploadJioDataService.setDataBeforeImport(importContext, dataSetList, dbfZdm, formFileInfo, fieldDefineMap);
                                    UploadJioDataUtil.setImportRowValueByFields(importContext, fieldDefineList2, rowData, dataSetList, dbfZdm);
                                    DimensionValueSet tranDim = null;
                                    if (!importContext.getDimEntityCache().getEntityTranDims().isEmpty() && importContext.getDimEntityCache().getEntityTranDims().containsKey(netEntityKey)) {
                                        tranDim = (DimensionValueSet)importContext.getDimEntityCache().getEntityTranDims().get(netEntityKey);
                                    }
                                    Map singleUnitDim = null;
                                    if (importContext.getDimEntityCache().getEntitySingleDimList().size() > 0 && importContext.getDimEntityCache().getEntitySingleDimList().containsKey(netEntityKey)) {
                                        singleUnitDim = (Map)importContext.getDimEntityCache().getEntitySingleDimList().get(netEntityKey);
                                    }
                                    for (String dimName : dimFields) {
                                        DimensionValue dim = null;
                                        if (singleUnitDim != null && singleUnitDim.containsKey(dimName)) {
                                            dim = (DimensionValue)singleUnitDim.get(dimName);
                                        } else if ("MD_CURRENCY".equalsIgnoreCase(dimName) && tranDim != null && tranDim.hasValue("MD_CURRENCY")) {
                                            listRow.add(tranDim.getValue("MD_CURRENCY"));
                                        } else {
                                            dim = dimensionValueSet.get(dimName);
                                        }
                                        if (dim == null) continue;
                                        listRow.add(dim.getValue());
                                    }
                                    for (Object obj : rowData) {
                                        listRow.add(obj);
                                    }
                                    DimensionValueSet dimSet = bathDataSet.importDatas(listRow);
                                    if (dimSet != null && dimSet.size() > 0) {
                                        ++importRowCount;
                                    }
                                    if (floatingRow <= 0 || dimSet == null || dimSet.size() <= 0) continue;
                                    Map<String, DimensionValue> floatDim = UploadJioDataUtil.getNewDimensionSet(dimensionValueSet);
                                    if (StringUtils.isEmpty((String)floatCode)) {
                                        floatUnitCode = dbfZdm + "_" + floatOrder;
                                        if (StringUtils.isEmpty((String)floatOrder)) {
                                            floatUnitCode = dbfZdm + "_" + i + "";
                                        }
                                    } else {
                                        floatUnitCode = dbfZdm + "_" + floatCode + '_' + floatOrder;
                                    }
                                    if (floatCodeDims.containsKey(floatUnitCode)) {
                                        floatCodeDimList = (List)floatCodeDims.get(floatUnitCode);
                                    } else {
                                        floatCodeDimList = new ArrayList();
                                        floatCodeDims.put(floatUnitCode, floatCodeDimList);
                                    }
                                    for (int j = 0; j < dimSet.size(); ++j) {
                                        if (floatDim.containsKey(dimSet.getName(j))) continue;
                                        DimensionValue dim = new DimensionValue();
                                        dim.setName(dimSet.getName(j));
                                        if (null != dimSet.getValue(j)) {
                                            dim.setValue(dimSet.getValue(j).toString());
                                        } else {
                                            dim.setValue(null);
                                        }
                                        dim.setType(0);
                                        floatDim.put(dim.getName(), dim);
                                    }
                                    floatCodeDimList.add(floatDim);
                                    allfloatDim.add(floatDim);
                                }
                                uploadLog.addSuccessForm(formDefine, netEntityKey);
                                if (null == floatCodeDimList || floatingRow <= 0 || floatCodeDimList.size() <= 0) continue;
                                zdmFloatCodeDims.put(dbfZdm, floatCodeDims);
                                continue;
                            }
                            if (!importContext.isDeleteEmptyData()) continue;
                            rowEmptyZdms.add(dbfZdm);
                            if (floatingRow >= 0) continue;
                            Object[] rowData = new Object[fieldDefineList2.size()];
                            ArrayList<Object> listRow = new ArrayList<Object>();
                            for (int k = 0; k < rowData.length; ++k) {
                                rowData[k] = "";
                            }
                            UploadJioDataUtil.setImportRowValueByFields(importContext, fieldDefineList2, rowData, dataSetList, dbfZdm);
                            for (String dimName : dimFields) {
                                DimensionValue dim = dimensionValueSet.get(dimName);
                                if (dim == null) continue;
                                listRow.add(dim.getValue());
                            }
                            for (Object obj : rowData) {
                                listRow.add(obj);
                            }
                            DimensionValueSet dimSet = bathDataSet.importDatas(listRow);
                            if (dimSet == null || dimSet.size() <= 0) continue;
                            ++importRowCount;
                        }
                        if (!importContext.isDeleteEmptyData() || rowEmptyZdms.size() != uploadZdms.size() || floatingRow <= 0) continue;
                        deleteRegionUnitCodes.add(netEntityKey);
                    }
                    catch (Exception e) {
                        importContext.error(logger, e.getMessage(), (Throwable)e);
                        Object message = this.uploadLogService.handException(e);
                        message = this.getUnitErrorMessage((String)message, 0);
                        uploadLog.addErrorForm(formDefine, netEntityKey, null, (String)message);
                        dataIsError = true;
                    }
                }
                try {
                    if (importRowCount > 0 && !dataIsError) {
                        if (dataSetList != null) {
                            dataSetList.close();
                            dataSetList = null;
                            olddataSetList = null;
                        }
                        ImportInfo importInfo = bathDataSet.commit();
                        if (importRowCount > 10000) {
                            importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u63d0\u4ea4\u6570\u636e,\u884c\u6570:" + importRowCount);
                        }
                        if (floatingRow > 0 && importInfo != null && importInfo.getDimValues() != null && allfloatDim.size() > 0) {
                            void var43_51;
                            if (importInfo.getDimValues().size() != allfloatDim.size()) {
                                importContext.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u7ed3\u679c\u6570\u636e\u4e0d\u4e00\u81f4\uff0c\u8bf7\u68c0\u67e5:" + importInfo.getDimValues().size());
                            }
                            boolean bl = false;
                            while (var43_51 < importInfo.getDimValues().size()) {
                                Object rDim;
                                if (var43_51 < allfloatDim.size() && (rDim = (Map)importInfo.getDimValues().get((int)var43_51)) != null) {
                                    Map cDim = (Map)allfloatDim.get((int)var43_51);
                                    for (String aCode : rDim.keySet()) {
                                        if (!cDim.containsKey(aCode)) continue;
                                        DimensionValue dim = (DimensionValue)cDim.get(aCode);
                                        String rValue = (String)rDim.get(aCode);
                                        if (!StringUtils.isNotEmpty((String)rValue)) continue;
                                        dim.setValue((String)rDim.get(aCode));
                                    }
                                }
                                ++var43_51;
                            }
                        }
                    }
                }
                catch (Exception e) {
                    importContext.error(logger, e.getMessage(), (Throwable)e);
                    String string2 = this.uploadLogService.handException(e);
                    string2 = this.getUnitErrorMessage(string2, uploadEntityZdmKeyMap.size());
                    for (Map.Entry entry : uploadEntityZdmKeyMap.entrySet()) {
                        String netEntityKey = (String)uploadEntityZdmKeyMap.get(entry.getKey());
                        uploadLog.addErrorForm(formDefine, netEntityKey, null, string2);
                    }
                    dataIsError = true;
                }
                if (importRowCount > 0 && !dataIsError && importContext.getDataOption().isUploadCheckData() && importContext.isNeedCheckInfo()) {
                    try {
                        this.singleDataCheckService.ImportCheckDataFromCache(importContext, dbfFilePath, param.getDimensionSet(), formDefine.getKey(), zdmFloatCodeDims, null);
                    }
                    catch (Exception e) {
                        importContext.error(logger, "\u5bfc\u5165\u51fa\u9519\u8bf4\u660e\u5931\u8d25\u6709\u672a\u77e5\u5f02\u5e38\uff1a " + e.getMessage(), (Throwable)e);
                        for (Map.Entry entry : uploadEntityZdmKeyMap.entrySet()) {
                            String netEntityKey = (String)uploadEntityZdmKeyMap.get(entry.getKey());
                            uploadLog.addErrorForm(formDefine, netEntityKey, null, "\u5bfc\u5165\u51fa\u9519\u8bf4\u660e\u5931\u8d25");
                        }
                    }
                }
                this.uploadJioDataService.deleteFloatRegionData(importContext, param, deleteRegionUnitCodes, formDefine.getKey(), regionData.getKey(), floatingRow);
                if (floatingRow > 0 && param.isAppending() && !fjFieldList.isEmpty()) {
                    for (Map map : importZdmDims) {
                        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
                        Map dimensionSet = map;
                        for (String key : dimensionSet.keySet()) {
                            dimensionCombinationBuilder.setValue(key, (Object)((DimensionValue)dimensionSet.get(key)).getValue());
                        }
                        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
                        SimpleAsyncProgressMonitor asyncTaskMonitor1 = new SimpleAsyncProgressMonitor(UUID.randomUUID().toString(), this.cacheObjectResourceRemote);
                        this.uploadFileService.incrementDeleteMarkFile(importContext.getDataSchemeKey(), dimensionCombination, regionData.getKey(), (AsyncTaskMonitor)asyncTaskMonitor1);
                    }
                }
            }
            catch (Exception e) {
                if (bathDataSet != null) {
                    bathDataSet.close();
                }
                throw e;
            }
            finally {
                if (bathDataSet != null) {
                    bathDataSet.close();
                }
                if (dataSetList != null) {
                    dataSetList.close();
                    dataSetList = null;
                    olddataSetList = null;
                }
            }
        }
    }

    private void batchMarkDelete(TaskDataContext importContext, SingleUploadLogManager uploadLog, FormDefine formDefine, Map<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap, DataImportRule dataStatusRule, List<SingleFieldFileInfo> fjFieldList, Map<String, FieldData> fieldDefineMap, Map<String, DimensionValue> dimensionValueSet, ReportRegionDataSetList dataSetList) {
        if (fjFieldList.isEmpty()) {
            return;
        }
        Map<String, JIOUnitImportResult> successMap = uploadLog.getSuccessUnits();
        Map<String, JIOUnitImportResult> errorMap = uploadLog.getErrorUnits();
        ArrayList<String> markEntityList = new ArrayList<String>();
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        Map repeatEntityNodes = importContext.getRepeatEntityNodes();
        Map repeatFormNodes = importContext.getRepeatFormNodes();
        RepeatFormNode repeatFormNode = (RepeatFormNode)repeatFormNodes.get(formDefine.getKey());
        for (String zdm : uploadEntityZdmKeyMap.keySet()) {
            RepeatEntityNode entityReetNode;
            String netEntityKey = (String)uploadEntityZdmKeyMap.get(zdm);
            if (repeatEntityNodes != null && repeatEntityNodes.containsKey(zdm) && ((entityReetNode = (RepeatEntityNode)repeatEntityNodes.get(zdm)).getRepeatMode() == 0 || repeatFormNode != null && repeatFormNode.getRepeatMode() == 0) || !this.judgeformAuth(uploadLog, formDefine, netEntityKey, entityKeyFormReadWritesMap, dataStatusRule, true)) continue;
            markEntityList.add(netEntityKey);
        }
        Map<String, DimensionValue> batchDimensionValueSet = UploadJioDataUtil.getNewDimensionSet(dimensionValueSet);
        DimensionValue batchEntityDim = batchDimensionValueSet.get(importContext.getEntityCompanyType());
        batchEntityDim.setValue(String.join((CharSequence)";", markEntityList));
        SingleFieldFileInfo batchFormFileInfo = new SingleFieldFileInfo();
        batchFormFileInfo.setDataSchemeKey(importContext.getDataSchemeKey());
        batchFormFileInfo.setFormKey(formDefine.getKey());
        batchFormFileInfo.setFormCode(formDefine.getFormCode());
        batchFormFileInfo.setFormSchemeKey(importContext.getFormSchemeKey());
        batchFormFileInfo.setDimensionSet(batchDimensionValueSet);
        batchFormFileInfo.setTaskKey(importContext.getTaskKey());
        if (!fjFieldList.isEmpty()) {
            this.uploadJioDataService.batchMarkDeleteFileDataBeforeImport(importContext, dataSetList, batchFormFileInfo, fieldDefineMap);
        }
    }

    private void setSingleDimsByEntityKey(TaskDataContext importContext, Map<String, DimensionValue> dimensionValueSet, String netEntityKey) {
        DimensionValueSet tranDim;
        DimensionValue entityDim = dimensionValueSet.get(importContext.getEntityCompanyType());
        entityDim.setValue(importContext.getEntityMasterCodeBykey(netEntityKey));
        if (importContext.getDimEntityCache().getEntitySingleDimList().size() > 0 && importContext.getDimEntityCache().getEntitySingleDimList().containsKey(netEntityKey)) {
            Map singleUnitDim = (Map)importContext.getDimEntityCache().getEntitySingleDimList().get(netEntityKey);
            for (String dimName : importContext.getDimEntityCache().getEntitySingleDims()) {
                DimensionValue setValue = dimensionValueSet.get(dimName);
                DimensionValue getValue = (DimensionValue)singleUnitDim.get(dimName);
                if (getValue == null || setValue == null) continue;
                setValue.setValue(getValue.getValue());
            }
        }
        if (!importContext.getDimEntityCache().getEntityTranDims().isEmpty() && importContext.getDimEntityCache().getEntityTranDims().containsKey(netEntityKey) && (tranDim = (DimensionValueSet)importContext.getDimEntityCache().getEntityTranDims().get(netEntityKey)).hasValue("MD_CURRENCY") && dimensionValueSet.containsKey("MD_CURRENCY")) {
            String currency = (String)tranDim.getValue("MD_CURRENCY");
            DimensionValue setValue = dimensionValueSet.get("MD_CURRENCY");
            setValue.setValue(currency);
        }
    }

    private boolean judgeformAuth(SingleUploadLogManager uploadLog, FormDefine formDefine, String netEntityKey, Map<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap, DataImportRule dataStatusRule, boolean recordError) {
        boolean result = true;
        Map<String, JIOUnitImportResult> successUnits = uploadLog.getSuccessUnits();
        Map<String, JIOUnitImportResult> errorUnits = uploadLog.getErrorUnits();
        Map<String, IAccessResult> formResult = null;
        if (entityKeyFormReadWritesMap.containsKey(netEntityKey)) {
            formResult = entityKeyFormReadWritesMap.get(netEntityKey);
        }
        if (null != formResult && formResult.containsKey(formDefine.getKey())) {
            IAccessResult dataResult = formResult.get(formDefine.getKey());
            try {
                if (!dataResult.haveAccess()) {
                    String msg = dataResult.getMessage();
                    if (dataStatusRule != null && dataStatusRule.isEnable() && ("\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91".equalsIgnoreCase(msg) || "\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91".equalsIgnoreCase(msg) || "\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91".equalsIgnoreCase(msg))) {
                        if ("\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91".equalsIgnoreCase(msg)) {
                            result = dataStatusRule.getImportData().indexOf(UnitState.UPLOAD) >= 0;
                        } else if ("\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91".equalsIgnoreCase(msg)) {
                            result = dataStatusRule.getImportData().indexOf(UnitState.CONFIRM) >= 0;
                        } else if ("\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91".equalsIgnoreCase(msg)) {
                            result = dataStatusRule.getImportData().indexOf(UnitState.SUBMIT) >= 0;
                        }
                    } else {
                        if (!"\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6".equalsIgnoreCase(msg) && recordError) {
                            uploadLog.addErrorForm(formDefine, netEntityKey, msg);
                        }
                        result = false;
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    private DataImportRule getDataImportRuleByFlowType(TaskDataContext importContext) {
        DataImportRule dataStatusRule = null;
        WorkFlowType wflowType = this.flowService.queryStartType(importContext.getFormSchemeKey());
        if (wflowType == WorkFlowType.ENTITY && importContext.getMapingCache().getMapConfig() != null && importContext.getMapingCache().getMapConfig().getConfig() != null) {
            dataStatusRule = importContext.getMapingCache().getMapConfig().getConfig().getDataImportRule();
        }
        return dataStatusRule;
    }

    private String getUnitErrorMessage(String oldMessage, int unitCount) {
        String message = oldMessage;
        if (message != null && StringUtils.isNotEmpty((String)message)) {
            if (unitCount > 1000 && message.length() > 100) {
                message = message.substring(0, 100) + "...";
            } else if (message.length() > 1000) {
                message = message.substring(0, 1000) + "...";
            }
        } else {
            message = "\u672a\u77e5\u5f02\u5e38";
        }
        return message;
    }

    @Override
    public boolean isJioUseCheckData() {
        return this.jioUseCheckData;
    }

    @Override
    public String getUploadTaskDocDir(UploadParam param) {
        String docPath = null;
        if (param.getVariableMap().containsKey("JioUploadTaskDocPath") && StringUtils.isNotEmpty((String)(docPath = (String)param.getVariableMap().get("JioUploadTaskDocPath")))) {
            docPath = SinglePathUtil.getPath((String)docPath);
        }
        return docPath;
    }

    @Override
    public String getUploadTaskImgDir(UploadParam param) {
        String docPath = null;
        if (param.getVariableMap().containsKey("JioUploadTaskImgPath") && StringUtils.isNotEmpty((String)(docPath = (String)param.getVariableMap().get("JioUploadTaskImgPath")))) {
            docPath = SinglePathUtil.getPath((String)docPath);
        }
        return docPath;
    }

    @Override
    public String getUploadTaskRptDir(UploadParam param) {
        String docPath = null;
        if (param.getVariableMap().containsKey("JioUploadTaskRptPath") && StringUtils.isNotEmpty((String)(docPath = (String)param.getVariableMap().get("JioUploadTaskRptPath")))) {
            docPath = SinglePathUtil.getPath((String)docPath);
        }
        return docPath;
    }

    @Override
    public String getUploadTaskTxtDir(UploadParam param) {
        String docPath = null;
        if (param.getVariableMap().containsKey("JioUploadTaskTxtPath") && StringUtils.isNotEmpty((String)(docPath = (String)param.getVariableMap().get("JioUploadTaskTxtPath")))) {
            docPath = SinglePathUtil.getPath((String)docPath);
        }
        return docPath;
    }

    private List<FormShareItem> getFormShareList(TaskDataContext importContext, List<FormDefine> dataForms) {
        ArrayList<FormShareItem> list = new ArrayList<FormShareItem>();
        for (FormDefine form : dataForms) {
            List fieldkeys = this.viewController.getFieldKeysInForm(form.getKey());
            List fieldList = this.dataSchemeSevice.getDataFields(fieldkeys);
            HashSet<String> netTableKeys = new HashSet<String>();
            for (Object field : fieldList) {
                if (netTableKeys.contains(field.getDataTableKey())) continue;
                netTableKeys.add(field.getDataTableKey());
            }
            ArrayList<FormShareItem> findlist = new ArrayList<FormShareItem>();
            if (!list.isEmpty()) {
                Object field;
                field = netTableKeys.iterator();
                block2: while (field.hasNext()) {
                    String netTableKey = (String)field.next();
                    for (FormShareItem item : list) {
                        if (!item.getShareNetTables().contains(netTableKey)) continue;
                        findlist.add(item);
                        continue block2;
                    }
                }
            }
            if (findlist.isEmpty()) {
                FormShareItem item = new FormShareItem();
                item.setCode(OrderGenerator.newOrder());
                item.add(form, netTableKeys, null);
                list.add(item);
                continue;
            }
            ArrayList<FormShareItem> oldList = new ArrayList<FormShareItem>();
            oldList.addAll(list);
            list.clear();
            HashMap<String, FormShareItem> deleteItems = new HashMap<String, FormShareItem>();
            FormShareItem destItem = new FormShareItem();
            destItem.setCode(OrderGenerator.newOrder());
            for (FormShareItem item : findlist) {
                destItem.merge(item);
                deleteItems.put(item.getCode(), item);
            }
            destItem.add(form, netTableKeys, null);
            list.add(destItem);
            for (FormShareItem item : oldList) {
                if (deleteItems.containsKey(item.getCode())) continue;
                list.add(item);
            }
        }
        return list;
    }
}

