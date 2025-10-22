/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.util.StringUtils;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=5)
@Component
public class TaskPublishReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(TaskPublishReadWriteAccessImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private DesignTimePlanPublishService designTimePlanPublishService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public String getName() {
        return "TaskPublish";
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        Boolean isProtectIng = (Boolean)item.getParams();
        if (isProtectIng.booleanValue()) {
            String message = "\u4efb\u52a1\u7ef4\u62a4\u4e2d\uff0c\u4e0d\u53ef\u5199";
            if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("TASK_DEFEND_CANNOT_WRITTEN"))) {
                message = this.i18nHelper.getMessage("TASK_DEFEND_CANNOT_WRITTEN");
            }
            return new ReadWriteAccessDesc(false, message);
        }
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        if (readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_WRITE || readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE) {
            JtableContext jtableContext = readWriteAccessCacheParams.getJtableContext();
            boolean isProtectIng = false;
            try {
                isProtectIng = this.designTimePlanPublishService.taskIsProtectIng(jtableContext.getTaskKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return isProtectIng;
        }
        return null;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == formAccessLevel) {
            Boolean isProtectIng = (Boolean)cacheObj;
            if (isProtectIng.booleanValue()) {
                String message = "\u4efb\u52a1\u7ef4\u62a4\u4e2d\uff0c\u4e0d\u53ef\u5199";
                if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("TASK_DEFEND_CANNOT_WRITTEN"))) {
                    message = this.i18nHelper.getMessage("TASK_DEFEND_CANNOT_WRITTEN");
                }
                return new ReadWriteAccessDesc(false, message);
            }
            return new ReadWriteAccessDesc(true, "");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "UNIT";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        try {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                return formScheme.getTaskKey();
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return null;
    }
}

