/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.common.DimensionFilterType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserverable
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserverable;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.MetaComparator;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.datalinkmapping.vo.DataLinkMappingVO;
import com.jiuqi.nr.definition.exception.DefinitonException;
import com.jiuqi.nr.definition.exception.DesignCheckException;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.facade.DesignEnumLinkageSettingDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.internal.controller.FormulaDesignTimeController;
import com.jiuqi.nr.definition.internal.controller.event.FormSchemeUpdateEvent;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDimensionFilterImpl;
import com.jiuqi.nr.definition.internal.impl.DesignEntityLinkageDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.provider.ConditionalStyleProvider;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.internal.service.DesignCaliberGroupLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDataRegionDefineService;
import com.jiuqi.nr.definition.internal.service.DesignDimensionFilterService;
import com.jiuqi.nr.definition.internal.service.DesignEntityLinkageDefineService;
import com.jiuqi.nr.definition.internal.service.DesignEnumLinkageSettingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.internal.service.DesignFormGroupDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaVariableDefineService;
import com.jiuqi.nr.definition.internal.service.DesignRegionSettingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignReportTemplateService;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTransformReportService;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.nr.definition.reportTag.service.IDesignReportTagService;
import com.jiuqi.nr.definition.util.GridDataTransform;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.SerializeListImpl;
import com.jiuqi.nr.definition.util.SerializeUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DesignTimeViewController
implements IDesignTimeViewController {
    private static final Logger logger = LoggerFactory.getLogger(DesignTimeViewController.class);
    @Autowired
    private DesignTaskDefineService taskService;
    @Autowired
    private DesignTaskGroupDefineService taskGroupService;
    @Autowired
    private DesignFormSchemeDefineService formSchemeService;
    @Autowired
    private DesignFormDefineService formService;
    @Autowired
    private DesignFormGroupDefineService formGroupService;
    @Autowired
    private DesignDataRegionDefineService dataRegionService;
    @Autowired
    private DesignDataLinkDefineService dataLinkService;
    @Autowired
    private DesignRegionSettingDefineService regionSettingService;
    @Autowired
    private DesignEnumLinkageSettingDefineService enumLinkageService;
    @Autowired
    private DesignTaskLinkDefineService taskLinkService;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    private IFormulaDesignTimeController iFormulaDesignTimeController;
    @Autowired
    private IPrintDesignTimeController iPrintDesignTimeController;
    @Autowired
    private DesignEntityLinkageDefineService designEntityLinkageDefineService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DesignBigDataService bigDataService;
    @Autowired
    private NpDefinitionObserverable observerable;
    @Autowired
    private FormulaDesignTimeController formulaController;
    @Autowired
    private DesignCaliberGroupLinkDefineService caliberGroupLinkDefineService;
    @Autowired
    private DesignFormulaVariableDefineService designFormulaVariableDefineService;
    @Autowired
    private DesignDataLinkMappingDefineService designDataLinkMappingDefineService;
    @Autowired
    private DesignReportTemplateService designReportTemplateService;
    @Autowired
    private IDesignReportTagService designReportTagService;
    @Autowired
    private DesignDataLinkMappingDefineService designDataLinkMappingService;
    @Autowired
    private QuickReportModelService quickReportModelService;
    @Autowired
    private DesignTransformReportService designTransformReportService;
    @Autowired
    private IDesignParamCheckService designParamCheckService;
    @Autowired
    private DesignDimensionFilterService designDimensionFilterService;
    @Autowired
    private DesignFormFoldingService formFoldingService;
    @Autowired
    private ConditionalStyleProvider conditionalStyleProvider;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;
    public static final String BIG_FLOWDATA = "FLOWSETTING";
    public static final String BIG_REGION_TAB = "REGION_TAB";
    public static final String BIG_REGION_ORDER = "REGION_ORDER";
    public static final String BIG_REGION_LAST_ROW_STYLES = "REGION_LT_ROW_STYLES";
    public static final String BIG_REGION_LAST_COLUMN_STYLES = "REGION_LT_COLUMN_STYLES";
    public static final String BIG_REGION_CARD_RECORD = "BIG_REGION_CARD";
    public static final String BIG_ANALYSIS_SCHEME_PARAM = "ANALYSIS_PARAM";
    public static final String BIG_ANALYSIS_FORM_PARAM = "ANALYSIS_FORM_PARAM";
    public static final String BIG_ANALYSIS_FORM_GROUP_PARAM = "ANALYSIS_GROUP_PARAM";

    @Override
    public DesignTaskDefine createTaskDefine() {
        DesignTaskDefineImpl define = new DesignTaskDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertTaskDefine(DesignTaskDefine taskDefine) throws JQException {
        try {
            this.designParamCheckService.checkTaskTitle(taskDefine);
            String id = this.taskService.insertTaskDefine(taskDefine);
            this.saveTaskFlowByTaskDefine(taskDefine);
            return id;
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_041, e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void updateTaskDefine(DesignTaskDefine taskDefine) throws JQException {
        try {
            this.designParamCheckService.checkTaskTitle(taskDefine);
            DesignTaskDefine oldTaskDefine = this.queryTaskDefine(taskDefine.getKey());
            TaskFlowsDefine oldTaskFlowsDefine = oldTaskDefine.getFlowsSetting();
            this.taskService.updateTaskDefine(taskDefine);
            TaskFlowsDefine designTaskFlowsDefine = taskDefine.getFlowsSetting();
            byte[] data = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(taskDefine.getFlowsSetting());
            this.bigDataService.updateBigDataDefine(taskDefine.getKey(), BIG_FLOWDATA, data);
            this.applicationContext.publishEvent(new FormSchemeUpdateEvent(taskDefine, oldTaskDefine, designTaskFlowsDefine, oldTaskFlowsDefine));
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_042, e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void deleteTaskDefine(String taskKey) throws JQException {
        this.deleteTaskDefine(taskKey, true);
    }

    @Override
    public void deleteTaskDefine(String taskKey, boolean delLinkedParam) throws JQException {
        try {
            DesignTaskDefine taskDefine = this.queryTaskDefine(taskKey);
            this.taskService.delete(taskKey);
            if (this.observerable != null) {
                this.observerable.notify(MessageType.NRDROPTASK, new Object[]{taskDefine});
            }
            this.bigDataService.deleteBigDataDefine(taskKey, BIG_FLOWDATA);
            if (delLinkedParam) {
                List<DesignFormSchemeDefine> schemes = this.formSchemeService.queryFormSchemeDefineByTaskKey(taskKey);
                for (DesignFormSchemeDefine scheme : schemes) {
                    this.deleteFormSchemeDefine(scheme.getKey(), delLinkedParam);
                }
            }
            this.formulaController.deleteFormulaConditionByTask(taskKey);
            this.taskOrgLinkService.deleteTaskOrgLinkByTask(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_043, (Throwable)e);
        }
    }

    @Override
    public DesignTaskDefine queryTaskDefine(String taskKey) {
        try {
            DesignTaskDefine define = this.taskService.queryTaskDefine(taskKey);
            if (null == define) {
                return null;
            }
            define.setFlowsSetting(this.queryFlowDefine(define.getKey()));
            return define;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DesignTaskFlowsDefine queryFlowDefine(String key) throws Exception {
        byte[] data = this.bigDataService.getBigData(key, BIG_FLOWDATA);
        return DesignTaskFlowsDefine.bytesToTaskFlowsData(data);
    }

    @Override
    public DesignAnalysisSchemeParamDefine queryAnalysisSchemeParamDefine(String formSchemeKey) throws Exception {
        byte[] data = this.bigDataService.getBigData(formSchemeKey, BIG_ANALYSIS_SCHEME_PARAM);
        if (null != data && data.length > 0) {
            return SerializeUtils.jsonDeserialize(AnalysisSchemeParamDefineImpl.getDefaultModule(), data, AnalysisSchemeParamDefineImpl.class);
        }
        return null;
    }

    @Override
    public void deleteAnalysisSchemeParamDefine(String formSchemeKey) throws Exception {
        this.bigDataService.deleteBigDataDefine(formSchemeKey, BIG_ANALYSIS_SCHEME_PARAM);
    }

    @Override
    public boolean enableAnalysisScheme(String formSchemeKey) throws Exception {
        return null != this.bigDataService.getBigData(formSchemeKey, BIG_ANALYSIS_SCHEME_PARAM);
    }

    @Override
    public List<DesignTaskDefine> checkTaskNameAvailable(String taskKey, String taskName) throws Exception {
        return this.taskService.checkTaskNameAvailable(taskKey, taskName);
    }

    @Override
    public void updataAnalysisSchemeParamDefine(String formSchemeKey, DesignAnalysisSchemeParamDefine anaParam) throws Exception {
        byte[] data = SerializeUtils.jsonSerializeToByte(anaParam);
        this.bigDataService.updateBigDataDefine(formSchemeKey, BIG_ANALYSIS_SCHEME_PARAM, data);
    }

    @Override
    public DesignAnalysisFormParamDefine queryAnalysisFormParamDefine(String formKey) throws Exception {
        byte[] data = this.bigDataService.getBigData(formKey, BIG_ANALYSIS_FORM_PARAM);
        if (null != data && data.length > 0) {
            return SerializeUtils.jsonDeserialize(AnalysisFormParamDefineImpl.getDefaultModule(), data, AnalysisFormParamDefineImpl.class);
        }
        return null;
    }

    @Override
    public void deleteAnalysisFormParamDefine(String formKey) throws Exception {
        this.bigDataService.deleteBigDataDefine(formKey, BIG_ANALYSIS_FORM_PARAM);
    }

    @Override
    public void updataAnalysisFormParamDefine(String formKey, DesignAnalysisFormParamDefine anaParam) throws Exception {
        DesignFormDefine form = this.formService.getFormWithoutBinaryData(formKey);
        if (form.isAnalysisForm()) {
            form.setAnalysisForm(true);
            this.formService.updateFormWithoutBinaryData(form);
        }
        byte[] data = SerializeUtils.jsonSerializeToByte(anaParam);
        this.bigDataService.updateBigDataDefine(formKey, BIG_ANALYSIS_FORM_PARAM, data);
    }

    @Override
    public DesignAnalysisFormGroupDefine queryAnalysisFormGroupDefine(String formGroupKey) throws Exception {
        byte[] data = this.bigDataService.getBigData(formGroupKey, BIG_ANALYSIS_FORM_GROUP_PARAM);
        if (null != data && data.length > 0) {
            return SerializeUtils.jsonDeserialize(data, AnalysisFormGroupDefineImpl.class);
        }
        return null;
    }

    @Override
    public void deleteAnalysisFormGroupDefine(String formGroupKey) throws Exception {
        this.bigDataService.deleteBigDataDefine(formGroupKey, BIG_ANALYSIS_FORM_GROUP_PARAM);
    }

    @Override
    public void updataAnalysisFormGroupDefine(String formGroupKey, DesignAnalysisFormGroupDefine groupParam) throws Exception {
        byte[] data = SerializeUtils.jsonSerializeToByte(groupParam);
        this.bigDataService.updateBigDataDefine(formGroupKey, BIG_ANALYSIS_FORM_GROUP_PARAM, data);
    }

    @Override
    public DesignTaskDefine queryTaskDefineByCode(String taskCode) {
        DesignTaskDefine define = null;
        try {
            define = this.taskService.queryTaskDefineByCode(taskCode);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public DesignTaskDefine queryTaskDefineByFilePrefix(String filePrefix) {
        DesignTaskDefine define = null;
        try {
            define = this.taskService.queryTaskDefineByfilePrefix(filePrefix);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignTaskDefine> getAllTaskDefines() {
        List<DesignTaskDefine> defines = null;
        try {
            defines = this.taskService.queryAllTaskDefine();
            defines = defines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignTaskDefine> getAllReportTaskDefines() {
        List<DesignTaskDefine> taskDefines = this.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        return taskDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<DesignTaskDefine> getAllTaskDefinesByType(TaskType type) {
        try {
            return this.taskService.queryAllTaskDefinesByType(type);
        }
        catch (Exception e) {
            Log.error((Exception)e);
            return Collections.emptyList();
        }
    }

    @Override
    public int countTask() {
        return this.taskService.count();
    }

    @Override
    public DesignFormSchemeDefine createFormSchemeDefine() {
        DesignFormSchemeDefineImpl define = new DesignFormSchemeDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertFormSchemeDefine(DesignFormSchemeDefine formSchemeDefine) {
        String id = null;
        try {
            this.designParamCheckService.checkFormScheme(formSchemeDefine);
            id = this.formSchemeService.insertFormSchemeDefine(formSchemeDefine);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return id;
    }

    @Override
    public void updateFormSchemeDefine(DesignFormSchemeDefine formSchemeDefine) {
        try {
            this.designParamCheckService.checkFormScheme(formSchemeDefine);
            DesignFormSchemeDefine oldFormScheme = this.queryFormSchemeDefine(formSchemeDefine.getKey());
            this.formSchemeService.updateFormSchemeDefine(formSchemeDefine);
            byte[] flowsData = this.formService.getBigData(formSchemeDefine.getTaskKey(), BIG_FLOWDATA);
            DesignTaskFlowsDefine designTaskFlowsDefine = DesignTaskFlowsDefine.bytesToTaskFlowsData(flowsData);
            this.applicationContext.publishEvent(new FormSchemeUpdateEvent(formSchemeDefine, oldFormScheme, designTaskFlowsDefine, designTaskFlowsDefine));
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteFormSchemeDefine(String formSchemeKey) throws JQException {
        this.deleteFormSchemeDefine(formSchemeKey, true);
    }

    @Override
    public void deleteFormSchemeDefine(String formSchemeKey, boolean delLinkedParam) throws JQException {
        if (formSchemeKey == null) {
            return;
        }
        try {
            DesignFormSchemeDefine schemeDefine = this.queryFormSchemeDefine(formSchemeKey);
            this.formSchemeService.delete(formSchemeKey);
            this.formSchemeService.deleteByScheme(formSchemeKey);
            if (this.observerable != null) {
                this.observerable.notify(MessageType.NRDROPSCHEME, new Object[]{schemeDefine});
            }
            if (delLinkedParam) {
                List<DesignFormGroupDefine> groups = this.formGroupService.queryFormGroupDefinesByScheme(formSchemeKey);
                for (DesignFormGroupDefine group : groups) {
                    List<DesignFormDefine> forms = this.formService.queryFormDefineByGroupId(group.getKey(), false);
                    for (DesignFormDefine form : forms) {
                        if (form.getFormType() == FormType.FORM_TYPE_ENTITY) continue;
                        this.removeFormFromGroup(form.getKey(), group.getKey());
                        List<DesignFormGroupLink> designFormGroupLinks = this.getFormGroupLinksByFormId(form.getKey());
                        if (designFormGroupLinks != null && designFormGroupLinks.size() != 0) continue;
                        this.deleteFormDefine(form.getKey(), delLinkedParam);
                    }
                    this.formGroupService.delete(group.getKey());
                }
                this.deleteFormulaAndScheme(formSchemeKey);
                this.deletePrintTemplateAndScheme(formSchemeKey);
                this.deleteFormulaVariableByFormSchemeKey(formSchemeKey);
            }
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_028, (Throwable)e);
        }
    }

    @Override
    public List<DesignFormSchemeDefine> queryFormSchemeByTask(String taskKey) throws JQException {
        try {
            return this.formSchemeService.queryFormSchemeDefineByTaskKey(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_001, (Throwable)e);
        }
    }

    @Override
    public DesignFormSchemeDefine queryFormSchemeDefine(String formSchemeKey) {
        DesignFormSchemeDefine designFormSchemeDefine = null;
        try {
            designFormSchemeDefine = this.formSchemeService.queryFormSchemeDefine(formSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return designFormSchemeDefine;
    }

    @Override
    public DesignFormSchemeDefine queryFormSchemeDefineByFilePrefix(String filePrefix) {
        DesignFormSchemeDefine designFormSchemeDefine = null;
        try {
            designFormSchemeDefine = this.formSchemeService.queryFormSchemeDefineByfilePrefix(filePrefix);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return designFormSchemeDefine;
    }

    @Override
    public List<DesignFormSchemeDefine> queryAllFormSchemeDefine() {
        try {
            return this.formSchemeService.queryAllFormSchemeDefine();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DesignFormSchemeDefine> queryFormSchemeDefines(String[] keys) throws Exception {
        return this.formSchemeService.queryFormSchemeDefines(keys);
    }

    @Override
    public DesignFormSchemeDefine queryFormSchemeDefineByTaskPrefix(String taskPrefix) {
        DesignFormSchemeDefine designFormSchemeDefine = null;
        try {
            designFormSchemeDefine = this.formSchemeService.queryFormSchemeDefineByTaskPrefix(taskPrefix);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return designFormSchemeDefine;
    }

    @Override
    public DesignFormDefine createFormDefine() {
        DesignFormDefineImpl define = new DesignFormDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertFormDefine(DesignFormDefine formDefine) throws JQException {
        String id;
        try {
            this.designParamCheckService.checkFormTitleAndCode(formDefine);
            id = this.formService.insertFormDefine(formDefine);
        }
        catch (JQException | DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_027, e.getMessage(), (Throwable)e);
        }
        return id;
    }

    @Override
    public String insertFormDefine(DesignFormDefine formDefine, int type) throws JQException {
        String id;
        try {
            this.designParamCheckService.checkFormTitleAndCode(formDefine);
            id = this.formService.insertFormDefine(formDefine, type);
        }
        catch (JQException | DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_027, e.getMessage(), (Throwable)e);
        }
        return id;
    }

    @Override
    public int addNewFormDefine(DesignFormDefine formDefine, String ownerFormGroupKey) throws JQException {
        this.insertFormDefine(formDefine);
        this.addFormToGroup(formDefine.getKey(), ownerFormGroupKey);
        return 1;
    }

    @Override
    public void updateFormDefine(DesignFormDefine formDefine) {
        try {
            this.designParamCheckService.checkFormTitleAndCode(formDefine);
            this.formService.updateFormDefine(formDefine);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void updateFormDefine(DesignFormDefine formDefine, int type) {
        try {
            this.designParamCheckService.checkFormTitleAndCode(formDefine);
            this.formService.updateFormDefine(formDefine, type);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteFormDefine(String formKey) throws Exception {
        this.deleteFormDefine(formKey, true);
    }

    @Override
    public void deleteFormDefine(String formKey, boolean delLinkedParam) throws Exception {
        this.formService.delete(formKey);
        this.designDataLinkMappingService.deleteDataLinkMappingByFormKey(formKey);
        this.conditionalStyleProvider.deleteCSInForm(formKey);
        this.formFoldingService.deleteByFormKey(formKey);
        if (delLinkedParam) {
            List<DesignDataRegionDefine> dataRegions = this.getAllRegionsInForm(formKey);
            for (DesignDataRegionDefine region : dataRegions) {
                this.deleteDataRegionDefine(region.getKey(), delLinkedParam);
            }
            this.formulaController.deleteFormulaDefinesByForm(formKey);
            this.iPrintDesignTimeController.deletePrintTemplateDefineByForm(formKey);
        }
    }

    @Override
    public void deleteFormDefine(String formKey, boolean deleteRegion, boolean deleteFormula, boolean deletePrintTemp) throws Exception {
        this.formService.delete(formKey);
        this.designDataLinkMappingService.deleteDataLinkMappingByFormKey(formKey);
        this.conditionalStyleProvider.deleteCSInForm(formKey);
        this.formFoldingService.deleteByFormKey(formKey);
        if (deleteRegion) {
            List<DesignDataRegionDefine> dataRegions = this.getAllRegionsInForm(formKey);
            for (DesignDataRegionDefine region : dataRegions) {
                this.deleteDataRegionDefine(region.getKey(), true);
            }
        }
        if (deleteFormula) {
            this.formulaController.deleteFormulaDefinesByForm(formKey);
        }
        if (deletePrintTemp) {
            this.iPrintDesignTimeController.deletePrintTemplateDefineByForm(formKey);
        }
    }

    @Override
    public void deleteAllFromDefines() {
        try {
            this.formService.deleteAllFromDefines();
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public DesignFormDefine queryFormById(String formKey) {
        try {
            DesignFormDefine formDefine = this.formService.queryFormDefineContainsFormData(formKey, 15);
            if (formDefine != null && formDefine.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS)) {
                QuickReport quickReportByGuidOrId = this.quickReportModelService.getQuickReportByGuidOrId((String)formDefine.getExtensionProp("analysisGuid"));
                if (null == quickReportByGuidOrId) {
                    formDefine.setBinaryData(null);
                    return formDefine;
                }
                List worksheets = quickReportByGuidOrId.getWorksheets();
                WorksheetModel worksheetModel = (WorksheetModel)worksheets.get(0);
                GridData griddata = worksheetModel.getGriddata();
                Grid2Data grid2Data = new Grid2Data();
                GridDataTransform.gridDataToGrid2Data(griddata, grid2Data);
                GridDataTransform.Grid2DataTextFilter(grid2Data);
                byte[] bytes = Grid2Data.gridToBytes((Grid2Data)grid2Data);
                formDefine.setBinaryData(bytes);
            }
            return formDefine;
        }
        catch (Exception e) {
            throw new DefinitonException("\u67e5\u8be2\u62a5\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public DesignFormDefine queryFormByCodeInFormScheme(String formSchemeKey, String formDefineCode) {
        DesignFormDefine define = null;
        if (StringUtils.isEmpty(formDefineCode)) {
            return define;
        }
        try {
            define = this.formService.queryFormDefineByScheme(formSchemeKey, formDefineCode);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public DesignFormDefine querySoftFormDefineByCodeInFormScheme(String formSchemeKey, String formDefineCode) {
        if (StringUtils.isEmpty(formSchemeKey) || StringUtils.isEmpty(formDefineCode)) {
            return null;
        }
        return this.formService.querySoftFormDefineBySchenme(formSchemeKey, formDefineCode);
    }

    @Override
    public List<DesignFormDefine> queryAllFormDefinesByTask(String taskKey) {
        ArrayList<DesignFormDefine> defines = new ArrayList<DesignFormDefine>();
        try {
            List<DesignFormSchemeDefine> schemes = this.formSchemeService.queryFormSchemeDefineByTaskKey(taskKey);
            if (schemes != null) {
                for (DesignFormSchemeDefine scheme : schemes) {
                    List<DesignFormDefine> queryFormByScheme = this.formService.queryFormByScheme(scheme.getKey(), true);
                    defines.addAll(queryFormByScheme);
                }
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormDefine> queryAllSoftFormDefinesByTask(String taskKey) {
        ArrayList<DesignFormDefine> defines = new ArrayList<DesignFormDefine>();
        try {
            List<DesignFormSchemeDefine> schemes = this.formSchemeService.queryFormSchemeDefineByTaskKey(taskKey);
            if (schemes != null) {
                for (DesignFormSchemeDefine scheme : schemes) {
                    List<DesignFormDefine> queryFormByScheme = this.queryAllSoftFormDefinesByFormScheme(scheme.getKey());
                    defines.addAll(queryFormByScheme);
                }
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormDefine> queryAllFormDefinesByFormScheme(String formSchemeKey) {
        List<DesignFormDefine> defines = new ArrayList<DesignFormDefine>();
        try {
            defines = this.formService.queryFormByScheme(formSchemeKey, true);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormDefine> queryAllSoftFormDefinesByFormScheme(String formSchemeKey) {
        return this.formService.queryAllSoftFormDefinesByFormScheme(formSchemeKey);
    }

    @Override
    public List<DesignFormDefine> queryAllFormDefines() {
        List<DesignFormDefine> defines = null;
        try {
            defines = this.formService.queryAllFormDefine();
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public void setReportDataToForm(String formKey, byte[] reportData) {
        try {
            this.formService.updateReportData(formKey, reportData);
        }
        catch (Exception e) {
            this.doThrowDefinitionException("\u4fdd\u5b58\u8868\u6837\u51fa\u9519", e);
        }
    }

    private void doThrowDefinitionException(String message, Exception e) {
        throw new DefinitonException(message, e);
    }

    @Override
    public void setReportDataToFormByLanguage(String formKey, byte[] reportData, int language) {
        try {
            this.formService.updateReportData(formKey, reportData, language);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public byte[] getReportDataFromForm(String formKey, int language) {
        try {
            return this.formService.getReportData(formKey, language);
        }
        catch (Exception e) {
            throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", e);
        }
    }

    @Override
    public List<DesignFormDefine> getAllFormDefinesWithoutBinaryData() {
        List<DesignFormDefine> defines = null;
        try {
            defines = this.formService.queryAllFormDefine(false);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormDefine> getAllFormDefinesInTaskWithoutBinaryData(String taskKey) {
        ArrayList<DesignFormDefine> defines = new ArrayList<DesignFormDefine>();
        try {
            List<DesignFormSchemeDefine> formSchemeByTask = this.queryFormSchemeByTask(taskKey);
            formSchemeByTask.forEach(formScheme -> defines.addAll(this.getAllFormDefinesInFormSchemeWithoutBinaryData(formScheme.getKey())));
        }
        catch (JQException e) {
            Log.error((Exception)((Object)e));
        }
        return defines;
    }

    @Override
    public List<DesignFormDefine> getAllFormDefinesInFormSchemeWithoutBinaryData(String formSchemeKey) {
        List<DesignFormDefine> defines = null;
        try {
            defines = this.formService.queryFormByScheme(formSchemeKey, false);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignFormDefine> getAllFormsInGroupWithoutBinaryData(String formGroupKey) {
        return this.queryAllSoftFormDefinesInGroup(formGroupKey);
    }

    @Override
    public List<DesignFormDefine> queryAllSoftFormDefinesInGroup(String formGroupKey) {
        try {
            return this.formService.querySoftFormDefineByGroupId(formGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
            return Collections.emptyList();
        }
    }

    @Override
    public DesignFormDefine queryFormDefineByCodeInFormSchemeWithoutBinaryData(String formSchemeKey, String formDefineCode) throws JQException {
        return this.formService.queryFormDefineByCodeInScheme(formSchemeKey, formDefineCode, false);
    }

    @Override
    public List<DesignFormDefine> queryFormDefineByCodeWithoutBinaryData(String formDefineCode) {
        List<DesignFormDefine> defines = null;
        try {
            defines = this.formService.queryFormDefinesListByCode(formDefineCode, false);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    @Deprecated
    public DesignFormDefine queryFormDefineByIdWithoutBinaryData(String formKey) {
        return this.querySoftFormDefine(formKey);
    }

    @Override
    public DesignFormGroupDefine createFormGroup() {
        DesignFormGroupDefineImpl define = new DesignFormGroupDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertFormGroup(DesignFormGroupDefine formGroup) {
        String id = null;
        try {
            this.designParamCheckService.checkFormGroup(formGroup);
            id = this.formGroupService.insertFormGroupDefine(formGroup);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return id;
    }

    @Override
    public int addNewFormGroupToScheme(DesignFormGroupDefine formGroup, String ownerformSchemeKey) {
        try {
            this.designParamCheckService.checkFormGroup(formGroup);
            formGroup.setFormSchemeKey(ownerformSchemeKey);
            this.formGroupService.insertFormGroupDefine(formGroup);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return 0;
    }

    @Override
    public void updateFormGroup(DesignFormGroupDefine formGroup) {
        try {
            this.designParamCheckService.checkFormGroup(formGroup);
            this.formGroupService.updateFormGroupDefine(formGroup);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteFormGroup(String formGroupKey) {
        this.deleteFormGroup(formGroupKey, true);
    }

    @Override
    public void deleteFormGroup(String formGroupKey, boolean delLinkedParam) {
        try {
            List<DesignFormDefine> forms = this.formService.queryFormDefineByGroupId(formGroupKey, false);
            for (DesignFormDefine form : forms) {
                if (form.getFormType() == FormType.FORM_TYPE_ENTITY) continue;
                this.removeFormFromGroup(form.getKey(), formGroupKey);
                List<DesignFormGroupLink> designFormGroupLinks = this.getFormGroupLinksByFormId(form.getKey());
                if (designFormGroupLinks != null && designFormGroupLinks.size() != 0) continue;
                this.deleteFormDefine(form.getKey(), delLinkedParam);
            }
            this.formGroupService.delete(formGroupKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DesignFormGroupDefine queryFormGroup(String formGroupKey) {
        DesignFormGroupDefine define = null;
        try {
            define = this.formGroupService.queryFormGroupDefine(formGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignFormGroupDefine> queryFormGroupByTitleInFormScheme(String formSchemeKey, String formGroupTitle) {
        List<DesignFormGroupDefine> list = null;
        try {
            list = this.formGroupService.queryFormGroupDefinesByScheme(formSchemeKey, formGroupTitle);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public List<DesignFormGroupDefine> queryRootGroupsByFormScheme(String formSchemeKey) {
        ArrayList<DesignFormGroupDefine> groupList = new ArrayList();
        try {
            groupList = this.formGroupService.queryFormGroupDefinesByScheme(formSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return groupList;
    }

    @Override
    public List<DesignFormGroupDefine> getFormGroupsByFormId(String formKey) {
        try {
            return this.formGroupService.getAllGroupsFromForm(formKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DesignFormGroupLink> getFormGroupLinksByFormId(String formKey) {
        List<DesignFormGroupLink> list = null;
        try {
            list = this.formService.getFormGroupLinksByFormId(formKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public List<DesignFormGroupLink> getFormGroupLinksByGroupId(String groupKey) {
        try {
            return this.formService.getFormGroupLinksByGroupId(groupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
            return null;
        }
    }

    @Override
    public DesignFormGroupLink getFormGroupLinksByFormIdAndGroupId(String formKey, String groupKey) throws Exception {
        return this.formService.queryDesignFormGroupLink(formKey, groupKey);
    }

    @Override
    public void updateDesignFormGroupLink(DesignFormGroupLink designFormGroupLink) throws Exception {
        try {
            this.formService.updateDesignFormGroupLink(designFormGroupLink);
        }
        catch (Exception e) {
            throw new Exception("\u66f4\u65b0 formKey\u4e3a\u3010" + designFormGroupLink.getFormKey() + "\u3011groupKey\u4e3a\u3010" + designFormGroupLink.getGroupKey() + "\u3011\u7684\u8868\u5355\u5206\u7ec4\u548c\u8868\u5355\u7684\u5173\u8054\u5173\u7cfb\u5217\u8868\u5931\u8d25\uff01");
        }
    }

    @Override
    public List<DesignFormGroupDefine> getChildFormGroups(String formGroupKey) {
        List<DesignFormGroupDefine> list = null;
        try {
            list = this.formGroupService.queryDefinesByParent(formGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public void addFormToGroup(String formKey, String formGroupKey) {
        try {
            this.formService.addFormToGroup(formKey, formGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void removeFormFromGroup(String formKey, String formGroupKey) {
        try {
            this.formService.removeFormFromGroup(formKey, formGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignFormDefine> getAllFormsInGroup(String formGroupKey, boolean isRecursion) {
        ArrayList<DesignFormDefine> list = new ArrayList<DesignFormDefine>();
        try {
            List<DesignFormGroupDefine> groupList;
            list.addAll(this.formService.queryFormDefineByGroupId(formGroupKey));
            if (isRecursion && (groupList = this.formGroupService.queryDefinesByParent(formGroupKey, isRecursion)) != null) {
                for (DesignFormGroupDefine group : groupList) {
                    list.addAll(this.formService.queryFormDefineByGroupId(group.getKey()));
                }
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        list.sort(new MetaComparator());
        return list;
    }

    @Override
    public List<DesignFormDefine> getAllFormsInGroupLazy(String formGroupKey, boolean isRecursion) {
        ArrayList<DesignFormDefine> list = new ArrayList<DesignFormDefine>();
        try {
            List<DesignFormGroupDefine> groupList;
            list.addAll(this.formService.queryFormDefineByGroupId(formGroupKey, false));
            if (isRecursion && (groupList = this.formGroupService.queryDefinesByParent(formGroupKey, isRecursion)) != null) {
                for (DesignFormGroupDefine group : groupList) {
                    list.addAll(this.formService.queryFormDefineByGroupId(group.getKey(), false));
                }
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        list.sort(new MetaComparator());
        return list;
    }

    @Override
    public List<DesignFormDefine> getAllFormsInGroupWithoutBinaryData(String formGroupKey, boolean isRecursion) {
        ArrayList<DesignFormDefine> list = new ArrayList<DesignFormDefine>();
        try {
            List<DesignFormGroupDefine> groupList;
            list.addAll(this.formService.queryFormDefineByGroupId(formGroupKey, false));
            if (isRecursion && (groupList = this.formGroupService.queryDefinesByParent(formGroupKey, isRecursion)) != null) {
                for (DesignFormGroupDefine group : groupList) {
                    list.addAll(this.formService.queryFormDefineByGroupId(group.getKey(), false));
                }
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public void exchangeFormGroup(String groupKey1, String groupKey2) {
        try {
            this.formGroupService.exchangeFormGroup(groupKey1, groupKey2);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignFormGroupDefine> getAllFormGroups() {
        List<DesignFormGroupDefine> list = null;
        try {
            list = this.formGroupService.queryAllFormGroupDefine();
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public int countAllForm() {
        return this.formService.count();
    }

    @Override
    public DesignDataRegionDefine createDataRegionDefine() {
        DesignDataRegionDefineImpl define = new DesignDataRegionDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertDataRegionDefine(DesignDataRegionDefine dataRegionDefine) {
        String key = null;
        try {
            key = this.dataRegionService.insertDataRegionDefine(dataRegionDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return key;
    }

    @Override
    public void updateDataRegionDefine(DesignDataRegionDefine dataRegionDefine) {
        try {
            this.dataRegionService.updateDataRegionDefine(dataRegionDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteDataRegionDefine(String dataRegionKey, boolean delLinkedParam) throws Exception {
        this.dataRegionService.delete(dataRegionKey);
        if (delLinkedParam) {
            this.dataLinkService.deleteByRegion(dataRegionKey);
        }
    }

    @Override
    public void deleteDataRegionDefine(String dataRegionKey) throws Exception {
        this.dataRegionService.delete(dataRegionKey);
        this.dataLinkService.deleteByRegion(dataRegionKey);
    }

    @Override
    public DesignDataRegionDefine queryDataRegionDefine(String dataRegionKey) {
        DesignDataRegionDefine define = null;
        try {
            define = this.dataRegionService.queryDataRegionDefine(dataRegionKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignDataRegionDefine> getAllRegionsInForm(String formKey) {
        List<DesignDataRegionDefine> defines = Collections.emptyList();
        try {
            defines = this.dataRegionService.getAllRegionsInForm(formKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<String> insertDataRegionDefines(DesignDataRegionDefine[] dataRegionDefines) {
        List<String> keys = null;
        try {
            keys = this.dataRegionService.insertDataRegionDefines(dataRegionDefines);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return keys;
    }

    @Override
    public void updateDataRegionDefines(DesignDataRegionDefine[] dataRegionDefines) {
        try {
            this.dataRegionService.updateDataRegionDefines(dataRegionDefines);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteDataRegionDefines(String[] dataRegionKeys, boolean delLinkedParam) {
        try {
            this.dataRegionService.delete(dataRegionKeys);
            if (delLinkedParam) {
                for (String dataRegionKey : dataRegionKeys) {
                    this.dataLinkService.deleteByRegion(dataRegionKey);
                }
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignDataRegionDefine> createDataRegionDefines(int count) {
        ArrayList<DesignDataRegionDefine> list = new ArrayList<DesignDataRegionDefine>();
        for (int i = 0; i < count; ++i) {
            DesignDataRegionDefineImpl define = new DesignDataRegionDefineImpl();
            define.setKey(UUIDUtils.getKey());
            list.add(define);
        }
        return list;
    }

    @Override
    public DesignDataLinkDefine createDataLinkDefine() {
        DesignDataLinkDefineImpl define = new DesignDataLinkDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertDataLinkDefine(DesignDataLinkDefine dataLinkDefine) {
        String key = null;
        try {
            key = this.dataLinkService.insertDatLinkDefine(dataLinkDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return key;
    }

    @Override
    public void updateDataLinkDefine(DesignDataLinkDefine dataLinkDefine) {
        try {
            this.dataLinkService.updateDataLinkDefine(dataLinkDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteDataLinkDefine(String dataLinkKey) {
        try {
            this.dataLinkService.deleteDataLinkDefine(dataLinkKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteDataLinkByRegionId(String dataRegionKey) throws Exception {
        this.dataLinkService.deleteByRegion(dataRegionKey);
    }

    @Override
    public DesignDataLinkDefine queryDataLinkDefine(String dataLinkKey) {
        DesignDataLinkDefine define = null;
        try {
            define = this.dataLinkService.queryDataLinkDefine(dataLinkKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignDataLinkDefine> getAllLinksInForm(String formKey) {
        ArrayList<DesignDataLinkDefine> dataLinkList = new ArrayList<DesignDataLinkDefine>();
        List<DesignDataRegionDefine> regionList = this.getAllRegionsInForm(formKey);
        if (null != regionList) {
            for (DesignDataRegionDefine designDataRegionDefine : regionList) {
                List<DesignDataLinkDefine> linklist = this.getAllLinksInRegion(designDataRegionDefine.getKey());
                if (null == linklist) continue;
                dataLinkList.addAll(linklist);
            }
        }
        return dataLinkList;
    }

    @Override
    public List<DesignDataLinkDefine> getAllLinksInRegion(String dataRegionKey) {
        try {
            return this.dataLinkService.getAllLinksInRegion(dataRegionKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<DesignFieldDefine> getAllFieldsByLinksInRegion(String dataRegionKey) throws JQException {
        try {
            return this.dataLinkService.getAllFieldsByLinksInRegion(dataRegionKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_051, (Throwable)e);
        }
    }

    @Override
    public List<DesignFieldDefine> getAllFieldsByLinksInForm(String formKey) throws JQException {
        ArrayList<DesignFieldDefine> rets = new ArrayList<DesignFieldDefine>();
        try {
            List<DesignDataRegionDefine> regions = this.getAllRegionsInForm(formKey);
            regions.forEach(region -> {
                try {
                    rets.addAll(this.getAllFieldsByLinksInRegion(region.getKey()));
                }
                catch (JQException e) {
                    this.doThrow((Exception)((Object)e));
                }
            });
            return rets;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_051, (Throwable)e);
        }
    }

    @Override
    public List<DesignTableDefine> getAllTableDefineInRegion(String dataRegionKey) {
        List<Object> defines = new ArrayList<DesignTableDefine>();
        try {
            List<String> tableIds = this.dataLinkService.getAllTableKeysInRegion(dataRegionKey);
            if (tableIds.size() > 0) {
                defines = this.npDesignController.queryTableDefines(tableIds.toArray(new String[1]));
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public DesignDataLinkDefine queryDataLinkDefine(String formKey, int posX, int posY) {
        DesignDataLinkDefine define = null;
        try {
            List<DesignDataLinkDefine> links = this.getAllLinksInForm(formKey);
            for (DesignDataLinkDefine link : links) {
                if (link.getPosX() != posX || link.getPosY() != posY) continue;
                define = link;
                break;
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignDataLinkDefine> getReferencedDataLinkByField(DesignFieldDefine field) {
        List<DesignDataLinkDefine> defines = new ArrayList<DesignDataLinkDefine>();
        try {
            defines = this.dataLinkService.getDefinesByFieldKey(field.getKey());
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<String> insertDataLinkDefines(DesignDataLinkDefine[] dataLinkDefines) {
        List<String> result = null;
        try {
            result = this.dataLinkService.insert(dataLinkDefines);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return result;
    }

    @Override
    public void updateDataLinkDefines(DesignDataLinkDefine[] dataLinkDefines) {
        try {
            this.dataLinkService.updateDataLinkDefines(dataLinkDefines);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteDataLinkDefines(String[] dataLinkKeys) {
        try {
            this.dataLinkService.delete(dataLinkKeys);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignDataLinkDefine> createDataLinkDefines(int count) {
        ArrayList<DesignDataLinkDefine> defines = new ArrayList<DesignDataLinkDefine>();
        for (int i = 0; i < count; ++i) {
            DesignDataLinkDefineImpl define = new DesignDataLinkDefineImpl();
            define.setKey(UUIDUtils.getKey());
            defines.add(define);
        }
        return defines;
    }

    @Override
    public void deleteDataLinkDefinesByFieldKey(String fieldKey) {
        try {
            this.dataLinkService.deleteByField(fieldKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteDataLinkDefinesByFieldKeys(String[] fieldKeys) {
        try {
            this.dataLinkService.deleteByFields(fieldKeys);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignDataLinkDefine> getAllLinks() {
        ArrayList<DesignDataLinkDefine> defines = new ArrayList();
        try {
            defines = this.dataLinkService.getAllLinks();
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public void removeRegionSetting(String dataRegionKey) {
        try {
            this.formService.deleteOrderBigData(dataRegionKey, BIG_REGION_TAB);
            this.formService.deleteOrderBigData(dataRegionKey, BIG_REGION_ORDER);
            this.formService.deleteOrderBigData(dataRegionKey, BIG_REGION_LAST_ROW_STYLES);
            this.formService.deleteOrderBigData(dataRegionKey, BIG_REGION_LAST_COLUMN_STYLES);
            this.regionSettingService.delete(dataRegionKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void removeEnumLinkage(String dataLinkKey) {
        try {
            this.enumLinkageService.delete(dataLinkKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void removeEnumLinkage(String[] dataLinkKeys) {
        try {
            this.enumLinkageService.delete(dataLinkKeys);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public DesignRegionSettingDefine createRegionSetting() {
        DesignRegionSettingDefineImpl define = new DesignRegionSettingDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String addRegionSetting(DesignRegionSettingDefine regionSettingDefine) {
        try {
            SerializeListImpl<Object> serializeUtil;
            this.regionSettingService.insertDefine(regionSettingDefine);
            List<RegionTabSettingDefine> regionTabSettingDataList = regionSettingDefine.getRegionTabSetting();
            if (regionTabSettingDataList != null && regionTabSettingDataList.size() > 0) {
                byte[] data = RegionTabSettingData.regionTabSettingDataToBytes(regionTabSettingDataList);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_TAB, 1, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_TAB);
            }
            List<RowNumberSetting> rowNumberSettings = regionSettingDefine.getRowNumberSetting();
            if (rowNumberSettings != null && rowNumberSettings.size() > 0) {
                serializeUtil = new SerializeListImpl<RowNumberSetting>(RowNumberSetting.class);
                byte[] data = serializeUtil.serialize(rowNumberSettings);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_ORDER, 1, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_ORDER);
            }
            serializeUtil = new SerializeListImpl<RegionEdgeStyleDefine>(RegionEdgeStyleDefine.class);
            List<RegionEdgeStyleDefine> lastRowStyles = regionSettingDefine.getLastRowStyles();
            if (null != lastRowStyles && !lastRowStyles.isEmpty()) {
                byte[] data = serializeUtil.serialize(lastRowStyles);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_LAST_ROW_STYLES, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_LAST_ROW_STYLES);
            }
            List<RegionEdgeStyleDefine> lastColumnStyles = regionSettingDefine.getLastColumnStyle();
            if (null != lastColumnStyles && !lastColumnStyles.isEmpty()) {
                byte[] data = serializeUtil.serialize(lastColumnStyles);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_LAST_COLUMN_STYLES, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_LAST_COLUMN_STYLES);
            }
            RecordCard cardRecord = regionSettingDefine.getCardRecord();
            if (null != cardRecord) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(cardRecord);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_CARD_RECORD, 1, byteArray);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_CARD_RECORD);
            }
            String regionSurvey = regionSettingDefine.getRegionSurvey();
            if (StringUtils.hasLength(regionSurvey)) {
                byte[] bytes = DesignFormDefineBigDataUtil.StringToBytes(regionSurvey);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), "BIG_REGION_SURVEY", 1, bytes);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), "BIG_REGION_SURVEY");
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return regionSettingDefine.getKey();
    }

    @Override
    public DesignRegionSettingDefine getRegionSetting(String regionSettingKey) {
        DesignRegionSettingDefine define = null;
        try {
            SerializeListImpl<Object> serializeUtil;
            byte[] bytes;
            byte[] bigData;
            define = this.regionSettingService.queryDefine(regionSettingKey);
            byte[] data = this.formService.getBigData(regionSettingKey, BIG_REGION_TAB);
            if (data != null) {
                ArrayList<RegionTabSettingDefine> regionTabSettingDataList = new ArrayList<RegionTabSettingDefine>(RegionTabSettingData.bytesToRegionTabSettingData(data));
                define.setRegionTabSetting(regionTabSettingDataList);
            }
            if ((bigData = this.formService.getBigData(regionSettingKey, BIG_REGION_CARD_RECORD)) != null) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bigData);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                RecordCard parseObject = (RecordCard)objectInputStream.readObject();
                define.setCardRecord(parseObject);
            }
            if ((bytes = this.formService.getBigData(regionSettingKey, BIG_REGION_ORDER)) != null) {
                serializeUtil = new SerializeListImpl<RowNumberSetting>(RowNumberSetting.class);
                List<RowNumberSetting> rowNumberSettings = serializeUtil.deserialize(bytes, RowNumberSetting.class);
                define.setRowNumberSetting(rowNumberSettings);
            }
            serializeUtil = new SerializeListImpl<RegionEdgeStyleDefine>(RegionEdgeStyleDefine.class);
            data = this.formService.getBigData(regionSettingKey, BIG_REGION_LAST_ROW_STYLES);
            if (null != data) {
                List<RegionEdgeStyleDefine> lastRowStyles = serializeUtil.deserialize(data, RegionEdgeStyleDefine.class);
                define.setLastRowStyle(lastRowStyles);
            }
            if (null != (data = this.formService.getBigData(regionSettingKey, BIG_REGION_LAST_COLUMN_STYLES))) {
                List<RegionEdgeStyleDefine> lastColumnStyles = serializeUtil.deserialize(data, RegionEdgeStyleDefine.class);
                define.setLastColumnStyle(lastColumnStyles);
            }
            if (null != (data = this.formService.getBigData(regionSettingKey, "BIG_REGION_SURVEY"))) {
                define.setRegionSurvey(DesignFormDefineBigDataUtil.bytesToString(data));
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public void updateRegionSetting(DesignRegionSettingDefine regionSettingDefine) {
        try {
            SerializeListImpl<Object> serializeUtil;
            this.regionSettingService.updateDefine(regionSettingDefine);
            List<RegionTabSettingDefine> regionTabSettingDataList = regionSettingDefine.getRegionTabSetting();
            if (regionTabSettingDataList != null && regionTabSettingDataList.size() > 0) {
                byte[] data = RegionTabSettingData.regionTabSettingDataToBytes(regionTabSettingDataList);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_TAB, 1, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_TAB);
            }
            RecordCard cardRecord = regionSettingDefine.getCardRecord();
            if (null != cardRecord) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(cardRecord);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_CARD_RECORD, 1, byteArrayOutputStream.toByteArray());
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_CARD_RECORD);
            }
            List<RowNumberSetting> rowNumberSettings = regionSettingDefine.getRowNumberSetting();
            if (rowNumberSettings != null && rowNumberSettings.size() > 0) {
                serializeUtil = new SerializeListImpl<RowNumberSetting>(RowNumberSetting.class);
                byte[] data = serializeUtil.serialize(rowNumberSettings);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_ORDER, 1, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_ORDER);
            }
            serializeUtil = new SerializeListImpl<RegionEdgeStyleDefine>(RegionEdgeStyleDefine.class);
            List<RegionEdgeStyleDefine> lastRowStyles = regionSettingDefine.getLastRowStyles();
            if (null != lastRowStyles && !lastRowStyles.isEmpty()) {
                byte[] data = serializeUtil.serialize(lastRowStyles);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_LAST_ROW_STYLES, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_LAST_ROW_STYLES);
            }
            List<RegionEdgeStyleDefine> lastColumnStyles = regionSettingDefine.getLastColumnStyle();
            if (null != lastColumnStyles && !lastColumnStyles.isEmpty()) {
                byte[] data = serializeUtil.serialize(lastColumnStyles);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), BIG_REGION_LAST_COLUMN_STYLES, data);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), BIG_REGION_LAST_COLUMN_STYLES);
            }
            String regionSurvey = regionSettingDefine.getRegionSurvey();
            if (StringUtils.hasLength(regionSurvey)) {
                byte[] bytes = DesignFormDefineBigDataUtil.StringToBytes(regionSurvey);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), "BIG_REGION_SURVEY", 1, bytes);
            } else {
                this.formService.deleteOrderBigData(regionSettingDefine.getKey(), "BIG_REGION_SURVEY");
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public String addEnumLinkage(String dataLinkKey, DesignEnumLinkageSettingDefine linkageDefine) {
        try {
            linkageDefine.setKey(dataLinkKey);
            DesignEnumLinkageSettingDefine define = this.enumLinkageService.queryDefine(dataLinkKey);
            if (null != define) {
                this.enumLinkageService.updateDefine(linkageDefine);
            } else {
                this.enumLinkageService.insertDefine(linkageDefine);
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return linkageDefine.getKey();
    }

    @Override
    public DesignEnumLinkageSettingDefine getEnumLinkage(String dataLinkKey) {
        DesignEnumLinkageSettingDefine define = null;
        try {
            define = this.enumLinkageService.queryDefine(dataLinkKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public void updateEnumLinkage(DesignEnumLinkageSettingDefine linkageDefine) {
        try {
            this.enumLinkageService.updateDefine(linkageDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignEnumLinkageSettingDefine> getEnumLinkages(String[] dataLinkKeys) {
        List<DesignEnumLinkageSettingDefine> defines = null;
        try {
            defines = this.enumLinkageService.queryDefine(dataLinkKeys);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public DesignTaskGroupDefine createTaskGroup() {
        DesignTaskGroupDefineImpl define = new DesignTaskGroupDefineImpl();
        define.setKey(UUIDUtils.getKey());
        define.setOrder(OrderGenerator.newOrder());
        return define;
    }

    @Override
    public String insertTaskGroupDefine(DesignTaskGroupDefine taskGroup) {
        String id = null;
        try {
            id = this.taskGroupService.insertTaskGroupDefine(taskGroup);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return id;
    }

    @Override
    public void updateTaskGroupDefine(DesignTaskGroupDefine taskGroup) {
        try {
            this.taskGroupService.updateTaskGroupDefine(taskGroup);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteTaskGroupDefine(String taskGroupKey) {
        try {
            this.taskGroupService.deleteTaskGroupDefine(taskGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public DesignTaskGroupDefine queryTaskGroupDefine(String taskGroupKey) {
        DesignTaskGroupDefine define = null;
        try {
            define = this.taskGroupService.queryTaskGroupDefine(taskGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignTaskGroupDefine> getChildTaskGroups(String taskGroupKey, boolean isRecursion) {
        List<DesignTaskGroupDefine> defines = null;
        try {
            defines = this.taskGroupService.queryTaskGroupDefineByGroupId(taskGroupKey, isRecursion);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignTaskGroupDefine> getAllTaskGroup() {
        List<DesignTaskGroupDefine> defines = null;
        try {
            defines = this.taskGroupService.queryAllTaskGroupDefine();
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignTaskGroupDefine> getGroupByTask(String taskKey) {
        List<DesignTaskGroupDefine> defines = null;
        try {
            defines = this.taskGroupService.getGroupByTask(taskKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignTaskDefine> getAllTasksInGroup(String taskGroupKey, boolean isRecursion) {
        List<DesignTaskDefine> defines = null;
        try {
            defines = this.taskService.getAllTasksInGroup(taskGroupKey, isRecursion);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public void exchangeTaskGroup(String taskGroupkey1, String taskGroupkey2) {
        try {
            this.taskGroupService.exchangeTaskGroup(taskGroupkey1, taskGroupkey2);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void addTaskToGroup(String taskKey, String taskGroupKey) {
        try {
            this.taskService.addTaskToGroup(taskKey, taskGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void removeTaskFromGroup(String taskKey, String taskGroupKey) {
        try {
            this.taskService.RemoveTaskFromGroup(taskKey, taskGroupKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public DesignTaskLinkDefine createTaskLinkDefine() {
        DesignTaskLinkDefineImpl define = new DesignTaskLinkDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertTaskLinkDefine(DesignTaskLinkDefine taskLinkDefine) {
        String id = null;
        try {
            id = this.taskLinkService.insertTaskLinkDefine(taskLinkDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return id;
    }

    @Override
    public DesignTaskLinkDefine queryDesignByKey(String key) {
        DesignTaskLinkDefine define = null;
        try {
            define = this.taskLinkService.queryTaskLinkDefine(key);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<String> insertTaskLinkDefines(List<DesignTaskLinkDefine> taskLinkDefines) {
        List<String> ids = null;
        try {
            ids = this.taskLinkService.insertTaskLinkDefines(taskLinkDefines.toArray(new DesignTaskLinkDefine[1]));
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return ids;
    }

    @Override
    public void deleteTaskLinkDefine(String taskLinkDefineKey) {
        try {
            this.taskLinkService.delete(taskLinkDefineKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteTaskLinkDefineByCurrentFormScheme(String currentFormSchemeKey) {
        try {
            this.taskLinkService.deleteByCurrentFormSchemeKey(currentFormSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deleteTaskLinkDefineByCurrentFormSchemeAndNum(String currentFormSchemeKey, String serialNumber) {
        try {
            this.taskLinkService.deleteByCurrentFormSchemeKeyAndNumber(currentFormSchemeKey, serialNumber);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void updateTaskLinkDefine(DesignTaskLinkDefine taskLinkDefine) {
        try {
            this.taskLinkService.updateTaskLinkDefine(taskLinkDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void updateTaskLinkDefines(List<DesignTaskLinkDefine> taskLinkDefines) {
        try {
            this.taskLinkService.updateTaskLinkDefines(taskLinkDefines.toArray(new DesignTaskLinkDefine[1]));
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignTaskLinkDefine> queryLinksByCurrentFormScheme(String currentFormSchemeKey) {
        List<DesignTaskLinkDefine> list = null;
        try {
            list = this.taskLinkService.queryDefinesByCurrentFormSchemeKey(currentFormSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public DesignTaskLinkDefine queryLinkByCurrentFormSchemeAndNum(String currentFormSchemeKey, String serialNumber) {
        DesignTaskLinkDefine define = null;
        try {
            define = this.taskLinkService.queryDefinesByCurrentFormSchemeKeyAndNumber(currentFormSchemeKey, serialNumber);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignTaskLinkDefine> queryLinksByRelatedTaskCode(String relatedTaskCode) {
        List<DesignTaskLinkDefine> list = null;
        try {
            list = this.taskLinkService.queryLinksByRelatedTaskCode(relatedTaskCode);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return list;
    }

    @Override
    public DesignTaskDefine queryTaskDefineByTaskTitle(String taskTitle) {
        DesignTaskDefine define = null;
        try {
            define = this.taskService.queryTaskDefineByTaskTitle(taskTitle);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public String getFormSchemeEntity(String formSchemeKey) throws JQException {
        DesignFormSchemeDefine formScheme = this.queryFormSchemeDefine(formSchemeKey);
        if (formScheme == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_001, formSchemeKey);
        }
        DesignTaskDefine queryTaskDefine = this.queryTaskDefine(formScheme.getTaskKey());
        return queryTaskDefine.getMasterEntitiesKey();
    }

    @Override
    public List<DesignTableDefine> queryAllTableDefineInRegion(String dataRegionKey, boolean containsEnum) throws JQException {
        if (!containsEnum) {
            return this.getAllTableDefineInRegion(dataRegionKey);
        }
        return this.getAllTableDefineInRegion(dataRegionKey);
    }

    @Override
    public DesignEntityLinkageDefine createEntityLinkageDefine() {
        return new DesignEntityLinkageDefineImpl();
    }

    @Override
    public String insertDesignerEntityeLinkageDefine(DesignEntityLinkageDefine define) {
        String String2 = null;
        try {
            String2 = this.designEntityLinkageDefineService.insertDesignEntityLinkageDefine(define);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return String2;
    }

    @Override
    public DesignEntityLinkageDefine queryDesignEntityLinkageDefineByKey(String taskKey) {
        try {
            return this.designEntityLinkageDefineService.queryDesignEntityLinkageDefine(taskKey);
        }
        catch (BeanParaException e) {
            Log.error((Exception)((Object)e));
            return new DesignEntityLinkageDefineImpl();
        }
    }

    @Override
    public void updateEntityLinkageDefine(DesignEntityLinkageDefine define) {
        try {
            this.designEntityLinkageDefineService.updateEntityLinkageDefine(define);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignFormGroupDefine> queryAllGroupsByFormScheme(String formSchemeKey) throws JQException {
        try {
            return this.formGroupService.queryFormGroupDefinesByScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_000, (Throwable)e);
        }
    }

    @Override
    public DesignDataLinkDefine queryDataLinkDefineByColRow(String reportKey, int colIndex, int rowIndex) {
        return this.dataLinkService.queryDataLinkDefineByColRow(reportKey, colIndex, rowIndex);
    }

    @Override
    public DesignDataLinkDefine queryDataLinkDefineByUniquecode(String reportKey, String dataLinkCode) {
        return this.dataLinkService.queryDataLinkDefineByUniquecode(reportKey, dataLinkCode);
    }

    @Override
    public List<DesignDataLinkDefine> getLinksInFormByField(String formKey, String fieldKey) {
        return this.dataLinkService.getLinksInFormByField(formKey, fieldKey);
    }

    @Override
    public DesignFormSchemeDefine getFormschemeByCode(String code) throws Exception {
        return this.formSchemeService.queryFormSchemeDefinesByCode(code);
    }

    private void deleteFormulaAndScheme(String formSchemeKey) {
        List<DesignFormulaSchemeDefine> designFormulaSchemeDefineList = this.iFormulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (designFormulaSchemeDefineList != null && designFormulaSchemeDefineList.size() > 0) {
            for (DesignFormulaSchemeDefine designFormulaSchemeDefine : designFormulaSchemeDefineList) {
                this.iFormulaDesignTimeController.deleteFormulaDefinesByScheme(designFormulaSchemeDefine.getKey());
                this.iFormulaDesignTimeController.deleteFormulaSchemeDefine(designFormulaSchemeDefine.getKey());
            }
        }
    }

    private void deleteFormulaVariableByFormSchemeKey(String formSchemeKey) throws Exception {
        this.designFormulaVariableDefineService.deleteFormulaVariByFormScheme(formSchemeKey);
    }

    private void deletePrintTemplateAndScheme(String formSchemeKey) throws Exception {
        List<DesignPrintTemplateSchemeDefine> designPrintTemplateSchemeDefineList = this.iPrintDesignTimeController.getAllPrintSchemeByFormScheme(formSchemeKey);
        if (designPrintTemplateSchemeDefineList != null && designPrintTemplateSchemeDefineList.size() > 0) {
            for (DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine : designPrintTemplateSchemeDefineList) {
                this.iPrintDesignTimeController.deletePrintTemplateDefineByScheme(designPrintTemplateSchemeDefine.getKey());
                this.iPrintDesignTimeController.deletePrintTemplateSchemeDefine(designPrintTemplateSchemeDefine.getKey());
            }
        }
    }

    private <E extends Exception> void doThrow(Exception e) throws E {
        throw e;
    }

    @Override
    public DesignFormDefine queryFormByLanguageType(String formKey, int type) {
        try {
            DesignFormDefine define = this.queryFormByIdWithoutFormData(formKey);
            if (define == null) {
                return null;
            }
            define.setBinaryData(this.getReportDataFromForm(formKey, type));
            return define;
        }
        catch (Exception e) {
            this.doThrowDefinitionException("\u67e5\u8be2\u62a5\u8868\u51fa\u9519\uff01", e);
            return null;
        }
    }

    @Override
    public List<DesignFormDefine> queryFormsByTypeInScheme(String formSchemeKey, FormType type) throws JQException {
        return this.formService.queryFormsByTypeInScheme(formSchemeKey, type);
    }

    @Override
    public DesignFormDefine querySoftFormDefine(String formKey) {
        try {
            return this.queryFormAndExtAttribute(formKey, 0);
        }
        catch (Exception e) {
            throw new DefinitonException("\u67e5\u8be2\u62a5\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public DesignFormDefine queryFormAndExtAttribute(String formKey, int containType) {
        try {
            return this.formService.queryFormDefineContainsFormData(formKey, containType);
        }
        catch (Exception e) {
            throw new DefinitonException("\u67e5\u8be2\u62a5\u8868\u5931\u8d25", e);
        }
    }

    @Override
    public DesignFormDefine queryFormByIdWithoutFormData(String formKey) {
        try {
            return this.queryFormAndExtAttribute(formKey, 12);
        }
        catch (Exception e) {
            throw new DefinitonException("\u67e5\u8be2\u62a5\u8868\u5931\u8d25", e);
        }
    }

    private TaskFlowsDefine initTaskFlowDefine(DesignTaskDefine taskDefine) {
        DesignTaskFlowsDefine flowDefine = new DesignTaskFlowsDefine();
        flowDefine.setDesignTableDefines(taskDefine.getDw() + ";" + taskDefine.getDateTime());
        return flowDefine;
    }

    private void saveTaskFlowByTaskDefine(DesignTaskDefine taskDefine) throws Exception {
        TaskFlowsDefine designTaskFlowsDefine = taskDefine.getFlowsSetting();
        if (designTaskFlowsDefine == null) {
            designTaskFlowsDefine = this.initTaskFlowDefine(taskDefine);
        }
        byte[] data = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes(designTaskFlowsDefine);
        this.bigDataService.updateBigDataDefine(taskDefine.getKey(), BIG_FLOWDATA, data);
    }

    @Override
    public byte[] getFillGuide(String formKey) {
        try {
            return this.formService.getFillGuide(formKey);
        }
        catch (Exception e) {
            throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", e);
        }
    }

    @Override
    public void setFillGuide(String formKey, byte[] fillGuide) {
        try {
            this.formService.updateFillGuide(formKey, fillGuide);
        }
        catch (Exception e) {
            this.doThrowDefinitionException("\u4fdd\u5b58\u62a5\u8868\u586b\u62a5\u8bf4\u660e\u51fa\u9519", e);
        }
    }

    @Override
    public byte[] getFrontScript(String formKey) {
        try {
            return this.formService.getFrontScript(formKey);
        }
        catch (Exception e) {
            throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", e);
        }
    }

    @Override
    public void setFrontScript(String formKey, byte[] frontScript) {
        try {
            this.formService.updateFrontScript(formKey, frontScript);
        }
        catch (Exception e) {
            this.doThrowDefinitionException("\u4fdd\u5b58\u524d\u7aef\u811a\u672c\u51fa\u9519\uff01", e);
        }
    }

    @Override
    public byte[] getSurveyData(String formKey) {
        try {
            return this.formService.getSurveyData(formKey);
        }
        catch (Exception e) {
            throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", e);
        }
    }

    @Override
    public byte[] getRegionSurveyData(String regionKey) {
        try {
            byte[] bigData = this.bigDataService.getBigData(regionKey, "BIG_REGION_SURVEY");
            if (null != bigData) {
                return bigData;
            }
            return null;
        }
        catch (Exception e) {
            throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", e);
        }
    }

    @Override
    public void setSurveyData(String formKey, byte[] surveyData) {
        try {
            this.formService.updateSurveyData(formKey, surveyData);
        }
        catch (Exception e) {
            this.doThrowDefinitionException("\u4fdd\u5b58\u95ee\u5377\u4fe1\u606f\u51fa\u9519\uff01", e);
        }
    }

    @Override
    public void setRegionSurveyData(String regionKey, byte[] surveyData) {
        try {
            this.formService.updateBigDataDefine(regionKey, "BIG_REGION_SURVEY", surveyData);
        }
        catch (Exception e) {
            this.doThrowDefinitionException("\u4fdd\u5b58\u95ee\u5377\u4fe1\u606f\u51fa\u9519\uff01", e);
        }
    }

    @Override
    public void deleteRegionSurveyData(String regionKey) {
        try {
            this.formService.deleteOrderBigData(regionKey, "BIG_REGION_SURVEY");
        }
        catch (Exception e) {
            this.doThrowDefinitionException("\u5220\u9664\u95ee\u5377\u4fe1\u606f\u51fa\u9519\uff01", e);
        }
    }

    @Override
    public List<DesignCaliberGroupLink> getCaliberGroupByCaliberKey(String caliberKey) throws Exception {
        return this.caliberGroupLinkDefineService.getAllLink(caliberKey);
    }

    @Override
    public void updateCaliberLink(DesignCaliberGroupLink designCaliberGroupLink) {
        try {
            this.caliberGroupLinkDefineService.updateCaliberLink(designCaliberGroupLink);
        }
        catch (BeanParaException e) {
            logger.error(e.getMessage(), e);
        }
        catch (DBParaException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteCaliberLink(DesignCaliberGroupLink caliberGroupLink) {
        try {
            this.caliberGroupLinkDefineService.deleteCaliberLink(caliberGroupLink);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<DesignDataRegionDefine> getAllRegions() {
        List<DesignDataRegionDefine> regions = Collections.emptyList();
        try {
            regions = this.dataRegionService.queryAllDataRegionDefine();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return regions;
    }

    @Override
    public List<FormulaVariDefine> queryAllFormulaVariable(String formSchemeKey) {
        return this.designFormulaVariableDefineService.queryAllFormulaVariable(formSchemeKey);
    }

    @Override
    public Map<Integer, byte[]> getReportDataFromForms(String formKey) {
        try {
            return this.formService.getReportDatas(formKey);
        }
        catch (Exception e) {
            throw new DefinitonException("\u83b7\u53d6\u8868\u6837\u5931\u8d25", e);
        }
    }

    @Override
    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String scheme) throws Exception {
        return this.formSchemeService.querySchemePeriodLinkByScheme(scheme);
    }

    @Override
    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByTask(String task) throws Exception {
        return this.formSchemeService.querySchemePeriodLinkByTask(task);
    }

    @Override
    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndScheme(String period, String scheme) throws Exception {
        return this.formSchemeService.querySchemePeriodLinkByPeriodAndScheme(period, scheme);
    }

    @Override
    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndTask(String period, String task) throws Exception {
        return this.formSchemeService.querySchemePeriodLinkByPeriodAndTask(period, task);
    }

    @Override
    public void deleteSchemePeriodLink(List<DesignSchemePeriodLinkDefine> defines) throws Exception {
        this.formSchemeService.deleteSchemePeriodLink(defines);
    }

    @Override
    public void inserSchemePeriodLink(List<DesignSchemePeriodLinkDefine> defines) throws Exception {
        this.formSchemeService.inserSchemePeriodLink(defines);
    }

    @Override
    public void deleteSchemePeriodLinkByScheme(String scheme) throws Exception {
        this.formSchemeService.deleteByScheme(scheme);
    }

    @Override
    public void deleteSchemePeriodLinkByTask(String task) throws Exception {
        List<DesignFormSchemeDefine> schemes = this.formSchemeService.queryFormSchemeDefineByTaskKey(task);
        for (DesignFormSchemeDefine define : schemes) {
            this.deleteSchemePeriodLinkByScheme(define.getKey());
        }
    }

    @Override
    public List<DesignDataLinkMappingDefine> queryDataLinkMappingByFormKey(String formKey) {
        return this.designDataLinkMappingDefineService.getDataLinkMapping(formKey);
    }

    @Override
    public void saveOrUpdateDataLinkMapping(String formKey, List<DataLinkMappingVO> content) throws JQException {
        ArrayList<DesignDataLinkMappingDefine> list = new ArrayList<DesignDataLinkMappingDefine>();
        if (CollectionUtils.isEmpty(content)) {
            try {
                this.designDataLinkMappingDefineService.saveOrUpdateDataLinkMapping(formKey, list);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_063, (Throwable)e);
            }
        }
        for (DataLinkMappingVO data : content) {
            list.add(this.createDesignDataLinkMappingDefine(formKey, data.getFirstLinkId(), data.getSecondLinkId()));
        }
        try {
            this.designDataLinkMappingDefineService.saveOrUpdateDataLinkMapping(formKey, list);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_063, (Throwable)e);
        }
    }

    @Override
    public List<DataLinkMappingVO> getDataLinkMappingVO(String formKey) {
        return this.invert(this.designDataLinkMappingDefineService.getDataLinkMapping(formKey));
    }

    @Override
    public void deleteDataLinkMapping(String formKey) throws JQException {
        try {
            this.designDataLinkMappingDefineService.deleteDataLinkMappingByFormKey(formKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_064, (Throwable)e);
        }
    }

    private List<DataLinkMappingVO> invert(List<DesignDataLinkMappingDefine> content) {
        ArrayList<DataLinkMappingVO> res = new ArrayList<DataLinkMappingVO>();
        for (DesignDataLinkMappingDefine designDataLinkMappingInfo : content) {
            res.add(new DataLinkMappingVO(designDataLinkMappingInfo.getLeftDataLinkKey(), designDataLinkMappingInfo.getRightDataLinkKey()));
        }
        return res;
    }

    private DesignDataLinkMappingDefine createDesignDataLinkMappingDefine(String formKey, String firstLinkKey, String secondLinkKey) {
        DesignDataLinkMappingDefineImpl info = new DesignDataLinkMappingDefineImpl();
        String uuid = UUIDUtils.getKey();
        info.setId(uuid);
        info.setFormKey(formKey);
        info.setLeftDataLinkKey(firstLinkKey);
        info.setRightDataLinkKey(secondLinkKey);
        return info;
    }

    @Override
    public DesignDataLinkMappingDefine createDataLinkMappingDefine() {
        DesignDataLinkMappingDefineImpl dataLinkMappingDefine = new DesignDataLinkMappingDefineImpl();
        dataLinkMappingDefine.setId(UUIDUtils.getKey());
        return dataLinkMappingDefine;
    }

    @Override
    public void insertDataLinkMappingDefine(DesignDataLinkMappingDefine designDataLinkMappingDefine) throws JQException {
        try {
            this.designDataLinkMappingDefineService.insertDataLinkMappingDefine(designDataLinkMappingDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_065, (Throwable)e);
        }
    }

    @Override
    public void updateDataLinkMappingDefine(DesignDataLinkMappingDefine designDataLinkMappingDefine) throws JQException {
        try {
            this.designDataLinkMappingDefineService.updateDataLinkMappingDefine(designDataLinkMappingDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_066, (Throwable)e);
        }
    }

    @Override
    public void insertDataLinkMappingDefines(DesignDataLinkMappingDefine[] designDataLinkMappingDefines) throws JQException {
        try {
            this.designDataLinkMappingDefineService.insertDataLinkMappingDefine(designDataLinkMappingDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_065, (Throwable)e);
        }
    }

    @Override
    public void updateDataLinkMappingDefines(DesignDataLinkMappingDefine[] designDataLinkMappingDefines) throws JQException {
        try {
            this.designDataLinkMappingDefineService.updateDataLinkMappingDefine(designDataLinkMappingDefines);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_066, (Throwable)e);
        }
    }

    @Override
    public DesignReportTemplateDefine createReportTemplateDefine() {
        return this.designReportTemplateService.createReportTemplateDefine();
    }

    @Override
    public List<DesignReportTemplateDefine> getReportTemplateByTask(String taskKey) {
        return this.designReportTemplateService.getReportTemplateByTask(taskKey);
    }

    @Override
    public List<DesignReportTemplateDefine> getReportTemplateByScheme(String formSchemeKey) {
        return this.designReportTemplateService.getReportTemplateByScheme(formSchemeKey);
    }

    @Override
    public void insertReportTemplate(DesignReportTemplateDefine template, String originalFileName, InputStream inputStream) throws JQException {
        this.designReportTemplateService.insertReportTemplate(template, originalFileName, inputStream);
    }

    @Override
    public void updateReportTemplate(DesignReportTemplateDefine template) throws JQException {
        this.designReportTemplateService.updateReportTemplate(template);
    }

    @Override
    public void updateReportTemplate(String templateKey, String fileName, String originalFileName, InputStream inputStream) throws JQException {
        this.designReportTemplateService.updateReportTemplate(templateKey, fileName, originalFileName, inputStream);
    }

    @Override
    public void deleteReportTemplate(String ... keys) throws JQException {
        this.designReportTemplateService.deleteReportTemplate(keys);
    }

    @Override
    public void deleteReportTemplateByScheme(String formSchemeKey) throws JQException {
        this.designReportTemplateService.deleteReportTemplateByScheme(formSchemeKey);
    }

    @Override
    public void getReportTemplateFile(String fileKey, OutputStream outputStream) {
        this.designReportTemplateService.getReportTemplateFile(fileKey, outputStream);
    }

    @Override
    public List<DesignFormDefine> getSimpleFormDefines(List<String> formKeys) {
        return this.formService.getSimpleFormDefines(formKeys);
    }

    @Override
    public List<DesignFormGroupLink> getFormGroupLinks(List<String> groups) {
        return this.formService.getFormGroupLink(groups);
    }

    @Override
    public List<DesignReportTagDefine> queryAllTagsByRptKey(String rptKey) {
        return this.designReportTagService.queryAllTagsByRptKey(rptKey);
    }

    @Override
    public void deleteTagByKeys(List<String> keys) throws JQException {
        this.designReportTagService.deleteTagByKeys(keys);
    }

    @Override
    public void insertTags(List<DesignReportTagDefine> list) throws JQException {
        this.designReportTagService.insertTags(list);
    }

    @Override
    public void deleteTagsByRptKey(String rptKey) throws JQException {
        this.designReportTagService.deleteTagsByRptKey(rptKey);
    }

    @Override
    public void saveTag(DesignReportTagDefine reportTagDefine) throws JQException {
        this.designReportTagService.saveTag(reportTagDefine);
    }

    @Override
    public List<DesignReportTagDefine> filterCustomTagsInRpt(InputStream is, String rptKey) throws JQException {
        return this.designReportTagService.filterCustomTagsInRpt(is, rptKey);
    }

    @Override
    public TransformReportDefine exportReport(String formSchemeKey) {
        return this.designTransformReportService.exportReport(formSchemeKey);
    }

    @Override
    public void importReport(TransformReportDefine transformReportDefine, Boolean isFullAmountImport) throws JQException {
        this.designTransformReportService.importReport(transformReportDefine, isFullAmountImport);
    }

    @Override
    public void deleteReport(String formSchemeKey) throws JQException {
        this.designTransformReportService.deleteReportInfoByFormSchemeKey(formSchemeKey);
    }

    @Override
    public List<DesignTaskGroupLink> getGroupLinkByTaskKey(String taskKey) {
        if (taskKey == null) {
            return Collections.emptyList();
        }
        return this.taskService.getGroupLinkByTaskKey(taskKey);
    }

    @Override
    public List<DesignTaskGroupLink> getGroupLink() {
        return this.taskService.getGroupLink();
    }

    @Override
    public List<DesignTaskGroupLink> getGroupLinkByGroupKey(String groupKey) {
        if (groupKey == null) {
            return Collections.emptyList();
        }
        return this.taskService.getGroupLinkByGroupKey(groupKey);
    }

    @Override
    public DesignDimensionFilter createDesignDimensionFilter() {
        DesignDimensionFilterImpl dimensionFilter = new DesignDimensionFilterImpl();
        dimensionFilter.setListType(DimensionFilterListType.BLACK_LIST);
        dimensionFilter.setType(DimensionFilterType.LIST_SELECT);
        dimensionFilter.setKey(UUID.randomUUID().toString());
        return dimensionFilter;
    }

    @Override
    public DesignDimensionFilter getDimensionFilterByTaskKey(String taskKey, String entityId) {
        return this.designDimensionFilterService.getDimensionFilterByTaskKey(taskKey, entityId);
    }

    @Override
    public List<DesignDimensionFilter> getDimensionFilterByTaskKey(String taskKey) {
        return this.designDimensionFilterService.getDimensionFilterByTaskKey(taskKey);
    }

    @Override
    public void updateDimensionFilter(DesignDimensionFilter dimensionFilter) {
        this.designDimensionFilterService.updateDimensionFilter(dimensionFilter);
    }

    @Override
    public void insertDimensionFilter(DesignDimensionFilter dimensionFilter) throws JQException {
        this.designDimensionFilterService.insertDimensionFilter(dimensionFilter);
    }

    @Override
    public void insertDimensionFilters(List<DesignDimensionFilter> dimensionFilters) throws JQException {
        this.designDimensionFilterService.insertDimensionFilters(dimensionFilters);
    }

    @Override
    public void deleteDimensionFilter(String taskKey) {
        this.designDimensionFilterService.deleteDimensionFilter(taskKey);
    }

    @Override
    public void saveDimensionFiltersByTaskKey(String taskKey, List<DesignDimensionFilter> list) {
        this.designDimensionFilterService.saveDimensionFiltersByTaskKey(taskKey, list);
    }

    @Override
    public DesignDataRegionDefine getDataRegion(String regionCode, String formKey) {
        return this.dataRegionService.getDataRegion(formKey, regionCode);
    }
}

