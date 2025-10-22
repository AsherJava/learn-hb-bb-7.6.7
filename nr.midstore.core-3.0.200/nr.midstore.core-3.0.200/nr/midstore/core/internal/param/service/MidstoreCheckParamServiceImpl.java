/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.mapping.bean.MappingScheme
 *  com.jiuqi.nr.mapping.service.MappingSchemeService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package nr.midstore.core.internal.param.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.MistoreWorkFormInfo;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.common.ExcutePeriodType;
import nr.midstore.core.definition.common.FormAccessType;
import nr.midstore.core.definition.common.PublishStateType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataDTO;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreBaseDataService;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.publish.service.MidstorePublishTaskServiceImpl;
import nr.midstore.core.param.service.IMidstoreCheckParamService;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.util.IMidstoreResultService;
import nr.midstore.core.util.auth.IMidstoreFormDataAccess;
import nr.midstore.core.work.service.org.IMidstoreOrgDataWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreCheckParamServiceImpl
implements IMidstoreCheckParamService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishTaskServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IMidstoreBaseDataService baseDataService;
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private MappingSchemeService mappingService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IMidstoreDimensionService dimensionService;
    @Autowired
    private IMidstoreOrgDataWorkService orgDataWorkService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired(required=false)
    private IMidstoreFormDataAccess formAccessService;
    @Autowired
    private IMidstoreDimensionService midstoreDimService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;

    @Override
    public MidstoreResultObject doCheckParams(String midstoreSchemeKey, AsyncTaskMonitor monitor) {
        MidstoreResultObject reulst = new MidstoreResultObject();
        reulst.setSuccess(false);
        MidstoreContext context = new MidstoreContext(midstoreSchemeKey);
        context.setAsyncMonitor(monitor);
        if (!this.checkSchemeExist(context, reulst)) {
            return reulst;
        }
        if (!this.checkOrgData(context, reulst)) {
            return reulst;
        }
        reulst.setSuccess(true);
        return reulst;
    }

    @Override
    public MidstoreResultObject doCheckParamsBeforePulish(MidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkSchemeExist(context, result)) {
            return result;
        }
        if (!this.checkLinkTask(context, result)) {
            return result;
        }
        if (!this.checkOrgData(context, result)) {
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
    public MidstoreResultObject doCheckParamsBeforeCleanData(MidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkSchemeExist(context, result)) {
            return result;
        }
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
    public MidstoreResultObject doCheckParamsBeforeGetData(MidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkSchemeExist(context, result)) {
            return result;
        }
        if (!this.checkLinkTask(context, result)) {
            return result;
        }
        if (!this.checkOrgData(context, result)) {
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
    public MidstoreResultObject doCheckParamsBeforePostData(MidstoreContext context) {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        if (!this.checkSchemeExist(context, result)) {
            return result;
        }
        if (!this.checkLinkTask(context, result)) {
            return result;
        }
        if (!this.checkOrgData(context, result)) {
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

    private boolean checkSchemeExist(MidstoreContext context, MidstoreResultObject result) {
        MappingScheme mappingScheme;
        MidstoreSchemeDTO midScheme = context.getMidstoreScheme();
        if (midScheme == null) {
            midScheme = this.midstoreSchemeSevice.getByKey(context.getSchemeKey());
            context.setMidstoreScheme(midScheme);
        }
        if (midScheme == null) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff01");
            result.setSuccess(false);
            return false;
        }
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        if (schemeInfo == null) {
            this.schemeInfoSevice.getBySchemeKey(context.getSchemeKey());
            context.setSchemeInfo(schemeInfo);
        }
        if (schemeInfo == null) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f\u4e0d\u5b58\u5728\uff01");
            result.setSuccess(false);
            return false;
        }
        context.setConfigKey(midScheme.getConfigKey());
        if (StringUtils.isNotEmpty((String)midScheme.getConfigKey()) && (mappingScheme = this.mappingService.getByKey(midScheme.getConfigKey())) == null) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u6240\u5173\u8054\u7684\u6620\u5c04\u65b9\u6848\u4e0d\u5b58\u5728\uff01");
            result.setSuccess(false);
            return false;
        }
        return true;
    }

    private boolean checkOrgData(MidstoreContext context, MidstoreResultObject result) {
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        if (!schemeInfo.isAllOrgData()) {
            MidstoreOrgDataDTO param = new MidstoreOrgDataDTO();
            param.setSchemeKey(context.getSchemeKey());
            List<MidstoreOrgDataDTO> list = this.orgDataService.list(param);
            if (list == null || list.size() == 0) {
                result.setMessage("\u4ea4\u6362\u65b9\u6848\u7684\u7ec4\u7ec7\u673a\u6784\u672a\u5b9a\u4e49\uff01");
                result.setSuccess(false);
                return false;
            }
        }
        return true;
    }

    private boolean checkBaseDataAndFields(MidstoreContext context, MidstoreResultObject result) {
        MidstoreFieldDTO fieldParam = new MidstoreFieldDTO();
        fieldParam.setSchemeKey(context.getSchemeKey());
        List<MidstoreFieldDTO> fields = this.fieldService.list(fieldParam);
        if (fields == null || fields.size() == 0) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u7684\u6307\u6807\u672a\u5b9a\u4e49\uff01");
            result.setSuccess(false);
            return false;
        }
        ArrayList<String> baseEntityIds = new ArrayList<String>();
        HashSet<String> baseDataCodes = new HashSet<String>();
        for (MidstoreFieldDTO field : fields) {
            if (StringUtils.isNotEmpty((String)field.getSrcFieldKey())) {
                DataField dataField = this.dataSchemeSevice.getDataField(field.getSrcFieldKey());
                if (dataField == null) {
                    logger.info("\u6307\u6807\u4e0d\u5b58\u5728\uff1a" + field.getCode() + "," + field.getSrcFieldKey());
                    continue;
                }
                if (!StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey())) continue;
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
            List<MidstoreBaseDataDTO> list = this.baseDataService.list(param);
            if (list == null || list.size() == 0 || baseDataCodes.size() > list.size()) {
                // empty if block
            }
        }
        return true;
    }

    private boolean checkPublishState(MidstoreContext context, MidstoreResultObject result) {
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        if (schemeInfo.getPublishState() != PublishStateType.PUBLISHSTATE_SUCCESS) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u672a\u53d1\u5e03\u6210\u529f\uff01");
            result.setSuccess(false);
            return false;
        }
        return true;
    }

    private boolean checkLinkTask(MidstoreContext context, MidstoreResultObject result) {
        MidstoreSchemeDTO scheme = context.getMidstoreScheme();
        if (StringUtils.isEmpty((String)scheme.getTaskKey())) {
            result.setMessage("\u672a\u8bbe\u7f6e\u4efb\u52a1\uff01");
            result.setSuccess(false);
            return false;
        }
        TaskDefine task = this.runTimeViewController.queryTaskDefine(scheme.getTaskKey());
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
        if (StringUtils.isNotEmpty((String)task.getDw())) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
            context.setEntityTypeName(entityDefine.getDimensionName());
        }
        return true;
    }

    private boolean checkExcutePeriod(MidstoreContext context, MidstoreResultObject result) {
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
        } else if (schemeInfo.getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_APPOINT && StringUtils.isEmpty((String)schemeInfo.getExcutePeriod())) {
            result.setMessage("\u672a\u6307\u5b9a\u6267\u884c\u8ba1\u5212\u4efb\u52a1\u7684\u65f6\u671f\uff01");
            result.setSuccess(false);
            return false;
        }
        return true;
    }

    private boolean checkMapingInfo(MidstoreContext context, MidstoreResultObject result) {
        return this.midstoreMappingService.checkMaping(context, result);
    }

    private boolean checkRepeatUnitCodes(MidstoreContext context, MidstoreResultObject result) {
        String excutePeriod = this.midstoreDimService.getExcutePeriod(context);
        GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)PeriodUtil.getPeriodWrapper((String)excutePeriod));
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(context.getTaskKey());
        String orgCode = EntityUtils.getId((String)taskDefine.getDw());
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCategoryname(orgCode);
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgParam.setAuthType(OrgDataOption.AuthType.NONE);
        PageVO queryRes = this.orgDataClient.list(orgParam);
        HashMap<String, OrgDO> dataMap = new HashMap<String, OrgDO>();
        if (queryRes != null && queryRes.getRows() != null) {
            for (OrgDO data : queryRes.getRows()) {
                dataMap.put(data.getCode(), data);
            }
        }
        this.midstoreMappingService.initOrgMapping(context);
        MidstoreOrgDataDTO queryParam = new MidstoreOrgDataDTO();
        queryParam.setSchemeKey(context.getSchemeKey());
        List<MidstoreOrgDataDTO> orgDatas = this.orgDataService.list(queryParam);
        HashSet<String> repeatUnitCodes = new HashSet<String>();
        if (context.getSchemeInfo().isAllOrgData()) {
            for (String aCode : dataMap.keySet()) {
                OrgDO ordData = (OrgDO)dataMap.get(aCode);
                String unitCode = aCode;
                context.getExchangeEnityCodes().add(unitCode);
                String parantCode = ordData.getParentcode();
                if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                    unitCode = context.getMappingCache().getUnitMappingInfos().get(unitCode).getUnitMapingCode();
                }
                if (context.getMappingCache().getUnitMappingInfos().containsKey(parantCode)) {
                    parantCode = context.getMappingCache().getUnitMappingInfos().get(parantCode).getUnitMapingCode();
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
                    if (StringUtils.isEmpty((String)unitTitle)) {
                        unitTitle = oldOrdData.getName();
                    }
                    if (StringUtils.isEmpty((String)parantCode)) {
                        parantCode = oldOrdData.getParentcode();
                    }
                } else {
                    result.setMessage("\u5355\u4f4d\u4e0d\u5b58\u5728\uff1a" + unitCode);
                    result.setSuccess(false);
                    return false;
                }
                context.getExchangeEnityCodes().add(unitCode);
                if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                    unitCode = context.getMappingCache().getUnitMappingInfos().get(unitCode).getUnitMapingCode();
                }
                if (context.getMappingCache().getUnitMappingInfos().containsKey(parantCode)) {
                    parantCode = context.getMappingCache().getUnitMappingInfos().get(parantCode).getUnitMapingCode();
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
    public MidstoreResultObject doLoadFormScheme(MidstoreContext context, boolean isLoadFormDefine) throws MidstoreException {
        Map<String, DimensionValue> dims = this.dimensionService.getDimSetMap(context);
        String periodCode = this.dimensionService.getExcutePeriod(context);
        String formSchemeKey = null;
        try {
            SchemePeriodLinkDefine periodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodCode, context.getTaskDefine().getKey());
            if (periodLinkDefine != null) {
                formSchemeKey = periodLinkDefine.getSchemeKey();
                context.setFormSchemeKey(formSchemeKey);
            }
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            throw new MidstoreException(e1);
        }
        if (StringUtils.isNotEmpty((String)formSchemeKey) && isLoadFormDefine) {
            context.getExcuteParams().put("FORMSCHEME", formSchemeKey);
            HashMap tableFieldList = new HashMap();
            HashMap<String, String> tableKeyCodes = new HashMap<String, String>();
            HashMap<String, String> tableFormKeyList = new HashMap<String, String>();
            HashMap<String, Object> formKeyTableList = new HashMap<String, Object>();
            ArrayList<String> formKeys = new ArrayList<String>();
            HashMap<String, String> tableRegionKeyList = new HashMap<String, String>();
            List formList = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
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
                        formKeys.add(formKey);
                    }
                    Object formTableCodes = null;
                    if (formKeyTableList.containsKey(formKey)) {
                        formTableCodes = (Set)formKeyTableList.get(formKey);
                        if (!formTableCodes.contains(tableCode)) {
                            formTableCodes.add(tableCode);
                        }
                    } else {
                        formTableCodes = new HashSet();
                        formTableCodes.add(tableCode);
                        formKeyTableList.put(formKey, formTableCodes);
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
            if (StringUtils.isNotEmpty((String)formSchemeKey) && dims.containsKey("MD_CURRENCY")) {
                HashMap unitDimsList = new HashMap();
                HashMap<String, Set<String>> dimNameValueList = new HashMap<String, Set<String>>();
                DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection(dims, (String)formSchemeKey);
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
            context.getExcuteParams().put("TABLEFIELDLIST", tableFieldList);
            context.getExcuteParams().put("TABLEFORMLIST", tableFormKeyList);
            context.getExcuteParams().put("TABLEREGIONLIST", tableRegionKeyList);
            context.getExcuteParams().put("FORMTABLESLIST", formKeyTableList);
        }
        return new MidstoreResultObject(true, "");
    }

    @Override
    public MidstoreContext getContext(String midstoreSchemeId, AsyncTaskMonitor monitor) {
        MidstoreContext context = new MidstoreContext();
        context.setSchemeKey(midstoreSchemeId);
        context.setAsyncMonitor(monitor);
        context.setMidstoreScheme(this.midstoreSchemeSevice.getByKey(midstoreSchemeId));
        context.setSchemeInfo(this.schemeInfoSevice.getBySchemeKey(midstoreSchemeId));
        return context;
    }

    @Override
    public void tranUnitFormsToTables(MidstoreContext context, Map<DimensionValueSet, List<String>> unitFormKeys) {
        HashMap formUnitDims = new HashMap();
        HashMap unitDimsList = new HashMap();
        HashMap<String, Set<String>> dimNameValueList = new HashMap<String, Set<String>>();
        HashMap formUnitList = new HashMap();
        String formSchemeKey = null;
        String importDataTime = null;
        context.getExchangeEnityCodes().clear();
        for (DimensionValueSet unitDim : unitFormKeys.keySet()) {
            String unitCode = (String)unitDim.getValue(context.getEntityTypeName());
            List<String> formKeys = unitFormKeys.get(unitDim);
            for (String formKey : formKeys) {
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
            if (context.getExchangeEnityCodes().contains(unitCode)) continue;
            context.getExchangeEnityCodes().add(unitCode);
        }
        context.getExcuteParams().put("FORMUNITDIM", formUnitDims);
        context.getExcuteParams().put("FORMUNITLIST", formUnitList);
        context.getExcuteParams().put("UNITDIMSLIST", unitDimsList);
        context.getExcuteParams().put("DIMNAMEVALUELIST", dimNameValueList);
        if (StringUtils.isEmpty(importDataTime) && unitFormKeys.size() > 0) {
            DimensionValueSet unitDim = (DimensionValueSet)unitFormKeys.keySet().toArray()[0];
            importDataTime = (String)unitDim.getValue(context.getDateTypeName());
            context.getExcuteParams().put("DataTime", importDataTime);
            HashMap<String, DimensionValue> otherDimSetMap = new HashMap<String, DimensionValue>();
            for (int i = 0; i < unitDim.size(); ++i) {
                String dimCode = unitDim.getName(i);
                String dimValue = (String)unitDim.getValue(i);
                if (context.getEntityTypeName().equalsIgnoreCase(dimCode) || context.getDateTypeName().equalsIgnoreCase(dimCode)) continue;
                DimensionValue dim = new DimensionValue();
                dim.setType(0);
                dim.setName(dimCode);
                dim.setValue(dimValue);
                otherDimSetMap.put(context.getDateTypeName(), dim);
            }
            context.getExcuteParams().put("OTHERDIM", otherDimSetMap);
        }
        context.getExcuteParams().put("DATATIME", importDataTime);
        if (StringUtils.isEmpty(formSchemeKey) && formUnitDims.size() > 0) {
            String curformKey = (String)formUnitDims.keySet().toArray()[0];
            FormDefine formDefine = this.runTimeViewController.queryFormById(curformKey);
            formSchemeKey = formDefine.getFormScheme();
            context.setFormSchemeKey(formSchemeKey);
            context.getExcuteParams().put("FORMSCHEME", formSchemeKey);
            HashMap tableFieldList = new HashMap();
            HashMap<String, String> tableKeyCodes = new HashMap<String, String>();
            HashMap tableUnitList = new HashMap();
            HashMap<String, String> tableFormKeyList = new HashMap<String, String>();
            HashMap<String, Set<String>> formKeyTablesList = new HashMap<String, Set<String>>();
            ArrayList<String> formKeys = new ArrayList<String>();
            HashMap<String, String> tableRegionKeyList = new HashMap<String, String>();
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
                        if (tableUnitList.containsKey(tableCode)) {
                            tableUnits = (HashSet)tableUnitList.get(tableCode);
                        } else {
                            List formUnits = (List)formUnitList.get(formKey);
                            tableUnits = new HashSet();
                            tableUnits.addAll(formUnits);
                            tableUnitList.put(tableCode, tableUnits);
                        }
                        tableFormKeyList.put(tableCode, formKey);
                        formKeys.add(formKey);
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
            context.getExcuteParams().put("TABLEFIELDLIST", tableFieldList);
            context.getExcuteParams().put("TABLEUNITLIST", tableUnitList);
            context.getExcuteParams().put("TABLEFORMLIST", tableFormKeyList);
            context.getExcuteParams().put("TABLEREGIONLIST", tableRegionKeyList);
            context.getExcuteParams().put("FORMTABLESLIST", formKeyTablesList);
        }
    }

    @Override
    public Map<DimensionValueSet, List<String>> getUnitFormKeys(MidstoreContext context, FormAccessType formAccess) throws MidstoreException {
        String paramformCode;
        Map<String, DimensionValue> dims = this.dimensionService.getDimSetMap(context);
        String periodCode = this.dimensionService.getExcutePeriod(context);
        String formSchemeKey = context.getFormSchemeKey();
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            try {
                SchemePeriodLinkDefine periodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodCode, context.getTaskDefine().getKey());
                if (periodLinkDefine == null) {
                    throw new MidstoreException("\u8be5\u65f6\u671f\u65e0\u5173\u8054\u62a5\u8868\u65b9\u6848\uff0c" + periodCode);
                }
                formSchemeKey = periodLinkDefine.getSchemeKey();
                context.setFormSchemeKey(formSchemeKey);
            }
            catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
                throw new MidstoreException(e1);
            }
        }
        HashMap<String, DimensionValue> otherDims = new HashMap<String, DimensionValue>();
        for (String dimCode : dims.keySet()) {
            DimensionValue dim = dims.get(dimCode);
            if (StringUtils.isEmpty((String)dim.getName()) || dim.getName().equalsIgnoreCase(context.getEntityTypeName()) || dim.getName().equalsIgnoreCase(context.getDateTypeName())) continue;
            otherDims.put(dimCode, dim);
        }
        ArrayList<String> queryUnitCodes = new ArrayList<String>();
        if (FormAccessType.FORMACCESS_WRITE == formAccess) {
            List<MidstoreOrgDataDTO> orgDataList = this.orgDataWorkService.getOrgDataFromMidstore(context.getSchemeKey(), context.getAsyncMonitor());
            ArrayList<String> unitCodes = new ArrayList<String>();
            for (MidstoreOrgDataDTO midstoreOrgDataDTO : orgDataList) {
                unitCodes.add(midstoreOrgDataDTO.getCode());
            }
            logger.info("\u4e2d\u95f4\u5e93\u91cc\u7684\u5355\u4f4d\u6570\uff1a" + unitCodes.size());
            if (context.getSchemeInfo().isAllOrgData()) {
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u5141\u8bb8\u4ea4\u6362\u6240\u6709\u5355\u4f4d");
                queryUnitCodes.addAll((Collection<String>)unitCodes);
            } else {
                List<String> list = this.orgDataWorkService.getUnitCodesByMidstoreScheme(context);
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u7684\u5355\u4f4d\u6570\uff1a" + list.size());
                Iterator iterator = unitCodes.iterator();
                while (iterator.hasNext()) {
                    String unitCode = (String)iterator.next();
                    if (!list.contains(unitCode)) continue;
                    queryUnitCodes.add(unitCode);
                }
                logger.info("\u9700\u8981\u67e5\u8be2\u6743\u9650\u7684\u5355\u4f4d\u6570\uff1a" + queryUnitCodes.size());
            }
        } else if (FormAccessType.FORMACCESS_READ == formAccess) {
            List<Object> unitCodes = new ArrayList();
            if (context.getSchemeInfo().isAllOrgData()) {
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u5141\u8bb8\u4ea4\u6362\u6240\u6709\u5355\u4f4d");
                unitCodes = this.orgDataWorkService.getUnitCodesByOrgData(context);
                queryUnitCodes.addAll(unitCodes);
            } else {
                unitCodes = this.orgDataWorkService.getUnitCodesByMidstoreScheme(context);
                logger.info("\u4ea4\u6362\u65b9\u6848\u91cc\u7684\u5355\u4f4d\u6570\uff1a" + unitCodes.size());
            }
            for (String string : unitCodes) {
                if (!unitCodes.contains(string)) continue;
                queryUnitCodes.add(string);
            }
            logger.info("\u9700\u8981\u67e5\u8be2\u6743\u9650\u7684\u5355\u4f4d\u6570\uff1a" + queryUnitCodes.size());
        }
        HashSet<String> paramFormCodes = null;
        if (context.getExcuteParams().containsKey("FormCode") && StringUtils.isNotEmpty((String)(paramformCode = (String)context.getExcuteParams().get("FormCode")))) {
            String[] stringArray;
            paramFormCodes = new HashSet<String>();
            for (String formCode : stringArray = paramformCode.split(",")) {
                paramFormCodes.add(formCode);
            }
        }
        Map<DimensionValueSet, MistoreWorkUnitInfo> unitFormResons = this.formAccessService.getFormDataAccessByReason(formSchemeKey, queryUnitCodes, periodCode, otherDims, formAccess);
        HashSet<String> hashSet = new HashSet<String>();
        MidstoreFieldDTO midstoreFieldDTO = new MidstoreFieldDTO();
        midstoreFieldDTO.setSchemeKey(context.getSchemeKey());
        List<MidstoreFieldDTO> midstoreFields = this.fieldService.list(midstoreFieldDTO);
        HashMap<String, MidstoreFieldDTO> midStoreFieldsMap = new HashMap<String, MidstoreFieldDTO>();
        if (midstoreFields != null && !midstoreFields.isEmpty()) {
            for (MidstoreFieldDTO field : midstoreFields) {
                midStoreFieldsMap.put(field.getSrcFieldKey(), field);
            }
            List nrForms = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
            block8: for (FormDefine nrForm : nrForms) {
                List fieldKeys = this.runTimeViewController.getFieldKeysInForm(nrForm.getKey());
                if (fieldKeys == null || fieldKeys.isEmpty()) continue;
                List dataFields = this.dataSchemeSevice.getDataFields(fieldKeys);
                HashMap fieldKeyTables = new HashMap();
                for (DataField field : dataFields) {
                    if (!midStoreFieldsMap.containsKey(field.getKey())) continue;
                    hashSet.add(nrForm.getKey());
                    continue block8;
                }
            }
        }
        HashMap<DimensionValueSet, List<String>> unitFormKeys = new HashMap<DimensionValueSet, List<String>>();
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
                        if (paramFormCodes != null && !paramFormCodes.contains(form.getFormCode()) || !hashSet.isEmpty() && !hashSet.contains(formInfo.getFormKey())) continue;
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
                        if (formList.contains(formInfo.getFormKey()) || paramFormCodes != null && !paramFormCodes.contains(form.getFormCode()) || !hashSet.isEmpty() && !hashSet.contains(formInfo.getFormKey())) continue;
                        formList.add(formInfo.getFormKey());
                        continue;
                    }
                    if (paramFormCodes != null && !paramFormCodes.contains(form.getFormCode()) || !hashSet.isEmpty() && !hashSet.contains(formInfo.getFormKey()) || form.getFormType() == FormType.FORM_TYPE_NEWFMDM || "\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6".equalsIgnoreCase(formInfo.getMessage())) continue;
                    this.resultService.addFormErrorInfo(context, context.getWorkResult(), "\u5176\u4ed6", formInfo.getMessage(), unitCode, "", form.getFormCode(), form.getTitle());
                }
            }
        }
        return unitFormKeys;
    }
}

