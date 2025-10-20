/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.collection.ArrayMap
 */
package com.jiuqi.bi.quickreport.engine.interaction;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.engine.IReportInteraction;
import com.jiuqi.bi.quickreport.engine.ReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.interaction.FilterQueryListener;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteractionException;
import com.jiuqi.bi.quickreport.engine.interaction.SortingCell;
import com.jiuqi.bi.quickreport.engine.result.CellFilterInfo;
import com.jiuqi.bi.quickreport.engine.result.CellSortInfo;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReportInteraction
implements IReportInteraction {
    private final ReportEngine engine;
    private List<InteractiveInfo> interactiveInfos;
    private Map<Position, SortingCell> cellSortInfos;
    private Map<CellFilterInfo, Object> cellFilterInfos;

    public ReportInteraction(ReportEngine engine) {
        this.engine = engine;
        this.interactiveInfos = new ArrayList<InteractiveInfo>();
        this.cellSortInfos = new ArrayMap();
        this.cellFilterInfos = new ArrayMap();
    }

    public List<InteractiveInfo> getInteractiveInfos() {
        return this.interactiveInfos;
    }

    @Override
    public void reset() throws ReportInteractionException {
        this.cellSortInfos.clear();
        this.cellFilterInfos.clear();
        this.doChange();
    }

    @Override
    public void orderBy(InteractiveInfo orderInfo, OrderMode mode) throws ReportInteractionException {
        Iterator<SortingCell> i = this.cellSortInfos.values().iterator();
        while (i.hasNext()) {
            SortingCell sortingCell = i.next();
            if (!sortingCell.groupName.equals(orderInfo.getGroupName())) continue;
            i.remove();
        }
        if (mode != null) {
            for (CellSortInfo sortInfo : orderInfo.getSortInfos()) {
                SortingCell sortingCell = new SortingCell(mode, sortInfo.getExpression(), orderInfo.getID(), orderInfo.getGroupName());
                this.cellSortInfos.put(sortInfo.getPosition(), sortingCell);
            }
        }
        this.doChange();
    }

    @Override
    public void setSortMode(int id, OrderMode mode) throws ReportInteractionException {
        if (this.interactiveInfos.isEmpty()) {
            throw new ReportInteractionException("\u5f53\u524d\u4f1a\u8bdd\u9519\u8bef\uff0c\u6ca1\u6709\u627e\u5230\u6392\u5e8f\u4ea4\u4e92\u4fe1\u606f\uff0c\u53ef\u80fd\u662f\u56e0\u4e3a\u8c03\u7528\u4e86\u5e9f\u5f03\u7684\u63a5\u53e3\u65b9\u6cd5");
        }
        InteractiveInfo info = this.interactiveInfos.get(id);
        this.orderBy(info, mode);
    }

    @Override
    public OrderMode changeSortMode(int id) throws ReportInteractionException {
        InteractiveInfo info = this.interactiveInfos.get(id);
        OrderMode rawMode = this.getSortMode(id);
        OrderMode newMode = rawMode == null || rawMode == OrderMode.DESC ? OrderMode.ASC : OrderMode.DESC;
        this.orderBy(info, newMode);
        return newMode;
    }

    @Override
    public OrderMode getSortMode(int id) {
        for (SortingCell info : this.cellSortInfos.values()) {
            if (id != info.srcID) continue;
            return info.mode;
        }
        return OrderMode.NONE;
    }

    @Override
    public void filterBy(InteractiveInfo filterInfo, List<Object> values) throws ReportInteractionException {
        if (!filterInfo.isFilterable()) {
            throw new ReportInteractionException("\u65e0\u6cd5\u9650\u5b9a\u5f53\u524d\u5217\u7684\u8fc7\u6ee4\u503c");
        }
        if (values == null || values.isEmpty()) {
            this.cellFilterInfos.remove(filterInfo.getFilterInfo());
        } else {
            this.cellFilterInfos.put(filterInfo.getFilterInfo(), values);
        }
        this.doChange();
    }

    @Override
    public void filterBy(InteractiveInfo filterInfo, String expression) throws ReportInteractionException {
        if (!filterInfo.isFilterable()) {
            throw new ReportInteractionException("\u65e0\u6cd5\u9650\u5b9a\u5f53\u524d\u5217\u7684\u8fc7\u6ee4\u6761\u4ef6");
        }
        if (expression == null || expression.isEmpty() || "1=1".equals(expression)) {
            this.cellFilterInfos.remove(filterInfo.getFilterInfo());
        } else {
            this.cellFilterInfos.put(filterInfo.getFilterInfo(), expression);
        }
        this.doChange();
    }

    @Override
    public MemoryDataSet<Object> queryFilterItems(InteractiveInfo filterInfo) throws ReportInteractionException {
        if (!filterInfo.isFilterable()) {
            throw new ReportInteractionException("\u5f53\u524d\u5355\u5143\u683c\u672a\u542f\u7528\u6570\u636e\u8fc7\u6ee4");
        }
        if (filterInfo.getFilterInfo().isMeasure()) {
            throw new ReportInteractionException("\u5ea6\u91cf\u578b\u5355\u5143\u683c\u65e0\u6cd5\u83b7\u53d6\u8fc7\u6ee4\u6761\u76ee");
        }
        Object currentFilter = this.cellFilterInfos.remove(filterInfo.getFilterInfo());
        FilterQueryListener listener = new FilterQueryListener(filterInfo.getFilterInfo());
        try {
            this.engine.execute(listener);
        }
        catch (ReportEngineException e) {
            throw new ReportInteractionException(e);
        }
        finally {
            if (currentFilter != null) {
                this.cellFilterInfos.put(filterInfo.getFilterInfo(), currentFilter);
            }
        }
        return listener.getResult();
    }

    public Map<Position, SortingCell> getCellSortInfos() {
        return this.cellSortInfos;
    }

    public Map<CellFilterInfo, Object> getCellFilterInfos() {
        return this.cellFilterInfos;
    }

    private void doChange() throws ReportInteractionException {
        this.engine.flush();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        boolean started = false;
        for (Map.Entry<Position, SortingCell> e : this.cellSortInfos.entrySet()) {
            if (started) {
                buffer.append(", ");
            } else {
                started = true;
            }
            buffer.append(e.getKey()).append('=').append(e.getValue());
        }
        buffer.append(']');
        return buffer.toString();
    }
}

