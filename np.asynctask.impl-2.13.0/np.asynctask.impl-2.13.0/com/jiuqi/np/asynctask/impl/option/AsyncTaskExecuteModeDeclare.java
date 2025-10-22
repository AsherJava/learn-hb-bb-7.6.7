/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 */
package com.jiuqi.np.asynctask.impl.option;

import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractRadioOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AsyncTaskExecuteModeDeclare
extends BaseNormalOptionDeclare {
    public String getId() {
        return "ASYNCTASK_EXECUTEMODE_SYSOPTION";
    }

    public String getTitle() {
        return "\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u65b9\u5f0f";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>(2);
        Iterator iterator = RealTimeJobFactory.getInstance().getGroupIterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            optionItems.add(this.getOptions((String)entry.getKey(), (String)entry.getValue()));
        }
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
                return "dispatch";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>(2);
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7acb\u5373\u6267\u884c";
                    }

                    public String getValue() {
                        return "immediately";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u8c03\u5ea6\u6267\u884c";
                    }

                    public String getValue() {
                        return "dispatch";
                    }
                });
                return values;
            }
        };
    }

    public int getOrdinal() {
        return 9;
    }
}

