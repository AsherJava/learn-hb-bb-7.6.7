/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle.operator;

import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;
import com.itextpdf.commons.bouncycastle.operator.AbstractOperatorCreationException;
import com.itextpdf.commons.bouncycastle.operator.IDigestCalculator;

public interface IDigestCalculatorProvider {
    public IDigestCalculator get(IAlgorithmIdentifier var1) throws AbstractOperatorCreationException;
}

