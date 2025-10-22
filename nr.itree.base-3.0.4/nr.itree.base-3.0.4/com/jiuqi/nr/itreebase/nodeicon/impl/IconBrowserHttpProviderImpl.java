/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.itreebase.nodeicon.impl;

import com.jiuqi.nr.itreebase.nodeicon.IconBrowserHttpProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IconBrowserHttpProviderImpl
implements IconBrowserHttpProvider {
    @Resource
    private IconSourceHelper sourceHelper;
    private Map<String, IconSourceScheme> iconSchemeMap = new HashMap<String, IconSourceScheme>();

    @Autowired(required=true)
    public IconBrowserHttpProviderImpl(List<IconSourceScheme> providers) {
        if (null != providers) {
            for (IconSourceScheme iconScheme : providers) {
                this.iconSchemeMap.put(iconScheme.getSchemeId(), iconScheme);
            }
        }
    }

    @Override
    public IconSourceScheme findIconSourceScheme(String sourceId) {
        return this.iconSchemeMap.get(sourceId);
    }

    @Override
    public String toBase64String(String sourceId, String key) {
        IconSourceScheme sourceScheme = this.findIconSourceScheme(sourceId);
        return this.sourceHelper.toBase64String(sourceScheme, key);
    }
}

