/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.queryscheme.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.queryscheme.common.QuerySchemeOptionTypeEnum;
import com.jiuqi.common.queryscheme.service.QuerySchemeService;
import com.jiuqi.common.queryscheme.vo.QuerySchemeVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuerySchemeController {
    private static final String BASE_API = "/api/gcreport/v1/queryscheme";
    @Autowired
    private QuerySchemeService querySchemeService;

    @GetMapping(value={"/api/gcreport/v1/queryscheme/condition/list/{resourceId}"})
    public BusinessResponseEntity<List<QuerySchemeVO>> listQueryCondition(@PathVariable(value="resourceId") String resourceId) {
        return BusinessResponseEntity.ok(this.querySchemeService.listByResourceIdAndOptionType(resourceId, QuerySchemeOptionTypeEnum.CONDITION.name()));
    }

    @GetMapping(value={"/api/gcreport/v1/queryscheme/list/{resourceId}/{optionType}"})
    public BusinessResponseEntity<List<QuerySchemeVO>> listByResourceIdAndOptionType(@PathVariable(value="resourceId") String resourceId, @PathVariable(value="optionType") String optionType) {
        return BusinessResponseEntity.ok(this.querySchemeService.listByResourceIdAndOptionType(resourceId, optionType));
    }

    @GetMapping(value={"/api/gcreport/v1/queryscheme/get/{id}"})
    public BusinessResponseEntity<QuerySchemeVO> getQueryScheme(@PathVariable(value="id") String id) {
        return BusinessResponseEntity.ok((Object)this.querySchemeService.getQueryScheme(id));
    }

    @GetMapping(value={"/api/gcreport/v1/queryscheme/delete/{id}"})
    public BusinessResponseEntity<Object> delete(@PathVariable(value="id") String id) {
        this.querySchemeService.delete(id);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/gcreport/v1/queryscheme/save"})
    public BusinessResponseEntity<QuerySchemeVO> save(@RequestBody QuerySchemeVO querySchemeVO) {
        return BusinessResponseEntity.ok((Object)this.querySchemeService.save(querySchemeVO));
    }

    @PostMapping(value={"/api/gcreport/v1/queryscheme/move_up"})
    public BusinessResponseEntity<QuerySchemeVO> moveUp(@RequestBody QuerySchemeVO querySchemeVO) {
        return BusinessResponseEntity.ok((Object)this.querySchemeService.moveUp(querySchemeVO));
    }

    @PostMapping(value={"/api/gcreport/v1/queryscheme/move_down"})
    public BusinessResponseEntity<QuerySchemeVO> moveDown(@RequestBody QuerySchemeVO querySchemeVO) {
        return BusinessResponseEntity.ok((Object)this.querySchemeService.moveDown(querySchemeVO));
    }

    @PostMapping(value={"/api/gcreport/v1/queryscheme/move_to"})
    public BusinessResponseEntity<Object> moveTo(@RequestBody QuerySchemeVO querySchemeVO, @RequestParam(value="id", required=false) String nextQuerySchemeId) {
        this.querySchemeService.moveTo(querySchemeVO, nextQuerySchemeId);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/gcreport/v1/queryscheme/set_default"})
    public BusinessResponseEntity<Object> setDefault(@RequestBody QuerySchemeVO querySchemeVO) {
        this.querySchemeService.setDefault(querySchemeVO);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/gcreport/v1/queryscheme/cancelDefault"})
    public BusinessResponseEntity<Object> cancelDefault(@RequestBody QuerySchemeVO querySchemeVO) {
        this.querySchemeService.cancelDefault(querySchemeVO);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/gcreport/v1/queryscheme/rename"})
    public BusinessResponseEntity<Object> rename(@RequestBody QuerySchemeVO querySchemeVO) {
        this.querySchemeService.rename(querySchemeVO);
        return BusinessResponseEntity.ok();
    }
}

