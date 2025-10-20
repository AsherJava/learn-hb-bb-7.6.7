/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.attachment.storage;

import com.jiuqi.va.attachment.config.VaAttachmentCoreConfig;
import com.jiuqi.va.attachment.entity.AttachmentTableCacheDTO;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentBizStorage {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentBizStorage.class);
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> tableCache = new ConcurrentHashMap();

    public static void init(String tenantName, String suffix) {
        Boolean flag;
        if (tableCache.containsKey(tenantName) && (flag = tableCache.get(tenantName).get(suffix)) != null && flag.booleanValue()) {
            return;
        }
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = AttachmentBizStorage.getCreateJTM(tenantName, suffix);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            logger.error("sync table error\uff1a", e);
            return;
        }
        if (!tableCache.containsKey(tenantName)) {
            tableCache.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        tableCache.get(tenantName).put(suffix, true);
        if (EnvConfig.getRedisEnable()) {
            AttachmentTableCacheDTO attachmentTableCacheDTO = new AttachmentTableCacheDTO();
            attachmentTableCacheDTO.setSuffix(suffix);
            attachmentTableCacheDTO.setCurrNodeId(VaAttachmentCoreConfig.getCurrNodeId());
            EnvConfig.sendRedisMsg((String)VaAttachmentCoreConfig.getVaAttachmentCoreSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)attachmentTableCacheDTO)));
        }
    }

    public static boolean hasTable(String tenantName, String suffix) {
        Boolean b;
        ConcurrentHashMap cache = tableCache.computeIfAbsent(tenantName, k -> new ConcurrentHashMap());
        if (cache.containsKey(suffix) && (b = (Boolean)cache.get(suffix)).booleanValue()) {
            return true;
        }
        boolean flag = false;
        try {
            JDialectUtil jDialect = JDialectUtil.getInstance();
            JTableModel jtm = AttachmentBizStorage.getCreateJTM(tenantName, suffix);
            flag = jDialect.hasTable(jtm);
        }
        catch (Exception e) {
            logger.error("query table error\uff1a", e);
        }
        cache.put(suffix, flag);
        return flag;
    }

    public static JTableModel getCreateJTM(String tenantName, String suffix) {
        JTableModel jtm = new JTableModel(tenantName, "BIZATTACHMENT_" + suffix);
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("QUOTECODE").VARCHAR(Integer.valueOf(43));
        jtm.column("FILEPATH").NVARCHAR(Integer.valueOf(200));
        jtm.column("FILESIZE").NUMERIC(new Integer[]{10, 2});
        jtm.column("STATUS").INTEGER(new Integer[]{1});
        jtm.column("CREATETIME").TIMESTAMP();
        jtm.column("CREATEUSER").VARCHAR(Integer.valueOf(36));
        jtm.column("SCHEMENAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("MODENAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("ORDINAL").INTEGER(new Integer[]{5}).defaultValue("0");
        jtm.column("SOURCEID").VARCHAR(Integer.valueOf(36));
        jtm.index("BIZATT_QTCODE" + suffix).columns(new String[]{"QUOTECODE"});
        return jtm;
    }

    public static void syncTableCache(String suffix) {
        String tenantName = ShiroUtil.getTenantName();
        ConcurrentHashMap cache = tableCache.computeIfAbsent(tenantName, k -> new ConcurrentHashMap());
        cache.put(suffix, true);
    }
}

