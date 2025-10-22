/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.AllChildrenDimensionProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.AllNodeDimensionProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.BaseCurrencyProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.ChildrenDimensionProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.FilterDWByVersion;
import com.jiuqi.nr.dataservice.core.dimension.provider.FilterDimByDw;
import com.jiuqi.nr.dataservice.core.dimension.provider.LeafNodeDimensionProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.PBaseCurrencyProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.VariableDimensionValueProviderFactory;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DimensionProviderFactory {
    private Logger logger = LoggerFactory.getLogger(DimensionProviderFactory.class);
    public static final String PROVIDER_ALLNODE = "PROVIDER_ALLNODE";
    public static final String PROVIDER_ALLCHILDREN = "PROVIDER_ALLCHILDREN";
    public static final String PROVIDER_CHILDREN = "PROVIDER_CHILDREN";
    public static final String PROVIDER_FILTERDIMBYDW = "PROVIDER_FILTERDIMBYDW";
    public static final String PROVIDER_FILTERDWBYVERSION = "PROVIDER_FILTERDWBYVERSION";
    public static final String PROVIDER_BASECURRENCY = "PROVIDER_BASECURRENCY";
    public static final String PROVIDER_PBASECURRENCY = "PROVIDER_PBASECURRENCY";
    public static final String PROVIDER_LEAFNODE = "PROVIDER_LEAFNODE";
    @Autowired(required=false)
    private Map<String, VariableDimensionValueProviderFactory> beans;

    public VariableDimensionValueProvider getDimensionProvider(String type, DimensionProviderData providerData) {
        switch (type) {
            case "PROVIDER_ALLNODE": {
                return new AllNodeDimensionProvider(providerData);
            }
            case "PROVIDER_ALLCHILDREN": {
                return new AllChildrenDimensionProvider(providerData);
            }
            case "PROVIDER_CHILDREN": {
                return new ChildrenDimensionProvider(providerData);
            }
            case "PROVIDER_FILTERDIMBYDW": {
                return new FilterDimByDw(providerData);
            }
            case "PROVIDER_FILTERDWBYVERSION": {
                return new FilterDWByVersion(providerData);
            }
            case "PROVIDER_BASECURRENCY": {
                return new BaseCurrencyProvider(providerData);
            }
            case "PROVIDER_PBASECURRENCY": {
                return new PBaseCurrencyProvider(providerData);
            }
            case "PROVIDER_LEAFNODE": {
                return new LeafNodeDimensionProvider(providerData);
            }
        }
        VariableDimensionValueProvider unfoldProvider = null;
        if (this.beans != null) {
            VariableDimensionValueProviderFactory providerFactory = this.beans.get(type);
            unfoldProvider = providerFactory.getProvider(providerData);
        }
        if (unfoldProvider == null) {
            unfoldProvider = new AllNodeDimensionProvider(providerData);
            this.logger.error("\u672a\u627e\u5230\u7ef4\u5ea6\u5c55\u5f00\u63d0\u4f9b\u5668\uff1a" + type + ",\u4f7f\u7528\u9ed8\u8ba4\u89c4\u5219\uff0c\u5168\u6570\u636e\u5c55\u5f00\uff01");
        }
        return unfoldProvider;
    }
}

