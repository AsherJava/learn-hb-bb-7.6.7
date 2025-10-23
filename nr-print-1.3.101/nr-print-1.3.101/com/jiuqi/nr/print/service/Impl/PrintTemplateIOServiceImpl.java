/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplatePage
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.print.service.IPrintTemplateIOService;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplatePage;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PrintTemplateIOServiceImpl
implements IPrintTemplateIOService {
    private static final Logger logger = LoggerFactory.getLogger(PrintTemplateIOServiceImpl.class);
    @Autowired
    private IPrintDesignExtendService designExtendService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;

    @Override
    public void printTemplateImport(MultipartFile file, String designerId) {
        try (InputStream inputStream = file.getInputStream();){
            DesignerInfoDTO info = this.designExtendService.getPrintDesignerInfo(designerId);
            if (info.isFormTemplate() && StringUtils.hasText(info.getLinkedCommonCode())) {
                info.setLinkedChange(true);
                info.setLinkedCommonCode(null);
                this.designExtendService.updatePrintDesignerInfo(designerId, info);
            }
            String template = IOUtils.toString(inputStream, "UTF-8");
            this.designExtendService.updateTemplate(designerId, template, false);
        }
        catch (Exception e) {
            logger.error("\u6253\u5370\u6a21\u677f\u5bfc\u5165\u5931\u8d25:", e);
            throw new RuntimeException("\u89e3\u6790\u5931\u8d25");
        }
    }

    @Override
    public void printTemplateIncrementImport(MultipartFile file, String designerId) {
        block26: {
            DesignerInfoDTO info = this.designExtendService.getPrintDesignerInfo(designerId);
            if (info.isFormTemplate() && StringUtils.hasText(info.getLinkedCommonCode())) {
                try (InputStream inputStream = file.getInputStream();){
                    ITemplateDocument imt = PrintElementUtils.toTemplateDocument((byte[])IOUtils.toByteArray(inputStream));
                    ArrayList<ITemplateElement> imtCustom = new ArrayList<ITemplateElement>();
                    ReportTemplateObject imtReport = null;
                    ITemplatePage imtPage = imt.getPage(0);
                    for (ITemplateElement element : imtPage.getTemplateElements()) {
                        if (!PrintElementUtils.isLinkCommon((String)element.getID())) {
                            imtCustom.add(element);
                            continue;
                        }
                        if (!(element instanceof ReportTemplateObject)) continue;
                        imtReport = (ReportTemplateObject)element;
                    }
                    ITemplateDocument current = PrintElementUtils.toTemplateDocument((String)this.designExtendService.getCurrTemplateDocument(designerId));
                    ReportTemplateObject currReport = null;
                    ITemplatePage currPage = current.getPage(0);
                    for (ITemplateElement element : currPage.getTemplateElements()) {
                        if (!PrintElementUtils.isLinkCommon((String)element.getID())) {
                            currPage.remove(element);
                            continue;
                        }
                        if (!(element instanceof ReportTemplateObject)) continue;
                        currReport = (ReportTemplateObject)element;
                    }
                    if (null != currReport) {
                        if (null != imtReport) {
                            if (imtReport.isPaginateConfigEdit()) {
                                currReport.setPaginateConfigEdit(true);
                                currReport.setPaginateConfig(imtReport.getPaginateConfig());
                                currReport.setRowPaginateFirst(imtReport.isRowPaginateFirst());
                            }
                            if (imtReport.isResizeConfigEdit()) {
                                currReport.setResizeConfigEdit(true);
                                currReport.setResizeConfig(imtReport.getResizeConfig());
                            }
                        }
                        for (ITemplateElement element : imtCustom) {
                            if (element instanceof ReportTemplateObject) continue;
                            currPage.add(element);
                        }
                    } else {
                        for (ITemplateElement element : imtCustom) {
                            currPage.add(element);
                        }
                    }
                    this.designExtendService.updateTemplate(designerId, PrintElementUtils.toString((ITemplateDocument)current), false);
                    break block26;
                }
                catch (Exception e) {
                    logger.error("\u6253\u5370\u6a21\u677f\u5bfc\u5165\u5931\u8d25:", e);
                    throw new RuntimeException("\u89e3\u6790\u5931\u8d25");
                }
            }
            throw new RuntimeException("\u6253\u5370\u6a21\u677f\u589e\u91cf\u5bfc\u5165\u4ec5\u9650\u5173\u8054\u6bcd\u7248\u7684\u6a21\u677f");
        }
    }

    @Override
    public String printTemplateExport(String designerId) {
        return this.designExtendService.getCurrTemplateDocument(designerId);
    }

    @Override
    public String printTemplateExportName(String designerId) {
        DesignerInfoDTO information = this.designExtendService.getPrintDesignerInfo(designerId);
        return this.printTemplateExportName(information.getFormId(), information.getPrintSchemeId());
    }

    private String getFileName(DesignPrintTemplateSchemeDefine printTemplateScheme, String formId) {
        if ("coverTem".equals(formId)) {
            return printTemplateScheme.getTitle() + "_\u6c47\u603b\u5c01\u9762\u8bbe\u8ba1.xml";
        }
        if ("commonTem".equals(formId)) {
            return printTemplateScheme.getTitle() + "_\u6253\u5370\u8bbe\u7f6e.xml";
        }
        DesignFormDefine formDefine = this.designTimeViewController.getForm(formId);
        if (formDefine == null) {
            throw new RuntimeException("\u62a5\u8868\u4e0d\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5\u5f53\u524d\u62a5\u8868");
        }
        return printTemplateScheme.getTitle() + "_" + formDefine.getTitle() + "_\u6253\u5370\u6a21\u677f.xml";
    }

    @Override
    public String printTemplateExportName(String formId, String printSchemeId) {
        DesignPrintTemplateSchemeDefine printTemplateScheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeId);
        if (printTemplateScheme == null) {
            throw new RuntimeException("\u6253\u5370\u65b9\u6848\u4e0d\u5b58\u5728\uff0c\u8bf7\u68c0\u67e5\u5f53\u524d\u6253\u5370\u65b9\u6848");
        }
        return this.getFileName(printTemplateScheme, formId);
    }
}

