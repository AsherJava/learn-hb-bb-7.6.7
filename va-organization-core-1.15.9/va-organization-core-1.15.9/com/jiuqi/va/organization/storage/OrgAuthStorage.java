/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.organization.storage;

import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrgAuthStorage {
    private static Logger logger = LoggerFactory.getLogger(OrgAuthStorage.class);
    public static final String TABLE_NAME = "AUTH_ORG_RIGHT";

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = OrgAuthStorage.getCreateJTM(tenantName);
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
        JTableModel jtm = new JTableModel(tenantName, TABLE_NAME);
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("BIZTYPE").INTEGER(new Integer[]{1}).notNull();
        jtm.column("BIZNAME").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("AUTHTYPE").INTEGER(new Integer[]{1}).notNull();
        jtm.column("ORGCATEGORY").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("ORGNAME").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("ATMANAGE").INTEGER(new Integer[]{1}).notNull().defaultValue("0");
        jtm.column("ATACCESS").INTEGER(new Integer[]{1}).notNull().defaultValue("0");
        jtm.column("ATWRITE").INTEGER(new Integer[]{1}).notNull().defaultValue("0");
        jtm.column("ATEDIT").INTEGER(new Integer[]{1}).notNull().defaultValue("0");
        jtm.column("ATREPORT").INTEGER(new Integer[]{1}).notNull().defaultValue("0");
        jtm.column("ATSUBMIT").INTEGER(new Integer[]{1}).notNull().defaultValue("0");
        jtm.column("ATAPPROVAL").INTEGER(new Integer[]{1}).notNull().defaultValue("0");
        jtm.index("AHORG_BBUA").columns(new String[]{"BIZTYPE", "BIZNAME", "ORGCATEGORY", "ORGNAME", "AUTHTYPE"}).unique();
        return jtm;
    }
}

