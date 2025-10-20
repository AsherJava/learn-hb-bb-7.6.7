/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  com.jiuqi.va.query.tree.web.QueryTreeClient
 */
package com.jiuqi.va.query.tree.service.join;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import com.jiuqi.va.query.tree.web.QueryTreeClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class QueryTreeClientImpl
implements QueryTreeClient {
    @Autowired
    private MenuTreeService menuTreeService;

    public BusinessResponseEntity<List<MenuTreeVO>> treeInit() {
        return BusinessResponseEntity.ok(this.menuTreeService.treeInit());
    }

    public List<TemplateInfoVO> listInit() {
        return this.menuTreeService.listInit();
    }
}

