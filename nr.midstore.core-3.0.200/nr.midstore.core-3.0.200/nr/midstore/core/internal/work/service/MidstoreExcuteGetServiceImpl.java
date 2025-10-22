/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEOrgInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package nr.midstore.core.internal.work.service;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEOrgInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.midstore.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore.core.dataset.IMidstoreDataSet;
import nr.midstore.core.dataset.MidsotreTableContext;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreFileInfo;
import nr.midstore.core.definition.bean.MidstoreParam;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.bean.mapping.EnumMappingInfo;
import nr.midstore.core.definition.bean.mapping.UnitMappingInfo;
import nr.midstore.core.definition.bean.mapping.ZBMappingInfo;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.common.FormAccessType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataFieldService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.work.service.data.MidstoreTableDataHandlerImpl;
import nr.midstore.core.param.service.IMidstoreCheckParamService;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.core.util.IMidstoreAttachmentService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.util.IMidstoreReadWriteService;
import nr.midstore.core.util.IMidstoreResultService;
import nr.midstore.core.util.auth.IMidstoreFormDataAccess;
import nr.midstore.core.work.service.IMidstoreExcuteGetService;
import nr.midstore.core.work.service.data.IMidstoreConditonService;
import nr.midstore.core.work.service.data.IMidstoreFixDataService;
import nr.midstore.core.work.service.extend.IMidstoreDataGetPreprocessService;
import nr.midstore.core.work.service.org.IMidstoreOrgDataWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MidstoreExcuteGetServiceImpl
implements IMidstoreExcuteGetService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreExcuteGetServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreOrgDataFieldService orgDataFieldSevice;
    @Autowired
    private IMidstoreCheckParamService checkParamService;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IMidstoreBatchImportDataService batchImportService;
    @Autowired
    private IMidstoreDimensionService dimensionService;
    @Autowired(required=false)
    private IMidstoreFormDataAccess formAccessService;
    @Autowired
    private IMidstoreReadWriteService readWriteService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired
    private IMidstoreAttachmentService attachmentService;
    @Autowired
    private IMidstoreOrgDataWorkService orgDataWorkService;
    @Autowired
    private IMidstoreFixDataService fixDataService;
    @Autowired
    private IMidstoreConditonService conditionService;
    @Autowired(required=false)
    private IMidstoreDataGetPreprocessService preprocessService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public MidstoreResultObject excuteGetData(String midstoreSchemeId, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.checkParamService.getContext(midstoreSchemeId, monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforePostData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        this.checkParamService.doLoadFormScheme(context, true);
        return this.excuteGetData2(context, true, monitor);
    }

    @Override
    public MidstoreResultObject excuteGetDataByUser(String midstoreSchemeId, String userName, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.checkParamService.getContext(midstoreSchemeId, monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforeGetData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        if (StringUtils.isNotEmpty((String)userName)) {
            context.setExcuteUserName(userName);
        }
        context.setDeleteEmptyData(context.getSchemeInfo().isDeleteEmpty());
        if (this.formAccessService != null) {
            logger.info("\u666e\u901a\u7528\u6237\uff0c\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
            context.setWorkResult(new MistoreWorkResultObject());
            Map<DimensionValueSet, List<String>> unitFormKeys = this.checkParamService.getUnitFormKeys(context, FormAccessType.FORMACCESS_WRITE);
            logger.info("\u8bfb\u53d6\u6743\u9650\u8bbe\u7f6e\u7684\u5355\u4f4d\u6570\uff1a" + unitFormKeys.size());
            this.tranUnitFormsToTables(context, unitFormKeys);
            return this.excuteGetData2(context, true, monitor);
        }
        logger.info("\u666e\u901a\u7528\u6237\uff0c\u672a\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
        this.checkParamService.doLoadFormScheme(context, true);
        return this.excuteGetData2(context, true, monitor);
    }

    @Override
    public MidstoreResultObject excuteGetData(MidstoreParam param, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.checkParamService.getContext(param.getMidstoreSchemeId(), monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforePostData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        context.getExcuteParams().putAll(param.getExcuteParams());
        String userName = (String)context.getExcuteParams().get("EXCUTEUSER");
        if (StringUtils.isNotEmpty((String)userName)) {
            context.setExcuteUserName(userName);
        }
        if (!this.readWriteService.isAdmin()) {
            logger.info("\u666e\u901a\u7528\u6237");
        }
        if (this.formAccessService != null) {
            logger.info("\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
            context.setWorkResult(new MistoreWorkResultObject());
            Map<DimensionValueSet, List<String>> unitFormKeys = this.checkParamService.getUnitFormKeys(context, FormAccessType.FORMACCESS_WRITE);
            logger.info("\u8bfb\u53d6\u6743\u9650\u8bbe\u7f6e\u7684\u5355\u4f4d\u6570\uff1a" + unitFormKeys.size());
            this.tranUnitFormsToTables(context, unitFormKeys);
            return this.excuteGetData2(context, true, monitor);
        }
        logger.info("\u672a\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
        this.checkParamService.doLoadFormScheme(context, true);
        return this.excuteGetData2(context, true, monitor);
    }

    @Override
    public MidstoreResultObject excuteGetDataByCode(String midstoreSchemeCode, Map<DimensionValueSet, List<String>> unitFormKeys, AsyncTaskMonitor monitor) throws MidstoreException {
        return this.excuteGetDataByCode(midstoreSchemeCode, unitFormKeys, false, monitor);
    }

    @Override
    public MidstoreResultObject excuteGetDataByCode(String midstoreSchemeCode, Map<DimensionValueSet, List<String>> unitFormKeys, boolean isDeleteEmpty, AsyncTaskMonitor monitor) throws MidstoreException {
        if (StringUtils.isEmpty((String)midstoreSchemeCode)) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u6807\u8bc6\u4e3a\u7a7a");
        }
        if (midstoreSchemeCode.contains(",")) {
            String[] subCodes = midstoreSchemeCode.split(",");
            ArrayList<MidstoreSchemeDTO> midstoreSchemes = new ArrayList<MidstoreSchemeDTO>();
            ArrayList<String> midstoreSchemeCodes = new ArrayList<String>();
            HashMap<String, MidstoreSchemeDTO> midstoreSchemesMap = new HashMap<String, MidstoreSchemeDTO>();
            for (String subCode : subCodes) {
                MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByCode(subCode);
                if (scheme == null) {
                    logger.info("\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + subCode);
                    continue;
                }
                if (scheme.getExchangeMode() != ExchangeModeType.EXCHANGE_GET) {
                    logger.info("\u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848\uff0c" + subCode);
                    continue;
                }
                midstoreSchemes.add(scheme);
                midstoreSchemeCodes.add(subCode);
                midstoreSchemesMap.put(subCode, scheme);
            }
            String taskKey = null;
            if (midstoreSchemes.isEmpty()) {
                return new MidstoreResultObject(false, "\u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848");
            }
            MidstoreSchemeDTO defaultScheme = (MidstoreSchemeDTO)midstoreSchemes.get(0);
            taskKey = defaultScheme.getTaskKey();
            if (unitFormKeys == null || unitFormKeys.isEmpty()) {
                return new MidstoreResultObject(false, "\u53c2\u6570\u6ca1\u6709\u8868\u5355\u4fe1\u606f");
            }
            TaskDefine task = this.viewController.queryTaskDefine(taskKey);
            if (task == null) {
                return new MidstoreResultObject(false, "\u6240\u5173\u8054\u7684\u4efb\u52a1\u4e0d\u5b58\u5728\uff01");
            }
            String dateTimeDimName = null;
            String entityDimName = null;
            if (this.periodAdapter.isPeriodEntity(task.getDateTime())) {
                IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(task.getDateTime());
                dateTimeDimName = periodEntity.getDimensionName();
            }
            if (StringUtils.isNotEmpty((String)task.getDw())) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
                entityDimName = entityDefine.getDimensionName();
            }
            ArrayList<String> oldOrgList = new ArrayList<String>();
            String dataTime = null;
            for (DimensionValueSet unitDim : unitFormKeys.keySet()) {
                String unitCode = (String)unitDim.getValue(entityDimName);
                if (StringUtils.isEmpty(dataTime)) {
                    dataTime = (String)unitDim.getValue(dateTimeDimName);
                }
                oldOrgList.add(unitCode);
            }
            Map<String, List<String>> midstoreMatchOrgs = null;
            if (this.preprocessService == null) {
                return new MidstoreResultObject(false, "\u672a\u6269\u5c55\u81ea\u52a8\u5339\u914d\u591a\u4e2d\u95f4\u5e93\u65b9\u6848");
            }
            midstoreMatchOrgs = this.preprocessService.preprocessOrgToMidstoreScheme(taskKey, dataTime, midstoreSchemeCodes, oldOrgList);
            if (midstoreMatchOrgs == null || midstoreMatchOrgs.isEmpty()) {
                return new MidstoreResultObject(false, "\u672a\u6269\u5c55\u81ea\u52a8\u5339\u914d\u591a\u4e2d\u95f4\u5e93\u65b9\u6848");
            }
            if (midstoreMatchOrgs.size() == 1) {
                String subMidSchemeCode = null;
                HashMap<DimensionValueSet, List<String>> subUnitFormKeys = new HashMap<DimensionValueSet, List<String>>();
                for (Map.Entry<String, List<String>> entry : midstoreMatchOrgs.entrySet()) {
                    String midSchemeCode;
                    subMidSchemeCode = midSchemeCode = entry.getKey();
                    List<String> orgList = midstoreMatchOrgs.get(midSchemeCode);
                    HashSet<String> orgSet = new HashSet<String>(orgList);
                    for (DimensionValueSet unitDim : unitFormKeys.keySet()) {
                        String unitCode = (String)unitDim.getValue(entityDimName);
                        if (!orgSet.contains(unitCode)) continue;
                        subUnitFormKeys.put(unitDim, unitFormKeys.get(unitDim));
                    }
                }
                MidstoreSchemeDTO scheme = (MidstoreSchemeDTO)midstoreSchemesMap.get(subMidSchemeCode);
                if (scheme == null) {
                    return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + subMidSchemeCode);
                }
                return this.excuteGetData(scheme.getKey(), subUnitFormKeys, isDeleteEmpty, monitor);
            }
            MidstoreResultObject result = new MidstoreResultObject();
            result.setSuccess(true);
            if (monitor != null) {
                monitor.progressAndMessage(0.1, "\u63a5\u6536\u6570\u636e\u5f00\u59cb");
            }
            double curProcess = 0.1;
            for (MidstoreSchemeDTO scheme : midstoreSchemes) {
                if (!midstoreMatchOrgs.containsKey(scheme.getCode())) {
                    logger.info("\u4ea4\u6362\u65b9\u6848\u6ca1\u6709\u5728\u81ea\u52a8\u5339\u914d\u7ed3\u679c\u4e2d\uff0c" + scheme.getCode());
                    continue;
                }
                List<String> orgList = midstoreMatchOrgs.get(scheme.getCode());
                if (orgList == null || orgList.isEmpty()) {
                    logger.info("\u4ea4\u6362\u65b9\u6848\u6ca1\u6709\u5339\u914d\u5bf9\u5e94\u7684\u5355\u4f4d\uff0c" + scheme.getCode());
                    continue;
                }
                if (monitor != null) {
                    monitor.progressAndMessage(curProcess, "\u6570\u636e\u63a5\u6536\uff0c\u5f00\u59cb\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle());
                }
                HashSet<String> orgSet = new HashSet<String>(orgList);
                HashMap<DimensionValueSet, List<String>> subUnitFormKeys = new HashMap<DimensionValueSet, List<String>>();
                for (DimensionValueSet unitDim : unitFormKeys.keySet()) {
                    String unitCode = (String)unitDim.getValue(entityDimName);
                    if (!orgSet.contains(unitCode)) continue;
                    subUnitFormKeys.put(unitDim, unitFormKeys.get(unitDim));
                }
                String taskId = UUID.randomUUID().toString();
                SimpleAsyncTaskMonitor submonitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, AsynctaskPoolType.ASYNCTASK_MIDSTORE_GETDATA.getName());
                MidstoreResultObject subResult = this.excuteGetData(scheme.getKey(), subUnitFormKeys, isDeleteEmpty, (AsyncTaskMonitor)submonitor);
                result.getWorkResults().addAll(subResult.getWorkResults());
                if (!subResult.isSuccess()) {
                    result.setSuccess(false);
                }
                if (StringUtils.isEmpty((String)result.getMessage())) {
                    result.setMessage(subResult.getMessage());
                } else if (StringUtils.isNotEmpty((String)subResult.getMessage())) {
                    result.setMessage(result.getMessage() + ";" + subResult.getMessage());
                }
                BigDecimal b = new BigDecimal((double)orgList.size() * 0.8 / (double)oldOrgList.size());
                double f1 = b.setScale(2, 4).doubleValue();
                curProcess += f1;
                if (monitor == null) continue;
                monitor.progressAndMessage(curProcess, "\u6570\u636e\u63a5\u6536\uff0c\u5b8c\u6210\u4e2d\u95f4\u5e93\u65b9\u6848\uff1a" + scheme.getTitle());
            }
            if (monitor != null) {
                monitor.progressAndMessage(0.9, "\u63a5\u6536\u6570\u636e\u7ed3\u675f");
            }
            return result;
        }
        MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByCode(midstoreSchemeCode);
        if (scheme == null) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + midstoreSchemeCode);
        }
        if (scheme.getExchangeMode() != ExchangeModeType.EXCHANGE_GET) {
            return new MidstoreResultObject(false, "\u672a\u914d\u7f6e\u53d6\u6570\u65b9\u6848");
        }
        return this.excuteGetData(scheme.getKey(), unitFormKeys, isDeleteEmpty, monitor);
    }

    @Override
    public MidstoreResultObject excuteGetData(String midstoreSchemeId, Map<DimensionValueSet, List<String>> unitFormKeys, boolean isDeleteEmpty, AsyncTaskMonitor monitor) throws MidstoreException {
        if (unitFormKeys == null || unitFormKeys.isEmpty()) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u6ca1\u6709\u8868\u5355\u4fe1\u606f");
        }
        MidstoreContext context = this.checkParamService.getContext(midstoreSchemeId, monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforeGetData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        context.setDeleteEmptyData(isDeleteEmpty);
        this.tranUnitFormsToTables(context, unitFormKeys);
        return this.excuteGetData2(context, false, monitor);
    }

    private MidstoreResultObject excuteGetData2(MidstoreContext context, boolean isReadOrgData, AsyncTaskMonitor monitor) throws MidstoreException {
        if (context.getWorkResult() == null) {
            context.setWorkResult(new MistoreWorkResultObject());
        }
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        if (monitor != null) {
            monitor.progressAndMessage(0.1, "\u63a5\u6536\u6570\u636e\u5f00\u59cb");
        }
        logger.info(midstoreScheme.getTitle() + "\u63a5\u6536\u5f00\u59cb");
        TaskDefine taskDefine = context.getTaskDefine();
        if (taskDefine == null) {
            taskDefine = this.viewController.queryTaskDefine(midstoreScheme.getTaskKey());
            context.setTaskDefine(taskDefine);
        }
        ArrayList<String> oldExchangeEnityCodes = new ArrayList<String>();
        oldExchangeEnityCodes.addAll(context.getExchangeEnityCodes());
        IDataExchangeTask dataExchangeTask = this.exchangeTaskService.getExchangeTask(context);
        List<Object> queryEntitys = new ArrayList<String>();
        if (isReadOrgData && context.getSchemeInfo().isUseUpdateOrg()) {
            if (monitor != null) {
                monitor.progressAndMessage(0.2, "\u63a5\u6536\u7ec4\u7ec7\u673a\u6784");
            }
            this.readOrgDataFromMidstore(context, dataExchangeTask);
            queryEntitys.addAll(context.getExchangeEnityCodes());
        } else {
            this.midstoreMappingService.initOrgMapping(context);
            if (!context.getSchemeInfo().isAllOrgData()) {
                queryEntitys = this.orgDataWorkService.getUnitCodesByMidstoreScheme(context);
            } else {
                queryEntitys = this.orgDataWorkService.getUnitCodesFromMidstore(context, dataExchangeTask);
                if (queryEntitys == null || queryEntitys.size() == 0) {
                    queryEntitys = this.orgDataWorkService.getUnitCodesByOrgData(context);
                }
            }
        }
        if (queryEntitys != null && queryEntitys.size() > 0) {
            Set<String> canWriteUnits = this.readWriteService.getUnitcodeByOnlyUnitState(context, queryEntitys);
            if (canWriteUnits != null) {
                ArrayList<String> canWriteUnits2 = new ArrayList<String>();
                if (oldExchangeEnityCodes.size() > 0) {
                    for (String string : oldExchangeEnityCodes) {
                        if (!queryEntitys.contains(string)) {
                            this.resultService.addUnitErrorInfo(context.getWorkResult(), "\u5355\u4f4d\u5339\u914d\u5931\u8d25", string, "");
                            continue;
                        }
                        if (!canWriteUnits.contains(string)) {
                            this.resultService.addUnitErrorInfo(context.getWorkResult(), "\u5355\u4f4d\u5df2\u7ecf\u4e0a\u62a5", string, "");
                            continue;
                        }
                        canWriteUnits2.add(string);
                    }
                } else {
                    for (String string : queryEntitys) {
                        if (!canWriteUnits.contains(string)) {
                            this.resultService.addUnitErrorInfo(context.getWorkResult(), "\u5355\u4f4d\u5df2\u7ecf\u4e0a\u62a5", string, "");
                            continue;
                        }
                        canWriteUnits2.add(string);
                    }
                }
                context.getExchangeEnityCodes().clear();
                context.getExchangeEnityCodes().addAll(canWriteUnits2);
            }
        } else {
            logger.info("\u8bfb\u53d6\u5355\u4f4d\u72b6\u6001\u5931\u8d25");
        }
        if (monitor != null) {
            monitor.progressAndMessage(0.4, "\u63a5\u6536\u6307\u6807\u6570\u636e");
        }
        logger.info("\u63a5\u6536\u6307\u6807\u6570\u636e\u7684\u5355\u4f4d\u6570\uff0c" + context.getExchangeEnityCodes().size());
        if (context.getExchangeEnityCodes().size() > 0) {
            if (this.preprocessService != null) {
                logger.info("\u6307\u6807\u6570\u636e\u9884\u5904\u7406\u5f00\u59cb");
                this.preprocessService.preprocessDataToMidstore(context, dataExchangeTask);
                logger.info("\u6307\u6807\u6570\u636e\u9884\u5904\u7406\u5b8c\u6210");
            }
            this.readFieldDataFromMidstore(context, dataExchangeTask);
        } else {
            logger.info("\u63a5\u6536\u6307\u6807\u6570\u636e\u7684\u5355\u4f4d\u6570\u4e3a0\uff0c\u4e0d\u63a5\u6536\u6307\u6807\u6570\u636e\u3002");
        }
        logger.info(midstoreScheme.getTitle() + "\u63a5\u6536\u5b8c\u6210");
        if (monitor != null) {
            monitor.progressAndMessage(0.9, "\u63a5\u6536\u5b8c\u6210");
        }
        this.resultService.reSetErrorInfo(context);
        MidstoreResultObject result = new MidstoreResultObject(true, "");
        result.getWorkResults().add(context.getWorkResult());
        return result;
    }

    private void readOrgDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask) throws MidstoreException {
        MistoreWorkResultObject workResult = context.getWorkResult();
        String excutePeriod = this.dimensionService.getExcutePeriod(context);
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        String orgCodes = (String)context.getExcuteParams().get("OrgData");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            excutePeriod = dataTime;
        }
        if (workResult != null) {
            workResult.setNrPeriodCode(excutePeriod);
            workResult.setNrPeriodTitle(this.dimensionService.getPeriodTitle(context.getTaskDefine().getDateTime(), excutePeriod));
        }
        HashSet<String> UnitParams = new HashSet<String>();
        if (StringUtils.isNotEmpty((String)orgCodes)) {
            String[] unitCodes;
            for (String unitCode : unitCodes = orgCodes.split(",")) {
                UnitParams.add(unitCode);
            }
        }
        GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)PeriodUtil.getPeriodWrapper((String)excutePeriod));
        this.midstoreMappingService.initOrgMapping(context);
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        if (schemeInfo != null && !schemeInfo.isUseUpdateOrg()) {
            logger.info("\u4e0d\u540c\u6b65\u7ec4\u7ec7\u673a\u6784\u6570\u636e");
            return;
        }
        Set<String> canWriteUnits = null;
        if (StringUtils.isNotEmpty((String)context.getExcuteUserName()) && !this.readWriteService.isAdmin()) {
            canWriteUnits = this.readWriteService.getCanWriteUnitList(context, excutePeriod);
        }
        TaskDefine taskDefine = this.viewController.queryTaskDefine(midstoreScheme.getTaskKey());
        String orgCode = EntityUtils.getId((String)taskDefine.getDw());
        OrgCategoryDO orgDefine = this.queryOrgDatadefine(orgCode);
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCategoryname(orgCode);
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgParam.setValidtime(startCalendar.getTime());
        PageVO queryRes = this.orgDataClient.list(orgParam);
        HashMap<String, OrgDO> orgDataMap = new HashMap<String, OrgDO>();
        if (queryRes != null && queryRes.getRows() != null) {
            for (OrgDO data : queryRes.getRows()) {
                orgDataMap.put(data.getCode(), data);
            }
        }
        OrgDTO orgBaseParam = new OrgDTO();
        orgBaseParam.setCategoryname("MD_ORG");
        orgBaseParam.setStopflag(Integer.valueOf(-1));
        orgBaseParam.setRecoveryflag(Integer.valueOf(-1));
        orgBaseParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgBaseParam.setValidtime(startCalendar.getTime());
        HashMap orgDataBaseMap = new HashMap();
        context.getExchangeEnityCodes().clear();
        List midOrgs = null;
        try {
            OrgBatchOptDTO batchOptDto = new OrgBatchOptDTO();
            OrgDTO queryParam = new OrgDTO();
            queryParam.setCategoryname(orgCode);
            queryParam.setAuthType(OrgDataOption.AuthType.NONE);
            queryParam.setVersionDate(startCalendar.getTime());
            queryParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
            batchOptDto.setQueryParam(queryParam);
            batchOptDto.setFullFieldOverride(false);
            batchOptDto.setDataList(new ArrayList());
            HashMap<String, OrgDTO> orgMap = new HashMap<String, OrgDTO>();
            ArrayList<OrgDTO> addOrgs = new ArrayList<OrgDTO>();
            ArrayList<OrgDTO> updateOrgs = new ArrayList<OrgDTO>();
            midOrgs = dataExchangeTask.getOrgs();
            for (DEOrgInfo midOrg : midOrgs) {
                String deCode = midOrg.getName();
                String code = midOrg.getName();
                String unitTitle = midOrg.getTitle();
                String pCode = midOrg.getpName();
                if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(code)) {
                    code = context.getMappingCache().getSrcUnitMappingInfos().get(code).getUnitCode();
                }
                if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(pCode)) {
                    pCode = context.getMappingCache().getSrcUnitMappingInfos().get(pCode).getUnitCode();
                }
                if (UnitParams.size() > 0 && !UnitParams.contains(code)) continue;
                OrgDTO entityRow = null;
                entityRow = new OrgDTO();
                if (orgDataMap.containsKey(code)) {
                    if (!this.readWriteService.isAdmin() && canWriteUnits != null && canWriteUnits.size() > 0 && !canWriteUnits.contains(code)) {
                        this.resultService.addUnitErrorInfo(workResult, "\u5355\u4f4d\u6ca1\u6709\u7f16\u8f91\u6743\u9650", code, unitTitle);
                        continue;
                    }
                    OrgDO oldEntityRow = (OrgDO)orgDataMap.get(code);
                    entityRow.setId(oldEntityRow.getId());
                    entityRow.putAll((Map)oldEntityRow);
                    updateOrgs.add(entityRow);
                } else {
                    addOrgs.add(entityRow);
                }
                entityRow.setCode(code);
                entityRow.setName(midOrg.getTitle());
                entityRow.setParentcode(pCode);
                orgMap.put(code, entityRow);
                context.getExchangeEnityCodes().add(code);
                context.getUnitCache().put(code, new MistoreWorkUnitInfo(code, midOrg.getTitle()));
                context.getWorkResult().getMidstoreTableUnits().add(code);
            }
            DETableInfo deTableInfo = dataExchangeTask.getTableInfoByName("ORG_OTHERDATA");
            if (deTableInfo != null) {
                MidstoreOrgDataFieldDTO queryFieldParam = new MidstoreOrgDataFieldDTO();
                queryFieldParam.setSchemeKey(midstoreScheme.getKey());
                List<MidstoreOrgDataFieldDTO> orgFields = this.orgDataFieldSevice.list(queryFieldParam);
                ArrayList<String> fieldNames = new ArrayList<String>();
                fieldNames.add("MDCODE");
                fieldNames.add("ORGCODE");
                for (MidstoreOrgDataFieldDTO field : orgFields) {
                    if (orgDefine != null && orgDefine.getZbByName(field.getCode()) == null && !"SHORTNAME".equalsIgnoreCase(field.getCode())) continue;
                    fieldNames.add(field.getCode());
                }
                MemoryDataSet memoryDataSet = dataExchangeTask.readTableData("ORG_OTHERDATA", fieldNames, "");
                for (DataRow row : memoryDataSet) {
                    String code = row.getString(0);
                    if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(code)) {
                        code = context.getMappingCache().getSrcUnitMappingInfos().get(code).getUnitCode();
                    }
                    if (!orgMap.containsKey(code)) continue;
                    OrgDTO entityRow = (OrgDTO)orgMap.get(code);
                    for (int i = 0; i < fieldNames.size(); ++i) {
                        Iterator fieldName = (String)fieldNames.get(i);
                        String fieldValue = row.getString(i);
                        if ("CODE".equalsIgnoreCase((String)((Object)fieldName))) continue;
                        entityRow.put(((String)((Object)fieldName)).toLowerCase(), (Object)fieldValue);
                    }
                    if (fieldNames.contains("SHORTNAME")) continue;
                    entityRow.setShortname(entityRow.getName());
                }
                ArrayList<OrgDTO> dataList = new ArrayList<OrgDTO>();
                if (addOrgs.size() > 0 || updateOrgs.size() > 0) {
                    R r;
                    OrgBatchOptDTO batchOrgOptDto = new OrgBatchOptDTO();
                    OrgDTO orgDataParam = new OrgDTO();
                    orgDataParam.setCategoryname("MD_ORG");
                    orgDataParam.setAuthType(OrgDataOption.AuthType.NONE);
                    orgDataParam.setVersionDate(startCalendar.getTime());
                    orgDataParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
                    batchOrgOptDto.setQueryParam(orgDataParam);
                    batchOrgOptDto.setFullFieldOverride(false);
                    ArrayList<OrgDTO> orgDataList = new ArrayList<OrgDTO>();
                    batchOrgOptDto.setDataList(orgDataList);
                    for (OrgDTO entityRow : addOrgs) {
                        dataList.add(entityRow);
                        if (orgDataBaseMap.containsKey(entityRow.getCode())) continue;
                        OrgDTO org = new OrgDTO();
                        org.setCode(entityRow.getCode());
                        org.setOrgcode(entityRow.getOrgcode());
                        org.setName(entityRow.getName());
                        org.setParentcode(entityRow.getParentcode());
                        org.setShortname(entityRow.getShortname());
                        orgDataList.add(org);
                    }
                    for (OrgDTO entityRow : updateOrgs) {
                        dataList.add(entityRow);
                    }
                    if (orgDataList.size() > 0 && StringUtils.isNotEmpty((String)(r = this.orgDataClient.sync(batchOrgOptDto)).getMsg())) {
                        logger.info("\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u66f4\u65b0\uff1a" + r.getMsg());
                    }
                }
                if (dataList.size() > 0) {
                    batchOptDto.setDataList(dataList);
                    R r = this.orgDataClient.sync(batchOptDto);
                    logger.info("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u6570\u636e\u65b0\u589e\uff1a" + addOrgs.size() + ",\u66f4\u65b0\uff1a" + updateOrgs.size());
                    if (StringUtils.isNotEmpty((String)r.getMsg())) {
                        logger.info("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u6570\u636e\u66f4\u65b0\uff1a" + r.getMsg());
                    }
                } else {
                    logger.info("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u6570\u636e\u65e0\u65b0\u589e\u6216\u66f4\u65b0");
                }
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            logger.info("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u5931\u8d25");
        }
        if (midOrgs == null) {
            logger.info("\u4e2d\u95f4\u5e93\u65e0\u7ec4\u7ec7\u673a\u6784\u6570\u636e");
        }
    }

    private OrgCategoryDO queryOrgDatadefine(String orgName) {
        OrgCategoryDO param = new OrgCategoryDO();
        param.setName(orgName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        PageVO orgDefines = this.orgCategoryClient.list(param);
        OrgCategoryDO orgDefine = null;
        if (orgDefines != null && orgDefines.getRows().size() > 0) {
            orgDefine = (OrgCategoryDO)orgDefines.getRows().get(0);
        }
        return orgDefine;
    }

    private void readFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask) throws MidstoreException {
        this.midstoreMappingService.initZbMapping(context);
        this.midstoreMappingService.initPeriodMapping(context);
        this.midstoreMappingService.initEnumMapping(context);
        MidstoreFieldDTO queryParam = new MidstoreFieldDTO();
        queryParam.setSchemeKey(context.getSchemeKey());
        List<MidstoreFieldDTO> fields = this.fieldService.list(queryParam);
        if (context.getExchangeEnityCodes().size() > 499 && context.getExchangeEnityCodes().size() < 5000) {
            this.dimensionService.createTempTable(context);
        }
        try {
            List tableModes = dataExchangeTask.getAllTableModel();
            ArrayList<DETableModel> fixTableModes = new ArrayList<DETableModel>();
            ArrayList<DETableModel> floatTableModes = new ArrayList<DETableModel>();
            for (DETableModel tableModel : tableModes) {
                List fieldInfos = tableModel.getFields();
                DETableInfo tableInfo = tableModel.getTableInfo();
                TableType tableType = tableInfo.getType();
                if (tableType == TableType.ZB) {
                    fixTableModes.add(tableModel);
                    continue;
                }
                if (tableType != TableType.BIZ) continue;
                floatTableModes.add(tableModel);
            }
            for (DETableModel tableModel : fixTableModes) {
                this.fixDataService.readFixFieldDataFromMidstore(context, dataExchangeTask, tableModel);
            }
            for (DETableModel tableModel : floatTableModes) {
                this.readFloatFieldDataFromMidstore(context, dataExchangeTask, tableModel);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
        finally {
            if (context.getIntfObjects().containsKey("TempAssistantTable")) {
                this.dimensionService.closeTempTable(context);
            }
        }
    }

    private void readFixFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        String nrPeriodCode = context.getExcutePeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDLIST");
        Map tableUnitList = (Map)context.getExcuteParams().get("TABLEUNITLIST");
        Map tableFormList = (Map)context.getExcuteParams().get("TABLEFORMLIST");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            nrPeriodCode = dataTime;
        }
        String dePeriodCode = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), nrPeriodCode);
        if (context.getMappingCache().getPeriodMappingInfos().containsKey(nrPeriodCode)) {
            dePeriodCode = context.getMappingCache().getPeriodMappingInfos().get(nrPeriodCode).getPeriodMapCode();
        }
        if (context.getWorkResult() != null) {
            context.getWorkResult().setNrPeriodCode(nrPeriodCode);
            context.getWorkResult().setNrPeriodTitle(this.dimensionService.getPeriodTitle(context.getTaskDefine().getDateTime(), nrPeriodCode));
            context.getWorkResult().setMidstorePeriodCode(dePeriodCode);
        }
        logger.info("\u6570\u636e\u63a5\u6536\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
        DETableInfo tableInfo = tableModel.getTableInfo();
        String deTableCode = tableInfo.getName();
        MidstoreFieldDTO param = new MidstoreFieldDTO();
        param.setSchemeKey(context.getSchemeKey());
        List<MidstoreFieldDTO> allMidFields = this.fieldService.list(param);
        HashMap allMidFieldMap = new HashMap();
        for (MidstoreFieldDTO field : allMidFields) {
            List<MidstoreFieldDTO> fieldList = null;
            if (allMidFieldMap.containsKey(field.getCode())) {
                fieldList = (List)allMidFieldMap.get(field.getCode());
            } else {
                fieldList = new ArrayList();
                allMidFieldMap.put(field.getCode(), fieldList);
            }
            fieldList.add(field);
        }
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        HashMap<String, DEZBInfo> nrFieldMapDes = new HashMap<String, DEZBInfo>();
        HashMap<String, String> deFieldMapNrs = new HashMap<String, String>();
        HashMap<String, String> nrFieldMapDables = new HashMap<String, String>();
        HashMap nrTableMapFields = new HashMap();
        ArrayList<String> dimFields = new ArrayList<String>();
        for (DimensionValue dim : dimSetMap.values()) {
            dimFields.add(dim.getName());
        }
        boolean hasFile = false;
        ArrayList<String> deFieldNames = new ArrayList<String>();
        HashSet<String> nrFixTableCodes = new HashSet<String>();
        List fieldInfos = tableModel.getRefZBs();
        for (DEZBInfo deField : fieldInfos) {
            List fieldList;
            String deFieldName = deField.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            String nrFieldName = deFieldName;
            String nrTableCode2 = null;
            if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName).getZbMapping().getZbCode();
                nrTableCode2 = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName).getZbMapping().getTable();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping().getZbCode();
                nrTableCode2 = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping().getTable();
            } else if (allMidFieldMap.containsKey(nrFieldName) && (fieldList = (List)allMidFieldMap.get(nrFieldName)).size() > 0) {
                MidstoreFieldDTO findField = (MidstoreFieldDTO)fieldList.get(0);
                DataTable dataTable = this.dataSchemeSevice.getDataTable(findField.getSrcTableKey());
                nrTableCode2 = dataTable.getCode();
            }
            nrFieldMapDes.put(nrFieldName, deField);
            deFieldMapNrs.put(deFieldName, nrFieldName);
            if (StringUtils.isEmpty((String)nrTableCode2)) {
                logger.info("\u6307\u6807\u6709\u95ee\u9898\uff1a" + deFieldName + "," + nrFieldName);
                continue;
            }
            nrFieldMapDables.put(nrFieldName, nrTableCode2);
            if (deField.getDataType() == DEDataType.FILE) {
                hasFile = true;
            }
            if (nrTableMapFields.containsKey(nrTableCode2)) {
                ((List)nrTableMapFields.get(nrTableCode2)).add(nrFieldName);
            } else {
                ArrayList<String> nrFields = new ArrayList<String>();
                for (DimensionValue dim : dimSetMap.values()) {
                    nrFields.add(dim.getName());
                }
                nrFields.add(nrFieldName);
                nrTableMapFields.put(nrTableCode2, nrFields);
            }
            if (tableFieldList != null && tableFieldList.size() > 0 && !tableFieldList.containsKey(nrTableCode2)) continue;
            nrFixTableCodes.add(nrTableCode2);
        }
        HashMap<String, IMidstoreDataSet> nrDataSets = new HashMap<String, IMidstoreDataSet>();
        HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
        HashMap nrDataTableFields = new HashMap();
        for (String nrTableCode : nrFixTableCodes) {
            DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
            if (dataTable == null) {
                logger.info("\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff1a" + nrTableCode);
                continue;
            }
            List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
            HashMap<String, DataField> fieldCodeList = new HashMap<String, DataField>();
            for (DataField field : dataFieldList) {
                fieldCodeList.put(field.getCode(), field);
                if (!StringUtils.isNotEmpty((String)field.getRefDataEntityKey())) continue;
                String baseDataCode = field.getRefDataEntityKey();
                baseDataCode = EntityUtils.getId((String)baseDataCode);
                nrFieldMapBaseDatas.put(field.getCode(), baseDataCode);
            }
            nrDataTableFields.put(nrTableCode, fieldCodeList);
            MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getTaskId());
            if (context.getIntfObjects().containsKey("TempAssistantTable")) {
                TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
                tableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
            }
            tableContext.setFloatImpOpt(2);
            List nrFieldsArr = (List)nrTableMapFields.get(nrTableCode);
            IMidstoreDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey(), nrFieldsArr);
            nrDataSets.put(nrTableCode, bathDataSet);
        }
        try {
            int importRowCount = 0;
            String contition = this.conditionService.getCondtionSQl(context, dePeriodCode);
            logger.info("\u6570\u636e\u63a5\u6536\uff0c" + deTableCode + ",\u6761\u4ef6\u662f\uff0c" + contition);
            MemoryDataSet memoryDataSet = dataExchangeTask.readZBData(deTableCode, contition);
            logger.info("\u6570\u636e\u63a5\u6536\uff0c" + deTableCode + ",\u6761\u4ef6\u662f\uff0c" + contition + ",\u8bfb\u53d6\u5b8c\u6210");
            Metadata metaData = memoryDataSet.getMetadata();
            Iterator it = memoryDataSet.iterator();
            if (!it.hasNext()) {
                logger.info("\u56fa\u5b9a\u6307\u6807\u65e0\u6570\u636e");
            }
            HashMap<String, Integer> deDataCoumnMap = new HashMap<String, Integer>();
            for (int i = 0; i < metaData.getColumnCount(); ++i) {
                Column col = metaData.getColumn(i);
                deDataCoumnMap.put(col.getName(), i);
            }
            while (it.hasNext()) {
                UnitMappingInfo unitInfo;
                DataRow row = (DataRow)it.next();
                String nrOrgCode = null;
                if (deDataCoumnMap.containsKey("MDCODE")) {
                    int columIndex = (Integer)deDataCoumnMap.get("MDCODE");
                    String orgDataCode2 = row.getString(columIndex);
                    if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
                        UnitMappingInfo unitInfo2 = context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
                        orgDataCode2 = unitInfo2.getUnitCode();
                    }
                    nrOrgCode = orgDataCode2;
                    context.getWorkResult().getMidstoreTableUnits().add(nrOrgCode);
                }
                HashMap<String, Object[]> nrTableMapRowDatas = new HashMap<String, Object[]>();
                for (int i = 0; i < metaData.getColumnCount(); ++i) {
                    String fieldValue;
                    block63: {
                        String nrZBCode;
                        block65: {
                            block66: {
                                DEZBInfo deField;
                                String nrTableCode;
                                String defieldName;
                                Object fieldObject;
                                block64: {
                                    Set unitList;
                                    int rowIndex;
                                    Column col = metaData.getColumn(i);
                                    fieldObject = row.getValue(i);
                                    fieldValue = row.getString(i);
                                    nrZBCode = defieldName = col.getName();
                                    Object[] rowData = null;
                                    List nrFieldsArr = null;
                                    if ("MDCODE".equalsIgnoreCase(defieldName)) {
                                        String deOrgCode = fieldValue;
                                        String nrOrgCode2 = fieldValue;
                                        if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(deOrgCode)) {
                                            unitInfo = context.getMappingCache().getSrcUnitMappingInfos().get(deOrgCode);
                                            nrOrgCode2 = unitInfo.getUnitCode();
                                        }
                                        nrOrgCode = nrOrgCode2;
                                        continue;
                                    }
                                    if ("DATATIME".equalsIgnoreCase(defieldName) || !deFieldMapNrs.containsKey(defieldName) || !nrFieldMapDables.containsKey(nrZBCode = (String)deFieldMapNrs.get(defieldName))) continue;
                                    nrTableCode = (String)nrFieldMapDables.get(nrZBCode);
                                    nrFieldsArr = (List)nrTableMapFields.get(nrTableCode);
                                    rowData = (Object[])nrTableMapRowDatas.get(nrTableCode);
                                    if (rowData == null) {
                                        rowData = new Object[nrFieldsArr.size()];
                                        nrTableMapRowDatas.put(nrTableCode, rowData);
                                    }
                                    if ((rowIndex = nrFieldsArr.indexOf(nrZBCode)) < 0 || tableUnitList != null && tableUnitList.size() > 0 && (!tableUnitList.containsKey(nrTableCode) || !(unitList = (Set)tableUnitList.get(nrTableCode)).contains(nrOrgCode))) continue;
                                    deField = (DEZBInfo)nrFieldMapDes.get(defieldName);
                                    if (deField == null || deField.getDataType() == DEDataType.INTEGER || deField.getDataType() == DEDataType.FLOAT) break block63;
                                    if (deField.getDataType() != DEDataType.DATE) break block64;
                                    if (fieldObject instanceof Date) {
                                        Date date = (Date)fieldObject;
                                        fieldValue = this.dateFormatter.format(date);
                                    } else if (fieldObject instanceof GregorianCalendar) {
                                        GregorianCalendar calendar = (GregorianCalendar)fieldObject;
                                        Date date = calendar.getTime();
                                        fieldValue = this.dateFormatter.format(date);
                                    } else {
                                        fieldValue = null;
                                    }
                                    break block63;
                                }
                                if (deField.getDataType() != DEDataType.FILE) break block65;
                                DEAttachMent attachMent = null;
                                if (fieldObject instanceof DEAttachMent) {
                                    attachMent = (DEAttachMent)fieldObject;
                                } else if (fieldObject != null) {
                                    logger.info("\u6587\u4ef6\u578b\u5b57\u6bb5\u5b58\u5728\u95ee\u9898\uff1a" + defieldName);
                                }
                                if (attachMent == null || attachMent.getData() == null) break block66;
                                try {
                                    DataField dataField;
                                    block68: {
                                        block67: {
                                            dataField = null;
                                            if (!nrDataTableFields.containsKey(nrTableCode)) break block67;
                                            Map fieldCodeList = (Map)nrDataTableFields.get(nrTableCode);
                                            if (!fieldCodeList.containsKey(nrZBCode)) break block68;
                                            dataField = (DataField)fieldCodeList.get(nrZBCode);
                                            break block68;
                                        }
                                        DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
                                        if (dataTable != null && (dataField = this.dataSchemeSevice.getDataFieldByTableKeyAndCode(dataTable.getKey(), nrZBCode)) == null) {
                                            List findList = this.dataSchemeSevice.getDataFieldByTableCode(nrTableCode);
                                            for (DataField field : findList) {
                                                if (!nrZBCode.equalsIgnoreCase(field.getCode())) continue;
                                                dataField = field;
                                                break;
                                            }
                                        }
                                    }
                                    MidstoreFileInfo fieldFileInfo = new MidstoreFileInfo();
                                    fieldFileInfo.setDataSchemeKey(context.getTaskDefine().getDataScheme());
                                    if (dataField != null) {
                                        fieldFileInfo.setFieldKey(dataField.getKey());
                                    }
                                    if (tableFormList.containsKey(nrTableCode)) {
                                        fieldFileInfo.setFormKey((String)tableFormList.get(nrTableCode));
                                    }
                                    fieldFileInfo.setFormSchemeKey(context.getFormSchemeKey());
                                    Map<String, DimensionValue> fieldDimSetMap = dimSetMap;
                                    fieldDimSetMap.get(context.getEntityTypeName()).setValue(nrOrgCode);
                                    fieldFileInfo.setDimensionSet(fieldDimSetMap);
                                    fieldFileInfo.setTaskKey(context.getTaskDefine().getKey());
                                    fieldValue = this.attachmentService.saveFileFieldDataToNR(attachMent.getData(), fieldFileInfo);
                                }
                                catch (Exception e) {
                                    logger.error("\u6587\u4ef6\u578b\u5b57\u6bb5" + defieldName + "\u51fa\u9519\uff1a" + e.getMessage(), e);
                                    fieldValue = null;
                                }
                                break block63;
                            }
                            fieldValue = null;
                            break block63;
                        }
                        if (nrFieldMapBaseDatas.containsKey(nrZBCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                            EnumMappingInfo enumMapping;
                            String baseDataCode = (String)nrFieldMapBaseDatas.get(nrZBCode);
                            if (context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getSrcItemMappings().containsKey(fieldValue)) {
                                fieldValue = enumMapping.getSrcItemMappings().get(fieldValue).getItemCode();
                            }
                        }
                    }
                    rowData[rowIndex] = fieldValue;
                }
                for (String nrTableCode : nrDataSets.keySet()) {
                    DimensionValueSet dimSet;
                    Set unitList;
                    IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                    Object[] rowData = (Object[])nrTableMapRowDatas.get(nrTableCode);
                    ArrayList<Object> listRow = new ArrayList<Object>();
                    for (String dimName : dimFields) {
                        int columIndex;
                        DimensionValue dim = dimSetMap.get(dimName);
                        if (dim == null) continue;
                        if ("DATATIME".equalsIgnoreCase(dimName)) {
                            listRow.add(nrPeriodCode);
                            continue;
                        }
                        if ("MD_ORG".equalsIgnoreCase(dimName)) {
                            columIndex = (Integer)deDataCoumnMap.get("MDCODE");
                            String orgDataCode2 = row.getString(columIndex);
                            if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
                                unitInfo = context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
                                orgDataCode2 = unitInfo.getUnitCode();
                            }
                            listRow.add(orgDataCode2);
                            continue;
                        }
                        columIndex = (Integer)deDataCoumnMap.get(dimName);
                        listRow.add(row.getString(columIndex));
                    }
                    for (int j = dimFields.size(); j < rowData.length; ++j) {
                        Object obj = rowData[j];
                        listRow.add(obj);
                    }
                    if (StringUtils.isEmpty((String)nrOrgCode)) {
                        logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bc6\u522b\u6709\u95ee\u9898");
                        continue;
                    }
                    if (context.getExchangeEnityCodes().size() > 0 && !context.getExchangeEnityCodes().contains(nrOrgCode) || tableUnitList != null && tableUnitList.size() > 0 && (!tableUnitList.containsKey(nrTableCode) || !(unitList = (Set)tableUnitList.get(nrTableCode)).contains(nrOrgCode)) || (dimSet = bathDataSet.importDatas(listRow)) == null || dimSet.size() <= 0) continue;
                    ++importRowCount;
                }
            }
            if (importRowCount > 0) {
                for (String nrTableCode : nrDataSets.keySet()) {
                    logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u6307\u6807\u8868\u6570\u636e\u63d0\u4ea4\uff0c" + nrTableCode);
                    IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                    bathDataSet.commit();
                }
            }
        }
        catch (Exception e) {
            for (String nrTableCode : nrDataSets.keySet()) {
                IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                if (bathDataSet == null) continue;
                bathDataSet.close();
            }
            logger.error(e.getMessage(), e);
            this.resultService.addUnitErrorInfo(context, context.getWorkResult(), "\u56fa\u5b9a\u6307\u6807\u5f02\u5e38", e.getMessage());
            throw new MidstoreException("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        finally {
            for (String nrTableCode : nrDataSets.keySet()) {
                IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                if (bathDataSet == null) continue;
                bathDataSet.close();
            }
        }
    }

    private void readFloatFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        Map dimNameValueList;
        String deTableCode;
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDLIST");
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
            logger.info("\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff0c\u4e0d\u63a5\u6536\uff1a" + nrTableCode);
            return;
        }
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        String nrPeriodCode = context.getExcutePeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            nrPeriodCode = dataTime;
        }
        String dePeriodCode = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), nrPeriodCode);
        if (context.getMappingCache().getPeriodMappingInfos().containsKey(nrPeriodCode)) {
            dePeriodCode = context.getMappingCache().getPeriodMappingInfos().get(nrPeriodCode).getPeriodMapCode();
        }
        if (context.getWorkResult() != null) {
            context.getWorkResult().setNrPeriodCode(nrPeriodCode);
            context.getWorkResult().setNrPeriodTitle(this.dimensionService.getPeriodTitle(context.getTaskDefine().getDateTime(), nrPeriodCode));
            context.getWorkResult().setMidstorePeriodCode(dePeriodCode);
        }
        logger.info("\u6570\u636e\u63a5\u6536\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
        List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
        Map<String, DataField> dataFieldMap = dataFieldList.stream().collect(Collectors.toMap(Basic::getCode, DataField2 -> DataField2));
        HashMap<String, Map<String, DataField>> nrDataTableFields = new HashMap<String, Map<String, DataField>>();
        nrDataTableFields.put(nrTableCode, dataFieldMap);
        HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
        for (Object dataField : dataFieldList) {
            if (!StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey())) continue;
            String baseDataCode = dataField.getRefDataEntityKey();
            baseDataCode = EntityUtils.getId((String)baseDataCode);
            nrFieldMapBaseDatas.put(dataField.getCode(), baseDataCode);
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
                    dimSetMap.get(dimName).setValue(sp.toString());
                    continue;
                }
                DimensionValue otherDim = new DimensionValue();
                otherDim.setType(0);
                otherDim.setName(dimName);
                otherDim.setValue(sp.toString());
                dimSetMap.put(dimName, otherDim);
            }
        }
        MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getTaskId());
        if (context.getIntfObjects().containsKey("TempAssistantTable")) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
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
            String deFieldName = deField.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            String mapCode1 = nrTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            String nrFieldName = deFieldName;
            if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping().getZbCode();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode1)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfos().get(mapCode1).getZbMapping().getZbCode();
            } else if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                ZBMappingInfo mapInfo = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName);
                if (dataTable.getCode().equalsIgnoreCase(mapInfo.getZbMapping().getTable())) {
                    nrFieldName = mapInfo.getZbMapping().getZbCode();
                }
            } else if ("MDCODE".equalsIgnoreCase(deFieldName)) {
                nrFieldName = "MDCODE";
            } else if ("TIMEKEY".equalsIgnoreCase(deFieldName)) {
                nrFieldName = "DATATIME";
            }
            if (dataFieldMap.containsKey(nrFieldName)) {
                DataField dataField = dataFieldMap.get(nrFieldName);
                if (dataTable.getDataTableType() == DataTableType.ACCOUNT && dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) continue;
            }
            nrFieldMapDes.put(nrFieldName, deField);
            deFieldMapNrs.put(deFieldName, nrFieldName);
            if (!("ORGCODE".equalsIgnoreCase(nrFieldName) || "MDCODE".equalsIgnoreCase(nrFieldName) || "DATATIME".equalsIgnoreCase(nrFieldName) || dimSetMap.containsKey(nrFieldName))) {
                nrFieldsArr.add(nrFieldName);
            }
            deFieldsArr.add(deFieldName);
            nrFieldMapDes.put(deFieldName, deField);
        }
        if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
            logger.info("\u6570\u636e\u63a5\u6536\u53f0\u5361\u8868\uff1a" + dataTable.getCode());
        }
        try (IMidstoreDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey(), nrFieldsArr);){
            String contition = this.conditionService.getCondtionSQl(context, dePeriodCode);
            MidstoreTableDataHandlerImpl dataHandler = new MidstoreTableDataHandlerImpl(context, bathDataSet, nrPeriodCode, nrTableCode, deTableCode, nrFieldsArr, nrFieldMapDes, nrDataTableFields, dimSetMap, nrFieldMapBaseDatas, dimFields, nrFieldMapDables, deFieldMapNrs, this.dataSchemeSevice, this.attachmentService);
            logger.info("\u6570\u636e\u63a5\u6536\uff1a" + deTableCode + "\uff0c\u6761\u4ef6\uff0c" + contition);
            dataExchangeTask.readTableData(deTableCode, deFieldsArr, contition, (ITableDataHandler)dataHandler);
        }
    }

    private void tranUnitFormsToTables(MidstoreContext context, Map<DimensionValueSet, List<String>> unitFormKeys) {
        this.checkParamService.tranUnitFormsToTables(context, unitFormKeys);
    }
}

