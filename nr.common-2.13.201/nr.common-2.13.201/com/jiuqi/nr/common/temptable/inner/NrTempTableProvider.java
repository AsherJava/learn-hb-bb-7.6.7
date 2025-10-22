/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.DefaultTempTableProvider
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.common.temptable.inner;

import com.jiuqi.bi.database.temp.DefaultTempTableProvider;
import com.jiuqi.bi.util.OrderGenerator;
import java.security.SecureRandom;

public class NrTempTableProvider
extends DefaultTempTableProvider {
    protected String createTableName(String type) {
        StringBuilder tableName = new StringBuilder(type.toUpperCase());
        tableName.append("_");
        tableName.append(OrderGenerator.newOrder());
        tableName.append("_");
        SecureRandom rand = new SecureRandom();
        int tableIndex = rand.nextInt(10000);
        tableName.append(tableIndex);
        return tableName.toString();
    }
}

