/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.util;

import com.jiuqi.common.financialcubes.util.NoParametersConsumer;

public class BooleanConsumerUtils {
    public static void trueFunction(Boolean flag, NoParametersConsumer consumer) {
        if (Boolean.TRUE.equals(flag)) {
            consumer.accept();
        }
    }

    public static void falseFunction(Boolean flag, NoParametersConsumer consumer) {
        if (!Boolean.TRUE.equals(flag)) {
            consumer.accept();
        }
    }

    public static void ifElseFunction(Boolean flag, NoParametersConsumer ifConsumer, NoParametersConsumer elseConsumer) {
        if (Boolean.TRUE.equals(flag)) {
            ifConsumer.accept();
        } else {
            elseConsumer.accept();
        }
    }
}

