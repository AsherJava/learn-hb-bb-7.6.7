/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataSnapshot.service.impl;

import com.jiuqi.nr.dataSnapshot.service.IDataSnapshotBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class EnhancedDataSnapshotHelper {
    private static IDataSnapshotBaseService dataSnapshotBaseService;

    @Autowired
    public void setiDataSnapshotBaseService(IDataSnapshotBaseService dataSnapshotBaseService) {
        EnhancedDataSnapshotHelper.dataSnapshotBaseService = dataSnapshotBaseService;
    }

    public static IDataSnapshotBaseService getDataSnapshotBaseService() {
        return dataSnapshotBaseService;
    }
}

