/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import javax.sql.rowset.serial.SerialClob;

public class DCQueryClobUtil {
    private static final String CLOB_ERROR = "clob\u8f6c\u6362\u6210\u5b57\u7b26\u4e32\u65f6\u53d1\u751f\u5f02\u5e38";

    public static String clobToString(Clob clob) {
        if (clob == null) {
            return null;
        }
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        }
        catch (Exception e) {
            throw new DefinedQueryRuntimeException(CLOB_ERROR, (Throwable)e);
        }
        BufferedReader br = new BufferedReader(is);
        String str = null;
        try {
            str = br.readLine();
        }
        catch (Exception e) {
            throw new DefinedQueryRuntimeException(CLOB_ERROR, (Throwable)e);
        }
        StringBuilder sb = new StringBuilder();
        while (str != null) {
            sb.append(str).append("\n");
            try {
                str = br.readLine();
            }
            catch (Exception e) {
                throw new DefinedQueryRuntimeException(CLOB_ERROR, (Throwable)e);
            }
        }
        return sb.toString();
    }

    public static Clob stringToClob(String str) {
        if (null == str) {
            return null;
        }
        try {
            return new SerialClob(str.toCharArray());
        }
        catch (Exception e) {
            return null;
        }
    }
}

