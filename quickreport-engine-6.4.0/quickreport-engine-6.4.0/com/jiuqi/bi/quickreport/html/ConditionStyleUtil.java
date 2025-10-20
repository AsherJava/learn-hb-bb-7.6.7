/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridColor
 *  com.jiuqi.bi.logging.LogManager
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.html;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.logging.LogManager;
import com.jiuqi.bi.quickreport.engine.result.CellResultInfo;
import com.jiuqi.bi.quickreport.engine.result.DataBarInfo;
import com.jiuqi.bi.quickreport.model.DataBarMode;
import com.jiuqi.bi.util.StringUtils;

public class ConditionStyleUtil {
    private ConditionStyleUtil() {
    }

    public static String creatContionStyleHTML(GridCell cell, String tdContent) {
        try {
            StringBuilder mainContainerBuffer = new StringBuilder();
            int cellWidth = cell.getCellWidth();
            int cellHeight = cell.getCellHeight();
            mainContainerBuffer.append("<div class='mainContainer' style='background-color:inherit;height:" + cellHeight + "px;width:" + cellWidth + "px;position: relative;left:-4px;'>");
            mainContainerBuffer.append("<div class='contentContainer' style='height:100%;width:" + (cellWidth - 2) + "px;left:0;top:0;position: absolute;z-index:110;'>");
            mainContainerBuffer.append("<table style='width:100%;height:100%' cellpadding='1' cellspacing='0'><tr><td style='" + ConditionStyleUtil.getCellStyle(cell) + "' >" + tdContent + "</td></tr></table>");
            mainContainerBuffer.append("</div>");
            CellResultInfo cellResultInfo = (CellResultInfo)cell.getCellObj();
            DataBarInfo dataBarInfo = cellResultInfo.getDataBarInfo();
            if (dataBarInfo != null) {
                String dataBarStyle = ConditionStyleUtil.getBarStyleContent(cell, dataBarInfo, cellResultInfo.getRawValue());
                if (!StringUtils.isEmpty((String)dataBarStyle)) {
                    mainContainerBuffer.append(dataBarStyle);
                }
            } else {
                mainContainerBuffer.append(ConditionStyleUtil.geySignalLampStyleContent(cellResultInfo.getIconName(), tdContent));
            }
            mainContainerBuffer.append("</div>");
            return mainContainerBuffer.toString();
        }
        catch (Exception e) {
            LogManager.getLogger(ConditionStyleUtil.class).error("\u751f\u6210\u9884\u8b66\u5355\u5143\u683cHTML\u9519\u8bef", (Throwable)e);
            return tdContent;
        }
    }

    private static String getCellStyle(GridCell cell) {
        StringBuilder sb = new StringBuilder();
        int fontSize = cell.getFontSize();
        sb.append("font-size:").append(fontSize).append("pt").append(";");
        sb.append("text-align:").append(ConditionStyleUtil.getCellHorzAlign(cell)).append(";");
        return sb.toString();
    }

    private static String getCellHorzAlign(GridCell cell) {
        switch (cell.getHorzAlign()) {
            case 2: {
                return "right";
            }
            case 3: {
                return "center";
            }
            case 1: {
                return "left";
            }
        }
        return "";
    }

