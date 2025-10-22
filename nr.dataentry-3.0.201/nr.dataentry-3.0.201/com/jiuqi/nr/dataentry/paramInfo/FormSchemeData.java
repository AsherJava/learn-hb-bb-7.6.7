/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dataentry.bean.DWorkflowConfig;
import com.jiuqi.nr.dataentry.paramInfo.FormulaSchemeData;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormSchemeData {
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeData.class);
    private String key;
    private String code;
    private String title;
    private List<EntityViewData> entitys = new ArrayList<EntityViewData>();
    private int periodType;
    private String fromPeriod;
    private String toPeriod;
    private int periodOffset;
    private List<FormulaSchemeData> formulaSchemes = new ArrayList<FormulaSchemeData>();
    private List<FormulaSchemeData> cwFormulaSchemes = new ArrayList<FormulaSchemeData>();
    private List<FormulaSchemeData> pickFormulaSchemes = new ArrayList<FormulaSchemeData>();
    private DWorkflowConfig workflowConfig;
    private AnalysisSchemeParamDefine analysisParamDefine;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EntityViewData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityViewData> entitys) {
        this.entitys = entitys;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public int getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public void setFormulaSchemes(List<FormulaSchemeData> formulaSchemes) {
        this.formulaSchemes = formulaSchemes;
    }

    public void init(IFormulaRunTimeController formulaRunTimeController, IDataAccessProvider dataAccessProvider, FormSchemeDefine formSchemeDefine, TaskDefine taskDefine) throws Exception {
        PeriodWrapper fromPeriodWrapper;
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.key = formSchemeDefine.getKey();
        this.code = formSchemeDefine.getFormSchemeCode();
        this.title = formSchemeDefine.getTitle();
        this.periodType = formSchemeDefine.getPeriodType().type();
        if (StringUtils.isEmpty((String)formSchemeDefine.getFromPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(1970, formSchemeDefine.getPeriodType().type(), 1);
            this.fromPeriod = fromPeriodWrapper.toString();
        } else {
            this.fromPeriod = formSchemeDefine.getFromPeriod();
        }
        if (StringUtils.isEmpty((String)formSchemeDefine.getToPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(9999, formSchemeDefine.getPeriodType().type(), 1);
            this.toPeriod = fromPeriodWrapper.toString();
        } else {
            this.toPeriod = formSchemeDefine.getToPeriod();
        }
        this.periodOffset = formSchemeDefine.getPeriodOffset();
        try {
            this.entitys = jtableParamService.getEntityList(formSchemeDefine.getKey());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        List formulaSchemeDefines = formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
        DefinitionAuthorityProvider authorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class);
        for (Object formulaScheme : formulaSchemeDefines) {
            if (!authorityProvider.canReadFormulaScheme((FormulaSchemeDefine)formulaScheme, formSchemeDefine)) continue;
            FormulaSchemeData formulaSchemeData = new FormulaSchemeData();
            formulaSchemeData.init((FormulaSchemeDefine)formulaScheme);
            this.getFormulaSchemes().add(formulaSchemeData);
        }
        List allCWFormulaSchemeDefinesByFormScheme = formulaRunTimeController.getAllCWFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
        for (FormulaSchemeDefine formulaScheme : allCWFormulaSchemeDefinesByFormScheme) {
            FormulaSchemeData formulaSchemeData = new FormulaSchemeData();
            formulaSchemeData.init(formulaScheme);
            this.getCwFormulaSchemes().add(formulaSchemeData);
        }
        List pickFormulaSchemeDefines = formulaRunTimeController.getAllPickFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
        for (FormulaSchemeDefine formulaScheme : pickFormulaSchemeDefines) {
            if (!authorityProvider.canReadFormulaScheme(formulaScheme, formSchemeDefine)) continue;
            FormulaSchemeData formulaSchemeData = new FormulaSchemeData();
            formulaSchemeData.init(formulaScheme);
            this.getPickFormulaSchemes().add(formulaSchemeData);
        }
    }

    public List<FormulaSchemeData> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public DWorkflowConfig getWorkflowConfig() {
        return this.workflowConfig;
    }

    public void setWorkflowConfig(DWorkflowConfig workflowConfig) {
        this.workflowConfig = workflowConfig;
    }

    public List<FormulaSchemeData> getCwFormulaSchemes() {
        return this.cwFormulaSchemes;
    }

    public void setCwFormulaSchemes(List<FormulaSchemeData> cwFormulaSchemes) {
        this.cwFormulaSchemes = cwFormulaSchemes;
    }

    public List<FormulaSchemeData> getPickFormulaSchemes() {
        return this.pickFormulaSchemes;
    }

    public void setPickFormulaSchemes(List<FormulaSchemeData> pickFormulaSchemes) {
        this.pickFormulaSchemes = pickFormulaSchemes;
    }

    public AnalysisSchemeParamDefine getAnalysisParamDefine() {
        return this.analysisParamDefine;
    }

    public void setAnalysisParamDefine(AnalysisSchemeParamDefine analysisParamDefine) {
        this.analysisParamDefine = analysisParamDefine;
    }
}

