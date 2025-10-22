/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridCell
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.grid2.Grid2CellField
 *  com.jiuqi.np.grid2.Grid2Data
 *  com.jiuqi.np.grid2.Grid2FieldList
 *  com.jiuqi.np.grid2.GridCellAddedData
 *  com.jiuqi.np.grid2.GridCellData
 *  com.jiuqi.np.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.single.core.print.IDrawResult
 *  com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xg.process.table.obj.TableTemplateObject
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package nr.single.para.print;

import com.jiuqi.grid.GridCell;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.grid2.Grid2CellField;
import com.jiuqi.np.grid2.Grid2Data;
import com.jiuqi.np.grid2.Grid2FieldList;
import com.jiuqi.np.grid2.GridCellAddedData;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.GridEnums;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.single.core.print.IDrawResult;
import com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xg.process.table.obj.TableTemplateObject;
import java.util.UUID;
import nr.single.para.print.ElementsDrawParam;
import nr.single.para.print.IElementsDrawHandler;
import nr.single.para.print.IWordLabelClassifier;
import nr.single.para.print.OrderWordLabelDrawer;
import nr.single.para.print.ReportFormConstants;
import nr.single.para.print.ReverseWordLabelDrawer;
import nr.single.para.print.WordLabelClassifier;
import org.json.JSONException;
import org.json.JSONObject;

