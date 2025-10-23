/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.DataSchemeEnum
 *  com.jiuqi.nr.datascheme.web.base.EntityUtil
 *  com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO
 *  com.jiuqi.nr.datascheme.web.facade.DataSchemeVO
 *  com.jiuqi.nr.datascheme.web.rest.DataSchemeRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.query.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO;
import com.jiuqi.nr.datascheme.web.facade.DataSchemeVO;
import com.jiuqi.nr.datascheme.web.rest.DataSchemeRestController;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nr.query.datascheme.service.IQueryDataSchemeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags={"\u67e5\u8be2\u6570\u636e\u65b9\u6848\u7ba1\u7406\u670d\u52a1"})
@JQRestController
@RequestMapping(value={"api/v1/query-datascheme/"})
public class QueryDataSchemeRestController {
    private final Logger logger = LoggerFactory.getLogger(DataSchemeRestController.class);
    @Autowired
    private IDesignQueryDataSchemeService designDataSchemeService;
    @Autowired
    private IQueryDataSchemeValidator dataSchemeValidator;

    @ApiOperation(value="\u65b0\u589e\u67e5\u8be2\u6570\u636e\u65b9\u6848")
    @PostMapping(value={"scheme/add"})
    @Transactional(rollbackFor={Exception.class})
    public String addDataScheme(@RequestBody DataSchemeVO schemeObj) throws JQException {
        this.logger.debug("\u65b0\u589e\u67e5\u8be2\u6570\u636e\u65b9\u6848\uff1a{}[{}]\u3002", (Object)schemeObj.getTitle(), (Object)schemeObj.getCode());
        DesignDataScheme scheme = EntityUtil.schemeVO2Entity((IDesignDataSchemeService)this.designDataSchemeService, (BaseDataSchemeVO)schemeObj, null);
        if (!StringUtils.hasLength(schemeObj.getOrgDimKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DS_1, "\u6ca1\u6709\u8bbe\u7f6e\u5355\u4f4d\u7ef4\u5ea6.");
        }
        List<DesignDataDimension> dimensions = this.buildDimsByVO(schemeObj);
        try {
            this.designDataSchemeService.insertDataScheme(scheme, dimensions);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
        return scheme.getKey();
    }

    private List<DesignDataDimension> buildDimsByVO(DataSchemeVO schemeObj) {
        DesignDataDimension dim;
        ArrayList<DesignDataDimension> dimensions = new ArrayList<DesignDataDimension>(5);
        DesignDataDimension dimension = this.designDataSchemeService.initDataSchemeDimension();
        dimension.setDimKey(schemeObj.getOrgDimKey());
        dimension.setDimensionType(DimensionType.UNIT);
        dimensions.add(dimension);
        if (StringUtils.hasLength(schemeObj.getPeriodDimKey())) {
            DesignDataDimension period = this.designDataSchemeService.initDataSchemeDimension();
            period.setDimKey(schemeObj.getPeriodDimKey());
            period.setDimensionType(DimensionType.PERIOD);
            period.setPeriodType(PeriodType.fromType((int)schemeObj.getPeriodType()));
            dimensions.add(period);
        }
        if (schemeObj.getDimKeys() != null) {
            for (String v : schemeObj.getDimKeys()) {
                dim = this.designDataSchemeService.initDataSchemeDimension();
                dim.setDimKey(v);
                dim.setDimensionType(DimensionType.DIMENSION);
                dimensions.add(dim);
            }
        }
        if (schemeObj.getOrgDimScope() != null) {
            for (String v : schemeObj.getOrgDimScope()) {
                dim = this.designDataSchemeService.initDataSchemeDimension();
                dim.setDimKey(v);
                dim.setDimensionType(DimensionType.UNIT_SCOPE);
                dimensions.add(dim);
            }
        }
        return dimensions;
    }

    @ApiOperation(value="\u66f4\u65b0\u67e5\u8be2\u6570\u636e\u65b9\u6848")
    @PostMapping(value={"scheme/update"})
    @Transactional(rollbackFor={Exception.class})
    public void updateDataScheme(@RequestBody DataSchemeVO schemeObj) throws JQException {
        this.logger.debug("\u66f4\u65b0\u67e5\u8be2\u6570\u636e\u65b9\u6848\uff1a{}[{}]\u3002", (Object)schemeObj.getTitle(), (Object)schemeObj.getCode());
        if (!StringUtils.hasLength(schemeObj.getOrgDimKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DS_1, "\u6ca1\u6709\u8bbe\u7f6e\u5355\u4f4d\u7ef4\u5ea6.");
        }
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(schemeObj.getKey());
        DesignDataScheme scheme = EntityUtil.schemeVO2Entity((IDesignDataSchemeService)this.designDataSchemeService, (BaseDataSchemeVO)schemeObj, (DesignDataScheme)dataScheme);
        List<DesignDataDimension> designDataDimensions = this.buildDimsByVO(schemeObj);
        try {
            if (this.dataSchemeValidator.isSubLevel(scheme)) {
                this.dataSchemeValidator.checkSubLevelModify(scheme, designDataDimensions);
                this.designDataSchemeService.updateSubLevelDataScheme(scheme);
            } else {
                this.designDataSchemeService.updateDataScheme(scheme, designDataDimensions);
            }
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u67e5\u8be2\u6570\u636e\u65b9\u6848")
    @GetMapping(value={"scheme/delete/{key}"})
    public void deleteDataScheme(@PathVariable String key) throws JQException {
        try {
            this.dataSchemeValidator.levelCheckDataScheme(key);
            this.designDataSchemeService.deleteQueryDataScheme(key);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }
}

