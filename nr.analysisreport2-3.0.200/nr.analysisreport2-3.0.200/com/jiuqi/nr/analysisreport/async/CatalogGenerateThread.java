/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.async;

import com.jiuqi.nr.analysisreport.helper.CatalogGenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatalogGenerateThread
implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CatalogGenerateThread.class);
    private final Object object;

    public CatalogGenerateThread(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        CatalogGenHelper genHelper = new CatalogGenHelper();
        try {
            genHelper.genChapterCatalog(this.object);
        }
        catch (Exception e) {
            logger.error("\u5f02\u6b65\u751f\u6210\u76ee\u5f55\u5931\u8d25", (Object)e.getMessage());
        }
    }
}

