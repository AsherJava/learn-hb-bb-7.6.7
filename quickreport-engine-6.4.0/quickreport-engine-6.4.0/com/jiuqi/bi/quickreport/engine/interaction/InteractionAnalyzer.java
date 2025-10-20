/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.ICellNode
 *  com.jiuqi.bi.syntax.cell.IRegionNode
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.reference.NewSerial
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.interaction;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.FixedArea;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteraction;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteractionException;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.function.NewSerialAdapter;
import com.jiuqi.bi.quickreport.engine.result.CellRestrictionInfo;
import com.jiuqi.bi.quickreport.engine.result.CellSortInfo;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.ICellNode;
import com.jiuqi.bi.syntax.cell.IRegionNode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.reference.NewSerial;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class InteractionAnalyzer {
    private ReportContext context;
    private EngineWorkbook workbook;
    private List<GridArea> areas = new ArrayList<GridArea>();
    private ReportInteraction interaction;

    public void setContext(ReportContext context) {
        this.context = context;
    }

    public void setWorkbook(EngineWorkbook workbook) {
        this.workbook = workbook;
    }

    public void setInteraction(ReportInteraction interaction) {
        this.interaction = interaction;
    }

    public List<GridArea> getAreas() {
        return this.areas;
    }

    public void analyse() throws ReportInteractionException {
        this.interaction.getInteractiveInfos().clear();
        for (GridArea area : this.areas) {
            if (!this.isInteractive(area)) continue;
            this.analyseArea((ExpandingArea)area);
        }
    }

    private boolean isInteractive(GridArea area) {
        if (!area.getSheetName().equalsIgnoreCase(this.workbook.getReport().getPrimarySheetName())) {
            return false;
        }
        if (!(area instanceof ExpandingArea)) {
            return false;
        }
        ExpandingArea expandingArea = (ExpandingArea)area;
        return !expandingArea.getRowAxis().isEmpty();
    }

    private void analyseArea(ExpandingArea area) throws ReportInteractionException {
        try {
            this.workbook.setActiveWorksheet(this.workbook.getReport().getPrimarySheetName());
        }
        catch (ReportExpressionException e) {
            throw new ReportInteractionException(e);
        }
        ExpandingRegion interactiveRegion = this.findInteractiveRegion(area);
        if (interactiveRegion != null) {
            this.analyseRegions(area, interactiveRegion);
        }
    }

    private ExpandingRegion findInteractiveRegion(ExpandingArea area) throws ReportInteractionException {
        ExpandingRegion sortedRegion = null;
        for (ExpandingRegion region : area.getRowAxis()) {
            if (!region.getMasterCell().isInteractive()) continue;
            if (sortedRegion == null) {
                sortedRegion = region;
                continue;
            }
            if (sortedRegion.contains(region.getMasterCell().getCellMap().getPosition())) continue;
            throw new ReportInteractionException("\u884c\u8868\u5934\u6392\u5e8f\u548c\u8fc7\u6ee4\u8bbe\u7f6e\u9519\u8bef\uff0c" + sortedRegion.getMasterCellPosition() + "\u548c" + region.getMasterCellPosition() + "\u4e0d\u5728\u540c\u4e00\u4e2a\u6d6e\u52a8\u884c\u5185\uff0c\u65e0\u6cd5\u540c\u65f6\u8bbe\u7f6e\u884c\u8868\u5934\u64cd\u4f5c");
        }
        return sortedRegion;
    }

    private EngineWorksheet getActiveSheet() throws ReportInteractionException {
        try {
            return (EngineWorksheet)this.workbook.activeWorksheet(this.context);
        }
        catch (CellExcpetion e) {
            throw new ReportInteractionException(e);
        }
    }

    private Position findInteractiveCell(ExpandingArea area, int col) throws ReportInteractionException {
        GridData grid = this.getActiveSheet().getGridData();
        Region rowRegion = area.getRowAxis().getRegion();
        for (int row = rowRegion.top() - 1; row >= 1; --row) {
            CellField field = grid.expandCell(col, row);
            if (field.left != col || field.top != row) continue;
            CellBindingInfo bindingInfo = (CellBindingInfo)grid.getObj(col, row);
            if (this.isInteractiveCell(grid.getCellData(col, row), bindingInfo, area)) {
                return Position.valueOf((int)col, (int)row);
            }
            if (bindingInfo != null && bindingInfo.getOwnerArea() != area && !(bindingInfo.getOwnerArea() instanceof FixedArea)) break;
        }
        return null;
    }

    private boolean isInteractiveCell(String text, CellBindingInfo bindingInfo, ExpandingArea area) throws ReportInteractionException {
        if (bindingInfo == null) {
            return !StringUtils.isEmpty((String)text);
        }
        if (bindingInfo.getOwnerArea() != area && !(bindingInfo.getOwnerArea() instanceof FixedArea)) {
            return false;
        }
        IReportExpression expr = bindingInfo.getDisplay() == null ? bindingInfo.getValue() : bindingInfo.getDisplay();
        try {
            return expr.getDataType(this.context) != 3;
        }
        catch (ReportExpressionException e) {
            throw new ReportInteractionException(e);
        }
    }

    private CellValue openInteractiveValue(ResultGridData resultGrid, int col, int row) {
        CellValue interactiveVal = (CellValue)resultGrid.getGridData().getObj(col, row);
        if (interactiveVal == null) {
            GridCell cell = resultGrid.getGridData().getCell(col, row);
            if (StringUtils.isEmpty((String)cell.getCellData())) {
                return null;
            }
            interactiveVal = new CellValue();
            interactiveVal.value = cell.getCellData();
            interactiveVal.displayValue = cell.getShowText();
            resultGrid.getGridData().setObj(col, row, (Object)interactiveVal);
        } else if (interactiveVal.value == null) {
            return null;
        }
        return interactiveVal;
    }

    private String createSortExpression(ExpandingArea area, CellValue sortByVal) throws ReportInteractionException {
        ArrayList<String> filters = new ArrayList<String>();
        if (!StringUtils.isEmpty((String)sortByVal._bindingInfo.getCellMap().getFilter())) {
            filters.add(sortByVal._bindingInfo.getCellMap().getFilter());
        }
        if (!sortByVal._restrictions.isEmpty() && !area.getColAxis().isEmpty()) {
            List<ExpandingRegion> colRegions = area.getColAxis().findRestrictiveRegions(sortByVal._bindingInfo.getCellMap().getPosition());
            for (AxisDataNode data : sortByVal._restrictions) {
                if (!colRegions.contains(data.getRegion())) continue;
                try {
                    DataNode value = new DataNode(null, data.getRegion().getKeyField().getType(this.context), data.getKeyValue());
                    Equal expr = new Equal(null, (IASTNode)data.getRegion().getKeyField(), (IASTNode)value);
                    String filter = expr.interpret((IContext)this.context, Language.FORMULA, null);
                    filters.add(filter);
                }
                catch (SyntaxException e) {
                    throw new ReportInteractionException(e);
                }
            }
        }
        if (filters.isEmpty()) {
            return sortByVal._bindingInfo.getCellMap().getValue();
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("Q_Eval").append('(').append(sortByVal._bindingInfo.getCellMap().getValue());
        for (String filter : filters) {
            buffer.append(", ").append(filter);
        }
        buffer.append(')');
        return buffer.toString();
    }

    private void analyseRegions(ExpandingArea area, ExpandingRegion region) throws ReportInteractionException {
        for (int col = region.getRegion().left(); col <= region.getRegion().right(); ++col) {
            Map<ExpandingRegion, CellBindingInfo> valueCells = this.findValueCells(area, region, col);
            if (valueCells.isEmpty()) continue;
            this.resolveSortByMeasures(region, valueCells);
            Position interactiveCell = this.findInteractiveCell(area, col);
            if (interactiveCell == null) continue;
            this.createInteractiveInfos(area, region, valueCells, interactiveCell);
        }
    }

    private Map<ExpandingRegion, CellBindingInfo> findValueCells(ExpandingArea area, ExpandingRegion sortedRegion, int col) throws ReportInteractionException {
        HashMap<ExpandingRegion, CellBindingInfo> sortByCells = new HashMap<ExpandingRegion, CellBindingInfo>();
        GridData grid = this.getActiveSheet().getGridData();
        for (int row = sortedRegion.getRegion().top(); row <= sortedRegion.getRegion().bottom(); ++row) {
            ExpandingRegion masterRegion;
            CellBindingInfo bindingInfo;
            CellField field = grid.expandCell(col, row);
            if (field.left != col || field.top != row || (bindingInfo = (CellBindingInfo)grid.getObj(col, row)) == null || bindingInfo.getValue().hasCellRef() || FunctionNode.isFunction((IASTNode)bindingInfo.getValue().getRootNode(), (Class[])new Class[]{NewSerialAdapter.class, NewSerial.class}) || (masterRegion = this.findSortMasterRegion(sortedRegion, bindingInfo)) == null) continue;
            sortByCells.putIfAbsent(masterRegion, bindingInfo);
        }
        if (sortByCells.size() >= 2) {
            List regions = sortByCells.keySet().stream().sorted((r1, r2) -> r1.getRegion().leftTop().compareTo(r2.getRegion().leftTop())).collect(Collectors.toList());
            for (int i = 0; i < regions.size() - 1; ++i) {
                ExpandingRegion region = (ExpandingRegion)regions.get(i);
                if (!regions.subList(i + 1, regions.size()).stream().anyMatch(r -> region.getRegion().contains(r.getRegion()))) continue;
                sortByCells.remove(region);
            }
        }
        if (sortByCells.size() >= 2) {
            throw new ReportInteractionException("\u884c\u8868\u5934\u6392\u5e8f\u548c\u8fc7\u6ee4\u8bbe\u7f6e\u9519\u8bef\uff0c\u6d6e\u52a8\u533a\u57df\u201c" + sortedRegion.getRegion() + "\u201d\u4e2d\u5b58\u5728\u5e76\u5217\u7684\u5185\u90e8\u6d6e\u52a8\u884c");
        }
        return sortByCells;
    }

    private void resolveSortByMeasures(ExpandingRegion sortedRegion, Map<ExpandingRegion, CellBindingInfo> sortByCells) {
        for (ExpandingRegion subRegion : sortedRegion.getSubRegions()) {
            this.resolveSortByMeasures(subRegion, sortByCells);
        }
        if (sortByCells.containsKey(sortedRegion)) {
            return;
        }
        for (ExpandingRegion subRegion : sortedRegion.getSubRegions()) {
            CellBindingInfo sortByCell = sortByCells.get(subRegion);
            if (sortByCell == null || !sortByCell.getValue().hasMeasureRef()) continue;
            sortByCells.putIfAbsent(sortedRegion, sortByCell);
            break;
        }
    }

    private ExpandingRegion findSortMasterRegion(ExpandingRegion sortedRegion, CellBindingInfo bindingInfo) {
        ArrayList<ExpandingRegion> regions = new ArrayList<ExpandingRegion>();
        if (sortedRegion.findRestrictiveRegions(bindingInfo.getCellMap().getPosition(), regions)) {
            return regions.isEmpty() ? null : (ExpandingRegion)regions.get(regions.size() - 1);
        }
        return null;
    }

    private void createInteractiveInfos(ExpandingArea area, ExpandingRegion region, Map<ExpandingRegion, CellBindingInfo> valuesCells, Position interactiveCell) throws ReportInteractionException {
        ResultGridData resultGrid = this.getActiveSheet().getResultGrid();
        List<Integer> interactiveCols = resultGrid.getExpandedCols(interactiveCell.col());
        for (int interactiveCol : interactiveCols) {
            int interactiveRow;
            InteractiveInfo interactiveInfo = new InteractiveInfo(this.interaction.getInteractiveInfos().size());
            interactiveInfo.setGroupName(area.getRegion().toString());
            if (region.getMasterCell().getCellMap().isRowSortable() && this.fillSortInfos(area, valuesCells, interactiveInfo, interactiveCol)) {
                interactiveInfo.setSortable(true);
            }
            if (region.getMasterCell().getCellMap().isRowFilterable() && this.fillFilterInfos(area, valuesCells, interactiveInfo, interactiveCol)) {
                interactiveInfo.setFilterable(true);
            }
            if (!interactiveInfo.enabled()) continue;
            try {
                interactiveRow = resultGrid.locateLastRow(interactiveCell.row());
            }
            catch (ReportBuildException e) {
                throw new ReportInteractionException(e);
            }
            CellValue interactiveVal = this.openInteractiveValue(resultGrid, interactiveCol, interactiveRow);
            if (interactiveVal == null) continue;
            interactiveVal.interactiveInfo = interactiveInfo;
            this.interaction.getInteractiveInfos().add(interactiveInfo);
        }
    }

    private boolean fillSortInfos(ExpandingArea area, Map<ExpandingRegion, CellBindingInfo> sortByCells, InteractiveInfo interactiveInfo, int interactiveCol) throws ReportInteractionException {
        ResultGridData resultGrid = this.getActiveSheet().getResultGrid();
        for (Map.Entry<ExpandingRegion, CellBindingInfo> entry : sortByCells.entrySet()) {
            int sortByRow;
            CellBindingInfo sortByCell = entry.getKey().getMasterCell();
            try {
                sortByRow = resultGrid.locateNewRow(sortByCell.getCellMap().getPosition().row());
            }
            catch (ReportBuildException e) {
                throw new ReportInteractionException(e);
            }
            CellValue sortByVal = (CellValue)resultGrid.getGridData().getObj(interactiveCol, sortByRow);
            if (sortByVal == null) {
                return false;
            }
            String sortByExpr = this.createSortExpression(area, sortByVal);
            interactiveInfo.getSortInfos().add(new CellSortInfo(sortByCell.getCellMap().getPosition(), sortByExpr));
        }
        return true;
    }

    private boolean fillFilterInfos(ExpandingArea area, Map<ExpandingRegion, CellBindingInfo> valuesCells, InteractiveInfo interactiveInfo, int col) throws ReportInteractionException {
        int row;
        String dispDSName;
        if (valuesCells.values().stream().distinct().count() != 1L) {
            return false;
        }
        CellBindingInfo valueCell = valuesCells.values().iterator().next();
        String dataSetName = InteractionAnalyzer.getFilteredDataSet(valueCell.getValue());
        if (dataSetName == null) {
            return false;
        }
        if (!(valueCell.getDisplay() == null || (dispDSName = InteractionAnalyzer.getFilteredDataSet(valueCell.getDisplay())) != null && dataSetName.equals(dispDSName))) {
            return false;
        }
        ResultGridData resultGrid = this.getActiveSheet().getResultGrid();
        try {
            row = resultGrid.locateNewRow(valueCell.getPosition().getPosition().row());
        }
        catch (ReportBuildException e) {
            throw new ReportInteractionException(e);
        }
        CellValue cellValue = (CellValue)resultGrid.getGridData().getObj(col, row);
        if (cellValue == null) {
            return false;
        }
        interactiveInfo.getFilterInfo().setDataSetName(dataSetName);
        interactiveInfo.getFilterInfo().setMeasure(InteractionAnalyzer.hasMeasure(valueCell.getValue()));
        interactiveInfo.getFilterInfo().setPosition(valueCell.getPosition().getPosition());
        if (cellValue._restrictions != null) {
            for (AxisDataNode data : cellValue._restrictions) {
                if (data.getRegion().isStatic() || data.getRegion().getExpandMode() != ExpandMode.COLEXPANDING) continue;
                CellRestrictionInfo restriction = new CellRestrictionInfo();
                DSFieldNode keyField = data.getRegion().getKeyField();
                restriction.setDataSetName(keyField.getDataSet().getName());
                restriction.setFieldName(keyField.getField().getName());
                restriction.setValue(data.getValue());
                interactiveInfo.getFilterInfo().getRestrictions().add(restriction);
            }
        }
        return true;
    }

    private static boolean hasMeasure(IReportExpression expression) {
        for (IASTNode node : expression.getRootNode()) {
            if (!(node instanceof DSFieldNode) || ((DSFieldNode)node).getField().getFieldType() != FieldType.MEASURE) continue;
            return true;
        }
        return false;
    }

    private static String getFilteredDataSet(IReportExpression expression) {
        HashSet<String> dsNames = new HashSet<String>();
        for (IASTNode node : expression.getRootNode()) {
            if (node instanceof ICellNode || node instanceof IRegionNode) {
                return null;
            }
            if (!(node instanceof DSFieldNode)) continue;
            dsNames.add(((DSFieldNode)node).getDataSet().getName());
            if (dsNames.size() <= 1) continue;
            return null;
        }
        return dsNames.isEmpty() ? null : (String)dsNames.iterator().next();
    }
}

