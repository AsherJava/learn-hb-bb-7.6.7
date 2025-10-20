/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping;
import java.util.List;

public interface SameCtrlChgSettingService {
    public void saveOptionData(SameCtrlChagSettingOptionVO var1);

    public SameCtrlChagSettingOptionVO getOptionData(String var1, String var2, String var3);

    public void saveZbAttribute(String var1, String var2, List<SameCtrlChagSettingZbAttributeVO> var3);

    public void saveSchemeMapping(String var1, String var2, List<TaskAndSchemeMapping> var3);

    public void deleteSchemeMappingByIds(List<String> var1);

    public List<TaskAndSchemeMapping> listSchemeMappings(String var1, String var2, String var3);

    public void deleteSubjectMapping(List<String> var1);

    public List<SameCtrlChagSettingZbAttributeVO> listSameCtrlChagSettingZbAttributeVOS(String var1, String var2);

    public void saveSubjectMapping(String var1, String var2, String var3, List<SameCtrlChagSubjectMapVO> var4);

    public List<SameCtrlChagSubjectMapVO> listSubjectMappings(String var1, String var2);

    public boolean enableSameCtr(String var1, String var2);

    public String queryFormDataForSameCtrlSetting(String var1, String var2);
}

