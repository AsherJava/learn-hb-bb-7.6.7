/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.integritycheck.common.ErrorDesInfo
 *  com.jiuqi.nr.integritycheck.common.IntegrityCheckParam
 *  com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo
 *  com.jiuqi.nr.integritycheck.common.PageTableICRInfo
 *  com.jiuqi.nr.integritycheck.common.QueryICRParam
 *  com.jiuqi.nr.integritycheck.common.TableICRInfo
 *  com.jiuqi.nr.integritycheck.common.UUIDMerger
 *  com.jiuqi.nr.integritycheck.service.IIntegrityCheckService
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
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.integritycheck.adapter.provider;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.integritycheck.adapter.provider.IntegritycheckConfig;
import com.jiuqi.nr.integritycheck.common.ErrorDesInfo;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckParam;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo;
import com.jiuqi.nr.integritycheck.common.PageTableICRInfo;
import com.jiuqi.nr.integritycheck.common.QueryICRParam;
import com.jiuqi.nr.integritycheck.common.TableICRInfo;
import com.jiuqi.nr.integritycheck.common.UUIDMerger;
import com.jiuqi.nr.integritycheck.service.IIntegrityCheckService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class IntegritycheckProvider
implements IMultcheckItemProvider {
    private static final Logger logger = LoggerFactory.getLogger(IntegritycheckProvider.class);
    @Autowired
    private IIntegrityCheckService integrityCheckService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IEntityMetaService metaService;

    public String getType() {
        return "integritycheck";
    }

    public String getTitle() {
        return "\u8868\u5b8c\u6574\u6027\u68c0\u67e5";
    }

    public double getOrder() {
        return 2.0;
    }

    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        try {
            MultCheckItemDTO multCheckItemDTO = new MultCheckItemDTO();
            multCheckItemDTO.setTitle(this.getTitle());
            multCheckItemDTO.setType(this.getType());
            IntegritycheckConfig config = new IntegritycheckConfig();
            config.setSelectForms(new ArrayList<String>());
            multCheckItemDTO.setConfig(SerializeUtil.serializeToJson((Object)config));
            return multCheckItemDTO;
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u6a21\u578b\u521d\u59cb\u5316\u5f02\u5e38" + formSchemeKey, e);
            return null;
        }
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-datacheck-integritycheck-plugin", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        if (!StringUtils.hasText(item.getConfig())) {
            return "\u9009\u62e9\u62a5\u8868 | 0\u5f20";
        }
        try {
            IntegritycheckConfig config = (IntegritycheckConfig)SerializeUtil.deserializeFromJson((String)item.getConfig(), IntegritycheckConfig.class);
            if (CollectionUtils.isEmpty(config.getSelectForms())) {
                return "\u9009\u62e9\u62a5\u8868 | \u6240\u6709\u62a5\u8868";
            }
            return "\u9009\u62e9\u62a5\u8868 | <span class=\"mtc-item-number-cls\">" + config.getSelectForms().size() + "</span>\u5f20";
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u6a21\u578b\u5f02\u5e38" + item.getKey(), e);
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
        return new PluginInfo("@nr", "nr-datacheck-integritycheck-plugin", "Selector");
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        try {
            String batchId = UUIDMerger.merge((String)param.getRunId(), (String)param.getCheckItem().getKey());
            IntegritycheckConfig config = (IntegritycheckConfig)SerializeUtil.deserializeFromJson((String)param.getCheckItem().getConfig(), IntegritycheckConfig.class);
            IntegrityCheckResInfo integrityCheckResInfo = this.integrityCheck(param, batchId, config);
            if (null == integrityCheckResInfo) {
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
            return this.structureMulResult(batchId, param, config);
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u6a21\u578b\u5f02\u5e38" + param.getCheckItem().getKey(), e);
            return null;
        }
    }

    private IntegrityCheckResInfo integrityCheck(CheckItemParam param, String batchId, IntegritycheckConfig config) throws Exception {
        IntegrityCheckParam integrityCheckParam = new IntegrityCheckParam();
        integrityCheckParam.setBatchId(batchId);
        integrityCheckParam.setTaskKey(param.getContext().getTaskKey());
        integrityCheckParam.setDims(param.getContext().getDims());
        integrityCheckParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
        integrityCheckParam.setFormKeys(config.getSelectForms());
        return this.integrityCheckService.integrityCheck(integrityCheckParam, param.getAsyncTaskMonitor());
    }

    @NotNull
    private CheckItemResult structureMulResult(String batchId, CheckItemParam param, IntegritycheckConfig config) throws Exception {
        CheckItemResult result = new CheckItemResult();
        if (param.getAsyncTaskMonitor().isCancel()) {
            return result;
        }
        ArrayList<String> successWithExplainOrgs = new ArrayList<String>();
        HashMap<String, FailedOrgInfo> failedOrgs = new HashMap<String, FailedOrgInfo>();
        HashMap<String, Integer> failedOrgCounts = new HashMap<String, Integer>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getContext().getTaskKey());
        IEntityDefine entityDefine = this.metaService.queryEntity(taskDefine.getDw());
        String dwDimName = entityDefine.getDimensionName();
        HashSet<String> errdwValueFormKeyCatch = new HashSet<String>();
        QueryICRParam queryICRParam = new QueryICRParam();
        queryICRParam.setDimensionCollection(param.getContext().getDims());
        List<String> selectForms = config.getSelectForms();
        if (CollectionUtils.isEmpty(selectForms)) {
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(param.getContext().getFormSchemeKey());
            formDefines.removeIf(form -> form.getFormType() != FormType.FORM_TYPE_FIX && form.getFormType() != FormType.FORM_TYPE_FLOAT);
            selectForms = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        }
        queryICRParam.setFormKeys(selectForms);
        queryICRParam.setBatchId(batchId);
        queryICRParam.setTaskKey(param.getContext().getTaskKey());
        queryICRParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
        queryICRParam.setNeedZeroTable(true);
        queryICRParam.setNeedDiffTable(true);
        PageTableICRInfo pageTableICRInfo = this.integrityCheckService.pageQueryCheckResult(queryICRParam);
        for (TableICRInfo tableICRInfo : pageTableICRInfo.getTableICRInfos()) {
            String checkResult;
            String dwValue = (String)tableICRInfo.getDimNameValueMap().get(dwDimName);
            if (errdwValueFormKeyCatch.contains(dwValue + tableICRInfo.getFormKey()) || "".equals(checkResult = tableICRInfo.getResult()) || "--".equals(checkResult) || "\u96f6\u8868".equals(checkResult)) continue;
            ErrorDesInfo errorDesInfo = tableICRInfo.getErrorDesInfo();
            if (null == errorDesInfo || !StringUtils.hasText(errorDesInfo.getDescription())) {
                errdwValueFormKeyCatch.add(dwValue + tableICRInfo.getFormKey());
                if (!failedOrgs.containsKey(dwValue)) {
                    FailedOrgInfo failedOrgInfo = new FailedOrgInfo();
                    failedOrgInfo.setDesc("1\u5f20\u62a5\u8868\u68c0\u67e5\u4e0d\u901a\u8fc7\u5e76\u4e14\u6ca1\u6709\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e");
                    failedOrgs.put(dwValue, failedOrgInfo);
                    failedOrgCounts.put(dwValue, 1);
                    continue;
                }
                int count = (Integer)failedOrgCounts.get(dwValue) + 1;
                failedOrgCounts.put(dwValue, count);
                FailedOrgInfo failedOrgInfo = new FailedOrgInfo();
                failedOrgInfo.setDesc(count + "\u5f20\u62a5\u8868\u68c0\u67e5\u4e0d\u901a\u8fc7\u5e76\u4e14\u6ca1\u6709\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e");
                failedOrgs.put(dwValue, failedOrgInfo);
                continue;
            }
            successWithExplainOrgs.add(dwValue);
        }
        LinkedList successOrgs = new LinkedList(param.getContext().getOrgList());
        successOrgs.removeAll(successWithExplainOrgs);
        successOrgs.removeAll(failedOrgs.keySet());
        result.setRunId(param.getRunId());
        if (failedOrgs.isEmpty()) {
            if (!successWithExplainOrgs.isEmpty()) {
                result.setResult(CheckRestultState.SUCCESS_ERROR);
            } else {
                result.setResult(CheckRestultState.SUCCESS);
            }
        } else {
            result.setResult(CheckRestultState.FAIL);
        }
        result.setSuccessOrgs(successOrgs);
        result.setSuccessWithExplainOrgs(successWithExplainOrgs.stream().distinct().collect(Collectors.toList()));
        result.setFailedOrgs(failedOrgs);
        result.setRunConfig(SerializeUtil.serializeToJson((Object)config));
        return result;
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-datacheck-integritycheck-plugin", "Result");
    }
}

