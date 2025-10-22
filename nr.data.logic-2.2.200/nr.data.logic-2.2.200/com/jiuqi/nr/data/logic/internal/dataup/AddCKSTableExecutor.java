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
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.dataup.ModifyAsyncIDLengthExecutor;
import com.jiuqi.nr.data.logic.internal.deploy.TableDeployService;
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

public class AddCKSTableExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ModifyAsyncIDLengthExecutor.class);
    private static final TableDeployService tableDeployService = (TableDeployService)BeanUtil.getBean(TableDeployService.class);
    private static final IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);

    public void execute(DataSource dataSource) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            List allTaskDefines = runTimeViewController.getAllTaskDefines();
            if (!CollectionUtils.isEmpty(allTaskDefines)) {
                for (TaskDefine taskDefine : allTaskDefines) {
                    AddCKSTableExecutor.executeByTask(taskDefine, executorService);
                }
            }
            executorService.shutdown();
            AddCKSTableExecutor.waitUntilFinish(executorService);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void waitUntilFinish(ExecutorService executorService) {
        while (!executorService.isTerminated()) {
        }
        logger.info("\u7cfb\u7edf\u6240\u6709\u4efb\u52a1\u62a5\u8868\u65b9\u6848\u4e0b\u65b0\u589eCKS\u3001CKS_S\u8868\u5347\u7ea7\u5b8c\u6210");
    }

    private static void executeByTask(TaskDefine taskDefine, ExecutorService executorService) {
        executorService.execute(() -> {
            try {
                List formSchemeDefines = runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                    for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                        AddCKSTableExecutor.executeByFormScheme(taskDefine, formSchemeDefine);
                    }
                }
            }
            catch (Exception e) {
                logger.error(taskDefine.getKey() + "\u4efb\u52a1\u4e0b\u65b0\u589eCKS\u3001CKS_S\u8868\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
            }
        });
    }

    private static void executeByFormScheme(TaskDefine taskDefine, FormSchemeDefine formSchemeDefine) {
        try {
            tableDeployService.deployCKSTableUncheck(taskDefine, formSchemeDefine);
            tableDeployService.deployCKSSubTableUncheck(taskDefine, formSchemeDefine);
            logger.info(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u65b0\u589eCKS\u3001CKS_S\u8868\u5347\u7ea7\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u65b0\u589eCKS\u3001CKS_S\u8868\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
        }
    }
}

