/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.dimension.executor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.dimension.executor.PreDimensionUpgrade;
import com.jiuqi.gcreport.dimension.update.UpdateDimensionService;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.Objects;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DimensionUpdateInit
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");

    public void execute(DataSource dataSource) throws Exception {
        PreDimensionUpgrade preDimensionUpgrade = (PreDimensionUpgrade)SpringContextUtils.getBean(PreDimensionUpgrade.class);
        if (Objects.nonNull(preDimensionUpgrade)) {
            logger.info("7.6.0\u62a5\u8868\u5e93\u7ef4\u5ea6\u7ba1\u7406\u5347\u7ea7-\u524d\u7f6e\u64cd\u4f5c\u5f00\u59cb");
            preDimensionUpgrade.doBefore(null);
            logger.info("7.6.0\u62a5\u8868\u5e93\u7ef4\u5ea6\u7ba1\u7406\u5347\u7ea7-\u524d\u7f6e\u64cd\u4f5c\u7ed3\u675f");
        }
        logger.info("7.6.0\u62a5\u8868\u5e93\u7ef4\u5ea6\u7ba1\u7406\u5347\u7ea7\u5f00\u59cb");
        UpdateDimensionService dimensionUpdateService = (UpdateDimensionService)SpringContextUtils.getBean(UpdateDimensionService.class);
        dimensionUpdateService.updateDimension(null);
        logger.info("7.6.0\u62a5\u8868\u5e93\u7ef4\u5ea6\u7ba1\u7406\u5347\u7ea7\u7ed3\u675f");
    }
}

