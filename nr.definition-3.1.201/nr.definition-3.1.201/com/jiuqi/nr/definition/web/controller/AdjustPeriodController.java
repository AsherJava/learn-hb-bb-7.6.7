/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.web.param.AdjustPeriodVO
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.definition.web.controller;

import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.web.param.AdjustPeriodVO;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IDesignFormSchemeService;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/definition/adjust"})
@Api(tags={"\u4efb\u52a1\u8bbe\u8ba1\u8c03\u6574\u671f"})
public class AdjustPeriodController {
    private final IFormSchemeService formSchemeService;
    private final IRunTimeViewController runTimeViewControl;
    private final IRuntimeDataSchemeService runtimeDataSchemeService;
    private final IDesignFormSchemeService iDesignFormSchemeService;
    private final IDesignDataSchemeService iDesignDataSchemeService;
    private final IDesignTimeViewController iDesignTimeViewController;
    private final AdjustPeriodDesignService adjustPeriodDesignService;

    public AdjustPeriodController(IFormSchemeService service, IRunTimeViewController runTimeViewControl, IRuntimeDataSchemeService runtimeDataSchemeService, IDesignFormSchemeService iDesignFormSchemeService, IDesignDataSchemeService iDesignDataSchemeService, IDesignTimeViewController iDesignTimeViewController, AdjustPeriodDesignService adjustPeriodDesignService) {
        this.formSchemeService = service;
        this.runTimeViewControl = runTimeViewControl;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.iDesignFormSchemeService = iDesignFormSchemeService;
        this.iDesignDataSchemeService = iDesignDataSchemeService;
        this.iDesignTimeViewController = iDesignTimeViewController;
        this.adjustPeriodDesignService = adjustPeriodDesignService;
    }

