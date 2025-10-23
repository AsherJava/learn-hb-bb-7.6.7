/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.print.designer.PrintDesigner
 *  com.jiuqi.xg.draw2d.geometry.Rectangle
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xlib.AbstractAction
 *  com.jiuqi.xlib.ICommand
 *  com.jiuqi.xlib.command.ChangeValueCommand
 *  com.jiuqi.xlib.command.ICommandStack
 */
package com.jiuqi.nr.print.action;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.service.Impl.PrintDesignExtendServiceImpl;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.print.designer.PrintDesigner;
import com.jiuqi.xg.draw2d.geometry.Rectangle;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xlib.AbstractAction;
import com.jiuqi.xlib.ICommand;
import com.jiuqi.xlib.command.ChangeValueCommand;
import com.jiuqi.xlib.command.ICommandStack;
import java.util.Date;

public class ReloadAction
extends AbstractAction {
    private final String designerId;
    private final PrintDesignExtendServiceImpl designerService;
    private final IDesignTimePrintController printController;
    private final IDesignTimeViewController reportController;

    public ReloadAction(String name, String designerId, PrintDesignExtendServiceImpl designerService, IDesignTimePrintController printController, IDesignTimeViewController reportController) {
        super(name);
        this.designerId = designerId;
        this.designerService = designerService;
        this.printController = printController;
        this.reportController = reportController;
    }

    public void refreshStatus() {
        this.setEnable(true);
    }

    public void dispose() {
    }

    protected boolean doRun() {
        DesignerInfoDTO info = this.designerService.getPrintDesignerInfo(this.designerId);
        PrintDesigner designer = this.designerService.getPrintDesigner(this.designerId);
        if (null == info || null == designer) {
            return false;
        }
        info.setCustomGrid(false);
        info.setCustomGuidDate(new Date());
        this.designerService.updatePrintDesignerInfo(this.designerId, info);
        DesignFormDefine report = this.reportController.queryFormById(info.getFormId());
        PageTemplateObject page = (PageTemplateObject)designer.getContent().getPage(0);
        ReportTemplateObject newReportTable = PrintElementUtils.getReportTemplate((PageTemplateObject)page);
        if (newReportTable != null) {
            byte[] reportData = report.getBinaryData();
            GridData gridData = new GridData();
            if (null != reportData) {
                PrintUtil.grid2DataToGridData((Grid2Data)Grid2Data.bytesToGrid((byte[])reportData), (GridData)gridData);
            }
            newReportTable.setGridData(gridData);
            newReportTable.setReportGuid(report.getKey());
            newReportTable.setReportTitle(report.getTitle());
        } else {
            ITemplateElement[] templateElements = page.getTemplateElements();
            double[] y = new double[templateElements.length + 2];
            double[] h = new double[templateElements.length + 2];
            for (int i = 0; i < templateElements.length; ++i) {
                ITemplateElement iTemplateElement = templateElements[i];
                y[i + 1] = iTemplateElement.getY();
                h[i + 1] = iTemplateElement.getY() + iTemplateElement.getHeight();
            }
            double[] margins = page.getMargins();
            double x = margins[2];
            double width = page.getWidth() - margins[2] - margins[3];
            y[0] = 0.0;
            y[y.length - 1] = page.getHeight() - margins[1];
            h[0] = margins[0];
            h[h.length - 1] = page.getHeight();
            double[] yAndHeight = PrintElementUtils.calculateTableVertical((double[])y, (double[])h);
            newReportTable = PrintElementUtils.createReportTemplate((Rectangle)new Rectangle(x, yAndHeight[0], width, yAndHeight[1]), (FormDefine)report);
        }
        ICommandStack commandStack = designer.getCommandStack();
        commandStack.execute((ICommand)new ChangeValueCommand<PageTemplateObject, ReportTemplateObject>(page, newReportTable){

            protected ReportTemplateObject getCurrentValue(PageTemplateObject targetObj) {
                return PrintElementUtils.getReportTemplate((PageTemplateObject)targetObj);
            }

            protected void setTargetValue(PageTemplateObject targetObj, ReportTemplateObject oldValue, ReportTemplateObject newValue) {
                if (oldValue != null) {
                    int index = targetObj.indexOf((ITemplateElement)oldValue);
                    targetObj.remove((ITemplateElement)oldValue);
                    if (newValue != null) {
                        targetObj.add(index, (ITemplateElement)newValue);
                    }
                } else if (newValue != null) {
                    targetObj.add((ITemplateElement)newValue);
                }
            }
        });
        return true;
    }
}

