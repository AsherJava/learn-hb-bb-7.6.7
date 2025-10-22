/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.internal;

import com.jiuqi.nr.reminder.internal.Reminder;

public class AutoReminder
extends Reminder {
    public static final byte NO_REPEAT = 0;
    public static final byte ONCE_PER_DAY = 1;
    public static final byte TWICE_PER_DAY = 2;
    public static final byte FOUR_TIMES_PER_DAY = 3;
    public static final byte ONCE_PER_HOU = 8;
    private int dayBeforeDeadline;
    private int repeatMode;

    @Override
    public byte getType() {
        return 1;
    }

    @Override
    public int getDayBeforeDeadline() {
        return this.dayBeforeDeadline;
    }

    @Override
    public void setDayBeforeDeadline(int dayBeforeDeadline) {
        this.dayBeforeDeadline = dayBeforeDeadline;
    }

    @Override
    public int getRepeatMode() {
        return this.repeatMode;
    }

    @Override
    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }
}

