/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.ref;

import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.ref.RefTableDataProvider;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
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
public class RefTableDataManager
implements InitializingBean {
    @Autowired(required=false)
    private List<RefTableDataProvider> list = new ArrayList<RefTableDataProvider>();
    private Map<Integer, RefTableDataProvider> map;
    private static RefTableDataManager instance;

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
        this.map = new HashMap<Integer, RefTableDataProvider>();
        this.list.forEach(o -> this.map.put(o.getRefTableType(), (RefTableDataProvider)o));
    }

    public static RefTableDataProvider get(int type) {
        RefTableDataProvider provider = RefTableDataManager.instance.map.get(type);
        if (provider == null) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.reftabledatamanager.notfounddataprovider") + type);
        }
        return provider;
    }
}

