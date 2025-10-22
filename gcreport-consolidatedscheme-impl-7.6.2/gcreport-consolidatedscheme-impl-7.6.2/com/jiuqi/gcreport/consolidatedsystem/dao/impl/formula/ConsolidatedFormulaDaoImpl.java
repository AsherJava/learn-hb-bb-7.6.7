/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl.formula;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.formula.ConsolidatedFormulaDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.Formula.ConsolidatedFormulaEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ConsolidatedFormulaDaoImpl
extends GcDbSqlGenericDAO<ConsolidatedFormulaEO, String>
implements ConsolidatedFormulaDao {
    public ConsolidatedFormulaDaoImpl() {
        super(ConsolidatedFormulaEO.class);
    }

    @Override
    public List<ConsolidatedFormulaEO> listConsFormulas(String systemId) {
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(ConsolidatedFormulaEO.class, (String)"t") + "\n from " + "GC_CONSFORMULA" + " t where t.systemId=?\n order by t.sortorder asc \n";
        return this.selectEntity(sql, new Object[]{systemId});
    }

    @Override
    public void batchDeleteByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String sql = "delete from GC_CONSFORMULA  where " + SqlUtils.getConditionOfIdsUseByPlaceholder(ids, (String)"id");
        this.execute(sql, new ArrayList<String>(ids));
    }

    @Override
    public ConsolidatedFormulaEO getPreNodeBySystemIdAndOrder(String systemId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_CONSFORMULA  t \nwhere t.systemId=? \nand t.sortOrder<? \norder by t.sortOrder desc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(ConsolidatedFormulaEO.class, (String)"t"));
        List eos = this.selectEntityByPaging(sql, 0, 1, new Object[]{systemId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return (ConsolidatedFormulaEO)((Object)eos.get(0));
    }

    @Override
    public ConsolidatedFormulaEO getNextNodeBySystemIdAndOrder(String systemId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_CONSFORMULA  t \nwhere t.systemId=? \nand t.sortOrder>? \norder by t.sortOrder asc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(ConsolidatedFormulaEO.class, (String)"t"));
        List eos = this.selectEntityByPaging(sql, 0, 1, new Object[]{systemId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return (ConsolidatedFormulaEO)((Object)eos.get(0));
    }
}

