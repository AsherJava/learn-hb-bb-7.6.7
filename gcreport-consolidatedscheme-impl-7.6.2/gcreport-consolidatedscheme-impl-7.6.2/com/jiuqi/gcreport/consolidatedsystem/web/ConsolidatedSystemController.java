/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedSystemChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedSystemChangedEvent$ConsolidatedSystemChangedInfo
 *  com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedSystemChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.sf.anno.Licence;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.base")
public class ConsolidatedSystemController
implements ConsolidatedSystemClient {
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ConsolidatedSubjectService subjectService;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<ConsolidatedSystemVO> getConsolidatedSystem(@PathVariable(value="id") String id) {
        ConsolidatedSystemVO consolidatedSystemVO = this.consolidatedSystemService.getConsolidatedSystemVO(id);
        return BusinessResponseEntity.ok((Object)consolidatedSystemVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<ConsolidatedSystemVO>> getConsolidatedSystems(@RequestParam(name="year", required=false) Integer year) {
        List<ConsolidatedSystemVO> consolidatedSystemVOS = this.consolidatedSystemService.getConsolidatedSystemVOS(year);
        return BusinessResponseEntity.ok(consolidatedSystemVOS);
    }

    public BusinessResponseEntity<List<ConsolidatedSystemVO>> getConsolidatedSystemsByTaskId(@PathVariable(value="taskId") String taskId) {
        List<ConsolidatedSystemVO> consolidatedSystemVOS = this.consolidatedSystemService.getConsolidatedSystemVOSByTaksId(taskId);
        return BusinessResponseEntity.ok(consolidatedSystemVOS);
    }

    public BusinessResponseEntity<List<ConsolidatedSubjectVO>> listAllSubjectBySystemId(String systemId) {
        List<ConsolidatedSubjectEO> allSubjects = this.subjectService.listAllSubjectsBySystemId(systemId);
        ArrayList<ConsolidatedSubjectVO> allSubjectVOs = new ArrayList<ConsolidatedSubjectVO>();
        for (ConsolidatedSubjectEO eo : allSubjects) {
            ConsolidatedSubjectVO subjectVO = SubjectConvertUtil.convertEO2VO(eo);
            subjectVO.setCreatetime(null);
            subjectVO.setModifiedtime(null);
            allSubjectVOs.add(subjectVO);
        }
        return BusinessResponseEntity.ok(allSubjectVOs);
    }

    public BusinessResponseEntity<String> addConsolidatedSystem(@Valid @RequestBody ConsolidatedSystemVO consolidatedSystemVO) {
        String id = this.consolidatedSystemService.addConsolidatedSystem(consolidatedSystemVO);
        this.afterConsolidatedSystemSave(consolidatedSystemVO);
        return BusinessResponseEntity.ok((Object)id);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> editConsolidatedSystem(@PathVariable(value="id") String id, @Valid @RequestBody ConsolidatedSystemVO consolidatedSystemVO) {
        this.consolidatedSystemService.editConsolidatedSystem(id, consolidatedSystemVO);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f\uff01");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteConsolidatedSystem(@PathVariable(value="id") String id) {
        this.consolidatedSystemService.deleteConsolidatedSystem(id);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f\uff01");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> handleConsolidatedSystem(@PathVariable(value="id") String id, @PathVariable(value="action") String action) {
        this.consolidatedSystemService.handleConsolidatedSystem(id, action);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f\uff01");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<SelectOptionVO>> getFormualSchemes(String systemId) {
        return BusinessResponseEntity.ok(this.consolidatedSystemService.getFormualSchemes(systemId));
    }

    public BusinessResponseEntity<Boolean> isCorporate(String taskId, String periodStr, String orgType) {
        return BusinessResponseEntity.ok((Object)this.consolidatedTaskService.isCorporate(taskId, periodStr, orgType));
    }

    private void afterConsolidatedSystemSave(ConsolidatedSystemVO consolidatedSystemVO) {
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent((ApplicationEvent)new ConsolidatedSystemChangedEvent(new ConsolidatedSystemChangedEvent.ConsolidatedSystemChangedInfo(consolidatedSystemVO.getDataSchemeKey()), context));
    }
}

