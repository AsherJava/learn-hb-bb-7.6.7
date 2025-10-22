/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.service.ExtentStyleService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class ExtentStyleController {
    private static final Logger log = LoggerFactory.getLogger(ExtentStyleController.class);
    @Autowired
    private ExtentStyleService extentStyleService;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private IDesignTimeViewController nrDesignController;

    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u6761\u76ee")
    @GetMapping(value={"getIBaseData/{dataBaseKey}/{formKey}/{regionKey}"})
    public String getIBaseData(@PathVariable(value="dataBaseKey") String databaseKey, @PathVariable(value="formKey") String formKey, @PathVariable(value="regionKey") String regionKey) throws JQException, JsonProcessingException {
        ExtentStyle extentStyle = null;
        ObjectMapper mapper = this.commonHelper.gridSerialize();
        String viewKey = this.extentStyleService.getEntityKey(regionKey, formKey, databaseKey);
        List<IEntityRow> queryItemData2 = this.extentStyleService.queryItemData(viewKey);
        try {
            String regionSettingKey = this.nrDesignController.queryDataRegionDefine(regionKey).getRegionSettingKey();
            byte[] bigData = this.designFormDefineService.getBigData(regionSettingKey, "EXTENTSTYLE");
            extentStyle = bigData != null ? this.extentStyleService.getFormStyle(formKey, queryItemData2, bigData, regionKey, databaseKey) : this.extentStyleService.getFormStyle(formKey, queryItemData2, regionKey, databaseKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return mapper.writeValueAsString(extentStyle);
    }

    @PostMapping(value={"saveEntentStyle"})
    public void saveExtentStyle(@RequestBody String extentStyle) throws IOException {
        ObjectMapper mapper = this.commonHelper.gridDeSerialize();
        ExtentStyle saveExtentStyle = (ExtentStyle)mapper.readValue(extentStyle, ExtentStyle.class);
        byte[] designTaskFlowsDefineToBytes = ExtentStyle.designTaskFlowsDefineToBytes((ExtentStyle)saveExtentStyle);
        String regionKey = saveExtentStyle.getRegionKey();
        this.extentStyleService.saveEntityStyle(designTaskFlowsDefineToBytes, regionKey, "EXTENTSTYLE");
    }
}

