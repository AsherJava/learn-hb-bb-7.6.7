/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.consolidatedsystem.api.option;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.FieldDefineVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.option.ConsolidatedOptionClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ConsolidatedOptionClient {
    public static final String CONSOLIDATED_OPTION_API_BASE_PATH = "/api/gcreport/v1/consolidatedOption";

    @GetMapping(value={"/api/gcreport/v1/consolidatedOption/systems/{systemId}"})
    public BusinessResponseEntity<ConsolidatedOptionVO> getConsolidatedOptionData(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedOption/systems/bySchemeId/{schemeId}/{periodStr}"})
    public BusinessResponseEntity<ConsolidatedOptionVO> getOptionDataBySchemeId(@PathVariable(value="schemeId") String var1, @PathVariable(value="periodStr") String var2);

    @PostMapping(value={"/api/gcreport/v1/consolidatedOption/systems/{systemId}"})
    public BusinessResponseEntity<String> saveOptionData(@PathVariable(value="systemId") String var1, @Valid @RequestBody ConsolidatedOptionVO var2);

    @PostMapping(value={"/api/gcreport/v1/consolidatedOption/systems/{systemId}/options/{code}"})
    public BusinessResponseEntity<String> getOptionItem(@PathVariable(value="systemId") String var1, @PathVariable(value="code") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedOption/systems/fieldDefineTree/{systemId}/{tablename}"})
    public BusinessResponseEntity<List<FieldDefineVO>> getFieldDefineTree(@PathVariable(value="systemId") String var1, @PathVariable(value="tablename") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedOption/getDimensionsBySchemeId/{tableName}/{schemeId}/{periodStr}"})
    public BusinessResponseEntity<List<DimensionVO>> getDimensionsByTableName(@PathVariable(value="tableName") String var1, @PathVariable(value="schemeId") String var2, @PathVariable(value="periodStr") String var3);

    @GetMapping(value={"/api/gcreport/v1/consolidatedOption/getAllDimensionsBySchemeId/{tableName}/{schemeId}/{periodStr}"})
    public BusinessResponseEntity<List<DimensionVO>> getAllDimensionsByTableName(@PathVariable(value="tableName") String var1, @PathVariable(value="schemeId") String var2, @PathVariable(value="periodStr") String var3);

    @GetMapping(value={"/api/gcreport/v1/consolidatedOption/getDimensionsBySystemId/{tableName}/{systemId}"})
    public BusinessResponseEntity<List<DimensionVO>> getDimensionsByTableName(@PathVariable(value="tableName") String var1, @PathVariable(value="systemId") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedOption/getAllDimensionsBySystemId/{tableName}/{systemId}"})
    public BusinessResponseEntity<List<DimensionVO>> getAllDimensionsByTableName(@PathVariable(value="tableName") String var1, @PathVariable(value="systemId") String var2);
}

