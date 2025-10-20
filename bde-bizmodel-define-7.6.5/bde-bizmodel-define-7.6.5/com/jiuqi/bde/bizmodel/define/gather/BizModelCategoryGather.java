/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.gather.IBizModelCategoryGather
 *  com.jiuqi.bde.bizmodel.client.intf.IBizModelCategory
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.define.gather;

import com.jiuqi.bde.bizmodel.client.gather.IBizModelCategoryGather;
import com.jiuqi.bde.bizmodel.client.intf.IBizModelCategory;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizModelCategoryGather
implements InitializingBean,
IBizModelCategoryGather {
    @Autowired(required=false)
    private List<IBizModelCategory> registeredCategoryList;
    private final List<IBizModelCategory> modelCategoryList = new ArrayList<IBizModelCategory>();
    private final Map<String, IBizModelCategory> modelCategoryMap = new ConcurrentHashMap<String, IBizModelCategory>(16);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.modelCategoryMap.clear();
        this.modelCategoryList.clear();
        if (CollectionUtils.isEmpty(this.registeredCategoryList)) {
            this.registeredCategoryList = new ArrayList<IBizModelCategory>();
        }
        this.registeredCategoryList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u4e1a\u52a1\u6a21\u578b\u7c7b\u522b{}\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.modelCategoryMap.containsKey(item.getCode())) {
                this.modelCategoryMap.put(item.getCode(), (IBizModelCategory)item);
                this.modelCategoryList.add((IBizModelCategory)item);
            } else {
                this.logger.warn("\u4e1a\u52a1\u6a21\u578b\u7c7b\u522b{}\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7", (Object)item.getClass());
            }
        });
        this.modelCategoryList.sort((p1, p2) -> p1.getOrder() - p2.getOrder());
    }

    public List<IBizModelCategory> list() {
        if (CollectionUtils.isEmpty(this.modelCategoryList)) {
            this.logger.error("\u4e1a\u52a1\u6a21\u578b\u7c7b\u522b\u83b7\u53d6\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.modelCategoryList);
    }

    public IBizModelCategory get(String bizCategoryCode) {
        Assert.isNotEmpty((String)bizCategoryCode, (String)"\u4e1a\u52a1\u6a21\u578b\u7c7b\u522b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        IBizModelCategory bizModelCategory = this.modelCategoryMap.get(bizCategoryCode);
        Assert.isNotNull((Object)bizModelCategory, (String)"\u8ba1\u7b97\u6a21\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", (Object[])new Object[]{bizCategoryCode});
        return bizModelCategory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

