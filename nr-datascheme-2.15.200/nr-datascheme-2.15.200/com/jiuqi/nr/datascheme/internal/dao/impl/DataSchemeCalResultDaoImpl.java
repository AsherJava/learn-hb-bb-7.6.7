/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeCalResultDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeCalResultDO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeCalResultDaoImpl
extends BaseDao
implements IDataSchemeCalResultDao {
    public Class<DataSchemeCalResultDO> getClz() {
        return DataSchemeCalResultDO.class;
    }

    @Override
    public List<DataSchemeCalResultDO> getResult(String schemeKey) {
        return super.list(new String[]{"DS_KEY"}, new Object[]{schemeKey}, this.getClz());
    }

    @Override
    public void insertResult(DataSchemeCalResultDO result) {
        this.insert(result);
    }

    @Override
    public void updateResult(DataSchemeCalResultDO result) {
        this.update(result);
    }

    @Override
    public void deleteResult(String schemeKey) {
        this.deleteBy(new String[]{"DS_KEY"}, new Object[]{schemeKey});
    }

    @Override
    public void saveResult(DataSchemeCalResultDO calResult) {
        this.deleteResult(calResult.getDataSchemeKey());
        this.insertResult(calResult);
    }
}

