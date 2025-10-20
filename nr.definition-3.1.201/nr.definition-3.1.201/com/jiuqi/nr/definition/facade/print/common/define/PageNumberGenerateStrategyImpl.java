/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.common.define;

import com.jiuqi.nr.definition.facade.print.common.define.AbstractPageNumberGenerateStrategy;

public class PageNumberGenerateStrategyImpl
extends AbstractPageNumberGenerateStrategy {
    public PageNumberGenerateStrategyImpl(int pageNumberOffset, int totalPageNumber) {
        super(totalPageNumber);
        this.pageNumberOffset = pageNumberOffset;
    }

    @Override
    public void accumulate(int pageCount) {
        this.pageNumberOffset += pageCount;
        ++this.templatePageOrderNumber;
    }
}

