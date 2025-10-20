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
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.IFetchSettingExportService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.IFetchSettingImportService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultImpExpHandleGather
implements ImpExpHandleGather {
    private List<ImpExpInnerColumnHandler> registeredExpColumnHandlerList;
    private List<IFetchSettingImportService> registeredFetchSettingImportServiceList;
    private List<IFetchSettingExportService> registeredFetchSettingExportServiceList;
    private List<ImpExpInnerColumnHandler> impExpColumnHandlerList = new ArrayList<ImpExpInnerColumnHandler>();
    private Map<String, ImpExpInnerColumnHandler> impExpColumnHandlerMap = new ConcurrentHashMap<String, ImpExpInnerColumnHandler>();
    private Map<String, IFetchSettingImportService> importServiceMap = new ConcurrentHashMap<String, IFetchSettingImportService>();
    private Map<String, IFetchSettingExportService> exportServiceMap = new ConcurrentHashMap<String, IFetchSettingExportService>();
    private static final Logger logger = LoggerFactory.getLogger(DefaultImpExpHandleGather.class);

    public DefaultImpExpHandleGather(@Autowired(required=false) List<ImpExpInnerColumnHandler> registeredExpColumnHandlerList, @Autowired(required=false) List<IFetchSettingImportService> registeredFetchSettingImportServiceList, @Autowired(required=false) List<IFetchSettingExportService> registeredFetchSettingExportServiceList, @Autowired(required=false) List<IFetchSettingImpExpHandleAdaptor> registeredFetchSettingImpExpHandleAdaptorList) {
        this.registeredExpColumnHandlerList = registeredExpColumnHandlerList;
        this.registeredFetchSettingImportServiceList = registeredFetchSettingImportServiceList;
        this.registeredFetchSettingExportServiceList = registeredFetchSettingExportServiceList;
        this.init();
    }

    @Override
    public List<ImpExpInnerColumnHandler> listColumnHandler() {
        return this.impExpColumnHandlerList;
    }

    @Override
    public ImpExpInnerColumnHandler getColumnHandler(String columnName) {
        return this.impExpColumnHandlerMap.get(columnName);
    }

    @Override
    public IFetchSettingImportService getImpServiceByBizType(String bizTypeStr) {
        BizTypeEnum bizType = BizTypeEnum.getEnumByCode((String)bizTypeStr);
        return this.importServiceMap.get(bizType.getCode());
    }

    @Override
    public IFetchSettingExportService getExpServiceByBizType(String bizTypeStr) {
        BizTypeEnum bizType = BizTypeEnum.getEnumByCode((String)bizTypeStr);
        return this.exportServiceMap.get(bizType.getCode());
    }

    private void init() {
        try {
            this.reload();
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u5bfc\u51fa\u62d3\u5c55\u6536\u96c6\u5668\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    public void reload() {
        this.impExpColumnHandlerList = new ArrayList<ImpExpInnerColumnHandler>();
        if (CollectionUtils.isEmpty(this.registeredExpColumnHandlerList)) {
            this.registeredExpColumnHandlerList = new ArrayList<ImpExpInnerColumnHandler>();
        }
        this.impExpColumnHandlerList = this.registeredExpColumnHandlerList.stream().sorted((o1, o2) -> o1.getOrder() - o2.getOrder()).collect(Collectors.toList());
        this.impExpColumnHandlerMap.clear();
        this.impExpColumnHandlerList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getKey())) {
                logger.warn("\u53d6\u6570\u8bbe\u7f6e\u62d3\u5c55\u5217{}\uff0c\u6807\u8bc6\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.impExpColumnHandlerMap.containsKey(item.getKey())) {
                this.impExpColumnHandlerMap.put(item.getKey(), (ImpExpInnerColumnHandler)item);
            }
        });
        this.importServiceMap.clear();
        if (CollectionUtils.isEmpty(this.registeredFetchSettingImportServiceList)) {
            this.registeredFetchSettingImportServiceList = new ArrayList<IFetchSettingImportService>();
        }
        this.registeredFetchSettingImportServiceList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getBizType())) {
                logger.warn("\u53d6\u6570\u8bbe\u7f6e\u5bfc\u5165\u4e1a\u52a1\u5c42{}\uff0c\u4e1a\u52a1\u7c7b\u578b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            BizTypeEnum bizType = BizTypeEnum.findEnumByCode((String)item.getBizType());
            if (bizType == null) {
                logger.warn("\u53d6\u6570\u8bbe\u7f6e\u5bfc\u5165\u4e1a\u52a1\u5c42{}\uff0c\u4e1a\u52a1\u7c7b\u578b\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getBizType());
                return;
            }
            if (!this.importServiceMap.containsKey(item.getBizType())) {
                this.importServiceMap.put(item.getBizType(), (IFetchSettingImportService)item);
            }
        });
        this.exportServiceMap.clear();
        if (CollectionUtils.isEmpty(this.registeredFetchSettingExportServiceList)) {
            this.registeredFetchSettingExportServiceList = new ArrayList<IFetchSettingExportService>();
        }
        this.registeredFetchSettingExportServiceList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getBizType())) {
                logger.warn("\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u4e1a\u52a1\u5c42{}\uff0c\u4e1a\u52a1\u7c7b\u578b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            BizTypeEnum bizType = BizTypeEnum.findEnumByCode((String)item.getBizType());
            if (bizType == null) {
                logger.warn("\u53d6\u6570\u8bbe\u7f6e\u5bfc\u51fa\u4e1a\u52a1\u5c42{}\uff0c\u4e1a\u52a1\u7c7b\u578b\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getBizType());
                return;
            }
            if (!this.exportServiceMap.containsKey(item.getBizType())) {
                this.exportServiceMap.put(item.getBizType(), (IFetchSettingExportService)item);
            }
        });
    }
}

