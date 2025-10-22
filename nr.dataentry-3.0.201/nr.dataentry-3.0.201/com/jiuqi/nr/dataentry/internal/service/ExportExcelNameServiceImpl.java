/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodLanguage
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.bean.ExportRuleSettings;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportExcelNameServiceImpl
implements ExportExcelNameService {
    private static final Logger logger = LoggerFactory.getLogger(ExportExcelNameServiceImpl.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private ISecretLevelService iSecretLevelService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    public static final String SHEET_NAME = "SHEET_NAME";
    public static final String EXCEL_NAME = "EXCEL_NAME";
    public static final String ZIP_NAME = "ZIP_NAME";
    public static final String SEPARATOR_ONE = " ";
    public static final String SEPARATOR_TWO = "_";
    public static final String SEPARATOR_THREE = "&";

    @Override
    public String compileNameInfo(String formKey, JtableContext jtableContext, String tag, boolean isOneForm, String unitViewKey) {
        IEntityRow iEntityRow;
        FormData formDefine;
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        long now = Instant.now().toEpochMilli();
        Date date = new Date(now);
        SimpleDateFormat dateFormatForFolder = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDateFolder = dateFormatForFolder.format(date);
        String separator = this.getSysSeparator();
        String nameInfoS = this.iNvwaSystemOptionService.get("nr-data-entry-export", tag);
        StringBuffer nameInfo = new StringBuffer();
        boolean isGZW = this.iFormTypeApplyService.enableNrFormTypeMgr();
        IEntityRow nameArray = nameInfoS.replace("[", "").replace("]", "").replace("\"", "").split(",");
        if (tag.equals(SHEET_NAME)) {
            formDefine = null;
            formDefine = isOneForm ? this.jtableParamService.getReport(formKey, jtableContext.getFormSchemeKey()) : this.jtableParamService.getReport(formKey, null);
            for (String name : nameArray) {
                if (name.equals("0") && formDefine.getTitle() != null) {
                    nameInfo = nameInfo.append(formDefine.getTitle()).append(separator);
                    continue;
                }
                if (name.equals("1") && formDefine.getTitle() != null) {
                    nameInfo = nameInfo.append(formDefine.getCode()).append(separator);
                    continue;
                }
                if (!name.equals("2") || formDefine.getSerialNumber() == null) continue;
                nameInfo = nameInfo.append(formDefine.getSerialNumber()).append(separator);
            }
        } else if (tag.equals(EXCEL_NAME)) {
            iEntityRow = null;
            IEntityTable iEntityTable = null;
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet());
            String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
            if (jtableContext.getFormSchemeKey() != null) {
                iEntityTable = this.getEntityDataList(jtableContext, jtableContext.getDimensionSet());
                iEntityRow = iEntityTable.findByEntityKey(dwId);
            }
            StringBuffer codeInfo = new StringBuffer("");
            codeInfo = !isGZW && iEntityRow != null ? codeInfo.append(iEntityRow.getCode()) : new StringBuffer(this.iFormTypeApplyService.getEntityRowBizCodeGetter(iEntityTable).getBizCode(iEntityRow, separator));
            for (IEntityRow name : nameArray) {
                if (name.equals("0") && iEntityRow != null && iEntityRow.getTitle() != null) {
                    nameInfo = nameInfo.append(iEntityRow.getTitle()).append(separator);
                    continue;
                }
                if (name.equals("1") && !StringUtils.isEmpty((String)codeInfo.toString())) {
                    nameInfo = nameInfo.append(codeInfo).append(separator);
                    continue;
                }
                if (name.equals("2") && StringUtils.isNotEmpty((String)((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue())) {
                    String period = ((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue();
                    String periodTitle = this.getPeriodTitle(jtableContext.getFormSchemeKey(), period);
                    nameInfo = nameInfo.append(periodTitle).append(separator);
                    continue;
                }
                if (!name.equals("3")) continue;
                TaskDefine queryTaskDefine = this.iRunTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
                nameInfo = nameInfo.append(queryTaskDefine.getTitle()).append(separator);
            }
            if (this.iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
                SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                nameInfo.append(secretLevelInfo.getSecretLevelItem().getTitle()).append(separator);
            }
        } else if (tag.equals(ZIP_NAME)) {
            for (String name : nameArray) {
                if (name.equals("0") && taskDefine.getTitle() != null) {
                    nameInfo = nameInfo.append(taskDefine.getTitle()).append(separator);
                    continue;
                }
                if (name.equals("1") && taskDefine.getTaskCode() != null) {
                    nameInfo = nameInfo.append(taskDefine.getTaskCode()).append(separator);
                    continue;
                }
                if (!nameInfoS.contains("2")) continue;
                nameInfo = nameInfo.append(formatDateFolder).append(separator);
            }
        }
        if (nameInfoS.equals("[]") || nameInfo.toString().equals("")) {
            if (tag.equals(SHEET_NAME)) {
                formDefine = null;
                formDefine = isOneForm ? this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey()) : this.jtableParamService.getReport(jtableContext.getFormKey(), null);
                nameInfo = nameInfo.append(formDefine.getCode()).append(separator).append(formDefine.getTitle()).append(separator);
            }
            if (tag.equals(EXCEL_NAME)) {
                iEntityRow = null;
                IEntityTable iEntityTable = null;
                if (jtableContext.getFormSchemeKey() != null) {
                    iEntityTable = this.getEntityDataList(jtableContext, jtableContext.getDimensionSet());
                    iEntityRow = (IEntityRow)iEntityTable.getAllRows().get(0);
                }
                if (iEntityRow != null && iEntityRow.getTitle() != null) {
                    nameInfo = nameInfo.append(iEntityRow.getTitle()).append(separator);
                }
                if (this.iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
                    SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                    nameInfo.append(secretLevelInfo.getSecretLevelItem().getTitle()).append(separator);
                }
            }
            if (tag.equals(ZIP_NAME)) {
                nameInfo = nameInfo.append(taskDefine.getTitle()).append(separator).append(formatDateFolder).append(separator);
            }
        }
        nameInfo = nameInfo.deleteCharAt(nameInfo.lastIndexOf(separator));
        return nameInfo.toString();
    }

    @Override
    public String compileNameInfoWithSetting(String formKey, JtableContext jtableContext, String tag, boolean isOneForm, String unitViewKey, ExportRuleSettings ruleSettings) {
        IEntityRow iEntityRow;
        FormData formDefine;
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        long now = Instant.now().toEpochMilli();
        Date date = new Date(now);
        SimpleDateFormat dateFormatForFolder = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDateFolder = dateFormatForFolder.format(date);
        String separator = this.getSysSeparator();
        if (ruleSettings != null && ruleSettings.getSeparatorCode() != null) {
            String separatorMessage = ruleSettings.getSeparatorCode();
            separator = SEPARATOR_ONE;
            if (separatorMessage.equals("1")) {
                separator = SEPARATOR_TWO;
            } else if (separatorMessage.equals("2")) {
                separator = SEPARATOR_THREE;
            }
        }
        String nameInfoS = this.iNvwaSystemOptionService.get("nr-data-entry-export", tag);
        StringBuffer nameInfo = new StringBuffer();
        boolean isGZW = this.iFormTypeApplyService.enableNrFormTypeMgr();
        IEntityRow nameArray = nameInfoS.replace("[", "").replace("]", "").replace("\"", "").split(",");
        if (tag.equals(SHEET_NAME)) {
            if (ruleSettings != null && ruleSettings.getSheetName() != null) {
                nameArray = (String[])ruleSettings.getSheetName().stream().toArray(String[]::new);
            }
            formDefine = null;
            formDefine = isOneForm ? this.jtableParamService.getReport(formKey, jtableContext.getFormSchemeKey()) : this.jtableParamService.getReport(formKey, null);
            for (String name : nameArray) {
                if (name.equals("0") && formDefine.getTitle() != null) {
                    nameInfo = nameInfo.append(formDefine.getTitle()).append(separator);
                    continue;
                }
                if (name.equals("1") && formDefine.getTitle() != null) {
                    nameInfo = nameInfo.append(formDefine.getCode()).append(separator);
                    continue;
                }
                if (!name.equals("2") || formDefine.getSerialNumber() == null) continue;
                nameInfo = nameInfo.append(formDefine.getSerialNumber()).append(separator);
            }
        } else if (tag.equals(EXCEL_NAME)) {
            if (ruleSettings != null && ruleSettings.getExcelName() != null) {
                nameArray = (String[])ruleSettings.getExcelName().stream().toArray(String[]::new);
            }
            iEntityRow = null;
            IEntityTable iEntityTable = null;
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet());
            String dwId = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
            if (jtableContext.getFormSchemeKey() != null) {
                iEntityTable = this.getEntityDataList(jtableContext, jtableContext.getDimensionSet());
                iEntityRow = iEntityTable.findByEntityKey(dwId);
            }
            StringBuffer codeInfo = new StringBuffer("");
            codeInfo = !isGZW && iEntityRow != null ? codeInfo.append(iEntityRow.getCode()) : new StringBuffer(this.iFormTypeApplyService.getEntityRowBizCodeGetter(iEntityTable).getBizCode(iEntityRow, separator));
            for (IEntityRow name : nameArray) {
                if (name.equals("0") && iEntityRow != null && iEntityRow.getTitle() != null) {
                    nameInfo = nameInfo.append(iEntityRow.getTitle()).append(separator);
                    continue;
                }
                if (name.equals("1") && !StringUtils.isEmpty((String)codeInfo.toString())) {
                    nameInfo = nameInfo.append(codeInfo).append(separator);
                    continue;
                }
                if (name.equals("2") && StringUtils.isNotEmpty((String)((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue())) {
                    String period = ((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue();
                    String periodTitle = this.getPeriodTitle(jtableContext.getFormSchemeKey(), period);
                    nameInfo = nameInfo.append(periodTitle).append(separator);
                    continue;
                }
                if (!name.equals("3")) continue;
                TaskDefine queryTaskDefine = this.iRunTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
                nameInfo = nameInfo.append(queryTaskDefine.getTitle()).append(separator);
            }
            if (this.iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
                SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                nameInfo.append(secretLevelInfo.getSecretLevelItem().getTitle()).append(separator);
            }
        } else if (tag.equals(ZIP_NAME)) {
            if (ruleSettings != null && ruleSettings.getZipName() != null) {
                nameArray = (String[])ruleSettings.getZipName().stream().toArray(String[]::new);
            }
            for (String name : nameArray) {
                if (name.equals("0") && taskDefine.getTitle() != null) {
                    nameInfo = nameInfo.append(taskDefine.getTitle()).append(separator);
                    continue;
                }
                if (name.equals("1") && taskDefine.getTaskCode() != null) {
                    nameInfo = nameInfo.append(taskDefine.getTaskCode()).append(separator);
                    continue;
                }
                if (!nameInfoS.contains("2")) continue;
                nameInfo = nameInfo.append(formatDateFolder).append(separator);
            }
        }
        if (nameInfoS.equals("[]") || nameInfo.toString().equals("")) {
            if (tag.equals(SHEET_NAME)) {
                formDefine = null;
                formDefine = isOneForm ? this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey()) : this.jtableParamService.getReport(jtableContext.getFormKey(), null);
                nameInfo = nameInfo.append(formDefine.getCode()).append(separator).append(formDefine.getTitle()).append(separator);
            }
            if (tag.equals(EXCEL_NAME)) {
                iEntityRow = null;
                IEntityTable iEntityTable = null;
                if (jtableContext.getFormSchemeKey() != null) {
                    iEntityTable = this.getEntityDataList(jtableContext, jtableContext.getDimensionSet());
                    iEntityRow = (IEntityRow)iEntityTable.getAllRows().get(0);
                }
                if (iEntityRow != null && iEntityRow.getTitle() != null) {
                    nameInfo = nameInfo.append(iEntityRow.getTitle()).append(separator);
                }
                if (this.iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
                    SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                    nameInfo.append(secretLevelInfo.getSecretLevelItem().getTitle()).append(separator);
                }
            }
            if (tag.equals(ZIP_NAME)) {
                nameInfo = nameInfo.append(taskDefine.getTitle()).append(separator).append(formatDateFolder).append(separator);
            }
        }
        nameInfo = nameInfo.deleteCharAt(nameInfo.lastIndexOf(separator));
        return nameInfo.toString();
    }

    public IEntityTable getEntityDataList(JtableContext jtableContext, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            String period = dimensionValueSet.get("DATATIME").getValue();
            DimensionValueSet deDimensionValueSet = new DimensionValueSet();
            deDimensionValueSet.setValue("DATATIME", (Object)period);
            iEntityQuery.setMasterKeys(deDimensionValueSet);
            ExecutorContext oldContext = this.jtableDataEngineService.getExecutorContext(jtableContext);
            com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(oldContext.getRuntimeController());
            executorContext.setDefaultGroupName(oldContext.getDefaultGroupName());
            executorContext.setJQReportModel(oldContext.isJQReportModel());
            executorContext.setVarDimensionValueSet(oldContext.getVarDimensionValueSet());
            executorContext.setEnv(oldContext.getEnv());
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, executorContext, entityViewDefine, formSchemeDefine.getKey()).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iEntityTable;
    }

    @Override
    public String getSysSeparator() {
        String separatorMessage = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        String separator = SEPARATOR_ONE;
        if (separatorMessage.equals("1")) {
            separator = SEPARATOR_TWO;
        } else if (separatorMessage.equals("2")) {
            separator = SEPARATOR_THREE;
        }
        return separator;
    }

    @Override
    public String getSysOptionOfExcelName() {
        String nameInfoS = this.iNvwaSystemOptionService.get("nr-data-entry-export", EXCEL_NAME);
        if (!nameInfoS.equals("[]")) {
            return nameInfoS;
        }
        return "-1";
    }

    @Override
    public String getPeriodTitle(String formSchemeKey, String period) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        if (DataEntryUtil.isChinese()) {
            periodWrapper.setLanguage(PeriodLanguage.Chinese);
        } else {
            periodWrapper.setLanguage(PeriodLanguage.English);
        }
        return periodProvider.getPeriodTitle(periodWrapper);
    }
}

