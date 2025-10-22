/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.dataentry.internal.upgrade;

import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DelReviewInfoExecutor
implements CustomClassExecutor {
    private static final Logger logger = LogFactory.getLogger(DelReviewInfoExecutor.class);
    protected JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
    protected IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);

    public void execute(DataSource dataSource) throws Exception {
        List allTaskDefines = this.iRunTimeViewController.getAllReportTaskDefines();
        String sqlPrefix = "DROP TABLE ";
        for (TaskDefine taskDefine : allTaskDefines) {
            List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                try {
                    this.jdbcTemplate.execute(sqlPrefix + "DE_RWIF_" + formSchemeDefine.getFormSchemeCode());
                    logger.info("\u6210\u529f\u5220\u9664\u62a5\u8868\u5ba1\u6838\u4fe1\u606f\u8868 DE_RWIF_" + formSchemeDefine.getFormSchemeCode());
                }
                catch (Exception e) {
                    logger.error("\u62a5\u8868\u5ba1\u6838\u4fe1\u606f\u8868 DE_RWIF_" + formSchemeDefine.getFormSchemeCode() + " \u5220\u9664\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
                }
            }
        }
    }
}

