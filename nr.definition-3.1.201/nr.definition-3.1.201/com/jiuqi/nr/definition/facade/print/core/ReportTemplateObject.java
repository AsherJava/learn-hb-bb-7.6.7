/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.process.table.obj.TableTemplateObject
 *  org.json.JSONObject
 */
package com.jiuqi.nr.definition.facade.print.core;

import com.jiuqi.grid.GridData;
import com.jiuqi.xg.process.table.obj.TableTemplateObject;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ReportTemplateObject
extends TableTemplateObject {
    private static final String ATT_REPORTGUID = "reportGuid";
    private static final String ATT_REPORTTITLE = "reportTitle";
    private static final String ATT_IS_AUTOLINE_PRINT = "isAutoBorderLinePrint";
    private static final String ATT_IS_ANAL_REPORT = "isAnalReport";
    private static final String ATT_IS_ODDLORC = "isOddLoRc";
    private static final String ATT_IS_EVENLORC = "isEvenLoRc";
    private static final String ATT_IS_UNDERLINE_PRINT = "isUnderLinePrint";
    public static final String ATT_RESIZE_CONFIG_EDIT = "resizeConfigEdit";
    public static final String ATT_PAGINATE_CONFIG_EDIT = "paginateConfigEdit";
    private String reportGuid;
    private String reportTitle;
    private boolean isAutoBorderLinePrint = true;
    private boolean isUnderLinePrint = false;
    private boolean isAnalReport = false;
    private boolean isOddLoRc = false;
    private boolean isEvenLoRc = false;
    private boolean resizeConfigEdit = false;
    private boolean paginateConfigEdit = false;

    public boolean isUnderLinePrint() {
        return this.isUnderLinePrint;
    }

    public void setUnderLinePrint(boolean isUnderLinePrint) {
        boolean old = this.isUnderLinePrint;
        if (old == isUnderLinePrint) {
            return;
        }
        this.isUnderLinePrint = isUnderLinePrint;
        this.firePropertyChange(ATT_IS_UNDERLINE_PRINT, old, isUnderLinePrint);
        this.firePropertyChange("validate", false, true);
    }

    public boolean isAutoBorderLinePrint() {
        return this.isAutoBorderLinePrint;
    }

    public void setAutoBorderLinePrint(boolean isAutoBorderLinePrint) {
        boolean old = this.isAutoBorderLinePrint;
        if (old == isAutoBorderLinePrint) {
            return;
        }
        this.isAutoBorderLinePrint = isAutoBorderLinePrint;
        this.firePropertyChange(ATT_IS_AUTOLINE_PRINT, old, isAutoBorderLinePrint);
        this.firePropertyChange("validate", false, true);
    }

    public boolean isAnalReport() {
        return this.isAnalReport;
    }

    public void setAnalReport(boolean isAnalReport) {
        boolean old = this.isAnalReport;
        if (old == isAnalReport) {
            return;
        }
        this.isAnalReport = isAnalReport;
        this.firePropertyChange(ATT_IS_ANAL_REPORT, old, isAnalReport);
        this.firePropertyChange("validate", false, true);
    }

    public boolean isOddLoRc() {
        return this.isOddLoRc;
    }

    public void setOddLoRc(boolean isOddLoRc) {
        this.isOddLoRc = isOddLoRc;
    }

    public boolean isEvenLoRc() {
        return this.isEvenLoRc;
    }

    public void setEvenLoRc(boolean isEvenLoRc) {
        this.isEvenLoRc = isEvenLoRc;
    }

    public String getReportGuid() {
        return this.reportGuid;
    }

    public void setReportGuid(String reportGuid) {
        this.reportGuid = reportGuid;
    }

    public String getReportTitle() {
        return this.reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public boolean isResizeConfigEdit() {
        return this.resizeConfigEdit;
    }

    public void setResizeConfigEdit(boolean resizeConfigEdit) {
        boolean oldValue = this.isResizeConfigEdit();
        if (oldValue == resizeConfigEdit) {
            return;
        }
        this.resizeConfigEdit = resizeConfigEdit;
        this.firePropertyChange(ATT_RESIZE_CONFIG_EDIT, oldValue, resizeConfigEdit);
    }

    public boolean isPaginateConfigEdit() {
        return this.paginateConfigEdit;
    }

    public void setPaginateConfigEdit(boolean paginateConfigEdit) {
        boolean oldValue = this.isPaginateConfigEdit();
        if (oldValue == paginateConfigEdit) {
            return;
        }
        this.paginateConfigEdit = paginateConfigEdit;
        this.firePropertyChange(ATT_PAGINATE_CONFIG_EDIT, oldValue, paginateConfigEdit);
    }

    public String getKind() {
        return "element_report";
    }

    public void setGridData(GridData gridData) {
        GridData old = this.gridData;
        if (old.equals(gridData)) {
            return;
        }
        this.gridData = gridData;
        this.firePropertyChange("gridData", old, gridData);
        this.firePropertyChange("validate", false, true);
        this.refreshWidthAndHeight();
    }

    public Element serialize(Document ownerDocument) {
        Element element = super.serialize(ownerDocument);
        element.setAttribute(ATT_REPORTGUID, this.reportGuid == null ? "" : this.reportGuid);
        element.setAttribute(ATT_REPORTTITLE, this.reportTitle == null ? "" : this.reportTitle);
        element.setAttribute(ATT_IS_AUTOLINE_PRINT, String.valueOf(this.isAutoBorderLinePrint));
        element.setAttribute(ATT_IS_ANAL_REPORT, String.valueOf(this.isAnalReport));
        element.setAttribute(ATT_IS_ODDLORC, String.valueOf(this.isOddLoRc));
        element.setAttribute(ATT_IS_EVENLORC, String.valueOf(this.isEvenLoRc));
        element.setAttribute(ATT_IS_UNDERLINE_PRINT, String.valueOf(this.isUnderLinePrint));
        element.setAttribute(ATT_RESIZE_CONFIG_EDIT, String.valueOf(this.resizeConfigEdit));
        element.setAttribute(ATT_PAGINATE_CONFIG_EDIT, String.valueOf(this.paginateConfigEdit));
        return element;
    }

    public void deserialize(Element element) {
        super.deserialize(element);
        this.reportGuid = element.getAttribute(ATT_REPORTGUID);
        this.reportTitle = element.getAttribute(ATT_REPORTTITLE);
        this.isAutoBorderLinePrint = Boolean.parseBoolean(element.getAttribute(ATT_IS_AUTOLINE_PRINT));
        this.isAnalReport = Boolean.parseBoolean(element.getAttribute(ATT_IS_ANAL_REPORT));
        this.isOddLoRc = Boolean.parseBoolean(element.getAttribute(ATT_IS_ODDLORC));
        this.isEvenLoRc = Boolean.parseBoolean(element.getAttribute(ATT_IS_EVENLORC));
        this.isUnderLinePrint = Boolean.parseBoolean(element.getAttribute(ATT_IS_UNDERLINE_PRINT));
        this.resizeConfigEdit = Boolean.parseBoolean(element.getAttribute(ATT_RESIZE_CONFIG_EDIT));
        this.paginateConfigEdit = Boolean.parseBoolean(element.getAttribute(ATT_PAGINATE_CONFIG_EDIT));
    }

    public void deserialize(JSONObject templateObject) {
        super.deserialize(templateObject);
        this.resizeConfigEdit = templateObject.optBoolean(ATT_RESIZE_CONFIG_EDIT);
        this.paginateConfigEdit = templateObject.optBoolean(ATT_PAGINATE_CONFIG_EDIT);
    }

    public JSONObject serialize() {
        JSONObject object = super.serialize();
        object.put(ATT_RESIZE_CONFIG_EDIT, (Object)String.valueOf(this.resizeConfigEdit));
        object.put(ATT_PAGINATE_CONFIG_EDIT, (Object)String.valueOf(this.paginateConfigEdit));
        return object;
    }
}

