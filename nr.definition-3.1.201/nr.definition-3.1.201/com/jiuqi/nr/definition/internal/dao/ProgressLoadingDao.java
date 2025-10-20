/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProgressLoadingDao
extends BaseDao {
    private Class<ProgressLoadingImpl> implClass = ProgressLoadingImpl.class;
    private static String P_TASKID = "taskId";
    private static String P_PROGRESSID = "progressId";

    public Class<?> getClz() {
        return this.implClass;
    }

    public void insertProgressLoading(ProgressLoadingImpl progressLoadingImpl) throws Exception {
        if (progressLoadingImpl.getInfo().length() > 2000) {
            progressLoadingImpl.setInfo(progressLoadingImpl.getInfo().substring(0, 2000));
        }
        this.insert(progressLoadingImpl);
    }

    public List<ProgressLoadingImpl> list() throws Exception {
        return this.list(this.implClass);
    }

    public ProgressLoadingImpl queryProgressLoadingBytaskId(String taskId) throws Exception {
        List defines = this.list(new String[]{P_TASKID}, new Object[]{taskId}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (ProgressLoadingImpl)defines.get(defines.size() - 1);
        }
        return null;
    }

    public void updateData(ProgressLoadingImpl data) throws Exception {
        this.update(data, new String[]{P_PROGRESSID, P_TASKID}, new Object[]{data.getProgressId(), data.getTaskId()});
    }

    public void deleteByTaskId(String taskId) throws Exception {
        this.deleteBy(new String[]{P_TASKID}, new Object[]{taskId});
    }
}

