/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.organization.service.OrgVersionService
 */
package com.jiuqi.gcreport.samecontrol.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@PlanTaskRunner(id="6841B9E8ABAF37C9ABC169E9D97B7FC5", name="com.jiuqi.gcreport.samecontrol.task.AutoCreateSameCtrlChgOrgRunner", title="\u81ea\u52a8\u521b\u5efa\u540c\u63a7\u53d8\u52a8\u4fe1\u606f\u8ba1\u5212\u4efb\u52a1", settingPage="createSameCtrlChgOrgAdvanceConfig")
public class AutoCreateSameCtrlChgOrgRunner
extends Runner {
    protected static final String NAME = "com.jiuqi.gcreport.samecontrol.task.AutoCreateSameCtrlChgOrgRunner";
    protected static final String TITLE = "\u81ea\u52a8\u521b\u5efa\u540c\u63a7\u53d8\u52a8\u4fe1\u606f\u8ba1\u5212\u4efb\u52a1";
    protected static final String SETTINGPAGE = "createSameCtrlChgOrgAdvanceConfig";
    protected static final String ID = "6841B9E8ABAF37C9ABC169E9D97B7FC5";
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;
    @Autowired
    private OrgVersionService orgVersionService;
    @Autowired
    private SystemUserService sysUserService;

    public boolean excute(String runnerParameter) {
        Map jsonObject = (Map)JsonUtils.readValue((String)runnerParameter, (TypeReference)new TypeReference<Map<String, Object>>(){});
        if (null == jsonObject) {
            this.appendLog("\u672a\u8bbe\u7f6e\u9ad8\u7ea7\u53c2\u6570");
            return false;
        }
        String orgType = jsonObject.get("orgType").toString();
        String nextPeriodStr = this.getPeriodStr();
        if (StringUtils.isEmpty((String)nextPeriodStr)) {
            this.appendLog("\u83b7\u53d6\u65f6\u671f\u4e3a\u7a7a");
            return false;
        }
        this.initAsAdmin();
        List<String> periodStrs = this.listPeriodStr(orgType, nextPeriodStr);
        for (String periodStr : periodStrs) {
            this.sameCtrlChgOrgService.autoCreateSameCtrlChgOrg(orgType, periodStr);
        }
        this.appendLog("\u81ea\u52a8\u521b\u5efa\u540c\u63a7\u53d8\u52a8\u4fe1\u606f\u5b8c\u6210");
        return true;
    }

    private List<String> listPeriodStr(String orgType, String periodStr) {
        int previousYearStr = this.getPreviousYear(periodStr);
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(orgType);
        List orgVersionList = this.orgVersionService.list(orgVersionDTO).getRows();
        ArrayList<String> periodStrs = new ArrayList<String>();
        YearPeriodDO yearPeriod = YearPeriodUtil.transform(null, (String)periodStr);
        for (OrgVersionDO orgVersion : orgVersionList) {
            Calendar versionCalendar = this.getCalendar(orgVersion.getValidtime());
            if (orgVersion.getValidtime().getTime() > yearPeriod.getEndDate().getTime() || versionCalendar.get(1) < previousYearStr) continue;
            Calendar calendarVersion = this.getCalendar(orgVersion.getValidtime());
            YearPeriodDO yearPeriodVer = YearPeriodUtil.transform(null, (int)yearPeriod.getType(), (Calendar)calendarVersion);
            periodStrs.add(yearPeriodVer.getFormatValue());
        }
        return periodStrs;
    }

    private String getPeriodStr() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)4, (int)1);
        return currentPeriod.toString();
    }

    private int getPreviousYear(String currentPeriodStr) {
        YearPeriodObject yearPeriodCurrentObject = new YearPeriodObject(null, currentPeriodStr);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(yearPeriodCurrentObject.formatYP().getEndDate());
        int previousYear = gregorianCalendar.get(1) - 1;
        return previousYear;
    }

    private Calendar getCalendar(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private void initAsAdmin() {
        SystemUser admin = (SystemUser)this.sysUserService.getByUsername("admin");
        Assert.notNull((Object)admin, "\u65e0\u6cd5\u83b7\u53d6\u7ba1\u7406\u5458\u4fe1\u606f\uff01");
        NpContextImpl context = new NpContextImpl();
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(admin.getId());
        contextIdentity.setTitle(admin.getName());
        context.setIdentity((ContextIdentity)contextIdentity);
        context.setTenant("__default_tenant__");
        NpContextUser contextUser = new NpContextUser();
        contextUser.setId(admin.getId());
        contextUser.setName(admin.getName());
        contextUser.setNickname(admin.getNickname());
        context.setUser((ContextUser)contextUser);
        NpContextHolder.setContext((NpContext)context);
    }
}

