/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IDataSchemeOptionGather;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeOptionGather
implements InitializingBean,
IDataSchemeOptionGather {
    @Autowired(required=false)
    private List<IDataSchemeOption> dataSchemeOptions;
    private final Map<String, IDataSchemeOption> optionMap = new ConcurrentHashMap<String, IDataSchemeOption>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.optionMap.clear();
        if (this.dataSchemeOptions == null || this.dataSchemeOptions.isEmpty()) {
            return;
        }
        for (IDataSchemeOption option : this.dataSchemeOptions) {
            if (StringUtils.isEmpty((String)option.getCode())) {
                this.logger.warn("\u6570\u636e\u6620\u5c04\u65b9\u6848\u7ba1\u63a7\u9009\u9879\u6807\u8bc6\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7", (Object)option.getClass());
                continue;
            }
            if (Objects.isNull(option.getOptionType())) {
                this.logger.warn("\u6570\u636e\u6620\u5c04\u65b9\u6848\u7ba1\u63a7\u9009\u9879\u7c7b\u578b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7", (Object)option.getClass());
                continue;
            }
            if (this.optionMap.containsKey(option.getCode())) {
                this.logger.warn("\u6570\u636e\u6620\u5c04\u65b9\u6848\u7ba1\u63a7\u9009\u9879\u6807\u8bc6\u91cd\u590d\uff0c\u5df2\u8df3\u8fc7", (Object)option.getClass());
                continue;
            }
            this.optionMap.put(option.getCode(), option);
        }
    }

    @Override
    public IDataSchemeOption getByCode(String code) {
        return this.optionMap.get(code);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

