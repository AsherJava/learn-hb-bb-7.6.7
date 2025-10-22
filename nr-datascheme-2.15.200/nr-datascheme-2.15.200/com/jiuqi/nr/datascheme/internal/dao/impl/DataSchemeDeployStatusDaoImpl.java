/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeDeployStatusDaoImpl
extends BaseDao {
    public Class<DataSchemeDeployStatusDO> getClz() {
        return DataSchemeDeployStatusDO.class;
    }

    public List<DataSchemeDeployStatusDO> getAll() {
        return super.list(this.getClz());
    }

    public DataSchemeDeployStatusDO getByDataSchemeKey(String dataSchemeKey) {
        return super.getByKey(dataSchemeKey, this.getClz());
    }

    public List<DataSchemeDeployStatusDO> getByDataSchemeKeys(String[] dataSchemeKeys) {
        if (null == dataSchemeKeys || 0 == dataSchemeKeys.length) {
            return Collections.emptyList();
        }
        StringBuilder whereSql = new StringBuilder();
        whereSql.append("DS_DS_KEY").append(" in (?");
        for (int i = 1; i < dataSchemeKeys.length; ++i) {
            whereSql.append(",?");
        }
        whereSql.append(")");
        return super.list(whereSql.toString(), (Object[])dataSchemeKeys, this.getClz());
    }

    public List<DataSchemeDeployStatusDO> getByStatus(DeployStatusEnum status) {
        String whereSql = "DS_DEPLOY_STATUS=?";
        return super.list(whereSql, new Object[]{status.getValue()}, this.getClz());
    }

    public void insert(DataSchemeDeployStatusDO deployStatus) {
        super.insert(deployStatus);
    }

    public void update(DataSchemeDeployStatusDO[] deployStatus) {
        super.update(deployStatus);
    }

    public void update(DataSchemeDeployStatusDO deployStatus) {
        super.update(deployStatus);
    }

    public void delete(String dataSchemeKey) {
        super.delete(dataSchemeKey);
    }
}

