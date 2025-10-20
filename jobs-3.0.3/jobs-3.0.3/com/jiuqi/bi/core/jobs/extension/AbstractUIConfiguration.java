/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.IUIConfiguration;
import com.jiuqi.bi.core.jobs.extension.UINavigationMode;

public abstract class AbstractUIConfiguration
implements IUIConfiguration {
    public UINavigationMode getNavigationMode() {
        return UINavigationMode.NAVIGATION_TREE;
    }

    public boolean canNewFolder() {
        return true;
    }

    public boolean canDeleteFolder() {
        return true;
    }

    public boolean canEditFolder() {
        return true;
    }

    public boolean showNewFolder() {
        return true;
    }

    public boolean canNewJob() {
        return true;
    }

    public boolean showNewJob() {
        return true;
    }

    public boolean canMove() {
        return false;
    }
}