    @ApiOperation(value="\u83b7\u53d6\u8c03\u6574\u671f\u6570\u636e")
    @GetMapping(value={"all/get"})
    public Res<Map<String, List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>>> getByFormScheme(@RequestParam String formSchemeKey) {
        List adjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey);
        Map<String, List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>> collect = adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(com.jiuqi.nr.definition.web.vo.AdjustPeriodVO::convertToVO).filter(Objects::nonNull).collect(Collectors.groupingBy(com.jiuqi.nr.definition.web.vo.AdjustPeriodVO::getPeriod));
        return new Res<Map<String, List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>>>(collect);
    }

    @ApiOperation(value="\u83b7\u53d6\u8c03\u6574\u671f\u6570\u636e")
    @GetMapping(value={"/get"})
    public Res<List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>> get(@RequestParam String formSchemeKey, @RequestParam String period) {
        List adjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey, period);
        List collect = adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(com.jiuqi.nr.definition.web.vo.AdjustPeriodVO::convertToVO).collect(Collectors.toList());
        return new Res<List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>>(collect);
    }

    @ApiOperation(value="\u662f\u5426\u5f00\u542f\u8c03\u6574\u671f")
    @GetMapping(value={"/enable"})
    public Res<Boolean> isEnable(@RequestParam String formSchemeKey) {
        return new Res<Boolean>(this.formSchemeService.enableAdjustPeriod(formSchemeKey));
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u83b7\u53d6\u65f6\u671f")
    @GetMapping(value={"/get_period"})
    public Res<String> getPeriodTypeByFormScheme(@RequestParam String formSchemeKey) {
        if (!StringUtils.hasLength(formSchemeKey)) {
            return new Res<String>(false, "\u62a5\u8868\u65b9\u6848\u5173\u952e\u5b57\u4e0d\u80fd\u4e3a\u7a7a");
        }
        FormSchemeDefine formScheme = this.runTimeViewControl.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return null;
        }
        return new Res<String>(formScheme.getDateTime());
    }

    @ApiOperation(value="\u6839\u636e\u6570\u636e\u65b9\u6848\u83b7\u53d6\u65f6\u671f")
    @GetMapping(value={"/get_period_by_data_scheme"})
    public Res<String> getPeriodTypeByDataScheme(@RequestParam String dataSchemeKey) {
        if (!StringUtils.hasLength(dataSchemeKey)) {
            return new Res<String>(false, "\u6570\u636e\u65b9\u6848\u5173\u952e\u5b57\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
        if (dataSchemeDimension == null) {
            return null;
        }
        String dimKey = ((DataDimension)dataSchemeDimension.get(0)).getDimKey();
        return new Res<String>(dimKey);
    }

    @ApiOperation(value="\u83b7\u53d6\u8c03\u6574\u671f\u6570\u636e")
    @GetMapping(value={"des/all/get"})
    public Res<Map<String, List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>>> getDesByFormScheme(@RequestParam String formSchemeKey) {
        List adjustPeriods = this.iDesignFormSchemeService.queryAdjustPeriods(formSchemeKey);
        Map<String, List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>> collect = adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(com.jiuqi.nr.definition.web.vo.AdjustPeriodVO::convertToVO).filter(Objects::nonNull).collect(Collectors.groupingBy(com.jiuqi.nr.definition.web.vo.AdjustPeriodVO::getPeriod));
        return new Res<Map<String, List<com.jiuqi.nr.definition.web.vo.AdjustPeriodVO>>>(collect);
    }

    @ApiOperation(value="\u662f\u5426\u5f00\u542f\u8c03\u6574\u671f")
    @GetMapping(value={"/des/enable"})
    public Res<Boolean> isDesEnable(@RequestParam String formSchemeKey) {
        return new Res<Boolean>(this.iDesignFormSchemeService.enableAdjustPeriod(formSchemeKey));
    }

    @ApiOperation(value="\u83b7\u53d6\u8c03\u6574\u671f\u6570\u636e")
    @GetMapping(value={"des/all/getByDataScheme"})
    public Res<Map<String, List<AdjustPeriodVO>>> getByDataScheme(@RequestParam String dataSchemeKey) {
        List adjustPeriods = this.adjustPeriodDesignService.query(dataSchemeKey);
        Map<String, List<AdjustPeriodVO>> collect = adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(AdjustPeriodVO::convertToVO).collect(Collectors.groupingBy(AdjustPeriodVO::getPeriod));
        return new Res<Map<String, List<AdjustPeriodVO>>>(collect);
    }

    @ApiOperation(value="\u662f\u5426\u5f00\u542f\u8c03\u6574\u671f")
    @GetMapping(value={"/des/enableByDataScheme"})
    public Res<Boolean> enable(@RequestParam String dataSchemeKey) {
        List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        boolean b = dataSchemeDimension.stream().anyMatch(x -> "ADJUST".equals(x.getDimKey()));
        return new Res<Boolean>(b);
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u83b7\u53d6\u65f6\u671f")
    @GetMapping(value={"/des/get_period"})
    public Res<String> getPeriodTypeByDesFormScheme(@RequestParam String formSchemeKey) {
        if (!StringUtils.hasLength(formSchemeKey)) {
            return new Res<String>(false, "\u62a5\u8868\u65b9\u6848\u5173\u952e\u5b57\u4e0d\u80fd\u4e3a\u7a7a");
        }
        DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.queryFormSchemeDefine(formSchemeKey);
        if (formScheme == null) {
            return new Res<String>();
        }
        DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        if (designTaskDefine == null) {
            return new Res<String>();
        }
        return new Res<String>(designTaskDefine.getDateTime());
    }

    @ApiOperation(value="\u6839\u636e\u6570\u636e\u65b9\u6848\u83b7\u53d6\u65f6\u671f")
    @GetMapping(value={"/get_period_by_des_data_scheme"})
    public Res<String> getPeriodTypeByDesDataScheme(@RequestParam String dataSchemeKey) {
        if (!StringUtils.hasLength(dataSchemeKey)) {
            return new Res<String>(false, "\u6570\u636e\u65b9\u6848\u5173\u952e\u5b57\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
        if (CollectionUtils.isEmpty(dataSchemeDimension)) {
            return new Res<String>();
        }
        String dimKey = ((DesignDataDimension)dataSchemeDimension.get(0)).getDimKey();
        return new Res<String>(dimKey);
    }

    static class Res<T> {
        private boolean success;
        private T data;

        public Res() {
            this(null);
        }

        public Res(T data) {
            this.data = data;
            this.success = true;
        }

        public Res(boolean success, T data) {
            this.data = data;
            this.success = success;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}

