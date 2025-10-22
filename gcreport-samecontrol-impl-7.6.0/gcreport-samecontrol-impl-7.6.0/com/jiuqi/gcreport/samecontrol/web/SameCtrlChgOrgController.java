/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlChgOrgClient
 *  com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlChgOrgClient;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import com.jiuqi.nvwa.sf.anno.Licence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
@Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.samecontrol")
public class SameCtrlChgOrgController
implements SameCtrlChgOrgClient {
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;

    public BusinessResponseEntity<List<SameCtrlChgOrgVO>> queryChangedOrgs(ChangeOrgCondition condition) {
        return BusinessResponseEntity.ok(this.sameCtrlChgOrgService.listSameCtrlChgOrgs(condition));
    }

    public BusinessResponseEntity<String> addChangedOrg(ChangeOrgCondition changeOrgCondition) {
        this.sameCtrlChgOrgService.addChangedOrg(changeOrgCondition);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f!");
    }

    public BusinessResponseEntity<List<GcOrgCacheVO>> getDisposeAndAcquisitionOrg(ChangeOrgCondition condition) {
        return BusinessResponseEntity.ok(this.sameCtrlChgOrgService.getDisposeAndAcquisitionOrg(condition));
    }

    public BusinessResponseEntity<String> changedCtrlOrgsState(List<String> deleteIds) {
        this.sameCtrlChgOrgService.changedCtrlOrgsState(deleteIds);
        return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f\uff01");
    }

    public BusinessResponseEntity<String> autoCreateSameCtrlChgOrg(String orgType, String periodStr) {
        this.sameCtrlChgOrgService.autoCreateSameCtrlChgOrg(orgType, periodStr);
        return BusinessResponseEntity.ok((Object)"\u6267\u884c\u6210\u529f!");
    }

    public BusinessResponseEntity<String> getOrgversionByChangeDate(Map<String, Object> params) {
        try {
            Integer periodType = (Integer)params.get("periodType");
            String changeDate = (String)params.get("changeDate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(format.parse(changeDate));
            YearPeriodDO yearPeriodVer = YearPeriodUtil.transform(null, (int)periodType, (Calendar)calendar);
            String periodStr = new YearPeriodObject(null, yearPeriodVer.getYear(), yearPeriodVer.getType(), yearPeriodVer.getPeriod()).formatYP().toString();
            return BusinessResponseEntity.ok((Object)periodStr);
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u5355\u4f4d\u7248\u672c\u5f02\u5e38\u3002");
        }
    }

    public BusinessResponseEntity<List<GcBaseData>> listVirtualOrgTypeBaseData() {
        return BusinessResponseEntity.ok(this.sameCtrlChgOrgService.listVirtualOrgTypeBaseData());
    }

    public BusinessResponseEntity<String> addManageChangedOrg(ChangeOrgCondition changeOrgCondition) {
        this.sameCtrlChgOrgService.addManageChangedOrg(changeOrgCondition);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<String> updateManageChangedOrg(ChangeOrgCondition changeOrgCondition) {
        this.sameCtrlChgOrgService.updateManageChangedOrg(changeOrgCondition);
        return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f");
    }

    public BusinessResponseEntity<String> deleteManageChangedOrgByMRecid(List<String> mRecid) {
        this.sameCtrlChgOrgService.deleteManageChangedOrgByMRecid(mRecid);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<String> deleteManageChangedOrg(String id) {
        this.sameCtrlChgOrgService.deleteManageChangedOrg(id);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<List<SameCtrlChgOrgVO>> listManageChangedOrg(ChangeOrgCondition changeOrgCondition) {
        return BusinessResponseEntity.ok(this.sameCtrlChgOrgService.listManageChangedOrg(changeOrgCondition));
    }
}

