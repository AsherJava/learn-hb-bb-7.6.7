/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils$Relation
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.lease.leasebill.service.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.lease.leasebill.dao.TenantryBillDao;
import com.jiuqi.gcreport.lease.leasebill.service.TenantryBillService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class TenantryBillServiceImpl
implements TenantryBillService {
    @Autowired
    private TenantryBillDao tenantryBillDao;
    @Autowired
    private BillDefineService billDefineService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<Map<String, Object>> listTenantryBills(Map<String, Object> params) {
        int pageSize = (Integer)params.get("pageSize");
        int pageNum = (Integer)params.get("pageNum");
        int offset = (pageNum - 1) * pageSize;
        TempTableCondition tempTableCondition = this.getTempTableCondition(params);
        if (null == tempTableCondition) {
            return PageInfo.of(new ArrayList(), (int)(offset / pageSize + 1), (int)pageSize, (int)0);
        }
        try {
            int totalCount = this.tenantryBillDao.countTenantryBills(tempTableCondition, params);
            if (totalCount <= 0) {
                PageInfo pageInfo = PageInfo.of(new ArrayList(), (int)(offset / pageSize + 1), (int)pageSize, (int)0);
                return pageInfo;
            }
            List<Map<String, Object>> records = this.tenantryBillDao.listTenantryBillsByPaging(tempTableCondition, params);
            InvestBillTool.formatBillContent(records, params, (String)"GC_TENANTRYBILL");
            PageInfo pageInfo = PageInfo.of(records, (int)(offset / pageSize + 1), (int)pageSize, (int)totalCount);
            return pageInfo;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    @Transactional
    public void batchDelete(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List tenantryBills = InvestBillTool.listBillsByIds(ids, (String)"GC_TENANTRYBILL");
        if (CollectionUtils.isEmpty(tenantryBills)) {
            return;
        }
        Map<String, BillModelImpl> defineCode2BillModel = this.getDefineCode2BillModel(tenantryBills);
        for (Map tenantryBill : tenantryBills) {
            String unitCode = (String)tenantryBill.get("UNITCODE");
            String oppUnitCode = (String)tenantryBill.get("OPPUNITCODE");
            String operateTypeTitle = String.format("\u5220\u9664-\u672c\u65b9\u5355\u4f4d%1s-\u5bf9\u65b9\u5355\u4f4d%2s", unitCode, oppUnitCode);
            String billCode = (String)tenantryBill.get("BILLCODE");
            BillModelImpl tenantryModel = defineCode2BillModel.get(tenantryBill.get("DEFINECODE"));
            tenantryModel.deleteByCode(billCode);
            LogHelper.info((String)"\u5408\u5e76-\u51fa\u79df\u65b9\u53f0\u8d26", (String)operateTypeTitle, (String)operateTypeTitle);
        }
    }

    private Map<String, BillModelImpl> getDefineCode2BillModel(List<Map<String, Object>> tenantryBills) {
        List defineCodeList = tenantryBills.stream().map(item -> (String)item.get("DEFINECODE")).collect(Collectors.toList());
        Map<String, BillModelImpl> defineCode2BillModel = defineCodeList.stream().collect(Collectors.toMap(defineCode -> defineCode, defineCode -> {
            BillContextImpl billContext = new BillContextImpl();
            billContext.setTenantName(ShiroUtil.getTenantName());
            billContext.setDisableVerify(true);
            return (BillModelImpl)this.billDefineService.createModel((BillContext)billContext, defineCode);
        }, (v1, v2) -> v1));
        return defineCode2BillModel;
    }

    private TempTableCondition getTempTableCondition(Map<String, Object> params) {
        String mergeUnit = (String)params.get("mergeUnit");
        String periodStr = (String)params.get("periodStr");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(mergeUnit);
        if (null == orgCacheVO) {
            return null;
        }
        List orgIds = orgCenterTool.listAllOrgByParentIdContainsSelf(mergeUnit).stream().map(GcOrgCacheVO::getId).collect(Collectors.toList());
        return SqlUtils.getConditionOfIds(orgIds, (String)" ", (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
    }
}

