/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.biz.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BizCommonRuleStorage {
    private static final Logger logger = LoggerFactory.getLogger(BizCommonRuleStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = BizCommonRuleStorage.getCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "BIZ_COMMRULE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").LONG();
        jtm.column("BIZTYPE").VARCHAR(Integer.valueOf(60)).notNull();
        jtm.column("DEFINECODE").VARCHAR(Integer.valueOf(60));
        jtm.column("DEFINETITLE").VARCHAR(Integer.valueOf(200));
        jtm.column("RULENAME").VARCHAR(Integer.valueOf(200)).notNull();
        jtm.column("OBJECTTYPE").VARCHAR(Integer.valueOf(60));
        jtm.column("PROPERTYTYPE").VARCHAR(Integer.valueOf(60));
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("CREATEUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(200));
        jtm.index("rulename_createuser").columns(new String[]{"RULENAME", "CREATEUSER"}).unique();
        return jtm;
    }
}

