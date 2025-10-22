/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.service;

import com.jiuqi.nr.dataentry.attachment.intf.IAttachmentContext;
import com.jiuqi.nr.dataentry.attachment.intf.SubordinateDWContext;
import com.jiuqi.nr.dataentry.attachment.message.TaskObj;
import java.util.List;

public interface TaskOperationService {
    public TaskObj getCurrentTaskInfo(IAttachmentContext var1);

    public List<TaskObj> getAllRunTimeTasks();

    public String getFormSchemeKey(IAttachmentContext var1);

    public boolean isOpenFilepool();

    public boolean isPreview();

    public List<String> getSubordinateDW(SubordinateDWContext var1);
}

