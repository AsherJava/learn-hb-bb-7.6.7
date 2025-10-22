/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.single.core.dbf.DBFCreator
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.idx.IndexFieldDef
 *  com.jiuqi.nr.single.core.util.Ini
 */
package nr.single.map.data;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.idx.IndexFieldDef;
import com.jiuqi.nr.single.core.util.Ini;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;

public class DbfUtil {
    public static void createDbfFileByFields(List<FieldData> fieldDefines, String fileName, boolean createZdmField, int zdmlen) throws Exception {
        DbfUtil.createDbfFileByFields(null, fieldDefines, fileName, createZdmField, zdmlen);
    }

    public static void createDbfFileByFields(TaskDataContext context, List<FieldData> fieldDefines, String fileName, boolean createZdmField, int zdmlen) throws Exception {
        DBFCreator creartor = new DBFCreator();
        if (createZdmField) {
            creartor.addField("SYS_ZDM", 'C', zdmlen, zdmlen);
        }
        int len = 0;
        for (int i = 0; i < fieldDefines.size(); ++i) {
            FieldData field = fieldDefines.get(i);
            FieldType atype = FieldType.forValue((int)field.getFieldType());
            creartor.addField(field.getFieldName(), DbfUtil.getDBFFieldType(atype), field.getFieldSize(), 2);
            len += field.getFieldSize();
        }
        creartor.createTable(fileName);
    }

    public static IDbfTable getDbfTable(String fileName) throws Exception {
        return DbfTableUtil.getDbfTable((String)fileName, (String)"GBK", (boolean)false);
    }

    public static IDbfTable getDbfTable(String fileName, boolean batchMode) throws Exception {
        return DbfTableUtil.getDbfTable((String)fileName, (String)"GBK", (boolean)false, (boolean)batchMode);
    }

