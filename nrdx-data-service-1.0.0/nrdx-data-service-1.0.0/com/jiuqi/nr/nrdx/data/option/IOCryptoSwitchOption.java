/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.nrdx.data.option;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import com.jiuqi.nr.nrdx.data.option.IOCryptoGroup;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class IOCryptoSwitchOption
implements TaskOptionDefine {
    public static final String KEY = "IO_CRYPTO_SWITCH";
    public static final String TITLE = "\u542f\u7528\u52a0\u5bc6";

    public String getKey() {
        return KEY;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getDefaultValue() {
        return "0";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_SWITCH;
    }

    public List<OptionItem> getOptionItems() {
        return Collections.emptyList();
    }

    public String getRegex() {
        return null;
    }

    public double getOrder() {
        return 100.0;
    }

    public String getPageTitle() {
        return new IOCryptoGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return "NRDX\u5bfc\u51fa\u52a0\u5bc6";
    }
}

