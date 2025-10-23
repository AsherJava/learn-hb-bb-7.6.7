/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.va.message.domain.VaMessageChannelDTO
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.service.VaMessageService
 */
package com.jiuqi.nr.reminder.plan.service.impl;

import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.va.message.domain.VaMessageChannelDTO;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.service.VaMessageService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

    public List<String> getOptionItemIds() {
        ArrayList<String> list = new ArrayList<String>(2);
        list.add(START_REMINDER);
        list.add(REMINDER_MSG_CHANNEL);
        return list;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        TrueFalseOption reminder = new TrueFalseOption();
        reminder.setId(START_REMINDER);
        reminder.setTitle("\u542f\u52a8\u50ac\u62a5");
        reminder.setDefaultValue("1");
        optionItems.add((ISystemOptionItem)reminder);
        CheckBoxOption checkBoxOption = new CheckBoxOption();
        checkBoxOption.setId(REMINDER_MSG_CHANNEL);
        checkBoxOption.setTitle("\u53d1\u9001\u65b9\u5f0f");
        checkBoxOption.setDefaultValue("[\"" + VaMessageOption.MsgChannel.PC + "\",\"" + VaMessageOption.MsgChannel.EMAIL + "\"]");
        optionItems.add((ISystemOptionItem)checkBoxOption);
        LinkedHashSet<ISystemOptionalValue> optionalValues = new LinkedHashSet<ISystemOptionalValue>();
        optionalValues.add(new SystemOptionalValue(VaMessageOption.MsgChannel.PC.toString(), "\u7ad9\u5185\u4fe1"));
        VaMessageService bean = null;
        try {
            bean = (VaMessageService)BeanUtil.getBean(VaMessageService.class);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (bean == null) {
            optionalValues.addAll(ReminderDeclare.defaultOptions());
        } else {
            List vaMessageChannels = bean.listChannel();
            for (VaMessageChannelDTO vaMessageChannel : vaMessageChannels) {
                optionalValues.add(new SystemOptionalValue(vaMessageChannel.getName(), vaMessageChannel.getTitle()));
            }
        }
        checkBoxOption.setOptionalValues(new ArrayList<ISystemOptionalValue>(optionalValues));
        return optionItems;
    }

    private static List<ISystemOptionalValue> defaultOptions() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new SystemOptionalValue(VaMessageOption.MsgChannel.PC.toString(), "\u7ad9\u5185\u4fe1"));
        values.add(new SystemOptionalValue(VaMessageOption.MsgChannel.EMAIL.toString(), "\u90ae\u4ef6"));
        values.add(new SystemOptionalValue(VaMessageOption.MsgChannel.SMS.toString(), "\u77ed\u4fe1"));
        return values;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    private static class SystemOptionalValue
    implements ISystemOptionalValue {
        private String value;
        private String title;

        public SystemOptionalValue() {
        }

        public SystemOptionalValue(String value, String title) {
            this.value = value;
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            SystemOptionalValue that = (SystemOptionalValue)o;
            return Objects.equals(this.value, that.value);
        }

        public int hashCode() {
            return this.value != null ? this.value.hashCode() : 0;
        }
    }

    private static class CheckBoxOption
    extends AbstractCheckBoxOption {
        private String id;
        private String title;
        private String defaultValue;
        private List<ISystemOptionalValue> optionalValues;

        private CheckBoxOption() {
        }

        public List<ISystemOptionalValue> getOptionalValues() {
            return this.optionalValues;
        }

        public void setOptionalValues(List<ISystemOptionalValue> optionalValues) {
            this.optionalValues = optionalValues;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public String getDefaultValue() {
            if (this.defaultValue == null) {
                return super.getDefaultValue();
            }
            return this.defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    private static class TrueFalseOption
    extends AbstractTrueFalseOption {
        private String id;
        private String title;
        private String defaultValue;

        public TrueFalseOption() {
        }

        public TrueFalseOption(VaMessageChannelDTO channelDTO) {
            this.id = channelDTO.getName();
            this.title = channelDTO.getTitle();
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public String getDefaultValue() {
            if (this.defaultValue == null) {
                return super.getDefaultValue();
            }
            return this.defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }
}

