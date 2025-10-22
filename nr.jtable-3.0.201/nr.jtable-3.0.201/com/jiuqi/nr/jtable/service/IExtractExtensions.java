/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import java.util.List;

public interface IExtractExtensions {
    public boolean getEnable(JtableContext var1);

    public FormulaSchemeDefine getSoluctionByDimensions(JtableContext var1);

    public List<ExtractCellInfo> getExtractDataLinkList(JtableContext var1, String var2);
}

