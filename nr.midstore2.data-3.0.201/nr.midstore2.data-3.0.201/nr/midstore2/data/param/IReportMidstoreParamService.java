/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType
 *  com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 */
package nr.midstore2.data.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.common.FormAccessType;
import com.jiuqi.nvwa.midstore.core.definition.common.MidstoreOperateType;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import java.util.List;
import java.util.Map;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreParamService {
    public ReportMidstoreContext getReportContext(MidstoreExeContext var1);

    public MidstoreResultObject doCheckParamsBeforePulish(ReportMidstoreContext var1);

    public MidstoreResultObject doCheckParamsBeforeCleanData(ReportMidstoreContext var1);

    public MidstoreResultObject doCheckParamsBeforeGetData(ReportMidstoreContext var1);

    public MidstoreResultObject doCheckParamsBeforePostData(ReportMidstoreContext var1);

    public MidstoreResultObject doLoadFormScheme(ReportMidstoreContext var1, boolean var2) throws MidstoreException;

    public void tranUnitFormsToTables(ReportMidstoreContext var1, Map<DimensionValueSet, List<String>> var2);

    public Map<DimensionValueSet, List<String>> getUnitFormKeys(ReportMidstoreContext var1, FormAccessType var2) throws MidstoreException;

    public Map<DimensionValueSet, List<String>> getUnitFormKeysByType(ReportMidstoreContext var1, FormAccessType var2, MidstoreOperateType var3) throws MidstoreException;

    public List<String> getVisibleEntityIds(String var1);

    public void checkAndSetContextEntity(ReportMidstoreContext var1);
}

