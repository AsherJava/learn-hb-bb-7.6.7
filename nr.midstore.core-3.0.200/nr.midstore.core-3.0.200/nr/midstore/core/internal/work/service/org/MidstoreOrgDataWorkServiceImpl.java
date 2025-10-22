/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEOrgInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package nr.midstore.core.internal.work.service.org;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEOrgInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreOrgDataFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.internal.publish.service.MidstorePublishOrgDataServiceImpl;
import nr.midstore.core.param.service.IMidstoreCheckParamService;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.util.IMidstoreReadWriteService;
import nr.midstore.core.util.IMidstoreResultService;
import nr.midstore.core.work.service.org.IMidstoreOrgDataWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreOrgDataWorkServiceImpl
implements IMidstoreOrgDataWorkService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishOrgDataServiceImpl.class);
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreOrgDataFieldService orgDataFieldService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IMidstoreDimensionService midstoreDimService;
    @Autowired
    private IMidstoreReadWriteService readWriteService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired
    private IMidstoreCheckParamService checkParamService;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;

    @Override
    public void saveOrgDatas(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        String excutePeriod = this.midstoreDimService.getExcutePeriod(context);
        MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoSevice.getBySchemeKey(midstoreScheme.getKey());
        GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)PeriodUtil.getPeriodWrapper((String)excutePeriod));
        Set<String> canReadUnits = null;
        if (StringUtils.isNotEmpty((String)context.getExcuteUserName()) && !this.readWriteService.isAdmin()) {
            canReadUnits = this.readWriteService.getCanReadUnitList(context, excutePeriod);
        }
        context.getExchangeEnityCodes().clear();
        TaskDefine taskDefine = this.viewController.queryTaskDefine(midstoreScheme.getTaskKey());
        String orgCode = EntityUtils.getId((String)taskDefine.getDw());
        OrgCategoryDO orgDefine = this.queryOrgDatadefine(orgCode);
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCategoryname(orgCode);
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO queryRes = this.orgDataClient.list(orgParam);
        HashMap<String, OrgDO> dataMap = new HashMap<String, OrgDO>();
        if (queryRes != null && queryRes.getRows() != null) {
            for (OrgDO data : queryRes.getRows()) {
                dataMap.put(data.getCode(), data);
            }
        }
        this.initOrgMapping(context);
        Map<Object, Object> oldDeOrgMap = new HashMap();
        try {
            List oldDeOrgs = dataExchangeTask.getOrgs();
            if (oldDeOrgs != null) {
                oldDeOrgMap = oldDeOrgs.stream().collect(Collectors.toMap(DEOrgInfo::getName, DEOrgInfo2 -> DEOrgInfo2, (v1, v2) -> v2));
            }
        }
        catch (DataExchangeException e1) {
            logger.error(e1.getMessage(), e1);
            throw new MidstoreException(e1.getMessage(), e1);
        }
        MidstoreOrgDataDTO queryParam = new MidstoreOrgDataDTO();
        queryParam.setSchemeKey(midstoreScheme.getKey());
        List<MidstoreOrgDataDTO> orgDatas = this.orgDataService.list(queryParam);
        HashSet<String> repeatUnitCodes = new HashSet<String>();
        ArrayList<DEOrgInfo> orgs = new ArrayList<DEOrgInfo>();
        if (schemeInfo.isAllOrgData()) {
            for (String aCode : dataMap.keySet()) {
                DEOrgInfo org;
                OrgDO ordData = (OrgDO)dataMap.get(aCode);
                String unitCode = aCode;
                if (!this.readWriteService.isAdmin() && canReadUnits != null && canReadUnits.size() > 0 && !canReadUnits.contains(unitCode)) {
                    this.resultService.addUnitErrorInfo(context.getWorkResult(), "\u5355\u4f4d\u6ca1\u6709\u8bfb\u53d6\u6743\u9650", unitCode, "");
                    continue;
                }
                context.getExchangeEnityCodes().add(unitCode);
                String parantCode = ordData.getParentcode();
                if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                    unitCode = context.getMappingCache().getUnitMappingInfos().get(unitCode).getUnitMapingCode();
                }
                if (context.getMappingCache().getUnitMappingInfos().containsKey(parantCode)) {
                    parantCode = context.getMappingCache().getUnitMappingInfos().get(parantCode).getUnitMapingCode();
                }
                if (repeatUnitCodes.contains(unitCode)) {
                    logger.info("\u5b58\u5728\u91cd\u7801\u5355\u4f4d," + unitCode);
                    continue;
                }
                repeatUnitCodes.add(unitCode);
                String unitTitle = ordData.getName();
                String unitId = UUID.randomUUID().toString();
                if (oldDeOrgMap.containsKey(unitCode)) {
                    unitId = ((DEOrgInfo)oldDeOrgMap.get(unitCode)).getId();
                    org = new DEOrgInfo(unitId, unitCode, unitTitle, parantCode);
                    orgs.add(org);
                    continue;
                }
                org = new DEOrgInfo(unitId, unitCode, unitTitle, parantCode);
                orgs.add(org);
            }
        } else {
            for (MidstoreOrgDataDTO ordData : orgDatas) {
                DEOrgInfo org;
                String unitCode = ordData.getCode();
                if (!this.readWriteService.isAdmin() && canReadUnits != null && canReadUnits.size() > 0 && !canReadUnits.contains(unitCode)) {
                    this.resultService.addUnitErrorInfo(context.getWorkResult(), "\u5355\u4f4d\u6ca1\u6709\u8bfb\u53d6\u6743\u9650", unitCode, "");
                    continue;
                }
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
                    logger.info("\u5355\u4f4d\u4e0d \u5b58\u5728\uff1a" + unitCode);
                    continue;
                }
                context.getExchangeEnityCodes().add(unitCode);
                if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                    unitCode = context.getMappingCache().getUnitMappingInfos().get(unitCode).getUnitMapingCode();
                }
                if (context.getMappingCache().getUnitMappingInfos().containsKey(parantCode)) {
                    parantCode = context.getMappingCache().getUnitMappingInfos().get(parantCode).getUnitMapingCode();
                }
                if (repeatUnitCodes.contains(unitCode)) {
                    logger.info("\u5b58\u5728\u91cd\u7801\u5355\u4f4d," + unitCode);
                    continue;
                }
                repeatUnitCodes.add(unitCode);
                String unitId = UUID.randomUUID().toString();
                if (oldDeOrgMap.containsKey(unitCode)) {
                    unitId = ((DEOrgInfo)oldDeOrgMap.get(unitCode)).getId();
                    org = new DEOrgInfo(unitId, unitCode, unitTitle, parantCode);
                    orgs.add(org);
                    continue;
                }
                org = new DEOrgInfo(unitId, unitCode, unitTitle, parantCode);
                orgs.add(org);
            }
        }
        try {
            dataExchangeTask.deleteData("ORG", "1=1");
            dataExchangeTask.setOrgs(orgs);
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
        if (midstoreScheme.getExchangeMode() == ExchangeModeType.EXCHANGE_POST || schemeInfo.isUseUpdateOrg()) {
            this.saveOtherOrgData(context, dataExchangeTask, orgDefine, orgDatas, dataMap, monitor);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveOtherOrgData(MidstoreContext context, IDataExchangeTask dataExchangeTask, OrgCategoryDO orgDefine, List<MidstoreOrgDataDTO> orgDatas, Map<String, OrgDO> dataMap, AsyncTaskMonitor monitor) throws MidstoreException {
        block25: {
            MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
            String excutePeriod = this.midstoreDimService.getExcutePeriod(context);
            MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoSevice.getBySchemeKey(midstoreScheme.getKey());
            MidstoreOrgDataFieldDTO queryFieldParam = new MidstoreOrgDataFieldDTO();
            queryFieldParam.setSchemeKey(midstoreScheme.getKey());
            List<MidstoreOrgDataFieldDTO> orgFields = this.orgDataFieldService.list(queryFieldParam);
            List orgZbList = orgDefine.getZbs();
            HashMap<String, ZB> orgZbMap = new HashMap<String, ZB>();
            for (Object orgZb : orgZbList) {
                orgZbMap.put(orgZb.getName(), (ZB)orgZb);
            }
            ArrayList<String> fieldNames = new ArrayList<String>();
            fieldNames.add("MDCODE");
            fieldNames.add("ORGCODE");
            if (schemeInfo.isAllOrgField()) {
                fieldNames.add("SHORTNAME");
                for (ZB zB : orgZbList) {
                    fieldNames.add(zB.getName());
                }
            } else {
                for (MidstoreOrgDataFieldDTO midstoreOrgDataFieldDTO : orgFields) {
                    if (orgDefine.getZbByName(midstoreOrgDataFieldDTO.getCode()) == null && !"SHORTNAME".equalsIgnoreCase(midstoreOrgDataFieldDTO.getCode()) || fieldNames.contains(midstoreOrgDataFieldDTO.getCode())) continue;
                    fieldNames.add(midstoreOrgDataFieldDTO.getCode());
                }
            }
            try {
                dataExchangeTask.deleteData("ORG_OTHERDATA", "1=1");
                try (IDataWriter tableWriter = dataExchangeTask.createTableWriter("ORG_OTHERDATA", fieldNames);){
                    if (schemeInfo.isAllOrgData()) {
                        for (String aCode : dataMap.keySet()) {
                            Object obj;
                            OrgDO orgData = dataMap.get(aCode);
                            String unitCode = aCode;
                            if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                                unitCode = context.getMappingCache().getUnitMappingInfos().get(unitCode).getUnitMapingCode();
                            }
                            ArrayList<Object> values = new ArrayList<Object>();
                            values.add(unitCode);
                            values.add(orgData.get((Object)"orgcode"));
                            if (schemeInfo.isAllOrgField()) {
                                values.add(orgData.get((Object)"SHORTNAME".toLowerCase()));
                                for (ZB orgZb : orgZbList) {
                                    obj = orgData.get((Object)orgZb.getName().toLowerCase());
                                    this.setOrgFieldValue(orgZb.getName(), orgZb, obj, values);
                                }
                            } else {
                                for (MidstoreOrgDataFieldDTO field : orgFields) {
                                    obj = orgData.get((Object)field.getCode());
                                    ZB orgZb = (ZB)orgZbMap.get(field.getCode());
                                    this.setOrgFieldValue(field.getCode(), orgZb, obj, values);
                                }
                            }
                            tableWriter.insert(values.toArray());
                        }
                        break block25;
                    }
                    for (MidstoreOrgDataDTO orgData : orgDatas) {
                        String unitCode = orgData.getCode();
                        String unitOrgCode = orgData.getOrgCode();
                        OrgDO oldOrdData = dataMap.get(unitCode);
                        if (oldOrdData != null && StringUtils.isEmpty((String)unitOrgCode)) {
                            unitOrgCode = oldOrdData.getOrgcode();
                        }
                        if (context.getMappingCache().getUnitMappingInfos().containsKey(unitCode)) {
                            unitCode = context.getMappingCache().getUnitMappingInfos().get(unitCode).getUnitMapingCode();
                        }
                        ArrayList<Object> values = new ArrayList<Object>();
                        values.add(unitCode);
                        values.add(unitOrgCode);
                        if (dataMap.containsKey(orgData.getCode())) {
                            Object obj;
                            OrgDO data = dataMap.get(orgData.getCode());
                            if (schemeInfo.isAllOrgField()) {
                                values.add(data.get((Object)"SHORTNAME".toLowerCase()));
                                for (ZB orgZb : orgZbList) {
                                    obj = data.get((Object)orgZb.getName().toLowerCase());
                                    this.setOrgFieldValue(orgZb.getName(), orgZb, obj, values);
                                }
                            } else {
                                for (String fieldName : fieldNames) {
                                    if ("MDCODE".equalsIgnoreCase(fieldName) || "ORGCODE".equalsIgnoreCase(fieldName)) continue;
                                    obj = data.get((Object)fieldName.toLowerCase());
                                    ZB orgZb = (ZB)orgZbMap.get(fieldName);
                                    this.setOrgFieldValue(fieldName, orgZb, obj, values);
                                }
                            }
                        }
                        tableWriter.insert(values.toArray());
                    }
                }
            }
            catch (DataExchangeException dataExchangeException) {
                logger.error(dataExchangeException.getMessage(), dataExchangeException);
                throw new MidstoreException(dataExchangeException.getMessage(), dataExchangeException);
            }
        }
    }

    private void setOrgFieldValue(String zbCode, ZB orgZb, Object zbObj, List<Object> values) {
        if (zbObj instanceof List) {
            List list = (List)zbObj;
            StringBuilder sp = new StringBuilder();
            for (String code : list) {
                if (StringUtils.isEmpty((String)code)) continue;
                String code1 = code;
                sp.append(code1).append(";");
            }
            if (sp.length() > 0) {
                sp.delete(sp.length() - 1, sp.length());
            }
            values.add(sp.toString());
        } else if (zbObj instanceof Date) {
            values.add(zbObj);
        } else if (orgZb != null && orgZb.getDatatype() == 8) {
            if (zbObj instanceof Integer) {
                Boolean b = null;
                if ((Integer)zbObj == 1) {
                    b = true;
                } else if ((Integer)zbObj == 0) {
                    b = false;
                }
                values.add(b);
            } else if (zbObj instanceof BigDecimal) {
                Boolean b = null;
                if (((BigDecimal)zbObj).intValue() == 1) {
                    b = true;
                } else if (((BigDecimal)zbObj).intValue() == 0) {
                    b = false;
                }
                values.add(b);
            } else if (zbObj instanceof String) {
                Boolean b = null;
                if ("1".equalsIgnoreCase((String)zbObj)) {
                    b = true;
                } else if ("0".equalsIgnoreCase((String)zbObj)) {
                    b = false;
                }
                values.add(b);
            } else {
                values.add(zbObj);
            }
        } else if (zbObj instanceof String) {
            values.add(zbObj);
        } else {
            values.add(zbObj);
        }
    }

    private void initOrgMapping(MidstoreContext context) {
        this.midstoreMappingService.initOrgMapping(context);
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

    @Override
    public List<String> getUnitCodesByMidstoreScheme(MidstoreContext context) {
        MidstoreOrgDataDTO queryParam = new MidstoreOrgDataDTO();
        queryParam.setSchemeKey(context.getSchemeKey());
        List<MidstoreOrgDataDTO> list2 = this.orgDataService.list(queryParam);
        List<MidstoreOrgDataDTO> orgDatas = this.orgDataService.list(queryParam);
        ArrayList<String> orgs = new ArrayList<String>();
        if (!context.getSchemeInfo().isAllOrgData()) {
            for (MidstoreOrgDataDTO org : orgDatas) {
                orgs.add(org.getCode());
                String unitCode = org.getCode();
                String unitTitle = org.getTitle();
                MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo(unitCode, unitTitle);
                context.getUnitCache().put(unitCode, unitInfo);
            }
        }
        return orgs;
    }

    @Override
    public List<String> getUnitCodesByOrgData(MidstoreContext context) {
        ArrayList<String> orgs = new ArrayList<String>();
        String excutePeriod = this.midstoreDimService.getExcutePeriod(context);
        GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)PeriodUtil.getPeriodWrapper((String)excutePeriod));
        TaskDefine taskDefine = context.getTaskDefine();
        String orgCode = EntityUtils.getId((String)taskDefine.getDw());
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCategoryname(orgCode);
        orgParam.setStopflag(Integer.valueOf(-1));
        orgParam.setRecoveryflag(Integer.valueOf(-1));
        orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        PageVO queryRes = this.orgDataClient.list(orgParam);
        if (queryRes != null && queryRes.getRows() != null) {
            for (OrgDO data : queryRes.getRows()) {
                String unitCode = data.getCode();
                String unitTitle = data.getName();
                orgs.add(unitCode);
                MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo(unitCode, unitTitle);
                context.getUnitCache().put(unitCode, unitInfo);
            }
        }
        return orgs;
    }

    @Override
    public List<MidstoreOrgDataDTO> getOrgDataFromMidstore(String midstoreSchemeId, AsyncTaskMonitor monitor) throws MidstoreException {
        ArrayList<MidstoreOrgDataDTO> list = new ArrayList<MidstoreOrgDataDTO>();
        MidstoreContext context = this.checkParamService.getContext(midstoreSchemeId, monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforePostData(context);
        this.checkParamService.doLoadFormScheme(context, false);
        if (checkResult == null) {
            return list;
        }
        if (checkResult.isSuccess()) {
            MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
            TaskDefine taskDefine = context.getTaskDefine();
            if (taskDefine == null) {
                taskDefine = this.viewController.queryTaskDefine(midstoreScheme.getTaskKey());
                context.setTaskDefine(taskDefine);
            }
            IDataExchangeTask dataExchangeTask = this.exchangeTaskService.getExchangeTask(context);
            this.getOrgDataFromMidstoreTask(context, dataExchangeTask, list);
        }
        return list;
    }

    private void getOrgDataFromMidstoreTask(MidstoreContext context, IDataExchangeTask dataExchangeTask, List<MidstoreOrgDataDTO> list) {
        try {
            this.midstoreMappingService.initOrgMapping(context);
            List midOrgs = dataExchangeTask.getOrgs();
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
                MidstoreOrgDataDTO unitData = new MidstoreOrgDataDTO();
                unitData.setCode(code);
                unitData.setTitle(unitTitle);
                unitData.setParentCode(pCode);
                list.add(unitData);
                MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo(code, unitTitle);
                context.getUnitCache().put(code, unitInfo);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getUnitCodesFromMidstore(MidstoreContext context) {
        List<String> list = null;
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        TaskDefine taskDefine = context.getTaskDefine();
        if (taskDefine == null) {
            taskDefine = this.viewController.queryTaskDefine(midstoreScheme.getTaskKey());
            context.setTaskDefine(taskDefine);
        }
        try {
            IDataExchangeTask dataExchangeTask = this.exchangeTaskService.getExchangeTask(context);
            list = this.getUnitCodesFromMidstore(context, dataExchangeTask);
        }
        catch (MidstoreException midstoreException) {
            // empty catch block
        }
        return list;
    }

    @Override
    public List<String> getUnitCodesFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<MidstoreOrgDataDTO> list2 = new ArrayList<MidstoreOrgDataDTO>();
        this.getOrgDataFromMidstoreTask(context, dataExchangeTask, list2);
        for (MidstoreOrgDataDTO org : list2) {
            list.add(org.getCode());
            String unitCode = org.getCode();
            String unitTitle = org.getTitle();
            MistoreWorkUnitInfo unitInfo = new MistoreWorkUnitInfo(unitCode, unitTitle);
            context.getUnitCache().put(unitCode, unitInfo);
        }
        return list;
    }
}

