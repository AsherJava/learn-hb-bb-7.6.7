/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.message.domain.VaMessageChannelDTO
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.service.VaMessageService
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.reminder.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.reminder.bean.AutoUserEntitys;
import com.jiuqi.nr.reminder.bean.DropTreeResult;
import com.jiuqi.nr.reminder.bean.TreeNodeImpl;
import com.jiuqi.nr.reminder.infer.PagedResource;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nr.reminder.infer.ReminderService;
import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import com.jiuqi.nr.reminder.internal.Reminder;
import com.jiuqi.nr.reminder.internal.ReminderVO;
import com.jiuqi.nr.reminder.spi.ReminderComponent;
import com.jiuqi.nr.reminder.untils.CommonMethod;
import com.jiuqi.nr.reminder.untils.ErrorEnumSub;
import com.jiuqi.nr.reminder.untils.ResultObject;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.message.domain.VaMessageChannelDTO;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.service.VaMessageService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"/api/reminder"})
public class ReminderController {
    private static final Logger log = LoggerFactory.getLogger(ReminderController.class);
    @Autowired
    private ReminderService reminderService;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private IRuntimeTaskService taskService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired(required=false)
    private ReminderComponent reminderComponent;
    @Autowired
    private VaMessageService vaMessageService;

    @GetMapping(value={"component"})
    public ReminderComponent getReminderComponent() {
        return this.reminderComponent;
    }

