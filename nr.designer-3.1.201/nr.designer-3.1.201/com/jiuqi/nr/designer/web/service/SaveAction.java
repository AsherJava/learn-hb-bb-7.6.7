/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.print.viewer.IPrintViewer
 *  com.jiuqi.xg.process.IDrawDocument
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.IGraphicalDocument
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xlib.AbstractAction
 *  com.jiuqi.xlib.ICallBack
 *  com.jiuqi.xlib.command.CommandStackEvent
 *  com.jiuqi.xlib.command.ICommandStackEventListener
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.xg.print.viewer.IPrintViewer;
import com.jiuqi.xg.process.IDrawDocument;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.IGraphicalDocument;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xlib.AbstractAction;
import com.jiuqi.xlib.ICallBack;
import com.jiuqi.xlib.command.CommandStackEvent;
import com.jiuqi.xlib.command.ICommandStackEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveAction
extends AbstractAction
implements ICommandStackEventListener {
    public static final String ACTION_NAME = "report_save";
    private ICallBack afterSave;
    private IPrintViewer designer;
    private static final Logger logger = LoggerFactory.getLogger(SaveAction.class);

    public SaveAction(IPrintViewer designer, ICallBack afterSave) {
        super(ACTION_NAME);
        this.setFlag(1, false);
        this.afterSave = afterSave;
        this.designer = designer;
        designer.getCommandStack().addCommandStackEventListener((ICommandStackEventListener)this);
        designer.getCommandStack().markSaveLocation();
    }

    protected boolean doRun() {
        IGraphicalDocument document = this.designer.getContent();
        String xmlDoc = null;
        if (document instanceof ITemplateDocument) {
            xmlDoc = SerializeUtil.serialize((ITemplateObject)((ITemplateDocument)document));
        } else if (document instanceof IDrawDocument) {
            xmlDoc = SerializeUtil.serialize((IDrawObject)((IDrawDocument)document));
        }
        this.designer.getCommandStack().markSaveLocation();
        this.refreshStatus();
        this.afterSave.performed(new Object[]{xmlDoc});
        logger.info(xmlDoc);
        return true;
    }

    public void dispose() {
    }

    public void refreshStatus() {
        this.setEnable(this.designer.getCommandStack().isDirty());
    }

    public boolean isEnable() {
        return this.designer.getCommandStack().isDirty();
    }

    public void stackChanged(CommandStackEvent event) {
        if (this.designer.getCommandStack().isDirty()) {
            this.setEnable(true);
        } else if (!this.designer.getCommandStack().isDirty()) {
            this.setEnable(false);
        }
    }
}

