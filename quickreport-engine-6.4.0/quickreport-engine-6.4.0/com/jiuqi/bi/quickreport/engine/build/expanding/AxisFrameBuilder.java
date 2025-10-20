/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeException
 *  com.jiuqi.bi.util.tree.TreeNode
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.quickreport.engine.area.ExpandingAxis;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataComparator;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AxisFrameBuilder {
    private ReportContext context;
    private ExpandingAxis axis;
    private ExpandingAxis otherAxis;
    private Map<ExpandingRegion, RegionBuildInfo> buildInfos = new HashMap<ExpandingRegion, RegionBuildInfo>();
    private boolean expandingNulls;

    public boolean isExpandingNulls() {
        return this.expandingNulls;
    }

    public void setExpandingNulls(boolean expandingNulls) {
        this.expandingNulls = expandingNulls;
    }

    public AxisDataNode build() throws ReportBuildException {
        List<IFilterDescriptor> globalFilters;
        try {
            globalFilters = this.otherAxis.getFilters(this.context);
        }
        catch (ReportAreaExpcetion e) {
            throw new ReportBuildException(e);
        }
        AxisDataNode rootNode = new AxisDataNode(null);
        this.pushFilters(globalFilters);
        this.expandNode(rootNode, this.axis.getExpandingRegions());
        this.popFilters(globalFilters);
        return rootNode;
    }

    private void expandNode(AxisDataNode node, List<ExpandingRegion> regions) throws ReportBuildException {
        for (ExpandingRegion expandingRegion : regions) {
            List<AxisDataNode> datas = expandingRegion.isStatic() ? this.createStaticData(expandingRegion) : this.expandRegion(node, expandingRegion);
            node.getChildren().addAll(datas);
        }
    }

    private List<AxisDataNode> createStaticData(ExpandingRegion expandingRegion) throws ReportBuildException {
        Object value;
        try {
            value = expandingRegion.getMasterCell().getValue().evaluate(this.context);
        }
        catch (ReportExpressionException e) {
            throw new ReportBuildException("\u8ba1\u7b97\u5355\u5143\u683c" + expandingRegion.getMasterCell().getPosition() + "\u8868\u8fbe\u5f0f\u503c\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        AxisDataNode data = new AxisDataNode(expandingRegion);
        data.setValue(value);
        data.setDisplayValue(value);
        data.setKeyValue(value);
        ArrayList<AxisDataNode> datas = new ArrayList<AxisDataNode>(1);
        datas.add(data);
        this.expandNodes(datas, expandingRegion);
        return datas;
    }

    private List<AxisDataNode> expandRegion(AxisDataNode parentNode, ExpandingRegion expandingRegion) throws ReportBuildException {
        RegionBuildInfo buildInfo = this.openBuildInfo(expandingRegion);
        List<AxisDataNode> datas = this.openDimItems(expandingRegion, buildInfo);
        this.orderDatas(datas, buildInfo);
        datas = this.getTopN(datas, buildInfo);
        datas = this.indentDatas(expandingRegion, datas, buildInfo);
        this.buildDisplays(expandingRegion, datas);
        this.buildComments(expandingRegion, datas);
        this.expandNodes(datas, expandingRegion);
        return datas;
    }

    private void buildDisplays(ExpandingRegion expandingRegion, List<AxisDataNode> datas) throws ReportBuildException {
        CellBindingInfo masterCell = expandingRegion.getMasterCell();
        if (masterCell.getDisplay() == null) {
            datas.forEach(data -> data.setDisplayValue(data.getValue()));
            return;
        }
        for (AxisDataNode data2 : datas) {
            this.context.getCurrentRestrictions().push(data2);
            data2.setDisplayValue(this.evalNodeValue(data2, masterCell.getDisplay()));
            this.context.getCurrentRestrictions().pop();
        }
    }

    private void buildComments(ExpandingRegion expandingRegion, List<AxisDataNode> datas) throws ReportBuildException {
        CellBindingInfo masterCell = expandingRegion.getMasterCell();
        if (masterCell.getComment() == null) {
            return;
        }
        for (AxisDataNode data : datas) {
            this.context.getCurrentRestrictions().push(data);
            Object comment = this.evalNodeValue(data, masterCell.getComment());
            data.setComment(comment == null ? null : comment.toString());
            this.context.getCurrentRestrictions().pop();
        }
    }

    private void expandNodes(List<AxisDataNode> datas, ExpandingRegion expandingRegion) throws ReportBuildException {
        if (expandingRegion.getSubRegions().isEmpty()) {
            return;
        }
        for (AxisDataNode data : datas) {
            List<IFilterDescriptor> filters = this.getNodeFilters(data);
            this.pushFilters(filters);
            this.context.getCurrentRestrictions().push(data);
            this.expandNode(data, expandingRegion.getSubRegions());
            this.context.getCurrentRestrictions().pop();
            this.popFilters(filters);
        }
    }

    private RegionBuildInfo openBuildInfo(ExpandingRegion expandingRegion) throws ReportBuildException {
        RegionBuildInfo buildInfo = this.buildInfos.get(expandingRegion);
        if (buildInfo == null) {
            buildInfo = this.createBuildInfo(expandingRegion);
            this.buildInfos.put(expandingRegion, buildInfo);
        }
        return buildInfo;
    }

    private RegionBuildInfo createBuildInfo(ExpandingRegion expandingRegion) throws ReportBuildException {
        RegionBuildInfo buildInfo = new RegionBuildInfo();
        if (expandingRegion.isStatic()) {
            buildInfo.dimFields = new ArrayList<String>();
        } else {
            String parentField = this.setTreeBuilder(buildInfo, expandingRegion);
            this.setDimFields(buildInfo, expandingRegion, parentField);
            this.setOrderInfo(buildInfo, expandingRegion);
            this.setTopNInfo(buildInfo, expandingRegion);
        }
        return buildInfo;
    }

    private String setTreeBuilder(RegionBuildInfo buildInfo, ExpandingRegion expandingRegion) throws ReportBuildException {
        if (expandingRegion.isStatic() || expandingRegion.getMasterCell().getCellMap().getHierarchyMode() == HierarchyMode.LIST) {
            return null;
        }
        DSModel model = expandingRegion.getDataSet();
        DSHierarchy hierachy = this.findHierarchy(expandingRegion.getKeyField().getField(), model);
        if (hierachy == null) {
            return null;
        }
        switch (hierachy.getType()) {
            case CODE_HIERARCHY: {
                try {
                    buildInfo.treeBuilder = TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)new NodeVistor(), (String)hierachy.getCodePattern(), (boolean)true);
                }
                catch (TreeException e) {
                    throw new ReportBuildException("\u5206\u6790\u5c42\u7ea7[" + hierachy.getName() + "]\u7ed3\u6784\u51fa\u9519\u3002", e);
                }
                buildInfo.treeBuilder.setSortMode(1);
                return null;
            }
            case PARENT_HIERARCHY: {
                try {
                    buildInfo.treeBuilder = TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new NodeVistor());
                }
                catch (TreeException e) {
                    throw new ReportBuildException("\u5206\u6790\u5c42\u7ea7[" + hierachy.getName() + "]\u7ed3\u6784\u51fa\u9519\u3002", e);
                }
                buildInfo.treeBuilder.setSortMode(1);
                return hierachy.getParentFieldName();
            }
        }
        return null;
    }

    private DSHierarchy findHierarchy(DSField field, DSModel model) {
        for (DSHierarchy hierachy : model.getHiers()) {
            if (hierachy.getType() == DSHierarchyType.COLUMN_HIERARCHY || !field.getKeyField().equalsIgnoreCase(hierachy.getKeyFieldName())) continue;
            return hierachy;
        }
        return null;
    }

    private void setDimFields(RegionBuildInfo buildInfo, ExpandingRegion expandingRegion, String parentField) {
        String keyFieldName;
        ArrayList<String> fieldNames = new ArrayList<String>(3);
        DSField field = expandingRegion.getField().getField();
        String valueFieldName = field.getName().toUpperCase();
        fieldNames.add(valueFieldName);
        buildInfo.valueIndex = 0;
        String string = keyFieldName = field.getKeyField() == null ? null : field.getKeyField().toUpperCase();
        if (keyFieldName == null || keyFieldName.length() == 0 || keyFieldName.equals(valueFieldName)) {
            buildInfo.keyIndex = 0;
        } else {
            fieldNames.add(keyFieldName);
            buildInfo.keyIndex = 1;
        }
        if (StringUtils.isEmpty((String)parentField)) {
            buildInfo.parentIndex = -1;
        } else {
            int p = fieldNames.indexOf(parentField);
            if (p == -1) {
                fieldNames.add(parentField);
                buildInfo.parentIndex = fieldNames.size() - 1;
            } else {
                buildInfo.parentIndex = p;
            }
        }
        buildInfo.dimFields = fieldNames;
    }

    private void setOrderInfo(RegionBuildInfo buildInfo, ExpandingRegion expandingRegion) {
        CellBindingInfo masterCell = expandingRegion.getMasterCell();
        if (masterCell.getOrderMode() == OrderMode.NONE) {
            return;
        }
        boolean pinyinMode = this.context.getPinYinOrdering().contains(masterCell.getPosition());
        buildInfo.comparator = new AxisDataComparator(masterCell.getOrderMode(), pinyinMode);
    }

    private void setTopNInfo(RegionBuildInfo buildInfo, ExpandingRegion expandingRegion) {
        int n;
        buildInfo.topN = expandingRegion.needRank() ? ((n = expandingRegion.getTopN()) < 1 ? Integer.MAX_VALUE : n) : 0;
        buildInfo.denseRank = expandingRegion.isDenseRank();
    }

    private List<AxisDataNode> openDimItems(ExpandingRegion expandingRegion, RegionBuildInfo buildInfo) throws ReportBuildException {
        BIDataSet dataset = this.openDimDataSet(expandingRegion, buildInfo);
        return this.readDimItems(expandingRegion, buildInfo, dataset);
    }

    private BIDataSet openDimDataSet(ExpandingRegion expandingRegion, RegionBuildInfo buildInfo) throws ReportBuildException {
        BIDataSet dataSet;
        List<IFilterDescriptor> filters = this.getMasterCellFilters(expandingRegion);
        filters.addAll(0, this.context.getCurrentFilters());
        FilterAnalyzer.distinctFilters(filters);
        String dataSetName = expandingRegion.getDataSet().getName();
        try {
            dataSet = this.context.openDataSet(dataSetName, filters);
        }
        catch (ReportContextException e) {
            throw new ReportBuildException(e.getMessage(), e);
        }
        try {
            return dataSet.distinct(buildInfo.dimFields);
        }
        catch (BIDataSetException e) {
            throw new ReportBuildException("\u83b7\u53d6\u7ef4\u5ea6\u5217\u8868\u5931\u8d25\uff1a" + (Object)((Object)expandingRegion.getField()), e);
        }
    }

    private List<AxisDataNode> readDimItems(ExpandingRegion expandingRegion, RegionBuildInfo buildInfo, BIDataSet dataSet) throws ReportBuildException {
        ArrayList<AxisDataNode> items = new ArrayList<AxisDataNode>(dataSet.getRecordCount());
        for (BIDataRow row : dataSet) {
            Object keyValue = row.getValue(buildInfo.keyIndex);
            if (!this.expandingNulls && DataType.isNull((Object)keyValue)) continue;
            AxisDataNode data = new AxisDataNode(expandingRegion);
            this.context.getCurrentRestrictions().push(data);
            this.setDataNode(data, expandingRegion.getMasterCell(), buildInfo, row);
            this.context.getCurrentRestrictions().pop();
            items.add(data);
        }
        return items;
    }

    private void setDataNode(AxisDataNode data, CellBindingInfo masterCell, RegionBuildInfo buildInfo, BIDataRow row) throws ReportBuildException {
        data.setValue(row.getValue(buildInfo.valueIndex));
        data.setKeyValue(row.getValue(buildInfo.keyIndex));
        if (buildInfo.parentIndex >= 0) {
            data.setParentValue(row.getValue(buildInfo.parentIndex));
        }
        if (masterCell.getOrder() == null) {
            data.setOrderValue(data.getValue());
        } else {
            data.setOrderValue(this.evalNodeValue(data, masterCell.getOrder()));
        }
    }

    private Object evalNodeValue(AxisDataNode node, IReportExpression expr) throws ReportBuildException {
        List<IFilterDescriptor> filters = this.getNodeFilters(node);
        this.pushFilters(filters);
        try {
            Object object = expr.evaluate(this.context);
            return object;
        }
        catch (ReportExpressionException e) {
            throw new ReportBuildException(node.getRegion().getMasterCell().getPosition() + "\u5355\u5143\u683c\u8868\u8fbe\u5f0f\u8ba1\u7b97\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        finally {
            this.popFilters(filters);
        }
    }

    private List<IFilterDescriptor> getNodeFilters(AxisDataNode data) throws ReportBuildException {
        List<IFilterDescriptor> filters = this.getMasterCellFilters(data.getRegion());
        if (!data.getRegion().isStatic()) {
            filters.add(0, data.toFilter());
        }
        return filters;
    }

    private List<IFilterDescriptor> getMasterCellFilters(ExpandingRegion region) throws ReportBuildException {
        if (region.getMasterCell().getFilter() == null) {
            return new ArrayList<IFilterDescriptor>();
        }
        try {
            return FilterAnalyzer.createFilterDescriptor(this.context, region.getMasterCell(), region.getField().getField());
        }
        catch (ReportContextException e) {
            throw new ReportBuildException("\u5206\u6790" + region.getMasterCell().getPosition() + "\u5355\u5143\u683c\u8fc7\u6ee4\u6761\u4ef6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private void pushFilters(List<IFilterDescriptor> filters) {
        this.context.pushCurrentFilters(filters);
    }

    private void popFilters(List<IFilterDescriptor> filters) throws ReportBuildException {
        try {
            this.context.popCurrentFilters(filters);
        }
        catch (ReportContextException e) {
            throw new ReportBuildException(e);
        }
    }

    private void orderDatas(List<AxisDataNode> datas, RegionBuildInfo buildInfo) {
        if (buildInfo.comparator != null) {
            Collections.sort(datas, buildInfo.comparator);
        }
    }

    private List<AxisDataNode> getTopN(List<AxisDataNode> datas, RegionBuildInfo buildInfo) {
        if (buildInfo.topN < 1) {
            return datas;
        }
        int count = buildInfo.denseRank ? this.denseRankDatas(datas, buildInfo.topN) : this.rankDatas(datas, buildInfo.topN);
        return count == datas.size() ? datas : datas.subList(0, count);
    }

    private int denseRankDatas(List<AxisDataNode> datas, int topN) {
        int index;
        int rank = 1;
        AxisDataNode prevData = null;
        for (index = 0; index < datas.size(); ++index) {
            AxisDataNode curData = datas.get(index);
            if (prevData != null && DataType.compareObject((Object)curData.getOrderValue(), (Object)prevData.getOrderValue()) != 0 && ++rank > topN) break;
            curData.setRank(rank);
            prevData = curData;
        }
        return index;
    }

    private int rankDatas(List<AxisDataNode> datas, int topN) {
        int index;
        int rank = 1;
        int count = 0;
        AxisDataNode prevData = null;
        for (index = 0; index < datas.size(); ++index) {
            AxisDataNode curData = datas.get(index);
            if (prevData != null && DataType.compareObject((Object)curData.getOrderValue(), (Object)prevData.getOrderValue()) != 0) {
                if ((rank += count) > topN) break;
                count = 0;
            }
            curData.setRank(rank);
            ++count;
            prevData = curData;
        }
        return index;
    }

    private List<AxisDataNode> indentDatas(ExpandingRegion expandingRegion, List<AxisDataNode> datas, RegionBuildInfo buildInfo) throws ReportBuildException {
        TreeNode root;
        if (buildInfo.treeBuilder == null) {
            return datas;
        }
        try {
            root = buildInfo.treeBuilder.build(datas.iterator());
        }
        catch (TreeException e) {
            throw new ReportBuildException("\u6784\u5efa" + expandingRegion.getMasterCell().getPosition() + "\u5355\u5143\u683c\u7684\u6811\u5f62\u7ed3\u6784\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        ArrayList<AxisDataNode> identedDatas = new ArrayList<AxisDataNode>(datas.size());
        for (int i = 0; i < root.size(); ++i) {
            this.setDataLevel(root.get(i), 1, identedDatas);
        }
        return identedDatas;
    }

    private void setDataLevel(TreeNode node, int level, List<AxisDataNode> datas) {
        AxisDataNode data = (AxisDataNode)node.getItem();
        data.setLevel(level);
        data.setChildrenSize(node.size());
        datas.add(data);
        for (int i = 0; i < node.size(); ++i) {
            this.setDataLevel(node.get(i), level + 1, datas);
        }
    }

    public ReportContext getContext() {
        return this.context;
    }

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public ExpandingAxis getAxis() {
        return this.axis;
    }

    public void setAxis(ExpandingAxis axis) {
        this.axis = axis;
    }

    public ExpandingAxis getOtherAxis() {
        return this.otherAxis;
    }

    public void setOtherAxis(ExpandingAxis otherAxis) {
        this.otherAxis = otherAxis;
    }

    private final class NodeVistor
    implements ObjectVistor {
        private NodeVistor() {
        }

        public String getCode(Object obj) {
            AxisDataNode node = (AxisDataNode)obj;
            return node.getKeyValue() == null ? null : node.getKeyValue().toString();
        }

        public String getParentCode(Object obj) {
            AxisDataNode node = (AxisDataNode)obj;
            return node.getParentValue() == null ? null : node.getParentValue().toString();
        }
    }

    private final class RegionBuildInfo {
        List<String> dimFields;
        int keyIndex;
        int valueIndex;
        int parentIndex;
        Comparator<AxisDataNode> comparator;
        TreeBuilder treeBuilder;
        int topN;
        boolean denseRank;

        private RegionBuildInfo() {
        }

        public String toString() {
            return this.dimFields == null ? "[null]" : this.dimFields.toString();
        }
    }
}

