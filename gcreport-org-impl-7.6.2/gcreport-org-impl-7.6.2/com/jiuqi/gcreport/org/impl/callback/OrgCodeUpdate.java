/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrgCodeUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(OrgCodeUpdate.class);

    public void execute(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        JTableModel jtm = new JTableModel("__default_tenant__", "MD_ORG_CATEGORY");
        if (!JDialectUtil.getInstance().hasTable(jtm)) {
            return;
        }
        List list = jdbcTemplate.queryForList("select c.name from MD_ORG_CATEGORY c");
        List orgTypes = list.stream().map(o -> (String)o.get("name")).collect(Collectors.toList());
        for (String orgType : orgTypes) {
            try {
                jdbcTemplate.update("update " + orgType + " set orgcode = code where orgcode is null");
            }
            catch (Exception exception) {}
        }
    }
}

