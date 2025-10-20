/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formsetting.vo.FormSettingVO
 *  com.jiuqi.gcreport.formsetting.vo.SettingVO
 *  com.jiuqi.np.definition.facade.TableDefine
 */
package com.jiuqi.gcreport.formsetting.service;

import com.jiuqi.gcreport.formsetting.vo.FormSettingVO;
import com.jiuqi.gcreport.formsetting.vo.SettingVO;
import com.jiuqi.np.definition.facade.TableDefine;

public interface FormSettingService {
    public String createForm(FormSettingVO var1);

    public void setForm(SettingVO var1);

    public TableDefine queryOwnTable(String var1);
}

