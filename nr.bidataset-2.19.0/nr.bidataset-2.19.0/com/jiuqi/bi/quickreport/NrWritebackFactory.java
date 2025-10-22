/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.writeback.IWritebackExecutor
 *  com.jiuqi.bi.quickreport.writeback.IWritebackFieldProvider
 *  com.jiuqi.bi.quickreport.writeback.IWritebackMetaDataProvider
 *  com.jiuqi.bi.quickreport.writeback.WritebackException
 *  com.jiuqi.bi.quickreport.writeback.WritebackFactory
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.bi.quickreport.NrWritebackExecutor;
import com.jiuqi.bi.quickreport.NrWritebackFieldProvider;
import com.jiuqi.bi.quickreport.NrWritebackMetaDataProvider;
import com.jiuqi.bi.quickreport.NrWritebackParam;
import com.jiuqi.bi.quickreport.writeback.IWritebackExecutor;
import com.jiuqi.bi.quickreport.writeback.IWritebackFieldProvider;
import com.jiuqi.bi.quickreport.writeback.IWritebackMetaDataProvider;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.quickreport.writeback.WritebackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrWritebackFactory
extends WritebackFactory {
    private static final String TYPE = "com.jiuqi.nr.writeback";
    private static final String TITLE = "\u62a5\u8868\u6570\u636e\u65b9\u6848";
    @Autowired
    private NrWritebackParam param;

    public IWritebackMetaDataProvider crateMetaDataProvider() throws WritebackException {
        return new NrWritebackMetaDataProvider(this.param);
    }

    public IWritebackExecutor createExecutor(String tableName) throws WritebackException {
        return new NrWritebackExecutor(this.param, tableName);
    }

    public IWritebackFieldProvider createFieldProvider(String tableName) throws WritebackException {
        return new NrWritebackFieldProvider(this.param, tableName);
    }

    public String getTitle() {
        return TITLE;
    }

    public String getType() {
        return TYPE;
    }

    public boolean hasFieldProvider() {
        return true;
    }
}

