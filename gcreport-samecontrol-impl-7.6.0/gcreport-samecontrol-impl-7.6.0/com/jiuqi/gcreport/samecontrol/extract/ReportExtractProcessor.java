/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractTableZbSetting
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.samecontrol.extract;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgSettingZbAttrDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingZbAttrEO;
import com.jiuqi.gcreport.samecontrol.enums.ChangedOrgTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractLogService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractTableZbSetting;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class ReportExtractProcessor {
    private SameCtrlChgEnvContextImpl sameCtrlChgEnvContext;
    private List<SameCtrlExtractTableZbSetting> tableZbSetting;
    private IRunTimeViewController iRunTimeViewController;
    private SameCtrlChgOrgDao sameCtrlChgOrgDao;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private SameCtrlChgSettingZbAttrDao sameCtrlChgSettingZbAttrDao;
    private IFormulaRunTimeController formulaRunTimeController;
    private SameCtrlExtractLogService sameCtrlExtractLogService;
    private FormulaSchemeConfigService formulaSchemeConfigService;
    private DataModelService dataModelService;
    private ConsolidatedOptionService consolidatedOptionService;
    private ConsolidatedTaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(ReportExtractProcessor.class);

    public static ReportExtractProcessor newInstance(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        return new ReportExtractProcessor(sameCtrlChgEnvContext);
    }

    private ReportExtractProcessor(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        this.sameCtrlChgEnvContext = sameCtrlChgEnvContext;
        this.iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        this.sameCtrlChgOrgDao = (SameCtrlChgOrgDao)SpringContextUtils.getBean(SameCtrlChgOrgDao.class);
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        this.sameCtrlChgSettingZbAttrDao = (SameCtrlChgSettingZbAttrDao)SpringContextUtils.getBean(SameCtrlChgSettingZbAttrDao.class);
        this.formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
        this.sameCtrlExtractLogService = (SameCtrlExtractLogService)SpringContextUtils.getBean(SameCtrlExtractLogService.class);
        this.formulaSchemeConfigService = (FormulaSchemeConfigService)SpringContextUtils.getBean(FormulaSchemeConfigService.class);
        this.dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        this.taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        this.consolidatedOptionService = (ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class);
    }

    public void extractReportData() {
        if (!this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().isVirtual()) {
            this.sameCtrlChgEnvContext.setSameCtrlExtractReportCond(this.getSameCtrlExtractReportCond(this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond()));
        }
        SameCtrlExtractLogVO sameCtrlExtractLog = this.saveSameCtrlExtractLog(this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond());
        this.sameCtrlChgEnvContext.addProgressValue(0.05);
        this.sameCtrlChgEnvContext.addResultItem("\u6267\u884c\u65f6\u95f4\uff1a" + DateUtils.format((Date)new Date(sameCtrlExtractLog.getBeginTime()), (String)"yyyy-MM-dd HH:mm:ss"));
        this.sameCtrlChgEnvContext.addResultItem("\u6267\u884c\u7528\u6237\uff1a" + sameCtrlExtractLog.getUserName());
        try {
            this.tableZbSetting = this.listTableZbSetting();
            this.sameCtrlChgEnvContext.addProgressValue(0.1);
            this.sameCtrlChgEnvContext.addResultItem(String.format("\u5f00\u59cb\u6267\u884c\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \u6570\u636e\u63d0\u53d6", this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode(), this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedName()));
            this.doReportDataExtract();
            sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.SUCCESS);
        }
        catch (Exception e) {
            this.sameCtrlChgEnvContext.setSuccessFlag(false);
            this.sameCtrlChgEnvContext.addResultItem(String.format("\u540c\u63a7\u63d0\u53d6\u53d1\u751f\u5f02\u5e38\uff1a%1s", e.getMessage()));
            sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.ERROR);
            logger.error(e.getMessage(), e);
        }
        sameCtrlExtractLog.setEndTime(Long.valueOf(System.currentTimeMillis()));
        sameCtrlExtractLog.setInfo(String.join((CharSequence)";\n", (Iterable)this.sameCtrlChgEnvContext.getResult()));
        this.sameCtrlExtractLogService.updateSamrCtrlLogById(sameCtrlExtractLog);
    }

    private void doReportDataExtract() {
        double stepProgress = 0.4 / (double)this.tableZbSetting.size();
        for (SameCtrlExtractTableZbSetting tableZbSetting : this.tableZbSetting) {
            try {
                this.handleExtractReportData(tableZbSetting, this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond());
                this.sameCtrlChgEnvContext.addResultItem(tableZbSetting.getTableTitle() + "\u63d0\u53d6\u6210\u529f");
            }
            catch (Exception e) {
                this.sameCtrlChgEnvContext.setSuccessFlag(false);
                this.sameCtrlChgEnvContext.addResultItem(String.format("\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \u63d0\u53d6\u65f6\u53d1\u751f\u5f02\u5e38, \u8868\uff1a%3s \u4fdd\u5b58\u53d1\u751f\u5f02\u5e38\uff0c\u8df3\u8fc7", this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode(), this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedName(), tableZbSetting.getTableName()));
                logger.error("\u4fdd\u5b58\u53d1\u751f\u5f02\u5e38\uff0c\u65f6\u671f\uff1a" + this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getPeriodStr() + "\u5355\u4f4d: " + this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode() + " \u8868\uff1a" + tableZbSetting.getTableName() + e.getMessage(), e);
            }
            this.sameCtrlChgEnvContext.addProgressValue(stepProgress);
        }
        this.exectBatchCalculate();
    }

    private List<SameCtrlExtractTableZbSetting> listTableZbSetting() {
        ArrayList<SameCtrlExtractTableZbSetting> tableZbSettings = new ArrayList<SameCtrlExtractTableZbSetting>();
        Map<String, List<SameCtrlChgSettingZbAttrEO>> zbAttrGroupByTableName = this.getZbSettingAttrGroupByTableName();
        for (Map.Entry<String, List<SameCtrlChgSettingZbAttrEO>> sameTableZbAttrs : zbAttrGroupByTableName.entrySet()) {
            List<String> allFieldNames;
            Map<String, List<String>> attrTypeAndFildNameMappings = this.getAttrTypeAndFieldNameMapping(sameTableZbAttrs.getValue(), sameTableZbAttrs.getKey());
            if (CollectionUtils.isEmpty(attrTypeAndFildNameMappings) || CollectionUtils.isEmpty(allFieldNames = this.getAllFieldName(attrTypeAndFildNameMappings))) continue;
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(sameTableZbAttrs.getKey());
            int dataRegionKind = sameTableZbAttrs.getValue().get(0).getDataRegionKind();
            SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting = new SameCtrlExtractTableZbSetting();
            sameCtrlExtractTableZbSetting.setUsingInFloatRegion(DataRegionKind.DATA_REGION_SIMPLE.getValue() != dataRegionKind);
            sameCtrlExtractTableZbSetting.setAttrTypeAndFieldNameMapping(attrTypeAndFildNameMappings);
            sameCtrlExtractTableZbSetting.setFieldNames(allFieldNames);
            sameCtrlExtractTableZbSetting.setTableName(sameTableZbAttrs.getKey());
            sameCtrlExtractTableZbSetting.setTableTitle(tableModelDefine.getTitle());
            tableZbSettings.add(sameCtrlExtractTableZbSetting);
        }
        return tableZbSettings;
    }

    private List<String> getAllFieldName(Map<String, List<String>> sameCtrlChgSettingZbAttrTypeMap) {
        ArrayList fieldNames = new ArrayList();
        sameCtrlChgSettingZbAttrTypeMap.values().forEach(fieldNames::addAll);
        return fieldNames.stream().distinct().collect(Collectors.toList());
    }

    private Map<String, List<SameCtrlChgSettingZbAttrEO>> getZbSettingAttrGroupByTableName() {
        HashMap<String, List<SameCtrlChgSettingZbAttrEO>> zbAttrGroupByTableName = new HashMap<String, List<SameCtrlChgSettingZbAttrEO>>(16);
        DimensionParamsVO queryParams = this.getDimensionParams();
        SameCtrlExtractReportCond sameCtrlExtractReportCond = this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond();
        this.sameCtrlChgEnvContext.addResultItem("\u5f00\u59cb\u83b7\u53d6\u540c\u63a7\u53d8\u52a8\u8bbe\u7f6e\u6307\u6807\u5c5e\u6027\u4fe1\u606f");
        List<SameCtrlChgSettingZbAttrEO> sameCtrlChgSettingZbAttrs = this.sameCtrlChgSettingZbAttrDao.getOptionZbAttrByTaskAndShcemeId(sameCtrlExtractReportCond.getTaskId(), sameCtrlExtractReportCond.getSchemeId());
        if (CollectionUtils.isEmpty(sameCtrlChgSettingZbAttrs)) {
            this.sameCtrlChgEnvContext.addResultItem("\u540c\u63a7\u53d8\u52a8\u8bbe\u7f6e\u672a\u914d\u7f6e\u6307\u6807\u5c5e\u6027");
            this.sameCtrlChgEnvContext.setSuccessFlag(false);
            throw new RuntimeException("\u540c\u63a7\u53d8\u52a8\u8bbe\u7f6e\u672a\u914d\u7f6e\u6307\u6807\u5c5e\u6027");
        }
        List<String> formKeys = sameCtrlChgSettingZbAttrs.stream().map(SameCtrlChgSettingZbAttrEO::getFormKey).distinct().collect(Collectors.toList());
        List<String> uploadedFormKeys = this.listUploadedFormKeys(formKeys, queryParams);
        ArrayList<String> notExtractTableWithForms = new ArrayList<String>();
        for (SameCtrlChgSettingZbAttrEO sameCtrlChgSettingZbAttr : sameCtrlChgSettingZbAttrs) {
            String tableNameWithFormKey;
            List dataFieldDeployInfos;
            String tableCode = sameCtrlChgSettingZbAttr.getZbCode().split("\\[")[0];
            DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableCode);
            if (null == dataTable || CollectionUtils.isEmpty(dataFieldDeployInfos = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTable.getKey())) || notExtractTableWithForms.contains(tableNameWithFormKey = tableCode + "@" + sameCtrlChgSettingZbAttr.getFormKey()) || uploadedFormKeys.contains(sameCtrlChgSettingZbAttr.getFormKey())) continue;
            DataFieldDeployInfo dataFieldDeployInfo = this.getZbSettingAttrFieldName(sameCtrlChgSettingZbAttr, tableCode);
            if (Objects.isNull(dataFieldDeployInfo)) {
                notExtractTableWithForms.add(tableNameWithFormKey);
                continue;
            }
            sameCtrlChgSettingZbAttr.setFieldName(dataFieldDeployInfo.getFieldName());
            List zbSettingAttrs = zbAttrGroupByTableName.computeIfAbsent(dataFieldDeployInfo.getTableName(), key -> new ArrayList());
            zbSettingAttrs.add(sameCtrlChgSettingZbAttr);
        }
        return zbAttrGroupByTableName;
    }

    private DataFieldDeployInfo getZbSettingAttrFieldName(SameCtrlChgSettingZbAttrEO sameCtrlChgSettingZbAttr, String tableCode) {
        if (StringUtils.isEmpty((String)sameCtrlChgSettingZbAttr.getFormKey())) {
            this.sameCtrlChgEnvContext.addResultItem("\u8868\u5355\uff1a" + sameCtrlChgSettingZbAttr.getFormKey() + "\u4e0d\u5b58\u5728, \u8df3\u8fc7");
            return null;
        }
        if (tableCode.contains("GC_INPUTDATA")) {
            this.sameCtrlChgEnvContext.addResultItem("\u5185\u90e8\u8868\u4e0d\u8fdb\u884c\u63d0\u53d6\u64cd\u4f5c\uff0c\u8df3\u8fc7");
            return null;
        }
        DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableCode);
        if (null == dataTable) {
            this.sameCtrlChgEnvContext.addResultItem("\u8868\uff1a" + tableCode + "\u4e0d\u5b58\u5728, \u8df3\u8fc7");
            return null;
        }
        String zbCode = sameCtrlChgSettingZbAttr.getZbCode().substring(sameCtrlChgSettingZbAttr.getZbCode().indexOf("[") + 1, sameCtrlChgSettingZbAttr.getZbCode().indexOf("]"));
        DataField dataField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), zbCode);
        if (dataField == null) {
            this.sameCtrlChgEnvContext.addResultItem("\u8868\uff1a" + tableCode + "\u4e0d\u5b58\u5728,\u6307\u6807\uff1a" + zbCode + "\u4e0d\u5b58\u5728, \u8df3\u8fc7");
            return null;
        }
        List dataFieldDeployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
        if (CollectionUtils.isEmpty(dataFieldDeployInfos)) {
            this.sameCtrlChgEnvContext.addResultItem("\u6570\u636e\u6a21\u7c7b\u578b\u53d1\u5e03\u4fe1\u606f\u4e2d\u4e0d\u5b58\u5728\u8868\uff1a" + tableCode + ",\u4e0d\u5b58\u5728\u6307\u6807\uff1a" + zbCode + ", \u8df3\u8fc7");
            return null;
        }
        return (DataFieldDeployInfo)dataFieldDeployInfos.get(0);
    }

    private Map<String, List<String>> getAttrTypeAndFieldNameMapping(List<SameCtrlChgSettingZbAttrEO> zbAttrs, String tableName) {
        Map<String, List<String>> attrTypeAndZbMappings = new HashMap<String, List<String>>(16);
        if (CollectionUtils.isEmpty(zbAttrs)) {
            return attrTypeAndZbMappings;
        }
        SameCtrlChgSettingZbAttrEO zbSettingAttr = zbAttrs.get(0);
        attrTypeAndZbMappings = DataRegionKind.DATA_REGION_SIMPLE.getValue() != zbSettingAttr.getDataRegionKind().intValue() ? this.getFloatAttrTypeAndFieldNameMapping(zbAttrs, tableName) : this.getFixAttrTypeAndFieldNameMapping(zbAttrs);
        return attrTypeAndZbMappings;
    }

    private Map<String, List<String>> getFloatAttrTypeAndFieldNameMapping(List<SameCtrlChgSettingZbAttrEO> settingZbAttrs, String tableName) {
        HashMap<String, List<String>> attrTypeAndZbMappings = new HashMap<String, List<String>>(16);
        List fields = this.runtimeDataSchemeService.getDeployInfoByTableName(tableName);
        SameCtrlChgSettingZbAttrEO zbSetting = this.getFirstZbSettingAttr(settingZbAttrs, fields);
        if (zbSetting != null) {
            List zbFieldNames = fields.stream().map(DataFieldDeployInfo::getFieldName).collect(Collectors.toList());
            attrTypeAndZbMappings.put(zbSetting.getZbAttribure(), zbFieldNames);
        }
        return attrTypeAndZbMappings;
    }

    private SameCtrlChgSettingZbAttrEO getFirstZbSettingAttr(List<SameCtrlChgSettingZbAttrEO> settingZbAttrs, List<DataFieldDeployInfo> fieldDefines) {
        Map<String, SameCtrlChgSettingZbAttrEO> zbAttrGroupByFieldName = settingZbAttrs.stream().collect(Collectors.toMap(SameCtrlChgSettingZbAttrEO::getFieldName, eo -> eo, (v1, v2) -> v1));
        Map<String, SameCtrlChgSettingZbAttrEO> zbAttrGroupFieldKey = fieldDefines.stream().filter(fieldDefine -> zbAttrGroupByFieldName.containsKey(fieldDefine.getFieldName())).collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, fieldDefine -> (SameCtrlChgSettingZbAttrEO)((Object)((Object)zbAttrGroupByFieldName.get(fieldDefine.getFieldName())))));
        String formKey = settingZbAttrs.get(0).getFormKey();
        List fieldKeys = this.iRunTimeViewController.getAllLinksInForm(formKey).stream().map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
        SameCtrlChgSettingZbAttrEO settingZbAttr = null;
        for (String key : fieldKeys) {
            if (!zbAttrGroupFieldKey.containsKey(key)) continue;
            settingZbAttr = zbAttrGroupFieldKey.get(key);
            break;
        }
        return settingZbAttr;
    }

    private Map<String, List<String>> getFixAttrTypeAndFieldNameMapping(List<SameCtrlChgSettingZbAttrEO> zbAttrs) {
        HashMap<String, List<String>> attrTypeAndFieldNameMappings = new HashMap<String, List<String>>(16);
        List<String> dimensionNames = this.listDimensionName();
        for (SameCtrlChgSettingZbAttrEO sameCtrlChgSettingZbAttr : zbAttrs) {
            List sameAttrTypeFieldNames = attrTypeAndFieldNameMappings.computeIfAbsent(sameCtrlChgSettingZbAttr.getZbAttribure(), key -> new ArrayList(dimensionNames));
            if (sameAttrTypeFieldNames.contains(sameCtrlChgSettingZbAttr.getFieldName())) continue;
            sameAttrTypeFieldNames.add(sameCtrlChgSettingZbAttr.getFieldName());
        }
        return attrTypeAndFieldNameMappings;
    }

    private DimensionParamsVO getDimensionParams() {
        SameCtrlExtractReportCond sameCtrlExtractReportCond = this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond();
        DimensionParamsVO queryParams = new DimensionParamsVO();
        BeanUtils.copyProperties(sameCtrlExtractReportCond, queryParams);
        queryParams.setCurrency(sameCtrlExtractReportCond.getCurrencyId());
        queryParams.setCurrencyId(sameCtrlExtractReportCond.getCurrencyId());
        String orgCode = sameCtrlExtractReportCond.getVirtualCode();
        queryParams.setOrgId(orgCode);
        queryParams.setSelectAdjustCode(sameCtrlExtractReportCond.getSelectAdjustCode());
        return queryParams;
    }

    private void handleExtractReportData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        sameCtrlExtractReportCond.setGcOrgCenterService(this.getGcOrgTool(sameCtrlExtractReportCond));
        List<List<Map<String, Object>>> extractDataList = this.listSameCtrlChgSettingZbData(sameCtrlExtractTableZbSetting, sameCtrlExtractReportCond);
        ((SameCtrlExtractDataService)SpringContextUtils.getBean(SameCtrlExtractDataService.class)).saveExtractReportData(sameCtrlExtractTableZbSetting, extractDataList, sameCtrlExtractReportCond);
    }

    protected GcOrgCenterService getGcOrgTool(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        YearPeriodObject yp = new YearPeriodObject(null, sameCtrlExtractReportCond.getPeriodStr());
        return GcOrgPublicTool.getInstance((String)sameCtrlExtractReportCond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
    }

    private List<List<Map<String, Object>>> listSameCtrlChgSettingZbData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        YearPeriodObject yearPeriodObject = new YearPeriodObject(null, sameCtrlExtractReportCond.getPeriodStr());
        yearPeriodObject.setType(yearPeriodObject.formatYP().getType());
        Calendar changeCalendar = this.getCalendar(sameCtrlExtractReportCond.getChangeDate());
        Calendar periodStrCalendar = this.getCalendar(yearPeriodObject.formatYP().getBeginDate());
        if (changeCalendar.get(1) == periodStrCalendar.get(1) && changeCalendar.get(2) == periodStrCalendar.get(2)) {
            return this.getChangedCurrentMonthZbData(sameCtrlExtractTableZbSetting, sameCtrlExtractReportCond, yearPeriodObject);
        }
        if (changeCalendar.get(1) == periodStrCalendar.get(1) && changeCalendar.get(2) < periodStrCalendar.get(2)) {
            return this.getChangedYearAfterZbData(sameCtrlExtractTableZbSetting, sameCtrlExtractReportCond, yearPeriodObject);
        }
        if (periodStrCalendar.get(1) - changeCalendar.get(1) == 1 && changeCalendar.get(2) > periodStrCalendar.get(2)) {
            return this.getSecondYearZbData(sameCtrlExtractTableZbSetting, sameCtrlExtractReportCond);
        }
        if (periodStrCalendar.get(1) - changeCalendar.get(1) == 1 && changeCalendar.get(2) <= periodStrCalendar.get(2)) {
            boolean isSameCtrlMonthEqual = changeCalendar.get(2) == periodStrCalendar.get(2);
            return this.getSecondYearAfterZbData(sameCtrlExtractTableZbSetting, sameCtrlExtractReportCond, yearPeriodObject, isSameCtrlMonthEqual);
        }
        return Collections.emptyList();
    }

    private List<List<Map<String, Object>>> getChangedCurrentMonthZbData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, SameCtrlExtractReportCond sameCtrlExtractReportCond, YearPeriodObject yearPeriodObject) {
        ArrayList<List<Map<String, Object>>> extractDataList = new ArrayList<List<Map<String, Object>>>();
        Map sameCtrlChgSettingZbAttrTypeMap = sameCtrlExtractTableZbSetting.getAttrTypeAndFieldNameMapping();
        String periodOffsetStr = this.consolidatedOptionService.getOptionData(sameCtrlExtractReportCond.getSystemId()).getSameCtrlExtractPeriodOffset();
        int periodOffset = 0;
        if (!StringUtils.isEmpty((String)periodOffsetStr)) {
            periodOffset = Integer.valueOf(periodOffsetStr);
        }
        if (ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(sameCtrlExtractReportCond.getChangedOrgType())) {
            SameCtrlExtractReportCond extractReportDataByDisposalDateCumulativeThisYear = this.getSameCtrlExtractReportCondByDisposalDate(sameCtrlExtractReportCond, yearPeriodObject, periodOffset);
            List<Map<String, Object>> beginData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.BEGIN_ZB.getCode()), sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
            List<Map<String, Object>> lastYearData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.SAME_PERIOD_LAST_YEAR_ZB.getCode()), sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
            extractDataList.add(beginData);
            extractDataList.add(lastYearData);
            List<Map<String, Object>> lastMonthData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.CUMULATIVE_THISY_EAR.getCode()), extractReportDataByDisposalDateCumulativeThisYear, sameCtrlExtractTableZbSetting.getTableName());
            extractDataList.add(lastMonthData);
        } else {
            SameCtrlExtractReportCond extractReportDataByDisposalDateBeginZb = this.getSameCtrlExtractReportCondByDisposalDate(sameCtrlExtractReportCond, yearPeriodObject, -1);
            List<Map<String, Object>> beginData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.BEGIN_ZB.getCode()), extractReportDataByDisposalDateBeginZb, sameCtrlExtractTableZbSetting.getTableName());
            SameCtrlExtractReportCond extractReportDataByDisposalDateCumulativeThisYear = this.getSameCtrlExtractReportCondByDisposalDate(sameCtrlExtractReportCond, yearPeriodObject, periodOffset);
            List<Map<String, Object>> lastMonthData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.CUMULATIVE_THISY_EAR.getCode()), extractReportDataByDisposalDateCumulativeThisYear, sameCtrlExtractTableZbSetting.getTableName());
            extractDataList.add(beginData);
            extractDataList.add(lastMonthData);
        }
        return extractDataList;
    }

    private List<List<Map<String, Object>>> getChangedYearAfterZbData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, SameCtrlExtractReportCond sameCtrlExtractReportCond, YearPeriodObject yearPeriodObject) {
        Map sameCtrlChgSettingZbAttrTypeMap = sameCtrlExtractTableZbSetting.getAttrTypeAndFieldNameMapping();
        ArrayList<List<Map<String, Object>>> extractDataList = new ArrayList<List<Map<String, Object>>>();
        if (ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(sameCtrlExtractReportCond.getChangedOrgType())) {
            SameCtrlExtractReportCond extractReportData = this.getSameCtrlExtractReportCond(sameCtrlExtractReportCond, yearPeriodObject, -1);
            List<Map<String, Object>> beginData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.BEGIN_ZB.getCode()), sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
            List<Map<String, Object>> lastYearData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.SAME_PERIOD_LAST_YEAR_ZB.getCode()), sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
            extractReportData.setChangedCode(extractReportData.getVirtualCode());
            List<Map<String, Object>> lastMonthData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.CUMULATIVE_THISY_EAR.getCode()), extractReportData, sameCtrlExtractTableZbSetting.getTableName());
            extractDataList.add(beginData);
            extractDataList.add(lastYearData);
            extractDataList.add(lastMonthData);
        } else {
            SameCtrlExtractReportCond extractReportData = this.getSameCtrlExtractReportCond(sameCtrlExtractReportCond, yearPeriodObject, -1);
            extractReportData.setChangedCode(extractReportData.getVirtualCode());
            List<Map<String, Object>> beginData = this.getChangeCodeZbData((List)sameCtrlChgSettingZbAttrTypeMap.get(SameCtrlZbAttrEnum.BEGIN_ZB.getCode()), extractReportData, sameCtrlExtractTableZbSetting.getTableName());
            SameCtrlExtractReportCond extractReportDataThisYear = this.getSameCtrlExtractReportCondByDisposalDate(sameCtrlExtractReportCond, yearPeriodObject, -1);
            extractReportData.setChangedCode(extractReportData.getVirtualCode());
            List<Map<String, Object>> lastMonthData = this.getChangeCodeZbData((List)sameCtrlExtractTableZbSetting.getAttrTypeAndFieldNameMapping().get(SameCtrlZbAttrEnum.CUMULATIVE_THISY_EAR.getCode()), extractReportDataThisYear, sameCtrlExtractTableZbSetting.getTableName());
            extractDataList.add(beginData);
            extractDataList.add(lastMonthData);
        }
        return extractDataList;
    }

    private List<List<Map<String, Object>>> getSecondYearZbData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        ArrayList<List<Map<String, Object>>> extractDataList = new ArrayList<List<Map<String, Object>>>();
        if (ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(sameCtrlExtractReportCond.getChangedOrgType())) {
            List<Map<String, Object>> lastMonthData = this.getChangeCodeZbData((List)sameCtrlExtractTableZbSetting.getAttrTypeAndFieldNameMapping().get(SameCtrlZbAttrEnum.SAME_PERIOD_LAST_YEAR_ZB.getCode()), sameCtrlExtractReportCond, sameCtrlExtractTableZbSetting.getTableName());
            extractDataList.add(lastMonthData);
        }
        return extractDataList;
    }

    private List<List<Map<String, Object>>> getSecondYearAfterZbData(SameCtrlExtractTableZbSetting sameCtrlExtractTableZbSetting, SameCtrlExtractReportCond sameCtrlExtractReportCond, YearPeriodObject yearPeriodObject, boolean isSameCtrlMonthEqual) {
        ArrayList<List<Map<String, Object>>> extractDataList = new ArrayList<List<Map<String, Object>>>();
        if (ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode().equals(sameCtrlExtractReportCond.getChangedOrgType())) {
            SameCtrlExtractReportCond extractReportData;
            if (isSameCtrlMonthEqual) {
                String periodOffsetStr = this.consolidatedOptionService.getOptionData(sameCtrlExtractReportCond.getSystemId()).getSameCtrlExtractPeriodOffset();
                int periodOffset = 0;
                if (!StringUtils.isEmpty((String)periodOffsetStr)) {
                    periodOffset = Integer.parseInt(periodOffsetStr);
                }
                extractReportData = this.getSameCtrlExtractReportCond(sameCtrlExtractReportCond, yearPeriodObject, periodOffset);
            } else {
                extractReportData = this.getSameCtrlExtractReportCond(sameCtrlExtractReportCond, yearPeriodObject, -1);
                extractReportData.setChangedCode(extractReportData.getVirtualCode());
            }
            List<Map<String, Object>> lastMonthData = this.getChangeCodeZbData((List)sameCtrlExtractTableZbSetting.getAttrTypeAndFieldNameMapping().get(SameCtrlZbAttrEnum.SAME_PERIOD_LAST_YEAR_ZB.getCode()), extractReportData, sameCtrlExtractTableZbSetting.getTableName());
            extractDataList.add(lastMonthData);
        }
        return extractDataList;
    }

    private SameCtrlExtractReportCond getSameCtrlExtractReportCond(SameCtrlExtractReportCond sameCtrlExtractReportCond, YearPeriodObject yearPeriodObject, int periodOffset) {
        SameCtrlExtractReportCond extractReportData = new SameCtrlExtractReportCond();
        BeanUtils.copyProperties(sameCtrlExtractReportCond, extractReportData);
        if (DimensionUtils.isExistAdjust((String)sameCtrlExtractReportCond.getTaskId()) && -1 == periodOffset && !"0".equals(sameCtrlExtractReportCond.getSelectAdjustCode())) {
            periodOffset = 0;
            extractReportData.setSelectAdjustCode("0");
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(yearPeriodObject.formatYP().getEndDate());
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)yearPeriodObject.getType(), (int)periodOffset);
        extractReportData.setPeriodStr(currentPeriod.toString());
        return extractReportData;
    }

    private SameCtrlExtractReportCond getSameCtrlExtractReportCondByDisposalDate(SameCtrlExtractReportCond sameCtrlExtractReportCond, YearPeriodObject yearPeriodObject, int periodOffset) {
        SameCtrlExtractReportCond extractReportData = new SameCtrlExtractReportCond();
        BeanUtils.copyProperties(sameCtrlExtractReportCond, extractReportData);
        if (sameCtrlExtractReportCond.isGoBack()) {
            return extractReportData;
        }
        if (DimensionUtils.isExistAdjust((String)sameCtrlExtractReportCond.getTaskId()) && -1 == periodOffset && !"0".equals(sameCtrlExtractReportCond.getSelectAdjustCode())) {
            periodOffset = 0;
            extractReportData.setSelectAdjustCode("0");
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(sameCtrlExtractReportCond.getDisposalDate());
        PeriodWrapper disposalDatePeriod = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)yearPeriodObject.getType(), (int)periodOffset);
        extractReportData.setPeriodStr(disposalDatePeriod.toString());
        return extractReportData;
    }

    private List<Map<String, Object>> getChangeCodeZbData(List<String> zbFieldNames, SameCtrlExtractReportCond sameCtrlExtractReportCond, String tableName) {
        if (CollectionUtils.isEmpty(zbFieldNames)) {
            return Collections.emptyList();
        }
        String fieldStr = this.getSelectFields(zbFieldNames);
        String sql = this.getSql(sameCtrlExtractReportCond, tableName, fieldStr);
        if (StringUtils.isEmpty((String)sql)) {
            return Collections.emptyList();
        }
        List maps = EntNativeSqlDefaultDao.getInstance().selectMap(sql, Arrays.asList(sameCtrlExtractReportCond.getChangedCode()));
        return maps;
    }

    private String getSql(SameCtrlExtractReportCond sameCtrlExtractReportCond, String tableName, String fieldNameStr) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT ").append(fieldNameStr).append(" FROM ").append(tableName).append(" t ").append(" WHERE ");
        StringBuilder whereSql = this.getDimensionWhereSql(sameCtrlExtractReportCond);
        if (whereSql != null && whereSql.length() <= 0) {
            return null;
        }
        return sqlBuilder.append((CharSequence)whereSql).toString();
    }

    private String getSelectFields(List<String> zbFieldNames) {
        StringBuffer zbFieldNameStr = new StringBuffer();
        zbFieldNames.forEach(fieldName -> zbFieldNameStr.append((String)fieldName).append(","));
        return zbFieldNameStr.deleteCharAt(zbFieldNameStr.length() - 1).toString();
    }

    private List<String> listDimensionName() {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        dimensionNames.add("MDCODE");
        dimensionNames.add("MD_GCORGTYPE");
        dimensionNames.add("DATATIME");
        dimensionNames.add("MD_CURRENCY");
        String taskId = this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getTaskId();
        if (DimensionUtils.isExisAdjType((String)taskId)) {
            dimensionNames.add("MD_GCADJTYPE");
        }
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            dimensionNames.add("ADJUST");
        }
        return dimensionNames;
    }

    private StringBuilder getDimensionWhereSql(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        List<String> dimensionNames = this.listDimensionName();
        if (dimensionNames.isEmpty()) {
            return null;
        }
        StringBuilder sqlBuilder = new StringBuilder(10);
        GcOrgCacheVO gcOrgCacheVO = sameCtrlExtractReportCond.getGcOrgCenterService().getOrgByCode(sameCtrlExtractReportCond.getChangedCode());
        String changedOrgType = gcOrgCacheVO != null && !StringUtils.isEmpty((String)gcOrgCacheVO.getOrgTypeId()) ? gcOrgCacheVO.getOrgTypeId() : sameCtrlExtractReportCond.getOrgType();
        for (String dimensionName : dimensionNames) {
            if ("MDCODE".equals(dimensionName)) {
                sqlBuilder.append(" t.").append(dimensionName).append("=?");
                continue;
            }
            if ("MD_CURRENCY".equals(dimensionName)) {
                sqlBuilder.append(" AND t.").append(dimensionName).append("='").append(sameCtrlExtractReportCond.getCurrencyId()).append("' ");
                continue;
            }
            if ("MD_GCORGTYPE".equals(dimensionName)) {
                sqlBuilder.append(" AND t.").append(dimensionName).append(" in ('").append(changedOrgType).append("','").append(GCOrgTypeEnum.NONE.getCode()).append("')");
                continue;
            }
            if ("DATATIME".equals(dimensionName)) {
                sqlBuilder.append(" AND t.").append(dimensionName).append("='").append(sameCtrlExtractReportCond.getPeriodStr()).append("'");
                continue;
            }
            if (!"ADJUST".equals(dimensionName)) continue;
            sqlBuilder.append(" AND ").append("ADJUST").append("='").append(sameCtrlExtractReportCond.getSelectAdjustCode()).append("'");
        }
        return sqlBuilder;
    }

    private void exectBatchCalculate() {
        BatchCalculateInfo batchCalculateInfo;
        SameCtrlExtractReportCond sameCtrlExtractReportCond = this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond();
        Map<String, String> dimensionParams = this.getDimensionParams(sameCtrlExtractReportCond);
        FormulaSchemeConfigDTO schemeConfig = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(sameCtrlExtractReportCond.getSchemeId(), sameCtrlExtractReportCond.getVirtualCode(), dimensionParams);
        if (ChangedOrgTypeEnum.NON_SAME_CTRL_DISPOSE.getCode().equals(sameCtrlExtractReportCond.getChangedOrgType())) {
            if (null != schemeConfig && !CollectionUtils.isEmpty(schemeConfig.getUnSaCtDeExtLaYeNumSaPerId())) {
                String formulaSchemeKeys = schemeConfig.getUnSaCtDeExtLaYeNumSaPerId().stream().filter(unSaCtDeExtLaYeNumSaPerId -> !StringUtils.isEmpty((String)unSaCtDeExtLaYeNumSaPerId)).collect(Collectors.joining(";"));
                BatchCalculateInfo batchCalculateInfo2 = this.buidBatchCalculateInfo(sameCtrlExtractReportCond, formulaSchemeKeys);
                if (batchCalculateInfo2 != null) {
                    ((IBatchCalculateService)SpringContextUtils.getBean(IBatchCalculateService.class)).batchCalculateForm(batchCalculateInfo2);
                } else {
                    this.sameCtrlChgEnvContext.addResultItem(String.format("\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \u5168\u7b97\u65f6\u672a\u627e\u5230\u975e\u540c\u63a7\u5904\u7f6e\u63d0\u53d6\u4e0a\u5e74\u540c\u671f\u6570\u516c\u5f0f\u65b9\u6848\uff0c\u8df3\u8fc7", sameCtrlExtractReportCond.getChangedCode(), sameCtrlExtractReportCond.getChangedName()));
                }
            } else {
                this.sameCtrlChgEnvContext.addResultItem(String.format("\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \u5168\u7b97\u65f6\u672a\u627e\u5230\u975e\u540c\u63a7\u5904\u7f6e\u63d0\u53d6\u4e0a\u5e74\u540c\u671f\u6570\u516c\u5f0f\u65b9\u6848\uff0c\u8df3\u8fc7", sameCtrlExtractReportCond.getChangedCode(), sameCtrlExtractReportCond.getChangedName()));
            }
        }
        if (null != schemeConfig && !CollectionUtils.isEmpty(schemeConfig.getSameCtrlExtAfterSchemeId())) {
            String formulaSchemeKeys = schemeConfig.getSameCtrlExtAfterSchemeId().stream().filter(sameCtrlExtAfterSchemeId -> !StringUtils.isEmpty((String)sameCtrlExtAfterSchemeId)).collect(Collectors.joining(";"));
            batchCalculateInfo = this.buidBatchCalculateInfo(sameCtrlExtractReportCond, formulaSchemeKeys);
        } else {
            batchCalculateInfo = this.buidBatchCalculateInfo(sameCtrlExtractReportCond, null);
        }
        if (batchCalculateInfo != null) {
            ((IBatchCalculateService)SpringContextUtils.getBean(IBatchCalculateService.class)).batchCalculateForm(batchCalculateInfo);
        } else {
            this.sameCtrlChgEnvContext.addResultItem(String.format("\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \u5168\u7b97\u65f6\u672a\u627e\u5230\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848\uff0c\u8df3\u8fc7", sameCtrlExtractReportCond.getChangedCode(), sameCtrlExtractReportCond.getChangedName()));
        }
    }

    private Map<String, String> getDimensionParams(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        HashMap<String, String> dimensionParam = new HashMap<String, String>(16);
        dimensionParam.put("MD_GCORGTYPE", sameCtrlExtractReportCond.getOrgType());
        dimensionParam.put("MD_CURRENCY", sameCtrlExtractReportCond.getCurrencyId());
        dimensionParam.put("DATATIME", sameCtrlExtractReportCond.getPeriodStr());
        return dimensionParam;
    }

    private BatchCalculateInfo buidBatchCalculateInfo(SameCtrlExtractReportCond ctrlExtractReportCond, String formulaSchemeKey) {
        Map dimensions = DimensionUtils.buildDimensionMap((String)ctrlExtractReportCond.getTaskId(), (String)ctrlExtractReportCond.getCurrencyId(), (String)ctrlExtractReportCond.getPeriodStr(), (String)ctrlExtractReportCond.getOrgType(), (String)ctrlExtractReportCond.getVirtualCode(), (String)ctrlExtractReportCond.getSelectAdjustCode());
        if (!StringUtils.isEmpty((String)this.getFormulaSchemeId(ctrlExtractReportCond.getSchemeId()))) {
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            batchCalculateInfo.setDimensionSet(dimensions);
            batchCalculateInfo.setTaskKey(ctrlExtractReportCond.getTaskId());
            batchCalculateInfo.setFormSchemeKey(ctrlExtractReportCond.getSchemeId());
            batchCalculateInfo.setContext(ctrlExtractReportCond.getJtableContext());
            if (StringUtils.isEmpty((String)formulaSchemeKey)) {
                batchCalculateInfo.setFormulaSchemeKey(this.getFormulaSchemeId(ctrlExtractReportCond.getSchemeId()));
            } else {
                batchCalculateInfo.setFormulaSchemeKey(formulaSchemeKey);
            }
            batchCalculateInfo.getVariableMap().put("GCSAMECTRL_RECOVERYFLAG_ORGTYPE", true);
            return batchCalculateInfo;
        }
        return null;
    }

    private String getFormulaSchemeId(String schemeId) {
        List definesByFormScheme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(schemeId);
        if (!CollectionUtils.isEmpty(definesByFormScheme)) {
            List formulaSchemeDefines = definesByFormScheme.stream().filter(formulaSchemeDefine -> {
                FormulaSchemeType schemeType = formulaSchemeDefine.getFormulaSchemeType();
                return schemeType.equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) && formulaSchemeDefine.isDefault();
            }).collect(Collectors.toList());
            if (formulaSchemeDefines.isEmpty()) {
                logger.error("\u672a\u627e\u5230\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848 schemeId:" + schemeId);
                return null;
            }
            return ((FormulaSchemeDefine)formulaSchemeDefines.get(0)).getKey();
        }
        return null;
    }

    private SameCtrlExtractLogVO saveSameCtrlExtractLog(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        SameCtrlExtractLogVO sameCtrlExtractLog = new SameCtrlExtractLogVO();
        BeanUtils.copyProperties(sameCtrlExtractReportCond, sameCtrlExtractLog);
        sameCtrlExtractLog.setChangedParentCode(null);
        sameCtrlExtractLog.setVirtualParentCode(null);
        sameCtrlExtractLog.setLatestFlag(Integer.valueOf(1));
        sameCtrlExtractLog.setOperate(SameCtrlExtractOperateEnum.DATAENTRY_EXTRACT);
        sameCtrlExtractLog.setEndTime(null);
        sameCtrlExtractLog.setTaskState(SameCtrlExtractTaskStateEnum.EXECUTING);
        Date currentDate = new Date();
        ContextUser currentUser = NpContextHolder.getContext().getUser();
        String userName = currentUser == null ? "" : (StringUtils.isEmpty((String)currentUser.getFullname()) ? currentUser.getName() : currentUser.getFullname());
        sameCtrlExtractLog.setBeginTime(Long.valueOf(currentDate.getTime()));
        sameCtrlExtractLog.setUserName(userName);
        return this.sameCtrlExtractLogService.insertSameCtrlExtractLog(sameCtrlExtractLog);
    }

    private GcOrgCacheVO getDifferenceOrg(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        YearPeriodObject yearPeriodObject = new YearPeriodObject(null, sameCtrlExtractReportCond.getPeriodStr());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)sameCtrlExtractReportCond.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodObject);
        GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(sameCtrlExtractReportCond.getVirtualCode());
        GcOrgCacheVO differenceOrg = null;
        if (orgCacheVO != null && !StringUtils.isEmpty((String)orgCacheVO.getParentId())) {
            List gcOrgCacheList = orgCenterTool.getOrgChildrenTree(orgCacheVO.getParentId());
            for (GcOrgCacheVO gcOrgCacheVO : gcOrgCacheList) {
                GcOrgCacheVO mergeUnit;
                if (!GcOrgKindEnum.DIFFERENCE.equals((Object)gcOrgCacheVO.getOrgKind()) || (mergeUnit = orgCenterTool.getMergeUnitByDifference(gcOrgCacheVO.getCode())) == null || !mergeUnit.getCode().equals(orgCacheVO.getParentId())) continue;
                differenceOrg = gcOrgCacheVO;
                break;
            }
        } else {
            this.sameCtrlChgEnvContext.setSuccessFlag(false);
            this.sameCtrlChgEnvContext.addResultItem("\u5355\u4f4d\uff1a" + sameCtrlExtractReportCond.getVirtualCode() + " \u65f6\u671f\uff1a" + sameCtrlExtractReportCond.getPeriodStr() + " \u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + sameCtrlExtractReportCond.getOrgType() + "\u4e0d\u5b58\u5728\uff01");
            throw new RuntimeException("\u5355\u4f4d\uff1a" + sameCtrlExtractReportCond.getVirtualCode() + " \u65f6\u671f\uff1a" + sameCtrlExtractReportCond.getPeriodStr() + " \u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + sameCtrlExtractReportCond.getOrgType() + "\u4e0d\u5b58\u5728\uff01");
        }
        if (differenceOrg == null || StringUtils.isEmpty((String)differenceOrg.getCode())) {
            this.sameCtrlChgEnvContext.setSuccessFlag(false);
            this.sameCtrlChgEnvContext.addResultItem("\u5408\u5e76\u5355\u4f4d\uff1a" + orgCacheVO.getParentId() + " \u65f6\u671f\uff1a" + sameCtrlExtractReportCond.getPeriodStr() + " \u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + sameCtrlExtractReportCond.getOrgType() + "\u5bf9\u5e94\u5dee\u989d\u5355\u4f4d\u4e0d\u5b58\u5728\uff01");
            throw new RuntimeException("\u5408\u5e76\u5355\u4f4d\uff1a" + orgCacheVO.getParentId() + " \u65f6\u671f\uff1a" + sameCtrlExtractReportCond.getPeriodStr() + " \u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + sameCtrlExtractReportCond.getOrgType() + "\u5bf9\u5e94\u5dee\u989d\u5355\u4f4d\u4e0d\u5b58\u5728\uff01");
        }
        return differenceOrg;
    }

    private List<String> listUploadedFormKeys(List<String> formKeys, DimensionParamsVO queryParams) {
        ArrayList<String> writableFormKeys = new ArrayList<String>();
        for (String formKey : formKeys) {
            FormDefine formDefine = null;
            try {
                formDefine = this.iRunTimeViewController.queryFormById(formKey);
            }
            catch (Exception e) {
                writableFormKeys.add(formKey);
                logger.error(formKeys + "\u8868\u5355\u4e0d\u5b58\u5728", e);
                this.sameCtrlChgEnvContext.addResultItem(String.format("\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \uff0c\u8868\u5355\u4e0d\u5b58\u5728formKey: %3s ", this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode(), this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedName(), formKeys));
                continue;
            }
            if (null == formDefine) {
                writableFormKeys.add(formKey);
                logger.error(formKeys + "\u8868\u5355\u4e0d\u5b58\u5728");
                this.sameCtrlChgEnvContext.addResultItem(String.format("\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \uff0c\u8868\u5355\u4e0d\u5b58\u5728formKey: %3s ", this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode(), this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedName(), formKeys));
                continue;
            }
            ReadWriteAccessDesc readWriteAccessDesc = FormUploadStateTool.getInstance().writeable(queryParams, formKey);
            if (Boolean.TRUE.equals(readWriteAccessDesc.getAble())) continue;
            writableFormKeys.add(formKey);
            this.sameCtrlChgEnvContext.addResultItem(String.format("\u53d8\u52a8\u5355\u4f4d\uff1a%1s | %2s \u8868\u5355\uff1a%3s\uff0c%4s", this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode(), this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedName(), formDefine.getTitle(), readWriteAccessDesc.getDesc()));
            logger.error("\u65f6\u671f\uff1a" + this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getPeriodStr() + "\u5355\u4f4d\uff1a" + this.sameCtrlChgEnvContext.getSameCtrlExtractReportCond().getChangedCode() + "\uff0c\u8868\u5355\uff1a" + formDefine.getTitle() + "  " + readWriteAccessDesc.getDesc() + ", \u8df3\u8fc7");
        }
        return writableFormKeys;
    }

    private SameCtrlExtractReportCond getSameCtrlExtractReportCond(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        YearPeriodObject yearPeriodObject = new YearPeriodObject(null, sameCtrlExtractReportCond.getPeriodStr());
        SameCtrlChgOrgEO sameCtrlChgOrg = this.sameCtrlChgOrgDao.listSameCtrlChgOrgsByChangeCodeAndDate(sameCtrlExtractReportCond.getChangedCode(), sameCtrlExtractReportCond.getChangeDate());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)sameCtrlExtractReportCond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yearPeriodObject);
        String changedOrgTitle = null;
        if (sameCtrlChgOrg == null) {
            sameCtrlExtractReportCond.setDisposalDate(sameCtrlExtractReportCond.getChangeDate());
            sameCtrlExtractReportCond.setChangedOrgType(ChangedOrgTypeEnum.SAME_CTRL_CHANGE.getCode());
            sameCtrlExtractReportCond.setChangedName("111");
            return sameCtrlExtractReportCond;
        }
        if (ChangedOrgTypeEnum.NON_SAME_CTRL_DISPOSE.getCode().equals(sameCtrlChgOrg.getChangedOrgType())) {
            GcOrgBaseClient gcOrgBase = (GcOrgBaseClient)SpringBeanUtils.getBean(GcOrgBaseClient.class);
            GcOrgBaseApiParam gcOrgBaseApiParam = new GcOrgBaseApiParam();
            OrgToJsonVO orgToJsonVO = (OrgToJsonVO)gcOrgBase.getUnitById(gcOrgBaseApiParam).getData();
            changedOrgTitle = orgToJsonVO.getTitle();
        } else {
            GcOrgCacheVO changedOrg = orgCenterService.getOrgByCode(sameCtrlExtractReportCond.getChangedCode());
            if (changedOrg == null) {
                throw new RuntimeException("\u53d8\u52a8\u5355\u4f4d: " + sameCtrlExtractReportCond.getChangedCode() + " \u4e0d\u5b58\u5728");
            }
            changedOrgTitle = changedOrg.getTitle();
        }
        if (sameCtrlChgOrg == null) {
            throw new RuntimeException("\u53d8\u52a8\u5355\u4f4d: " + sameCtrlExtractReportCond.getChangedCode() + "\u53d8\u52a8\u65f6\u95f4\uff1a" + DateUtils.format((Date)sameCtrlExtractReportCond.getChangeDate(), (String)"yyyy-MM-dd") + " \u540c\u63a7\u53d8\u52a8\u4fe1\u606f\u4e0d\u5b58\u5728");
        }
        if (StringUtils.isEmpty((String)sameCtrlChgOrg.getVirtualCode())) {
            throw new RuntimeException("\u5355\u4f4d\uff1a" + sameCtrlChgOrg.getVirtualCode() + " \u65f6\u671f\uff1a" + sameCtrlExtractReportCond.getPeriodStr() + " \u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\uff1a" + sameCtrlExtractReportCond.getOrgType() + "\u4e0d\u5b58\u5728");
        }
        GcOrgCacheVO virtualOrg = orgCenterService.getOrgByCode(sameCtrlChgOrg.getVirtualCode());
        if (virtualOrg == null) {
            throw new RuntimeException("\u865a\u62df\u5355\u4f4d: " + sameCtrlChgOrg.getVirtualCode() + " \u4e0d\u5b58\u5728");
        }
        String reportSystem = this.taskService.getSystemIdByTaskId(sameCtrlExtractReportCond.getTaskId(), sameCtrlExtractReportCond.getPeriodStr());
        if (StringUtils.isEmpty((String)reportSystem)) {
            throw new BusinessRuntimeException("\u4efb\u52a1\u4e0e\u5408\u5e76\u4f53\u7cfb\u5173\u8054\u5173\u7cfb\u4e22\u5931\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
        sameCtrlExtractReportCond.setSystemId(reportSystem);
        sameCtrlExtractReportCond.setDisposalDate(sameCtrlChgOrg.getDisposalDate());
        sameCtrlExtractReportCond.setChangedOrgType(sameCtrlChgOrg.getChangedOrgType());
        sameCtrlExtractReportCond.setChangedName(changedOrgTitle);
        sameCtrlExtractReportCond.setVirtualCode(sameCtrlChgOrg.getVirtualCode());
        return sameCtrlExtractReportCond;
    }

    private Calendar getCalendar(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}

