/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.annotation.DecryptRequest
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.BaseDataRefDefineClient
 *  com.jiuqi.dc.mappingscheme.client.common.Columns
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.ParseSqlParamDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.RuleTypeShowVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.mappingscheme.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.annotation.DecryptRequest;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.BaseDataRefDefineClient;
import com.jiuqi.dc.mappingscheme.client.common.Columns;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.client.dto.ParseSqlParamDTO;
import com.jiuqi.dc.mappingscheme.client.vo.RuleTypeShowVO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseDataRefDefineController
implements BaseDataRefDefineClient {
    @Autowired
    private BaseDataRefDefineService service;

    public BusinessResponseEntity<List<RuleTypeShowVO>> ruleType() {
        return BusinessResponseEntity.ok(this.service.ruleType());
    }

    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody DataRefDefineListDTO dto) {
        return BusinessResponseEntity.ok(this.service.tree(dto));
    }

    public BusinessResponseEntity<List<BaseDataMappingDefineDTO>> list(@RequestBody DataRefDefineListDTO dto) {
        return BusinessResponseEntity.ok(this.service.list(dto));
    }

    public BusinessResponseEntity<BaseDataMappingDefineDTO> get(@PathVariable String id) {
        return BusinessResponseEntity.ok((Object)this.service.findById(id));
    }

    public BusinessResponseEntity<BaseDataMappingDefineDTO> fixed(@RequestBody DataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.fixed(dto));
    }

    public BusinessResponseEntity<List<DimensionVO>> listAssistDim() {
        return BusinessResponseEntity.ok(this.service.listAssistDim());
    }

    public BusinessResponseEntity<List<SelectOptionVO>> parseSql(BaseDataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok(this.service.parseSql(dto.getDataSchemeCode(), dto.getAdvancedSql()));
    }

    public BusinessResponseEntity<List<SelectOptionVO>> parseSqlByScheme(@DecryptRequest ParseSqlParamDTO parseSqlParamDTO) {
        return BusinessResponseEntity.ok(this.service.parseSqlByDataSource(parseSqlParamDTO.getDataSourceCode(), parseSqlParamDTO.getAdvancedSql()));
    }

    public BusinessResponseEntity<Boolean> create(@RequestBody BaseDataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.create(dto));
    }

    public BusinessResponseEntity<Boolean> modify(@RequestBody BaseDataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.modify(dto));
    }

    public BusinessResponseEntity<Boolean> delete(@RequestBody BaseDataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.delete(dto));
    }

    public BusinessResponseEntity<List<Columns>> getRefTableColumns(@PathVariable String tableName) {
        return BusinessResponseEntity.ok(this.service.getRefTableColumns(tableName));
    }
}

