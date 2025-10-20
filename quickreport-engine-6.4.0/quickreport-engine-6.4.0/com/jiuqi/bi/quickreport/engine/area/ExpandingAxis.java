/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegionIterator;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ExpandingAxis
implements Iterable<ExpandingRegion> {
    protected List<ExpandingRegion> expandingRegions = new ArrayList<ExpandingRegion>();
    private AxisDataNode data;

    public List<ExpandingRegion> getExpandingRegions() {
        return this.expandingRegions;
    }

    public Region getRegion() {
        return ExpandingRegion.expandRegions(this.expandingRegions);
    }

    public boolean isEmpty() {
        return this.expandingRegions.isEmpty();
    }

    public boolean isRelated(Region region) {
        for (ExpandingRegion subRegion : this.expandingRegions) {
            if (region.compareTo(subRegion.getRegion()) == 2) continue;
            return true;
        }
        return false;
    }

    public List<ExpandingRegion> findRestrictiveRegions(Position position) {
        ArrayList<ExpandingRegion> regions = new ArrayList<ExpandingRegion>();
        for (ExpandingRegion region : this.expandingRegions) {
            if (region.findRestrictiveRegions(position, regions)) break;
        }
        return regions;
    }

    @Override
    public Iterator<ExpandingRegion> iterator() {
        return new ExpandingRegionIterator(this.expandingRegions);
    }

    protected ExpandingRegion findCrossRegion(Region region) {
        for (ExpandingRegion expandingRegion : this.expandingRegions) {
            ExpandingRegion crossRegion = this.findCrossRegion(expandingRegion, region);
            if (crossRegion == null) continue;
            return crossRegion;
        }
        return null;
    }

    private ExpandingRegion findCrossRegion(ExpandingRegion expandingRegion, Region region) {
        if (expandingRegion.getRegion().compareTo(region) == -2) {
            return expandingRegion;
        }
        for (ExpandingRegion subRegion : expandingRegion.getSubRegions()) {
            ExpandingRegion crossRegion = this.findCrossRegion(subRegion, region);
            if (crossRegion == null) continue;
            return crossRegion;
        }
        return null;
    }

    protected boolean addInit(IContext context, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        ExpandingRegion expandingRegion = new ExpandingRegion(bindingInfo);
        expandingRegion.build(context);
        this.expandingRegions.add(expandingRegion);
        return true;
    }

    protected boolean addNest(IContext context, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        if (this.tryAddInside(context, bindingInfo)) {
            return true;
        }
        return this.tryAddOutside(context, this.expandingRegions, bindingInfo);
    }

    private boolean tryAddInside(IContext context, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        for (ExpandingRegion expandingRegion : this.expandingRegions) {
            if (!this.tryAddInside(context, expandingRegion, bindingInfo)) continue;
            return true;
        }
        return false;
    }

    private boolean tryAddInside(IContext context, ExpandingRegion expandingRegion, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        if (!expandingRegion.getRegion().contains(bindingInfo.getCellMap().getExpandRegion())) {
            return false;
        }
        for (ExpandingRegion subRegion : expandingRegion.getSubRegions()) {
            if (!this.tryAddInside(context, subRegion, bindingInfo)) continue;
            return true;
        }
        if (!this.tryAddOutside(context, expandingRegion.getSubRegions(), bindingInfo)) {
            this.doAddSide(context, expandingRegion.getSubRegions(), bindingInfo);
        }
        return true;
    }

    private boolean tryAddOutside(IContext context, List<ExpandingRegion> subRegions, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        ArrayList<ExpandingRegion> innerRegions = new ArrayList<ExpandingRegion>();
        Iterator<ExpandingRegion> i = subRegions.iterator();
        while (i.hasNext()) {
            ExpandingRegion subRegion = i.next();
            if (!bindingInfo.getCellMap().getExpandRegion().contains(subRegion.getRegion())) continue;
            i.remove();
            innerRegions.add(subRegion);
        }
        if (innerRegions.isEmpty()) {
            return false;
        }
        ExpandingRegion newRegion = new ExpandingRegion(bindingInfo);
        newRegion.build(context);
        newRegion.getSubRegions().addAll(innerRegions);
        subRegions.add(newRegion);
        return true;
    }

    protected boolean addSide(IContext context, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        return this.doAddSide(context, this.expandingRegions, bindingInfo);
    }

    private boolean doAddSide(IContext context, List<ExpandingRegion> regions, CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        ExpandingRegion newRegion = new ExpandingRegion(bindingInfo);
        newRegion.build(context);
        regions.add(newRegion);
        return true;
    }

    public void getRestrictions(CellBindingInfo cellInfo, List<ExpandingRegion> restrictions) {
        for (ExpandingRegion expandingRegion : this.expandingRegions) {
            expandingRegion.fillRestrictions(cellInfo, restrictions);
        }
    }

    public List<IFilterDescriptor> getFilters(IContext context) throws ReportAreaExpcetion {
        IASTNode filter;
        try {
            filter = ExpandingRegion.buildFilters(context, this.expandingRegions);
        }
        catch (ReportContextException e) {
            throw new ReportAreaExpcetion(e);
        }
        if (filter == null) {
            return new ArrayList<IFilterDescriptor>();
        }
        try {
            filter = filter.optimize(context, 3);
        }
        catch (SyntaxException e) {
            throw new ReportAreaExpcetion(e);
        }
        ReportExpression filterExpr = new ReportExpression(filter);
        try {
            return FilterAnalyzer.createFilterDescriptor(context, filterExpr);
        }
        catch (ReportContextException e) {
            throw new ReportAreaExpcetion("\u5206\u6790\u6d6e\u52a8\u533a\u57df[" + this.getRegionDescr() + "]\u7684\u9650\u5b9a\u6761\u4ef6\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private String getRegionDescr() {
        if (this.expandingRegions.isEmpty()) {
            return "ERROR REGION";
        }
        return this.expandingRegions.get(0).getMasterCell().getPosition().getSheetName() + "!" + this.getRegion();
    }

    abstract void sort();

    void validate(ExpandingAxis anotherAxis) throws ReportAreaExpcetion {
        ArrayDeque<ExpandingRegion> route = new ArrayDeque<ExpandingRegion>();
        for (ExpandingRegion region : this.expandingRegions) {
            region.validate(route);
        }
    }

    public AxisDataNode getData() {
        return this.data;
    }

    public void setData(AxisDataNode data) {
        this.data = data;
    }

    public List<DSFieldNode> getPrimaryFields() {
        ArrayList<DSFieldNode> fields = new ArrayList<DSFieldNode>(this.expandingRegions.size());
        for (ExpandingRegion region : this.expandingRegions) {
            if (region.getKeyField() == null) continue;
            fields.add(region.getKeyField());
        }
        return fields;
    }

    public String toString() {
        return this.expandingRegions.isEmpty() ? "(null)" : this.getRegion().toString();
    }
}

