/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.util.OrderGenerator
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.designer.service.impl.DeployManager;
import com.jiuqi.nr.designer.web.facade.vo.ProgressVO;
import com.jiuqi.util.OrderGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class UtilController {
    @Autowired
    private DeployManager deployManager;
    @Autowired
    private ProgressCacheService progressCacheService;

    @ApiOperation(value="\u83b7\u53d650\u4e2a\u968f\u673aID")
    @RequestMapping(value={"/random-ids"}, method={RequestMethod.GET})
    public List<String> getIds() {
        return Stream.generate(UUIDUtils::getKey).limit(50L).collect(Collectors.toList());
    }

    @ApiOperation(value="\u83b7\u53d650\u4e2a\u968f\u673aCODE")
    @RequestMapping(value={"/random-codes"}, method={RequestMethod.GET})
    public List<String> getCodes() throws JQException {
        return Stream.generate(OrderGenerator::newOrder).limit(50L).collect(Collectors.toList());
    }

    @ApiOperation(value="\u53d1\u5e03\u8fdb\u5ea6\u67e5\u770b")
    @RequestMapping(value={"/deploy_progress/{id}"}, method={RequestMethod.GET})
    public ProgressVO getprogerss(@PathVariable String id) {
        ProgressItem progress = null;
        String delployObjID = this.deployManager.getDelployObjID(id);
        if (StringUtils.hasText(delployObjID)) {
            progress = this.progressCacheService.getProgress(delployObjID);
        }
        return new ProgressVO(progress);
    }

    @ApiOperation(value="\u6309\u4efb\u52a1id\u67e5\u627e\u53d1\u5e03\u8fdb\u5ea6")
    @RequestMapping(value={"/deploy_progress/t/{id}"}, method={RequestMethod.GET})
    public ProgressVO getprogerssT(@PathVariable String id) {
        ProgressItem progress = null;
        if (StringUtils.hasText(id)) {
            progress = this.progressCacheService.getProgress(id);
        }
        return new ProgressVO(progress);
    }
}

