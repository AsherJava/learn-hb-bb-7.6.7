/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable
 *  com.jiuqi.nvwa.dispatch.core.task.ITaskListener
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.dto.ReportLabelDTO;
import com.jiuqi.nr.print.helper.EnhancedReportPrintHelper;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable;
import com.jiuqi.nvwa.dispatch.core.task.ITaskListener;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dispatchable(name="EnhancedReportPrintService", title="\u65b0\u62a5\u8868\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668", expireTime=300000L)
public class PrintDesignInvokeServiceImpl
implements IPrintDesignExtendService,
ITaskListener {
    private Logger logger = LoggerFactory.getLogger(PrintDesignInvokeServiceImpl.class);

    public void onTaskStart(ITaskContext context) {
        this.logger.info("\u65b0\u62a5\u8868\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668: \u521d\u59cb\u5316\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668.");
    }

    public void onTaskEnd(ITaskContext context) {
        this.logger.info("\u65b0\u62a5\u8868\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668: \u6269\u5c55\u6253\u5370\u670d\u52a1\u5668\u7ec8\u6b62.");
    }

    public String init(String nature, String id) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().init(nature, id);
    }

    public String load(String id, String content) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().load(id, content);
    }

    public void destoryDesigner(String id) {
        EnhancedReportPrintHelper.getReportPrintExtendService().destoryDesigner(id);
    }

    public String getAllProterty(String designerId, String modelId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getAllProterty(designerId, modelId);
    }

    public String getProterty(String designerId, String modelId, String propertyName) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getProterty(designerId, modelId, propertyName);
    }

    public String updateProterty(String designerId, String modelId, String content) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().updateProterty(designerId, modelId, content);
    }

    public String doAction(String designerId, String actionName) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().doAction(designerId, actionName);
    }

    public String message(String designerId, String body) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().message(designerId, body);
    }

    @Override
    public DesignerInfoDTO getPrintDesignerInfo(String designerId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getPrintDesignerInfo(designerId);
    }

    @Override
    public void updatePrintDesignerInfo(String designerId, DesignerInfoDTO info) {
        EnhancedReportPrintHelper.getReportPrintExtendService().updatePrintDesignerInfo(designerId, info);
    }

    @Override
    public void updateLinkedComTem(String designerId, String commonCode) {
        EnhancedReportPrintHelper.getReportPrintExtendService().updateLinkedComTem(designerId, commonCode);
    }

    @Override
    public String getCurrTemplateDocument(String designerId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getCurrTemplateDocument(designerId);
    }

    @Override
    public boolean templateIsSave(String designerId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().templateIsSave(designerId);
    }

    public String initPrintViewer(String nature, String id) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().initPrintViewer(nature, id);
    }

    public String loadViewDocument(String id, String content) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().loadViewDocument(id, content);
    }

    public void destoryPrintViewer(String id) {
        EnhancedReportPrintHelper.getReportPrintExtendService().destoryPrintViewer(id);
    }

    public String getAllProtertyByViewer(String viewererId, String modelId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getAllProtertyByViewer(viewererId, modelId);
    }

    public String getProtertyByViewer(String viewerId, String modelId, String propertyName) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getProtertyByViewer(viewerId, modelId, propertyName);
    }

    public String updateProtertyByViewer(String viewerId, String modelId, String content) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().updateProtertyByViewer(viewerId, modelId, content);
    }

    public String getProtertiesByViewer(String content) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getProtertiesByViewer(content);
    }

    public String updateProtertiesByViewer(String content) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().updateProtertiesByViewer(content);
    }

    public String viewerMessage(String viewerId, String body) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().viewerMessage(viewerId, body);
    }

    public String doActionByViewer(String designerId, String actionName) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().doActionByViewer(designerId, actionName);
    }

    public String getDefaultPrinter(String viewererId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getDefaultPrinter(viewererId);
    }

    public String getAvailablePrinterNames(String viewererId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getAvailablePrinterNames(viewererId);
    }

    public String getPrinterInfo(String viewererId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getPrinterInfo(viewererId);
    }

    public String showPrinterPreference(String viewererId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().showPrinterPreference(viewererId);
    }

    public String printAsyncWork(String printerID, String workName) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().printAsyncWork(printerID, workName);
    }

    public String printAsyncWorkResult(String printerID, String workName, String result) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().printAsyncWorkResult(printerID, workName, result);
    }

    public String getImageUrl(String designerId, String resourceId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getImageUrl(designerId, resourceId);
    }

    @Override
    public void upload(String designerId, String elementId, String fileName, byte[] fileData) {
        EnhancedReportPrintHelper.getReportPrintExtendService().upload(designerId, elementId, fileName, fileData);
    }

    @Override
    public String getCurrPrintSchemeKey(String designerId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getCurrPrintSchemeKey(designerId);
    }

    @Override
    public String getCurrFormKey(String designerId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getCurrFormKey(designerId);
    }

    @Override
    public void updateTemplate(String designerId, String templateStr, boolean updateGrid) {
        EnhancedReportPrintHelper.getReportPrintExtendService().updateTemplate(designerId, templateStr, updateGrid);
    }

    @Override
    public List<ReportLabelDTO> updateReportLabel(String designerId, ReportLabelDTO oldLabel, ReportLabelDTO newLabel) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().updateReportLabel(designerId, oldLabel, newLabel);
    }

    @Override
    public String getTableGrid(String designerId, String elementId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getTableGrid(designerId, elementId);
    }

    @Override
    public void updateTableGrid(String designerId, String elementId, byte[] grid) {
        EnhancedReportPrintHelper.getReportPrintExtendService().updateTableGrid(designerId, elementId, grid);
    }

    @Override
    public Map<String, Object> getAttribute(String designerId) {
        return EnhancedReportPrintHelper.getReportPrintExtendService().getAttribute(designerId);
    }
}