    public static String getBarStyleContent(GridCell cell, DataBarInfo dataBarInfo, Object rawValue) {
        int positiveWidth;
        int negativeWidth;
        int positiveContainerWidth;
        int negativeContainerWidth;
        if (rawValue == null) {
            return null;
        }
        String cellData = rawValue.toString();
        int color = dataBarInfo.getForegroundColor();
        double min = dataBarInfo.getMinValue();
        double max = dataBarInfo.getMaxValue();
        double value = 0.0;
        try {
            value = Double.parseDouble(cellData);
        }
        catch (Exception e) {
            return "";
        }
        if (dataBarInfo.getMode().equals((Object)DataBarMode.PERCENTAGE)) {
            if (min < 0.0) {
                min = -1.0;
                max = 1.0;
            } else {
                min = 0.0;
                max = 1.0;
            }
        }
        if (min < 0.0) {
            negativeContainerWidth = (int)Math.floor(-min / (max - min) * 100.0);
            positiveContainerWidth = 100 - negativeContainerWidth;
        } else {
            negativeContainerWidth = 0;
            positiveContainerWidth = 100;
        }
        if (value >= 0.0) {
            negativeWidth = 0;
            positiveWidth = value > max ? 100 : (int)Math.floor(value / max * 100.0);
        } else {
            negativeWidth = value < min || min == 0.0 ? 100 : (int)Math.floor(value / min * 100.0);
            positiveWidth = 0;
        }
        StringBuilder barContainerBuffer = new StringBuilder();
        barContainerBuffer.append("<div class='barContainer' style='background-color:inherit;height:100%;width:100%;position: absolute;left:0;top:0;z-index:100;'>");
        GridColor gridColor = GridColor.valueOf((int)color);
        String hexColor = "rgb(" + gridColor.red() + "," + gridColor.green() + "," + gridColor.blue() + ")";
        barContainerBuffer.append("<div style='float:left;background-color:inherit;height:100%;width:" + negativeContainerWidth + "%;'>");
        barContainerBuffer.append("<div style='float:right;background-color: " + hexColor + ";height:100%;width:" + negativeWidth + "%;'></div>");
        barContainerBuffer.append("</div>");
        barContainerBuffer.append("<div style='float:left;background-color:inherit;height:100%;width:" + positiveContainerWidth + "%;'>");
        barContainerBuffer.append("<div style='float:left;background-color: " + hexColor + ";height:100%;width:" + positiveWidth + "%;'></div>");
        barContainerBuffer.append("</div>");
        if (min < 0.0) {
            barContainerBuffer.append("<div style='background-color:inherit;height:100%;position:absolute;left:" + negativeContainerWidth + "%;top:0;border-right:1px dashed #000000;z-index:103;'></div>");
            barContainerBuffer.append("<div class='topSpaceContainer' style='background-color:inherit;height:3px;;width:100%;left:0;top:0;position: absolute;z-index:102;'></div>");
            barContainerBuffer.append("<div class='bottomSpaceContainer' style='background-color:inherit;height:3px;width:100%;left:0;bottom:0;position: absolute;z-index:102;'></div>");
        } else {
            barContainerBuffer.append("<div class='topSpaceContainer' style='background-color:inherit;height:3px;;width:100%;left:0;top:0;position: absolute;z-index:102;'></div>");
            barContainerBuffer.append("<div class='bottomSpaceContainer' style='background-color:inherit;height:3px;width:100%;left:0;bottom:0;position: absolute;z-index:102;'></div>");
        }
        barContainerBuffer.append("</div>");
        return barContainerBuffer.toString();
    }

    public static String geySignalLampStyleContent(String signalLamp, String cellContent) {
        return ConditionStyleUtil.geySignalLampStyleContent(null, signalLamp, cellContent);
    }

    public static String geySignalLampStyleContent(String rootPath, String signalLamp, String cellContent) {
        return ConditionStyleUtil.geySignalLampStyleContent(rootPath, signalLamp, cellContent, 0);
    }

    public static String geySignalLampStyleContent(String rootPath, String signalLamp, String cellContent, int position) {
        StringBuilder barContainerBuffer = new StringBuilder();
        String align = "left";
        if (StringUtils.isEmpty((String)cellContent) || "&nbsp;".equalsIgnoreCase(cellContent)) {
            align = "center";
        } else if (position == 1) {
            align = "right";
        }
        String signalLampUrl = "images/conditionstyle/" + signalLamp;
        if (StringUtils.isNotEmpty((String)rootPath)) {
            signalLampUrl = rootPath + "/" + signalLampUrl;
        }
        barContainerBuffer.append("<div style='height:100%;width:100%;left:0px;top:0;position: absolute;z-index:101;background-position: " + align + " ;background-repeat: no-repeat;background-image:url(" + signalLampUrl + ");'></div>");
        return barContainerBuffer.toString();
    }
}

