/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.Map;
import java.util.Set;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIgnoreProperties(ignoreUnknown=true)
public interface FormDefine
extends IMetaItem,
Cloneable {
    public String getFormCode();

    public String getSubTitle();

    public String getDescription();

    public String getSerialNumber();

    public boolean getIsGather();

    public String getMeasureUnit();

    public IMeasureConfig getMeasureConfig();

    public int getSecretLevel();

    public FormType getFormType();

    public String getFormCondition();

    @Deprecated
    public PeriodType getFormPeriodType();

    @Deprecated
    public FormViewType getFormViewType();

    public String getMasterEntitiesKey();

    public String getReadOnlyCondition();

    @Deprecated
    public String getFillingGuide();

    @Deprecated
    public byte[] getBinaryData();

    public String getSurveyData();

    public String getScriptEditor();

    public boolean getQuoteType();

    public boolean isAnalysisForm();

    public boolean getLedgerForm();

    public Object getExtensionProp(String var1);

    public FormDefine clone() throws CloneNotSupportedException;

    public Set<String> getExtensionPronNames();

    public FillInAutomaticallyDue getFillInAutomaticallyDue();

    public String getFormScheme();

    public Map<String, Object> getFormExtension();

    default public String getUpdateUser() {
        return "";
    }
}

