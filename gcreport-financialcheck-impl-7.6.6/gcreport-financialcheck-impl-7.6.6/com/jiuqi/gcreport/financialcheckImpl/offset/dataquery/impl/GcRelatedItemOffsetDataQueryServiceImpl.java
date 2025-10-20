/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  com.jiuqi.nvwa.certification.manage.impl.NvwaAppRequestManageImpl
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.GcRelatedItemOffsetDataQueryService;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.GcRelatedOffsetVoucherItemService;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import com.jiuqi.nvwa.certification.manage.impl.NvwaAppRequestManageImpl;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GcRelatedItemOffsetDataQueryServiceImpl
implements GcRelatedItemOffsetDataQueryService {
    @Autowired
    private GcRelatedItemQueryService itemQueryService;
    @Autowired
    private OffsetRelatedItemDao offsetRelatedItemDao;
    @Autowired
    private NvwaAppRequestManageImpl nvwaAppRequestManage;
    @Autowired
    private GcRelatedOffsetVoucherItemService offsetVoucherItemService;

    @Override
    public List<GcRelatedItemEO> relationToMergeExecute(RelationToMergeArgDTO relationToMergeArgDTO) {
        List<String> ids;
        Set<String> notOffsetRelatedIds;
        BalanceCondition balanceCondition = new BalanceCondition();
        balanceCondition.setOrgType(relationToMergeArgDTO.getOrgType());
        balanceCondition.setOrgId(relationToMergeArgDTO.getOrgCode());
        balanceCondition.setAcctYear(relationToMergeArgDTO.getAcctYear());
        balanceCondition.setAcctPeriod(relationToMergeArgDTO.getAcctPeriod());
        balanceCondition.setPeriodType(relationToMergeArgDTO.getPeriodType());
        balanceCondition.setCurrency(relationToMergeArgDTO.getCurrency());
        balanceCondition.setChecked(relationToMergeArgDTO.isChecked());
        balanceCondition.setBoundSubjects(relationToMergeArgDTO.getBoundSubjects());
        ReconciliationModeEnum checkWay = FinancialCheckConfigUtils.getCheckWay();
        balanceCondition.setCheckWay(checkWay == null ? null : checkWay.getCode());
        List itemS = this.itemQueryService.queryByOffsetCondition(balanceCondition);
        if (!CollectionUtils.isEmpty(itemS) && !CollectionUtils.isEmpty(notOffsetRelatedIds = this.offsetRelatedItemDao.listOffsetDataRelatedIds(ids = itemS.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()), relationToMergeArgDTO.getSystemId(), relationToMergeArgDTO.getCurrency(), relationToMergeArgDTO.getPeriodScheme()))) {
            itemS = itemS.stream().filter(x -> !notOffsetRelatedIds.contains(x.getId())).collect(Collectors.toList());
        }
        return itemS;
    }

    @Override
    public List<GcRelatedOffsetVoucherItemEO> queryOffsetVoucherItems(BalanceCondition balanceCondition) {
        List<Object> offsetVchrItems = this.offsetVoucherItemService.queryByOffsetCondition(balanceCondition);
        if (!CollectionUtils.isEmpty(offsetVchrItems)) {
            List<String> ids = offsetVchrItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
            Set<String> notOffsetRelatedIds = ((OffsetRelatedItemDao)SpringContextUtils.getBean(OffsetRelatedItemDao.class)).listOffsetDataRelatedIds(ids, balanceCondition.getSystemId(), balanceCondition.getCurrency(), balanceCondition.getPeriodStr());
            if (!CollectionUtils.isEmpty(notOffsetRelatedIds)) {
                offsetVchrItems = offsetVchrItems.stream().filter(x -> !notOffsetRelatedIds.contains(x.getId())).collect(Collectors.toList());
            }
        }
        return offsetVchrItems;
    }
}