    public static void createDbfFileBySingleRegion(TaskDataContext context, SingleFileRegionInfo singleRegion, List<FieldData> fieldDefines, String fileName, boolean createZdmField, int zdmlen, boolean isFMDM, boolean isOrderField) throws Exception {
        DBFCreator creartor = new DBFCreator();
        if (createZdmField) {
            creartor.addField("SYS_ZDM", 'C', zdmlen, zdmlen);
        }
        if (null != singleRegion && singleRegion.getFields().size() > 0) {
            int len = 0;
            for (int i = 0; i < singleRegion.getFields().size(); ++i) {
                SingleFileFieldInfo field = singleRegion.getFields().get(i);
                boolean isNotSingleFile = "PARENTID".equalsIgnoreCase(field.getFieldCode());
                isNotSingleFile = isNotSingleFile || "PARENTCODE".equalsIgnoreCase(field.getFieldCode());
                isNotSingleFile = isNotSingleFile || "FJFIELD".equalsIgnoreCase(field.getFieldCode()) && field.getFieldType() == FieldType.FIELD_TYPE_FILE;
                boolean bl = isNotSingleFile = isNotSingleFile || "WZFIELD".equalsIgnoreCase(field.getFieldCode()) && field.getFieldType() == FieldType.FIELD_TYPE_FILE;
                if (field.getFieldSize() <= 0 || isNotSingleFile) continue;
                FieldType atype = field.getFieldType();
                int fieldLen = field.getFieldSize();
                if (fieldLen > 255) {
                    fieldLen = 255;
                }
                creartor.addField(field.getFieldCode(), DbfUtil.getDBFFieldType(atype), fieldLen, field.getFieldDecimal());
                len += fieldLen;
            }
        } else if (null != fieldDefines) {
            int len = 0;
            for (int i = 0; i < fieldDefines.size(); ++i) {
                FieldData field = fieldDefines.get(i);
                FieldType atype = FieldType.forValue((int)field.getFieldType());
                int fieldLen = field.getFieldSize();
                int fractionDigits = field.getFractionDigits();
                if (fractionDigits < 0) {
                    fractionDigits = 0;
                }
                if (fieldLen > 255) {
                    fieldLen = 255;
                } else if (FieldType.FIELD_TYPE_UUID == atype) {
                    fieldLen = 40;
                }
                creartor.addField(field.getFieldName(), DbfUtil.getDBFFieldType(atype), fieldLen, fractionDigits);
                len += fieldLen;
            }
        }
        if (isOrderField && !creartor.hasField("SYS_ORDER")) {
            creartor.addField("SYS_ORDER", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_STRING), 6, 0);
        }
        if (isFMDM) {
            creartor.addField("SYS_HIDDEN", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_LOGIC), 1, 0);
            creartor.addField("SYS_TSBZ", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_STRING), 25, 0);
            creartor.addField("SYS_LYBZ", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_STRING), 4, 0);
            creartor.addField("SYS_LSBZ", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_STRING), 8, 0);
            creartor.addField("SYS_BS", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_STRING), 8, 0);
            creartor.addField("SYS_FJD", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_STRING), zdmlen, 0);
            creartor.addField("SYS_XZBZ", DbfUtil.getDBFFieldType(FieldType.FIELD_TYPE_LOGIC), 1, 0);
        }
        creartor.createTable(fileName);
    }

    public static void createIndexFileConfig(String fileName, boolean unique, int RecCount, int FileSize) throws Exception {
        DbfUtil.createIndexFileConfigByZdmLen(fileName, unique, RecCount, FileSize, 10);
    }

    public static void createIndexFileConfigByZdmLen(String fileName, boolean unique, int RecCount, int FileSize, int zdmLen) throws Exception {
        List<IndexFieldDef> fields = DbfUtil.getZdmIndexFields(zdmLen);
        DbfUtil.createIndexFileConfigByFields(fileName, unique, RecCount, FileSize, fields);
    }

    public static void createIndexFileConfigByFields(String fileName, boolean unique, int recCount, long fileSize, List<IndexFieldDef> fields) throws Exception {
        File file = new File(fileName);
        String name = file.getName();
        name = name.replaceAll(".DBF", ".JQI_INF");
        String jqiFileName = file.getParent() + File.separatorChar + name;
        Ini ini = new Ini();
        ini.writeString("option", "indexName", "ZDM_Other");
        if (unique) {
            ini.writeString("option", "unique", "1");
        } else {
            ini.writeString("option", "unique", "0");
        }
        long CheckCode = (long)(recCount * 31) + fileSize;
        ini.writeString("option", "checkCode", String.valueOf(CheckCode));
        ini.writeString("option", "fieldSize", String.valueOf(fields.size()));
        for (int i = 0; i < fields.size(); ++i) {
            IndexFieldDef field = fields.get(i);
            String secName = "field_" + String.valueOf(i);
            ini.writeString(secName, "fieldName", field.getFieldName());
            ini.writeString(secName, "fieldType", String.valueOf(field.getDataType()));
            ini.writeString(secName, "fieldLen", String.valueOf(field.getFieldLen()));
        }
        ini.saveToFile(jqiFileName);
    }

    public static List<IndexFieldDef> getZdmIndexFields(int fieldLen) {
        ArrayList<IndexFieldDef> fields = new ArrayList<IndexFieldDef>();
        IndexFieldDef field = new IndexFieldDef();
        field.setFieldName("SYS_ZDM");
        field.setDataType('C');
        field.setFieldLen((short)fieldLen);
        fields.add(field);
        return fields;
    }

    private static char getDBFFieldType(FieldType fieldtype) {
        int result = 67;
        if (fieldtype == FieldType.FIELD_TYPE_GENERAL) {
            result = 67;
        } else if (fieldtype == FieldType.FIELD_TYPE_FLOAT) {
            result = 66;
        } else if (fieldtype == FieldType.FIELD_TYPE_STRING) {
            result = 67;
        } else if (fieldtype == FieldType.FIELD_TYPE_INTEGER) {
            result = 73;
        } else if (fieldtype == FieldType.FIELD_TYPE_LOGIC) {
            result = 76;
        } else if (fieldtype == FieldType.FIELD_TYPE_DATE) {
            result = 84;
        } else if (fieldtype == FieldType.FIELD_TYPE_DATE_TIME) {
            result = 84;
        } else if (fieldtype == FieldType.FIELD_TYPE_TIME) {
            result = 84;
        } else if (fieldtype == FieldType.FIELD_TYPE_UUID) {
            result = 67;
        } else if (fieldtype == FieldType.FIELD_TYPE_DECIMAL) {
            result = 66;
        } else if (fieldtype == FieldType.FIELD_TYPE_TIME_STAMP) {
            result = 67;
        } else if (fieldtype == FieldType.FIELD_TYPE_TEXT) {
            result = 82;
        } else if (fieldtype == FieldType.FIELD_TYPE_PICTURE) {
            result = 71;
        } else if (fieldtype == FieldType.FIELD_TYPE_BINARY) {
            result = 67;
        } else if (fieldtype == FieldType.FIELD_TYPE_FILE) {
            result = 79;
        } else if (fieldtype == FieldType.FIELD_TYPE_LATITUDE_LONGITUDE) {
            result = 67;
        } else if (fieldtype == FieldType.FIELD_TYPE_OBJECT_ARRAY) {
            result = 67;
        } else if (fieldtype == FieldType.FIELD_TYPE_ERROR) {
            result = 67;
        }
        return (char)result;
    }
}

