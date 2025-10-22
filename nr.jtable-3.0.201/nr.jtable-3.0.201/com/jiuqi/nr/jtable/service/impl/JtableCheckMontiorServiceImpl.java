/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableCheckMontiorServiceDefault;
import com.jiuqi.nr.jtable.service.impl.CheckMonitor;

@Deprecated
public class JtableCheckMontiorServiceImpl
implements IJtableCheckMontiorServiceDefault {
    @Override
    public CheckMonitor getMonitor(FormSchemeDefine formScheme, FormulaSchemeDefine formulaSchemeDefine, JtableContext jtableContext) {
        return new CheckMonitor(formScheme, formulaSchemeDefine);
    }
}

