/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.datatype;

import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CellBookDataTypeFactory {
    private static final Set<String> oldTypeSet = new HashSet<String>();
    private static final Map<String, CommonCellDataType> oldBaseType = new HashMap<String, CommonCellDataType>();
    public static final String AUTO = "auto";
    public static final String OLD_INLINE_STR_DATA_TYPE = "0";
    public static final String OLD_NUMBER_DATA_TYPE = "1";
    public static final String OLD_BOOLEAN_DATA_TYPE = "2";
    public static final String OLD_DATE_DATA_TYPE = "3";
    public static final String OLD_FORMULA_DATA_TYPE = "4";
    public static final String OLD_ERROR_DATA_TYPE = "5";
    public static final String OLD_AUTO_DATA_TYPE = "6";
    public static final String OLD_CURRENCY_DATA_TYPE = "7";
    public static final String OLD_HTML_DATA_TYPE = "8";
    public static final String OLD_HYPERLINK_DATA_TYPE = "9";
    public static final String OLD_PICTURE_DATA_TYPE = "10";
    public static final String OLD_TREE_DATA_TYPE = "11";
    public static final String OLD_RADIO_DATA_TYPE = "12";
    public static final String OLD_SWITCH_DATA_TYPE = "13";
    public static final String OLD_TIME_DATA_TYPE = "14";
    public static final String OLD_CHECK_DATA_TYPE = "15";
    public static final String OLD_SIMPLEHTML_DATA_TYPE = "16";
    public static final String OLD_PERCENTAGE_DATA_TYPE = "17";

    private static void register(String oldType) {
        oldTypeSet.add(oldType);
    }

    private static void registerBase(String oldType, CommonCellDataType commonCellDataType) {
        oldBaseType.put(oldType, commonCellDataType);
    }

    public static boolean isOldType(String dataType) {
        return oldTypeSet.contains(dataType);
    }

    public static CommonCellDataType getOldBaseType(String dataType) {
        return oldBaseType.get(dataType);
    }

    public static boolean isOldBaseType(String dataType) {
        return oldBaseType.containsKey(dataType);
    }

    static {
        CellBookDataTypeFactory.register(OLD_INLINE_STR_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_NUMBER_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_BOOLEAN_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_DATE_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_FORMULA_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_ERROR_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_AUTO_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_CURRENCY_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_HTML_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_HYPERLINK_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_PICTURE_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_TREE_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_RADIO_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_SWITCH_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_TIME_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_CHECK_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_SIMPLEHTML_DATA_TYPE);
        CellBookDataTypeFactory.register(OLD_PERCENTAGE_DATA_TYPE);
        CellBookDataTypeFactory.registerBase(OLD_INLINE_STR_DATA_TYPE, CommonCellDataType.STRING);
        CellBookDataTypeFactory.registerBase(OLD_NUMBER_DATA_TYPE, CommonCellDataType.NUMBER);
        CellBookDataTypeFactory.registerBase(OLD_BOOLEAN_DATA_TYPE, CommonCellDataType.BOOLEAN);
        CellBookDataTypeFactory.registerBase(OLD_DATE_DATA_TYPE, CommonCellDataType.DATE);
        CellBookDataTypeFactory.registerBase(OLD_FORMULA_DATA_TYPE, null);
        CellBookDataTypeFactory.registerBase(OLD_ERROR_DATA_TYPE, CommonCellDataType.ERROR);
        CellBookDataTypeFactory.registerBase(OLD_AUTO_DATA_TYPE, null);
    }
}

