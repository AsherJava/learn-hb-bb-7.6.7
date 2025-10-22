/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.AdjustException
 *  com.jiuqi.bi.syntax.ast.IASTAdjustor
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.adjust.BaseAdjustor
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.WildcardRange
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.bi.syntax.ast.AdjustException;
import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.adjust.BaseAdjustor;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.WildcardRange;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.nr.data.excel.export.fml.Fml;
import com.jiuqi.nr.data.excel.export.fml.FmlContext;
import com.jiuqi.nr.data.excel.export.fml.FmlNode;
import com.jiuqi.nr.data.excel.export.grid.GridArea;
import com.jiuqi.nr.data.excel.export.grid.GridAreaInfo;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class FmlPositionAdjustor
extends BaseAdjustor
implements IASTAdjustor {
    private int currentFloatRowIndex;
    private GridArea currentGridArea;
    private FmlContext fmlContext;
    private Fml fml;

    public FmlPositionAdjustor(FmlContext fmlContext, Fml fml) {
        this.fmlContext = fmlContext;
        this.fml = fml;
    }

    public FmlPositionAdjustor(Fml fml, int currentFloatRowIndex, GridArea currentGridArea, FmlContext fmlContext) {
        this.fml = fml;
        this.currentFloatRowIndex = currentFloatRowIndex;
        this.currentGridArea = currentGridArea;
        this.fmlContext = fmlContext;
    }

    public boolean adjust(IASTNode node) throws AdjustException {
        super.adjust(node);
        if (node instanceof CellDataNode) {
            Position calPos;
            IRunTimeViewController runTimeViewController = this.fmlContext.getRunTimeViewController();
            CellDataNode cellNode = (CellDataNode)node;
            DataModelLinkColumn dataModelLinkColumn = cellNode.getDataModelLinkColumn();
            if (dataModelLinkColumn == null || dataModelLinkColumn.getReportInfo() == null) {
                return false;
            }
            String dataLinkCode = dataModelLinkColumn.getDataLinkCode();
            String formKey = dataModelLinkColumn.getReportInfo().getReportKey();
            DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefineByUniquecode(formKey, dataLinkCode);
            if (dataLinkDefine == null) {
                return false;
            }
            String dataLinkKey = dataLinkDefine.getKey();
            FmlNode fmlNodeByDataLink = this.fml.getFmlNodeByDataLink(dataLinkKey);
            if (fmlNodeByDataLink == null) {
                return false;
            }
            DataRegionKind dataRegionKind = fmlNodeByDataLink.getDataRegionKind();
            Position pos = cellNode.getCellPosition();
            GridAreaInfo gridAreaInfo = this.fmlContext.getGridAreaInfo(formKey);
            if (dataRegionKind == DataRegionKind.DATA_REGION_SIMPLE) {
                if (CollectionUtils.isEmpty(gridAreaInfo.getGridAreaList())) {
                    calPos = pos.insertRows(pos.row(), gridAreaInfo.getMoreRow());
                } else {
                    GridArea beforeFloatArea = null;
                    GridArea firstFloatArea = gridAreaInfo.getGridAreaList().get(0);
                    if (firstFloatArea.getFloatType() == 1) {
                        for (int i = 0; i < gridAreaInfo.getGridAreaList().size(); ++i) {
                            GridArea gridArea = gridAreaInfo.getGridAreaList().get(i);
                            if (pos.col() < gridArea.getOriginalLeft()) break;
                            if (pos.col() <= gridArea.getOriginalRight()) continue;
                            beforeFloatArea = gridArea;
                        }
                        if (beforeFloatArea == null) {
                            calPos = pos.insertRows(pos.row(), gridAreaInfo.getMoreRow());
                        } else {
                            calPos = pos.insertRows(pos.row(), gridAreaInfo.getMoreRow());
                            calPos = calPos.insertCols(pos.col(), beforeFloatArea.getRight() - beforeFloatArea.getOriginalRight());
                        }
                    } else {
                        for (int i = 0; i < gridAreaInfo.getGridAreaList().size(); ++i) {
                            GridArea gridArea = gridAreaInfo.getGridAreaList().get(i);
                            if (pos.row() < gridArea.getOriginalTop()) break;
                            if (pos.row() <= gridArea.getOriginalBottom()) continue;
                            beforeFloatArea = gridArea;
                        }
                        calPos = beforeFloatArea == null ? pos.insertRows(pos.row(), gridAreaInfo.getMoreRow()) : pos.insertRows(pos.row(), beforeFloatArea.getBottom() - beforeFloatArea.getOriginalBottom());
                    }
                }
            } else {
                DataRegionKind assignRegionKind = this.fml.getAssignNode().getDataRegionKind();
                if (assignRegionKind == DataRegionKind.DATA_REGION_SIMPLE) {
                    return false;
                }
                if (assignRegionKind == DataRegionKind.DATA_REGION_COLUMN_LIST) {
                    calPos = pos.insertRows(pos.row(), gridAreaInfo.getMoreRow());
                    int rowSpan = this.currentGridArea.getRowSpan();
                    int moreCol = this.currentGridArea.getLeft() - this.currentGridArea.getOriginalLeft() + (this.currentFloatRowIndex - 1) * rowSpan;
                    calPos = calPos.insertCols(pos.col(), moreCol);
                } else {
                    int rowSpan = this.currentGridArea.getRowSpan();
                    int moreRow = this.currentGridArea.getTop() - this.currentGridArea.getOriginalTop() + (this.currentFloatRowIndex - 1) * rowSpan;
                    calPos = pos.insertRows(pos.row(), moreRow);
                }
            }
            if (pos != calPos) {
                cellNode.setCellPosition(calPos);
                return true;
            }
        }
        return false;
    }

    public int adjust(List<WildcardRange> ranges) throws AdjustException {
        return 0;
    }
}

