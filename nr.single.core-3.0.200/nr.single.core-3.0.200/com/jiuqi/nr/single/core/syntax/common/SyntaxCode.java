/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.common;

import java.util.HashMap;

public enum SyntaxCode {
    SY_OK(0),
    SY_UNKOWN_SYNTAX(1),
    SY_LACKOF_LEFT_SMALL_BRACKEK(2),
    SY_LACKOF_RIGHT_SMALL_BRACKEK(3),
    SY_LACKOF_LEFT_MIDDLE_BRACKEK(4),
    SY_LACKOF_RIGHT_MIDDLE_BRACKEK(5),
    SY_LACKOF_LEFT_BIG_BRACKEK(6),
    SY_LACKOF_RIGHT_BIG_BRACKEK(7),
    SY_LACKOF_INTEGER(8),
    SY_LACKOF_CHAR(9),
    SY_LACKOF_CHAR_WELL(10),
    SY_LACKOF_EQUAL_SIGN(11),
    SY_LACKOF_DIGIT(12),
    SY_LACKOF_COMMA(13),
    SY_LACKOF_STRING(14),
    SY_LACKOF_AND(15),
    SY_LACKOF_OR(16),
    SY_LACKOF_TABLE(17),
    SY_LACKOF_THEN(18),
    SY_NO_SUPPORT_STAR(19),
    SY_BAD_INTERVAL_SET(20),
    SY_INTERVAL_OUTOF_ORDER(21),
    SY_INTERVAL_SET_AT_END(22),
    SY_BAD_USE_LINK_PLUS(23),
    SY_LINK_PLUS_OUTOF_ORDER(24),
    SY_BAD_USE_DOLLAR(25),
    SY_BAD_USE_BIG_BRACKET(26),
    SY_NO_LOGIC_EXPRESS(27),
    SY_MUCH_OF_BRACKET(28),
    SY_BRACKET_NO_MATCH(29),
    SY_INT_OVER_BORDER(30),
    SY_INVALID_CHAR_IN_CELL(31),
    SY_EXIST_SPARE_CHAR(32),
    SY_TYPE_NO_MATCH(33),
    SY_INVALID_CHAR(34),
    SY_OVER_LAP_OPERATOR(35),
    SY_BAD_USE_QUOTE_SIGN(36),
    SY_NEED_OF_INTERVAL_SET(37),
    SY_NEED_OF_STRING_EXP(38),
    SY_NEED_OF_REAL_EXP(39),
    SY_NEED_OF_IF_EXP(40),
    SY_NEED_OF_THEN_EXP(41),
    SY_NEED_OF_ELSE_EXP(42),
    SY_IDC_NEED_EIGHT_DIGIT(43),
    SY_EXIST_DATA_NEED_TABSIGN(44),
    SY_LAST_SYNTAX_CODE(45),
    SY_UNKOWN_SIGN(46),
    SY_NO_EXIST_TABLE_NAME(47),
    SY_NO_EXIST_CELL_SIGN(48),
    SY_NO_EXIST_CELL_NUMBER(49),
    SY_BAD_TABLE_CELL_PARA(50),
    SY_NO_NUMBER_TABLE_CELL(51),
    SY_CODE_NO_MEANING(52),
    SY_MONTH_OVER_BORDER(53),
    SY_ONLY_SET_CURRENT(54),
    SY_CELL_READ_ONLY(55),
    SY_NO_SUPPORT_MONTH(56),
    SY_INVALID_TASK(57),
    SY_INVALID_STAT_CODE(58),
    SY_UNSUITABLE_USE_OF_STAR(59),
    SY_SCRIPT_CODE(60);

    private int intValue;
    private static HashMap<Integer, SyntaxCode> mappings;

    private static synchronized HashMap<Integer, SyntaxCode> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private SyntaxCode(int value) {
        this.intValue = value;
        SyntaxCode.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static SyntaxCode forValue(int value) {
        return SyntaxCode.getMappings().get(value);
    }
}

