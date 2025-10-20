/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.dc.bill.task;

import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;

public class DcBillMetaDesignInitDataTask
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        this.dcAgeUnClearedBiillMetaDesign();
        this.dcvoucherBiillMetaDesign();
    }

    private void dcAgeUnClearedBiillMetaDesign() {
    }

    private void dcvoucherBiillMetaDesign() {
    }
}

