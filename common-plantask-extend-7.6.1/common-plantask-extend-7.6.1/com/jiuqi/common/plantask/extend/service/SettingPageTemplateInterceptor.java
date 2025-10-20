/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.plantask.extend.service;

import com.jiuqi.common.plantask.extend.vo.SettingPageTemplateVO;
import org.springframework.core.Ordered;

public interface SettingPageTemplateInterceptor
extends Ordered {
    public String getTemplateCode();

    public SettingPageTemplateVO intercept(SettingPageTemplateVO var1);
}

