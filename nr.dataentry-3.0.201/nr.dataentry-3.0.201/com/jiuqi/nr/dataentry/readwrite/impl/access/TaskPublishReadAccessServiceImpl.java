/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.common.AccessLevel
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.param.UnitBatchAccessCache
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.UnitBatchAccessCache;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskPublishReadAccessServiceImpl
implements IDataExtendAccessItemService {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private DesignTimePlanPublishService designTimePlanPublishService;
    private static final Logger logger = LoggerFactory.getLogger(TaskPublishReadAccessServiceImpl.class);
    private static final String TASK_PUB_MSG = "\u4efb\u52a1\u7ef4\u62a4\u4e2d\uff0c\u4e0d\u53ef\u5199";
    private Function<String, String> noAccessResion = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(TASK_PUB_MSG);

    public int getOrder() {
        return 1;
    }

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Boolean isProtectIng = (Boolean)param.getParams();
        if (isProtectIng.booleanValue()) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection collectionKey, List<String> formKeys) {
        UnitBatchAccessCache unitBatchAccessCache = new UnitBatchAccessCache(this.name(), formSchemeKey);
        Map cacheMap = unitBatchAccessCache.getCacheMap();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        boolean isProtectIng = false;
        try {
            isProtectIng = this.designTimePlanPublishService.taskIsProtectIng(formScheme.getTaskKey());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        List masterKeyList = DimensionValueSetUtil.toDimensionValueSetList((DimensionCollection)collectionKey);
        if (isProtectIng) {
            masterKeyList.forEach(v -> cacheMap.put(v, "2"));
        }
        return unitBatchAccessCache;
    }

    public String name() {
        return "TaskPublish";
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessResion.apply(code);
    }

    public AccessLevel getLevel() {
        return AccessLevel.UNIT;
    }
}

