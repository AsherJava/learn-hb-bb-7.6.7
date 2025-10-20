/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.organization.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrgImportTemplateStorage {
    private static Logger logger = LoggerFactory.getLogger(OrgImportTemplateStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialectUtil = JDialectUtil.getInstance();
        try {
            JTableModel jTableModel = OrgImportTemplateStorage.getCreateJTM(tenantName);
            if (!jDialectUtil.hasTable(jTableModel)) {
                jDialectUtil.createTable(jTableModel);
            } else {
                jDialectUtil.updateTable(jTableModel);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "ORG_IMPORT_TEMPLATE");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("CODE").VARCHAR(Integer.valueOf(100));
        jtm.column("NAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.column("TEMPLATEDATA").CLOB();
        return jtm;
    }
}

