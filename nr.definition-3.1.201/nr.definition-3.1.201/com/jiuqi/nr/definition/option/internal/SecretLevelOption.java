/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.impl.group.SecretGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecretLevelOption
implements TaskOptionDefine {
    @Value(value="${jiuqi.nr.task-option.secret-level.visible-type:DEFAULT}")
    private String visibleType;
    @Value(value="${jiuqi.nr.task-option.secret-level.default-value:0}")
    private String defaultValue;
    private final Logger logger = LoggerFactory.getLogger(SecretLevelOption.class);

    @Override
    public String getKey() {
        return "SECRET_LEVEL";
    }

    @Override
    public String getTitle() {
        return "\u542f\u7528\u5bc6\u7ea7";
    }

    @Override
    public String getDefaultValue() {
        if ("1".equals(this.defaultValue) || "0".equals(this.defaultValue)) {
            return this.defaultValue;
        }
        this.logger.warn("\u914d\u7f6e\u9519\u8bef,\u65e0\u6cd5\u52a0\u8f7d\uff0c\u6539\u4e3a\u4f7f\u7528\u9ed8\u8ba4\u503c\u3002\u8bf7\u786e\u8ba4\u914d\u7f6e\u6587\u4ef6 jiuqi.nr.task-option.secret-level.default-value: " + this.defaultValue);
        return "0";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_SWITCH;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u662f";
            }

            @Override
            public String getValue() {
                return "1";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u5426";
            }

            @Override
            public String getValue() {
                return "0";
            }
        });
        return optionItems;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public double getOrder() {
        return 38.0;
    }

    @Override
    public VisibleType getVisibleType(String taskKey) {
        try {
            return VisibleType.valueOf(VisibleType.class, this.visibleType);
        }
        catch (Exception e) {
            this.logger.warn("\u914d\u7f6e\u9519\u8bef,\u65e0\u6cd5\u52a0\u8f7d\uff0c\u6539\u4e3a\u4f7f\u7528\u9ed8\u8ba4\u503c\u3002\u8bf7\u786e\u8ba4\u914d\u7f6e\u6587\u4ef6 jiuqi.nr.task-option.secret-level.visible-type: " + this.visibleType, e);
            return VisibleType.DEFAULT;
        }
    }

    @Override
    public String getPageTitle() {
        return new SecretGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new SecretGroup().getTitle();
    }
}

