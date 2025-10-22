/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.treestore.uselector.bean;

import org.json.JSONObject;

public interface USFilterTemplate {
    public static final String TYPE = "type";
    public static final String VALUES = "values";
    public static final String KEYWORD = "keyword";
    public static final String TEMPLATE = "template";
    public static final String CHECK_VALUES = "checkValues";
    public static final String IS_HIGHEST_SELECT = "isHighestSelect";

    public String toJSON();

    public JSONObject getTemplate();

    public USFilterTemplate toTemplate(String var1);
}

