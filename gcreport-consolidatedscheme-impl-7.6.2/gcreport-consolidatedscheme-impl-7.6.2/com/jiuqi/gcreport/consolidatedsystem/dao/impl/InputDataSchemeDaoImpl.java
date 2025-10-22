/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.InputDataSchemeDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class InputDataSchemeDaoImpl
extends GcDbSqlGenericDAO<InputDataSchemeEO, String>
implements InputDataSchemeDao {
    public InputDataSchemeDaoImpl() {
        super(InputDataSchemeEO.class);
    }

    @Override
    public InputDataSchemeEO getInputDataSchemeByDataSchemeKey(String dataSchemeKey) {
        String sql = " select %s \n    from GC_INPUTDATASCHEME  d \n    where d.dataschemekey=? \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_INPUTDATASCHEME", (String)"d"));
        List inputDataSchemes = this.selectEntity(sql, new Object[]{dataSchemeKey});
        if (!CollectionUtils.isEmpty(inputDataSchemes)) {
            return (InputDataSchemeEO)((Object)inputDataSchemes.get(0));
        }
        return null;
    }

    @Override
    public void deleteInputDataSchemeByDataSchemeKey(String dataSchemeKey) {
        String sql = "delete  from GC_INPUTDATASCHEME  \n   where dataschemekey=? \n";
        this.execute(sql, new Object[]{dataSchemeKey});
    }
}

