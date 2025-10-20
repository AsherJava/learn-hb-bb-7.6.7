/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.snapshot;

import com.jiuqi.bi.quickreport.snapshot.ISnapshotProvider;
import com.jiuqi.bi.quickreport.snapshot.SnapshotBean;
import com.jiuqi.bi.quickreport.snapshot.SnapshotData;
import com.jiuqi.bi.quickreport.snapshot.SnapshotException;
import java.util.List;

public class SnapshotManager {
    private static final SnapshotManager instance = new SnapshotManager();
    private ISnapshotProvider provider;

    public static SnapshotManager getInstance() {
        return instance;
    }

    public void setProvider(ISnapshotProvider provider) {
        this.provider = provider;
    }

    public ISnapshotProvider getProvider() {
        return this.provider;
    }

    public boolean enable() throws SnapshotException {
        if (this.provider == null) {
            return false;
        }
        return this.provider.enable();
    }

    public SnapshotData get(String guid) throws SnapshotException {
        if (this.provider == null) {
            throw new SnapshotException("\u672a\u6ce8\u518c\u5feb\u7167\u64cd\u4f5c\u63d0\u4f9b\u5668");
        }
        return this.provider.get(guid);
    }

    public List<SnapshotBean> getByResourceId(String resourceId) throws SnapshotException {
        if (this.provider == null) {
            throw new SnapshotException("\u672a\u6ce8\u518c\u5feb\u7167\u64cd\u4f5c\u63d0\u4f9b\u5668");
        }
        return this.provider.getByResourceId(resourceId);
    }
}

