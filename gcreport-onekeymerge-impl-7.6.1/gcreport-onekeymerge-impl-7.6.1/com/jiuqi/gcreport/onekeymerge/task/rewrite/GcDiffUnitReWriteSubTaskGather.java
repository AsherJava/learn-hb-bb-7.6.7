/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.onekeymerge.task.rewrite;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.IGcDiffUnitReWriteSubTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcDiffUnitReWriteSubTaskGather
implements InitializingBean {
    @Autowired(required=false)
    private List<IGcDiffUnitReWriteSubTask> gcDiffUnitReWriteSubTasks;
    private final Map<String, IGcDiffUnitReWriteSubTask> nameToExecutorMap = new ConcurrentHashMap<String, IGcDiffUnitReWriteSubTask>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.nameToExecutorMap.clear();
        if (CollectionUtils.isEmpty(this.gcDiffUnitReWriteSubTasks)) {
            this.gcDiffUnitReWriteSubTasks = new ArrayList<IGcDiffUnitReWriteSubTask>();
        }
        this.gcDiffUnitReWriteSubTasks.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getName())) {
                this.logger.warn("\u5408\u5e76\u5dee\u989d\u5355\u4f4d\u56de\u5199\u4efb\u52a1{}\uff0c\u5904\u7406\u65b9\u5f0f\u540d\u79f0\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (this.nameToExecutorMap.containsKey(item.getName())) {
                this.logger.warn("\u5408\u5e76\u5dee\u989d\u5355\u4f4d\u56de\u5199\u4efb\u52a1{}\u88ab\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getName());
                return;
            }
            this.nameToExecutorMap.put(item.getName(), (IGcDiffUnitReWriteSubTask)item);
        });
    }

    public IGcDiffUnitReWriteSubTask getByName(String name) {
        Assert.isNotEmpty((String)name);
        IGcDiffUnitReWriteSubTask gcDiffUnitReWriteSubTask = this.nameToExecutorMap.get(name);
        Assert.isNotNull((Object)gcDiffUnitReWriteSubTask, (String)String.format("\u6ca1\u6709\u540d\u79f0\u4e3a\u3010%s\u3011\u7684\u5408\u5e76\u5dee\u989d\u5355\u4f4d\u56de\u5199\u4efb\u52a1", name), (Object[])new Object[0]);
        return gcDiffUnitReWriteSubTask;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

