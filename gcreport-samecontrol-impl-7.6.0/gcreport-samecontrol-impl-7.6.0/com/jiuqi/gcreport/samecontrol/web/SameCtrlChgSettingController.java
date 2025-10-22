/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlChgSettingClient
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlChgSettingClient;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgSettingService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingOptionVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSettingZbAttributeVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.SameCtrlChagSubjectMapVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlsetting.TaskAndSchemeMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class SameCtrlChgSettingController
implements SameCtrlChgSettingClient {
    @Autowired
    private SameCtrlChgSettingService sameCtrlChgSettingService;

    public BusinessResponseEntity<String> saveOptionData(SameCtrlChagSettingOptionVO optionVO) {
        this.sameCtrlChgSettingService.saveOptionData(optionVO);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f!");
    }

    public BusinessResponseEntity<SameCtrlChagSettingOptionVO> getOptionData(String taskId, String schemeId, String systemId) {
        return BusinessResponseEntity.ok((Object)this.sameCtrlChgSettingService.getOptionData(taskId, schemeId, systemId));
    }

    public BusinessResponseEntity<String> saveZbAttribute(String taskId, String schemeId, List<SameCtrlChagSettingZbAttributeVO> zbAttributes) {
        this.sameCtrlChgSettingService.saveZbAttribute(taskId, schemeId, zbAttributes);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f!");
    }

    public BusinessResponseEntity<String> saveSchemeMapping(String taskId, String schemeId, List<TaskAndSchemeMapping> taskAndSchemeMappings) {
        this.sameCtrlChgSettingService.saveSchemeMapping(taskId, schemeId, taskAndSchemeMappings);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f!");
    }

    public BusinessResponseEntity<String> deleteSchemeMappingByIds(List<String> deleteIds) {
        this.sameCtrlChgSettingService.deleteSchemeMappingByIds(deleteIds);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f!");
    }

    public BusinessResponseEntity<List<TaskAndSchemeMapping>> listSchemeMappings(String taskId, String schemeId, String systemId) {
        return BusinessResponseEntity.ok(this.sameCtrlChgSettingService.listSchemeMappings(taskId, schemeId, systemId));
    }

    public BusinessResponseEntity<String> deleteSubjectMappings(List<String> deleteIds) {
        this.sameCtrlChgSettingService.deleteSubjectMapping(deleteIds);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f!");
    }

    public BusinessResponseEntity<String> saveSubjectMapping(String schemeMappingId, String taskId, String schemeId, List<SameCtrlChagSubjectMapVO> subjectMappings) {
        this.sameCtrlChgSettingService.saveSubjectMapping(schemeMappingId, taskId, schemeId, subjectMappings);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f!");
    }

    public BusinessResponseEntity<List<SameCtrlChagSubjectMapVO>> listSubjectMappings(String schemeMappingId, String systemId) {
        return BusinessResponseEntity.ok(this.sameCtrlChgSettingService.listSubjectMappings(schemeMappingId, systemId));
    }

    public BusinessResponseEntity<Boolean> getEnableSameCtrFlag(String taskId, String schemeId) {
        return BusinessResponseEntity.ok((Object)this.sameCtrlChgSettingService.enableSameCtr(taskId, schemeId));
    }

    public BusinessResponseEntity<String> queryFormDataForSameCtrlSetting(String schemeId, String formKey) {
        return BusinessResponseEntity.ok((Object)this.sameCtrlChgSettingService.queryFormDataForSameCtrlSetting(schemeId, formKey));
    }
}

