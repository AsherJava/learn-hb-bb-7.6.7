/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.simpleschedule;

import com.jiuqi.bi.core.jobs.simpleschedule.bean.SimpleJobBean;
import com.jiuqi.bi.core.jobs.simpleschedule.bean.SimpleJobExecuteType;
import com.jiuqi.bi.core.jobs.simpleschedule.dao.SimpleJobDao;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;

public class SimpleJobManager {
    public void addSimpleJob(SimpleJobBean bean) throws Exception {
        if (bean == null) {
            return;
        }
        bean.setLastModifyTime(System.currentTimeMillis());
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            SimpleJobDao.addBean(conn, bean);
        }
    }

    public SimpleJobBean getSimpleJob(String jobId) throws Exception {
        if (StringUtils.isEmpty((String)jobId)) {
            return null;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            SimpleJobBean simpleJobBean = SimpleJobDao.queryBean(conn, jobId);
            return simpleJobBean;
        }
    }

    public void setFinished(String jobId) throws Exception {
        if (StringUtils.isEmpty((String)jobId)) {
            return;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            SimpleJobDao.updateFinished(conn, jobId, SimpleJobExecuteType.API);
        }
    }

    public void deleteSimpleJob(String jobId) throws Exception {
        if (StringUtils.isEmpty((String)jobId)) {
            return;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            SimpleJobDao.deleteBean(conn, jobId);
        }
    }
}

