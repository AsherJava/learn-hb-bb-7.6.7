/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.enumcheck.common.EnumCheckResInfo
 *  com.jiuqi.nr.enumcheck.common.EnumDataCheckParam
 *  com.jiuqi.nr.enumcheck.service.IEnumCheckService
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  com.jiuqi.nr.integritycheck.common.UUIDMerger
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.common.SerializeUtil
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.provider.PluginInfo
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.enumcheck.adapter.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.enumcheck.adapter.message.ResultInfo;
import com.jiuqi.nr.enumcheck.adapter.provider.EnumConfig;
import com.jiuqi.nr.enumcheck.common.EnumCheckResInfo;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckParam;
import com.jiuqi.nr.enumcheck.service.IEnumCheckService;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.integritycheck.common.UUIDMerger;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class EnumCheckProvider
implements IMultcheckItemProvider {
    private static final Logger logger = LoggerFactory.getLogger(EnumCheckProvider.class);
    @Autowired
    private IEnumCheckService enumCheckService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private MultCheckResDao multCheckResDao;

    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        try {
            MultCheckItemDTO multCheckItemDTO = new MultCheckItemDTO();
            multCheckItemDTO.setTitle(this.getTitle());
            multCheckItemDTO.setType(this.getType());
            EnumConfig config = new EnumConfig();
            config.setFilterFormula("");
            config.setSelectEnums(new ArrayList<String>());
            multCheckItemDTO.setConfig(SerializeUtil.serializeToJson((Object)config));
            return multCheckItemDTO;
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u6a21\u578b\u521d\u59cb\u5316\u5f02\u5e38" + formSchemeKey, e);
            return null;
        }
    }

    public String getType() {
        return "enumcheck";
    }

    public String getTitle() {
        return "\u679a\u4e3e\u5b57\u5178\u68c0\u67e5";
    }

    public double getOrder() {
        return 1.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-datacheck-enumcheck-plugin", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        if (!StringUtils.hasText(item.getConfig())) {
            return "\u9009\u62e9\u679a\u4e3e | 0\u4e2a";
        }
        try {
            EnumConfig config = (EnumConfig)SerializeUtil.deserializeFromJson((String)item.getConfig(), EnumConfig.class);
            if (CollectionUtils.isEmpty(config.getSelectEnums())) {
                return "\u9009\u62e9\u679a\u4e3e | \u6240\u6709\u679a\u4e3e";
            }
            return "\u9009\u62e9\u679a\u4e3e | <span class=\"mtc-item-number-cls\">" + config.getSelectEnums().size() + "</span>\u4e2a";
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u6a21\u578b\u5f02\u5e38" + item.getKey(), e);
            return null;
        }
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO dto = new MultCheckItemDTO();
        BeanUtils.copyProperties(sourceItem, dto);
        return dto;
    }

    public boolean canChangeConfig() {
        return true;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.getItemDescribe(formSchemeKey, item);
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-datacheck-enumcheck-plugin", "Selector");
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        try {
            EnumConfig config = (EnumConfig)SerializeUtil.deserializeFromJson((String)param.getCheckItem().getConfig(), EnumConfig.class);
            String batchId = UUIDMerger.merge((String)param.getRunId(), (String)param.getCheckItem().getKey());
            EnumCheckResInfo enumCheckResInfo = this.enumDataCheck(batchId, param, config);
            if (null == enumCheckResInfo) {
                CheckItemResult result = new CheckItemResult();
                result.setRunId(param.getRunId());
                result.setResult(CheckRestultState.SUCCESS);
                result.setSuccessOrgs(param.getContext().getOrgList());
                result.setRunConfig(SerializeUtil.serializeToJson((Object)config));
                return result;
            }
            if (param.getAsyncTaskMonitor().isCancel()) {
                return null;
            }
            this.saveSummaryResult(batchId, param, enumCheckResInfo);
            return this.structureMulResult(param, config, enumCheckResInfo);
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u6a21\u578b\u5f02\u5e38" + param.getCheckItem().getKey(), e);
            return null;
        }
    }

    @NotNull
    private CheckItemResult structureMulResult(CheckItemParam param, EnumConfig config, EnumCheckResInfo enumCheckResInfo) throws Exception {
        HashMap<String, FailedOrgInfo> failedOrgs = new HashMap<String, FailedOrgInfo>();
        HashMap<String, Integer> failedOrgCounts = new HashMap<String, Integer>();
        for (String errorEntityKey : enumCheckResInfo.getErrorEntityKeys()) {
            if (!failedOrgs.containsKey(errorEntityKey)) {
                FailedOrgInfo failedOrgInfo = new FailedOrgInfo();
                failedOrgInfo.setDesc("1\u4e2a\u679a\u4e3e\u68c0\u67e5\u4e0d\u901a\u8fc7");
                failedOrgs.put(errorEntityKey, failedOrgInfo);
                failedOrgCounts.put(errorEntityKey, 1);
                continue;
            }
            int count = (Integer)failedOrgCounts.get(errorEntityKey) + 1;
            failedOrgCounts.put(errorEntityKey, count);
            FailedOrgInfo failedOrgInfo = new FailedOrgInfo();
            failedOrgInfo.setDesc(count + "\u4e2a\u679a\u4e3e\u68c0\u67e5\u4e0d\u901a\u8fc7");
            failedOrgs.put(errorEntityKey, failedOrgInfo);
        }
        List allOrgList = param.getContext().getOrgList();
        ArrayList successOrgs = new ArrayList(allOrgList);
        successOrgs.removeAll(failedOrgs.keySet());
        CheckItemResult result = new CheckItemResult();
        result.setRunId(param.getRunId());
        if (failedOrgs.isEmpty()) {
            result.setResult(CheckRestultState.SUCCESS);
        } else {
            result.setResult(CheckRestultState.FAIL);
        }
        result.setSuccessOrgs(successOrgs);
        result.setFailedOrgs(failedOrgs);
        result.setRunConfig(SerializeUtil.serializeToJson((Object)config));
        return result;
    }

    @NotNull
    private void saveSummaryResult(String asyncTaskId, CheckItemParam param, EnumCheckResInfo enumCheckResInfo) throws JsonProcessingException {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
        StringBuilder resultDesc = new StringBuilder();
        resultDesc.append("\u5355\u4f4d\u5171").append(enumCheckResInfo.getSelEntityCount()).append("\u6237\uff0c");
        resultDesc.append("\u4e0d\u901a\u8fc7").append(enumCheckResInfo.getSaveInfo().getErrorUnitCount()).append("\u6237\uff0c");
        resultDesc.append("\u679a\u4e3e\u9879\u5171").append(enumCheckResInfo.getSelEnumDicCount()).append("\u4e2a\uff0c");
        resultDesc.append("\u4e0d\u901a\u8fc7").append(enumCheckResInfo.getSaveInfo().getErrorEnumCount()).append("\u4e2a");
        resultInfo.setCheckResultDesc(resultDesc.toString());
        resultInfo.setCheckStatus(0 == enumCheckResInfo.getCheckResCount());
        resultInfo.setAsyncTaskId(asyncTaskId);
        resultInfo.setEnumCheckResInfo(enumCheckResInfo);
        resultInfo.setEnumCheckResultSaveInfo(enumCheckResInfo.getSaveInfo());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date();
        resultInfo.setCheckEndTime(formatter.format(date));
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getContext().getTaskKey());
        resultInfo.setEntityId(taskDefine.getDw());
        resultInfo.setDimensionSet(enumCheckResInfo.getDimensionSet());
        resultInfo.setDimRange(enumCheckResInfo.getDimRange());
        resultInfo.setDimNameTitleMap(enumCheckResInfo.getDimNameTitleMap());
        resultInfo.setDimNameIsShowMap(enumCheckResInfo.getDimNameIsShowMap());
        ObjectMapper objectMapper = new ObjectMapper();
        this.multCheckResDao.insert(asyncTaskId, objectMapper.writeValueAsString((Object)resultInfo));
    }

    private EnumCheckResInfo enumDataCheck(String batchId, CheckItemParam param, EnumConfig config) {
        List<String> selectEnums = config.getSelectEnums();
        ArrayList<String> enums = new ArrayList<String>();
        for (String selectEnum : selectEnums) {
            int i = selectEnum.indexOf("\u3010");
            int j = selectEnum.indexOf("\u3011");
            enums.add(selectEnum.substring(i + 1, j));
        }
        EnumDataCheckParam enumDataCheckParam = new EnumDataCheckParam();
        enumDataCheckParam.setBatchId(batchId);
        enumDataCheckParam.setTaskKey(param.getContext().getTaskKey());
        enumDataCheckParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
        enumDataCheckParam.setDims(param.getContext().getDims());
        enumDataCheckParam.setEnumNames(enums);
        enumDataCheckParam.setIgnoreBlank(false);
        enumDataCheckParam.setFilterFormula(config.getFilterFormula());
        return this.enumCheckService.enumDataCheck(enumDataCheckParam, param.getAsyncTaskMonitor());
    }

    public void cleanCheckItemTables(Date date) {
        this.multCheckResDao.deleteByCreatedAt(date);
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-datacheck-enumcheck-plugin", "Result");
    }

    public String getEntryView() {
        return "enumCheckInfoView2";
    }
}

