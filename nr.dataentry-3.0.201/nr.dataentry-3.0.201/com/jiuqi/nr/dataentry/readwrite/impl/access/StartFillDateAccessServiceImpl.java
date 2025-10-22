/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.param.UnitBatchAccessCache
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.param.UnitBatchAccessCache;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IStartFillDateService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class StartFillDateAccessServiceImpl
implements IDataExtendAccessItemService {
    @Autowired
    @Lazy
    private IStartFillDateService startFillDateService;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IRunTimeViewController runtimeView;
    private final Logger logger = LoggerFactory.getLogger(StartFillDateAccessServiceImpl.class);
    public static final String START_READ_MSG = "\u672a\u5230\u586b\u62a5\u65f6\u95f4\u4e0d\u53ef\u7f16\u8f91";
    private Function<String, String> noAccessReason = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(START_READ_MSG);

    public int getOrder() {
        return 0;
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        FillDateType fillingDateType = taskDefine.getFillingDateType();
        return !fillingDateType.equals((Object)FillDateType.NONE);
    }

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        boolean canFill = this.startFillDateService.canFill(formScheme.getTaskKey(), formSchemeKey, String.valueOf(masterKey.getValue("DATATIME")));
        if (canFill) {
            return new AccessCode(this.name());
        }
        return new AccessCode(this.name(), "2");
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        UnitBatchAccessCache unitBatchAccessCache = new UnitBatchAccessCache(this.name(), formSchemeKey);
        Map cacheMap = unitBatchAccessCache.getCacheMap();
        List dimValueList = masterKeys.getDimensionCombinations();
        Map<String, String> fillMap = this.startFillDateService.fillDateMap(formScheme.getTaskKey());
        for (DimensionCombination dimensionCombination : dimValueList) {
            String period = String.valueOf(dimensionCombination.getValue("DATATIME"));
            if (!fillMap.containsKey(period)) {
                cacheMap.put(dimensionCombination.toDimensionValueSet(), "2");
                continue;
            }
            String schemeKey = fillMap.get(period);
            if (formSchemeKey.equals(schemeKey)) continue;
            cacheMap.put(dimensionCombination.toDimensionValueSet(), "2");
        }
        return unitBatchAccessCache;
    }

    public String name() {
        return "startFillDate";
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessReason.apply(code);
    }

    public boolean isServerAccess() {
        return true;
    }
}

