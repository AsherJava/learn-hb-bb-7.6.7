/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import java.util.List;

public interface IExtractFormulaService {
    public List<String> getExtractDataLinkList(JtableContext var1);

    public List<ExtractCellInfo> getExtractDataLinkList(JtableContext var1, String var2);

    public FormulaSchemeDefine getSoluctionByDimensions(JtableContext var1);

    public List<FormulaDefine> getEFDCFormulaInfo(JtableContext var1, String var2);
}

