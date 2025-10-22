/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.common.param.ReadOnlyContext
 *  com.jiuqi.nr.data.common.service.IRegionDataLinkReadOnlyService
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.ExtractCellInfo
 *  com.jiuqi.nr.jtable.service.IExtractFormulaService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.efdc.service.impl;

import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.common.param.ReadOnlyContext;
import com.jiuqi.nr.data.common.service.IRegionDataLinkReadOnlyService;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.efdc.extract.ExtractDataRegion;
import com.jiuqi.nr.efdc.extract.IExtractRequest;
import com.jiuqi.nr.efdc.extract.exception.ExtractException;
import com.jiuqi.nr.efdc.extract.impl.EFDCExtractRequestFactory;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import com.jiuqi.nr.jtable.service.IExtractFormulaService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EFDCExtractFormulaService
implements IExtractFormulaService,
IRegionDataLinkReadOnlyService {
    private static final Logger log = LoggerFactory.getLogger(EFDCExtractFormulaService.class);
    @Resource
    IRunTimeViewController runTimeViewController;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IJtableParamService jtableParamService;
    @Resource
    IFormulaRunTimeController formulaRunTimeController;
    @Resource
    IEFDCConfigService EFDCConfigServiceImpl;
    @Resource
    IRuntimeDataLinkService iRuntimeDataLinkService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private ITaskOptionController iTaskOptionController;

    public Set<String> getReadOnlyDataLinks(ReadOnlyContext readOnlyContext) {
        String value = this.iTaskOptionController.getValue(readOnlyContext.getTaskKey(), "EFDC_GET_VALUE_MODIFY_TASK");
        if ("1".equals(value)) {
            return new HashSet<String>();
        }
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(readOnlyContext.getTaskKey());
        jtableContext.setFormSchemeKey(readOnlyContext.getFormSchemeKey());
        jtableContext.setFormKey(readOnlyContext.getFormKey());
        DimensionValueSet dimensionValueSet = readOnlyContext.getDimensionValueSet();
        if (dimensionValueSet != null) {
            jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        }
        return new HashSet<String>(this.getExtractDataLinkList(jtableContext));
    }

    public boolean isAllOrgShare(ReadOnlyContext readOnlyContext) {
        return true;
    }

    public List<String> getExtractDataLinkList(JtableContext jtableContext) {
        List<String> efdcDataLinks = new ArrayList<String>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        if (taskDefine == null || !taskDefine.getEfdcSwitch()) {
            return efdcDataLinks;
        }
        FormulaSchemeDefine formulaScheme = this.getFormulaScheme(jtableContext);
        if (formulaScheme == null) {
            return efdcDataLinks;
        }
        efdcDataLinks = this.getExtractDataLinkList(jtableContext.getFormSchemeKey(), formulaScheme, jtableContext.getFormKey());
        return efdcDataLinks;
    }

    public List<ExtractCellInfo> getExtractDataLinkList(JtableContext jtableContext, String formulaSchemaKey) {
        FormulaSchemeDefine formulaScheme = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemaKey);
        List<String> efdcLinks = this.getExtractDataLinkList(jtableContext.getFormSchemeKey(), formulaScheme, jtableContext.getFormKey());
        ArrayList<ExtractCellInfo> cellInfos = new ArrayList<ExtractCellInfo>();
        for (String link : efdcLinks) {
            ExtractCellInfo extractCellInfo = new ExtractCellInfo();
            extractCellInfo.setLinkKey(link);
            cellInfos.add(extractCellInfo);
        }
        return cellInfos;
    }

    private List<String> getExtractDataLinkList(String formSchemaKey, FormulaSchemeDefine formulaScheme, String formKey) {
        ArrayList<String> efdcDataLinks = new ArrayList<String>();
        if (formulaScheme == null) {
            return efdcDataLinks;
        }
        FormDefine form = this.runTimeViewController.queryFormById(formKey);
        EFDCExtractRequestFactory efdcxtractResultFactory = new EFDCExtractRequestFactory();
        IExtractRequest createReqeust = efdcxtractResultFactory.createReqeust(formulaScheme, form);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        if (form != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemaKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        try {
            List<ExtractDataRegion> extractDataRegionList = createReqeust.doPrepare(executorContext, null, this.formulaRunTimeController);
            for (ExtractDataRegion extractDataRegion : extractDataRegionList) {
                for (int i = 0; i < extractDataRegion.getColmumCount(); ++i) {
                    DataLinkColumn colmum = extractDataRegion.getColmum(i);
                    DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefineByUniquecode(form.getKey(), colmum.getDataLinkCode());
                    if (dataLinkDefine == null || dataLinkDefine.getPosX() == 0 || dataLinkDefine.getPosY() == 0) continue;
                    efdcDataLinks.add(dataLinkDefine.getKey().toString());
                }
            }
        }
        catch (ExtractException extractException) {
            // empty catch block
        }
        return efdcDataLinks;
    }

    public FormulaSchemeDefine getSoluctionByDimensions(JtableContext jtableContext) {
        FormulaSchemeDefine formulaScheme = this.getFormulaScheme(jtableContext);
        return formulaScheme;
    }

    public List<FormulaDefine> getEFDCFormulaInfo(JtableContext jtableContext, String dataLinkKey) {
        ArrayList<FormulaDefine> formulaDefineList = new ArrayList<FormulaDefine>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        if (taskDefine == null || !taskDefine.getEfdcSwitch()) {
            return formulaDefineList;
        }
        FormulaSchemeDefine formulaScheme = this.getFormulaScheme(jtableContext);
        if (formulaScheme == null) {
            return formulaDefineList;
        }
        FormDefine form = this.runTimeViewController.queryFormById(jtableContext.getFormKey());
        EFDCExtractRequestFactory efdcxtractResultFactory = new EFDCExtractRequestFactory();
        IExtractRequest createReqeust = efdcxtractResultFactory.createReqeust(formulaScheme, form);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION);
        if (form != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, jtableContext.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        try {
            List allFormulasDefines = this.formulaRunTimeController.getAllFormulasInForm(formulaScheme.getKey(), form.getKey());
            if (allFormulasDefines == null) {
                throw new ExtractException("\u6ca1\u6709\u627e\u5230\u5f53\u524d\u516c\u5f0f\u65b9\u6848\u4e2d\u7684\u53d6\u6570\u516c\u5f0f");
            }
            List queryPublicFormulaDefineByScheme = this.formulaRunTimeController.queryPublicFormulaDefineByScheme(formulaScheme.getKey(), form.getKey());
            allFormulasDefines.addAll(queryPublicFormulaDefineByScheme);
            List<ExtractDataRegion> extractDataRegionList = createReqeust.doPrepare(executorContext, null, this.formulaRunTimeController);
            DataLinkDefine dataLinkDefine = this.iRuntimeDataLinkService.queryDataLink(dataLinkKey);
            for (ExtractDataRegion extractDataRegion : extractDataRegionList) {
                for (int i = 0; i < extractDataRegion.getColmumCount(); ++i) {
                    DataLinkColumn colmum = extractDataRegion.getColmum(i);
                    if (!colmum.getDataLinkCode().equals(dataLinkDefine.getUniqueCode())) continue;
                    String formulaKey = extractDataRegion.getFormulaKeys().get(i);
                    if (formulaKey.equals("")) {
                        for (FormulaDefine formulaDefineInfo : allFormulasDefines) {
                            if (formulaDefineInfo.getExpression().indexOf(extractDataRegion.getColmumExpression(i)) <= 0) continue;
                            formulaDefineList.add(formulaDefineInfo);
                        }
                        continue;
                    }
                    FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(formulaKey);
                    formulaDefineList.add(formulaDefine);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return formulaDefineList;
    }

    private FormulaSchemeDefine getFormulaScheme(JtableContext jtableContext) {
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        excutorJtableContext.setFormKey("");
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)excutorJtableContext);
        String unitKey = dimensionValueSet.getValue(dwEntity.getDimensionName()).toString();
        List dimEntityList = this.jtableParamService.getDimEntityList(jtableContext.getFormSchemeKey());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            DimensionValue dimensionValue = (DimensionValue)jtableContext.getDimensionSet().get(entityInfo.getDimensionName());
            if (dimensionValue == null) {
                dimensionValue = new DimensionValue();
                dimensionValue.setValue("");
            }
            dimMap.put(entityInfo.getTableName(), dimensionValue.getValue());
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(formSchemeDefine.getDateTime());
        dimMap.put(periodEntity.getDimensionName(), ((DimensionValue)jtableContext.getDimensionSet().get(periodEntity.getDimensionName())).getValue());
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), unitKey);
        return this.EFDCConfigServiceImpl.getSoluctionByDimensions(queryObjectImpl, dimMap, dwEntity.getKey());
    }
}

