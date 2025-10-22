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
import com.jiuqi.nr.data.logic.internal.dataup.AlterCKRTableExecutor;
import com.jiuqi.nr.data.logic.internal.deploy.TableDeployService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.NvwaDataModelUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

public class CKRBatchIdExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AlterCKRTableExecutor.class);
    private static final NvwaDataModelUtil nvwaDataModelUtil = (NvwaDataModelUtil)BeanUtil.getBean(NvwaDataModelUtil.class);
    private static final JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
    private static final TableDeployService tableDeployService = (TableDeployService)BeanUtil.getBean(TableDeployService.class);
    private static final IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);

    public void execute(DataSource dataSource) throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE NR_PARAM_REVIEW_INFO");
        List allTaskDefines = runTimeViewController.getAllTaskDefines();
        if (!CollectionUtils.isEmpty(allTaskDefines)) {
            for (TaskDefine taskDefine : allTaskDefines) {
                try {
                    List formSchemeDefines = runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                    if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                            try {
                                String ckrTableName = CheckTableNameUtil.getCKRTableName(formSchemeDefine.getFormSchemeCode());
                                nvwaDataModelUtil.deployDeleteTableByCode(ckrTableName);
                                tableDeployService.deployCKRTable(taskDefine, formSchemeDefine);
                                logger.info(formSchemeDefine.getKey() + "\u65b9\u6848\u5347\u7ea7\u6210\u529f,\u521b\u5efa\u65b0\u7684CKR\u8868" + ckrTableName);
                            }
                            catch (Exception e) {
                                logger.error(formSchemeDefine.getKey() + "\u65b9\u6848\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
                            }
                        }
                    }
                    logger.info(taskDefine.getKey() + "\u4efb\u52a1\u5347\u7ea7\u6210\u529f");
                }
                catch (Exception e) {
                    logger.error(taskDefine.getKey() + "\u4efb\u52a1\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
                }
            }
        }
    }
}

