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
 */
package com.jiuqi.bde.bizmodel.client;

import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.bde.bizmodel.client.util.TreeNode;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.CustomFetchFormVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoParamVO;
import com.jiuqi.bde.bizmodel.client.vo.ExtInfoResultVO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BizModelClient {
    public static final String BIZMODEL_API_PREFIX = "/api/bde/v1/bizModel";

    @GetMapping(value={"/api/bde/v1/bizModel/listByCategory"})
    public BusinessResponseEntity<List<? extends BizModelDTO>> listByCategory(@RequestParam(value="category") String var1);

    @GetMapping(value={"/api/bde/v1/bizModel/list"})
    public BusinessResponseEntity<List<BizModelDTO>> list();

    @GetMapping(value={"/api/bde/v1/bizModel/listBizModelAllProps"})
    public BusinessResponseEntity<List<BizModelAllPropsDTO>> listBizModelAllProps();

    @GetMapping(value={"/api/bde/v1/bizModel/get/{bizModelCode"})
    public BusinessResponseEntity<BizModelDTO> get(@PathVariable(value="bizModelCode") String var1);

    @GetMapping(value={"/api/bde/v1/fetch/getFetchSourceColumnDefines/{bizModelCode}"})
    public BusinessResponseEntity<BizModelColumnDefineVO> getColumnDefines(@PathVariable(value="bizModelCode") String var1);

    @GetMapping(value={"/api/bde/v1/bizModel/getBaseDataInputConfig"})
    public BusinessResponseEntity<Map<String, String>> getBaseDataInputConfig();

    @GetMapping(value={"/api/bde/v1/fetch/customFetchComboBoxData"})
    public BusinessResponseEntity<CustomFetchFormVO> getCustomFetchFormData();

    @GetMapping(value={"/api/bde/v1/fetch/queryTreeInit/{bizModelCode}"})
    public BusinessResponseEntity<List<TreeNode>> queryTreeInitByFetchSourceCode(@PathVariable(value="bizModelCode") String var1);

    @PostMapping(value={"/api/bde/v1/fetch/queryExtInfo"})
    public BusinessResponseEntity<Map<String, List<ExtInfoResultVO>>> queryExtInfo(@RequestBody ExtInfoParamVO var1);

    @GetMapping(value={"/api/bde/v1/bizModel/listByTfv"})
    public BusinessResponseEntity<List<BizModelDTO>> listByTfv();

    @GetMapping(value={"/api/bde/v1/bizModel/listByFin"})
    public BusinessResponseEntity<List<FinBizModelDTO>> listByFin();

    @GetMapping(value={"/api/bde/v1/bizModel/listByBaseData"})
    public BusinessResponseEntity<List<BaseDataBizModelDTO>> listByBaseData();

    @GetMapping(value={"/api/bde/v1/bizModel/listByCustom"})
    public BusinessResponseEntity<List<CustomBizModelDTO>> listByCustom();

    @Deprecated
    @GetMapping(value={"/api/bde/v1/fetch/listFetchSource"})
    public BusinessResponseEntity<List<BizModelDTO>> listFetchSource();
}

