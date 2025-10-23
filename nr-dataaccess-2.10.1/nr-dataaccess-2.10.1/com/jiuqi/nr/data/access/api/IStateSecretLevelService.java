/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.data.access.api;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.api.response.ReturnInfo;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.access.param.SecretLevelDim;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface IStateSecretLevelService {
    public boolean secretLevelEnable(String var1);

    public List<SecretLevel> getSecretLevelItems();

    public INvwaDataSet getSercetLevelDataTable(String var1, DimensionValueSet var2, boolean var3);

    public SecretLevel getSecretLevelItem(String var1);

    public boolean canAccess(SecretLevel var1);

    public boolean compareSercetLevel(SecretLevel var1, SecretLevel var2);

    public void saveSecretLevel(DimensionValueSet var1, String var2, SecretLevel var3);

    public void batchSaveSecretLevel(DimensionValueSet var1, String var2, List<SecretLevelDim> var3);

    public SecretLevel getSecretLevel(DimensionValueSet var1, String var2);

    public List<SecretLevel> getSecretLevelItems(DimensionValueSet var1, String var2);

    public Map<DimensionValueSet, SecretLevel> batchQuerySecretLevels(DimensionValueSet var1, String var2);

    public TableModelDefine getTableModelDefine(String var1);

    public void extractPrePeriodSecretLevel(DimensionValueSet var1, String var2, String var3);

    public void exportSecretLevel(DimensionValueSet var1, String var2, String var3, HttpServletResponse var4);

    public ReturnInfo importSecretLevel(String var1, DimensionValueSet var2, String var3, String var4);
}

