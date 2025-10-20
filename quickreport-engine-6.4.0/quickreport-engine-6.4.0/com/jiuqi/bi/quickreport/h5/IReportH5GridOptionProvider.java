/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.h5;

import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.IReportInteraction;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.h5.ReportH5GridCell;
import com.jiuqi.bi.quickreport.model.OrderMode;

public interface IReportH5GridOptionProvider {
    public boolean isTransparent(int var1, int var2);

    public String getRootPath();

    public IReportEngine getReportEngine();

    default public StringBuilder buildSortHead(ReportH5GridCell cell, InteractiveInfo info) {
        IReportH5GridOptionProvider provider = cell.getProvider();
        IReportInteraction interaction = provider.getReportEngine().getInteraction();
        StringBuilder buffer = new StringBuilder();
        buffer.append(cell.getCellStyleDivPrefix());
        if (interaction != null) {
            OrderMode orderMode = interaction.getSortMode(info.getID());
            String verticalAlign = "vertical-align:" + cell.getCellVertAlign() + ";";
            buffer.append("<a href=\"javascript:void(0)\" onclick=\"dosort(this)\"");
            buffer.append("style=\"").append(verticalAlign).append("\"");
            buffer.append("class=\"sortabletd\"");
            buffer.append("tdid=\"").append(info.getID()).append("\"");
            buffer.append("mode=\"").append((Object)orderMode).append("\"");
            buffer.append(">");
            if (orderMode == OrderMode.ASC || orderMode == OrderMode.DESC) {
                String imgName = orderMode == OrderMode.ASC ? "sortup.png" : "sortdown.png";
                buffer.append("<img src=\"").append(provider.getRootPath()).append("/images/" + imgName + "\"");
                buffer.append("href=\"javascript:void(0)\" onclick=\"dosort(this)\" style=\"margin-right:2px;\"");
                buffer.append("tdid=\"").append(info.getID()).append("\"");
                buffer.append("mode=\"").append((Object)orderMode).append("\"");
                buffer.append(" />");
            }
            buffer.append(cell.getShowText());
            buffer.append("</a>");
        }
        buffer.append(cell.getCellStyleDivSuffix());
        return buffer;
    }
}

