/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.authz.bean.UserQueryParam
 *  com.jiuqi.nr.authz.bean.UserTreeNode
 *  com.jiuqi.nr.authz.service.IUserTreeService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam
 *  com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult
 *  com.jiuqi.nr.bpm.setting.pojo.ShowResult
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.dataentry.export.IReportExport
 *  com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.util.BatchExportConsts
 *  com.jiuqi.nr.definition.auth.authz2.ResourceType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.reminder.infer.ReminderRepository
 *  com.jiuqi.nvwa.jobmanager.api.dto.LogQueryParam
 *  com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskLogVO
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.nvwa.jobmanager.service.PlanTaskLogService
 *  javax.activation.DataSource
 *  javax.annotation.Resource
 *  javax.mail.MessagingException
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimeUtility
 *  javax.mail.util.ByteArrayDataSource
 *  org.springframework.mail.javamail.JavaMailSender
 *  org.springframework.mail.javamail.MimeMessageHelper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.reportnotice.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.reportnotice.entity.GcReportNoticeEnum;
import com.jiuqi.gcreport.reportnotice.entity.GcReportNoticeParam;
import com.jiuqi.gcreport.reportnotice.factory.ReportNoticeFactory;
import com.jiuqi.gcreport.reportnotice.service.ReportNoticeService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.authz.bean.UserQueryParam;
import com.jiuqi.nr.authz.bean.UserTreeNode;
import com.jiuqi.nr.authz.service.IUserTreeService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.export.IReportExport;
import com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.definition.auth.authz2.ResourceType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nvwa.jobmanager.api.dto.LogQueryParam;
import com.jiuqi.nvwa.jobmanager.api.vo.PlanTaskLogVO;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.nvwa.jobmanager.service.PlanTaskLogService;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.LambdaMetafactory;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

