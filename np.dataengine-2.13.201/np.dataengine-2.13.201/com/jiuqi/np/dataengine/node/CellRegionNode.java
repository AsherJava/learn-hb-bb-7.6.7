/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.cell.RegionNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.cell.RegionNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.parse.WorkSheet;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.List;

public class CellRegionNode
extends RegionNode {
    private static final long serialVersionUID = -6749965685467666270L;
    private List<IASTNode> childs = new ArrayList<IASTNode>();

    public CellRegionNode(Token token, IWorksheet workSheet, Region region) {
        super(token, workSheet, region);
    }

    public CellRegionNode(Token token, IWorksheet workSheet, Region region, int options) {
        super(token, workSheet, region, options);
    }

    public void parseChilds(QueryContext qContext) throws Exception {
        for (int row = this.region.top(); row <= this.region.bottom(); ++row) {
            for (int col = this.region.left(); col <= this.region.right(); ++col) {
                WorkSheet workSheet = (WorkSheet)this.workSheet;
                DataModelLinkColumn column = qContext.getExeContext().getDataModelLinkFinder().findDataColumn(workSheet.getReportInfo(), row, col, !qContext.getExeContext().isJQReportModel());
                IASTNode child = ExpressionUtils.createNodeByDataModelLinkColumn(qContext, this.token, column, null);
                if (child == null) continue;
                this.childs.add(child);
            }
        }
    }

    public Object evaluate(IContext context) throws SyntaxException {
        if (this.childrenSize() > 0) {
            ArrayList<Object> values = new ArrayList<Object>();
            int dataType = this.childs.get(0).getType(context);
            for (IASTNode child : this.childs) {
                values.add(child.evaluate(context));
            }
            return new ArrayData(dataType, values);
        }
        return null;
    }

    public int childrenSize() {
        return this.childs.size();
    }

    public IASTNode getChild(int index) {
        return this.childs.get(index);
    }
}

