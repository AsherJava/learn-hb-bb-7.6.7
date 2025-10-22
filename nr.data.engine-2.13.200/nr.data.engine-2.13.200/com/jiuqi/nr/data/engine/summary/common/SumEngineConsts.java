/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.common;

public class SumEngineConsts {
    public static final int PARSER_TYPE_FILTER = 1;
    public static final int PARSER_TYPE_EVAL = 2;
    public static final int SUM_STRATEGY_NO_PARALLEL = 0;
    public static final int SUM_STRATEGY_CELL_PARALLEL = 1;
    public static final int SUM_STRATEGY_ROW_PARALLEL = 2;
    public static final int SUM_STRATEGY_COL_PARALLEL = 3;
    public static final int SUM_STRATEGY_DEFAULT = 2;
    public static final int UNITCODE_LENTH = 6;
    public static final String SUB_BASE_DEFINE_TYPE_DEFAULT = "nr";

    public static enum SumType {
        UNKNOWN(-1, "UNKNOWN", "\u672a\u77e5"),
        SUM(0, "SUM", "\u7d2f\u52a0"),
        COUNT(1, "COUNT", "\u8ba1\u6570"),
        MAX(2, "MAX", "\u6700\u5927"),
        MIN(3, "MIN", "\u6700\u5c0f"),
        AVG(4, "AVG", "\u5e73\u5747"),
        VARIANCE(5, "VARIANCE", "\u65b9\u5dee"),
        STDDEV(5, "STDDEV", "\u6807\u51c6\u5dee"),
        MEDIAN(5, "MEDIAN", "\u4e2d\u4f4d\u6570");

        private final int value;
        private final String name;
        private final String title;

        private SumType(int value, String name, String title) {
            this.value = value;
            this.name = name;
            this.title = title;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public String getTitle() {
            return this.title;
        }

        public static SumType valueOf(int value) {
            for (SumType state : SumType.values()) {
                if (state.getValue() != value) continue;
                return state;
            }
            return null;
        }
    }
}

