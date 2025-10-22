/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.FormBatchAuthCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=25)
@Component
@Deprecated
public class FormWriteableReadWriteAccessImpl
implements IReadWriteAccess,
IBatchDimensionReadWriteAccess {
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;

    @Override
    public String getName() {
        return "formWritealbe";
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, JtableContext context) throws Exception {
        String formKey;
        JSONObject json = new JSONObject((Map)((HashMap)item.getParams()));
        if (json.has("formKey") && !this.authorityProvider.canWriteForm(formKey = json.getString("formKey"))) {
            if (DataEntryUtil.isChinese()) {
                return new ReadWriteAccessDesc(false, "\u7528\u6237\u6ca1\u6709\u7f16\u8f91\u6743\u9650");
            }
            return new ReadWriteAccessDesc(false, "The user has no edit permission");
        }
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        List<String> formKeysByUser = readWriteAccessCacheParams.getFormKeys();
        FormBatchAuthCache authCache = new FormBatchAuthCache();
        HashSet<String> notReadForms = new HashSet<String>();
        HashSet<String> notWriteForms = new HashSet<String>();
        if (readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_READ || readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_READ) {
            for (String formKey : formKeysByUser) {
                if (this.authorityProvider.canReadForm(formKey)) continue;
                notReadForms.add(formKey);
            }
        } else if (readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_WRITE || readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE) {
            for (String formKey : formKeysByUser) {
                if (this.authorityProvider.canWriteForm(formKey)) continue;
                notWriteForms.add(formKey);
            }
        }
        authCache.setNotReadForms(notReadForms);
        authCache.setNotWriteForms(notWriteForms);
        return authCache;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        HashSet<String> notWriteForms;
        FormBatchAuthCache authCache;
        HashSet<String> notReadForms;
        if (formAccessLevel == Consts.FormAccessLevel.FORM_READ || formAccessLevel == Consts.FormAccessLevel.FORM_DATA_READ ? (notReadForms = (authCache = (FormBatchAuthCache)cacheObj).getNotReadForms()).contains(formKey) : (formAccessLevel == Consts.FormAccessLevel.FORM_DATA_WRITE || formAccessLevel == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE) && (notWriteForms = (authCache = (FormBatchAuthCache)cacheObj).getNotWriteForms()).contains(formKey)) {
            return new ReadWriteAccessDesc(false, "\u5f53\u524d\u7528\u6237\u5bf9\u62a5\u8868\u65e0\u7f16\u8f91\u6743\u9650");
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "FORM";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        return ReadWriteAccessCacheManager.getStatusKey(null, formKey, entityList);
    }
}

