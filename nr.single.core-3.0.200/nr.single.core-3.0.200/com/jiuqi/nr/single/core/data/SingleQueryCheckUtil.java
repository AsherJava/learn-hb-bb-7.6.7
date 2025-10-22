/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.data;

import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.idx.IndexFieldDef;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SingleQueryCheckUtil {
    public static void createCheckDBFFile(boolean createNew, String path, int zmdLenth) throws SingleFileException {
        String fileName = path + "JQModalCheckDesc.DBF";
        SingleQueryCheckUtil.CreateCheckDBFByFile(createNew, fileName, zmdLenth);
    }

    public static void createCheckIDXFile(boolean createNew, String path, int zmdLenth) throws SingleFileException {
        String fileName = path + "JQModalCheckDesc.JQI_INF";
        SingleQueryCheckUtil.CreateCheckDBFByFile(createNew, fileName, zmdLenth);
    }

    public static void createCheckInexByFile(boolean createNew, String fileName, int zmdLenth, int recCount, long fileSize) throws SingleFileException {
        if (createNew || !SinglePathUtil.getFileExists(fileName)) {
            ArrayList<IndexFieldDef> indexFields = new ArrayList<IndexFieldDef>();
            IndexFieldDef field = new IndexFieldDef();
            field.setFieldName("ZDM");
            field.setDataType('C');
            field.setFieldLen((short)zmdLenth);
            indexFields.add(field);
            SingleQueryCheckUtil.createIndexFileConfigByFields(fileName, false, recCount, fileSize, indexFields);
        }
    }

    private static void createIndexFileConfigByFields(String fileName, boolean unique, int recCount, long fileSize, List<IndexFieldDef> fields) throws SingleFileException {
        File file = SinglePathUtil.getNormalizeFile(fileName);
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
        try {
            ini.saveToFile(jqiFileName);
        }
        catch (Exception ex) {
            throw new SingleFileException(ex.getMessage(), ex);
        }
    }

    private static void CreateCheckDBFByFile(boolean createNew, String fileName, int zmdLenth) throws SingleFileException {
        if (createNew || !SinglePathUtil.getFileExists(fileName)) {
            DBFCreator creartor = new DBFCreator();
            int zmdLenth2 = zmdLenth;
            if (zmdLenth2 <= 0) {
                zmdLenth2 = 20;
            }
            try {
                creartor.addField("ZDM", 'C', zmdLenth2, 0);
                creartor.addField("MODALNAME", 'C', 254, 0);
                creartor.addField("DESC", 'C', 254, 0);
                creartor.createTable(fileName);
            }
            catch (Exception ex) {
                throw new SingleFileException(ex.getMessage(), ex);
            }
        }
    }
}

