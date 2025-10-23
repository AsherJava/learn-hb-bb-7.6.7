/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.mapping2.bean.PeriodMapping
 *  com.jiuqi.nr.mapping2.service.MappingTransferService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.bean.OrgMapping
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nrdt.unitdownload.web;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nrdt.unitdownload.common.FMDMTransferDTO;
import com.jiuqi.nrdt.unitdownload.service.UnitDownloadService;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.bean.OrgMapping;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import com.jiuqi.va.domain.org.OrgVersionDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/net/unitDownload"})
public class UnitDownloadController {
    private static final Logger logger = LoggerFactory.getLogger(UnitDownloadController.class);
    @Autowired
    UnitDownloadService unitDownloadService;
    @Autowired
    private IMappingSchemeService mappingSchemeService;
    @Autowired
    private MappingTransferService mappingTransferService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodMappingService periodMappingService;
    @Autowired
    private IOrgMappingService orgMappingService;
    @Autowired
    private PeriodEngineService periodEngineService;

    @PostMapping(value={"/taskInfo"})
    public Map<String, String> getTaskInfo(@RequestParam(value="taskCode") String taskCode, @RequestParam(value="formSchemeKey") String formSchemeKey) {
        return this.unitDownloadService.getTaskInfo(taskCode, formSchemeKey);
    }

    @PostMapping(value={"/unitData"})
    public List<FMDMTransferDTO> getUnitData(@RequestParam(value="taskCode") String taskCode, @RequestParam(value="period") String period, @RequestBody List<String> unitKeys) {
        return this.unitDownloadService.getUnitData(taskCode, period, unitKeys);
    }

    @PostMapping(value={"/orgInfo"})
    public List<String> getOrgNotNullAttribute(@RequestParam(value="category") String category) {
        return this.unitDownloadService.getOrgNotNullAttribute(category);
    }

    @PostMapping(value={"/orgData"})
    public Map<Integer, List<Map<String, Object>>> getOrgData(@RequestParam(value="taskCode") String taskCode, @RequestParam(value="period") String period, @RequestBody List<String> unitKeys) {
        return this.unitDownloadService.getOrgData(taskCode, period, unitKeys);
    }

    @PostMapping(value={"/orgVersion"})
    public List<OrgVersionDO> getOrgVersion(@RequestParam(value="taskCode") String taskCode) {
        return this.unitDownloadService.getOrgVersion(taskCode);
    }

    @GetMapping(value={"/getMappingScheme"})
    public List<MappingScheme> getMappingScheme(@RequestParam String taskKey, @RequestParam String taskCode, @RequestParam(required=false) String period) {
        try {
            if (!StringUtils.hasText(period)) {
                TaskDefine taskDefine = this.matchTask(taskKey, taskCode);
                return this.mappingTransferService.getMappingSchemeByTask(taskDefine.getKey());
            }
            String formSchemeKey = this.getFormSchemeKey(taskKey, taskCode, period);
            return this.mappingTransferService.getMappingSchemeByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<MappingScheme>();
        }
    }

    @GetMapping(value={"/matchTask"})
    public TaskDefine matchTask(@RequestParam String taskKey, @RequestParam String taskCode) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        if (task != null) {
            return task;
        }
        return this.runTimeViewController.getTaskByCode(taskCode);
    }

    @GetMapping(value={"/getFormScheme"})
    public String getFormSchemeKey(@RequestParam String taskKey, @RequestParam String taskCode, @RequestParam String period) {
        TaskDefine taskDefine = this.matchTask(taskKey, taskCode);
        if (taskDefine == null) {
            return null;
        }
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
        if (schemePeriodLinkDefine != null) {
            return schemePeriodLinkDefine.getSchemeKey();
        }
        return null;
    }

    @GetMapping(value={"/getPeriodMappings"})
    public List<PeriodMapping> getPeriodMappings(@RequestParam String mappingSchemeKey) {
        return this.periodMappingService.findByMS(mappingSchemeKey);
    }

    @GetMapping(value={"/getOrgMappings"})
    public List<OrgMapping> getOrgMappings(@RequestParam String mappingSchemeKey) {
        return this.orgMappingService.getOrgMappingByMS(mappingSchemeKey);
    }

    @GetMapping(value={"getPeriodRegion"})
    public String[] periodDateRegion(@RequestParam String periodKey) {
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodKey);
        return periodProvider.getPeriodCodeRegion();
    }
}

