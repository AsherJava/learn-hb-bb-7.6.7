/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.archive.entity.ArchiveParamData
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.period.PeriodConsts
 */
package com.jiuqi.gcreport.archive.utils;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.archive.entity.ArchiveParamData;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.period.PeriodConsts;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLFileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLFileUtils.class);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] createXMLFile(ArchiveParamData archiveParamData, String dateStr, Map<String, String> formInfo) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Map extendParam = archiveParamData.getExtendParam();
            String qydm = String.valueOf(archiveParamData.getExtendParam().get("QYDM"));
            String orgType = String.valueOf(extendParam.get("ORGTYPEID"));
            String currencyCode = String.valueOf(extendParam.get("CURRENCYID"));
            String adjustType = String.valueOf(extendParam.get("ADJTYPEIDS"));
            YearPeriodObject yearPeriodObject = new YearPeriodObject(null, archiveParamData.getDataTime());
            String itemTitle = DateUtils.format((Date)yearPeriodObject.formatYP().getEndDate(), (String)"yyyyMM") + PeriodConsts.typeToTitle((int)yearPeriodObject.getType()) + "\u62a5";
            Element reportMetadata = document.createElement("ReportMetadata");
            document.appendChild(reportMetadata);
            Element coverInformation = document.createElement("CoverInformation");
            reportMetadata.appendChild(coverInformation);
            Element documentType = document.createElement("BB_DocumentType");
            documentType.appendChild(document.createTextNode("\u4f1a\u8ba1\u4e1a\u52a1\u7c7b"));
            coverInformation.appendChild(documentType);
            Element documentCategory = document.createElement("BB_DocumentCategory");
            documentCategory.appendChild(document.createTextNode(PeriodConsts.typeToTitle((int)yearPeriodObject.getType()) + "\u62a5"));
            coverInformation.appendChild(documentCategory);
            Element title = document.createElement("BB_Title");
            title.appendChild(document.createTextNode(itemTitle));
            coverInformation.appendChild(title);
            Element subjectKeywords = document.createElement("BB_SubjectKeywords");
            subjectKeywords.appendChild(document.createTextNode(itemTitle));
            coverInformation.appendChild(subjectKeywords);
            Element unitCode = document.createElement("BB_UnitCode");
            unitCode.appendChild(document.createTextNode(qydm));
            coverInformation.appendChild(unitCode);
            Element currency = document.createElement("BB_Currency");
            currency.appendChild(document.createTextNode(currencyCode));
            coverInformation.appendChild(currency);
            Element consolidatedUnitType = document.createElement("BB_ConsolidatedUnitType");
            consolidatedUnitType.appendChild(document.createTextNode(orgType));
            coverInformation.appendChild(consolidatedUnitType);
            Element consolidationAdjustmentType = document.createElement("BB_ConsolidationAdjustmentType");
            consolidationAdjustmentType.appendChild(document.createTextNode(adjustType));
            coverInformation.appendChild(consolidationAdjustmentType);
            Element creationDate = document.createElement("BB_CreationDate");
            creationDate.appendChild(document.createTextNode(dateStr));
            coverInformation.appendChild(creationDate);
            Element affiliatedUnit = document.createElement("BB_AffiliatedUnit");
            affiliatedUnit.appendChild(document.createTextNode(qydm));
            coverInformation.appendChild(affiliatedUnit);
            Element custodianUnit = document.createElement("BB_CustodianUnit");
            custodianUnit.appendChild(document.createTextNode(qydm));
            coverInformation.appendChild(custodianUnit);
            Element fiscalYear = document.createElement("BB_FiscalYear");
            fiscalYear.appendChild(document.createTextNode(DateUtils.format((Date)yearPeriodObject.formatYP().getEndDate(), (String)"yyyy")));
            coverInformation.appendChild(fiscalYear);
            Element fiscalMonth = document.createElement("BB_FiscalMonth");
            fiscalMonth.appendChild(document.createTextNode(DateUtils.format((Date)yearPeriodObject.formatYP().getEndDate(), (String)"MM")));
            coverInformation.appendChild(fiscalMonth);
            Element preparedBy = document.createElement("BB_PreparedBy");
            preparedBy.appendChild(document.createTextNode(""));
            coverInformation.appendChild(preparedBy);
            Element reviewedBy = document.createElement("BB_ReviewedBy");
            reviewedBy.appendChild(document.createTextNode(""));
            coverInformation.appendChild(reviewedBy);
            Element recheckedBy = document.createElement("BB_RecheckedBy");
            recheckedBy.appendChild(document.createTextNode(""));
            coverInformation.appendChild(recheckedBy);
            XMLFileUtils.createFormInfoXml(document, formInfo, coverInformation, archiveParamData);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);
            LOGGER.debug("\u6d6a\u6f6e\u7535\u5b50\u6863\u6848\u751f\u6210xml\u4fe1\u606f\uff1a" + result.getOutputStream().toString());
            byte[] byArray = outputStream.toByteArray();
            return byArray;
        }
        catch (Exception e) {
            LOGGER.error("\u6d6a\u6f6e\u7535\u5b50\u6863\u6848\u751f\u6210xml\u62a5\u9519", e);
            throw new RuntimeException("\u6d6a\u6f6e\u7535\u5b50\u6863\u6848\u751f\u6210xml\u62a5\u9519", e);
        }
    }

    private static void createFormInfoXml(Document document, Map<String, String> formInfo, Element coverInformation, ArchiveParamData archiveParamData) {
        if (ObjectUtils.isEmpty(formInfo) || formInfo.isEmpty()) {
            return;
        }
        Element formInfoDatasElement = document.createElement("BB_FROMINFO");
        coverInformation.appendChild(formInfoDatasElement);
        for (String key : formInfo.keySet()) {
            String value = formInfo.get(key);
            Element formInfoElement = document.createElement(key);
            formInfoElement.appendChild(document.createTextNode(value));
            formInfoDatasElement.appendChild(formInfoElement);
        }
        Element mdCode = document.createElement("MD_ORG");
        mdCode.appendChild(document.createTextNode(String.valueOf(archiveParamData.getExtendParam().get("MD_ORG"))));
        formInfoDatasElement.appendChild(mdCode);
        Element title = document.createElement("ORG_TITLE");
        title.appendChild(document.createTextNode(String.valueOf(archiveParamData.getExtendParam().get("ORG_TITLE"))));
        formInfoDatasElement.appendChild(title);
        Element dataTime = document.createElement("DATATIME");
        dataTime.appendChild(document.createTextNode(String.valueOf(archiveParamData.getExtendParam().get("DATATIME"))));
        formInfoDatasElement.appendChild(dataTime);
        Element bblx = document.createElement("BBLX");
        bblx.appendChild(document.createTextNode(String.valueOf(archiveParamData.getExtendParam().get("BBLX"))));
        formInfoDatasElement.appendChild(bblx);
    }

    public static void main(String[] args) {
        ArchiveParamData archiveParamData = new ArchiveParamData();
        HashMap x = new HashMap();
        archiveParamData.setExtendParam(x);
        HashMap<String, String> i = new HashMap<String, String>();
        i.put("AAA", "111");
        XMLFileUtils.createXMLFile(archiveParamData, DateUtils.nowDateStr(), i);
    }
}

