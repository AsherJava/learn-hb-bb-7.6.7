/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.consolidatedsystem.api.option.ConsolidatedOptionClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.FieldDefineVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web.option;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.api.option.ConsolidatedOptionClient;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.FieldDefineVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class ConsolidatedOptionController
implements ConsolidatedOptionClient {
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<ConsolidatedOptionVO> getConsolidatedOptionData(@PathVariable(value="systemId") String systemId) {
        ConsolidatedOptionVO optionData = this.consolidatedOptionService.getOptionData(systemId);
        return BusinessResponseEntity.ok((Object)optionData);
    }

    public BusinessResponseEntity<ConsolidatedOptionVO> getOptionDataBySchemeId(String schemeId, String periodStr) {
        return BusinessResponseEntity.ok((Object)this.consolidatedOptionService.getOptionDataBySchemeId(schemeId, periodStr));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> saveOptionData(@PathVariable(value="systemId") String systemId, @Valid @RequestBody ConsolidatedOptionVO optionVO) {
        this.consolidatedOptionService.saveOptionData(systemId, optionVO);
        return BusinessResponseEntity.ok((Object)"\u5df2\u4fdd\u5b58.");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> getOptionItem(@PathVariable(value="systemId") String systemId, @PathVariable(value="code") String code) {
        this.consolidatedOptionService.getOptionItem(systemId, code);
        return BusinessResponseEntity.ok((Object)"\u5df2\u4fdd\u5b58.");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<FieldDefineVO>> getFieldDefineTree(String systemId, String tablename) {
        List<FieldDefineVO> fieldDefines = this.consolidatedOptionService.getFieldDefineTree(systemId, tablename);
        return BusinessResponseEntity.ok(fieldDefines);
    }

    public BusinessResponseEntity<List<DimensionVO>> getDimensionsByTableName(String tableName, String systemId) {
        return BusinessResponseEntity.ok(this.consolidatedOptionService.getDimensionsByTableName(tableName, systemId));
    }

    public BusinessResponseEntity<List<DimensionVO>> getAllDimensionsByTableName(String tableName, String systemId) {
        return BusinessResponseEntity.ok(this.consolidatedOptionService.getAllDimensionsByTableName(tableName, systemId));
    }

    public BusinessResponseEntity<List<DimensionVO>> getDimensionsByTableName(String tableName, String schemeId, String periodStr) {
        return BusinessResponseEntity.ok(this.consolidatedOptionService.getDimensionsByTableName(tableName, schemeId, periodStr));
    }

    public BusinessResponseEntity<List<DimensionVO>> getAllDimensionsByTableName(String tableName, String schemeId, String periodStr) {
        return BusinessResponseEntity.ok(this.consolidatedOptionService.getAllDimensionsByTableName(tableName, schemeId, periodStr));
    }
}

