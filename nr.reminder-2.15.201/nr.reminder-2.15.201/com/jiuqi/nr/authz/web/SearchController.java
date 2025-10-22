/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 *  com.jiuqi.nvwa.authority.user.vo.BatchUserReq
 *  com.jiuqi.nvwa.authority.user.vo.UserQueryReq
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.authz.web;

import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.nr.authz.ResultObject;
import com.jiuqi.nr.authz.bean.EntityData;
import com.jiuqi.nr.authz.bean.UEQueryParam;
import com.jiuqi.nr.authz.bean.UserTreeNode;
import com.jiuqi.nr.authz.service.IEntityService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import com.jiuqi.nvwa.authority.user.vo.BatchUserReq;
import com.jiuqi.nvwa.authority.user.vo.UserQueryReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/itree-common/search"})
@Api(tags={"\u7528\u6237\u5217\u8868\u670d\u52a1"})
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private IEntityService iEntityService;
    @Autowired
    private NvwaUserService nvwaUserService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @PostMapping(value={"query/by-param"})
    @ApiOperation(value="\u83b7\u53d6\u641c\u7d22\u5355\u4f4d\u548c\u7528\u6237", notes="\u6309\u7167\u89d2\u8272\u8fc7\u6ee4")
    public ResultObject getUserByUserQueryParam(@RequestBody UEQueryParam ueQueryParam) {
        ResultObject resultObject = new ResultObject();
        try {
            List<IEntityRow> rows;
            ArrayList<Object> searchRes = new ArrayList<Object>();
            BatchUserReq userQueryReq = new BatchUserReq();
            userQueryReq.setKeyword(ueQueryParam.getKeyword());
            userQueryReq.setEnabled(Boolean.valueOf(true));
            userQueryReq.setPage(Integer.valueOf(1));
            userQueryReq.setMaxResult(Integer.valueOf(1000));
            userQueryReq.setOrgCode("-");
            String type = "MD_ORG";
            try {
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(ueQueryParam.getFormSchemeKey());
                String dw = formScheme.getDw();
                type = dw.substring(0, dw.indexOf("@"));
            }
            catch (Exception e) {
                logger.warn("\u641c\u7d22\u7528\u6237\u65f6\u65e0\u6cd5\u786e\u5b9a\u7ec4\u7ec7\u673a\u6784\uff0c\u4f7f\u7528 MD_ORG \u4f5c\u4e3a\u8fc7\u6ee4\u6761\u4ef6", e);
            }
            userQueryReq.setOrgType(type);
            List userDTOS = this.nvwaUserService.querySimpleUser((UserQueryReq)userQueryReq, true);
            if (!CollectionUtils.isEmpty(userDTOS)) {
                searchRes.add(new ITree((INode)new UserTreeNode("-", "\u7528\u6237", "\u7528\u6237")));
                for (UserDTO userDTO : userDTOS) {
                    searchRes.add(new ITree((INode)new UserTreeNode(userDTO.getId(), userDTO.getName(), userDTO.getNickname(), userDTO.getOrgCode())));
                }
            }
            if (!CollectionUtils.isEmpty(rows = this.iEntityService.searchEntityRow(ueQueryParam.getTaskKey(), ueQueryParam.getFormSchemeKey(), ueQueryParam.getKeyword(), null))) {
                searchRes.add(new ITree((INode)new UserTreeNode("--", "\u5355\u4f4d", "\u5355\u4f4d")));
                ArrayList<ITree> eRows = new ArrayList<ITree>();
                for (IEntityRow row : rows) {
                    EntityData entityData = new EntityData(row.getEntityKeyData(), row.getTitle(), row.getCode());
                    ITree eItem = new ITree((INode)entityData);
                    eRows.add(eItem);
                }
                searchRes.addAll(eRows);
            }
            resultObject.setState(true);
            resultObject.setData(searchRes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultObject.setState(false);
            resultObject.setMessage(e.getMessage());
        }
        return resultObject;
    }
}

