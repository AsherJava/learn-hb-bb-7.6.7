/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.enums.SourceDataTypeEnum
 *  com.jiuqi.dc.base.common.intf.impl.VchrMasterDim
 *  com.jiuqi.dc.base.common.utils.DataCenterUtil
 *  com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil
 *  com.jiuqi.dc.base.impl.temptable.dao.DcTempCodeDao
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.enums.DataRefFilterType
 *  com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.impl.FieldMappingProviderGather
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 *  com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager
 *  com.jiuqi.dc.taskscheduling.lockmgr.common.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.enums.SourceDataTypeEnum;
import com.jiuqi.dc.base.common.intf.impl.VchrMasterDim;
import com.jiuqi.dc.base.common.utils.DataCenterUtil;
import com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil;
import com.jiuqi.dc.base.impl.temptable.dao.DcTempCodeDao;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.enums.DataRefFilterType;
import com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather;
import com.jiuqi.dc.integration.execute.impl.vchrchange.dao.VchrChangeDao;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDO;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDim;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeHandleResult;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrDeleteDim;
import com.jiuqi.dc.integration.execute.impl.vchrchange.service.VchrChangeHandleService;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.impl.FieldMappingProviderGather;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.core.util.TaskHandlerManager;
import com.jiuqi.dc.taskscheduling.lockmgr.common.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VchrChangeHandleServiceImpl
implements VchrChangeHandleService {
    private static final Logger log = LoggerFactory.getLogger(VchrChangeHandleServiceImpl.class);
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private BizDataRefDefineService defineService;
    @Autowired
    private DataRefConfigureServiceGather dataRefConfigureServiceGather;
    @Autowired
    private TaskManageService taskMgrService;
    @Autowired
    private IPluginTypeGather pluginGather;
    @Autowired
    private FieldMappingProviderGather fieldMappingProviderGather;
    @Autowired
    private VchrChangeDao vchrChangeDao;
    @Autowired
    private TaskHandlerFactory taskHandleFactory;
    @Autowired
    private DcTempCodeDao tempCodeDao;

    @Override
    public List<VchrChangeDim> queryVchrChangeDim(String dataSchemeCode) {
        return this.vchrChangeDao.queryVchrChangeDim(dataSchemeCode, null);
    }

    @Override
    public void beforeHandle(VchrChangeDim param) {
        DataSchemeDTO dataScheme = this.dataSchemeService.findByCode(param.getDataSchemeCode());
        Assert.isNotNull((Object)dataScheme, (String)("\u672a\u627e\u5230\u4ee3\u7801\u4e3a\u3010" + param.getDataSchemeCode() + "\u3011\u7684\u6570\u636e\u65b9\u6848"), (Object[])new Object[0]);
        DataMappingDefineDTO define = this.defineService.getByCode(dataScheme.getCode(), "BizVoucherConvert");
        IFieldMappingProvider provider = this.fieldMappingProviderGather.getProvider(this.pluginGather.getPluginType(define.getPluginType()), "BizVoucherConvert");
        Assert.isNotNull((Object)provider, (String)("\u672a\u627e\u5230\u6570\u636e\u65b9\u6848\u4ee3\u7801\u4e3a\u3010" + dataScheme.getCode() + "\u3011\u7684\u51ed\u8bc1\u5b57\u6bb5\u6620\u5c04\u63d0\u4f9b\u5668"), (Object[])new Object[0]);
    }

    @Override
    public VchrChangeHandleResult handle(VchrChangeDim param) {
        String unitCode = this.getUnitCode(param.getDataSchemeCode(), param.getUnitCode());
        Assert.isNotNull((Object)unitCode, (String)("\u672a\u627e\u5230\u4ee3\u7801\u4e3a\u3010" + param.getUnitCode() + "\u3011\u7684\u4e00\u672c\u8d26\u7ec4\u7ec7\u673a\u6784\u6216\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u6570\u636e"), (Object[])new Object[0]);
        List<String> errorList = this.vchrChangeDao.checkSrcVchrData(param);
        if (!CollectionUtils.isEmpty(errorList)) {
            this.vchrChangeDao.updateCheckDataState(errorList);
            String msg = String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5e74\u5ea6\u3010%2$d\u3011\u5b58\u5728%3$d\u6761\u51ed\u8bc1\u53d8\u66f4\u6570\u636e\u83b7\u53d6\u4e0d\u5230\u5bf9\u5e94\u7684\u4e00\u672c\u8d26\u51ed\u8bc1\u5206\u5f55\uff0c\u5df2\u66f4\u65b0\u53d8\u66f4\u8868\u6570\u636e\u72b6\u6001", unitCode, param.getAcctYear(), errorList.size());
            log.error(msg);
            LogHelper.info((String)DcFunctionModuleEnum.DATAINTEGRATION.getFullModuleName(), (String)"\u51ed\u8bc1\u53d8\u66f4", (String)msg);
        }
        this.taskMgrService.updateBeginHandle(TaskTypeEnum.BIZVOUCHERCONVERT, unitCode);
        List<String> excluldFieldList = Arrays.asList("SRC_SUBJECT_ID", "SUBJECT_SRCCODE", "SUBJECT_SRCNAME", "EXPIREDATE");
        List<DimensionVO> assistDims = AssistDimUtil.listByEffectTable((AssistDimEffectTableEnum)AssistDimEffectTableEnum.DC_VOUCHERITEMASS).stream().filter(e -> !excluldFieldList.contains(e.getCode())).collect(Collectors.toList());
        List cfAssistDims = AssistDimUtil.listByEffectTable((AssistDimEffectTableEnum)AssistDimEffectTableEnum.DC_CFVOUCHERITEMASS);
        StringBuilder log = new StringBuilder();
        this.vchrChangeDao.prepareVchrTemp(param, assistDims);
        List<String> srcVchrIdList = this.vchrChangeDao.querySrcVchrIdList();
        this.vchrChangeDao.updateTempVchrItemAssId(param);
        this.vchrChangeDao.updateDcSrcVchrItemAssId(param);
        List<VchrMasterDim> dims = this.vchrChangeDao.queryVchrPeriodFromTemp();
        int vchrHandleBatchNum = this.taskMgrService.getTaskManageByName(TaskTypeEnum.BIZVOUCHERCONVERT, unitCode).getBatchNum();
        for (VchrMasterDim dim : dims) {
            dim.setBatchNum(Integer.valueOf(++vchrHandleBatchNum));
            dim.setDataSchemeCode(param.getDataSchemeCode());
            this.vchrChangeDao.insertOffsetVchrFromTemp(param, assistDims, dim);
            this.vchrChangeDao.insertCfOffsetVchrFromTemp(param, cfAssistDims, dim);
        }
        this.vchrChangeDao.updateDcCfSrcVchrId(param);
        this.vchrChangeDao.updateVchrChangeInfoOffsetGroupId(param);
        this.vchrChangeDao.updateVchrChangeInfoCreateOffsetVchrFlag(param);
        this.vchrChangeDao.cleanVchrTemp();
        log.append(String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5e74\u5ea6\u3010%2$d\u3011\u51ed\u8bc1\u53d8\u66f4\u6570\u636e\u5904\u7406\u5b8c\u6210\u3002", unitCode, param.getAcctYear()));
        this.taskMgrService.updateEndHandle(TaskTypeEnum.BIZVOUCHERCONVERT, unitCode, vchrHandleBatchNum);
        return new VchrChangeHandleResult(srcVchrIdList, dims, log.toString());
    }

    @Override
    public String resetEtlProcessLog(DataSchemeDTO dataScheme, VchrChangeDim param, List<String> srcVchrIdList) {
        if (SourceDataTypeEnum.ODS_TYPE.getCode().equals(dataScheme.getSourceDataType())) {
            return "\u6570\u636e\u6765\u6e90\u4e3a\u4e00\u672c\u8d26ODS\u6e90\u6570\u636e\u65f6\u4e0d\u9700\u8981\u91cd\u7f6e\u8fc7\u7a0b\u8bb0\u5f55\u8868\u6570\u636e\n";
        }
        if (CollectionUtils.isEmpty(srcVchrIdList)) {
            return "ETL\u8fc7\u7a0b\u8bb0\u5f55\u8868\u6ca1\u6709\u9700\u8981\u91cd\u7f6e\u7684\u6570\u636e\n";
        }
        this.vchrChangeDao.updateEtlProcessLog(param, dataScheme.getDataSourceCode(), srcVchrIdList);
        return "\u8fc7\u7a0b\u8bb0\u5f55\u8868\u6570\u636e\u72b6\u6001\u91cd\u7f6e\u5b8c\u6210\n";
    }

    @Override
    @OuterTransaction
    public String deleteVchrData() {
        StringBuilder log = new StringBuilder();
        List<Integer> yearList = this.vchrChangeDao.queryDeleteVchrYearList();
        if (yearList.isEmpty()) {
            log.append("\u672c\u6b21\u6267\u884c\u6ca1\u6709\u9700\u8981\u5220\u9664\u7684\u51ed\u8bc1\u53d8\u66f4\u6570\u636e");
        }
        for (Integer acctYear : yearList) {
            this.doDeleteByAcctYear(acctYear, log);
        }
        return log.toString();
    }

    @Override
    public void insertDirectVchrChangeDim(List<VchrChangeDO> vchrChangeList) {
        this.vchrChangeDao.insertDirectVchrChangeDim(vchrChangeList);
    }

    @Override
    public List<VchrChangeDim> queryVchrChangeDimByOdsUnitId(String dataSchemeCode, String odsUnitId) {
        return this.vchrChangeDao.queryVchrChangeDim(dataSchemeCode, odsUnitId);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doDeleteByAcctYear(int acctYear, StringBuilder log) {
        List<VchrDeleteDim> deleteDims = this.vchrChangeDao.queryUnCleanOffsetVchrGroupId(acctYear);
        HashMap deleteDimMap = new HashMap();
        for (VchrDeleteDim vchrDeleteDim : deleteDims) {
            if (deleteDimMap.containsKey(vchrDeleteDim.getUnitCode())) {
                Map snMap = (Map)deleteDimMap.get(vchrDeleteDim.getUnitCode());
                if (snMap.containsKey(vchrDeleteDim.getSn())) {
                    ((List)snMap.get(vchrDeleteDim.getSn())).add(vchrDeleteDim.getSrcItemAssId());
                    continue;
                }
                ArrayList<String> srcItemAssIdList = new ArrayList<String>();
                srcItemAssIdList.add(vchrDeleteDim.getSrcItemAssId());
                snMap.put(vchrDeleteDim.getSn(), srcItemAssIdList);
                continue;
            }
            ArrayList<String> srcItemAssIdList = new ArrayList<String>();
            srcItemAssIdList.add(vchrDeleteDim.getSrcItemAssId());
            HashMap<Integer, ArrayList<String>> snMap = new HashMap<Integer, ArrayList<String>>();
            snMap.put(vchrDeleteDim.getSn(), srcItemAssIdList);
            deleteDimMap.put(vchrDeleteDim.getUnitCode(), snMap);
        }
        for (Map.Entry entry : deleteDimMap.entrySet()) {
            for (Map.Entry snEntry : ((Map)entry.getValue()).entrySet()) {
                if (!this.checkCanDelete((String)entry.getKey(), acctYear, (Integer)snEntry.getKey())) {
                    log.append(String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5e74\u5ea6\u3010%2$d\u3011\u6279\u6b21\u3010%3$d\u3011\u540e\u7f6e\u4efb\u52a1\u672a\u52a0\u5de5\u5b8c\u6210\uff0c\u4e0d\u5220\u9664\u51ed\u8bc1\u6570\u636e\n", entry.getKey(), acctYear, snEntry.getKey()));
                    continue;
                }
                try {
                    this.tempCodeDao.batchInsert((List)snEntry.getValue());
                    this.vchrChangeDao.deleteVchr((String)entry.getKey(), acctYear);
                    this.vchrChangeDao.updateVchrChangeInfoVchrCleanFlag();
                    log.append(String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5e74\u5ea6\u3010%2$d\u3011\u6279\u6b21\u3010%3$d\u3011\u51ed\u8bc1\u6570\u636e\u5df2\u5220\u9664\n", entry.getKey(), acctYear, snEntry.getKey()));
                }
                finally {
                    this.tempCodeDao.cleanTemp();
                }
            }
        }
    }

    private boolean checkCanDelete(String unitCode, Integer acctYear, Integer sn) {
        String vchrHandleTask = "BizVoucherConvert";
        List<TaskHandlerVO> postTaskList = this.getPostTask("BizVoucherConvert");
        for (TaskHandlerVO task : postTaskList) {
            int postTaskBatchNum = Optional.ofNullable(this.taskMgrService.getTaskManageByName(task.getName(), unitCode)).map(t -> (int)t.getBatchNum()).orElse((int)sn);
            if (postTaskBatchNum >= sn) continue;
            return false;
        }
        return true;
    }

    private List<TaskHandlerVO> getPostTask(String taskName) {
        List children = TaskHandlerManager.getChildrenTaskHandlerByCode((String)taskName);
        if (children.isEmpty()) {
            return null;
        }
        ArrayList taskList = CollectionUtils.newArrayList();
        for (TaskHandlerVO task : children) {
            if (!this.taskHandleFactory.isEnable(task, taskName, null)) continue;
            taskList.add(task);
            List<TaskHandlerVO> postTaskList = this.getPostTask(task.getName());
            if (postTaskList == null || postTaskList.isEmpty()) continue;
            taskList.addAll(postTaskList);
        }
        return taskList;
    }

    private String getUnitCode(String dataSchemeCode, String changeUnitCode) {
        OrgDO org;
        DataRefListDTO dto = new DataRefListDTO();
        dto.setDataSchemeCode(dataSchemeCode);
        dto.setTableName("MD_ORG");
        dto.setFilterType(DataRefFilterType.HASREF.getCode());
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(dto.getDataSchemeCode());
        List orgRefList = this.dataRefConfigureServiceGather.getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dto).getPageVo().getRows();
        if (!CollectionUtils.isEmpty((Collection)orgRefList)) {
            List orgCodeRef = orgRefList.stream().filter(ref -> changeUnitCode.equals(ref.getOdsCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orgCodeRef)) {
                return ((DataRefDTO)orgCodeRef.get(0)).getCode();
            }
            List orgIdRef = orgRefList.stream().filter(ref -> changeUnitCode.equals(ref.getOdsId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orgIdRef)) {
                return ((DataRefDTO)orgCodeRef.get(0)).getCode();
            }
        }
        return (org = DataCenterUtil.getUnitByCode((String)changeUnitCode)) != null ? org.getCode() : null;
    }
}

