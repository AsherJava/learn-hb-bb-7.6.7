/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.annotation.DecryptRequest
 *  com.jiuqi.dc.base.common.annotation.EncryptResponse
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.DataSchemeVO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.DataSchemeClient
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeListDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.mappingscheme.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.annotation.DecryptRequest;
import com.jiuqi.dc.base.common.annotation.EncryptResponse;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.DataSchemeVO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.DataSchemeClient;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeListDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO;
import com.jiuqi.dc.mappingscheme.impl.annotation.DataSchemeCheck;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSchemeController
implements DataSchemeClient {
    @Autowired
    private DataSchemeService service;
    @Autowired
    private DataSchemeOptionService optionService;

    public BusinessResponseEntity<List<DataSchemeVO>> listPluginType() {
        return BusinessResponseEntity.ok(this.service.listPluginType());
    }

    public BusinessResponseEntity<List<SelectOptionVO>> listDataSource() {
        return BusinessResponseEntity.ok(this.service.listDataSource());
    }

    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody DataSchemeDTO dto) {
        return BusinessResponseEntity.ok(this.service.tree(dto));
    }

    public BusinessResponseEntity<List<DataSchemeDTO>> list(@RequestBody DataSchemeListDTO dto) {
        return BusinessResponseEntity.ok(this.service.list(dto));
    }

    @EncryptResponse
    public BusinessResponseEntity<DataSchemeDTO> get(@PathVariable String id) {
        return BusinessResponseEntity.ok((Object)this.service.findById(id));
    }

    public BusinessResponseEntity<DataSchemeDTO> getByCode(@PathVariable String code) {
        return BusinessResponseEntity.ok((Object)this.service.findByCode(code));
    }

    public BusinessResponseEntity<String> calNextCode() {
        return BusinessResponseEntity.ok((Object)this.service.calNextCode());
    }

    @DataSchemeCheck
    public BusinessResponseEntity<Boolean> create(@RequestBody @DecryptRequest DataSchemeDTO DataSchemeDTO2) {
        this.service.check(DataSchemeDTO2);
        return BusinessResponseEntity.ok((Object)this.service.create(DataSchemeDTO2));
    }

    @DataSchemeCheck
    public BusinessResponseEntity<Boolean> modify(@RequestBody @DecryptRequest DataSchemeDTO DataSchemeDTO2) {
        this.service.modifyCheck(DataSchemeDTO2);
        return BusinessResponseEntity.ok((Object)this.service.modify(DataSchemeDTO2));
    }

    public BusinessResponseEntity<Boolean> quoted(String dataSource) {
        return BusinessResponseEntity.ok((Object)this.service.quoted(dataSource));
    }

    public BusinessResponseEntity<Boolean> init(String dataSource) {
        return BusinessResponseEntity.ok((Object)this.service.init(dataSource));
    }

    public BusinessResponseEntity<Boolean> delete(@RequestBody DataSchemeDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.delete(dto));
    }

    @DataSchemeCheck
    public BusinessResponseEntity<Boolean> stop(@RequestBody DataSchemeDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.stop(dto));
    }

    @DataSchemeCheck
    public BusinessResponseEntity<Boolean> start(@RequestBody DataSchemeDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.start(dto));
    }

    public BusinessResponseEntity<String> initTemporaryTable(String id) {
        return BusinessResponseEntity.ok((Object)this.service.initTemporaryTable(id));
    }

    public BusinessResponseEntity<List<DataSchemeOptionVO>> getOptionList(@PathVariable String pluginType) {
        return BusinessResponseEntity.ok(this.optionService.getListByPluginType(pluginType));
    }

    @EncryptResponse
    public BusinessResponseEntity<DataSchemeDTO> initPluginInfo(DataSchemeDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.initPluginInfo(dto));
    }
}

