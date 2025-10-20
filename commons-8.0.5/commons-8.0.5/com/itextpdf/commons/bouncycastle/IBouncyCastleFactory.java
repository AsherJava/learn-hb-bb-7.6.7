/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.bouncycastle;

import com.itextpdf.commons.bouncycastle.IBouncyCastleTestConstantsFactory;
import com.itextpdf.commons.bouncycastle.asn1.IASN1BitString;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Encodable;
import com.itextpdf.commons.bouncycastle.asn1.IASN1EncodableVector;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Encoding;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Enumerated;
import com.itextpdf.commons.bouncycastle.asn1.IASN1GeneralizedTime;
import com.itextpdf.commons.bouncycastle.asn1.IASN1InputStream;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Integer;
import com.itextpdf.commons.bouncycastle.asn1.IASN1ObjectIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.IASN1OctetString;
import com.itextpdf.commons.bouncycastle.asn1.IASN1OutputStream;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Primitive;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Sequence;
import com.itextpdf.commons.bouncycastle.asn1.IASN1Set;
import com.itextpdf.commons.bouncycastle.asn1.IASN1String;
import com.itextpdf.commons.bouncycastle.asn1.IASN1TaggedObject;
import com.itextpdf.commons.bouncycastle.asn1.IASN1UTCTime;
import com.itextpdf.commons.bouncycastle.asn1.IDERIA5String;
import com.itextpdf.commons.bouncycastle.asn1.IDERNull;
import com.itextpdf.commons.bouncycastle.asn1.IDEROctetString;
import com.itextpdf.commons.bouncycastle.asn1.IDERSequence;
import com.itextpdf.commons.bouncycastle.asn1.IDERSet;
import com.itextpdf.commons.bouncycastle.asn1.IDERTaggedObject;
import com.itextpdf.commons.bouncycastle.asn1.cms.IAttribute;
import com.itextpdf.commons.bouncycastle.asn1.cms.IAttributeTable;
import com.itextpdf.commons.bouncycastle.asn1.cms.IContentInfo;
import com.itextpdf.commons.bouncycastle.asn1.cms.IEncryptedContentInfo;
import com.itextpdf.commons.bouncycastle.asn1.cms.IEnvelopedData;
import com.itextpdf.commons.bouncycastle.asn1.cms.IIssuerAndSerialNumber;
import com.itextpdf.commons.bouncycastle.asn1.cms.IKeyTransRecipientInfo;
import com.itextpdf.commons.bouncycastle.asn1.cms.IOriginatorInfo;
import com.itextpdf.commons.bouncycastle.asn1.cms.IRecipientIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.cms.IRecipientInfo;
import com.itextpdf.commons.bouncycastle.asn1.esf.IOtherHashAlgAndValue;
import com.itextpdf.commons.bouncycastle.asn1.esf.ISigPolicyQualifierInfo;
import com.itextpdf.commons.bouncycastle.asn1.esf.ISignaturePolicyId;
import com.itextpdf.commons.bouncycastle.asn1.esf.ISignaturePolicyIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.ess.ISigningCertificate;
import com.itextpdf.commons.bouncycastle.asn1.ess.ISigningCertificateV2;
import com.itextpdf.commons.bouncycastle.asn1.ocsp.IBasicOCSPResponse;
import com.itextpdf.commons.bouncycastle.asn1.ocsp.IOCSPObjectIdentifiers;
import com.itextpdf.commons.bouncycastle.asn1.ocsp.IOCSPResponse;
import com.itextpdf.commons.bouncycastle.asn1.ocsp.IOCSPResponseStatus;
import com.itextpdf.commons.bouncycastle.asn1.ocsp.IResponseBytes;
import com.itextpdf.commons.bouncycastle.asn1.pkcs.IPKCSObjectIdentifiers;
import com.itextpdf.commons.bouncycastle.asn1.pkcs.IRSASSAPSSParams;
import com.itextpdf.commons.bouncycastle.asn1.tsp.ITSTInfo;
import com.itextpdf.commons.bouncycastle.asn1.util.IASN1Dump;
import com.itextpdf.commons.bouncycastle.asn1.x500.IX500Name;
import com.itextpdf.commons.bouncycastle.asn1.x509.IAlgorithmIdentifier;
import com.itextpdf.commons.bouncycastle.asn1.x509.IBasicConstraints;
import com.itextpdf.commons.bouncycastle.asn1.x509.ICRLDistPoint;
import com.itextpdf.commons.bouncycastle.asn1.x509.ICRLReason;
import com.itextpdf.commons.bouncycastle.asn1.x509.IDistributionPointName;
import com.itextpdf.commons.bouncycastle.asn1.x509.IExtendedKeyUsage;
import com.itextpdf.commons.bouncycastle.asn1.x509.IExtension;
import com.itextpdf.commons.bouncycastle.asn1.x509.IExtensions;
import com.itextpdf.commons.bouncycastle.asn1.x509.IGeneralName;
import com.itextpdf.commons.bouncycastle.asn1.x509.IGeneralNames;
import com.itextpdf.commons.bouncycastle.asn1.x509.IIssuingDistributionPoint;
import com.itextpdf.commons.bouncycastle.asn1.x509.IKeyPurposeId;
import com.itextpdf.commons.bouncycastle.asn1.x509.IKeyUsage;
import com.itextpdf.commons.bouncycastle.asn1.x509.IReasonFlags;
import com.itextpdf.commons.bouncycastle.asn1.x509.ISubjectPublicKeyInfo;
import com.itextpdf.commons.bouncycastle.asn1.x509.ITBSCertificate;
import com.itextpdf.commons.bouncycastle.asn1.x509.ITime;
import com.itextpdf.commons.bouncycastle.cert.IX509CertificateHolder;
import com.itextpdf.commons.bouncycastle.cert.IX509ExtensionUtils;
import com.itextpdf.commons.bouncycastle.cert.IX509v2CRLBuilder;
import com.itextpdf.commons.bouncycastle.cert.jcajce.IJcaCertStore;
import com.itextpdf.commons.bouncycastle.cert.jcajce.IJcaX509CertificateConverter;
import com.itextpdf.commons.bouncycastle.cert.jcajce.IJcaX509CertificateHolder;
import com.itextpdf.commons.bouncycastle.cert.jcajce.IJcaX509v3CertificateBuilder;
import com.itextpdf.commons.bouncycastle.cert.ocsp.AbstractOCSPException;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IBasicOCSPResp;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IBasicOCSPRespBuilder;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateID;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ICertificateStatus;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IOCSPReq;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IOCSPReqBuilder;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IOCSPResp;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IOCSPRespBuilder;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IRespID;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IRevokedStatus;
import com.itextpdf.commons.bouncycastle.cert.ocsp.ISingleResp;
import com.itextpdf.commons.bouncycastle.cert.ocsp.IUnknownStatus;
import com.itextpdf.commons.bouncycastle.cms.AbstractCMSException;
import com.itextpdf.commons.bouncycastle.cms.ICMSEnvelopedData;
import com.itextpdf.commons.bouncycastle.cms.ISignerInfoGenerator;
import com.itextpdf.commons.bouncycastle.cms.jcajce.IJcaSignerInfoGeneratorBuilder;
import com.itextpdf.commons.bouncycastle.cms.jcajce.IJcaSimpleSignerInfoVerifierBuilder;
import com.itextpdf.commons.bouncycastle.cms.jcajce.IJceKeyAgreeEnvelopedRecipient;
import com.itextpdf.commons.bouncycastle.cms.jcajce.IJceKeyTransEnvelopedRecipient;
import com.itextpdf.commons.bouncycastle.openssl.IPEMParser;
import com.itextpdf.commons.bouncycastle.openssl.jcajce.IJcaPEMKeyConverter;
import com.itextpdf.commons.bouncycastle.openssl.jcajce.IJceOpenSSLPKCS8DecryptorProviderBuilder;
import com.itextpdf.commons.bouncycastle.operator.IDigestCalculator;
import com.itextpdf.commons.bouncycastle.operator.IDigestCalculatorProvider;
import com.itextpdf.commons.bouncycastle.operator.jcajce.IJcaContentSignerBuilder;
import com.itextpdf.commons.bouncycastle.operator.jcajce.IJcaContentVerifierProviderBuilder;
import com.itextpdf.commons.bouncycastle.operator.jcajce.IJcaDigestCalculatorProviderBuilder;
import com.itextpdf.commons.bouncycastle.tsp.AbstractTSPException;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampRequest;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampRequestGenerator;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampResponse;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampResponseGenerator;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampToken;
import com.itextpdf.commons.bouncycastle.tsp.ITimeStampTokenGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IBouncyCastleFactory {
    public String getAlgorithmOid(String var1);

    public String getDigestAlgorithmOid(String var1);

    public String getAlgorithmName(String var1);

    public IASN1ObjectIdentifier createASN1ObjectIdentifier(IASN1Encodable var1);

    public IASN1ObjectIdentifier createASN1ObjectIdentifier(String var1);

    public IASN1ObjectIdentifier createASN1ObjectIdentifierInstance(Object var1);

    public IASN1InputStream createASN1InputStream(InputStream var1);

    public IASN1InputStream createASN1InputStream(byte[] var1);

    public IASN1OctetString createASN1OctetString(IASN1Encodable var1);

    public IASN1OctetString createASN1OctetString(IASN1TaggedObject var1, boolean var2);

    public IASN1OctetString createASN1OctetString(byte[] var1);

    public IASN1Sequence createASN1Sequence(Object var1);

    public IASN1Sequence createASN1Sequence(IASN1Encodable var1);

    public IASN1Sequence createASN1Sequence(byte[] var1) throws IOException;

    public IASN1Sequence createASN1SequenceInstance(Object var1);

    public IDERSequence createDERSequence(IASN1EncodableVector var1);

    public IDERSequence createDERSequence(IASN1Primitive var1);

    public IASN1TaggedObject createASN1TaggedObject(IASN1Encodable var1);

    public IASN1Integer createASN1Integer(IASN1Encodable var1);

    public IASN1Integer createASN1Integer(int var1);

    public IASN1Integer createASN1Integer(BigInteger var1);

    public IASN1Set createASN1Set(IASN1Encodable var1);

    public IASN1Set createASN1Set(Object var1);

    public IASN1Set createASN1Set(IASN1TaggedObject var1, boolean var2);

    public IASN1Set createNullASN1Set();

    public IASN1OutputStream createASN1OutputStream(OutputStream var1);

    public IASN1OutputStream createASN1OutputStream(OutputStream var1, String var2);

    public IDEROctetString createDEROctetString(byte[] var1);

    public IDEROctetString createDEROctetString(IASN1Encodable var1);

    public IASN1EncodableVector createASN1EncodableVector();

    public IDERNull createDERNull();

    public IDERTaggedObject createDERTaggedObject(int var1, IASN1Primitive var2);

    public IDERTaggedObject createDERTaggedObject(boolean var1, int var2, IASN1Primitive var3);

    public IDERSet createDERSet(IASN1EncodableVector var1);

    public IDERSet createDERSet(IASN1Primitive var1);

    public IDERSet createDERSet(ISignaturePolicyIdentifier var1);

    public IDERSet createDERSet(IRecipientInfo var1);

    public IASN1Enumerated createASN1Enumerated(int var1);

    public IASN1Enumerated createASN1Enumerated(IASN1Encodable var1);

    public IASN1Encoding createASN1Encoding();

    public IAttributeTable createAttributeTable(IASN1Set var1);

    public IPKCSObjectIdentifiers createPKCSObjectIdentifiers();

    public IAttribute createAttribute(IASN1ObjectIdentifier var1, IASN1Set var2);

    public IContentInfo createContentInfo(IASN1Sequence var1);

    public IContentInfo createContentInfo(IASN1ObjectIdentifier var1, IASN1Encodable var2);

    public ITimeStampToken createTimeStampToken(IContentInfo var1) throws AbstractTSPException, IOException;

    public ISigningCertificate createSigningCertificate(IASN1Sequence var1);

    public ISigningCertificateV2 createSigningCertificateV2(IASN1Sequence var1);

    public IBasicOCSPResponse createBasicOCSPResponse(IASN1Primitive var1);

    public IBasicOCSPResponse createBasicOCSPResponse(byte[] var1);

    public IBasicOCSPResp createBasicOCSPResp(IBasicOCSPResponse var1);

    public IBasicOCSPResp createBasicOCSPResp(Object var1);

    public IOCSPObjectIdentifiers createOCSPObjectIdentifiers();

    public IAlgorithmIdentifier createAlgorithmIdentifier(IASN1ObjectIdentifier var1);

    public IAlgorithmIdentifier createAlgorithmIdentifier(IASN1ObjectIdentifier var1, IASN1Encodable var2);

    public IRSASSAPSSParams createRSASSAPSSParams(IASN1Encodable var1);

    public IRSASSAPSSParams createRSASSAPSSParamsWithMGF1(IASN1ObjectIdentifier var1, int var2, int var3);

    public Provider getProvider();

    public String getProviderName();

    public IJceKeyTransEnvelopedRecipient createJceKeyTransEnvelopedRecipient(PrivateKey var1);

    public IJceKeyAgreeEnvelopedRecipient createJceKeyAgreeEnvelopedRecipient(PrivateKey var1);

    public IJcaContentVerifierProviderBuilder createJcaContentVerifierProviderBuilder();

    public IJcaSimpleSignerInfoVerifierBuilder createJcaSimpleSignerInfoVerifierBuilder();

    public IJcaX509CertificateConverter createJcaX509CertificateConverter();

    public IJcaDigestCalculatorProviderBuilder createJcaDigestCalculatorProviderBuilder();

    public ICertificateID createCertificateID(IDigestCalculator var1, IX509CertificateHolder var2, BigInteger var3) throws AbstractOCSPException;

    public ICertificateID createCertificateID();

    public IX509CertificateHolder createX509CertificateHolder(byte[] var1) throws IOException;

    public IJcaX509CertificateHolder createJcaX509CertificateHolder(X509Certificate var1) throws CertificateEncodingException;

    public IExtension createExtension(IASN1ObjectIdentifier var1, boolean var2, IASN1OctetString var3);

    public IExtension createExtension();

    public IExtensions createExtensions(IExtension var1);

    public IExtensions createExtensions(IExtension[] var1);

    public IExtensions createNullExtensions();

    public IOCSPReqBuilder createOCSPReqBuilder();

    public ISigPolicyQualifierInfo createSigPolicyQualifierInfo(IASN1ObjectIdentifier var1, IDERIA5String var2);

    public IASN1String createASN1String(IASN1Encodable var1);

    public IASN1Primitive createASN1Primitive(IASN1Encodable var1);

    public IASN1Primitive createASN1Primitive(byte[] var1) throws IOException;

    public IOCSPResp createOCSPResp(IOCSPResponse var1);

    public IOCSPResp createOCSPResp(byte[] var1) throws IOException;

    public IOCSPResp createOCSPResp();

    public IOCSPResponse createOCSPResponse(IOCSPResponseStatus var1, IResponseBytes var2);

    public IResponseBytes createResponseBytes(IASN1ObjectIdentifier var1, IDEROctetString var2);

    public IOCSPRespBuilder createOCSPRespBuilderInstance();

    public IOCSPRespBuilder createOCSPRespBuilder();

    public IOCSPResponseStatus createOCSPResponseStatus(int var1);

    public IOCSPResponseStatus createOCSPResponseStatus();

    public ICertificateStatus createCertificateStatus();

    public IRevokedStatus createRevokedStatus(ICertificateStatus var1);

    public IRevokedStatus createRevokedStatus(Date var1, int var2);

    public IDERIA5String createDERIA5String(IASN1TaggedObject var1, boolean var2);

    public IDERIA5String createDERIA5String(String var1);

    public ICRLDistPoint createCRLDistPoint(Object var1);

    public IIssuingDistributionPoint createIssuingDistributionPoint(Object var1);

    public IIssuingDistributionPoint createIssuingDistributionPoint(IDistributionPointName var1, boolean var2, boolean var3, IReasonFlags var4, boolean var5, boolean var6);

    public IReasonFlags createReasonFlags(int var1);

    public IDistributionPointName createDistributionPointName();

    public IDistributionPointName createDistributionPointName(IGeneralNames var1);

    public IGeneralNames createGeneralNames(IASN1Encodable var1);

    public IGeneralName createGeneralName();

    public IOtherHashAlgAndValue createOtherHashAlgAndValue(IAlgorithmIdentifier var1, IASN1OctetString var2);

    public ISignaturePolicyId createSignaturePolicyId(IASN1ObjectIdentifier var1, IOtherHashAlgAndValue var2);

    public ISignaturePolicyId createSignaturePolicyId(IASN1ObjectIdentifier var1, IOtherHashAlgAndValue var2, ISigPolicyQualifierInfo ... var3);

    public ISignaturePolicyIdentifier createSignaturePolicyIdentifier(ISignaturePolicyId var1);

    public IEnvelopedData createEnvelopedData(IOriginatorInfo var1, IASN1Set var2, IEncryptedContentInfo var3, IASN1Set var4);

    public IRecipientInfo createRecipientInfo(IKeyTransRecipientInfo var1);

    public IEncryptedContentInfo createEncryptedContentInfo(IASN1ObjectIdentifier var1, IAlgorithmIdentifier var2, IASN1OctetString var3);

    public ITBSCertificate createTBSCertificate(IASN1Encodable var1);

    public ITBSCertificate createTBSCertificate(byte[] var1);

    public IIssuerAndSerialNumber createIssuerAndSerialNumber(IX500Name var1, BigInteger var2);

    public IRecipientIdentifier createRecipientIdentifier(IIssuerAndSerialNumber var1);

    public IKeyTransRecipientInfo createKeyTransRecipientInfo(IRecipientIdentifier var1, IAlgorithmIdentifier var2, IASN1OctetString var3);

    public IOriginatorInfo createNullOriginatorInfo();

    public ICMSEnvelopedData createCMSEnvelopedData(byte[] var1) throws AbstractCMSException;

    public ITimeStampRequestGenerator createTimeStampRequestGenerator();

    public ITimeStampResponse createTimeStampResponse(byte[] var1) throws AbstractTSPException, IOException;

    public AbstractOCSPException createAbstractOCSPException(Exception var1);

    public IUnknownStatus createUnknownStatus();

    public IASN1Dump createASN1Dump();

    public IASN1BitString createASN1BitString(IASN1Encodable var1);

    public IASN1GeneralizedTime createASN1GeneralizedTime(IASN1Encodable var1);

    public IASN1GeneralizedTime createASN1GeneralizedTime(Date var1);

    public IASN1UTCTime createASN1UTCTime(IASN1Encodable var1);

    public IJcaCertStore createJcaCertStore(List<Certificate> var1) throws CertificateEncodingException;

    public ITimeStampResponseGenerator createTimeStampResponseGenerator(ITimeStampTokenGenerator var1, Set<String> var2);

    public ITimeStampRequest createTimeStampRequest(byte[] var1) throws IOException;

    public IJcaContentSignerBuilder createJcaContentSignerBuilder(String var1);

    public IJcaSignerInfoGeneratorBuilder createJcaSignerInfoGeneratorBuilder(IDigestCalculatorProvider var1);

    public ITimeStampTokenGenerator createTimeStampTokenGenerator(ISignerInfoGenerator var1, IDigestCalculator var2, IASN1ObjectIdentifier var3) throws AbstractTSPException;

    public IX500Name createX500Name(X509Certificate var1) throws CertificateEncodingException;

    public IX500Name createX500Name(String var1);

    public IRespID createRespID(IX500Name var1);

    public IBasicOCSPRespBuilder createBasicOCSPRespBuilder(IRespID var1);

    public IOCSPReq createOCSPReq(byte[] var1) throws IOException;

    public IX509v2CRLBuilder createX509v2CRLBuilder(IX500Name var1, Date var2);

    public IJcaX509v3CertificateBuilder createJcaX509v3CertificateBuilder(X509Certificate var1, BigInteger var2, Date var3, Date var4, IX500Name var5, PublicKey var6);

    public IBasicConstraints createBasicConstraints(boolean var1);

    public IBasicConstraints createBasicConstraints(int var1);

    public IKeyUsage createKeyUsage();

    public IKeyUsage createKeyUsage(int var1);

    public IKeyPurposeId createKeyPurposeId();

    public IKeyPurposeId createKeyPurposeId(IASN1ObjectIdentifier var1);

    public IExtendedKeyUsage createExtendedKeyUsage(IKeyPurposeId var1);

    public IExtendedKeyUsage createExtendedKeyUsage(IKeyPurposeId[] var1);

    public IX509ExtensionUtils createX509ExtensionUtils(IDigestCalculator var1);

    public ISubjectPublicKeyInfo createSubjectPublicKeyInfo(Object var1);

    public ICRLReason createCRLReason();

    public ITSTInfo createTSTInfo(IContentInfo var1) throws AbstractTSPException, IOException;

    public ISingleResp createSingleResp(IBasicOCSPResponse var1);

    public X509Certificate createX509Certificate(Object var1);

    public IBouncyCastleTestConstantsFactory getBouncyCastleFactoryTestUtil();

    public CRL createNullCrl();

    public IPEMParser createPEMParser(Reader var1);

    public IJceOpenSSLPKCS8DecryptorProviderBuilder createJceOpenSSLPKCS8DecryptorProviderBuilder();

    public IJcaPEMKeyConverter createJcaPEMKeyConverter();

    public ITime createTime(Date var1);

    public ITime createEndDate(X509Certificate var1);

    public boolean isNullExtension(IExtension var1);

    public boolean isNull(IASN1Encodable var1);

    public SecureRandom getSecureRandom();

    public boolean isInApprovedOnlyMode();

    public byte[] createCipherBytes(X509Certificate var1, byte[] var2, IAlgorithmIdentifier var3) throws GeneralSecurityException;

    public void isEncryptionFeatureSupported(int var1, boolean var2);
}

