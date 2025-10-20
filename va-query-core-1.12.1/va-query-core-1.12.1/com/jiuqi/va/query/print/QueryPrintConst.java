/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.layout.borders.Border
 *  com.itextpdf.layout.element.Cell
 */
package com.jiuqi.va.query.print;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class QueryPrintConst {
    public static final Cell NO_BORDER_CELL;
    public static final String LOG_PREFIX = "[\u5355\u636e\u6253\u5370]>>>";
    public static final int MAP_INITIAL_CAPACITY = 16;
    public static final String BLANK_SPACE = " ";
    public static final char CAHR_BLANK_SPACE = ' ';
    public static final String POINT = ".";
    public static final String LINE_FEED = "\n";
    public static final Pattern DOUBLE_LINE_FEED_PATTERN;
    public static final Pattern BLANK_SPACE_PATTERN;
    public static final Pattern LEFT_SYMBOL_ONE_PATTERN;
    public static final Pattern RIGHT_SYMBOL_ONE_PATTERN;
    public static final Pattern LF_COMPILE;
    public static final Pattern CR_PATTERN;
    public static final char CHAR_CR = '\n';
    public static final String BLANK_FLAG = "\u00a0";
    public static final String CHINESE_COLON = "\uff1a";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String RUNG = "-";
    public static final String LEFT_SYMBOL_ONE = "\\[";
    public static final String RIGHT_SYMBOL_ONE = "\\]";
    public static final String COMMA = ",";
    public static final String UNDERLINE = "_";
    public static final int PADDING_LENGTH = 4;
    public static final String MICROSOFT_ACCOR_BLACK = "Microsoft YaHei";
    public static final int MULTIPLE = 72;
    public static final String L_TITLE = "title";
    public static final String L_NULL = "null";
    public static final String L_COMMENT = "comment";
    public static final String L_MODEL = "model";
    public static final String L_ORDINAL = "ordinal";
    public static final String L_SERVER = "server";
    public static final String L_PATH = "path";
    public static final String L_DATA = "data";
    public static final String L_TYPE = "type";
    public static final String L_NAME = "name";
    public static final String L_TRUE = "true";
    public static final String L_OBJECTCODE = "objectcode";
    public static final String L_CONTENT_TYPE = "content-type";
    public static final String L_EXPRESSION = "expression";
    public static final String L_UNITCODE = "unitcode";
    public static final String KEY_NODEUSER = "nodeUser";
    public static final String KEY_SERIALNUMBER = "serialNumber";
    public static final String KEY_AUDIT_USER = "auditUser";
    public static final String KEY_AUDIT_INFO = "auditInfo";
    public static final String KEY_CELLPROPMAP = "cellPropMap";
    public static final String KEY_EXCLUDE_END_MAP = "excludeEndMap";
    public static final String KEY_PAGE_INFO = "pageInfo";
    public static final String KEY_TABLE_DATA_LIST = "tableDataList";
    public static final String KEY_SHOW_TITLE = "showTitle";
    public static final String KEY_NODEGROUP = "nodeGroup";
    public static final String KEY_SCHEMES_NAME = "schemesName";
    public static final String KEY_FLOAT_ROW_FILTER_EXPRESSION = "floatRowFilterExpression";
    public static final String U_BILL = "BILL";
    public static final String U_BINDINGID = "BINDINGID";
    public static final String U_BINDINGVALUE = "BINDINGVALUE";
    public static final String U_UNITCODE = "UNITCODE";
    public static final String L_USERCODE = "usercode";
    public static final String L_USER = "user";
    public static final String L_INDEX = "index";
    public static final String FINANCIAL_CHARGE_NODE_NAME = "\u8d22\u52a1\u6536\u5355\u6821\u9a8c";
    public static final String GATEWAY_FLAG = "^ ";
    public static final String CURINR_NODE_NAME1 = "\u7ed3\u7b97\u4e2d\u5fc3";
    public static final String CURINR_NODE_NAME2 = "\u5904\u7406\u4e2d";
    public static final String APPROVAL_OPINION_FLAG = "\u5ba1\u6279\u610f\u89c1";
    public static final String CURRENT_ROW_INDEX = "CurrentRowIndex";
    public static final String PRINTTABLENO = "PrintTableNo";
    public static final String ALL_DATA = "AllData";
    public static final String SUFFIX_M = "_M";
    public static final String EMPTY_STR = "";
    public static final String PARAM_ILLEGAL = "\u53c2\u6570\u4e0d\u5408\u6cd5";
    public static final String DEFAULT_PADDING = "0 0 0 0";
    public static final String KEY_NODENAME = "nodeName";
    public static final float PARAGRAPH_LEADING = 1.35f;
    public static final float PARAGRAPH_LEADING_MM = 13.5f;
    public static final String SCHEME_NOT_EXIST_FLAG = "va.billcore.billprintservice.nofindscheme";
    public static final String ERROR_CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String ERROR_PRINT = "\u5355\u636e\u6253\u5370\u51fa\u9519\uff1a";
    public static final String SCHEMENAME_BILLCODE = "'SCHEMENAME_BILLCODE'";
    public static final List<Integer> REF_TABLE_TYPES;
    public static final float TYPO_ASCENDER_SCALE_COEFF = 1.2f;
    public static final String PROPERTY_TYPE_MASK = "mask";
    public static final String FONT_DEFAULT_COLOR = "#000000";

    private QueryPrintConst() {
    }

    static {
        DOUBLE_LINE_FEED_PATTERN = Pattern.compile("\n\n");
        BLANK_SPACE_PATTERN = Pattern.compile(BLANK_SPACE);
        LEFT_SYMBOL_ONE_PATTERN = Pattern.compile(LEFT_SYMBOL_ONE);
        RIGHT_SYMBOL_ONE_PATTERN = Pattern.compile(RIGHT_SYMBOL_ONE);
        LF_COMPILE = Pattern.compile(LINE_FEED, 16);
        CR_PATTERN = Pattern.compile("\r", 16);
        REF_TABLE_TYPES = new ArrayList<Integer>();
        NO_BORDER_CELL = new Cell();
        NO_BORDER_CELL.setBorder(Border.NO_BORDER);
        REF_TABLE_TYPES.add(3);
        REF_TABLE_TYPES.add(1);
        REF_TABLE_TYPES.add(2);
        REF_TABLE_TYPES.add(4);
    }

    public static final class FloatContainerConst {
        public static final int FLOATX = 9999;
        public static final int FLOATY = 9998;
        public static final int FLOAT_DIV = 9999;
    }
}

