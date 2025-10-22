/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$ClassifierContext
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$Path
 *  com.jiuqi.bi.core.jobs.extension.IJobListener
 *  com.jiuqi.bi.core.jobs.extension.JobListenerContext
 *  com.jiuqi.bi.core.jobs.extension.item.FolderItem
 *  com.jiuqi.bi.core.jobs.extension.item.JobItem
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.crypto.Crypto
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.TicketToken
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.nvwa.ticket.service.TicketService
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobListener;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;
import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.crypto.Crypto;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.etl.common.ETLConfigErrorEnum;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.service.IEtlOrNrdlService;
import com.jiuqi.nr.etl.service.internal.ETLFilterParam;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nr.etl.service.internal.ParamFilter;
import com.jiuqi.nr.etl.service.internal.ParamSplicing;
import com.jiuqi.nr.etl.service.internal.QueryEntity;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.TicketToken;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.nvwa.ticket.service.TicketService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ETLPlanTaskRunner
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "dcf72f7b-0d74-49b2-8698-baca59914756";
    private static final String JOB_TITLE = "ETL\u8ba1\u5212\u4efb\u52a1";
    private static final String JOB_CONFIG_PAGE = "job-etl";
    private static final Logger logger = LoggerFactory.getLogger(ETLPlanTaskRunner.class);
    @Resource
    private IEtlOrNrdlService etlOrNrdlService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private QueryEntity queryEntity;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;
    @Autowired
    private ParamFilter paramFilter;
    @Autowired
    private ParamSplicing paramSplicing;
    @Autowired
    private TicketService ticketService;

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new ETLExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new BatchETLJobClassifier();
    }

    public IJobListener getJobListener() {
        return new BatchETLJobListener();
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }

    class BatchETLJobListener
    implements IJobListener {
        BatchETLJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    class BatchETLJobClassifier
    implements IJobClassifier {
        BatchETLJobClassifier() {
        }

        public List<FolderItem> getFolders(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public List<JobItem> getItems(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public JobItem getJobItem(String s) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(FolderItem folderItem) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(JobItem jobItem) throws Exception {
            return null;
        }
    }

    class ETLExecutor
    extends JobExecutor {
        private final Logger log = LoggerFactory.getLogger(ETLExecutor.class);
        private JobContext jobContext;

        ETLExecutor() {
        }

        public void execute(JobContext jobContext) throws JobExecutionException {
            String user = jobContext.getJob().getUser();
            NpContextImpl npContext = null;
            if (!StringUtils.isEmpty((String)user)) {
                try {
                    npContext = this.buildContext(user);
                    NpContextHolder.setContext((NpContext)npContext);
                }
                catch (JQException e) {
                    LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage()));
                }
            } else {
                throw new JobExecutionException("\u672a\u8bbe\u7f6e\u6267\u884c\u4eba");
            }
            this.jobContext = jobContext;
            try {
                this.excute();
            }
            catch (JQException e) {
                throw new RuntimeException(e);
            }
        }

        public void excute() throws JobExecutionException, JQException {
            Optional<EtlServeEntity> etlServeEntity = ETLPlanTaskRunner.this.etlServeEntityDao.getServerInfo();
            if (!etlServeEntity.isPresent()) {
                throw new JQException((ErrorEnum)ETLConfigErrorEnum.ETL_CONFIG_ERROR_ENUM);
            }
            String url = etlServeEntity.get().getAddress();
            String userName = etlServeEntity.get().getUserName();
            String passWord = etlServeEntity.get().getPwd();
            String nrUserName = this.jobContext.getUsername();
            Ticket tck = ETLPlanTaskRunner.this.ticketService.apply();
            TicketToken userToken = ETLPlanTaskRunner.this.ticketService.verifyTicket(tck.getId());
            String userTokenId = userToken.getId();
            try {
                passWord = Crypto.desEncrypt((String)passWord);
            }
            catch (Exception e) {
                throw new RuntimeException("\u5bc6\u7801\u65e0\u6cd5\u89e3\u5bc6\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\u5bc6\u7801", e);
            }
            String runnerParameter = this.jobContext.getJob().getExtendedConfig();
            try {
                String[] periods;
                JSONObject jsonObject = new JSONObject(runnerParameter);
                String taskKey = jsonObject.getString("taskKey");
                String formSchemeKey = jsonObject.getString("formSchemeKey");
                String etlId = jsonObject.getString("etlTaskKey");
                String datatime = jsonObject.getString("period");
                String unitid = jsonObject.get("unitIds").toString();
                String formKey = jsonObject.getString("formKey");
                for (String period : periods = datatime.split(";")) {
                    ETLFilterParam etlFilterParam = ETLPlanTaskRunner.this.paramFilter.paramFiltration(period, taskKey, unitid.split(";"), formKey.split(";"));
                    JSONObject param = ETLPlanTaskRunner.this.paramSplicing.paramSplicing(etlFilterParam, nrUserName, userTokenId, etlServeEntity.get().getType());
                    boolean valided = this.validity(url, userName);
                    if (!valided) {
                        return;
                    }
                    EtlExecuteInfo result = ETLPlanTaskRunner.this.etlOrNrdlService.executePlanTask(etlId, param.toString(), url, userName, passWord, logger);
                    this.jobContext.getDefaultLogger().info("\u6267\u884c\u7ed3\u679c\uff1a" + result.getResult());
                    this.jobContext.getDefaultLogger().info("\u6267\u884c\u7ec6\u8282\uff1a" + result.getDetailMessage());
                }
            }
            catch (Exception e) {
                this.jobContext.getDefaultLogger().error("\u8bf7\u68c0\u67e5\u9519\u8bef\u4fe1\u606f\uff1a" + e.getMessage());
                logger.error(e.getMessage(), e);
                throw new JobExecutionException("\u672a\u8bbe\u7f6e\u6267\u884c\u4eba");
            }
        }

        private StringBuffer arrayIdToString(JSONObject jsonobejct, StringBuffer sBuffer) {
            return sBuffer.append(jsonobejct.getString("formKey")).append(",");
        }

        private boolean validity(String url, String userName) {
            if (StringUtils.isEmpty((String)url)) {
                logger.warn("ETL\u670d\u52a1\u5730\u5740\u4e3a\u7a7a\uff01");
                return false;
            }
            if (StringUtils.isEmpty((String)userName)) {
                logger.warn("ETL\u670d\u52a1\u7528\u6237\u540d\u4e3a\u7a7a\uff01");
                return false;
            }
            return true;
        }

        private DimensionValueSet getEntity(String formSchemeKey, List<String> unitIdList) {
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            try {
                FormSchemeDefine formScheme = ETLPlanTaskRunner.this.runTimeViewController.getFormScheme(formSchemeKey);
                String mainDimName = ETLPlanTaskRunner.this.entityMetaService.getDimensionName(formScheme.getDw());
                dimensionValueSet.setValue(mainDimName, unitIdList);
            }
            catch (Exception e) {
                this.log.error(e.getMessage(), e);
            }
            return dimensionValueSet;
        }

        public String getFormList(String formSchemeKey, String taskKey, String period, String unitid) {
            HashMap<String, DimensionValue> dim = new HashMap<String, DimensionValue>();
            DimensionValue dimensionValue = null;
            String unitStr = "";
            if (unitid != null && unitid.length() > 0) {
                String[] unitSplit;
                dimensionValue = new DimensionValue();
                for (String unit : unitSplit = unitid.split(",")) {
                    unitStr = unitStr + unit + ",";
                }
                FormSchemeDefine formScheme = ETLPlanTaskRunner.this.runTimeViewController.getFormScheme(formSchemeKey);
                String mainDimName = "";
                mainDimName = ETLPlanTaskRunner.this.entityMetaService.getDimensionName(formScheme.getDw());
                dimensionValue.setName(mainDimName);
                dimensionValue.setValue(unitStr);
                dim.put(mainDimName, dimensionValue);
            }
            if (period != null) {
                dimensionValue = new DimensionValue();
                dimensionValue.setName("DATATIME");
                dimensionValue.setValue(period);
                dim.put("DATATIME", dimensionValue);
            }
            return ETLPlanTaskRunner.this.queryEntity.getFormList(formSchemeKey, taskKey, dim);
        }

        private NpContextImpl buildContext(String userName) throws JQException {
            NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
            npContext.setTenant("__default_tenant__");
            NpContextUser contextUser = this.buildUserContext(userName);
            npContext.setUser((ContextUser)contextUser);
            NpContextIdentity identity = this.buildIdentityContext(contextUser);
            npContext.setIdentity((ContextIdentity)identity);
            return npContext;
        }

        private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(contextUser.getId());
            identity.setTitle(contextUser.getFullname());
            return identity;
        }

        private NpContextUser buildUserContext(String userName) throws JQException {
            NpContextUser userContext = new NpContextUser();
            User user = this.getUserByUserName(userName);
            if (user == null) {
                throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
            }
            userContext.setId(user.getId());
            userContext.setName(user.getName());
            userContext.setNickname(user.getNickname());
            userContext.setDescription(user.getDescription());
            return userContext;
        }

        private User getUserByUserName(String userName) {
            if (StringUtils.isEmpty((String)userName)) {
                return null;
            }
            Optional user = ETLPlanTaskRunner.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = ETLPlanTaskRunner.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
        }
    }
}

