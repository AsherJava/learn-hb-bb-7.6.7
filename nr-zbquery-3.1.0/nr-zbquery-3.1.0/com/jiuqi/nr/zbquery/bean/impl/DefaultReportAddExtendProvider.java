/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.zbquery.bean.facade.IReportAddExtendProvider;
import org.springframework.stereotype.Component;

@Component
public class DefaultReportAddExtendProvider
implements IReportAddExtendProvider {
    private static final String PRODLINE = "@nr";
    private static final String NAME = "zb-selector";
    private static final String TYPE = "lib";
    private static final String COMPONENTNAME = "zbSelector";

    @Override
    public String getProdLine() {
        return PRODLINE;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getComponentName() {
        return COMPONENTNAME;
    }

    @Override
    public double getOrder() {
        return 0.0;
    }
}