@PlanTaskRunner(id="97B18196831F446E8F1E5579C0A7931E", settingPage="reportNoticeConfig", name="com.jiuqi.gcreport.reportnotice.runner.ReportNoticeSyncRunner", title="\u4e0a\u62a5\u90ae\u4ef6\u901a\u77e5\u8ba1\u5212\u4efb\u52a1")
public class ReportNoticeSyncRunner
extends Runner {
    @Value(value="${spring.mail.nickname:}")
    private String nickname;
    @Value(value="${spring.mail.shortmessage.code:}")
    private String shortMessageCode;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private SystemUserService systemUserService;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private ExportExcelNameServiceImpl exportExcelNameService;
    @Autowired
    private IReportExport reportExportService;
    @Resource
    private UserService<User> userService;
    @Autowired(required=false)
    private JavaMailSender mailSenderImpl;
    @Autowired
    private PlanTaskLogService planTaskLogService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private IUserTreeService iUserTreeService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Resource
    private IEntityAuthorityService iEntityAuthorityService;
    private final Logger logger = LoggerFactory.getLogger(ReportNoticeSyncRunner.class);
    private static final String EMAIL = "EMAIL";
    private static final String SHORT_MESSAGE = "SHORT_MESSAGE";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Transactional(rollbackFor={Exception.class})
    public boolean excute(JobContext jobContext) {
        npContext = null;
        try {
            this.logger.info("\u90ae\u4ef6\u901a\u77e5\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6d41\u7a0b\u542f\u52a8");
            this.appendLog(jobContext.getJob().getTitle() + "\u6267\u884c\u6d41\u7a0b\u542f\u52a8:\r\n");
            runnerParameter = jobContext.getJob().getExtendedConfig();
            actionParam = (GcReportNoticeParam)JsonUtils.readValue((String)runnerParameter, GcReportNoticeParam.class);
            if (actionParam == null) {
                this.appendLog("\u9ad8\u7ea7\u8bbe\u7f6e\u53c2\u6570\u4e3a\u7a7a\u3002\r\n");
                this.appendLog("\u6267\u884c\u5931\u8d25\u3002");
                var5_7 = false;
                return var5_7;
            }
            startTime = null;
            endTime = null;
            if ("EMAIL".equals(actionParam.getSendType())) {
                startTime = this.getPlanStartTime(jobContext.getJob().getTitle(), "2", "startType");
                endTime = this.getPlanStartTime(jobContext.getJob().getTitle(), "0", "endType");
                if (StringUtils.isEmpty((String)startTime) || StringUtils.isEmpty((String)endTime)) {
                    this.appendLog("\u8ba1\u5212\u4efb\u52a1\u7b2c\u4e00\u6b21\u6267\u884c\uff0c\u6267\u884c\u6210\u529f\u3002");
                    var7_10 = true;
                    return var7_10;
                }
            }
            this.initParamPeriod(actionParam);
            fromKeyList = actionParam.getFromKeyListMap().stream().map((Function<Map, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$excute$0(java.util.Map ), (Ljava/util/Map;)Ljava/lang/String;)()).collect(Collectors.toList());
            if (fromKeyList.size() > 0) {
                fromStr = new StringBuilder();
                fromKeyList.forEach((Consumer<String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$excute$1(java.lang.StringBuilder java.lang.String ), (Ljava/lang/String;)V)((StringBuilder)fromStr));
                actionParam.setFromKeyStrs(fromStr.toString());
            }
            if ((user = this.getUserByUserName(jobContext.getJob().getUser())) == null || !"admin".equals(user.getName()) && !this.reminderRepository.findUserState(user.getId())) {
                this.appendLog("\u8ba1\u5212\u4efb\u52a1\u7528\u6237\u540d\u4e0d\u53ef\u7528\n");
                this.appendLog("\u6267\u884c\u5931\u8d25\u3002");
                this.logger.error("\u90ae\u4ef6\u901a\u77e5\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff0c\u6267\u884c\u7684\u7528\u6237\u540d\u4e0d\u53ef\u7528");
                var9_13 = false;
                return var9_13;
            }
            npContext = this.buildContextByUserName(jobContext.getJob().getUser());
            NpContextHolder.setContext((NpContext)npContext);
            var9_14 = actionParam.getSendType();
            var10_15 = -1;
            switch (var9_14.hashCode()) {
                case 66081660: {
                    if (!var9_14.equals("EMAIL")) break;
                    var10_15 = 0;
                    break;
                }
                case 1195746052: {
                    if (!var9_14.equals("SHORT_MESSAGE")) break;
                    var10_15 = 1;
                }
            }
            switch (var10_15) {
                case 0: {
                    shortMessageTextarea = actionParam.getShortMessageTextarea();
                    shortMessageTextarea = shortMessageTextarea.replaceAll("\r|\n", "");
                    actionParam.setShortMessageTextarea(shortMessageTextarea);
                    if (this.isRegularConstant(actionParam)) {
                        this.sendZipEmail(jobContext, actionParam);
                        ** break;
lbl53:
                        // 1 sources

                    } else {
                        this.sendExcelEmail(jobContext, actionParam, startTime, endTime);
                        ** break;
                    }
lbl56:
                    // 1 sources

                    break;
                }
                case 1: {
                    this.handShortMessage(actionParam, jobContext);
                    break;
                }
                ** default:
lbl61:
                // 1 sources

                break;
            }
        }
        catch (Exception e) {
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38\u3002");
            this.logger.error("\u90ae\u4ef6\u901a\u77e5\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38", e);
            var4_6 = false;
            return var4_6;
        }
        finally {
            if (npContext != null) {
                NpContextHolder.clearContext();
            }
        }
        this.logger.info("\u90ae\u4ef6\u901a\u77e5\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6210\u529f");
        this.appendLog("\u6267\u884c\u6210\u529f\u3002");
        return true;
    }

    private void handShortMessage(GcReportNoticeParam actionParam, JobContext jobContext) throws Exception {
        HashMap<String, FieldDefine> fieldStr2field = new HashMap<String, FieldDefine>();
        this.getFieldList(actionParam.getShortMessageTextarea(), fieldStr2field);
        List<String> fsUserIdList = this.initUserIds(actionParam.getJsInfos());
        if (CollectionUtils.isEmpty(fsUserIdList)) {
            this.appendLog("\u63a5\u6536\u7528\u6237\u4fe1\u606f\u4e3a\u7a7a\u3002");
            return;
        }
        Map<String, Set<String>> orgCode2fsUserIdSet = this.getAuthorityOrgCodeMap(fsUserIdList, actionParam, jobContext);
        if (orgCode2fsUserIdSet.size() < 1) {
            this.appendLog("\u6709\u5355\u4f4d\u6743\u9650\u7684\u63a5\u6536\u7528\u6237\u4e3a\u7a7a\u3002");
            return;
        }
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)actionParam.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS);
        for (String orgCode : actionParam.getOrgCodeList()) {
            if (fieldStr2field.size() == 0) {
                this.sendShortMessage(orgCode2fsUserIdSet, orgCode, actionParam, null);
                continue;
            }
            GcOrgCacheVO gcOrgCacheVO = tool.getOrgByCode(orgCode);
            if (gcOrgCacheVO == null) {
                return;
            }
            Map<String, DimensionValue> dimensionSet = this.getDimensionSet(gcOrgCacheVO, actionParam);
            IDataTable iDataTable = this.getIDataTable(ReportNoticeSyncRunner.getDimensionValueSet(dimensionSet), new ArrayList<FieldDefine>(fieldStr2field.values()));
            Map<String, Object> fieldStr2Value = this.getIDateRowValue(iDataTable, fieldStr2field);
            if (!actionParam.getDepartureAction().equals(GcReportNoticeEnum.UNITALLCHOOSE.getCode())) {
                return;
            }
            this.sendShortMessage(orgCode2fsUserIdSet, orgCode, actionParam, fieldStr2Value);
        }
    }

    private Map<String, Object> getIDateRowValue(IDataTable iDataTable, Map<String, FieldDefine> fieldStr2field) {
        HashMap<String, Object> fieldStr2Value = new HashMap<String, Object>();
        fieldStr2field.forEach((fieldStr, fieldDefine) -> {
            if (iDataTable.getCount() > 1) {
                ArrayList<String> contentList = new ArrayList<String>();
                for (int count = 0; count < iDataTable.getCount(); ++count) {
                    IDataRow dataRow = iDataTable.getItem(count);
                    contentList.add(dataRow.getAsString(fieldDefine));
                }
                fieldStr2Value.put((String)fieldStr, contentList);
            } else if (iDataTable.getCount() == 1) {
                IDataRow dataRow = iDataTable.getItem(0);
                fieldStr2Value.put((String)fieldStr, dataRow.getAsString(fieldDefine));
            } else {
                fieldStr2Value.put((String)fieldStr, "");
            }
        });
        return fieldStr2Value;
    }

    private void sendShortMessage(Map<String, Set<String>> orgCode2fsUserIdSet, String orgCode, GcReportNoticeParam actionParam, Map<String, Object> fieldStr2Value) throws Exception {
        ReportNoticeService reportNoticeService = ReportNoticeFactory.getInstance().getClassOfCode(this.shortMessageCode);
        List<String> userPhoneList = this.getUserPhone(orgCode2fsUserIdSet.get(orgCode));
        if (reportNoticeService == null) {
            this.appendLog("\u77ed\u4fe1\u53d1\u9001\u6267\u884c\u5668\u4e3a\u7a7a\u3002");
            return;
        }
        if (CollectionUtils.isEmpty(userPhoneList)) {
            this.appendLog("\u63a5\u6536\u7528\u6237\u7535\u8bdd\u4e3a\u7a7a\u3002");
            return;
        }
        reportNoticeService.executeList(this.setContent(actionParam, fieldStr2Value), userPhoneList);
    }

    private void getFieldList(String shortMessageTextarea, Map<String, FieldDefine> fieldStr2field) {
        int leftBracket = shortMessageTextarea.indexOf(123);
        int rightBracket = shortMessageTextarea.indexOf(125);
        if (leftBracket != -1 && rightBracket != -1) {
            String fieldStr = shortMessageTextarea.substring(leftBracket + 1, rightBracket);
            if (StringUtils.isNotEmpty((String)fieldStr)) {
                this.getFieldDefine(Arrays.stream(fieldStr.split(",")).collect(Collectors.toList()), fieldStr2field);
            }
            shortMessageTextarea = shortMessageTextarea.replaceFirst("\\{", "");
            shortMessageTextarea = shortMessageTextarea.replaceFirst("}", "");
            this.getFieldList(shortMessageTextarea, fieldStr2field);
        }
    }

    private String setContent(GcReportNoticeParam actionParam, Map<String, Object> fieldStr2Value) {
        if (fieldStr2Value == null) {
            return actionParam.getShortMessageTextarea();
        }
        String[] shortMessageTextarea = new String[]{actionParam.getShortMessageTextarea()};
        fieldStr2Value.forEach((fieldStr, field) -> {
            shortMessageTextarea[0] = shortMessageTextarea[0].replace((CharSequence)fieldStr, field.toString());
        });
        shortMessageTextarea[0] = shortMessageTextarea[0].replace("\\{", "");
        shortMessageTextarea[0] = shortMessageTextarea[0].replace("}", "");
        return shortMessageTextarea[0];
    }

    private void getFieldDefine(List<String> fieldStrList, Map<String, FieldDefine> fieldStr2field) {
        fieldStrList.forEach(fieldStr -> {
            int leftBracket = fieldStr.indexOf(91);
            int rightBracket = fieldStr.indexOf(93);
            if (leftBracket != -1 && rightBracket != -1) {
                String tableCode = fieldStr.substring(0, leftBracket);
                String fieldCode = fieldStr.substring(leftBracket + 1, rightBracket);
                try {
                    TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefineByCode(tableCode);
                    if (tableDefine != null) {
                        String tableKey = tableDefine.getKey();
                        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode, tableKey);
                        fieldStr2field.put((String)fieldStr, fieldDefine);
                    }
                }
                catch (Exception e) {
                    this.logger.error("\u6307\u6807\u3010" + fieldStr + "\u3011\u683c\u5f0f\u5316\u5f02\u5e38\u3002", e);
                }
            } else {
                this.logger.error("\u6307\u6807\u3010" + fieldStr + "\u3011\u683c\u5f0f\u5f02\u5e38\u3002");
            }
        });
    }

    private IDataTable getIDataTable(DimensionValueSet dimensionValueSet, List<FieldDefine> fieldDefineList) throws Exception {
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        fieldDefineList.stream().forEach(arg_0 -> ((IDataQuery)dataQuery).addColumn(arg_0));
        dataQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        return dataQuery.executeQuery(context);
    }

    public static DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    private void sendZipEmail(JobContext jobContext, GcReportNoticeParam actionParam) throws Exception {
        ArrayList<GcOrgCacheVO> gcOrgCacheVOList = new ArrayList<GcOrgCacheVO>();
        for (String orgCode : actionParam.getOrgCodeList()) {
            GcOrgCacheVO gcOrgCacheVO = this.getOrgByCode(orgCode, actionParam);
            if (gcOrgCacheVO != null) {
                if (!this.handleOrgCodeAuthority(gcOrgCacheVO, actionParam)) continue;
                gcOrgCacheVOList.add(gcOrgCacheVO);
                continue;
            }
            this.appendLog("\u5355\u4f4d\u3010" + orgCode + "\u3011\u4e3a\u7a7a\u6216\u8005\u6ca1\u6709\u6743\u9650\u3002\r\n");
        }
        this.handleZipParamLogger(actionParam, gcOrgCacheVOList, jobContext);
        this.handleZipFormulaText(actionParam, gcOrgCacheVOList);
        this.exportZip(actionParam, jobContext, gcOrgCacheVOList);
    }

    private void sendExcelEmail(JobContext jobContext, GcReportNoticeParam actionParam, String startTime, String endTime) throws JQException, UnauthorizedEntityException {
        if (StringUtils.isEmpty((String)startTime) || StringUtils.isEmpty((String)endTime)) {
            return;
        }
        HashMap<String, Integer> orgCode2Count = new HashMap<String, Integer>();
        Map<String, ShowResult> showResultList = this.getShowResult(actionParam);
        showResultList.forEach((orgCode, showResult) -> {
            for (ShowNodeResult showNodeResult : showResult.getNodeList()) {
                if (!this.isUploadConstant(showNodeResult, actionParam) || showNodeResult.getTime() == null || !this.isDatePeriod(startTime, endTime, showNodeResult.getTime())) continue;
                int count = orgCode2Count.getOrDefault("orgCode", 0);
                orgCode2Count.put((String)orgCode, ++count);
            }
        });
        this.handleExcelParamLogger(actionParam, jobContext);
        if (orgCode2Count.size() > 0) {
            this.exportExcel(actionParam, orgCode2Count, jobContext);
        }
    }

    protected boolean canOperateTaskResource(String formKey, String userId) {
        String resourceId = ResourceType.FORM.toResourceId(formKey);
        String privilegeId = "task_privilege_read";
        if (userId == null) {
            return false;
        }
        return this.privilegeService.hasAuth(privilegeId, userId, (Object)resourceId);
    }

    private Map<String, Set<String>> getAuthorityOrgCodeMap(List<String> userIdList, GcReportNoticeParam actionParam, JobContext jobContext) throws UnauthorizedEntityException, JQException {
        HashMap<String, Set<String>> orgCode2userIdSet = new HashMap<String, Set<String>>();
        Date data = new Date();
        for (String userId : userIdList) {
            if (this.systemIdentityService.isSystemIdentity(userId)) continue;
            NpContextHolder.setContext((NpContext)this.buildContextByUserId(userId));
            Set orgCodeSet = this.iEntityAuthorityService.getCanReadEntityKeys(actionParam.getOrgType() + "@ORG", data, data);
            orgCodeSet.forEach(orgCode -> {
                Set userIdSet = orgCode2userIdSet.getOrDefault(orgCode, new HashSet());
                userIdSet.add(userId);
                orgCode2userIdSet.put((String)orgCode, userIdSet);
            });
        }
        NpContextHolder.setContext((NpContext)this.buildContextByUserName(jobContext.getJob().getUser()));
        return orgCode2userIdSet;
    }

    private void handleZipFormulaText(GcReportNoticeParam actionParam, List<GcOrgCacheVO> gcOrgCacheVOList) {
        String textarea = this.handleExcelFormulaText(actionParam.getTextarea(), actionParam, actionParam.getOrgCodeList().toString(), gcOrgCacheVOList.stream().map(GcOrgCacheVO::getTitle).collect(Collectors.toList()).toString());
        textarea = textarea.replace("\\[MATCHUNITNUM]", String.valueOf(gcOrgCacheVOList.size()));
        textarea = textarea.replace("\\[UNMATCHUNITNUM]", String.valueOf(actionParam.getOrgCodeList().size() - gcOrgCacheVOList.size()));
        actionParam.setTextarea(textarea);
        String title = this.handleExcelFormulaText(actionParam.getTitle(), actionParam, actionParam.getOrgCodeList().toString(), gcOrgCacheVOList.stream().map(GcOrgCacheVO::getTitle).collect(Collectors.toList()).toString());
        title = title.replace("\\[MATCHUNITNUM]", String.valueOf(gcOrgCacheVOList.size()));
        title = title.replace("\\[UNMATCHUNITNUM]", String.valueOf(actionParam.getOrgCodeList().size() - gcOrgCacheVOList.size()));
        actionParam.setTitle(title);
    }

    private String handleExcelFormulaText(String text, GcReportNoticeParam actionParam, String orgCodeStr, String orgNameListStr) {
        if (StringUtils.isEmpty((String)text)) {
            return "";
        }
        text = text.replace("\\[CUR_TIME]", String.valueOf(actionParam.getPeriodWrapper().getPeriod()));
        text = text.replace("\\[CUR_YEAR]", String.valueOf(actionParam.getPeriodWrapper().getYear()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        text = text.replace("\\[ACTIONTIME]", simpleDateFormat.format(new Date()));
        text = text.replace("\\[DWDM]", orgCodeStr);
        text = text.replace("\\[DWMC]", orgNameListStr);
        return text;
    }

    private void handleExcelParamLogger(GcReportNoticeParam actionParam, JobContext jobContext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.appendLog("\u64cd\u4f5c\u65f6\u95f4\uff1a" + simpleDateFormat.format(new Date()) + "\r\n");
        this.appendLog("\u5355\u4f4d\u4ee3\u7801\u540d\u79f0\uff1a" + actionParam.getOrgCodeList().toString() + "\r\n");
    }

    private void handleZipParamLogger(GcReportNoticeParam actionParam, List<GcOrgCacheVO> gcOrgCacheVOList, JobContext jobContext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.appendLog("\u64cd\u4f5c\u65f6\u95f4\uff1a" + simpleDateFormat.format(new Date()) + "\r\n");
        this.appendLog("\u6ee1\u8db3\u6761\u4ef6\u5355\u4f4d\u6570\u91cf\uff1a" + gcOrgCacheVOList.size() + "\r\n");
        this.appendLog("\u6ee1\u8db3\u6761\u4ef6\u5355\u4f4d\u4ee3\u7801\uff1a" + gcOrgCacheVOList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()) + "\r\n");
        ArrayList<String> orgCodeList = new ArrayList<String>(actionParam.getOrgCodeList());
        orgCodeList.removeAll(gcOrgCacheVOList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
        this.appendLog("\u4e0d\u6ee1\u8db3\u6761\u4ef6\u5355\u4f4d\u6570\u91cf\uff1a" + orgCodeList.size() + "\r\n");
        this.appendLog("\u4e0d\u6ee1\u8db3\u6761\u4ef6\u5355\u4f4d\u4ee3\u7801\uff1a" + orgCodeList + "\r\n");
    }

    private void initParamPeriod(GcReportNoticeParam actionParam) {
        PeriodWrapper periodWrapper = this.getPeriodForScheme(actionParam.getSchemeId());
        if (GcReportNoticeEnum.PREVIOUSPERIOD.toString().equals(actionParam.getPeriodType())) {
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            defaultPeriodAdapter.priorPeriod(periodWrapper);
        } else if (GcReportNoticeEnum.LATERPERIOD.toString().equals(actionParam.getPeriodType())) {
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            defaultPeriodAdapter.nextPeriod(periodWrapper);
        }
        actionParam.setPeriodWrapper(periodWrapper);
    }

    private GcOrgCacheVO getOrgByCode(String orgCode, GcReportNoticeParam actionParam) {
        YearPeriodObject yp = new YearPeriodObject(null, actionParam.getPeriodWrapper().toString());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)actionParam.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return instance.getOrgByCode(orgCode);
    }

    private Map<String, ByteArrayOutputStream> exportFileToExcel(GcReportNoticeParam actionParam, List<String> orgCodeList) {
        try {
            HashMap<String, ByteArrayOutputStream> orgCode2reportByte = new HashMap<String, ByteArrayOutputStream>();
            for (String orgCode : orgCodeList) {
                GcOrgCacheVO gcOrgCacheVO = this.getOrgByCode(orgCode, actionParam);
                if (gcOrgCacheVO == null) continue;
                ExportParam exportParam = new ExportParam();
                exportParam.setType("EXPORT_EXCEL");
                exportParam.setSplitMark("\\t");
                JtableContext context = new JtableContext();
                context.setFormSchemeKey(actionParam.getSchemeId());
                context.setTaskKey(actionParam.getTaskId());
                context.setDimensionSet(this.getDimensionSet(gcOrgCacheVO, actionParam));
                exportParam.setContext(context);
                ByteArrayOutputStream result = null;
                try {
                    if (null != actionParam.getFromKeyStrs() && !StringUtils.isEmpty((String)actionParam.getFromKeyStrs())) {
                        result = this.export(exportParam, actionParam.getFromKeyStrs());
                    }
                }
                catch (IOException e) {
                    this.logger.error("\u83b7\u53d6\u6587\u4ef6\u6d41\u5f02\u5e38", e);
                    throw new BusinessRuntimeException("\u83b7\u53d6\u6587\u4ef6\u6d41\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\r\n");
                }
                if (result == null) continue;
                orgCode2reportByte.put(orgCode, result);
            }
            return orgCode2reportByte;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u62a5\u8868\u6570\u636e\u6d41\u5f02\u5e38", e);
            throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6570\u636e\u6d41\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
    }

    /*
     * Exception decompiling
     */
    public ByteArrayOutputStream export(ExportParam param, String formKeys) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private String getPlanStartTime(String planTaskName, String state, String timeType) throws JQException {
        block2: {
            List planTaskLogVOS;
            block3: {
                LogQueryParam logQueryParam = new LogQueryParam();
                logQueryParam.setPlantaskname(planTaskName);
                logQueryParam.setState(state);
                planTaskLogVOS = this.planTaskLogService.queryPlanTaskExecuteLogByCondi(logQueryParam, 1, 20);
                if (CollectionUtils.isEmpty((Collection)planTaskLogVOS)) break block2;
                if (!"startType".equals(timeType)) break block3;
                for (PlanTaskLogVO planTaskLogVO : planTaskLogVOS) {
                    if (!planTaskName.equals(planTaskLogVO.getPlantaskname())) continue;
                    return planTaskLogVO.getStarttime();
                }
                break block2;
            }
            if (!"endType".equals(timeType)) break block2;
            for (int index = planTaskLogVOS.size() - 1; index >= 0; --index) {
                if (!planTaskName.equals(((PlanTaskLogVO)planTaskLogVOS.get(index)).getPlantaskname())) continue;
                return ((PlanTaskLogVO)planTaskLogVOS.get(index)).getStarttime();
            }
        }
        return "";
    }

    private boolean isDatePeriod(String startTime, String endTime, String nodeResultTime) {
        SimpleDateFormat planFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            long startTimeLong = planFormat.parse(startTime).getTime();
            long endTimeLong = planFormat.parse(endTime).getTime();
            long resultTimeLong = planFormat.parse(nodeResultTime).getTime();
            if (resultTimeLong >= startTimeLong && resultTimeLong < endTimeLong) {
                return true;
            }
        }
        catch (ParseException e) {
            this.logger.error("\u5224\u65ad\u5de5\u4f5c\u6d41\u662f\u5426\u5728\u8ba1\u5212\u4efb\u52a1\u65f6\u671f\u8303\u56f4\u5185\u5f02\u5e38\u3002", e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        return false;
    }

    private boolean isDatePeriod(String startTime, String endTime, Date nodeResultTime) {
        SimpleDateFormat planFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            long startTimeLong = planFormat.parse(startTime).getTime();
            long endTimeLong = planFormat.parse(endTime).getTime();
            long resultTimeLong = nodeResultTime.getTime();
            if (resultTimeLong >= startTimeLong && resultTimeLong < endTimeLong) {
                return true;
            }
        }
        catch (ParseException e) {
            this.logger.error("\u5224\u65ad\u5de5\u4f5c\u6d41\u662f\u5426\u5728\u8ba1\u5212\u4efb\u52a1\u65f6\u671f\u8303\u56f4\u5185\u5f02\u5e38\u3002", e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        return false;
    }

    private boolean isUploadConstant(ShowNodeResult showNodeResult, GcReportNoticeParam actionParam) {
        if (("act_upload".equals(showNodeResult.getActionCode()) || "cus_upload".equals(showNodeResult.getActionCode())) && GcReportNoticeEnum.UNITREPORT.getCode().equals(actionParam.getDepartureAction())) {
            return true;
        }
        if (("act_reject".equals(showNodeResult.getActionCode()) || "cus_reject".equals(showNodeResult.getActionCode())) && GcReportNoticeEnum.UNITRETURN.getCode().equals(actionParam.getDepartureAction())) {
            return true;
        }
        if (("act_submit".equals(showNodeResult.getActionCode()) || "cus_submit".equals(showNodeResult.getActionCode())) && GcReportNoticeEnum.UNITEXAMINE.getCode().equals(actionParam.getDepartureAction())) {
            return true;
        }
        return ("act_return".equals(showNodeResult.getActionCode()) || "cus_return".equals(showNodeResult.getActionCode())) && GcReportNoticeEnum.UNITEXAMINERETURN.getCode().equals(actionParam.getDepartureAction());
    }

    private boolean isRegularConstant(GcReportNoticeParam actionParam) {
        return GcReportNoticeEnum.UNITREGULAREXAMINE.getCode().equals(actionParam.getDepartureAction()) || GcReportNoticeEnum.UNITREGULARREPORT.getCode().equals(actionParam.getDepartureAction()) || GcReportNoticeEnum.UNITALLCHOOSE.getCode().equals(actionParam.getDepartureAction());
    }

    private MimeMessageHelper getEmailMessage(JobContext jobContext, MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        User user = this.getUserByUserName(jobContext.getJob().getUser());
        if (user == null || !StringUtils.isNotEmpty((String)user.getEmail())) {
            this.appendLog("\u7528\u6237\u6216\u90ae\u7bb1\u4fe1\u606f\u4e3a\u7a7a\r\n");
            throw new BusinessRuntimeException("\u7528\u6237\u6216\u90ae\u7bb1\u4fe1\u606f\u4e3a\u7a7a");
        }
        messageHelper.setFrom(new InternetAddress(user.getEmail(), new String(this.nickname.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), "utf-8"));
        return messageHelper;
    }

    private boolean handleOrgCodeAuthority(GcOrgCacheVO gcOrgCacheVO, GcReportNoticeParam actionParam) {
        Map<String, DimensionValue> dimensionSetMap = this.getDimensionSet(gcOrgCacheVO, actionParam);
        UploadState status = this.queryUnitUploadState(actionParam.getSchemeId(), DimensionValueSetUtil.getDimensionValueSet(dimensionSetMap));
        if (GcReportNoticeEnum.UNITALLCHOOSE.getCode().equals(actionParam.getDepartureAction())) {
            return true;
        }
        if (GcReportNoticeEnum.UNITREGULARREPORT.getCode().equals(actionParam.getDepartureAction()) && status == UploadState.UPLOADED) {
            return true;
        }
        return GcReportNoticeEnum.UNITREGULAREXAMINE.getCode().equals(actionParam.getDepartureAction()) && status == UploadState.SUBMITED;
    }

    private UploadState queryUnitUploadState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setDim(dimensionValueSet);
        dataEntryParam.setFormSchemeKey(formSchemeKey);
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        IFormConditionService formConditionService = (IFormConditionService)BeanUtil.getBean(IFormConditionService.class);
        IConditionCache conditionCache = formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
        dataEntryParam.setFormKeys(conditionCache.getSeeForms(dimensionValueSet));
        dataEntryParam.setGroupKeys(conditionCache.getSeeFormGroups(dimensionValueSet));
        ActionState uploadState = dataFlowService.queryState(dataEntryParam);
        if (null == uploadState || null == uploadState.getUnitState()) {
            return UploadState.ORIGINAL;
        }
        return UploadState.valueOf((String)uploadState.getUnitState().getCode());
    }

    private Map<String, DimensionValue> getDimensionSet(GcOrgCacheVO gcOrgCacheVO, GcReportNoticeParam actionParam) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue orgDimen = new DimensionValue();
        orgDimen.setName("MD_ORG");
        orgDimen.setValue(gcOrgCacheVO.getCode());
        dimensionSet.put("MD_ORG", orgDimen);
        DimensionValue periodDimen = new DimensionValue();
        periodDimen.setName("DATATIME");
        periodDimen.setValue(actionParam.getPeriodWrapper().toString());
        dimensionSet.put("DATATIME", periodDimen);
        DimensionValue currencyDimen = new DimensionValue();
        currencyDimen.setName("MD_CURRENCY");
        currencyDimen.setValue("CNY");
        dimensionSet.put("MD_CURRENCY", currencyDimen);
        DimensionValue orgTypeDimen = new DimensionValue();
        orgTypeDimen.setName("MD_GCORGTYPE");
        orgTypeDimen.setValue(gcOrgCacheVO.getOrgTypeId());
        dimensionSet.put("MD_GCORGTYPE", orgTypeDimen);
        return dimensionSet;
    }

    private void exportZip(GcReportNoticeParam actionParam, JobContext jobContext, List<GcOrgCacheVO> gcOrgCacheVOList) throws Exception {
        File outFile = null;
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);){
            List<String> fsUserIdList = this.initUserIds(actionParam.getJsInfos());
            List<String> ccUserIdList = this.initUserIds(actionParam.getCcInfos());
            ccUserIdList.removeAll(fsUserIdList);
            Map<String, Set<String>> orgCode2fsUserIdSet = this.getAuthorityOrgCodeMap(fsUserIdList, actionParam, jobContext);
            Map<String, Set<String>> orgCode2ccUserIdSet = this.getAuthorityOrgCodeMap(ccUserIdList, actionParam, jobContext);
            String innerPath = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + jobContext.getJob().getUser() + BatchExportConsts.SEPARATOR + LocalDate.now() + BatchExportConsts.SEPARATOR + UUID.randomUUID();
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(actionParam.getTaskId());
            String fileNameInfo = this.exportExcelNameService.compileNameInfo(null, jtableContext, "ZIP_NAME", false, null);
            outFile = new File(innerPath + File.separator + fileNameInfo + ".zip");
            File fileParent = outFile.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            if (!outFile.createNewFile()) {
                this.appendLog("\u521b\u5efa\u6587\u4ef6\u5931\u8d25\uff0c\u8be6\u60c5\uff1a\u8def\u5f84" + outFile.getPath());
            }
            Map<String, ByteArrayOutputStream> orgCode2reportByte = this.exportFileToExcel(actionParam, gcOrgCacheVOList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
            Set<String> authorityFsUserIdList = new HashSet<String>(fsUserIdList);
            Set<String> authorityCcUserIdList = new HashSet<String>(ccUserIdList);
            for (String orgCode : gcOrgCacheVOList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList())) {
                GcOrgCacheVO gcOrgCacheVO = this.getOrgByCode(orgCode, actionParam);
                if (gcOrgCacheVO == null || orgCode2fsUserIdSet.get(orgCode) == null || orgCode2ccUserIdSet.get(orgCode) == null) continue;
                authorityFsUserIdList = orgCode2fsUserIdSet.get(orgCode).stream().filter(authorityFsUserIdList::contains).collect(Collectors.toSet());
                authorityCcUserIdList = orgCode2ccUserIdSet.get(orgCode).stream().filter(authorityCcUserIdList::contains).collect(Collectors.toSet());
                if (orgCode2reportByte.get(orgCode) == null) continue;
                try {
                    int read;
                    ZipEntry entry = new ZipEntry("\u5355\u4f4d[" + gcOrgCacheVO.getTitle() + "]\u90ae\u4ef6\u901a\u77e5.xlsx");
                    zipOutputStream.putNextEntry(entry);
                    ByteArrayInputStream swapStream = new ByteArrayInputStream(orgCode2reportByte.get(orgCode).toByteArray());
                    BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                    byte[] bufs = new byte[0xA00000];
                    while ((read = bis.read(bufs, 0, 10240)) != -1) {
                        zipOutputStream.write(bufs, 0, read);
                    }
                }
                catch (IOException e) {
                    this.logger.error("\u9644\u4ef6\u538b\u7f29\u5f02\u5e38", e);
                }
            }
            zipOutputStream.close();
            fileOutputStream.close();
            List<String> fsUserEmailList = this.getUserEmail(new HashSet<String>(authorityFsUserIdList), actionParam.getFromKeyStrs());
            if (fsUserEmailList.size() > 0) {
                MimeMessage mimeMessage = this.mailSenderImpl.createMimeMessage();
                MimeMessageHelper messageHelper = this.getEmailMessage(jobContext, mimeMessage);
                messageHelper.setSubject(actionParam.getTitle());
                messageHelper.setTo(fsUserEmailList.toArray(new String[0]));
                messageHelper.setCc(this.getUserEmail(new HashSet<String>(authorityCcUserIdList), actionParam.getFromKeyStrs()).toArray(new String[0]));
                if (outFile.length() > 0L) {
                    messageHelper.addAttachment(MimeUtility.encodeWord((String)outFile.getName(), (String)"utf-8", (String)"B"), outFile);
                    messageHelper.setText(actionParam.getTextarea());
                } else {
                    messageHelper.setText(actionParam.getTextarea() + "\r\n \u62a5\u8868\u6570\u636e\u4e3a\u7a7a\u3002");
                }
                this.mailSenderImpl.send(mimeMessage);
            } else {
                this.appendLog("\u53d1\u9001\u7528\u6237\u4e3a\u7a7a\r\n");
            }
        }
        catch (MessagingException e) {
            this.logger.error("\u6279\u91cf\u90ae\u4ef6\u901a\u77e5\u53d1\u9001\u5f02\u5e38", e);
            throw new BusinessRuntimeException("\u6279\u91cf\u90ae\u4ef6\u901a\u77e5\u53d1\u9001\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        finally {
            if (outFile != null && outFile.exists() && !outFile.delete()) {
                this.appendLog("\u5220\u9664\u6587\u4ef6\u5931\u8d25\uff0c\u8be6\u60c5\uff1a\u8def\u5f84" + outFile.getPath());
            }
        }
    }

    private void exportExcel(GcReportNoticeParam actionParam, Map<String, Integer> orgCode2Count, JobContext jobContext) throws JQException, UnauthorizedEntityException {
        List<String> fsUserIdList = this.initUserIds(actionParam.getJsInfos());
        List<String> ccUserIdList = this.initUserIds(actionParam.getCcInfos());
        ccUserIdList.removeAll(fsUserIdList);
        Map<String, Set<String>> orgCode2fsUserIdSet = this.getAuthorityOrgCodeMap(fsUserIdList, actionParam, jobContext);
        Map<String, Set<String>> orgCode2ccUserIdSet = this.getAuthorityOrgCodeMap(ccUserIdList, actionParam, jobContext);
        Map<String, ByteArrayOutputStream> orgCode2reportByte = this.exportFileToExcel(actionParam, new ArrayList<String>(orgCode2Count.keySet()));
        orgCode2Count.forEach((orgCode, count) -> {
            for (int index = 0; index < count; ++index) {
                Set fsUserIdSet = (Set)orgCode2fsUserIdSet.get(orgCode);
                if (fsUserIdSet == null || fsUserIdSet.isEmpty()) continue;
                Set ccUserSet = (Set)orgCode2ccUserIdSet.get(orgCode);
                GcOrgCacheVO gcOrgCacheVO = this.getOrgByCode((String)orgCode, actionParam);
                if (gcOrgCacheVO != null) {
                    try {
                        MimeMessage mimeMessage = this.mailSenderImpl.createMimeMessage();
                        MimeMessageHelper messageHelper = this.getEmailMessage(jobContext, mimeMessage);
                        if (orgCode2reportByte.get(orgCode) != null) {
                            messageHelper.addAttachment(MimeUtility.encodeWord((String)("\u5355\u4f4d[" + gcOrgCacheVO.getTitle() + "]\u62a5\u8868\u90ae\u4ef6\u901a\u77e5.xlsx"), (String)"utf-8", (String)"B"), (DataSource)new ByteArrayDataSource(((ByteArrayOutputStream)orgCode2reportByte.get(orgCode)).toByteArray(), "application/msexcel"));
                            messageHelper.setText(this.handleExcelFormulaText(actionParam.getTextarea(), actionParam, (String)orgCode, gcOrgCacheVO.getTitle()));
                        } else {
                            messageHelper.setText(this.handleExcelFormulaText(actionParam.getTextarea(), actionParam, (String)orgCode, gcOrgCacheVO.getTitle()) + "\r\n \u62a5\u8868\u6570\u636e\u4e3a\u7a7a\u3002");
                        }
                        List<String> fsUserEmailList = this.getUserEmail(fsUserIdSet, actionParam.getFromKeyStrs());
                        if (fsUserEmailList.size() <= 0) continue;
                        messageHelper.setSubject(this.handleExcelFormulaText(actionParam.getTitle(), actionParam, (String)orgCode, gcOrgCacheVO.getTitle()));
                        messageHelper.setTo(fsUserEmailList.toArray(new String[0]));
                        messageHelper.setCc(this.getUserEmail(ccUserSet, actionParam.getFromKeyStrs()).toArray(new String[0]));
                        this.mailSenderImpl.send(mimeMessage);
                        continue;
                    }
                    catch (Exception e) {
                        this.logger.error("\u5355\u4f4d[" + gcOrgCacheVO.getTitle() + "]\u53d1\u9001\u90ae\u4ef6\u5f02\u5e38", e);
                        throw new BusinessRuntimeException("\u5355\u4f4d[" + gcOrgCacheVO.getTitle() + "]\u53d1\u9001\u90ae\u4ef6\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
                    }
                }
                this.appendLog("\u5355\u4f4d\u3010" + orgCode + "\u3011\u4e3a\u7a7a\u6216\u8005\u6ca1\u6709\u6743\u9650\u3002\r\n");
            }
        });
    }

    private List<String> getUserEmail(Set<String> userIdSet, String formKeys) {
        ArrayList<String> userEmailList = new ArrayList<String>();
        if (userIdSet != null) {
            for (String userId : userIdSet) {
                User user = this.getUserEmailByUserId(userId);
                if (user == null || !StringUtils.isNotEmpty((String)user.getEmail())) continue;
                String[] formKeyArray = formKeys.split(";");
                boolean isHasPower = true;
                for (String fromId : formKeyArray) {
                    if (this.canOperateTaskResource(fromId, userId)) continue;
                    isHasPower = false;
                }
                if (!isHasPower) continue;
                userEmailList.add(user.getEmail());
            }
        }
        return userEmailList;
    }

    private List<String> getUserPhone(Set<String> userIdSet) {
        ArrayList<String> userPhoneList = new ArrayList<String>();
        if (userIdSet != null) {
            for (String userId : userIdSet) {
                User user = this.getUserEmailByUserId(userId);
                if (user == null || !StringUtils.isNotEmpty((String)user.getTelephone())) continue;
                userPhoneList.add(user.getTelephone());
            }
        }
        return userPhoneList;
    }

    private Map<String, ShowResult> getShowResult(GcReportNoticeParam actionParam) {
        HashMap<String, ShowResult> showResultMap = new HashMap<String, ShowResult>();
        for (String orgCode : actionParam.getOrgCodeList()) {
            ShowNodeParam nodeParam = new ShowNodeParam();
            nodeParam.setFormSchemeKey(actionParam.getSchemeId());
            nodeParam.setPeriod(actionParam.getPeriodWrapper().toString());
            GcOrgCacheVO gcOrgCacheVO = this.getOrgByCode(orgCode, actionParam);
            Map dimensionSetMap = DimensionUtils.buildDimensionMap((String)actionParam.getTaskId(), (String)"CNY", (String)nodeParam.getPeriod(), (String)(StringUtils.isEmpty((String)gcOrgCacheVO.getOrgTypeId()) ? actionParam.getOrgType() : gcOrgCacheVO.getOrgTypeId()), (String)orgCode, (String)"0");
            nodeParam.setDimensionSetMap(dimensionSetMap);
            showResultMap.put(orgCode, this.workflowSettingService.showWorkflow(nodeParam));
        }
        return showResultMap;
    }

    private PeriodWrapper getPeriodForScheme(String schemeId) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(schemeId);
        return TaskPeriodUtils.getCurrentPeriod((int)formScheme.getPeriodType().type());
    }

    private List<String> initUserIds(List<String> userIdEmailList) throws JQException {
        ArrayList<String> userIdList = new ArrayList<String>();
        ArrayList<String> roleIdList = new ArrayList<String>();
        for (String userId : userIdEmailList) {
            String[] userIdArray = userId.split(":");
            if ("0".equals(userIdArray[2])) {
                boolean isSendUser = this.reminderRepository.findUserState(userIdArray[0]);
                if (!isSendUser) continue;
                userIdList.add(userIdArray[0]);
                continue;
            }
            if (!"1".equals(userIdArray[2])) continue;
            roleIdList.add(userIdArray[0]);
        }
        if (roleIdList.size() > 0) {
            UserQueryParam queryParam = new UserQueryParam();
            queryParam.setRoleIds(roleIdList);
            List authUserInfoList = this.iUserTreeService.getUserListByUserQueryParam(queryParam);
            for (UserTreeNode authUserInfo : authUserInfoList) {
                if (userIdList.contains(authUserInfo.getKey())) continue;
                userIdList.add(authUserInfo.getKey());
            }
        }
        return userIdList;
    }

    private User getUserById(String userId) {
        if (StringUtils.isEmpty((String)userId)) {
            return null;
        }
        Optional user = this.userService.find(userId);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.find(userId);
        if (sysUser.isPresent()) {
            return (User)sysUser.get();
        }
        return null;
    }

    private User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((String)userName)) {
            return null;
        }
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        if (sysUser.isPresent()) {
            return (User)sysUser.get();
        }
        return null;
    }

    private User getUserEmailByUserId(String userId) {
        if (StringUtils.isEmpty((String)userId)) {
            return null;
        }
        Optional user = this.userService.find(userId);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.find(userId);
        return sysUser.orElse(null);
    }

    private NpContextUser buildUserContextByUserId(String userId) throws JQException {
        return this.buildUserContext(this.getUserById(userId));
    }

    private NpContextUser buildUserContext(User user) throws JQException {
        NpContextUser userContext = new NpContextUser();
        if (user == null) {
            throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private NpContextUser buildUserContextByUserName(String userName) throws JQException {
        return this.buildUserContext(this.getUserByUserName(userName));
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
    }

    private NpContextImpl buildContextByUserId(String userId) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContextByUserId(userId);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextImpl buildContextByUserName(String userName) throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContextByUserName(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private static /* synthetic */ void lambda$excute$1(StringBuilder fromStr, String fromKey) {
        fromStr.append(fromKey).append(";");
    }

    private static /* synthetic */ String lambda$excute$0(Map fromKeyMap) {
        return (String)fromKeyMap.get("formKey");
    }
}

