/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ReminderDeclare
implements ISystemOptionDeclare {
    @Autowired
    protected SystemOptionOperator systemOptionOperator;
    public static final String START_REMINDER = "START_REMINDER";
    public static final String ID = "start-reminder";
    public static final String REMINDER_MSG_CHANNEL = "REMINDER_MSG_CHANNEL";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u50ac\u62a5";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public int getOrdinal() {
        return 3;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return ReminderDeclare.START_REMINDER;
            }

            public String getTitle() {
                return "\u542f\u52a8\u50ac\u62a5";
            }

            @Override
            public String getDefaultValue() {
                return "1";
            }
        });
        optionItems.add(new AbstractCheckBoxOption(){

            public String getId() {
                return ReminderDeclare.REMINDER_MSG_CHANNEL;
            }

            public String getTitle() {
                return "\u53d1\u9001\u65b9\u5f0f";
            }

            @Override
            public List<ISystemOptionalValue> getOptionalValues() {
                ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u7ad9\u5185\u4fe1";
                    }

                    public String getValue() {
                        return "PC";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u90ae\u4ef6";
                    }

                    public String getValue() {
                        return "EMAIL";
                    }
                });
                values.add(new ISystemOptionalValue(){

                    public String getTitle() {
                        return "\u77ed\u4fe1";
                    }

                    public String getValue() {
                        return "SMS";
                    }
                });
                return values;
            }

            @Override
            public String getDefaultValue() {
                return "[\"PC\",\"EMAIL\"]";
            }
        });
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

