/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.period.common.rest.PeriodDataSelectObject
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.period.select.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.AdjustUtil;
import com.jiuqi.nr.period.select.common.RunType;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.service.IDesignPeriodModuleService;
import com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService;
import com.jiuqi.nr.period.select.vo.AdjustData;
import com.jiuqi.nr.period.select.vo.CompreData;
import com.jiuqi.nr.period.select.vo.ModuleObj;
import com.jiuqi.nr.period.select.vo.PageData;
import com.jiuqi.nr.period.select.vo.PeriodData;
import com.jiuqi.nr.period.select.vo.PeriodObj;
import com.jiuqi.nr.period.select.vo.RangeData;
import com.jiuqi.nr.period.select.vo.SelectData;
import com.jiuqi.nr.period.select.web.param.LoadPageData;
import com.jiuqi.nr.period.select.web.param.LoadYearData;
import com.jiuqi.nr.period.select.web.param.LoadYearsData;
import com.jiuqi.nr.period.select.web.param.ModeSelectData;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/definition/periodModule"})
@Api(tags={"\u65f6\u671f\u7ec4\u4ef6\u63a5\u53e3"})
public class PeriodModuleController {
    @Autowired
    private IDesignPeriodModuleService iDesignPeriodModuleService;
    @Autowired
    private IRuntimePeriodModuleService iRuntimePeriodModuleService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private INvwaSystemOptionService sysOption;

