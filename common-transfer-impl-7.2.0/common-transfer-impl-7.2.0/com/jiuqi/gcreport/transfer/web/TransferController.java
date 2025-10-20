/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.transfer.api.TransferClient
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  com.jiuqi.gcreport.transfer.vo.TransferVo
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.transfer.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.transfer.api.TransferClient;
import com.jiuqi.gcreport.transfer.service.TransferService;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.gcreport.transfer.vo.TransferVo;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class TransferController
implements TransferClient {
    @Autowired
    private TransferService transferService;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> save(@RequestBody @Valid TransferVo vo) {
        this.transferService.save(vo);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<String>> getSelectColumnsByPath(@RequestBody TransferVo vo) {
        if (vo == null || StringUtils.isEmpty((String)vo.getPath())) {
            return BusinessResponseEntity.error((String)"\u8bf7\u6c42\u5217\u9009\u7684\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        return BusinessResponseEntity.ok(this.transferService.getSelectColumnsByPath(vo.getPath(), vo.getUsingUser()));
    }

    public BusinessResponseEntity<List<String>> getSelectColumnsByPathNoUser(String path) {
        if (path == null) {
            return BusinessResponseEntity.error((String)"\u8bf7\u6c42\u5217\u9009\u7684\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        Map params = (Map)JsonUtils.readValue((String)path, (TypeReference)new TypeReference<Map<String, Object>>(){});
        path = (String)params.get("path");
        return BusinessResponseEntity.ok(this.transferService.getSelectColumnsByPathNoUser(path));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TransferColumnVo>> getColumns(@PathVariable String tableName) {
        if (tableName == null) {
            return BusinessResponseEntity.error((String)"\u8bf7\u6c42\u5217\u9009\u5217\u7684\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        return BusinessResponseEntity.ok(this.transferService.getAllField(tableName));
    }
}

