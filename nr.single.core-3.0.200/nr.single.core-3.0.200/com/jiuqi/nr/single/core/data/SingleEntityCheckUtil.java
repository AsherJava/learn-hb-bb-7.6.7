/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.data;

import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.util.SinglePathUtil;

public class SingleEntityCheckUtil {
    public static void CreateCheckDBFFile(boolean createNew, String path, int zmdLenth) throws Exception {
        String fileName = path + "SYS_JSHDB.DBF";
        SingleEntityCheckUtil.CreateCheckDBFByFile(createNew, fileName, zmdLenth);
    }

    public static void CreateSBCheckDBFFile(boolean createNew, String path, int zmdLenth) throws Exception {
        String fileName = path + "SYS_SBJSHDB.DBF";
        SingleEntityCheckUtil.CreateCheckDBFByFile(createNew, fileName, zmdLenth);
    }

    private static void CreateCheckDBFByFile(boolean createNew, String fileName, int zmdLenth) throws Exception {
        if (createNew || !SinglePathUtil.getFileExists(fileName)) {
            DBFCreator creartor = new DBFCreator();
            int zmdLenth2 = zmdLenth;
            if (zmdLenth2 <= 0) {
                zmdLenth2 = 20;
            }
            creartor.addField("QYDM", 'C', zmdLenth2, 0);
            creartor.addField("QYMC", 'C', 250, 0);
            creartor.addField("JSYY", 'C', 1, 0);
            creartor.createTable(fileName);
        }
    }
}

