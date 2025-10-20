/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.ICellNode
 *  com.jiuqi.bi.syntax.cell.IRegionNode
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.writeback.workbook;

import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackCellNode;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackRegionNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.ICellNode;
import com.jiuqi.bi.syntax.cell.IRegionNode;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class WritebackWorksheet
implements IWorksheet {
    private IWorksheet worksheet;

    public WritebackWorksheet(IWorksheet worksheet) {
        this.worksheet = worksheet;
    }

    public String name() {
        return this.worksheet.name();
    }

    public IASTNode findCell(IContext context, Token token, Position pos, int cellOptions) throws CellExcpetion {
        ICellNode cellNode = (ICellNode)this.worksheet.findCell(context, token, pos, cellOptions);
        return cellNode == null ? null : new WritebackCellNode(cellNode, this.worksheet, cellOptions);
    }

    public IASTNode findCell(IContext context, Token token, CompatiblePosition pos, int cellOptions, List<IASTNode> restrictions) throws CellExcpetion {
        throw new CellExcpetion(token, "\u4e0d\u652f\u6301\u517c\u5bb9\u7684\u62a5\u8868\u8bed\u6cd5\u3002");
    }

    public IASTNode findRegion(IContext context, Token token, Region region, int cellOptions) throws CellExcpetion {
        IRegionNode regionNode = (IRegionNode)this.worksheet.findRegion(context, token, region, cellOptions);
        return regionNode == null ? null : new WritebackRegionNode(regionNode, this.worksheet, cellOptions);
    }
}

