/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.midstore.core.util.auth;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.common.FormAccessType;

public interface IMidstoreFormDataAccess {
    public Map<DimensionValueSet, List<String>> getFormDataAccess(String var1, List<String> var2, String var3, Map<String, DimensionValue> var4, FormAccessType var5);

    public Map<DimensionValueSet, MistoreWorkUnitInfo> getFormDataAccessByReason(String var1, List<String> var2, String var3, Map<String, DimensionValue> var4, FormAccessType var5);
}

