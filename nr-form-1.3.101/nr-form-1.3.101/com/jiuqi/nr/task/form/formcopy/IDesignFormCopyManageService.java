/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.form.formcopy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.dto.FormCopyInfoParams;
import com.jiuqi.nr.task.form.dto.FormCopyInfoVO;
import com.jiuqi.nr.task.form.dto.FormCopyLinksVO;
import com.jiuqi.nr.task.form.dto.FormCopyTaskTreeNode;
import com.jiuqi.nr.task.form.dto.FormDoCopyParams;
import com.jiuqi.nr.task.form.dto.FormSyncParamsVO;
import com.jiuqi.nr.task.form.dto.FormSyncRecordVO;
import com.jiuqi.nr.task.form.dto.FormSyncVO;
import com.jiuqi.nr.task.form.dto.SyncCoverForm;
import com.jiuqi.nr.task.form.formcopy.FormSyncResult;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import java.util.List;

public interface IDesignFormCopyManageService {
    public List<UITreeNode<FormCopyTaskTreeNode>> getTaskTree();

    public FormCopyLinksVO getSchemeCopyLinks(String var1, String var2);

    public List<FormCopyInfoVO> getFormCopyInfo(FormCopyInfoParams var1) throws Exception;

    public List<FormulaSyncResult> doFormCopy(FormDoCopyParams var1, AsyncTaskMonitor var2, StringBuilder var3) throws Exception;

    public List<FormSyncVO> getFormSyncInfo(String var1) throws Exception;

    public List<FormSyncRecordVO> getFormSyncRecord(String var1);

    public List<FormSyncResult> doFormSync(List<FormSyncParamsVO> var1, AsyncTaskMonitor var2, StringBuilder var3) throws Exception;

    public List<SyncCoverForm> messageBox(List<String> var1);
}

