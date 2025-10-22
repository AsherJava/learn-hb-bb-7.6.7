/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.dataup.B2CUp;
import javax.sql.DataSource;

public class RCIC2BExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        String[] pk = new String[]{"RWIF_KEY"};
        B2CUp b2CUp = new B2CUp("NR_PARAM_REVIEW_INFO", "RWIF_CHECK_INFO", pk, dataSource);
        b2CUp.up();
    }
}

