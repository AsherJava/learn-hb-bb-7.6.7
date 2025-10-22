/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.bufdb.BufDBException
 *  com.jiuqi.bi.bufdb.db.ISchema
 */
package com.jiuqi.nr.io.bufdb;

import com.jiuqi.bi.bufdb.BufDBException;
import com.jiuqi.bi.bufdb.db.ISchema;

public interface IIOBufDBProvider {
    public ISchema getSchema() throws BufDBException;
}

