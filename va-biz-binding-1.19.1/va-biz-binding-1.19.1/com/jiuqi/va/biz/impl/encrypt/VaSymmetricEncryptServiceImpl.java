/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricDecryptor
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptor
 */
package com.jiuqi.va.biz.impl.encrypt;

import com.jiuqi.nvwa.encryption.common.EncryptionException;
import com.jiuqi.nvwa.encryption.crypto.SymmetricDecryptor;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptor;
import com.jiuqi.va.biz.intf.encrypt.VaSymmetricEncryptService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaSymmetricEncryptServiceImpl
implements VaSymmetricEncryptService {
    @Autowired
    private SymmetricEncryptFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(VaSymmetricEncryptServiceImpl.class);

    @Override
    public List<String> doEncrypt(List<String> plaintexts) {
        if (plaintexts == null || plaintexts.isEmpty()) {
            return new ArrayList<String>();
        }
        try {
            HashMap<String, String> cache = new HashMap<String, String>();
            SymmetricEncryptor encryptor = this.factory.createEncryptor();
            ArrayList<String> results = new ArrayList<String>(plaintexts.size());
            for (String plaintext : plaintexts) {
                if (plaintext == null) {
                    results.add(null);
                    continue;
                }
                String result = cache.computeIfAbsent(plaintext, key -> {
                    try {
                        return encryptor.encrypt(key);
                    }
                    catch (EncryptionException e) {
                        throw new RuntimeException(e);
                    }
                });
                results.add(result);
            }
            return results;
        }
        catch (Exception e) {
            logger.error("\u52a0\u5bc6\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> doDecrypt(List<String> ciphertexts) {
        if (ciphertexts == null || ciphertexts.isEmpty()) {
            return new ArrayList<String>();
        }
        try {
            HashMap<String, String> cache = new HashMap<String, String>();
            SymmetricDecryptor decrypt = this.factory.createDecryptor();
            ArrayList<String> results = new ArrayList<String>(ciphertexts.size());
            for (String ciphertext : ciphertexts) {
                if (ciphertext == null) {
                    results.add(null);
                    continue;
                }
                String result = cache.computeIfAbsent(ciphertext, key -> {
                    try {
                        return decrypt.decrypt(key);
                    }
                    catch (EncryptionException e) {
                        throw new RuntimeException(e);
                    }
                });
                results.add(result);
            }
            return results;
        }
        catch (Exception e) {
            logger.error("\u89e3\u5bc6\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }
}

