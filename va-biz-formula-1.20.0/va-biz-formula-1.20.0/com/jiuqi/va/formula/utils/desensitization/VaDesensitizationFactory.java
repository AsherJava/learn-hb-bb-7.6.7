/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils.desensitization;

import com.jiuqi.va.formula.domain.desensitization.SensitiveType;
import com.jiuqi.va.formula.utils.desensitization.BankCardDataDesensitization;
import com.jiuqi.va.formula.utils.desensitization.DataDesensitization;
import com.jiuqi.va.formula.utils.desensitization.EmailDesensitization;
import com.jiuqi.va.formula.utils.desensitization.EmptyDesensitization;
import com.jiuqi.va.formula.utils.desensitization.IdCardDesensitization;
import com.jiuqi.va.formula.utils.desensitization.PhoneDesensitization;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class VaDesensitizationFactory {
    private static final Map<String, DataDesensitization> STRATEGY_MAP = new HashMap<String, DataDesensitization>(4);

    private VaDesensitizationFactory() {
    }

    public static DataDesensitization getDesensitization(SensitiveType sensitiveType) {
        return STRATEGY_MAP.getOrDefault(sensitiveType.name().toUpperCase(Locale.ROOT), EmptyDesensitization.getEmptyDesensitization());
    }

    private static void registerStrategy(String name, DataDesensitization strategy) {
        STRATEGY_MAP.put(name.toUpperCase(Locale.ROOT), strategy);
    }

    static {
        VaDesensitizationFactory.registerStrategy(SensitiveType.ID_CARD.name(), new IdCardDesensitization());
        VaDesensitizationFactory.registerStrategy(SensitiveType.PHONE.name(), new PhoneDesensitization());
        VaDesensitizationFactory.registerStrategy(SensitiveType.EMAIL.name(), new EmailDesensitization());
        VaDesensitizationFactory.registerStrategy(SensitiveType.BANK_CARD.name(), new BankCardDataDesensitization());
        VaDesensitizationFactory.registerStrategy(SensitiveType.NONE.name(), EmptyDesensitization.getEmptyDesensitization());
    }
}

