/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.nr.multcheck2.config;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class MCTableOrgExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(MCTableOrgExecutor.class);

    public void execute(DataSource dataSource) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.logger.info("\u6a21\u5757[\u7efc\u5408\u5ba1\u6838]\u65b9\u6848\u8868\u5355\u4f4d\u53e3\u5f84\u4fee\u590d\u5f00\u59cb\uff1a{}", (Object)sdf.format(new Date()));
        IMCSchemeService schemeService = (IMCSchemeService)SpringBeanUtils.getBean(IMCSchemeService.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        List<MultcheckScheme> allSchemes = schemeService.getAllSchemes();
        List taskDefines = runTimeViewController.getAllTaskDefines();
        Map<String, String> taskMap = taskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, TaskDefine::getDw));
        ArrayList<MultcheckScheme> list = new ArrayList<MultcheckScheme>();
        for (MultcheckScheme scheme : allSchemes) {
            String org;
            if (StringUtils.hasText((String)scheme.getOrg()) || !StringUtils.hasText((String)(org = taskMap.get(scheme.getTask())))) continue;
            scheme.setOrg(org);
            list.add(scheme);
        }
        if (!CollectionUtils.isEmpty(list)) {
            schemeService.batchModifyOrg(list);
            this.logger.info("\u6a21\u5757[\u7efc\u5408\u5ba1\u6838]\u65b9\u6848\u8868\u5171\u4fee\u590d {} \u4e2a\u65b9\u6848", (Object)list.size());
        }
        this.logger.info("\u6a21\u5757[\u7efc\u5408\u5ba1\u6838]\u65b9\u6848\u8868\u5355\u4f4d\u53e3\u5f84\u4fee\u590d\u7ed3\u675f\uff1a{}", (Object)sdf.format(new Date()));
    }
}

