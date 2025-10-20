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
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DesignFormDefineGetterImpl
implements FormDefine {
    private static final long serialVersionUID = 1L;
    private DesignFormDefine designFormDefine;
    private DesignFormSchemeDefineGetterImpl designFormSchemeDefineGetterImpl;

    public DesignFormDefineGetterImpl(DesignFormDefine designFormDefine) {
        if (designFormDefine != null) {
            this.designFormDefine = designFormDefine;
            IDesignTimeViewController designTimeViewController = BeanUtil.getBean(DesignTimeViewController.class);
            List<DesignFormGroupDefine> formGroups = designTimeViewController.getFormGroupsByFormId(designFormDefine.getKey());
            if (formGroups != null && formGroups.size() > 0) {
                DesignFormGroupDefine formGroup = formGroups.get(0);
                DesignFormSchemeDefine formScheme = designTimeViewController.queryFormSchemeDefine(formGroup.getFormSchemeKey());
                this.designFormSchemeDefineGetterImpl = new DesignFormSchemeDefineGetterImpl(formScheme);
            }
        }
    }

    @Override
    public String getFormScheme() {
        return this.designFormDefine.getFormScheme();
    }

    public String getKey() {
        return this.designFormDefine.getKey();
    }

    public String getTitle() {
        return this.designFormDefine.getTitle();
    }

    public String getOrder() {
        return this.designFormDefine.getOrder();
    }

    public String getVersion() {
        return this.designFormDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designFormDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.designFormDefine.getUpdateTime();
    }

    @Override
    public String getFormCode() {
        return this.designFormDefine.getFormCode();
    }

    @Override
    public String getSubTitle() {
        return this.designFormDefine.getSubTitle();
    }

    @Override
    public String getDescription() {
        return this.designFormDefine.getDescription();
    }

    @Override
    public String getSerialNumber() {
        return this.designFormDefine.getSerialNumber();
    }

    @Override
    public boolean getIsGather() {
        return this.designFormDefine.getIsGather();
    }

    @Override
    public String getMeasureUnit() {
        if (StringUtils.isEmpty((String)this.designFormDefine.getMeasureUnit()) && this.designFormSchemeDefineGetterImpl != null) {
            return this.designFormSchemeDefineGetterImpl.getMeasureUnit();
        }
        return this.designFormDefine.getMeasureUnit();
    }

    @Override
    public IMeasureConfig getMeasureConfig() {
        return this.designFormDefine.getMeasureConfig();
    }

    @Override
    public int getSecretLevel() {
        return this.designFormDefine.getSecretLevel();
    }

    @Override
    public FormType getFormType() {
        return this.designFormDefine.getFormType();
    }

    @Override
    public String getFormCondition() {
        return this.designFormDefine.getFormCondition();
    }

    @Override
    public PeriodType getFormPeriodType() {
        return this.designFormDefine.getFormPeriodType();
    }

    @Override
    public FormViewType getFormViewType() {
        return this.designFormDefine.getFormViewType();
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.designFormSchemeDefineGetterImpl.getMasterEntitiesKey();
    }

    @Override
    public FormDefine clone() throws CloneNotSupportedException {
        return this.designFormDefine.clone();
    }

    @Override
    public String getReadOnlyCondition() {
        return this.designFormDefine.getReadOnlyCondition();
    }

    @Override
    @Deprecated
    public String getFillingGuide() {
        return this.designFormDefine.getFillingGuide();
    }

    @Override
    @Deprecated
    public byte[] getBinaryData() {
        return this.designFormDefine.getBinaryData();
    }

    @Override
    public String getSurveyData() {
        return this.designFormDefine.getSurveyData();
    }

    @Override
    public String getScriptEditor() {
        return this.designFormDefine.getScriptEditor();
    }

    @Override
    public boolean getQuoteType() {
        return this.designFormDefine.getQuoteType();
    }

    @Override
    public boolean isAnalysisForm() {
        return this.designFormDefine.isAnalysisForm();
    }

    @Override
    public boolean getLedgerForm() {
        return this.designFormDefine.getLedgerForm();
    }

    @Override
    public Object getExtensionProp(String key) {
        return this.designFormDefine.getExtensionProp(key);
    }

    @Override
    public Set<String> getExtensionPronNames() {
        return this.designFormDefine.getExtensionPronNames();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        if (this.designFormDefine.getFillInAutomaticallyDue().getType() == FillInAutomaticallyDue.Type.DEFAULT.getValue()) {
            return null;
        }
        return this.designFormDefine.getFillInAutomaticallyDue();
    }

    @Override
    public Map<String, Object> getFormExtension() {
        return this.designFormDefine.getFormExtension();
    }

    @Override
    public String getUpdateUser() {
        return this.designFormDefine.getUpdateUser();
    }
}

