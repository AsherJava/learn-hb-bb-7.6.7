/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.examine.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.examine.bean.ParamExamineDetailInfo;
import com.jiuqi.nr.examine.bean.TransUtil;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ParamExamineDetailInfoDao
extends BaseDao {
    private Class<ParamExamineDetailInfo> implClass = ParamExamineDetailInfo.class;

    public Class<ParamExamineDetailInfo> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<ParamExamineDetailInfo> listByExamineInfoKey(String infoKey) {
        return this.list("PED_PEI_KEY = ?", new Object[]{infoKey}, this.getClz());
    }

    public List<ParamExamineDetailInfo> listAllRefuseByExamineInfoKey(String infoKey) {
        return this.list("PED_PEI_KEY = ? and PED_EXAMINE_TYPE = 1", new Object[]{infoKey}, this.getClz());
    }
}

