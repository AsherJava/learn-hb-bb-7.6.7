/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.dao.JobParameterDao
 *  com.jiuqi.bi.core.jobs.dao.JobStorageDao
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskItemVO
 *  com.jiuqi.nvwa.jobmanager.service.PlanTaskItemService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.bi.core.jobs.dao.JobParameterDao;
import com.jiuqi.bi.core.jobs.dao.JobStorageDao;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskItemVO;
import com.jiuqi.nvwa.jobmanager.service.PlanTaskItemService;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DeleteRTAJobExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DeleteRTAJobExecutor.class);
    private PlanTaskItemService planTaskItemService = (PlanTaskItemService)SpringBeanUtils.getBean(PlanTaskItemService.class);
    private JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);

    public void execute(DataSource dataSource) throws Exception {
        try {
            PlanTaskItemVO visitsDayJobVO;
            PlanTaskItemVO paramsJobVO;
            PlanTaskItemVO visitsMinuteJobVO;
            PlanTaskItemVO clearHistoryJobVO = this.planTaskItemService.findVOById("rta_clear_history_job");
            if (null != clearHistoryJobVO) {
                this.deleteJob("rta_clear_history_job");
            }
            if (null != (visitsMinuteJobVO = this.planTaskItemService.findVOById("rta_visits_minute_job"))) {
                this.deleteJob("rta_visits_minute_job");
            }
            if (null != (paramsJobVO = this.planTaskItemService.findVOById("rta_params_job"))) {
                this.deleteJob("rta_params_job");
            }
            if (null != (visitsDayJobVO = this.planTaskItemService.findVOById("rta_visits_day_job"))) {
                this.deleteJob("rta_visits_day_job");
            }
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void deleteJob(String jobGuid) {
        try {
            Connection conn = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            this.execute(jobGuid, conn);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void execute(String jobGuid, Connection conn) throws SQLException {
        try {
            conn.setAutoCommit(false);
            JobStorageDao.deleteJob((Connection)conn, (String)jobGuid);
            JobParameterDao.deleteParameters((Connection)conn, (String)jobGuid);
            conn.commit();
        }
        catch (Exception e) {
            conn.rollback();
            logger.error(e.getMessage(), e);
        }
        finally {
            conn.close();
        }
    }
}

