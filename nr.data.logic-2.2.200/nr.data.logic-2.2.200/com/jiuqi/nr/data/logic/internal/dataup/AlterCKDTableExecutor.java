/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.NvwaDataModelUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlterCKDTableExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AlterCKDTableExecutor.class);
    private static final ParamUtil paramUtil = (ParamUtil)BeanUtil.getBean(ParamUtil.class);
    private static final NvwaDataModelUtil nvwaDataModelUtil = (NvwaDataModelUtil)BeanUtil.getBean(NvwaDataModelUtil.class);

    public void execute(DataSource dataSource) throws Exception {
        for (String formSchemeCode : paramUtil.getAllFormScheme()) {
            String ckdTableName = CheckTableNameUtil.getCKDTableName(formSchemeCode);
            try {
                String tableID;
                DesignColumnModelDefine column;
                DesignTableModelDefine tableModelDefine = nvwaDataModelUtil.getDesTableByCode(ckdTableName);
                if (tableModelDefine == null || (column = nvwaDataModelUtil.getDesColumnByCode("CKD_USERNAME", tableID = tableModelDefine.getID())) == null) continue;
                column.setPrecision(200);
                nvwaDataModelUtil.updateTableModelDefine(tableModelDefine);
                nvwaDataModelUtil.updateColumns(new DesignColumnModelDefine[]{column});
                nvwaDataModelUtil.deployTable(tableID);
                logger.info("{}\u5347\u7ea7CKD_USERNAME\u5b57\u6bb5\u6210\u529f", (Object)ckdTableName);
            }
            catch (Exception e) {
                logger.error(ckdTableName + "\u5347\u7ea7CKD_USERNAME\u5b57\u6bb5\u5931\u8d25:" + e.getMessage(), e);
            }
        }
    }
}

