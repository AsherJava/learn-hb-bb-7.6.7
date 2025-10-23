/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridColor
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.nr.zbquery.engine.grid;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.LayoutField;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryLayout;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.QueryOption;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class GridDataProcessor {
    private final GridData gridData;
    private final List<IProcessor> processors;
    private PageInfo pageInfo;
    private final QueryModelFinder queryModelFinder;

    public GridDataProcessor(GridData gridData, QueryModelFinder queryModelFinder) {
        this.gridData = gridData;
        this.queryModelFinder = queryModelFinder;
        this.processors = new ArrayList<IProcessor>();
    }

    public GridDataProcessor(GridData gridData, QueryModelFinder queryModelFinder, PageInfo pageInfo) {
        this(gridData, queryModelFinder);
        this.pageInfo = pageInfo;
    }

    public void process() {
        if (this.gridData == null) {
            return;
        }
        this.generateRowCheckProcessor();
        this.generateRowsColumnProcessor();
        if (!CollectionUtils.isEmpty(this.processors)) {
            this.processors.forEach(processor -> processor.process(this.gridData));
        }
    }

    private void generateRowCheckProcessor() {
        QueryOption option = this.queryModelFinder.getQueryModel().getOption();
        if (option.isDisplayRowCheck()) {
            this.processors.add(new CheckRowProcessor());
        }
    }

    private void generateRowsColumnProcessor() {
        ZBQueryModel zbQueryModel = this.queryModelFinder.getQueryModel();
        QueryOption option = this.queryModelFinder.getQueryModel().getOption();
        boolean displayRowsColumn = option.isDisplayRowsColumn();
        if (displayRowsColumn) {
            boolean total = false;
            boolean zbInCol = false;
            List<QueryDimension> layoutDimensions = this.queryModelFinder.getLayoutDimensions();
            block0: for (QueryDimension dim : layoutDimensions) {
                QueryObject queryObject = this.queryModelFinder.getQueryObject(dim.getFullName());
                FieldGroup dimGroup = (FieldGroup)queryObject;
                List<QueryObject> childObjs = dimGroup.getChildren();
                if (CollectionUtils.isEmpty(childObjs)) continue;
                for (QueryObject childObj : childObjs) {
                    DimensionAttributeField dimAttribute;
                    if (childObj instanceof FieldGroup) {
                        FieldGroup childGroupObj = (FieldGroup)childObj;
                        dimAttribute = childGroupObj.getDimAttribute();
                    } else {
                        dimAttribute = (DimensionAttributeField)childObj;
                    }
                    if (dimAttribute == null || !dimAttribute.isVisible() || !dimAttribute.isDisplaySum()) continue;
                    total = true;
                    continue block0;
                }
            }
            QueryLayout layout = zbQueryModel.getLayout();
            boolean zbVertical = layout.isZbVertical();
            List<LayoutField> cols = layout.getCols();
            if (zbVertical && !CollectionUtils.isEmpty(cols)) {
                for (LayoutField col : cols) {
                    QueryObject queryObject = this.queryModelFinder.getQueryObject(col.getFullName());
                    if ((!queryObject.isVisible() || queryObject.getType() != QueryObjectType.ZB) && queryObject.getType() != QueryObjectType.FORMULA) continue;
                    zbInCol = true;
                    break;
                }
            }
            if (!total && !zbInCol) {
                int colIndex = option.isDisplayRowCheck() ? 2 : 1;
                this.processors.add(new RowsColumnProcessor(this.pageInfo, colIndex));
            }
        }
    }

    static interface IProcessor {
        public void process(GridData var1);
    }

    static class CheckRowProcessor
    implements IProcessor {
        private static final int COL_WIDTH = 40;

        CheckRowProcessor() {
        }

        @Override
        public void process(GridData gridData) {
            gridData.insertCol(1, 1);
            gridData.setColWidths(1, 40);
            int rowCount = gridData.getRowCount();
            for (int row = 1; row < rowCount; ++row) {
                GridCell cell = gridData.getCellEx(1, row);
                cell.setType(4);
                cell.setSilverHead(true);
                if (row == 1) {
                    this.mergeLockCell(gridData);
                }
                gridData.setCell(cell);
            }
        }

        private void mergeLockCell(GridData gridData) {
            int scrollTopRow = gridData.getScrollTopRow();
            if (scrollTopRow > 1) {
                gridData.mergeCells(1, 1, 1, scrollTopRow - 1);
            }
        }
    }

    static class RowsColumnProcessor
    implements IProcessor {
        private static final String CELL_TITLE = "\u884c\u6b21";
        private static final int COL_WIDTH = 40;
        private static final float FONT_SIZE = 10.5f;
        private static final String FONT_NAME = "\u5fae\u8f6f\u96c5\u9ed1";
        private static final GridColor COLOR_DD = new GridColor(0xDDDDDD);
        private final PageInfo pageInfo;
        private final int colIndex;

        public RowsColumnProcessor(PageInfo pageInfo, int colIndex) {
            this.pageInfo = pageInfo;
            this.colIndex = colIndex;
        }

        @Override
        public void process(GridData gridData) {
            gridData.insertCol(this.colIndex, 1);
            gridData.setColWidths(this.colIndex, 40);
            int rowCount = gridData.getRowCount();
            int scrollTopRow = gridData.getScrollTopRow();
            for (int row = 1; row < rowCount; ++row) {
                GridCell cell = gridData.getCellEx(this.colIndex, row);
                if (row == 1) {
                    this.rebuildHeaderCell(cell);
                    this.mergeLockCell(gridData);
                } else if (row >= scrollTopRow) {
                    this.rebuildCell(cell);
                    int rowIndex = row - scrollTopRow + 1;
                    int value = this.pageInfo == null ? rowIndex : (this.pageInfo.getPageIndex() - 1) * this.pageInfo.getPageSize() + rowIndex;
                    cell.setInteger(value);
                }
                gridData.setCell(cell);
            }
        }

        private void mergeLockCell(GridData gridData) {
            int scrollTopRow = gridData.getScrollTopRow();
            if (scrollTopRow > 1) {
                gridData.mergeCells(1, 1, 1, scrollTopRow - 1);
            }
        }

        private void rebuildHeaderCell(GridCell cell) {
            cell.setType(1);
            cell.setCellData(CELL_TITLE);
            this.rebuildCellStyle(cell);
        }

        private void rebuildCell(GridCell cell) {
            cell.setType(2);
            this.rebuildCellStyle(cell);
        }

        private void rebuildCellStyle(GridCell cell) {
            cell.setSilverHead(true);
            cell.setFontName(FONT_NAME);
            cell.setFontSizeF(10.5f);
            cell.setHorzAlign(3);
            cell.setVertAlign(3);
            cell.setLEdgeColor(COLOR_DD);
            cell.setTEdgeColor(COLOR_DD);
            cell.setREdgeColor(COLOR_DD);
            cell.setBEdgeColor(COLOR_DD);
        }
    }
}

