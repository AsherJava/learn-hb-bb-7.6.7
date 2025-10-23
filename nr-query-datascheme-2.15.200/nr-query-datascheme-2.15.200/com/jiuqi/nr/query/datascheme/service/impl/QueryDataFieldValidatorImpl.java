/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.datascheme.internal.validator.DefaultFieldValidator
 */
package com.jiuqi.nr.query.datascheme.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.validator.DefaultFieldValidator;
import com.jiuqi.nr.query.datascheme.service.IQueryDataFieldValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="QueryDataFieldValidatorImpl")
public class QueryDataFieldValidatorImpl
extends DefaultFieldValidator
implements IQueryDataFieldValidator {
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;

    protected void checkField1(DesignDataTableDO tableDO, Map<String, DesignDataField> codes) {
        String tableKey = tableDO.getKey();
        List codeFields = this.dataFieldDao.getByTableAndCode(tableKey, new ArrayList<String>(codes.keySet()), DataFieldKind.DETAIL);
        this.codeCheck(codes, codeFields);
    }

    protected void checkField4(DesignDataTable table, Collection<? extends DesignDataField> fields) {
    }
}

