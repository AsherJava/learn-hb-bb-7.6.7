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

public class EntInitWizardInitExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String INITIALIZATION_WIZARD_FILE_PATH = EntSystemParamInitConst.SYSTEM_PARAM_BASE_URL + "initializationWizard.nvdata";

    public void execute(DataSource dataSource) {
        EntLocalFileParamImportUtil.importParam(this.INITIALIZATION_WIZARD_FILE_PATH, progressLogConsumer -> this.logger.info("\u9884\u7f6e\u521d\u59cb\u5316\u5411\u5bfc\u521d\u59cb\u5316\uff1a" + progressLogConsumer.getMsg()));
    }
}

