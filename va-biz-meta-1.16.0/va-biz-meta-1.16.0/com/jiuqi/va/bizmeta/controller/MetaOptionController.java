/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupAuthVO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.service.IMetaAuthService;
import com.jiuqi.va.bizmeta.service.IMetaOptionService;
import com.jiuqi.va.bizmeta.service.impl.MetaGroupService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/authGroups"})
public class MetaOptionController {
    @Autowired
    private MetaGroupService groupService;
    @Autowired
    private IMetaOptionService metaOptionService;
    @Autowired
    private IMetaAuthService metaAuthService;

    @GetMapping(value={"/getResult"})
    public MetaGroupAuthVO getResult() {
        OptionItemVO optionItemVO;
        OptionItemDTO optionParam = new OptionItemDTO();
        optionParam.setName("META001");
        List<OptionItemVO> optionItemList = this.metaOptionService.list(optionParam);
        UserLoginDTO user = ShiroUtil.getUser();
        MetaGroupAuthVO metaGroupAuthVO = new MetaGroupAuthVO();
        metaGroupAuthVO.setAuthFlag(0);
        if (user.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO = optionItemList.get(0)).getVal().equals("1")) {
            metaGroupAuthVO.setAuthFlag(1);
            MetaAuthDTO metaAuthCheck = new MetaAuthDTO();
            metaAuthCheck.setTenantName(ShiroUtil.getTenantName());
            metaAuthCheck.setGroupflag(1);
            metaAuthCheck.setMetaType(MetaTypeEnum.WORKFLOW.getName());
            Set<String> userAuth = this.metaAuthService.checkUserAuth(metaAuthCheck);
            List<MetaGroupDTO> allGroup = this.groupService.getGroupList(null, null, OperateType.DESIGN);
            HashMap<String, String> groupRelationship = new HashMap<String, String>();
            for (MetaGroupDTO groupDto : allGroup) {
                this.metaAuthService.addRelationshipToMap(groupDto, groupRelationship);
            }
            HashSet<String> groupSet = new HashSet<String>(8);
            for (String name : userAuth) {
                groupSet.add(name);
                this.groupService.addChildrenToSet(name, groupRelationship, groupSet);
            }
            metaGroupAuthVO.setGroupAuth(groupSet);
        }
        return metaGroupAuthVO;
    }
}

