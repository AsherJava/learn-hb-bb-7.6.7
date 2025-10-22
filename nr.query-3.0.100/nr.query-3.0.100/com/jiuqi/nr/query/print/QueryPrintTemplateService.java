/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintPaperDefine
 *  com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.nr.definition.print.vo.PrintAttributeVo
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.table.TableFontSizeConfig
 *  com.jiuqi.xg.process.table.TableLineConfig
 *  com.jiuqi.xg.process.table.TablePaginateConfig
 *  com.jiuqi.xg.process.watermark.WatermarkConfig
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.query.print;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.definition.print.vo.PrintAttributeVo;
import com.jiuqi.nr.query.block.DimensionItemScop;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.QuerySelectionType;
import com.jiuqi.nr.query.service.impl.DataQueryHelper;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.table.TableFontSizeConfig;
import com.jiuqi.xg.process.table.TableLineConfig;
import com.jiuqi.xg.process.table.TablePaginateConfig;
import com.jiuqi.xg.process.watermark.WatermarkConfig;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class QueryPrintTemplateService {
    @Autowired
    private IPrintDesignTimeController printController;
    public static Boolean isPrintPageNum = false;

    public ITemplateDocument loadQueryTemplateDocument(QueryBlockDefine queryBlockDefine, Grid2Data grid2Data, PrintAttributeVo attrVoConfig, TablePaginateConfig paginateConfig) throws Exception {
        DesignPrintTemplateSchemeDefine printScheme = this.printController.createPrintTemplateSchemeDefine();
        printScheme.setTitle(queryBlockDefine.getTitle() + "\u6253\u5370\u6a21\u677f");
        printScheme.setFormSchemeKey(null);
        printScheme.setTaskKey(null);
        printScheme.setUpdateTime(new Date());
        printScheme.setOrder(OrderGenerator.newOrder());
        printScheme.setGatherCoverData(null);
        printScheme.setCommonAttribute(null);
        PrintAttributeVo vo = attrVoConfig;
        PrintSchemeAttributeDefine attributeDefine = PrintAttributeVo.toAttributeDefine((PrintAttributeVo)vo);
        this.printController.setPrintSchemeAttribute(printScheme, attributeDefine);
        return this.createPrintTemplateData(this.printController.getPrintSchemeAttribute(printScheme), queryBlockDefine, grid2Data, paginateConfig);
    }

    private ITemplateDocument createPrintTemplateData(PrintSchemeAttributeDefine printAttribute, QueryBlockDefine blockDefine, Grid2Data grid2Data, TablePaginateConfig paginateConfig) {
        this.cellWrapLine(grid2Data);
        GridData gridData = PrintUtil.grid2DataToGridData((Grid2Data)grid2Data, null);
        return this.createPrintTemplateData(printAttribute, blockDefine, blockDefine.getId(), blockDefine.getTitle(), gridData, paginateConfig);
    }

    private ITemplateDocument createPrintTemplateData(PrintSchemeAttributeDefine printAttribute, QueryBlockDefine queryBlockDefine, String reportkey, String title, GridData reportGridData, TablePaginateConfig tablePaginateConfig) {
        List<WordLabelDefine> labels = queryBlockDefine.getBlockInfo().getWordLabels();
        if (null == labels) {
            labels = new ArrayList<WordLabelDefine>();
        }
        List dim = queryBlockDefine.getQueryDimensions().stream().filter(item -> item.getLayoutType() == QueryLayoutType.LYT_CONDITION).collect(Collectors.toList());
        labels.forEach(wordLabelDefine -> {
            boolean isQueryLabel;
            if (wordLabelDefine.getText().contains("MODALTITLE")) {
                wordLabelDefine.setText(queryBlockDefine.getTitle());
            }
            if (isQueryLabel = wordLabelDefine.getText().contains("@")) {
                this.getConditionLablel(dim, (WordLabelDefine)wordLabelDefine);
            }
            if (wordLabelDefine.getText().contains("AllCONDITION")) {
                wordLabelDefine.setText(new DataQueryHelper().getBlockConditionStr(queryBlockDefine));
            }
            if (wordLabelDefine.getText().contains("TotalCount") || wordLabelDefine.getText().contains("PageNumber")) {
                isPrintPageNum = true;
            }
        });
        ITemplateDocument documentObject = PrintElementUtils.createTemplateDocument((PrintPaperDefine)printAttribute.getPaper(), labels, (WatermarkConfig)printAttribute.getMarkConfig(), (String)reportkey, (String)title, (GridData)reportGridData);
        ITemplatePage page = documentObject.getPage(0);
        ITemplateElement[] templateElements = page.getTemplateElements();
        for (int i = 0; i < templateElements.length; ++i) {
            ReportTemplateObject report;
            ITemplateElement element = templateElements[i];
            if (element instanceof ReportTemplateObject) {
                report = (ReportTemplateObject)element;
                TableFontSizeConfig fontSizeConfig = report.getFontSizeConfig();
                if (null != fontSizeConfig) {
                    fontSizeConfig.setHasSameFontSize(true);
                    fontSizeConfig.setSameFontSize(10.0);
                }
                report.setPaginateConfig(tablePaginateConfig);
                report.getResizeConfig().setHorizonResizeType(4);
                report.getResizeConfig().setVerticalResizeType(4);
                continue;
            }
            if (!(element instanceof ReportLabelTemplateObject)) continue;
            report = (ReportLabelTemplateObject)element;
            report.setReportGuid(reportkey);
        }
        return documentObject;
    }

    private void getConditionLablel(List<QueryDimensionDefine> dim, WordLabelDefine wordLabelDefine) {
        String dimName = wordLabelDefine.getText().replace("@", "");
        for (QueryDimensionDefine dimension : dim) {
            if (!StringUtil.equals((String)dimName, (String)dimension.getDimensionName())) continue;
            dimension.getDimensionName();
            String cstr = dimension.getTitle() != null ? dimension.getTitle() + "[" : " ";
            QuerySelectionType selectType = dimension.getExtensionSelectionType();
            List<QuerySelectItem> items = dimension.getSelectItems();
            for (int i = 0; i < items.size(); ++i) {
                QuerySelectItem item = items.get(i);
                String itemtitle = item.getTitle();
                if (dimension.isPeriodDim()) {
                    PeriodWrapper pw = PeriodUtil.getPeriodWrapper((String)itemtitle);
                    itemtitle = pw.toTitleString();
                }
                cstr = cstr + itemtitle;
                if (selectType == QuerySelectionType.SINGLE) {
                    String scopTitle = "";
                    scopTitle = DimensionItemScop.getTitle(dimension.getItemScop());
                    cstr = cstr + "(" + scopTitle + ")];";
                    continue;
                }
                String splitStr = selectType == QuerySelectionType.RANGE ? "-" : ",";
                cstr = cstr + (i == items.size() - 1 ? "];" : splitStr);
            }
            if (items.size() == 0) {
                if (selectType == QuerySelectionType.SINGLE) {
                    String scopTitle = DimensionItemScop.getTitle(dimension.getItemScop());
                    cstr = cstr + "(" + scopTitle + ")];";
                } else if (dimension.getTitle() != null) {
                    cstr = cstr + "];";
                }
            }
            wordLabelDefine.setText(cstr);
        }
    }

    private Grid2CellField getGrid2CellField(Grid2FieldList merges, int topRowMark, int leftRowMark) {
        for (int i = 0; i < merges.count(); ++i) {
            Grid2CellField grid2CellField = merges.get(i);
            if (grid2CellField.top != topRowMark || grid2CellField.left != leftRowMark) continue;
            return grid2CellField;
        }
        return null;
    }

    private void cellWrapLine(Grid2Data gridData) {
        for (int i = 1; i < gridData.getColumnCount(); ++i) {
            for (int j = 1; j < gridData.getRowCount(); ++j) {
                GridCellData gridCell = gridData.getGridCellData(i, j);
                int colWidth = gridData.getColumnWidth(i);
                int rowHeight = gridData.getRowHeight(j);
                if (i < gridData.getHeaderColumnCount() || j < gridData.getHeaderRowCount()) {
                    int wrapLineNum;
                    int calcRowHeight;
                    int showTextLength;
                    gridData.getGridCellData(i, j).setWrapLine(true);
                    Grid2FieldList merges = gridData.merges();
                    Grid2CellField grid2CellField = this.getGrid2CellField(merges, j, i);
                    int top = grid2CellField == null ? j : grid2CellField.top;
                    int bottom = grid2CellField == null ? j : grid2CellField.bottom;
                    int topRowMark = top;
                    int bottomRowMark = bottom;
                    if (j != topRowMark) continue;
                    int mergeRowCount = bottomRowMark - j + 1;
                    int mergeRowHeight = mergeRowCount * rowHeight;
                    int fontSize = gridCell.getFontSize();
                    int showTextSize = fontSize * (showTextLength = gridCell.getShowText() != null ? gridCell.getShowText().length() : 0) + showTextLength - 1;
                    if (showTextSize - colWidth <= 0 || (calcRowHeight = (wrapLineNum = showTextSize / colWidth + 1) * fontSize + wrapLineNum + fontSize) <= rowHeight) continue;
                    if (gridCell.isMerged()) {
                        if (mergeRowHeight >= calcRowHeight) continue;
                        int rowNowHight = calcRowHeight / mergeRowCount + 1;
                        for (int x = j; x <= bottomRowMark; ++x) {
                            gridData.setRowHeight(x, rowNowHight);
                        }
                        continue;
                    }
                    gridData.setRowHeight(j, calcRowHeight);
                    continue;
                }
                gridData.getGridCellData(i, j).setFitFontSize(true);
            }
        }
    }

    public static ReportTemplateObject createReportTemplate(double[] xywh, String reportId, String reportTitle, GridData gridData) {
        ReportTemplateObject reportElement = (ReportTemplateObject)PrintElementUtils.FACTORY.create("element_report");
        if (StringUtils.isEmpty(reportId)) {
            reportId = UUIDUtils.getKey();
        }
        if (null == reportTitle) {
            reportTitle = "";
        }
        reportElement.setID(reportId.toString() + reportTitle);
        reportElement.setX(xywh[0]);
        reportElement.setY(xywh[1]);
        reportElement.setWidth(xywh[2]);
        reportElement.setHeight(xywh[3]);
        reportElement.setReportGuid(reportId);
        reportElement.setReportTitle(reportTitle);
        reportElement.setGridData(gridData);
        TableLineConfig lineConfig = new TableLineConfig();
        lineConfig.setInsideThickness(0.1);
        lineConfig.setOutsideThickness(0.1);
        reportElement.setLineConfig(lineConfig);
        reportElement.getResizeConfig().setHorizonResizeType(6);
        reportElement.getResizeConfig().setVerticalResizeType(6);
        reportElement.getResizeConfig().setHorizonScaleLocked(false);
        reportElement.getPaginateConfig().setRowPaginateType(0);
        reportElement.getPaginateConfig().setColPaginateType(0);
        return reportElement;
    }
}

