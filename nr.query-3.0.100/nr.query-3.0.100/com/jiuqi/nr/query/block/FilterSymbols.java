/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

public enum FilterSymbols {
    MORETHAN,
    LESSTHAN,
    MOREANDEQUE,
    LESSANDEQUE,
    EQUE,
    NOTEQUE,
    START,
    NOTSTART,
    END,
    NOTEND,
    CONTAIN,
    NOTCONTAIN,
    BETWEEN,
    NOTBETWEEN,
    REPEAT,
    ONLY,
    RANK,
    AVERAGE;


    public static FilterSymbols getAntonym(String type) {
        FilterSymbols result;
        FilterSymbols current = FilterSymbols.valueOf(type);
        switch (current) {
            case MORETHAN: {
                result = LESSANDEQUE;
                break;
            }
            case LESSTHAN: {
                result = MOREANDEQUE;
                break;
            }
            case MOREANDEQUE: {
                result = LESSTHAN;
                break;
            }
            case LESSANDEQUE: {
                result = MORETHAN;
                break;
            }
            default: {
                result = BETWEEN;
            }
        }
        return result;
    }
}

