/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.data.text.api.IFieldDataFileServiceFactory
 *  com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData
 *  com.jiuqi.nr.datapartnerapi.domain.FieldInfo
 *  com.jiuqi.nr.datapartnerapi.domain.FormInfo
 *  com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfo
 *  com.jiuqi.nr.datapartnerapi.domain.TaskInfo
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldService
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeDataRegionDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeSchemePeriodLinkDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao
 *  com.jiuqi.nr.definition.internal.dao.TaskOrgLinkDao
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.datapartnerapi.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.data.text.api.IFieldDataFileServiceFactory;
import com.jiuqi.nr.datapartnerapi.common.GrowthRateTypeEnum;
import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthData;
import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataDTO;
import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryDTO;
import com.jiuqi.nr.datapartnerapi.domain.FieldInfo;
import com.jiuqi.nr.datapartnerapi.domain.FieldInfoDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormDataQueryDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormInfo;
import com.jiuqi.nr.datapartnerapi.domain.FormInfoDTO;
import com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfo;
import com.jiuqi.nr.datapartnerapi.domain.FormSchemaInfoDTO;
import com.jiuqi.nr.datapartnerapi.domain.SchemaPeriodDTO;
import com.jiuqi.nr.datapartnerapi.domain.TaskInfo;
import com.jiuqi.nr.datapartnerapi.domain.TaskInfoDTO;
import com.jiuqi.nr.datapartnerapi.exception.FormDataApiException;
import com.jiuqi.nr.datapartnerapi.service.FormDataApiService;
import com.jiuqi.nr.datapartnerapi.util.GrowthRateUtils;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataRegionDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeSchemePeriodLinkDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.dao.TaskOrgLinkDao;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.util.StringUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDataApiServiceImpl
implements FormDataApiService {
    @Autowired
    private RunTimeTaskDefineDao runTimeTaskDefineDao;
    @Autowired
    private TaskOrgLinkDao taskOrgLinkDao;
    @Autowired
    private RunTimeFormSchemeDefineDao runTimeFormSchemeDefineDao;
    @Autowired
    private RunTimeSchemePeriodLinkDao runTimeSchemePeriodLinkDao;
    @Autowired
    private RunTimeFormGroupDefineDao runTimeFormGroupDefineDao;
    @Autowired
    private RunTimeFormDefineDao runTimeFormDefineDao;
    @Autowired
    private RunTimeDataRegionDefineDao runTimeDataRegionDefineDao;
    @Autowired
    private RunTimeDataLinkDefineDao runTimeDataLinkDefineDao;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataFieldService dataFieldService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController apiRunTimeViewController;
    @Autowired
    private IFieldDataFileServiceFactory fieldDataFileServiceFactory;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IFormConditionAccessService formConditionAccessService;
    @Autowired
    private IRuntimeFormSchemePeriodService runtimeFormSchemePeriodService;
    @Resource(name="RuntimeSchemeServiceImpl-NO_CACHE")
    private DataSchemeService dataSchemeService;
    private static final Logger logger = LoggerFactory.getLogger(FormDataApiServiceImpl.class);

    @Override
    public List<TaskInfo> queryAllForms(String taskKey, String formSchemeKey) {
        List<TaskDefine> taskDefines;
        ArrayList<TaskInfo> returnTasks = new ArrayList<TaskInfo>();
        FormSchemeDefine formSchemeDefine = null;
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            formSchemeDefine = this.runTimeFormSchemeDefineDao.getDefineByKey(formSchemeKey);
            if (formSchemeDefine == null || StringUtils.isNotEmpty((String)taskKey) && !formSchemeDefine.getTaskKey().equals(taskKey)) {
                return Collections.emptyList();
            }
            taskKey = formSchemeDefine.getTaskKey();
        }
        if (StringUtils.isNotEmpty((String)taskKey)) {
            TaskDefine taskDefine = this.runTimeTaskDefineDao.getDefineByKey(taskKey);
            if (formSchemeDefine != null) {
                TaskInfoDTO taskInfo = new TaskInfoDTO(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle());
                if (this.setOtherParamsByTaskDefine(taskInfo, taskDefine)) {
                    FormSchemaInfo formSchemaInfo = this.queryFormSchemaByDefines(taskDefine, formSchemeDefine);
                    if (formSchemaInfo != null) {
                        taskInfo.getFormSchemas().add(formSchemaInfo);
                    }
                    taskInfo.setTimestamp(System.currentTimeMillis());
                    returnTasks.add(taskInfo);
                }
                return returnTasks;
            }
            taskDefines = Collections.singletonList(taskDefine);
        } else {
            taskDefines = this.runTimeTaskDefineDao.list();
        }
        for (TaskDefine taskDefine : taskDefines) {
            TaskInfoDTO taskInfo;
            if (taskDefine == null || !this.setOtherParamsByTaskDefine(taskInfo = new TaskInfoDTO(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle()), taskDefine)) continue;
            List<FormSchemaInfo> formSchemas = this.queryFormSchemasByTask(taskDefine);
            taskInfo.getFormSchemas().addAll(formSchemas);
            taskInfo.setTimestamp(System.currentTimeMillis());
            returnTasks.add(taskInfo);
        }
        return returnTasks;
    }

    @Override
    public TaskInfo queryTaskByKey(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine != null) {
            return new TaskInfoDTO(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle());
        }
        return null;
    }

    @Override
    public FormInfo queryFormByKey(String formKey) {
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        if (formDefine != null) {
            return new FormInfoDTO(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
        }
        return null;
    }

    @Override
    public List<FieldInfo> queryFormFields(String formKey) {
        ArrayList<FieldInfo> returnFields = new ArrayList<FieldInfo>();
        List dataRegionDefines = this.runTimeDataRegionDefineDao.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            FieldInfoDTO fieldInfo;
            HashMap<String, FieldInfoDTO> fieldInfoMap = new HashMap<String, FieldInfoDTO>();
            if (dataRegionDefine == null) continue;
            String regionKey = dataRegionDefine.getKey();
            boolean isFloatRegion = !DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind());
            List dataLinkDefines = this.runTimeDataLinkDefineDao.getAllLinksInRegion(regionKey);
            for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                if (dataLinkDefine == null) continue;
                String fieldKey = dataLinkDefine.getLinkExpression();
                fieldInfo = new FieldInfoDTO(fieldKey, null, null, String.valueOf(dataLinkDefine.getPosY()), String.valueOf(dataLinkDefine.getPosX()));
                fieldInfoMap.put(fieldKey, fieldInfo);
            }
            List fieldDTOS = this.dataFieldService.getDataFields(new ArrayList(fieldInfoMap.keySet()));
            for (DataFieldDTO fieldDTO : fieldDTOS) {
                if (fieldDTO == null || (fieldInfo = (FieldInfoDTO)fieldInfoMap.get(fieldDTO.getKey())) == null) continue;
                fieldInfo.setFieldCode(fieldDTO.getCode());
                fieldInfo.setFieldTitle(fieldDTO.getTitle());
                fieldInfo.setFloatRegion(isFloatRegion);
                if (isFloatRegion) {
                    fieldInfo.setRegionKey(regionKey);
                }
                returnFields.add(fieldInfo);
            }
        }
        return returnFields;
    }

    /*
     * Exception decompiling
     */
    @Override
    public byte[] downloadFormStyle(FormInfo formInfo) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public List<String> getAccessForms(FormAuthorityFilterDTO formAuthorityFilterDTO) {
        String taskKey = formAuthorityFilterDTO.getTaskKey();
        String formSchemeKey = formAuthorityFilterDTO.getFormSchemeKey();
        String mdCode = formAuthorityFilterDTO.getMdCode();
        String dataTime = formAuthorityFilterDTO.getDatatime();
        if (StringUtils.isEmpty((String)taskKey) && StringUtils.isEmpty((String)formSchemeKey) || StringUtils.isEmpty((String)mdCode) || StringUtils.isEmpty((String)dataTime)) {
            return Collections.emptyList();
        }
        ArrayList allFormKeys = new ArrayList();
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runtimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(formSchemeKey, dataTime);
            if (schemePeriodLinkDefine == null) {
                return Collections.emptyList();
            }
        } else if (StringUtils.isEmpty((String)formSchemeKey) && StringUtils.isNotEmpty((String)taskKey)) {
            List formSchemeDefines;
            try {
                formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
            }
            catch (Exception e) {
                logger.error("\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5217\u8868\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
                return Collections.emptyList();
            }
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                String key = formSchemeDefine.getKey();
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.runtimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(key, dataTime);
                if (schemePeriodLinkDefine == null) continue;
                formSchemeKey = key;
                break;
            }
        }
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
            allFormKeys.addAll(formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        }
        if (!allFormKeys.isEmpty()) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
            String entityID = Optional.ofNullable(formAuthorityFilterDTO.getEntityID()).orElse(formSchemeDefine.getDw());
            this.setDsContextEntityID(entityID);
            IEntityDefine dwEntity = this.iEntityMetaService.queryEntity(entityID);
            DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
            builder.setDWValue(dwEntity.getDimensionName(), entityID, new Object[]{mdCode});
            builder.setEntityValue("DATATIME", formSchemeDefine.getDateTime(), new Object[]{dataTime});
            DimensionCollection dimensionCollection = builder.getCollection();
            DimensionCombination dimensionCombination = (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0);
            IBatchAccess batchVisible = this.formConditionAccessService.getBatchVisible(formSchemeKey, dimensionCollection, allFormKeys);
            if (batchVisible != null) {
                ArrayList<String> accessFormKeys = new ArrayList<String>();
                for (String formKey : allFormKeys) {
                    AccessCode accessCode = batchVisible.getAccessCode(dimensionCombination, formKey);
                    if (!"1".equals(accessCode.getCode())) continue;
                    accessFormKeys.add(formKey);
                }
                return accessFormKeys;
            }
        }
        return Collections.emptyList();
    }

    /*
     * Exception decompiling
     */
    @Override
    public byte[] exportTaskData(FormDataQueryDTO formDataQueryDTO) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public Map<String, FieldGrowthData> queryFieldsGrowthData(FieldGrowthDataQueryDTO fieldGrowthDataQueryDTO) {
        HashMap<String, FieldGrowthData> returnMap = new HashMap<String, FieldGrowthData>();
        List queryFields = fieldGrowthDataQueryDTO.getQueryFields();
        if (queryFields.isEmpty()) {
            return returnMap;
        }
        String formSchemeKey = fieldGrowthDataQueryDTO.getFormSchemeKey();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formSchemeDefine == null) {
            logger.info("\u6839\u636e\u4f20\u5165\u7684\u62a5\u8868\u65b9\u6848key\uff1a\u3010{}\u3011\u672a\u67e5\u8be2\u5230\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\uff01", (Object)formSchemeKey);
            return null;
        }
        String entityID = Optional.ofNullable(fieldGrowthDataQueryDTO.getEntityID()).orElse(formSchemeDefine.getDw());
        this.setDsContextEntityID(entityID);
        IEntityDefine dwEntity = this.iEntityMetaService.queryEntity(entityID);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(dwEntity.getDimensionName(), (Object)fieldGrowthDataQueryDTO.getMdCode());
        String currentDataTime = fieldGrowthDataQueryDTO.getDatatime();
        try {
            IDataRow prevPeriodRow;
            IDataQuery iDataQuery = this.dataAccessProvider.newDataQuery();
            List fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines((Collection)queryFields);
            fieldDefines.forEach(arg_0 -> ((IDataQuery)iDataQuery).addColumn(arg_0));
            ReportFmlExecEnvironment iFmlExecEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            context.setEnv((IFmlExecEnvironment)iFmlExecEnvironment);
            IDataRow currentRow = this.queryDataRowByDataTime(currentDataTime, dimensionValueSet, iDataQuery, context);
            String pervYearDataTime = this.getOffsetPeriod(currentDataTime, GrowthRateTypeEnum.YOY);
            IDataRow prevYearRow = this.queryDataRowByDataTime(pervYearDataTime, dimensionValueSet, iDataQuery, context);
            if (currentDataTime.length() > 4 && PeriodConsts.codeToType((int)currentDataTime.charAt(4)) == 1) {
                prevPeriodRow = prevYearRow;
            } else {
                String pervPeriodDataTime = this.getOffsetPeriod(currentDataTime, GrowthRateTypeEnum.POP);
                prevPeriodRow = this.queryDataRowByDataTime(pervPeriodDataTime, dimensionValueSet, iDataQuery, context);
            }
            for (FieldDefine fieldDefine : fieldDefines) {
                AbstractData prevPeriodData;
                String fieldKey = fieldDefine.getKey();
                FieldType fieldType = fieldDefine.getType();
                FieldGrowthDataDTO fieldGrowthData = new FieldGrowthDataDTO();
                fieldGrowthData.setFieldKey(fieldKey);
                AbstractData currentData = currentRow == null ? null : currentRow.getValue(fieldDefine);
                AbstractData prevYearData = prevYearRow == null ? null : prevYearRow.getValue(fieldDefine);
                AbstractData abstractData = prevPeriodData = prevPeriodRow == null ? null : prevPeriodRow.getValue(fieldDefine);
                if (currentData == null) {
                    fieldGrowthData.setYoyGrowthRate("-%");
                    fieldGrowthData.setPopGrowthRate("-%");
                } else {
                    fieldGrowthData.setYoyGrowthRate(prevYearData == null ? "-%" : this.calculateGrowthRate(currentData, prevYearData, fieldType));
                    fieldGrowthData.setPopGrowthRate(prevPeriodData == null ? "-%" : this.calculateGrowthRate(currentData, prevPeriodData, fieldType));
                }
                fieldGrowthData.setCurrentValue(currentData == null ? null : currentData.getAsString());
                fieldGrowthData.setPrevYearValue(prevYearData == null ? null : prevYearData.getAsString());
                fieldGrowthData.setPrevPeriodValue(prevPeriodData == null ? null : prevPeriodData.getAsString());
                returnMap.put(fieldKey, fieldGrowthData);
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u62a5\u8868\u540c\u6bd4\u73af\u6bd4\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FormDataApiException("\u67e5\u8be2\u62a5\u8868\u540c\u6bd4\u73af\u6bd4\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
        return returnMap;
    }

    private List<FormSchemaInfo> queryFormSchemasByTask(TaskDefine taskDefine) {
        ArrayList<FormSchemaInfo> returnFormSchemas = new ArrayList<FormSchemaInfo>();
        String taskKey = taskDefine.getKey();
        if (StringUtils.isEmpty((String)taskKey)) {
            return returnFormSchemas;
        }
        List formSchemeDefines = this.runTimeFormSchemeDefineDao.listByTask(taskKey);
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            if (formSchemeDefine == null) continue;
            String formSchemeKey = formSchemeDefine.getKey();
            FormSchemaInfoDTO formSchemaInfo = new FormSchemaInfoDTO(formSchemeKey, formSchemeDefine.getTitle());
            List<SchemaPeriodDTO> schemaPeriods = this.querySchemaPeriods(formSchemeKey, taskDefine.getDateTime());
            if (schemaPeriods.isEmpty()) continue;
            formSchemaInfo.getSchemaPeriods().addAll(schemaPeriods);
            List<FormInfoDTO> forms = this.queryFormsByFormSchemaKey(formSchemeKey);
            formSchemaInfo.getForms().addAll(forms);
            returnFormSchemas.add(formSchemaInfo);
        }
        return returnFormSchemas;
    }

    private FormSchemaInfo queryFormSchemaByDefines(TaskDefine taskDefine, FormSchemeDefine formSchemeDefine) {
        String formSchemeKey = formSchemeDefine.getKey();
        FormSchemaInfoDTO formSchemaInfo = new FormSchemaInfoDTO(formSchemeKey, formSchemeDefine.getTitle());
        List<SchemaPeriodDTO> schemaPeriods = this.querySchemaPeriods(formSchemeKey, taskDefine.getDateTime());
        if (schemaPeriods.isEmpty()) {
            return null;
        }
        formSchemaInfo.getSchemaPeriods().addAll(schemaPeriods);
        List<FormInfoDTO> forms = this.queryFormsByFormSchemaKey(formSchemeKey);
        formSchemaInfo.getForms().addAll(forms);
        return formSchemaInfo;
    }

    private List<SchemaPeriodDTO> querySchemaPeriods(String formSchemaKey, String dataTime) {
        ArrayList<SchemaPeriodDTO> schemaPeriods = new ArrayList<SchemaPeriodDTO>();
        if (StringUtils.isEmpty((String)formSchemaKey)) {
            return schemaPeriods;
        }
        List allSchemaPeriods = this.runTimeSchemePeriodLinkDao.queryByScheme(formSchemaKey);
        if (allSchemaPeriods.isEmpty()) {
            return schemaPeriods;
        }
        List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dataTime).getPeriodItems();
        boolean isStartPeriodSet = false;
        SchemaPeriodDTO schemaPeriodDTO = new SchemaPeriodDTO();
        Map<String, String> periodKeyMap = allSchemaPeriods.stream().filter(define -> StringUtils.isNotEmpty((String)define.getPeriodKey())).collect(Collectors.toMap(SchemePeriodLinkDefine::getPeriodKey, SchemePeriodLinkDefine::getPeriodKey, (existing, replacement) -> replacement));
        for (int i = 0; i < periodItems.size(); ++i) {
            IPeriodRow row = (IPeriodRow)periodItems.get(i);
            if (row.getCode().equals(periodKeyMap.get(row.getCode()))) {
                if (!isStartPeriodSet) {
                    if (i == periodItems.size() - 1) {
                        schemaPeriodDTO.setStartPeriod(row.getCode());
                        schemaPeriodDTO.setEndPeriod(row.getCode());
                        schemaPeriods.add(schemaPeriodDTO);
                        continue;
                    }
                    isStartPeriodSet = true;
                    schemaPeriodDTO.setStartPeriod(row.getCode());
                    continue;
                }
                if (i != periodItems.size() - 1) continue;
                schemaPeriodDTO.setEndPeriod(row.getCode());
                schemaPeriods.add(schemaPeriodDTO);
                continue;
            }
            isStartPeriodSet = false;
            if (!StringUtils.isNotEmpty((String)schemaPeriodDTO.getStartPeriod()) || i == 0) continue;
            schemaPeriodDTO.setEndPeriod(((IPeriodRow)periodItems.get(i - 1)).getCode());
            schemaPeriods.add(schemaPeriodDTO);
            schemaPeriodDTO = new SchemaPeriodDTO();
        }
        return schemaPeriods;
    }

    private List<FormInfoDTO> queryFormsByFormSchemaKey(String formSchemaKey) {
        ArrayList<FormInfoDTO> returnForms = new ArrayList<FormInfoDTO>();
        if (StringUtils.isEmpty((String)formSchemaKey)) {
            return returnForms;
        }
        List formDefines = this.runTimeFormDefineDao.queryDefinesByFormScheme(formSchemaKey);
        for (FormDefine formDefine : formDefines) {
            if (formDefine == null) continue;
            String formKey = formDefine.getKey();
            FormInfoDTO formInfo = new FormInfoDTO(formKey, formDefine.getFormCode(), formDefine.getTitle());
            List formGroupDefines = null;
            try {
                formGroupDefines = this.runTimeFormGroupDefineDao.getAllGroupsFromForm(formKey);
            }
            catch (Exception e) {
                logger.error("\u67e5\u8be2\u62a5\u8868\u6240\u5728\u62a5\u8868\u5206\u7ec4\u5217\u8868\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
            if (formGroupDefines != null && !formGroupDefines.isEmpty()) {
                Optional.ofNullable(formGroupDefines.get(0)).ifPresent(formGroupDefineDTO -> {
                    formInfo.setFormGroupCode(formGroupDefineDTO.getCode());
                    formInfo.setFormGroupTitle(formGroupDefineDTO.getTitle());
                });
            }
            returnForms.add(formInfo);
        }
        return returnForms;
    }

    private boolean setOtherParamsByTaskDefine(TaskInfoDTO taskInfo, TaskDefine taskDefine) {
        String dataSchemeKey = taskDefine.getDataScheme();
        DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme == null) {
            return false;
        }
        taskInfo.setDataSchemeKey(taskDefine.getDataScheme());
        taskInfo.setDataSchemeTitle(dataScheme.getTitle());
        taskInfo.setDataTime(taskDefine.getDateTime());
        List taskOrgLinkDefines = this.taskOrgLinkDao.getByTask(taskDefine.getKey());
        taskOrgLinkDefines.forEach(e -> taskInfo.getEntityIds().add(e.getEntity()));
        return true;
    }

    private void writeFileToZip(String formSchemeKey, File file, ZipOutputStream zipOutputStream) {
        try {
            ZipEntry zipEntry = new ZipEntry(formSchemeKey + "/" + file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);){
                int bytesRead;
                byte[] buffer = new byte[8192];
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, bytesRead);
                }
            }
            zipOutputStream.flush();
            zipOutputStream.closeEntry();
        }
        catch (IOException e) {
            logger.error("\u538b\u7f29\u5bfc\u51fa\u6570\u636e\u6587\u4ef6\u65f6\u53d1\u751f\u9519\u8bef\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FormDataApiException("\u538b\u7f29\u5bfc\u51fa\u6570\u636e\u6587\u4ef6\u65f6\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
    }

    private IDataRow queryDataRowByDataTime(String dataTime, DimensionValueSet dimensionValueSet, IDataQuery iDataQuery, ExecutorContext context) throws Exception {
        dimensionValueSet.setValue("DATATIME", (Object)dataTime);
        iDataQuery.setMasterKeys(dimensionValueSet);
        IDataTable dataTable = iDataQuery.executeQuery(context);
        if (dataTable != null && dataTable.getTotalCount() > 0) {
            return dataTable.getItem(0);
        }
        return null;
    }

    private String getOffsetPeriod(String sourcePeriod, GrowthRateTypeEnum growthRateType) {
        PeriodWrapper sourceWrapper = PeriodUtil.getPeriodWrapper((String)sourcePeriod);
        GregorianCalendar sourceCalendar = PeriodUtil.period2Calendar((String)sourcePeriod);
        int periodType = sourceWrapper.getType();
        int periodOffset = -1;
        if (growthRateType.equals((Object)GrowthRateTypeEnum.YOY)) {
            periodOffset = -PeriodUtil.getPeriodSize((int)sourceWrapper.getYear(), (int)periodType);
        }
        PeriodWrapper targetWrapper = PeriodUtil.currentPeriod((GregorianCalendar)sourceCalendar, (int)periodType, (int)periodOffset);
        return targetWrapper.toString();
    }

    private String calculateGrowthRate(AbstractData current, AbstractData previous, FieldType fieldType) {
        try {
            BigDecimal growthRate;
            switch (fieldType) {
                case FIELD_TYPE_FLOAT: {
                    double currDoubleValue = current.getAsFloat();
                    double prevDoubleValue = previous.getAsFloat();
                    growthRate = GrowthRateUtils.calculateGrowthRate(currDoubleValue, prevDoubleValue);
                    break;
                }
                case FIELD_TYPE_INTEGER: {
                    int currIntValue = current.getAsInt();
                    int prevIntValue = previous.getAsInt();
                    growthRate = GrowthRateUtils.calculateGrowthRate(currIntValue, prevIntValue);
                    break;
                }
                case FIELD_TYPE_DECIMAL: {
                    BigDecimal currBigDecimalValue = current.getAsCurrency();
                    BigDecimal prevBigDecimalValue = previous.getAsCurrency();
                    growthRate = GrowthRateUtils.calculateGrowthRate(currBigDecimalValue, prevBigDecimalValue);
                    break;
                }
                default: {
                    return "-%";
                }
            }
            return growthRate + "%";
        }
        catch (IllegalArgumentException e) {
            return "-%";
        }
    }

    private void setDsContextEntityID(String entityID) {
        if (StringUtils.isNotEmpty((String)entityID)) {
            DsContext context = DsContextHolder.getDsContext();
            DsContextImpl dsContext = (DsContextImpl)context;
            dsContext.setEntityId(entityID);
        }
    }

    private /* synthetic */ void lambda$exportTaskData$2(FormDefine formDefine, ZipOutputStream zipOutputStream, File[] files) {
        Arrays.stream(files).filter(File::isFile).filter(file -> file.getName().endsWith(".csv")).forEach(csvFile -> this.writeFileToZip(formDefine.getFormScheme(), (File)csvFile, zipOutputStream));
    }
}

