/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.attachment.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.attachment.deploy.ManualDeployTableExecutor;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradeFileTableExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(UpgradeFileTableExecutor.class);
    private IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);

    public void execute(DataSource dataSource) throws Exception {
        ManualDeployTableExecutor manualDeployTableExecutor = new ManualDeployTableExecutor();
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : allDataScheme) {
            try {
                manualDeployTableExecutor.insertCol(dataScheme);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

