/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class StyleType {
    public static JSONArray setStyleType() {
        JSONArray sytles = new JSONArray();
        JSONObject cellStyleTable = new JSONObject();
        cellStyleTable.put("fitFontSize", false);
        cellStyleTable.put("rightBorderStyle", 1);
        cellStyleTable.put("bottomBorderStyle", 1);
        cellStyleTable.put("leftBorderStyle", 1);
        cellStyleTable.put("topBorderStyle", 1);
        cellStyleTable.put("count", 950);
        cellStyleTable.put("mode", 0);
        cellStyleTable.put("fontName", (Object)"\u5fae\u8f6f\u96c5\u9ed1");
        cellStyleTable.put("horizontalAlignment", 1);
        cellStyleTable.put("backGroundColor", (Object)"#f2f2f2");
        cellStyleTable.put("fontSize", 14);
        cellStyleTable.put("fontColor", (Object)"#000000");
        cellStyleTable.put("verticalAlignment", 2);
        cellStyleTable.put("leftBorderColor", (Object)"#000000");
        cellStyleTable.put("topBorderColor", (Object)"#000000");
        cellStyleTable.put("rightBorderColor", (Object)"#000000");
        cellStyleTable.put("bottomBorderColor", (Object)"#000000");
        cellStyleTable.put("cellType", (Object)"string");
        sytles.put((Object)cellStyleTable);
        JSONObject cellStyleTitle = new JSONObject();
        cellStyleTitle.put("fitFontSize", false);
        cellStyleTitle.put("rightBorderStyle", 1);
        cellStyleTitle.put("bottomBorderColor", (Object)"#dddddd");
        cellStyleTitle.put("rightBorderColor", (Object)"#dddddd");
        cellStyleTitle.put("topBorderColor", (Object)"#dddddd");
        cellStyleTitle.put("count", 19);
        cellStyleTitle.put("mode", 0);
        cellStyleTitle.put("bottomBorderStyle", 1);
        cellStyleTitle.put("fontName", (Object)"\u5fae\u8f6f\u96c5\u9ed1");
        cellStyleTitle.put("horizontalAlignment", 2);
        cellStyleTitle.put("backGroundColor", (Object)"#D1D1D1");
        cellStyleTitle.put("leftBorderStyle", 1);
        cellStyleTitle.put("topBorderStyle", 1);
        cellStyleTitle.put("fontSize", 14);
        cellStyleTitle.put("fontColor", (Object)"#000000");
        cellStyleTitle.put("verticalAlignment", 2);
        cellStyleTitle.put("cellType", (Object)"string");
        sytles.put((Object)cellStyleTitle);
        JSONObject cellStyle = new JSONObject();
        cellStyle.put("fitFontSize", false);
        cellStyle.put("rightBorderStyle", 1);
        cellStyle.put("bottomBorderStyle", 1);
        cellStyle.put("leftBorderStyle", 1);
        cellStyle.put("topBorderStyle", 1);
        cellStyle.put("count", 950);
        cellStyle.put("mode", 0);
        cellStyle.put("fontName", (Object)"\u5fae\u8f6f\u96c5\u9ed1");
        cellStyle.put("backGroundColor", (Object)"#ffffff");
        cellStyle.put("fontSize", 14);
        cellStyle.put("fontColor", (Object)"#000000");
        cellStyle.put("leftBorderColor", (Object)"#000000");
        cellStyle.put("topBorderColor", (Object)"#000000");
        cellStyle.put("rightBorderColor", (Object)"#000000");
        cellStyle.put("bottomBorderColor", (Object)"#000000");
        cellStyle.put("horizontalAlignment", 1);
        cellStyle.put("cellType", (Object)"string");
        sytles.put((Object)cellStyle);
        JSONObject cellStyleLC = new JSONObject();
        cellStyleLC.put("fitFontSize", false);
        cellStyleLC.put("rightBorderStyle", (Object)"THIN");
        cellStyleLC.put("count", 950);
        cellStyleLC.put("mode", 0);
        cellStyleLC.put("bottomBorderStyle", (Object)"THIN");
        cellStyleLC.put("fontName", (Object)"\u5fae\u8f6f\u96c5\u9ed1");
        cellStyleLC.put("horizontalAlignment", 2);
        cellStyleLC.put("backGroundColor", (Object)"#f2f2f2");
        cellStyleLC.put("leftBorderStyle", (Object)"THIN");
        cellStyleLC.put("topBorderStyle", (Object)"THIN");
        cellStyleLC.put("fontSize", 14);
        cellStyleLC.put("fontColor", (Object)"#000000");
        cellStyleLC.put("verticalAlignment", 2);
        cellStyleLC.put("leftBorderColor", (Object)"#000000");
        cellStyleLC.put("topBorderColor", (Object)"#000000");
        cellStyleLC.put("rightBorderColor", (Object)"#000000");
        cellStyleLC.put("bottomBorderColor", (Object)"#000000");
        cellStyleLC.put("cellType", (Object)"string");
        sytles.put((Object)cellStyleLC);
        JSONObject cellStyle2 = new JSONObject();
        cellStyle2.put("fitFontSize", false);
        cellStyle2.put("rightBorderStyle", 1);
        cellStyle2.put("bottomBorderStyle", 1);
        cellStyle2.put("leftBorderStyle", 1);
        cellStyle2.put("topBorderStyle", 1);
        cellStyle2.put("count", 950);
        cellStyle2.put("mode", 0);
        cellStyle2.put("fontName", (Object)"\u5fae\u8f6f\u96c5\u9ed1");
        cellStyle2.put("backGroundColor", (Object)"#ffffff");
        cellStyle2.put("fontSize", 14);
        cellStyle2.put("fontColor", (Object)"#000000");
        cellStyle2.put("leftBorderColor", (Object)"#000000");
        cellStyle2.put("topBorderColor", (Object)"#000000");
        cellStyle2.put("rightBorderColor", (Object)"#000000");
        cellStyle2.put("bottomBorderColor", (Object)"#000000");
        cellStyle2.put("horizontalAlignment", 3);
        cellStyle2.put("cellType", (Object)"string");
        sytles.put((Object)cellStyle2);
        return sytles;
    }
}

