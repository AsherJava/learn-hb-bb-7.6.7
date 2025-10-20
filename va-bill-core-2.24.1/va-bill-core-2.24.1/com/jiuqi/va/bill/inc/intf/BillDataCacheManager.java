/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.ModelException
 */
package com.jiuqi.va.bill.inc.intf;

import com.jiuqi.va.bill.inc.intf.BillDataCacheProvider;
import com.jiuqi.va.biz.intf.model.ModelException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BillDataCacheManager
implements InitializingBean {
    @Autowired(required=false)
    private List<BillDataCacheProvider> list = new ArrayList<BillDataCacheProvider>();
    private Map<Integer, BillDataCacheProvider> map;
    public static BillDataCacheManager instance;

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
        this.map = new HashMap<Integer, BillDataCacheProvider>();
        this.list.forEach(o -> this.map.put(o.getCacheType(), (BillDataCacheProvider)o));
    }

    public static BillDataCacheProvider get(int type) {
        BillDataCacheProvider provider = BillDataCacheManager.instance.map.get(type);
        if (provider == null) {
            throw new ModelException("\u5355\u636e\u6570\u636e\u7f13\u5b58\u5b9e\u73b0\u4e0d\u5b58\u5728" + type);
        }
        return provider;
    }
}

