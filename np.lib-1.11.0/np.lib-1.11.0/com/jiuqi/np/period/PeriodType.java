/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period;

import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.util.LogHelper;
import java.util.Calendar;

public enum PeriodType {
    DEFAULT(0){

        @Override
        protected boolean doModify(PeriodWrapper wrapper, PeriodType otherType, int offset) {
            wrapper.setPeriod(wrapper.getPeriod() + offset);
            return true;
        }

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            return null;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            return false;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
        }
    }
    ,
    YEAR(1){

        @Override
        protected boolean doModify(PeriodWrapper wrapper, PeriodType otherType, int offset) {
            wrapper.setYear(wrapper.getYear() + offset);
            return true;
        }

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            Calendar c = PeriodType.getCachedCalendar();
            c.set(wrapper.getYear(), 0, 1);
            return c;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            return false;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
            wrapper.setYear(calendar.get(1));
        }
    }
    ,
    HALFYEAR(2){

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            Calendar c = PeriodType.getCachedCalendar();
            c.set(wrapper.getYear(), wrapper.getPeriod() == 1 ? 0 : 6, 1);
            return c;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            calendar.add(2, offset * 6);
            return true;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
            wrapper.setYear(calendar.get(1));
            wrapper.setPeriod(calendar.get(2) < 6 ? 1 : 2);
        }
    }
    ,
    SEASON(3){

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            Calendar c = PeriodType.getCachedCalendar();
            c.set(wrapper.getYear(), (wrapper.getPeriod() - 1) * 3, 1);
            return c;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            calendar.add(2, offset * 3);
            return true;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
            wrapper.setYear(calendar.get(1));
            wrapper.setPeriod(calendar.get(2) / 3 + 1);
        }
    }
    ,
    MONTH(4){

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            Calendar c = PeriodType.getCachedCalendar();
            int period = wrapper.getPeriod();
            if (period < 1) {
                period = 1;
            } else if (period > 12) {
                period = 12;
            }
            c.set(wrapper.getYear(), period - 1, 1);
            return c;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            calendar.add(2, offset);
            return true;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
            wrapper.setYear(calendar.get(1));
            wrapper.setPeriod(calendar.get(2) + 1);
        }
    }
    ,
    TENDAY(5){

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            Calendar c = PeriodType.getCachedCalendar();
            int tenday = wrapper.getPeriod();
            c.set(wrapper.getYear(), (tenday - 1) / 3, (tenday - 1) % 3 * 10 + 1);
            return c;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            calendar.add(6, offset * 10);
            return true;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
            wrapper.setYear(calendar.get(1));
            int tenday = calendar.get(2) * 3;
            int monthDay = calendar.get(5);
            tenday = monthDay <= 10 ? ++tenday : (monthDay <= 20 ? (tenday += 2) : (tenday += 3));
            wrapper.setPeriod(tenday);
        }
    }
    ,
    DAY(6){

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            Calendar c = PeriodType.getCachedCalendar();
            c.set(1, wrapper.getYear());
            c.set(6, wrapper.getPeriod());
            return c;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            calendar.add(6, offset);
            return true;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
            wrapper.setYear(calendar.get(1));
            wrapper.setPeriod(calendar.get(6));
        }
    }
    ,
    WEEK(7){

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            Calendar c = PeriodType.getCachedCalendar();
            c.set(1, wrapper.getYear());
            c.set(3, wrapper.getPeriod());
            c.set(7, 2);
            return c;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            calendar.add(3, offset);
            return true;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
            wrapper.setYear(calendar.get(1));
            wrapper.setPeriod(calendar.get(3));
        }
    }
    ,
    CUSTOM(8){

        @Override
        public boolean canModify(PeriodType other) {
            return false;
        }

        @Override
        public Calendar toCalendar(PeriodWrapper wrapper) {
            return null;
        }

        @Override
        protected boolean doOffset(Calendar calendar, int offset) {
            return false;
        }

        @Override
        protected void applyModify(PeriodWrapper wrapper, Calendar calendar) {
        }
    };

    private static ThreadLocal<Calendar> calendarProvider;
    private static PeriodType[] TYPES;
    private static final int[] MODIFY_INDEX;
    private int fType;

    private static Calendar getCachedCalendar() {
        Calendar c = calendarProvider.get();
        if (c == null) {
            c = Calendar.getInstance();
            c.setMinimalDaysInFirstWeek(7);
            c.setFirstDayOfWeek(2);
            calendarProvider.set(c);
        }
        return c;
    }

    public static PeriodType fromType(int type) {
        return TYPES[type];
    }

    public static PeriodType fromCode(int code) {
        return TYPES[PeriodConsts.codeToTypeMap[code]];
    }

    public static PeriodType fromTitle(String title) {
        return TYPES[PeriodConsts.titleToType(title)];
    }

    private static void throwInvalidModify(PeriodType target, PeriodType modifier) {
        throw new IllegalArgumentException("\u4e0d\u5408\u6cd5\u7684\u65f6\u671f\u504f\u79fb\uff1a\u5bf9[" + target.title() + "]\u65f6\u671f\u8fdb\u884c[" + modifier.title() + "]\u504f\u79fb");
    }

    private PeriodType(int type) {
        this.fType = type;
    }

    public int type() {
        return this.fType;
    }

    public int code() {
        return PeriodConsts.PERIOD_TYPE_CODEs[this.fType];
    }

    public String title() {
        return PeriodConsts.PERIOD_TYPE_TITLEs[this.fType];
    }

    public boolean canModify(PeriodType other) {
        return MODIFY_INDEX[other.fType] != 0 && MODIFY_INDEX[other.fType] != Integer.MAX_VALUE;
    }

    public boolean modify(PeriodWrapper wrapper, int offset) {
        PeriodType otherType = PeriodType.fromType(wrapper.getType());
        if (!this.canModify(otherType) || !this.doModify(wrapper, otherType, offset)) {
            PeriodType.throwInvalidModify(this, otherType);
        }
        return true;
    }

    protected boolean doModify(PeriodWrapper wrapper, PeriodType otherType, int offset) {
        Calendar calendar = otherType.toCalendar(wrapper);
        if (calendar != null && this.doOffset(calendar, offset)) {
            otherType.applyModify(wrapper, calendar);
            return true;
        }
        return false;
    }

    public PeriodWrapper fromCalendar(Calendar calendar) {
        PeriodWrapper wrapper = new PeriodWrapper();
        wrapper.setType(this.fType);
        this.applyModify(wrapper, calendar);
        return wrapper;
    }

    public abstract Calendar toCalendar(PeriodWrapper var1);

    protected abstract boolean doOffset(Calendar var1, int var2);

    protected abstract void applyModify(PeriodWrapper var1, Calendar var2);

    public static void main(String[] args) {
        PeriodType.test(new PeriodWrapper(2013, 6, 60), PeriodModifier.parse("-1N"));
        PeriodType.test(new PeriodWrapper(2013, 6, 60), PeriodModifier.parse("+1N"));
    }

    private static void test(PeriodWrapper w, PeriodModifier m) {
        LogHelper.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        LogHelper.info(m.toString());
        PeriodType.print(w);
        m.modify(w);
        PeriodType.print(w);
        m.modify(w);
        PeriodType.print(w);
        m.modify(w);
        PeriodType.print(w);
        LogHelper.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void print(PeriodWrapper w) {
        LogHelper.info(w + " -- " + PeriodType.fromType(w.getType()).toCalendar(w).getTime().toGMTString());
    }

    static {
        calendarProvider = new ThreadLocal();
        TYPES = new PeriodType[]{DEFAULT, YEAR, HALFYEAR, SEASON, MONTH, TENDAY, DAY, WEEK, CUSTOM};
        MODIFY_INDEX = new int[]{Integer.MAX_VALUE, 0x7FFFFFFE, 6, 5, 4, 3, 1, 2, 0};
    }
}

