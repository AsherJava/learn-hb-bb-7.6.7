/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRowFilter
implements RowFilter {
    private static final Logger logger = LoggerFactory.getLogger(TestRowFilter.class);

    @Override
    public String name() {
        return "TestRowFilter";
    }

    @Override
    public boolean supportFormula() {
        return false;
    }

    @Override
    public String toFormula() {
        return "1 >= 0";
    }

    @Override
    public boolean filter(String formula, IContext context) {
        if (logger.isDebugEnabled()) {
            logger.debug(formula);
        }
        return true;
    }
}

