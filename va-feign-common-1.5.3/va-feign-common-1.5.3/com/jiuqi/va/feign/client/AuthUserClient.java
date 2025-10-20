/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserOrgDTO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.feign.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserOrgDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="vaAuthUserClient", name="${feignClient.authMgr.name}", path="${feignClient.authMgr.path}", url="${feignClient.authMgr.url}")
public interface AuthUserClient {
    default public UserDO get(@RequestBody UserDTO param) {
        ResponseEntity<byte[]> respond = this.get(JSONUtil.toBytes((Object)param));
        if (respond != null) {
            return (UserDO)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), UserDO.class);
        }
        return null;
    }

    @PostMapping(value={"/user/binary/get"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> get(@RequestBody byte[] var1);

    default public PageVO<UserDO> list(@RequestBody UserDTO param) {
        ResponseEntity<byte[]> respond = this.list(JSONUtil.toBytes((Object)param));
        if (respond != null) {
            return (PageVO)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), (TypeReference)new TypeReference<PageVO<UserDO>>(){});
        }
        return null;
    }

    @PostMapping(value={"/user/binary/list"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> list(@RequestBody byte[] var1);

    default public R getLoginToken(@RequestBody UserDTO param) {
        ResponseEntity<byte[]> respond = this.getLoginToken(JSONUtil.toBytes((Object)param));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @PostMapping(value={"/user/binary/login"}, headers={"Content-Type=application/msgpack"})
    public ResponseEntity<byte[]> getLoginToken(@RequestBody byte[] var1);

    @PostMapping(value={"/user/add"})
    public R add(@RequestBody UserDTO var1);

    @PostMapping(value={"/user/update"})
    public R update(@RequestBody UserDTO var1);

    @PostMapping(value={"/user/remove"})
    public R remove(@RequestBody UserDTO var1);

    @PostMapping(value={"/user/both/org/list"})
    public UserOrgDTO listBothOrg(@RequestBody UserOrgDTO var1);
}

