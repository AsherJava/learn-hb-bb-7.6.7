/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.formsetting.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.formsetting.dao.OffsetDimSettingDao;
import com.jiuqi.gcreport.inputdata.formsetting.entity.OffsetDimSettingEO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OffsetDimSettingDaoImpl
extends GcDbSqlGenericDAO<OffsetDimSettingEO, String>
implements OffsetDimSettingDao {
    public OffsetDimSettingDaoImpl() {
        super(OffsetDimSettingEO.class);
    }

    @Override
    public OffsetDimSettingEO getOffsetDimSettingByFormId(String formId) {
        String sql = " select od.id as id,od.formId as formId,od.offsetDims as offsetDims,od.operateTime as operateTime,od.dimOperator as dimOperator \n    from gc_offsetdimsetting  od \n   where od.formId = ? \n";
        List offsetDimSettings = this.selectEntity(" select od.id as id,od.formId as formId,od.offsetDims as offsetDims,od.operateTime as operateTime,od.dimOperator as dimOperator \n    from gc_offsetdimsetting  od \n   where od.formId = ? \n", new Object[]{formId});
        if (!CollectionUtils.isEmpty((Collection)offsetDimSettings)) {
            return (OffsetDimSettingEO)((Object)offsetDimSettings.get(0));
        }
        return null;
    }
}

