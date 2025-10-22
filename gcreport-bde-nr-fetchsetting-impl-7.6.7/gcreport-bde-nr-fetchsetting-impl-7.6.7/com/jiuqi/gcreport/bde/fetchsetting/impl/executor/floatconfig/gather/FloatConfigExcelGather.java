/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.gather;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.floatconfig.FloatConfigExcelHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FloatConfigExcelGather
implements InitializingBean {
    @Autowired(required=false)
    List<FloatConfigExcelHandle> floatConfigExcelHandleList;
    private final Map<String, FloatConfigExcelHandle> floatConfigExcelHandleMap = new ConcurrentHashMap<String, FloatConfigExcelHandle>();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    private void init() {
        this.floatConfigExcelHandleMap.clear();
        if (CollectionUtils.isEmpty(this.floatConfigExcelHandleList)) {
            this.floatConfigExcelHandleList = new ArrayList<FloatConfigExcelHandle>();
        }
        this.floatConfigExcelHandleList.forEach(item -> this.floatConfigExcelHandleMap.put(item.getCode(), (FloatConfigExcelHandle)item));
    }

    public FloatConfigExcelHandle getByFloatConfigCode(String floatConfigType) {
        Assert.isNotEmpty((String)floatConfigType);
        FloatConfigExcelHandle modelLoader = this.floatConfigExcelHandleMap.get(floatConfigType);
        return modelLoader;
    }
}

