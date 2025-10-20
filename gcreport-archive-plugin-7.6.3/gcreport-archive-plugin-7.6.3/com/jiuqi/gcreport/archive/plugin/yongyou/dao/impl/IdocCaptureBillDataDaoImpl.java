/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.dao.impl;

import com.jiuqi.gcreport.archive.plugin.yongyou.dao.IdocCaptureBillDataDao;
import com.jiuqi.gcreport.archive.plugin.yongyou.dao.impl.YongYouArichiveBaseDaoImpl;
import com.jiuqi.gcreport.archive.plugin.yongyou.entity.IdocCaptureBillDataEo;
import java.util.ArrayList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdocCaptureBillDataDaoImpl
extends YongYouArichiveBaseDaoImpl
implements IdocCaptureBillDataDao {
    @Override
    public void save(IdocCaptureBillDataEo idocCaptureBillDataEo) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %s \n");
        sql.append("       (ID,ORGCODE,BBORGCODE,ORGNAME,ACCOUNTYEAR, ACCOUNTMONTH, PERIOD, PERIODINDEX, DOCTYPE, PK, FILEURL, SRCFILENAME, \n");
        sql.append("       FILESIZE, DIGITALDIGEST, TITLE, FILENO, STORETYPE, ATTACHCOUNT, OWNER, PAGES, ABSTRACTS, KEYWORDS, \n");
        sql.append("       DOCDATE, SECRETLEVEL, INSTID, ACCOUNTVOUCHERTYPE, RELATEBILLNO, BARCODE, BILLTYPE, BILLNO, \n");
        sql.append("       TOTAL, BILLMAKER, DOCUMENTNUMBER, COMPUTERFILENAME, SCANRESOLUTION, SCANCOLORMODE, \n");
        sql.append("       OFFLINECARRIERNUMBER, BUSINESSMETADATA, TS)  \n");
        sql.append("VALUES (?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) \n");
        String sqlResult = String.format(sql.toString(), "IDOC_CAPTURE_BILL_DATA");
        ArrayList<Object> queryParams = new ArrayList<Object>();
        queryParams.add(idocCaptureBillDataEo.getId());
        queryParams.add(idocCaptureBillDataEo.getOrgCode());
        queryParams.add(idocCaptureBillDataEo.getBbOrgCode());
        queryParams.add(idocCaptureBillDataEo.getOrgName());
        queryParams.add(idocCaptureBillDataEo.getAccountYear());
        queryParams.add(idocCaptureBillDataEo.getAccountMonth());
        queryParams.add(idocCaptureBillDataEo.getPeriod());
        queryParams.add(idocCaptureBillDataEo.getPeriodIndex());
        queryParams.add(idocCaptureBillDataEo.getDocType());
        queryParams.add(idocCaptureBillDataEo.getPk());
        queryParams.add(idocCaptureBillDataEo.getFileUrl());
        queryParams.add(idocCaptureBillDataEo.getSrcFileName());
        queryParams.add(idocCaptureBillDataEo.getFileSize());
        queryParams.add(idocCaptureBillDataEo.getDigitalDigest());
        queryParams.add(idocCaptureBillDataEo.getTitle());
        queryParams.add(idocCaptureBillDataEo.getFileNo());
        queryParams.add(idocCaptureBillDataEo.getStoreType());
        queryParams.add(idocCaptureBillDataEo.getAttachCount());
        queryParams.add(idocCaptureBillDataEo.getOwner());
        queryParams.add(idocCaptureBillDataEo.getPages());
        queryParams.add(idocCaptureBillDataEo.getAbstracts());
        queryParams.add(idocCaptureBillDataEo.getKeywords());
        queryParams.add(idocCaptureBillDataEo.getDocDate());
        queryParams.add(idocCaptureBillDataEo.getSecretLevel());
        queryParams.add(idocCaptureBillDataEo.getInstId());
        queryParams.add(idocCaptureBillDataEo.getAccountVoucherType());
        queryParams.add(idocCaptureBillDataEo.getRelateBillNo());
        queryParams.add(idocCaptureBillDataEo.getBarcode());
        queryParams.add(idocCaptureBillDataEo.getBillType());
        queryParams.add(idocCaptureBillDataEo.getBillNo());
        queryParams.add(idocCaptureBillDataEo.getTotal());
        queryParams.add(idocCaptureBillDataEo.getBillMaker());
        queryParams.add(idocCaptureBillDataEo.getDocumentNumber());
        queryParams.add(idocCaptureBillDataEo.getComputerFileName());
        queryParams.add(idocCaptureBillDataEo.getScanResolution());
        queryParams.add(idocCaptureBillDataEo.getScanColorMode());
        queryParams.add(idocCaptureBillDataEo.getOfflineCarrierNumber());
        queryParams.add(idocCaptureBillDataEo.getBusinessMetaData());
        queryParams.add(idocCaptureBillDataEo.getTs());
        jdbcTemplate.update(sqlResult.toString(), queryParams.toArray());
    }
}

