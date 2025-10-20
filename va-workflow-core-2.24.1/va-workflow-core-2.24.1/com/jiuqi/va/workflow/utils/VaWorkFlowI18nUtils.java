/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.workflow.NodeView
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.utils.VaI18nParamUtil
 */
package com.jiuqi.va.workflow.utils;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.workflow.NodeView;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.domain.forward.AuditInfo;
import com.jiuqi.va.workflow.domain.forward.RejectDesignateNodeVO;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public final class VaWorkFlowI18nUtils {
    private static final Logger logger = LoggerFactory.getLogger(VaWorkFlowI18nUtils.class);
    private static final String ERROR_OCCUR = "Error Occur:";
    public static final Map<String, String> DICT_MAP = new HashMap<String, String>(16);
    private static final String VAL = "val";
    private static final String TITLE = "title";
    private static final String NODENAME = "nodeName";
    private static final String AUDITRESULT = "auditResult";
    private static final String AUDITCOMMENT = "comment";
    private static final String AUDITINFO = "auditInfo";
    private static final String COUNTERSIGN_SELECTUSER = "VA#other#COUNTERSIGN_SELECTUSER#";
    public static final String WORKFLOW_ACTIVITI_I18NKEY = "va_activiti_";
    public static final String FAILED_TO_GET_APPROVER = "va_activiti_failedgetnextnodeapprover_i18n";
    private static MessageSource messageSource;
    private static VaI18nClient vaI18nClient;
    private static WorkflowHelperService workflowHelperService;

    private VaWorkFlowI18nUtils() {
    }

    private static MessageSource getMessageSource() {
        if (messageSource == null) {
            messageSource = (MessageSource)ApplicationContextRegister.getBean((String)"workflowMessageSource");
        }
        return messageSource;
    }

    public static VaI18nClient getVaI18nClient() {
        if (vaI18nClient == null) {
            vaI18nClient = (VaI18nClient)ApplicationContextRegister.getBean(VaI18nClient.class);
        }
        return vaI18nClient;
    }

    public static WorkflowHelperService getWorkflowHelperService() {
        if (workflowHelperService == null) {
            workflowHelperService = (WorkflowHelperService)ApplicationContextRegister.getBean(WorkflowHelperService.class);
        }
        return workflowHelperService;
    }

    public static String getInfo(String infoFlag) {
        try {
            String message = VaWorkFlowI18nUtils.getMessageSource().getMessage(infoFlag, null, LocaleContextHolder.getLocale());
            if (!StringUtils.hasText(message)) {
                message = ERROR_OCCUR;
            }
            return message;
        }
        catch (Exception e) {
            return ERROR_OCCUR;
        }
    }

    public static String getInfo(String infoFlag, Object ... args) {
        try {
            LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
            Locale locale = localeContext == null || localeContext.getLocale() == null ? EnvConfig.getDefaultLocale() : LocaleContextHolder.getLocale();
            String message = VaWorkFlowI18nUtils.getMessageSource().getMessage(infoFlag, args, locale);
            if (!StringUtils.hasText(message)) {
                message = ERROR_OCCUR;
            }
            return message;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ERROR_OCCUR;
        }
    }

    public static void convertApprovalLanguage(List<NodeView> views) {
        if (!VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
            return;
        }
        if (CollectionUtils.isEmpty(views) || LocaleContextHolder.getLocale().equals(Locale.CHINA)) {
            return;
        }
        try {
            HashMap<String, Object> bizCodeCacheMap = new HashMap<String, Object>();
            String bizCode = null;
            for (NodeView view : views) {
                if (!Objects.isNull(bizCode)) continue;
                bizCode = view.getBizCode();
            }
            for (NodeView nodeView : views) {
                List nodeViews;
                nodeView.getNodeNameMap().put(TITLE, nodeView.getNodeName());
                String nodeName = DICT_MAP.get(nodeView.getNodeName());
                String approvalComment = DICT_MAP.get(nodeView.getApprovalComment());
                if (StringUtils.hasText(nodeName) && StringUtils.hasText(VaWorkFlowI18nUtils.getInfo(nodeName))) {
                    nodeView.setNodeName(VaWorkFlowI18nUtils.getInfo(nodeName));
                } else {
                    String nodeLanguageFlag;
                    String tempNodeName;
                    ProcessDO tempProcessDO;
                    if (bizCodeCacheMap.containsKey(bizCode)) {
                        tempProcessDO = (ProcessDO)bizCodeCacheMap.get(bizCode);
                    } else {
                        tempProcessDO = VaWorkFlowI18nUtils.getWorkflowHelperService().getProcessDoByBizCode(bizCode);
                        if (Objects.nonNull(tempProcessDO)) {
                            bizCodeCacheMap.put(bizCode, tempProcessDO);
                        }
                    }
                    if (Objects.nonNull(tempProcessDO) && StringUtils.hasLength(tempProcessDO.getDefinekey()) && !ObjectUtils.isEmpty(tempNodeName = VaWorkFlowI18nUtils.findNodeNameFromLanguage(nodeLanguageFlag = VaWorkFlowI18nUtils.getWorkflowHelperService().getWorkFlowLanguageFlag(bizCodeCacheMap, nodeView, tempProcessDO)))) {
                        nodeView.setNodeName(tempNodeName);
                    }
                }
                if (StringUtils.hasText(approvalComment) && StringUtils.hasText(VaWorkFlowI18nUtils.getInfo(approvalComment))) {
                    nodeView.setApprovalComment(VaWorkFlowI18nUtils.getInfo(approvalComment));
                }
                if (CollectionUtils.isEmpty(nodeViews = nodeView.getNodeViews())) continue;
                VaWorkFlowI18nUtils.convertApprovalLanguage(nodeViews);
            }
        }
        catch (Exception e) {
            logger.error(ERROR_OCCUR, e);
        }
    }

    private static String findNodeNameFromLanguage(String nodeLanguageFlag) {
        if (ObjectUtils.isEmpty(nodeLanguageFlag)) {
            return "";
        }
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.add(nodeLanguageFlag);
        VaI18nResourceDTO vaI18nResourceDTO = new VaI18nResourceDTO();
        vaI18nResourceDTO.setKey(tempList);
        List list = VaWorkFlowI18nUtils.getVaI18nClient().queryList(vaI18nResourceDTO);
        if (!ObjectUtils.isEmpty(list)) {
            return (String)list.get(0);
        }
        return "";
    }

    @Deprecated
    public static void convertOptionItemLanguage(List<OptionItemVO> optionItems) {
        if (CollectionUtils.isEmpty(optionItems)) {
            return;
        }
        try {
            OptionItemVO optionItemVO = optionItems.stream().filter(x -> "WF1005".equals(x.getName())).findAny().orElse(null);
            if (Objects.nonNull(optionItemVO)) {
                VaI18nResourceDTO vaI18nResourceDTO = new VaI18nResourceDTO();
                String val = optionItemVO.getVal();
                List valList = (List)JSONUtil.parseObject((String)val, List.class);
                List columnNames2 = valList.stream().map(x -> COUNTERSIGN_SELECTUSER + x.get(VAL)).collect(Collectors.toList());
                vaI18nResourceDTO.setKey(columnNames2);
                List languages = Optional.ofNullable(VaWorkFlowI18nUtils.getVaI18nClient().queryList(vaI18nResourceDTO)).orElse(Collections.emptyList());
                int size = languages.size();
                for (int i = 0; i < size; ++i) {
                    if (!StringUtils.hasText((String)languages.get(i))) continue;
                    ((Map)valList.get(i)).put(TITLE, languages.get(i));
                }
                optionItemVO.setVal(JSONUtil.toJSONString((Object)valList));
                if (optionItems.size() == 1) {
                    optionItems.set(0, optionItemVO);
                } else {
                    optionItems.set(4, optionItemVO);
                }
            }
        }
        catch (Exception e) {
            logger.debug(ERROR_OCCUR, e);
        }
    }

    public static void handleWorkflowOptionI18n(List<OptionItemVO> optionItems) {
        if (CollectionUtils.isEmpty(optionItems)) {
            return;
        }
        List wf1005DefaultItems = null;
        List wf1005valItems = null;
        Map wf1016valItemMap = null;
        ArrayList<String> i18nKeys = new ArrayList<String>();
        String userPrefixKey = "VA#workflow#counterSignShow#user#";
        String staffPrefixKey = "VA#workflow#counterSignShow#staff#";
        try {
            for (OptionItemVO optionItem : optionItems) {
                String valStr;
                String val;
                Map item;
                Iterator item22;
                String name = optionItem.getName();
                if ("WF1005".equals(name)) {
                    String valStr2;
                    String defaultValStr = optionItem.getDefauleVal();
                    if (StringUtils.hasText(defaultValStr)) {
                        wf1005DefaultItems = JSONUtil.parseMapArray((String)defaultValStr);
                        for (Iterator item22 : wf1005DefaultItems) {
                            String val2 = (String)item22.get(VAL);
                            i18nKeys.add(userPrefixKey + val2);
                        }
                    }
                    if (!StringUtils.hasText(valStr2 = optionItem.getVal())) continue;
                    wf1005valItems = JSONUtil.parseMapArray((String)valStr2);
                    item22 = wf1005valItems.iterator();
                    while (item22.hasNext()) {
                        item = (Map)item22.next();
                        val = (String)item.get(VAL);
                        i18nKeys.add(userPrefixKey + val);
                    }
                    continue;
                }
                if (!"WF1016".equals(name) || "0".equals(valStr = optionItem.getVal()) || !StringUtils.hasText(valStr)) continue;
                wf1016valItemMap = JSONUtil.parseMap((String)valStr);
                List wf1016valItems = (List)wf1016valItemMap.get("columns");
                item22 = wf1016valItems.iterator();
                while (item22.hasNext()) {
                    item = (Map)item22.next();
                    val = (String)item.get(VAL);
                    i18nKeys.add(staffPrefixKey + val);
                }
            }
            if (i18nKeys.isEmpty()) {
                return;
            }
            VaI18nResourceDTO i18nResourceDTO = new VaI18nResourceDTO();
            i18nResourceDTO.setKey(i18nKeys);
            List i18nValues = VaWorkFlowI18nUtils.getVaI18nClient().queryList(i18nResourceDTO);
            HashMap<String, String> i18nMap = new HashMap<String, String>();
            int i18nKeysSize = i18nKeys.size();
            for (int i = 0; i < i18nKeysSize; ++i) {
                String value = (String)i18nValues.get(i);
                if (!StringUtils.hasText(value)) continue;
                String i18nKey = (String)i18nKeys.get(i);
                i18nMap.put(i18nKey, value);
            }
            if (i18nMap.isEmpty()) {
                return;
            }
            VaWorkFlowI18nUtils.handleOptionItemI18n(wf1005DefaultItems, i18nMap, userPrefixKey);
            VaWorkFlowI18nUtils.handleOptionItemI18n(wf1005valItems, i18nMap, userPrefixKey);
            if (!CollectionUtils.isEmpty(wf1016valItemMap)) {
                VaWorkFlowI18nUtils.handleOptionItemI18n((List)wf1016valItemMap.get("columns"), i18nMap, staffPrefixKey);
            }
            for (OptionItemVO optionItem : optionItems) {
                String name = optionItem.getName();
                if ("WF1005".equals(name)) {
                    if (!CollectionUtils.isEmpty(wf1005DefaultItems)) {
                        optionItem.setDefauleVal(JSONUtil.toJSONString((Object)wf1005DefaultItems));
                    }
                    if (CollectionUtils.isEmpty(wf1005valItems)) continue;
                    optionItem.setVal(JSONUtil.toJSONString((Object)wf1005valItems));
                    continue;
                }
                if (!"WF1016".equals(name) || CollectionUtils.isEmpty(wf1016valItemMap)) continue;
                optionItem.setVal(JSONUtil.toJSONString((Object)wf1016valItemMap));
            }
        }
        catch (Exception e) {
            logger.error("Error Occur:\u5de5\u4f5c\u6d41\u7cfb\u7edf\u9009\u9879\u591a\u8bed\u8a00\u7ffb\u8bd1\u5f02\u5e38{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private static void handleOptionItemI18n(List<Map<String, Object>> items, Map<String, String> i18nMap, String prefixKey) {
        if (!CollectionUtils.isEmpty(items)) {
            for (Map<String, Object> wf1005DefaultItem : items) {
                String val = (String)wf1005DefaultItem.get(VAL);
                String i18nTitle = i18nMap.get(prefixKey + val);
                if (!StringUtils.hasText(i18nTitle)) continue;
                wf1005DefaultItem.put(TITLE, i18nTitle);
            }
        }
    }

    public static void convertNodeLanguage(List<Map<String, Object>> nodes, String bizDefine, String bizCode, Long metaDataVersion) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }
        if (ObjectUtils.isEmpty(bizDefine) || ObjectUtils.isEmpty(bizCode)) {
            return;
        }
        try {
            HashMap<String, Object> bizCodeCacheMap = new HashMap<String, Object>();
            for (Map<String, Object> node : nodes) {
                Object auditInfoObject;
                Object nodeCode = node.get("stencilId");
                if (ObjectUtils.isEmpty(nodeCode)) continue;
                String nodeName = DICT_MAP.get(node.get(NODENAME));
                if (StringUtils.hasText(nodeName)) {
                    String info = VaWorkFlowI18nUtils.getInfo(nodeName);
                    if (!ObjectUtils.isEmpty(info)) {
                        node.put(NODENAME, info);
                    }
                } else {
                    String nodeLanguageFlag;
                    String tempNodeName;
                    ProcessDO tempProcessDO;
                    NodeView nodeView = new NodeView();
                    nodeView.setBizDefine(bizDefine);
                    nodeView.setBizCode(bizCode);
                    nodeView.setNodeCode(String.valueOf(nodeCode));
                    if (bizCodeCacheMap.containsKey(bizDefine)) {
                        tempProcessDO = (ProcessDO)bizCodeCacheMap.get(bizDefine);
                    } else {
                        tempProcessDO = VaWorkFlowI18nUtils.getWorkflowHelperService().getProcessDoByBizCode(bizCode);
                        if (Objects.isNull(tempProcessDO) && !ObjectUtils.isEmpty(metaDataVersion)) {
                            tempProcessDO = new ProcessDO();
                            tempProcessDO.setDefineversion(new BigDecimal(metaDataVersion));
                            tempProcessDO.setDefinekey(bizDefine);
                        }
                        if (Objects.nonNull(tempProcessDO)) {
                            bizCodeCacheMap.put(bizDefine, tempProcessDO);
                        }
                    }
                    if (Objects.nonNull(tempProcessDO) && StringUtils.hasLength(tempProcessDO.getDefinekey()) && !ObjectUtils.isEmpty(tempNodeName = VaWorkFlowI18nUtils.findNodeNameFromLanguage(nodeLanguageFlag = VaWorkFlowI18nUtils.getWorkflowHelperService().getWorkFlowLanguageFlag(bizCodeCacheMap, nodeView, tempProcessDO)))) {
                        node.put(NODENAME, tempNodeName);
                    }
                }
                if (!((auditInfoObject = node.get(AUDITINFO)) instanceof List)) continue;
                List list = (List)auditInfoObject;
                for (Map map : list) {
                    Object auditComment;
                    Object auditResult = map.get(AUDITRESULT);
                    if (StringUtils.hasText(DICT_MAP.get(auditResult)) && StringUtils.hasText(VaWorkFlowI18nUtils.getInfo(DICT_MAP.get(auditResult)))) {
                        map.put(AUDITRESULT, VaWorkFlowI18nUtils.getInfo(DICT_MAP.get(auditResult)));
                    }
                    if (!StringUtils.hasText(DICT_MAP.get(auditComment = map.get(AUDITCOMMENT))) || !StringUtils.hasText(VaWorkFlowI18nUtils.getInfo(DICT_MAP.get(auditComment)))) continue;
                    map.put(AUDITCOMMENT, VaWorkFlowI18nUtils.getInfo(DICT_MAP.get(auditComment)));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String convertError(String message) {
        String tempStr = message;
        if (StringUtils.hasText(tempStr) && tempStr.contains(WORKFLOW_ACTIVITI_I18NKEY)) {
            String prefixInfo = "";
            String info = "";
            if (tempStr.contains("$")) {
                int index = tempStr.indexOf("$");
                prefixInfo = tempStr.substring(0, index);
                tempStr = tempStr.substring(index + 1);
            }
            String suffixInfo = "";
            if (tempStr.contains("#")) {
                int index = tempStr.indexOf("#");
                suffixInfo = tempStr.substring(index + 1);
                tempStr = tempStr.substring(0, index);
            }
            info = VaWorkFlowI18nUtils.getInfo(tempStr);
            if (tempStr.contains(FAILED_TO_GET_APPROVER)) {
                info = VaWorkFlowI18nUtils.getInfo(FAILED_TO_GET_APPROVER) + tempStr.substring(FAILED_TO_GET_APPROVER.length());
            }
            return prefixInfo + info + suffixInfo;
        }
        return tempStr;
    }

    public static void dealNodeMap(String msg, Map<String, Object> node) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TITLE, msg);
        map.put("value", VaWorkFlowI18nUtils.getInfo(DICT_MAP.get(msg)));
        node.put("nodeNameMap", map);
    }

    public static void convertProcessNodeListI18n(List<? extends ProcessNodeDO> tempProcessNodeList, Map<String, String> nodeCodeLanguageMap, Map<String, Object> bizCodeCacheMap) {
        if (ObjectUtils.isEmpty(tempProcessNodeList)) {
            return;
        }
        try {
            ArrayList<String> nodeCodeList = new ArrayList<String>();
            ArrayList<String> nodeCodeLanguageFlagList = new ArrayList<String>();
            for (ProcessNodeDO processNodeDO : tempProcessNodeList) {
                ProcessDO tempProcessDO;
                String bizCode = processNodeDO.getBizcode();
                String string = processNodeDO.getNodecode();
                String tempNodeNameFromCache = nodeCodeLanguageMap.get(string);
                if (!ObjectUtils.isEmpty(tempNodeNameFromCache)) {
                    processNodeDO.setProcessnodename(tempNodeNameFromCache);
                    continue;
                }
                NodeView nodeView = new NodeView();
                nodeView.setNodeCode(processNodeDO.getNodecode());
                nodeView.setBizDefine(processNodeDO.getBizdefine());
                nodeView.setBizCode(processNodeDO.getBizcode());
                if (bizCodeCacheMap.containsKey(bizCode)) {
                    tempProcessDO = (ProcessDO)bizCodeCacheMap.get(bizCode);
                } else {
                    tempProcessDO = VaWorkFlowI18nUtils.getWorkflowHelperService().getProcessDoByBizCode(bizCode);
                    if (Objects.nonNull(tempProcessDO)) {
                        bizCodeCacheMap.put(bizCode, tempProcessDO);
                    }
                }
                String processNodeName = processNodeDO.getProcessnodename();
                String i18nFlag = DICT_MAP.get(processNodeName);
                if (!ObjectUtils.isEmpty(i18nFlag)) {
                    String tempProcessNodeName = VaWorkFlowI18nUtils.getInfo(i18nFlag);
                    processNodeDO.setProcessnodename(tempProcessNodeName);
                    nodeCodeLanguageMap.put(string, tempProcessNodeName);
                    continue;
                }
                if (!Objects.nonNull(tempProcessDO) || !StringUtils.hasLength(tempProcessDO.getDefinekey())) continue;
                String nodeLanguageFlag = VaWorkFlowI18nUtils.getWorkflowHelperService().getWorkFlowLanguageFlag(bizCodeCacheMap, nodeView, tempProcessDO);
                nodeCodeLanguageFlagList.add(nodeLanguageFlag);
                nodeCodeList.add(string);
            }
            if (nodeCodeList.isEmpty()) {
                return;
            }
            VaI18nResourceDTO vaI18nResourceDTO = new VaI18nResourceDTO();
            vaI18nResourceDTO.setKey(nodeCodeLanguageFlagList);
            List list = Optional.ofNullable(VaWorkFlowI18nUtils.getVaI18nClient().queryList(vaI18nResourceDTO)).orElse(Collections.emptyList());
            if (list.size() == nodeCodeList.size()) {
                for (ProcessNodeDO processNodeDO : tempProcessNodeList) {
                    String tempLanguage;
                    String nodeCode = processNodeDO.getNodecode();
                    int i = nodeCodeList.indexOf(nodeCode);
                    if (i == -1 || ObjectUtils.isEmpty(tempLanguage = (String)list.get(i))) continue;
                    processNodeDO.setProcessnodename(tempLanguage);
                    nodeCodeLanguageMap.put(nodeCode, tempLanguage);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static String getWorkFlowLanguageFlag(String workFlowDefine, String workFlowDefineVersion, String nodeCode) {
        return "VA#workflow#" + workFlowDefine + "&define#" + workFlowDefineVersion + "&workflowversion#processDesignPlugin&plugin#" + nodeCode;
    }

    public static void convertRejectNodeI18n(List<RejectDesignateNodeVO> nodeVOList, ProcessDO processDO, ArrayNode arrayNode) {
        boolean i18nEnable;
        if (CollectionUtils.isEmpty(nodeVOList) || Objects.isNull(processDO) || arrayNode.isEmpty()) {
            return;
        }
        boolean bl = i18nEnable = !Boolean.TRUE.equals(VaI18nParamUtil.getTranslationEnabled());
        if (i18nEnable) {
            return;
        }
        try {
            Map<String, String> i18nMap = VaWorkFlowI18nUtils.getRejectDesignateNodeMap(nodeVOList);
            if (CollectionUtils.isEmpty(i18nMap)) {
                return;
            }
            String defineCode = processDO.getDefinekey();
            BigDecimal defineVersion = processDO.getDefineversion();
            ArrayList<String> nodeCodeFlagList = new ArrayList<String>();
            for (String s : i18nMap.keySet()) {
                String workFlowLanguageFlag = VaWorkFlowI18nUtils.getWorkFlowLanguageFlag(defineCode, String.valueOf(defineVersion), s);
                nodeCodeFlagList.add(workFlowLanguageFlag);
            }
            VaI18nResourceDTO vaI18nResourceDTO = new VaI18nResourceDTO();
            vaI18nResourceDTO.setKey(nodeCodeFlagList);
            List languageResultList = Optional.ofNullable(VaWorkFlowI18nUtils.getVaI18nClient().queryList(vaI18nResourceDTO)).orElse(Collections.emptyList());
            int size = languageResultList.size();
            if (size == nodeCodeFlagList.size()) {
                for (int i = 0; i < size; ++i) {
                    String i18nKey = (String)nodeCodeFlagList.get(i);
                    String[] split = i18nKey.split("#");
                    String nodeCode = split[split.length - 1];
                    String language = (String)languageResultList.get(i);
                    if (StringUtils.hasText(language)) {
                        i18nMap.put(nodeCode, language);
                        continue;
                    }
                    String tempI18nKey = i18nMap.get(nodeCode);
                    String i18nFlag = DICT_MAP.get(tempI18nKey);
                    if (!StringUtils.hasText(i18nFlag)) continue;
                    String tempProcessNodeName = VaWorkFlowI18nUtils.getInfo(i18nFlag);
                    i18nMap.put(nodeCode, tempProcessNodeName);
                }
            }
            VaWorkFlowI18nUtils.convertRejectNodeI18nExecute(nodeVOList, i18nMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void convertRejectNodeI18nExecute(List<RejectDesignateNodeVO> nodeVOList, Map<String, String> i18nMap) {
        if (CollectionUtils.isEmpty(nodeVOList) || CollectionUtils.isEmpty(i18nMap)) {
            return;
        }
        for (RejectDesignateNodeVO designateNodeVO : nodeVOList) {
            String stencilId = designateNodeVO.getStencilId();
            String nodeNameWithI18n = i18nMap.get(stencilId);
            if (StringUtils.hasText(nodeNameWithI18n)) {
                designateNodeVO.setNodeName(nodeNameWithI18n);
            }
            List auditInfoList = Optional.ofNullable(designateNodeVO.getAuditInfo()).orElse(Collections.emptyList());
            for (AuditInfo auditInfo : auditInfoList) {
                String auditResultWithI18n;
                String auditResult = auditInfo.getAuditResult();
                String i18nKey = DICT_MAP.get(auditResult);
                if (!StringUtils.hasText(i18nKey) || !StringUtils.hasText(auditResultWithI18n = VaWorkFlowI18nUtils.getInfo(i18nKey))) continue;
                auditInfo.setAuditResult(auditResultWithI18n);
            }
            List<List<RejectDesignateNodeVO>> children = designateNodeVO.getChildren();
            if (CollectionUtils.isEmpty(children)) continue;
            for (List<RejectDesignateNodeVO> child : children) {
                VaWorkFlowI18nUtils.convertRejectNodeI18nExecute(child, i18nMap);
            }
        }
    }

    private static Map<String, String> getRejectDesignateNodeMap(List<RejectDesignateNodeVO> nodeVOList) {
        HashMap<String, String> nodeMap = new HashMap<String, String>();
        if (CollectionUtils.isEmpty(nodeVOList)) {
            return nodeMap;
        }
        Stack<List<RejectDesignateNodeVO>> stack = new Stack<List<RejectDesignateNodeVO>>();
        stack.push(nodeVOList);
        while (!stack.isEmpty()) {
            List currentList = Optional.ofNullable(stack.pop()).orElse(Collections.emptyList());
            for (RejectDesignateNodeVO designateNodeVO : currentList) {
                String stencilId = designateNodeVO.getStencilId();
                String nodeName = designateNodeVO.getNodeName();
                nodeMap.put(stencilId, nodeName);
                List<List<RejectDesignateNodeVO>> children = designateNodeVO.getChildren();
                if (CollectionUtils.isEmpty(children)) continue;
                stack.addAll(children);
            }
        }
        return nodeMap;
    }

    static {
        DICT_MAP.put("\u63d0\u4ea4", "va.workflow.submit");
        DICT_MAP.put("\u5165\u6c60", "va.workflow.intopool");
        DICT_MAP.put("\u6d41\u7a0b\u7ed3\u675f", "va.workflow.endprocess");
        DICT_MAP.put("\u5171\u4eab\u590d\u6838\u5c97", "va.workflow.sharedreviewwork");
        DICT_MAP.put("\u540c\u610f", "va.workflow.agree");
        DICT_MAP.put("\u5ba1\u6279\u540c\u610f", "va.workflow.approval");
        DICT_MAP.put("\u5ba1\u6279\u9a73\u56de", "va.workflow.approvaltodismiss");
        DICT_MAP.put("\u5ba1\u6838\u901a\u8fc7", "va.workflow.approved");
        DICT_MAP.put("\u5f85\u5ba1\u6838", "va.workflow.toaudit");
        DICT_MAP.put("\u5171\u4eab\u9a73\u56de\u91cd\u8d70\u5de5\u4f5c\u6d41", "va.workflow.shareddismissed");
        DICT_MAP.put("\u5171\u4eab\u9a73\u56de\u4e0d\u91cd\u8d70", "va.workflow.sharenotretracerejection");
        DICT_MAP.put("\u5f85\u590d\u6838\u63d0\u53d6", "va.workflow.toreviewextraction");
        DICT_MAP.put("\u5f85\u590d\u6838", "va.workflow.toreview");
        DICT_MAP.put("\u590d\u6838\u4e0d\u901a\u8fc7", "va.workflow.reviewby");
        DICT_MAP.put("\u51fa\u6c60", "va.workflow.outofpoll");
        DICT_MAP.put("\u9a73\u56de", "va.workflow.reject");
        DICT_MAP.put("\u81ea\u52a8\u540c\u610f", "va.workflow.automicagree");
        DICT_MAP.put("\u53d6\u56de", "va.workflow.toretrieve");
        DICT_MAP.put("\u5df2\u5ba1\u6279", "va.workflow.havebeenapproval");
        DICT_MAP.put("\u5f85\u5ba1\u6279", "va.workflow.pending");
        DICT_MAP.put("\u7ed3\u675f", "va.workflow.end");
        DICT_MAP.put("\u5f00\u59cb", "va.workflow.start");
        DICT_MAP.put("\u9650\u671f\u81ea\u52a8\u5ba1\u6279\u540c\u610f", "va.workflow.deadlineautoapproval");
        DICT_MAP.put("\u9650\u671f\u81ea\u52a8\u5ba1\u6279\u9a73\u56de", "va.workflow.deadlineautoapprovaltodismiss");
    }
}

