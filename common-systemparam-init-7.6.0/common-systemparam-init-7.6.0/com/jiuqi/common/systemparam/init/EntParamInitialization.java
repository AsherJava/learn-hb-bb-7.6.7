/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.common.systemparam.init;

import com.jiuqi.common.systemparam.executor.EntHomePagesInitExecutor;
import com.jiuqi.common.systemparam.executor.EntInitWizardInitExecutor;
import com.jiuqi.common.systemparam.executor.EntMenuInitExecutor;
import com.jiuqi.common.systemparam.executor.EntOrgDataInitExecutor;
import com.jiuqi.common.systemparam.executor.EntUserInitExecutor;
import com.jiuqi.common.systemparam.executor.EntUserRoleInitExecutor;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EntParamInitialization
implements ModuleInitiator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        if (!this.isEmptyDbStart()) {
            return;
        }
        new EntMenuInitExecutor().execute(null);
        new EntHomePagesInitExecutor().execute(null);
        new EntOrgDataInitExecutor().execute(null);
        new EntUserInitExecutor().execute(null);
        new EntUserRoleInitExecutor().execute(null);
        new EntInitWizardInitExecutor().execute(null);
    }

    private boolean isEmptyDbStart() {
        Integer orgCount = (Integer)this.jdbcTemplate.queryForObject("select count(1) from MD_ORG t", Integer.class);
        if (orgCount > 0) {
            return false;
        }
        Long minSfVersion = (Long)this.jdbcTemplate.queryForObject("select min(updatetime) from sf_version t", Long.class);
        if (minSfVersion == null) {
            return true;
        }
        return System.currentTimeMillis() - minSfVersion < 3600000L;
    }
}

