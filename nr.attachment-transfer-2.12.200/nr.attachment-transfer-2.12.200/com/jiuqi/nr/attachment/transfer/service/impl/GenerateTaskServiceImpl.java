/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 */
package com.jiuqi.nr.attachment.transfer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateRecordDao;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateTaskDao;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskExecutor;
import com.jiuqi.nr.attachment.transfer.domain.AttachmentRecordDO;
import com.jiuqi.nr.attachment.transfer.domain.GenerateTaskDO;
import com.jiuqi.nr.attachment.transfer.dto.AttachmentRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateStatusDTO;
import com.jiuqi.nr.attachment.transfer.dto.RecordsQueryDTO;
import com.jiuqi.nr.attachment.transfer.service.IGenerateTaskService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class GenerateTaskServiceImpl
implements IGenerateTaskService {
    private static final Logger log = LoggerFactory.getLogger(GenerateTaskServiceImpl.class);
    @Autowired
    private IGenerateTaskDao generateTaskDao;
    @Autowired
    private IGenerateRecordDao generateRecordDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private TaskExecutor taskExecutor;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<AttachmentRecordDTO> initGenerateInfo(GenerateParamDTO generateParamDTO) {
        String serverId = DistributionManager.getInstance().self().getName();
        GenerateTaskDO query = this.generateTaskDao.query(serverId);
        if (query != null && !query.getServerId().equals(serverId)) {
            throw new RuntimeException("\u5f53\u524d\u8282\u70b9\u4e0d\u53ef\u64cd\u4f5c\u5176\u4ed6\u8282\u70b9\u7684\u4efb\u52a1");
        }
        GenerateTaskDO taskDO = new GenerateTaskDO();
        taskDO.setKey(UUID.randomUUID().toString());
        taskDO.setStatus(0);
        taskDO.setServerId(serverId);
        List<String> entityKeys = generateParamDTO.getEntityKeys();
        if (CollectionUtils.isEmpty(entityKeys) && generateParamDTO.isAllEntity()) {
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            EntityViewDefine dwViewDefine = this.runTimeViewController.getViewByTaskDefineKey(generateParamDTO.getTaskKey());
            iEntityQuery.setEntityView(dwViewDefine);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)generateParamDTO.getPeriod());
            iEntityQuery.setMasterKeys(dimensionValueSet);
            try {
                IEntityTable iEntityTable = iEntityQuery.executeFullBuild((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
                List allRows = iEntityTable.getAllRows();
                Set keySet = allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
                generateParamDTO.setEntityKeys(new ArrayList<String>(keySet));
            }
            catch (Exception e) {
                throw new RuntimeException("\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u65f6\u53d1\u751f\u9519\u8bef");
            }
        }
        try {
            taskDO.setParamConfig(this.mapper.writeValueAsString((Object)generateParamDTO));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<AttachmentRecordDTO> records = this.generateRecords(generateParamDTO);
        taskDO.setTotal(records.size());
        this.generateTaskDao.insert(taskDO);
        return records;
    }

    @Override
    public boolean existRunningTask() {
        String serverId = DistributionManager.getInstance().self().getName();
        GenerateTaskDO query = this.generateTaskDao.query(serverId);
        return query != null && (query.getStatus() == 1 || query.getStatus() == 2);
    }

    @Override
    public List<AttachmentRecordDTO> listRecords(RecordsQueryDTO recordsQueryDTO) {
        int start = (recordsQueryDTO.getPage() - 1) * recordsQueryDTO.getPageSize();
        int endIndex = start + recordsQueryDTO.getPageSize();
        List<AttachmentRecordDO> records = this.generateRecordDao.listByFilter(recordsQueryDTO.getStatus(), recordsQueryDTO.getDownLoad(), recordsQueryDTO.isCurrenPage() ? 0 : -1, start, endIndex);
        ArrayList<AttachmentRecordDTO> recordDTOS = new ArrayList<AttachmentRecordDTO>(records.size());
        records.forEach(e -> recordDTOS.add(AttachmentRecordDTO.getInstance(e)));
        return recordDTOS;
    }

    @Override
    public List<AttachmentRecordDTO> listRecordsByStatus(int ... status) {
        List<AttachmentRecordDO> records = this.generateRecordDao.listByStatus(status);
        ArrayList<AttachmentRecordDTO> recordDTOS = new ArrayList<AttachmentRecordDTO>(records.size());
        records.forEach(e -> recordDTOS.add(AttachmentRecordDTO.getInstance(e)));
        return recordDTOS;
    }

    @Override
    public AttachmentRecordDTO queryRecord(String key) {
        AttachmentRecordDO recordDO = this.generateRecordDao.get(key);
        if (recordDO == null) {
            return null;
        }
        return AttachmentRecordDTO.getInstance(recordDO);
    }

    @Override
    public List<AttachmentRecordDTO> countProcess() {
        List<AttachmentRecordDO> recordDOS = this.generateRecordDao.listByStatus(Constant.GenerateStatus.READY.getStatus(), Constant.GenerateStatus.DOING.getStatus(), Constant.GenerateStatus.SUCCESS.getStatus(), Constant.GenerateStatus.FAIL.getStatus(), Constant.GenerateStatus.DESTROYED.getStatus());
        ArrayList<AttachmentRecordDTO> recordDTOS = new ArrayList<AttachmentRecordDTO>(recordDOS.size());
        recordDOS.forEach(e -> recordDTOS.add(AttachmentRecordDTO.getInstance(e)));
        return recordDTOS;
    }

    @Override
    public GenerateParamDTO getParam() {
        GenerateParamDTO paramDTO;
        String serverId = DistributionManager.getInstance().self().getName();
        GenerateTaskDO query = this.generateTaskDao.query(serverId);
        if (query == null) {
            return null;
        }
        String paramConfig = query.getParamConfig();
        if (!StringUtils.hasText(paramConfig)) {
            return null;
        }
        try {
            paramDTO = (GenerateParamDTO)this.mapper.readValue(paramConfig, GenerateParamDTO.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return paramDTO;
    }

    @Override
    public void reset() {
        String serverId = DistributionManager.getInstance().self().getName();
        this.generateTaskDao.delete(serverId);
        this.generateRecordDao.deleteAll();
    }

    @Override
    public GenerateStatusDTO queryRecords(RecordsQueryDTO recordsQueryDTO) {
        GenerateStatusDTO dto = new GenerateStatusDTO();
        List<AttachmentRecordDTO> recordDTOS = this.listRecords(recordsQueryDTO);
        List<AttachmentRecordDTO> finishedRecords = this.countProcess();
        long finish = finishedRecords.size();
        long canDownLoad = finishedRecords.stream().filter(e -> e.getStatus() == Constant.GenerateStatus.SUCCESS.getStatus()).count();
        dto.setFinish(finish);
        dto.setEnableDownLoad(canDownLoad);
        dto.setRecords(recordDTOS);
        String serverId = DistributionManager.getInstance().self().getName();
        GenerateTaskDO query = this.generateTaskDao.query(serverId);
        if (query != null) {
            GenerateParamDTO generateParamDTO;
            int status = query.getStatus();
            if (status == 1) {
                status = this.taskExecutor.isAlive() ? status : 2;
            }
            String paramConfig = query.getParamConfig();
            try {
                generateParamDTO = (GenerateParamDTO)this.mapper.readValue(paramConfig, GenerateParamDTO.class);
            }
            catch (JsonProcessingException e2) {
                throw new RuntimeException(e2);
            }
            dto.setTotalUnit(generateParamDTO.getEntityKeys().size());
            dto.setTotal(query.getTotal());
            dto.setTaskStatus(status);
        }
        return dto;
    }

    @Override
    public List<AttachmentRecordDO> cleanRecords() {
        List<AttachmentRecordDO> running = this.generateRecordDao.listByStatus(Constant.GenerateStatus.READY.getStatus(), Constant.GenerateStatus.DOING.getStatus(), Constant.GenerateStatus.CANCEL.getStatus());
        String[] runningKeys = (String[])running.stream().map(AttachmentRecordDO::getKey).toArray(String[]::new);
        this.generateRecordDao.batchUpdateStatus(Constant.GenerateStatus.NONE.getStatus(), false, runningKeys);
        List<AttachmentRecordDO> finish = this.generateRecordDao.listByStatus(Constant.GenerateStatus.SUCCESS.getStatus());
        String[] finishKeys = (String[])finish.stream().map(AttachmentRecordDO::getKey).toArray(String[]::new);
        this.generateRecordDao.batchUpdateStatus(Constant.GenerateStatus.DESTROYED.getStatus(), true, finishKeys);
        return finish;
    }

    @Override
    public List<AttachmentRecordDO> cleanRecords(List<String> keys) {
        List<AttachmentRecordDO> list = this.generateRecordDao.list(keys);
        String[] cleanKeys = (String[])list.stream().map(AttachmentRecordDO::getKey).toArray(String[]::new);
        this.generateRecordDao.batchUpdateStatus(Constant.GenerateStatus.DESTROYED.getStatus(), true, cleanKeys);
        return list;
    }

    @Override
    public String queryError(String key) {
        AttachmentRecordDO recordDO = this.generateRecordDao.get(key);
        if (recordDO != null) {
            return recordDO.getContent();
        }
        return null;
    }

    private List<AttachmentRecordDTO> generateRecords(GenerateParamDTO generateParamDTO) {
        ArrayList<AttachmentRecordDTO> recordDTOS = new ArrayList<AttachmentRecordDTO>();
        TaskDefine task = this.runTimeViewController.getTask(generateParamDTO.getTaskKey());
        List<String> entityKeys = generateParamDTO.getEntityKeys();
        int fileSize = (int)Math.ceil((double)entityKeys.size() / (double)generateParamDTO.getUnitPage());
        ArrayList<AttachmentRecordDO> records = new ArrayList<AttachmentRecordDO>();
        for (int i = 1; i <= fileSize; ++i) {
            List<String> pageData = Utils.getCurrentPageData(entityKeys, generateParamDTO.getUnitPage(), i);
            AttachmentRecordDO recordDO = new AttachmentRecordDO();
            recordDO.setKey(UUID.randomUUID().toString());
            recordDO.setStatus(Constant.GenerateStatus.NONE.getStatus());
            recordDO.setFileName(task.getTaskCode() + "-" + generateParamDTO.getPeriod() + "-" + String.format("%05d", i) + ".jio");
            recordDO.setEntityKey(pageData);
            recordDO.setOrder(i);
            records.add(recordDO);
            recordDTOS.add(AttachmentRecordDTO.getInstance(recordDO));
        }
        this.generateRecordDao.insert(records);
        return recordDTOS;
    }
}

