/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.gcreport.formulaschemeconfig.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigCategoryDTO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="com.jiuqi.gcreport.formulaschemeconfig.client.FormulaSchemeConfigClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface FormulaSchemeConfigManageClient {
    public static final String FORMULASCHEMECONFIGMANAGE_API_PREFIX = "/api/gcreport/v1//formulaSchemeConfig";

    @GetMapping(value={"/api/gcreport/v1//formulaSchemeConfig/listCategory"})
    public BusinessResponseEntity<List<FormulaSchemeConfigCategoryDTO>> listCategory();

    @GetMapping(value={"/api/gcreport/v1//formulaSchemeConfig/listAllCategoryAppInfo"})
    public BusinessResponseEntity<List<FormulaSchemeConfigCategoryDTO>> listAllCategoryAppInfo();
}

