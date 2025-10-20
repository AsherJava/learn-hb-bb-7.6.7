/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.ICellNode
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.area.ColAxis;
import com.jiuqi.bi.quickreport.engine.area.ExpandingAxis;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.area.RestrictedCell;
import com.jiuqi.bi.quickreport.engine.area.RowAxis;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_ExpandingFunction;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_RankOf;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.ICellNode;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ExpandingArea
extends GridArea
implements Iterable<ExpandingRegion> {
    private ColAxis colAxis = new ColAxis();
    private RowAxis rowAxis = new RowAxis();
    private List<RestrictedCell> restrictedCells = new ArrayList<RestrictedCell>();
    private Map<CellBindingInfo, RestrictedCell> restrictionMap;

    public ExpandingAxis getColAxis() {
        return this.colAxis;
    }

    public ExpandingAxis getRowAxis() {
        return this.rowAxis;
    }

    public List<RestrictedCell> getRestrictedCells() {
        return this.restrictedCells;
    }

    public Map<CellBindingInfo, RestrictedCell> getRestrictionMap() {
        if (this.restrictionMap == null) {
            this.restrictionMap = new HashMap<CellBindingInfo, RestrictedCell>();
            for (RestrictedCell cell : this.restrictedCells) {
                this.restrictionMap.put(cell.getCell(), cell);
            }
        }
        return this.restrictionMap;
    }

    public boolean tryAddMaster(IContext context, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        boolean added = false;
        switch (bindingInfo.getCellMap().getExpandMode()) {
            case COLEXPANDING: {
                added = this.colAxis.add(context, bindingInfo, this.rowAxis);
                break;
            }
            case ROWEXPANDING: {
                added = this.rowAxis.add(context, bindingInfo, this.colAxis);
                break;
            }
            default: {
                throw new ReportAreaExpcetion(bindingInfo.getPosition().toString() + "\u4e0d\u662f\u4e3b\u63a7\u5355\u5143\u683c\u3002");
            }
        }
        if (added) {
            this.cells.add(bindingInfo);
        }
        return added;
    }

    public boolean tryAddCell(CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        ArrayList<ExpandingRegion> rowRestrictions = new ArrayList<ExpandingRegion>();
        ArrayList<ExpandingRegion> colRestrictions = new ArrayList<ExpandingRegion>();
        this.rowAxis.getRestrictions(bindingInfo, rowRestrictions);
        this.colAxis.getRestrictions(bindingInfo, colRestrictions);
        if (rowRestrictions.isEmpty() && colRestrictions.isEmpty()) {
            return false;
        }
        RestrictedCell restrictedCell = new RestrictedCell();
        restrictedCell.setCell(bindingInfo);
        restrictedCell.getRowRestrictions().addAll(rowRestrictions);
        restrictedCell.getColRestrictions().addAll(colRestrictions);
        this.restrictedCells.add(restrictedCell);
        this.cells.add(bindingInfo);
        return true;
    }

    void buildMasterRestrictions() {
        ArrayDeque<ExpandingRegion> path;
        for (ExpandingRegion expandingRegion : this.rowAxis.getExpandingRegions()) {
            path = new ArrayDeque<ExpandingRegion>();
            this.buildRestriction(expandingRegion, path);
        }
        for (ExpandingRegion expandingRegion : this.colAxis.getExpandingRegions()) {
            path = new ArrayDeque();
            this.buildRestriction(expandingRegion, path);
        }
    }

    public Map<CellBindingInfo, ExpandingRegion> buildRegionMaps() {
        HashMap<CellBindingInfo, ExpandingRegion> maps = new HashMap<CellBindingInfo, ExpandingRegion>();
        for (ExpandingRegion region : this) {
            maps.put(region.getMasterCell(), region);
        }
        return maps;
    }

    private void buildRestriction(ExpandingRegion expandingRegion, Deque<ExpandingRegion> path) {
        path.push(expandingRegion);
        RestrictedCell restrictedCell = new RestrictedCell();
        restrictedCell.setCell(expandingRegion.getMasterCell());
        switch (expandingRegion.getMasterCell().getCellMap().getExpandMode()) {
            case COLEXPANDING: {
                path.descendingIterator().forEachRemaining(r -> restrictedCell.getColRestrictions().add((ExpandingRegion)r));
                break;
            }
            case ROWEXPANDING: {
                path.descendingIterator().forEachRemaining(r -> restrictedCell.getRowRestrictions().add((ExpandingRegion)r));
                break;
            }
        }
        this.restrictedCells.add(restrictedCell);
        for (ExpandingRegion subRegion : expandingRegion.getSubRegions()) {
            this.buildRestriction(subRegion, path);
        }
        path.pop();
    }

    @Override
    public void validate() throws ReportAreaExpcetion {
        this.colAxis.sort();
        this.rowAxis.sort();
        this.colAxis.validate(this.rowAxis);
        this.rowAxis.validate(this.colAxis);
        this.checkRank();
    }

    private void checkRank() throws ReportAreaExpcetion {
        HashSet<IStyleProcessor> styleProcessors = new HashSet<IStyleProcessor>();
        for (RestrictedCell cell : this.restrictedCells) {
            CellBindingInfo cellInfo = cell.getCell();
            if (cellInfo.getValue() != null) {
                try {
                    this.scanFunctions(cellInfo.getValue());
                }
                catch (ReportAreaExpcetion e) {
                    throw new ReportAreaExpcetion(cellInfo.getPosition() + "\u5355\u5143\u683c\u503c\u8868\u8fbe\u5f0f\u4e2d\u6392\u540d\u51fd\u6570\u4f7f\u7528\u9519\u8bef\uff0c" + e.getMessage(), e);
                }
            }
            if (cellInfo.getDisplay() != null) {
                try {
                    this.scanFunctions(cellInfo.getDisplay());
                }
                catch (ReportAreaExpcetion e) {
                    throw new ReportAreaExpcetion(cellInfo.getPosition() + "\u5355\u5143\u683c\u663e\u793a\u8868\u8fbe\u5f0f\u4e2d\u6392\u540d\u51fd\u6570\u4f7f\u7528\u9519\u8bef\uff0c" + e.getMessage(), e);
                }
            }
            if (cellInfo.getStyleProcessor() == null || styleProcessors.contains(cellInfo.getStyleProcessor())) continue;
            try {
                this.scanStyles(cellInfo.getStyleProcessor());
            }
            catch (ReportAreaExpcetion e) {
                throw new ReportAreaExpcetion(cellInfo.getPosition() + "\u5355\u5143\u683c\u6761\u4ef6\u8868\u8fbe\u5f0f\u4e2d\u6392\u540d\u51fd\u6570\u4f7f\u7528\u9519\u8bef\uff0c" + e.getMessage(), e);
            }
            styleProcessors.add(cellInfo.getStyleProcessor());
        }
    }

    private void scanStyles(IStyleProcessor styleProcessor) throws ReportAreaExpcetion {
        while (styleProcessor != null) {
            for (IReportExpression condition : styleProcessor.getConditions()) {
                this.scanFunctions(condition);
            }
            styleProcessor = styleProcessor.next();
        }
    }

    private void scanFunctions(IReportExpression expression) throws ReportAreaExpcetion {
        for (IASTNode node : expression) {
            if (!FunctionNode.isFunction((IASTNode)node, (Class[])new Class[]{Q_ExpandingFunction.class})) continue;
            this.setExpandingFunction((FunctionNode)node);
        }
    }

    private void setExpandingFunction(FunctionNode node) throws ReportAreaExpcetion {
        ExpandingRegion masterRegion;
        Q_ExpandingFunction function = (Q_ExpandingFunction)node.getDefine();
        if (function.getMasterCell() != null) {
            return;
        }
        IASTNode dimItem = node.getChild(0);
        if (dimItem instanceof ICellNode) {
            masterRegion = this.locateRankCell((ICellNode)dimItem);
        } else if (dimItem instanceof DSFieldNode) {
            masterRegion = this.locateRankCell((DSFieldNode)dimItem);
        } else {
            throw new ReportAreaExpcetion("\u53c2\u6570[" + dimItem.toString() + "]\u7c7b\u578b\u9519\u8bef\u3002");
        }
        CellBindingInfo masterCell = masterRegion.getMasterCell();
        if (function instanceof Q_RankOf) {
            if (masterCell.getOrderMode() == OrderMode.NONE) {
                if (dimItem instanceof ICellNode) {
                    throw new ReportAreaExpcetion("\u6392\u540d\u5355\u5143\u683c" + dimItem.toString() + "\u6ca1\u6709\u8fdb\u884c\u6392\u5e8f\u8bbe\u7f6e\u3002");
                }
                throw new ReportAreaExpcetion("\u6392\u540d\u5b57\u6bb5" + dimItem.toString() + "\u5bf9\u5e94\u7684\u4e3b\u63a7\u5355\u5143\u683c" + masterCell.getCellMap().getPosition().toString() + "\u6ca1\u6709\u8fdb\u884c\u6392\u5e8f\u8bbe\u7f6e\u3002");
            }
            masterRegion.setNeedRank(true);
        }
        function.setMasterCell(masterCell);
    }

    private ExpandingRegion locateRankCell(ICellNode cellNode) throws ReportAreaExpcetion {
        if (!cellNode.sheetName().equalsIgnoreCase(this.getSheetName())) {
            throw new ReportAreaExpcetion("\u6392\u540d\u5355\u5143\u683c" + cellNode.toString() + "\u4e0d\u662f\u5f53\u524d\u6d6e\u52a8\u533a\u57df\u7684\u4e3b\u63a7\u5355\u5143\u683c\u3002");
        }
        for (ExpandingRegion region : this) {
            if (!cellNode.getCellPosition().equals((Object)region.getMasterCellPosition())) continue;
            return region;
        }
        throw new ReportAreaExpcetion("\u6392\u540d\u5355\u5143\u683c" + cellNode.toString() + "\u4e0d\u662f\u5f53\u524d\u6d6e\u52a8\u533a\u57df\u7684\u4e3b\u63a7\u5355\u5143\u683c\u3002");
    }

    private ExpandingRegion locateRankCell(DSFieldNode dimItem) throws ReportAreaExpcetion {
        for (ExpandingRegion region : this) {
            if (region.isStatic() || dimItem.getField() != region.getField().getField() && dimItem.getField() != region.getKeyField().getField()) continue;
            return region;
        }
        throw new ReportAreaExpcetion("\u6392\u540d\u5b57\u6bb5" + dimItem.toString() + "\u4e0d\u662f\u5f53\u524d\u6d6e\u52a8\u533a\u57df\u7684\u4e3b\u63a7\u5b57\u6bb5\u3002");
    }

    public Region getRegion() {
        if (this.colAxis.isEmpty()) {
            return this.rowAxis.getRegion();
        }
        if (this.rowAxis.isEmpty()) {
            return this.colAxis.getRegion();
        }
        Region colRegion = this.colAxis.getRegion();
        Region rowRegion = this.rowAxis.getRegion();
        return new Region(rowRegion.left(), colRegion.top(), Math.max(colRegion.right(), rowRegion.right()), Math.max(colRegion.bottom(), rowRegion.bottom()));
    }

    public Region getMiniRegion() {
        if (this.colAxis.isEmpty()) {
            return this.rowAxis.getRegion();
        }
        if (this.rowAxis.isEmpty()) {
            return this.colAxis.getRegion();
        }
        Region colRegion = this.colAxis.getRegion();
        Region rowRegion = this.rowAxis.getRegion();
        return new Region(rowRegion.left(), colRegion.top(), colRegion.right(), rowRegion.bottom());
    }

    public List<DSFieldNode> getPrimaryFields() {
        if (!this.rowAxis.isEmpty()) {
            return this.rowAxis.getPrimaryFields();
        }
        if (!this.colAxis.isEmpty()) {
            return this.colAxis.getPrimaryFields();
        }
        return Collections.emptyList();
    }

    @Override
    public Iterator<ExpandingRegion> iterator() {
        return new Itr();
    }

    public String toString() {
        return this.getSheetName() + ":" + this.getRegion() + "->" + this.getCells();
    }

    private final class Itr
    implements Iterator<ExpandingRegion> {
        private Deque<Iterator<ExpandingRegion>> itrs = new ArrayDeque<Iterator<ExpandingRegion>>();

        public Itr() {
            if (!ExpandingArea.this.rowAxis.isEmpty()) {
                this.itrs.push(ExpandingArea.this.rowAxis.iterator());
            }
            if (!ExpandingArea.this.colAxis.isEmpty()) {
                this.itrs.push(ExpandingArea.this.colAxis.iterator());
            }
        }

        @Override
        public boolean hasNext() {
            if (this.itrs.isEmpty()) {
                return false;
            }
            Iterator<ExpandingRegion> i = this.itrs.peek();
            if (i.hasNext()) {
                return true;
            }
            this.itrs.pop();
            return this.hasNext();
        }

        @Override
        public ExpandingRegion next() {
            Iterator<ExpandingRegion> i = this.itrs.peek();
            return i.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

