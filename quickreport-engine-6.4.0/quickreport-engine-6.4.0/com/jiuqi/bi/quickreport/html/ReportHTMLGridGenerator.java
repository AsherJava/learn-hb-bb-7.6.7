/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.output.HTMLGridGenerator
 *  com.jiuqi.bi.grid.output.IPropertySet
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.html;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.output.HTMLGridGenerator;
import com.jiuqi.bi.grid.output.IPropertySet;
import com.jiuqi.bi.quickreport.engine.IReportInteraction;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.html.ConditionStyleUtil;
import com.jiuqi.bi.quickreport.hyperlink.HyperlinkManager;
import com.jiuqi.bi.quickreport.hyperlink.ReportHyperlinkException;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;

public final class ReportHTMLGridGenerator
extends HTMLGridGenerator {
    private IReportInteraction interaction = null;
    private static final String Q_REPORT_SCRIPT;
    private static final String Q_REPORT_SCRIPT_DEFAULT = "function q_openwindow(url) { window.open(url); }";

    public ReportHTMLGridGenerator(GridData griddata, boolean allowInput) {
        super(griddata);
    }

    public ReportHTMLGridGenerator(GridData griddata, boolean allowInput, IReportInteraction iri) {
        this(griddata, allowInput);
        this.interaction = iri;
    }

    protected void getCellTDProperties(GridCell cell, IPropertySet cellProps) {
        super.getCellTDProperties(cell, cellProps);
        cellProps.remove("rV");
    }

    protected String genTDContent(GridCell cell) {
        Object cellObj = cell.getCellObj();
        if (cellObj == null) {
            String _data = super.genTDContent(cell);
            return _data;
        }
        CellResultInfo cellResultInfo = (CellResultInfo)cellObj;
        InteractiveInfo interactionInfo = cellResultInfo.getInteractiveInfo();
        if (interactionInfo != null && interactionInfo.isSortable()) {
            String _data = this.genSimpleTDContent(cell);
            _data = this.interaction != null && this.interaction.getSortMode(interactionInfo.getID()) == OrderMode.ASC ? "<img src=\"images/sortup.png\" href=\"javascript:void(0)\" style=\"margin-right:2px;\" onclick=\"dosort(this)\" class=\"sortabletd\" tdid=" + interactionInfo.getID() + " /><a href=\"javascript:void(0)\" style=\"text-decoration:none;\" class=\"sortabletd\" onclick=\"dosort(this)\" tdid=" + interactionInfo.getID() + ">" + _data + "</a>" : (this.interaction != null && this.interaction.getSortMode(interactionInfo.getID()) == OrderMode.DESC ? "<img src=\"images/sortdown.png\"  href=\"javascript:void(0)\" style=\"margin-right:2px;\" onclick=\"dosort(this)\" class=\"sortabletd\" tdid=" + interactionInfo.getID() + " /><a href=\"javascript:void(0)\" style=\"text-decoration:none;\" class=\"sortabletd\" onclick=\"dosort(this)\" tdid=" + interactionInfo.getID() + ">" + _data + "</a>" : "<a  href=\"javascript:void(0)\" style=\"text-decoration:none;\" class=\"sortabletd\" onclick=\"dosort(this)\" tdid=" + interactionInfo.getID() + ">" + _data + "</a>");
            return _data;
        }
        if (cellResultInfo.getDataBarInfo() != null || cellResultInfo.getIconName() != null) {
            String _data = ConditionStyleUtil.creatContionStyleHTML(cell, this.genSimpleTDContent(cell));
            return _data;
        }
        String _data = this.genSimpleTDContent(cell);
        return _data;
    }

    private String genSimpleTDContent(GridCell cell) {
        return super.genTDContent(cell);
    }

    public void getRowTRProperties(int row, IPropertySet rowProps) {
        super.getRowTRProperties(row, rowProps);
    }

    public static String getLinkFunction(String contextPath, String cacheID) throws ReportHyperlinkException {
        return ReportHTMLGridGenerator.getLinkFunction(contextPath, cacheID, "ql");
    }

    public static String getLinkFunction(String contextPath, String cacheID, String funcName) throws ReportHyperlinkException {
        String func = HyperlinkManager.getHyperlinkFunction(contextPath, cacheID);
        if (StringUtils.isEmpty((String)func)) {
            return ReportHTMLGridGenerator.getDefaultLinkFunction(contextPath, cacheID, funcName);
        }
        return func;
    }

    private static String getDefaultLinkFunction(String contextPath, String cacheId, String funcName) {
        return Q_REPORT_SCRIPT + "function " + funcName + "(cellID, filterID) {\r\n\tq_existed(cellID, filterID);\r\n}";
    }

    static {
        InputStream inStream = ReportHTMLGridGenerator.class.getResourceAsStream("util.js");
        if (inStream == null) {
            Q_REPORT_SCRIPT = Q_REPORT_SCRIPT_DEFAULT;
        } else {
            String func;
            try {
                byte[] buffer = new byte[inStream.available()];
                inStream.read(buffer);
                func = new String(buffer, "UTF-8");
            }
            catch (Exception e) {
                func = Q_REPORT_SCRIPT_DEFAULT;
                e.printStackTrace();
            }
            finally {
                try {
                    inStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Q_REPORT_SCRIPT = func;
        }
    }
}

