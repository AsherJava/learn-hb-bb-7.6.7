/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.archive.service.impl;

import com.jiuqi.gcreport.archive.service.GcArchiveService;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GCArchiveReadWriteAccess
implements IReadWriteAccess {
    @Autowired
    private GcArchiveService archiveService;

    public String getName() {
        return "archiveAccess";
    }

    public boolean isServerAccess() {
        return true;
    }

    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(Boolean.valueOf(true), "");
    }

    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(Boolean.valueOf(true), "");
    }
}

