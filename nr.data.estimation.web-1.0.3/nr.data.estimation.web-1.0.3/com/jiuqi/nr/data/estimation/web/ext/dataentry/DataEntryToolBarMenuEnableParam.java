/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService
 *  com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase
 *  com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams
 *  com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams$RefreshType
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.ext.dataentry;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService;
import com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.data.estimation.web.enumeration.DataEntryToolBarMenus;
import com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DataEntryToolBarMenuEnableParam
implements IRegisterDataentryRefreshParams {
    static final String paramKey = "can_estimation_buttons_enable";
    @Resource
    private IEstimationSchemeTemplateService estimationSchemeTemplateService;
    @Resource
    private IEstimationSchemeUserService estimationSchemeUserService;
    @Resource
    private IEstimationSubDatabaseHelper estimationSubDatabaseHelper;

    public IRegisterDataentryRefreshParams.RefreshType getRefreshType() {
        return IRegisterDataentryRefreshParams.RefreshType.UNIT_REFRESH;
    }

    public String getPramaKey(JtableContext context) {
        return paramKey;
    }

    public Object getPramaValue(JtableContext context) {
        IEstimationSchemeTemplate schemeTemplateByFormScheme;
        HashMap<String, Boolean> values = new HashMap<String, Boolean>();
        values.put(DataEntryToolBarMenus.newEstimationMenu.code, false);
        values.put(DataEntryToolBarMenus.oldEstimationMenu.code, false);
        String formSchemeKey = context.getFormSchemeKey();
        IDataSchemeSubDatabase subDatabaseDefine = this.estimationSubDatabaseHelper.getSubDatabaseDefine(formSchemeKey);
        if (subDatabaseDefine != null && (schemeTemplateByFormScheme = this.estimationSchemeTemplateService.findSchemeTemplateByFormScheme(formSchemeKey)) != null) {
            values.put(DataEntryToolBarMenus.newEstimationMenu.code, true);
            DimensionValueSet dimValueSet = EstimationSchemeUtils.convert2DimValueSet((Map)context.getDimensionSet());
            List oldEstimationSchemes = this.estimationSchemeUserService.findEstimationSchemes(context.getFormSchemeKey(), dimValueSet);
            values.put(DataEntryToolBarMenus.oldEstimationMenu.code, !oldEstimationSchemes.isEmpty());
        }
        return values;
    }
}

