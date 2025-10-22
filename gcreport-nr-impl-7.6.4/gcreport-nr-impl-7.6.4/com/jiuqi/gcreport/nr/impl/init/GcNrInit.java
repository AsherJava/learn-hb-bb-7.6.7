/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.update.ReportDimUpdate
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.nr.impl.init;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.update.ReportDimUpdate;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GcNrInit
implements ModuleInitiator {
    private Logger logger = LoggerFactory.getLogger(GcNrInit.class);

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) {
        DataSource datasource = ((JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class)).getDataSource();
        ReportDimUpdate reportDimUpdate = new ReportDimUpdate();
        try {
            reportDimUpdate.execute(datasource);
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u65b9\u6848\u60c5\u666f\u5173\u8054\u5b57\u6bb5\u5347\u7ea7\u51fa\u9519", e);
        }
    }
}

