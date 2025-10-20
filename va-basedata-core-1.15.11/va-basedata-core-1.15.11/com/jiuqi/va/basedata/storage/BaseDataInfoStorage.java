/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.basedata.storage;

import com.jiuqi.va.basedata.dao.VaBaseDataGroupDao;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import java.math.BigDecimal;
import java.util.UUID;

public class BaseDataInfoStorage {
    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String groupTableName = "BASEDATA_GROUP";
        String infoTableName = "BASEDATA_DEFINE";
        String versionTableName = "BASEDATA_VERSION";
        JTableModel jtm = new JTableModel(tenantName, groupTableName);
        try {
            BaseDataInfoStorage.setCreateBaseDataGroup(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
            BaseDataInfoStorage.initGroupData(tenantName);
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
        jtm = new JTableModel(tenantName, infoTableName);
        try {
            BaseDataInfoStorage.setCreateBaseDataDefine(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
        jtm = new JTableModel(tenantName, versionTableName);
        try {
            BaseDataInfoStorage.setCreateBaseDataVersion(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }

    private static void initGroupData(String tenantName) {
        VaBaseDataGroupDao baseDataDao = (VaBaseDataGroupDao)ApplicationContextRegister.getBean(VaBaseDataGroupDao.class);
        BaseDataGroupDTO basedata = new BaseDataGroupDTO();
        basedata.setTenantName(tenantName);
        basedata.setName("system");
        if (baseDataDao.selectCount(basedata) == 0) {
            basedata.setParentname("-");
            basedata.setTitle("\u7cfb\u7edf");
            basedata.setRemark("\u521d\u59cb\u5206\u7ec4");
            basedata.setId(UUID.randomUUID());
            basedata.setOrdernum(BigDecimal.valueOf(1.0));
            baseDataDao.insert(basedata);
        }
        basedata = new BaseDataGroupDTO();
        basedata.setTenantName(tenantName);
        basedata.setName("public");
        if (baseDataDao.selectCount(basedata) == 0) {
            basedata.setParentname("-");
            basedata.setTitle("\u516c\u7528");
            basedata.setRemark("\u521d\u59cb\u5206\u7ec4");
            basedata.setId(UUID.randomUUID());
            basedata.setOrdernum(BigDecimal.valueOf(1.000001));
            baseDataDao.insert(basedata);
        }
        basedata = new BaseDataGroupDTO();
        basedata.setTenantName(tenantName);
        basedata.setName("other");
        if (baseDataDao.selectCount(basedata) == 0) {
            basedata.setParentname("-");
            basedata.setTitle("\u5176\u4ed6");
            basedata.setRemark("\u521d\u59cb\u5206\u7ec4");
            basedata.setId(UUID.randomUUID());
            basedata.setOrdernum(BigDecimal.valueOf(1.000002));
            baseDataDao.insert(basedata);
        }
    }

    public static void setCreateBaseDataGroup(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").VARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(200));
        jtm.column("PARENTNAME").VARCHAR(Integer.valueOf(100));
        jtm.column("CREATOR").VARCHAR(Integer.valueOf(100));
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.index("BASEDATA_GROUP_NAME").columns(new String[]{"NAME"}).unique();
        jtm.index("BASEDATA_GROUP_TITLE").columns(new String[]{"TITLE"});
        jtm.index("BASEDATA_GROUP_PARENTNAME").columns(new String[]{"PARENTNAME"});
    }

    public static void setCreateBaseDataDetail(JTableModel jtm, String tableName) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("MASTERID").VARCHAR(Integer.valueOf(36));
        jtm.column("FIELDNAME").VARCHAR(Integer.valueOf(60));
        jtm.column("FIELDVALUE").VARCHAR(Integer.valueOf(200));
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.index(tableName + "_MSTFID").columns(new String[]{"MASTERID", "FIELDNAME"});
    }

    public static void setCreateBaseDataDefine(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").VARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(200));
        jtm.column("GROUPNAME").VARCHAR(Integer.valueOf(100));
        jtm.column("CREATOR").VARCHAR(Integer.valueOf(100));
        jtm.column("MODIFYTIME").TIMESTAMP();
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.column("DUMMYFLAG").INTEGER(new Integer[]{1});
        jtm.column("DESIGNNAME").VARCHAR(Integer.valueOf(100));
        jtm.column("STRUCTTYPE").INTEGER(new Integer[]{1});
        jtm.column("GROUPFIELDNAME").VARCHAR(Integer.valueOf(100));
        jtm.column("SHARETYPE").INTEGER(new Integer[]{1});
        jtm.column("SHAREFIELDNAME").VARCHAR(Integer.valueOf(100));
        jtm.column("LEVELCODE").VARCHAR(Integer.valueOf(20));
        jtm.column("SHOWTYPE").VARCHAR(Integer.valueOf(50));
        jtm.column("VERSIONFLAG").INTEGER(new Integer[]{1});
        jtm.column("AUTHFLAG").INTEGER(new Integer[]{1});
        jtm.column("ACTAUTHFLAG").INTEGER(new Integer[]{1});
        jtm.column("READONLY").INTEGER(new Integer[]{1});
        jtm.column("SOLIDIFYFLAG").INTEGER(new Integer[]{1});
        jtm.column("CACHEDISABLED").INTEGER(new Integer[]{1});
        jtm.column("DEFINE").CLOB();
        jtm.column("EXTDATA").CLOB();
        jtm.index("BASEDATA_INFO_NAME").columns(new String[]{"NAME"}).unique();
        jtm.index("BASEDATA_INFO_GROUPNAME").columns(new String[]{"GROUPNAME"});
    }

    public static void setCreateBaseDataVersion(JTableModel jtm) {
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("TABLENAME").VARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("NAME").NVARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("CREATOR").VARCHAR(Integer.valueOf(100));
        jtm.column("MODIFYTIME").TIMESTAMP();
        jtm.column("VALIDTIME").DATE().notNull();
        jtm.column("INVALIDTIME").DATE().notNull();
        jtm.column("REMARK").NVARCHAR(Integer.valueOf(500));
        jtm.column("ACTIVEFLAG").INTEGER(new Integer[]{1}).defaultValue("1");
        jtm.index("BASEDATA_VERSION_TENE").columns(new String[]{"TABLENAME", "NAME"}).unique();
    }

    public static void initDetailTable(String name, String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String tableName = name + "_SUBLIST";
        JTableModel jtm = new JTableModel(tenantName, tableName);
        try {
            BaseDataInfoStorage.setCreateBaseDataDetail(jtm, tableName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            }
        }
        catch (JTableException e) {
            e.printStackTrace();
        }
    }
}

