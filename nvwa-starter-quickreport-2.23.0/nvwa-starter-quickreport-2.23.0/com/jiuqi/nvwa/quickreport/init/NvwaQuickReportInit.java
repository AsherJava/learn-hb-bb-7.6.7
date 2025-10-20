/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nvwa.quickreport.init;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;

@Component
public class NvwaQuickReportInit
implements ModuleInitiator {
    public void init(ServletContext context) {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        this.initBucket();
    }

    private void initBucket() throws Exception {
        ObjectStorageEngine engine = ObjectStorageEngine.newInstance();
        Bucket bucket = engine.getBucket("QUICKREPORT");
        if (bucket == null) {
            bucket = new Bucket("QUICKREPORT");
            bucket.setDesc("\u5b58\u653e\u5206\u6790\u8868\u76f8\u5173\u7684\u56fe\u7247\u3001\u9644\u4ef6\u7b49\u8d44\u6e90");
            engine.createBucket(bucket);
        }
    }
}

