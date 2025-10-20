/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.AbstractUIConfiguration;
import com.jiuqi.bi.core.jobs.extension.UINavigationMode;

public abstract class AbstractSinglePageUIConfiguration
extends AbstractUIConfiguration {
    @Override
    public final UINavigationMode getNavigationMode() {
        return UINavigationMode.SINGLE_PAGE;
    }

    @Override
    public final String getJobConfigPageUrlPattern() {
        return "\u6846\u67b6\u4e0d\u652f\u6301\u914d\u7f6e\u9875\u9762";
    }

    public abstract String getNavigationUrl();
}

