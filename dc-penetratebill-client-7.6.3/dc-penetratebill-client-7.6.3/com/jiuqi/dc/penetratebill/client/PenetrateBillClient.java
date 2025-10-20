/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.penetratebill.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.penetratebill.client.common.CustomParam;
import com.jiuqi.dc.penetratebill.client.common.PenetrateContext;
import com.jiuqi.dc.penetratebill.client.common.PenetrateParam;
import com.jiuqi.dc.penetratebill.client.dto.MatchResultDTO;
import com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PenetrateBillClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/penetrate/bill";

    @GetMapping(value={"/api/datacenter/v1/penetrate/bill/scope"})
    public BusinessResponseEntity<List<BaseDataShowVO>> listScopeType();

    @GetMapping(value={"/api/datacenter/v1/penetrate/bill/handler"})
    public BusinessResponseEntity<List<BaseDataShowVO>> listHandler();

    @GetMapping(value={"/api/datacenter/v1/penetrate/bill/openWay"})
    public BusinessResponseEntity<List<BaseDataShowVO>> listOpenWay();

    @GetMapping(value={"/api/datacenter/v1/penetrate/bill/handler/{handlerCode}"})
    public BusinessResponseEntity<List<CustomParam>> listParam(@PathVariable String var1);

    @PostMapping(value={"/api/datacenter/v1/penetrate/bill/tree"})
    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody PenetrateSchemeDTO var1);

    @GetMapping(value={"/api/datacenter/v1/penetrate/bill/get/{id}"})
    public BusinessResponseEntity<PenetrateSchemeDTO> get(@PathVariable String var1);

    @PostMapping(value={"/api/datacenter/v1/penetrate/bill/create"})
    public BusinessResponseEntity<Boolean> create(@RequestBody PenetrateSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/penetrate/bill/modify"})
    public BusinessResponseEntity<Boolean> modify(@RequestBody PenetrateSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/penetrate/bill/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody PenetrateSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/penetrate/bill/match"})
    public BusinessResponseEntity<MatchResultDTO> match(@RequestBody PenetrateContext var1);

    @PostMapping(value={"/api/datacenter/v1/penetrate/bill/penetrate"})
    public BusinessResponseEntity<PenetrateParam> penetrate(@RequestBody PenetrateContext var1);
}

