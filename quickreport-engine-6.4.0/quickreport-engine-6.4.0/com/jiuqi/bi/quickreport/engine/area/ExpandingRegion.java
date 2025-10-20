/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSCalcField
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.operator.Or
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.util.ASTHelper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNode
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegionIterator;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.MappingFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNode;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.operator.Or;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.util.ASTHelper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class ExpandingRegion
implements Iterable<ExpandingRegion> {
    private final CellBindingInfo masterCell;
    private final Region region;
    private final List<ExpandingRegion> subRegions;
    private DSFieldNode field;
    private DSFieldNode keyField;
    private final List<IFilterDescriptor> selfFilters;
    private final List<IFilterDescriptor> extraFilters;
    private final List<MappingFilterDescriptor> mappingFilters;
    private boolean needRank;
    private CellBindingInfo refIndentCell;
    private int deltaSize = -1;
    private static final String MASTERCELL_PREFIX = "MC_";

    public ExpandingRegion(CellBindingInfo masterCell) {
        this.masterCell = masterCell;
        this.region = masterCell.getCellMap().getExpandRegion();
        this.subRegions = new ArrayList<ExpandingRegion>();
        this.selfFilters = new ArrayList<IFilterDescriptor>();
        this.extraFilters = new ArrayList<IFilterDescriptor>();
        this.mappingFilters = new ArrayList<MappingFilterDescriptor>();
    }

    public void build(IContext context) throws ReportAreaExpcetion {
        this.field = this.bindExpandingField(context);
        this.keyField = this.bindKeyField(context);
        this.scanFilter(context);
    }

    private DSFieldNode bindExpandingField(IContext context) throws ReportAreaExpcetion {
        IASTNode rootNode = this.masterCell.getValue().getRootNode();
        if (rootNode instanceof DSFieldNode && ((DSFieldNode)rootNode).getRestrictions().isEmpty()) {
            return (DSFieldNode)rootNode;
        }
        if (this.masterCell.getValue().isStatic(context)) {
            return null;
        }
        return this.createCalcField(context);
    }

    private DSFieldNode bindKeyField(IContext context) {
        IASTNode rootNode = this.masterCell.getValue().getRootNode();
        if (rootNode instanceof DSFieldNode && ((DSFieldNode)rootNode).getRestrictions().isEmpty()) {
            DSFieldNode fieldNode = (DSFieldNode)rootNode;
            if (StringUtils.isEmpty((String)fieldNode.getField().getKeyField()) || fieldNode.getField().getName().equalsIgnoreCase(fieldNode.getField().getKeyField())) {
                return fieldNode;
            }
            DSField key = fieldNode.getDataSet().findField(fieldNode.getField().getKeyField());
            if (key == null) {
                return fieldNode;
            }
            return new DSFieldNode(null, fieldNode.getDataSet(), key, true);
        }
        return this.field;
    }

    private DSFieldNode createCalcField(IContext context) throws ReportAreaExpcetion {
        int dataType;
        String expr;
        DSModel dsModel = this.findRefDataSet();
        try {
            expr = this.masterCell.getValue().toDSFormula(context, dsModel.getName());
            dataType = this.masterCell.getValue().getDataType(context);
        }
        catch (ReportExpressionException e) {
            throw new ReportAreaExpcetion(e);
        }
        DSCalcField calcField = this.findFieldByExpression(dsModel, expr);
        if (calcField == null) {
            calcField = new DSCalcField();
            calcField.setName(this.newFieldName(dsModel));
            calcField.setNameField(calcField.getName());
            calcField.setKeyField(calcField.getName());
            calcField.setFieldType(FieldType.GENERAL_DIM);
            calcField.setFormula(expr);
            calcField.setValType(dataType == 3 ? 5 : dataType);
            calcField.setAggregation(null);
            calcField.setApplyType(null);
            dsModel.getCalcFields().add(calcField);
            this.updateDSParamRefs(dsModel, this.masterCell.getValue());
        }
        return new DSFieldNode(null, dsModel, (DSField)calcField, true);
    }

    private void updateDSParamRefs(DSModel dsModel, IReportExpression valExpr) {
        for (IASTNode node : valExpr) {
            ParamNode paramNode;
            ParameterModel paramModel;
            if (!(node instanceof ParamNode) || dsModel.findParamemterModel((paramModel = (paramNode = (ParamNode)node).getParam()).getName()) != null) continue;
            dsModel.getParameterModels().add(paramModel);
        }
    }

    private void scanFilter(IContext context) throws ReportAreaExpcetion {
        List<IFilterDescriptor> filters;
        if (this.masterCell.getFilter() == null || this.keyField == null) {
            return;
        }
        try {
            filters = FilterAnalyzer.createFilterDescriptor(context, this.masterCell, this.keyField.getField());
        }
        catch (ReportContextException e) {
            throw new ReportAreaExpcetion(e.getMessage(), e);
        }
        for (IFilterDescriptor filter : filters) {
            if (filter instanceof MappingFilterDescriptor) {
                this.mappingFilters.add((MappingFilterDescriptor)filter);
                continue;
            }
            if (this.keyField.getDataSet().getName().equalsIgnoreCase(filter.getDataSetName()) && this.keyField.getField().getName().equalsIgnoreCase(filter.getFieldName())) {
                this.selfFilters.add(filter);
                continue;
            }
            this.extraFilters.add(filter);
        }
    }

    private DSModel findRefDataSet() throws ReportAreaExpcetion {
        HashSet<DSModel> dataSets = new HashSet<DSModel>();
        for (IASTNode node : this.masterCell.getValue()) {
            if (node instanceof DataSetNode) {
                dataSets.add(((DataSetNode)node).getDataSetModel());
                continue;
            }
            if (!(node instanceof DSFieldNode)) continue;
            dataSets.add(((DSFieldNode)node).getDataSet());
        }
        if (dataSets.isEmpty()) {
            throw new ReportAreaExpcetion("\u4e3b\u63a7\u5355\u5143\u683c[" + this.masterCell.getPosition() + "]\u7684\u53d6\u503c\u516c\u5f0f\u6ca1\u6709\u5173\u8054\u4efb\u4f55\u6570\u636e\u96c6\u5b57\u6bb5\u3002");
        }
        if (dataSets.size() > 1) {
            throw new ReportAreaExpcetion("\u4e3b\u63a7\u5355\u5143\u683c[" + this.masterCell.getPosition() + "]\u7684\u53d6\u503c\u516c\u5f0f\u5173\u8054\u4e86\u591a\u4e2a\u6570\u636e\u96c6\u7684\u5b57\u6bb5\u3002");
        }
        return (DSModel)dataSets.iterator().next();
    }

    private String newFieldName(DSModel dsModel) {
        String prefix;
        String fieldName = prefix = MASTERCELL_PREFIX + this.masterCell.getPosition().getPosition();
        int i = 1;
        while (dsModel.findField(fieldName) != null) {
            fieldName = prefix + "_" + i;
            ++i;
        }
        return fieldName;
    }

    private DSCalcField findFieldByExpression(DSModel dsModel, String expr) {
        for (DSCalcField field : dsModel.getCalcFields()) {
            if (!expr.equals(field.getFormula())) continue;
            return field;
        }
        return null;
    }

    public CellBindingInfo getMasterCell() {
        return this.masterCell;
    }

    public ExpandMode getExpandMode() {
        return this.masterCell.getCellMap().getExpandMode();
    }

    public HierarchyMode getHierarchyMode() {
        return this.masterCell.getCellMap().getHierarchyMode();
    }

    public List<ExpandingRegion> getSubRegions() {
        return this.subRegions;
    }

    public Region expandSubRegion() {
        return ExpandingRegion.expandRegions(this.subRegions);
    }

    public static Region expandRegions(List<ExpandingRegion> regions) {
        if (regions.isEmpty()) {
            return null;
        }
        if (regions.size() == 1) {
            return regions.get((int)0).region;
        }
        Region firstRegion = regions.get((int)0).region;
        int left = firstRegion.left();
        int right = firstRegion.right();
        int top = firstRegion.top();
        int bottom = firstRegion.bottom();
        for (int i = 1; i < regions.size(); ++i) {
            Region subRegion = regions.get((int)i).region;
            left = Math.min(left, subRegion.left());
            right = Math.max(right, subRegion.right());
            top = Math.min(top, subRegion.top());
            bottom = Math.max(bottom, subRegion.bottom());
        }
        return new Region(left, top, right, bottom);
    }

    public Region getRegion() {
        return this.region;
    }

    public Position getMasterCellPosition() {
        return this.masterCell.getCellMap().getPosition();
    }

    public DSFieldNode getField() {
        return this.field;
    }

    public DSFieldNode getKeyField() {
        return this.keyField;
    }

    public DSModel getDataSet() {
        return this.keyField == null ? null : this.keyField.getDataSet();
    }

    public void appendNexFilters(Collection<IFilterDescriptor> nextFilters) {
        nextFilters.addAll(this.extraFilters);
        nextFilters.addAll(this.mappingFilters);
    }

    public int getTopN() {
        if (this.masterCell != null) {
            return this.masterCell.getCellMap().getTopN();
        }
        return 0;
    }

    public boolean needRank() {
        return this.needRank || this.masterCell != null && this.masterCell.getCellMap().getTopN() > 0;
    }

    public boolean isDenseRank() {
        return this.masterCell != null && this.masterCell.getCellMap().isDenseRank();
    }

    public boolean isStatic() {
        return this.field == null || this.keyField == null;
    }

    public void setNeedRank(boolean needRank) {
        this.needRank = needRank;
    }

    public CellBindingInfo getRefIndentCell() {
        return this.refIndentCell;
    }

    public void setRefIndentCell(CellBindingInfo refIdentCell) {
        this.refIndentCell = refIdentCell;
    }

    public boolean contains(Position pos) {
        return this.getRegion().contains(pos);
    }

    public void fillRestrictions(CellBindingInfo cellInfo, List<ExpandingRegion> restrictions) {
        if (!this.contains(cellInfo.getPosition().getPosition())) {
            return;
        }
        if (this.field != null) {
            restrictions.add(this);
        }
        for (ExpandingRegion subRegion : this.subRegions) {
            subRegion.fillRestrictions(cellInfo, restrictions);
        }
        if ((this.masterCell.getCellMap().getHierarchyMode() == HierarchyMode.INDENTED || this.masterCell.getCellMap().getHierarchyMode() == HierarchyMode.TIERED) && this.isNameFieldCell(cellInfo)) {
            this.refIndentCell = cellInfo;
        }
    }

    private boolean isNameFieldCell(CellBindingInfo cellInfo) {
        if (cellInfo.getValue() == null || this.field == null) {
            return false;
        }
        IASTNode rootNode = cellInfo.getValue().getRootNode();
        if (!(rootNode instanceof DSFieldNode)) {
            return false;
        }
        DSFieldNode fieldNode = (DSFieldNode)rootNode;
        if (!fieldNode.getRestrictions().isEmpty()) {
            return false;
        }
        return this.field.getField().getNameField() != null && this.field.getField().getNameField().equalsIgnoreCase(fieldNode.getField().getName());
    }

    public void sort(Comparator<ExpandingRegion> comparator) {
        Collections.sort(this.subRegions, comparator);
        for (ExpandingRegion region : this.subRegions) {
            region.sort(comparator);
        }
    }

    public IASTNode buildFilters(IContext context) throws ReportContextException {
        String curFilterExpr;
        IASTNode subFilters = ExpandingRegion.buildFilters(context, this.subRegions);
        IASTNode curFilter = this.buildCurrentFilters(context);
        if (subFilters == null) {
            return curFilter;
        }
        if (curFilter == null) {
            return subFilters;
        }
        String subFilterExpr = ASTHelper.toString((IContext)context, (IASTNode)subFilters);
        if (subFilterExpr.equals(curFilterExpr = ASTHelper.toString((IContext)context, (IASTNode)curFilter))) {
            return subFilters;
        }
        return ASTHelper.and((IASTNode[])new IASTNode[]{curFilter, subFilters});
    }

    private IASTNode buildCurrentFilters(IContext context) throws ReportContextException {
        IASTNode curFilter;
        IASTNode filter = null;
        for (IFilterDescriptor selfFilter : this.selfFilters) {
            curFilter = selfFilter.toASTFilter(context);
            if (filter == null) {
                filter = curFilter;
                continue;
            }
            filter = ASTHelper.and((IASTNode[])new IASTNode[]{filter, curFilter});
        }
        for (IFilterDescriptor extraFilter : this.extraFilters) {
            curFilter = extraFilter.toASTFilter(context);
            if (filter == null) {
                filter = curFilter;
                continue;
            }
            filter = ASTHelper.and((IASTNode[])new IASTNode[]{filter, curFilter});
        }
        return filter;
    }

    static IASTNode buildFilters(IContext context, List<ExpandingRegion> regions) throws ReportContextException {
        IASTNode filter = null;
        HashSet<String> exprs = new HashSet<String>();
        for (ExpandingRegion region : regions) {
            String expr;
            IASTNode subFilter = region.buildFilters(context);
            if (subFilter == null || !exprs.add(expr = ASTHelper.toString((IContext)context, (IASTNode)subFilter))) continue;
            if (filter == null) {
                filter = subFilter;
                continue;
            }
            filter = new Or(null, filter, subFilter);
        }
        return filter;
    }

    public String toString() {
        return this.masterCell.getPosition() + "@" + this.getRegion();
    }

    @Override
    public Iterator<ExpandingRegion> iterator() {
        return new ExpandingRegionIterator(this);
    }

    void validate(Deque<ExpandingRegion> route) throws ReportAreaExpcetion {
        if (this.getMasterCell().getFilter() != null) {
            this.validateFilter(route);
        }
        for (ExpandingRegion subRegion : this.subRegions) {
            route.push(this);
            subRegion.validate(route);
            route.pop();
        }
    }

    private void validateFilter(Deque<ExpandingRegion> route) throws ReportAreaExpcetion {
        if (this.isStatic()) {
            throw new ReportAreaExpcetion("\u5355\u5143\u683c" + this.getMasterCell().getPosition() + "\u53ea\u5f15\u7528\u4e86\u5e38\u91cf\u6216\u53c2\u6570\u503c\uff0c\u672a\u7ed1\u5b9a\u6570\u636e\u96c6\u5b57\u6bb5\u7684\u4e3b\u63a7\u5355\u5143\u683c\u4e0d\u5141\u8bb8\u8bbe\u7f6e\u4efb\u4f55\u8fc7\u6ee4\u6761\u4ef6\u3002");
        }
        if (!this.mappingFilters.isEmpty()) {
            HashSet<String> joinedDSNames = new HashSet<String>();
            for (MappingFilterDescriptor mappingFilter : this.mappingFilters) {
                if (!this.contains(route, mappingFilter.getMappingField().dataSetName)) {
                    throw new ReportAreaExpcetion("\u5355\u5143\u683c" + this.getMasterCell().getPosition() + "\u5173\u8054\u7684\u6570\u636e\u96c6" + mappingFilter.getMappingField().dataSetName + "\u5728\u4e0a\u7ea7\u7684\u6d6e\u52a8\u884c\uff08\u6216\u680f\uff09\u4e2d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u8fdb\u884c\u5173\u8054\u3002");
                }
                joinedDSNames.add(mappingFilter.getMappingField().dataSetName);
            }
            ExpandingRegion lastRegion = null;
            Iterator<ExpandingRegion> i = route.descendingIterator();
            while (i.hasNext()) {
                ExpandingRegion curRegion = i.next();
                if (curRegion.isStatic()) continue;
                lastRegion = curRegion;
                break;
            }
            if (lastRegion != null && lastRegion.getKeyField().getDataSet() != this.keyField.getDataSet() && !joinedDSNames.contains(lastRegion.getKeyField().getDataSet().getName())) {
                throw new ReportAreaExpcetion("\u4e3b\u63a7\u5355\u5143\u683c" + lastRegion.getMasterCell().getPosition() + "\u4e0e" + this.getMasterCell().getPosition() + "\u6765\u6e90\u4e8e\u4e0d\u540c\u7684\u6570\u636e\u96c6\uff0c\u4f46\u6ca1\u6709\u6307\u5b9a\u5173\u8054\u5173\u7cfb\u3002");
            }
        }
    }

    private boolean contains(Deque<ExpandingRegion> route, String dataSetName) {
        for (ExpandingRegion region : route) {
            if (region.getKeyField() == null || !region.getKeyField().getDataSet().getName().equalsIgnoreCase(dataSetName)) continue;
            return true;
        }
        return false;
    }

    public boolean findRestrictiveRegions(Position position, List<ExpandingRegion> regions) {
        if (!this.contains(position)) {
            return false;
        }
        regions.add(this);
        for (ExpandingRegion subRegion : this.subRegions) {
            if (subRegion.findRestrictiveRegions(position, regions)) break;
        }
        return true;
    }

    public int getDeltaSize() {
        if (this.deltaSize >= 0) {
            return this.deltaSize;
        }
        switch (this.masterCell.getCellMap().getExpandMode()) {
            case ROWEXPANDING: {
                this.deltaSize = this.region.rowSize() - this.subRegions.stream().mapToInt(r -> r.getRegion().rowSize()).sum();
                break;
            }
            case COLEXPANDING: {
                this.deltaSize = this.region.colSize() - this.subRegions.stream().mapToInt(r -> r.getRegion().colSize()).sum();
                break;
            }
            default: {
                this.deltaSize = 0;
            }
        }
        return this.deltaSize;
    }
}

