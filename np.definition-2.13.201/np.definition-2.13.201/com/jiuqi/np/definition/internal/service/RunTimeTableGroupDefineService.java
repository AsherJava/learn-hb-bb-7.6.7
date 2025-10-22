/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.service;

import com.jiuqi.np.definition.facade.TableGroupDefine;
import com.jiuqi.np.definition.internal.dao.RunTimeGroupDefineDao;
import com.jiuqi.np.definition.internal.impl.RunTimeTableGroupDefineImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeTableGroupDefineService {
    @Autowired
    RunTimeGroupDefineDao dao;

    public void setDao(RunTimeGroupDefineDao dao) {
        this.dao = dao;
    }

    public RunTimeTableGroupDefineImpl queryTableGroup(String tableGroupKey) throws Exception {
        return this.dao.getGroup(tableGroupKey);
    }

    public List<TableGroupDefine> getChildTableGroups(String tableGroupKey) throws Exception {
        return this.dao.queryChildGroups(tableGroupKey);
    }
}

