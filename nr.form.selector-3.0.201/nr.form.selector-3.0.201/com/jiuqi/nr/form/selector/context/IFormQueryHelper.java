/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.form.selector.context;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface IFormQueryHelper
extends IFormQueryContext {
    public FormSchemeDefine getFormSchemeDefine();

    public List<FormGroupDefine> queryRootGroupsByFormScheme(String var1);

    public List<FormDefine> queryAllFormDefinesByFormScheme(String var1);

    public List<FormDefine> queryAllFormDefinesByTask(String var1);

    public List<FormDefine> getAllFormsInGroupWithoutOrder(String var1);

    public JtableContext translate2JTableContext(IFormQueryContext var1);

    public boolean isBatchDimValueSet();

    public List<String> getCheckForms();
}

