/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.controller;

import com.google.gson.Gson;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.IMultCheckItemBase;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckConfigBean;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckEnum;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckItemImpl;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity.MultCheckTableBean;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity.MultCheckTableData;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.service.IMultCheckTableService;
import com.jiuqi.nr.finalaccountsaudit.multcheck.service.rest.MultCheckConfigService;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/multcheck"})
public class MultCheckTableController {
    private static final Logger log = LoggerFactory.getLogger(MultCheckTableController.class);
    Gson gson = new Gson();
    @Resource
    private IRunTimeViewController runtimeView;
    @Autowired
    MultCheckConfigService multCheckServiceAPI;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IMultCheckTableService iMultCheckTableService;
    @Autowired
    ResourceTreeNodeService resourceTreeNodeService;

    @PostMapping(value={"/insert"})
    @ResponseBody
    public String insertData(@RequestBody String insertStr) {
        MultCheckTableData multCheckTableData = (MultCheckTableData)this.gson.fromJson(insertStr, MultCheckTableData.class);
        String taskkey = multCheckTableData.getTaskkey();
        String formSchemeKey = multCheckTableData.getFormSchemeKey();
        List<MultCheckTableBean.MultCheckTableDataItem> itemList = this.getMultCheckTableDataItemList(taskkey, formSchemeKey);
        MultCheckTableBean multCheckTableBean = new MultCheckTableBean();
        multCheckTableBean.setS_key(multCheckTableData.getS_key() == null ? UUID.randomUUID().toString() : multCheckTableData.getS_key());
        multCheckTableBean.setS_name(multCheckTableData.getS_name());
        multCheckTableBean.setS_order(multCheckTableData.getS_order());
        multCheckTableBean.setS_dw(multCheckTableData.getS_dw());
        multCheckTableBean.setMultCheckTableDataItem(itemList);
        multCheckTableBean.setS_taskkey(taskkey);
        multCheckTableBean.setS_formschemekey(formSchemeKey);
        int num = 0;
        try {
            num = this.iMultCheckTableService.insertData(multCheckTableBean);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
        if (num == 1) {
            resultMap.put("success", true);
        } else {
            resultMap.put("fail", false);
        }
        String resultStr = this.gson.toJson(resultMap);
        return resultStr;
    }

    @GetMapping(value={"/delete"})
    @ResponseBody
    public String deleteData(@RequestParam String key) {
        int num = 0;
        try {
            num = this.iMultCheckTableService.deleteData(key);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
        if (num == 1) {
            resultMap.put("success", true);
        } else {
            resultMap.put("fail", false);
        }
        String resultStr = this.gson.toJson(resultMap);
        return resultStr;
    }

    @PostMapping(value={"/update"})
    @ResponseBody
    public String updateData(@RequestBody String updateStr) {
        int num = 0;
        try {
            num = this.iMultCheckTableService.updateData(updateStr);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
        if (num == 1) {
            resultMap.put("success", true);
        } else {
            resultMap.put("fail", false);
        }
        String resultStr = this.gson.toJson(resultMap);
        return resultStr;
    }

    public void tabaleUpdateOperation(MultCheckConfigBean multCheckConfigBean, String taskkey, String schemaKey) {
        boolean multCheckEnable = multCheckConfigBean.getMultCheckEnable();
        HashMap map = multCheckConfigBean.getMultCheckMap();
        try {
            if (multCheckEnable) {
                List<String> checkItemList = this.iMultCheckTableService.getCheckItemList();
                boolean bl = this.iMultCheckTableService.deleteCheckResult(taskkey, schemaKey, checkItemList);
            }
            this.iMultCheckTableService.tabaleUpdateOperation(map);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(value={"/select"})
    @ResponseBody
    public MultCheckTableBean selectData(@RequestParam String key) {
        MultCheckTableBean multCheckTableBean = null;
        try {
            multCheckTableBean = this.iMultCheckTableService.selectData(key);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return multCheckTableBean;
    }

    @GetMapping(value={"/selectSchemeList"})
    @ResponseBody
    public List selectSchemeList(@RequestParam String formschemeKey) {
        List<Object> schemeList = new ArrayList();
        try {
            schemeList = this.iMultCheckTableService.getSchemeList(formschemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return schemeList;
    }

    public List<MultCheckTableBean.MultCheckTableDataItem> getMultCheckTableDataItemList(String taskkey, String formSchemeKey) {
        ArrayList<MultCheckTableBean.MultCheckTableDataItem> list = new ArrayList<MultCheckTableBean.MultCheckTableDataItem>();
        try {
            List<IMultCheckItemBase> items = this.multCheckServiceAPI.getMultCheckList(formSchemeKey);
            int index = 0;
            for (int i = 0; i < items.size(); ++i) {
                FormulaSchemeDefine formulaSchemeDefine;
                IMultCheckItemBase item = items.get(i);
                if (item.getChildren() == null) {
                    list.add(this.getMultCheckTableDataItem(index, taskkey, formSchemeKey, item));
                    ++index;
                    continue;
                }
                if (MultCheckEnum.GSSH.getKey() != item.getId() || (formulaSchemeDefine = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey)) == null) continue;
                MultCheckItemImpl childrenItem = new MultCheckItemImpl(formulaSchemeDefine.getKey(), MultCheckEnum.GSSH.getKey(), formulaSchemeDefine.getTitle());
                list.add(this.getMultCheckTableDataItem(index, taskkey, formSchemeKey, childrenItem));
                ++index;
            }
            if (items.size() == 0) {
                return null;
            }
        }
        catch (Exception e) {
            log.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return list;
    }

    public MultCheckTableBean.MultCheckTableDataItem getMultCheckTableDataItem(int index, String taskkey, String formSchemeKey, IMultCheckItemBase item) {
        MultCheckTableBean.MultCheckTableDataItem<Object> multCheckTableDataItem = new MultCheckTableBean.MultCheckTableDataItem<Object>();
        multCheckTableDataItem.setIndex(index);
        multCheckTableDataItem.setKey(UUID.randomUUID().toString());
        multCheckTableDataItem.setMultCheckKey(item.getId());
        multCheckTableDataItem.setMultCheckType(item.getType());
        multCheckTableDataItem.setMultCheckName(item.getLabel());
        Object auditScope = this.getInitauditScopeObj(item.getType(), taskkey, formSchemeKey);
        multCheckTableDataItem.setAuditScope(auditScope);
        return multCheckTableDataItem;
    }

    private Object getInitauditScopeObj(String multCheckType, String taskkey, String formSchemeKey) {
        Object auditScopeObj = null;
        MultCheckEnum multCheckEnum = MultCheckEnum.findByKey(multCheckType);
        switch (multCheckEnum) {
            case GSSH: {
                auditScopeObj = this.getAuditScopeFormula();
                break;
            }
            case ENTITY_CHECK: {
                auditScopeObj = this.getAuditScopeEntityCheck(taskkey, formSchemeKey);
                break;
            }
            case ENUM_CHECK: {
                auditScopeObj = this.getAuditScopeEnum();
                break;
            }
            case ERROR_DESC_CHECK: {
                FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
                auditScopeObj = this.getAuditScopeErrorDesc(formulaSchemeDefine.getKey(), true);
                break;
            }
            case ENTITY_TREE_CHECK: {
                auditScopeObj = "null";
                break;
            }
            case ATTACHMENT_CHECK: {
                auditScopeObj = this.getAuditScopeAttachment();
                break;
            }
            case INTEGRITY_FORM: {
                auditScopeObj = this.getAuditScopeForm();
                break;
            }
            case NODE_CHECK: {
                auditScopeObj = this.getAuditScopeNodeCheck(0, false);
                break;
            }
            default: {
                auditScopeObj = "null";
            }
        }
        return auditScopeObj;
    }

    public MultCheckTableBean.AuditScopeFormula getAuditScopeFormula() {
        MultCheckTableBean.AuditScopeFormula auditScopeFormula = new MultCheckTableBean.AuditScopeFormula();
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        auditScopeFormula.setFormulaMap(map);
        return auditScopeFormula;
    }

    public MultCheckTableBean.AuditScopeEnum getAuditScopeEnum() {
        MultCheckTableBean.AuditScopeEnum auditScopeEnum = new MultCheckTableBean.AuditScopeEnum();
        HashSet<String> set = new HashSet<String>();
        auditScopeEnum.setEnumList(set);
        return auditScopeEnum;
    }

    public MultCheckTableBean.AuditScopeErrorDesc getAuditScopeErrorDesc(String key, boolean impactReport) {
        MultCheckTableBean.AuditScopeErrorDesc auditScopeErrorDesc = new MultCheckTableBean.AuditScopeErrorDesc();
        ArrayList<String> list = new ArrayList<String>();
        list.add(key);
        auditScopeErrorDesc.setFormulaSolution(list);
        auditScopeErrorDesc.setImpactReport(impactReport);
        return auditScopeErrorDesc;
    }

    public MultCheckTableBean.AuditScopeAttachment getAuditScopeAttachment() {
        MultCheckTableBean.AuditScopeAttachment auditScopeAttachment = new MultCheckTableBean.AuditScopeAttachment();
        HashSet<MultCheckTableBean.ZBInfo> set = new HashSet<MultCheckTableBean.ZBInfo>();
        auditScopeAttachment.setZbList(set);
        return auditScopeAttachment;
    }

    public MultCheckTableBean.AuditScopeForm getAuditScopeForm() {
        MultCheckTableBean.AuditScopeForm auditScopeForm = new MultCheckTableBean.AuditScopeForm();
        HashSet<MultCheckTableBean.FormInfo> set = new HashSet<MultCheckTableBean.FormInfo>();
        auditScopeForm.setFormList(set);
        return auditScopeForm;
    }

    public MultCheckTableBean.AuditScopeNodeCheck getAuditScopeNodeCheck(int errorRange, boolean tierCheck) {
        MultCheckTableBean.AuditScopeNodeCheck auditScopeNodeCheck = new MultCheckTableBean.AuditScopeNodeCheck();
        HashSet<MultCheckTableBean.FormInfo> set = new HashSet<MultCheckTableBean.FormInfo>();
        auditScopeNodeCheck.setFormList(set);
        auditScopeNodeCheck.setTierCheck(tierCheck);
        auditScopeNodeCheck.setErrorRange(errorRange);
        return auditScopeNodeCheck;
    }

    public MultCheckTableBean.AuditScopeEntityCheck getAuditScopeEntityCheck(String taskkey, String formSchemeKey) {
        MultCheckTableBean.AuditScopeEntityCheck auditScopeEntityCheck = null;
        String lastIssue = "";
        String nextIssue = "";
        List taskLinks = this.runtimeView.queryLinksByCurrentFormScheme(formSchemeKey);
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
        String period = PeriodUtil.currentPeriod((int)formSchemeDefine.getPeriodType().type()).toString();
        switch (formSchemeDefine.getPeriodType().type()) {
            case 1: {
                lastIssue = "-1N";
                nextIssue = "+1N";
                break;
            }
            case 2: {
                lastIssue = "-1H";
                nextIssue = "+1H";
                break;
            }
            case 3: {
                lastIssue = "-1J";
                nextIssue = "+1J";
                break;
            }
            case 4: {
                lastIssue = "-1Y";
                nextIssue = "+1Y";
                break;
            }
            case 5: {
                lastIssue = "-1X";
                nextIssue = "+1X";
                break;
            }
            case 6: {
                lastIssue = "-1R";
                nextIssue = "+1R";
                break;
            }
            case 7: {
                lastIssue = "-1Z";
                nextIssue = "+1Z";
            }
        }
        if (taskLinks.size() > 0) {
            TaskLinkDefine taskLink = (TaskLinkDefine)taskLinks.get(0);
            String contrastPeriod = "";
            PeriodMatchingType periodMatchType = taskLink.getConfiguration();
            DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
            PeriodWrapper periodWrapper = new PeriodWrapper(period);
            if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_CURRENT) {
                contrastPeriod = period;
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_NEXT) {
                periodAdapter.nextPeriod(periodWrapper);
                contrastPeriod = periodWrapper.toString();
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_OFFSET) {
                periodAdapter.modify(periodWrapper, PeriodModifier.parse((String)taskLink.getPeriodOffset()));
                contrastPeriod = periodWrapper.toString();
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_PREVIOUS) {
                periodAdapter.priorPeriod(periodWrapper);
                contrastPeriod = periodWrapper.toString();
            } else if (periodMatchType == PeriodMatchingType.PERIOD_TYPE_SPECIFIED) {
                contrastPeriod = taskLink.getSpecified();
            }
            if (taskLink.getRelatedTaskKey() != null && taskLink.getRelatedFormSchemeKey() != null && !taskLink.getRelatedTaskKey().isEmpty() && !taskLink.getRelatedFormSchemeKey().isEmpty()) {
                auditScopeEntityCheck = new MultCheckTableBean.AuditScopeEntityCheck();
                this.runtimeView.queryTaskDefine(taskLink.getRelatedTaskKey());
                auditScopeEntityCheck.setFormSchemeKey(taskLink.getRelatedFormSchemeKey());
                auditScopeEntityCheck.setTaskTitle(this.runtimeView.queryTaskDefine(taskLink.getRelatedTaskKey()) != null ? this.runtimeView.queryTaskDefine(taskLink.getRelatedTaskKey()).getTitle() : "");
                auditScopeEntityCheck.setFormSchemeTitle(this.runtimeView.getFormScheme(taskLink.getRelatedFormSchemeKey()) != null ? this.runtimeView.getFormScheme(taskLink.getRelatedFormSchemeKey()).getTitle() : "");
                auditScopeEntityCheck.setDatatime(this.getDimensionValue("DATATIME", contrastPeriod, 1));
                auditScopeEntityCheck.setAssociation(this.getAssociation(1, period, lastIssue, nextIssue, ""));
            }
        }
        if (auditScopeEntityCheck == null) {
            auditScopeEntityCheck = new MultCheckTableBean.AuditScopeEntityCheck();
            auditScopeEntityCheck.setTaskKey(taskkey);
            auditScopeEntityCheck.setFormSchemeKey(formSchemeKey);
            auditScopeEntityCheck.setTaskTitle(this.runtimeView.queryTaskDefine(taskkey) != null ? this.runtimeView.queryTaskDefine(taskkey).getTitle() : "");
            auditScopeEntityCheck.setFormSchemeTitle(this.runtimeView.getFormScheme(formSchemeKey) != null ? this.runtimeView.getFormScheme(formSchemeKey).getTitle() : "");
            auditScopeEntityCheck.setDatatime(this.getDimensionValue("DATATIME", period, 1));
            auditScopeEntityCheck.setAssociation(this.getAssociation(1, period, lastIssue, nextIssue, ""));
        }
        return auditScopeEntityCheck;
    }

    public DimensionValue getDimensionValue(String name, String value, int type) {
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName(name);
        dimensionValue.setValue(value);
        dimensionValue.setType(type);
        return dimensionValue;
    }

    public MultCheckTableBean.Association getAssociation(int configuration, String specified, String lastIssue, String nextIssue, String periodOffset) {
        MultCheckTableBean.Association association = new MultCheckTableBean.Association();
        association.setConfiguration(configuration);
        association.setSpecified(specified);
        association.setLastIssue(lastIssue);
        association.setNextIssue(nextIssue);
        association.setPeriodOffset(periodOffset);
        return association;
    }
}

