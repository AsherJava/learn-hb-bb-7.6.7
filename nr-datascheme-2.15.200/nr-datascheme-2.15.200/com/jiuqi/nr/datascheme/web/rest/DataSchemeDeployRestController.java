/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressCacheService;
import com.jiuqi.nr.datascheme.web.facade.ProgressVO;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\u53d1\u5e03\u670d\u52a1"})
public class DataSchemeDeployRestController {
    @Autowired
    private DSProgressCacheService progressCacheService;
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @ApiOperation(value="\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff0c\u4e0d\u68c0\u67e5")
    @GetMapping(value={"deploy/unsafe/{key}"})
    public DeployResult unsafeDeploy(@PathVariable(value="key") String dataSchemeKey) throws JQException {
        if (!this.dataSchemeAuthService.canWriteScheme(dataSchemeKey)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.initProgressItem(dataSchemeKey);
        return this.dataSchemeDeployService.unsafeDeployDataScheme(dataSchemeKey, p -> this.updateProgressItem(dataSchemeKey, (ProgressItem)p), null);
    }

    @ApiOperation(value="\u53d1\u5e03\u6570\u636e\u65b9\u6848")
    @GetMapping(value={"deploy/{key}"})
    public DeployResult deploy(@PathVariable(value="key") String dataSchemeKey) throws JQException {
        if (!this.dataSchemeAuthService.canWriteScheme(dataSchemeKey)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.initProgressItem(dataSchemeKey);
        return this.dataSchemeDeployService.deployDataScheme(dataSchemeKey, p -> this.updateProgressItem(dataSchemeKey, (ProgressItem)p), null);
    }

    @ApiOperation(value="\u67e5\u8be2\u53d1\u5e03\u8fdb\u5ea6")
    @GetMapping(value={"deploy/progress/{key}"})
    public ProgressVO queryDeployState(@PathVariable(value="key") String dataSchemeKey) {
        ProgressItem progress = this.progressCacheService.getProgress(dataSchemeKey);
        return new ProgressVO(progress);
    }

    private void initProgressItem(String dataSchemeKey) {
        this.progressCacheService.removeProgress(dataSchemeKey);
    }

    private void updateProgressItem(String dataSchemeKey, ProgressItem progressItem) {
        if (progressItem == null) {
            return;
        }
        this.progressCacheService.setProgress(dataSchemeKey, progressItem);
    }

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u65b9\u6848\u53d1\u5e03\u7ed3\u679c")
    @GetMapping(value={"deploy/result/{key}"})
    public DeployResult queryDeployResult(@PathVariable(value="key") String dataSchemeKey) throws JQException {
        DataSchemeDeployStatus deployStatus = this.runtimeDataSchemeService.getDeployStatus(dataSchemeKey);
        return deployStatus.getDeployResult();
    }
}

