/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.IFetchSettingImpExpHandleAdaptor;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleAdaptorGather;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultImpExpHandleAdaptorGather
implements ImpExpHandleAdaptorGather {
    private List<IFetchSettingImpExpHandleAdaptor> registeredFetchSettingImpExpHandleAdaptorList;
    private Map<String, IFetchSettingImpExpHandleAdaptor> handleAdaptorMap = new ConcurrentHashMap<String, IFetchSettingImpExpHandleAdaptor>();
    private static final Logger logger = LoggerFactory.getLogger(DefaultImpExpHandleAdaptorGather.class);

    public DefaultImpExpHandleAdaptorGather(@Autowired(required=false) List<IFetchSettingImpExpHandleAdaptor> registeredFetchSettingImpExpHandleAdaptorList) {
        this.registeredFetchSettingImpExpHandleAdaptorList = registeredFetchSettingImpExpHandleAdaptorList;
        this.init();
    }

    @Override
    public IFetchSettingImpExpHandleAdaptor getHandleAdaptor(String bizTypeStr) {
        BizTypeEnum bizType = BizTypeEnum.getEnumByCode((String)bizTypeStr);
        return this.handleAdaptorMap.get(bizType.getCode());
    }

    private void init() {
        try {
            this.reload();
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u5bfc\u51fa\u9002\u914d\u5668\u6536\u96c6\u5668\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    public void reload() {
        this.handleAdaptorMap.clear();
        if (CollectionUtils.isEmpty(this.registeredFetchSettingImpExpHandleAdaptorList)) {
            this.registeredFetchSettingImpExpHandleAdaptorList = new ArrayList<IFetchSettingImpExpHandleAdaptor>();
        }
        this.registeredFetchSettingImpExpHandleAdaptorList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getBizType())) {
                logger.warn("\u53d6\u6570\u8bbe\u7f6e\u5904\u7406\u9002\u914d\u5668{}\uff0c\u4e1a\u52a1\u7c7b\u578b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            BizTypeEnum bizType = BizTypeEnum.findEnumByCode((String)item.getBizType());
            if (bizType == null) {
                logger.warn("\u53d6\u6570\u8bbe\u7f6e\u5904\u7406\u9002\u914d\u5668{}\uff0c\u4e1a\u52a1\u7c7b\u578b\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getBizType());
                return;
            }
            if (!this.handleAdaptorMap.containsKey(item.getBizType())) {
                this.handleAdaptorMap.put(item.getBizType(), (IFetchSettingImpExpHandleAdaptor)item);
            }
        });
    }
}

