/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.authz.bean.UserQueryParam
 *  com.jiuqi.nr.authz.bean.UserTreeNode
 *  com.jiuqi.nr.authz.service.IUserTreeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.reminder.infer.ReminderRepository
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 */
package com.jiuqi.gcreport.inputdata.runner.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.runner.service.InputDataOffsetRemindService;
import com.jiuqi.gcreport.inputdata.runner.vo.InputDataMessageRemindConfigVO;
import com.jiuqi.gcreport.inputdata.runner.vo.InputDataRemindRuleSetInfoCondition;
import com.jiuqi.gcreport.inputdata.runner.vo.InputDataRemindRuleSetVO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.authz.bean.UserQueryParam;
import com.jiuqi.nr.authz.bean.UserTreeNode;
import com.jiuqi.nr.authz.service.IUserTreeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InputDataOffsetRemindServiceImpl
implements InputDataOffsetRemindService {
    private final String PERIOD = "\\{DATATIME\\}";
    private final String TIME = "\\{TIME\\}";
    private final String COUNT = "\\{COUNT\\}";
    private final String ORGCODE = "\\{ORGCODE\\}";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private IUserTreeService iUserTreeService;
    @Autowired
    private VaMessageClient messageClient;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private UserService<User> userService;

    @Override
    public boolean execute(InputDataMessageRemindConfigVO inputDataMessageRemindConfig, StringBuffer massage) {
        if (StringUtils.isEmpty((String)inputDataMessageRemindConfig.getTaskId())) {
            massage.append("\u4efb\u52a1ID\u4e3a\u7a7a\n");
            return false;
        }
        List<InputDataRemindRuleSetVO> ruleSetDatas = inputDataMessageRemindConfig.getRuleSetData();
        if (CollectionUtils.isEmpty(ruleSetDatas)) {
            massage.append("\u63d0\u9192\u89c4\u5219\u8bbe\u7f6e\u672a\u914d\u7f6e\n");
            return false;
        }
        if (CollectionUtils.isEmpty(inputDataMessageRemindConfig.getUserAndRoleData())) {
            massage.append("\u63a5\u6536\u7528\u6237\u89d2\u8272\u4fe1\u606f\u672a\u914d\u7f6e\n");
            return false;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(inputDataMessageRemindConfig.getTaskId());
        String periodStr = this.getPeriodStr(inputDataMessageRemindConfig.getPeriodTypeSet(), inputDataMessageRemindConfig.getPeriodStr(), taskDefine.getPeriodType().type());
        String orgTableName = inputDataMessageRemindConfig.getOrgType();
        if (StringUtils.isEmpty((String)orgTableName)) {
            orgTableName = DimensionUtils.getDwEntitieTableByTaskKey((String)inputDataMessageRemindConfig.getTaskId());
        }
        YearPeriodDO yearPeriodDO = YearPeriodUtil.transformByDataSchemeKey((String)taskDefine.getDataScheme(), (String)periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTableName, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodDO)yearPeriodDO);
        HashSet<String> unitCodes = new HashSet<String>();
        HashSet<String> oppUnitCodes = new HashSet<String>();
        HashSet<String> ruleIds = new HashSet<String>();
        ArrayList<InputDataRemindRuleSetInfoCondition> ruleSetInfoConditions = new ArrayList<InputDataRemindRuleSetInfoCondition>();
        for (InputDataRemindRuleSetVO inputDataRemindRuleSetVO : ruleSetDatas) {
            List<UnionRuleVO> rules;
            List<GcOrgCacheVO> oppUnits;
            List<GcOrgCacheVO> units = inputDataRemindRuleSetVO.getUnits();
            InputDataRemindRuleSetInfoCondition condition = new InputDataRemindRuleSetInfoCondition();
            if (!CollectionUtils.isEmpty(units)) {
                Set<String> orgCodes = this.orgTreeToSet(units, tool);
                unitCodes.addAll(orgCodes);
                condition.setUnitCodes(orgCodes);
            }
            if (!CollectionUtils.isEmpty(oppUnits = inputDataRemindRuleSetVO.getOppUnits())) {
                Set<String> orgCodes = this.orgTreeToSet(oppUnits, tool);
                oppUnitCodes.addAll(orgCodes);
                condition.setOppUnitCodes(orgCodes);
            }
            if (!CollectionUtils.isEmpty(rules = inputDataRemindRuleSetVO.getRules())) {
                Set<String> ruleCodes = this.getUnionRuleChildrenIds(rules);
                ruleIds.addAll(ruleCodes);
                condition.setRuleIds(ruleCodes);
            }
            ruleSetInfoConditions.add(condition);
        }
        List<Map<String, Object>> inputDataItems = this.listInputDataByCondition(periodStr, inputDataMessageRemindConfig, unitCodes, oppUnitCodes, ruleIds);
        if (CollectionUtils.isEmpty(inputDataItems)) {
            massage.append("\u67e5\u8be2\u6570\u636e\u4e3a\u7a7a\n");
            return false;
        }
        return this.sendMessage(periodStr, inputDataItems, inputDataMessageRemindConfig, ruleSetInfoConditions, tool, massage);
    }

    private boolean sendMessage(String periodStr, List<Map<String, Object>> inputDataItems, InputDataMessageRemindConfigVO inputDataMessageRemindConfig, List<InputDataRemindRuleSetInfoCondition> ruleSetInfoConditions, GcOrgCenterService tool, StringBuffer massage) {
        Map<String, Set<String>> orgCode2fsUserIdSet;
        List<String> userIds;
        List<String> userAndRoleData = inputDataMessageRemindConfig.getUserAndRoleData();
        try {
            userIds = this.listUserIds(userAndRoleData);
        }
        catch (JQException e) {
            massage.append("\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u5f02\u5e38");
            this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u5f02\u5e38", e);
            return false;
        }
        if (CollectionUtils.isEmpty(userIds)) {
            massage.append("\u83b7\u53d6\u7528\u6237\u4fe1\u606f\u4e3a\u7a7a");
            return false;
        }
        try {
            orgCode2fsUserIdSet = this.getAuthorityOrgCodeMap(userIds, tool);
            NpContextHolder.setContext((NpContext)this.buildContextByUserId(inputDataMessageRemindConfig.getUserId()));
        }
        catch (Exception e) {
            massage.append("\u52a0\u8f7d\u89d2\u8272\u4e0e\u7528\u6237\u4fe1\u606f\u5f02\u5e38");
            this.logger.error("\u52a0\u8f7d\u89d2\u8272\u4e0e\u7528\u6237\u4fe1\u606f\u5f02\u5e38", e);
            return false;
        }
        Map<String, List<Map<String, Object>>> gatherDataGroupByOrgCode = this.listGatherData(inputDataItems, ruleSetInfoConditions, inputDataMessageRemindConfig.getUnitRange());
        String changeDataGather = inputDataMessageRemindConfig.getChangeDataGather();
        ArrayList<GcOrgCacheVO> orgCaches = new ArrayList<GcOrgCacheVO>();
        ArrayList<UnionRuleVO> unionRules = new ArrayList<UnionRuleVO>();
        String dateStr = DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss");
        String mailTitle = inputDataMessageRemindConfig.getMailTitle();
        String periodStrTitle = PeriodUtils.getDateStrFromPeriod((String)periodStr);
        String mailContext = inputDataMessageRemindConfig.getMailContent();
        mailContext = mailContext.replace("\\{DATATIME\\}", periodStrTitle).replace("\\{TIME\\}", dateStr);
        for (List<Map<String, Object>> sameGroupItems : gatherDataGroupByOrgCode.values()) {
            List<String> receiveUserIds;
            Map item2;
            Integer sumCount;
            GcOrgCacheVO orgOppUnitVO;
            String oppUnitCode;
            Map<String, List<Map>> inputDataGroupByOppUnitCode;
            Map<String, Object> firstItem;
            if (CollectionUtils.isEmpty(sameGroupItems) || Objects.isNull((firstItem = sameGroupItems.get(0)).get("MDCODE"))) continue;
            String mdCode = String.valueOf(firstItem.get("MDCODE"));
            GcOrgCacheVO orgUnitVO = this.getGcOrgCache(orgCaches, mdCode, tool);
            if (Objects.isNull(orgUnitVO)) {
                this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u67e5\u8be2\u672c\u65b9\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c\u3010code\u3011:" + mdCode);
                continue;
            }
            String orgMailTitle = mailTitle.replace("\\{ORGCODE\\}", orgUnitVO.getTitle());
            String orgMailContext = mailContext.replace("\\{ORGCODE\\}", orgUnitVO.getTitle());
            if ("unit".equals(changeDataGather)) {
                Integer sumCount2 = this.getSumCount(sameGroupItems);
                orgMailContext = orgMailContext.replace("\\{COUNT\\}", sumCount2 + "\u6761");
            }
            if ("oppUnit".equals(changeDataGather)) {
                inputDataGroupByOppUnitCode = sameGroupItems.stream().collect(Collectors.groupingBy(item -> String.valueOf(item.get("MDCODE")) + "@" + String.valueOf(item.get("OPPUNITID"))));
                StringBuffer oppUnitMailContext = new StringBuffer("");
                for (List<Map<String, Object>> list : inputDataGroupByOppUnitCode.values()) {
                    Map item22 = list.get(0);
                    if (Objects.isNull(item22.get("OPPUNITID"))) continue;
                    oppUnitCode = String.valueOf(item22.get("OPPUNITID"));
                    orgOppUnitVO = this.getGcOrgCache(orgCaches, oppUnitCode, tool);
                    if (Objects.isNull(orgOppUnitVO)) {
                        this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u67e5\u8be2\u5bf9\u65b9\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c\u3010code\u3011:" + mdCode);
                        continue;
                    }
                    sumCount = this.getSumCount(list);
                    if (oppUnitMailContext.length() <= 0) {
                        oppUnitMailContext.append("\u4e0e");
                    } else {
                        oppUnitMailContext.append("\uff1b");
                    }
                    oppUnitMailContext.append(orgOppUnitVO.getTitle()).append(sumCount).append("\u6761");
                }
                orgMailContext = orgMailContext.replace("\\{COUNT\\}", oppUnitMailContext.toString());
            }
            if ("oppUnitAndRule".equals(changeDataGather)) {
                inputDataGroupByOppUnitCode = sameGroupItems.stream().collect(Collectors.groupingBy(item -> String.valueOf(item.get("MDCODE")) + "@" + String.valueOf(item.get("OPPUNITID")) + "@" + String.valueOf(item.get("UNIONRULEID"))));
                StringBuffer oppUnitAndUnitMailContext = new StringBuffer("");
                for (List<Map<String, Object>> list : inputDataGroupByOppUnitCode.values()) {
                    item2 = list.get(0);
                    if (Objects.isNull(item2.get("OPPUNITID")) || Objects.isNull(item2.get("UNIONRULEID"))) continue;
                    oppUnitCode = String.valueOf(item2.get("OPPUNITID"));
                    orgOppUnitVO = this.getGcOrgCache(orgCaches, oppUnitCode, tool);
                    if (Objects.isNull(orgOppUnitVO)) {
                        this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u67e5\u8be2\u5bf9\u65b9\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c\u3010code\u3011:" + mdCode);
                        continue;
                    }
                    String ruleId = String.valueOf(item2.get("UNIONRULEID"));
                    UnionRuleVO unionRule = this.getUnionRule(unionRules, ruleId);
                    if (Objects.isNull(unionRule)) continue;
                    Integer sumCount3 = this.getSumCount(list);
                    if (oppUnitAndUnitMailContext.length() <= 0) {
                        oppUnitAndUnitMailContext.append("\u4e0e");
                    } else {
                        oppUnitAndUnitMailContext.append(";");
                    }
                    oppUnitAndUnitMailContext.append(orgOppUnitVO.getTitle()).append("\uff0c").append(unionRule.getTitle()).append(sumCount3).append("\u6761");
                }
                orgMailContext = orgMailContext.replace("\\{COUNT\\}", oppUnitAndUnitMailContext.toString());
            }
            if ("unitAndRule".equals(changeDataGather)) {
                inputDataGroupByOppUnitCode = sameGroupItems.stream().collect(Collectors.groupingBy(item -> String.valueOf(item.get("MDCODE")) + "@" + String.valueOf(item.get("UNIONRULEID"))));
                StringBuffer unitRuleMailContext = new StringBuffer("");
                for (List<Map<String, Object>> list : inputDataGroupByOppUnitCode.values()) {
                    String ruleId;
                    UnionRuleVO unionRule;
                    item2 = list.get(0);
                    if (Objects.isNull(item2.get("UNIONRULEID")) || Objects.isNull(unionRule = this.getUnionRule(unionRules, ruleId = String.valueOf(item2.get("UNIONRULEID"))))) continue;
                    sumCount = this.getSumCount(list);
                    if (unitRuleMailContext.length() <= 0) {
                        unitRuleMailContext.append("\u4e0e");
                    } else {
                        unitRuleMailContext.append("\uff1b");
                    }
                    unitRuleMailContext.append(unionRule.getTitle()).append(sumCount).append("\u6761");
                }
                orgMailContext = orgMailContext.replace("\\{COUNT\\}", unitRuleMailContext.toString());
            }
            if (CollectionUtils.isEmpty(receiveUserIds = orgCode2fsUserIdSet.get(mdCode).stream().collect(Collectors.toList()))) continue;
            this.sendMessage(inputDataMessageRemindConfig.getRemindType(), orgMailTitle, orgMailContext, receiveUserIds);
            this.logger.info("\u5355\u4f4d\uff1a" + orgUnitVO.getTitle() + "\u4fe1\u606f\u53d1\u9001\u6210\u529f, \u7528\u6237id:" + receiveUserIds.toString());
            massage.append("\u5355\u4f4d\uff1a").append(orgUnitVO.getTitle()).append("\u4fe1\u606f\u53d1\u9001\u6210\u529f\n");
        }
        return true;
    }

    private void sendMessage(String remindType, String mailTitle, String mailContext, List<String> receiveUserIds) {
        VaMessageSendDTO infoDO = new VaMessageSendDTO();
        infoDO.setAllUsers(false);
        infoDO.setReceiveUserIds(receiveUserIds);
        infoDO.setNoticeflag(false);
        if ("mail".equals(remindType)) {
            infoDO.setMsgChannel(VaMessageOption.MsgChannel.EMAIL);
        } else {
            infoDO.setMsgChannel(VaMessageOption.MsgChannel.PC);
        }
        infoDO.setTitle(mailTitle);
        infoDO.setContent(mailContext);
        infoDO.setGrouptype("\u901a\u77e5");
        infoDO.setMsgtype("\u5f85\u529e\u901a\u77e5");
        this.messageClient.addMsg(infoDO);
    }

    private Map<String, Set<String>> getAuthorityOrgCodeMap(List<String> userIdList, GcOrgCenterService tool) throws UnauthorizedEntityException, JQException {
        HashMap<String, Set<String>> orgCode2userIdSet = new HashMap<String, Set<String>>();
        for (String userId : userIdList) {
            NpContextHolder.setContext((NpContext)this.buildContextByUserId(userId));
            List orgTree = tool.getOrgChildrenTree(null);
            if (CollectionUtils.isEmpty((Collection)orgTree)) continue;
            for (GcOrgCacheVO org : orgTree) {
                if (Objects.isNull(org)) continue;
                String orgCode = org.getCode();
                if (org.isLeaf()) {
                    Set userIdSet = orgCode2userIdSet.getOrDefault(orgCode, new HashSet());
                    userIdSet.add(userId);
                    orgCode2userIdSet.put(orgCode, userIdSet);
                    continue;
                }
                List allOrgByParentId = tool.listAllOrgByParentIdContainsSelf(orgCode);
                if (CollectionUtils.isEmpty((Collection)allOrgByParentId)) continue;
                allOrgByParentId.forEach(item -> {
                    if (Objects.isNull(item)) {
                        return;
                    }
                    Set userIdSet = orgCode2userIdSet.getOrDefault(item.getCode(), new HashSet());
                    userIdSet.add(userId);
                    orgCode2userIdSet.put(item.getCode(), userIdSet);
                });
            }
        }
        return orgCode2userIdSet;
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

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
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
        userContext.setOrgCode(user.getOrgCode());
        return userContext;
    }

    private User getUserById(String userId) {
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)userId)) {
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

    private Integer getSumCount(List<Map<String, Object>> inputDataItems) {
        Integer count = 0;
        for (Map<String, Object> item : inputDataItems) {
            count = count + NumberUtils.formatObject((Object)item.get("GATHERSUM")).intValue();
        }
        return count;
    }

    private GcOrgCacheVO getGcOrgCache(List<GcOrgCacheVO> orgCaches, String code, GcOrgCenterService tool) {
        List existUnitOrgs = orgCaches.stream().filter(org -> code.equals(org.getCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(existUnitOrgs)) {
            GcOrgCacheVO orgCacheVO = tool.getOrgByCode(code);
            if (Objects.isNull(orgCacheVO)) {
                this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u67e5\u8be2\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c\u3010code\u3011:" + code);
                return null;
            }
            orgCaches.add(orgCacheVO);
            return orgCacheVO;
        }
        return (GcOrgCacheVO)existUnitOrgs.get(0);
    }

    private UnionRuleVO getUnionRule(List<UnionRuleVO> unionRules, String ruleId) {
        List existUnionRule = unionRules.stream().filter(rule -> ruleId.equals(rule.getId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(existUnionRule)) {
            UnionRuleVO unionRule = this.unionRuleService.selectUnionRuleById(ruleId);
            if (Objects.isNull(unionRule)) {
                this.logger.error("\u5185\u90e8\u8868\u6570\u636e\u53d8\u52a8\u63d0\u9192\u8ba1\u5212\u4efb\u52a1\u67e5\u8be2\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c\u3010ruleId\u3011:" + ruleId);
                return null;
            }
            unionRules.add(unionRule);
            return unionRule;
        }
        return (UnionRuleVO)existUnionRule.get(0);
    }

    private Map<String, List<Map<String, Object>>> listGatherData(List<Map<String, Object>> inputDataItems, List<InputDataRemindRuleSetInfoCondition> ruleSetInfoConditions, String unitRang) {
        Set oppUnitAndUnitCodeAndRuleIds = inputDataItems.stream().map(item -> String.valueOf(item.get("OPPUNITID")) + "@" + String.valueOf(item.get("MDCODE"))).collect(Collectors.toSet());
        Set unitAndOppUnitCodeAndRuleIds = inputDataItems.stream().map(item -> String.valueOf(item.get("MDCODE")) + "@" + String.valueOf(item.get("OPPUNITID"))).collect(Collectors.toSet());
        HashMap<String, List<Map<String, Object>>> gatherDataGroupByOrgCode = new HashMap<String, List<Map<String, Object>>>();
        for (InputDataRemindRuleSetInfoCondition infoCondition : ruleSetInfoConditions) {
            Set<String> unitCodes = infoCondition.getUnitCodes();
            Set<String> oppUnitCodes = infoCondition.getOppUnitCodes();
            Set<String> ruleIds = infoCondition.getRuleIds();
            if ("unit".equals(unitRang)) {
                for (String orgCode : unitCodes) {
                    boolean isExist = oppUnitAndUnitCodeAndRuleIds.stream().anyMatch(value -> !StringUtils.isEmpty((String)value) && value.startsWith(orgCode + "@"));
                    if (isExist) continue;
                    inputDataItems.forEach(item -> {
                        String mdCode = String.valueOf(item.get("MDCODE"));
                        String oppUnitCode = String.valueOf(item.get("OPPUNITID"));
                        String ruleId = String.valueOf(item.get("UNIONRULEID"));
                        boolean filterFlag = false;
                        if (orgCode.equals(mdCode)) {
                            filterFlag = !CollectionUtils.isEmpty((Collection)oppUnitCodes) ? (oppUnitCodes.contains(oppUnitCode) ? (!CollectionUtils.isEmpty((Collection)ruleIds) ? ruleIds.contains(ruleId) : true) : false) : (!CollectionUtils.isEmpty((Collection)ruleIds) ? ruleIds.contains(ruleId) : true);
                        }
                        if (filterFlag) {
                            List sameGroupItems = gatherDataGroupByOrgCode.computeIfAbsent(mdCode, k -> new ArrayList());
                            sameGroupItems.add(item);
                        }
                    });
                }
                continue;
            }
            if ("oppUnit".equals(unitRang)) {
                for (String orgCode : unitCodes) {
                    boolean isExist = unitAndOppUnitCodeAndRuleIds.stream().anyMatch(value -> !StringUtils.isEmpty((String)value) && value.startsWith(orgCode + "@"));
                    if (isExist) continue;
                    inputDataItems.forEach(item -> {
                        String mdCode = String.valueOf(item.get("MDCODE"));
                        String oppUnitCode = String.valueOf(item.get("OPPUNITID"));
                        String ruleId = String.valueOf(item.get("UNIONRULEID"));
                        boolean filterFlag = false;
                        if (orgCode.equals(oppUnitCode)) {
                            filterFlag = !CollectionUtils.isEmpty((Collection)ruleIds) ? ruleIds.contains(ruleId) : true;
                        }
                        if (filterFlag) {
                            List sameGroupItems = gatherDataGroupByOrgCode.computeIfAbsent(mdCode, k -> new ArrayList());
                            sameGroupItems.add(item);
                        }
                    });
                }
                continue;
            }
            HashSet intersectionData = CollectionUtils.newHashSet(unitAndOppUnitCodeAndRuleIds);
            intersectionData.retainAll(oppUnitAndUnitCodeAndRuleIds);
            for (String orgCode : unitCodes) {
                boolean isExist = intersectionData.stream().anyMatch(value -> !StringUtils.isEmpty((String)value) && value.startsWith(orgCode + "@"));
                if (!isExist) continue;
                inputDataItems.forEach(item -> {
                    String mdCode = String.valueOf(item.get("MDCODE"));
                    String oppUnitCode = String.valueOf(item.get("OPPUNITID"));
                    String ruleId = String.valueOf(item.get("UNIONRULEID"));
                    String unitAndOppUnitCodeAndRuleId = mdCode + "@" + oppUnitCode;
                    boolean filterFlag = false;
                    if (intersectionData.contains(unitAndOppUnitCodeAndRuleId) && orgCode.equals(mdCode)) {
                        filterFlag = !CollectionUtils.isEmpty((Collection)oppUnitCodes) ? (oppUnitCodes.contains(oppUnitCode) ? (!CollectionUtils.isEmpty((Collection)ruleIds) ? ruleIds.contains(ruleId) : true) : false) : (!CollectionUtils.isEmpty((Collection)ruleIds) ? ruleIds.contains(ruleId) : true);
                    }
                    if (filterFlag) {
                        List sameGroupItems = gatherDataGroupByOrgCode.computeIfAbsent(mdCode, k -> new ArrayList());
                        sameGroupItems.add(item);
                    }
                });
            }
        }
        return gatherDataGroupByOrgCode;
    }

    private List<String> listUserIds(List<String> userIdEmailList) throws JQException {
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

    private String getPeriodStr(String periodTypeSet, String periodStr, int periodType) {
        if ("custom".equals(periodTypeSet)) {
            return periodStr;
        }
        GregorianCalendar gregorianCalendar = PeriodUtil.getCurrentCalendar();
        if ("before".equals(periodTypeSet)) {
            PeriodWrapper periodWrapper = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)periodType, (int)-1);
            return periodWrapper.toString();
        }
        PeriodWrapper periodWrapper = PeriodUtil.currentPeriod((GregorianCalendar)gregorianCalendar, (int)periodType, (int)0);
        return periodWrapper.toString();
    }

    private Set<String> getUnionRuleChildrenIds(List<UnionRuleVO> ruleVos) {
        HashSet<String> ruleIds = new HashSet<String>();
        for (UnionRuleVO rule : ruleVos) {
            String ruleId;
            if (Objects.isNull(rule) || StringUtils.isEmpty((String)(ruleId = rule.getId())) || "empty".equals(ruleId) || ruleIds.contains(ruleId)) continue;
            if (Boolean.TRUE.equals(rule.getLeafFlag())) {
                ruleIds.add(ruleId);
            }
            List rules = this.unionRuleService.selectAllChildrenRuleEo(ruleId);
            for (UnionRuleEO childrenRule : rules) {
                if (!Objects.nonNull(childrenRule)) continue;
                ruleIds.add(childrenRule.getId());
            }
        }
        return ruleIds;
    }

    private Set<String> orgTreeToSet(List<GcOrgCacheVO> orgCodes, GcOrgCenterService tool) {
        if (CollectionUtils.isEmpty(orgCodes)) {
            return Collections.emptySet();
        }
        HashSet<String> orgIds = new HashSet<String>();
        for (GcOrgCacheVO orgCache : orgCodes) {
            GcOrgCacheVO cacheVO;
            if (Objects.isNull(orgCache) || Objects.isNull(cacheVO = tool.getOrgByCode(orgCache.getCode()))) continue;
            if (cacheVO.isLeaf()) {
                orgIds.add(cacheVO.getCode());
                continue;
            }
            List childrenOrg = tool.listAllOrgByParentIdContainsSelf(cacheVO.getCode());
            if (CollectionUtils.isEmpty((Collection)childrenOrg)) continue;
            orgIds.addAll(childrenOrg.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet()));
        }
        return orgIds;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Map<String, Object>> listInputDataByCondition(String periodStr, InputDataMessageRemindConfigVO inputDataMessageRemindConfig, Set<String> unitCodes, Set<String> oppUnitCodes, Set<String> ruleIds) {
        List inputDataItems;
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataMessageRemindConfig.getTaskId(), periodStr);
        if (StringUtils.isEmpty((String)systemId)) {
            return null;
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataMessageRemindConfig.getTaskId());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<String> offsetGroupIds = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MDCODE,OPPUNITID ,UNIONRULEID,count(1) GATHERSUM FROM ").append(tableName).append(" \n");
        String whereSql = this.getWhereSql(inputDataMessageRemindConfig.getTaskId(), systemId, periodStr, inputDataMessageRemindConfig.getSelectAdjustCode(), unitCodes, oppUnitCodes, ruleIds, offsetGroupIds);
        sql.append(whereSql).append(" \n").append(" GROUP BY MDCODE,OPPUNITID ,UNIONRULEID ");
        try {
            inputDataItems = dao.selectMap(sql.toString(), new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupIds(offsetGroupIds);
        }
        if (CollectionUtils.isEmpty((Collection)inputDataItems)) {
            return null;
        }
        return inputDataItems;
    }

    private List<String> getSelectColumns(List<String> changeDataGather) {
        ArrayList<String> selectColumns = new ArrayList<String>();
        if (CollectionUtils.isEmpty(changeDataGather)) {
            selectColumns.add("MDCODE");
            return selectColumns;
        }
        Iterator<String> iterator = changeDataGather.iterator();
        block12: while (iterator.hasNext()) {
            String gatherStr;
            switch (gatherStr = iterator.next()) {
                case "unit": {
                    selectColumns.add("MDCODE");
                    continue block12;
                }
                case "oppUnit": {
                    selectColumns.add("MDCODE");
                    selectColumns.add("OPPUNITID");
                    continue block12;
                }
                case "oppUnitAndRule": {
                    selectColumns.add("MDCODE");
                    selectColumns.add("OPPUNITID");
                    selectColumns.add("UNIONRULEID");
                    continue block12;
                }
                case "unitAndRule": {
                    selectColumns.add("MDCODE");
                    selectColumns.add("UNIONRULEID");
                    continue block12;
                }
            }
            selectColumns.add("MDCODE");
        }
        return selectColumns;
    }

    private String getWhereSql(String taskId, String systemId, String periodStr, String selectAdjustCode, Set<String> unitCodes, Set<String> oppUnitCodes, Set<String> ruleIds, List<String> offsetGroupIds) {
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" where \n").append(" REPORTSYSTEMID='").append(systemId).append("'\n").append(" and \n").append(" datatime='").append(periodStr).append("'\n").append(" and \n").append(" OFFSETSTATE='0'\n").append(" and \n").append(" MD_CURRENCY='CNY'\n");
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            whereSql.append(" and ").append("ADJUST").append("='").append(selectAdjustCode).append("'\n");
        }
        HashSet orgCodes = CollectionUtils.newHashSet(unitCodes);
        if (!CollectionUtils.isEmpty(oppUnitCodes)) {
            orgCodes.addAll(oppUnitCodes);
        }
        if (!CollectionUtils.isEmpty((Collection)orgCodes)) {
            TempTableCondition unitCondition = SqlUtils.getConditionOfIds((Collection)orgCodes, (String)"MDCODE");
            whereSql.append(" and (").append(unitCondition.getCondition());
            offsetGroupIds.add(unitCondition.getTempGroupId());
            TempTableCondition oppUnitCondition = SqlUtils.getConditionOfIds((Collection)orgCodes, (String)"OPPUNITID");
            whereSql.append(" OR ").append(oppUnitCondition.getCondition()).append(") \n");
            offsetGroupIds.add(oppUnitCondition.getTempGroupId());
        }
        if (!CollectionUtils.isEmpty(ruleIds)) {
            TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(ruleIds, (String)"UNIONRULEID");
            whereSql.append(" and ").append(tempTableCondition.getCondition()).append("\n");
            offsetGroupIds.add(tempTableCondition.getTempGroupId());
        }
        return whereSql.toString();
    }
}

