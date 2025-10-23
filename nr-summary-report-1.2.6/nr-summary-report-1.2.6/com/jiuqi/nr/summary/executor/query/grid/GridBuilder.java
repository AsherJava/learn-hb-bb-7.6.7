/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.model.CellMap
 *  com.jiuqi.bi.quickreport.model.ExpandMode
 *  com.jiuqi.bi.quickreport.model.HierarchyMode
 *  com.jiuqi.bi.quickreport.model.OrderMode
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.nr.summary.executor.query.grid;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.quickreport.model.HierarchyMode;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.grid.GridHelper;
import com.jiuqi.nr.summary.executor.query.grid.PatternParser;
import com.jiuqi.nr.summary.model.caliber.CaliberApplyType;
import com.jiuqi.nr.summary.model.caliber.SumPosition;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class GridBuilder {
    private final Map<String, SummaryDSModel> modelMap;
    private final WorksheetModel wsModel;
    private final GridHelper gridHelper;
    protected final SummaryReportModelHelper reportModelHelper;
    private Map<Integer, Integer> sumOffsetMap = new HashMap<Integer, Integer>();

    public GridBuilder(Map<String, SummaryDSModel> modelMap, WorksheetModel wsModel, SummaryReportModelHelper reportModelHelper) {
        this.modelMap = modelMap;
        this.wsModel = wsModel;
        this.gridHelper = new GridHelper(wsModel.getGriddata());
        this.reportModelHelper = reportModelHelper;
    }

    public void build() throws Exception {
        List<SummaryFloatRegion> floatRegions = this.reportModelHelper.getFloatRegions();
        Collections.sort(floatRegions, Comparator.comparingInt(SummaryFloatRegion::getPosition));
        for (SummaryFloatRegion floatRegion : floatRegions) {
            new FloatRegionBuilder(floatRegion).build();
        }
        String fixTableName = this.reportModelHelper.getReportName();
        new FixRegionBuilder(fixTableName).build();
    }

    private CellMap buildDataCell(String dsModelName, DataCell dataCell) {
        Position pos = this.getPosition(dataCell);
        GridCell gridCell = this.gridHelper.getCellEx(pos);
        try {
            SummaryDSModel dsModel = this.getDSModel(dsModelName);
            DSField dsField = dsModel.findDsFieldByKey(this.reportModelHelper.getDataCellKey(dataCell));
            PatternParser parser = this.getPattenParser(dsField.getShowPattern());
            this.gridHelper.setValueStyle(gridCell, dataCell.getSummaryZb().getDataType().getValue(), parser);
            this.gridHelper.setCell(gridCell);
            CellMap cellMap = this.newCellMap(dsModelName, dsField, pos);
            return cellMap;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    protected CellMap newCellMap(String dsModelName, DSField dsField, Position pos) {
        String valueExp;
        String displayExp = valueExp = dsModelName + "." + dsField.getName();
        CellMap cellMap = new CellMap(pos);
        cellMap.setValue(valueExp);
        cellMap.setDisplay(displayExp);
        return cellMap;
    }

    protected CellMap newCellMap(String dsModelName, String fieldName, Position pos) throws Exception {
        DSField dsField = this.getDSField(dsModelName, fieldName);
        return this.newCellMap(dsModelName, dsField, pos);
    }

    protected SummaryDSModel getDSModel(String modelName) throws Exception {
        SummaryDSModel dsModel = this.modelMap.get(modelName);
        if (dsModel == null) {
            throw new Exception("\u6570\u636e\u96c6\u6a21\u578b\u67e5\u627e\u4e2d\uff0c\u672a\u627e\u5230\u6a21\u578b[" + modelName + "]");
        }
        return dsModel;
    }

    protected DSField getDSField(String modelName, String fieldName) throws Exception {
        SummaryDSModel dsModel = this.getDSModel(modelName);
        if (dsModel == null) {
            throw new Exception("\u6570\u636e\u96c6\u6a21\u578b\u67e5\u627e\u4e2d\uff0c\u672a\u627e\u5230\u6a21\u578b[" + modelName + "]");
        }
        DSField dsField = dsModel.findField(fieldName);
        if (dsField == null) {
            throw new Exception("\u6570\u636e\u96c6\u6a21\u578b\u4e2d\uff0c\u672a\u627e\u5230\u5b57\u6bb5[" + fieldName + "]");
        }
        return dsField;
    }

    protected PatternParser getPattenParser(String patten) {
        PatternParser patternParser = new PatternParser(patten);
        return patternParser;
    }

    private int getSumOffset(int row) {
        for (int i = row - 1; i >= 1; --i) {
            if (!this.sumOffsetMap.containsKey(i)) continue;
            return this.sumOffsetMap.get(i);
        }
        return 0;
    }

    private Position getPosition(MainCell mainCell) {
        return this.getPosition(mainCell.getY() + 1, mainCell.getX() + 1 + this.getSumOffset(mainCell.getX()));
    }

    private Position getPosition(DataCell dataCell) {
        return this.getPosition(dataCell.getY() + 1, dataCell.getX() + 1 + this.getSumOffset(dataCell.getX()));
    }

    private Position getPosition(int col, int row) {
        return this.gridHelper.newPosition(col, row);
    }

    class FloatRegionBuilder {
        private SummaryFloatRegion floatRegion;
        private List<MainCell> mainCells;
        private List<MainCell> floatMainCells = new ArrayList<MainCell>();
        private List<MainCell> topMainCells = new ArrayList<MainCell>();
        private List<MainCell> bottomMainCells = new ArrayList<MainCell>();
        private Map<Position, CellMap> originCellMaps = new HashMap<Position, CellMap>();
        List<DataCell> floatDataCells;

        public FloatRegionBuilder(SummaryFloatRegion floatRegion) {
            this.floatRegion = floatRegion;
            this.init();
        }

        private void init() {
            this.mainCells = GridBuilder.this.reportModelHelper.getMainCellsByRegion(this.floatRegion);
            Collections.sort(this.mainCells, Comparator.comparingInt(MainCell::getY));
            this.floatDataCells = GridBuilder.this.reportModelHelper.getFloatDataCells(this.floatRegion);
        }

        private void prepareMainCells() {
            this.mainCells.forEach(mainCell -> mainCell.getCaliberInfos().forEach(caliberInfo -> {
                if (caliberInfo.getApplyType() == CaliberApplyType.FLOAT) {
                    this.floatMainCells.add((MainCell)mainCell);
                    if (caliberInfo.getFloatInfo().isShowSum()) {
                        if (caliberInfo.getFloatInfo().getSumPosition() == SumPosition.TOP) {
                            this.topMainCells.add((MainCell)mainCell);
                        } else {
                            this.bottomMainCells.add(0, (MainCell)mainCell);
                        }
                    }
                }
            }));
        }

        public void build() throws Exception {
            List<CellMap> mainCellMaps = this.buildFloatDimensions(this.mainCells);
            List<CellMap> dataCellMaps = this.buildDataCells(this.floatDataCells);
            mainCellMaps.forEach(cellMap -> this.originCellMaps.put((Position)cellMap.getPosition().clone(), (CellMap)cellMap));
            dataCellMaps.forEach(cellMap -> this.originCellMaps.put((Position)cellMap.getPosition().clone(), (CellMap)cellMap));
            this.prepareMainCells();
            this.buildSumMainCells(this.topMainCells, this.bottomMainCells);
        }

        private void buildSumMainCells(List<MainCell> topMainCells, List<MainCell> bottomMainCells) {
            this.buildSumMainCells(topMainCells, SumPosition.TOP);
            this.buildSumMainCells(bottomMainCells, SumPosition.BOTTOM);
            GridBuilder.this.wsModel.getCellMaps().addAll(this.originCellMaps.values());
        }

        private void buildSumMainCells(List<MainCell> sumMainCells, SumPosition sumPosition) {
            if (CollectionUtils.isEmpty(sumMainCells)) {
                return;
            }
            for (int i = 0; i < sumMainCells.size(); ++i) {
                MainCell mainCell = sumMainCells.get(i);
                int offset = sumPosition == SumPosition.TOP ? -(i + 1) : i + 1;
                this.addGridCells(mainCell, offset);
                this.addCellMaps(offset);
                this.updateSumOffset(mainCell);
            }
            int sumOffset = sumMainCells.size();
            for (int i = 0; i < this.floatMainCells.size(); ++i) {
                int changeOffset;
                MainCell mainCell = this.floatMainCells.get(i);
                boolean isContains = this.isContains(mainCell, sumMainCells);
                CellMap cellMap = this.originCellMaps.get(GridBuilder.this.getPosition(mainCell));
                Position position = cellMap.getPosition();
                Region expandRegion = cellMap.getExpandRegion();
                if (ObjectUtils.isEmpty(expandRegion)) continue;
                Position leftTop = expandRegion.leftTop();
                Position rightBottom = expandRegion.rightBottom();
                if (sumPosition == SumPosition.TOP) {
                    changeOffset = i - sumOffset + (isContains ? 1 : 0);
                    cellMap.setExpandRegion(new Region(GridBuilder.this.getPosition(leftTop.col(), position.row() + changeOffset), GridBuilder.this.getPosition(rightBottom.col(), position.row())));
                    continue;
                }
                changeOffset = sumOffset - i + (isContains ? -1 : 0);
                cellMap.setExpandRegion(new Region(position, GridBuilder.this.getPosition(rightBottom.col(), position.row() + changeOffset)));
            }
        }

        private boolean isContains(MainCell mainCell, List<MainCell> sumMainCells) {
            return sumMainCells.contains(mainCell);
        }

        private void addCellMaps(int offset) {
            for (MainCell mainCell : this.mainCells) {
                this.updateOriginPosition(GridBuilder.this.getPosition(mainCell), offset);
            }
            for (DataCell floatDataCell : this.floatDataCells) {
                Position originPosition = GridBuilder.this.getPosition(floatDataCell);
                this.updateOriginPosition(originPosition, offset);
                CellMap originCellMap = this.originCellMaps.get(originPosition);
                CellMap newCellMap = (CellMap)originCellMap.clone();
                int rowOffset = offset < 0 ? -1 : offset;
                newCellMap.setPosition(GridBuilder.this.getPosition(originCellMap.getPosition().col(), originCellMap.getPosition().row() + rowOffset));
                GridBuilder.this.wsModel.getCellMaps().add(newCellMap);
            }
        }

        private void updateOriginPosition(Position originPosition, int offset) {
            CellMap originCellMap = this.originCellMaps.get(originPosition);
            if (ObjectUtils.isEmpty(originCellMap)) {
                return;
            }
            int rowOffset = offset < 0 ? 1 : 0;
            originCellMap.setPosition(GridBuilder.this.getPosition(originCellMap.getPosition().col(), originCellMap.getPosition().row() + rowOffset));
        }

        private void addGridCells(MainCell mainCell, int offset) {
            Position mainPosition = GridBuilder.this.getPosition(mainCell);
            CellMap originCellMap = this.originCellMaps.get(mainPosition);
            GridData gridData = GridBuilder.this.gridHelper.getGridData();
            int indexOffset = offset < 0 ? 0 : offset;
            gridData.insertRow(originCellMap.getPosition().row() + indexOffset, 1);
            int rowOffset = offset < 0 ? 0 : offset;
            GridCell cell = gridData.getCell(mainPosition.col(), originCellMap.getPosition().row() + rowOffset);
            if (mainCell.getY() == 0) {
                cell.setShowText("\u5408\u8ba1");
            } else {
                cell.setShowText("\u5c0f\u8ba1");
            }
        }

        private void updateSumOffset(MainCell mainCell) {
            int row = mainCell.getX();
            if (!GridBuilder.this.sumOffsetMap.containsKey(row)) {
                GridBuilder.this.sumOffsetMap.put(row, GridBuilder.this.getSumOffset(mainCell.getX()));
            }
            GridBuilder.this.sumOffsetMap.put(row, (Integer)GridBuilder.this.sumOffsetMap.get(row) + 1);
        }

        private List<CellMap> buildFloatDimensions(List<MainCell> mainCells) throws Exception {
            ArrayList<CellMap> cellMaps = new ArrayList<CellMap>();
            if (mainCells == null) {
                return cellMaps;
            }
            String dsModelName = this.floatRegion.getTableName();
            DSField parentField = null;
            for (MainCell mainCell : mainCells) {
                String mainCellKey;
                SummaryDSModel dsModel = GridBuilder.this.getDSModel(dsModelName);
                DSField dsField = dsModel.findDsFieldByKey(mainCellKey = GridBuilder.this.reportModelHelper.getMainCellKey(mainCell));
                if (dsField == null) continue;
                Position c_pos = GridBuilder.this.getPosition(mainCell);
                CellMap cellMap = this.buildFloatDimension(dsModelName, dsField, parentField, c_pos);
                parentField = dsField;
                if (ObjectUtils.isEmpty(cellMap)) continue;
                cellMaps.add(cellMap);
            }
            return cellMaps;
        }

        private CellMap buildFloatDimension(String dsModelName, DSField dsField, DSField parentField, Position pos) throws Exception {
            SummaryDSModel dsModel;
            DSField orderyField;
            CellMap cellMap = null;
            cellMap = GridBuilder.this.newCellMap(dsModelName, dsField, pos);
            cellMap.setExpandMode(ExpandMode.ROWEXPANDING);
            int right = GridBuilder.this.gridHelper.getColCount() - 1;
            int bottom = pos.row();
            Region expandRegion = new Region(pos, GridBuilder.this.gridHelper.newPosition(right, bottom));
            cellMap.setExpandRegion(expandRegion);
            cellMap.setOrderMode(OrderMode.ASC);
            String keyField = dsField.getKeyField();
            if (StringUtils.hasLength(keyField) && !ObjectUtils.isEmpty(orderyField = (dsModel = GridBuilder.this.getDSModel(dsModelName)).findOrderFieldByKeyField(keyField))) {
                cellMap.setOrderValue(dsModelName + "." + orderyField.getName());
            }
            cellMap.setHierarchyMode(HierarchyMode.INDENTED);
            return cellMap;
        }

        private boolean isKeyNamePair(DSField dsField, DSField parentField) {
            if (dsField == null || parentField == null) {
                return false;
            }
            if (dsField.getKeyField() == null) {
                return false;
            }
            return dsField.getKeyField().equals(parentField.getKeyField());
        }

        private List<CellMap> buildDataCells(List<DataCell> dataCells) {
            ArrayList<CellMap> cellMaps = new ArrayList<CellMap>();
            String tableName = this.floatRegion.getTableName();
            dataCells.stream().forEach(dataCell -> {
                CellMap cellMap = GridBuilder.this.buildDataCell(tableName, dataCell);
                if (!ObjectUtils.isEmpty(cellMap)) {
                    cellMaps.add(cellMap);
                }
            });
            return cellMaps;
        }
    }

    class FixRegionBuilder {
        private final String dsModelName;

        FixRegionBuilder(String dsModelName) {
            this.dsModelName = dsModelName;
        }

        public void build() {
            List<DataCell> fixDataCells = GridBuilder.this.reportModelHelper.getFixDataCells();
            List<CellMap> cellMaps = this.buildDataCells(fixDataCells);
            GridBuilder.this.wsModel.getCellMaps().addAll(cellMaps);
        }

        private List<CellMap> buildDataCells(List<DataCell> fixDataCells) {
            ArrayList<CellMap> cellMaps = new ArrayList<CellMap>();
            fixDataCells.stream().forEach(cell -> {
                CellMap cellMap = GridBuilder.this.buildDataCell(this.dsModelName, cell);
                if (!ObjectUtils.isEmpty(cellMap)) {
                    cellMaps.add(cellMap);
                }
            });
            return cellMaps;
        }
    }
}

