/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.definition.upgrade.regiontabupdate;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.upgrade.regiontabupdate.RegionTabProcessor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionTabUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        Logger logger = LoggerFactory.getLogger(RegionTabProcessor.class);
        logger.info("\u5f00\u59cb\u6269\u5145\u9875\u7b7e\u6570\u636e");
        RegionTabProcessor bean = (RegionTabProcessor)SpringBeanUtils.getBean(RegionTabProcessor.class);
        bean.updateData();
        logger.info("\u6269\u5145\u9875\u7b7e\u6570\u636e\u5b8c\u6210");
    }
}

