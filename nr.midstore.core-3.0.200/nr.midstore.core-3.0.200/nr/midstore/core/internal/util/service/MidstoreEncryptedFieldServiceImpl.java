/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 *  com.jiuqi.nvwa.encryption.utils.SymmetricAlgorithmUtil
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore.core.internal.util.service;

import com.jiuqi.nvwa.encryption.common.EncryptionException;
import com.jiuqi.nvwa.encryption.utils.SymmetricAlgorithmUtil;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.util.IMidstoreEncryptedFieldService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MidstoreEncryptedFieldServiceImpl
implements IMidstoreEncryptedFieldService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreEncryptedFieldServiceImpl.class);

    @Override
    public String getExportEncrypeText(MidstoreSchemeDTO scheme) {
        if (StringUtils.isNotEmpty((CharSequence)scheme.getSceneCode())) {
            String text = "\u9ed8\u8ba4";
            try {
                text = SymmetricAlgorithmUtil.getAlgorithmBySceneId((String)scheme.getSceneCode());
            }
            catch (EncryptionException e) {
                logger.error(e.getMessage(), e);
            }
            return "\u4f7f\u7528" + text + "\u8fdb\u884c\u52a0\u5bc6\u3002";
        }
        return "";
    }

    @Override
    public String encrypt(MidstoreSchemeDTO scheme, String plaintext) {
        String result = plaintext;
        if (StringUtils.isNotEmpty((CharSequence)scheme.getSceneCode()) && StringUtils.isNotEmpty((CharSequence)plaintext)) {
            try {
                result = SymmetricAlgorithmUtil.encrypt((String)scheme.getSceneCode(), (String)plaintext);
            }
            catch (EncryptionException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public String decrypt(MidstoreSchemeDTO scheme, String ciphertext) {
        String result = ciphertext;
        if (StringUtils.isNotEmpty((CharSequence)scheme.getSceneCode()) && StringUtils.isNotEmpty((CharSequence)ciphertext)) {
            try {
                result = SymmetricAlgorithmUtil.decrypt((String)scheme.getSceneCode(), (String)ciphertext);
            }
            catch (EncryptionException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public int getCiphertextMaxLength(int plaintextLength) {
        return SymmetricAlgorithmUtil.getCiphertextMaxLength((int)plaintextLength);
    }
}

