/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.TempTableActuator
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 */
package com.jiuqi.nr.data.engine.gather.util;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.TempTableActuator;
import com.jiuqi.nr.data.engine.gather.param.FieldAndGroupKeyInfo;
import com.jiuqi.nr.data.engine.gather.param.FileSumInfo;
import com.jiuqi.nr.data.engine.gather.util.GatherTempTable;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatherTempTableUtils {
    private static final Logger logger = LoggerFactory.getLogger(GatherTempTableUtils.class);

    public static ITempTable getTempTable(Connection connection, GatherTempTable tempTable) {
        try {
            return TempTableActuator.getTempTableByMeta((Connection)connection, (ITempTableMeta)tempTable);
        }
        catch (SQLException e) {
            logger.error("\u83b7\u53d6\u6c47\u603b\u8fc7\u7a0b\u4e2d\u4e0d\u5b9a\u5217\u4e34\u65f6\u8868\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    public static List<Object[]> fileGatherBuildTempValues(List<FileSumInfo> fileSumInfos, ITempTable gatherTempTable, List<DataField> collect) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        Map<String, DataField> collect1 = collect.stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        ArrayList res = new ArrayList();
        fileSumInfos.forEach(a -> res.addAll(a.getFieldAndGroupKeyInfos()));
        List columns = gatherTempTable.getMeta().getLogicFields();
        for (FieldAndGroupKeyInfo info : res) {
            Object[] rowvalue = new Object[columns.size()];
            LinkedHashMap<String, String> fieldGroupMap = info.getFieldGroupMap();
            for (int i = 0; i < columns.size(); ++i) {
                LogicField logicField = (LogicField)columns.get(i);
                rowvalue[i] = logicField.getFieldName().equals("MDCODE") ? info.getBizKey() : (logicField.getFieldName().equals("BIZKEYORDER") ? info.getBizKey() : fieldGroupMap.get(collect1.get(logicField.getFieldName()).getKey()));
            }
            batchValues.add(rowvalue);
        }
        return batchValues;
    }

    public static List<LogicField> ConvertFields(List<DataField> dataFields) {
        ArrayList<LogicField> resFields = new ArrayList<LogicField>();
        for (DataField dataField : dataFields) {
            LogicField dimField = new LogicField();
            dimField.setFieldName(dataField.getCode());
            GatherTempTableUtils.parseFieldType(dataField, dimField);
            dimField.setNullable(dataField.isNullable());
            resFields.add(dimField);
        }
        return resFields;
    }

    public static List<LogicField> ConvertNotNullFields(List<DataField> dataFields) {
        ArrayList<LogicField> resFields = new ArrayList<LogicField>();
        for (DataField dataField : dataFields) {
            LogicField dimField = new LogicField();
            dimField.setFieldName(dataField.getCode());
            GatherTempTableUtils.parseFieldType(dataField, dimField);
            dimField.setNullable(false);
            resFields.add(dimField);
        }
        return resFields;
    }

    public static void parseFieldType(DataField tableDimField, LogicField dimField) {
        switch (tableDimField.getDataFieldType()) {
            case BIGDECIMAL: {
                dimField.setDataType(3);
                if (tableDimField.getPrecision() != null) {
                    dimField.setPrecision(tableDimField.getPrecision().intValue());
                }
                if (tableDimField.getDecimal() == null) break;
                dimField.setScale(tableDimField.getDecimal().intValue());
                break;
            }
            case STRING: 
            case PICTURE: 
            case FILE: {
                dimField.setDataType(6);
                if (tableDimField.getPrecision() != null && tableDimField.getPrecision() > 0) {
                    dimField.setSize(tableDimField.getPrecision().intValue());
                    dimField.setRawType(-9);
                    break;
                }
                dimField.setSize(50);
                break;
            }
            default: {
                if (tableDimField.getPrecision() != null) {
                    dimField.setSize(tableDimField.getPrecision().intValue());
                } else {
                    dimField.setSize(255);
                }
                dimField.setDataType(tableDimField.getDataFieldType().getValue());
            }
        }
    }

    public static void releaseTempTable(ITempTable tmpTable) {
        if (tmpTable == null) {
            return;
        }
        try {
            tmpTable.close();
        }
        catch (IOException e) {
            logger.error("\u5173\u95ed\u6c47\u603b\u8fc7\u7a0b\u4e2d\u4e0d\u5b9a\u5217\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
    }
}

