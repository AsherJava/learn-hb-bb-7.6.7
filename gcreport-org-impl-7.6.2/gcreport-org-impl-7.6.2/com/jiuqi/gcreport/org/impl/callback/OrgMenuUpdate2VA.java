/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.impl.callback.UpdateMenService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrgMenuUpdate2VA
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(OrgMenuUpdate2VA.class);

    public void execute(DataSource dataSource) {
        try {
            UpdateMenService bean = (UpdateMenService)SpringContextUtils.getBean(UpdateMenService.class);
            bean.updateMenu();
        }
        catch (Exception e) {
            logger.error("\u4fee\u6539\u57fa\u7840\u7ec4\u7ec7\u83dc\u5355\u5931\u8d25\uff0c\u8bf7\u624b\u52a8\u66ff\u6362", e);
        }
    }
}