public class ElementsDrawHandler
implements IElementsDrawHandler {
    protected WordLabelClassifier ptClassifier;
    protected WordLabelClassifier ttClassifier;
    protected WordLabelClassifier tbClassifier;
    protected WordLabelClassifier pbClassifier;

    protected void classify(WordLabelDefine[] allWordLabels) {
        this.ptClassifier = new WordLabelClassifier(allWordLabels, 0, 0);
        this.ttClassifier = new WordLabelClassifier(allWordLabels, 1, 0);
        this.tbClassifier = new WordLabelClassifier(allWordLabels, 1, 1);
        this.pbClassifier = new WordLabelClassifier(allWordLabels, 0, 1);
    }

    @Override
    public void handle(ElementsDrawParam param) {
        this.classify(param.getAllWordLabels());
        this.drawElements(param);
    }

    private void drawElements(ElementsDrawParam param) {
        PageTemplateObject page = param.getPage();
        double[] margins = page.getMargins();
        ITemplatePageStyleConfig config = param.getConfig();
        double labelHeight = config.getWordLabelHeight();
        double spacing = config.getVerticalSpacing();
        double ptStartY = margins[0];
        IDrawResult ptResult = this.drawWordLabelsFromTop(ptStartY, param, this.ptClassifier);
        page.addAllElements(ptResult.getElements());
        double ttAreaHeight = this.ttClassifier.getMaxVertiaclHeight(labelHeight, spacing);
        double ptAreaHeight = this.ptClassifier.getMaxVertiaclHeight(labelHeight, spacing);
        double tableBeginEdgeY = ptAreaHeight + ttAreaHeight + ptStartY;
        double ttStartY = tableBeginEdgeY - (labelHeight + spacing);
        IDrawResult ttResult = this.drawWordLabelsFromBottom(ttStartY, param, this.ttClassifier);
        page.addAllElements(ttResult.getElements());
        double pageEndEdgeY = 0.0;
        if (page.getOrientation() == 256) {
            pageEndEdgeY = page.getPaper().getWidth() - margins[1];
        } else if (page.getOrientation() == 512) {
            pageEndEdgeY = page.getPaper().getHeight() - margins[1];
        }
        double pbStartY = pageEndEdgeY - labelHeight;
        IDrawResult pbResult = this.drawWordLabelsFromBottom(pbStartY, param, this.pbClassifier);
        page.addAllElements(pbResult.getElements());
        double pbAreaHeight = this.pbClassifier.getMaxVertiaclHeight(labelHeight, spacing);
        double tableBottomLabelEndY = pageEndEdgeY - pbAreaHeight;
        double tbStartY = tableBottomLabelEndY - labelHeight;
        IDrawResult tbResult = this.drawWordLabelsFromBottom(tbStartY, param, this.tbClassifier);
        page.addAllElements(tbResult.getElements());
        double tbAreaHeight = this.tbClassifier.getMaxVertiaclHeight(labelHeight, spacing);
        double tableEndEdgeY = tableBottomLabelEndY - tbAreaHeight;
        double reportWidth = page.getWidth() - (margins[2] + margins[3]);
        double reportHeight = tableEndEdgeY - tableBeginEdgeY;
        TableTemplateObject reportElement = this.drawReportElement(param, tableBeginEdgeY, reportWidth, reportHeight);
        if (reportElement != null) {
            page.add((ITemplateElement)reportElement);
        }
    }

    protected TableTemplateObject drawReportElement(ElementsDrawParam param, double elementY, double reportWidth, double reportHeight) {
        DesignFormDefine currReport = param.getReport();
        if (param.getPrintSolutionType() == 1) {
            return null;
        }
        ReportTemplateObject reportElement = (ReportTemplateObject)param.getFactory().create("element_report");
        reportElement.setWidth(reportWidth);
        reportElement.setHeight(reportHeight);
        reportElement.setX(param.getLeftX());
        reportElement.setY(elementY);
        String title = currReport == null ? "\u901a\u7528\u6a21\u677f" : currReport.getTitle();
        String reportGuid = currReport == null ? UUIDUtils.getKey() : currReport.getKey();
        reportElement.setID(title + "_" + UUID.randomUUID().toString());
        this.setRelationship(reportElement, title, reportGuid);
        this.setData(param, currReport, reportElement);
        this.setPaginate(reportElement);
        this.setResize(reportElement);
        return reportElement;
    }

    private void setRelationship(ReportTemplateObject reportElement, String title, String reportGuid) {
        reportElement.setReportGuid(reportGuid);
        reportElement.setReportTitle(title);
    }

    private void setData(ElementsDrawParam param, DesignFormDefine report, ReportTemplateObject reportElement) {
        byte[] reportData;
        if (report == null) {
            return;
        }
        if (!ReportFormConstants.isIfr(report.getFormType().getValue()) && !ReportFormConstants.isQuickReport(report.getFormType().getValue()) && null != (reportData = report.getBinaryData())) {
            Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])reportData);
            GridData gridData = new GridData();
            ElementsDrawHandler.data2ToXgdata(grid2Data, gridData);
            reportElement.setGridData(gridData);
        }
    }

    public static void data2ToXgdata(Grid2Data n, GridData o) {
        o.setColCount(n.getColumnCount());
        o.setRowCount(n.getRowCount());
        o.setScrollTopCol(n.getHeaderColumnCount());
        o.setScrollTopRow(n.getHeaderRowCount());
        o.setScrollBottomCol(n.getFooterColumnCount());
        o.setScrollBottomRow(n.getFooterRowCount());
        for (int i = 0; i < n.getRowCount(); ++i) {
            o.setRowHeights(i, n.getRowHeight(i));
            o.setRowVisible(i, !n.isRowHidden(i));
            o.setRowAutoSize(i, n.isRowAutoHeight(i));
            for (int j = 0; j < n.getColumnCount(); ++j) {
                if (i == 0) {
                    o.setColWidths(j, n.getColumnWidth(j));
                    o.setColVisible(j, !n.isColumnHidden(j));
                    o.setColAutoSize(j, n.isColumnAutoWidth(j));
                }
                GridCell cell = o.getCell(j, i);
                ElementsDrawHandler.copyCellData(cell, n.getGridCellData(j, i), 0);
                o.setCell(cell);
            }
        }
        Grid2FieldList gfl = n.merges();
        Grid2CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
            }
        }
    }

    private static void copyCellData(GridCell c, GridCellData d, int direction) {
        if (direction == 0) {
            if (d.getBackGroundColor() != -1) {
                c.setBackColor(d.getBackGroundColor());
            }
            c.setBackStyle(d.getBackGroundStyle());
            if (d.getForeGroundColor() != -1) {
                c.setFontColor(d.getForeGroundColor());
            }
            c.setFontSize(d.getFontSize());
            c.setFontName(d.getFontName());
            c.setFontBold((d.getFontStyle() & 2) != 0);
            c.setFontItalic((d.getFontStyle() & 4) != 0);
            c.setFontStrikeOut((d.getFontStyle() & 0x10) != 0);
            c.setFontUnderLine((d.getFontStyle() & 8) != 0);
            c.setSilverHead(d.isSilverHead());
            if (d.getRightBorderColor() != -1) {
                c.setREdgeColor(d.getRightBorderColor());
            }
            if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 1);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 2);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 3);
            }
            if (d.getBottomBorderColor() != -1) {
                c.setBEdgeColor(d.getBottomBorderColor());
            }
            if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 1);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 2);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 3);
            }
            c.setCanSelect(d.isSelectable());
            c.setCanModify(d.isEditable());
            c.setWrapLine(d.isWrapLine());
            c.setIndent(d.getIndent());
            c.setVertAlign(d.getVertAlign());
            c.setHorzAlign(d.getHorzAlign());
            c.setVertText(d.isVertText());
            c.setShowText(d.getShowText());
            c.setCssClass(d.getEditText());
            c.setDataType(d.getDataType());
            c.setMultiLine(d.isMultiLine());
            c.setFitFontSize(d.isFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            obj = new JSONObject(d.getDataExString());
            data.setDataEx(obj);
            c.setScript(data.toString());
        } else if (direction == 1) {
            d.setBackGroundColor(c.getBackColor());
            d.setBackGroundStyle(c.getBackStyle());
            d.setForeGroundColor(c.getFontColor());
            d.setFontSize(c.getFontSize());
            d.setFontName(c.getFontName());
            int style = 0;
            if (c.getFontBold()) {
                style |= 2;
            }
            if (c.getFontItalic()) {
                style |= 4;
            }
            if (c.getFontStrikeOut()) {
                style |= 0x10;
            }
            if (c.getFontUnderLine()) {
                style |= 8;
            }
            d.setFontStyle(style);
            d.setRightBorderColor(c.getREdgeColor());
            d.setRightBorderStyle(c.getREdgeStyle() - 1);
            d.setBottomBorderColor(c.getBEdgeColor());
            d.setBottomBorderStyle(c.getBEdgeStyle() - 1);
            d.setSelectable(c.getCanSelect());
            d.setEditable(c.getCanModify());
            d.setWrapLine(c.getWrapLine());
            d.setIndent(c.getIndent());
            d.setVertAlign(c.getVertAlign());
            d.setHorzAlign(c.getHorzAlign());
            d.setVertText(c.getVertText());
            d.setShowText(c.getShowText());
            d.setEditText(c.getCssClass());
            d.setDataType(c.getDataType());
            d.setSilverHead(c.getSilverHead());
            d.setMultiLine(c.getMultiLine());
            d.setFitFontSize(c.getFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            if ((obj = data.getDataEx()) != null) {
                try {
                    d.setDataExFromString(obj.toString());
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
            }
        }
    }

    private void setResize(ReportTemplateObject reportElement) {
        reportElement.getResizeConfig().setHorizonResizeType(5);
        reportElement.getResizeConfig().setVerticalResizeType(4);
        reportElement.getResizeConfig().setHorizonScaleLocked(false);
    }

    private void setPaginate(ReportTemplateObject reportElement) {
        reportElement.getPaginateConfig().setRowPaginateType(0);
        reportElement.getPaginateConfig().setColPaginateType(1);
    }

    protected IDrawResult drawWordLabelsFromTop(double newStartY, ElementsDrawParam param, IWordLabelClassifier classifier) {
        param.setStartY(newStartY);
        return new OrderWordLabelDrawer().draw(param, classifier);
    }

    protected IDrawResult drawWordLabelsFromBottom(double newStartY, ElementsDrawParam param, IWordLabelClassifier classifier) {
        param.setStartY(newStartY);
        return new ReverseWordLabelDrawer().draw(param, classifier);
    }

    @Override
    public String getId() {
        return "_defaultHandler";
    }
}

