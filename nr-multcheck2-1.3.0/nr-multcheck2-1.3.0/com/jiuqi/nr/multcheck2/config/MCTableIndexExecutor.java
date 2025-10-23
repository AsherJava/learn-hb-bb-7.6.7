/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeNoCacheServiceImpl
 */
package com.jiuqi.nr.multcheck2.config;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeNoCacheServiceImpl;
import com.jiuqi.nr.multcheck2.service.IMCTableService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCTableIndexExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(MCTableIndexExecutor.class);

    public void execute(DataSource dataSource) throws Exception {
        IMCTableService mcTableService = (IMCTableService)SpringBeanUtils.getBean(IMCTableService.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(RuntimeDataSchemeNoCacheServiceImpl.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.logger.info("\u6a21\u5757[\u7efc\u5408\u5ba1\u6838]\u52a8\u6001\u8868\u7d22\u5f15\u4fee\u590d\u5f00\u59cb\uff1a{}", (Object)sdf.format(new Date()));
        List allDataScheme = runtimeDataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : allDataScheme) {
            try {
                mcTableService.dealIndex(dataScheme);
            }
            catch (Exception e) {
                this.logger.info("\u6a21\u5757[\u7efc\u5408\u5ba1\u6838]\u6570\u636e\u65b9\u6848[{}]\u52a8\u6001\u8868\u7d22\u5f15\u4fee\u590d\u5931\u8d25\uff1a{}", dataScheme.getTitle() + dataScheme.getCode(), sdf.format(new Date()), e);
            }
        }
        this.logger.info("\u6a21\u5757[\u7efc\u5408\u5ba1\u6838]\u52a8\u6001\u8868\u7d22\u5f15\u4fee\u590d\u7ed3\u675f\uff1a{}", (Object)sdf.format(new Date()));
    }
}

