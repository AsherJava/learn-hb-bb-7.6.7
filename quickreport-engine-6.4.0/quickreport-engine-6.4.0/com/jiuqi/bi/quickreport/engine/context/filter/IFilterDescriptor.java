/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.context.filter;

import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.Collection;

public interface IFilterDescriptor
extends Comparable<IFilterDescriptor>,
Cloneable {
    public String getDataSetName();

    public String getFieldName();

    public DSField getField();

    public FilterItem toFilter() throws ReportContextException;

    public IASTNode toASTFilter(IContext var1) throws ReportContextException;

    public void getRefFields(Collection<String> var1);

    public Object clone();

    public IFilterDescriptor mapTo(String var1, DSField var2);
}

