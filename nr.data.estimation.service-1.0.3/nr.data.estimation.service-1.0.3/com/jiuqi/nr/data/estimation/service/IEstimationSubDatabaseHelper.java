/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.estimation.service;

import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import java.util.Map;

public interface IEstimationSubDatabaseHelper {
    public IDataSchemeSubDatabase getSubDatabaseDefine(String var1);

    public IDataSchemeSubDatabase getSubDatabaseDefine(FormSchemeDefine var1);

    public List<TableModelDefine> getTableModelsByFormScheme(String var1);

    public List<String> getTableNamesByFormScheme(String var1);

    public Map<String, String> getOri2CopiedTableMap(IEstimationScheme var1);

    public Map<String, String> getOtherPrimaryMap(IEstimationScheme var1);
}

