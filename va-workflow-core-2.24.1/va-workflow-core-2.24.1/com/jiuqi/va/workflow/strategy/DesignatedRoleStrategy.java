/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 */
package com.jiuqi.va.workflow.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesignatedRoleStrategy
implements Strategy {
    @Autowired
    private AuthUserClient authUserClient;

    public String getName() {
        return "designatedRole";
    }

    public String getTitle() {
        return "\u6307\u5b9a\u89d2\u8272";
    }

    public String getOrder() {
        return "002";
    }

    public String getStrategyModule() {
        return "general";
    }

    public Set<String> execute(Object params) {
        LinkedHashSet<String> list = new LinkedHashSet<String>();
        Map param = (Map)params;
        ArrayNode paramsList = (ArrayNode)param.get("assignParam");
        for (JsonNode node : paramsList) {
            UserDTO userDTO = new UserDTO();
            userDTO.setRoleName(node.get("value").asText());
            PageVO pageVO = this.authUserClient.list(userDTO);
            if (pageVO == null || pageVO.getRows() == null) continue;
            for (UserDO userDO : pageVO.getRows()) {
                list.add(userDO.getId().toString());
            }
        }
        return list;
    }
}

