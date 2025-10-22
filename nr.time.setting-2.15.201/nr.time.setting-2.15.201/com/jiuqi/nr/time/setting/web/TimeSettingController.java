/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.time.setting.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.time.setting.bean.SelectData;
import com.jiuqi.nr.time.setting.bean.SelectDataResult;
import com.jiuqi.nr.time.setting.bean.SelectParam;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.nr.time.setting.bean.TimeSettingResult;
import com.jiuqi.nr.time.setting.service.ITimeSettingService;
import com.jiuqi.nr.time.setting.util.ResultObject;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"/tdfoh/time"})
public class TimeSettingController {
    private static final Logger logger = LoggerFactory.getLogger(TimeSettingController.class);
    @Autowired
    private ITimeSettingService setTimeService;

    @PostMapping(value={"/saveDeadlineUnit"})
    public ResultObject createDeadlineInfo(@RequestBody TimeSettingInfo deadlineInfo) throws JQException {
        ResultObject resultObject = new ResultObject();
        try {
            String msg = this.setTimeService.createDeadlineInfo(deadlineInfo);
            resultObject.setMessage(msg);
            resultObject.setState(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultObject.setState(false);
            resultObject.setMessage("\u63d2\u5165\u6709\u62a5\u9519");
            resultObject.setData(e);
        }
        return resultObject;
    }

    @PostMapping(value={"/saveUnitTime"})
    public ResultObject saveDeadlineInfo(@RequestBody TimeSettingInfo deadlineInfo) throws JQException {
        ResultObject resultObject = new ResultObject();
        try {
            this.setTimeService.saveUnitTime(deadlineInfo);
            resultObject.setState(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultObject.setState(false);
            resultObject.setMessage("\u63d2\u5165\u6709\u62a5\u9519");
            resultObject.setData(e);
        }
        return resultObject;
    }

    @PostMapping(value={"/queryTableData"})
    public List<TimeSettingResult> queryTableData(@RequestBody TimeSettingInfo deadlineInfo) {
        ArrayList<TimeSettingResult> result = new ArrayList();
        result = this.setTimeService.queryTableData(deadlineInfo.getFormSchemeKey(), deadlineInfo.getPeriod());
        return result;
    }

    @GetMapping(value={"/queryUnitOfUserAuth"})
    public List<TimeSettingResult> queryUnitOfUserAuth(@RequestParam String formSchemeKey, @RequestParam String period, @RequestParam String parentId, @RequestParam String unitName) {
        ArrayList<TimeSettingResult> result = new ArrayList();
        result = this.setTimeService.queryUnitOfUserAuth(formSchemeKey, period, parentId, unitName);
        return result;
    }

    @PostMapping(value={"/queryUnitOfAuth"})
    public List<TimeSettingResult> queryUnitOfAuth(@RequestBody SelectParam selectParam) {
        ArrayList<TimeSettingResult> result = new ArrayList();
        result = this.setTimeService.queryUnitOfUserAuth(selectParam.getFormSchemeKey(), selectParam.getPeriod(), selectParam.getParentId(), selectParam.getUnitName());
        return result;
    }

    @GetMapping(value={"/queryUnitTime"})
    public TimeSettingInfo queryUnitTime(@RequestParam String formSchemeKey, @RequestParam String period, @RequestParam String unitId) {
        return this.setTimeService.getDeadlineInfoOfUnit(formSchemeKey, period, unitId);
    }

    @GetMapping(value={"/canOperateEntity"})
    public boolean canOperateEntity(@RequestParam String formSchemeKey, @RequestParam String period, @RequestParam String unitId) {
        return this.setTimeService.canOperatorEntity(formSchemeKey, period, unitId);
    }

    @PostMapping(value={"/distinguishData"})
    public SelectDataResult distinguishData(@RequestBody SelectData selectData) {
        SelectDataResult selectDataResult = new SelectDataResult();
        selectDataResult = this.setTimeService.distinguishData(selectData);
        return selectDataResult;
    }
}

