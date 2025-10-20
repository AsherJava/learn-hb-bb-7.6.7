/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal.review;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.CheckGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;

public class ReviewTipOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "REVIEW_TIP";
    }

    @Override
    public String getTitle() {
        return "\u4e0a\u62a5\u524d\u5ba1\u6838\u4e2a\u6027\u5316\u65b9\u6848\u63d0\u793a\u7c7b\u578b";
    }

    @Override
    public String getDefaultValue() {
        return "2";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_DROP_DOWN;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> list = new ArrayList<OptionItem>();
        list.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u53ef\u5ffd\u7565";
            }

            @Override
            public String getValue() {
                return "2";
            }
        });
        list.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u9700\u586b\u5199\u8bf4\u660e";
            }

            @Override
            public String getValue() {
                return "1";
            }
        });
        list.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u5fc5\u987b\u5ba1\u6838\u901a\u8fc7";
            }

            @Override
            public String getValue() {
                return "0";
            }
        });
        return list;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public double getOrder() {
        return 49.0;
    }

    @Override
    public String getPageTitle() {
        return new CheckGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new CheckGroup().getTitle();
    }
}

