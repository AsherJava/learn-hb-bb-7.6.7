/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.node.WorkflowQueryRes
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BillBusinessClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO
 */
package com.jiuqi.va.workflow.utils;

import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.node.WorkflowQueryRes;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BillBusinessClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.domain.WorkflowOptionDTO;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public final class VaWorkFlowDataUtils {
    private static final Logger log = LoggerFactory.getLogger(VaWorkFlowDataUtils.class);
    public static final String META_TYPE = "metaType";

    private VaWorkFlowDataUtils() {
    }

    public static AuthUserClient getAuthUserClient() {
        return (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
    }

    public static MetaDataClient getMetaDataClient() {
        return (MetaDataClient)ApplicationContextRegister.getBean(MetaDataClient.class);
    }

    public static OrgDataClient getOrgDataClient() {
        return (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
    }

    public static BaseDataClient getBaseDataClient() {
        return (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
    }

    public static EnumDataClient getEnumDataClient() {
        return (EnumDataClient)ApplicationContextRegister.getBean(EnumDataClient.class);
    }

    public static UserDO getUserDoWithCache(String userId, Map<String, UserDO> userCacheMap) {
        if (userCacheMap.containsKey(userId)) {
            return userCacheMap.get(userId);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        UserDO userDO = VaWorkFlowDataUtils.getAuthUserClient().get(userDTO);
        if (Objects.nonNull(userDO)) {
            userCacheMap.put(userId, userDO);
            return userDO;
        }
        return new UserDO();
    }

    public static UserDO getUserDOWithOrgInfo(String userId, Map<String, UserDO> userCache) {
        if (userCache.containsKey(userId)) {
            return userCache.get(userId);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setStopflag(Integer.valueOf(0));
        UserDO userDO = VaWorkFlowDataUtils.getAuthUserClient().get(userDTO);
        if (userDO != null && userDO.getStopflag() != 1) {
            UserDO user = new UserDO();
            user.setId(userDO.getId());
            user.setName(userDO.getName());
            user.setUsername(userDO.getUsername());
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCategoryname("MD_ORG");
            orgDTO.setCode(userDO.getUnitcode());
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            PageVO orgDOPageVO = VaWorkFlowDataUtils.getOrgDataClient().list(orgDTO);
            user.setUnitcode(userDO.getUnitcode());
            String unitname = "";
            if (orgDOPageVO != null && orgDOPageVO.getTotal() != 0) {
                unitname = ((OrgDO)orgDOPageVO.getRows().get(0)).getName();
            } else {
                log.warn("\u83b7\u53d6\u7528\u6237\u7ec4\u7ec7\u673a\u6784\u4e3a\u7a7a\uff0cid:{}", (Object)userDO.getId());
            }
            user.addExtInfo("unitname", (Object)unitname);
            user.setTelephone(userDO.getTelephone());
            user.setEmail(userDO.getEmail());
            userCache.put(userId, user);
            return userDO;
        }
        return new UserDO();
    }

    public static Map<String, MetaInfoDim> getAllBillDefineInfoMap() {
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo(META_TYPE, (Object)"bill");
        tenantDO.addExtInfo("ignoreModelTitle", (Object)Boolean.TRUE);
        tenantDO.setTenantName(ShiroUtil.getTenantName());
        List metaInfoDimList = VaWorkFlowDataUtils.getMetaDataClient().getAllMetaInfoByMetaType(tenantDO).getRows();
        return metaInfoDimList.stream().collect(Collectors.toMap(MetaInfoDim::getUniqueCode, o -> o));
    }

    public static Map<String, MetaInfoDim> getAllWorkflowDefineInfoMap() {
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo(META_TYPE, (Object)"workflow");
        tenantDO.addExtInfo("ignoreModelTitle", (Object)Boolean.TRUE);
        tenantDO.setTenantName(ShiroUtil.getTenantName());
        List metaInfoDimList = VaWorkFlowDataUtils.getMetaDataClient().getAllMetaInfoByMetaType(tenantDO).getRows();
        return metaInfoDimList.stream().collect(Collectors.toMap(MetaInfoDim::getUniqueCode, o -> o));
    }

    public static OrgDO getOrgData(String tenantName, String code) {
        OrgDO orgDO = null;
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setCode(code);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setTenantName(tenantName);
        PageVO pageVO = VaWorkFlowDataUtils.getOrgDataClient().list(orgDTO);
        if (Objects.nonNull(pageVO) && !CollectionUtils.isEmpty(pageVO.getRows())) {
            orgDO = (OrgDO)pageVO.getRows().get(0);
        }
        return orgDO;
    }

    @Deprecated
    public static Map<String, String> getBizDefineTitleMap(String tenantName) {
        HashMap<String, String> billDefineTitleMap = new HashMap<String, String>(16);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo(META_TYPE, (Object)"bill");
        tenantDO.setTenantName(tenantName);
        MetaDataClient metaDataClient = VaWorkFlowDataUtils.getMetaDataClient();
        PageVO metaInfoDimPageVO = Optional.ofNullable(metaDataClient.getAllMetaInfoByMetaType(tenantDO)).orElse(new PageVO());
        List metaInfoDimList = Optional.ofNullable(metaInfoDimPageVO.getRows()).orElse(Collections.emptyList());
        for (MetaInfoDim metaInfoDim : metaInfoDimList) {
            String title = metaInfoDim.getTitle();
            String uniqueCode = metaInfoDim.getUniqueCode();
            if (ObjectUtils.isEmpty(title)) {
                title = metaInfoDim.getUniqueCode();
            }
            billDefineTitleMap.put(uniqueCode, title);
        }
        return billDefineTitleMap;
    }

    public static String getBizDefineTitle(String bizType, String bizDefine) {
        String title = bizDefine;
        TenantDO tenantDO = new TenantDO();
        tenantDO.setTraceId(Utils.getTraceId());
        if ("bill".equalsIgnoreCase(bizType)) {
            tenantDO.addExtInfo("defineCode", (Object)bizDefine);
            MetaDataClient metaDataClient = VaWorkFlowDataUtils.getMetaDataClient();
            R r = metaDataClient.findMetaInfoByDefineCode(tenantDO);
            title = (String)r.get((Object)"title");
        } else {
            Object bizDefineTitleObject;
            tenantDO.addExtInfo("bizDefine", (Object)bizDefine);
            BizTypeConfig bizTypeConfig = (BizTypeConfig)ApplicationContextRegister.getBean(BizTypeConfig.class);
            BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, bizTypeConfig, bizType);
            R r = bussinessClient.getBizTitle(tenantDO);
            if (r.getCode() == 0 && !ObjectUtils.isEmpty(bizDefineTitleObject = r.get((Object)"bizDefineTitle"))) {
                title = String.valueOf(bizDefineTitleObject);
            }
        }
        return title;
    }

    public static List<ProcessNodeDO> handleBizProcessNodes(List<ProcessNodeDO> processNodes, String bizDefine) {
        boolean hasBizNode = processNodes.stream().anyMatch(node -> !Objects.equals(node.getSyscode(), "WORKFLOW"));
        if (hasBizNode) {
            BussinessClient bussinessClient = VaWorkflowUtils.getBussinessClient("bill", bizDefine);
            ProcessNodeDTO nodeDTO = new ProcessNodeDTO();
            nodeDTO.setProcessNodes(processNodes);
            try {
                WorkflowQueryRes res = bussinessClient.businessProcessNodeProcessed(nodeDTO);
                Assert.notNull((Object)res, "\u8fd4\u56de\u7ed3\u679c\u4e3a\u7a7a");
                if (res.getCode() != 0) {
                    throw new WorkflowException(res.getMsg());
                }
                processNodes = res.getProcessNodes();
            }
            catch (Exception e) {
                log.error("\u5904\u7406\u4e1a\u52a1\u8282\u70b9\u8f68\u8ff9\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
        return processNodes;
    }

    public static UserDO getOneUserData(String tenantName, String userId) {
        if (!StringUtils.hasText(userId)) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(tenantName);
        userDTO.setId(userId);
        return VaWorkFlowDataUtils.getAuthUserClient().get(userDTO);
    }

    public static PageVO<BaseDataDO> getDeptByDeptCode(String deptcode) {
        BaseDataDTO deptDO = new BaseDataDTO();
        deptDO.setStopflag(Integer.valueOf(-1));
        deptDO.setRecoveryflag(Integer.valueOf(-1));
        deptDO.setAuthType(BaseDataOption.AuthType.NONE);
        deptDO.setTableName("MD_DEPARTMENT");
        deptDO.setObjectcode(deptcode);
        return VaWorkFlowDataUtils.getBaseDataClient().list(deptDO);
    }

    public static PageVO<BaseDataDO> getPositionByCode(String positionCode) {
        BaseDataDTO deptDO = new BaseDataDTO();
        deptDO.setStopflag(Integer.valueOf(-1));
        deptDO.setRecoveryflag(Integer.valueOf(-1));
        deptDO.setAuthType(BaseDataOption.AuthType.NONE);
        deptDO.setTableName("MD_POSITION");
        deptDO.setObjectcode(positionCode);
        return VaWorkFlowDataUtils.getBaseDataClient().list(deptDO);
    }

    public static PageVO<OrgDO> getOrgDOByUnitCode(String unitcode) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setCode(unitcode);
        return VaWorkFlowDataUtils.getOrgDataClient().list(orgDTO);
    }

    public static BaseDataDO getStaffPost(String staffObjectCode) {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName("MD_STAFF_POST");
        basedataDTO.put("commonQuery", (Object)"true");
        basedataDTO.setObjectcode(staffObjectCode);
        PageVO staffPostPageVO = VaWorkFlowDataUtils.getBaseDataClient().list(basedataDTO);
        if (staffPostPageVO == null || staffPostPageVO.getTotal() <= 0) {
            return null;
        }
        List staffPostList = staffPostPageVO.getRows();
        BaseDataDO staffPost = (BaseDataDO)staffPostList.get(0);
        String staffcode = (String)staffPost.get((Object)"staffcode");
        BaseDataDTO basedataDTO1 = new BaseDataDTO();
        basedataDTO1.setTableName("MD_STAFF");
        basedataDTO1.setObjectcode(staffcode);
        PageVO staffPageVO = VaWorkFlowDataUtils.getBaseDataClient().list(basedataDTO1);
        if (staffPageVO == null || staffPageVO.getTotal() <= 0) {
            return null;
        }
        List staffList = staffPageVO.getRows();
        return (BaseDataDO)staffList.get(0);
    }

    public static BaseDataDO getStaffPostInfo(String staffObjectCode) {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName("MD_STAFF_POST");
        basedataDTO.put("commonQuery", (Object)"true");
        basedataDTO.setObjectcode(staffObjectCode);
        PageVO staffPostPageVO = VaWorkFlowDataUtils.getBaseDataClient().list(basedataDTO);
        if (staffPostPageVO == null || staffPostPageVO.getTotal() <= 0) {
            return null;
        }
        List staffPostList = staffPostPageVO.getRows();
        return (BaseDataDO)staffPostList.get(0);
    }

    public static BaseDataDO getStaff(String staffObjectCode) {
        BaseDataDTO basedataDTO1 = new BaseDataDTO();
        basedataDTO1.setTableName("MD_STAFF");
        basedataDTO1.setObjectcode(staffObjectCode);
        PageVO staffPageVO = VaWorkFlowDataUtils.getBaseDataClient().list(basedataDTO1);
        if (staffPageVO == null || staffPageVO.getTotal() <= 0) {
            return null;
        }
        List staffList = staffPageVO.getRows();
        return (BaseDataDO)staffList.get(0);
    }

    public static UserLoginDTO getCurrentUserInfo() {
        UserLoginDTO user = ShiroUtil.getUser();
        String tenantName = user.getTenantName();
        if (!StringUtils.hasText(user.getName())) {
            UserDO userDO = VaWorkFlowDataUtils.getOneUserData(tenantName, user.getId());
            user.setName(userDO.getName());
            user.setUnitcode(userDO.getUnitcode());
            user.setTelephone(userDO.getTelephone());
            user.setEmail(userDO.getEmail());
        }
        return user;
    }

    public static boolean getChooseStaffOption() {
        Map wf1016Map;
        String isStaff;
        WorkflowHelperService workflowHelperService = (WorkflowHelperService)ApplicationContextRegister.getBean(WorkflowHelperService.class);
        WorkflowOptionDTO workFlowOptionDto = workflowHelperService.getWorkFlowOptionDto();
        String wf1016Str = workFlowOptionDto.getWf1016();
        if (!StringUtils.hasText(wf1016Str)) {
            return false;
        }
        boolean ifChooseStaff = false;
        Object o = JSONUtil.parseObject((String)wf1016Str, Object.class);
        if (o instanceof Map && Objects.equals("1", isStaff = (String)(wf1016Map = (Map)JSONUtil.parseObject((String)wf1016Str, Map.class)).get("isStaff"))) {
            ifChooseStaff = true;
        }
        return ifChooseStaff;
    }

    public BillBusinessClient getBillBusinessClientByDefineCode(String defineCode) {
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setFunctionType("BILL");
        moduleDTO.setModuleName(defineCode.split("_")[0]);
        moduleDTO.setTraceId(Utils.getTraceId());
        R module = VaWorkFlowDataUtils.getMetaDataClient().getModuleByName(moduleDTO);
        String server = String.valueOf(module.get((Object)"server"));
        String path = String.valueOf(module.get((Object)"path"));
        return (BillBusinessClient)FeignUtil.getDynamicClient(BillBusinessClient.class, (String)server, (String)path);
    }

    public static void getParamMap(Map<String, Object> todoMap, List<VaMessageTemplateParamDO> params, Map<String, Object> messageParamMap, String tenantName) {
        for (VaMessageTemplateParamDO vaMessageTemplateParamDO : params) {
            String paramName = vaMessageTemplateParamDO.getName();
            if (messageParamMap.get(paramName) != null) continue;
            String value = todoMap.get(paramName) == null ? "" : todoMap.get(paramName);
            VaWorkFlowDataUtils.processDateTypeData(messageParamMap, vaMessageTemplateParamDO, value, paramName);
            VaWorkFlowDataUtils.processMappingData(messageParamMap, tenantName, vaMessageTemplateParamDO, value, paramName);
        }
    }

    private static void processMappingData(Map<String, Object> messageParamMap, String tenantName, VaMessageTemplateParamDO vaMessageTemplateParamDO, Object value, String paramName) {
        Integer mappingType = vaMessageTemplateParamDO.getMappingType();
        if (mappingType == null || ObjectUtils.isEmpty(value)) {
            return;
        }
        String mapping = vaMessageTemplateParamDO.getMapping();
        switch (mappingType) {
            case 0: {
                messageParamMap.put(paramName, "1".equals(value) ? "\u662f" : "\u5426");
                break;
            }
            case 4: {
                OrgDO orgDO = Optional.ofNullable(VaWorkFlowDataUtils.getOrgData(tenantName, value.toString())).orElse((OrgDO)new OrgDTO());
                messageParamMap.put(paramName, orgDO.getShowTitle());
                break;
            }
            case 3: {
                UserDO userDO = VaWorkFlowDataUtils.getOneUserData(tenantName, value.toString());
                if (userDO == null) {
                    return;
                }
                messageParamMap.put(paramName, userDO.getName());
                break;
            }
            case 2: {
                EnumDataDO enumDataDO = VaWorkFlowDataUtils.getEnumData(mapping, tenantName, value.toString());
                if (enumDataDO == null) break;
                messageParamMap.put(paramName, enumDataDO.getTitle());
                break;
            }
            case 1: {
                BaseDataDO baseDataDO = VaWorkFlowDataUtils.getOneBaseData(mapping, tenantName, value.toString());
                if (baseDataDO == null) break;
                messageParamMap.put(paramName, baseDataDO.getShowTitle());
                break;
            }
        }
    }

    private static void processDateTypeData(Map<String, Object> messageParamMap, VaMessageTemplateParamDO vaMessageTemplateParamDO, Object value, String paramName) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String columnType = vaMessageTemplateParamDO.getType();
        if (DataModelType.ColumnType.TIMESTAMP.name().equals(columnType) && value instanceof Long) {
            messageParamMap.put(paramName, dateTimeFormat.format(new Date((Long)value)));
        } else if (DataModelType.ColumnType.TIMESTAMP.name().equals(columnType) && value instanceof Date) {
            messageParamMap.put(paramName, dateTimeFormat.format(value));
        } else if (DataModelType.ColumnType.DATE.name().equals(columnType) && value instanceof Date) {
            messageParamMap.put(paramName, dateFormat.format(value));
        } else {
            messageParamMap.put(paramName, value);
        }
    }

    public static EnumDataDO getEnumData(String mapping, String tenantName, String fieldValue) {
        EnumDataDO enumDataDO = null;
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype(mapping.split("\\.")[0]);
        enumDataDTO.setVal(fieldValue);
        enumDataDTO.setTenantName(tenantName);
        enumDataDTO.addExtInfo("languageTransFlag", (Object)true);
        List enumDatas = VaWorkFlowDataUtils.getEnumDataClient().list(enumDataDTO);
        if (enumDatas != null && !enumDatas.isEmpty()) {
            enumDataDO = (EnumDataDO)enumDatas.get(0);
        }
        return enumDataDO;
    }

    public static BaseDataDO getOneBaseData(String mapping, String tenantName, String fieldValue) {
        BaseDataDO baseDataDO = null;
        BaseDataDTO basedataDTO = new BaseDataDTO();
        String[] mapp = mapping.split("\\.");
        basedataDTO.setTableName(mapp[0]);
        if ("OBJECTCODE".equals(mapp[1])) {
            basedataDTO.setObjectcode(fieldValue);
        } else {
            basedataDTO.setCode(fieldValue);
        }
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        basedataDTO.setTenantName(tenantName);
        PageVO list = VaWorkFlowDataUtils.getBaseDataClient().list(basedataDTO);
        if (list != null && !list.getRows().isEmpty()) {
            baseDataDO = (BaseDataDO)list.getRows().get(0);
        }
        return baseDataDO;
    }
}

