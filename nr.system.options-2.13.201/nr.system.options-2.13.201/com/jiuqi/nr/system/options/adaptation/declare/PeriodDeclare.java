/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PeriodDeclare
extends BaseNormalOptionDeclare {
    private static final String ID = "PERIOD_GROUP";
    private static final String TITLE = "\u65f6\u671f";
    private static final String OUTSIDE_TYPE = "DISABLE_OUTSIDE_TYPE";
    private static final String OUTSIDE_TYPE_TITLE = "\u6709\u6548\u65f6\u671f\u8303\u56f4\u5916\u65f6\u671f";
    private static final String INSIDE_TYPE = "DISABLE_INSIDE_TYPE";
    private static final String INSIDE_TYPE_TITLE = "\u6709\u6548\u65f6\u671f\u8303\u56f4\u5185\u4e0d\u53ef\u9009\u65f6\u671f";
    private static final String WEEKLY_SELECTION_MODE = "WEEKLY_SELECTION_MODE";
    private static final String WEEKLY_DISPLAY_MODE_TITLE = "\u5468\u9009\u62e9\u6a21\u5f0f";
    private static final String LIST = "list";
    private static final String LIST_TITLE = "\u5217\u8868";
    private static final String TRADITION = "tradition";
    private static final String TRADITION_TITLE = "\u4f20\u7edf";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>(2);
        optionItems.add(this.getOptions(OUTSIDE_TYPE, OUTSIDE_TYPE_TITLE));
        optionItems.add(this.getOptions(INSIDE_TYPE, INSIDE_TYPE_TITLE));
        optionItems.add(this.getWeeklyDisplayModeOptions(WEEKLY_SELECTION_MODE, WEEKLY_DISPLAY_MODE_TITLE));
        return optionItems;
    }

    private ISystemOptionItem getOptions(final String id, final String title) {
        return new AbstractRadioOption(){

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getDefaultValue() {
                return "disable";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>(2);
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7981\u7528";
                    }

                    public String getValue() {
                        return "disable";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u9690\u85cf";
                    }

                    public String getValue() {
                        return "hidden";
                    }
                });
                return values;
            }
        };
    }

    private ISystemOptionItem getWeeklyDisplayModeOptions(final String id, final String title) {
        return new AbstractRadioOption(){

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getDefaultValue() {
                return PeriodDeclare.TRADITION;
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>(2);
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return PeriodDeclare.TRADITION_TITLE;
                    }

                    public String getValue() {
                        return PeriodDeclare.TRADITION;
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return PeriodDeclare.LIST_TITLE;
                    }

                    public String getValue() {
                        return PeriodDeclare.LIST;
                    }
                });
                return values;
            }
        };
    }
}

