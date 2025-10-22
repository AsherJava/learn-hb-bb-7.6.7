/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtablePlugServiceDefault;
import com.jiuqi.nr.jtable.service.impl.CheckMonitor;

@Deprecated
public interface IJtableCheckMontiorServiceDefault
extends IJtablePlugServiceDefault {
    public CheckMonitor getMonitor(FormSchemeDefine var1, FormulaSchemeDefine var2, JtableContext var3);
}

