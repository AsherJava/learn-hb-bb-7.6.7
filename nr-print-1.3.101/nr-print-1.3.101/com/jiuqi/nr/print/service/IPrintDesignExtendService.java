/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.print.designer.IPrintDesignService
 *  com.jiuqi.print.designer.IPrintResourceService
 *  com.jiuqi.print.viewer.IPrintViewService
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplatePage
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.print.service;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.dto.ReportLabelDTO;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.print.designer.IPrintDesignService;
import com.jiuqi.print.designer.IPrintResourceService;
import com.jiuqi.print.viewer.IPrintViewService;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplatePage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface IPrintDesignExtendService
extends IPrintDesignService,
IPrintViewService,
IPrintResourceService {
    public DesignerInfoDTO getPrintDesignerInfo(String var1);

    public void updatePrintDesignerInfo(String var1, DesignerInfoDTO var2);

    public void updateLinkedComTem(String var1, String var2);

    public String getCurrTemplateDocument(String var1);

    public boolean templateIsSave(String var1);

    public String getCurrPrintSchemeKey(String var1);

    public String getCurrFormKey(String var1);

    public void updateTemplate(String var1, String var2, boolean var3);

    public List<ReportLabelDTO> updateReportLabel(String var1, ReportLabelDTO var2, ReportLabelDTO var3);

    public String getTableGrid(String var1, String var2);

    public void updateTableGrid(String var1, String var2, byte[] var3);

    public Map<String, Object> getAttribute(String var1);

    default public void upload(String designerId, String elementId, MultipartFile file) throws IOException {
        this.upload(designerId, elementId, file.getOriginalFilename(), file.getBytes());
    }

    public void upload(String var1, String var2, String var3, byte[] var4);

    public static GridData cell2GridData(CellBook cellBook) {
        Grid2Data grid2Data = new Grid2Data();
        CellBookGrid2dataConverter.cellBookToGrid2Data((CellSheet)((CellSheet)cellBook.getSheets().get(0)), (Grid2Data)grid2Data);
        if (grid2Data.isRowHidden(0) || grid2Data.isColumnHidden(0)) {
            grid2Data.setRowHidden(0, false);
            grid2Data.setColumnHidden(0, false);
        }
        GridData gridData = new GridData();
        PrintUtil.grid2DataToGridData((Grid2Data)grid2Data, (GridData)gridData);
        return gridData;
    }

    public static ITemplateElement<?> getITemplateElement(ITemplateDocument templateDocument, String elementId) {
        ITemplateElement[] elements;
        ITemplatePage page = templateDocument.getPage(0);
        for (ITemplateElement element : elements = page.getTemplateElements()) {
            if (!element.getID().equals(elementId)) continue;
            return element;
        }
        return null;
    }

    public static List<ReportLabelDTO> getLabels(ITemplateElement<?>[] templateElements) {
        if (templateElements == null) {
            return new ArrayList<ReportLabelDTO>();
        }
        ArrayList<ReportLabelDTO> list = new ArrayList<ReportLabelDTO>();
        for (ITemplateElement<?> templateElement : templateElements) {
            ReportLabelDTO reportLabel = new ReportLabelDTO();
            reportLabel.setId(templateElement.getID());
            ReportLabelTemplateObject reportLabelTemplate = null;
            if (templateElement instanceof ReportLabelTemplateObject) {
                reportLabelTemplate = (ReportLabelTemplateObject)templateElement;
            }
            if (reportLabelTemplate == null || reportLabelTemplate.getContent() == null) continue;
            reportLabel.setTitle(reportLabelTemplate.getContent());
            reportLabel.setLocation(reportLabelTemplate.getLocation());
            reportLabel.setX(templateElement.getX());
            reportLabel.setY(templateElement.getY());
            list.add(reportLabel);
        }
        list.sort((o1, o2) -> Double.compare(IPrintDesignExtendService.getDoubleOrder(o1, o2), IPrintDesignExtendService.getDoubleOrder(o2, o1)));
        return list;
    }

    public static double getDoubleOrder(ReportLabelDTO o1, ReportLabelDTO o2) {
        return Double.parseDouble(o1.getCode() + IPrintDesignExtendService.fillNum(o1.getY(), o2.getY()) + IPrintDesignExtendService.fillNum(o1.getX(), o2.getX()));
    }

    public static String fillNum(double o1, double o2) {
        String s1 = String.format("%.2f", o1);
        String s2 = String.format("%.2f", o2);
        s1 = s1.replace(".", "");
        s2 = s2.replace(".", "");
        if (s1.length() >= s2.length()) {
            return s1;
        }
        return IPrintDesignExtendService.padLeft(s1, s2.length());
    }

    public static String padLeft(String origin, int length) {
        StringBuilder originBuilder = new StringBuilder(origin);
        while (originBuilder.length() < length) {
            originBuilder.insert(0, "0");
        }
        origin = originBuilder.toString();
        return origin;
    }
}

