/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 */
package com.jiuqi.va.query.common;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.query.cache.QueryCacheManage;
import com.jiuqi.va.query.common.service.UpgradeService;
import com.jiuqi.va.query.task.QueryStorageSyncTask;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class UpgradeDataTool
implements CustomClassExecutor {
    public static final Logger logger = LoggerFactory.getLogger(UpgradeDataTool.class);

    public void execute(DataSource dataSource) {
        QueryTemplateInfoDao templateInfoDao = DCQuerySpringContextUtils.getBean(QueryTemplateInfoDao.class);
        List<TemplateInfoVO> allTemplates = templateInfoDao.getAllTemplates();
        List<String> collect = allTemplates.stream().map(TemplateInfoVO::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return;
        }
        LogUtil.add((String)"\u81ea\u5b9a\u4e49\u67e5\u8be2", (String)"\u5f00\u59cb\u5347\u7ea7");
        this.templateMigration(collect);
        LogUtil.add((String)"\u81ea\u5b9a\u4e49\u67e5\u8be2", (String)"\u5347\u7ea7\u7ed3\u675f");
    }

    public void templateMigration(List<String> tartemplateIds) {
        UpgradeService upgradeService = DCQuerySpringContextUtils.getBean(UpgradeService.class);
        QueryStorageSyncTask task = DCQuerySpringContextUtils.getBean(QueryStorageSyncTask.class);
        QueryCacheManage cacheManage = DCQuerySpringContextUtils.getBean(QueryCacheManage.class);
        task.execute();
        if (CollectionUtils.isEmpty(tartemplateIds)) {
            return;
        }
        for (String templateId : tartemplateIds) {
            upgradeService.templateMigration(templateId);
        }
        cacheManage.clearAllCache();
    }
}

