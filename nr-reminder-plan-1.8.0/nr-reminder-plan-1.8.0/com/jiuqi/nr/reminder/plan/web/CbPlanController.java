/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.va.message.domain.VaMessageChannelDTO
 *  com.jiuqi.va.message.service.VaMessageService
 *  org.springframework.dao.DataIntegrityViolationException
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.reminder.plan.web;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.TaskInfo;
import com.jiuqi.nr.reminder.plan.TreeNode;
import com.jiuqi.nr.reminder.plan.common.ErrorEnumImpl;
import com.jiuqi.nr.reminder.plan.dao.CbExecLogDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanContentDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanLogDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbExecLogDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanContentDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanLogDO;
import com.jiuqi.nr.reminder.plan.service.CbPlanService;
import com.jiuqi.nr.reminder.plan.web.CbExecLogVO;
import com.jiuqi.nr.reminder.plan.web.CbPlanVO;
import com.jiuqi.nr.reminder.plan.web.PageData;
import com.jiuqi.va.message.domain.VaMessageChannelDTO;
import com.jiuqi.va.message.service.VaMessageService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/nr/reminder-plan/"})
public class CbPlanController {
    @Autowired
    private CbPlanService cbPlanService;
    @Autowired
    private CbExecLogDAO logDAO;
    @Autowired
    private CbPlanContentDAO cbPlanContentDAO;
    @Autowired
    private CbPlanLogDAO cbPlanLogDAO;
    @Autowired
    private NvwaSystemUserClient nvwaSystemUserClient;
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private VaMessageService vaMessageService;
    private static final Logger logger = LoggerFactory.getLogger(CbPlanController.class);

    @NRContextBuild
    @PostMapping(value={"add"})
    public void addCb(@RequestBody CbPlanVO cbPlanDTO) throws JQException {
        this.buildTimes(cbPlanDTO);
        cbPlanDTO.setEnabled(true);
        this.cbPlanService.addCbPlan(cbPlanDTO);
    }

    private void buildTimes(CbPlanVO cbPlanDTO) {
        cbPlanDTO.toTimeList();
    }

    @NRContextBuild
    @PostMapping(value={"update"})
    public void updateCb(@RequestBody CbPlanVO cbPlanDTO) throws JQException {
        this.buildTimes(cbPlanDTO);
        this.cbPlanService.updateCbPlan(cbPlanDTO);
    }

    @PostMapping(value={"enable"})
    public void enableCb(@RequestBody CbPlanDTO cbPlanDTO) throws JQException {
        this.cbPlanService.enablePlan(cbPlanDTO.getPlanId());
    }

    @PostMapping(value={"disable"})
    public void disableCb(@RequestBody CbPlanDTO cbPlanDTO) throws JQException {
        this.cbPlanService.disablePlan(cbPlanDTO.getPlanId());
    }

    @PostMapping(value={"delete"})
    public void deleteCb(@RequestBody CbPlanDTO cbPlanDTO) throws JQException {
        this.cbPlanService.deletePlan(cbPlanDTO.getPlanId());
    }

    @NRContextBuild
    @PostMapping(value={"exec"})
    public void execPlan(@RequestBody CbPlanDTO cbPlanDTO) {
        cbPlanDTO.setMode(1);
        this.cbPlanService.execPlan(cbPlanDTO.getPlanId());
    }

    @PostMapping(value={"list"})
    public PageData<List<CbPlanVO>> listPlan(@RequestBody PagerInfo pagerInfo) {
        PageData<List<CbPlanVO>> pageData = new PageData<List<CbPlanVO>>();
        pageData.setData(this.cbPlanService.queryByPlan(pagerInfo));
        pageData.setTotal(this.cbPlanService.countPlan());
        return pageData;
    }

    @GetMapping(value={"count"})
    public int countPlan() {
        return this.cbPlanService.countPlan();
    }

