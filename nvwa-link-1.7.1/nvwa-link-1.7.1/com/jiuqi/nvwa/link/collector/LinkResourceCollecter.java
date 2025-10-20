/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.collector;

import com.jiuqi.nvwa.link.provider.ILinkResourceProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class LinkResourceCollecter {
    private Map<String, ILinkResourceProvider> providerMap = new HashMap<String, ILinkResourceProvider>();
    private List<ILinkResourceProvider> providerList = new ArrayList<ILinkResourceProvider>();

    public LinkResourceCollecter(@Autowired(required=false) List<ILinkResourceProvider> linkSourceProvider) {
        if (CollectionUtils.isEmpty(linkSourceProvider)) {
            return;
        }
        List sortList = linkSourceProvider.stream().sorted(Comparator.comparingDouble(ILinkResourceProvider::getOrder)).collect(Collectors.toList());
        for (ILinkResourceProvider provider : sortList) {
            if (this.providerMap.containsKey(provider.getType())) {
                Iterator<ILinkResourceProvider> iter = this.providerList.iterator();
                while (iter.hasNext()) {
                    ILinkResourceProvider lrp = iter.next();
                    if (!provider.getType().equals(lrp.getType())) continue;
                    iter.remove();
                }
            }
            this.providerMap.put(provider.getType(), provider);
            this.providerList.add(provider);
        }
    }

    public ILinkResourceProvider getLinkResourceProvider(String type) {
        return this.providerMap.get(type);
    }

    public List<ILinkResourceProvider> getAllLinkResource() {
        return this.providerList;
    }
}

