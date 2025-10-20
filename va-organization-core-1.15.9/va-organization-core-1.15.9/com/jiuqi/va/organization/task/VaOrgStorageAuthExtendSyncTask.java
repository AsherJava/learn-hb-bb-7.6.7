/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.extend.OrgDataAuthTypeExtend
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.domain.TableColumnDO
 */
package com.jiuqi.va.organization.task;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.extend.OrgDataAuthTypeExtend;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.domain.TableColumnDO;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VaOrgStorageAuthExtendSyncTask
implements StorageSyncTask {
    private static Logger logger = LoggerFactory.getLogger(VaOrgStorageAuthExtendSyncTask.class);

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        Map authTypeExtendClassMap = ApplicationContextRegister.getBeansOfType(OrgDataAuthTypeExtend.class);
        if (authTypeExtendClassMap == null || authTypeExtendClassMap.isEmpty()) {
            return;
        }
        HashSet<String> hasAuth = new HashSet<String>();
        for (OrgDataOption.AuthType authType : OrgDataOption.AuthType.values()) {
            hasAuth.add(authType.toString());
        }
        HashSet<String> extendAuth = new HashSet<String>();
        Map authTypeMap = null;
        for (OrgDataAuthTypeExtend authTypeClass : authTypeExtendClassMap.values()) {
            authTypeMap = authTypeClass.getAuthType();
            for (String authTypeName : authTypeMap.keySet()) {
                if (hasAuth.contains(authTypeName = authTypeName.toUpperCase())) continue;
                hasAuth.add(OrgDataOption.AuthType.valueOf((String)authTypeName).toString());
                extendAuth.add(authTypeName);
            }
        }
        this.initExtendAuthColumn(tenantName, extendAuth);
    }

    private void initExtendAuthColumn(String tenantName, Set<String> extendAuth) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        HashSet<String> oldColSet = new HashSet<String>();
        List colList = jDialect.getTableColumns(tenantName, "AUTH_ORG_RIGHT");
        for (TableColumnDO tableColumnDO : colList) {
            oldColSet.add(tableColumnDO.getColumnName().toUpperCase());
        }
        JTableModel jtm = new JTableModel(tenantName, "AUTH_ORG_RIGHT");
        boolean flag = false;
        String colName = null;
        for (String authTypeExtendName : extendAuth) {
            colName = "AT" + authTypeExtendName.toUpperCase();
            if (oldColSet.contains(colName)) continue;
            jtm.column(colName).INTEGER(new Integer[]{1}).notNull().defaultValue("0");
            flag = true;
        }
        if (flag) {
            try {
                jDialect.updateTable(jtm);
            }
            catch (JTableException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public int getSortNum() {
        return -2147482647;
    }

    public boolean needCompareVersion() {
        return false;
    }
}