    @PostMapping
    public void create(@RequestBody CreateReminderCommand command) throws JQException {
        try {
            if (command.getTaskId() == null) {
                Map<String, Object> taskInfo = this.getTaskInfo(command.getFormSchemeId());
                command.setTaskId((String)taskInfo.get("taskKey"));
                String title = command.getTitle();
                if (StringUtils.isEmpty((String)title)) {
                    command.buildTitle((String)taskInfo.get("taskTitle"));
                }
            }
            this.reminderService.createReminder(command);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)("\u539f\u56e0:" + e.getMessage() + ", \u50ac\u62a5id\uff1a " + command.getId()));
            throw new JQException((ErrorEnum)ErrorEnumSub.create(e));
        }
    }

    @GetMapping
    public PagedResource<ReminderVO> getAllReminder(@RequestParam(defaultValue="1", required=false) int pageIndex, @RequestParam(defaultValue="15", required=false) int pageSize) throws JQException {
        try {
            List<Reminder> reminders = this.reminderRepository.find(pageIndex, pageSize);
            int size = this.reminderRepository.findAllNums();
            List reminderVOs = reminders.stream().map(e -> {
                String unitName;
                String s;
                ReminderVO reminderVO = new ReminderVO();
                BeanUtils.copyProperties(e, reminderVO);
                if (e.getFormSchemeId() != null) {
                    FormSchemeDefine formScheme = null;
                    try {
                        formScheme = this.viewController.getFormScheme(e.getFormSchemeId());
                        if (formScheme != null) {
                            s = formScheme != null ? formScheme.getTitle() : null;
                            reminderVO.setFormSchemeTitle(s);
                            PeriodWrapper currPeriod = CommonMethod.getCurrPeriod(formScheme);
                            reminderVO.setPeriod(currPeriod.toString());
                        }
                    }
                    catch (Exception e1) {
                        log.error(e1.getMessage(), e1);
                    }
                }
                if (e.getTaskId() != null) {
                    TaskDefine define = this.taskService.queryTaskDefine(e.getTaskId());
                    s = define != null ? define.getTitle() : null;
                    reminderVO.setTaskTitle(s);
                }
                if (e.getUnitId() != null && null != (unitName = this.reminderService.queryUnitName(e.getFormSchemeId(), e.getUnitId()))) {
                    reminderVO.setUnitName(unitName);
                }
                return reminderVO;
            }).collect(Collectors.toList());
            return new PagedResource<ReminderVO>(reminderVOs, pageIndex, pageSize, size);
        }
        catch (Exception e2) {
            log.error(e2.getMessage(), e2);
            throw new JQException((ErrorEnum)ErrorEnumSub.create(e2), e2.getCause());
        }
    }

    @GetMapping(value={"/taskInfo"})
    public Map<String, Object> getTaskInfo(@RequestParam String formSchemeKey) throws JQException {
        FormSchemeDefine formSchemeDefine;
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            throw new JQException((ErrorEnum)ErrorEnumSub.create(new IllegalArgumentException("\u975e\u6cd5\u7684\u62a5\u8868\u65b9\u6848ID")));
        }
        try {
            formSchemeDefine = this.viewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ErrorEnumSub.create(e));
        }
        if (formSchemeDefine == null) {
            throw new JQException((ErrorEnum)ErrorEnumSub.create(new IllegalArgumentException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848\u5b9a\u4e49")));
        }
        String taskKey = formSchemeDefine.getTaskKey();
        try {
            PeriodType periodType;
            PeriodWrapper toPeriodWrapper;
            String[] endTimes;
            PeriodWrapper fromPeriodWrapper;
            String[] startTimes;
            this.viewController.initTask(taskKey);
            TaskDefine taskDefine = this.viewController.queryTaskDefine(taskKey);
            String startTime = null;
            String endTime = null;
            if (formSchemeDefine.getPeriodType() == PeriodType.DEFAULT) {
                if (taskDefine.getFromPeriod() != null && (startTimes = PeriodUtil.getTimesArr((PeriodWrapper)(fromPeriodWrapper = new PeriodWrapper(taskDefine.getFromPeriod())))) != null) {
                    startTime = startTimes[0];
                }
                if (taskDefine.getToPeriod() != null && (endTimes = PeriodUtil.getTimesArr((PeriodWrapper)(toPeriodWrapper = new PeriodWrapper(taskDefine.getToPeriod())))) != null) {
                    endTime = endTimes[1];
                }
                periodType = taskDefine.getPeriodType();
            } else {
                if (formSchemeDefine.getFromPeriod() != null && (startTimes = PeriodUtil.getTimesArr((PeriodWrapper)(fromPeriodWrapper = new PeriodWrapper(formSchemeDefine.getFromPeriod())))) != null) {
                    startTime = startTimes[0];
                }
                if (formSchemeDefine.getToPeriod() != null && (endTimes = PeriodUtil.getTimesArr((PeriodWrapper)(toPeriodWrapper = new PeriodWrapper(formSchemeDefine.getToPeriod())))) != null) {
                    endTime = endTimes[1];
                }
                periodType = formSchemeDefine.getPeriodType();
            }
            List formulaSchme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            flowsSetting = formSchemeDefine.getFlowsSetting();
            WorkFlowType workFlowType = flowsSetting.getWordFlowType();
            HashMap<String, Object> taskInfo = new HashMap<String, Object>();
            taskInfo.put("taskKey", taskKey);
            taskInfo.put("taskTitle", taskDefine.getTitle());
            taskInfo.put("formSchemeKey", formSchemeKey);
            taskInfo.put("formSchemeTitle", formSchemeDefine.getTitle());
            taskInfo.put("entityId", formSchemeDefine.getDw());
            taskInfo.put("periodType", periodType);
            taskInfo.put("formPeriod", startTime);
            taskInfo.put("toPeriod", endTime);
            taskInfo.put("workFlowType", workFlowType.name());
            taskInfo.put("formulaSchme", formulaSchme);
            String value = this.nvwaSystemOptionService.get("start-reminder", "REMINDER_MSG_CHANNEL");
            taskInfo.put("msgChannel", value);
            ArrayList<VaMessageChannelDTO> select = new ArrayList<VaMessageChannelDTO>();
            taskInfo.put("msgChannelList", select);
            List vaMessageChannels = this.vaMessageService.listChannel();
            if (value.contains(VaMessageOption.MsgChannel.PC.toString())) {
                select.add(new VaMessageChannelDTO(VaMessageOption.MsgChannel.PC.toString(), "\u7ad9\u5185\u4fe1"));
            }
            for (VaMessageChannelDTO vaMessageChannel : vaMessageChannels) {
                if (VaMessageOption.MsgChannel.PC.toString().equals(vaMessageChannel.getName()) || !value.contains(vaMessageChannel.getName())) continue;
                select.add(vaMessageChannel);
            }
            return taskInfo;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ErrorEnumSub.create(e));
        }
    }

    @GetMapping(value={"/key"})
    public ReminderVO getReminderByKey(@RequestParam String key) {
        Reminder reminder = this.reminderRepository.find(key);
        if (reminder == null) {
            return null;
        }
        ReminderVO reminderVO = new ReminderVO();
        BeanUtils.copyProperties(reminder, reminderVO);
        return reminderVO;
    }

    @GetMapping(value={"/delete"})
    public void delete(@RequestParam String remId) {
        this.getReminderService().deleteReminder(remId);
    }

    @PostMapping(value={"delete"})
    public void delete(@RequestBody List<String> remId) {
        this.getReminderService().batchDelete(remId);
    }

    @GetMapping(value={"/get/user-entity"})
    public ResultObject getUserEntitys(@RequestParam String formSchemeKey) {
        ResultObject resultObject = new ResultObject();
        try {
            AutoUserEntitys userEntitys = this.getReminderService().getUserEntitys(formSchemeKey);
            resultObject.setState(true);
            resultObject.setData(userEntitys);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            resultObject.setState(false);
            resultObject.setMessage("\u67e5\u8be2\u9047\u5230\u672a\u77e5\u9519\u8bef\uff01");
            resultObject.setData(e);
        }
        return resultObject;
    }

    @GetMapping(value={"/keyWords"})
    public PagedResource<ReminderVO> getKeyWords(@RequestParam String keyWords) throws JQException {
        ArrayList<Reminder> reminderSelectedList = new ArrayList<Reminder>();
        List<Reminder> reminders = this.reminderRepository.findAll();
        for (Reminder reminder : reminders) {
            FormSchemeDefine formScheme = this.viewController.getFormScheme(reminder.getFormSchemeId());
            if (formScheme == null || !formScheme.getTitle().contains(keyWords)) continue;
            reminderSelectedList.add(reminder);
        }
        try {
            List reminderVOs = reminderSelectedList.stream().map(e -> {
                ReminderVO reminderVO = new ReminderVO();
                BeanUtils.copyProperties(e, reminderVO);
                if (e.getFormSchemeId() != null) {
                    FormSchemeDefine formScheme = null;
                    try {
                        formScheme = this.viewController.getFormScheme(e.getFormSchemeId());
                        if (formScheme != null) {
                            reminderVO.setFormSchemeTitle(formScheme != null ? formScheme.getTitle() : null);
                            PeriodWrapper currPeriod = CommonMethod.getCurrPeriod(formScheme);
                            reminderVO.setPeriod(currPeriod.toString());
                        }
                    }
                    catch (Exception e1) {
                        log.error(e1.getMessage(), e1);
                    }
                }
                if (e.getTaskId() != null) {
                    TaskDefine define = this.taskService.queryTaskDefine(e.getTaskId());
                    reminderVO.setTaskTitle(define != null ? define.getTitle() : null);
                }
                if (e.getUnitId() != null) {
                    String unitName = this.reminderService.queryUnitName(e.getFormSchemeId(), e.getUnitId());
                    reminderVO.setUnitName(unitName);
                }
                return reminderVO;
            }).collect(Collectors.toList());
            return new PagedResource<ReminderVO>(reminderVOs, 1, 15, reminders.size());
        }
        catch (Exception e2) {
            log.error(e2.getMessage(), e2);
            throw new JQException((ErrorEnum)ErrorEnumSub.create(e2), e2.getCause());
        }
    }

    @GetMapping(value={"/getRedId"})
    public PagedResource<ReminderVO> getRedId(@RequestParam String id, @RequestParam(defaultValue="15", required=false) int pageSize) throws JQException {
        try {
            int size = this.reminderRepository.findAllNums();
            int pageIndex = this.reminderRepository.getPageInfo(id, pageSize);
            List<Reminder> reminders = this.reminderRepository.find(pageIndex, pageSize);
            List reminderVOs = reminders.stream().map(e -> {
                ReminderVO reminderVO = new ReminderVO();
                BeanUtils.copyProperties(e, reminderVO);
                if (e.getFormSchemeId() != null) {
                    FormSchemeDefine formScheme = null;
                    try {
                        formScheme = this.viewController.getFormScheme(e.getFormSchemeId());
                        if (formScheme != null) {
                            reminderVO.setFormSchemeTitle(formScheme != null ? formScheme.getTitle() : null);
                            PeriodWrapper currPeriod = CommonMethod.getCurrPeriod(formScheme);
                            reminderVO.setPeriod(currPeriod.toString());
                        }
                    }
                    catch (Exception e1) {
                        log.error(e1.getMessage(), e1);
                    }
                }
                if (e.getTaskId() != null) {
                    TaskDefine define = this.taskService.queryTaskDefine(e.getTaskId());
                    reminderVO.setTaskTitle(define != null ? define.getTitle() : null);
                }
                if (e.getUnitId() != null) {
                    String unitName = this.reminderService.queryUnitName(e.getFormSchemeId(), e.getUnitId());
                    reminderVO.setUnitName(unitName);
                }
                return reminderVO;
            }).collect(Collectors.toList());
            return new PagedResource<ReminderVO>(reminderVOs, pageIndex, pageSize, size);
        }
        catch (Exception e2) {
            log.error(e2.getMessage(), e2);
            throw new JQException((ErrorEnum)ErrorEnumSub.create(e2), e2.getCause());
        }
    }

    @GetMapping(value={"/queryDropTreeData"})
    public List<DropTreeResult> queryDropTreeData(@RequestParam String formSchemeKey, @RequestParam String period) {
        ArrayList<DropTreeResult> dropTreeData = new ArrayList();
        dropTreeData = this.getReminderService().queryDropTreeData(formSchemeKey, period);
        return dropTreeData;
    }

    public ReminderService getReminderService() {
        return this.reminderService;
    }

    @RequestMapping(value={"/getTreeNodes"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1")
    public List<TreeNodeImpl> getTreeNodes(@RequestParam(required=false) String taskId) throws JQException {
        List<TreeNodeImpl> treeNodes = null;
        try {
            treeNodes = this.reminderService.getReportTask();
            for (TreeNodeImpl treeNode : treeNodes) {
                treeNode.setChildren(Collections.emptyList());
                if (!treeNode.getKey().equals(taskId)) continue;
                List<TreeNodeImpl> formSchemeList = this.getFormSchemeList(taskId);
                treeNode.setChildren(formSchemeList);
            }
            if (treeNodes.size() <= 0) {
                throw new RuntimeException("\u6ca1\u6709\u9700\u8981\u7ed1\u5b9a\u6d41\u7a0b\u7684\u4efb\u52a1\uff01");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u6ca1\u6709\u9700\u8981\u7ed1\u5b9a\u6d41\u7a0b\u7684\u4efb\u52a1\uff01");
        }
        return treeNodes;
    }

    @RequestMapping(value={"/getFormSchemeList"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u4e0b\u5c5e\u62a5\u8868\u65b9\u6848")
    public List<TreeNodeImpl> getFormSchemeList(@RequestParam String taskKey) throws JQException {
        List<TreeNodeImpl> treeNodes = null;
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            treeNodes = this.reminderService.getFormSchemeList(taskDefine);
            if (treeNodes.size() <= 0) {
                throw new RuntimeException("\u6ca1\u6709\u9700\u8981\u7ed1\u5b9a\u6d41\u7a0b\u7684\u4efb\u52a1\uff01");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u6ca1\u6709\u9700\u8981\u7ed1\u5b9a\u6d41\u7a0b\u7684\u4efb\u52a1\uff01");
        }
        return treeNodes;
    }
}

