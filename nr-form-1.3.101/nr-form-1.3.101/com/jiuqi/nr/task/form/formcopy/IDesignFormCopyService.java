/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.task.form.formcopy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.task.form.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.task.form.formcopy.FormCopyParams;
import com.jiuqi.nr.task.form.formcopy.FormCopyRecord;
import com.jiuqi.nr.task.form.formcopy.FormCopyRecordPush;
import com.jiuqi.nr.task.form.formcopy.FormSyncParams;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResultAll;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyInfo;
import java.util.List;

public interface IDesignFormCopyService {
    public List<IFormCopyInfo> getFormCopyInfoBySchemeKey(String var1);

    public List<IFormCopyInfo> getFormCopyInfoBySrcSchemeKey(String var1);

    public List<IFormCopyInfo> getFormCopyInfoByFormKeys(List<String> var1);

    public List<IFormCopyInfo> getFormCopyInfoBySchemeKey(String var1, String var2);

    public void deleteCopyFormInfo(String var1) throws JQException;

    public void deleteCopyFormInfosbyGroup(String var1) throws JQException;

    public void deleteCopyFormInfos(String var1) throws JQException;

    public List<IFormCopyAttSchemeInfo> getFormCopySchemeInfo(String var1, String var2);

    public void deleteFormCopySchemeInfo(String var1) throws JQException;

    public List<FormCopyRecord> getFormCopyRecords(String var1);

    public void saveFormCopyRecord(FormCopyRecord var1) throws JQException;

    public List<FormCopyRecordPush> getFormCopyPushRecords(String var1);

    public int saveFormCopyPushRecord(FormCopyRecordPush var1) throws JQException;

    public FormulaSyncResultAll doCopy(String var1, String var2, List<FormCopyParams> var3, FormCopyAttSchemeMap var4) throws Exception;

    public FormulaSyncResultAll doSync(String var1, String var2, List<FormSyncParams> var3, FormCopyAttSchemeMap var4, boolean var5, AsyncTaskMonitor var6, double var7, double var9) throws Exception;
}

