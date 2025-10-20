/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.ZeroShowValue;
import com.jiuqi.nr.definition.option.impl.group.DisplayGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NumberZeroShow
implements TaskOptionDefine {
    public static final String KEY = "NUMBER_ZERO_SHOW";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTitle() {
        return "\u96f6\u503c\u663e\u793a";
    }

    @Override
    public String getDefaultValue() {
        return "\u539f\u503c";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_DROP_DOWN;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        return null;
    }

    @Override
    public List<OptionItem> getOptionItems(String taskKey) {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return ZeroShowValue.ORIGINAL_VALUE.getTitle();
            }

            @Override
            public String getValue() {
                return ZeroShowValue.ORIGINAL_VALUE.getValue();
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return ZeroShowValue.NULL_VALUE.getTitle();
            }

            @Override
            public String getValue() {
                return ZeroShowValue.NULL_VALUE.getValue();
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return ZeroShowValue.SYMBOL_ONE_VALUE.getTitle();
            }

            @Override
            public String getValue() {
                return ZeroShowValue.SYMBOL_ONE_VALUE.getValue();
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return ZeroShowValue.SYMBOL_TWO_VALUE.getTitle();
            }

            @Override
            public String getValue() {
                return ZeroShowValue.SYMBOL_TWO_VALUE.getValue();
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return ZeroShowValue.NUMBER_ZERO_VALUE.getTitle();
            }

            @Override
            public String getValue() {
                return ZeroShowValue.NUMBER_ZERO_VALUE.getValue();
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return ZeroShowValue.CHINESE_ZERO_VALUE.getTitle();
            }

            @Override
            public String getValue() {
                return ZeroShowValue.CHINESE_ZERO_VALUE.getValue();
            }
        });
        ITaskOptionController taskOptionController = BeanUtil.getBean(ITaskOptionController.class);
        String value = taskOptionController.getValue(taskKey, KEY);
        if (value.equals(" ")) {
            value = ZeroShowValue.NULL_VALUE.getValue();
            taskOptionController.setValue(taskKey, KEY, value);
        }
        if (value != null && !this.isContains(value)) {
            final String finalValue = value;
            optionItems.add(new OptionItem(){

                @Override
                public String getTitle() {
                    return "\u5176\u4ed6\uff1a" + finalValue;
                }

                @Override
                public String getValue() {
                    return finalValue;
                }
            });
        }
        return optionItems;
    }

    private boolean isContains(String value) {
        return Arrays.stream(ZeroShowValue.values()).anyMatch(any -> any.getValue().equals(value));
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public double getOrder() {
        return 20.0;
    }

    @Override
    public String getPageTitle() {
        return new DisplayGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new DisplayGroup().getTitle();
    }

    @Override
    public boolean canEmpty() {
        return false;
    }
}

