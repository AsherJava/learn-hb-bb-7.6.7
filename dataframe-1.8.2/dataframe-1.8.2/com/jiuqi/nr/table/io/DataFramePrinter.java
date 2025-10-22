/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.Index;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class DataFramePrinter {
    private static final String TOO_SHORT_COLUMN_MARKER = "?";
    private final int maxRows;
    private final OutputStream stream;

    public DataFramePrinter(int maxRows, OutputStream stream) {
        this.maxRows = maxRows;
        this.stream = stream;
    }

    public void print(DataFrame<?> frame) {
        try {
            List<Object> headers = this.getHeaderTokens(frame);
            String[][] data = this.getDataTokens(frame);
            int[] widths = DataFramePrinter.getWidths(headers, data);
            String dataTemplate = DataFramePrinter.getDataTemplate(widths);
            String headerTemplate = DataFramePrinter.getHeaderTemplate(widths, headers);
            int totalWidth = IntStream.of(widths).map(w -> w + 5).sum() - 1;
            int totalHeight = data.length + 1;
            int capacity = totalWidth * totalHeight;
            if (capacity < 0) {
                capacity = 0;
            }
            StringBuilder text = new StringBuilder(capacity);
            for (int j = 0; j < totalWidth; ++j) {
                text.append("-");
            }
            for (String[] row : data) {
                String dataLine = String.format(dataTemplate, row);
                text.append(System.lineSeparator());
                text.append(dataLine);
            }
            byte[] bytes = text.toString().getBytes();
            this.stream.write(bytes);
            this.stream.flush();
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to print DataFrame", ex);
        }
    }

    private List<Object> getHeaderTokens(DataFrame<?> frame) {
        Set<Object> levels = frame.columns().levels();
        return new ArrayList<Object>(levels);
    }

    private String getDataToken(Index col, int i) {
        return col.size() > i ? col.getKey(i).toString() : TOO_SHORT_COLUMN_MARKER;
    }

    private static int[] getWidths(List<Object> headers, String[][] data) {
        int[] widths = new int[headers.size()];
        for (int j = 0; j < headers.size(); ++j) {
            Object header = headers.get(j);
            widths[j] = Math.max(widths[j], header != null ? header.toString().length() : 0);
        }
        for (String[] rowValues : data) {
            for (int j = 0; j < rowValues.length; ++j) {
                String value = rowValues[j];
                widths[j] = Math.max(widths[j], value != null ? value.length() : 0);
            }
        }
        return widths;
    }

    private static String getHeaderTemplate(int[] widths, List<Object> headers) {
        return IntStream.range(0, widths.length).mapToObj(i -> {
            int width = widths[i];
            int length = headers.get(i).toString().length();
            int leading = (width - length) / 2;
            int trailing = width - (length + leading);
            StringBuilder text = new StringBuilder();
            DataFramePrinter.whitespace(text, leading + 1);
            text.append("%").append(i + 1).append("$s");
            DataFramePrinter.whitespace(text, trailing);
            text.append("  |");
            return text.toString();
        }).reduce((left, right) -> left + " " + right).orElse("");
    }

    private static String getDataTemplate(int[] widths) {
        return IntStream.range(0, widths.length).mapToObj(i -> " %" + (i + 1) + "$" + widths[i] + "s  |").reduce((left, right) -> left + " " + right).orElse("");
    }

    private static void whitespace(StringBuilder text, int length) {
        IntStream.range(0, length).forEach(i -> text.append(" "));
    }

    private String[][] getDataTokens(DataFrame<?> frame) {
        String[][] data = null;
        return data;
    }
}

