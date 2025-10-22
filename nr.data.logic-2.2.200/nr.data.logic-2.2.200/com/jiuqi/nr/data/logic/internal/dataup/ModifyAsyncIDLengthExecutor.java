/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.deploy.TableDeployService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

public class ModifyAsyncIDLengthExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ModifyAsyncIDLengthExecutor.class);
    private static final TableDeployService tableDeployService = (TableDeployService)BeanUtil.getBean(TableDeployService.class);
    private static final IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private static final JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
    private static final String SQL_TRUNCATE = "TRUNCATE TABLE ";

    public void execute(DataSource dataSource) throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE NR_PARAM_REVIEW_INFO");
        logger.info("\u6279\u91cf\u5ba1\u6838\u5386\u53f2\u8bb0\u5f55\u8868{}\u6570\u636e\u6e05\u9664\u5b8c\u6210", (Object)"NR_PARAM_REVIEW_INFO");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            List allTaskDefines = runTimeViewController.getAllTaskDefines();
            if (!CollectionUtils.isEmpty(allTaskDefines)) {
                for (TaskDefine taskDefine : allTaskDefines) {
                    ModifyAsyncIDLengthExecutor.executeByTask(taskDefine, executorService);
                }
            }
            executorService.shutdown();
            ModifyAsyncIDLengthExecutor.waitUntilFinish(executorService);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void waitUntilFinish(ExecutorService executorService) {
        while (!executorService.isTerminated()) {
        }
        logger.info("\u7cfb\u7edf\u6240\u6709\u4efb\u52a1\u62a5\u8868\u65b9\u6848\u4e0b\u7684ALLCKR\u3001CKR\u8868\u5f02\u6b65\u4efb\u52a1ID\u5b57\u6bb5\u957f\u5ea6\u5347\u7ea7\u5b8c\u6210");
    }

    private static void executeByTask(TaskDefine taskDefine, ExecutorService executorService) {
        executorService.execute(() -> {
            try {
                List formSchemeDefines = runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                    for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                        ModifyAsyncIDLengthExecutor.executeByFormScheme(taskDefine, formSchemeDefine);
                    }
                }
                logger.info(taskDefine.getKey() + "\u4efb\u52a1\u4e0b\u7684ALLCKR\u3001CKR\u8868\u5f02\u6b65\u4efb\u52a1ID\u5b57\u6bb5\u957f\u5ea6\u5347\u7ea7\u6210\u529f");
            }
            catch (Exception e) {
                logger.error(taskDefine.getKey() + "\u4efb\u52a1\u4e0b\u7684ALLCKR\u3001CKR\u8868\u5f02\u6b65\u4efb\u52a1ID\u5b57\u6bb5\u957f\u5ea6\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
            }
        });
    }

    private static void executeByFormScheme(TaskDefine taskDefine, FormSchemeDefine formSchemeDefine) {
        try {
            jdbcTemplate.execute(SQL_TRUNCATE + CheckTableNameUtil.getAllCKRTableName(formSchemeDefine.getFormSchemeCode()));
            tableDeployService.deployALLCKRTable(taskDefine, formSchemeDefine);
            jdbcTemplate.execute(SQL_TRUNCATE + CheckTableNameUtil.getCKRTableName(formSchemeDefine.getFormSchemeCode()));
            tableDeployService.deployCKRTable(taskDefine, formSchemeDefine);
            logger.info(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684ALLCKR\u3001CKR\u8868\u5f02\u6b65\u4efb\u52a1ID\u5b57\u6bb5\u957f\u5ea6\u5347\u7ea7\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684ALLCKR\u3001CKR\u8868\u5f02\u6b65\u4efb\u52a1ID\u5b57\u6bb5\u957f\u5ea6\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
        }
    }
}

