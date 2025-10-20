/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.mappingscheme.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.Columns;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.client.dto.ParseSqlParamDTO;
import com.jiuqi.dc.mappingscheme.client.vo.RuleTypeShowVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseDataRefDefineClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/ref/define/basedata";

    @GetMapping(value={"/api/datacenter/v1/ref/define/basedata/rule_type"})
    public BusinessResponseEntity<List<RuleTypeShowVO>> ruleType();

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/tree"})
    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody DataRefDefineListDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/list"})
    public BusinessResponseEntity<List<BaseDataMappingDefineDTO>> list(@RequestBody DataRefDefineListDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/define/basedata/get/{id}"})
    public BusinessResponseEntity<BaseDataMappingDefineDTO> get(@PathVariable String var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/fixed"})
    public BusinessResponseEntity<BaseDataMappingDefineDTO> fixed(@RequestBody DataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/assistdim"})
    public BusinessResponseEntity<List<DimensionVO>> listAssistDim();

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/parse_sql"})
    public BusinessResponseEntity<List<SelectOptionVO>> parseSql(@RequestBody BaseDataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/parse_sql_by_scheme"})
    public BusinessResponseEntity<List<SelectOptionVO>> parseSqlByScheme(@RequestBody ParseSqlParamDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/create"})
    public BusinessResponseEntity<Boolean> create(@RequestBody BaseDataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/modify"})
    public BusinessResponseEntity<Boolean> modify(@RequestBody BaseDataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/basedata/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody BaseDataMappingDefineDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/define/basedata/getRefTableColumns/{tableName}"})
    public BusinessResponseEntity<List<Columns>> getRefTableColumns(@PathVariable String var1);
}

