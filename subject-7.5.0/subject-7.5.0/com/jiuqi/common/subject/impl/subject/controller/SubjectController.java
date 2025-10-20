/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.subject.impl.subject.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.dto.TreeDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.common.subject.impl.subject.vo.SubjectInitVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectController {
    final String API_BASE_PATH = "/api/gcreport/v1/base/subject";
    @Autowired
    private SubjectService service;

    @GetMapping(value={"/api/gcreport/v1/base/subject/initSubjectInfo"})
    public BusinessResponseEntity<SubjectInitVO> initSubjectInfo() {
        return BusinessResponseEntity.ok((Object)this.service.getSubjectInitInfo());
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/sync_cache"})
    public BusinessResponseEntity<Boolean> syncCache() {
        return BusinessResponseEntity.ok((Object)this.service.syncCache());
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/define"})
    public BusinessResponseEntity<BaseDataDefineDO> define(@RequestBody BaseDataDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.findDefineByName(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/list"})
    public BusinessResponseEntity<PageVO<SubjectDTO>> list(@RequestBody BaseDataDTO dto) {
        return BusinessResponseEntity.ok(this.service.pagination(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/tree"})
    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody BaseDataDTO dto) {
        return BusinessResponseEntity.ok(this.service.tree(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/treeByCodes"})
    public BusinessResponseEntity<List<TreeDTO>> treeByCodes(@RequestBody BaseDataDTO dto) {
        return BusinessResponseEntity.ok(this.service.buildTreeBySubjectCodes(dto));
    }

    @GetMapping(value={"/api/gcreport/v1/base/subject/get/{id}"})
    public BusinessResponseEntity<SubjectDTO> get(@PathVariable UUID id) {
        return BusinessResponseEntity.ok((Object)this.service.findById(id));
    }

    @GetMapping(value={"/api/gcreport/v1/base/subject/get_by_code/{code}"})
    public BusinessResponseEntity<SubjectDTO> getByCode(@PathVariable String code) {
        return BusinessResponseEntity.ok((Object)this.service.findByCode(code));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/create"})
    public BusinessResponseEntity<Boolean> create(@RequestBody SubjectDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.create(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/modify"})
    public BusinessResponseEntity<Boolean> modify(@RequestBody SubjectDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.modify(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody SubjectDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.delete(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/stop"})
    public BusinessResponseEntity<Boolean> stop(@RequestBody SubjectDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.stop(dto));
    }

    @PostMapping(value={"/api/gcreport/v1/base/subject/start"})
    public BusinessResponseEntity<Boolean> start(@RequestBody SubjectDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.start(dto));
    }

    @GetMapping(value={"/api/gcreport/v1/base/subject/gatAllPublishedDim"})
    public BusinessResponseEntity<List<DimensionVO>> gatAllPublishedDim() {
        return BusinessResponseEntity.ok(this.service.getAllPublishedDim());
    }
}

