/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.org.api.intf.GcOrgTypeClient
 *  com.jiuqi.gcreport.org.api.intf.GcOrgVerClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.org.impl.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.org.api.intf.GcOrgTypeClient;
import com.jiuqi.gcreport.org.api.intf.GcOrgVerClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@PlanTaskRunner(id="552CF3EE4AE84DB7B53F118B7EC742FA", settingPage="unitverAdvanceConfig", name="com.jiuqi.gcreport.org.nr.task.AutoCreateMergeUnitVersionRunner", title="\u81ea\u52a8\u521b\u5efa\u5355\u4f4d\u7248\u672c\u8ba1\u5212\u4efb\u52a1")
public class AutoCreateMergeUnitVersionRunner
extends Runner {
    public static final String MONTH_CHAR_BY_DASH = DateCommonFormatEnum.MONTH_CHAR_BY_DASH.getFormat();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean excute(String runnerParameter) {
        JsonNode paramJson = JsonUtils.readTree((String)runnerParameter);
        if (paramJson == null) {
            this.appendLog("\u672a\u8bbe\u7f6e\u9ad8\u7ea7\u53c2\u6570");
            return false;
        }
        int failCount = 0;
        ArrayList orgTypeArr = new ArrayList();
        List orgTypeList = paramJson.findValues("orgTypeList");
        orgTypeList.forEach(jsonNode -> jsonNode.forEach(node -> orgTypeArr.add(node.asText())));
        if (orgTypeArr.isEmpty()) {
            this.appendLog("\u672a\u83b7\u53d6\u5230\u5355\u4f4d\u7c7b\u578b");
            return false;
        }
        try {
            GcOrgVerClient gcOrgVerTool = (GcOrgVerClient)SpringBeanUtils.getBean(GcOrgVerClient.class);
            ShiroUtil.bindTenantName((String)"__default_tenant__");
            Date nowDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            Date validTime = DateUtils.firstDateOf((int)calendar.get(1), (int)(calendar.get(2) + 1));
            String orgVerCode = DateUtils.format((Date)validTime, (String)"yyyyMM");
            GcOrgTypeClient gcOrgTypeTool = (GcOrgTypeClient)SpringBeanUtils.getBean(GcOrgTypeClient.class);
            List typeList = (List)gcOrgTypeTool.queryAllUnitType().getData();
            if (typeList == null || typeList.isEmpty()) {
                this.appendLog("\u672a\u83b7\u53d6\u5230\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
                boolean bl = false;
                return bl;
            }
            for (OrgTypeVO orgType : typeList) {
                if (!orgTypeArr.contains(orgType.getName())) continue;
                GcOrgVerApiParam param = new GcOrgVerApiParam();
                param.setOrgType(orgType.getName());
                List versions = (List)gcOrgVerTool.queryAllOrgVersion(param).getData();
                List selectVers = versions.stream().filter(v -> !v.getValidTime().after(validTime) && v.getInvalidTime().after(validTime)).collect(Collectors.toList());
                OrgVersionVO curMonthVer = (OrgVersionVO)selectVers.get(0);
                int currMonth = DateUtils.getDateFieldValue((Date)validTime, (int)2);
                int verMonth = DateUtils.getDateFieldValue((Date)curMonthVer.getValidTime(), (int)2);
                if (currMonth == verMonth) {
                    this.appendLog(String.format("\u672c\u6708\u7248\u672c\u5df2\u7ecf\u5b58\u5728\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u7248\u672c[%1$s] \n", orgType.getName()));
                    continue;
                }
                OrgVersionVO orgVer = new OrgVersionVO();
                orgVer.setId(UUID.randomUUID().toString());
                orgVer.setName(orgVerCode);
                orgVer.setOrgType(orgType);
                orgVer.setTitle(DateUtils.format((Date)validTime, (String)MONTH_CHAR_BY_DASH));
                orgVer.setValidTime(validTime);
                gcOrgVerTool.addOrgVersion(orgVer);
                this.appendLog(String.format("\u65b0\u5efa\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u7248\u672c[%1$s]-[%2$s] \n", orgType.getName(), DateUtils.format((Date)validTime, (String)MONTH_CHAR_BY_DASH)));
            }
        }
        catch (Exception e) {
            ++failCount;
            this.appendLog(String.format("\u81ea\u52a8\u521b\u5efa\u3010%1$s\u3011\u5355\u4f4d\u7248\u672c\u8ba1\u5212\u4efb\u52a1\u5931\u8d25\uff1a\u3010%2$s\u3011 \n", orgTypeArr, e.getMessage()));
        }
        finally {
            ShiroUtil.unbindTenantName();
        }
        return failCount == 0;
    }

    private static Date getNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, 1);
        return cal.getTime();
    }
}

