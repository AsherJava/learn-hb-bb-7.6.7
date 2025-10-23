/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.i18n.IDesignDataSchemeI18nService;
import com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;
import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.datascheme.web.facade.DataSchemeI18nVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\u591a\u8bed\u8a00\u670d\u52a1"})
public class DataSchemeI18nRestController {
    @Autowired
    private IDesignDataSchemeI18nService i18nService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @ApiOperation(value="\u662f\u5426\u542f\u7528\u591a\u8bed\u8a00")
    @PostMapping(value={"i18n/enable/multi-lang"})
    public boolean enableMultiLanguage() {
        return LanguageType.enableMultiLanguage();
    }

    @ApiOperation(value="\u67e5\u8be2\u7cfb\u7edf\u652f\u6301\u7684\u8bed\u79cd")
    @PostMapping(value={"i18n/query/lang"})
    public List<ILanguageType> queryLanguages() {
        return LanguageType.allValues();
    }

    @ApiOperation(value="\u6309\u6570\u636e\u8868\u67e5\u8be2\u6307\u6807\u3001\u5b57\u6bb5\u591a\u8bed\u8a00\u4fe1\u606f")
    @PostMapping(value={"i18n/query/{tableKey}/{type}"})
    public List<DataSchemeI18nVO> query(@PathVariable String tableKey, @PathVariable String type) {
        List<DesignDataSchemeI18nDTO> dtos = this.i18nService.getByTableKey(tableKey, type);
        return dtos.stream().map(DataSchemeI18nVO::new).collect(Collectors.toList());
    }

    @ApiOperation(value="\u6309\u6570\u636e\u8868\u62a5\u9519\u591a\u8bed\u8a00\u53c2\u6570")
    @PostMapping(value={"i18n/save/{tableKey}/{type}"})
    public void save(@PathVariable String tableKey, @PathVariable String type, @RequestBody List<DataSchemeI18nVO> vos) throws JQException {
        if (null == vos || vos.isEmpty()) {
            return;
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (null == dataTable) {
            return;
        }
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        List<DesignDataSchemeI18nDO> dos = vos.stream().map(DataSchemeI18nVO::toDO).collect(Collectors.toList());
        this.i18nService.save(tableKey, type, dos);
    }
}

