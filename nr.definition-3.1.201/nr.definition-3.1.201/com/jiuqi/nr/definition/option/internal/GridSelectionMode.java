/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.DisplayGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GridSelectionMode
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "GRID_SELECTION_MODE";
    }

    @Override
    public String getTitle() {
        return "\u8868\u683c\u9009\u4e2d\u6a21\u5f0f";
    }

    @Override
    public String getDefaultValue() {
        return "3";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_RADIO_BUTTON;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u9009\u4e2d\u5355\u5143\u683c";
            }

            @Override
            public String getValue() {
                return "3";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u9009\u4e2d\u884c";
            }

            @Override
            public String getValue() {
                return "1";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u9009\u4e2d\u5217";
            }

            @Override
            public String getValue() {
                return "2";
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
        return 40.0;
    }

    @Override
    public String getPageTitle() {
        return new DisplayGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new DisplayGroup().getTitle();
    }
}

