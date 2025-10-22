/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.office.template.document.DocumentContext
 *  com.jiuqi.nvwa.office.template.document.ISchemaExceptionHandler
 *  com.jiuqi.nvwa.office.template.document.fragment.AbstractDocumentFragment
 */
package com.jiuqi.nr.datareport.service.impl;

import com.jiuqi.nvwa.office.template.document.DocumentContext;
import com.jiuqi.nvwa.office.template.document.ISchemaExceptionHandler;
import com.jiuqi.nvwa.office.template.document.fragment.AbstractDocumentFragment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NrDocumentExceptionHandler
implements ISchemaExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(NrDocumentExceptionHandler.class);

    public boolean handleException(Exception e, AbstractDocumentFragment fragment, DocumentContext context) {
        logger.error("\u6807\u7b7e\u89e3\u6790\u5f02\u5e38:" + fragment.getFullContent() + "-" + context.getPeriod() + "-" + context.getPeriodTitle() + "-" + context.getUnit() + "-" + context.getUnitTitle() + "-\u62a5\u544a\u6a21\u677fkey:" + context.getKey() + "-" + e.getMessage(), e);
        return true;
    }
}

