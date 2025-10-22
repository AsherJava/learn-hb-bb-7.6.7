/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 */
package com.jiuqi.nr.single.core.task.service.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.service.ISingleTableDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleTableDataHandlerImpl
implements ISingleTableDataHandler {
    private static final Logger logger = LoggerFactory.getLogger(SingleTableDataHandlerImpl.class);

    @Override
    public void start(Metadata<Column> metadata) throws SingleTaskException {
        logger.info("\u5f00\u59cb\u8bfb\u53d6");
    }

    @Override
    public void handle(DataRow dataRow) throws SingleTaskException {
    }

    @Override
    public void finish() throws SingleTaskException {
    }
}

