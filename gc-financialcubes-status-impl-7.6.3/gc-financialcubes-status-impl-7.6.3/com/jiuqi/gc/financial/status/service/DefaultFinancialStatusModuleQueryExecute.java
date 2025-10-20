/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO
 *  com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData
 *  com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin
 *  com.jiuqi.gc.financial.status.intf.IFinancialStatusModuleQueryExecute
 *  com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleGather
 *  com.jiuqi.gc.financial.status.vo.FinancialStatusQueryParam
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.gc.financial.status.service;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gc.financial.status.dao.DefaultFinancialGroupStatusQueryDao;
import com.jiuqi.gc.financial.status.dao.DefaultFinancialUnitStatusQueryDao;
import com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO;
import com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModuleQueryExecute;
import com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleGather;
import com.jiuqi.gc.financial.status.vo.FinancialStatusQueryParam;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultFinancialStatusModuleQueryExecute
implements IFinancialStatusModuleQueryExecute {
    public static final String EXECUTE_NAME = "DefaultFinancialStatusModuleQueryExecute";
    private final String CACHE_NAME = "FinancialStatusCaChe";
    private final String FINANCIAL_GROUP_CACHE_PREFIX = "financialGroup_";
    private final String FINANCIAL_UNIT_CACHE_PREFIX = "financialUnit_";
    @Autowired
    private DefaultFinancialUnitStatusQueryDao unitStatusQueryDao;
    @Autowired
    private DefaultFinancialGroupStatusQueryDao groupStatusQueryDao;
    @Autowired
    private NedisCacheManager cacheManger;
    @Autowired
    private IFinancialStatusModuleGather moduleGather;

    public String getExecuteName() {
        return EXECUTE_NAME;
    }

    public List<FinancialGroupStatusDTO> listFinancialGroupStatusData(FinancialStatusQueryParam param) {
        List allFinancialGroupStatusDTOList = (List)this.cacheManger.getCache("FinancialStatusCaChe").get("financialGroup_" + param.getModuleCode(), () -> this.groupStatusQueryDao.listAllFinancialGroupStatusDataByModuleCode(param.getModuleCode()));
        return allFinancialGroupStatusDTOList.stream().filter(item -> {
            if (!StringUtils.isEmpty((String)param.getPeriodType()) && !item.getPeriodType().equals(param.getPeriodType())) {
                return false;
            }
            return StringUtils.isEmpty((String)param.getDataTime()) || item.getDataTime().equals(param.getDataTime());
        }).collect(Collectors.toList());
    }

    public List<FinancialUnitStatusDTO> listFinancialUnitStatusData(FinancialStatusQueryParam param) {
        Assert.isNotEmpty((String)param.getDataTime(), (String)"\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isFalse((boolean)CollectionUtils.isEmpty((Collection)param.getUnitCodeSet()), (String)"\u67e5\u8be2\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Map unitCodeToStatusMap = (Map)this.cacheManger.getCache("FinancialStatusCaChe").get("financialUnit_" + param.getModuleCode() + "_" + param.getDataTime(), () -> this.loadDataFromDb(param.getModuleCode(), param.getDataTime()));
        IFinancialStatusModulePlugin modulePlugin = this.moduleGather.getPluginByModuleCode(param.getModuleCode());
        ArrayList<FinancialUnitStatusDTO> result = new ArrayList<FinancialUnitStatusDTO>();
        for (String unitCode : param.getUnitCodeSet()) {
            if (!unitCodeToStatusMap.containsKey(unitCode)) {
                FinancialUnitStatusDTO defaultItem = new FinancialUnitStatusDTO();
                defaultItem.setUnitCode(unitCode);
                defaultItem.setDataTime(param.getDataTime());
                defaultItem.setStatus(modulePlugin.isDefaultOpen() ? FinancialStatusEnum.OPEN : FinancialStatusEnum.CLOSE);
                result.add(defaultItem);
                continue;
            }
            FinancialUnitStatusDTO item = (FinancialUnitStatusDTO)unitCodeToStatusMap.get(unitCode);
            if (item.getInvalidTime() != null && item.getInvalidTime().getTime() < System.currentTimeMillis()) {
                item.setStatus(FinancialStatusEnum.CLOSE);
            }
            result.add(item);
        }
        return result;
    }

    private Map<String, FinancialUnitStatusDTO> loadDataFromDb(String moduleCode, String dataTime) {
        List<FinancialUnitStatusDTO> unitStatusDTOList = this.unitStatusQueryDao.listFinancialUnitStatusData(moduleCode, dataTime);
        if (CollectionUtils.isEmpty(unitStatusDTOList)) {
            return CollectionUtils.newHashMap();
        }
        return unitStatusDTOList.stream().collect(Collectors.toMap(FinancialUnitStatusDTO::getUnitCode, item -> item, (v1, v2) -> v1));
    }

    public void syncGroupCache(FinancialStatusChangeEventData eventData) {
        this.cacheManger.getCache("FinancialStatusCaChe").evict("financialGroup_" + eventData.getModuleCode());
    }

    public void syncUnitCache(FinancialStatusChangeEventData eventData) {
        this.cacheManger.getCache("FinancialStatusCaChe").evict("financialUnit_" + eventData.getModuleCode() + "_" + eventData.getDataTime());
    }
}

