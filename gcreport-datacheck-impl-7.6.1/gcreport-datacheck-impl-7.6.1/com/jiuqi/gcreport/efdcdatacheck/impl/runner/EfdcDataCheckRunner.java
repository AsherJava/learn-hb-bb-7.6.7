/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.runner;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EFDCDataCheckExportService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EFDCDataCheckReportService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import java.net.ConnectException;
import java.util.Arrays;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@PlanTaskRunner(id="CD85FD211CD547A5B806CA46570F637C", settingPage="efdcAdvanceConfig", name="com.jiuqi.gcreport.nr.impl.runner.EfdcDataCheckRunner", title="\u6570\u636e\u7a3d\u6838\u8ba1\u5212\u4efb\u52a1")
public class EfdcDataCheckRunner
extends Runner {
    @Autowired
    private EFDCDataCheckExportService dataCheckExportService;
    @Autowired
    private SystemUserService sysUserService;
    @Autowired
    private EFDCDataCheckReportService efdcDataCheckReportService;

    @Transactional(rollbackFor={Exception.class})
    public boolean excute(String runnerParameter) {
        JSONObject jsonObject = new JSONObject(runnerParameter);
        if (null == jsonObject) {
            this.appendLog("\u672a\u8bbe\u7f6e\u9ad8\u7ea7\u53c2\u6570");
            return false;
        }
        this.initAsAdmin();
        EfdcCheckCreateReportVO efdcCheckCreateReportVO = (EfdcCheckCreateReportVO)JsonUtils.readValue((String)runnerParameter, EfdcCheckCreateReportVO.class);
        try {
            String logString;
            GcBatchEfdcCheckInfo batchCheckInfo = this.efdcDataCheckReportService.buildBatchCheckInfo(efdcCheckCreateReportVO, true);
            NpContext context = NpContextHolder.getContext();
            StringBuffer log = new StringBuffer();
            String asynTaskKey = UUIDOrderUtils.newUUIDStr();
            EFDCDataCheckImpl checkResultInfo = new EFDCDataCheckImpl();
            checkResultInfo.batchEfdcDataCheck(asynTaskKey, batchCheckInfo);
            if (checkResultInfo.getCheckZbCount() == 0) {
                log.append(checkResultInfo.getLog().append(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcZbNumber"))).append("\n");
                this.appendLog("\u6267\u884c\u5931\u8d25<br>" + log.toString());
                return false;
            }
            if (Arrays.asList(efdcCheckCreateReportVO.getFileType()).contains("PDF")) {
                log.append(this.dataCheckExportService.planTaskCheckResultPdf(context, asynTaskKey, checkResultInfo, batchCheckInfo)).append("\n");
            }
            if (Arrays.asList(efdcCheckCreateReportVO.getFileType()).contains("PDF")) {
                log.append(this.dataCheckExportService.planTaskCheckResultExcel(asynTaskKey, checkResultInfo, batchCheckInfo)).append("\n");
            }
            if ((logString = log.toString()).indexOf("\u5b9e\u9645\u7a3d\u6838\u6307\u6807\u51710\u4e2a") != -1) {
                this.appendLog("\u6267\u884c\u5931\u8d25<br>" + logString);
                return false;
            }
            this.appendLog("\u6267\u884c\u6210\u529f<br>" + logString);
        }
        catch (ConnectException e) {
            this.appendLog(e.getMessage() + "\n");
            return false;
        }
        catch (Exception e) {
            this.appendLog(e.getMessage() + "\n");
            e.printStackTrace();
            return false;
        }
        return true;
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

