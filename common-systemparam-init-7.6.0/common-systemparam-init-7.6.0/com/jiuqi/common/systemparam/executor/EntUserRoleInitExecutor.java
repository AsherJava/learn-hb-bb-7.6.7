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

public class EntUserRoleInitExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String INIT_ROLE_PERMISSION_FILE_PATH = EntSystemParamInitConst.SYSTEM_PARAM_BASE_URL + "initRolePermission.nvdata";

    public void execute(DataSource dataSource) throws Exception {
        EntLocalFileParamImportUtil.importParam(this.INIT_ROLE_PERMISSION_FILE_PATH, progressLogConsumer -> this.logger.info("\u9884\u7f6e\u89d2\u8272\u6743\u9650\u4fe1\u606f\u521d\u59cb\u5316\uff1a" + progressLogConsumer.getMsg()));
    }
}

