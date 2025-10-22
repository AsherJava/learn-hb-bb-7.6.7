/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType
 */
package nr.midstore2.data.util.auth;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType;
import java.util.List;
import java.util.Map;

public interface IReportMidstoreFormDataAccess {
    public Map<DimensionValueSet, List<String>> getFormDataAccess(String var1, List<String> var2, String var3, Map<String, DimensionValue> var4, FormAccessType var5);

    public Map<DimensionValueSet, MistoreWorkUnitInfo> getFormDataAccessByReason(String var1, List<String> var2, String var3, Map<String, DimensionValue> var4, FormAccessType var5);

    public List<String> getUnitListByfiterUnitState(String var1, List<String> var2, String var3, Map<String, DimensionValue> var4, List<String> var5);
}

