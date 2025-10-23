/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.exception.BeanParaException
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.apache.shiro.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.fix.dao;

import com.jiuqi.nr.datascheme.exception.BeanParaException;
import com.jiuqi.nr.datascheme.fix.core.DeployFailFixMethods;
import com.jiuqi.nr.datascheme.fix.entity.DeployFailFixLogDO;
import com.jiuqi.nr.datascheme.fix.utils.DeployFixTransUtils;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class DeployFailFixLogDao
extends BaseDao {
    @Autowired
    private DeployFailFixMethods fixMethods;

    public Class<DeployFailFixLogDO> getClz() {
        return DeployFailFixLogDO.class;
    }

    public Class<?> getExternalTransCls() {
        return DeployFixTransUtils.class;
    }

    public List<DeployFailFixLogDO> getByTable(String tableKey) throws BeanParaException {
        return super.list(new String[]{"DF_DT_KEY"}, (Object[])new String[]{tableKey}, this.getClz());
    }

    public DeployFailFixLogDO getNewestByTable(String tableKey) throws BeanParaException {
        List fixLogsDesc = this.getByTable(tableKey).stream().sorted(Comparator.comparing(DeployFailFixLogDO::getDeployFailFixTime).reversed()).collect(Collectors.toList());
        return (DeployFailFixLogDO)fixLogsDesc.get(0);
    }

    public List<DeployFailFixLogDO> getByDataScheme(String dataSchemeKey) throws BeanParaException {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null");
        return super.list(new String[]{"DF_DS_KEY"}, new Object[]{dataSchemeKey}, this.getClz());
    }

    public List<DeployFailFixLogDO> getByDataSchemeAndTime(String dataSchemeKey, Instant time) throws BeanParaException {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null");
        return super.list(new String[]{"DF_FIX_TIME", "DF_DS_KEY"}, new Object[]{time, dataSchemeKey}, this.getClz());
    }

    public int insert(DeployFailFixLogDO deployFailFixLog) throws DataAccessException {
        return super.insert((Object)deployFailFixLog);
    }

    public int update(DeployFailFixLogDO deployFailFixLog) throws DataAccessException {
        Timestamp time = Timestamp.from(deployFailFixLog.getDeployFailFixTime());
        return super.update((Object)deployFailFixLog, new String[]{"DF_FIX_TIME", "DF_DT_KEY"}, new Object[]{time, deployFailFixLog.getDataTableKey()});
    }

    public int delete(DeployFailFixLogDO deployFailFixLogDO) throws DataAccessException {
        Timestamp time = Timestamp.from(deployFailFixLogDO.getDeployFailFixTime());
        return super.deleteBy(new String[]{"DF_FIX_TIME", "DF_DT_KEY"}, new Object[]{time, deployFailFixLogDO.getDataTableKey()});
    }

    public DeployFailFixLogDO getByTimeAndTableKey(Instant time, String dataTableKey) {
        return super.list(new String[]{"DF_FIX_TIME", "DF_DT_KEY"}, new Object[]{time, dataTableKey}, this.getClz()).stream().findFirst().orElse(null);
    }

    public List<DeployFailFixLogDO> getAllFixLogs() {
        return super.list(this.getClz());
    }
}

