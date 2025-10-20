/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.intf.impl.DcBaseTaskHandler
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.mq;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.integration.execute.impl.basedatasync.enums.BaseDataSyncTypeEnum;
import com.jiuqi.dc.integration.execute.impl.basedatasync.enums.BaseDataTypeEnum;
import com.jiuqi.dc.integration.execute.impl.basedatasync.mq.BaseDataSyncParam;
import com.jiuqi.dc.integration.execute.impl.basedatasync.service.DcBaseDataSyncService;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.intf.impl.DcBaseTaskHandler;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataSyncTaskHandler
extends DcBaseTaskHandler {
    public static final String BASE_DATA_SYNC_TASK = "BaseDataSync";
    @Autowired
    private DcBaseDataSyncService baseDataSyncService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private BaseDataChangeInfoDao baseDataChangeInfoDao;
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    public String getName() {
        return BASE_DATA_SYNC_TASK;
    }

    public String getTitle() {
        return "\u3010\u57fa\u7840\u6570\u636e\u3011\u57fa\u7840\u6570\u636e\u540c\u6b65";
    }

    public String getPreTask() {
        return null;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.LEVEL;
    }

    public Map<String, String> getHandleParams(String preParam) {
        BaseDataSyncParam executeParam = (BaseDataSyncParam)JsonUtils.readValue((String)preParam, BaseDataSyncParam.class);
        HashMap handleParams = CollectionUtils.newHashMap();
        if (CollectionUtils.isEmpty(executeParam.getOrgTypeList()) && CollectionUtils.isEmpty(executeParam.getBaseDataCodeList())) {
            List dimensionVOS = this.dimensionService.loadAllDimensions();
            Set baseCodeSet = dimensionVOS.stream().map(DimensionVO::getReferField).filter(referField -> !StringUtils.isEmpty((String)referField)).collect(Collectors.toSet());
            baseCodeSet.addAll(CollectionUtils.newArrayList((Object[])new String[]{"MD_ACCTSUBJECT", "MD_CURRENCY", "MD_CFITEM", "MD_GCORGTYPE", "MD_AUDITTRAIL", "MD_AGING"}));
            List unSyncBaseCodeList = this.baseDataChangeInfoDao.getUnSyncBaseCodeList();
            HashSet syncBaseCodeSet = CollectionUtils.newHashSet();
            syncBaseCodeSet.addAll(baseCodeSet.stream().filter(unSyncBaseCodeList::contains).collect(Collectors.toSet()));
            for (String baseCode : syncBaseCodeSet) {
                handleParams.put(JsonUtils.writeValueAsString((Object)new BaseDataSyncParam(baseCode, executeParam.getSyncType(), BaseDataTypeEnum.BASEDATA.getCode())), baseCode);
            }
            PageVO result = this.orgCategoryClient.list(new OrgCategoryDO());
            if (!CollectionUtils.isEmpty((Collection)result.getRows())) {
                result.getRows().forEach(orgType -> {
                    if (Arrays.asList("MD_ORG", "MD_ORG_CORPORATE", "MD_ORG_MANAGEMENT").contains(orgType.getName())) {
                        handleParams.put(JsonUtils.writeValueAsString((Object)new BaseDataSyncParam(orgType.getName(), BaseDataSyncTypeEnum.ALL.getCode(), BaseDataTypeEnum.ORG.getCode())), orgType.getName());
                    }
                });
            }
            return handleParams;
        }
        if (!CollectionUtils.isEmpty(executeParam.getOrgTypeList())) {
            for (String baseCode : executeParam.getOrgTypeList()) {
                handleParams.put(JsonUtils.writeValueAsString((Object)new BaseDataSyncParam(baseCode, BaseDataSyncTypeEnum.ALL.getCode(), BaseDataTypeEnum.ORG.getCode())), baseCode);
            }
        }
        HashSet baseCodeSet = CollectionUtils.newHashSet();
        if (!CollectionUtils.isEmpty(executeParam.getBaseDataCodeList())) {
            if (BaseDataSyncTypeEnum.ADD.getCode().equals(executeParam.getSyncType())) {
                List unSyncBaseCodeList = this.baseDataChangeInfoDao.getUnSyncBaseCodeList();
                baseCodeSet.addAll(executeParam.getBaseDataCodeList().stream().filter(unSyncBaseCodeList::contains).collect(Collectors.toSet()));
            } else {
                baseCodeSet.addAll(executeParam.getBaseDataCodeList());
            }
        }
        if (CollectionUtils.isEmpty((Collection)baseCodeSet)) {
            return handleParams;
        }
        for (String baseCode : baseCodeSet) {
            handleParams.put(JsonUtils.writeValueAsString((Object)new BaseDataSyncParam(baseCode, executeParam.getSyncType(), BaseDataTypeEnum.BASEDATA.getCode())), baseCode);
        }
        return handleParams;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        BaseDataSyncParam executeParam = (BaseDataSyncParam)JsonUtils.readValue((String)param, BaseDataSyncParam.class);
        String log = this.baseDataSyncService.doSync(executeParam);
        TaskHandleResult result = new TaskHandleResult();
        result.appendLog(log);
        return result;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.NEW;
    }

    public IDimType getDimType() {
        return DimType.BASEDATADEFINE;
    }

    public String getSpecialQueueFlag() {
        return "baseDataSyncTask";
    }
}