    @GetMapping(value={"log"})
    public List<CbExecLogVO> execLogsByPlan(@RequestParam String planId) {
        List<CbExecLogDO> cbExecLogDOS = this.logDAO.queryLogByPlanId(planId);
        ArrayList<CbExecLogVO> list = new ArrayList<CbExecLogVO>(cbExecLogDOS.size());
        HashMap<String, String> userMap = new HashMap<String, String>();
        for (CbExecLogDO cbExecLogDO : cbExecLogDOS) {
            CbExecLogVO cbExecLogVO = new CbExecLogVO(cbExecLogDO);
            String execUser = cbExecLogVO.getExecUser();
            String userName = (String)userMap.get(execUser);
            if (userName == null) {
                SystemUserDTO systemUserDTO = this.nvwaSystemUserClient.get(execUser);
                if (systemUserDTO == null) {
                    UserDTO userDTO = this.nvwaUserClient.get(execUser);
                    if (userDTO != null) {
                        userName = userDTO.getNickname();
                    }
                } else {
                    userName = systemUserDTO.getNickname();
                }
                userMap.put(execUser, userName);
            }
            cbExecLogVO.setExecUserName(userName);
            list.add(cbExecLogVO);
        }
        return list;
    }

    @PostMapping(value={"log/detail"})
    public PageData<List<CbPlanLogDO>> logsByLogId(@RequestParam String logId, @RequestBody(required=false) PagerInfo pagerInfo) {
        PageData<List<CbPlanLogDO>> pageData = new PageData<List<CbPlanLogDO>>();
        if (pagerInfo == null) {
            List<CbPlanLogDO> list = this.cbPlanLogDAO.queryLogByLogId(logId);
            pageData.setData(this.buildLogVo(list));
            pageData.setTotal(((List)pageData.getData()).size());
        } else {
            List<CbPlanLogDO> list = this.cbPlanLogDAO.queryLogByLogId(logId, pagerInfo.getOffset() * pagerInfo.getLimit(), (pagerInfo.getOffset() + 1) * pagerInfo.getLimit());
            pageData.setData(this.buildLogVo(list));
            pageData.setTotal(this.cbPlanLogDAO.count(logId));
        }
        return pageData;
    }

    private List<CbPlanLogDO> buildLogVo(List<CbPlanLogDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        List channels = this.vaMessageService.listChannel();
        Map<String, String> map = channels.stream().collect(Collectors.toMap(VaMessageChannelDTO::getName, VaMessageChannelDTO::getTitle, (s, o) -> s));
        for (CbPlanLogDO cbPlanLogDO : list) {
            String channel = cbPlanLogDO.getChannel();
            cbPlanLogDO.setChannelTitle(map.get(channel));
        }
        return list;
    }

    @GetMapping(value={"content"})
    public List<CbPlanContentDO> content() {
        return this.cbPlanContentDAO.queryAll();
    }

    @PostMapping(value={"content/add"})
    public void addContent(@RequestBody CbPlanContentDO contentDO) throws JQException {
        contentDO.setId(OrderGenerator.newOrder());
        Instant now = Instant.now();
        Timestamp from = Timestamp.from(now);
        contentDO.setCreateTime(from);
        contentDO.setUpdateTime(from);
        contentDO.setCreateUser(NpContextHolder.getContext().getUserId());
        contentDO.setUpdateUser(NpContextHolder.getContext().getUserId());
        try {
            this.cbPlanContentDAO.insert(contentDO);
        }
        catch (DataIntegrityViolationException e) {
            logger.error("\u6807\u9898\u91cd\u590d", e);
            throw new JQException((ErrorEnum)new ErrorEnumImpl("\u6807\u9898\u91cd\u590d"));
        }
    }

    @PostMapping(value={"content/del"})
    public void delContent(@RequestParam String contentId) {
        this.cbPlanContentDAO.delete(contentId);
    }

    @PostMapping(value={"content/update"})
    public void updateContent(@RequestBody CbPlanContentDO contentDO) {
        if (contentDO.getId() == null) {
            return;
        }
        Instant now = Instant.now();
        Timestamp from = Timestamp.from(now);
        contentDO.setUpdateTime(from);
        contentDO.setUpdateUser(NpContextHolder.getContext().getUserId());
        this.cbPlanContentDAO.updateById(contentDO);
    }

    @GetMapping(value={"context"})
    public TaskInfo queryTaskInfo(@RequestParam String formSchemeId) {
        if (StringUtils.hasLength(formSchemeId)) {
            return this.cbPlanService.queryTaskInfo(formSchemeId);
        }
        return null;
    }

    @GetMapping(value={"tasks"})
    public List<TreeNode> getRoot(@RequestParam(required=false) String taskId) {
        if (taskId != null) {
            return this.cbPlanService.task(taskId);
        }
        return this.cbPlanService.task();
    }

    @PostMapping(value={"schemes"})
    public List<TreeNode> getChildren(TreeNode parent) {
        return this.cbPlanService.task(parent);
    }
}

