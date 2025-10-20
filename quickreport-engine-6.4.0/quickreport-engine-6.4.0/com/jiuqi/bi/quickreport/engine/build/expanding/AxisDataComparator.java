/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.nvwa.pinyin.HanYuPinYinHelper
 *  com.jiuqi.nvwa.pinyin.format.HanyuPinyinOutputFormat
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.model.OrderMode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.nvwa.pinyin.HanYuPinYinHelper;
import com.jiuqi.nvwa.pinyin.format.HanyuPinyinOutputFormat;
import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Function;

final class AxisDataComparator
implements Comparator<AxisDataNode> {
    private final int factor;
    private final boolean pinyin;
    private Comparator<Object> zhCompartor;
    private static final String SEPERATOR = "\u00029";

    public AxisDataComparator(OrderMode orderMode, boolean pinyin) {
        this.factor = orderMode == OrderMode.DESC ? -1 : 1;
        this.pinyin = pinyin;
    }

    private Comparator<Object> getZHComparator() {
        if (this.zhCompartor == null) {
            if (this.pinyin) {
                HashMap pinyins = new HashMap();
                HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
                Function<String, String[]> translator = k -> {
                    try {
                        String v = HanYuPinYinHelper.toHanYuPinyinString((String)k, (HanyuPinyinOutputFormat)format, (String)SEPERATOR, (boolean)true);
                        return v.split(SEPERATOR);
                    }
                    catch (RuntimeException e) {
                        return new String[]{k};
                    }
                };
                this.zhCompartor = (o1, o2) -> {
                    String[] s1 = (String[])pinyins.computeIfAbsent(o1.toString(), translator);
                    String[] s2 = (String[])pinyins.computeIfAbsent(o2.toString(), translator);
                    int len = Math.min(s1.length, s2.length);
                    for (int i = 0; i < len; ++i) {
                        int c = s1[i].compareTo(s2[i]);
                        if (c == 0) continue;
                        return c;
                    }
                    return s1.length - s2.length;
                };
            } else {
                this.zhCompartor = Collator.getInstance(Locale.CHINA);
            }
        }
        return this.zhCompartor;
    }

    @Override
    public int compare(AxisDataNode o1, AxisDataNode o2) {
        Object v2;
        Object v1 = o1.getOrderValue();
        if (v1 == (v2 = o2.getOrderValue())) {
            return 0;
        }
        if (v1 == null) {
            return 1;
        }
        if (v2 == null) {
            return -1;
        }
        if (v1 instanceof Number && v2 instanceof Number) {
            return DataType.compare((Number)((Number)v1), (Number)((Number)v2)) * this.factor;
        }
        if (v1 instanceof String && v2 instanceof String) {
            return this.getZHComparator().compare(v1, v2) * this.factor;
        }
        return ((Comparable)v1).compareTo(v2) * this.factor;
    }
}

