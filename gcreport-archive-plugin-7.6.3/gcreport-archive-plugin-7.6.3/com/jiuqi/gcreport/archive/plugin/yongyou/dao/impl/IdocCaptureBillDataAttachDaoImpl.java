/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.dao.impl;

import com.jiuqi.gcreport.archive.plugin.yongyou.dao.IdocCaptureBillDataAttachDao;
import com.jiuqi.gcreport.archive.plugin.yongyou.dao.impl.YongYouArichiveBaseDaoImpl;
import com.jiuqi.gcreport.archive.plugin.yongyou.entity.IdocCaptureBillAttachEo;
import java.util.ArrayList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdocCaptureBillDataAttachDaoImpl
extends YongYouArichiveBaseDaoImpl
implements IdocCaptureBillDataAttachDao {
    @Override
    public void save(IdocCaptureBillAttachEo idocCaptureBillAttachEo) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %s \n ");
        sql.append("       (ID,BILLPK, DOCTYPE, FILENAME, FILEURL, FILESIZE, DIGITALDIGEST,FILEDESC, TS, ATTACHTYPE, ATTACHJSONDATA, ORGCODE, BBORGCODE,ORGNAME) \n");
        sql.append("VALUES (? ,? ,? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
        String sqlResult = String.format(sql.toString(), "IDOC_CAPTURE_BILL_ATTACH");
        ArrayList<Object> queryParams = new ArrayList<Object>();
        queryParams.add(idocCaptureBillAttachEo.getId());
        queryParams.add(idocCaptureBillAttachEo.getBillPk());
        queryParams.add(idocCaptureBillAttachEo.getDocType());
        queryParams.add(idocCaptureBillAttachEo.getFileName());
        queryParams.add(idocCaptureBillAttachEo.getFileUrl());
        queryParams.add(idocCaptureBillAttachEo.getFileSize());
        queryParams.add(idocCaptureBillAttachEo.getDigitalDigest());
        queryParams.add(idocCaptureBillAttachEo.getFileDesc());
        queryParams.add(idocCaptureBillAttachEo.getTs());
        queryParams.add(idocCaptureBillAttachEo.getAttachType());
        queryParams.add(idocCaptureBillAttachEo.getAttachJsonData());
        queryParams.add(idocCaptureBillAttachEo.getOrgCode());
        queryParams.add(idocCaptureBillAttachEo.getBbOrgCode());
        queryParams.add(idocCaptureBillAttachEo.getOrgName());
        jdbcTemplate.update(sqlResult.toString(), queryParams.toArray());
    }
}

