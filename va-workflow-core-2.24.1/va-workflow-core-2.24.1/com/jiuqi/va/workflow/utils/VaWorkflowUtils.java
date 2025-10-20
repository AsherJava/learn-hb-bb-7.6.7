/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy
 *  com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategyManager
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.BizType
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule
 *  com.jiuqi.va.domain.workflow.ValueType
 *  com.jiuqi.va.domain.workflow.WorkflowSubProcessBranchStrategyDTO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.SubProcessBranchStrategyClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.workflow.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy;
import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategyManager;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.BizType;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule;
import com.jiuqi.va.domain.workflow.ValueType;
import com.jiuqi.va.domain.workflow.WorkflowSubProcessBranchStrategyDTO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.SubProcessBranchStrategyClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.config.SubProcessBranchStrategyModuleConfig;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class VaWorkflowUtils {
    private static final Logger logger = LoggerFactory.getLogger(VaWorkflowUtils.class);
    private static final String BIZ_TYPE_BILL = "BILL";
    private static MetaDataClient metaDataClient;
    private static BizTypeConfig bizTypeConfig;

    private VaWorkflowUtils() {
    }

    public static MetaDataClient getMetaDataClient() {
        if (metaDataClient == null) {
            metaDataClient = (MetaDataClient)ApplicationContextRegister.getBean(MetaDataClient.class);
        }
        return metaDataClient;
    }

    public static BizTypeConfig getBizTypeConfig() {
        if (bizTypeConfig == null) {
            bizTypeConfig = (BizTypeConfig)ApplicationContextRegister.getBean(BizTypeConfig.class);
        }
        return bizTypeConfig;
    }

    public static <T> T getDynamicFeignClient(Class<T> t, BizTypeConfig bizTypeConfig, String bizTypeName) {
        BizType resultBizType = null;
        List<BizType> bizTypes = bizTypeConfig.getTypes();
        for (BizType bizType : bizTypes) {
            if (!bizTypeName.equalsIgnoreCase(bizType.getName())) continue;
            resultBizType = bizType;
        }
        if (resultBizType == null) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.checkworkflowbiztypeinfo"));
        }
        return (T)FeignUtil.getDynamicClient(t, (String)resultBizType.getAppName(), (String)resultBizType.getAppPath());
    }

    public static <T> T getDynamicFeignClient(Class<T> t, BizTypeConfig bizTypeConfig, String bizTypeName, boolean appendPath) {
        BizType resultBizType = null;
        List<BizType> bizTypes = bizTypeConfig.getTypes();
        for (BizType bizType : bizTypes) {
            if (!bizTypeName.equalsIgnoreCase(bizType.getName())) continue;
            resultBizType = bizType;
        }
        if (resultBizType == null) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.checkworkflowbiztypeinfo"));
        }
        if (appendPath) {
            return (T)FeignUtil.getDynamicClient(t, (String)resultBizType.getAppName(), (String)(resultBizType.getAppPath() + "/" + bizTypeName.toLowerCase()));
        }
        return (T)FeignUtil.getDynamicClient(t, (String)resultBizType.getAppName(), (String)resultBizType.getAppPath());
    }

    public static List<Map<String, Object>> getMapList(Object object) {
        if (object instanceof List) {
            return (List)object;
        }
        return Collections.emptyList();
    }

    public static Map<String, Object> getMap(Object object) {
        if (object instanceof Map) {
            return (Map)object;
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> getStringMap(Object object) {
        if (object instanceof Map) {
            try {
                return (Map)object;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return new HashMap<String, String>();
    }

    public static <T> List<List<T>> splitList(List<T> list, int limit) {
        int length = list.size();
        int num = (length + limit - 1) / limit;
        ArrayList<List<T>> newList = new ArrayList<List<T>>(num);
        for (int i = 0; i < num; ++i) {
            int fromIndex = i * limit;
            int toIndex = Math.min((i + 1) * limit, length);
            newList.add(list.subList(fromIndex, toIndex));
        }
        return newList;
    }

    public static <T> List<T> getList(Object object, Class<? extends T> clazz) {
        if (object instanceof List) {
            return JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)object), clazz);
        }
        return Collections.emptyList();
    }

    public static <T> List<T> getList(Object object) {
        if (object instanceof List) {
            return (List)object;
        }
        return new ArrayList();
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static FormulaImpl getFormulaImpl(String expression, FormulaType formulaType) {
        FormulaImpl formulaImpl = new FormulaImpl();
        formulaImpl.setFormulaType(formulaType);
        formulaImpl.setExpression(expression);
        return formulaImpl;
    }

    public static void verifyInParamLegal(List<String> list) {
        List tempList = Optional.ofNullable(list).orElse(Collections.emptyList());
        for (String str : tempList) {
            if (!str.contains("=") && !str.contains("'") && !str.contains(")")) continue;
            throw new IllegalArgumentException("\u53c2\u6570\u4e0d\u5408\u6cd5\uff01");
        }
    }

    public static String dateStrConvert(String datetime, String pattern1, String pattern2) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern1);
        LocalDateTime ldt = LocalDateTime.parse(datetime, dtf);
        DateTimeFormatter fa = DateTimeFormatter.ofPattern(pattern2);
        return ldt.format(fa);
    }

    public static void setTractId(TenantDO tenantDO) {
        if (!StringUtils.hasText(tenantDO.getTraceId())) {
            String traceId = GUID.newGUID();
            Utils.setTraceId((String)traceId);
            tenantDO.setTraceId(traceId);
        } else {
            Utils.setTraceId((String)tenantDO.getTraceId());
        }
    }

    public static UserLoginDTO getLoginUserWithToken() {
        UserLoginDTO loginUser = ShiroUtil.getUser();
        String token = ShiroUtil.getToken();
        if (!StringUtils.hasText(token)) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(loginUser.getUsername());
            userDTO.setCheckPwd(false);
            userDTO.setTenantName(loginUser.getTenantName());
            userDTO.addExtInfo("loginDate", (Object)new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            AuthUserClient authUserClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
            R authR = authUserClient.getLoginToken(userDTO);
            token = (String)authR.get((Object)"token");
        }
        loginUser.addExtInfo("JTOKENID", (Object)token);
        return loginUser;
    }

    public static BussinessClient getBussinessClient(String bizType, String bizDefine) {
        BussinessClient bussinessClient;
        if (BIZ_TYPE_BILL.equalsIgnoreCase(bizType)) {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setModuleName(bizDefine.split("_")[0]);
            moduleDTO.setFunctionType(BIZ_TYPE_BILL);
            R r = VaWorkflowUtils.getMetaDataClient().getModuleByName(moduleDTO);
            String server = String.valueOf(r.get((Object)"server"));
            String path = String.valueOf(r.get((Object)"path"));
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
        } else {
            BizTypeConfig bizTypeConfig = VaWorkflowUtils.getBizTypeConfig();
            bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, bizTypeConfig, bizType, false);
        }
        return bussinessClient;
    }

    public static boolean isSubmitterCannotApprove(boolean globalSubmitterCannotApprove, JsonNode jsonNode) {
        if (jsonNode.get("submittercannotapprove") == null) {
            return false;
        }
        String value = jsonNode.get("submittercannotapprove").asText();
        if ("01".equals(value)) {
            return globalSubmitterCannotApprove;
        }
        return "02".equals(value);
    }

    public static Set<String> getApproverBeforeLastRejection(String bizCode, String nodeCode, String subProcessBranch) {
        HashSet<String> approverBeforeLastRejection = new HashSet<String>();
        WorkflowProcessNodeService workflowProcessNodeService = (WorkflowProcessNodeService)ApplicationContextRegister.getBean(WorkflowProcessNodeService.class);
        ProcessNodeDTO nodeDTO = new ProcessNodeDTO();
        nodeDTO.setOrder("DESC");
        nodeDTO.setBizcode(bizCode);
        List processNodeDOList = workflowProcessNodeService.listProcessNode(nodeDTO);
        ProcessNodeDO targetNode = VaWorkflowUtils.findTargetNode(nodeCode, subProcessBranch, processNodeDOList);
        if (targetNode == null) {
            return approverBeforeLastRejection;
        }
        VaWorkflowUtils.filterOtherBranch(processNodeDOList, targetNode);
        boolean stopFlag = false;
        for (ProcessNodeDO processNodeDO : processNodeDOList) {
            if (nodeCode.equals(processNodeDO.getNodecode())) {
                approverBeforeLastRejection.add(processNodeDO.getCompleteuserid());
                stopFlag = true;
                continue;
            }
            if (!stopFlag) continue;
            break;
        }
        return approverBeforeLastRejection;
    }

    public static ProcessNodeDO findTargetNode(String nodeCode, String subProcessBranch, List<ProcessNodeDO> processNodeDOList) {
        if (StringUtils.hasText(subProcessBranch)) {
            return processNodeDOList.stream().filter(x -> nodeCode.equals(x.getNodecode())).filter(x -> subProcessBranch.equals(x.getSubprocessbranch())).findFirst().orElse(null);
        }
        return processNodeDOList.stream().filter(x -> nodeCode.equals(x.getNodecode())).findFirst().orElse(null);
    }

    public static void filterOtherBranch(List<ProcessNodeDO> processNodeDOList, ProcessNodeDO targetNode) {
        if (StringUtils.hasText(targetNode.getPgwbranch())) {
            processNodeDOList.removeIf(x -> targetNode.getPgwnodeid().equals(x.getPgwnodeid()) && !targetNode.getPgwbranch().equals(x.getPgwbranch()));
        }
        if (StringUtils.hasText(targetNode.getSubprocessbranch())) {
            processNodeDOList.removeIf(x -> targetNode.getSubprocessnodeid().equals(x.getSubprocessnodeid()) && !targetNode.getSubprocessbranch().equals(x.getSubprocessbranch()));
        }
    }

    public static String getOriginalApproverByPlus(ProcessNodeDO processNodeDO, List<ProcessNodeDO> latestProcessNodes) {
        String originUser = processNodeDO.getCompleteuserid();
        List nodeIds = latestProcessNodes.stream().map(ProcessNodeDO::getNodeid).collect(Collectors.toList());
        PlusApprovalInfoDTO info = new PlusApprovalInfoDTO();
        info.setNodecode(processNodeDO.getNodecode());
        info.setNodeIds(nodeIds);
        WorkflowPlusApprovalService workflowPlusApprovalService = (WorkflowPlusApprovalService)ApplicationContextRegister.getBean(WorkflowPlusApprovalService.class);
        List plusApprovalInfos = workflowPlusApprovalService.selectByConditionAndNodeIds(info);
        if (plusApprovalInfos.isEmpty()) {
            return originUser;
        }
        String finalOriginUser = originUser;
        PlusApprovalInfoDO approvalInfoDO = plusApprovalInfos.stream().filter(plus -> Objects.equals(plus.getNodeid(), processNodeDO.getNodeid())).filter(plus -> Objects.equals(plus.getApprovaluser(), finalOriginUser)).findFirst().orElse(null);
        if (approvalInfoDO == null) {
            return originUser;
        }
        String finalPlusUser = approvalInfoDO.getUsername();
        LinkedList<String> list = new LinkedList<String>();
        list.push(finalPlusUser);
        LinkedHashSet<String> uniqueSet = new LinkedHashSet<String>();
        uniqueSet.add(finalPlusUser);
        while (!list.isEmpty()) {
            String plusUser = (String)list.pop();
            plusApprovalInfos.stream().filter(plus -> Objects.equals(plus.getApprovaluser(), plusUser)).findFirst().ifPresent(plus -> {
                String username = plus.getUsername();
                if (!uniqueSet.add(username)) {
                    return;
                }
                list.push(username);
            });
        }
        originUser = (String)new ArrayList(uniqueSet).get(uniqueSet.size() - 1);
        return originUser;
    }

    public static void builderMultiInCondition(TenantDO tenantDO, List<String> values, String whereKey, String itemKey, SQL sql) {
        HashMap<String, String> param = new HashMap<String, String>(values.size());
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < values.size(); ++j) {
            String bizCode = values.get(j);
            param.put(itemKey + j, bizCode);
            if (j % 500 == 0) {
                if (j > 0) {
                    stringBuilder.append(") or ");
                }
                stringBuilder.append(whereKey).append(" in (");
                stringBuilder.append(" #{").append("extInfo.").append(itemKey).append(j).append("}");
                continue;
            }
            stringBuilder.append(", #{").append("extInfo.").append(itemKey).append(j).append("}");
        }
        stringBuilder.append(")");
        tenantDO.setExtInfo(param);
        sql.WHERE("(" + stringBuilder + ")");
    }

    public static String getSubProcessRetractMq(Map<String, Object> branchStrategy) {
        String mqName = "";
        String strategyModuleName = (String)branchStrategy.get("subProcessBranchStrategyModuleName");
        String strategyName = (String)branchStrategy.get("subProcessBranchStrategyName");
        mqName = VaWorkflowUtils.isGeneralModule(strategyModuleName) ? VaWorkflowUtils.findMqInLocalStrategies(strategyModuleName, strategyName) : VaWorkflowUtils.findMqInRemoteStrategies(strategyModuleName, strategyName);
        return mqName;
    }

    private static boolean isGeneralModule(String strategyModuleName) {
        return "general".equalsIgnoreCase(strategyModuleName);
    }

    private static String findMqInLocalStrategies(String strategyModuleName, String strategyName) {
        SubProcessBranchStrategyManager manager = (SubProcessBranchStrategyManager)ApplicationContextRegister.getBean(SubProcessBranchStrategyManager.class);
        List strategies = manager.getStrategyList(strategyModuleName);
        for (SubProcessBranchStrategy strategy : strategies) {
            if (!strategyName.equalsIgnoreCase(strategy.getName())) continue;
            return strategy.getRetractMq();
        }
        return "";
    }

    private static String findMqInRemoteStrategies(String strategyModuleName, String strategyName) {
        SubProcessBranchStrategyModuleConfig config = (SubProcessBranchStrategyModuleConfig)ApplicationContextRegister.getBean(SubProcessBranchStrategyModuleConfig.class);
        List<SubProcessBranchStrategyModule> modules = config.getModules();
        for (SubProcessBranchStrategyModule module : modules) {
            if (!strategyModuleName.equalsIgnoreCase(module.getName())) continue;
            return VaWorkflowUtils.fetchMqFromRemoteService(module, strategyName);
        }
        return "";
    }

    private static String fetchMqFromRemoteService(SubProcessBranchStrategyModule module, String strategyName) {
        SubProcessBranchStrategyClient client = (SubProcessBranchStrategyClient)FeignUtil.getDynamicClient(SubProcessBranchStrategyClient.class, (String)module.getAppName(), (String)module.getAppPath());
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("strategyModule", (Object)module.getName());
        R r = client.getStrategyList(tenantDO);
        List strategies = JSONUtil.parseArray((String)String.valueOf(r.get((Object)"strategys")), WorkflowSubProcessBranchStrategyDTO.class);
        for (WorkflowSubProcessBranchStrategyDTO strategy : strategies) {
            if (!strategyName.equalsIgnoreCase(strategy.getName())) continue;
            return strategy.getRetractMq();
        }
        return "";
    }

    public static void calProcessParam(Map<String, Object> workflowVariables, WorkflowModel workflowModel) {
        ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)workflowModel.getPlugins().get(ProcessParamPlugin.class);
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
        List<ProcessParam> processParams = processParamPluginDefine.getProcessParam();
        for (ProcessParam processParam : processParams) {
            Object paramValue = workflowVariables.get(processParam.getParamName());
            if (paramValue == null) {
                ValueType valueType = processParam.getParamType();
                if (ValueType.BOOLEAN == valueType) {
                    paramValue = false;
                }
                if (ValueType.STRING == valueType) {
                    paramValue = "";
                }
                if (ValueType.DECIMAL == valueType || ValueType.INTEGER == valueType || ValueType.LONG == valueType) {
                    paramValue = 0;
                }
            }
            workflowVariables.put(processParam.getParamName(), paramValue);
        }
    }

    public static Set<String> extractProcessParam(String input) {
        if (!StringUtils.hasText(input)) {
            return Collections.emptySet();
        }
        HashSet<String> matchesSet = new HashSet<String>();
        String pattern = "(?<![a-zA-Z_])\\[[A-Z][A-Z0-9_]*]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        while (m.find()) {
            matchesSet.add(m.group());
        }
        return matchesSet;
    }

    public static byte[] encodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlEncoder().encode(src);
    }

    public static byte[] decodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlDecoder().decode(src);
    }
}

