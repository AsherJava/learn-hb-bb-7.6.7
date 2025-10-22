/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.extend.MidstoreSchemeInfoExtendDTO
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.common.ExcutePeriodType
 *  com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType
 *  com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType
 *  com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreBaseDataDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreBaseDataService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService
 *  com.jiuqi.nvwa.midstore.param.service.IMidstoreMappingService
 *  com.jiuqi.nvwa.midstore.work.service.org.IMidstoreOrgDataWorkService
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreDimensionService
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package nr.midstore2.data.param.internal;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.extend.MidstoreSchemeInfoExtendDTO;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.common.ExcutePeriodType;
import com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType;
import com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType;
import com.jiuqi.nvwa.midstore.core.definition.common.PublishStateType;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreBaseDataDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSourceDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreBaseDataService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSourceService;
import com.jiuqi.nvwa.midstore.param.service.IMidstoreMappingService;
import com.jiuqi.nvwa.midstore.work.service.org.IMidstoreOrgDataWorkService;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreDimensionService;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.param.IReportMidstoreParamService;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import nr.midstore2.data.util.auth.IReportMidstoreFormDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ReportMidstoreParamServiceImpl
implements IReportMidstoreParamService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreParamServiceImpl.class);
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IMidstoreBaseDataService baseDataService;
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewContoller;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IReportMidstoreDimensionService dimensionService;
    @Autowired
    private IMidstoreOrgDataWorkService orgDataWorkService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired(required=false)
    private IReportMidstoreFormDataAccess formAccessService;
    @Autowired
    private IMidstoreDimensionService midstoreDimService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IMidstoreSourceService sourceService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private DimensionBuildUtil dimBuildUtil;

    @Override
    public ReportMidstoreContext getReportContext(MidstoreExeContext exeContext) {
        MidstoreSourceDTO oldSource = this.sourceService.getBySchemeAndSource(exeContext.getMidstoreScheme().getKey(), exeContext.getSourceTypeId());
        ReportDataSourceDTO reportSource = new ReportDataSourceDTO(oldSource);
        ReportMidstoreContext reportContext = new ReportMidstoreContext(exeContext);
        reportContext.setMidstoreScheme((MidstoreSchemeDTO)exeContext.getMidstoreScheme());
        reportContext.setSchemeInfo(exeContext.getMidstoreContext().getSchemeInfo());
        reportContext.setDataSourceDTO(reportSource);
        reportContext.setTaskDefine(this.runTimeViewController.queryTaskDefine(reportSource.getTaskKey()));
        if (!exeContext.getMidstoreContext().getExchangeEnityCodes().isEmpty()) {
            reportContext.getExchangeEnityCodes().addAll(exeContext.getMidstoreContext().getExchangeEnityCodes());
        }
        if (!exeContext.getMidstoreContext().getExcuteParams().isEmpty()) {
            reportContext.getExcuteParams().putAll(exeContext.getMidstoreContext().getExcuteParams());
        }
        if (!exeContext.getMidstoreContext().getEntityCache().getEntityCodeList().isEmpty()) {
            reportContext.getEntityCache().getEntityCodeList().putAll(exeContext.getMidstoreContext().getEntityCache().getEntityCodeList());
            reportContext.getEntityCache().getEntityOrgCodeList().putAll(exeContext.getMidstoreContext().getEntityCache().getEntityOrgCodeList());
        }
        reportContext.setSchemeKey(exeContext.getMidstoreScheme().getKey());
        reportContext.setAsyncMonitor(exeContext.getMidstoreContext().getAsyncMonitor());
        reportContext.setConfigKey(exeContext.getMidstoreContext().getConfigKey());
        reportContext.setExcutePeriod(exeContext.getMidstoreContext().getExcutePeriod());
        reportContext.setWorkResult(exeContext.getMidstoreContext().getWorkResult());
        reportContext.setUnitCache(exeContext.getMidstoreContext().getUnitCache());
        reportContext.setDeleteEmptyData(exeContext.getMidstoreContext().isDeleteEmptyData());
        return reportContext;
    }

    @Override
    public MidstoreResultObject doCheckParamsBeforePulish(ReportMidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkLinkTask(context, result)) {
            return result;
        }
        if (!this.checkBaseDataAndFields(context, result)) {
            return result;
        }
        if (!this.checkMapingInfo(context, result)) {
            return result;
        }
        if (!this.checkRepeatUnitCodes(context, result)) {
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    @Override
    public MidstoreResultObject doCheckParamsBeforeCleanData(ReportMidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkLinkTask(context, result)) {
            return result;
        }
        if (!this.checkPublishState(context, result)) {
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    @Override
    public MidstoreResultObject doCheckParamsBeforeGetData(ReportMidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkLinkTask(context, result)) {
            return result;
        }
        if (!this.checkBaseDataAndFields(context, result)) {
            return result;
        }
        if (!this.checkPublishState(context, result)) {
            return result;
        }
        if (!this.checkExcutePeriod(context, result)) {
            return result;
        }
        if (!this.checkMapingInfo(context, result)) {
            return result;
        }
        if (!this.checkRepeatUnitCodes(context, result)) {
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    @Override
    public MidstoreResultObject doCheckParamsBeforePostData(ReportMidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkLinkTask(context, result)) {
            return result;
        }
        if (!this.checkBaseDataAndFields(context, result)) {
            return result;
        }
        if (!this.checkPublishState(context, result)) {
            return result;
        }
        if (!this.checkMapingInfo(context, result)) {
            return result;
        }
        if (!this.checkRepeatUnitCodes(context, result)) {
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    private boolean checkBaseDataAndFields(ReportMidstoreContext context, MidstoreResultObject result) {
        MidstoreFieldDTO fieldParam = new MidstoreFieldDTO();
        fieldParam.setSchemeKey(context.getSchemeKey());
        fieldParam.setSourceType(context.getExeContext().getSourceTypeId());
        List fields = this.fieldService.list(fieldParam);
        if (fields == null || fields.size() == 0) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u7684\u6307\u6807\u672a\u5b9a\u4e49\uff01");
            result.setSuccess(false);
            return false;
        }
        ArrayList<String> baseEntityIds = new ArrayList<String>();
        HashSet<String> baseDataCodes = new HashSet<String>();
        for (MidstoreFieldDTO field : fields) {
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)field.getSrcFieldKey())) {
                DataField dataField = this.dataSchemeSevice.getDataField(field.getSrcFieldKey());
                if (dataField == null) {
                    logger.info("\u6307\u6807\u4e0d\u5b58\u5728\uff1a" + field.getCode() + "," + field.getSrcFieldKey());
                    continue;
                }
                if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey())) continue;
                baseEntityIds.add(dataField.getRefDataEntityKey());
                baseDataCodes.add(EntityUtils.getId((String)dataField.getRefDataEntityKey()));
                continue;
            }
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u7684\u6307\u6807\u6765\u6e90\u672a\u5b9a\u4e49\uff1a" + field.getCode());
            result.setSuccess(false);
            return false;
        }
        if (baseDataCodes.size() > 0) {
            MidstoreBaseDataDTO param = new MidstoreBaseDataDTO();
            param.setSchemeKey(context.getSchemeKey());
            List list = this.baseDataService.list(param);
            if (list == null || list.size() == 0) {
                result.setMessage("\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u7840\u6570\u636e\u672a\u5b9a\u4e49\uff01");
                result.setSuccess(false);
                return false;
            }
            if (baseDataCodes.size() > list.size()) {
                result.setMessage("\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u7840\u6570\u636e\u672a\u5b9a\u4e49\u5b8c\u6574\uff01");
                result.setSuccess(false);
                return false;
            }
        }
        return true;
    }

    private boolean checkPublishState(ReportMidstoreContext context, MidstoreResultObject result) {
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        if (schemeInfo.getPublishState() != PublishStateType.PUBLISHSTATE_SUCCESS) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u672a\u53d1\u5e03\u6210\u529f\uff01");
            result.setSuccess(false);
            return false;
        }
        return true;
    }

    private boolean checkLinkTask(ReportMidstoreContext context, MidstoreResultObject result) {
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)context.getDataSourceDTO().getTaskKey())) {
            result.setMessage("\u672a\u8bbe\u7f6e\u4efb\u52a1\uff01");
            result.setSuccess(false);
            return false;
        }
        TaskDefine task = this.runTimeViewController.queryTaskDefine(context.getDataSourceDTO().getTaskKey());
        if (task == null) {
            result.setMessage("\u6240\u5173\u8054\u7684\u4efb\u52a1\u4e0d\u5b58\u5728\uff01");
            result.setSuccess(false);
            return false;
        }
        context.setTaskDefine(task);
        context.setTaskKey(task.getKey());
        context.setEntityTypeName("");
        if (this.periodAdapter.isPeriodEntity(task.getDateTime())) {
            IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(task.getDateTime());
            context.setDateTypeName(periodEntity.getDimensionName());
        }
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)task.getDw())) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
            context.setEntityTypeName(entityDefine.getDimensionName());
        }
        HashSet dimEntityIdList = new HashSet();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)task.getDims())) {
            String[] dimEntityIds = task.getDims().split(";");
            Collections.addAll(dimEntityIdList, dimEntityIds);
        }
        for (String entityID : dimEntityIdList) {
            String dimName = null;
            if ("ADJUST".equals(entityID)) {
                dimName = "ADJUST";
                continue;
            }
            if (this.periodAdapter.isPeriodEntity(entityID)) {
                IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(entityID);
                dimName = periodEntity.getDimensionName();
            } else {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityID);
                if (!this.checkShowDimEntityByTaskKey(task.getKey(), entityID)) {
                    context.getDimEntityCache().getEntitySingleDims().add(entityDefine.getDimensionName());
                    context.getDimEntityCache().getEntitySingleIds().add(entityID);
                    String fieldName = this.getDimAttributeByReportDim(task.getDataScheme(), entityID);
                    context.getDimEntityCache().getEntitySingleDimAndFields().put(entityDefine.getDimensionName(), fieldName);
                }
                dimName = entityDefine.getDimensionName();
            }
            context.getDimEntityCache().getEntityDimAndEntityIds().put(dimName, entityID);
        }
        return true;
    }

    @Override
    public List<String> getVisibleEntityIds(String taskKey) {
        ArrayList<String> list = new ArrayList<String>();
        TaskDefine task = this.runTimeViewController.queryTaskDefine(taskKey);
        if (task != null) {
            HashSet dimEntityIdList = new HashSet();
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)task.getDims())) {
                String[] dimEntityIds = task.getDims().split(";");
                Collections.addAll(dimEntityIdList, dimEntityIds);
            }
            for (String entityID : dimEntityIdList) {
                if ("ADJUST".equals(entityID) || this.periodAdapter.isPeriodEntity(entityID) || !this.checkShowDimEntityByTaskKey(task.getKey(), entityID)) continue;
                list.add(entityID);
            }
        }
        return list;
    }

    private boolean checkShowDimEntity(String formSchemeKey, String entityKey) {
        String dimensionEntity = this.runTimeViewController.getFormScheme(formSchemeKey).getDw();
        String id = this.formSchemeService.getDimAttributeByReportDim(formSchemeKey, entityKey);
        if (StringUtils.hasText(id)) {
            return this.entityMetaService.getEntityModel(dimensionEntity).getAttribute(id).isMultival();
        }
        return true;
    }

    private boolean checkShowDimEntityByTaskKey(String taskKey, String entityKey) {
        TaskDefine task = this.runTimeViewController.queryTaskDefine(taskKey);
        String dimensionEntity = this.runTimeViewController.queryTaskDefine(taskKey).getDw();
        String fieldId = this.getDimAttributeByReportDim(task.getDataScheme(), entityKey);
        if (StringUtils.hasText(fieldId)) {
            return this.entityMetaService.getEntityModel(dimensionEntity).getAttribute(fieldId).isMultival();
        }
        return true;
    }

    public String getDimAttributeByReportDim(String dataSchemeKey, String dimKey) {
        List dimensions = this.dataSchemeSevice.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        DataDimension report = dimensions.stream().filter(dataDimension -> dimKey.equals(dataDimension.getDimKey())).findFirst().orElse(null);
        return report == null ? null : report.getDimAttribute();
    }

    private boolean checkExcutePeriod(ReportMidstoreContext context, MidstoreResultObject result) {
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        if (schemeInfo.getExcutePeriodType() == null || schemeInfo.getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_CURRENT || schemeInfo.getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_LAST) {
            TaskDefine task = context.getTaskDefine();
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(task.getDateTime());
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
            Calendar instance = Calendar.getInstance();
            String curPeriod = PeriodUtils.getPeriodFromDate((int)periodEntity.getPeriodType().type(), (Date)instance.getTime());
            if (schemeInfo.getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_LAST) {
                curPeriod = periodProvider.priorPeriod(curPeriod);
            }
            context.setExcutePeriod(curPeriod);
        } else if (schemeInfo.getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_APPOINT && com.jiuqi.bi.util.StringUtils.isEmpty((String)schemeInfo.getExcutePeriod())) {
            result.setMessage("\u672a\u6307\u5b9a\u6267\u884c\u8ba1\u5212\u4efb\u52a1\u7684\u65f6\u671f\uff01");
            result.setSuccess(false);
            return false;
        }
        return true;
    }

    private boolean checkMapingInfo(ReportMidstoreContext context, MidstoreResultObject result) {
        return this.midstoreMappingService.checkMaping((MidstoreContext)context, result);
    }

    private boolean checkRepeatUnitCodes(ReportMidstoreContext context, MidstoreResultObject result) {
        String excutePeriod = this.midstoreDimService.getExcutePeriod((MidstoreContext)context);
        GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)PeriodUtil.getPeriodWrapper((String)excutePeriod));
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTaskKey());
        String orgCode = EntityUtils.getId((String)taskDefine.getDw());
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCategoryname(orgCode);
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgParam.setVersionDate(startCalendar.getTime());
        orgParam.setAuthType(OrgDataOption.AuthType.NONE);
        PageVO queryRes = this.orgDataClient.list(orgParam);
        HashMap<String, OrgDO> dataMap = new HashMap<String, OrgDO>();
        if (queryRes != null && queryRes.getRows() != null) {
            for (OrgDO data : queryRes.getRows()) {
                dataMap.put(data.getCode(), data);
            }
        }
        this.midstoreMappingService.initOrgMapping((MidstoreContext)context);
        MidstoreOrgDataDTO queryParam = new MidstoreOrgDataDTO();
        queryParam.setSchemeKey(context.getSchemeKey());
        List orgDatas = this.orgDataService.list(queryParam);
        HashSet<String> repeatUnitCodes = new HashSet<String>();
        if (context.getSchemeInfo().isAllOrgData()) {
            for (String aCode : dataMap.keySet()) {
                OrgDO ordData = (OrgDO)dataMap.get(aCode);
                String unitCode = aCode;
                context.getExchangeEnityCodes().add(unitCode);
                String parantCode = ordData.getParentcode();
                if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                    unitCode = ((UnitMappingInfo)context.getMappingCache().getUnitMappingInfos().get(unitCode)).getUnitMapingCode();
                } else if (context.isUseOrgCode() && context.getEntityCache().getEntityCodeList().containsKey(unitCode)) {
                    unitCode = ((MidstoreOrgDataDTO)context.getEntityCache().getEntityCodeList().get(unitCode)).getOrgCode();
                }
                if (context.getMappingCache().getUnitMappingInfos().containsKey(parantCode)) {
                    parantCode = ((UnitMappingInfo)context.getMappingCache().getUnitMappingInfos().get(parantCode)).getUnitMapingCode();
                } else if (context.isUseOrgCode() && context.getEntityCache().getEntityCodeList().containsKey(parantCode)) {
                    parantCode = ((MidstoreOrgDataDTO)context.getEntityCache().getEntityCodeList().get(parantCode)).getOrgCode();
                }
                if (!repeatUnitCodes.contains(unitCode)) {
                    repeatUnitCodes.add(unitCode);
                    continue;
                }
                result.setMessage("\u5b58\u5728\u91cd\u7801\u5355\u4f4d\uff0c" + unitCode);
                result.setSuccess(false);
                return false;
            }
        } else {
            for (MidstoreOrgDataDTO ordData : orgDatas) {
                String unitCode = ordData.getCode();
                String parantCode = ordData.getParentCode();
                String unitTitle = ordData.getTitle();
                OrgDO oldOrdData = (OrgDO)dataMap.get(unitCode);
                if (oldOrdData != null) {
                    if (com.jiuqi.bi.util.StringUtils.isEmpty((String)unitTitle)) {
                        unitTitle = oldOrdData.getName();
                    }
                    if (com.jiuqi.bi.util.StringUtils.isEmpty((String)parantCode)) {
                        parantCode = oldOrdData.getParentcode();
                    }
                } else {
                    logger.info("\u5355\u4f4d\u4e0d\u5b58\u5728\uff1a" + unitCode + "," + excutePeriod + "," + startCalendar.getTime());
                }
                context.getExchangeEnityCodes().add(unitCode);
                if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                    unitCode = ((UnitMappingInfo)context.getMappingCache().getUnitMappingInfos().get(unitCode)).getUnitMapingCode();
                }
                if (context.getMappingCache().getUnitMappingInfos().containsKey(parantCode)) {
                    parantCode = ((UnitMappingInfo)context.getMappingCache().getUnitMappingInfos().get(parantCode)).getUnitMapingCode();
                }
                if (!repeatUnitCodes.contains(unitCode)) {
                    repeatUnitCodes.add(unitCode);
                    continue;
                }
                result.setMessage("\u5b58\u5728\u91cd\u7801\u5355\u4f4d\uff0c" + unitCode);
                result.setSuccess(false);
                return false;
            }
        }
        return true;
    }

    @Override
    public MidstoreResultObject doLoadFormScheme(ReportMidstoreContext context, boolean isLoadFormDefine) throws MidstoreException {
        Map<String, DimensionValue> dims = this.dimensionService.getDimSetMap(context);
        String nrPeriodCode = this.dimensionService.getExcuteNrPeriod(context);
        String formSchemeKey = null;
        try {
            SchemePeriodLinkDefine periodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(nrPeriodCode, context.getTaskDefine().getKey());
            if (periodLinkDefine != null) {
                formSchemeKey = periodLinkDefine.getSchemeKey();
                context.setFormSchemeKey(formSchemeKey);
            }
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            throw new MidstoreException((Throwable)e1);
        }
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formSchemeKey) && isLoadFormDefine) {
            context.getExcuteParams().put("FORMSCHEME", formSchemeKey);
            HashMap tableFieldList = new HashMap();
            HashMap<String, String> tableKeyCodes = new HashMap<String, String>();
            HashMap<String, String> tableFormKeyList = new HashMap<String, String>();
            HashMap<String, String> tableRegionKeyList = new HashMap<String, String>();
            HashMap<String, Object> formKeyTablesList = new HashMap<String, Object>();
            List formList = null;
            try {
                formList = this.runTimeAuthViewContoller.queryAllFormDefinesByFormScheme(formSchemeKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException((Throwable)e);
            }
            if (formList != null) {
                for (FormDefine form : formList) {
                    Object field2;
                    String formKey = form.getKey();
                    List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
                    List dataFields = this.dataSchemeSevice.getDataFields(fieldKeys);
                    HashMap<String, String> fieldKeyTables = new HashMap<String, String>();
                    for (Object field2 : dataFields) {
                        String tableCode = null;
                        if (tableKeyCodes.containsKey(field2.getDataTableKey())) {
                            tableCode = (String)tableKeyCodes.get(field2.getDataTableKey());
                        } else {
                            DataTable dataTable = this.dataSchemeSevice.getDataTable(field2.getDataTableKey());
                            tableKeyCodes.put(field2.getDataTableKey(), dataTable.getCode());
                            tableCode = dataTable.getCode();
                        }
                        fieldKeyTables.put(field2.getKey(), tableCode);
                        Set<String> fieldCodes = null;
                        if (tableFieldList.containsKey(tableCode)) {
                            fieldCodes = (Set)tableFieldList.get(tableCode);
                        } else {
                            fieldCodes = new HashSet();
                            tableFieldList.put(tableCode, fieldCodes);
                            tableFormKeyList.put(tableCode, formKey);
                        }
                        Object formTableCodes = null;
                        if (formKeyTablesList.containsKey(formKey)) {
                            formTableCodes = (Set)formKeyTablesList.get(formKey);
                            if (!formTableCodes.contains(tableCode)) {
                                formTableCodes.add(tableCode);
                            }
                        } else {
                            formTableCodes = new HashSet();
                            formTableCodes.add(tableCode);
                            formKeyTablesList.put(formKey, formTableCodes);
                        }
                        fieldCodes.add(field2.getCode());
                    }
                    List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
                    field2 = regions.iterator();
                    while (field2.hasNext()) {
                        DataRegionDefine region = (DataRegionDefine)field2.next();
                        List regionFields = this.runTimeViewController.getFieldKeysInRegion(region.getKey());
                        for (String fieldKey : regionFields) {
                            String tableCode;
                            if (!fieldKeyTables.containsKey(fieldKey) || tableRegionKeyList.containsKey(tableCode = (String)fieldKeyTables.get(fieldKey))) continue;
                            tableRegionKeyList.put(tableCode, region.getKey());
                        }
                    }
                }
            }
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formSchemeKey) && dims.containsKey("MD_CURRENCY")) {
                HashMap unitDimsList = new HashMap();
                HashMap<String, Set<String>> dimNameValueList = new HashMap<String, Set<String>>();
                DimensionCollection dimCollection = this.dimBuildUtil.getDimensionCollection(dims, formSchemeKey);
                List dimensionCombinations = dimCollection.getDimensionCombinations();
                for (DimensionCombination dimensionCombination : dimensionCombinations) {
                    DimensionValueSet unitDim = dimensionCombination.toDimensionValueSet();
                    Object obj = unitDim.getValue(context.getEntityTypeName());
                    if (obj == null) continue;
                    String unitCode = obj.toString();
                    if (!context.getExchangeEnityCodes().isEmpty() && !context.getExchangeEnityCodes().contains(unitCode)) continue;
                    List<DimensionValueSet> unitDimList = null;
                    if (unitDimsList.containsKey(unitCode)) {
                        unitDimList = (List)unitDimsList.get(unitCode);
                    } else {
                        unitDimList = new ArrayList();
                        unitDimsList.put(unitCode, unitDimList);
                    }
                    unitDimList.add(unitDim);
                    for (int i = 0; i < unitDim.size(); ++i) {
                        String dimValueCode;
                        String dimName = unitDim.getName(i);
                        Set<String> dimValues = null;
                        if (dimNameValueList.containsKey(dimName)) {
                            dimValues = (Set)dimNameValueList.get(dimName);
                        } else {
                            dimValues = new HashSet();
                            dimNameValueList.put(dimName, dimValues);
                        }
                        if (unitDim.getValue(i) == null || dimValues.contains(dimValueCode = unitDim.getValue(i).toString())) continue;
                        dimValues.add(dimValueCode);
                    }
                }
                context.getExcuteParams().put("UNITDIMSLIST", unitDimsList);
                context.getExcuteParams().put("DIMNAMEVALUELIST", dimNameValueList);
            }
            context.getExcuteParams().put("TABLEFIELDlIST", tableFieldList);
            context.getExcuteParams().put("TABLEFORMLIST", tableFormKeyList);
            context.getExcuteParams().put("TABLEREGIONLIST", tableRegionKeyList);
            context.getExcuteParams().put("FORMTABLESLIST", formKeyTablesList);
        }
        return new MidstoreResultObject(true, "");
    }

    @Override
    public void tranUnitFormsToTables(ReportMidstoreContext context, Map<DimensionValueSet, List<String>> unitFormKeys) {
        HashMap formUnitDims = new HashMap();
        HashMap<String, List<DimensionValueSet>> unitDimsList = new HashMap<String, List<DimensionValueSet>>();
        HashMap<String, Set<String>> dimNameValueList = new HashMap<String, Set<String>>();
        HashMap formUnitList = new HashMap();
        String formSchemeKey = null;
        String importDataTime = null;
        if (!unitFormKeys.isEmpty()) {
            context.getExchangeEnityCodes().clear();
        }
        if (!unitFormKeys.isEmpty()) {
            for (DimensionValueSet unitDim : unitFormKeys.keySet()) {
                String unitCode = (String)unitDim.getValue(context.getEntityTypeName());
                List<String> formKeys = unitFormKeys.get(unitDim);
                Iterator iterator = formKeys.iterator();
                while (iterator.hasNext()) {
                    String formKey = (String)iterator.next();
                    List<DimensionValueSet> unitDims = null;
                    if (formUnitDims.containsKey(formKey)) {
                        unitDims = (List)formUnitDims.get(formKey);
                    } else {
                        unitDims = new ArrayList();
                        formUnitDims.put(formKey, unitDims);
                    }
                    unitDims.add(unitDim);
                    List<String> unitLists = null;
                    if (formUnitList.containsKey(formKey)) {
                        unitLists = (List)formUnitList.get(formKey);
                    } else {
                        unitLists = new ArrayList();
                        formUnitList.put(formKey, unitLists);
                    }
                    unitLists.add(unitCode);
                }
                this.setUnitDim(context, unitCode, unitDim, unitDimsList, dimNameValueList);
            }
        } else {
            try {
                Map<String, DimensionValue> dimensionValueSet = this.dimensionService.getDimSetMap(context);
                DimensionValueSet valueSet = new DimensionValueSet();
                List tranDims = new ArrayList();
                for (String string : dimensionValueSet.keySet()) {
                    String dimValueCode = dimensionValueSet.get(string).getValue();
                    String[] values = dimValueCode.split(",");
                    List<String> valueList = Arrays.asList(values);
                    if (valueList.size() > 1) {
                        valueSet.setValue(string, valueList);
                        continue;
                    }
                    if ("MD_CURRENCY".equalsIgnoreCase(string) && "baseCurrency".equalsIgnoreCase(dimValueCode)) {
                        dimValueCode = "PROVIDER_BASECURRENCY";
                    }
                    valueSet.setValue(string, (Object)dimValueCode);
                }
                DimensionCollection dimensionCollection = this.dimBuildUtil.getDimensionCollection(valueSet, context.getFormSchemeKey());
                tranDims = DimensionValueSetUtil.toDimensionValueSetList((DimensionCollection)dimensionCollection);
                for (DimensionValueSet unitDim : tranDims) {
                    String unitCode = (String)unitDim.getValue(context.getEntityTypeName());
                    if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)unitCode)) continue;
                    String[] unitCodes = unitCode.split(",");
                    for (String unitCode2 : unitCodes) {
                        this.setUnitDim(context, unitCode2, unitDim, unitDimsList, dimNameValueList);
                    }
                }
            }
            catch (MidstoreException e) {
                logger.error("\u8f6c\u6362\u7ef4\u5ea6\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
        }
        context.getExcuteParams().put("FORMUNITDIM", formUnitDims);
        context.getExcuteParams().put("FORMUNITLIST", formUnitList);
        context.getExcuteParams().put("UNITDIMSLIST", unitDimsList);
        context.getExcuteParams().put("DIMNAMEVALUELIST", dimNameValueList);
        if (com.jiuqi.bi.util.StringUtils.isEmpty(importDataTime) && unitFormKeys.size() > 0) {
            DimensionValueSet unitDim = (DimensionValueSet)unitFormKeys.keySet().toArray()[0];
            importDataTime = (String)unitDim.getValue(context.getDateTypeName());
            HashMap<String, DimensionValue> otherDimSetMap = new HashMap<String, DimensionValue>();
            for (int i = 0; i < unitDim.size(); ++i) {
                String dimCode = unitDim.getName(i);
                String string = (String)unitDim.getValue(i);
                if (context.getEntityTypeName().equalsIgnoreCase(dimCode) || context.getDateTypeName().equalsIgnoreCase(dimCode)) continue;
                DimensionValue dim = new DimensionValue();
                dim.setType(0);
                dim.setName(dimCode);
                dim.setValue(string);
                otherDimSetMap.put(context.getDateTypeName(), dim);
            }
            context.getExcuteParams().put("OTHERDIM", otherDimSetMap);
        }
        if (com.jiuqi.bi.util.StringUtils.isEmpty(formSchemeKey) && formUnitDims.size() > 0) {
            String curformKey = (String)formUnitDims.keySet().toArray()[0];
            FormDefine formDefine = this.runTimeViewController.queryFormById(curformKey);
            formSchemeKey = formDefine.getFormScheme();
            context.setFormSchemeKey(formSchemeKey);
            context.getExcuteParams().put("FORMSCHEME", formSchemeKey);
            HashMap tableFieldList = new HashMap();
            HashMap<String, String> tableKeyCodes = new HashMap<String, String>();
            HashMap hashMap = new HashMap();
            HashMap<String, String> tableFormKeyList = new HashMap<String, String>();
            HashMap<String, String> tableRegionKeyList = new HashMap<String, String>();
            HashMap<String, Set<String>> formKeyTablesList = new HashMap<String, Set<String>>();
            for (String formKey : formUnitDims.keySet()) {
                List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
                List dataFields = this.dataSchemeSevice.getDataFields(fieldKeys);
                HashMap<String, String> fieldKeyTables = new HashMap<String, String>();
                for (DataField field : dataFields) {
                    String tableCode = null;
                    if (tableKeyCodes.containsKey(field.getDataTableKey())) {
                        tableCode = (String)tableKeyCodes.get(field.getDataTableKey());
                    } else {
                        DataTable dataTable = this.dataSchemeSevice.getDataTable(field.getDataTableKey());
                        tableKeyCodes.put(field.getDataTableKey(), dataTable.getCode());
                        tableCode = dataTable.getCode();
                    }
                    fieldKeyTables.put(field.getKey(), tableCode);
                    Set<String> fieldCodes = null;
                    if (tableFieldList.containsKey(tableCode)) {
                        fieldCodes = (Set)tableFieldList.get(tableCode);
                    } else {
                        fieldCodes = new HashSet();
                        tableFieldList.put(tableCode, fieldCodes);
                        HashSet tableUnits = null;
                        if (hashMap.containsKey(tableCode)) {
                            tableUnits = (HashSet)hashMap.get(tableCode);
                        } else {
                            List formUnits = (List)formUnitList.get(formKey);
                            tableUnits = new HashSet();
                            tableUnits.addAll(formUnits);
                            hashMap.put(tableCode, tableUnits);
                        }
                        tableFormKeyList.put(tableCode, formKey);
                    }
                    Set<String> formTableCodes = null;
                    if (formKeyTablesList.containsKey(formKey)) {
                        formTableCodes = (Set)formKeyTablesList.get(formKey);
                        if (!formTableCodes.contains(tableCode)) {
                            formTableCodes.add(tableCode);
                        }
                    } else {
                        formTableCodes = new HashSet();
                        formTableCodes.add(tableCode);
                        formKeyTablesList.put(formKey, formTableCodes);
                    }
                    fieldCodes.add(field.getCode());
                }
                List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
                for (DataRegionDefine region : regions) {
                    List regionFields = this.runTimeViewController.getFieldKeysInRegion(region.getKey());
                    for (String fieldKey : regionFields) {
                        String tableCode;
                        if (!fieldKeyTables.containsKey(fieldKey) || tableRegionKeyList.containsKey(tableCode = (String)fieldKeyTables.get(fieldKey))) continue;
                        tableRegionKeyList.put(tableCode, region.getKey());
                    }
                }
            }
            context.getExcuteParams().put("TABLEFIELDlIST", tableFieldList);
            context.getExcuteParams().put("TABLEUNITLIST", hashMap);
            context.getExcuteParams().put("TABLEFORMLIST", tableFormKeyList);
            context.getExcuteParams().put("TABLEREGIONLIST", tableRegionKeyList);
            context.getExcuteParams().put("FORMTABLESLIST", formKeyTablesList);
        }
    }

    private void setUnitDim(ReportMidstoreContext context, String unitCode, DimensionValueSet unitDim, Map<String, List<DimensionValueSet>> unitDimsList, Map<String, Set<String>> dimNameValueList) {
        List<Object> unitDimList = null;
        if (unitDimsList.containsKey(unitCode)) {
            unitDimList = unitDimsList.get(unitCode);
        } else {
            unitDimList = new ArrayList();
            unitDimsList.put(unitCode, unitDimList);
        }
        unitDimList.add(unitDim);
        for (int i = 0; i < unitDim.size(); ++i) {
            String dimValueCode;
            String dimName = unitDim.getName(i);
            Set<Object> dimValues = null;
            if (dimNameValueList.containsKey(dimName)) {
                dimValues = dimNameValueList.get(dimName);
            } else {
                dimValues = new HashSet();
                dimNameValueList.put(dimName, dimValues);
            }
            if (unitDim.getValue(i) == null || dimValues.contains(dimValueCode = unitDim.getValue(i).toString())) continue;
            dimValues.add(dimValueCode);
        }
        if (!context.getExchangeEnityCodes().contains(unitCode)) {
            context.getExchangeEnityCodes().add(unitCode);
        }
    }

    @Override
    public Map<DimensionValueSet, List<String>> getUnitFormKeys(ReportMidstoreContext context, FormAccessType formAccess) throws MidstoreException {
        return this.getUnitFormKeysByType(context, formAccess, MidstoreOperateType.OPERATETYPE_DEFAULT);
    }

    @Override
    public Map<DimensionValueSet, List<String>> getUnitFormKeysByType(ReportMidstoreContext context, FormAccessType formAccess, MidstoreOperateType operateType) throws MidstoreException {
        String paramformCode;
        MidstoreSchemeInfoDTO schemeInfo;
        MidstoreSchemeInfoExtendDTO extDto;
        String flag;
        List unitCodes;
        String orgCodes1;
        String[] dimValue;
        HashMap<DimensionValueSet, List<String>> unitFormKeys = new HashMap<DimensionValueSet, List<String>>();
        Map<String, DimensionValue> dims = this.dimensionService.getDimSetMap(context);
        String nrPeriodCode = this.dimensionService.getExcuteNrPeriod(context);
        String formSchemeKey = context.getFormSchemeKey();
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)formSchemeKey)) {
            try {
                SchemePeriodLinkDefine periodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(nrPeriodCode, context.getTaskDefine().getKey());
                if (periodLinkDefine == null) {
                    throw new MidstoreException("\u8be5\u65f6\u671f\u65e0\u5173\u8054\u62a5\u8868\u65b9\u6848\uff0c" + nrPeriodCode);
                }
                formSchemeKey = periodLinkDefine.getSchemeKey();
                context.setFormSchemeKey(formSchemeKey);
            }
            catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
                throw new MidstoreException((Throwable)e1);
            }
        }
        HashMap<String, DimensionValue> otherDims = new HashMap<String, DimensionValue>();
        for (String string : dims.keySet()) {
            DimensionValue dim = dims.get(string);
            if (com.jiuqi.bi.util.StringUtils.isEmpty((String)dim.getName()) || dim.getName().equalsIgnoreCase(context.getEntityTypeName()) || dim.getName().equalsIgnoreCase(context.getDateTypeName())) continue;
            otherDims.put(string, dim);
        }
        if (context.getDataSourceDTO() != null && !context.getDataSourceDTO().getDimSetMap().isEmpty()) {
            for (Map.Entry entry : context.getDataSourceDTO().getDimSetMap().entrySet()) {
                String dimName;
                IEntityDefine define = this.entityMetaService.queryEntity((String)entry.getKey());
                if (define == null || otherDims.containsKey(dimName = define.getDimensionName())) continue;
                dimValue = new DimensionValue();
                dimValue.setName(dimName);
                dimValue.setValue("");
                dimValue.setType(0);
                otherDims.put(dimName, (DimensionValue)dimValue);
            }
        } else {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            List<String> list = this.dimensionService.getDimNamesByFormScheme(formScheme);
            for (String dimName : list) {
                if (otherDims.containsKey(dimName) || "ADJUST".equalsIgnoreCase(dimName)) continue;
                dimValue = new DimensionValue();
                dimValue.setName(dimName);
                dimValue.setValue("");
                dimValue.setType(0);
                otherDims.put(dimValue.getName(), (DimensionValue)dimValue);
            }
        }
        ArrayList<String> queryUnitCodes = new ArrayList<String>();
        HashSet<String> hashSet = new HashSet<String>();
        if (context.getExcuteParams().containsKey("OrgData") && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)(orgCodes1 = (String)context.getExcuteParams().get("OrgData")))) {
            String[] orgCodes = orgCodes1.split(",");
            for (String orgCode : orgCodes) {
                hashSet.add(orgCode);
            }
        }
        if (FormAccessType.FORMACCESS_WRITE == formAccess) {
            if (context.getSchemeInfo().isAllOrgData()) {
                unitCodes = this.orgDataWorkService.getUnitCodesByOrgDataByType((MidstoreContext)context, operateType);
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u5141\u8bb8\u4ea4\u6362\u6240\u6709\u5355\u4f4d,\u6570\u91cf\uff1a" + unitCodes.size());
                for (String orgCode : unitCodes) {
                    if (!hashSet.isEmpty() && !hashSet.contains(orgCode)) continue;
                    queryUnitCodes.add(orgCode);
                }
            } else {
                List schemeUnitCodes = this.orgDataWorkService.getUnitCodesByMidstoreScheme((MidstoreContext)context);
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u7684\u5355\u4f4d\u6570\uff1a" + schemeUnitCodes.size());
                for (String orgCode : schemeUnitCodes) {
                    if (!hashSet.isEmpty() && !hashSet.contains(orgCode)) continue;
                    queryUnitCodes.add(orgCode);
                }
                logger.info("\u9700\u8981\u67e5\u8be2\u6743\u9650\u7684\u5355\u4f4d\u6570\uff1a" + queryUnitCodes.size());
            }
        } else if (FormAccessType.FORMACCESS_READ == formAccess) {
            if (context.getSchemeInfo().isAllOrgData()) {
                unitCodes = this.orgDataWorkService.getUnitCodesByOrgDataByType((MidstoreContext)context, operateType);
                for (String orgCode : unitCodes) {
                    if (!hashSet.isEmpty() && !hashSet.contains(orgCode)) continue;
                    queryUnitCodes.add(orgCode);
                }
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u5141\u8bb8\u4ea4\u6362\u6240\u6709\u5355\u4f4d\uff0c,\u6570\u91cf\uff1a" + unitCodes.size());
            } else {
                unitCodes = this.orgDataWorkService.getUnitCodesByMidstoreScheme((MidstoreContext)context);
                for (String orgCode : unitCodes) {
                    if (!hashSet.isEmpty() && !hashSet.contains(orgCode)) continue;
                    queryUnitCodes.add(orgCode);
                }
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u7684\u5355\u4f4d\u6570\uff1a" + unitCodes.size());
            }
            logger.info("\u9700\u8981\u67e5\u8be2\u6743\u9650\u7684\u5355\u4f4d\u6570\uff1a" + queryUnitCodes.size());
        }
        if (queryUnitCodes.isEmpty()) {
            logger.info("\u6ca1\u7b26\u5408\u7684\u5355\u4f4d\u6743\u9650\uff1a" + queryUnitCodes.size());
            return unitFormKeys;
        }
        if (operateType == MidstoreOperateType.OPERATETYPE_POST && context.getExcuteParams().containsKey("EXCUTE_SOURCE") && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)(flag = (String)context.getExcuteParams().get("EXCUTE_SOURCE"))) && "EXCUTE_SOURCE_PLANTASK".equalsIgnoreCase(flag) && (extDto = (schemeInfo = context.getSchemeInfo()).getExtendDTO()) != null && extDto.getFilterDataStates() != null && !extDto.getFilterDataStates().isEmpty()) {
            List<String> unitList = this.formAccessService.getUnitListByfiterUnitState(formSchemeKey, queryUnitCodes, nrPeriodCode, otherDims, extDto.getFilterDataStates());
            logger.info("\u8fc7\u6ee4\u5355\u4f4d\u72b6\u6001\u540e\u7684\u5355\u4f4d\u6570\uff1a" + unitList.size());
            HashSet<String> filterUnitList = new HashSet<String>();
            filterUnitList.addAll(unitList);
            ArrayList<String> unitCodes2 = new ArrayList<String>();
            unitCodes2.addAll(queryUnitCodes);
            queryUnitCodes.clear();
            for (String unitCode : unitCodes2) {
                if (filterUnitList.isEmpty()) {
                    this.resultService.addUnitErrorInfo((MidstoreContext)context, context.getWorkResult(), "\u5355\u4f4d\u72b6\u6001\u4e0d\u6ee1\u8db3", unitCode, null);
                    continue;
                }
                if (filterUnitList.contains(unitCode)) {
                    queryUnitCodes.add(unitCode);
                    continue;
                }
                this.resultService.addUnitErrorInfo((MidstoreContext)context, context.getWorkResult(), "\u5355\u4f4d\u72b6\u6001\u4e0d\u6ee1\u8db3", unitCode, null);
            }
            if (queryUnitCodes.isEmpty()) {
                logger.info("\u6ca1\u7b26\u5408\u7684\u5355\u4f4d\u72b6\u6001\uff1a" + queryUnitCodes.size());
                return unitFormKeys;
            }
        }
        HashSet<String> paramFormCodes = null;
        if (context.getExcuteParams().containsKey("FormCode") && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)(paramformCode = (String)context.getExcuteParams().get("FormCode")))) {
            String[] formCodes;
            paramFormCodes = new HashSet<String>();
            for (String formCode : formCodes = paramformCode.split(",")) {
                paramFormCodes.add(formCode);
            }
        }
        Map<DimensionValueSet, MistoreWorkUnitInfo> unitFormResons = this.formAccessService.getFormDataAccessByReason(formSchemeKey, queryUnitCodes, nrPeriodCode, otherDims, formAccess);
        HashSet<String> midstoreFormKeys = new HashSet<String>();
        MidstoreFieldDTO fieldParam = new MidstoreFieldDTO();
        fieldParam.setSchemeKey(context.getSchemeKey());
        fieldParam.setSourceType(context.getExeContext().getSourceTypeId());
        List midstoreFields = this.fieldService.list(fieldParam);
        HashMap<String, MidstoreFieldDTO> midStoreFieldsMap = new HashMap<String, MidstoreFieldDTO>();
        if (midstoreFields != null && !midstoreFields.isEmpty()) {
            for (MidstoreFieldDTO field : midstoreFields) {
                midStoreFieldsMap.put(field.getSrcFieldKey(), field);
            }
            List nrForms = null;
            try {
                nrForms = this.runTimeAuthViewContoller.queryAllFormDefinesByFormScheme(formSchemeKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException((Throwable)e);
            }
            if (nrForms != null) {
                block15: for (FormDefine nrForm : nrForms) {
                    List fieldKeys = this.runTimeAuthViewContoller.getFieldKeysInForm(nrForm.getKey());
                    if (fieldKeys == null || fieldKeys.isEmpty()) continue;
                    List dataFields = this.dataSchemeSevice.getDataFields(fieldKeys);
                    for (DataField field : dataFields) {
                        if (!midStoreFieldsMap.containsKey(field.getKey())) continue;
                        midstoreFormKeys.add(nrForm.getKey());
                        continue block15;
                    }
                }
            }
        }
        if (unitFormResons != null) {
            for (DimensionValueSet unitDim : unitFormResons.keySet()) {
                FormDefine form;
                String unitCode = (String)unitDim.getValue(context.getEntityTypeName());
                MistoreWorkUnitInfo unitInfo = unitFormResons.get(unitDim);
                List<String> formList = null;
                if (unitFormKeys.containsKey(unitDim)) {
                    formList = (List)unitFormKeys.get(unitDim);
                } else {
                    formList = new ArrayList();
                    unitFormKeys.put(unitDim, formList);
                }
                if (unitInfo.isSuccess()) {
                    for (MistoreWorkFormInfo formInfo : unitInfo.getFormInfos().values()) {
                        if (!formInfo.isSuccess() || formList.contains(formInfo.getFormKey())) continue;
                        form = this.runTimeViewController.queryFormById(formInfo.getFormKey());
                        if (paramFormCodes != null && !paramFormCodes.contains(form.getFormCode()) || !midstoreFormKeys.isEmpty() && !midstoreFormKeys.contains(formInfo.getFormKey())) continue;
                        formList.add(formInfo.getFormKey());
                    }
                    continue;
                }
                if (unitInfo.getFormInfos().size() == 0) {
                    this.resultService.addUnitErrorInfo(context.getWorkResult(), unitInfo.getMessage(), unitCode, "");
                    continue;
                }
                for (MistoreWorkFormInfo formInfo : unitInfo.getFormInfos().values()) {
                    form = this.runTimeViewController.queryFormById(formInfo.getFormKey());
                    if (formInfo.isSuccess()) {
                        if (formList.contains(formInfo.getFormKey()) || paramFormCodes != null && !paramFormCodes.contains(form.getFormCode()) || !midstoreFormKeys.isEmpty() && !midstoreFormKeys.contains(formInfo.getFormKey())) continue;
                        formList.add(formInfo.getFormKey());
                        continue;
                    }
                    if (paramFormCodes != null && !paramFormCodes.contains(form.getFormCode()) || !midstoreFormKeys.isEmpty() && !midstoreFormKeys.contains(formInfo.getFormKey()) || form.getFormType() == FormType.FORM_TYPE_NEWFMDM || "\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6".equalsIgnoreCase(formInfo.getMessage())) continue;
                    this.resultService.addFormErrorInfo((MidstoreContext)context, context.getWorkResult(), "\u5176\u4ed6", formInfo.getMessage(), unitCode, "", form.getFormCode(), form.getTitle());
                }
            }
        }
        return unitFormKeys;
    }

    @Override
    public void checkAndSetContextEntity(ReportMidstoreContext reportContext) {
        String midostreEntityId;
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)DsContextHolder.getDsContext().getContextEntityId()) && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)(midostreEntityId = EntityUtils.getEntityId((String)reportContext.getExeContext().getMidstoreContext().getSchemeInfo().getOrgDataName(), (String)"ORG"))) && !midostreEntityId.equalsIgnoreCase(reportContext.getTaskDefine().getDw())) {
            DsContextImpl newContext = (DsContextImpl)DsContextHolder.createEmptyContext();
            newContext.setEntityId(midostreEntityId);
            DsContextHolder.setDsContext((DsContext)newContext);
            reportContext.info(logger, "\u4e2d\u95f4\u5e93\u8bbe\u7f6e\u6267\u884c\u53e3\u5f84\uff1a" + midostreEntityId);
        }
    }
}

