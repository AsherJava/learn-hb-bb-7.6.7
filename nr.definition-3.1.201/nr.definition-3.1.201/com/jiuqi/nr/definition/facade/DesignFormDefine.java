/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.log.ComparePropertyAble;
import java.util.Date;

public interface DesignFormDefine
extends FormDefine,
ComparePropertyAble {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setFormCode(String var1);

    public void setSubTitle(String var1);

    public void setDescription(String var1);

    public void setSerialNumber(String var1);

    public void setIsGather(boolean var1);

    public void setMeasureUnit(String var1);

    public void setSecretLevel(int var1);

    public void setFormType(FormType var1);

    public void setFormCondition(String var1);

    @Deprecated
    public void setFormPeriodType(PeriodType var1);

    public void setFillingGuide(String var1);

    @Deprecated
    public void setFormViewType(FormViewType var1);

    @Deprecated
    public void setBinaryData(byte[] var1);

    public void setMasterEntitiesKey(String var1);

    public void setReadOnlyCondition(String var1);

    public void setSurveyData(String var1);

    public void setScriptEditor(String var1);

    public void setQuoteType(boolean var1);

    public void setAnalysisForm(boolean var1);

    public void setLedgerForm(boolean var1);

    public void addExtensions(String var1, Object var2);

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue var1);

    public void setFormScheme(String var1);

    default public void setUpdateUser(String updateUser) {
    }
}

