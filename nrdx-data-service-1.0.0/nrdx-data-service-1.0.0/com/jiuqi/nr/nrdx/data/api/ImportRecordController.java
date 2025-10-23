/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.domain.Page
 *  com.jiuqi.nr.data.common.service.ImportResultDisplayCollector
 *  com.jiuqi.nr.data.common.service.TaskDataFactoryManager
 *  com.jiuqi.nr.io.record.bean.FormStatisticLog
 *  com.jiuqi.nr.io.record.bean.ImportHistoryVO
 *  com.jiuqi.nr.io.record.bean.ImportLog
 *  com.jiuqi.nr.io.record.bean.UnitFailureRecord
 *  com.jiuqi.nr.io.record.service.FormStatisticService
 *  com.jiuqi.nr.io.record.service.ImportHistoryService
 *  com.jiuqi.nr.io.record.service.UnitFailureService
 *  com.jiuqi.nr.io.tsd.dto.ExportType
 *  com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO
 *  com.jiuqi.nr.transmission.data.service.ISyncHistoryService
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.nrdx.data.api;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.data.common.service.ImportResultDisplayCollector;
import com.jiuqi.nr.data.common.service.TaskDataFactoryManager;
import com.jiuqi.nr.io.record.bean.FormStatisticLog;
import com.jiuqi.nr.io.record.bean.ImportHistoryVO;
import com.jiuqi.nr.io.record.bean.ImportLog;
import com.jiuqi.nr.io.record.bean.UnitFailureRecord;
import com.jiuqi.nr.io.record.service.FormStatisticService;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.io.record.service.UnitFailureService;
import com.jiuqi.nr.io.tsd.dto.ExportType;
import com.jiuqi.nr.nrdx.data.dto.RecordQueryParam;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/importrecord/"})
@Api(tags={"NRDX\u5bfc\u5165\u8bb0\u5f55"})
public class ImportRecordController {
    @Autowired
    private ImportHistoryService importHistoryService;
    @Autowired
    private FormStatisticService formStatisticService;
    @Autowired
    private UnitFailureService unitFailureRecordService;
    @Autowired
    private TaskDataFactoryManager taskDataFactoryManager;
    @Autowired
    private ISyncHistoryService syncHistoryService;

    @GetMapping(value={"query/nrd/result"})
    public String queryNrdResult(@RequestParam(value="recKey") String recKey) {
        SyncHistoryDTO syncHistoryDTO = this.syncHistoryService.get(recKey);
        if (syncHistoryDTO != null && syncHistoryDTO.getDetail() != null) {
            return syncHistoryDTO.getDetail();
        }
        return "";
    }

    @PostMapping(value={"query/history"})
    public Page<ImportHistoryVO> queryImportHistory(@RequestBody RecordQueryParam param) {
        String userId = NpContextHolder.getContext().getUserId();
        return this.importHistoryService.queryByCreator(userId, param.getPage(), param.getSize());
    }

    @GetMapping(value={"query/factory"})
    public List<ExportType> queryFactory(@RequestParam(value="recKey") String recKey) {
        ArrayList<ExportType> exportTypes = new ArrayList<ExportType>();
        List importLogs = this.importHistoryService.getImportLogs(recKey);
        Set factoryIds = importLogs.stream().map(ImportLog::getFactoryId).collect(Collectors.toSet());
        List collectors = this.taskDataFactoryManager.getResultDisplayCollectors(new ArrayList(factoryIds));
        for (ImportResultDisplayCollector collector : collectors) {
            ExportType exportType = new ExportType();
            exportType.setCode(collector.getCode());
            exportType.setName(collector.getName());
            exportType.setDescription(collector.getDescription());
            exportTypes.add(exportType);
        }
        return exportTypes;
    }

    @GetMapping(value={"query/log"})
    public List<ImportLog> queryImportLog(@RequestParam(value="recKey") String recKey) {
        return this.importHistoryService.getImportLogs(recKey);
    }

    @PostMapping(value={"query/log/factory"})
    public List<ImportLog> queryImportLogByFactory(@RequestBody RecordQueryParam param) {
        return this.importHistoryService.getImportLogsByFactory(param.getRecKey(), param.getFactoryId());
    }

    @PostMapping(value={"query/unit/failure"})
    public Page<UnitFailureRecord> queryUnitFailureRecords(@RequestBody RecordQueryParam param) {
        return this.unitFailureRecordService.queryFailureRecords(param.getRecKey(), param.getPage(), param.getSize());
    }

    @PostMapping(value={"query/unit/failure/factory"})
    public Page<UnitFailureRecord> queryUnitFailureRecordsByFactory(@RequestBody RecordQueryParam param) {
        return this.unitFailureRecordService.queryFailureRecordsByFactory(param.getRecKey(), param.getFactoryId(), param.getPage(), param.getSize());
    }

    @PostMapping(value={"query/form/statistic"})
    public Page<FormStatisticLog> queryStatisticLogsByFactory(@RequestBody RecordQueryParam param) {
        return this.formStatisticService.queryStatisticLogs(param.getRecKey(), param.getFactoryId(), param.getPage(), param.getSize());
    }
}

