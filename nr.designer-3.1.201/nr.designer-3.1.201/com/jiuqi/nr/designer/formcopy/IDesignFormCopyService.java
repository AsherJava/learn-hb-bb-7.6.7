/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.designer.formcopy;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.designer.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.designer.formcopy.FormCopyParams;
import com.jiuqi.nr.designer.formcopy.FormCopyRecord;
import com.jiuqi.nr.designer.formcopy.FormCopyRecordPush;
import com.jiuqi.nr.designer.formcopy.FormSyncParams;
import com.jiuqi.nr.designer.formcopy.FormulaSyncResultAll;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyInfo;
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

    public FormulaSyncResultAll doCopy(String var1, String var2, List<FormCopyParams> var3, FormCopyAttSchemeMap var4) throws JQException;

    public FormulaSyncResultAll doSync(String var1, String var2, List<FormSyncParams> var3, FormCopyAttSchemeMap var4, boolean var5) throws JQException;
}

