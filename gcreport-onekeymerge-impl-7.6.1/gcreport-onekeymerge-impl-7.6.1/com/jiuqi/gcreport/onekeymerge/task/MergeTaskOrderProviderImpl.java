/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.gcreport.onekeymerge.task.MergeTaskOrderProvider;
import com.jiuqi.gcreport.onekeymerge.util.OrgKindEnum;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MergeTaskOrderProviderImpl
implements MergeTaskOrderProvider {
    @Override
    public List<String> getTaskTypesOrderByBBLX(String bblx) {
        if (OrgKindEnum.MERGE.getCode().equals(bblx)) {
            return TaskTypeEnum.MERGEORGTASKS.stream().map(TaskTypeEnum::getCode).collect(Collectors.toList());
        }
        return TaskTypeEnum.SINGLEORGTASKS.stream().map(TaskTypeEnum::getCode).collect(Collectors.toList());
    }
}

