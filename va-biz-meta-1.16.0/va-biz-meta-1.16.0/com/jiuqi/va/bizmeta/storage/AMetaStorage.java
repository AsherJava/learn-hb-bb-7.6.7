/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bizmeta.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AMetaStorage {
    public static final Logger logger = LoggerFactory.getLogger(AMetaStorage.class);

    public final void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = this.getCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            logger.error("\u5143\u6570\u636e\u521d\u59cb\u5316\u8868\u7ed3\u6784\u5f02\u5e38", e);
        }
    }

    public JTableModel getSyncCreateJTM(String tenantName) {
        return this.getCreateJTM(tenantName);
    }

    protected abstract JTableModel getCreateJTM(String var1);
}

