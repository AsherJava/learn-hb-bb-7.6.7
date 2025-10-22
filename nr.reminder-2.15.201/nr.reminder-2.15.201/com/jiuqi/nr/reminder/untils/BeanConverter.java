/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.untils;

import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import com.jiuqi.nr.reminder.internal.Reminder;
import com.jiuqi.nr.reminder.internal.ReminderVO;
import org.springframework.beans.BeanUtils;

public class BeanConverter {
    public static void convert(CreateReminderCommand command, Reminder reminder) {
        BeanUtils.copyProperties(command, reminder);
    }

    public static void convert(Reminder reminder, ReminderVO reminderVO) {
        BeanUtils.copyProperties(reminder, reminderVO);
    }

    public static void convert(Reminder reminder, Reminder reminderContent) {
        BeanUtils.copyProperties(reminder, reminderContent);
    }
}

