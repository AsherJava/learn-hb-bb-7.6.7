/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.dc.base.common.storage;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.storage.intf.SyncTableCtx;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DcBaseStorage {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getStorageName() {
        return this.getClass().getName();
    }

    public final void syncTable(SyncTableCtx ctx) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        List<JTableModel> jtms = this.getJTableModels(ctx);
        if (CollectionUtils.isEmpty(jtms)) {
            this.logger.info("\u3010\u603b\u8d26\u8868\u7ed3\u6784\u540c\u6b65\u3011\u6a21\u578b{}\u540c\u6b65\u5931\u8d25\uff0c \u672a\u540c\u6b65\u8868\u7ed3\u6784\u3002\n\u5931\u8d25\u539f\u56e0\uff1a\u6ca1\u6709\u6307\u5b9a\u9700\u8981\u540c\u6b65\u7684\u6a21\u578b", (Object)this.getStorageName());
            return;
        }
        SyncCheckResult result = this.syncCheck();
        if (result != null && !result.success) {
            this.logger.info("\u3010\u603b\u8d26\u8868\u7ed3\u6784\u540c\u6b65\u3011\u6a21\u578b{}\u540c\u6b65\u6821\u9a8c\u5931\u8d25\uff0c \u672a\u540c\u6b65\u8868\u7ed3\u6784\u3002\n\u5931\u8d25\u539f\u56e0\uff1a{}", (Object)this.getStorageName(), (Object)result.info);
            return;
        }
        for (JTableModel jTableModel : jtms) {
            if (jTableModel == null || StringUtils.isEmpty((String)jTableModel.getTableName())) {
                this.logger.info("\u3010\u603b\u8d26\u8868\u7ed3\u6784\u540c\u6b65\u3011\u6a21\u578b{}\u540c\u6b65\u5931\u8d25\uff0c \u672a\u540c\u6b65\u8868\u7ed3\u6784\u3002\n\u5931\u8d25\u539f\u56e0\uff1a\u8868\u540d\u4e3a\u7a7a", (Object)this.getStorageName());
                continue;
            }
            try {
                if (!jDialect.hasTable(jTableModel)) {
                    jDialect.createTable(jTableModel);
                } else {
                    jDialect.updateTable(jTableModel);
                }
                this.logger.info("\u3010\u603b\u8d26\u8868\u7ed3\u6784\u540c\u6b65\u3011\u8868{}\u540c\u6b65\u8868\u7ed3\u6784\u5b8c\u6210", (Object)jTableModel.getTableName());
            }
            catch (Exception e) {
                this.logger.error(String.format("\u3010\u603b\u8d26\u8868\u7ed3\u6784\u540c\u6b65\u3011\u8868%1$s\u540c\u6b65\u8868\u7ed3\u6784\u51fa\u73b0\u5f02\u5e38\uff1a%2$s", jTableModel.getTableName(), e.getMessage()), e);
            }
        }
    }

    public abstract List<JTableModel> getJTableModels(SyncTableCtx var1);

    protected JTableModel createBasicStorage(String tenantName, String tableName) {
        JTableModel jtm = new JTableModel(tenantName, tableName);
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("VER").NUMERIC(new Integer[]{19, 0}).setNullable(Boolean.valueOf(false));
        return jtm;
    }

    protected SyncCheckResult syncCheck() {
        return null;
    }

    protected class SyncCheckResult {
        private boolean success = true;
        private String info = null;

        public boolean isSuccess() {
            return this.success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getInfo() {
            return this.info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}

