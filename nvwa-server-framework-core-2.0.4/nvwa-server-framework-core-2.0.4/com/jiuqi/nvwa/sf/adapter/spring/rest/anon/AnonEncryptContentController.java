/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.RSAEncryptUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.np.core.utils.RSAEncryptUtils;
import com.jiuqi.nvwa.sf.adapter.spring.rest.anon.dto.EncryptKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/framework/api"})
public class AnonEncryptContentController {
    @Value(value="${jiuqi.nvwa.sf.encryption-disable:false}")
    private boolean closeEncryption;

    @GetMapping(value={"/encrypt/key"})
    public EncryptKey encryptKey() {
        String alias = RSAEncryptUtils.getAlias();
        String publicKey = RSAEncryptUtils.getPublicKey();
        return new EncryptKey(alias, publicKey, this.closeEncryption ? 1 : 0);
    }
}

