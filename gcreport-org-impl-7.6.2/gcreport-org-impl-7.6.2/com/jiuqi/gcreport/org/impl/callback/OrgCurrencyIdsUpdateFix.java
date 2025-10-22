/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.impl.inspect.OrgCurrencyIdsInspect;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.HashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrgCurrencyIdsUpdateFix
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(OrgCurrencyIdsUpdateFix.class);

    public void execute(DataSource dataSource) {
        OrgCurrencyIdsInspect bean = (OrgCurrencyIdsInspect)SpringContextUtils.getBean(OrgCurrencyIdsInspect.class);
        HashMap<String, Object> params = new HashMap<String, Object>();
        try {
            bean.executeInspect(params);
            bean.executeFix(params);
        }
        catch (Exception e) {
            logger.error("\u4fee\u590d\u62a5\u8868\u5e01\u79cd\u5931\u8d25", e);
        }
    }
}

