/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.examine.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.examine.bean.ParamExamineDetailInfo;
import com.jiuqi.nr.examine.bean.ParamExamineInfo;
import com.jiuqi.nr.examine.bean.TransUtil;
import com.jiuqi.nr.examine.common.ExamineStatus;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ParamExamineInfoDao
extends BaseDao {
    private Class<ParamExamineInfo> implClass = ParamExamineInfo.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public ParamExamineInfo getInfo(String id) throws Exception {
        return (ParamExamineInfo)this.getByKey(id, this.implClass);
    }

    public void finish(String id) {
        this.update("UPDATE NR_PARAMEXAMINE_INFO SET PEI_STATUS = ?", new Object[]{ExamineStatus.FINISH.getValue()}, new int[]{4});
    }

    public ParamExamineInfo getLast() {
        List list = this.list(this.implClass);
        if (list.size() > 0) {
            return (ParamExamineInfo)list.get(list.size() - 1);
        }
        return null;
    }

    public List<ParamExamineDetailInfo> listByExamineInfoKey(String infoKey) {
        return null;
    }
}

