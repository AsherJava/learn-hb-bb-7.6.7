/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemUnCheckDescDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemUnCheckDescEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class GcRelatedItemUnCheckDescDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedItemUnCheckDescEO>
implements GcRelatedItemUnCheckDescDao {
    public GcRelatedItemUnCheckDescDaoImpl() {
        super(GcRelatedItemUnCheckDescEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATED_ITEM_UNCHECK_DESC");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemUnCheckDescEO> queryExistUnCheckDesc(Collection<String> ids) {
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(ids, "v.itemId");
        try {
            String sql = " select * from  %1$s v where %2$s";
            List list = this.selectEntity(String.format(sql, "GC_RELATED_ITEM_UNCHECK_DESC", tempTableCondition.getCondition()), new Object[0]);
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public void updateExistUnCheckDesc(List<GcRelatedItemUnCheckDescEO> existUnCheckDesc) {
        String sqlTemplate = " update %1$s set UNCHECKDESC = ?, UNCHECKTYPE = ?, updateDate = ?, updateUser = ? where  itemId = ?";
        sqlTemplate = String.format(sqlTemplate, "GC_RELATED_ITEM_UNCHECK_DESC");
        List params = existUnCheckDesc.stream().map(x -> {
            ArrayList param = new ArrayList();
            param.addAll(CollectionUtils.newArrayList((Object[])new Serializable[]{x.getUnCheckDesc(), x.getUnCheckType(), x.getUpdateDate(), x.getUpdateUser(), x.getItemId()}));
            return param;
        }).collect(Collectors.toList());
        this.executeBatch(sqlTemplate, params);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public void deleteUnCheckDesc(Collection<String> ids) {
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(ids, "v.itemId");
        try {
            String sql = " delete  from  %1$s v where %2$s";
            this.execute(String.format(sql, "GC_RELATED_ITEM_UNCHECK_DESC", tempTableCondition.getCondition()));
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }
}

