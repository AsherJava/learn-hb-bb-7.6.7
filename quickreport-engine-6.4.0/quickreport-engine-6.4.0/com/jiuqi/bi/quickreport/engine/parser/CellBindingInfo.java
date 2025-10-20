/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.parser.ErrorExpression;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public final class CellBindingInfo {
    private SheetPosition position;
    private CellMap cellMap;
    private IReportExpression value;
    private IReportExpression display;
    private IReportExpression comment;
    private IReportExpression order;
    private OrderMode orderMode;
    private IReportExpression filter;
    private IStyleProcessor styleProcessor;
    private IReportExpression hyperlinkFilter;
    private Format format;
    private String errorMessage;
    private final List<CellBindingInfo> depends = new ArrayList<CellBindingInfo>();
    private final List<CellBindingInfo> affects = new ArrayList<CellBindingInfo>();
    private int toplogicOrder;
    private int _indegree;
    private GridArea ownerArea;
    private boolean traceable;

    public CellBindingInfo(SheetPosition position, CellMap cellMap) {
        this.position = position;
        this.cellMap = cellMap;
    }

    public CellBindingInfo(String sheetName, CellMap cellMap) {
        this.position = new SheetPosition(sheetName, cellMap.getPosition());
        this.cellMap = cellMap;
    }

    public SheetPosition getPosition() {
        return this.position;
    }

    public void setPosition(SheetPosition position) {
        this.position = position;
    }

    public CellMap getCellMap() {
        return this.cellMap;
    }

    public void setCellMap(CellMap cellMap) {
        this.cellMap = cellMap;
    }

    public IReportExpression getValue() {
        return this.value;
    }

    public void setValue(IReportExpression value) {
        this.value = value;
    }

    public IReportExpression getDisplay() {
        return this.display;
    }

    public void setDisplay(IReportExpression display) {
        this.display = display;
    }

    public IReportExpression getComment() {
        return this.comment;
    }

    public void setComment(IReportExpression comment) {
        this.comment = comment;
    }

    public IReportExpression getFilter() {
        return this.filter;
    }

    public void setFilter(IReportExpression filter) {
        this.filter = filter;
    }

    public IReportExpression getOrder() {
        return this.order;
    }

    public void setOrder(IReportExpression order) {
        this.order = order;
    }

    public OrderMode getOrderMode() {
        if (this.orderMode != null) {
            return this.orderMode;
        }
        if (this.getCellMap() != null) {
            return this.getCellMap().getOrderMode();
        }
        return OrderMode.NONE;
    }

    public void setOrderMode(OrderMode orderMode) {
        this.orderMode = orderMode;
    }

    public IReportExpression getHyperlinkFilter() {
        return this.hyperlinkFilter;
    }

    public void setHyperlinkFilter(IReportExpression hyperlinkFilter) {
        this.hyperlinkFilter = hyperlinkFilter;
    }

    public boolean isMaster() {
        return this.getCellMap() != null && this.getCellMap().getExpandMode() != ExpandMode.NONE;
    }

    public boolean isHiddenWhenEmpty() {
        return this.isMaster() && this.getCellMap().isHiddenWhenEmpty();
    }

    @Deprecated
    public boolean isTracing() {
        return this.traceable;
    }

    @Deprecated
    public void setTracing(boolean tracing) {
        this.traceable = tracing;
    }

    public boolean isTraceable() {
        return this.traceable;
    }

    public void setTraceable(boolean traceable) {
        this.traceable = traceable;
    }

    public boolean isInteractive() {
        return this.cellMap != null && (this.cellMap.isRowSortable() || this.cellMap.isRowFilterable());
    }

    public GridArea getOwnerArea() {
        return this.ownerArea;
    }

    public void setOwnerArea(GridArea ownerArea) {
        this.ownerArea = ownerArea;
    }

    public Format getFormat() {
        return this.format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public int getToplogicOrder() {
        return this.toplogicOrder;
    }

    public void setToplogicOrder(int toplogicOrder) {
        this.toplogicOrder = toplogicOrder;
    }

    public List<CellBindingInfo> getAffects() {
        return this.affects;
    }

    public List<CellBindingInfo> getDepends() {
        return this.depends;
    }

    public int _getIndegree() {
        return this._indegree;
    }

    public void _setIndegree(int indegree) {
        this._indegree = indegree;
    }

    public IStyleProcessor getStyleProcessor() {
        return this.styleProcessor;
    }

    public void setStyleProcessor(IStyleProcessor styleProcessor) {
        this.styleProcessor = styleProcessor;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static boolean isIdentable(IReportExpression valueExpression) {
        if (valueExpression == null || valueExpression instanceof ErrorExpression) {
            return false;
        }
        IASTNode rootNode = valueExpression.getRootNode();
        if (!(rootNode instanceof DSFieldNode)) {
            return false;
        }
        DSFieldNode fieldNode = (DSFieldNode)rootNode;
        if (!fieldNode.getRestrictions().isEmpty()) {
            return false;
        }
        for (DSHierarchy hierarchy : fieldNode.getDataSet().getHiers()) {
            if (hierarchy.getType() == DSHierarchyType.COLUMN_HIERARCHY || hierarchy.getLevels().size() != 1 || !((String)hierarchy.getLevels().get(0)).equalsIgnoreCase(fieldNode.getField().getKeyField())) continue;
            return true;
        }
        return false;
    }

    public static void link(CellBindingInfo prev, CellBindingInfo next) {
        prev.getAffects().add(next);
        next.getDepends().add(prev);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.getPosition());
        if (this.getValue() != null) {
            buffer.append('=').append(this.getValue());
        }
        return buffer.toString();
    }
}

