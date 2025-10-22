/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 */
package com.jiuqi.nr.single.core.task.util;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.task.model.SingleDataType;
import com.jiuqi.nr.single.core.task.model.SingleFieldInfo;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;

public class SingleTaskUtils {
    public static Column getDbfFieldColumn(DataColumn column) {
        SingleFieldInfo field = new SingleFieldInfo();
        field.setFieldCode(column.getColumnName());
        field.setFieldTitle(column.getCaptionName());
        field.setMapFieldCode(column.getMapColumnName());
        field.setPrecision(column.getPrecision());
        field.setDecimal(column.getDecimal());
        field.setDataType(SingleTaskUtils.getSingleDataType(column.getDataType()));
        Column newColumn = new Column(column.getColumnName(), field.getDataType().value(), (Object)field);
        return newColumn;
    }

    public static Metadata<Column> getDbfMetadata(IDbfTable dataDbf) {
        Metadata colums = new Metadata();
        for (DataColumn column : dataDbf.getTable().getColumns()) {
            colums.addColumn(SingleTaskUtils.getDbfFieldColumn(column));
        }
        return colums;
    }

    private static SingleDataType getSingleDataType(int dbfColumType) {
        SingleDataType dataType = SingleDataType.STRING;
        switch (dbfColumType) {
            case 0: {
                dataType = SingleDataType.STRING;
                break;
            }
            case 1: {
                dataType = SingleDataType.FLOAT;
                break;
            }
            case 2: {
                dataType = SingleDataType.DATE;
                break;
            }
            case 3: {
                dataType = SingleDataType.FLOAT;
                break;
            }
            case 4: {
                dataType = SingleDataType.INTEGER;
                break;
            }
            case 5: {
                dataType = SingleDataType.BOOLEAN;
                break;
            }
            case 6: {
                dataType = SingleDataType.CLOB;
                break;
            }
            case 7: {
                dataType = SingleDataType.FILE;
                break;
            }
            case 8: {
                dataType = SingleDataType.FILE;
                break;
            }
            case 9: {
                dataType = SingleDataType.FILE;
                break;
            }
            default: {
                dataType = SingleDataType.STRING;
            }
        }
        return dataType;
    }

    public static SingleDataType getSingleDataType(ZBDataType zbType) {
        SingleDataType dataType = SingleDataType.STRING;
        switch (zbType.getValue()) {
            case 1: {
                dataType = SingleDataType.STRING;
                break;
            }
            case 2: {
                dataType = SingleDataType.INTEGER;
                break;
            }
            case 3: {
                dataType = SingleDataType.FLOAT;
                break;
            }
            case 4: {
                dataType = SingleDataType.DATE;
                break;
            }
            case 5: {
                dataType = SingleDataType.CLOB;
                break;
            }
            case 6: {
                dataType = SingleDataType.FILE;
                break;
            }
            case 7: {
                dataType = SingleDataType.FLOAT;
                break;
            }
            case 8: {
                dataType = SingleDataType.BOOLEAN;
                break;
            }
            case 17: {
                dataType = SingleDataType.FILE;
                break;
            }
            case 39: {
                dataType = SingleDataType.STRING;
                break;
            }
            case 9: {
                dataType = SingleDataType.STRING;
                break;
            }
            default: {
                dataType = SingleDataType.STRING;
            }
        }
        return dataType;
    }
}