    @ApiOperation(value="\u67e5\u8be2\u5468\u671f\u7c7b\u578b\u6807\u8bc6")
    @RequestMapping(value={"queryPeriodType"}, method={RequestMethod.POST})
    public String queryPeriodType(@RequestBody ModuleObj moduleObj) throws JQException {
        try {
            if (null == moduleObj || StringUtils.isEmpty((String)moduleObj.getKey())) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0121);
            }
            if (RunType.DESIGNER.equals((Object)moduleObj.getRunType())) {
                return this.iDesignPeriodModuleService.queryPeriodType(moduleObj);
            }
            return this.iRuntimePeriodModuleService.queryPeriodType(moduleObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0121, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u5468\u671f\u7c7b\u578b\u67e5\u8be2\u65f6\u671f\u6570\u636e")
    @RequestMapping(value={"queryPeriodData"}, method={RequestMethod.POST})
    public List<PeriodDataSelectObject> queryPeriodData(@RequestBody PeriodObj periodObj) throws JQException {
        try {
            if (null == periodObj || StringUtils.isEmpty((String)periodObj.getPeriod())) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
            }
            return this.iRuntimePeriodModuleService.queryPeriodData(periodObj.getPeriod(), periodObj.isAllData());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u662f\u5426\u5f00\u542f\u8c03\u6574\u671f")
    @RequestMapping(value={"checkOpenAdjust"}, method={RequestMethod.POST})
    public boolean checkOpenAdjust(@RequestBody ModuleObj moduleObj) throws JQException {
        try {
            if (null == moduleObj || StringUtils.isEmpty((String)moduleObj.getKey())) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0123);
            }
            if (RunType.DESIGNER.equals((Object)moduleObj.getRunType())) {
                return this.iDesignPeriodModuleService.queryAdjustStatus(moduleObj);
            }
            return this.iRuntimePeriodModuleService.queryAdjustStatus(moduleObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0123, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u8c03\u6574\u671f\u6570\u636e")
    @RequestMapping(value={"queryAdjustData"}, method={RequestMethod.POST})
    public Map<String, List<AdjustPeriod>> queryAdjustData(@RequestBody ModuleObj moduleObj) throws JQException {
        try {
            if (null == moduleObj || StringUtils.isEmpty((String)moduleObj.getKey())) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0123);
            }
            if (RunType.DESIGNER.equals((Object)moduleObj.getRunType())) {
                List<AdjustData> adjustData = this.iDesignPeriodModuleService.queryAdjustData(moduleObj);
                return AdjustUtil.toMap(adjustData);
            }
            List<AdjustData> adjustData = this.iRuntimePeriodModuleService.queryAdjustData(moduleObj);
            return AdjustUtil.toMap(adjustData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0123, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6570\u636e\u7efc\u5408\u67e5\u8be2")
    @RequestMapping(value={"queryData"}, method={RequestMethod.POST})
    public CompreData queryData(@RequestBody ModuleObj moduleObj) throws JQException {
        try {
            if (null == moduleObj || StringUtils.isEmpty((String)moduleObj.getKey())) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0123);
            }
            if (RunType.DESIGNER.equals((Object)moduleObj.getRunType())) {
                return this.iDesignPeriodModuleService.queryData(moduleObj);
            }
            return this.iRuntimePeriodModuleService.queryData(moduleObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0123, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4efb\u52a1\u504f\u79fb\u8bbe\u7f6e\u7efc\u5408\u67e5\u8be2")
    @RequestMapping(value={"queryOffsetPeriod"}, method={RequestMethod.POST})
    public String queryOffsetPeriod(@RequestBody String taskKey) throws JQException {
        try {
            if (StringUtils.isEmpty((String)taskKey)) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
            }
            return this.iRuntimePeriodModuleService.queryOffsetPeriod(taskKey, RunType.RUNTIME);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u65f6\u671f\u6570\u636e\u5206\u9875\u67e5\u8be2")
    @RequestMapping(value={"queryPeriodDataByPage"}, method={RequestMethod.POST})
    public List<PeriodData> queryPeriodDataByPage(@RequestBody ModuleObj moduleObj) throws JQException {
        try {
            if (null == moduleObj) {
                throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
            }
            if (RunType.DESIGNER.equals((Object)moduleObj.getRunType())) {
                return this.iDesignPeriodModuleService.queryPeriodDataByPage(moduleObj);
            }
            return this.iRuntimePeriodModuleService.queryPeriodDataByPage(moduleObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u65f6\u671f\u7ec4\u4ef6\u521d\u59cb\u5316")
    @RequestMapping(value={"initData"}, method={RequestMethod.POST})
    public PageData initData(@RequestBody ModeSelectData modeSelectData) throws JQException {
        if (null == modeSelectData) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
        }
        try {
            return this.iRuntimePeriodModuleService.initData(modeSelectData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u5e74\u6570\u636e")
    @RequestMapping(value={"loadDataByYear"}, method={RequestMethod.POST})
    public Page loadDataByYear(@RequestBody LoadYearData loadYearData) throws JQException {
        if (null == loadYearData) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
        }
        try {
            return this.iRuntimePeriodModuleService.loadDataByYear(loadYearData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u6362\u9875\u6570\u636e")
    @RequestMapping(value={"loadDataByPage"}, method={RequestMethod.POST})
    public List<Page> loadDataByOtherPage(@RequestBody LoadPageData loadPageData) throws JQException {
        if (null == loadPageData) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
        }
        try {
            return this.iRuntimePeriodModuleService.loadDataByOtherPage(loadPageData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u9009\u62e9\u6570\u636e\u5217\u8868")
    @RequestMapping(value={"getSelectData"}, method={RequestMethod.POST})
    public SelectData getSelectData(@RequestBody ModeSelectData modeSelectData) throws JQException {
        if (null == modeSelectData) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
        }
        try {
            return this.iRuntimePeriodModuleService.getSelectData(modeSelectData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u591a\u4e2a\u5e74\u6570\u636e")
    @RequestMapping(value={"loadDataByYears"}, method={RequestMethod.POST})
    public List<Page> loadDataByYears(@RequestBody LoadYearsData loadYearsData) throws JQException {
        if (null == loadYearsData) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
        }
        try {
            return this.iRuntimePeriodModuleService.loadDataByYears(loadYearsData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u4e00\u4e2a\u8303\u56f4\u5185\u7684\u6570\u636e")
    @RequestMapping(value={"loadDataByRange"}, method={RequestMethod.POST})
    public RangeData loadDataByRange(@RequestBody ModeSelectData modeSelectData) throws JQException {
        if (null == modeSelectData) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122);
        }
        try {
            return this.iRuntimePeriodModuleService.loadDataByRange(modeSelectData);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122, (Throwable)e);
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u4e00\u4e2a\u65f6\u671f\u7c7b\u578b")
    @RequestMapping(value={"getPeriodType/{code}"}, method={RequestMethod.GET})
    public String getPeriodType(@PathVariable String code) throws JQException {
        if (null == code) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0121);
        }
        try {
            IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(code);
            return PeriodUtils.getPeriodType((PeriodType)periodEntity.getPeriodType());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0121, (Throwable)e);
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u7b80\u79f0\u96c6\u5408")
    @RequestMapping(value={"getSimpleTitle/{code}"}, method={RequestMethod.GET})
    public Map<String, String> getSimpleTitle(@PathVariable String code) throws JQException {
        if (null == code) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0121);
        }
        try {
            LinkedHashMap<String, String> simpleTitle = new LinkedHashMap<String, String>();
            List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(code).getPeriodItems();
            periodItems.sort((o1, o2) -> o1.getCode().compareTo(o2.getCode()));
            for (IPeriodRow periodItem : periodItems) {
                simpleTitle.put(periodItem.getCode(), periodItem.getSimpleTitle());
            }
            return simpleTitle;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0121, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6709\u6548\u65f6\u671f\u8303\u56f4\u5916\u65f6\u671f")
    @RequestMapping(value={"getOutsidePeriod"}, method={RequestMethod.GET})
    public boolean getOutsidePeriod() {
        String s = this.sysOption.get("PERIOD_GROUP", "DISABLE_OUTSIDE_TYPE");
        return "disable".equals(s);
    }

    @ApiOperation(value="\u6709\u6548\u65f6\u671f\u8303\u56f4\u5185\u4e0d\u53ef\u9009\u65f6\u671f")
    @RequestMapping(value={"getNoSelectPeriodValidPeriodRange"}, method={RequestMethod.GET})
    public boolean getNoSelectPeriodValidPeriodRange() {
        String ss = this.sysOption.get("PERIOD_GROUP", "DISABLE_INSIDE_TYPE");
        return "disable".equals(ss);
    }

    @ApiOperation(value="\u5468\u9009\u62e9\u6a21\u5f0f")
    @RequestMapping(value={"getWeeklySelectionMode"}, method={RequestMethod.GET})
    public String getWeeklyDisplayMode() {
        return this.sysOption.get("PERIOD_GROUP", "WEEKLY_SELECTION_MODE");
    }
}

