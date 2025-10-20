/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.conversion.conversionsystem.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class ConversionSystemItemDaoImpl
extends GcDbSqlGenericDAO<ConversionSystemItemEO, String>
implements ConversionSystemItemDao {
    public ConversionSystemItemDaoImpl() {
        super(ConversionSystemItemEO.class);
    }

    @Override
    public ConversionSystemItemEO getSystemItemByFormIdAndIndexId(String formId, String indexId) {
        String sql = "begin \n  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemItemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_ITEM" + "   scheme  \n  where   1=1 \n  and  scheme.formId = ? \n  and  scheme.indexId = ? \n";
        List conversionSystemItemEOs = this.selectEntity(sql, new Object[]{formId, indexId});
        if (CollectionUtils.isEmpty(conversionSystemItemEOs)) {
            return null;
        }
        return (ConversionSystemItemEO)((Object)conversionSystemItemEOs.get(0));
    }

    @Override
    public List<ConversionSystemItemEO> batchGetSystemItemsByFormIdAndIndexIds(String formId, Set<String> indexIds) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemItemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_ITEM" + "   scheme  \n  where   1=1 \n  and  scheme.formId = ? \n  and " + SqlUtils.getConditionOfIdsUseOr(indexIds, (String)"scheme.indexId");
        List conversionSystemItemEOs = this.selectEntity(sql, new Object[]{formId});
        return conversionSystemItemEOs;
    }

    @Override
    public List<ConversionSystemItemEO> getSystemItemsByIndexIds(Set<String> indexIds) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemItemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_ITEM" + "   scheme  \n  where   1=1 \n  and " + SqlUtils.getConditionOfIdsUseOr(indexIds, (String)"scheme.indexId");
        List conversionSystemItemEOs = this.selectEntity(sql, new Object[0]);
        return conversionSystemItemEOs;
    }

    @Override
    public List<ConversionSystemItemEO> getSystemItemByFormId(String formKey) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemItemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_ITEM" + "   scheme  \n  where   1=1 \n  and  scheme.formId = ? \n";
        List conversionSystemItemEOs = this.selectEntity(sql, new Object[]{formKey});
        if (CollectionUtils.isEmpty(conversionSystemItemEOs)) {
            return null;
        }
        return conversionSystemItemEOs;
    }

    @Override
    public List<ConversionSystemItemEO> getSystemItemBySchemeTaskIdsAndFormIds(Set<String> formKeys) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemItemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_ITEM" + "   scheme  \n  where 1=1      and " + SqlUtils.getConditionOfIdsUseOr(formKeys, (String)"scheme.formId");
        List conversionSystemItemEOs = this.selectEntity(sql, new Object[0]);
        if (CollectionUtils.isEmpty(conversionSystemItemEOs)) {
            return null;
        }
        return conversionSystemItemEOs;
    }

    @Override
    public List<ConversionSystemItemEO> getSystemItemByTaskSchemeId(String taskSchemeId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(ConversionSystemItemEO.class, (String)"scheme") + "  from  " + "GC_CONV_SYSTEM_ITEM" + "   scheme  \n  where   1=1 \n  and  scheme.schemeTaskId = ? \n";
        return this.selectEntity(sql, new Object[]{taskSchemeId});
    }

    @Override
    public void deleteBySchemeTaskIdAndIndexId(String formId, String indexId) {
        String sql = "  delete from GC_CONV_SYSTEM_ITEM   \n  where  indexid = ? \n     and formId = ? \n";
        this.execute(sql, new Object[]{indexId, formId});
    }

    @Override
    public void deleteById(String itemId) {
        String sql = "  delete from GC_CONV_SYSTEM_ITEM   \n  where  id = ? \n";
        this.execute(sql, new Object[]{itemId});
    }

    @Override
    public void deleteByFormIdAndIndexId(String formId, String indexId) {
        String sql = "  delete from GC_CONV_SYSTEM_ITEM   \n  where  formId = ?        and indexid = ? \n";
        this.execute(sql, new Object[]{formId, indexId});
    }
}

