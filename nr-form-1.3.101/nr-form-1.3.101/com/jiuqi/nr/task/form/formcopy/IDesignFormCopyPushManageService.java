/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy;

import com.jiuqi.nr.task.form.dto.FormCopyFormCodeSameCheckVO;
import com.jiuqi.nr.task.form.dto.FormCopyInfoCheckVO;
import com.jiuqi.nr.task.form.dto.FormCopyInfoVO;
import com.jiuqi.nr.task.form.dto.FormCopyLinksVO;
import com.jiuqi.nr.task.form.dto.FormCopyPushLinkVO;
import com.jiuqi.nr.task.form.dto.FormDoCopyParams;
import com.jiuqi.nr.task.form.dto.FormSyncPushExecuteVO;
import com.jiuqi.nr.task.form.dto.FormSyncRecordPushVO;
import com.jiuqi.nr.task.form.formcopy.FormSyncPushResult;
import java.util.List;
import java.util.Map;

public interface IDesignFormCopyPushManageService {
    public FormCopyLinksVO getSrcScheme(String var1) throws Exception;

    public Map<String, FormCopyLinksVO> getSrcDesLinks(FormCopyPushLinkVO var1);

    public Map<String, List<FormCopyInfoVO>> getFormCopyPushCheck(FormCopyInfoCheckVO var1) throws Exception;

    public boolean checkFormCodeSame(String var1, String var2);

    public List<String> checkFormCodeSames(FormCopyFormCodeSameCheckVO var1) throws Exception;

    public List<FormSyncPushResult> doFormCopyPush(List<FormDoCopyParams> var1) throws Exception;

    public FormSyncPushExecuteVO getFormSyncInfo(String var1) throws Exception;

    public List<FormSyncPushResult> doFormPushSync(FormSyncPushExecuteVO var1) throws Exception;

    public List<FormSyncRecordPushVO> getFormSyncPushRecord(String var1);
}

