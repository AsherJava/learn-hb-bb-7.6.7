/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.web.param.AdjustPeriodVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u8c03\u6574\u671f"})
public class AdjustPeriodRestController {
    private final AdjustPeriodDesignService adjustPeriodDesignService;
    private final IDesignDataSchemeService designDataSchemeService;
    private final IDataSchemeAuthService dataSchemeAuthService;

    public AdjustPeriodRestController(AdjustPeriodDesignService service, IDesignDataSchemeService designDataSchemeService, IDataSchemeAuthService dataSchemeAuthService) {
        this.adjustPeriodDesignService = service;
        this.designDataSchemeService = designDataSchemeService;
        this.dataSchemeAuthService = dataSchemeAuthService;
    }

    @ApiOperation(value="\u66f4\u65b0\u8c03\u6574\u671f\u6570\u636e")
    @PostMapping(value={"adjustment-period/update"})
    @Transactional
    public void update(@RequestBody List<AdjustPeriodVO> list, @RequestParam String schemeKey, @RequestParam String period) throws JQException {
        if (!this.dataSchemeAuthService.canWriteScheme(schemeKey)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        if (list != null) {
            List<DesignAdjustPeriodDTO> periodDOS = this.convert(schemeKey, period, list);
            try {
                this.adjustPeriodDesignService.updateByPeriod(schemeKey, period, periodDOS);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)DataSchemeEnum.ADJUSTMENT_PERIOD_1, e.getMessage());
            }
        }
    }

    private List<DesignAdjustPeriodDTO> convert(String schemeKey, String dimKey, List<AdjustPeriodVO> list) {
        ArrayList<DesignAdjustPeriodDTO> periodDOS = new ArrayList<DesignAdjustPeriodDTO>(list.size());
        for (AdjustPeriodVO periodVO : list) {
            DesignAdjustPeriodDTO adjustmentPeriodDTO = new DesignAdjustPeriodDTO();
            adjustmentPeriodDTO.setCode(periodVO.getCode());
            adjustmentPeriodDTO.setOrder(periodVO.getOrder());
            adjustmentPeriodDTO.setTitle(periodVO.getTitle());
            adjustmentPeriodDTO.setDataSchemeKey(schemeKey);
            adjustmentPeriodDTO.setPeriod(dimKey);
            periodDOS.add(adjustmentPeriodDTO);
        }
        return periodDOS;
    }

    @ApiOperation(value="\u83b7\u53d6\u8c03\u6574\u671f\u6570\u636e")
    @GetMapping(value={"adjust/data/query"})
    public Map<String, List<AdjustPeriodVO>> get(@RequestParam String dataSchemeKey) {
        List<DesignAdjustPeriodDTO> query = this.adjustPeriodDesignService.query(dataSchemeKey);
        query.removeIf(AdjustUtils::isNotAdjustData);
        return query.stream().collect(Collectors.groupingBy(AdjustPeriod::getPeriod, Collectors.mapping(AdjustPeriodVO::convertToVO, Collectors.toList())));
    }

    @ApiOperation(value="\u67e5\u8be2\u662f\u5426\u5f00\u542f\u8c03\u6574\u671f")
    @GetMapping(value={"adjust/enable/query"})
    public Boolean enable(@RequestParam String dataSchemeKey) {
        List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        return dataSchemeDimension.stream().anyMatch(x -> "ADJUST".equals(x.getDimKey()));
    }

    @ApiOperation(value="\u83b7\u53d6\u8c03\u6574\u671f\u6570\u636e")
    @GetMapping(value={"adjust/data/query2"})
    public Map<String, List<AdjustPeriodVO>> getByDataScheme(@RequestParam String dataSchemeKey) {
        List<DesignAdjustPeriodDTO> adjustPeriods = this.adjustPeriodDesignService.query(dataSchemeKey);
        return adjustPeriods.stream().filter(AdjustUtils::isAdjustData).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).map(AdjustPeriodVO::convertToVO).collect(Collectors.groupingBy(AdjustPeriodVO::getPeriod));
    }
}

