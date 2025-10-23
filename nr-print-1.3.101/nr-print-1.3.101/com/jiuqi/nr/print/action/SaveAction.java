/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xlib.AbstractAction
 */
package com.jiuqi.nr.print.action;

import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.service.Impl.PrintDesignExtendServiceImpl;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xlib.AbstractAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

public class SaveAction
extends AbstractAction {
    private final String designerId;
    private final PrintDesignExtendServiceImpl designerService;
    private final IDesignTimePrintController printController;

    public SaveAction(String name, String designerId, PrintDesignExtendServiceImpl designerService, IDesignTimePrintController printController) {
        super(name);
        this.designerId = designerId;
        this.designerService = designerService;
        this.printController = printController;
    }

    public void refreshStatus() {
        this.setEnable(true);
    }

    public void dispose() {
    }

    protected boolean doRun() {
        DesignerInfoDTO info = this.designerService.getPrintDesignerInfo(this.designerId);
        ITemplateDocument document = this.designerService.getTemplateDocument(this.designerId);
        if (null == info || null == document) {
            return false;
        }
        String template = SerializeUtil.serialize((ITemplateObject)document);
        if (info.isCoverTemplate()) {
            this.printController.updateCoverTemplateDocument(info.getPrintSchemeId(), document);
        } else if (info.isCommonTemplate()) {
            String key = StringUtils.hasText(info.getPrintTemplateId()) ? info.getPrintTemplateId() : info.getPrintSchemeId();
            this.updateLinkedForms(info);
            this.printController.updateCommonTemplateDocument(info.getPrintSchemeId(), key, true, document);
        } else {
            DesignPrintTemplateDefine define = this.printController.getPrintTemplateBySchemeAndForm(info.getPrintSchemeId(), info.getFormId());
            boolean isInsert = false;
            if (null == define) {
                define = this.printController.initPrintTemplate();
                define.setPrintSchemeKey(info.getPrintSchemeId());
                define.setFormKey(info.getFormId());
                define.setTemplateData(template.getBytes());
                define.setAutoRefreshForm(!info.isCustomGrid());
                define.setFormUpdateTime(info.getCustomGuidDate());
                if (DesignerInfoDTO.DesignerVersion.V2 == info.getDesignerVersion()) {
                    define.setComTemCode("DEFAULT");
                }
                isInsert = true;
            } else {
                define.setTemplateData(template.getBytes());
                define.setAutoRefreshForm(!info.isCustomGrid());
                define.setFormUpdateTime(info.getCustomGuidDate());
            }
            if (null == info.getDesignerVersion() || DesignerInfoDTO.DesignerVersion.V1 == info.getDesignerVersion()) {
                define.setComTemCode(null);
            } else if (info.isLinkedChange()) {
                define.setComTemCode(info.getLinkedCommonCode());
            }
            define.setUpdateTime(new Date());
            if (isInsert) {
                this.printController.insertPrintTemplate(define);
            } else {
                this.printController.updatePrintTemplate(define);
            }
        }
        info.setOriginTemplate(template);
        info.setLinkedChange(false);
        info.setResetTemplate(false);
        this.designerService.updatePrintDesignerInfo(this.designerId, info);
        return true;
    }

    private void updateLinkedForms(DesignerInfoDTO info) {
        ITemplateDocument document;
        DesignPrintTemplateDefine define;
        if (!info.isLinkedChange()) {
            return;
        }
        Collection<String> oldLinkedForms = this.designerService.getLinkedForms(info.getPrintSchemeId(), info.getPrintTemplateId());
        Collection<String> linkedForms = info.getLinkedForms();
        ArrayList<String> unlinkedForms = new ArrayList<String>(oldLinkedForms);
        unlinkedForms.removeAll(linkedForms);
        linkedForms.removeAll(oldLinkedForms);
        ArrayList<DesignPrintTemplateDefine> inserts = new ArrayList<DesignPrintTemplateDefine>();
        ArrayList<DesignPrintTemplateDefine> updates = new ArrayList<DesignPrintTemplateDefine>();
        DesignPrintComTemDefine comTem = this.printController.getPrintComTem(info.getPrintTemplateId());
        String commonCode = comTem == null ? "DEFAULT" : comTem.getCode();
        List defines = this.printController.listPrintTemplateByScheme(info.getPrintSchemeId());
        for (DesignPrintTemplateDefine define2 : defines) {
            if (unlinkedForms.contains(define2.getFormKey())) {
                define2.setComTemCode(null);
                define2.setUpdateTime(new Date());
                unlinkedForms.remove(define2.getFormKey());
                updates.add(define2);
                continue;
            }
            if (!linkedForms.contains(define2.getFormKey())) continue;
            define2.setComTemCode(commonCode);
            define2.setUpdateTime(new Date());
            ITemplateDocument document2 = this.printController.initTemplateDocument(info.getPrintSchemeId(), commonCode, define2.getFormKey());
            define2.setTemplateData(SerializeUtil.serialize((ITemplateObject)document2).getBytes());
            define2.setAutoRefreshForm(true);
            define2.setFormUpdateTime(null);
            linkedForms.remove(define2.getFormKey());
            updates.add(define2);
        }
        for (String unlinkedForm : unlinkedForms) {
            define = this.printController.initPrintTemplate();
            define.setPrintSchemeKey(info.getPrintSchemeId());
            define.setFormKey(unlinkedForm);
            define.setComTemCode(null);
            document = this.printController.initTemplateDocument(info.getPrintSchemeId(), define.getFormKey());
            define.setTemplateData(SerializeUtil.serialize((ITemplateObject)document).getBytes());
            define.setAutoRefreshForm(true);
            define.setFormUpdateTime(null);
            define.setUpdateTime(new Date());
            inserts.add(define);
        }
        for (String linkedForm : linkedForms) {
            define = this.printController.initPrintTemplate();
            define.setPrintSchemeKey(info.getPrintSchemeId());
            define.setFormKey(linkedForm);
            define.setComTemCode(commonCode);
            document = this.printController.initTemplateDocument(info.getPrintSchemeId(), commonCode, define.getFormKey());
            define.setTemplateData(SerializeUtil.serialize((ITemplateObject)document).getBytes());
            define.setAutoRefreshForm(true);
            define.setFormUpdateTime(null);
            define.setUpdateTime(new Date());
            inserts.add(define);
        }
        if (!inserts.isEmpty()) {
            this.printController.insertPrintTemplate(inserts.toArray(new DesignPrintTemplateDefine[0]));
        }
        if (!updates.isEmpty()) {
            this.printController.updatePrintTemplate(updates.toArray(new DesignPrintTemplateDefine[0]));
        }
    }
}

