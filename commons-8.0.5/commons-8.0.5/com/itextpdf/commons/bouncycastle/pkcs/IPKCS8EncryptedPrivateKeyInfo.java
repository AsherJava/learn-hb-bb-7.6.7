/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.pkcs;

import com.itextpdf.commons.bouncycastle.asn1.pkcs.IPrivateKeyInfo;
import com.itextpdf.commons.bouncycastle.operator.IInputDecryptorProvider;
import com.itextpdf.commons.bouncycastle.pkcs.AbstractPKCSException;

public interface IPKCS8EncryptedPrivateKeyInfo {
    public IPrivateKeyInfo decryptPrivateKeyInfo(IInputDecryptorProvider var1) throws AbstractPKCSException;
}

