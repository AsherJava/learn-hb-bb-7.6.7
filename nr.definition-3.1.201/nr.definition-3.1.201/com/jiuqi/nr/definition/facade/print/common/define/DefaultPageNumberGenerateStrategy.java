/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.common.define;

import com.jiuqi.nr.definition.facade.print.common.define.AbstractPageNumberGenerateStrategy;

public class DefaultPageNumberGenerateStrategy
extends AbstractPageNumberGenerateStrategy {
    public DefaultPageNumberGenerateStrategy() {
        super(0);
    }

    @Override
    public void accumulate(int pageCount) {
        this.pageNumberOffset += pageCount;
        ++this.templatePageOrderNumber;
    }
}

