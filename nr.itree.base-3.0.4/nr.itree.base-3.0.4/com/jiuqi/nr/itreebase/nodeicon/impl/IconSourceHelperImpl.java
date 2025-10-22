/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.itreebase.nodeicon.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.ImageType;
import com.jiuqi.nr.itreebase.nodeicon.ImgExtensionScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceProviderImpl;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class IconSourceHelperImpl
implements IconSourceHelper {
    @Resource
    private NedisCacheProvider nedisCacheProvider;

    @Override
    public IconSourceProvider getIconProvider(String sourceId, IconSourceScheme[] schemes) {
        NedisCache nedisCache = this.nedisCacheProvider.getCacheManager("dim_tree_cache_8070815796873628132L").getCache(sourceId);
        return new IconSourceProviderImpl(sourceId, schemes, nedisCache, this);
    }

    @Override
    public IconSource toIconSource(IconSourceScheme scheme, String key) {
        if (scheme != null) {
            return scheme.getIconSource(key);
        }
        return null;
    }

    @Override
    public byte[] toByteArray(IconSourceScheme scheme, String key) {
        IconSource iconSource = this.toIconSource(scheme, key);
        if (iconSource != null) {
            return iconSource.getContent();
        }
        return null;
    }

    @Override
    public String toBase64String(IconSourceScheme scheme, String key) {
        byte[] bytes = this.toByteArray(scheme, key);
        if (bytes != null) {
            return this.toBase64(bytes);
        }
        return null;
    }

    @Override
    public String toImgSrc(IconSourceScheme scheme, String key) {
        IconSource iconSource = this.toIconSource(scheme, key);
        return this.toImgSrc(iconSource);
    }

    @Override
    public String toImgSrc(IconSource iconSource) {
        if (iconSource != null) {
            ImageType imageType = iconSource.getType();
            if (imageType == null) {
                throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u56fe\u7247\u7c7b\u578b\uff0c\u89e3\u6790\u5931\u8d25\uff01");
            }
            byte[] content = iconSource.getContent();
            String base64Icon = this.toBase64(content);
            return ImgExtensionScheme.get((ImageType)imageType).extensionScheme + base64Icon;
        }
        return null;
    }

    private String toBase64(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }
}

