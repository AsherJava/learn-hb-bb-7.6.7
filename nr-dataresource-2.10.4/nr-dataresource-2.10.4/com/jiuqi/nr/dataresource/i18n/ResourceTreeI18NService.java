/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.adapter.impl.PeriodAdapterImpl
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.dataresource.i18n;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.adapter.impl.PeriodAdapterImpl;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@DependsOn(value={"i18nHelperSupport"})
public class ResourceTreeI18NService {
    private static final String NAMESPACE = "nr";
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodAdapterImpl periodAdapter;

    public boolean isOpenLanaguage() {
        return this.i18nHelper.isOpenLanaguage();
    }

    public String getI18NTitle(String key, String title) {
        String message;
        if (this.i18nHelper.isOpenLanaguage() && StringUtils.hasText(message = this.i18nHelper.getMessage(key))) {
            return message;
        }
        return title;
    }

    public String getI18NDimTitle(String dimKey, String title) {
        String dimTitle;
        if (this.i18nHelper.isOpenLanaguage() && StringUtils.hasText(dimKey) && StringUtils.hasText(dimTitle = this.getDimTitle(dimKey))) {
            return dimTitle;
        }
        return title;
    }

    private String getDimTitle(String key) {
        if (this.periodAdapter.isPeriodEntity(key)) {
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(key);
            return periodProvider.getPeriodEntity().getTitle();
        }
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(key);
        if (entityDefine == null) {
            return null;
        }
        return entityDefine.getTitle();
    }
}

