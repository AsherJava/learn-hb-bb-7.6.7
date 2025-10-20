/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.draw2d.FontMetrics
 *  com.jiuqi.xg.draw2d.XG
 *  com.jiuqi.xg.draw2d.geometry.Insets
 *  com.jiuqi.xg.draw2d.geometry.Rectangle
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IGraphicalElement
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.Paper
 *  com.jiuqi.xg.process.obj.DocumentTemplateObject
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xg.process.table.TableLineConfig
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xg.process.watermark.WatermarkConfig
 *  com.jiuqi.xg.process.watermark.WatermarkTemplateObject
 *  com.jiuqi.xlib.measure.ILengthUnit
 */
package com.jiuqi.nr.definition.print.common;

import com.jiuqi.grid.GridData;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.FontConvertUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.draw2d.Font;
import com.jiuqi.xg.draw2d.FontMetrics;
import com.jiuqi.xg.draw2d.XG;
import com.jiuqi.xg.draw2d.geometry.Insets;
import com.jiuqi.xg.draw2d.geometry.Rectangle;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IGraphicalElement;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.Paper;
import com.jiuqi.xg.process.obj.DocumentTemplateObject;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xg.process.table.TableLineConfig;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xg.process.watermark.WatermarkConfig;
import com.jiuqi.xg.process.watermark.WatermarkTemplateObject;
import com.jiuqi.xlib.measure.ILengthUnit;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class PrintElementUtils {
    private static final Logger logger;
    private static final Map<String, Paper> PAPER_TYPE;
    public static final ITemplateObjectFactory FACTORY;
    private static final double ELEMENT_HORIZONTAL_SPACING = 0.0;
    private static final double ELEMENT_LABEL_WIDTH = 80.0;
    private static final double ELEMENT_LABEL_HEIGHT = 8.0;

    private PrintElementUtils() {
    }

    public static Paper getPaperByName(String paperName) {
        return PAPER_TYPE.getOrDefault(paperName, Paper.A4_PAPER);
    }

    public static Paper getPaperByType(int paperType) {
        Collection<Paper> values = PAPER_TYPE.values();
        for (Paper paper : values) {
            if (paper.getSize() != paperType) continue;
            return paper;
        }
        return Paper.A4_PAPER;
    }

    public static List<Paper> getAllPapers() {
        return PAPER_TYPE.values().stream().sorted(Comparator.comparing(Paper::getSize)).collect(Collectors.toList());
    }

    public static DocumentTemplateObject createDocumentTemplate() {
        DocumentTemplateObject documentObject = (DocumentTemplateObject)FACTORY.create("document");
        documentObject.setNature("REPORT_PRINT_NATURE");
        return documentObject;
    }

    public static PageTemplateObject createPageTemplate(PrintPaperDefine paper) {
        PageTemplateObject page = (PageTemplateObject)FACTORY.create("page");
        page.setID(UUIDUtils.getKey());
        page.setPaper(PrintElementUtils.getPaperByType(paper.getPaperType()));
        page.setOrientation(paper.getDirection() == 0 ? 512 : 256);
        page.setMargins(new double[]{paper.getMarginTop(), paper.getMarginBottom(), paper.getMarginLeft(), paper.getMarginRight()});
        return page;
    }

    public static ReportTemplateObject createReportTemplate(Rectangle rectangle, FormDefine currReport) {
        String reportId = currReport.getKey();
        String reportTitle = currReport.getTitle();
        GridData gridData = new GridData();
        byte[] data = currReport.getBinaryData();
        if (null != data && data.length > 0) {
            PrintUtil.grid2DataToGridData(Grid2Data.bytesToGrid((byte[])data), gridData);
        }
        return PrintElementUtils.createReportTemplate(rectangle, reportId, reportTitle, gridData);
    }

    public static ReportTemplateObject createReportTemplate(Rectangle rectangle, String reportId, String reportTitle, GridData gridData) {
        ReportTemplateObject reportElement = (ReportTemplateObject)FACTORY.create("element_report");
        reportElement.setID(StringUtils.hasText(reportId) ? reportId : UUIDUtils.getKey());
        reportElement.setX(rectangle.getX());
        reportElement.setY(rectangle.getY());
        reportElement.setWidth(rectangle.getWidth());
        reportElement.setHeight(rectangle.getHeight());
        reportElement.setReportGuid(reportId);
        reportElement.setReportTitle(reportTitle);
        reportElement.setGridData(gridData);
        TableLineConfig lineConfig = new TableLineConfig();
        lineConfig.setInsideThickness(0.1);
        lineConfig.setOutsideThickness(0.1);
        reportElement.setLineConfig(lineConfig);
        reportElement.getResizeConfig().setHorizonResizeType(4);
        reportElement.getResizeConfig().setVerticalResizeType(4);
        reportElement.getResizeConfig().setHorizonScaleLocked(false);
        reportElement.getPaginateConfig().setRowPaginateType(0);
        reportElement.getPaginateConfig().setColPaginateType(0);
        reportElement.setBackgroundVisible(true);
        return reportElement;
    }

    private static ReportLabelTemplateObject createWordLabelTemplate(WordLabelDefine labelDefine) {
        ReportLabelTemplateObject labelElement = (ReportLabelTemplateObject)FACTORY.create("element_reportLabel");
        labelElement.setID(UUIDUtils.getKey());
        labelElement.setContent(labelDefine.getText());
        labelElement.setCharSet("UTF-16");
        labelElement.setFont(FontConvertUtil.getDraw2ndFont(labelDefine.getFont()));
        labelElement.setAutoSize(false);
        labelElement.setAutoWrap(labelDefine.isAutoWrap());
        labelElement.setLetterSpace((int)labelDefine.getLetterSpace());
        labelElement.setLineSpace(labelDefine.getLineSpace());
        labelElement.setLocation(labelDefine.getLocationCode());
        switch (labelDefine.getHorizontalPos()) {
            case 1: {
                labelElement.setHorizonAlignment(0x1000000);
                break;
            }
            case 2: {
                labelElement.setHorizonAlignment(131072);
                break;
            }
            default: {
                labelElement.setHorizonAlignment(16384);
            }
        }
        labelElement.setDrawScope(labelDefine.getScope());
        return labelElement;
    }

    public static ReportLabelTemplateObject createWordLabelTemplate(Rectangle rectangle, WordLabelDefine labelDefine) {
        ReportLabelTemplateObject labelElement = PrintElementUtils.createWordLabelTemplate(labelDefine);
        labelElement.setID(UUIDUtils.getKey());
        labelElement.setX(rectangle.getX());
        labelElement.setY(rectangle.getY());
        labelElement.setWidth(rectangle.getWidth());
        labelElement.setHeight(rectangle.getHeight());
        return labelElement;
    }

    public static ReportLabelTemplateObject createWordLabelTemplate(double x, double y, WordLabelDefine labelDefine) {
        ReportLabelTemplateObject labelElement = PrintElementUtils.createWordLabelTemplate(labelDefine);
        labelElement.setID(UUIDUtils.getKey());
        labelElement.setX(x);
        labelElement.setY(y);
        PrintElementUtils.calculateWidthAndHeight(labelElement.getFont(), labelElement.getInsets(), labelElement.getContent(), (w, h) -> {
            labelElement.setWidth(w);
            labelElement.setHeight(h);
        });
        return labelElement;
    }

    public static void calculateWidthAndHeight(Font font, Insets insets, String content, DoubleBiConsumer consumer) {
        if (null == font || null == consumer || !StringUtils.hasLength(content)) {
            return;
        }
        ILengthUnit lengthUnit = XG.DEFAULT_LENGTH_UNIT;
        Font clone = font.clone();
        clone.setSize(lengthUnit.fromPoint(font.getSize()));
        FontMetrics metrics = FontMetrics.getMetrics((Font)clone, (ILengthUnit)lengthUnit);
        String[] split = content.split("\n");
        double fontHeight = metrics.getFontHeight() * (double)split.length;
        double fontWidth = 0.0;
        for (String str : split) {
            double width = metrics.getStringWidth(str);
            if (!(width > fontWidth)) continue;
            fontWidth = width;
        }
        if (null != insets) {
            fontHeight = fontHeight + insets.getTop() + insets.getBottom();
            fontWidth = fontWidth + insets.getLeft() + insets.getRight();
        }
        consumer.accept(fontWidth, fontHeight);
    }

    public static ITemplateDocument createTemplateDocument(PrintSchemeAttributeDefine attribute, String reportGuid, String reportTitle, byte[] reportData) {
        GridData gridData = new GridData();
        if (null != reportData && reportData.length > 0) {
            PrintUtil.grid2DataToGridData(Grid2Data.bytesToGrid((byte[])reportData), gridData);
        }
        return PrintElementUtils.createTemplateDocument(attribute, reportGuid, reportTitle, gridData);
    }

    public static ITemplateDocument createTemplateDocument(PrintSchemeAttributeDefine attribute, String reportGuid, String reportTitle, Grid2Data reportData) {
        GridData gridData = new GridData();
        if (null != reportData) {
            PrintUtil.grid2DataToGridData(reportData, gridData);
        }
        return PrintElementUtils.createTemplateDocument(attribute, reportGuid, reportTitle, gridData);
    }

    public static ITemplateDocument createTemplateDocument(PrintSchemeAttributeDefine attribute, FormDefine report) {
        GridData gridData = new GridData();
        byte[] data = report.getBinaryData();
        if (null != data && data.length > 0) {
            PrintUtil.grid2DataToGridData(Grid2Data.bytesToGrid((byte[])data), gridData);
        }
        return PrintElementUtils.createTemplateDocument(attribute, report.getKey(), report.getTitle(), gridData);
    }

    public static ITemplateDocument createTemplateDocument(PrintSchemeAttributeDefine attribute, String reportGuid, String reportTitle, GridData reportData) {
        return PrintElementUtils.createTemplateDocument(attribute.getPaper(), attribute.getWordLabels(), attribute.getMarkConfig(), reportGuid, reportTitle, reportData);
    }

    public static ITemplateDocument createTemplateDocument(PrintPaperDefine paper, List<WordLabelDefine> wordLabels, WatermarkConfig watermarkConfig, String reportGuid, String reportTitle, GridData reportData) {
        DocumentTemplateObject documentObject = PrintElementUtils.createDocumentTemplate();
        PageTemplateObject pageObject = PrintElementUtils.createPageTemplate(paper);
        documentObject.add((ITemplatePage)pageObject);
        double[] margins = pageObject.getMargins();
        double tlx = margins[2];
        double tly = margins[0];
        double brx = pageObject.getPaper().getWidth() - margins[3];
        double bry = pageObject.getPaper().getHeight() - margins[1];
        if (!CollectionUtils.isEmpty(wordLabels)) {
            List[][][] array = new List[2][2][3];
            for (WordLabelDefine wordLabel : wordLabels) {
                if (null == array[wordLabel.getElement()][wordLabel.getVerticalPos()][wordLabel.getHorizontalPos()]) {
                    array[wordLabel.getElement()][wordLabel.getVerticalPos()][wordLabel.getHorizontalPos()] = new ArrayList();
                }
                List list = array[wordLabel.getElement()][wordLabel.getVerticalPos()][wordLabel.getHorizontalPos()];
                list.add(wordLabel);
            }
            for (int i = 0; i < array.length; ++i) {
                if (null == array[i]) continue;
                for (int j = 0; j < array[i].length; ++j) {
                    if (null == array[i][j]) continue;
                    boolean top = j == 0;
                    double ny = top ? tly : bry;
                    for (int k = 0; k < array[i][j].length; ++k) {
                        List list = array[i][j][k];
                        if (null == list) continue;
                        double y = top ? tly : bry;
                        for (WordLabelDefine label : list) {
                            if (null == label || !StringUtils.hasLength(label.getText())) continue;
                            ReportLabelTemplateObject template = PrintElementUtils.createWordLabelTemplate(label);
                            if (template.getWidth() < 80.0) {
                                template.setWidth(80.0);
                            }
                            if (template.getHeight() < 8.0) {
                                template.setHeight(8.0);
                            }
                            PrintElementUtils.setLabelX(template, label.getHorizontalPos(), tlx, brx);
                            y = PrintElementUtils.setLabelY(template, label.getVerticalPos(), y, 0.0);
                            pageObject.add((ITemplateElement)template);
                        }
                        if (top && y > ny) {
                            ny = y;
                            continue;
                        }
                        if (top || !(y < ny)) continue;
                        ny = y;
                    }
                    if (top) {
                        tly = ny;
                        continue;
                    }
                    bry = ny;
                }
            }
        }
        Rectangle table = new Rectangle(tlx, tly, brx - tlx, Math.abs(bry - tly));
        ReportTemplateObject reportElement = PrintElementUtils.createReportTemplate(table, reportGuid, reportTitle, reportData);
        pageObject.add((ITemplateElement)reportElement);
        WatermarkTemplateObject.createWatermark((PageTemplateObject)pageObject, (WatermarkConfig)watermarkConfig);
        return documentObject;
    }

    private static double setLabelY(ReportLabelTemplateObject template, int pos, double y, double spacing) {
        switch (pos) {
            case 0: {
                template.setY(y);
                y = y + spacing + template.getHeight();
                break;
            }
            case 1: {
                template.setY(y - template.getHeight());
                y = y - spacing - template.getHeight();
                break;
            }
        }
        return y;
    }

    private static void setLabelX(ReportLabelTemplateObject template, int pos, double tlx, double brx) {
        switch (pos) {
            case 0: {
                template.setX(tlx);
                break;
            }
            case 1: {
                template.setX((Math.abs(brx - tlx) - template.getWidth()) / 2.0);
                break;
            }
            case 2: {
                template.setX(brx - template.getWidth());
                break;
            }
        }
    }

    public static ReportTemplateObject getReportTemplate(ITemplateDocument document) {
        if (null == document) {
            return null;
        }
        for (ITemplatePage page : document.getPages()) {
            ITemplateElement[] elements;
            for (ITemplateElement element : elements = page.getTemplateElements()) {
                if (!(element instanceof ReportTemplateObject)) continue;
                return (ReportTemplateObject)element;
            }
        }
        return null;
    }

    public static ReportTemplateObject getReportTemplate(PageTemplateObject page) {
        ITemplateElement[] elements;
        if (null == page) {
            return null;
        }
        for (ITemplateElement element : elements = page.getTemplateElements()) {
            if (!(element instanceof ReportTemplateObject)) continue;
            return (ReportTemplateObject)element;
        }
        return null;
    }

    public static double[] calculateTableVertical(double[] y, double[] h) {
        if (y.length == 0 || y.length != h.length) {
            return new double[]{10.0, 100.0};
        }
        int[] t = new int[y.length];
        int i = 0;
        while (i < y.length) {
            int n = 0;
            for (int j = 0; j < y.length; ++j) {
                if (i == j || !(y[i] >= y[j])) continue;
                ++n;
            }
            t[n] = i++;
        }
        for (i = 0; i < t.length - 1; ++i) {
            if (!(h[t[i]] > h[t[i + 1]])) continue;
            h[t[i + 1]] = h[t[i]];
        }
        double[] d = new double[h.length - 1];
        for (int i2 = 0; i2 < t.length - 1; ++i2) {
            d[i2] = y[t[i2 + 1]] - h[t[i2]];
        }
        double maxH = 0.0;
        double maxY = 0.0;
        for (int i3 = 0; i3 < d.length; ++i3) {
            if (!(maxH < d[i3])) continue;
            maxH = d[i3];
            maxY = h[t[i3]];
        }
        return new double[]{maxY, maxH};
    }

    public static String toString(ITemplateDocument document) {
        return SerializeUtil.serialize((ITemplateObject)document);
    }

    public static byte[] toByteArray(ITemplateDocument document) {
        return PrintElementUtils.toString(document).getBytes();
    }

    public static ITemplateDocument toTemplateDocument(PrintTemplateDefine define, Supplier<ITemplateDocument> common, Supplier<Grid2Data> grid) {
        byte[] data = define.getTemplateData();
        if (null != data && data.length > 0) {
            ITemplateDocument document = PrintElementUtils.toTemplateDocument(data);
            if (define.isAutoRefreshForm()) {
                PrintElementUtils.refreshGridData(document, grid.get());
            }
            return document;
        }
        return PrintElementUtils.toTemplateDocument(common.get(), grid.get());
    }

    public static ITemplateDocument toTemplateDocument(String data) {
        return (ITemplateDocument)SerializeUtil.deserialize((String)data, (ITemplateObjectFactory)FACTORY);
    }

    public static ITemplateDocument toTemplateDocument(byte[] data) {
        return (ITemplateDocument)SerializeUtil.deserialize((String)new String(data), (ITemplateObjectFactory)FACTORY);
    }

    public static boolean isLinkCommon(String id) {
        return id.startsWith("commonTem_");
    }

    public static String linkCommon(String id) {
        return "commonTem_" + id;
    }

    public static void linkCommon(ITemplateDocument template) {
        for (ITemplatePage page : template.getPages()) {
            for (IGraphicalElement element : page.getGraphicalElements()) {
                element.setID(PrintElementUtils.linkCommon(element.getID()));
            }
        }
    }

    public static ITemplateDocument toTemplateDocument(ITemplateDocument common, Grid2Data style) {
        PrintElementUtils.linkCommon(common);
        PrintElementUtils.refreshGridData(common, style);
        return common;
    }

    private static void refreshGridData(ITemplateDocument common, Grid2Data style) {
        ReportTemplateObject reportTemplate = PrintElementUtils.getReportTemplate(common);
        if (reportTemplate != null) {
            GridData gridData = new GridData();
            if (null != style) {
                PrintUtil.grid2DataToGridData(style, gridData);
            }
            reportTemplate.setGridData(gridData);
        }
    }

    public static ITemplateDocument newTemplateDocument() {
        DocumentTemplateObject documentObject = PrintElementUtils.createDocumentTemplate();
        PageTemplateObject page = new PageTemplateObject();
        page.setID(com.jiuqi.nvwa.definition.common.UUIDUtils.getKey());
        page.setOrientation(512);
        page.setPaper(new Paper());
        documentObject.add((ITemplatePage)page);
        return documentObject;
    }

    public static ITemplateDocument newTemplateDocument(PrintSchemeAttributeDefine attr) {
        GridData gridData = GridData.base64ToGrid((String)GridData.gridToBase64((GridData)new GridData()));
        return PrintElementUtils.createTemplateDocument(attr, null, null, gridData);
    }

    private static /* synthetic */ void lambda$createTemplateDocument$1(ReportLabelTemplateObject template, double w, double h) {
        template.setWidth(w);
        template.setHeight(h);
    }

    static {
        Field[] fields;
        logger = LoggerFactory.getLogger(PrintElementUtils.class);
        PAPER_TYPE = new HashMap<String, Paper>();
        for (Field field : fields = Paper.class.getDeclaredFields()) {
            if (!field.getType().equals(Paper.class)) continue;
            try {
                Paper paper = (Paper)field.get(Paper.class);
                if (paper == null) continue;
                PAPER_TYPE.put(paper.getName(), paper);
            }
            catch (IllegalAccessException | IllegalArgumentException e) {
                logger.error("\u83b7\u53d6\u6253\u5370\u7eb8\u5f20\u7c7b\u578b\u5931\u8d25", e);
            }
        }
        FACTORY = GraphicalFactoryManager.getTemplateObjectFactory((String)"REPORT_PRINT_NATURE");
    }

    @FunctionalInterface
    public static interface DoubleBiConsumer {
        public void accept(double var1, double var3);
    }
}

