/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.common;

import java.util.HashMap;

public enum TPSPasToken {
    CSTI_EOF(0),
    CSTIINT_COMMENT(1),
    CSTIINT_WHITESPACE(2),
    CSTI_IDENTIFIER(3),
    CSTI_SEMICOLON(4),
    CSTI_COMMA(5),
    CSTI_PERIOD(6),
    CSTI_COLON(7),
    CSTI_OPENROUND(8),
    CSTI_CLOSEROUND(9),
    CSTI_OPENBLOCK(10),
    CSTI_CLOSEBLOCK(11),
    CSTI_ASSIGNMENT(12),
    CSTI_EQUAL(13),
    CSTI_NOTEQUAL(14),
    CSTI_GREATER(15),
    CSTI_GREATEREQUAL(16),
    CSTI_LESS(17),
    CSTI_LESSEQUAL(18),
    CSTI_PLUS(19),
    CSTI_MINUS(20),
    CSTI_DIVIDE(21),
    CSTI_MULTIPLY(22),
    CSTI_INTEGER(23),
    CSTI_REAL(24),
    CSTI_STRING(25),
    CSTI_CHAR(26),
    CSTI_HEXINT(27),
    CSTI_ADDRESSOF(28),
    CSTI_DEREFERENCE(29),
    CSTI_TWODOTS(30),
    CSTII_AND(31),
    CSTII_ARRAY(32),
    CSTII_BEGIN(33),
    CSTII_CASE(34),
    CSTII_CONST(35),
    CSTII_DIV(36),
    CSTII_DO(37),
    CSTII_DOWNTO(38),
    CSTII_ELSE(39),
    CSTII_END(40),
    CSTII_FOR(41),
    CSTII_FUNCTION(42),
    CSTII_IF(43),
    CSTII_IN(44),
    CSTII_MOD(45),
    CSTII_NOT(46),
    CSTII_OF(47),
    CSTII_OR(48),
    CSTII_PROCEDURE(49),
    CSTII_PROGRAM(50),
    CSTII_REPEAT(51),
    CSTII_RECORD(52),
    CSTII_SET(53),
    CSTII_SHL(54),
    CSTII_SHR(55),
    CSTII_THEN(56),
    CSTII_TO(57),
    CSTII_TYPE(58),
    CSTII_UNTIL(59),
    CSTII_USES(60),
    CSTII_VAR(61),
    CSTII_WHILE(62),
    CSTII_WITH(63),
    CSTII_XOR(64),
    CSTII_EXIT(65),
    CSTII_CLASS(66),
    CSTII_CONSTRUCTOR(67),
    CSTII_DESTRUCTOR(68),
    CSTII_INHERITED(69),
    CSTII_PRIVATE(70),
    CSTII_PUBLIC(71),
    CSTII_PUBLISHED(72),
    CSTII_PROTECTED(73),
    CSTII_PROPERTY(74),
    CSTII_VIRTUAL(75),
    CSTII_OVERRIDE(76),
    CSTII_AS(77),
    CSTII_IS(78),
    CSTII_UNIT(79),
    CSTII_TRY(80),
    CSTII_EXCEPT(81),
    CSTII_FINALLY(82),
    CSTII_EXTERNAL(83),
    CSTII_FORWARD(84),
    CSTII_EXPORT(85),
    CSTII_LABEL(86),
    CSTII_GOTO(87),
    CSTII_CHR(88),
    CSTII_ORD(89),
    CSTII_INTERFACE(90),
    CSTII_IMPLEMENTATION(91),
    CSTII_INITIALIZATION(92),
    CSTII_FINALIZATION(93),
    CSTII_OUT(94),
    CSTII_NIL(95),
    CSTI_BOF(96);

    private int intValue;
    private static HashMap<Integer, TPSPasToken> mappings;

    private static synchronized HashMap<Integer, TPSPasToken> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TPSPasToken(int value) {
        this.intValue = value;
        TPSPasToken.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TPSPasToken forValue(int value) {
        return TPSPasToken.getMappings().get(value);
    }
}

