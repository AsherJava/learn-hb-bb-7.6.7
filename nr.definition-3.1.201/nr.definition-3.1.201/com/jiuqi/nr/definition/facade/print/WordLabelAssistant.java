/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class WordLabelAssistant {
    public static final int SCOPE_ALL = 0;
    public static final int SCOPE_ODD_PAGE = 1;
    public static final int SCOPE_EVEN_PAGE = 2;
    public static final int SCOPE_NOT_PRINT = 3;
    public static final int LOC_ELE_PAGE = 0;
    public static final int LOC_ELE_TABLE = 1;
    public static final int LOC_V_TOP = 0;
    public static final int LOC_V_BELOW = 1;
    public static final int LOC_H_LEFT = 0;
    public static final int LOC_H_CENTER = 1;
    public static final int LOC_H_RIGHT = 2;
    private static final Map<Integer, String> SCOPE_MAP = new TreeMap<Integer, String>();
    private static final Map<Integer, String> LOC_ELE_MAP = new TreeMap<Integer, String>();
    private static final Map<Integer, String> LOC_V_MAP = new TreeMap<Integer, String>();
    private static final Map<Integer, String> LOC_H_MAP = new TreeMap<Integer, String>();
    private static Map<String, String> locEVMap = new LinkedHashMap<String, String>();
    private static boolean initialized = false;
}

