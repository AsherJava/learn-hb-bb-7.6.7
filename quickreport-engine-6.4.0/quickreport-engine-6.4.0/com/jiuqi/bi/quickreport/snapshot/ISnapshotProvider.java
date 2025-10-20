/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.snapshot;

import com.jiuqi.bi.quickreport.snapshot.SnapshotBean;
import com.jiuqi.bi.quickreport.snapshot.SnapshotData;
import com.jiuqi.bi.quickreport.snapshot.SnapshotException;
import java.util.List;

public interface ISnapshotProvider {
    public boolean enable() throws SnapshotException;

    public SnapshotData get(String var1) throws SnapshotException;

    public List<SnapshotBean> getByResourceId(String var1) throws SnapshotException;
}

