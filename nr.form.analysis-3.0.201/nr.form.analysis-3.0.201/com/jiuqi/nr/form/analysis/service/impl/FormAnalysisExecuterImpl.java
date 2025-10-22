/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.analysis.define.AnalysisCaliber
 *  com.jiuqi.nr.data.engine.analysis.define.AnalysisModel
 *  com.jiuqi.nr.data.engine.analysis.define.FloatRegionConfig
 *  com.jiuqi.nr.data.engine.analysis.define.GroupingConfig
 *  com.jiuqi.nr.data.engine.analysis.define.OrderField
 *  com.jiuqi.nr.data.engine.analysis.exe.IAnalysisEngine
 *  com.jiuqi.nr.data.engine.analysis.exe.IAnalysisEngineProvider
 *  com.jiuqi.nr.definition.common.DimensionRange
 *  com.jiuqi.nr.definition.common.DimensionType
 *  com.jiuqi.nr.definition.common.LineType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.facade.analysis.FloatListConfig
 *  com.jiuqi.nr.definition.facade.analysis.LineCaliber
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.form.analysis.service.impl;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.analysis.define.AnalysisCaliber;
import com.jiuqi.nr.data.engine.analysis.define.AnalysisModel;
import com.jiuqi.nr.data.engine.analysis.define.FloatRegionConfig;
import com.jiuqi.nr.data.engine.analysis.define.GroupingConfig;
import com.jiuqi.nr.data.engine.analysis.define.OrderField;
import com.jiuqi.nr.data.engine.analysis.exe.IAnalysisEngine;
import com.jiuqi.nr.data.engine.analysis.exe.IAnalysisEngineProvider;
import com.jiuqi.nr.definition.common.DimensionRange;
import com.jiuqi.nr.definition.common.DimensionType;
import com.jiuqi.nr.definition.common.LineType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.facade.analysis.FloatListConfig;
import com.jiuqi.nr.definition.facade.analysis.LineCaliber;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.form.analysis.common.AnalysisFmlExecEnvironment;
import com.jiuqi.nr.form.analysis.common.FormAnalysisErrorEnum;
import com.jiuqi.nr.form.analysis.common.FormAnalysisParamEnum;
import com.jiuqi.nr.form.analysis.dto.AnalysisFormParamDTO;
import com.jiuqi.nr.form.analysis.dto.AnalysisParamDTO;
import com.jiuqi.nr.form.analysis.dto.AnalysisSchemeParamDTO;
import com.jiuqi.nr.form.analysis.dto.CaliberParamDTO;
import com.jiuqi.nr.form.analysis.dto.DestDimsParamDTO;
import com.jiuqi.nr.form.analysis.dto.FloatRegionParamDTO;
import com.jiuqi.nr.form.analysis.dto.PeriodDimParamDTO;
import com.jiuqi.nr.form.analysis.dto.SrcDimsParamDTO;
import com.jiuqi.nr.form.analysis.dto.SrcEntityDimParamDTO;
import com.jiuqi.nr.form.analysis.service.IFormAnalysisExecuter;
import com.jiuqi.nr.form.analysis.service.impl.FormAnalysisEntityHelper;
import com.jiuqi.nr.form.analysis.web.facade.CommonDimParamVO;
import com.jiuqi.nr.form.analysis.web.facade.FormAnalysisParamVO;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_FORMANALYSIS_BATCHANALYSIS", groupTitle="\u5d4c\u5165\u5f0f\u5206\u6790")
public class FormAnalysisExecuterImpl
extends NpRealTimeTaskExecutor
implements IFormAnalysisExecuter {
    private transient IAnalysisEngineProvider analysisEngineProvider;
    private transient IRunTimeViewController runTimeViewController;
    private transient IFormulaRunTimeController formulaRunTimeController;
    private transient FormAnalysisEntityHelper formAnalysisEntityHelper;
    private transient IDataDefinitionRuntimeController runtimeController;
    private transient IEntityViewRunTimeController iEntityViewRunTimeController;
    private transient INvwaSystemOptionService iNvwaSystemOptionService;
    private static final Logger log = LoggerFactory.getLogger(FormAnalysisExecuterImpl.class);

    public void execute(JobContext jobContext) throws JobExecutionException {
        this.analysisEngineProvider = (IAnalysisEngineProvider)SpringBeanUtils.getBean(IAnalysisEngineProvider.class);
        this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        this.formulaRunTimeController = (IFormulaRunTimeController)SpringBeanUtils.getBean(IFormulaRunTimeController.class);
        this.formAnalysisEntityHelper = (FormAnalysisEntityHelper)SpringBeanUtils.getBean(FormAnalysisEntityHelper.class);
        this.runtimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        this.iEntityViewRunTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
        this.iNvwaSystemOptionService = (INvwaSystemOptionService)SpringBeanUtils.getBean(INvwaSystemOptionService.class);
        String params = (String)jobContext.getRealTimeJob().getParams().get("NR_ARGS");
        FormAnalysisParamVO args = (FormAnalysisParamVO)SimpleParamConverter.SerializationUtils.deserialize((String)params);
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), "ASYNCTASK_FORMANALYSIS_BATCHANALYSIS", jobContext);
        try {
            monitor.progressAndMessage(0.1, "\u6784\u5efa\u5206\u6790\u53c2\u6570");
            AnalysisParamDTO analysisParam = this.buildAnalysisParam(args);
            this.execute(analysisParam, arg_0 -> FormAnalysisExecuterImpl.lambda$execute$0((AsyncTaskMonitor)monitor, arg_0));
        }
        catch (Exception e) {
            monitor.error("\u5206\u6790\u5f02\u5e38", (Throwable)e);
        }
        if (!monitor.isFinish()) {
            monitor.finish("\u5206\u6790\u5b8c\u6210", null);
        }
    }

    private AnalysisParamDTO buildAnalysisParam(FormAnalysisParamVO param) throws Exception {
        AnalysisSchemeParamDTO analysisScheme = new AnalysisSchemeParamDTO();
        analysisScheme.setFormSchemeKey(param.getSchemeKey());
        analysisScheme.setFormulaSchemeKey(param.getFormulaSchemeKey());
        AnalysisSchemeParamDefine anaSchemeDefine = this.runTimeViewController.queryAnalysisSchemeParamDefine(param.getSchemeKey());
        analysisScheme.setSrcTaskKey(anaSchemeDefine.getSrcTaskKey());
        analysisScheme.setSrcFormSchemeKey(anaSchemeDefine.getSrcFormSchemeKey());
        analysisScheme.setCondition(anaSchemeDefine.getCondition());
        List<CommonDimParamVO> srcDimension = param.getSrcDimension();
        List srcDims = anaSchemeDefine.getSrcDims();
        analysisScheme.setSrcDims(this.buildSrcDimsParam(srcDimension, srcDims));
        analysisScheme.setDistDims(this.buildDestDimsParam(param));
        List<AnalysisFormParamDTO> anaForms = this.buildAnaFormsParam(param);
        return new AnalysisParamDTO(analysisScheme, anaForms);
    }

    private SrcDimsParamDTO buildSrcDimsParam(List<CommonDimParamVO> srcDimensions, List<DimensionInfo> srcDims) {
        SrcEntityDimParamDTO org = null;
        PeriodDimParamDTO period = null;
        List<SrcEntityDimParamDTO> dims = null;
        HashMap<String, SrcEntityDimParamDTO> dimsMap = new HashMap<String, SrcEntityDimParamDTO>();
        if (!CollectionUtils.isEmpty(srcDimensions)) {
            for (CommonDimParamVO srcDimension : srcDimensions) {
                if (DimensionType.ENTITY_PERIOD == srcDimension.getType()) {
                    period = this.buildSrcPeriodDimParam(srcDimension);
                    dimsMap.put(period.getEntityId(), null);
                    continue;
                }
                SrcEntityDimParamDTO dimParam = this.buildSrcEntityDimParam(srcDimension);
                dimsMap.put(dimParam.getEntityId(), dimParam);
            }
        }
        String orgEntityId = null;
        if (!CollectionUtils.isEmpty(srcDims)) {
            for (DimensionInfo srcDim : srcDims) {
                if (!StringUtils.hasText(orgEntityId) && DimensionType.ENTITY == srcDim.getType()) {
                    orgEntityId = srcDim.getKey();
                }
                if (dimsMap.containsKey(srcDim.getKey())) continue;
                if (DimensionType.ENTITY_PERIOD == srcDim.getType()) {
                    period = this.buildSrcPeriodDimParam(srcDim);
                    dimsMap.put(period.getEntityId(), null);
                    continue;
                }
                SrcEntityDimParamDTO dimParam = this.buildSrcEntityDimParam(srcDim);
                dimsMap.put(dimParam.getEntityId(), dimParam);
            }
        }
        org = (SrcEntityDimParamDTO)dimsMap.get(orgEntityId);
        dimsMap.remove(orgEntityId);
        dimsMap.remove(period.getEntityId());
        dims = dimsMap.values().stream().collect(Collectors.toList());
        return new SrcDimsParamDTO(org, period, dims);
    }

    private SrcEntityDimParamDTO buildSrcEntityDimParam(DimensionInfo srcDim) {
        SrcEntityDimParamDTO dimParam = new SrcEntityDimParamDTO();
        dimParam.setEntityId(srcDim.getKey());
        dimParam.setDataRangeType(this.valueOf(srcDim.getConfig().getUnitRange()));
        dimParam.setCondition(srcDim.getConfig().getCondition());
        dimParam.setDimDatas(srcDim.getConfig().getUnitKeys());
        return dimParam;
    }

    private SrcEntityDimParamDTO buildSrcEntityDimParam(CommonDimParamVO srcDimension) {
        SrcEntityDimParamDTO dimParam = new SrcEntityDimParamDTO();
        dimParam.setEntityId(srcDimension.getKey());
        dimParam.setDataRangeType(this.valueOf(srcDimension.getConfig().getUnitRangeType()));
        dimParam.setCondition(srcDimension.getConfig().getCondition());
        dimParam.setDimDatas(srcDimension.getConfig().getDimDataCodes());
        return dimParam;
    }

    private PeriodDimParamDTO buildSrcPeriodDimParam(DimensionInfo srcDim) {
        PeriodDimParamDTO period = new PeriodDimParamDTO();
        period.setEntityId(srcDim.getKey());
        switch (srcDim.getConfig().getPeriodRangeType()) {
            case NONE: {
                period.setDataRangeType(FormAnalysisParamEnum.PeriodDataRangeType.NONE);
                break;
            }
            case DEST: {
                period.setDataRangeType(FormAnalysisParamEnum.PeriodDataRangeType.AS_DEST);
                break;
            }
            case SELECTION: {
                period.setDataRangeType(FormAnalysisParamEnum.PeriodDataRangeType.NONE);
                if (!CollectionUtils.isEmpty((Collection)srcDim.getConfig().getUnitKeys())) {
                    period.setDimDatas(srcDim.getConfig().getUnitKeys());
                    break;
                }
                period.setDimDatas(this.formAnalysisEntityHelper.getPeriodDatas(srcDim.getKey(), srcDim.getConfig().getPeriodRange()));
                break;
            }
        }
        return period;
    }

    private PeriodDimParamDTO buildSrcPeriodDimParam(CommonDimParamVO srcDimension) {
        PeriodDimParamDTO period = new PeriodDimParamDTO();
        period.setEntityId(srcDimension.getKey());
        switch (srcDimension.getConfig().getPeriodRangeType()) {
            case NONE: {
                period.setDataRangeType(FormAnalysisParamEnum.PeriodDataRangeType.NONE);
                break;
            }
            case DEST: {
                period.setDataRangeType(FormAnalysisParamEnum.PeriodDataRangeType.AS_DEST);
                break;
            }
            case SELECTION: {
                period.setDataRangeType(FormAnalysisParamEnum.PeriodDataRangeType.NONE);
                if (!CollectionUtils.isEmpty(srcDimension.getConfig().getDimDataCodes())) {
                    period.setDimDatas(srcDimension.getConfig().getDimDataCodes());
                    break;
                }
                period.setDimDatas(this.formAnalysisEntityHelper.getPeriodDatas(srcDimension.getKey(), srcDimension.getConfig().getPeriodRange()));
                break;
            }
        }
        return period;
    }

    private FormAnalysisParamEnum.DimDataRangeType valueOf(DimensionRange type) {
        switch (type) {
            case NONE: {
                return FormAnalysisParamEnum.DimDataRangeType.NONE;
            }
            case ITSELF: {
                return FormAnalysisParamEnum.DimDataRangeType.AS_DEST;
            }
            case BROTHERS: {
                return FormAnalysisParamEnum.DimDataRangeType.DEST_BROTHERS;
            }
            case CHILDREN: {
                return FormAnalysisParamEnum.DimDataRangeType.DEST_CHILDREN;
            }
            case ALL_CHILDREN: {
                return FormAnalysisParamEnum.DimDataRangeType.DEST_ALL_CHILDREN;
            }
            case SELECTION: {
                return FormAnalysisParamEnum.DimDataRangeType.SELECTED;
            }
            case CONDITION: {
                return FormAnalysisParamEnum.DimDataRangeType.CONDITION;
            }
        }
        return FormAnalysisParamEnum.DimDataRangeType.NONE;
    }

    private DestDimsParamDTO buildDestDimsParam(FormAnalysisParamVO param) throws JQException {
        FormAnalysisEntityHelper.SrcDimDataQueryer dimInfoQueryer = this.formAnalysisEntityHelper.createSrcDimDataQueryer(null);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getSchemeKey());
        String orgEntityId = formScheme.getDw();
        String periodEntityId = formScheme.getDateTime();
        ArrayList<String> dimEntityIds = null;
        String dims = formScheme.getDims();
        HashMap<String, String> allDimNames = new HashMap<String, String>();
        allDimNames.put(orgEntityId, dimInfoQueryer.getDimName(orgEntityId));
        allDimNames.put(periodEntityId, dimInfoQueryer.getDimName(periodEntityId));
        if (StringUtils.hasText((String)dims)) {
            dimEntityIds = new ArrayList<String>();
            for (String dimId : dims.split(";")) {
                dimEntityIds.add(dimId);
                allDimNames.put(dimId, dimInfoQueryer.getDimName(dimId));
            }
        }
        Map<String, DimensionValue> destDims = param.getDestDims();
        DimensionValueSet periodValueSet = new DimensionValueSet();
        periodValueSet.setValue((String)allDimNames.get(periodEntityId), (Object)destDims.get(allDimNames.get(periodEntityId)).getValue());
        List<String> orgEntityRowIds = null;
        if (param.isAllEntity()) {
            IEntityTable iEntityTable = this.formAnalysisEntityHelper.getIEntityTable(orgEntityId, periodValueSet, true);
            orgEntityRowIds = iEntityTable.getAllRows().stream().map(IEntityItem::getCode).collect(Collectors.toList());
        } else {
            orgEntityRowIds = Collections.singletonList(destDims.get(allDimNames.get(orgEntityId)).getValue());
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(destDims);
        return new DestDimsParamDTO(orgEntityId, periodEntityId, dimEntityIds, dimensionValueSet, allDimNames, orgEntityRowIds);
    }

    private List<AnalysisFormParamDTO> buildAnaFormsParam(FormAnalysisParamVO param) throws Exception {
        ArrayList<AnalysisFormParamDTO> anaForms = new ArrayList<AnalysisFormParamDTO>();
        List<Object> formDefines = null;
        switch (param.getFormSelectionEnum()) {
            case ALL: {
                formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(param.getSchemeKey());
                break;
            }
            case CURRENT: {
                FormDefine formDefine = this.runTimeViewController.queryFormById(param.getFormKey());
                formDefines = Arrays.asList(formDefine);
                break;
            }
            case SELECTION: {
                List<String> formKeys = param.getSelectedFormKeys();
                formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(param.getSchemeKey());
                formDefines = formDefines.stream().filter(f -> formKeys.contains(f.getKey())).collect(Collectors.toList());
                break;
            }
        }
        if (null != formDefines) {
            for (FormDefine formDefine : formDefines) {
                AnalysisFormParamDTO anaFormParam = this.buildAnaFormParam(formDefine);
                if (null == anaFormParam) continue;
                anaForms.add(anaFormParam);
            }
        }
        return anaForms;
    }

    private AnalysisFormParamDTO buildAnaFormParam(FormDefine formDefine) throws Exception {
        List floatListSettings;
        AnalysisFormParamDTO anaForm = new AnalysisFormParamDTO();
        anaForm.setFormKey(formDefine.getKey());
        anaForm.setFormCode(formDefine.getFormCode());
        AnalysisFormParamDefine anaFormDefine = this.runTimeViewController.queryAnalysisFormParamDefine(formDefine.getKey());
        if (null == anaFormDefine) {
            return null;
        }
        anaForm.setCondition(anaFormDefine.getConditionFormula());
        List lineCalibers = anaFormDefine.getLineCalibers();
        if (!CollectionUtils.isEmpty((Collection)lineCalibers)) {
            ArrayList<CaliberParamDTO> colCalibers = new ArrayList<CaliberParamDTO>();
            ArrayList<CaliberParamDTO> rowCalibers = new ArrayList<CaliberParamDTO>();
            for (LineCaliber c : lineCalibers) {
                if (LineType.ROW == c.getType()) {
                    rowCalibers.add(new CaliberParamDTO(c.getLineNumber(), c.getCondition()));
                    continue;
                }
                colCalibers.add(new CaliberParamDTO(c.getColNumber(), c.getCondition()));
            }
            anaForm.setColCalibers(colCalibers);
            anaForm.setRowCalibers(rowCalibers);
        }
        if (!CollectionUtils.isEmpty((Collection)(floatListSettings = anaFormDefine.getFloatListSettings()))) {
            List<FloatRegionParamDTO> floatRegionSettings = floatListSettings.stream().map(this::toFloatRegionParam).collect(Collectors.toList());
            anaForm.setFloatRegionSettings(floatRegionSettings);
        }
        return anaForm;
    }

    private FloatRegionParamDTO toFloatRegionParam(FloatListConfig floatSetting) {
        FloatRegionParamDTO param = new FloatRegionParamDTO();
        param.setDataRegionKey(floatSetting.getDataRegionKey());
        param.setClassifyFields(floatSetting.getClassifyFields());
        param.setClassifySumOnly(floatSetting.getClassifySumOnly());
        param.setClassifyWidths(floatSetting.getClassifyWidths());
        param.setListCondition(floatSetting.getListCondition());
        param.setListFilter(floatSetting.getListFilter());
        param.setMaxRowCount(floatSetting.getMaxRowCount());
        param.setSortFields(floatSetting.getSortFields());
        param.setSortFlags(floatSetting.getSortFlags());
        param.setSumExpressions(floatSetting.getSumExpressions());
        return param;
    }

    public String getTaskPoolType() {
        return "ASYNCTASK_FORMANALYSIS_BATCHANALYSIS";
    }

    @Override
    public void execute(AnalysisParamDTO analysisParam, Consumer<IFormAnalysisExecuter.FormAnalysisProgress> progressConsumer) {
        IFormAnalysisExecuter.FormAnalysisProgress progress = new IFormAnalysisExecuter.FormAnalysisProgress();
        progressConsumer.accept(progress);
        try {
            Map<String, IAnalysisEngine> analysisEngines = this.buildAnalysisModel(analysisParam);
            AnalysisSchemeParamDTO analysisScheme = analysisParam.getAnalysisScheme();
            FormAnalysisEntityHelper.SrcDimDataQueryer dimInfoQueryer = this.formAnalysisEntityHelper.createSrcDimDataQueryer(analysisScheme.getSrcFormSchemeKey());
            DestDimsParamDTO distDims = analysisScheme.getDistDims();
            SrcDimsParamDTO srcDims = analysisScheme.getSrcDims();
            List<String> orgEntityRowIds = distDims.getOrgEntityRowIds();
            DimensionValueSet destDimValueSet = null;
            String formCode = null;
            int anaIndex = 0;
            progressConsumer.accept(progress.analysising(analysisEngines.size() * orgEntityRowIds.size()));
            for (String orgEntityRowId : orgEntityRowIds) {
                for (Map.Entry<String, IAnalysisEngine> entry : analysisEngines.entrySet()) {
                    formCode = entry.getKey();
                    destDimValueSet = distDims.getDimValueSet(orgEntityRowId);
                    progressConsumer.accept(progress.setActiveProgress(++anaIndex, destDimValueSet, formCode));
                    entry.getValue().execute(destDimValueSet, this.buildSrcDimValueSet(dimInfoQueryer, distDims, distDims.getEntityDatasMap(orgEntityRowId), srcDims));
                }
            }
        }
        catch (Exception e) {
            log.error("\u5d4c\u5165\u5f0f\u5206\u6790\uff1a\u5206\u6790\u5f02\u5e38[{}].", (Object)e.getMessage());
            progressConsumer.accept(progress.fail(e.getMessage()));
        }
        progressConsumer.accept(progress.success("\u5206\u6790\u5b8c\u6210"));
    }

    private DimensionValueSet buildDestDimValueSet(FormAnalysisEntityHelper.SrcDimDataQueryer dimInfoQueryer, Map<String, String> destDimDataMap) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry<String, String> e : destDimDataMap.entrySet()) {
            dimensionValueSet.setValue(dimInfoQueryer.getDimName(e.getKey()), (Object)e.getValue());
        }
        return dimensionValueSet;
    }

    private DimensionValueSet buildSrcDimValueSet(FormAnalysisEntityHelper.SrcDimDataQueryer dimInfoQueryer, DestDimsParamDTO distDims, Map<String, String> destDimDataMap, SrcDimsParamDTO srcDims) throws JQException {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        DimensionValueSet periodValueSet = new DimensionValueSet();
        this.buildSrcPeriodValueSet(periodValueSet, dimInfoQueryer, srcDims, distDims, destDimDataMap);
        dimInfoQueryer.set(periodValueSet);
        dimensionValueSet.assign(periodValueSet);
        SrcEntityDimParamDTO srcOrgDim = srcDims.getOrg();
        List<SrcEntityDimParamDTO> srcEnDims = srcDims.getDims();
        if (null == srcEnDims) {
            srcEnDims = Collections.singletonList(srcOrgDim);
        } else {
            srcEnDims.add(0, srcOrgDim);
        }
        this.buildSrcEntityValueSet(dimensionValueSet, dimInfoQueryer, distDims, destDimDataMap, srcEnDims);
        return dimensionValueSet;
    }

    private void buildSrcEntityValueSet(DimensionValueSet dimensionValueSet, FormAnalysisEntityHelper.SrcDimDataQueryer dimInfoQueryer, DestDimsParamDTO distDims, Map<String, String> destDimDataMap, List<SrcEntityDimParamDTO> srcEnDims) throws JQException {
        List<String> enDimDatas = null;
        for (SrcEntityDimParamDTO srcDim : srcEnDims) {
            switch (srcDim.getDataRangeType()) {
                case CONDITION: {
                    enDimDatas = dimInfoQueryer.getEntityDatas(distDims.getOrgEntityId(), srcDim.getEntityId(), destDimDataMap.get(srcDim.getEntityId()), srcDim.getCondition());
                    break;
                }
                case SELECTED: {
                    enDimDatas = srcDim.getDimDatas();
                    break;
                }
                case AS_DEST: {
                    enDimDatas = destDimDataMap.get(srcDim.getEntityId());
                    break;
                }
                case DEST_CHILDREN: 
                case DEST_ALL_CHILDREN: 
                case DEST_BROTHERS: {
                    enDimDatas = dimInfoQueryer.getEntityDatas(srcDim.getEntityId(), destDimDataMap.get(srcDim.getEntityId()), srcDim.getDataRangeType());
                    break;
                }
            }
            if (null == enDimDatas) continue;
            dimensionValueSet.setValue(dimInfoQueryer.getDimName(srcDim.getEntityId()), (Object)enDimDatas);
        }
    }

    private void buildSrcPeriodValueSet(DimensionValueSet periodValueSet, FormAnalysisEntityHelper.SrcDimDataQueryer dimInfoQueryer, SrcDimsParamDTO srcDims, DestDimsParamDTO distDims, Map<String, String> destDimDataMap) {
        PeriodDimParamDTO srcPeriodDim = srcDims.getPeriod();
        Object periodDimValue = null;
        switch (srcPeriodDim.getDataRangeType()) {
            case SELECTED: {
                periodDimValue = srcPeriodDim.getDimDatas();
                break;
            }
            case AS_DEST: {
                periodDimValue = destDimDataMap.get(distDims.getPeriodEntityId());
                break;
            }
        }
        if (null != periodDimValue) {
            periodValueSet.setValue(dimInfoQueryer.getDimName(srcPeriodDim.getEntityId()), periodDimValue);
        }
    }

    private <K, T> void superForEach(Map<K, List<T>> dataMap, Consumer<Map<K, T>> mapConsumer) {
        int dimCount = dataMap.size();
        int foreachCount = 1;
        int[] dimDataCount = new int[dimCount];
        ArrayList<Map.Entry<K, List<T>>> allDatas = new ArrayList<Map.Entry<K, List<T>>>();
        for (Map.Entry<K, List<T>> e : dataMap.entrySet()) {
            foreachCount *= e.getValue().size();
            dimDataCount[allDatas.size()] = e.getValue().size();
            allDatas.add(e);
        }
        int[] dimDataIndex = null;
        Map.Entry entry = null;
        HashMap result = new HashMap();
        for (int index = 0; index < foreachCount; ++index) {
            dimDataIndex = this.getDimDataIndex(index, dimDataCount);
            for (int i = 0; i < dimCount; ++i) {
                entry = (Map.Entry)allDatas.get(i);
                result.put(entry.getKey(), ((List)entry.getValue()).get(dimDataIndex[i]));
            }
            mapConsumer.accept(result);
            result.clear();
        }
    }

    private int[] getDimDataIndex(int index, int[] dimDataCount) {
        int[] dimDataIndex = new int[dimDataCount.length];
        int s = index;
        int y = 0;
        for (int i = dimDataCount.length - 1; i >= 0; --i) {
            s = index / dimDataIndex[i];
            dimDataIndex[i] = y = index % dimDataIndex[i];
            if (0 == s) break;
        }
        return dimDataIndex;
    }

    private ExecutorContext buildExecutorContext(String formSchemeKey, String srcFormSchemeKey) throws ParseException {
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        AnalysisFmlExecEnvironment env = new AnalysisFmlExecEnvironment(this.runTimeViewController, this.runtimeController, this.iEntityViewRunTimeController, formSchemeKey, srcFormSchemeKey);
        String sumMonth = this.iNvwaSystemOptionService.get("form-ana-sum-month-options-id", "FORMULA_VAR_SUM_MONTH");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat FORMULA_VAR_DATEFORMATE = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(FORMULA_VAR_DATEFORMATE.parse(sumMonth));
        Variable variable = new Variable("HZYF", "\u6c47\u603b\u6708\u4efd", 5);
        variable.setVarValue((Object)cal);
        env.getVariableManager().add(variable);
        executorContext.setEnv((IFmlExecEnvironment)env);
        return executorContext;
    }

    private Map<String, IAnalysisEngine> buildAnalysisModel(AnalysisParamDTO analysisParam) throws JQException {
        AnalysisSchemeParamDTO analysisScheme = analysisParam.getAnalysisScheme();
        List<AnalysisFormParamDTO> analysisForms = analysisParam.getAnalysisForms();
        if (null == analysisScheme || CollectionUtils.isEmpty(analysisForms)) {
            return Collections.emptyMap();
        }
        HashMap<String, IAnalysisEngine> result = new HashMap<String, IAnalysisEngine>();
        try {
            ExecutorContext executorContext = this.buildExecutorContext(analysisScheme.getFormSchemeKey(), analysisScheme.getSrcFormSchemeKey());
            IAnalysisEngine analysisEngine = null;
            for (AnalysisFormParamDTO analysisForm : analysisForms) {
                analysisEngine = this.analysisEngineProvider.createAnalysisEngine();
                analysisEngine.prepare(executorContext, this.buildAnalysisModel(analysisScheme, analysisForm));
                result.put(analysisForm.getFormCode(), analysisEngine);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormAnalysisErrorEnum.EXCEPTION_801, (Throwable)e);
        }
        return result;
    }

    private AnalysisModel buildAnalysisModel(AnalysisSchemeParamDTO anaScheme, AnalysisFormParamDTO anaForm) {
        AnalysisModel model = new AnalysisModel();
        model.setReportName(anaForm.getFormCode());
        model.setGlobalFilter(anaScheme.getCondition());
        List formulaDefines = this.formulaRunTimeController.getAllFormulasInForm(anaScheme.getFormulaSchemeKey(), anaForm.getFormKey());
        if (!CollectionUtils.isEmpty((Collection)formulaDefines)) {
            model.getFormulas().addAll(formulaDefines.stream().map(FormulaDefine::getExpression).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        this.fillCalibers(model.getRowCalibers(), anaForm.getRowCalibers());
        this.fillCalibers(model.getColCalibers(), anaForm.getColCalibers());
        this.fillRegionConfig(model.getRegionConfigMap(), anaForm.getFloatRegionSettings());
        return model;
    }

    private void fillCalibers(List<AnalysisCaliber> calibers, List<CaliberParamDTO> caliberParams) {
        if (CollectionUtils.isEmpty(caliberParams)) {
            return;
        }
        caliberParams.forEach(c -> {
            AnalysisCaliber caliber = new AnalysisCaliber(c.getPosNum(), c.getCondition());
            calibers.add(caliber);
        });
    }

    private void fillRegionConfig(Map<String, FloatRegionConfig> regionConfigMap, List<FloatRegionParamDTO> regionSettings) {
        if (CollectionUtils.isEmpty(regionSettings)) {
            return;
        }
        for (FloatRegionParamDTO floatSetting : regionSettings) {
            GroupingConfig groupingConfig = new GroupingConfig();
            groupingConfig.setGroupingKey(floatSetting.getClassifyFields());
            groupingConfig.setLevelString(floatSetting.getClassifyWidths());
            if (!StringUtils.isEmpty((String)floatSetting.getClassifyFields())) {
                groupingConfig.setDiscardDetail(floatSetting.isClassifySumOnly());
            }
            groupingConfig.getGroupingKeyEvalExps().addAll(floatSetting.getSumExpressions());
            DataRegionDefine dataRegion = this.runTimeViewController.queryDataRegionDefine(floatSetting.getDataRegionKey());
            FloatRegionConfig regionConfig = new FloatRegionConfig(floatSetting.getDataRegionKey());
            regionConfig.setRegionCode(floatSetting.getDataRegionKey());
            regionConfig.setSrcMainDimFilter(floatSetting.getListCondition());
            regionConfig.setRowFilter(floatSetting.getListFilter());
            regionConfig.setTopN(floatSetting.getMaxRowCount());
            regionConfig.setGroupingConfig(groupingConfig);
            regionConfig.setRegionRowIndex(dataRegion.getRegionTop());
            List orderFields = regionConfig.getOrderFields();
            if (!StringUtils.isEmpty((String)floatSetting.getSortFields())) {
                String[] fields = floatSetting.getSortFields().split(";");
                String[] flags = floatSetting.getSortFlags().split(";");
                int fLen = flags.length;
                int len = fields.length;
                for (int i = 0; i < len; ++i) {
                    Boolean desc = i >= fLen ? flags[fLen - 1].equals("0") : flags[i].equals("0");
                    orderFields.add(new OrderField(fields[i], desc.booleanValue()));
                }
            }
            regionConfigMap.put(regionConfig.getRegionCode(), regionConfig);
        }
    }

    private static /* synthetic */ void lambda$execute$0(AsyncTaskMonitor monitor, IFormAnalysisExecuter.FormAnalysisProgress p) {
        if (p.isFail()) {
            monitor.error("\u5206\u6790\u5f02\u5e38", null, p.getMessage());
        } else if (p.isSuccess()) {
            monitor.finish("\u5206\u6790\u5b8c\u6210", (Object)p.getMessage());
        } else {
            monitor.progressAndMessage(0.15 + 0.8 * p.getProgress(), p.getMessage());
        }
        log.info("\u5d4c\u5165\u5f0f\u5206\u6790\u8868\uff1a{}", (Object)p.getMessage());
    }
}

