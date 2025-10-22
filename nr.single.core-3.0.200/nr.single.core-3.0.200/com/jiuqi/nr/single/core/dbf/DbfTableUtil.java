/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.dbf;

import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfTableImpl;
import com.jiuqi.nr.single.core.dbf.IDbfTable;

public class DbfTableUtil {
    public static IDbfTable getDbfTable(String fileName, String enCoding, boolean create, boolean batchMode) throws DbfException {
        return new DbfTableImpl(fileName, enCoding, create, batchMode);
    }

    public static IDbfTable getDbfTable(String fileName, String enCoding, boolean create) throws DbfException {
        return new DbfTableImpl(fileName, enCoding, create);
    }

    public static IDbfTable getDbfTable(String fileName) throws DbfException {
        return new DbfTableImpl(fileName);
    }

    public static IDbfTable getDbfTable(String fileName, String encodingName) throws Exception {
        return new DbfTableImpl(fileName, encodingName);
    }
}

