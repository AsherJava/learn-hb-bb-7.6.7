/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.ScriptParser;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineGetterImpl;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RunTimeFormDefineGetterImpl
implements FormDefine {
    private static final long serialVersionUID = 1L;
    private RunTimeFormDefineImpl formDefine;
    private RunTimeFormSchemeDefineGetterImpl runTimeFormSchemeDefineGetter;

    public RunTimeFormDefineGetterImpl(FormDefine form) {
        if (form != null && form instanceof RunTimeFormDefineImpl) {
            this.formDefine = (RunTimeFormDefineImpl)form;
        }
    }

    @Override
    public String getFormScheme() {
        return this.formDefine.getFormScheme();
    }

    private String getExtendMasterEntitiesKey() {
        this.initFormSchemeDefines();
        return this.runTimeFormSchemeDefineGetter.getMasterEntitiesKey();
    }

    private String getExtendMeasureUnit() {
        if (StringUtils.isEmpty((String)this.formDefine.getMeasureUnit())) {
            this.initFormSchemeDefines();
            IRunTimeViewController runTimeViewController = BeanUtil.getBean(IRunTimeViewController.class);
            List<FormGroupDefine> formGroupDefineList = runTimeViewController.getFormGroupsByFormKey(this.formDefine.getKey());
            Iterator<FormGroupDefine> iterator = formGroupDefineList.iterator();
            if (iterator.hasNext()) {
                FormGroupDefine formGroupDefine = iterator.next();
                if (StringUtils.isEmpty((String)formGroupDefine.getMeasureUnit())) {
                    return this.runTimeFormSchemeDefineGetter.getMeasureUnit();
                }
                return formGroupDefine.getMeasureUnit();
            }
        }
        return this.formDefine.getMeasureUnit();
    }

    public String getKey() {
        return this.formDefine.getKey();
    }

    public String getTitle() {
        return this.formDefine.getTitle();
    }

    public String getOrder() {
        return this.formDefine.getOrder();
    }

    public String getVersion() {
        return this.formDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.formDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.formDefine.getUpdateTime();
    }

    @Override
    public String getFormCode() {
        return this.formDefine.getFormCode();
    }

    @Override
    public String getSubTitle() {
        return this.formDefine.getSubTitle();
    }

    @Override
    public String getDescription() {
        return this.formDefine.getDescription();
    }

    @Override
    public String getSerialNumber() {
        return this.formDefine.getSerialNumber();
    }

    @Override
    public boolean getIsGather() {
        return this.formDefine.getIsGather();
    }

    @Override
    public String getMeasureUnit() {
        if (null == this.formDefine.getMeasureUnit()) {
            String extendMeasureUnit = this.getExtendMeasureUnit();
            this.formDefine.setMeasureUnit(null == extendMeasureUnit ? "" : extendMeasureUnit);
        }
        return this.formDefine.getMeasureUnit();
    }

    @Override
    public IMeasureConfig getMeasureConfig() {
        return this.formDefine.getMeasureConfig();
    }

    @Override
    public int getSecretLevel() {
        return this.formDefine.getSecretLevel();
    }

    @Override
    public FormType getFormType() {
        return this.formDefine.getFormType();
    }

    @Override
    public String getFormCondition() {
        return this.formDefine.getFormCondition();
    }

    @Override
    public PeriodType getFormPeriodType() {
        return this.formDefine.getFormPeriodType();
    }

    @Override
    public FormViewType getFormViewType() {
        return this.formDefine.getFormViewType();
    }

    @Override
    public String getMasterEntitiesKey() {
        if (null == this.formDefine.getMasterEntitiesKey()) {
            String extendMasterEntitiesKey = this.getExtendMasterEntitiesKey();
            this.formDefine.setMasterEntitiesKey(null == extendMasterEntitiesKey ? "" : extendMasterEntitiesKey);
        }
        return this.formDefine.getMasterEntitiesKey();
    }

    private void initFormSchemeDefines() {
        IRunTimeViewController runTimeViewController = BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(this.formDefine.getFormScheme());
        this.runTimeFormSchemeDefineGetter = new RunTimeFormSchemeDefineGetterImpl(formScheme);
    }

    @Override
    public FormDefine clone() throws CloneNotSupportedException {
        return this.formDefine.clone();
    }

    @Override
    public String getReadOnlyCondition() {
        return this.formDefine.getReadOnlyCondition();
    }

    @Override
    @Deprecated
    public String getFillingGuide() {
        return this.formDefine.getFillingGuide();
    }

    @Override
    @Deprecated
    public byte[] getBinaryData() {
        return this.formDefine.getBinaryData();
    }

    @Override
    public String getSurveyData() {
        return this.formDefine.getSurveyData();
    }

    @Override
    public String getScriptEditor() {
        return new ScriptParser(this.getKey()).parser(this.formDefine.getScriptEditor());
    }

    @Override
    public boolean getQuoteType() {
        return this.formDefine.getQuoteType();
    }

    @Override
    public boolean isAnalysisForm() {
        return this.formDefine.isAnalysisForm();
    }

    @Override
    public boolean getLedgerForm() {
        return this.formDefine.getLedgerForm();
    }

    @Override
    public Object getExtensionProp(String key) {
        return this.formDefine.getExtensionProp(key);
    }

    @Override
    public Set<String> getExtensionPronNames() {
        return this.formDefine.getExtensionPronNames();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        if (this.formDefine.getFillInAutomaticallyDue().getType() == FillInAutomaticallyDue.Type.DEFAULT.getValue()) {
            return null;
        }
        return this.formDefine.getFillInAutomaticallyDue();
    }

    public RunTimeFormDefineImpl getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(RunTimeFormDefineImpl formDefine) {
        this.formDefine = formDefine;
    }

    @Override
    public Map<String, Object> getFormExtension() {
        return this.formDefine.getFormExtension();
    }

    @Override
    public String getUpdateUser() {
        return this.formDefine.getUpdateUser();
    }
}

