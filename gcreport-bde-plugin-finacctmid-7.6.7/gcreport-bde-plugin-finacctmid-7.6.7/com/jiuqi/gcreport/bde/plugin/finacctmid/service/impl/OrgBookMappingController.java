/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.plugin.finacctmid.service.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.plugin.finacctmid.dto.OrgBookMappingDTO;
import com.jiuqi.gcreport.bde.plugin.finacctmid.dto.OrgBookMappingListDTO;
import com.jiuqi.gcreport.bde.plugin.finacctmid.dto.OrgBookMappingUnitTypeDTO;
import com.jiuqi.gcreport.bde.plugin.finacctmid.service.impl.OrgBookMappingRequest;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.common.PageVO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrgBookMappingController {
    private final String ORG_BOOK_MAPPING_API = "/api/gcreport/v1/orgBookMapping";
    @Autowired
    private OrgBookMappingRequest orgBookMappingRequest;
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;

    @GetMapping(value={"/api/gcreport/v1/orgBookMapping/orgInfo/{taskKey}"})
    public BusinessResponseEntity<OrgBookMappingUnitTypeDTO> getOrgInfo(@PathVariable String taskKey) throws JQException {
        TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(taskKey);
        TableModelDefine orgTableModelDefine = this.entityMetaService.getTableModel(taskDefine.getDw());
        return BusinessResponseEntity.ok((Object)new OrgBookMappingUnitTypeDTO(orgTableModelDefine.getName(), this.getCurrentPeriod()));
    }

    @PostMapping(value={"/api/gcreport/v1/orgBookMapping/list"})
    public BusinessResponseEntity<PageVO<OrgBookMappingDTO>> list(@RequestBody OrgBookMappingListDTO dto) {
        return BusinessResponseEntity.ok(this.orgBookMappingRequest.list(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/orgBookMapping/save"})
    public BusinessResponseEntity<Boolean> save(@RequestBody List<OrgBookMappingDTO> orgBookMappingList) {
        return BusinessResponseEntity.ok((Object)this.orgBookMappingRequest.save(orgBookMappingList));
    }

    private String getCurrentPeriod() {
        LocalDate date = LocalDate.now();
        PeriodWrapper periodWrapper = new PeriodWrapper(date.getYear(), 4, date.getMonthValue());
        return periodWrapper.toString();
    }
}

