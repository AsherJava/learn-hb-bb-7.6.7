/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel.spire;

import com.jiuqi.bi.office.SpireInitializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Locale;

public class SpireHelper {
    public static void loadSpireLicence() {
        SpireInitializer.initialize();
    }

    public SpireHelper() {
        SpireHelper.loadSpireLicence();
    }

    public void excel2pdf(ByteArrayOutputStream excel_os, OutputStream pdf_os) throws Exception {
        this.excel2file(excel_os, pdf_os, "PDF");
    }

    public void excel2pdf(InputStream excel_is, OutputStream pdf_os) throws Exception {
        this.excel2file(excel_is, pdf_os, "PDF");
    }

    public void excel2file(ByteArrayOutputStream excel_os, OutputStream file_os, String file_type) throws Exception {
        try (ByteArrayInputStream excel_is = new ByteArrayInputStream(excel_os.toByteArray());){
            this.excel2file(excel_is, file_os, file_type);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void excel2file(InputStream excel_is, OutputStream file_os, String file_type) throws Exception {
        file_type = file_type.toUpperCase();
        Locale aDefault = Locale.getDefault();
        try {
            Class<?> enumClass;
            Class<?> spireClass;
            Locale.setDefault(Locale.US);
            try {
                spireClass = Class.forName("com.spire.xls.Workbook");
                enumClass = Class.forName("com.spire.xls.FileFormat");
            }
            catch (ClassNotFoundException e) {
                throw new Exception("\u65e0\u6cd5\u52a0\u8f7d[" + file_type + "]\u7ec4\u4ef6", e);
            }
            Object wb = spireClass.newInstance();
            Method loadMethod = spireClass.getMethod("loadFromStream", InputStream.class);
            loadMethod.invoke(wb, excel_is);
            ?[] constants = enumClass.getEnumConstants();
            Object fFormat = null;
            for (Object constant : constants) {
                Method nameMethod = enumClass.getMethod("name", new Class[0]);
                if (!file_type.equals(nameMethod.invoke(constant, new Object[0]))) continue;
                fFormat = constant;
                break;
            }
            if (fFormat == null) {
                throw new RuntimeException("\u672a\u627e\u5230[" + file_type + "]\u7c7b\u578b\u7684\u679a\u4e3e");
            }
            Method saveToStreamMethod = spireClass.getMethod("saveToStream", OutputStream.class, enumClass);
            saveToStreamMethod.invoke(wb, file_os, fFormat);
        }
        finally {
            Locale.setDefault(aDefault);
        }
    }
}

