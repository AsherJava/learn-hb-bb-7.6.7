/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.dto.ReportLabelDTO;
import com.jiuqi.nr.print.helper.EnhancedReportPrintServiceHelper;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PrintDesignForwardServiceImpl
extends EnhancedReportPrintServiceHelper
implements IPrintDesignExtendService {
    public String init(String nature, String id) {
        return this.getEhancedReportPrintService().init(nature, id);
    }

    public String load(String id, String content) {
        return this.getEhancedReportPrintService().load(id, content);
    }

    public void destoryDesigner(String id) {
        this.getEhancedReportPrintService().destoryDesigner(id);
    }

    public String getAllProterty(String designerId, String modelId) {
        return this.getEhancedReportPrintService().getAllProterty(designerId, modelId);
    }

    public String getProterty(String designerId, String modelId, String propertyName) {
        return this.getEhancedReportPrintService().getProterty(designerId, modelId, propertyName);
    }

    public String updateProterty(String designerId, String modelId, String content) {
        return this.getEhancedReportPrintService().updateProterty(designerId, modelId, content);
    }

    public String doAction(String designerId, String actionName) {
        return this.getEhancedReportPrintService().doAction(designerId, actionName);
    }

    public String message(String designerId, String body) {
        return this.getEhancedReportPrintService().message(designerId, body);
    }

    @Override
    public DesignerInfoDTO getPrintDesignerInfo(String designerId) {
        return this.getEhancedReportPrintService().getPrintDesignerInfo(designerId);
    }

    @Override
    public void updatePrintDesignerInfo(String designerId, DesignerInfoDTO info) {
        this.getEhancedReportPrintService().updatePrintDesignerInfo(designerId, info);
    }

    @Override
    public void updateLinkedComTem(String designerId, String commonCode) {
        this.getEhancedReportPrintService().updateLinkedComTem(designerId, commonCode);
    }

    @Override
    public String getCurrTemplateDocument(String designerId) {
        return this.getEhancedReportPrintService().getCurrTemplateDocument(designerId);
    }

    @Override
    public boolean templateIsSave(String designerId) {
        return this.getEhancedReportPrintService().templateIsSave(designerId);
    }

    public String initPrintViewer(String nature, String id) {
        return this.getEhancedReportPrintService().initPrintViewer(nature, id);
    }

    public String loadViewDocument(String id, String content) {
        return this.getEhancedReportPrintService().loadViewDocument(id, content);
    }

    public void destoryPrintViewer(String id) {
        this.getEhancedReportPrintService().destoryPrintViewer(id);
    }

    public String getAllProtertyByViewer(String viewererId, String modelId) {
        return this.getEhancedReportPrintService().getAllProtertyByViewer(viewererId, modelId);
    }

    public String getProtertyByViewer(String viewerId, String modelId, String propertyName) {
        return this.getEhancedReportPrintService().getProtertyByViewer(viewerId, modelId, propertyName);
    }

    public String updateProtertyByViewer(String viewerId, String modelId, String content) {
        return this.getEhancedReportPrintService().updateProtertyByViewer(viewerId, modelId, content);
    }

    public String getProtertiesByViewer(String content) {
        return this.getEhancedReportPrintService().getProtertiesByViewer(content);
    }

    public String updateProtertiesByViewer(String content) {
        return this.getEhancedReportPrintService().updateProtertiesByViewer(content);
    }

    public String viewerMessage(String viewerId, String body) {
        return this.getEhancedReportPrintService().viewerMessage(viewerId, body);
    }

    public String doActionByViewer(String designerId, String actionName) {
        return this.getEhancedReportPrintService().doActionByViewer(designerId, actionName);
    }

    public String getDefaultPrinter(String viewererId) {
        return this.getEhancedReportPrintService().getDefaultPrinter(viewererId);
    }

    public String getAvailablePrinterNames(String viewererId) {
        return this.getEhancedReportPrintService().getAvailablePrinterNames(viewererId);
    }

    public String getPrinterInfo(String viewererId) {
        return this.getEhancedReportPrintService().getPrinterInfo(viewererId);
    }

    public String showPrinterPreference(String viewererId) {
        return this.getEhancedReportPrintService().showPrinterPreference(viewererId);
    }

    public String printAsyncWork(String printerID, String workName) {
        return this.getEhancedReportPrintService().printAsyncWork(printerID, workName);
    }

    public String printAsyncWorkResult(String printerID, String workName, String result) {
        return this.getEhancedReportPrintService().printAsyncWorkResult(printerID, workName, result);
    }

    public String getImageUrl(String designerId, String resourceId) {
        return this.getEhancedReportPrintService().getImageUrl(designerId, resourceId);
    }

    @Override
    public void upload(String designerId, String elementId, String fileName, byte[] fileData) {
        this.getEhancedReportPrintService().upload(designerId, elementId, fileName, fileData);
    }

    @Override
    public String getCurrPrintSchemeKey(String designerId) {
        return this.getEhancedReportPrintService().getCurrPrintSchemeKey(designerId);
    }

    @Override
    public String getCurrFormKey(String designerId) {
        return this.getEhancedReportPrintService().getCurrFormKey(designerId);
    }

    @Override
    public void updateTemplate(String designerId, String templateStr, boolean updateGrid) {
        this.getEhancedReportPrintService().updateTemplate(designerId, templateStr, updateGrid);
    }

    @Override
    public List<ReportLabelDTO> updateReportLabel(String designerId, ReportLabelDTO oldLabel, ReportLabelDTO newLabel) {
        return this.getEhancedReportPrintService().updateReportLabel(designerId, oldLabel, newLabel);
    }

    @Override
    public String getTableGrid(String designerId, String elementId) {
        return this.getEhancedReportPrintService().getTableGrid(designerId, elementId);
    }

    @Override
    public void updateTableGrid(String designerId, String elementId, byte[] grid) {
        this.getEhancedReportPrintService().updateTableGrid(designerId, elementId, grid);
    }

    @Override
    public Map<String, Object> getAttribute(String designerId) {
        return this.getEhancedReportPrintService().getAttribute(designerId);
    }
}

