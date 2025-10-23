/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.List;

public interface IProcessRuntimeParamHelper {
    public TaskDefine getTaskDefine(String var1);

    public IEntityDefine getProcessEntityDefinition(String var1);

    public IPeriodEntity getPeriodEntityDefine(String var1);

    public EntityViewDefine buildEntityView(String var1, String var2);

    public EntityViewDefine buildEntityView(String var1, String var2, String var3);

    public FormSchemeDefine getFormScheme(String var1, String var2);

    public List<FormDefine> listFormByFormScheme(String var1, String var2);

    public List<FormDefine> listFormByFormScheme(String var1, String var2, List<String> var3);

    public List<FormGroupDefine> listFormGroupByFormScheme(String var1, String var2);

    public List<IEntityDefine> listAllProcessEntityDefine(String var1, boolean var2);

    public List<FormulaSchemeDefine> listAllFormulaSchemeByFormScheme(String var1, String var2);
}

