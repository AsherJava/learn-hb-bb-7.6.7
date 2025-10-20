/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.delegate.DelegateDO
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.delegate.DelegateService
 *  com.jiuqi.va.domain.delegate.DelegateType
 *  com.jiuqi.va.domain.delegate.DelegateVO
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.todo.TodoQueryDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.delegate.DelegateDO;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.delegate.DelegateService;
import com.jiuqi.va.domain.delegate.DelegateType;
import com.jiuqi.va.domain.delegate.DelegateVO;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.todo.TodoQueryDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.dao.DelegateDao;
import com.jiuqi.va.workflow.service.impl.DelegateHandler;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class DelegateServiceImpl
implements DelegateService {
    private static final Logger logger = LoggerFactory.getLogger(DelegateServiceImpl.class);
    @Autowired
    private DelegateDao delegateDao;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private DelegateHandler delegateHandler;
    @Autowired
    private WorkflowOptionService optionService;
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Value(value="${jiuqi.va.staff-basedata.enableposts:false}")
    private boolean staffPartTimeJobFlag;

    @Transactional(rollbackFor={Exception.class})
    public R add(DelegateDTO delegatedto) {
        Map<String, Object> map = VaWorkflowUtils.getMap(delegatedto.getExtInfo("delegateInfo"));
        if (ObjectUtils.isEmpty(map)) {
            this.addDelegateInfo(delegatedto);
        } else {
            for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
                String bizType = mapEntry.getKey();
                Object value = mapEntry.getValue();
                DelegateDTO delegateDTO = (DelegateDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)value), DelegateDTO.class);
                delegateDTO.setBizType(bizType);
                this.addDelegateInfo(delegateDTO);
            }
        }
        LogUtil.add((String)"\u5de5\u4f5c\u6d41\u59d4\u6258", (String)"\u65b0\u5efa", (String)"\u59d4\u6258", (String)delegatedto.getId(), (String)JSONUtil.toJSONString((Object)delegatedto));
        return R.ok();
    }

    private void addDelegateInfo(DelegateDTO delegatedto) {
        if (delegatedto.getInvaldate() != null) {
            this.formatInvaldate((DelegateDO)delegatedto);
        }
        long nowtime = System.currentTimeMillis();
        long tempinvaldate = delegatedto.getInvaldate() == null ? nowtime + 1000L : delegatedto.getInvaldate().getTime();
        String userid = ShiroUtil.getUser().getId().toString();
        delegatedto.setId(UUID.randomUUID().toString());
        delegatedto.setCreater(userid);
        delegatedto.setCreatetime(new Date(nowtime));
        delegatedto.setEnableflag(BigDecimal.ZERO);
        boolean isStaff = VaWorkFlowDataUtils.getChooseStaffOption();
        if (isStaff) {
            this.handleStaffInfo(delegatedto);
        } else {
            delegatedto.setAgentuser(JSONUtil.toJSONString((Object)delegatedto.getAgentusers()));
        }
        delegatedto.setDelecondition(JSONUtil.toJSONString((Object)delegatedto.getDeleconditions()));
        this.delegateDao.insert(delegatedto);
        if (delegatedto.getValdate().getTime() < nowtime && nowtime < tempinvaldate && delegatedto.getHisdeleflag().compareTo(BigDecimal.ONE) == 0) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setBackendRequest(true);
            taskDTO.setFilterDelegate(true);
            taskDTO.setParticipant(delegatedto.getDelegateuser());
            PageVO unfinished = this.todoClient.listUnfinished(taskDTO);
            if (unfinished.getRs().getCode() == 0 && !unfinished.getRows().isEmpty()) {
                List<Map<String, Object>> todotasks = this.delegateHandler.filterByCondition(delegatedto, unfinished.getRows());
                this.delegateHandler.send(todotasks, (DelegateDO)delegatedto, null);
            }
        }
    }

    private void handleStaffInfo(DelegateDTO delegatedto) {
        List staffs = delegatedto.getStaffs();
        if (CollectionUtils.isEmpty(staffs)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.staffisempty"));
        }
        HashSet<String> userSet = new HashSet<String>();
        ArrayList<String> staffCodes = new ArrayList<String>();
        for (Map staffMap : staffs) {
            String staffCode = (String)staffMap.get("staffCode");
            String staffName = (String)staffMap.get("staffName");
            if (!StringUtils.hasText(staffCode)) {
                throw new RuntimeException(staffName + VaWorkFlowI18nUtils.getInfo("va.workflow.staffcodeisempty"));
            }
            if (this.staffPartTimeJobFlag) {
                BaseDataDO staffPost = VaWorkFlowDataUtils.getStaffPostInfo(staffCode);
                if (staffPost == null) {
                    throw new RuntimeException(staffName + VaWorkFlowI18nUtils.getInfo("va.workflow.staffpositionisempty"));
                }
                String staffObjectcode = (String)staffPost.get((Object)"staffcode");
                if (!StringUtils.hasText(staffObjectcode)) {
                    throw new RuntimeException(staffName + VaWorkFlowI18nUtils.getInfo("va.workflow.staffnotexists"));
                }
                this.checkStaffInfo(userSet, staffCodes, staffCode, staffName, staffObjectcode);
                continue;
            }
            this.checkStaffInfo(userSet, staffCodes, staffCode, staffName, staffCode);
        }
        delegatedto.setStaffcode(JSONUtil.toJSONString(staffCodes));
        delegatedto.setAgentuser(JSONUtil.toJSONString(userSet));
    }

    private void checkStaffInfo(Set<String> userSet, List<String> staffCodes, String staffCode, String staffName, String staffObjectcode) {
        BaseDataDO staff = VaWorkFlowDataUtils.getStaff(staffObjectcode);
        if (staff == null) {
            throw new RuntimeException(staffName + VaWorkFlowI18nUtils.getInfo("va.workflow.staffnotexists"));
        }
        String linkuser = (String)staff.get((Object)"linkuser");
        if (!StringUtils.hasText(linkuser)) {
            throw new RuntimeException(staffName + VaWorkFlowI18nUtils.getInfo("va.workflow.staffassociateduserisempty"));
        }
        UserDO userDO = VaWorkFlowDataUtils.getOneUserData(ShiroUtil.getTenantName(), linkuser);
        if (userDO == null || userDO.getStopflag() != 0) {
            throw new RuntimeException(staffName + VaWorkFlowI18nUtils.getInfo("va.workflow.staffassociateduserisempty") + VaWorkFlowI18nUtils.getInfo("va.workflow.or") + VaWorkFlowI18nUtils.getInfo("va.workflow.staffassociateduserhasstopped"));
        }
        userSet.add(linkuser);
        staffCodes.add(staffCode);
    }

    @Transactional(rollbackFor={Exception.class})
    public R update(DelegateDTO delegatedto) {
        long tempinvaldate;
        DelegateDO doTemp;
        if (delegatedto.getInvaldate() != null) {
            this.formatInvaldate((DelegateDO)delegatedto);
        }
        if ((doTemp = this.delegateDao.selectByPk(delegatedto)) == null) {
            return R.error((int)1, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.currenttrustdeleted"));
        }
        if (doTemp.getInvaldate() != null) {
            this.formatInvaldate(doTemp);
        }
        long nowtime = System.currentTimeMillis();
        long tempinval = delegatedto.getInvaldate() == null ? nowtime + 1000L : delegatedto.getInvaldate().getTime();
        long l = tempinvaldate = doTemp.getInvaldate() == null ? nowtime + 1000L : doTemp.getInvaldate().getTime();
        if (!((doTemp.getEnableflag() == null || !doTemp.getEnableflag().equals(BigDecimal.ONE)) && tempinvaldate >= nowtime || doTemp.getValdate().equals(delegatedto.getValdate()) && Objects.equals(doTemp.getInvaldate(), delegatedto.getInvaldate()))) {
            return R.error((int)3, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.delegateinfoone"));
        }
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDelegateId(delegatedto.getId());
        taskDTO.setBackendRequest(true);
        PageVO listUnfinished = this.todoClient.listUnfinished(taskDTO);
        if (listUnfinished.getRs().getCode() == 0 && !listUnfinished.getRows().isEmpty() && !delegatedto.isCheckflag()) {
            return R.error((int)2, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.delegateinfotwo"));
        }
        boolean isStaff = VaWorkFlowDataUtils.getChooseStaffOption();
        if (isStaff) {
            this.handleStaffInfo(delegatedto);
        } else {
            delegatedto.setAgentuser(JSONUtil.toJSONString((Object)delegatedto.getAgentusers()));
        }
        delegatedto.setDelecondition(JSONUtil.toJSONString((Object)delegatedto.getDeleconditions()));
        this.delegateDao.updateByPk(delegatedto);
        this.delegateHandler.retake(listUnfinished.getRows(), doTemp, null);
        if (delegatedto.getValdate().getTime() < nowtime && nowtime < tempinval && (doTemp.getEnableflag() == null || doTemp.getEnableflag().equals(BigDecimal.ZERO)) && BigDecimal.ONE.equals(delegatedto.getHisdeleflag())) {
            TaskDTO tempTaskDTO = new TaskDTO();
            tempTaskDTO.setBackendRequest(true);
            tempTaskDTO.setFilterDelegate(true);
            tempTaskDTO.setParticipant(delegatedto.getDelegateuser());
            PageVO unfinished = this.todoClient.listUnfinished(tempTaskDTO);
            if (unfinished.getRs().getCode() == 0 && !unfinished.getRows().isEmpty()) {
                List<Map<String, Object>> todotasks = this.delegateHandler.filterByCondition(delegatedto, unfinished.getRows());
                this.delegateHandler.send(todotasks, (DelegateDO)delegatedto, null);
            }
        }
        return R.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public R delete(List<DelegateDTO> delegatedtos) {
        ArrayList success = new ArrayList();
        ArrayList faileds = new ArrayList();
        for (DelegateDTO delegateDTO : delegatedtos) {
            HashMap<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("TITLE", delegateDTO.getDelegatetitle());
            try {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setDelegateId(delegateDTO.getId());
                taskDTO.setBackendRequest(true);
                PageVO listUnfinished = this.todoClient.listUnfinished(taskDTO);
                PageVO listHistoryTask = this.todoClient.listHistoryTask(taskDTO);
                TodoQueryDTO todoQueryDTO = new TodoQueryDTO();
                todoQueryDTO.setTabName("archived");
                todoQueryDTO.setDelegateId(delegateDTO.getId());
                todoQueryDTO.setIgnoreUser(true);
                ArrayList<String> queryTypes = new ArrayList<String>();
                queryTypes.add("DONE");
                todoQueryDTO.addExtInfo("queryTypes", queryTypes);
                int archiveCnt = 0;
                try {
                    Map date;
                    R countR = this.todoClient.count(todoQueryDTO);
                    if (countR.getCode() == 0 && !CollectionUtils.isEmpty(date = (Map)countR.get((Object)"data"))) {
                        archiveCnt = (Integer)date.get("donecount");
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                if (listUnfinished.getRs().getCode() == 0 && !listUnfinished.getRows().isEmpty() || listHistoryTask.getRs().getCode() == 0 && !listHistoryTask.getRows().isEmpty() || archiveCnt > 0) {
                    resultMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.delegateinfothree"));
                    faileds.add(resultMap);
                    continue;
                }
                this.delegateDao.deleteByPk(delegateDTO);
                resultMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.dosuccess"));
                success.add(resultMap);
            }
            catch (Exception e) {
                logger.error("\u5220\u9664\u59d4\u6258\u5f02\u5e38", e);
                resultMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.servererror"));
                faileds.add(resultMap);
            }
        }
        R r = R.ok();
        r.put("success", success);
        r.put("faileds", faileds);
        return r;
    }

    @Transactional(rollbackFor={Exception.class})
    public R enable(List<DelegateDTO> delegatedtos) {
        ArrayList success = new ArrayList();
        ArrayList faillist = new ArrayList();
        long nowtime = System.currentTimeMillis();
        for (DelegateDTO delegateDTO : delegatedtos) {
            long tempinvaldate;
            if (delegateDTO.getInvaldate() != null) {
                this.formatInvaldate((DelegateDO)delegateDTO);
            }
            long dtoinvaldate = delegateDTO.getInvaldate() == null ? nowtime + 1000L : delegateDTO.getInvaldate().getTime();
            DelegateDO doTemp = this.delegateDao.selectByPk(delegateDTO);
            HashMap<String, String> resMap = new HashMap<String, String>();
            resMap.put("TITLE", delegateDTO.getDelegatetitle());
            if (doTemp == null) {
                resMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.delegatedeleted"));
                faillist.add(resMap);
                continue;
            }
            long l = tempinvaldate = doTemp.getInvaldate() == null ? nowtime + 1000L : doTemp.getInvaldate().getTime();
            if (tempinvaldate > nowtime && (doTemp.getEnableflag() == null || doTemp.getEnableflag().equals(BigDecimal.ZERO))) {
                resMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.delegateenable"));
                faillist.add(resMap);
                continue;
            }
            delegateDTO.setEnableflag(BigDecimal.ZERO);
            this.delegateDao.enableByPk(delegateDTO);
            if (delegateDTO.getValdate().getTime() < nowtime && nowtime < dtoinvaldate && BigDecimal.ONE.equals(delegateDTO.getHisdeleflag())) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setBackendRequest(true);
                taskDTO.setFilterDelegate(true);
                taskDTO.setParticipant(doTemp.getDelegateuser());
                PageVO unfinished = this.todoClient.listUnfinished(taskDTO);
                if (unfinished.getRs().getCode() == 0 && !unfinished.getRows().isEmpty()) {
                    List<Map<String, Object>> todotasks = this.delegateHandler.filterByCondition(delegateDTO, unfinished.getRows());
                    this.delegateHandler.send(todotasks, doTemp, null);
                }
            }
            resMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.dosuccess"));
            success.add(resMap);
        }
        R r = R.ok();
        r.put("success", success);
        r.put("faileds", faillist);
        return r;
    }

    @Transactional(rollbackFor={Exception.class})
    public R disable(List<DelegateDTO> delegatedtos) {
        ArrayList success = new ArrayList();
        ArrayList faillist = new ArrayList();
        long nowtime = System.currentTimeMillis();
        for (DelegateDTO delegateDTO : delegatedtos) {
            if (delegateDTO.getInvaldate() != null) {
                this.formatInvaldate((DelegateDO)delegateDTO);
            }
            long tempinvaldate = delegateDTO.getInvaldate() == null ? nowtime + 1000L : delegateDTO.getInvaldate().getTime();
            DelegateDO doTemp = this.delegateDao.selectByPk(delegateDTO);
            HashMap<String, String> resMap = new HashMap<String, String>();
            resMap.put("TITLE", delegateDTO.getDelegatetitle());
            if (doTemp == null) {
                resMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.delegatedeleted"));
                faillist.add(resMap);
                continue;
            }
            if (tempinvaldate < nowtime || doTemp.getEnableflag() != null && BigDecimal.ONE.equals(doTemp.getEnableflag())) {
                resMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.deletefailure"));
                faillist.add(resMap);
                continue;
            }
            delegateDTO.setEnableflag(BigDecimal.ONE);
            this.delegateDao.disenableByPk(delegateDTO);
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setBackendRequest(true);
            taskDTO.setDelegateId(doTemp.getId());
            PageVO unfinished = this.todoClient.listUnfinished(taskDTO);
            if (unfinished.getRs().getCode() == 0 && !unfinished.getRows().isEmpty()) {
                this.delegateHandler.retake(unfinished.getRows(), doTemp, null);
            }
            resMap.put("MSG", VaWorkFlowI18nUtils.getInfo("va.workflow.dosuccess"));
            success.add(resMap);
        }
        R r = R.ok();
        r.put("success", success);
        r.put("faileds", faillist);
        return r;
    }

    public List<DelegateVO> query(DelegateDTO delegatedto) {
        try {
            List<DelegateVO> list = this.delegateDao.queryList(delegatedto);
            if (delegatedto.isBackendrequest()) {
                return list;
            }
            return this.convertDelegateVoList(list, delegatedto);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u59d4\u6258\u5931\u8d25", e);
            throw e;
        }
    }

    public int count(DelegateDTO delegatedto) {
        return this.delegateDao.count(delegatedto);
    }

    private List<DelegateVO> convertDelegateVoList(List<DelegateVO> list, DelegateDTO delegateDTO) {
        ArrayList<DelegateVO> resultList = new ArrayList<DelegateVO>();
        HashMap<String, String> userMap = new HashMap<String, String>(16);
        HashMap<String, Map<String, String>> cacheUserMap = new HashMap<String, Map<String, String>>(16);
        HashMap<String, String> userUnitMap = new HashMap<String, String>(16);
        HashMap<String, String> orgMap = new HashMap<String, String>(16);
        HashMap<String, Map<String, String>> cacheOrgMap = new HashMap<String, Map<String, String>>(16);
        boolean isStaff = VaWorkFlowDataUtils.getChooseStaffOption();
        int size = list.size();
        HashMap<String, String> billDefineTitleCache = new HashMap<String, String>(size);
        HashMap<String, Map<String, String>> staffPostCache = null;
        HashMap<String, Map<String, String>> staffCache = null;
        HashMap<String, String> staffPositionCache = null;
        if (isStaff) {
            staffPostCache = new HashMap<String, Map<String, String>>(size);
            staffCache = new HashMap<String, Map<String, String>>(size);
            staffPositionCache = new HashMap<String, String>(size);
        }
        Map<String, String> otherBusinessDefineTitleMap = this.getOtherBusinessDefineTitleMap(list);
        for (DelegateVO delegateVO : list) {
            String bizType = Optional.ofNullable(delegateVO.getBizType()).orElse("BILL");
            String createUser = delegateVO.getCreater();
            this.getOneUserData(userMap, userUnitMap, createUser, delegateVO.getTenantName(), cacheUserMap);
            delegateVO.setCreatertitle((String)userMap.get(createUser));
            this.dealUserDetailInfo(userMap, orgMap, delegateVO, "createUser", userUnitMap, null, null, cacheOrgMap);
            String delegateUser = delegateVO.getDelegateuser();
            this.getOneUserData(userMap, userUnitMap, delegateUser, delegateVO.getTenantName(), cacheUserMap);
            String delegateUserTitle = delegateDTO.getDelegateusertitle();
            if (delegateDTO.getDelegateusertitle() != null && userMap.get(delegateUser) != null && !((String)userMap.get(delegateUser)).contains(delegateUserTitle)) continue;
            delegateVO.setDelegateusertitle((String)userMap.get(delegateUser));
            this.dealUserDetailInfo(userMap, orgMap, delegateVO, "delegateUser", userUnitMap, null, null, cacheOrgMap);
            if (isStaff) {
                this.handleChooseStaff(delegateVO, staffPostCache, staffCache, cacheOrgMap, staffPositionCache);
            }
            List agentUsers = JSONUtil.parseArray((String)delegateVO.getAgentuser(), String.class);
            delegateVO.setAgentusers(agentUsers);
            ArrayList<String> agentUserAndUnits = new ArrayList<String>();
            StringBuilder agents = new StringBuilder();
            for (Object agentUser : agentUsers) {
                this.getOneUserData(userMap, userUnitMap, (String)agentUser, delegateVO.getTenantName(), cacheUserMap);
                if (userMap.get((String)agentUser) != null) {
                    agents.append((String)userMap.get((String)agentUser)).append("\u3001");
                }
                this.dealUserDetailInfo(userMap, orgMap, delegateVO, "agentUser", userUnitMap, agentUserAndUnits, agentUser, cacheOrgMap);
            }
            if (agents.length() > 0) {
                String agentString = agents.substring(0, agents.length() - 1);
                String agentusertitle = delegateDTO.getAgentusertitle();
                if (StringUtils.hasText(agentusertitle) && !agentString.replace("\u3001", "").contains(agentusertitle)) continue;
                delegateVO.setAgentusertitle(agentString);
            }
            List delegateCondition = JSONUtil.parseMapArray((String)delegateVO.getDelecondition());
            delegateVO.setDeleconditions(delegateCondition);
            for (Map cdn : delegateCondition) {
                String columnName = (String)cdn.get("columnName");
                if (columnName.equalsIgnoreCase("UNITCODE")) {
                    Object valueObject = cdn.get("value");
                    if (valueObject instanceof String) {
                        valueObject = Arrays.asList(valueObject.toString());
                    }
                    if (valueObject instanceof List) {
                        List value = (List)valueObject;
                        StringBuilder orgStringBuilder = new StringBuilder();
                        for (Object vle : value) {
                            if (orgMap.get((String)vle) == null) {
                                this.getOneOrgData(orgMap, (String)vle, delegateVO.getTenantName(), cacheOrgMap);
                            }
                            orgStringBuilder.append((String)orgMap.get((String)vle)).append(",");
                        }
                        if (orgStringBuilder.length() > 0) {
                            String orgString = orgStringBuilder.substring(0, orgStringBuilder.length() - 1);
                            delegateVO.setUnitcodetitle(orgString);
                        }
                    }
                }
                if (!"BIZDEFINE".equalsIgnoreCase(columnName) || cdn.get("value") == null || "".equals(cdn.get("value"))) continue;
                Map value = (Map)cdn.get("value");
                StringBuilder bizStringBuilder = new StringBuilder();
                List storageValue = (List)value.get("storageValue");
                for (Map tempMap : storageValue) {
                    String tempTitle;
                    if (ObjectUtils.isEmpty(bizType) || "BILL".equalsIgnoreCase(bizType)) {
                        String uniqueCode = (String)tempMap.get("uniqueCode");
                        tempTitle = (String)billDefineTitleCache.get(uniqueCode);
                        if (!StringUtils.hasText(tempTitle)) {
                            tempTitle = VaWorkFlowDataUtils.getBizDefineTitle("BILL", uniqueCode);
                            billDefineTitleCache.put(uniqueCode, tempTitle);
                        }
                    } else {
                        tempTitle = otherBusinessDefineTitleMap.get(tempMap.get("uniqueCode"));
                    }
                    if (ObjectUtils.isEmpty(tempTitle)) {
                        tempTitle = (String)tempMap.get("uniqueCode");
                    }
                    bizStringBuilder.append(tempTitle).append(",");
                }
                if (bizStringBuilder.length() <= 0) continue;
                String bizString = bizStringBuilder.substring(0, bizStringBuilder.length() - 1);
                delegateVO.setBizdefinetitle(bizString);
            }
            resultList.add(delegateVO);
        }
        return resultList;
    }

    private void handleChooseStaff(DelegateVO delegateVO, Map<String, Map<String, String>> staffPostCache, Map<String, Map<String, String>> staffCache, Map<String, Map<String, String>> staffUnitCache, Map<String, String> staffPositionCache) {
        String staffcode = delegateVO.getStaffcode();
        if (StringUtils.hasText(staffcode)) {
            List staffCodes = JSONUtil.parseArray((String)staffcode, String.class);
            if (this.staffPartTimeJobFlag) {
                this.handleStaffPartTimeJob(delegateVO, staffCodes, staffPostCache, staffUnitCache, staffPositionCache);
            } else {
                this.handleStaffNotPartTimeJob(delegateVO, staffCodes, staffCache, staffUnitCache, staffPositionCache);
            }
        }
    }

    private void handleStaffNotPartTimeJob(DelegateVO delegateVO, List<String> staffCodes, Map<String, Map<String, String>> staffCache, Map<String, Map<String, String>> staffUnitCache, Map<String, String> staffPositionCache) {
        ArrayList<String> staffInfos = new ArrayList<String>();
        StringBuilder staffTitleBuilder = new StringBuilder();
        ArrayList<Object> staffs = new ArrayList<Object>();
        for (String staffCode : staffCodes) {
            Object staff;
            if (!StringUtils.hasText(staffCode)) continue;
            String staffName = "";
            Map<String, String> localStaff = staffCache.get(staffCode);
            if (localStaff != null) {
                staffName = localStaff.get("staffName");
                String staffInfoStr = localStaff.get("staffInfoStr");
                staffTitleBuilder.append("\u3001").append(staffName);
                staffInfos.add(staffInfoStr);
            } else {
                staff = VaWorkFlowDataUtils.getStaff(staffCode);
                if (staff == null) continue;
                String staffInfoStr = staffName = staff.getName();
                staffTitleBuilder.append("\u3001").append(staffName);
                String unitcode = staff.getUnitcode();
                String positioncode = (String)staff.get((Object)"positioncode");
                staffInfoStr = this.getStaffInfoStr(staffInfoStr, unitcode, positioncode, staffUnitCache, staffPositionCache);
                staffInfos.add(staffInfoStr);
                HashMap<String, String> map = new HashMap<String, String>(4);
                map.put("staffName", staffName);
                map.put("staffInfoStr", staffInfoStr);
                staffCache.put(staffCode, map);
            }
            staff = new HashMap(4);
            staff.put("staffCode", staffCode);
            staff.put("staffName", staffName);
            staffs.add(staff);
        }
        String staffTitle = "";
        if (staffTitleBuilder.length() > 0) {
            staffTitle = staffTitleBuilder.substring(1, staffTitleBuilder.length());
        }
        delegateVO.setStafftitle(staffTitle);
        delegateVO.setStaffinfo(staffInfos);
        delegateVO.setStaffs(staffs);
    }

    private String getStaffInfoStr(String staffInfoStr, String unitcode, String positioncode, Map<String, Map<String, String>> staffUnitCache, Map<String, String> staffPositionCache) {
        if (StringUtils.hasText(unitcode)) {
            Map<String, String> localOrg = staffUnitCache.get(unitcode);
            if (localOrg != null) {
                String unitname = localOrg.get("unitname");
                staffInfoStr = staffInfoStr + "|" + unitname;
            } else {
                OrgDO orgDO = VaWorkFlowDataUtils.getOrgData(ShiroUtil.getTenantName(), unitcode);
                if (orgDO != null && StringUtils.hasText(orgDO.getName())) {
                    String unitname = orgDO.getName();
                    staffInfoStr = staffInfoStr + "|" + unitname;
                    HashMap<String, String> map = new HashMap<String, String>(4);
                    map.put(unitcode, unitname);
                    map.put("unitname", unitname);
                    staffUnitCache.put(unitcode, map);
                }
            }
        }
        if (StringUtils.hasText(positioncode)) {
            String positionName = staffPositionCache.get(positioncode);
            if (StringUtils.hasText(positionName)) {
                staffInfoStr = staffInfoStr + "|" + positionName;
            } else {
                BaseDataDO positionInfo;
                PageVO<BaseDataDO> positionPageVO = VaWorkFlowDataUtils.getPositionByCode(positioncode);
                if (positionPageVO != null && positionPageVO.getTotal() > 0 && StringUtils.hasText(positionName = (positionInfo = (BaseDataDO)positionPageVO.getRows().get(0)).getName())) {
                    staffInfoStr = staffInfoStr + "|" + positionName;
                    staffPositionCache.put(positioncode, positionName);
                }
            }
        }
        return staffInfoStr;
    }

    private void handleStaffPartTimeJob(DelegateVO delegateVO, List<String> staffCodes, Map<String, Map<String, String>> staffPostCache, Map<String, Map<String, String>> staffUnitCache, Map<String, String> staffPositionCache) {
        ArrayList<String> staffInfos = new ArrayList<String>();
        StringBuilder staffTitleBuilder = new StringBuilder();
        ArrayList staffs = new ArrayList();
        for (String staffCode : staffCodes) {
            if (!StringUtils.hasText(staffCode)) continue;
            String staffName = "";
            Map<String, String> localStaffPost = staffPostCache.get(staffCode);
            if (localStaffPost != null) {
                staffName = localStaffPost.get("staffName");
                String staffInfoStr = localStaffPost.get("staffInfoStr");
                staffTitleBuilder.append("\u3001").append(staffName);
                staffInfos.add(staffInfoStr);
            } else {
                BaseDataDO staff;
                BaseDataDO staffPost = VaWorkFlowDataUtils.getStaffPostInfo(staffCode);
                if (staffPost == null) continue;
                String positioncode = (String)staffPost.get((Object)"positioncode");
                String staffObjectcode = (String)staffPost.get((Object)"staffcode");
                if (!StringUtils.hasText(staffObjectcode) || (staff = VaWorkFlowDataUtils.getStaff(staffObjectcode)) == null) continue;
                String staffInfoStr = staffName = staff.getName();
                staffTitleBuilder.append("\u3001").append(staffName);
                String unitcode = staff.getUnitcode();
                staffInfoStr = this.getStaffInfoStr(staffInfoStr, unitcode, positioncode, staffUnitCache, staffPositionCache);
                staffInfos.add(staffInfoStr);
                HashMap<String, String> map = new HashMap<String, String>(4);
                map.put("staffName", staffName);
                map.put("staffInfoStr", staffInfoStr);
                staffPostCache.put(staffCode, map);
            }
            HashMap<String, String> staff = new HashMap<String, String>(4);
            staff.put("staffCode", staffCode);
            staff.put("staffName", staffName);
            staffs.add(staff);
        }
        String staffTitle = "";
        if (staffTitleBuilder.length() > 0) {
            staffTitle = staffTitleBuilder.substring(1, staffTitleBuilder.length());
        }
        delegateVO.setStafftitle(staffTitle);
        delegateVO.setStaffinfo(staffInfos);
        delegateVO.setStaffs(staffs);
    }

    private Map<String, String> getOtherBusinessDefineTitleMap(List<? extends DelegateVO> list) {
        HashMap<String, String> map = new HashMap<String, String>(16);
        List delegateVoList = Optional.ofNullable(list).orElse(Collections.emptyList()).stream().filter(x -> !ObjectUtils.isEmpty(x.getBizType()) && !"BILL".equalsIgnoreCase(x.getBizType())).collect(Collectors.toList());
        block2: for (DelegateVO delegateVO : delegateVoList) {
            String bizType = delegateVO.getBizType();
            List delegateConditionList = JSONUtil.parseMapArray((String)delegateVO.getDelecondition());
            for (Map tempMap : delegateConditionList) {
                String columnName = (String)tempMap.get("columnName");
                if (!"BIZDEFINE".equalsIgnoreCase(columnName)) continue;
                Object value = tempMap.get("value");
                if (!(value instanceof Map)) continue block2;
                List storageValueMapList = (List)((Map)value).get("storageValue");
                for (Map storageValueMap : storageValueMapList) {
                    String uniqueCode = (String)storageValueMap.get("uniqueCode");
                    if (map.containsKey(uniqueCode)) continue;
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.setTraceId(Utils.getTraceId());
                    tenantDO.addExtInfo("bizDefine", (Object)uniqueCode);
                    try {
                        BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
                        R r = bussinessClient.getBizTitle(tenantDO);
                        if (r.getCode() == 0) {
                            String bizDefineTitle = r.get((Object)"bizDefineTitle") != null ? String.valueOf(r.get((Object)"bizDefineTitle")) : uniqueCode;
                            map.put(uniqueCode, bizDefineTitle);
                            continue;
                        }
                        map.put(uniqueCode, uniqueCode);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        map.put(uniqueCode, uniqueCode);
                    }
                }
                continue block2;
            }
        }
        return map;
    }

    private void dealUserDetailInfo(Map<String, String> userMap, Map<String, String> orgMap, DelegateVO delegateVO, String type, Map<String, String> userUnitMap, List<String> agentUserAndUnits, Object agentUser, Map<String, Map<String, String>> cacheOrgMap) {
        String userUnit;
        String delegateUserUnit;
        String createUserUnit;
        List<String> configColumns = this.getConfigColumns();
        if ("createUser".equals(type) && (createUserUnit = userUnitMap.get(delegateVO.getCreater())) != null) {
            this.getOneOrgData(orgMap, createUserUnit, delegateVO.getTenantName(), cacheOrgMap);
            ArrayList<String> createUserUnits = new ArrayList<String>();
            if (CollectionUtils.isEmpty(configColumns)) {
                createUserUnits.add(userMap.get(delegateVO.getCreater()) + "|" + orgMap.get(createUserUnit));
            } else {
                createUserUnits.add(this.getInFoFromUserMap(configColumns, userMap, orgMap));
            }
            delegateVO.setCreaterandunitcode(createUserUnits);
        }
        if ("delegateUser".equals(type) && (delegateUserUnit = userUnitMap.get(delegateVO.getDelegateuser())) != null) {
            this.getOneOrgData(orgMap, delegateUserUnit, delegateVO.getTenantName(), cacheOrgMap);
            ArrayList<String> delegateUserAndUnits = new ArrayList<String>();
            if (CollectionUtils.isEmpty(configColumns)) {
                delegateUserAndUnits.add(userMap.get(delegateVO.getDelegateuser()) + "|" + orgMap.get(delegateUserUnit));
            } else {
                delegateUserAndUnits.add(this.getInFoFromUserMap(configColumns, userMap, orgMap));
            }
            delegateVO.setDelegateuserandunitcode(delegateUserAndUnits);
        }
        if ("agentUser".equals(type) && (userUnit = userUnitMap.get((String)agentUser)) != null) {
            this.getOneOrgData(orgMap, userUnit, delegateVO.getTenantName(), cacheOrgMap);
            if (CollectionUtils.isEmpty(configColumns)) {
                agentUserAndUnits.add(userMap.get((String)agentUser) + "|" + orgMap.get(userUnit));
            } else {
                agentUserAndUnits.add(this.getInFoFromUserMap(configColumns, userMap, orgMap));
            }
            delegateVO.setAgentuserandunitcode(agentUserAndUnits);
        }
    }

    private List<String> getConfigColumns() {
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        optionItemDTO.setSearchKey("WF1005");
        List optionItemVOS = this.optionService.list(optionItemDTO);
        ArrayList<String> configColumns = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(optionItemVOS) && Objects.nonNull(optionItemVOS.get(0))) {
            OptionItemVO optionItemVO = (OptionItemVO)optionItemVOS.get(0);
            String val = optionItemVO.getVal();
            List list = (List)JSONUtil.parseObject((String)val, List.class);
            for (Object object : list) {
                Boolean tips = (Boolean)((Map)object).get("tips");
                String columnName = String.valueOf(((Map)object).get("val"));
                if (!tips.booleanValue()) continue;
                configColumns.add(columnName);
            }
        }
        return configColumns;
    }

    private String getInFoFromUserMap(List<String> configColumns, Map<String, String> userMap, Map<String, String> orgMap) {
        StringBuilder builder = new StringBuilder();
        for (String columnName : configColumns) {
            String columnValue = userMap.get(columnName);
            if ("unitname".equals(columnName)) {
                columnValue = orgMap.get(columnName);
            }
            if (!StringUtils.hasText(columnValue)) continue;
            builder.append(columnValue).append("|");
        }
        if (StringUtils.hasText(builder.toString())) {
            return builder.substring(0, builder.length() - 1);
        }
        return "";
    }

    private void getOneUserData(Map<String, String> userMap, Map<String, String> userUnitMap, String id, String tenantName, Map<String, Map<String, String>> cacheMap) {
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(tenantName);
        userDTO.setId(id);
        userMap.clear();
        if (Objects.nonNull(cacheMap.get(id))) {
            cacheMap.get(id).forEach(userMap::put);
            return;
        }
        UserDO userDO = this.authUserClient.get(userDTO);
        if (userDO != null) {
            userMap.put(id, userDO.getName());
            userUnitMap.put(id, userDO.getUnitcode());
            userMap.put("email", userDO.getEmail());
            userMap.put("telephone", userDO.getTelephone());
            userMap.put("name", userDO.getName());
            userMap.put("unitname", userDO.getUnitcode());
            HashMap<String, String> temp = new HashMap<String, String>(userMap);
            cacheMap.put(id, temp);
        }
    }

    private void getOneOrgData(Map<String, String> orgMap, String code, String tenantName, Map<String, Map<String, String>> cacheOrgMap) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setCode(code);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setTenantName(tenantName);
        orgMap.clear();
        if (Objects.nonNull(cacheOrgMap.get(code))) {
            cacheOrgMap.get(code).forEach(orgMap::put);
            return;
        }
        PageVO list = this.orgDataClient.list(orgDTO);
        List rows = list.getRows();
        if (!CollectionUtils.isEmpty(rows)) {
            orgMap.put(code, ((OrgDO)rows.get(0)).getName());
            orgMap.put("unitname", ((OrgDO)rows.get(0)).getName());
            HashMap<String, String> temp = new HashMap<String, String>(orgMap);
            cacheOrgMap.put(code, temp);
        }
    }

    public List<Map<String, Object>> filterByCondition(DelegateDTO delegatedto, List<Map<String, Object>> unfinished) {
        return this.delegateHandler.filterByCondition(delegatedto, unfinished);
    }

    public List<DelegateDTO> getDelegate(String userid, Map<String, Object> todo) {
        ArrayList<DelegateDTO> reslist = new ArrayList<DelegateDTO>();
        DelegateDTO dto = new DelegateDTO();
        dto.setDelegateuser(userid);
        dto.setQuerytype(DelegateType.INEFFECT.getValue());
        dto.setBackendrequest(true);
        List<DelegateVO> list = this.query(dto);
        ArrayList<Map<String, Object>> todos = new ArrayList<Map<String, Object>>();
        todos.add(todo);
        for (DelegateVO delegateVO : list) {
            DelegateDTO delegateDTO = new DelegateDTO();
            delegateDTO.setBizType(delegateVO.getBizType());
            delegateDTO.setDeleconditions(JSONUtil.parseMapArray((String)delegateVO.getDelecondition()));
            List<Map<String, Object>> filterList = this.delegateHandler.filterByCondition(delegateDTO, todos);
            if (filterList.isEmpty()) continue;
            delegateDTO.setAgentusers(JSONUtil.parseArray((String)delegateVO.getAgentuser(), String.class));
            delegateDTO.setId(delegateVO.getId());
            dto = delegateDTO;
            reslist.add(dto);
        }
        return reslist;
    }

    private void formatInvaldate(DelegateDO delegatedto) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(delegatedto.getInvaldate());
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        delegatedto.setInvaldate(cal.getTime());
    }
}

