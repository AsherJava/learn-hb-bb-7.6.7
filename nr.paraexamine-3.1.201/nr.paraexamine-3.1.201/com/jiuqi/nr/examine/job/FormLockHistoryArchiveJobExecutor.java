/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.util.JqLib
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.examine.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.util.JqLib;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.examine.job.service.HistoryArchivePlanRegService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FormLockHistoryArchiveJobExecutor
extends JobExecutor {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    PeriodEngineService periodEngineService;
    private static final Logger logger = LoggerFactory.getLogger(FormLockHistoryArchiveJobExecutor.class);

    public void execute(JobContext jobContext) throws JobExecutionException {
        HistoryArchivePlanRegService historyArchivePlanRegService = (HistoryArchivePlanRegService)BeanUtils.getBean(HistoryArchivePlanRegService.class);
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        assert (dataSource != null) : "\u83b7\u53d6\u6570\u636e\u6e90\u5f02\u5e38";
        logger.info("\u5f00\u59cb\u6267\u884c\u8ba1\u5212\u4efb\u52a1\uff1a\u9501\u5b9a\u5386\u53f2\u72b6\u6001\u8868\u5f52\u6863");
        String runnerParameter = jobContext.getJob().getExtendedConfig();
        try {
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            GregorianCalendar calendar = new GregorianCalendar();
            JSONObject jsonObject = new JSONObject(runnerParameter);
            int archiveNums = jsonObject.getInt("archiveNums");
            String archivePath = jsonObject.getString("archivePath");
            boolean haspwd = jsonObject.getBoolean("haspwd");
            String zippwdEncode = jsonObject.getString("zippwd");
            String fcCodeDatetimeSql = "SELECT DS_BIZCODE,DD_DIM_KEY FROM NR_DATASCHEME_DIM,NR_DATASCHEME_SCHEME WHERE DD_DS_KEY = ? AND DD_TYPE= ?";
            String fcCode = "DS_BIZCODE";
            String timeType = "DD_DIM_KEY";
            String frontend = "NR_STATE_";
            String backend = "_FORMLOCK_HIS";
            String tableName = "NR_STATE_FORMLOCK_HIS";
            String zippwd = JqLib.decodePassword((String)zippwdEncode);
            Map fcCodeDatetimeMap = historyArchivePlanRegService.getFcCodeDatetimeMap(this.jdbcTemplate, fcCodeDatetimeSql, "DS_KEY", "4", fcCode, timeType, logger);
            boolean hasCustomPeriod = historyArchivePlanRegService.checkCustomPeriod(fcCodeDatetimeMap);
            Map<Object, Object> datetimeMap = new HashMap();
            if (hasCustomPeriod) {
                Object[] archiveNumsResult = (Object[])historyArchivePlanRegService.getArchiveNums(archiveNums, calendar, fcCodeDatetimeMap, periodAdapter, logger, frontend, backend);
                fcCodeDatetimeMap = (Map)archiveNumsResult[0];
                datetimeMap = (Map)archiveNumsResult[1];
            } else {
                datetimeMap = historyArchivePlanRegService.getArchiveNums(archiveNums, calendar);
            }
            Object[] csvFileResult = (Object[])historyArchivePlanRegService.csvFile(this.jdbcTemplate, fcCodeDatetimeMap, datetimeMap, archivePath, logger, frontend, backend);
            boolean hasFile = (Boolean)csvFileResult[0];
            ArrayList files = (ArrayList)csvFileResult[1];
            ArrayList deleteSqlList = (ArrayList)csvFileResult[2];
            if (hasFile) {
                historyArchivePlanRegService.zipCSVFiles(this.jdbcTemplate, calendar, tableName, archivePath, haspwd, zippwd, files, logger, deleteSqlList);
            }
        }
        catch (Exception e) {
            logger.error("\u8ba1\u5212\u4efb\u52a1\uff1a\u9501\u5b9a\u5386\u53f2\u72b6\u6001\u8868\u5f52\u6863\u6267\u884c\u5931\u8d25", e);
        }
    }
}

