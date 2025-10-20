/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.common.systemparam.executor;

import com.jiuqi.common.systemparam.consts.EntSystemParamInitConst;
import com.jiuqi.common.systemparam.util.EntLocalFileParamImportUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntOrgTypeInitExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String INIT_ORG_TYPE_FILE_PATH = EntSystemParamInitConst.SYSTEM_PARAM_BASE_URL + "initOrgType.nvdata";

    public void execute(DataSource dataSource) {
        EntLocalFileParamImportUtil.importParam(this.INIT_ORG_TYPE_FILE_PATH, progressLogConsumer -> this.logger.info("\u9884\u7f6e\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u521d\u59cb\u5316\uff1a" + progressLogConsumer.getMsg()));
    }
}

