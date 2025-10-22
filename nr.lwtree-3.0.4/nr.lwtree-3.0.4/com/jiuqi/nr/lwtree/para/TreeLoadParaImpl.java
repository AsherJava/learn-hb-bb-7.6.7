/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.lwtree.para;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.lwtree.para.ITreeLoadPara;
import com.jiuqi.nr.lwtree.para.VariableMapDeserializer;
import java.util.Map;
import org.json.JSONObject;

public class TreeLoadParaImpl
implements ITreeLoadPara {
    private String taskId;
    private String viewKey;
    private String period;
    private String parentKey;
    private String vStartDate;
    private String vEndDate;
    private String formSchemeKey;
    private Map<String, DimensionValue> dimValueSet;
    private JSONObject customVariable;
    private String expression;

    @Override
    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    @Override
    public String getFormSchemeKey() {
        if (!StringUtils.isNotEmpty((String)this.formSchemeKey)) {
            IRunTimeViewController rtViewService = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            if (StringUtils.isNotEmpty((String)this.taskId) && StringUtils.isNotEmpty((String)this.period)) {
                try {
                    FormSchemeDefine formScheme;
                    SchemePeriodLinkDefine schemePeriodLinkDefine = rtViewService.querySchemePeriodLinkByPeriodAndTask(this.period, this.taskId);
                    if (schemePeriodLinkDefine != null && (formScheme = rtViewService.getFormScheme(schemePeriodLinkDefine.getSchemeKey())) != null) {
                        this.formSchemeKey = formScheme.getKey();
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public String getvStartDate() {
        return this.vStartDate;
    }

    public void setvStartDate(String vStartDate) {
        this.vStartDate = vStartDate;
    }

    @Override
    public String getvEndDate() {
        return this.vEndDate;
    }

    public void setvEndDate(String vEndDate) {
        this.vEndDate = vEndDate;
    }

    @Override
    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    @Override
    public JSONObject getCustomVariable() {
        return this.customVariable;
    }

    @JsonDeserialize(using=VariableMapDeserializer.class)
    public void setCustomVariable(JSONObject customVariable) {
        this.customVariable = customVariable;
    }

    public void checkPara() {
    }

    public void checkPeriod() {
        if (StringUtils.isNotEmpty((String)this.period)) {
            PeriodWrapper periodWrapper = new PeriodWrapper();
            periodWrapper.parseString(this.period);
        }
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

