/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  org.apache.commons.collections4.CollectionUtils
 */
package com.jiuqi.gc.inspector.service.impl;

import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gc.inspector.common.BusinessRuntimeException;
import com.jiuqi.gc.inspector.common.TaskTypeEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gc.inspector.service.InpectorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class InpectorServiceImpl
implements InpectorService,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(InpectorService.class);
    private final Map<String, InspectBaseItem> inspectBaseItemMap = new ConcurrentHashMap<String, InspectBaseItem>();
    @Autowired(required=false)
    private List<InspectBaseItem> inspectBaseItems;

    @Override
    public void afterPropertiesSet() {
        if (CollectionUtils.isEmpty(this.inspectBaseItems)) {
            return;
        }
        for (InspectBaseItem inspectBaseItem : this.inspectBaseItems) {
            if (this.inspectBaseItemMap.containsKey(inspectBaseItem.getName())) {
                logger.warn("\u91cd\u590d\u6ce8\u518c\u7684\u68c0\u67e5\u5b9a\u4e49" + inspectBaseItem.getName() + ",\u5c06\u4ee5\u6700\u540e\u4e00\u4e2a\u4e3a\u51c6");
            }
            this.inspectBaseItemMap.put(inspectBaseItem.getName(), inspectBaseItem);
        }
    }

    @Override
    public InspectResultVO execute(TaskTypeEnum taskTypeEnum, String inspectCode, Map<String, Object> params) {
        Assert.notNull((Object)inspectCode, "\u68c0\u67e5\u9879\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        InspectBaseItem inspectBaseItem = this.inspectBaseItemMap.get(inspectCode);
        Assert.notNull((Object)inspectBaseItem, "\u672a\u6ce8\u518c\u7684\u68c0\u67e5\u9879");
        if (taskTypeEnum.equals((Object)TaskTypeEnum.INSPECT)) {
            return inspectBaseItem.executeInspect(params);
        }
        if (taskTypeEnum.equals((Object)TaskTypeEnum.FIX)) {
            BusinessLogUtils.operate((String)"\u5408\u5e76\u68c0\u67e5", (String)inspectBaseItem.getTitle(), (String)"\u6267\u884c\u4fee\u590d");
            return inspectBaseItem.executeFix(params);
        }
        throw new BusinessRuntimeException("\u64cd\u4f5c\u7c7b\u578b\u4e3a\u7a7a");
    }

    @Override
    public InspectResultVO executeInspect(String inspectCode, Map<String, Object> params) {
        return this.execute(TaskTypeEnum.INSPECT, inspectCode, params);
    }

    @Override
    public List<InspectResultVO> executeInspect(Map<String, Map<String, Object>> codesAndParams) {
        ArrayList<InspectResultVO> resultList = new ArrayList<InspectResultVO>();
        Set<String> keySet = codesAndParams.keySet();
        for (String inspectCode : keySet) {
            Map<String, Object> params = codesAndParams.get(inspectCode);
            InspectResultVO result = this.executeInspect(inspectCode, params);
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public InspectResultVO executeFix(String inspectCode, Map<String, Object> params) {
        return this.execute(TaskTypeEnum.FIX, inspectCode, params);
    }

    @Override
    public List<InspectResultVO> executeFix(Map<String, Map<String, Object>> codesAndParams) {
        ArrayList<InspectResultVO> resultList = new ArrayList<InspectResultVO>();
        Set<String> keySet = codesAndParams.keySet();
        for (String inspectCode : keySet) {
            Map<String, Object> params = codesAndParams.get(inspectCode);
            InspectResultVO result = this.executeFix(inspectCode, params);
            resultList.add(result);
        }
        return resultList;
    }
}

