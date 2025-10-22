/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.dafafill.init;

import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NRDataFillInit
implements ModuleInitiator {
    public static final Logger log = LoggerFactory.getLogger(NRDataFillInit.class);

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

