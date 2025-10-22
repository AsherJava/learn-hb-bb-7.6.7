/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.reportparam.init;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.reportparam.dao.GcReportParamLockDao;
import com.jiuqi.gcreport.reportparam.eo.GcReportParamLockEO;
import com.jiuqi.gcreport.reportparam.init.executor.GcReportDataEntryTemplateInitExecutor;
import com.jiuqi.gcreport.reportparam.init.executor.GcReportTaskGroupInitExecutor;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.Date;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GcReportParamInitialization
implements ModuleInitiator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GcReportParamLockDao gcReportParamLockDao;

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        if (!this.isEmptyDbStart()) {
            return;
        }
        this.logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u62a5\u8868\u53c2\u6570\u9884\u7f6e\u7a7a\u5e93\u542f\u52a8\u7cfb\u7edf\u53c2\u6570");
        GcReportParamLockEO gcReportParamLockEO = new GcReportParamLockEO();
        gcReportParamLockEO.setId(UUIDUtils.emptyUUIDStr());
        gcReportParamLockEO.setLocked(0);
        gcReportParamLockEO.setLockTime(new Date());
        this.gcReportParamLockDao.save(gcReportParamLockEO);
        new GcReportDataEntryTemplateInitExecutor().execute(null);
        this.logger.info("\u62a5\u8868\u53c2\u6570\u9884\u7f6e\u7a7a\u5e93\u542f\u52a8\u7cfb\u7edf\u53c2\u6570\u3010\u6570\u636e\u5f55\u5165\u6a21\u677f\u3011\u9884\u7f6e\u5b8c\u6210");
        new GcReportTaskGroupInitExecutor().execute(null);
        this.logger.info("\u62a5\u8868\u53c2\u6570\u9884\u7f6e\u7a7a\u5e93\u542f\u52a8\u7cfb\u7edf\u53c2\u6570\u3010\u4efb\u52a1\u5206\u7ec4\u3011\u9884\u7f6e\u5b8c\u6210");
        this.logger.info("\u62a5\u8868\u53c2\u6570\u9884\u7f6e\u7a7a\u5e93\u542f\u52a8\u7cfb\u7edf\u53c2\u6570\u9884\u7f6e\u5b8c\u6210");
        String sql1 = "insert into auth_org_right (ID, BIZTYPE, BIZNAME, AUTHTYPE, ORGCATEGORY, ORGNAME, ATMANAGE, ATACCESS, ATWRITE, ATEDIT, ATREPORT, ATSUBMIT, ATAPPROVAL, ATREAD_UN_PUBLISH, ATPUBLISH)\nvalues ('c9a54ce2-10cb-465e-9e53-e3c5908aedd4', 0, '-', 0, '-', 'rule_belong', 0, 1, 1, 1, 1, 0, 1, 0, 0)";
        String sql2 = "insert into auth_org_right (ID, BIZTYPE, BIZNAME, AUTHTYPE, ORGCATEGORY, ORGNAME, ATMANAGE, ATACCESS, ATWRITE, ATEDIT, ATREPORT, ATSUBMIT, ATAPPROVAL, ATREAD_UN_PUBLISH, ATPUBLISH)\nvalues ('b71f080a-b340-4cfa-b206-96aedafcb2e6', 0, '-', 0, '-', 'rule_all_children', 0, 1, 1, 1, 1, 0, 1, 0, 0)";
        EntNativeSqlDefaultDao.getInstance().execute(sql1);
        EntNativeSqlDefaultDao.getInstance().execute(sql2);
    }

    private boolean isEmptyDbStart() {
        Long minSfVersion = (Long)this.jdbcTemplate.queryForObject("select min(updatetime) from sf_version t", Long.class);
        if (minSfVersion == null) {
            return true;
        }
        return System.currentTimeMillis() - minSfVersion < 3600000L;
    }
}

