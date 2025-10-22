/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.cell.RegionNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.dafafill.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.cell.RegionNode;
import com.jiuqi.bi.syntax.parser.IContext;

public class DatafillRegionNode
extends RegionNode {
    private static final long serialVersionUID = 1L;

    public DatafillRegionNode(Token token, IWorksheet workSheet, Region region) {
        super(token, workSheet, region);
    }

    public Object evaluate(IContext var1) throws SyntaxException {
        throw new CellExcpetion("\u4e0d\u652f\u6301\u533a\u57df\u6a21\u5f0f\u3002");
    }
}

