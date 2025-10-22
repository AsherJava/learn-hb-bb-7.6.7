/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.financialcheckcore.sqlutils;

import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

public class FcNativeSqlTemplate
extends EntNativeSqlTemplate {
    public JdbcTemplate getJdbcTemplate() {
        return OuterDataSourceUtils.getJdbcTemplate();
    }
}

