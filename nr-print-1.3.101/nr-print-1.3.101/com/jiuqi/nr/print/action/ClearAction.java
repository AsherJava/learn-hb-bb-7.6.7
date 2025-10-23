/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xlib.AbstractAction
 */
package com.jiuqi.nr.print.action;

import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.service.Impl.PrintDesignExtendServiceImpl;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xlib.AbstractAction;
import org.springframework.util.StringUtils;

public class ClearAction
extends AbstractAction {
    private final String designerId;
    private final PrintDesignExtendServiceImpl designerService;
    private final IDesignTimePrintController printController;

    public ClearAction(String name, String designerId, PrintDesignExtendServiceImpl designerService, IDesignTimePrintController printController) {
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
        if (null == info) {
            return false;
        }
        ITemplateDocument template = null;
        template = !StringUtils.hasText(info.getFormId()) || "coverTem".equals(info.getFormId()) ? this.printController.initCoverTemplateDocument(info.getPrintSchemeId()) : ("commonTem".equals(info.getFormId()) ? this.printController.initCommonTemplateDocument(info.getPrintSchemeId()) : (StringUtils.hasText(info.getLinkedCommonCode()) ? this.printController.initTemplateDocument(info.getPrintSchemeId(), info.getLinkedCommonCode(), info.getFormId()) : this.printController.initTemplateDocument(info.getPrintSchemeId(), info.getFormId())));
        this.designerService.updateTemplate(this.designerId, template);
        info.setResetTemplate(true);
        this.designerService.updatePrintDesignerInfo(this.designerId, info);
        return true;
    }
}

