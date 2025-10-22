/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.data.estimation.storage.entity;

import com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Date;
import java.util.List;

public interface IEstimationSchemeTemplate {
    public String getKey();

    public String getCode();

    public String getTitle();

    public Date getUpdateTime();

    public String getCreator();

    public String getOrder();

    public TaskDefine getTaskDefine();

    public FormSchemeDefine getFormSchemeDefine();

    public List<IEstimationForm> getEstimationForms();

    public List<FormulaSchemeDefine> getAccessFormulaSchemes();

    public List<FormulaSchemeDefine> getCalcFormulaSchemes();
}

