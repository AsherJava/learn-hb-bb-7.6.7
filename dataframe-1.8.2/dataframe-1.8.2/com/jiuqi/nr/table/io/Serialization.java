/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.Index;
import java.util.ArrayList;

public class Serialization {
    private static final String EMPTY_DF_STRING = "[empty data frame]";
    private static final String ELLIPSES = "...";
    private static final String NEWLINE = "\n";
    private static final String DELIMITER = "\t";
    private static final Object INDEX_KEY = new Object();
    private static final int MAX_COLUMN_WIDTH = 20;

    public static String toString(DataFrame<?> df, int limit) {
        int size = df.size();
        if (size == 0) {
            return EMPTY_DF_STRING;
        }
        StringBuilder sb = new StringBuilder();
        Index rows = df.rows();
        Index columns = df.columns();
        ArrayList<Object> r_levels = new ArrayList<Object>(rows.levels());
        ArrayList<Object> c_levels = new ArrayList<Object>(columns.levels());
        sb.append(DELIMITER);
        int w = 6;
        for (int i = 0; i < rows.count(); ++i) {
            String name = rows.getName(i);
            sb.append(Serialization.lpad(name, w - 3));
        }
        sb.append(DELIMITER);
        for (int l = 0; l < columns.count(); ++l) {
            for (int c = 0; c < columns.size(); ++c) {
                Object column = c_levels.get(c);
                sb.append(Serialization.lpad(column, w));
            }
            sb.append(NEWLINE);
        }
        for (int r = 0; r < df.size(); ++r) {
            sb.append(DELIMITER);
            Object row = r_levels.get(r);
            sb.append(Serialization.lpad(row, w));
            for (int c = 0; c < df.length(); ++c) {
                sb.append(Serialization.lpad(df.get(c, r), w));
                sb.append(DELIMITER);
            }
            sb.append(NEWLINE);
            if (limit - 3 >= r || r >= limit << 1 || r >= df.size() - 4) continue;
            sb.append(NEWLINE).append(ELLIPSES).append(" ").append(df.size() - limit).append(" rows skipped ").append(ELLIPSES).append(NEWLINE).append(NEWLINE);
            while (r < df.size() - 2) {
                ++r;
            }
        }
        return sb.toString();
    }

    private static final String lpad(Object o, int w) {
        StringBuilder sb = new StringBuilder();
        String value = String.valueOf(o);
        for (int i = value.length(); i < w; ++i) {
            sb.append(' ');
        }
        sb.append(value);
        return sb.toString();
    }

    private static final String rpad(Object o, int w) {
        StringBuilder sb = new StringBuilder();
        String value = String.valueOf(o);
        sb.append(value);
        for (int i = value.length(); i < w; ++i) {
            sb.append(' ');
        }
        return sb.toString();
    }
}

