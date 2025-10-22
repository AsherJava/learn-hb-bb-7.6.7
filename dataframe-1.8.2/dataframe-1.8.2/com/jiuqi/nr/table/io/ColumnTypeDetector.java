/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ColumnTypeDetector {
    private static final int STRING_COLUMN_ROW_COUNT_CUTOFF = 50000;
    private static final double STRING_COLUMN_CUTOFF = 0.5;
    private final List<ColumnType> typeArray;

    public ColumnTypeDetector(List<ColumnType> typeArray) {
        this.typeArray = typeArray;
    }

    public ColumnType[] detectColumnTypes(Iterator<String[]> rows, ReadOptions options) {
        boolean useSampling = options.sample();
        ArrayList<ColumnType> columnTypes = new ArrayList<ColumnType>();
        ArrayList columnData = new ArrayList();
        int rowCount = 0;
        int nextRow = 0;
        String[] nextLine = new String[]{};
        while (rows.hasNext()) {
            try {
                nextLine = rows.next();
                if (rowCount == 0) {
                    for (int i = 0; i < nextLine.length; ++i) {
                        columnData.add(new ArrayList());
                    }
                }
                int columnNumber = 0;
                if (rowCount == nextRow) {
                    for (String field : nextLine) {
                        ((List)columnData.get(columnNumber)).add(field);
                        ++columnNumber;
                    }
                    nextRow = useSampling ? this.nextRow(nextRow) : this.nextRowWithoutSampling(nextRow);
                }
                ++rowCount;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
        }
        for (List list : columnData) {
            HashSet unique;
            double uniquePct;
            ColumnType detectedType = this.detectType(list, options);
            if (detectedType.equals(ColumnType.STRING) && rowCount > 50000 && options.columnTypesToDetect().contains(ColumnType.TEXT) && (uniquePct = (double)(unique = new HashSet(list)).size() / ((double)list.size() * 1.0)) > 0.5) {
                detectedType = ColumnType.TEXT;
            }
            columnTypes.add(detectedType);
        }
        return columnTypes.toArray(new ColumnType[0]);
    }

    private int nextRowWithoutSampling(int nextRow) {
        return nextRow + 1;
    }

    private int nextRow(int nextRow) {
        if (nextRow < 10000) {
            return nextRow + 1;
        }
        if (nextRow < 100000) {
            return nextRow + 1000;
        }
        if (nextRow < 1000000) {
            return nextRow + 10000;
        }
        if (nextRow < 10000000) {
            return nextRow + 100000;
        }
        if (nextRow < 100000000) {
            return nextRow + 1000000;
        }
        return nextRow + 10000000;
    }

    private ColumnType detectType(List<String> valuesList, ReadOptions options) {
        CopyOnWriteArrayList parsers = new CopyOnWriteArrayList(this.getParserList(this.typeArray, options));
        CopyOnWriteArrayList<ColumnType> typeCandidates = new CopyOnWriteArrayList<ColumnType>(this.typeArray);
        boolean hasNonMissingValues = false;
        for (String s : valuesList) {
            for (AbstractColumnParser<?> parser : parsers) {
                if (parser.isMissing(s)) continue;
                hasNonMissingValues = true;
                if (parser.canParse(s)) continue;
                typeCandidates.remove(parser.columnType());
                parsers.remove(parser);
            }
        }
        if (hasNonMissingValues) {
            return this.selectType(typeCandidates);
        }
        return this.typeArray.get(this.typeArray.size() - 1);
    }

    private ColumnType selectType(List<ColumnType> typeCandidates) {
        return typeCandidates.get(0);
    }

    private List<AbstractColumnParser<?>> getParserList(List<ColumnType> typeArray, ReadOptions options) {
        ArrayList parsers = new ArrayList();
        for (ColumnType type : typeArray) {
            parsers.add(type.customParser(options));
        }
        return parsers;
    }
}

