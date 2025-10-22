/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.datastatus.upgrade;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datastatus.internal.model.TableDeployService;
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
import org.springframework.util.CollectionUtils;

public class AlterEntryCol
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AlterEntryCol.class);

    public void execute(DataSource dataSource) throws Exception {
        TableDeployService tableDeployService = (TableDeployService)BeanUtil.getBean(TableDeployService.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            List allTaskDefines = runTimeViewController.getAllTaskDefines();
            if (!CollectionUtils.isEmpty(allTaskDefines)) {
                for (TaskDefine taskDefine : allTaskDefines) {
                    executorService.execute(() -> {
                        try {
                            List formSchemeDefines = runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                            if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                                    try {
                                        tableDeployService.deployTables(taskDefine, formSchemeDefine);
                                        logger.info(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684DE_DAST\u8868DAST_ISENTRY\u5b57\u6bb5\u5347\u7ea7\u6210\u529f");
                                    }
                                    catch (Exception e) {
                                        logger.error(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684DE_DAST\u8868DAST_ISENTRY\u5b57\u6bb5\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
                                    }
                                }
                            }
                            logger.info(taskDefine.getKey() + "\u4efb\u52a1\u4e0b\u7684DE_DAST\u8868DAST_ISENTRY\u5b57\u6bb5\u5347\u7ea7\u6210\u529f");
                        }
                        catch (Exception e) {
                            logger.error(taskDefine.getKey() + "\u4efb\u52a1\u4e0b\u7684DE_DAST\u8868DAST_ISENTRY\u5b57\u6bb5\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
                        }
                    });
                }
            }
            executorService.shutdown();
            while (true) {
                if (executorService.isTerminated()) {
                    logger.info("\u7cfb\u7edf\u6240\u6709\u4efb\u52a1\u62a5\u8868\u65b9\u6848\u4e0b\u7684DE_DAST\u8868DAST_ISENTRY\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210");
                    break;
                }
                Thread.sleep(1000L);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

