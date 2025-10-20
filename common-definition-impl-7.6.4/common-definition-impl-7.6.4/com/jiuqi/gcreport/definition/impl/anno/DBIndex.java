/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.anno;

import com.jiuqi.gcreport.definition.impl.anno.DBIndexs;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Repeatable(value=DBIndexs.class)
public @interface DBIndex {
    public String name() default "";

    public TableIndexType type() default TableIndexType.TABLE_INDEX_NORMAL;

    public String[] columnsFields();

    public static enum TableIndexType {
        TABLE_INDEX_NORMAL(0, "\u9ed8\u8ba4\u503c\u5e38\u89c4\u7d22\u5f15"),
        TABLE_INDEX_BITMAP(1, "\u4f4d\u56fe\u7d22\u5f15"),
        TABLE_INDEX_UNIQUE(2, "\u552f\u4e00\u7d22\u5f15");

        private int intValue;
        private String title;

        private TableIndexType(int value, String title) {
            this.intValue = value;
            this.title = title;
        }

        public int getValue() {
            return this.intValue;
        }

        public String getTitle() {
            return this.title;
        }
    }
}

