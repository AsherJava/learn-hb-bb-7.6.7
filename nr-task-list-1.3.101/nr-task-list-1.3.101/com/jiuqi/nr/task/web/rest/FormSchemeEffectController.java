/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.task.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.exception.ValidateTimeException;
import com.jiuqi.nr.task.service.IValidateTimeService;
import com.jiuqi.nr.task.web.vo.ValidateTimeCheckResultVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeMergeVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeSettingVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@ApiOperation(value="\u62a5\u8868\u65b9\u6848\u751f\u6548\u65f6\u671f")
@RequestMapping(value={"api/v1/task/effective"})
public class FormSchemeEffectController {
    @Autowired
    private IValidateTimeService validateTimeService;

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u6240\u6709\u62a5\u8868\u65b9\u6848\u4e0b\u751f\u6548\u65f6\u671f")
    @RequestMapping(value={"/queryByTask/{taskID}"}, method={RequestMethod.GET})
    public List<ValidateTimeVO> queryEffectTimeByTask(@PathVariable String taskID) throws JQException {
        List<ValidateTimeVO> validateTimes;
        try {
            validateTimes = this.validateTimeService.queryByTask(taskID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ValidateTimeException.QUERY_FORM_SCHEME_EFFECT_TIME_FAILED, e.getMessage());
        }
        return validateTimes;
    }

    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u4efb\u52a1\u4e0b\u5168\u90e8\u62a5\u8868\u65b9\u6848\u548c\u65f6\u671f\u9009\u62e9\u9650\u5236")
    @RequestMapping(value={"/options/{taskKey}"}, method={RequestMethod.GET})
    public ValidateTimeSettingVO queryLimitedOptions(@PathVariable String taskKey) throws JQException {
        ValidateTimeSettingVO validateTimeSetting;
        try {
            validateTimeSetting = this.validateTimeService.queryLimitedOptions(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ValidateTimeException.QUERY_FORM_SCHEME_EFFECT_TIME_LIMITED_FAILED, e.getMessage());
        }
        return validateTimeSetting;
    }

    @ApiOperation(value="\u5408\u5e76\u540c\u4e00\u62a5\u8868\u65b9\u6848\u7684\u91cd\u53e0\u6216\u8005\u8fde\u7eed\u7684\u65f6\u95f4\u6bb5")
    @RequestMapping(value={"/merge"}, method={RequestMethod.POST})
    public List<ValidateTimeVO> mergeSimilarlyEffectTime(@RequestBody ValidateTimeMergeVO mergeVO) throws JQException {
        List<ValidateTimeVO> validateTimes;
        try {
            validateTimes = this.validateTimeService.merge(mergeVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ValidateTimeException.MERGE_FORM_SCHEME_EFFECT_TIME_FAILED, e.getMessage());
        }
        return validateTimes;
    }

    @ApiOperation(value="\u5bf9\u751f\u6548\u65f6\u671f\u505a\u6821\u9a8c\u68c0\u67e5\u662f\u5426\u6709\u51b2\u7a81")
    @RequestMapping(value={"/check"}, method={RequestMethod.POST})
    public ValidateTimeCheckResultVO checkEffectTimeConflict(@RequestBody List<ValidateTimeVO> validateTimes) throws JQException {
        ValidateTimeCheckResultVO checkResult;
        try {
            checkResult = this.validateTimeService.check(validateTimes);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ValidateTimeException.CHECK_FORM_SCHEME_EFFECT_TIME_FAILED, e.getMessage());
        }
        return checkResult;
    }

    @ApiOperation(value="\u5bf9\u751f\u6548\u65f6\u671f\u505a\u6821\u9a8c\u68c0\u67e5\u662f\u5426\u6709\u7a7a\u95f2\u65f6\u671f")
    @RequestMapping(value={"/checkEmpty"}, method={RequestMethod.POST})
    public ValidateTimeCheckResultVO checkEffectTimeEmpty(@RequestBody List<ValidateTimeVO> validateTimes) throws JQException {
        ValidateTimeCheckResultVO checkResult;
        try {
            checkResult = this.validateTimeService.checkEmptyPeriod(validateTimes);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ValidateTimeException.CHECK_FORM_SCHEME_EFFECT_TIME_FAILED, e.getMessage());
        }
        return checkResult;
    }

    @ApiOperation(value="\u4fdd\u5b58\u751f\u6548\u65f6\u671f")
    @RequestMapping(value={"/save"}, method={RequestMethod.POST})
    @TaskLog(operation="\u4fdd\u5b58\u65b9\u6848\u751f\u6548\u671f")
    public void saveEffectTime(@RequestBody List<ValidateTimeVO> validateTimes) throws JQException {
        try {
            this.validateTimeService.doSave(validateTimes, 0);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ValidateTimeException.SAVE_FORM_SCHEME_EFFECT_TIME_FAILED, e.getMessage());
        }
    }
}

