/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.DataSchemeVO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.dc.mappingscheme.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.DataSchemeVO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeListDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface DataSchemeClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/ref/scheme";

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/plugin"})
    public BusinessResponseEntity<List<DataSchemeVO>> listPluginType();

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/datasource"})
    public BusinessResponseEntity<List<SelectOptionVO>> listDataSource();

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/tree"})
    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody DataSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/list"})
    public BusinessResponseEntity<List<DataSchemeDTO>> list(@RequestBody DataSchemeListDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/scheme/get/{id}"})
    public BusinessResponseEntity<DataSchemeDTO> get(@PathVariable String var1);

    @GetMapping(value={"/api/datacenter/v1/ref/scheme/get_by_code/{code}"})
    public BusinessResponseEntity<DataSchemeDTO> getByCode(@PathVariable String var1);

    @GetMapping(value={"/api/datacenter/v1/ref/scheme/next_code"})
    public BusinessResponseEntity<String> calNextCode();

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/create"})
    public BusinessResponseEntity<Boolean> create(@RequestBody DataSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/modify"})
    public BusinessResponseEntity<Boolean> modify(@RequestBody DataSchemeDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/scheme/quoted"})
    public BusinessResponseEntity<Boolean> quoted(@RequestParam String var1);

    @GetMapping(value={"/api/datacenter/v1/ref/scheme/init"})
    public BusinessResponseEntity<Boolean> init(@RequestParam String var1);

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody DataSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/stop"})
    public BusinessResponseEntity<Boolean> stop(@RequestBody DataSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/start"})
    public BusinessResponseEntity<Boolean> start(@RequestBody DataSchemeDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/scheme/initTemporaryTable/{id}"})
    public BusinessResponseEntity<String> initTemporaryTable(@PathVariable String var1);

    @GetMapping(value={"/api/datacenter/v1/ref/scheme/option/list/{pluginType}"})
    public BusinessResponseEntity<List<DataSchemeOptionVO>> getOptionList(String var1);

    @PostMapping(value={"/api/datacenter/v1/ref/scheme/initPluginInfo"})
    public BusinessResponseEntity<DataSchemeDTO> initPluginInfo(@RequestBody DataSchemeDTO var1);
}

