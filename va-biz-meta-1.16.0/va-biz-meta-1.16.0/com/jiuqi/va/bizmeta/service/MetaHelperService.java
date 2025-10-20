/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.helper.BizViewTemplateDO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public interface MetaHelperService {
    public R saveBillTemplate(BizViewTemplateDO var1);

    public R listBillTemplate(String var1, String var2);

    public R deleteBillTemplate(BizViewTemplateDO var1);

    public List<VaI18nResourceItem> listWorkFlowVersionItemResourceList(TenantDO var1);

    public List<VaI18nResourceItem> listWorkFlowVersionResourceList(TenantDO var1);

    public <T> void convertMetaInfoI18nLanguage(T var1, Function<T, String> var2, BiConsumer<T, String> var3);

    public <T> void convertMetaInfoI18nLanguage(List<T> var1, Function<T, String> var2, BiConsumer<T, String> var3);

    public R findZhEnI18nLanguage(MetaInfoDTO var1);
}

