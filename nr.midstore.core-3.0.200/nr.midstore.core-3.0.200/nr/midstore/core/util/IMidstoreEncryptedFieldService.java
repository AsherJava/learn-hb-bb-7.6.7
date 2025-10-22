/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.util;

import nr.midstore.core.definition.dto.MidstoreSchemeDTO;

public interface IMidstoreEncryptedFieldService {
    public String getExportEncrypeText(MidstoreSchemeDTO var1);

    public String encrypt(MidstoreSchemeDTO var1, String var2);

    public String decrypt(MidstoreSchemeDTO var1, String var2);

    public int getCiphertextMaxLength(int var1);
}

