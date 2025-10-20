/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.penetratebill.client.PenetrateBillClient
 *  com.jiuqi.dc.penetratebill.client.common.CustomParam
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateContext
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateParam
 *  com.jiuqi.dc.penetratebill.client.dto.MatchResultDTO
 *  com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.penetratebill.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.penetratebill.client.PenetrateBillClient;
import com.jiuqi.dc.penetratebill.client.common.CustomParam;
import com.jiuqi.dc.penetratebill.client.common.PenetrateContext;
import com.jiuqi.dc.penetratebill.client.common.PenetrateParam;
import com.jiuqi.dc.penetratebill.client.dto.MatchResultDTO;
import com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO;
import com.jiuqi.dc.penetratebill.impl.service.PenetrateBillService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PenetrateBillController
implements PenetrateBillClient {
    @Autowired
    private PenetrateBillService service;

    public BusinessResponseEntity<List<BaseDataShowVO>> listScopeType() {
        return BusinessResponseEntity.ok(this.service.listScopeType());
    }

    public BusinessResponseEntity<List<BaseDataShowVO>> listHandler() {
        return BusinessResponseEntity.ok(this.service.listHandler());
    }

    public BusinessResponseEntity<List<BaseDataShowVO>> listOpenWay() {
        return BusinessResponseEntity.ok(this.service.listOpenWay());
    }

    public BusinessResponseEntity<List<CustomParam>> listParam(String handlerCode) {
        return BusinessResponseEntity.ok(this.service.listParam(handlerCode));
    }

    public BusinessResponseEntity<List<TreeDTO>> tree(PenetrateSchemeDTO dto) {
        return BusinessResponseEntity.ok(this.service.tree(dto));
    }

    public BusinessResponseEntity<PenetrateSchemeDTO> get(String id) {
        return BusinessResponseEntity.ok((Object)this.service.getById(id));
    }

    public BusinessResponseEntity<Boolean> create(@Valid PenetrateSchemeDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.create(dto));
    }

    public BusinessResponseEntity<Boolean> modify(@Valid PenetrateSchemeDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.modify(dto));
    }

    public BusinessResponseEntity<Boolean> delete(PenetrateSchemeDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.delete(dto));
    }

    public BusinessResponseEntity<MatchResultDTO> match(PenetrateContext context) {
        return BusinessResponseEntity.ok((Object)this.service.match(context));
    }

    public BusinessResponseEntity<PenetrateParam> penetrate(PenetrateContext context) {
        return BusinessResponseEntity.ok((Object)this.service.penetrate(context));
    }
}

