/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.task.service;

import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.task.dto.FormPublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeBatchPublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeBatchPublishResultDTO;
import com.jiuqi.nr.task.dto.FormSchemePublishDTO;
import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import com.jiuqi.nr.task.exception.PublishException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface IFormSchemeReleaseService {
    public void publish(FormSchemePublishDTO var1) throws PublishException;

    public void publish(FormPublishDTO var1) throws PublishException;

    public String asyncPublish(FormSchemePublishDTO var1) throws PublishException;

    public List<FormSchemeBatchPublishResultDTO> batchPublish(FormSchemeBatchPublishDTO var1) throws PublishException;

    public FormSchemeStatusDTO getStatus(String var1);

    public void maintain(String var1);

    public void cancelMaintain(String var1);

    public ProgressItem getProgress(String var1);

    public void exportPublishError(String var1, HttpServletResponse var2);
}

