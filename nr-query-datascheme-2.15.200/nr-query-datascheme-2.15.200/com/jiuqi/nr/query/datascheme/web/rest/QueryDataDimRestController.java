/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.query.datascheme.web.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataDimVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/query-datascheme/"})
@Api(tags={"\u67e5\u8be2\u6570\u636e\u65b9\u6848\u516c\u5171\u7ef4\u5ea6\u7ba1\u7406\u670d\u52a1"})
public class QueryDataDimRestController {
    @Autowired
    private IDesignQueryDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u516c\u5171\u7ef4\u5ea6")
    @GetMapping(value={"dim/query/{dataSchemeKey}"})
    public List<QueryDataDimVO> queryDataDims(@PathVariable String dataSchemeKey) {
        ArrayList<QueryDataDimVO> dims = new ArrayList<QueryDataDimVO>();
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        for (DesignDataDimension dimension : dimensions) {
            if (DimensionType.UNIT_SCOPE == dimension.getDimensionType()) continue;
            QueryDataDimVO vo = new QueryDataDimVO();
            vo.setDimKey(dimension.getDimKey());
            vo.setDimType(dimension.getDimensionType().getValue());
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dimension.getDimKey());
                vo.setDimCode(periodEntity.getCode());
                vo.setDimTitle(periodEntity.getTitle());
            } else if ("ADJUST".equals(dimension.getDimKey())) {
                vo.setDimCode(dimension.getDimKey());
                vo.setDimTitle(dimension.getDimKey());
            } else {
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimension.getDimKey());
                vo.setDimCode(iEntityDefine.getCode());
                vo.setDimTitle(iEntityDefine.getTitle());
            }
            dims.add(vo);
        }
        return dims;
    }
}

