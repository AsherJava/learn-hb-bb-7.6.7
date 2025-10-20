/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 */
package com.jiuqi.va.query.common;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.query.common.service.UpgradeService;
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
public class UpgradeSqlTool
implements CustomClassExecutor {
    public static final Logger logger = LoggerFactory.getLogger(UpgradeSqlTool.class);

    public void execute(DataSource dataSource) {
        QueryTemplateInfoDao templateInfoDao = DCQuerySpringContextUtils.getBean(QueryTemplateInfoDao.class);
        List<TemplateInfoVO> allTemplates = templateInfoDao.getAllTemplates();
        List<String> collect = allTemplates.stream().map(TemplateInfoVO::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return;
        }
        this.parser(collect);
    }

    public void parser(List<String> tartemplateIds) {
        UpgradeService upgradeService = DCQuerySpringContextUtils.getBean(UpgradeService.class);
        if (CollectionUtils.isEmpty(tartemplateIds)) {
            return;
        }
        for (String templateId : tartemplateIds) {
            try {
                upgradeService.parserDefine(templateId);
            }
            catch (Exception exception) {}
        }
    }
}

