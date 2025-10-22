/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.NvwaDataModelUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class AlterCKRTableExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AlterCKRTableExecutor.class);
    private static final ParamUtil paramUtil = (ParamUtil)BeanUtil.getBean(ParamUtil.class);
    private static final NvwaDataModelUtil nvwaDataModelUtil = (NvwaDataModelUtil)BeanUtil.getBean(NvwaDataModelUtil.class);
    private static final JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);

    public void execute(DataSource dataSource) throws Exception {
        for (String formSchemeCode : paramUtil.getAllFormScheme()) {
            String ckrTableName = CheckTableNameUtil.getCKRTableName(formSchemeCode);
            try {
                String tableID;
                DesignColumnModelDefine column;
                DesignTableModelDefine tableModelDefine = nvwaDataModelUtil.getDesTableByCode(ckrTableName);
                if (tableModelDefine == null || (column = nvwaDataModelUtil.getDesColumnByCode("CKR_UNITORDER", tableID = tableModelDefine.getID())) == null) continue;
                this.cleanOldData(ckrTableName);
                column.setColumnType(ColumnModelType.BIGDECIMAL);
                column.setDecimal(6);
                column.setPrecision(20);
                nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
                nvwaDataModelUtil.updateColumns(new DesignColumnModelDefine[]{column});
                nvwaDataModelUtil.deployTable(tableID);
                logger.info("{}\u5347\u7ea7CKR_UNITORDER\u5b57\u6bb5\u6210\u529f", (Object)ckrTableName);
            }
            catch (Exception e) {
                logger.error(ckrTableName + "\u5347\u7ea7CKR_UNITORDER\u5b57\u6bb5\u5931\u8d25:" + e.getMessage(), e);
            }
        }
    }

    private void cleanOldData(String ckrTableName) {
        jdbcTemplate.execute(String.format("UPDATE %s SET %s = NUll", ckrTableName, "CKR_UNITORDER"));
    }
}

