/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FloatDimensionSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl.FetchSettingDesServiceImpl;
import com.jiuqi.gcreport.bde.fetchsetting.impl.web.FetchFloatSettingDesController;
import com.jiuqi.gcreport.bde.fetchsetting.impl.web.FetchSchemeController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchSettingLogHelperUtil {
    private static final String BDE_FETCHSETTING_MOUDLE_NAME = "BDE\u53d6\u6570\u8bbe\u7f6e";
    private static final String DELETE = "DELETE";
    private static final String CREATE = "CREATE";
    private static final String UPDATE = "UPDATE";
    private static final Logger log = LoggerFactory.getLogger(FetchSettingLogHelperUtil.class);
    private static final String FIX_FLOAT_ZB_MESSAGE_FORMAT = "%1$s-\u533a\u57df\u3010%2$s\u3011-\u6307\u6807:%3$s";
    private static final String FIX_ZB_MESSAGE_FORMAT = "%1$s-\u6307\u6807:%2$s";
    private static final String FLOAT_ROW_MESSAGE_FORMAT = "%1$s-\u6d6e\u52a8\u533a\u57df\u3010%2$s\u3011";
    private static final String FLOAT_CLOUMN_MESSAGE_FORMAT = "%1$s-\u6d6e\u52a8\u533a\u57df\u3010%2$s\u3011-\u6307\u6807:%3$s";

    public static void logFetchSettingSave(FetchSettingSaveDataVO fetchSettingSaveData) {
        try {
            log.debug("\u53d6\u6570\u8bbe\u7f6e-\u4fdd\u5b58{}", (Object)JsonUtils.writeValueAsString((Object)fetchSettingSaveData));
            String fetchSchemeId = fetchSettingSaveData.getFetchSchemeId();
            String formSchemeId = fetchSettingSaveData.getFormSchemeId();
            String formId = fetchSettingSaveData.getFormId();
            String fetchSettingMessage = FetchSettingLogHelperUtil.buildTaskMessage(formSchemeId, fetchSchemeId, formId);
            FetchSettingLogHelperUtil.logFetchFixSettingSave(fetchSettingSaveData, fetchSettingMessage);
            FetchSettingLogHelperUtil.logFetchFloatRowSave(fetchSettingSaveData, fetchSettingMessage);
        }
        catch (Exception e) {
            log.error("\u53d6\u6570\u8bbe\u7f6e-\u4fdd\u5b58\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    private static String buildTaskMessage(String formSchemeId, String fetchSchemeId, String formId) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        FetchSchemeController fetchSchemeController = (FetchSchemeController)ApplicationContextRegister.getBean(FetchSchemeController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
        FetchSchemeVO fetchSchemeVO = (FetchSchemeVO)fetchSchemeController.queryFetchSchemeById(fetchSchemeId).getData();
        String fetchSettingMessage = "";
        if (Objects.nonNull(formScheme)) {
            TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            if (StringUtils.isEmpty((String)formId)) {
                fetchSettingMessage = String.format("\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), fetchSchemeVO.getName());
            } else {
                FormDefine formDefine = runTimeViewController.queryFormById(formId);
                fetchSettingMessage = String.format("\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011-\u62a5\u8868\u3010%4$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), fetchSchemeVO.getName(), formDefine.getTitle() + "|" + formDefine.getFormCode());
            }
        }
        return fetchSettingMessage;
    }

    /*
     * Could not resolve type clashes
     */
    private static void logFetchFixSettingSave(FetchSettingSaveDataVO fetchSettingSaveData, String fetchSettingMessage) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        String formId = fetchSettingSaveData.getFormId();
        Map<String, Map<String, List<String>>> actionTypeLinkIdMap = FetchSettingLogHelperUtil.judgmentFetchFixSettingSaveType(fetchSettingSaveData);
        for (Map.Entry<String, Map<String, List<String>>> actionTypeRegionEntry : actionTypeLinkIdMap.entrySet()) {
            String actionType = actionTypeRegionEntry.getKey();
            Map<String, List<String>> regionLinkMap = actionTypeRegionEntry.getValue();
            String title = "";
            switch (actionType) {
                case "CREATE": {
                    title = "\u65b0\u589e\u53d6\u6570\u89c4\u5219-" + fetchSettingMessage;
                    break;
                }
                case "DELETE": {
                    title = "\u5220\u9664\u53d6\u6570\u89c4\u5219-" + fetchSettingMessage;
                    break;
                }
                case "UPDATE": {
                    title = "\u4fee\u6539\u53d6\u6570\u89c4\u5219-" + fetchSettingMessage;
                    break;
                }
                default: {
                    title = "";
                    log.error("\u6682\u672a\u8003\u8651\u5230\u7684\u53d6\u6570\u8bbe\u7f6e\u64cd\u4f5c\u7c7b\u578b{}", (Object)actionType);
                }
            }
            for (Map.Entry regionLinkIdsEntry : regionLinkMap.entrySet()) {
                String regionKey = (String)regionLinkIdsEntry.getKey();
                String linkExpListStr = FetchSettingLogHelperUtil.getLinkExpStrByLinkList((List)regionLinkIdsEntry.getValue());
                DataRegionDefine dataRegionDefine = runTimeViewController.queryDataRegionDefine(regionKey);
                boolean isFixArea = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind());
                String zbMessage = isFixArea ? String.format(FIX_ZB_MESSAGE_FORMAT, fetchSettingMessage, linkExpListStr) : String.format(FIX_FLOAT_ZB_MESSAGE_FORMAT, fetchSettingMessage, FetchSettingLogHelperUtil.getRegionCodeName(formId, (String)regionLinkIdsEntry.getKey()), linkExpListStr);
                LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)title, (String)zbMessage);
            }
        }
    }

    private static String getLinkExpStrByLinkList(List<String> linkIdList) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)ApplicationContextRegister.getBean(IRuntimeDataSchemeService.class);
        StringBuilder linkExpStr = new StringBuilder();
        for (String linkId : linkIdList) {
            DataField dataField;
            DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefine(linkId);
            if (Objects.isNull(dataLinkDefine) || Objects.isNull(dataField = runtimeDataSchemeService.getDataField(dataLinkDefine.getLinkExpression()))) continue;
            linkExpStr.append("\u3010").append(dataField.getTitle()).append("|").append(dataField.getCode()).append("\u3011");
        }
        return linkExpStr.toString();
    }

    private static Map<String, Map<String, List<String>>> judgmentFetchFixSettingSaveType(FetchSettingSaveDataVO fetchSettingSaveData) {
        FetchSettingDesServiceImpl fetchSettingDesService = (FetchSettingDesServiceImpl)ApplicationContextRegister.getBean(FetchSettingDesServiceImpl.class);
        LinkedHashMap fixedSettingDatas = fetchSettingSaveData.getFixedSettingDatas();
        String fetchSchemeId = fetchSettingSaveData.getFetchSchemeId();
        String formSchemeId = fetchSettingSaveData.getFormSchemeId();
        String formId = fetchSettingSaveData.getFormId();
        HashMap<String, Map<String, List<String>>> actionTypeLinkIds = new HashMap<String, Map<String, List<String>>>();
        for (String regionId : fixedSettingDatas.keySet()) {
            LinkedHashMap regionFixSetting = (LinkedHashMap)fixedSettingDatas.get(regionId);
            for (String linkId : regionFixSetting.keySet()) {
                FixedFieldDefineSettingDTO fixedFieldDefineSettingDTO = (FixedFieldDefineSettingDTO)regionFixSetting.get(linkId);
                FixedFieldDefineSettingDTO oldFixedFieldDefineSettingDTO = fetchSettingDesService.listDataLinkFixedSettingDesRowRecords(new FetchSettingCond(fetchSchemeId, formSchemeId, formId, regionId, linkId));
                if (Objects.isNull(oldFixedFieldDefineSettingDTO)) {
                    if (Objects.isNull(fixedFieldDefineSettingDTO) || Objects.isNull(fixedFieldDefineSettingDTO.getFixedSettingData())) continue;
                    FetchSettingLogHelperUtil.putActionTypeLinkMap(actionTypeLinkIds, regionId, linkId, CREATE);
                    continue;
                }
                if (Objects.isNull(fixedFieldDefineSettingDTO) || Objects.isNull(fixedFieldDefineSettingDTO.getFixedSettingData())) {
                    FetchSettingLogHelperUtil.putActionTypeLinkMap(actionTypeLinkIds, regionId, linkId, DELETE);
                    continue;
                }
                FetchSettingLogHelperUtil.putActionTypeLinkMap(actionTypeLinkIds, regionId, linkId, UPDATE);
            }
        }
        return actionTypeLinkIds;
    }

    private static void putActionTypeLinkMap(Map<String, Map<String, List<String>>> actionTypeLinkMap, String regionId, String linkId, String type) {
        if (actionTypeLinkMap.containsKey(type)) {
            if (!actionTypeLinkMap.get(type).containsKey(regionId)) {
                actionTypeLinkMap.get(type).put(regionId, new ArrayList());
            }
        } else {
            HashMap regionMap = new HashMap();
            regionMap.put(regionId, new ArrayList());
            actionTypeLinkMap.put(type, regionMap);
        }
        actionTypeLinkMap.get(type).get(regionId).add(linkId);
    }

    private static void logFetchFloatRowSave(FetchSettingSaveDataVO fetchSettingSaveData, String fetchSettingMessage) {
        FetchFloatSettingDesController floatSettingDesController = (FetchFloatSettingDesController)ApplicationContextRegister.getBean(FetchFloatSettingDesController.class);
        String fetchSchemeId = fetchSettingSaveData.getFetchSchemeId();
        String formSchemeId = fetchSettingSaveData.getFormSchemeId();
        String formId = fetchSettingSaveData.getFormId();
        Map fetchFloatSettingDatas = fetchSettingSaveData.getFetchFloatSettingDatas();
        for (String regionId : fetchFloatSettingDatas.keySet()) {
            String oldPluginData;
            FloatRegionConfigVO floatRegionConfigVO;
            FetchSettingCond fetchSettingCond = new FetchSettingCond(fetchSchemeId, formSchemeId, formId, regionId);
            FloatRegionConfigVO oldFloatRegionConfigVO = (FloatRegionConfigVO)floatSettingDesController.getFloatRegionSettingDes(fetchSettingCond).getData();
            if (Objects.isNull(oldFloatRegionConfigVO)) {
                FetchSettingLogHelperUtil.logFloatRowCreate(fetchSettingMessage, formId, regionId);
                floatRegionConfigVO = (FloatRegionConfigVO)fetchFloatSettingDatas.get(regionId);
                QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
                FetchSettingLogHelperUtil.logFloatCloumnCreate(fetchSettingMessage, formId, regionId, queryConfigInfo.getZbMapping());
                continue;
            }
            floatRegionConfigVO = (FloatRegionConfigVO)fetchFloatSettingDatas.get(regionId);
            String queryType = floatRegionConfigVO.getQueryType();
            QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
            String oldQueryType = oldFloatRegionConfigVO.getQueryType();
            QueryConfigInfo oldQueryConfigInfo = oldFloatRegionConfigVO.getQueryConfigInfo();
            if (!queryType.equals(oldQueryType)) {
                FetchSettingLogHelperUtil.logFloatRowUpdate(fetchSettingMessage, formId, regionId);
                FetchSettingLogHelperUtil.logFloatCloumnDelete(fetchSettingMessage, regionId, formId, oldQueryConfigInfo.getZbMapping());
                FetchSettingLogHelperUtil.logFloatCloumnCreate(fetchSettingMessage, regionId, formId, queryConfigInfo.getZbMapping());
                continue;
            }
            if (JsonUtils.writeValueAsString((Object)queryConfigInfo).equals(JsonUtils.writeValueAsString((Object)oldQueryConfigInfo))) continue;
            String pluginData = queryConfigInfo.getPluginData();
            if (!pluginData.equals(oldPluginData = oldQueryConfigInfo.getPluginData())) {
                FetchSettingLogHelperUtil.logFloatRowUpdate(fetchSettingMessage, formId, regionId);
            }
            List zbMapping = queryConfigInfo.getZbMapping();
            Map<String, FloatZbMappingVO> updateLinkIdZbMappingMap = zbMapping.stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, item -> item));
            List oldZbMapping = oldQueryConfigInfo.getZbMapping();
            Map<String, FloatZbMappingVO> oldLinkIdZbMappingMap = oldZbMapping.stream().collect(Collectors.toMap(FloatZbMappingVO::getDataLinkId, item -> item));
            ArrayList<FloatZbMappingVO> updateFloatZbMappingVOs = new ArrayList<FloatZbMappingVO>();
            ArrayList<FloatZbMappingVO> createFloatZbMappingVOs = new ArrayList<FloatZbMappingVO>();
            for (String linkId : updateLinkIdZbMappingMap.keySet()) {
                if (!oldLinkIdZbMappingMap.containsKey(linkId)) {
                    createFloatZbMappingVOs.add(updateLinkIdZbMappingMap.get(linkId));
                    oldLinkIdZbMappingMap.remove(linkId);
                    continue;
                }
                if (!updateLinkIdZbMappingMap.get(linkId).getQueryName().equals(oldLinkIdZbMappingMap.get(linkId).getQueryName())) {
                    updateFloatZbMappingVOs.add(updateLinkIdZbMappingMap.get(linkId));
                }
                oldLinkIdZbMappingMap.remove(linkId);
            }
            ArrayList<FloatZbMappingVO> deleteLinkIds = new ArrayList<FloatZbMappingVO>(oldLinkIdZbMappingMap.values());
            FetchSettingLogHelperUtil.logFloatCloumnUpdate(fetchSettingMessage, formId, regionId, updateFloatZbMappingVOs);
            FetchSettingLogHelperUtil.logFloatCloumnCreate(fetchSettingMessage, formId, regionId, createFloatZbMappingVOs);
            FetchSettingLogHelperUtil.logFloatCloumnDelete(fetchSettingMessage, formId, regionId, deleteLinkIds);
        }
    }

    private static void logFloatRowCreate(String fromMessage, String formId, String regionId) {
        LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)("\u65b0\u589e\u884c\u89c4\u5219-" + fromMessage), (String)String.format(FLOAT_ROW_MESSAGE_FORMAT, fromMessage, FetchSettingLogHelperUtil.getRegionCodeName(formId, regionId)));
    }

    private static void logFloatRowUpdate(String fromMessage, String formId, String regionId) {
        LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)("\u4fee\u6539\u884c\u89c4\u5219-" + fromMessage), (String)String.format(FLOAT_ROW_MESSAGE_FORMAT, fromMessage, FetchSettingLogHelperUtil.getRegionCodeName(formId, regionId)));
    }

    private static void logFloatRowDelete(String fromMessage, String formId, String regionId) {
        LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)("\u6e05\u9664\u884c\u89c4\u5219-" + fromMessage), (String)String.format(FLOAT_ROW_MESSAGE_FORMAT, fromMessage, FetchSettingLogHelperUtil.getRegionCodeName(formId, regionId)));
    }

    private static void logFloatCloumnCreate(String fromMessage, String formId, String regionId, List<FloatZbMappingVO> createFloatZbMappingVOs) {
        if (CollectionUtils.isEmpty(createFloatZbMappingVOs)) {
            return;
        }
        List<String> createLinkIds = createFloatZbMappingVOs.stream().filter(item -> !"=".equals(item.getQueryName())).map(FloatZbMappingVO::getDataLinkId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(createLinkIds)) {
            LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)("\u65b0\u589e\u5217\u89c4\u5219-" + fromMessage), (String)String.format(FLOAT_CLOUMN_MESSAGE_FORMAT, fromMessage, FetchSettingLogHelperUtil.getRegionCodeName(formId, regionId), FetchSettingLogHelperUtil.getLinkExpStrByLinkList(createLinkIds)));
        }
    }

    private static void logFloatCloumnUpdate(String fromMessage, String formId, String regionId, List<FloatZbMappingVO> updateFloatZbMappingVOs) {
        if (CollectionUtils.isEmpty(updateFloatZbMappingVOs)) {
            return;
        }
        List<String> updateLinkIds = updateFloatZbMappingVOs.stream().filter(item -> !"=".equals(item.getQueryName())).map(FloatZbMappingVO::getDataLinkId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(updateLinkIds)) {
            LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)("\u4fee\u6539\u5217\u89c4\u5219-" + fromMessage), (String)String.format(FLOAT_CLOUMN_MESSAGE_FORMAT, fromMessage, FetchSettingLogHelperUtil.getRegionCodeName(formId, regionId), FetchSettingLogHelperUtil.getLinkExpStrByLinkList(updateLinkIds)));
        }
    }

    private static void logFloatCloumnDelete(String fromMessage, String formId, String regionId, List<FloatZbMappingVO> deleteFloatZbMappingVOs) {
        if (CollectionUtils.isEmpty(deleteFloatZbMappingVOs)) {
            return;
        }
        List<String> deleteLinkIds = deleteFloatZbMappingVOs.stream().map(FloatZbMappingVO::getDataLinkId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteLinkIds)) {
            LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)("\u5220\u9664\u5217\u89c4\u5219-" + fromMessage), (String)String.format(FLOAT_CLOUMN_MESSAGE_FORMAT, fromMessage, FetchSettingLogHelperUtil.getRegionCodeName(formId, regionId), FetchSettingLogHelperUtil.getLinkExpStrByLinkList(deleteLinkIds)));
        }
    }

    public static void logCleanFloatSetting(FetchSettingCond fetchSettingCond) {
        try {
            log.debug("BDE\u53d6\u6570\u8bbe\u7f6e-\u6e05\u9664\u6d6e\u52a8\u884c\u8bbe\u7f6e{}", (Object)JsonUtils.writeValueAsString((Object)fetchSettingCond));
            String fetchSchemeId = fetchSettingCond.getFetchSchemeId();
            String formSchemeId = fetchSettingCond.getFormSchemeId();
            String formId = fetchSettingCond.getFormId();
            String fetchSettingMessage = FetchSettingLogHelperUtil.buildTaskMessage(formSchemeId, fetchSchemeId, formId);
            FetchSettingLogHelperUtil.logFloatRowDelete(fetchSettingMessage, fetchSettingCond.getFormId(), fetchSettingCond.getRegionId());
        }
        catch (Exception e) {
            log.error("BDE\u53d6\u6570\u8bbe\u7f6e-\u6e05\u9664\u6d6e\u52a8\u884c\u8bbe\u7f6e\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    public static void logSaveFloatDimensionSetting(FloatDimensionSettingDTO floatDimensionSettingDTO) {
        try {
            log.debug("BDE-BDE\u53d6\u6570\u8bbe\u7f6e-\u7ef4\u5ea6\u8bbe\u7f6e{}", (Object)JsonUtils.writeValueAsString((Object)floatDimensionSettingDTO));
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
            String formSchemeId = floatDimensionSettingDTO.getFormSchemeId();
            String formId = floatDimensionSettingDTO.getFormId();
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
            FormDefine formDefine = runTimeViewController.queryFormById(formId);
            String fetchSettingMessage = "";
            if (Objects.nonNull(formScheme)) {
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                fetchSettingMessage = String.format("\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u62a5\u8868\u3010%3$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), formDefine.getTitle() + "|" + formDefine.getFormCode());
            }
            LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)"\u7ef4\u5ea6\u8bbe\u7f6e", (String)String.format("%1$s-\u533a\u57df\u3010%2$s\u3011", fetchSettingMessage, FetchSettingLogHelperUtil.getRegionCodeName(floatDimensionSettingDTO.getFormId(), floatDimensionSettingDTO.getRegionId())));
        }
        catch (Exception e) {
            log.error("BDE-BDE\u53d6\u6570\u8bbe\u7f6e-\u7ef4\u5ea6\u8bbe\u7f6e\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    public static void logFetchSettingImport(String formSchemeId, String fetchSchemeId, List<String> importFromIds) {
        try {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
            FetchSchemeController fetchSchemeController = (FetchSchemeController)ApplicationContextRegister.getBean(FetchSchemeController.class);
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
            FetchSchemeVO fetchSchemeVO = (FetchSchemeVO)fetchSchemeController.queryFetchSchemeById(fetchSchemeId).getData();
            String fetchSettingMessage = "";
            StringBuilder importFromStr = new StringBuilder();
            for (String importFromId : importFromIds) {
                FormDefine formDefine = runTimeViewController.queryFormById(importFromId);
                importFromStr.append(String.format("\u3010%1$s|%2$s\u3011", formDefine.getTitle(), formDefine.getFormCode()));
            }
            if (Objects.nonNull(formScheme)) {
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                fetchSettingMessage = String.format("\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), fetchSchemeVO.getName());
            }
            LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)"\u5bfc\u5165\u53d6\u6570\u89c4\u5219", (String)String.format("%1$s-\u62a5\u8868\uff1a%2$s", fetchSettingMessage, importFromStr));
        }
        catch (Exception e) {
            log.error("BDE-BDE\u53d6\u6570\u8bbe\u7f6e-\u5bfc\u5165\u53d6\u6570\u89c4\u5219\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    public static void logFetchSettingExport() {
        LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)"\u5bfc\u51fa\u53d6\u6570\u89c4\u5219", (String)"");
    }

    public static void logFetchSettingPublish(FetchSettingCond fetchSettingCond) {
        try {
            log.debug("BDE\u53d6\u6570\u8bbe\u7f6e-\u53d1\u5e03{}", (Object)JsonUtils.writeValueAsString((Object)fetchSettingCond));
            String fetchSchemeId = fetchSettingCond.getFetchSchemeId();
            String formSchemeId = fetchSettingCond.getFormSchemeId();
            String formId = fetchSettingCond.getFormId();
            String fetchSettingMessage = FetchSettingLogHelperUtil.buildTaskMessage(formSchemeId, fetchSchemeId, formId);
            if (StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
                LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)"\u53d1\u5e03\u53d6\u6570\u89c4\u5219-\u53d6\u6570\u65b9\u6848", (String)fetchSettingMessage);
            } else {
                LogHelper.info((String)BDE_FETCHSETTING_MOUDLE_NAME, (String)"\u53d1\u5e03\u53d6\u6570\u89c4\u5219-\u5355\u8868", (String)fetchSettingMessage);
            }
        }
        catch (Exception e) {
            log.error("BDE\u53d6\u6570\u8bbe\u7f6e-\u53d1\u5e03\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    private static String getRegionCodeName(String formId, String regionId) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
        List allRegionsInForm = runTimeViewController.getAllRegionsInForm(formId);
        List regions = allRegionsInForm.stream().filter(item -> item.getKey().equals(regionId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(regions)) {
            return regionId;
        }
        DataRegionDefine dataRegionDefine = (DataRegionDefine)regions.get(0);
        return String.format("%1$s|%2$s", dataRegionDefine.getTitle(), FetchSettingNrUtil.getRegionTopStr(dataRegionDefine.getRegionTop()));
    }
}

