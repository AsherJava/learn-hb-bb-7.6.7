/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="IDOC_CAPTURE_BILL_DATA", title="\u7ed3\u6784\u5316\u6570\u636e\u8868", inStorage=true, indexs={})
public class IdocCaptureBillDataEo
extends DefaultTableEntity {
    public static final String TABLENAME = "IDOC_CAPTURE_BILL_DATA";
    @DBColumn(nameInDB="ORGCODE", title="\u4f01\u4e1a\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String orgCode;
    @DBColumn(nameInDB="BBORGCODE", title="\u62a5\u8868\u5355\u4f4d\u7f16\u7801", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String bbOrgCode;
    @DBColumn(nameInDB="ORGNAME", title="\u5355\u4f4d\u540d\u79f0", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String orgName;
    @DBColumn(nameInDB="ACCOUNTYEAR", title="\u4f1a\u8ba1\u5e74", dbType=DBColumn.DBType.Varchar, length=4, isRequired=true)
    private String accountYear;
    @DBColumn(nameInDB="ACCOUNTMONTH", title="\u4f1a\u8ba1\u6708", dbType=DBColumn.DBType.Varchar, length=2, isRequired=true)
    private String accountMonth;
    @DBColumn(nameInDB="PERIOD", title="\u62a5\u8868\u91c7\u96c6\u9636\u6bb5", dbType=DBColumn.DBType.Varchar, length=10, isRequired=true)
    private String period;
    @DBColumn(nameInDB="PERIODINDEX", title="\u65f6\u95f4\u7d22\u5f15", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer periodIndex;
    @DBColumn(nameInDB="DOCTYPE", title="\u6863\u6848\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=100, isRequired=true)
    private String docType;
    @DBColumn(nameInDB="PK", title="\u51ed\u8bc1\u6216\u5355\u636e\u539f\u59cb\u4e3b\u952e", dbType=DBColumn.DBType.Varchar, length=200, isRequired=true)
    private String pk;
    @DBColumn(nameInDB="FILEURL", title="\u6587\u4ef6\u5730\u5740", dbType=DBColumn.DBType.Varchar, length=500, isRequired=true)
    private String fileUrl;
    @DBColumn(nameInDB="SRCFILENAME", title="\u6587\u4ef6\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=500, isRequired=true)
    private String srcFileName;
    @DBColumn(nameInDB="FILESIZE", title="\u6587\u4ef6\u5927\u5c0f", dbType=DBColumn.DBType.Varchar, length=20, isRequired=true)
    private String fileSize;
    @DBColumn(nameInDB="DIGITALDIGEST", title="\u6570\u5b57\u6458\u8981", dbType=DBColumn.DBType.Varchar, length=100, isRequired=true)
    private String digitalDigest;
    @DBColumn(nameInDB="TITLE", title="\u9898\u540d", dbType=DBColumn.DBType.Varchar, length=200, isRequired=true)
    private String title;
    @DBColumn(nameInDB="FILENO", title="\u6587\u53f7/\u51ed\u8bc1\u53f7", dbType=DBColumn.DBType.Varchar, length=200, isRequired=false)
    private String fileNo;
    @DBColumn(nameInDB="STORETYPE", title="\u5b58\u50a8\u5f62\u5f0f", dbType=DBColumn.DBType.Int, length=1, isRequired=true)
    private Integer storeType;
    @DBColumn(nameInDB="ATTACHCOUNT", title="\u9644\u4ef6\u6570\u91cf", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer attachCount;
    @DBColumn(nameInDB="OWNER", title="\u8d23\u4efb\u4eba", dbType=DBColumn.DBType.Varchar, length=50, isRequired=false)
    private String owner;
    @DBColumn(nameInDB="PAGES", title="\u9875\u6570", dbType=DBColumn.DBType.Int, length=10, isRequired=false)
    private Integer pages;
    @DBColumn(nameInDB="ABSTRACTS", title="\u6458\u8981", dbType=DBColumn.DBType.Varchar, length=200, isRequired=false)
    private String abstracts;
    @DBColumn(nameInDB="KEYWORDS", title="\u5173\u952e\u8bcd", dbType=DBColumn.DBType.Varchar, length=200, isRequired=false)
    private String keywords;
    @DBColumn(nameInDB="DOCDATE", title="\u6240\u5c5e\u65e5\u671f", dbType=DBColumn.DBType.Varchar, length=10, isRequired=true)
    private String docDate;
    @DBColumn(nameInDB="SECRETLEVEL", title="\u5bc6\u7ea7", dbType=DBColumn.DBType.Varchar, length=1, isRequired=false)
    private String secretLevel;
    @DBColumn(nameInDB="INSTID", title="\u673a\u6784\u548c\u95ee\u9898-\u8457\u5f55", dbType=DBColumn.DBType.Varchar, length=20, isRequired=false)
    private String instId;
    @DBColumn(nameInDB="ACCOUNTVOUCHERTYPE", title="\u8bb0\u8d26\u51ed\u8bc1\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=128, isRequired=false)
    private String accountVoucherType;
    @DBColumn(nameInDB="RELATEBILLNO", title="\u5173\u8054\u5355\u636e\u53f7", dbType=DBColumn.DBType.Varchar, length=2000, isRequired=false)
    private String relateBillNo;
    @DBColumn(nameInDB="BARCODE", title="\u6761\u5f62\u7801", dbType=DBColumn.DBType.Varchar, length=160, isRequired=false)
    private String barcode;
    @DBColumn(nameInDB="BILLTYPE", title="\u5355\u636e\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=128, isRequired=false)
    private String billType;
    @DBColumn(nameInDB="BILLNO", title="\u5355\u636e\u53f7", dbType=DBColumn.DBType.Varchar, length=128, isRequired=false)
    private String billNo;
    @DBColumn(nameInDB="TOTAL", title="\u603b\u91d1\u989d", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String total;
    @DBColumn(nameInDB="BILLMAKER", title="\u5236\u5355\u4eba", dbType=DBColumn.DBType.Varchar, length=128, isRequired=false)
    private String billMaker;
    @DBColumn(nameInDB="DOCUMENTNUMBER", title="\u6587\u6863\u5e8f\u53f7", dbType=DBColumn.DBType.Varchar, length=10, isRequired=false)
    private String documentNumber;
    @DBColumn(nameInDB="COMPUTERFILENAME", title="\u8ba1\u7b97\u673a\u6587\u4ef6\u540d", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String computerFileName;
    @DBColumn(nameInDB="SCANRESOLUTION", title="\u626b\u63cf\u5206\u8fa8\u7387", dbType=DBColumn.DBType.Varchar, length=20, isRequired=false)
    private String scanResolution;
    @DBColumn(nameInDB="SCANCOLORMODE", title="\u626b\u63cf\u8272\u5f69\u6a21\u5f0f", dbType=DBColumn.DBType.Varchar, length=10, isRequired=false)
    private String scanColorMode;
    @DBColumn(nameInDB="OFFLINECARRIERNUMBER", title="\u8131\u673a\u8f7d\u4f53\u7f16\u53f7", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String offlineCarrierNumber;
    @DBColumn(nameInDB="BUSINESSMETADATA", title="\u4e1a\u52a1\u5143\u6570\u636e", dbType=DBColumn.DBType.Varchar)
    private String businessMetaData;
    @DBColumn(nameInDB="TS", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String ts;
    @DBColumn(nameInDB="ASSIST1", title="\u6269\u5145\u5b57\u6bb51", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist1;
    @DBColumn(nameInDB="ASSIST2", title="\u6269\u5145\u5b57\u6bb52", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist2;
    @DBColumn(nameInDB="ASSIST3", title="\u6269\u5145\u5b57\u6bb53", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist3;
    @DBColumn(nameInDB="ASSIST4", title="\u6269\u5145\u5b57\u6bb54", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist4;
    @DBColumn(nameInDB="ASSIST5", title="\u6269\u5145\u5b57\u6bb55", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist5;
    @DBColumn(nameInDB="ASSIST6", title="\u6269\u5145\u5b57\u6bb56", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist6;
    @DBColumn(nameInDB="ASSIST7", title="\u6269\u5145\u5b57\u6bb57", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist7;
    @DBColumn(nameInDB="ASSIST8", title="\u6269\u5145\u5b57\u6bb58", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist8;
    @DBColumn(nameInDB="ASSIST9", title="\u6269\u5145\u5b57\u6bb59", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist9;
    @DBColumn(nameInDB="ASSIST10", title="\u6269\u5145\u5b57\u6bb510", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist10;
    @DBColumn(nameInDB="ASSIST11", title="\u6269\u5145\u5b57\u6bb511", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist11;
    @DBColumn(nameInDB="ASSIST12", title="\u6269\u5145\u5b57\u6bb512", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist12;
    @DBColumn(nameInDB="ASSIST13", title="\u6269\u5145\u5b57\u6bb513", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist13;
    @DBColumn(nameInDB="ASSIST14", title="\u6269\u5145\u5b57\u6bb514", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist14;
    @DBColumn(nameInDB="ASSIST15", title="\u6269\u5145\u5b57\u6bb515", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist15;
    @DBColumn(nameInDB="ASSIST16", title="\u6269\u5145\u5b57\u6bb516", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist16;
    @DBColumn(nameInDB="ASSIST17", title="\u6269\u5145\u5b57\u6bb517", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist17;
    @DBColumn(nameInDB="ASSIST18", title="\u6269\u5145\u5b57\u6bb518", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist18;
    @DBColumn(nameInDB="ASSIST19", title="\u6269\u5145\u5b57\u6bb519", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist19;
    @DBColumn(nameInDB="ASSIST20", title="\u6269\u5145\u5b57\u6bb520", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist20;
    @DBColumn(nameInDB="ASSIST21", title="\u6269\u5145\u5b57\u6bb521", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist21;
    @DBColumn(nameInDB="ASSIST22", title="\u6269\u5145\u5b57\u6bb522", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist22;
    @DBColumn(nameInDB="ASSIST23", title="\u6269\u5145\u5b57\u6bb523", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist23;
    @DBColumn(nameInDB="ASSIST24", title="\u6269\u5145\u5b57\u6bb524", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist24;
    @DBColumn(nameInDB="ASSIST25", title="\u6269\u5145\u5b57\u6bb525", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist25;
    @DBColumn(nameInDB="ASSIST26", title="\u6269\u5145\u5b57\u6bb526", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist26;
    @DBColumn(nameInDB="ASSIST27", title="\u6269\u5145\u5b57\u6bb527", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist27;
    @DBColumn(nameInDB="ASSIST28", title="\u6269\u5145\u5b57\u6bb528", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist28;
    @DBColumn(nameInDB="ASSIST29", title="\u6269\u5145\u5b57\u6bb529", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist29;
    @DBColumn(nameInDB="ASSIST30", title="\u6269\u5145\u5b57\u6bb530", dbType=DBColumn.DBType.Varchar, length=100, isRequired=false)
    private String assist30;

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAccountYear() {
        return this.accountYear;
    }

    public void setAccountYear(String accountYear) {
        this.accountYear = accountYear;
    }

    public String getAccountMonth() {
        return this.accountMonth;
    }

    public void setAccountMonth(String accountMonth) {
        this.accountMonth = accountMonth;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getPeriodIndex() {
        return this.periodIndex;
    }

    public void setPeriodIndex(Integer periodIndex) {
        this.periodIndex = periodIndex;
    }

    public String getDocType() {
        return this.docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPk() {
        return this.pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getSrcFileName() {
        return this.srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDigitalDigest() {
        return this.digitalDigest;
    }

    public void setDigitalDigest(String digitalDigest) {
        this.digitalDigest = digitalDigest;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileNo() {
        return this.fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public Integer getStoreType() {
        return this.storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public Integer getAttachCount() {
        return this.attachCount;
    }

    public void setAttachCount(Integer attachCount) {
        this.attachCount = attachCount;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getPages() {
        return this.pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getAbstracts() {
        return this.abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDocDate() {
        return this.docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(String secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getInstId() {
        return this.instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getAccountVoucherType() {
        return this.accountVoucherType;
    }

    public void setAccountVoucherType(String accountVoucherType) {
        this.accountVoucherType = accountVoucherType;
    }

    public String getRelateBillNo() {
        return this.relateBillNo;
    }

    public void setRelateBillNo(String relateBillNo) {
        this.relateBillNo = relateBillNo;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBillType() {
        return this.billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillNo() {
        return this.billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBillMaker() {
        return this.billMaker;
    }

    public void setBillMaker(String billMaker) {
        this.billMaker = billMaker;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getComputerFileName() {
        return this.computerFileName;
    }

    public void setComputerFileName(String computerFileName) {
        this.computerFileName = computerFileName;
    }

    public String getScanResolution() {
        return this.scanResolution;
    }

    public void setScanResolution(String scanResolution) {
        this.scanResolution = scanResolution;
    }

    public String getScanColorMode() {
        return this.scanColorMode;
    }

    public void setScanColorMode(String scanColorMode) {
        this.scanColorMode = scanColorMode;
    }

    public String getOfflineCarrierNumber() {
        return this.offlineCarrierNumber;
    }

    public void setOfflineCarrierNumber(String offlineCarrierNumber) {
        this.offlineCarrierNumber = offlineCarrierNumber;
    }

    public String getBusinessMetaData() {
        return this.businessMetaData;
    }

    public void setBusinessMetaData(String businessMetaData) {
        this.businessMetaData = businessMetaData;
    }

    public String getTs() {
        return this.ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getAssist1() {
        return this.assist1;
    }

    public void setAssist1(String assist1) {
        this.assist1 = assist1;
    }

    public String getAssist2() {
        return this.assist2;
    }

    public void setAssist2(String assist2) {
        this.assist2 = assist2;
    }

    public String getAssist3() {
        return this.assist3;
    }

    public void setAssist3(String assist3) {
        this.assist3 = assist3;
    }

    public String getAssist4() {
        return this.assist4;
    }

    public void setAssist4(String assist4) {
        this.assist4 = assist4;
    }

    public String getAssist5() {
        return this.assist5;
    }

    public void setAssist5(String assist5) {
        this.assist5 = assist5;
    }

    public String getAssist6() {
        return this.assist6;
    }

    public void setAssist6(String assist6) {
        this.assist6 = assist6;
    }

    public String getAssist7() {
        return this.assist7;
    }

    public void setAssist7(String assist7) {
        this.assist7 = assist7;
    }

    public String getAssist8() {
        return this.assist8;
    }

    public void setAssist8(String assist8) {
        this.assist8 = assist8;
    }

    public String getAssist9() {
        return this.assist9;
    }

    public void setAssist9(String assist9) {
        this.assist9 = assist9;
    }

    public String getAssist10() {
        return this.assist10;
    }

    public void setAssist10(String assist10) {
        this.assist10 = assist10;
    }

    public String getAssist11() {
        return this.assist11;
    }

    public void setAssist11(String assist11) {
        this.assist11 = assist11;
    }

    public String getAssist12() {
        return this.assist12;
    }

    public void setAssist12(String assist12) {
        this.assist12 = assist12;
    }

    public String getAssist13() {
        return this.assist13;
    }

    public void setAssist13(String assist13) {
        this.assist13 = assist13;
    }

    public String getAssist14() {
        return this.assist14;
    }

    public void setAssist14(String assist14) {
        this.assist14 = assist14;
    }

    public String getAssist15() {
        return this.assist15;
    }

    public void setAssist15(String assist15) {
        this.assist15 = assist15;
    }

    public String getAssist16() {
        return this.assist16;
    }

    public void setAssist16(String assist16) {
        this.assist16 = assist16;
    }

    public String getAssist17() {
        return this.assist17;
    }

    public void setAssist17(String assist17) {
        this.assist17 = assist17;
    }

    public String getAssist18() {
        return this.assist18;
    }

    public void setAssist18(String assist18) {
        this.assist18 = assist18;
    }

    public String getAssist19() {
        return this.assist19;
    }

    public void setAssist19(String assist19) {
        this.assist19 = assist19;
    }

    public String getAssist20() {
        return this.assist20;
    }

    public void setAssist20(String assist20) {
        this.assist20 = assist20;
    }

    public String getAssist21() {
        return this.assist21;
    }

    public void setAssist21(String assist21) {
        this.assist21 = assist21;
    }

    public String getAssist22() {
        return this.assist22;
    }

    public void setAssist22(String assist22) {
        this.assist22 = assist22;
    }

    public String getAssist23() {
        return this.assist23;
    }

    public void setAssist23(String assist23) {
        this.assist23 = assist23;
    }

    public String getAssist24() {
        return this.assist24;
    }

    public void setAssist24(String assist24) {
        this.assist24 = assist24;
    }

    public String getAssist25() {
        return this.assist25;
    }

    public void setAssist25(String assist25) {
        this.assist25 = assist25;
    }

    public String getAssist26() {
        return this.assist26;
    }

    public void setAssist26(String assist26) {
        this.assist26 = assist26;
    }

    public String getAssist27() {
        return this.assist27;
    }

    public void setAssist27(String assist27) {
        this.assist27 = assist27;
    }

    public String getAssist28() {
        return this.assist28;
    }

    public void setAssist28(String assist28) {
        this.assist28 = assist28;
    }

    public String getAssist29() {
        return this.assist29;
    }

    public void setAssist29(String assist29) {
        this.assist29 = assist29;
    }

    public String getAssist30() {
        return this.assist30;
    }

    public void setAssist30(String assist30) {
        this.assist30 = assist30;
    }

    public String getBbOrgCode() {
        return this.bbOrgCode;
    }

    public void setBbOrgCode(String bbOrgCode) {
        this.bbOrgCode = bbOrgCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}

