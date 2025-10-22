/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.constraints.NotNull
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.authz.web;

import com.jiuqi.nr.authz.ResultObject;
import com.jiuqi.nr.authz.bean.UserQueryParam;
import com.jiuqi.nr.authz.bean.UserQueryParamExtend;
import com.jiuqi.nr.authz.bean.UserTreeNode;
import com.jiuqi.nr.authz.service.IUserTreeService;
import com.jiuqi.nr.common.itree.ITree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/itree-common/user"})
@Api(tags={"\u7528\u6237\u5217\u8868\u670d\u52a1"})
public class UserTreeController {
    private static final Logger logger = LoggerFactory.getLogger(UserTreeController.class);
    @Autowired
    private IUserTreeService iUserTreeService;

    @PostMapping(value={"query/by-ids"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u5217\u8868", notes="\u6839\u636e\u7528\u6237id\u5217\u8868")
    public ResultObject getUsersByIds(@NotNull @RequestBody List<String> userIds) {
        ResultObject resultObject = new ResultObject();
        try {
            if (userIds.isEmpty()) {
                resultObject.setData(Collections.emptyList());
            } else {
                List<ITree<UserTreeNode>> usersByIds = this.iUserTreeService.getUsersByIds(userIds);
                resultObject.setData(usersByIds);
            }
            resultObject.setState(true);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"query/by-param"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u53ef\u89c1\u7684\u7528\u6237\u5217\u8868\uff0c\u6761\u4ef6\u67e5\u8be2", notes="\u6309\u7167\u89d2\u8272\u8fc7\u6ee4")
    public ResultObject getUserByUserQueryParam(@NotNull @RequestBody UserQueryParam userQueryParam) {
        ResultObject resultObject = new ResultObject();
        try {
            List<ITree<UserTreeNode>> userByUserQueryParam = this.iUserTreeService.getUserByUserQueryParam(userQueryParam);
            resultObject.setState(true);
            resultObject.setData(userByUserQueryParam);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"query-count/by-param"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u53ef\u89c1\u7684\u7528\u6237\u5217\u8868\uff0c\u6761\u4ef6\u67e5\u8be2", notes="\u6309\u7167\u89d2\u8272\u8fc7\u6ee4")
    public ResultObject getUserByUserCountQueryParam(@NotNull @RequestBody UserQueryParam userQueryParam) {
        ResultObject resultObject = new ResultObject();
        try {
            long userCountByUserQueryParam = this.iUserTreeService.getUserCountByUserQueryParam(userQueryParam);
            resultObject.setState(true);
            resultObject.setData(userCountByUserQueryParam);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }

    @PostMapping(value={"query/by-params"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u7528\u6237\u53ef\u89c1\u7684\u7528\u6237\u5217\u8868\uff0c\u6761\u4ef6\u67e5\u8be2", notes="\u6309\u7167\u89d2\u8272\u8fc7\u6ee4")
    public ResultObject getUsersByUserQueryParamExtend(@NotNull @RequestBody UserQueryParamExtend userQueryParam) {
        ResultObject resultObject = new ResultObject();
        try {
            List<ITree<UserTreeNode>> userByUserQueryParam = this.iUserTreeService.getUsersByUserQueryParamExtend(userQueryParam);
            resultObject.setState(true);
            resultObject.setData(userByUserQueryParam);
        }
        catch (Exception e) {
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }
}

