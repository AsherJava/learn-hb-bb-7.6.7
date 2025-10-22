/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.common;

import com.jiuqi.nr.single.core.script.bean.TRTab;
import com.jiuqi.nr.single.core.script.common.TPSPasToken;
import java.util.HashMap;
import java.util.Map;

public class uPSUtils {
    public static final String PS_MAIN_PROC_NAME = "!MAIN";
    public static final String PS_MAIN_PROC_NAME_ORG = "Main Proc";
    public static final short PS_LOW_BUILD_SUPPORT = 12;
    public static final short PS_CURRENT_BUILDNO = 2;
    public static final String PS_CURRENT_VERSION = "1.30";
    public static final int PS_VALID_HEADER = 1397769801;
    public static final int PS_ADDR_STACK_START = 0x60000000;
    public static final int PS_ADDR_NEGATIVE_STACK_START = 0x40000000;
    public static final short CM_A = 0;
    public static final short CM_CA = 1;
    public static final short CM_PV = 3;
    public static final short CM_PO = 4;
    public static final short CM_C = 5;
    public static final short CM_G = 6;
    public static final short CM_CG = 7;
    public static final short CM_CNG = 8;
    public static final short CM_R = 9;
    public static final short CM_ST = 10;
    public static final short CM_PT = 11;
    public static final short CM_CO = 12;
    public static final short CM_CV = 13;
    public static final short CM_SP = 14;
    public static final short CM_BN = 15;
    public static final short CM_SF = 17;
    public static final short CM_FG = 18;
    public static final short CM_PUEXH = 19;
    public static final short CM_POEXH = 20;
    public static final short CM_IN = 21;
    public static final short CM_SPC = 22;
    public static final short CM_INC = 23;
    public static final short CM_DEC = 24;
    public static final short CM_NOP = 255;
    public static final short CM_PG = 25;
    public static final short CM_P2G = 26;
    public static final TRTab[] LookupTable = new TRTab[]{new TRTab("AND", TPSPasToken.CSTII_AND), new TRTab("ARRAY", TPSPasToken.CSTII_ARRAY), new TRTab("AS", TPSPasToken.CSTII_AS), new TRTab("BEGIN", TPSPasToken.CSTII_BEGIN), new TRTab("CASE", TPSPasToken.CSTII_CASE), new TRTab("CHR", TPSPasToken.CSTII_CHR), new TRTab("CLASS", TPSPasToken.CSTII_CLASS), new TRTab("CONST", TPSPasToken.CSTII_CONST), new TRTab("CONSTRUCTOR", TPSPasToken.CSTII_CONSTRUCTOR), new TRTab("DESTRUCTOR", TPSPasToken.CSTII_DESTRUCTOR), new TRTab("DIV", TPSPasToken.CSTII_DIV), new TRTab("DO", TPSPasToken.CSTII_DO), new TRTab("DOWNTO", TPSPasToken.CSTII_DOWNTO), new TRTab("ELSE", TPSPasToken.CSTII_ELSE), new TRTab("END", TPSPasToken.CSTII_END), new TRTab("EXCEPT", TPSPasToken.CSTII_EXCEPT), new TRTab("EXIT", TPSPasToken.CSTII_EXIT), new TRTab("EXPORT", TPSPasToken.CSTII_EXPORT), new TRTab("EXTERNAL", TPSPasToken.CSTII_EXTERNAL), new TRTab("FINALIZATION", TPSPasToken.CSTII_FINALIZATION), new TRTab("FINALLY", TPSPasToken.CSTII_FINALLY), new TRTab("FOR", TPSPasToken.CSTII_FOR), new TRTab("FORWARD", TPSPasToken.CSTII_FORWARD), new TRTab("FUNCTION", TPSPasToken.CSTII_FUNCTION), new TRTab("GOTO", TPSPasToken.CSTII_GOTO), new TRTab("IF", TPSPasToken.CSTII_IF), new TRTab("IMPLEMENTATION", TPSPasToken.CSTII_IMPLEMENTATION), new TRTab("IN", TPSPasToken.CSTII_IN), new TRTab("INHERITED", TPSPasToken.CSTII_INHERITED), new TRTab("INITIALIZATION", TPSPasToken.CSTII_INITIALIZATION), new TRTab("INTERFACE", TPSPasToken.CSTII_INTERFACE), new TRTab("IS", TPSPasToken.CSTII_IS), new TRTab("LABEL", TPSPasToken.CSTII_LABEL), new TRTab("MOD", TPSPasToken.CSTII_MOD), new TRTab("NIL", TPSPasToken.CSTII_NIL), new TRTab("NOT", TPSPasToken.CSTII_NOT), new TRTab("OF", TPSPasToken.CSTII_OF), new TRTab("OR", TPSPasToken.CSTII_OR), new TRTab("ORD", TPSPasToken.CSTII_ORD), new TRTab("OUT", TPSPasToken.CSTII_OUT), new TRTab("OVERRIDE", TPSPasToken.CSTII_OVERRIDE), new TRTab("PRIVATE", TPSPasToken.CSTII_PRIVATE), new TRTab("PROCEDURE", TPSPasToken.CSTII_PROCEDURE), new TRTab("PROGRAM", TPSPasToken.CSTII_PROGRAM), new TRTab("PROPERTY", TPSPasToken.CSTII_PROPERTY), new TRTab("PROTECTED", TPSPasToken.CSTII_PROTECTED), new TRTab("PUBLIC", TPSPasToken.CSTII_PUBLIC), new TRTab("PUBLISHED", TPSPasToken.CSTII_PUBLISHED), new TRTab("RECORD", TPSPasToken.CSTII_RECORD), new TRTab("REPEAT", TPSPasToken.CSTII_REPEAT), new TRTab("SET", TPSPasToken.CSTII_SET), new TRTab("SHL", TPSPasToken.CSTII_SHL), new TRTab("SHR", TPSPasToken.CSTII_SHR), new TRTab("THEN", TPSPasToken.CSTII_THEN), new TRTab("TO", TPSPasToken.CSTII_TO), new TRTab("TRY", TPSPasToken.CSTII_TRY), new TRTab("TYPE", TPSPasToken.CSTII_TYPE), new TRTab("UNIT", TPSPasToken.CSTII_UNIT), new TRTab("UNTIL", TPSPasToken.CSTII_UNTIL), new TRTab("USES", TPSPasToken.CSTII_USES), new TRTab("VAR", TPSPasToken.CSTII_VAR), new TRTab("VIRTUAL", TPSPasToken.CSTII_VIRTUAL), new TRTab("WHILE", TPSPasToken.CSTII_WHILE), new TRTab("WITH", TPSPasToken.CSTII_WITH), new TRTab("XOR", TPSPasToken.CSTII_XOR)};
    public static final int KEYWORD_COUNT = 65;

    public static final int makeHash(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); ++i) {
            result = (result << 7 | result >> 25) + s.charAt(i);
        }
        return result;
    }

    public static final Map<String, TRTab> getLookupTableMap() {
        HashMap<String, TRTab> result = new HashMap<String, TRTab>();
        for (TRTab tab : LookupTable) {
            result.put(tab.getName(), tab);
        }
        return result;
    }
}

