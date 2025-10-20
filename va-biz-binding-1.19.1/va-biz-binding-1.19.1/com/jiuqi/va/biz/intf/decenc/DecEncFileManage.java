/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.decenc;

import com.jiuqi.va.biz.intf.decenc.DecEncFile;
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
public class DecEncFileManage
implements InitializingBean {
    @Autowired(required=false)
    private List<DecEncFile> list = new ArrayList<DecEncFile>();
    private Map<String, DecEncFile> map;
    private static DecEncFileManage instance;

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
        this.map = new HashMap<String, DecEncFile>();
        this.list.forEach(o -> this.map.put(o.getName(), (DecEncFile)o));
    }

    public static DecEncFile get(String type) {
        DecEncFile provider = DecEncFileManage.instance.map.get(type);
        if (provider == null) {
            throw new ModelException("\u6587\u4ef6\u52a0\u89e3\u5bc6\u5b9e\u73b0\u4e0d\u5b58\u5728" + type);
        }
        return provider;
    }
}

