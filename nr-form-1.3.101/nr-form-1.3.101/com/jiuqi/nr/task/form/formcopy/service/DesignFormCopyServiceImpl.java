/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.i18n.dao.DesignDataSchemeI18nDao
 *  com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO
 *  com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.facade.formula.FormulaCondition
 *  com.jiuqi.nr.definition.facade.formula.FormulaConditionLink
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFieldInfo
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFormInfo
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionContext
 *  com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.provider.ConditionalStyleProvider
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.option.internal.FieldReuseOption
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.GridDataTransform
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.definition.util.TitleAndKey
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 *  com.jiuqi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.form.formcopy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.i18n.dao.DesignDataSchemeI18nDao;
import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeI18nDO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFieldInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFormInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionContext;
import com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.provider.ConditionalStyleProvider;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.option.internal.FieldReuseOption;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.GridDataTransform;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.TitleAndKey;
import com.jiuqi.nr.task.form.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.task.form.formcopy.FormCopyDeleteFormRegionDefine;
import com.jiuqi.nr.task.form.formcopy.FormCopyParams;
import com.jiuqi.nr.task.form.formcopy.FormCopyRecord;
import com.jiuqi.nr.task.form.formcopy.FormCopyRecordPush;
import com.jiuqi.nr.task.form.formcopy.FormSyncParams;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResultAll;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyDataModelInfo;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyInfo;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyPushRecord;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyRecord;
import com.jiuqi.nr.task.form.formcopy.bean.impl.FormCopyAttSchemeInfoImpl;
import com.jiuqi.nr.task.form.formcopy.bean.impl.FormCopyDataModelInfoImpl;
import com.jiuqi.nr.task.form.formcopy.bean.impl.FormCopyInfoImpl;
import com.jiuqi.nr.task.form.formcopy.common.DesignDataRegion;
import com.jiuqi.nr.task.form.formcopy.common.FormCopyContext;
import com.jiuqi.nr.task.form.formcopy.common.FormCopyExceptionEnum;
import com.jiuqi.nr.task.form.formcopy.common.SchemeType;
import com.jiuqi.nr.task.form.formcopy.dao.FormCopyDataModelInfoDao;
import com.jiuqi.nr.task.form.formcopy.dao.FormCopyInfoDao;
import com.jiuqi.nr.task.form.formcopy.dao.FormCopyRecordDao;
import com.jiuqi.nr.task.form.formcopy.dao.FormCopyRecordPushDao;
import com.jiuqi.nr.task.form.formcopy.dao.FormCopySchemeInfoDao;
import com.jiuqi.nr.task.form.formcopy.service.DesignFormCopyHelper;
import com.jiuqi.nr.task.form.formcopy.utils.FormCopyUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import com.jiuqi.util.OrderGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DesignFormCopyServiceImpl
implements IDesignFormCopyService {
    @Autowired
    private FormCopySchemeInfoDao formCopySchemeInfoDao;
    @Autowired
    private FormCopyInfoDao formCopyInfoDao;
    @Autowired
    private FormCopyRecordDao formCopyRecordDao;
    @Autowired
    private FormCopyRecordPushDao formCopyRecordPushDao;
    @Autowired
    private FormCopyDataModelInfoDao formCopyDataModelInfoDao;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private DesignFormDefineService formDefineService;
    @Autowired
    private DesignFormCopyHelper designFormCopyHelper;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IDesignTimeFormulaController designTimeFormulaController;
    @Autowired
    private ConditionalStyleProvider conditionalStyleProvider;
    @Autowired
    private QuickReportModelService quickReportModelService;
    @Autowired
    private ITaskOptionController taskOptionController;
    private static final String FIELD_REUSE_OPTION_VALUE = "1";
    private static final Logger logger = LoggerFactory.getLogger(DesignFormCopyServiceImpl.class);
    @Autowired
    private DesParamLanguageDao paramLanguageDao;
    @Autowired
    private DesignBigDataTableDao bigDataTableDao;
    @Autowired
    private DesignDataSchemeI18nDao dataSchemeI18nDao;

    @Override
    public List<IFormCopyInfo> getFormCopyInfoBySchemeKey(String formSchemeKey) {
        return this.formCopyInfoDao.getByFormSchemeKey(formSchemeKey);
    }

    @Override
    public List<IFormCopyInfo> getFormCopyInfoBySrcSchemeKey(String srcFormSchemeKey) {
        return this.formCopyInfoDao.getBySrcFormSchemeKey(srcFormSchemeKey);
    }

    @Override
    public List<IFormCopyInfo> getFormCopyInfoByFormKeys(List<String> formKeys) {
        if (null == formKeys || formKeys.isEmpty()) {
            return Collections.emptyList();
        }
        return this.formCopyInfoDao.getByFormKeys(formKeys);
    }

    @Override
    public List<IFormCopyInfo> getFormCopyInfoBySchemeKey(String formSchemeKey, String srcFormSchemeKey) {
        return this.formCopyInfoDao.getByFormSchemeKey(formSchemeKey, srcFormSchemeKey);
    }

    @Override
    public void deleteCopyFormInfo(String formKey) throws JQException {
        try {
            this.formCopyInfoDao.deleteByFormKey(formKey);
        }
        catch (BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_001, e);
        }
    }

    @Override
    public void deleteCopyFormInfosbyGroup(String formGroupKey) throws JQException {
        try {
            List designFormDefineList = this.designTimeViewController.listFormByGroup(formGroupKey);
            for (DesignFormDefine designFormDefine : designFormDefineList) {
                this.formCopyInfoDao.deleteByFormKey(designFormDefine.getKey());
            }
        }
        catch (BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_001, e);
        }
    }

    @Override
    public void deleteCopyFormInfos(String formSchemeKey) throws JQException {
        try {
            this.formCopyInfoDao.deleteByFormSchemeKey(formSchemeKey);
        }
        catch (BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_001, e);
        }
    }

    @Override
    public List<IFormCopyAttSchemeInfo> getFormCopySchemeInfo(String formSchemeKey, String srcFormSchemeKey) {
        return this.formCopySchemeInfoDao.getByFormSchemeKey(formSchemeKey, srcFormSchemeKey);
    }

    @Override
    public void deleteFormCopySchemeInfo(String formSchemeKey) throws JQException {
        try {
            this.formCopySchemeInfoDao.deleteByFormSchemeKey(formSchemeKey);
        }
        catch (BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_002, e);
        }
    }

    @Override
    public List<FormCopyRecord> getFormCopyRecords(String formSchemeKey) {
        List<IFormCopyRecord> records = this.formCopyRecordDao.getByFormSchemeKey(formSchemeKey);
        if (null == records || records.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<FormCopyRecord> result = new ArrayList<FormCopyRecord>();
        for (IFormCopyRecord r : records) {
            try {
                result.add(new FormCopyRecord(r));
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public void saveFormCopyRecord(FormCopyRecord formCopyRecord) throws JQException {
        try {
            if (StringUtils.hasText(formCopyRecord.getKey())) {
                this.formCopyRecordDao.update(formCopyRecord.toIFormCopyScheme());
            } else {
                List<IFormCopyRecord> records = this.formCopyRecordDao.getByFormSchemeKey(formCopyRecord.getFormSchemeKey());
                if (records.size() >= 10) {
                    ArrayList<String> recordKeys = new ArrayList<String>();
                    for (int i = 9; i < records.size(); ++i) {
                        recordKeys.add(records.get(records.size() - i).getKey());
                    }
                    this.formCopyRecordDao.delete(recordKeys.toArray());
                }
                formCopyRecord.setKey(UUIDUtils.getKey());
                this.formCopyRecordDao.insert(formCopyRecord.toIFormCopyScheme());
            }
        }
        catch (JsonProcessingException | BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_003, e);
        }
    }

    @Override
    public List<FormCopyRecordPush> getFormCopyPushRecords(String srcFormSchemeKey) {
        List<IFormCopyPushRecord> records = this.formCopyRecordPushDao.getByFormSchemeKey(srcFormSchemeKey);
        if (null == records || records.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<FormCopyRecordPush> result = new ArrayList<FormCopyRecordPush>();
        for (IFormCopyPushRecord r : records) {
            try {
                result.add(new FormCopyRecordPush(r));
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public int saveFormCopyPushRecord(FormCopyRecordPush formCopyRecordPush) throws JQException {
        try {
            if (StringUtils.hasText(formCopyRecordPush.getKey())) {
                int update = this.formCopyRecordPushDao.update(formCopyRecordPush.toFormCopyPushRecord());
                return update;
            }
            List<IFormCopyPushRecord> records = this.formCopyRecordPushDao.getByFormSchemeKey(formCopyRecordPush.getSrcFormSchemeKey());
            if (records.size() >= 10) {
                ArrayList<String> recordKeys = new ArrayList<String>();
                for (int i = 9; i < records.size(); ++i) {
                    recordKeys.add(records.get(records.size() - i - 1).getKey());
                }
                this.formCopyRecordPushDao.delete(recordKeys.toArray());
            }
            formCopyRecordPush.setKey(UUIDUtils.getKey());
            int insert = this.formCopyRecordPushDao.insert(formCopyRecordPush.toFormCopyPushRecord());
            return insert;
        }
        catch (JsonProcessingException | BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_003, e);
        }
    }

    private FormCopyContext createCopyContext(String srcFormSchemeKey, String formSchemeKey) {
        boolean result;
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        boolean matchByCode = false;
        FieldReuseOption fieldReuseOptionBean = (FieldReuseOption)SpringBeanUtils.getBean(FieldReuseOption.class);
        if (fieldReuseOptionBean != null) {
            matchByCode = FIELD_REUSE_OPTION_VALUE.equals(this.taskOptionController.getValue(task.getKey(), fieldReuseOptionBean.getKey()));
        }
        String dataSchemeKey = task.getDataScheme();
        DesignFormSchemeDefine srcFormScheme = this.designTimeViewController.getFormScheme(srcFormSchemeKey);
        DesignTaskDefine srcTask = this.designTimeViewController.getTask(srcFormScheme.getTaskKey());
        String srcDataSchemeKey = srcTask.getDataScheme();
        FormCopyContext context = new FormCopyContext(srcFormSchemeKey, srcFormScheme.getTaskKey(), formSchemeKey, formScheme.getTaskKey(), matchByCode);
        StringBuilder formCopyMessage = context.getFormCopyMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formCopyMessage.append("\u5f00\u59cb\u65f6\u95f4\u4e3a ").append(LocalDateTime.now().format(formatter)).append("\u3002").append("\r\n");
        if (matchByCode) {
            formCopyMessage.append("\u8981\u590d\u5236\u62a5\u8868\u7684\u4efb\u52a1\u5f00\u542f\u4e86\u6307\u6807\u590d\u7528\u3002").append("\r\n");
            logger.info("\u4efb\u52a1\uff1a{}\u4e0b\u7684\u62a5\u8868\u65b9\u6848\uff1a{}\u8fdb\u884c\u62a5\u8868\u590d\u5236\uff0c\u5176\u62a5\u8868\u5173\u8054\u6307\u6807\u662f\u6309\u7167\u4ee3\u7801\u540c\u6b65", (Object)task.getTitle(), (Object)formScheme.getTitle());
        } else {
            formCopyMessage.append("\u8981\u590d\u5236\u62a5\u8868\u7684\u4efb\u52a1\u6ca1\u6709\u5f00\u542f\u6307\u6807\u590d\u7528\u3002").append("\r\n");
            logger.info("\u4efb\u52a1\uff1a{}\u4e0b\u7684\u62a5\u8868\u65b9\u6848\uff1a{}\u8fdb\u884c\u62a5\u8868\u590d\u5236\uff0c\u5176\u62a5\u8868\u5173\u8054\u6307\u6807\u662f\u6309\u7167\u4e3b\u952e\u540c\u6b65", (Object)task.getTitle(), (Object)formScheme.getTitle());
        }
        boolean bl = result = !dataSchemeKey.equals(srcDataSchemeKey);
        if (result) {
            DesignDataScheme srcDataScheme = this.iDesignDataSchemeService.getDataScheme(srcDataSchemeKey);
            String srcPrefix = srcDataScheme.getPrefix();
            DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(dataSchemeKey);
            String prefix = dataScheme.getPrefix();
            formCopyMessage.append("\u6765\u6e90\u4efb\u52a1\u4e3a\uff1a").append(srcTask.getTitle()).append("\uff0c\u6765\u6e90\u62a5\u8868\u65b9\u6848\u4e3a\uff1a").append(srcFormScheme.getTitle()).append("\uff0c\u6765\u6e90\u6570\u636e\u65b9\u6848\u4e3a\uff1a").append(srcDataScheme.getTitle()).append("\r\n");
            formCopyMessage.append("\u76ee\u6807\u4efb\u52a1\u4e3a\uff1a").append(task.getTitle()).append("\uff0c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u4e3a\uff1a").append(formScheme.getTitle()).append("\uff0c\u76ee\u6807\u6570\u636e\u65b9\u6848\u4e3a\uff1a").append(dataScheme.getTitle()).append("\r\n");
            formCopyMessage.append("\u6765\u6e90\u548c\u76ee\u6807\u76ee\u6807\u6570\u636e\u65b9\u6848\u4e0d\u4e00\u81f4\uff0c\u5c06\u4f1a\u590d\u5236\u6570\u636e\u6a21\u578b\uff01").append("\r\n");
            context.setSrcFormSchemeTitle(srcFormScheme.getTitle());
            context.setCopyDataModel(result, srcDataSchemeKey, dataSchemeKey, formScheme.getTitle(), srcPrefix, prefix);
            List<IFormCopyDataModelInfo> copyDataModelInfo = this.formCopyDataModelInfoDao.getBySchemeKey(dataSchemeKey);
            if (null != copyDataModelInfo && !copyDataModelInfo.isEmpty()) {
                this.loadExsitDataModelInfo(context, copyDataModelInfo);
            }
            List allDataTables = this.iDesignDataSchemeService.getAllDataTable(context.getDataSchemeKey());
            List allDataFields = this.iDesignDataSchemeService.getAllDataField(context.getDataSchemeKey());
            context.addAllDataTable(allDataTables);
            context.addAllDataField(allDataFields);
            if (null != allDataFields && !allDataFields.isEmpty()) {
                this.loadFieldCode(context, allDataFields);
            }
        } else {
            formCopyMessage.append("\u6765\u6e90\u4efb\u52a1\u4e3a\uff1a").append(srcTask.getTitle()).append("\uff0c\u6765\u6e90\u62a5\u8868\u65b9\u6848\u4e3a\uff1a").append(srcFormScheme.getTitle()).append("\r\n");
            formCopyMessage.append("\u76ee\u6807\u4efb\u52a1\u4e3a\uff1a").append(task.getTitle()).append("\uff0c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u4e3a\uff1a").append(formScheme.getTitle()).append("\r\n");
            formCopyMessage.append("\u6765\u6e90\u548c\u76ee\u6807\u76ee\u6807\u6570\u636e\u65b9\u6848\u4e00\u81f4\uff0c\u4e0d\u4f1a\u590d\u5236\u6570\u636e\u6a21\u578b\uff01").append("\r\n");
        }
        return context;
    }

    private void loadExsitDataModelInfo(FormCopyContext context, List<IFormCopyDataModelInfo> copyDataModelInfo) {
        HashMap<String, String> copiedDataTableInfo = new HashMap<String, String>();
        HashMap<String, String> copiedDataFieldInfo = new HashMap<String, String>();
        for (IFormCopyDataModelInfo iFormCopyDataModelInfo : copyDataModelInfo) {
            copiedDataTableInfo.put(iFormCopyDataModelInfo.getSrcDataTableKey(), iFormCopyDataModelInfo.getDataTableKey());
            copiedDataFieldInfo.put(iFormCopyDataModelInfo.getSrcDataFieldKey(), iFormCopyDataModelInfo.getDataFieldKey());
        }
        context.setCopiedDataTableInfo(copiedDataTableInfo);
        context.setCopiedDataFieldInfo(copiedDataFieldInfo);
    }

    private void loadFieldCode(FormCopyContext context, List<DesignDataField> allDataFields) {
        HashMap<String, Set<String>> fieldCodeSet = new HashMap<String, Set<String>>();
        HashSet<String> zbFieldCodeSet = new HashSet<String>();
        for (DesignDataField dataField : allDataFields) {
            if (DataFieldKind.FIELD == dataField.getDataFieldKind()) {
                if (fieldCodeSet.containsKey(dataField.getDataTableKey())) {
                    ((Set)fieldCodeSet.get(dataField.getDataTableKey())).add(dataField.getCode());
                    continue;
                }
                HashSet<String> codeSet = new HashSet<String>();
                codeSet.add(dataField.getCode());
                fieldCodeSet.put(dataField.getDataTableKey(), codeSet);
                continue;
            }
            zbFieldCodeSet.add(dataField.getCode());
        }
        context.setFieldCodeSet(fieldCodeSet);
        context.setZbFieldCodeSet(zbFieldCodeSet);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public FormulaSyncResultAll doCopy(String srcFormSchemeKey, String formSchemeKey, List<FormCopyParams> formCopyParams, FormCopyAttSchemeMap attScheemMap) throws Exception {
        if (null == formCopyParams || formCopyParams.isEmpty()) {
            return null;
        }
        FormulaSyncResultAll formulaSyncResult = this.doCopy(srcFormSchemeKey, formSchemeKey, formCopyParams, attScheemMap, true);
        return formulaSyncResult;
    }

    private FormulaSyncResultAll doCopy(String srcFormSchemeKey, String formSchemeKey, List<FormCopyParams> formCopyParams, FormCopyAttSchemeMap attScheemMap, boolean updateAttSchemeMapping) throws Exception {
        return this.doCopy(srcFormSchemeKey, formSchemeKey, formCopyParams, null, attScheemMap, updateAttSchemeMapping, null, 0.0, 0.0, null);
    }

    private FormulaSyncResultAll doCopy(String srcFormSchemeKey, String formSchemeKey, List<FormCopyParams> formCopyParams, Map<String, String> formMap, FormCopyAttSchemeMap attScheemMap, boolean updateAttSchemeMapping, AsyncTaskMonitor monitor, double startProgress, double thisProgress, Map<String, List<FormCopyDeleteFormRegionDefine>> formKeyToFormRegionMap) throws Exception {
        List<FormulaSyncResult> formulaSyncResult = null;
        Map<String, String> newFormLink = null;
        FormCopyUtil.setProgressAndMessage(monitor, startProgress, "\u5f00\u59cb\u5904\u7406\u8868\u5355");
        FormulaSyncResultAll formulaSyncResultAll = null;
        try {
            FormCopyContext context = this.createCopyContext(srcFormSchemeKey, formSchemeKey);
            context.setFormKeyToFormRegionMap(formKeyToFormRegionMap);
            logger.info("\u62a5\u8868\u590d\u5236\u5f00\u59cb\uff1a\u6765\u6e90\u62a5\u8868\u65b9\u6848{} -> \u76ee\u6807\u62a5\u8868\u65b9\u6848{}", (Object)context.getSrcFormSchemeKey(), (Object)context.getFormSchemeKey());
            newFormLink = this.doCopyForm(context, formCopyParams, monitor, startProgress, thisProgress * 0.7);
            this.saveCopyFormMapping(context);
            if (null != attScheemMap) {
                FormCopyUtil.setProgressAndMessage(monitor, startProgress + 0.7 * thisProgress, "\u5f00\u59cb\u590d\u5236\u516c\u5f0f\u4e0e\u6253\u5370\u65b9\u6848");
                formulaSyncResult = this.doCopyAttParams(context, formMap, attScheemMap, updateAttSchemeMapping);
                FormCopyUtil.setProgressAndMessage(monitor, startProgress + 0.9 * thisProgress, "\u516c\u5f0f\u4e0e\u6253\u5370\u65b9\u6848\u590d\u5236\u6210\u529f");
            }
            FormCopyUtil.setProgressAndMessage(monitor, startProgress + 0.9 * thisProgress, "\u5f00\u59cb\u590d\u5236\u591a\u8bed\u8a00\u4fe1\u606f");
            this.doCopyI18n(context);
            String message = context.getSrcFormSchemeTitle() + " \u590d\u5236\u62a5\u8868\u5230 " + context.getFormSchemeTitle() + " \u6210\u529f";
            logger.info(message);
            FormCopyUtil.setProgressAndMessage(monitor, startProgress + thisProgress, message);
            context.getFormCopyMessage().append("\u7ed3\u675f\u65f6\u95f4\u4e3a ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\u3002").append("\r\n").append("\r\n").append("\r\n");
            formulaSyncResultAll = new FormulaSyncResultAll(formulaSyncResult, newFormLink, context.getFormCopyMessage());
            logger.info(context.getFormCopyMessage().toString());
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u590d\u5236\u6267\u884c\u5f02\u5e38", e);
            throw new Exception(e.getMessage());
        }
        return formulaSyncResultAll;
    }

    private List<FormulaSyncResult> doCopyAttParams(FormCopyContext context, Map<String, String> formMap, FormCopyAttSchemeMap attScheemMap, boolean updateAttSchemeMapping) throws Exception {
        Map<String, String> printSchemeMap;
        StringBuilder formulaAndPrintMessage = new StringBuilder();
        String formSchemeKey = context.getFormSchemeKey();
        String srcFormSchemeKey = context.getSrcFormSchemeKey();
        ArrayList<IFormCopyAttSchemeInfo> attSchemeInfos = new ArrayList<IFormCopyAttSchemeInfo>();
        if (null == formMap || formMap.isEmpty()) {
            formMap = context.getFormInfo().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> ((DesignFormDefine)e.getValue()).getKey()));
        }
        if (null != (printSchemeMap = attScheemMap.getPrintSchemeMap()) && !printSchemeMap.isEmpty()) {
            this.doSyncPrintTemplate(context, formMap, printSchemeMap, formulaAndPrintMessage);
            for (Map.Entry<String, String> entry : printSchemeMap.entrySet()) {
                context.getI18nResourceMap().put(entry.getKey(), entry.getValue());
                attSchemeInfos.add(this.createIFormCopyAttSchemeInfo(formSchemeKey, srcFormSchemeKey, entry.getValue(), entry.getKey(), SchemeType.PRINT_SCHEME));
            }
        }
        List<FormulaSyncResult> formulaSyncResult = null;
        HashMap<String, String> allFormulaSchemeMap = new HashMap<String, String>(attScheemMap.getFormulaSchemeMap());
        if (!allFormulaSchemeMap.isEmpty()) {
            formulaSyncResult = this.doSyncFormulaAndTrans(context, formSchemeKey, formMap, allFormulaSchemeMap, attScheemMap, formulaAndPrintMessage);
            SchemeType schemType = null;
            for (Map.Entry entry : allFormulaSchemeMap.entrySet()) {
                schemType = SchemeType.FORMULA_SCHEME;
                attSchemeInfos.add(this.createIFormCopyAttSchemeInfo(formSchemeKey, srcFormSchemeKey, (String)entry.getValue(), (String)entry.getKey(), schemType));
            }
        }
        if (updateAttSchemeMapping) {
            this.formCopySchemeInfoDao.deleteByFormSchemeKey(formSchemeKey, srcFormSchemeKey);
            if (!attSchemeInfos.isEmpty()) {
                this.formCopySchemeInfoDao.insert(attSchemeInfos.toArray());
            }
        }
        StringBuilder formCopyMessage = context.getFormCopyMessage();
        formCopyMessage.append((CharSequence)formulaAndPrintMessage).append("\r\n");
        return formulaSyncResult;
    }

    private <K, V> Map<K, V> mergeMap(Collection<Map<K, V>> maps) {
        HashMap<K, V> mergeMap = new HashMap<K, V>();
        if (null != maps) {
            for (Map<K, V> map : maps) {
                if (null == map || map.isEmpty()) continue;
                mergeMap.putAll(map);
            }
        }
        return mergeMap;
    }

    private IFormCopyAttSchemeInfo createIFormCopyAttSchemeInfo(String formSchemeKey, String srcFormSchemeKey, String schemeKey, String srcSchemeKey, SchemeType schemeType) {
        FormCopyAttSchemeInfoImpl info = new FormCopyAttSchemeInfoImpl();
        info.setKey(UUIDUtils.getKey());
        info.setFormSchemeKey(formSchemeKey);
        info.setSrcFormSchemeKey(srcFormSchemeKey);
        info.setSchemeKey(schemeKey);
        info.setSrcSchemeKey(srcSchemeKey);
        info.setSchemeType(schemeType);
        return info;
    }

    private void saveCopyFormMapping(FormCopyContext context) throws Exception {
        Map<String, Date> updateTimeInfo = this.designTimeViewController.listFormByFormScheme(context.getFormSchemeKey()).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IMetaItem::getUpdateTime));
        ArrayList<IFormCopyInfo> formCopyInfos = new ArrayList<IFormCopyInfo>();
        for (String srcFormKey : context.getFormInfo().keySet()) {
            FormCopyInfoImpl formCopyInfo = new FormCopyInfoImpl();
            formCopyInfo.setFormKey(context.getFormInfo().get(srcFormKey).getKey());
            formCopyInfo.setFormSchemeKey(context.getFormSchemeKey());
            formCopyInfo.setSrcFormKey(srcFormKey);
            formCopyInfo.setSrcFormSchemeKey(context.getSrcFormSchemeKey());
            formCopyInfo.setUpdateTime(updateTimeInfo.get(formCopyInfo.getFormKey()));
            formCopyInfos.add(formCopyInfo);
        }
        this.formCopyInfoDao.saveFormCopyInfos(formCopyInfos);
        if (context.isCopyDataModel() && !context.getDataFieldInfo().isEmpty()) {
            this.copyDataModelRecord(context.getDataFieldInfo());
        }
        if (context.isCopyDataModel() && !context.getDataFieldCopyRecordInfo().isEmpty()) {
            this.copyDataModelRecord(context.getDataFieldCopyRecordInfo());
        }
    }

    private void copyDataModelRecord(Map<DesignDataField, DesignDataField> copyRecode) throws Exception {
        ArrayList<IFormCopyDataModelInfo> dataModelInfo = new ArrayList<IFormCopyDataModelInfo>();
        ArrayList<String> newFieldKeys = new ArrayList<String>();
        for (Map.Entry<DesignDataField, DesignDataField> entry : copyRecode.entrySet()) {
            dataModelInfo.add(this.createFormCopyDataModelInfo(entry.getKey(), entry.getValue()));
            newFieldKeys.add(entry.getValue().getKey());
        }
        this.formCopyDataModelInfoDao.deleteByFieldKey(newFieldKeys);
        this.formCopyDataModelInfoDao.insert(dataModelInfo.toArray());
    }

    private IFormCopyDataModelInfo createFormCopyDataModelInfo(DesignDataField srcDataField, DesignDataField dataField) {
        FormCopyDataModelInfoImpl info = new FormCopyDataModelInfoImpl();
        info.setDataFieldKey(dataField.getKey());
        info.setDataTableKey(dataField.getDataTableKey());
        info.setDataSchemeKey(dataField.getDataSchemeKey());
        info.setSrcDataFieldKey(srcDataField.getKey());
        info.setSrcDataTableKey(srcDataField.getDataTableKey());
        return info;
    }

    private void doSyncPrintTemplate(FormCopyContext context, Map<String, String> formMap, Map<String, String> printSchemeMap, StringBuilder formulaAndPrintMessage) throws Exception {
        if (null == printSchemeMap || printSchemeMap.isEmpty() || null == formMap || formMap.isEmpty()) {
            return;
        }
        String formSchemeKey = context.getFormSchemeKey();
        logger.info("\u540c\u6b65\u6253\u5370\u6a21\u677f\u5f00\u59cb");
        Map<String, DesignPrintTemplateSchemeDefine> srcKeyToDesPrintTemplateSchemeDefineMap = this.doCopyPrintScheme(context, printSchemeMap, formulaAndPrintMessage);
        for (Map.Entry<String, String> printSchemeEntry : printSchemeMap.entrySet()) {
            String printSchemeKey = printSchemeEntry.getValue();
            ArrayList<String> commonCodes = new ArrayList<String>();
            ArrayList<String> insertPrintTemplateKey = new ArrayList<String>();
            for (Map.Entry<String, String> formEntry : formMap.entrySet()) {
                DesignPrintTemplateDefine printTemplate = this.designTimePrintController.getPrintTemplateBySchemeAndForm(printSchemeEntry.getKey(), formEntry.getKey());
                this.designTimePrintController.deletePrintTemplateBySchemeAndForm(printSchemeKey, formEntry.getValue());
                if (null != printTemplate) {
                    DesignPrintTemplateDefine newPrintTemplate = this.designTimePrintController.copyPrintTemplate(printTemplate, printSchemeKey, formEntry.getValue());
                    insertPrintTemplateKey.add(formEntry.getKey());
                    this.designTimePrintController.insertPrintTemplate(new DesignPrintTemplateDefine[]{newPrintTemplate});
                    commonCodes.add(newPrintTemplate.getComTemCode());
                }
                DesignPrintSettingDefine printSetting = this.designTimePrintController.getPrintSettingDefine(printSchemeEntry.getKey(), formEntry.getKey());
                this.designTimePrintController.deletePrintSettingDefine(printSchemeKey, formEntry.getValue());
                if (null == printSetting) continue;
                DesignPrintSettingDefine newPrintSetting = this.designTimePrintController.copyPrintSetting(printSetting, printSchemeKey, formEntry.getValue());
                this.designTimePrintController.insertPrintSettingDefine(newPrintSetting);
            }
            if (!commonCodes.isEmpty()) {
                List srcComTemDefines = this.designTimePrintController.listPrintComTemByScheme(printSchemeEntry.getKey());
                Map desComTemDefines = this.designTimePrintController.listPrintComTemByScheme(printSchemeKey).stream().collect(Collectors.toMap(PrintComTemDefine::getCode, Function.identity()));
                DesignPrintComTemDefine define = null;
                ArrayList<DesignPrintComTemDefine> addComTemDefine = new ArrayList<DesignPrintComTemDefine>();
                ArrayList<DesignPrintComTemDefine> updateComTemDefine = new ArrayList<DesignPrintComTemDefine>();
                for (DesignPrintComTemDefine comTemDefine : srcComTemDefines) {
                    if (!commonCodes.contains(comTemDefine.getCode())) continue;
                    define = (DesignPrintComTemDefine)desComTemDefines.get(comTemDefine.getCode());
                    if (null == define) {
                        define = this.designTimePrintController.copyPrintComTem(comTemDefine, printSchemeKey);
                        addComTemDefine.add(define);
                        continue;
                    }
                    define = this.designTimePrintController.copyPrintComTem(comTemDefine, printSchemeKey);
                    updateComTemDefine.add(define);
                }
                if (!CollectionUtils.isEmpty(addComTemDefine)) {
                    this.designTimePrintController.insertPrintComTem(addComTemDefine);
                }
                if (!CollectionUtils.isEmpty(updateComTemDefine)) {
                    this.designTimePrintController.updatePrintComTem(updateComTemDefine);
                }
            }
            DesignPrintTemplateSchemeDefine srcPrintTemplateScheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeEntry.getKey());
            if (insertPrintTemplateKey.size() > 0) {
                DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine = srcKeyToDesPrintTemplateSchemeDefineMap.get(printSchemeEntry.getKey());
                if (designPrintTemplateSchemeDefine != null) {
                    formulaAndPrintMessage.append("\u6253\u5370\u65b9\u6848 ").append(srcPrintTemplateScheme.getTitle()).append("\u590d\u5236\u6253\u5370\u6a21\u677f\u5230\u65b9\u6848 ").append(designPrintTemplateSchemeDefine.getTitle()).append("\u4e0b\u7684\u62a5\u8868\uff1a");
                    Map<String, DesignFormDefine> formInfo = context.getFormInfo();
                    for (String srcFormKey : insertPrintTemplateKey) {
                        DesignFormDefine desFormDefine = formInfo.get(srcFormKey);
                        if (desFormDefine == null) continue;
                        formulaAndPrintMessage.append(desFormDefine.getTitle()).append("\u3001");
                    }
                }
                formulaAndPrintMessage.append("\u590d\u5236\u4e86\u6253\u5370\u6a21\u677f").append("\r\n");
                continue;
            }
            formulaAndPrintMessage.append("\u6253\u5370\u65b9\u6848 ").append(srcPrintTemplateScheme.getTitle()).append(" \u4e0b\u6ca1\u6709\u6253\u5370\u6a21\u677f\u8fdb\u884c\u590d\u5236").append("\r\n");
        }
        if (printSchemeMap.isEmpty()) {
            logger.info("\u62a5\u8868\u65b9\u6848" + formSchemeKey + "\u8981\u540c\u6b65\u7684\u540c\u6b65\u6253\u5370\u6a21\u677f\u4e3a\u7a7a");
        }
        logger.info("\u540c\u6b65\u6253\u5370\u6a21\u677f\u7ed3\u675f");
    }

    private Map<String, DesignPrintTemplateSchemeDefine> doCopyPrintScheme(FormCopyContext context, Map<String, String> printSchemeMap, StringBuilder formulaAndPrintMessage) throws Exception {
        List<Object> addPrintSchemeDefine = new ArrayList();
        ArrayList<DesignPrintTemplateSchemeDefine> updatePrintSchemeDefine = new ArrayList<DesignPrintTemplateSchemeDefine>();
        String formSchemeKey = context.getFormSchemeKey();
        HashSet<String> printSchemeKeySet = new HashSet<String>(printSchemeMap.keySet());
        boolean hasMessage = false;
        HashMap<String, DesignPrintTemplateSchemeDefine> printTemplateSchemeDefineMap = new HashMap<String, DesignPrintTemplateSchemeDefine>();
        for (String string : printSchemeKeySet) {
            DesignPrintTemplateSchemeDefine desTemplateScheme = null;
            DesignPrintTemplateSchemeDefine srcTemplateScheme = this.designTimePrintController.getPrintTemplateScheme(string);
            String printSchemeKey = printSchemeMap.get(string);
            if (!StringUtils.hasText(printSchemeKey)) {
                desTemplateScheme = this.designTimePrintController.copyPrintTemplateScheme(srcTemplateScheme, context.getTaskKey(), formSchemeKey);
                addPrintSchemeDefine.add(desTemplateScheme);
                printSchemeMap.put(string, desTemplateScheme.getKey());
                formulaAndPrintMessage.append("\u65b0\u589e\u6253\u5370\u65b9\u6848 ").append(desTemplateScheme.getTitle());
                hasMessage = true;
            } else {
                desTemplateScheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeKey);
                if (desTemplateScheme == null) {
                    printSchemeMap.remove(string);
                    logger.info("\u540c\u6b65\u6253\u5370\u6a21\u677f\uff1a\u76ee\u6807\u6253\u5370\u65b9\u6848 ", (Object)(printSchemeKey + " \u67e5\u8be2\u4e3a\u7a7a\uff0c\u53ef\u80fd\u5df2\u5220\u9664\uff0c\u8df3\u8fc7\u6b64\u6b21\u540c\u6b65"));
                    formulaAndPrintMessage.append("\u76ee\u6807\u6253\u5370\u65b9\u6848 ").append(printSchemeKey).append(" \u67e5\u8be2\u4e3a\u7a7a\uff0c\u53ef\u80fd\u5df2\u5220\u9664\uff0c\u8df3\u8fc7\u6b64\u6b21\u540c\u6b65\u3002");
                    hasMessage = true;
                    continue;
                }
                desTemplateScheme.setCommonAttribute(srcTemplateScheme.getCommonAttribute());
                desTemplateScheme.setGatherCoverData(srcTemplateScheme.getGatherCoverData());
                updatePrintSchemeDefine.add(desTemplateScheme);
            }
            printTemplateSchemeDefineMap.put(string, desTemplateScheme);
        }
        if (hasMessage) {
            formulaAndPrintMessage.append("\r\n");
        } else {
            formulaAndPrintMessage.append("\u6ca1\u6709\u6253\u5370\u65b9\u6848\u53d1\u751f\u53d8\u66f4").append("\r\n");
        }
        if (!CollectionUtils.isEmpty(addPrintSchemeDefine)) {
            addPrintSchemeDefine = addPrintSchemeDefine.stream().sorted(new Comparator<DesignPrintTemplateSchemeDefine>(){

                @Override
                public int compare(DesignPrintTemplateSchemeDefine o1, DesignPrintTemplateSchemeDefine o2) {
                    return o1.getOrder().compareTo(o2.getOrder());
                }
            }).collect(Collectors.toList());
            for (DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine : addPrintSchemeDefine) {
                designPrintTemplateSchemeDefine.setOrder(OrderGenerator.newOrder());
                this.designTimePrintController.insertPrintTemplateScheme(designPrintTemplateSchemeDefine);
            }
        }
        if (!CollectionUtils.isEmpty(updatePrintSchemeDefine)) {
            for (DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine : updatePrintSchemeDefine) {
                this.designTimePrintController.updatePrintTemplateScheme(designPrintTemplateSchemeDefine);
            }
        }
        return printTemplateSchemeDefineMap;
    }

    private List<FormulaSyncResult> doSyncFormulaAndTrans(FormCopyContext context, String formSchemeKey, Map<String, String> formMap, Map<String, String> formulaSchemeMap, FormCopyAttSchemeMap attScheemMap, StringBuilder formulaAndPrintMessage) throws JQException {
        if (null == formMap || formMap.isEmpty() || null == formulaSchemeMap || formulaSchemeMap.isEmpty()) {
            return Collections.emptyList();
        }
        logger.info("\u540c\u6b65\u516c\u5f0f\u5f00\u59cb");
        Map<String, DesignFormulaSchemeDefine> formulaSchemeDefineMap = this.doCopyFormulaScheme(formSchemeKey, formulaSchemeMap, attScheemMap, formulaAndPrintMessage);
        ArrayList<FormulaSyncResult> filteredFormulas = new ArrayList<FormulaSyncResult>();
        ArrayList<DesignFormulaDefine> addFormulas = new ArrayList<DesignFormulaDefine>();
        ArrayList<DesignFormulaDefine> updateFormulas = new ArrayList<DesignFormulaDefine>();
        ArrayList<String> srcFormulaConditionKeys = new ArrayList<String>();
        HashMap<String, String> srcToDesFormulaKey = new HashMap<String, String>();
        ArrayList<DesignFormulaConditionLink> addFormulaConditionLinks = new ArrayList<DesignFormulaConditionLink>();
        Map<String, DesignFormDefine> formInfo = context.getFormInfo();
        for (String srcFormulaSchemeKey : formulaSchemeMap.keySet()) {
            addFormulas.clear();
            updateFormulas.clear();
            srcToDesFormulaKey.clear();
            DesignFormulaSchemeDefine formulaSchemeDefine = formulaSchemeDefineMap.get(srcFormulaSchemeKey);
            Map<String, DesignFormulaDefine> formulaCodeMap = this.getForumulaCodeMap(formulaSchemeDefine.getKey());
            context.getI18nResourceMap().put(srcFormulaSchemeKey, formulaSchemeDefine.getKey());
            StringBuilder formulaFormMessage = new StringBuilder();
            for (Map.Entry<String, String> formEntry : formMap.entrySet()) {
                DesignFormDefine designFormDefine;
                List srcFormulas = this.designTimeFormulaController.listFormulaBySchemeAndForm(srcFormulaSchemeKey, formEntry.getKey());
                StringBuilder formulaAddFormMessage = new StringBuilder();
                StringBuilder formulaUpdateFormMessage = new StringBuilder();
                StringBuilder formulaFilterFormMessage = new StringBuilder();
                for (DesignFormulaDefine formula : srcFormulas) {
                    String srcFormulaKey = formula.getKey();
                    DesignFormulaDefine checkFormulaCode = formulaCodeMap.get(formula.getCode());
                    if (null == checkFormulaCode) {
                        this.updateFormulaInfo(formula, UUIDUtils.getKey(), formEntry.getValue(), formulaSchemeDefine.getKey(), formulaSchemeDefine.getTitle());
                        addFormulas.add(formula);
                        formulaAddFormMessage.append(formula.getCode()).append("\u3001");
                        context.getI18nResourceMap().put(srcFormulaKey, formula.getKey());
                        srcToDesFormulaKey.put(srcFormulaKey, formula.getKey());
                        continue;
                    }
                    if (formEntry.getValue().equals(checkFormulaCode.getFormKey())) {
                        this.updateFormulaInfo(formula, checkFormulaCode.getKey(), formEntry.getValue(), formulaSchemeDefine.getKey(), formulaSchemeDefine.getTitle());
                        updateFormulas.add(formula);
                        formulaUpdateFormMessage.append(formula.getCode()).append("\u3001");
                        context.getI18nResourceMap().put(srcFormulaKey, formula.getKey());
                        srcToDesFormulaKey.put(srcFormulaKey, formula.getKey());
                        continue;
                    }
                    filteredFormulas.add(new FormulaSyncResult(checkFormulaCode, formulaSchemeDefine.getFormulaSchemeType(), formulaSchemeDefine.getTitle()));
                    formulaFilterFormMessage.append(formula.getCode()).append("\u3001");
                    logger.info("\u540c\u6b65\u516c\u5f0f\uff1a\u8fc7\u6ee4\u516c\u5f0f{}", (Object)formula.getCode());
                }
                if (formulaAddFormMessage.length() == 0 && formulaUpdateFormMessage.length() == 0 && formulaFilterFormMessage.length() == 0 || (designFormDefine = formInfo.get(formEntry.getKey())) == null) continue;
                formulaFormMessage.append(designFormDefine.getTitle());
                if (formulaAddFormMessage.length() != 0) {
                    formulaFormMessage.append(" \u65b0\u589e\u7684\u516c\u5f0f\u6709\uff1a").append((CharSequence)formulaAddFormMessage).append("\u3002").append("\r\n");
                }
                if (formulaUpdateFormMessage.length() != 0) {
                    formulaFormMessage.append(" \u66f4\u65b0\u7684\u516c\u5f0f\u6709\uff1a").append((CharSequence)formulaUpdateFormMessage).append("\u3002").append("\r\n");
                }
                if (formulaFilterFormMessage.length() == 0) continue;
                formulaFormMessage.append(" \u5b58\u5728\u76f8\u540c\u4ee3\u7801\u800c\u88ab\u8fc7\u6ee4\u7684\u516c\u5f0f\u6709\uff1a").append((CharSequence)formulaFilterFormMessage).append("\u3002").append("\r\n");
            }
            DesignFormulaSchemeDefine srcFormulaScheme = this.designTimeFormulaController.getFormulaScheme(srcFormulaSchemeKey);
            if (formulaFormMessage.length() != 0) {
                formulaAndPrintMessage.append("\u516c\u5f0f\u65b9\u6848 ").append(srcFormulaScheme.getTitle()).append("\u590d\u5236\u516c\u5f0f\u5230\u65b9\u6848 ").append(formulaSchemeDefine.getTitle()).append(" \u4e0b").append((CharSequence)formulaFormMessage);
            } else {
                formulaAndPrintMessage.append("\u516c\u5f0f\u65b9\u6848 ").append(srcFormulaScheme.getTitle()).append(" \u4e0b\u6ca1\u6709\u516c\u5f0f\u8fdb\u884c\u590d\u5236").append("\r\n");
            }
            if (!updateFormulas.isEmpty()) {
                this.designTimeFormulaController.updateFormula(updateFormulas.toArray(new DesignFormulaDefine[0]));
            }
            if (!addFormulas.isEmpty()) {
                this.designTimeFormulaController.insertFormula(addFormulas.toArray(new DesignFormulaDefine[0]));
            }
            List<DesignFormulaConditionLink> formulaConditionLinks = this.doCopyFormulaConditionLink(srcFormulaSchemeKey, formulaSchemeDefine.getKey(), srcToDesFormulaKey, srcFormulaConditionKeys);
            addFormulaConditionLinks.addAll(formulaConditionLinks);
        }
        this.doCopyFormulaCondition(context, srcFormulaConditionKeys, addFormulaConditionLinks);
        if (formulaSchemeMap.isEmpty()) {
            logger.info("\u62a5\u8868\u65b9\u6848" + formSchemeKey + "\u8981\u540c\u6b65\u7684\u540c\u6b65\u516c\u5f0f\u65b9\u6848\u4e3a\u7a7a");
        }
        logger.info("\u540c\u6b65\u516c\u5f0f\u7ed3\u675f");
        return filteredFormulas;
    }

    private List<DesignFormulaConditionLink> doCopyFormulaConditionLink(String srcFormulaSchemeKey, String desFormulaSchemeKey, Map<String, String> srcToDesFormulaKey, List<String> srcFormulaConditionKeys) throws JQException {
        ArrayList<DesignFormulaConditionLink> addFormulaConditionLinks = new ArrayList<DesignFormulaConditionLink>();
        try {
            Map<String, List<DesignFormulaConditionLink>> conditionLinkFormulaMap = this.formulaDesignTimeController.listConditionLinkByScheme(srcFormulaSchemeKey).stream().collect(Collectors.groupingBy(FormulaConditionLink::getFormulaKey));
            ArrayList<String> hasFormulaLinkKey = new ArrayList<String>();
            for (Map.Entry<String, String> desFormulaKeyEntry : srcToDesFormulaKey.entrySet()) {
                List<DesignFormulaConditionLink> designFormulaConditionLinks = conditionLinkFormulaMap.get(desFormulaKeyEntry.getKey());
                if (designFormulaConditionLinks == null) continue;
                String desFormulaKey = desFormulaKeyEntry.getValue();
                hasFormulaLinkKey.add(desFormulaKey);
                for (DesignFormulaConditionLink designFormulaConditionLink : designFormulaConditionLinks) {
                    designFormulaConditionLink.setFormulaKey(desFormulaKey);
                    designFormulaConditionLink.setFormulaSchemeKey(desFormulaSchemeKey);
                }
                addFormulaConditionLinks.addAll(designFormulaConditionLinks);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORM_COPY_ERROR_009, (Throwable)e);
        }
        Set updateConditionKeys = addFormulaConditionLinks.stream().map(FormulaConditionLink::getConditionKey).collect(Collectors.toSet());
        srcFormulaConditionKeys.addAll(updateConditionKeys);
        return addFormulaConditionLinks;
    }

    private void doCopyFormulaCondition(FormCopyContext context, List<String> srcFormulaConditionKeys, List<DesignFormulaConditionLink> addFormulaConditionLinks) throws JQException {
        try {
            if (!context.getSrcTaskKey().equals(context.getTaskKey())) {
                HashMap<String, String> formulaConditionKeyMap = new HashMap<String, String>();
                List srcFormulaConditions = this.formulaDesignTimeController.listFormulaConditionByKey(srcFormulaConditionKeys = srcFormulaConditionKeys.stream().distinct().collect(Collectors.toList()));
                if (!srcFormulaConditions.isEmpty()) {
                    ArrayList<DesignFormulaCondition> addFormulaConditions = new ArrayList<DesignFormulaCondition>();
                    ArrayList<DesignFormulaCondition> updateFormulaConditions = new ArrayList<DesignFormulaCondition>();
                    List desFormulaConditionsForTask = this.formulaDesignTimeController.listFormulaConditionByTask(context.getTaskKey());
                    Map<String, DesignFormulaCondition> desFormulaConditionsForTaskCodeMap = desFormulaConditionsForTask.stream().collect(Collectors.toMap(FormulaCondition::getCode, a -> a));
                    for (DesignFormulaCondition srcFormulaCondition : srcFormulaConditions) {
                        DesignFormulaCondition desFormulaCondition = desFormulaConditionsForTaskCodeMap.get(srcFormulaCondition.getCode());
                        String srcFormulaConditionKey = srcFormulaCondition.getKey();
                        DesignFormulaCondition newFormulaCondition = this.getFormulaCondition(srcFormulaCondition, context.getTaskKey(), desFormulaCondition != null ? desFormulaCondition.getKey() : UUIDUtils.getKey());
                        if (desFormulaCondition != null) {
                            updateFormulaConditions.add(newFormulaCondition);
                        } else {
                            addFormulaConditions.add(newFormulaCondition);
                        }
                        formulaConditionKeyMap.put(srcFormulaConditionKey, newFormulaCondition.getKey());
                    }
                    if (!updateFormulaConditions.isEmpty()) {
                        this.formulaDesignTimeController.updateFormulaConditions(updateFormulaConditions);
                    }
                    if (!addFormulaConditions.isEmpty()) {
                        this.formulaDesignTimeController.insertFormulaConditions(addFormulaConditions);
                    }
                    ArrayList<DesignFormulaConditionLink> addExistFormulaConditionLinks = new ArrayList<DesignFormulaConditionLink>();
                    for (DesignFormulaConditionLink addFormulaConditionLink : addFormulaConditionLinks) {
                        String srcConditionKey = addFormulaConditionLink.getConditionKey();
                        String desConditionKey = (String)formulaConditionKeyMap.get(srcConditionKey);
                        if (!StringUtils.hasText(desConditionKey)) continue;
                        addFormulaConditionLink.setConditionKey(desConditionKey);
                        addExistFormulaConditionLinks.add(addFormulaConditionLink);
                    }
                    if (addExistFormulaConditionLinks.size() > 0) {
                        this.formulaDesignTimeController.deleteFormulaConditionLinkByFormula((String[])addExistFormulaConditionLinks.stream().map(FormulaConditionLink::getFormulaKey).distinct().toArray(String[]::new));
                        this.formulaDesignTimeController.insertFormulaConditionLinks(addExistFormulaConditionLinks);
                    }
                }
            } else {
                DesignFormSchemeDefine srcFormSchemeDefine = this.designTimeViewController.getFormScheme(context.getSrcFormSchemeKey());
                DesignFormSchemeDefine desFormSchemeDefine = this.designTimeViewController.getFormScheme(context.getFormSchemeKey());
                logger.info("\u62a5\u8868\u590d\u5236\u4e24\u4e2a\u6570\u636e\u65b9\u6848 " + srcFormSchemeDefine.getTitle() + " \u548c " + desFormSchemeDefine.getTitle() + " \u90fd\u662f\u540c\u4e00\u4e2a\u4efb\u52a1\u4e0b\u7684\uff0c\u4e0d\u590d\u5236\u516c\u5f0f\u9002\u5e94\u4f53\u6761\u4ef6\uff01");
                this.formulaDesignTimeController.insertFormulaConditionLinks(addFormulaConditionLinks);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORM_COPY_ERROR_010, (Throwable)e);
        }
    }

    private DesignFormulaCondition getFormulaCondition(DesignFormulaCondition srcFormulaCondition, String taskKey, String key) {
        srcFormulaCondition.setTaskKey(taskKey);
        srcFormulaCondition.setKey(key);
        return srcFormulaCondition;
    }

    private Map<String, DesignFormulaSchemeDefine> doCopyFormulaScheme(String formSchemeKey, Map<String, String> formulaSchemeMap, FormCopyAttSchemeMap attScheemMap, StringBuilder formulaAndPrintMessage) {
        HashMap<String, DesignFormulaSchemeDefine> formulaSchemeDefineMap = new HashMap<String, DesignFormulaSchemeDefine>();
        ArrayList<DesignFormulaSchemeDefine> addFormulaSchemeDefines = new ArrayList<DesignFormulaSchemeDefine>();
        HashSet<String> formulaSchemeKeySet = new HashSet<String>(formulaSchemeMap.keySet());
        boolean hasMessage = false;
        for (String srcFormulaSchemeKey : formulaSchemeKeySet) {
            DesignFormulaSchemeDefine formulaSchemeDefine = null;
            if (!StringUtils.hasText(formulaSchemeMap.get(srcFormulaSchemeKey))) {
                formulaSchemeDefine = this.designTimeFormulaController.getFormulaScheme(srcFormulaSchemeKey);
                formulaSchemeDefine.setKey(UUIDUtils.getKey());
                formulaSchemeDefine.setFormSchemeKey(formSchemeKey);
                formulaSchemeDefine.setDefault(false);
                formulaSchemeMap.put(srcFormulaSchemeKey, formulaSchemeDefine.getKey());
                this.setAttScheemMap(attScheemMap, srcFormulaSchemeKey, formulaSchemeDefine.getKey());
                addFormulaSchemeDefines.add(formulaSchemeDefine);
                logger.info("\u540c\u6b65\u516c\u5f0f\uff1a\u65b0\u589e\u516c\u5f0f\u65b9\u6848[{}]", (Object)formulaSchemeDefine.getTitle());
                formulaAndPrintMessage.append("\u65b0\u589e\u516c\u5f0f\u65b9\u6848 ").append(formulaSchemeDefine.getTitle());
                hasMessage = true;
            } else {
                formulaSchemeDefine = this.designTimeFormulaController.getFormulaScheme(formulaSchemeMap.get(srcFormulaSchemeKey));
            }
            if (formulaSchemeDefine == null) {
                logger.info("\u540c\u6b65\u516c\u5f0f\uff1a\u76ee\u6807\u516c\u5f0f\u65b9\u6848 " + formulaSchemeMap.get(srcFormulaSchemeKey) + " \u4e3a\u7a7a\uff0c\u53ef\u80fd\u5df2\u5220\u9664\uff0c\u8df3\u8fc7\u6b64\u516c\u5f0f\u65b9\u6848\u540c\u6b65");
                formulaAndPrintMessage.append("\u76ee\u6807\u516c\u5f0f\u65b9\u6848 ").append(srcFormulaSchemeKey).append(" \u67e5\u8be2\u4e3a\u7a7a\uff0c\u53ef\u80fd\u5df2\u5220\u9664\uff0c\u8df3\u8fc7\u6b64\u6b21\u540c\u6b65\u3002");
                formulaSchemeMap.remove(srcFormulaSchemeKey);
                hasMessage = true;
                continue;
            }
            formulaSchemeDefineMap.put(srcFormulaSchemeKey, formulaSchemeDefine);
        }
        if (hasMessage) {
            formulaAndPrintMessage.append("\r\n");
        } else {
            formulaAndPrintMessage.append("\u6ca1\u6709\u516c\u5f0f\u65b9\u6848\u53d1\u751f\u53d8\u66f4").append("\r\n");
        }
        if (!CollectionUtils.isEmpty(addFormulaSchemeDefines)) {
            addFormulaSchemeDefines.stream().sorted(new Comparator<DesignFormulaSchemeDefine>(){

                @Override
                public int compare(DesignFormulaSchemeDefine o1, DesignFormulaSchemeDefine o2) {
                    return o1.getOrder().compareTo(o2.getOrder());
                }
            }).forEach(define -> {
                define.setOrder(OrderGenerator.newOrder());
                this.designTimeFormulaController.insertFormulaScheme(define);
            });
        }
        return formulaSchemeDefineMap;
    }

    void setAttScheemMap(FormCopyAttSchemeMap attScheemMap, String srcFormulaSchemeKey, String desFormulaSchemeKey) {
        Map<String, String> formulaSchemeMap = attScheemMap.getFormulaSchemeMap();
        if (formulaSchemeMap.containsKey(srcFormulaSchemeKey)) {
            formulaSchemeMap.put(srcFormulaSchemeKey, desFormulaSchemeKey);
            return;
        }
    }

    private void transFormulaDefines(ExecutorContext executorContext, List<DesignFormulaDefine> formulaDefines, Map<String, String> formCodeMap) {
        String expression = "";
        String formCode = null;
        for (DesignFormulaDefine designFormulaDefine : formulaDefines) {
            formCode = formCodeMap.get(designFormulaDefine.getFormKey());
            try {
                expression = DataEngineFormulaParser.transFormulaStyle((ExecutorContext)executorContext, (String)designFormulaDefine.getExpression(), (String)formCode, (DataEngineConsts.FormulaShowType)DataEngineConsts.FormulaShowType.JQ);
                designFormulaDefine.setExpression(expression);
            }
            catch (ParseException e) {
                logger.debug("\u516c\u5f0f\u8f6c\u4e2d\u95f4\u683c\u5f0f\u5f02\u5e38", e);
            }
        }
    }

    private ExecutorContext initContext(String formSchemeKey, Collection<String> formKeys, String dataSchemekey) throws JQException {
        Map<String, ConversionFormInfo> conversionFormInfoMap = this.getConversionFormInfoMap(dataSchemekey, formKeys);
        try {
            return this.initContext(conversionFormInfoMap, formSchemeKey);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_006, (Throwable)e);
        }
    }

    private ExecutorContext initContext(Map<String, ConversionFormInfo> conversionFormInfoMap, String formSchemeKey) throws ParseException {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setDesignTimeData(true, this.dataDefinitionDesignTimeController);
        context.setJQReportModel(true);
        FormulaConversionContext formulaConversionContext = new FormulaConversionContext();
        formulaConversionContext.setConversionFormInfoMap(conversionFormInfoMap);
        FormulaConversionFmlExecEnvironment env = new FormulaConversionFmlExecEnvironment(this.designTimeViewController, this.dataDefinitionDesignTimeController, formSchemeKey, null, formulaConversionContext);
        context.setEnv((IFmlExecEnvironment)env);
        return context;
    }

    private Map<String, ConversionFormInfo> getConversionFormInfoMap(String dataSchemeKey, Collection<String> formKeys) {
        HashMap<String, ConversionFormInfo> conversionFormInfoMap = new HashMap<String, ConversionFormInfo>();
        Map<String, String> fieldKeyMap = this.formCopyDataModelInfoDao.getBySchemeKey(dataSchemeKey).stream().collect(Collectors.toMap(IFormCopyDataModelInfo::getDataFieldKey, IFormCopyDataModelInfo::getSrcDataFieldKey));
        ArrayList<String> allFieldKeys = new ArrayList<String>();
        allFieldKeys.addAll(fieldKeyMap.keySet());
        allFieldKeys.addAll(fieldKeyMap.values());
        Map<String, DesignDataField> allDataFieldMap = this.iDesignDataSchemeService.getDataFields(allFieldKeys).stream().collect(Collectors.toMap(Basic::getKey, f -> f, (k1, k2) -> k1));
        String fieldKey = null;
        for (String formKey : formKeys) {
            ConversionFormInfo formInfo = new ConversionFormInfo();
            HashMap<String, ConversionFieldInfo> fieldInfoMap = new HashMap<String, ConversionFieldInfo>();
            List allLinks = this.designTimeViewController.listDataLinkByForm(formKey);
            for (DesignDataLinkDefine link : allLinks) {
                if (link.getType() != DataLinkType.DATA_LINK_TYPE_FIELD) continue;
                fieldKey = link.getLinkExpression();
                DesignDataField newDataField = allDataFieldMap.get(fieldKey);
                DesignDataField srcDataField = allDataFieldMap.get(fieldKeyMap.get(fieldKey));
                if (null == newDataField || null == srcDataField) continue;
                ConversionFieldInfo fieldInfo = new ConversionFieldInfo();
                fieldInfo.setNewCode(newDataField.getCode());
                fieldInfo.setNewKey(newDataField.getKey());
                fieldInfo.setOldCode(srcDataField.getCode());
                fieldInfo.setOldKey(srcDataField.getKey());
                fieldInfoMap.put(fieldInfo.getOldCode(), fieldInfo);
            }
            formInfo.setFieldInfoMap(fieldInfoMap);
            conversionFormInfoMap.put(formKey, formInfo);
        }
        return conversionFormInfoMap;
    }

    private void updateFormulaInfo(DesignFormulaDefine formula, String key, String formKey, String formulaSchemeKey, String formulaSchemeTitle) {
        formula.setKey(key);
        formula.setFormKey(formKey);
        formula.setFormulaSchemeKey(formulaSchemeKey);
    }

    private Map<String, DesignFormulaDefine> getForumulaCodeMap(String formulaSchemeKey) throws JQException {
        HashMap<String, DesignFormulaDefine> formulaCodeMap = new HashMap<String, DesignFormulaDefine>();
        List allFormulas = this.designTimeFormulaController.listFormulaByScheme(formulaSchemeKey);
        if (!CollectionUtils.isEmpty(allFormulas)) {
            for (DesignFormulaDefine formulaDefine : allFormulas) {
                if (!Objects.nonNull(formulaDefine)) continue;
                formulaCodeMap.put(formulaDefine.getCode(), formulaDefine);
            }
        }
        return formulaCodeMap;
    }

    private Map<String, String> doCopyForm(FormCopyContext context, List<FormCopyParams> formCopyParams, AsyncTaskMonitor monitor, double startProgress, double thisProgress) throws JQException {
        HashMap<String, DesignFormGroupDefine> srcKeyGroupMap = new HashMap<String, DesignFormGroupDefine>();
        HashMap<String, DesignFormGroupDefine> keyGroupMap = new HashMap<String, DesignFormGroupDefine>();
        HashMap<String, DesignFormGroupDefine> titleGroupMap = new HashMap<String, DesignFormGroupDefine>();
        List srcFormGroups = this.designTimeViewController.listFormGroupByFormScheme(context.getSrcFormSchemeKey());
        for (Object group : srcFormGroups) {
            srcKeyGroupMap.put(group.getKey(), (DesignFormGroupDefine)group);
        }
        List formGroups = this.designTimeViewController.listFormGroupByFormScheme(context.getFormSchemeKey());
        for (DesignFormGroupDefine group : formGroups) {
            keyGroupMap.put(group.getKey(), group);
            titleGroupMap.put(group.getTitle(), group);
        }
        HashMap<String, String> newFormLink = new HashMap<String, String>();
        for (FormCopyParams param : formCopyParams) {
            context.setDataFieldInfoForForm(new HashMap<DesignDataField, DesignDataField>());
            DesignFormGroupDefine newFormGroup = null;
            if (StringUtils.hasText(param.getSrcFormGroupKey())) {
                if (context.getFormGroupInfo().containsKey(param.getSrcFormGroupKey())) {
                    newFormGroup = context.getFormGroupInfo().get(param.getSrcFormGroupKey());
                } else {
                    DesignFormGroupDefine srcFormGroup = (DesignFormGroupDefine)srcKeyGroupMap.get(param.getSrcFormGroupKey());
                    if (titleGroupMap.containsKey(srcFormGroup.getTitle())) {
                        newFormGroup = (DesignFormGroupDefine)titleGroupMap.get(srcFormGroup.getTitle());
                    } else {
                        newFormGroup = this.designFormCopyHelper.copyFormGroupTo(srcFormGroup, context.getFormSchemeKey());
                        context.addFormGroupInfo(param.getSrcFormGroupKey(), newFormGroup);
                    }
                }
            } else {
                newFormGroup = (DesignFormGroupDefine)keyGroupMap.get(param.getFormGroupKey());
            }
            DesignFormDefine newForm = this.designFormCopyHelper.copyFormTo(param.getSrcFormKey(), context.getFormSchemeKey());
            if (StringUtils.hasText(param.getFormKey())) {
                newForm.setKey(param.getFormKey());
            }
            newFormLink.put(param.getSrcFormKey(), newForm.getKey());
            if (StringUtils.hasText(param.getFormCode())) {
                newForm.setFormCode(param.getFormCode());
            }
            if (StringUtils.hasText(param.getFormTitle())) {
                // empty if block
            }
            if (StringUtils.hasText(param.getOrder())) {
                newForm.setOrder(param.getOrder());
            }
            context.addFormInfo(param.getSrcFormKey(), newForm);
            context.addFormGroupLink(newForm.getKey(), newFormGroup.getKey());
            FormCopyUtil.setProgressAndMessage(monitor, startProgress + 0.6 * thisProgress, context.getSrcFormSchemeTitle() + " \u5f00\u59cb\u5904\u7406\u6570\u636e\u6a21\u578b");
            this.doCopyFormRegion(context, param.getSrcFormKey(), newForm.getKey());
            FormCopyUtil.setProgressAndMessage(monitor, startProgress + 0.78 * thisProgress, context.getSrcFormSchemeTitle() + " \u6570\u636e\u6a21\u578b\u5904\u7406\u5b8c\u6210");
            this.doCopyDataLinkMapping(context, param.getSrcFormKey(), newForm.getKey());
        }
        context.getI18nResourceMap().putAll(context.getFormGroupInfo().entrySet().stream().collect(Collectors.toMap(e -> (String)e.getKey(), e -> ((DesignFormGroupDefine)e.getValue()).getKey())));
        context.getI18nResourceMap().putAll(context.getFormInfo().entrySet().stream().collect(Collectors.toMap(e -> (String)e.getKey(), e -> ((DesignFormDefine)e.getValue()).getKey())));
        context.getI18nResourceMap().putAll(context.getRegionInfo().entrySet().stream().collect(Collectors.toMap(e -> (String)e.getKey(), e -> ((DesignDataRegion)e.getValue()).getKey())));
        FormCopyUtil.setProgressAndMessage(monitor, startProgress + 0.8 * thisProgress, context.getSrcFormSchemeTitle() + " \u8868\u5355\u548c\u6570\u636e\u6a21\u578b\u53c2\u51c6\u5907\u5165\u5e93");
        this.saveNewForm(context);
        FormCopyUtil.setProgressAndMessage(monitor, startProgress + thisProgress, context.getSrcFormSchemeTitle() + " \u8868\u5355\u548c\u6a21\u578b\u5165\u5e93\u5b8c\u6bd5\uff0c\u5f00\u59cb\u5904\u7406\u5176\u5b83\u53c2\u6570");
        return newFormLink;
    }

    private void saveNewForm(FormCopyContext context) throws JQException {
        Collection<DesignDataLinkMappingDefine> collection;
        Collection<DesignDataLinkDefine> links;
        Collection<DesignDataRegion> regions;
        Map<String, DesignFormDefine> formInfo;
        StringBuilder dataModelMessage = new StringBuilder();
        if (context.isCopyDataModel()) {
            int i;
            List value;
            DesignDataTable thisDataTable;
            Set<DesignDataField> updateDataFields = context.getUpdateDataFields();
            Collection<DesignDataField> dataFields = context.getNewDataFields();
            Set updateFieldTableKeys = updateDataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
            Set insertFieldTableKeys = dataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
            HashSet hasFieldTableKeySets = new HashSet();
            hasFieldTableKeySets.addAll(updateFieldTableKeys);
            hasFieldTableKeySets.addAll(insertFieldTableKeys);
            List list = context.getNewDataTables().stream().filter(a -> !hasFieldTableKeySets.contains(a.getKey())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(list)) {
                StringBuilder notInsertDataTablesMessage = new StringBuilder();
                notInsertDataTablesMessage.append("\u56e0\u4e3a\u6307\u6807\u590d\u7528\u6307\u6807\u5168\u90e8\u79fb\u52a8\u5230\u5176\u5b83\u6307\u6807\u8868\u7684\u800c\u4e0d\u65b0\u589e\u7684\u6307\u6807\u8868\u6709").append(list.size()).append("\u4e2a\uff0c\u5206\u522b\u662f\uff1a");
                int i2 = 0;
                for (DesignDataTable notInsertDataTable : list) {
                    notInsertDataTablesMessage.append(notInsertDataTable.getTitle()).append("[").append(notInsertDataTable.getCode()).append("]");
                    if (i2 >= list.size() - 1) continue;
                    notInsertDataTablesMessage.append("\u3001");
                }
                logger.info(notInsertDataTablesMessage.toString());
                dataModelMessage.append((CharSequence)notInsertDataTablesMessage).append("\r\n");
            }
            List insertDataTables = context.getNewDataTables().stream().filter(a -> hasFieldTableKeySets.contains(a.getKey())).collect(Collectors.toList());
            Set insertDataTableOfGroups = insertDataTables.stream().map(DataTable::getDataGroupKey).collect(Collectors.toSet());
            Map<String, DesignDataGroup> dataGroupDefineInfos = context.getDataGroupDefineInfos();
            ArrayList<DesignDataGroup> dataGroupArrays = new ArrayList<DesignDataGroup>(dataGroupDefineInfos.values());
            List hasDataTableGroups = dataGroupArrays.stream().filter(a -> insertDataTableOfGroups.contains(a.getKey())).collect(Collectors.toList());
            Set hasDataTableGroupParentSets = hasDataTableGroups.stream().filter(a -> StringUtils.hasLength(a.getParentKey())).map(Grouped::getParentKey).collect(Collectors.toSet());
            List parentDataTableGroups = dataGroupArrays.stream().filter(a -> hasDataTableGroupParentSets.contains(a.getKey()) || !StringUtils.hasLength(a.getParentKey())).collect(Collectors.toList());
            ArrayList insertDataGroupArrays = new ArrayList();
            insertDataGroupArrays.addAll(hasDataTableGroups);
            insertDataGroupArrays.addAll(parentDataTableGroups);
            this.iDesignDataSchemeService.insertDataGroups(insertDataGroupArrays);
            Map<String, DesignDataTable> mdDataTableMap = insertDataTables.stream().filter(a -> a.getDataTableType() == DataTableType.MD_INFO).collect(Collectors.toMap(Basic::getKey, a -> a, (k1, k2) -> k1));
            if (!insertDataTables.isEmpty()) {
                this.iDesignDataSchemeService.insertDataTables(new ArrayList(insertDataTables));
                dataModelMessage.append("\u65b0\u589e\u7684\u6570\u636e\u8868\u6709").append(insertDataTables.size()).append("\u4e2a\uff0c\u5206\u522b\u662f\uff1a");
                int i3 = 0;
                for (DesignDataTable updateDataTable : insertDataTables) {
                    dataModelMessage.append(updateDataTable.getTitle()).append("[").append(updateDataTable.getCode()).append("]");
                    if (i3 >= insertDataTables.size() - 1) continue;
                    dataModelMessage.append("\u3001");
                }
                dataModelMessage.append("\r\n");
            } else {
                dataModelMessage.append("\u6ca1\u6709\u65b0\u589e\u7684\u6570\u636e\u8868").append("\r\n");
            }
            Set<DesignDataTable> updateDataTables = context.getUpdateDataTables();
            if (!updateDataTables.isEmpty()) {
                this.iDesignDataSchemeService.updateDataTables(new ArrayList<DesignDataTable>(updateDataTables));
                dataModelMessage.append("\u66f4\u65b0\u7684\u6570\u636e\u8868\u6709").append(updateDataTables.size()).append("\u4e2a\uff0c\u5206\u522b\u662f\uff1a");
                int i4 = 0;
                for (DesignDataTable designDataTable : updateDataTables) {
                    dataModelMessage.append(designDataTable.getTitle()).append("[").append(designDataTable.getCode()).append("]");
                    if (i4 < updateDataTables.size() - 1) {
                        dataModelMessage.append("\u3001");
                    }
                    ++i4;
                }
                dataModelMessage.append("\r\n");
            } else {
                dataModelMessage.append("\u6ca1\u6709\u66f4\u65b0\u7684\u6570\u636e\u8868").append("\r\n");
            }
            if (!updateDataFields.isEmpty()) {
                this.iDesignDataSchemeService.updateDataFields(new ArrayList<DesignDataField>(updateDataFields));
                Map<String, List<DesignDataField>> updateDataFieldMap = updateDataFields.stream().collect(Collectors.groupingBy(DataField::getDataTableKey));
                for (Map.Entry entry : updateDataFieldMap.entrySet()) {
                    thisDataTable = context.getDataTable((String)entry.getKey());
                    value = (List)entry.getValue();
                    if (thisDataTable == null || CollectionUtils.isEmpty(value)) continue;
                    dataModelMessage.append("\u6570\u636e\u8868 ").append(thisDataTable.getTitle()).append("[").append(thisDataTable.getCode()).append("]").append("\u4e0b\u66f4\u65b0\u7684\u6307\u6807\u6709 ").append(value.size()).append("\u4e2a\uff1a");
                    i = 0;
                    for (DesignDataField designDataField : value) {
                        dataModelMessage.append(designDataField.getTitle()).append("[").append(designDataField.getCode()).append("]");
                        if (i >= value.size() - 1) continue;
                        dataModelMessage.append("\u3001");
                    }
                    dataModelMessage.append("\r\n");
                }
            } else {
                dataModelMessage.append("\u6ca1\u6709\u66f4\u65b0\u7684\u6307\u6807").append("\r\n");
            }
            if (mdDataTableMap.size() > 0) {
                HashMap mdTableKeyMap = new HashMap();
                for (Map.Entry entry : mdDataTableMap.entrySet()) {
                    mdTableKeyMap.put(entry.getKey(), ((DesignDataTable)entry.getValue()).getKey());
                }
                for (DesignDataField designDataField : dataFields) {
                    String newDataTableKey = (String)mdTableKeyMap.get(designDataField.getDataTableKey());
                    if (!StringUtils.hasLength(newDataTableKey)) continue;
                    designDataField.setDataTableKey(newDataTableKey);
                }
            }
            if (!dataFields.isEmpty()) {
                this.iDesignDataSchemeService.insertDataFields(new ArrayList<DesignDataField>(dataFields));
                Map<String, List<DesignDataField>> addDataFieldMap = dataFields.stream().collect(Collectors.groupingBy(DataField::getDataTableKey));
                for (Map.Entry entry : addDataFieldMap.entrySet()) {
                    thisDataTable = context.getDataTable((String)entry.getKey());
                    value = (List)entry.getValue();
                    if (thisDataTable == null || CollectionUtils.isEmpty(value)) continue;
                    dataModelMessage.append("\u6570\u636e\u8868 ").append(thisDataTable.getTitle()).append("[").append(thisDataTable.getCode()).append("]").append("\u4e0b\u65b0\u589e\u7684\u6307\u6807\u6709 ").append(value.size()).append("\u4e2a\uff1a");
                    i = 0;
                    for (DesignDataField designDataField : value) {
                        dataModelMessage.append(designDataField.getTitle()).append("[").append(designDataField.getCode()).append("]");
                        if (i >= value.size() - 1) continue;
                        dataModelMessage.append("\u3001");
                    }
                    dataModelMessage.append("\r\n");
                }
            } else {
                dataModelMessage.append("\u6ca1\u6709\u65b0\u589e\u7684\u6307\u6807").append("\r\n");
            }
        }
        StringBuilder formModelMessage = new StringBuilder();
        Collection<DesignFormGroupDefine> formGroups = context.getFormGroupInfo().values();
        if (!formGroups.isEmpty()) {
            formModelMessage.append("\u65b0\u589e\u7684\u62a5\u8868\u5206\u7ec4\u6709").append(formGroups.size()).append("\u4e2a\uff0c\u5206\u522b\u662f\uff1a");
            for (DesignFormGroupDefine formGroup : formGroups) {
                formModelMessage.append(formGroup.getTitle()).append("\u3001");
                this.designTimeViewController.insertFormGroup(formGroup);
            }
            formModelMessage.append("\r\n");
        }
        if (!(formInfo = context.getFormInfo()).isEmpty()) {
            formModelMessage.append("\u540c\u6b65\u7684\u62a5\u8868\u6709").append(formInfo.size()).append("\u4e2a\uff0c\u5206\u522b\u662f\uff1a");
            int i = 0;
            for (Map.Entry entry : formInfo.entrySet()) {
                DesignAnalysisFormParamDefine srcParamDefine;
                DesignFormDefine form = (DesignFormDefine)entry.getValue();
                String groupKey = context.getFormGroupLink().get(form.getKey());
                DesignFormGroupLink formGroupLink = this.designTimeViewController.initFormGroupLink();
                formGroupLink.setFormOrder(form.getOrder());
                formGroupLink.setFormKey(form.getKey());
                formGroupLink.setGroupKey(groupKey);
                this.designTimeViewController.insertFormGroupLink(new DesignFormGroupLink[]{formGroupLink});
                if (!StringUtils.hasText(form.getUpdateUser())) {
                    form.setUpdateUser(NpContextHolder.getContext().getUserName());
                }
                formModelMessage.append(form.getTitle()).append("[").append(form.getFormCode()).append("]");
                if (i < formInfo.size() - 1) {
                    formModelMessage.append("\u3001");
                }
                ++i;
                this.designTimeViewController.insertForm(form);
                if (form.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS) {
                    try {
                        String analysisGuid = form.getFormExtension().get("analysisGuid").toString();
                        Grid2Data grid2Data = this.queryAnalysisStyle(analysisGuid);
                        this.designTimeViewController.insertFormStyle(form.getKey(), grid2Data);
                    }
                    catch (Exception e) {
                        throw new RuntimeException("\u5206\u6790\u62a5\u8868\uff0ckey: " + form.getKey() + " title:  " + form.getTitle() + " \u67e5\u8be2\u8868\u6837\u65f6\u5019\u5931\u8d25\uff1a " + e.getMessage());
                    }
                }
                if (form.isAnalysisForm() && (srcParamDefine = this.designTimeViewController.getAnalysisData(form.getKey())) != null) {
                    this.designTimeViewController.updataAnalysisData(form.getKey(), srcParamDefine);
                }
                this.formDefineService.syncFormStyleUpdateTime((String)entry.getKey(), form.getKey());
            }
            formModelMessage.append("\r\n");
        }
        if (!(regions = context.getRegionInfo().values()).isEmpty()) {
            DesignDataRegionDefine[] dataRegionDefines = new DesignDataRegionDefine[regions.size()];
            boolean bl = false;
            for (DesignDataRegion region : regions) {
                dataRegionDefines[++var8_13] = region.getRegion();
                if (null == region.getRegionSetting()) continue;
                this.designTimeViewController.insertRegionSetting(region.getRegionSetting());
            }
            this.doFixRegion(context, dataRegionDefines);
            this.designTimeViewController.insertDataRegion(dataRegionDefines);
        }
        if (!(links = context.getLinkInfo().values()).isEmpty()) {
            this.designTimeViewController.insertDataLink(links.toArray(new DesignDataLinkDefine[0]));
        }
        if (!(collection = context.getLinkMappingInfo().values()).isEmpty()) {
            this.designTimeViewController.insertDataLinkMapping(collection.toArray(new DesignDataLinkMappingDefine[0]));
        }
        StringBuilder formCopyMessage = context.getFormCopyMessage();
        formCopyMessage.append((CharSequence)formModelMessage).append("\r\n").append((CharSequence)dataModelMessage).append("\r\n");
    }

    private Grid2Data queryAnalysisStyle(String analysisKey) throws Exception {
        Grid2Data grid2Data = new Grid2Data();
        QuickReport quickReportByGuidOrId = this.quickReportModelService.getQuickReportByGuidOrId(analysisKey);
        List worksheets = quickReportByGuidOrId.getWorksheets();
        WorksheetModel worksheetModel = (WorksheetModel)worksheets.get(0);
        GridData griddata = worksheetModel.getGriddata();
        GridDataTransform.gridDataToGrid2Data((GridData)griddata, (Grid2Data)grid2Data);
        GridDataTransform.Grid2DataTextFilter((Grid2Data)grid2Data);
        return grid2Data;
    }

    private void doFixRegion(FormCopyContext context, DesignDataRegionDefine[] dataRegionDefines) {
        if (!context.isCopyDataModel()) {
            return;
        }
        HashMap<String, String> regionTableInfo = new HashMap<String, String>();
        Collection<DesignDataLinkDefine> dataLinks = context.getLinkInfo().values();
        for (DesignDataLinkDefine dataLink : dataLinks) {
            DesignDataField dataField;
            if (DataLinkType.DATA_LINK_TYPE_FIELD != dataLink.getType() && DataLinkType.DATA_LINK_TYPE_INFO != dataLink.getType() || regionTableInfo.containsKey(dataLink.getRegionKey()) || null == (dataField = context.getDataField(dataLink.getLinkExpression()))) continue;
            regionTableInfo.put(dataLink.getRegionKey(), dataField.getDataTableKey());
        }
        for (DesignDataRegionDefine dataRegion : dataRegionDefines) {
            DesignDataField dataField;
            if (DataRegionKind.DATA_REGION_SIMPLE == dataRegion.getRegionKind() || StringUtils.hasText(dataRegion.getInputOrderFieldKey()) || !regionTableInfo.containsKey(dataRegion.getKey()) || null == (dataField = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode((String)regionTableInfo.get(dataRegion.getKey()), "FLOATORDER"))) continue;
            dataRegion.setInputOrderFieldKey(dataField.getKey());
        }
    }

    private void doCopyDataLinkMapping(FormCopyContext context, String srcFormKey, String formKey) {
        List dataLinkMappings = this.designTimeViewController.listDataLinkMappingByForm(srcFormKey);
        if (CollectionUtils.isEmpty(dataLinkMappings)) {
            return;
        }
        for (DesignDataLinkMappingDefine mapping : dataLinkMappings) {
            DesignDataLinkMappingDefine newMapping = this.designTimeViewController.initDataLinkMapping();
            newMapping.setFormKey(formKey);
            String leftDataLink = this.replaceLinkKey(context, mapping.getLeftDataLinkKey());
            String rightDataLink = this.replaceLinkKey(context, mapping.getRightDataLinkKey());
            if (!StringUtils.hasText(leftDataLink) || !StringUtils.hasText(rightDataLink)) continue;
            newMapping.setLeftDataLinkKey(leftDataLink);
            newMapping.setRightDataLinkKey(rightDataLink);
            context.addLinkMappingInfo(mapping.getId(), newMapping);
        }
    }

    private String replaceLinkKey(FormCopyContext context, String srcLinkKey) {
        DesignDataLinkDefine link = context.getLinkInfo().get(srcLinkKey);
        if (null == link) {
            return null;
        }
        return link.getKey();
    }

    private void doCopyFormRegion(FormCopyContext context, String srcFormKey, String formKey) {
        Object newDataRegion;
        List srcRegions = this.designTimeViewController.listDataRegionByForm(srcFormKey);
        if (null == srcRegions || srcRegions.isEmpty()) {
            return;
        }
        List<Object> thisFormKeyToFormRegion = new ArrayList();
        Map<String, List<FormCopyDeleteFormRegionDefine>> formKeyToFormRegionMap = context.getFormKeyToFormRegionMap();
        if (formKeyToFormRegionMap != null && formKeyToFormRegionMap.get(formKey) != null) {
            thisFormKeyToFormRegion = formKeyToFormRegionMap.get(formKey);
        }
        Map<String, FormCopyDeleteFormRegionDefine> thisFormKeyToFormRegionMap = thisFormKeyToFormRegion.stream().collect(Collectors.toMap(FormCopyDeleteFormRegionDefine::getCode, a -> a, (k1, k2) -> k1));
        List srcRegionSettingKeys = srcRegions.stream().map(DataRegionDefine::getRegionSettingKey).filter(Objects::nonNull).collect(Collectors.toList());
        List designRegionSettingDefines = this.designTimeViewController.listRegionSetting(srcRegionSettingKeys);
        Map<String, DesignRegionSettingDefine> srcRegionSettings = designRegionSettingDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v, (a, b) -> a));
        HashMap<String, DesignDataTable> existDataTableMap = new HashMap<String, DesignDataTable>();
        for (DesignDataRegionDefine srcRegion : srcRegions) {
            newDataRegion = this.designFormCopyHelper.copyDataRegionTo(srcRegion, srcRegionSettings.get(srcRegion.getRegionSettingKey()), formKey, thisFormKeyToFormRegionMap.get(srcRegion.getCode()));
            context.addRegionInfo(srcRegion.getKey(), (DesignDataRegion)newDataRegion);
            if (context.isCopyDataModel()) {
                Map<String, DesignDataTable> regionExistDataTableMap = this.doCopyFormLinkAndDataModel(context, srcRegion.getKey(), formKey, ((DesignDataRegion)newDataRegion).getKey());
                if (!CollectionUtils.isEmpty(regionExistDataTableMap)) {
                    existDataTableMap.putAll(regionExistDataTableMap);
                }
                DesignDataRegionDefine region = ((DesignDataRegion)newDataRegion).getRegion();
                region.setHideZeroGatherFields(this.replaceFieldKey(context, region.getHideZeroGatherFields()));
                region.setGatherFields(this.replaceFieldKey(context, region.getGatherFields()));
                region.setSortFieldsList(this.replaceSortFieldsList(context, region.getSortFieldsList()));
                String inputOrderFieldKey = this.replaceFieldKey(context, region.getInputOrderFieldKey());
                if (StringUtils.hasText(inputOrderFieldKey)) {
                    inputOrderFieldKey = inputOrderFieldKey.equals(region.getInputOrderFieldKey()) ? null : inputOrderFieldKey;
                }
                region.setInputOrderFieldKey(inputOrderFieldKey);
                DesignRegionSettingDefine regionSetting = ((DesignDataRegion)newDataRegion).getRegionSetting();
                if (null == regionSetting) continue;
                List entityDefaultValues = regionSetting.getEntityDefaultValue();
                if (CollectionUtils.isEmpty(entityDefaultValues)) {
                    entityDefaultValues = srcRegionSettings.get(srcRegion.getRegionSettingKey()).getEntityDefaultValue();
                }
                if (!CollectionUtils.isEmpty(entityDefaultValues)) {
                    for (EntityDefaultValue entityDefaultValue : entityDefaultValues) {
                        entityDefaultValue.setFieldKey(this.replaceFieldKey(context, entityDefaultValue.getFieldKey()));
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        regionSetting.setEntityDefaultValue(mapper.writeValueAsString((Object)entityDefaultValues));
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
                regionSetting.setDictionaryFillLinks(this.replaceFieldKey(context, regionSetting.getDictionaryFillLinks()));
                RecordCard cardRecord = regionSetting.getCardRecord();
                if (cardRecord == null || CollectionUtils.isEmpty(cardRecord.getLinkTitleKey())) continue;
                ArrayList linkTitleKey = cardRecord.getLinkTitleKey();
                for (TitleAndKey titleAndKey : linkTitleKey) {
                    String id;
                    String dataFieldKey;
                    if (titleAndKey == null || !StringUtils.hasLength(titleAndKey.getId()) || !StringUtils.hasText(dataFieldKey = context.getCopiedDataFieldInfo(id = titleAndKey.getId()))) continue;
                    titleAndKey.setId(dataFieldKey);
                }
                continue;
            }
            this.doCopyFormLink(context, srcRegion.getKey(), ((DesignDataRegion)newDataRegion).getKey());
        }
        if (!CollectionUtils.isEmpty(existDataTableMap) && context.getMatchByCode()) {
            Collection values = existDataTableMap.values();
            DesignDataTable designDataTable = null;
            newDataRegion = values.iterator();
            while (newDataRegion.hasNext()) {
                DesignDataTable value;
                designDataTable = value = (DesignDataTable)newDataRegion.next();
            }
            if (designDataTable != null) {
                DesignFormDefine srcForm = this.designTimeViewController.getForm(srcFormKey);
                String message = String.format("\u5f00\u542f\u6307\u6807\u590d\u7528\uff0c\u62a5\u8868 %s \u4e0b\u6620\u5c04\u7684\u76f8\u5173\u6307\u6807\uff0c \u65b0\u589e\u7684\u6307\u6807\u5168\u90e8\u653e\u5230\u6307\u6807\u8868 %s[%s] \u91cc", srcForm.getTitle(), designDataTable.getTitle(), designDataTable.getCode());
                logger.info(message);
                StringBuilder formCopyMessage = context.getFormCopyMessage();
                formCopyMessage.append(message).append("\r\n");
            }
            for (DesignDataField newDataField : context.getDataFieldInfoForForms()) {
                if (existDataTableMap.containsKey(newDataField.getDataTableKey()) || designDataTable == null) continue;
                newDataField.setDataTableKey(designDataTable.getKey());
            }
        }
    }

    private String replaceFieldKey(FormCopyContext context, String fieldKeys) {
        if (!StringUtils.hasText(fieldKeys)) {
            return null;
        }
        String[] oldFieldKeys = fieldKeys.split(";");
        ArrayList<String> newFieldKeys = new ArrayList<String>(oldFieldKeys.length);
        for (int i = 0; i < oldFieldKeys.length; ++i) {
            String dataFieldKey = context.getCopiedDataFieldInfo(oldFieldKeys[i]);
            if (StringUtils.hasText(dataFieldKey)) {
                newFieldKeys.add(dataFieldKey);
                continue;
            }
            newFieldKeys.add(oldFieldKeys[i]);
        }
        return StringUtils.collectionToDelimitedString(newFieldKeys, ";");
    }

    private String replaceSortFieldsList(FormCopyContext context, String fieldKeys) {
        if (!StringUtils.hasText(fieldKeys)) {
            return null;
        }
        String[] oldFieldKeys = fieldKeys.split(";");
        ArrayList<String> newFieldKeys = new ArrayList<String>(oldFieldKeys.length);
        for (int i = 0; i < oldFieldKeys.length; ++i) {
            String oldFieldKey = oldFieldKeys[i];
            String substring = oldFieldKey.substring(0, oldFieldKey.length() - 1);
            if (StringUtils.hasText(context.getCopiedDataFieldInfo(substring))) {
                newFieldKeys.add(oldFieldKey.replace(substring, context.getCopiedDataFieldInfo(substring)));
                continue;
            }
            if (StringUtils.hasText(context.getCopiedDataFieldInfo(oldFieldKey))) {
                newFieldKeys.add(context.getCopiedDataFieldInfo(oldFieldKey));
                continue;
            }
            newFieldKeys.add(oldFieldKey);
        }
        return StringUtils.collectionToDelimitedString(newFieldKeys, ";");
    }

    private void doCopyFormLink(FormCopyContext context, String srcRegionKey, String regionKey) {
        List srcLinks = this.designTimeViewController.listDataLinkByDataRegion(srcRegionKey);
        if (null == srcLinks || srcLinks.isEmpty()) {
            return;
        }
        for (DesignDataLinkDefine srcLink : srcLinks) {
            DesignDataLinkDefine newDataLink = this.designFormCopyHelper.copyDataLinkTo(srcLink, regionKey);
            context.addLinkInfo(srcLink.getKey(), newDataLink);
        }
        this.doUpdateDataLink(context, srcLinks);
    }

    private Map<String, DesignDataTable> doCopyFormLinkAndDataModel(FormCopyContext context, String srcRegionKey, String formKey, String regionKey) {
        List srcLinks = this.designTimeViewController.listDataLinkByDataRegion(srcRegionKey);
        if (null == srcLinks || srcLinks.isEmpty()) {
            return null;
        }
        ArrayList<String> dataFieldKeys = new ArrayList<String>();
        for (DesignDataLinkDefine srcLink : srcLinks) {
            if (srcLink.getType() == DataLinkType.DATA_LINK_TYPE_FIELD || srcLink.getType() == DataLinkType.DATA_LINK_TYPE_INFO) {
                dataFieldKeys.add(srcLink.getLinkExpression());
            }
            DesignDataLinkDefine newDataLink = this.designFormCopyHelper.copyDataLinkTo(srcLink, regionKey);
            context.addLinkInfo(srcLink.getKey(), newDataLink);
        }
        if (dataFieldKeys.isEmpty()) {
            return null;
        }
        Map<String, DesignDataTable> existDataTableMap = this.doSyncDataModel(context, formKey, dataFieldKeys);
        this.doUpdateDataLink(context, srcLinks);
        return existDataTableMap;
    }

    private void doUpdateDataLink(FormCopyContext context, List<DesignDataLinkDefine> srcLinks) {
        for (DesignDataLinkDefine srcLink : srcLinks) {
            String enumLinkage;
            DesignDataLinkDefine newDataLink = context.getLinkInfo().get(srcLink.getKey());
            if ((srcLink.getType() == DataLinkType.DATA_LINK_TYPE_FIELD || srcLink.getType() == DataLinkType.DATA_LINK_TYPE_INFO) && context.isCopyDataModel()) {
                String srcDataFieldKey = srcLink.getLinkExpression();
                String dataFieldKey = context.getCopiedDataFieldInfo(srcDataFieldKey);
                if (StringUtils.hasLength(dataFieldKey)) {
                    newDataLink.setLinkExpression(dataFieldKey);
                }
                newDataLink.setCaptionFieldsString(this.replaceFieldKey(context, newDataLink.getCaptionFieldsString()));
                newDataLink.setDropDownFieldsString(this.replaceFieldKey(context, newDataLink.getDropDownFieldsString()));
                newDataLink.setEnumTitleField(this.replaceFieldKey(context, newDataLink.getEnumTitleField()));
            }
            if (!StringUtils.hasLength(enumLinkage = srcLink.getEnumLinkage())) continue;
            Map enumLinkageData = srcLink.getEnumLinkageData();
            for (Map.Entry entry : enumLinkageData.entrySet()) {
                DesignDataLinkDefine link = context.getLinkInfo().get(entry.getValue());
                if (link == null) continue;
                enumLinkage = enumLinkage.replaceAll((String)entry.getValue(), link.getKey());
            }
            newDataLink.setEnumLinkage(enumLinkage);
        }
    }

    private Map<String, DesignDataTable> doSyncDataModel(FormCopyContext context, String formKey, List<String> dataFieldKeys) {
        List srcDataFields = this.iDesignDataSchemeService.getDataFields(dataFieldKeys);
        Set srcDataTableKeys = srcDataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
        List srcDataTables = this.iDesignDataSchemeService.getDataTables(new ArrayList(srcDataTableKeys));
        Map<String, DesignDataTable> existDataTableMap = this.doSyncDataTables(context, srcDataTables, formKey);
        Map<String, DesignDataTable> hasSameCodeFieldDataTable = this.doSyncFields(context, srcDataFields);
        existDataTableMap.putAll(hasSameCodeFieldDataTable);
        return existDataTableMap;
    }

    private Map<String, DesignDataTable> doSyncFields(FormCopyContext context, List<DesignDataField> srcDataFields) {
        DesignDataField newDataField = null;
        HashMap<String, DesignDataTable> hasSameCodeFieldDataTable = new HashMap<String, DesignDataTable>();
        for (DesignDataField srcDataField : srcDataFields) {
            DesignDataTable sameCodeDataFieldTable;
            String newDataTableKey = context.getCopiedDataTableInfo(srcDataField.getDataTableKey());
            String newDataFieldKey = context.getCopiedDataFieldInfo(srcDataField.getKey());
            if (!StringUtils.hasText(newDataFieldKey)) {
                if (context.getMatchByCode() && DataFieldKind.FIELD_ZB == srcDataField.getDataFieldKind() && this.designFormCopyHelper.hasSameZBCode(context, srcDataField)) {
                    sameCodeDataFieldTable = this.designFormCopyHelper.copySameCodeZB(context, srcDataField, this.getFieldCodeSetter(context));
                    if (sameCodeDataFieldTable == null) continue;
                    hasSameCodeFieldDataTable.put(sameCodeDataFieldTable.getKey(), sameCodeDataFieldTable);
                    continue;
                }
                newDataField = this.designFormCopyHelper.copyDataFieldTo(srcDataField, context.getDataSchemeKey(), newDataTableKey, this.getFieldCodeSetter(context));
                context.addDataFieldInfo(srcDataField, newDataField);
                if (DataFieldKind.FIELD_ZB != srcDataField.getDataFieldKind() || newDataField.getDataTableKey().equals(context.getMdInfoDataTableKey())) continue;
                context.addDataFieldInfoForForm(srcDataField, newDataField);
                continue;
            }
            if (null == context.getDataField(newDataFieldKey)) {
                if (context.getMatchByCode() && DataFieldKind.FIELD_ZB == srcDataField.getDataFieldKind() && this.designFormCopyHelper.hasSameZBCode(context, srcDataField)) {
                    sameCodeDataFieldTable = this.designFormCopyHelper.copySameCodeZB(context, srcDataField, this.getFieldCodeSetter(context));
                    if (sameCodeDataFieldTable == null) continue;
                    hasSameCodeFieldDataTable.put(sameCodeDataFieldTable.getKey(), sameCodeDataFieldTable);
                    continue;
                }
                newDataField = this.designFormCopyHelper.copyDataFieldTo(srcDataField, context.getDataSchemeKey(), newDataTableKey, this.getFieldCodeSetter(context));
                newDataField.setKey(newDataFieldKey);
                context.addDataFieldInfo(srcDataField, newDataField);
                if (DataFieldKind.FIELD_ZB != srcDataField.getDataFieldKind() || newDataField.getDataTableKey().equals(context.getMdInfoDataTableKey())) continue;
                context.addDataFieldInfoForForm(srcDataField, newDataField);
                continue;
            }
            newDataField = context.getDataField(newDataFieldKey);
            if (0 == newDataField.getUpdateTime().compareTo(srcDataField.getUpdateTime())) continue;
            String oldCode = this.designFormCopyHelper.syncDataFieldTo(srcDataField, newDataField, newDataTableKey, this.getFieldCodeSetter(context));
            newDataField.setKey(newDataFieldKey);
            context.addUpdateDataField(newDataField, oldCode);
        }
        return hasSameCodeFieldDataTable;
    }

    private String getDataGroupKey(FormCopyContext context, String formKey) {
        String groupKey;
        if (context.containsDataGroup(formKey)) {
            return context.getDataGroupInfo(formKey);
        }
        if (!context.containsDataGroup(context.getFormSchemeKey())) {
            this.createDataGroup(context, context.getFormSchemeKey(), null, context.getFormSchemeTitle());
        }
        if (!context.containsDataGroup(groupKey = context.getFormGroupLink().get(formKey))) {
            String groupTitle = null;
            for (DesignFormGroupDefine formGroup : context.getFormGroupInfo().values()) {
                if (!formGroup.getKey().equals(groupKey)) continue;
                groupTitle = formGroup.getTitle();
                break;
            }
            if (!StringUtils.hasText(groupTitle)) {
                DesignFormGroupDefine formGroup = this.designTimeViewController.getFormGroup(groupKey);
                groupTitle = formGroup.getTitle();
            }
            this.createDataGroup(context, groupKey, context.getFormSchemeKey(), groupTitle);
        }
        if (!context.containsDataGroup(formKey)) {
            String formTitle = null;
            for (DesignFormDefine form : context.getFormInfo().values()) {
                if (!form.getKey().equals(formKey)) continue;
                formTitle = form.getTitle();
                break;
            }
            this.createDataGroup(context, formKey, groupKey, formTitle);
        }
        return context.getDataGroupInfo(formKey);
    }

    private Map<String, DesignDataTable> doSyncDataTables(FormCopyContext context, List<DesignDataTable> srcDataTables, String formKey) {
        DesignDataTable newDataTable = null;
        HashMap<String, DesignDataTable> existDataTable = new HashMap<String, DesignDataTable>();
        boolean matchByCode = context.getMatchByCode();
        for (DesignDataTable srcDataTable : srcDataTables) {
            Map<DesignDataField, DesignDataField> map;
            DesignDataTable dataTableByCode;
            List mdDataTable;
            if (srcDataTable.getDataTableType() == DataTableType.MD_INFO && (mdDataTable = this.iDesignDataSchemeService.getAllDataTableBySchemeAndTypes(context.getDataSchemeKey(), new DataTableType[]{DataTableType.MD_INFO})).size() > 0) {
                context.addCopiedDataTableInfo(srcDataTable.getKey(), ((DesignDataTable)mdDataTable.get(0)).getKey());
                context.setMdInfoDataTableKey(((DesignDataTable)mdDataTable.get(0)).getKey());
                logger.info("\u6307\u6807\u8868 {} \u662f\u5355\u4f4d\u4fe1\u606f\u8868\uff0c\u4e14\u76ee\u6807\u6570\u636e\u65b9\u6848\u4e0b\u4e5f\u6709\u5355\u4f4d\u4fe1\u606f\u8868\uff0c\u8df3\u8fc7\u4e0d\u5904\u7406", (Object)srcDataTable.getTitle());
                continue;
            }
            String newDataTableKey = context.getCopiedDataTableInfo(srcDataTable.getKey());
            if (!StringUtils.hasText(newDataTableKey)) {
                dataTableByCode = null;
                if (srcDataTable.getDataTableType() == DataTableType.TABLE && matchByCode && (dataTableByCode = this.getDesignDataTableByCode(context, srcDataTable)) != null && dataTableByCode.getDataSchemeKey().equals(context.getDataSchemeKey())) {
                    newDataTable = dataTableByCode;
                    context.addCopiedDataTableInfo(srcDataTable.getKey(), newDataTable.getKey());
                    context.addDataTableCopyRecordInfo(srcDataTable, newDataTable);
                    existDataTable.put(newDataTable.getKey(), newDataTable);
                    logger.info("\u65b0\u589e\u6307\u6807\u8868 {} \u65f6\u5019\u590d\u7528\u6307\u6807\u8868 {}", (Object)srcDataTable.getTitle(), (Object)newDataTable.getTitle());
                } else {
                    newDataTable = this.designFormCopyHelper.copyDataTableTo(srcDataTable, context.getDataSchemeKey(), srcDataTable.getDataTableType() == DataTableType.MD_INFO ? null : this.getDataGroupKey(context, formKey), context.getSrcPrefix(), context.getPrefix());
                    newDataTableKey = newDataTable.getKey();
                    context.addDataTableInfo(srcDataTable, newDataTable);
                    logger.info("\u65b0\u589e\u6307\u6807\u8868 {}", (Object)newDataTable.getTitle());
                }
                if (DataTableType.TABLE != srcDataTable.getDataTableType() && DataTableType.MD_INFO != srcDataTable.getDataTableType()) {
                    map = this.designFormCopyHelper.copyAllDataFieldTo(context, srcDataTable.getKey(), context.getDataSchemeKey(), newDataTableKey, false, this.getFieldCodeSetter(context));
                }
            } else if (null == context.getDataTable(newDataTableKey)) {
                dataTableByCode = null;
                if (srcDataTable.getDataTableType() == DataTableType.TABLE && matchByCode && (dataTableByCode = this.getDesignDataTableByCode(context, srcDataTable)) != null && dataTableByCode.getDataSchemeKey().equals(context.getDataSchemeKey())) {
                    newDataTable = dataTableByCode;
                    context.addCopiedDataTableInfo(srcDataTable.getKey(), newDataTable.getKey());
                    context.addDataTableCopyRecordInfo(srcDataTable, newDataTable);
                    existDataTable.put(newDataTable.getKey(), newDataTable);
                    logger.info("\u6307\u6807\u8868 {} \u6709\u590d\u5236\u8bb0\u5f55\u4f46\u662f\u4e0d\u5b58\u5728\uff0c\u5b58\u5728\u76f8\u540ccode\u7684\u6307\u6807\u8868 {} \uff0c\u8d70\u590d\u7528", (Object)srcDataTable.getTitle(), (Object)newDataTable.getTitle());
                } else {
                    newDataTable = this.designFormCopyHelper.copyDataTableTo(srcDataTable, context.getDataSchemeKey(), srcDataTable.getDataTableType() == DataTableType.MD_INFO ? null : this.getDataGroupKey(context, formKey), context.getSrcPrefix(), context.getPrefix());
                    newDataTable.setKey(newDataTableKey);
                    context.addDataTableInfo(srcDataTable, newDataTable);
                    logger.info("\u6307\u6807\u8868 {} \u6709\u590d\u5236\u8bb0\u5f55\u4f46\u662f\u4e0d\u5b58\u5728\uff0c\u8d70\u65b0\u589e {}", (Object)srcDataTable.getTitle(), (Object)newDataTable.getTitle());
                }
                if (DataTableType.TABLE != srcDataTable.getDataTableType() && DataTableType.MD_INFO != srcDataTable.getDataTableType()) {
                    map = this.designFormCopyHelper.copyAllDataFieldTo(context, srcDataTable.getKey(), context.getDataSchemeKey(), newDataTableKey, true, this.getFieldCodeSetter(context));
                }
            } else {
                newDataTable = context.getDataTable(newDataTableKey);
                if (newDataTable.getDataTableType() == DataTableType.TABLE) {
                    existDataTable.put(newDataTableKey, newDataTable);
                }
                if (0 != newDataTable.getUpdateTime().compareTo(srcDataTable.getUpdateTime()) && newDataTable.getDataTableType() != DataTableType.MD_INFO) {
                    String[] bizKeys = newDataTable.getBizKeys();
                    String desTableCode = newDataTable.getCode();
                    this.designFormCopyHelper.syncDataTableTo(srcDataTable, newDataTable, context.getSrcPrefix(), context.getPrefix());
                    newDataTable.setKey(newDataTableKey);
                    newDataTable.setBizKeys(bizKeys);
                    if (context.getUpdateDataTables().contains(newDataTable)) {
                        if (this.fieldTableCodeEqual(context, srcDataTable, desTableCode)) {
                            context.addUpdateDataTable(newDataTable);
                            logger.info("\u6307\u6807\u8868 {} \u6709\u590d\u5236\u8bb0\u5f55\uff0c\u8d70\u66f4\u65b0 {}", (Object)srcDataTable.getTitle(), (Object)newDataTable.getTitle());
                        }
                    } else {
                        context.addUpdateDataTable(newDataTable);
                        logger.info("\u6307\u6807\u8868 {} \u6709\u590d\u5236\u8bb0\u5f55\uff0c\u8d70\u66f4\u65b0 {}", (Object)srcDataTable.getTitle(), (Object)newDataTable.getTitle());
                    }
                }
            }
            if (newDataTable == null || newDataTable.getDataTableType() != DataTableType.MD_INFO) continue;
            context.setMdInfoDataTableKey(newDataTable.getKey());
        }
        return existDataTable;
    }

    private boolean fieldTableCodeEqual(FormCopyContext context, DesignDataTable srcDataTable, String desTableCode) {
        String srcDataTableCode = srcDataTable.getCode();
        String srcPrefix = context.getSrcPrefix();
        String prefix = context.getPrefix();
        if (StringUtils.hasText(prefix) || StringUtils.hasText(srcPrefix)) {
            String srcCode = srcDataTableCode.replaceFirst(srcPrefix, "");
            String desCode = desTableCode.replaceFirst(prefix, "");
            return srcCode.equals(desCode);
        }
        return srcDataTableCode.equals(desTableCode);
    }

    private DesignDataTable getDesignDataTableByCode(FormCopyContext context, DesignDataTable srcDataTable) {
        DesignDataTable dataTableByCode;
        String srcDataTableCode = srcDataTable.getCode();
        String srcPrefix = context.getSrcPrefix();
        String prefix = context.getPrefix();
        if (StringUtils.hasText(prefix) || StringUtils.hasText(srcPrefix)) {
            String srcCode = srcDataTableCode.replaceFirst(srcPrefix, "");
            srcDataTableCode = prefix + srcCode;
        }
        if ((dataTableByCode = context.getDataTableByCode(srcDataTableCode)) != null && dataTableByCode.getDataTableType() == DataTableType.TABLE) {
            return dataTableByCode;
        }
        return null;
    }

    private Consumer<DesignDataField> getFieldCodeSetter(FormCopyContext context) {
        return f -> {
            if (DataFieldKind.FIELD_ZB == f.getDataFieldKind()) {
                Set<String> zbFieldCodeSet = context.getZbFieldCodeSet();
                String newcode = DesignFormCopyHelper.getCode(f.getCode(), c -> !zbFieldCodeSet.contains(c));
                f.setCode(newcode);
            } else {
                Set<String> fieldCodeSet = context.getFieldCodeSet(f.getDataTableKey());
                if (null != fieldCodeSet && !fieldCodeSet.isEmpty()) {
                    String newcode = DesignFormCopyHelper.getCode(f.getCode(), c -> !fieldCodeSet.contains(c));
                    f.setCode(newcode);
                }
            }
        };
    }

    private void createDataGroup(FormCopyContext context, String paramObjKey, String parentParamObjKey, String groupTitle) {
        if (!context.isCopyDataModel() || context.containsDataGroup(paramObjKey)) {
            return;
        }
        boolean insertGroup = false;
        String parentGroupKey = null;
        List dataGroups = null;
        if (StringUtils.hasLength(parentParamObjKey)) {
            parentGroupKey = context.getDataGroupInfo(parentParamObjKey);
            dataGroups = this.iDesignDataSchemeService.getDataGroupByParent(parentGroupKey);
        } else {
            parentGroupKey = null;
            dataGroups = this.iDesignDataSchemeService.getDataGroupByScheme(context.getDataSchemeKey());
        }
        DesignDataGroup exsitGroup = null;
        for (DesignDataGroup designDataGroup : dataGroups) {
            if (!designDataGroup.getTitle().equals(groupTitle)) continue;
            exsitGroup = designDataGroup;
            break;
        }
        if (null == exsitGroup) {
            exsitGroup = this.iDesignDataSchemeService.initDataGroup();
            exsitGroup.setTitle(groupTitle);
            exsitGroup.setParentKey(parentGroupKey);
            exsitGroup.setDataSchemeKey(context.getDataSchemeKey());
            insertGroup = true;
        }
        context.addDataGroupInfo(paramObjKey, exsitGroup, insertGroup);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public FormulaSyncResultAll doSync(String srcFormSchemeKey, String formSchemeKey, List<FormSyncParams> formSyncParams, FormCopyAttSchemeMap attScheemMap, boolean ifCopySchemes, AsyncTaskMonitor monitor, double startProgress, double thisProgress) throws Exception {
        if (null == formSyncParams || formSyncParams.isEmpty()) {
            return new FormulaSyncResultAll();
        }
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        DesignFormSchemeDefine srcFormScheme = this.designTimeViewController.getFormScheme(srcFormSchemeKey);
        String message = "\u62a5\u8868\u65b9\u6848  " + srcFormScheme.getTitle() + " \u540c\u6b65\u62a5\u8868\u5230 " + formScheme.getTitle();
        String startMessage = message + " \u5f00\u59cb\u6267\u884c\uff01";
        logger.info(startMessage);
        FormCopyUtil.setProgressAndMessage(monitor, startProgress, startMessage);
        FormCopyUtil.setProgressAndMessage(monitor, startProgress, "\u62a5\u8868\u540c\u6b65\u5f00\u59cb\u5904\u7406\u65e7\u62a5\u8868\uff01");
        try {
            ArrayList<FormCopyParams> copyParams = new ArrayList<FormCopyParams>();
            HashMap<String, String> formMap = new HashMap<String, String>();
            HashMap<String, List<FormCopyDeleteFormRegionDefine>> formKeyToFormRegionMap = new HashMap<String, List<FormCopyDeleteFormRegionDefine>>();
            for (FormSyncParams param : formSyncParams) {
                formMap.put(param.getSrcFormKey(), param.getFormKey());
                DesignFormDefine srcForm = this.designTimeViewController.getForm(param.getSrcFormKey());
                DesignFormDefine form = this.designTimeViewController.getForm(param.getFormKey());
                if (0 == srcForm.getUpdateTime().compareTo(form.getUpdateTime())) continue;
                List designFormGroupDefines = this.designTimeViewController.listFormGroupByForm(form.getKey());
                FormCopyParams formCopyParams = new FormCopyParams(param, ((DesignFormGroupDefine)designFormGroupDefines.get(0)).getKey(), null);
                if (!StringUtils.hasText(formCopyParams.getFormCode())) {
                    formCopyParams.setFormCode(form.getFormCode());
                }
                if (!StringUtils.hasText(formCopyParams.getFormTitle())) {
                    formCopyParams.setFormTitle(form.getTitle());
                }
                formCopyParams.setOrder(form.getOrder());
                List<FormCopyDeleteFormRegionDefine> formCopyDeleteFormRegionDefines = this.deleteFormAndRegion(form.getKey(), ((DesignFormGroupDefine)designFormGroupDefines.get(0)).getKey());
                formKeyToFormRegionMap.put(form.getKey(), formCopyDeleteFormRegionDefines);
                copyParams.add(formCopyParams);
            }
            double newStartProgress = startProgress + 0.1 * thisProgress;
            FormCopyUtil.setProgressAndMessage(monitor, newStartProgress, "\u5904\u7406\u65e7\u62a5\u8868\u5b8c\u6210\uff0c\u51c6\u5907\u590d\u5236\u8fd9\u5bf9\u62a5\u8868\u65b9\u6848\u7684\u62a5\u8868");
            logger.info("\u65e7\u62a5\u8868\u5904\u7406\u5b8c\u6210\uff0c\u51c6\u5907\u590d\u5236\u62a5\u8868");
            FormulaSyncResultAll formulaSyncResultAll = this.doCopy(srcFormSchemeKey, formSchemeKey, copyParams, formMap, attScheemMap, ifCopySchemes, monitor, newStartProgress, thisProgress * 0.9, formKeyToFormRegionMap);
            String finishMessage = message + " \u5b8c\u6210\uff01";
            logger.info(finishMessage);
            FormCopyUtil.setProgressAndMessage(monitor, startProgress + thisProgress, finishMessage);
            return formulaSyncResultAll;
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u540c\u6b65\u6267\u884c\u5f02\u5e38", e);
            throw new Exception(e.getMessage());
        }
    }

    private List<FormCopyDeleteFormRegionDefine> deleteFormAndRegion(String formKey, String formGroupKey) {
        this.designTimeViewController.deleteFormDefine(formKey);
        this.conditionalStyleProvider.deleteCSInForm(formKey);
        List designDataRegionDefines = this.designTimeViewController.listDataRegionByForm(formKey);
        List<FormCopyDeleteFormRegionDefine> formCopyDeleteFormRegionDefines = FormCopyDeleteFormRegionDefine.valueOfs(designDataRegionDefines);
        String[] regionKeys = (String[])designDataRegionDefines.stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
        this.designTimeViewController.deleteDataRegion(regionKeys);
        DesignFormGroupLink delFormGroupLink = new DesignFormGroupLink();
        delFormGroupLink.setGroupKey(formGroupKey);
        delFormGroupLink.setFormKey(formKey);
        this.designTimeViewController.deleteFormGroupLink(new DesignFormGroupLink[]{delFormGroupLink});
        return formCopyDeleteFormRegionDefines;
    }

    private void doCopyI18n(FormCopyContext context) throws JQException {
        this.doCopyFormI18n(context.getI18nResourceMap());
        this.doCopyFormStyleI18n(context.getFormInfo().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> ((DesignFormDefine)e.getValue()).getKey())));
        if (context.isCopyDataModel()) {
            this.doCopyFieldI18n(context.getSrcDataSchemeKey(), context.getDataSchemeKey(), context.getDataFieldInfo().entrySet().stream().collect(Collectors.toMap(e -> ((DesignDataField)e.getKey()).getKey(), e -> ((DesignDataField)e.getValue()).getKey())));
        }
    }

    private void doCopyFormI18n(Map<String, String> resourceMap) throws JQException {
        if (CollectionUtils.isEmpty(resourceMap)) {
            return;
        }
        List list = this.paramLanguageDao.queryLanguageByResKeys(new ArrayList<String>(resourceMap.keySet()));
        ArrayList<DesParamLanguage> newList = new ArrayList<DesParamLanguage>();
        for (DesParamLanguage item : list) {
            String resourceKey = resourceMap.get(item.getResourceKey());
            if (!StringUtils.hasLength(resourceKey)) continue;
            item.setKey(UUIDUtils.getKey());
            item.setResourceKey(resourceKey);
            newList.add(item);
        }
        if (CollectionUtils.isEmpty(newList)) {
            return;
        }
        try {
            this.paramLanguageDao.deleteLanguageByResKeys(newList.stream().map(DesParamLanguage::getResourceKey).collect(Collectors.toList()));
            this.paramLanguageDao.batchInsert(newList.toArray(new DesParamLanguage[0]));
        }
        catch (DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_008, (Throwable)e);
        }
    }

    private void doCopyFormStyleI18n(Map<String, String> formMap) throws JQException {
        List bigDatas = this.bigDataTableDao.queryBigDataDefine(new ArrayList<String>(formMap.keySet()), null, null);
        ArrayList<DesignBigDataTable> newBigDatas = new ArrayList<DesignBigDataTable>();
        for (DesignBigDataTable bigData : bigDatas) {
            String newKey;
            if (bigData.getLang() == LanguageType.CHINESE.getValue() || !StringUtils.hasLength(newKey = formMap.get(bigData.getKey()))) continue;
            bigData.setKey(newKey);
            newBigDatas.add(bigData);
        }
        if (CollectionUtils.isEmpty(newBigDatas)) {
            return;
        }
        try {
            this.bigDataTableDao.delete(newBigDatas);
            this.bigDataTableDao.insert(newBigDatas.toArray());
        }
        catch (BeanParaException | DBParaException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)FormCopyExceptionEnum.FORMCOPY_ERROR_008, e);
        }
    }

    private void doCopyFieldI18n(String srcDataSchemeKey, String dataSchemeKey, Map<String, String> fieldMap) {
        if (srcDataSchemeKey.equals(dataSchemeKey)) {
            return;
        }
        Map<String, DesignDataSchemeI18nDO> dataFieldI18nMap = this.dataSchemeI18nDao.getBySchemeKey(srcDataSchemeKey, null).stream().collect(Collectors.toMap(DataSchemeI18nDO::getKey, v -> v));
        ArrayList<DesignDataSchemeI18nDO> list = new ArrayList<DesignDataSchemeI18nDO>();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            DesignDataSchemeI18nDO d = dataFieldI18nMap.get(entry.getKey());
            if (null == d || !StringUtils.hasLength(entry.getValue())) continue;
            d.setKey(entry.getValue());
            d.setDataSchemeKey(dataSchemeKey);
            list.add(d);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        this.dataSchemeI18nDao.delete(list);
        this.dataSchemeI18nDao.insert(list.toArray());
    }
}

