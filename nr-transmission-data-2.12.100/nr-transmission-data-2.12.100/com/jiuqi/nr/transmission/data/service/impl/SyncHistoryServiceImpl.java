/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.util.Html;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.transmission.data.dao.ISyncHistoryDao;
import com.jiuqi.nr.transmission.data.domain.SyncHistoryDO;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SyncHistoryServiceImpl
implements ISyncHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(SyncHistoryServiceImpl.class);
    public static final int SCHEME_SYNC_HISTORY_MAX_COUNT = 10;
    @Autowired
    private ISyncHistoryDao syncHistoryDao;

    @Override
    public boolean insert(SyncHistoryDTO syncHistoryDTO) {
        int add;
        if (!StringUtils.hasText(syncHistoryDTO.getKey())) {
            syncHistoryDTO.setKey(UUIDUtils.getKey());
        }
        return (add = this.syncHistoryDao.add(syncHistoryDTO)) == 1;
    }

    @Override
    public boolean delete(String key) {
        int delete = this.syncHistoryDao.delete(key);
        return delete == 1;
    }

    @Override
    public boolean deletes(String schemeKey) {
        int delete = this.syncHistoryDao.delete(schemeKey);
        return delete == 1;
    }

    @Override
    public SyncHistoryDTO get(String key) {
        SyncHistoryDO syncHistoryDO = this.syncHistoryDao.get(key);
        return SyncHistoryDTO.getInstance(syncHistoryDO);
    }

    @Override
    public List<SyncHistoryDTO> getByScheme(String schemeKey) {
        List<SyncHistoryDO> list = this.syncHistoryDao.list(schemeKey);
        list.sort((o1, o2) -> o2.getStartTime().compareTo(o1.getStartTime()));
        List<SyncHistoryDTO> syncHistoryDTOS = SyncHistoryDTO.toListInstance(list);
        syncHistoryDTOS = syncHistoryDTOS.stream().filter(syncHistoryDTO -> syncHistoryDTO.getType() == 0).collect(Collectors.toList());
        return syncHistoryDTOS;
    }

    @Override
    public List<SyncHistoryDTO> getImport() {
        List<SyncHistoryDO> list = this.syncHistoryDao.listImport();
        list.sort((o1, o2) -> o2.getStartTime().compareTo(o1.getStartTime()));
        List<SyncHistoryDTO> syncHistoryDTOS = SyncHistoryDTO.toListInstance(list);
        return syncHistoryDTOS;
    }

    @Override
    public List<SyncHistoryDTO> getBySchemes(List<String> schemeKeys) {
        if (schemeKeys != null && schemeKeys.size() > 0) {
            List<SyncHistoryDO> syncHistoryDOS = this.syncHistoryDao.listByGroup(schemeKeys);
            syncHistoryDOS = syncHistoryDOS.stream().filter(syncHistoryDTO -> syncHistoryDTO.getType() == 0).collect(Collectors.toList());
            return SyncHistoryDTO.toListInstance(syncHistoryDOS);
        }
        return new ArrayList<SyncHistoryDTO>();
    }

    @Override
    public List<SyncHistoryDTO> getUnComplete() {
        List<SyncHistoryDO> list = this.syncHistoryDao.getUnComplete();
        if (list != null && list.size() > 0) {
            List<SyncHistoryDO> sync = list.stream().filter(syncHistoryDTO -> syncHistoryDTO.getType() == 0).collect(Collectors.toList());
            return SyncHistoryDTO.toListInstance(sync);
        }
        return new ArrayList<SyncHistoryDTO>();
    }

    @Override
    public int update(SyncHistoryDO historyDO) {
        int update = this.syncHistoryDao.update(historyDO);
        return update;
    }

    @Override
    public int updateSyncSchemeParam(SyncHistoryDO historyDO) {
        int update = this.syncHistoryDao.updateSyncSchemeParam(historyDO);
        return update;
    }

    @Override
    public int updateResult(SyncHistoryDO historyDO) {
        int update = this.syncHistoryDao.updateResult(historyDO);
        return update;
    }

    @Override
    public int updateDetail(String executeKey, String detail) {
        int i = this.syncHistoryDao.updateField(executeKey, "TH_DETAIL", detail);
        return i;
    }

    @Override
    public int updateField(String executeKey, String field, String value) {
        int i = this.syncHistoryDao.updateField(executeKey, field, value);
        return i;
    }

    @Override
    public void getExportData(String historyKey) {
    }

    @Override
    public void exportDetail(String historyKey, HttpServletResponse response) throws Exception {
        SyncHistoryDTO syncHistoryDTO = this.get(historyKey);
        String detail = syncHistoryDTO.getDetail();
        if (!StringUtils.hasLength(detail = Html.cleanUrlXSS((String)detail))) {
            throw new RuntimeException("\u5bfc\u51fa\u540c\u6b65\u8be6\u60c5\u67e5\u8be2\u4e3a\u7a7a");
        }
        String fileName = "\u540c\u6b65\u8be6\u60c5";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u6587\u4ef6\u540d\u79f0\u8f6c\u7801\u5931\u8d25", e);
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".txt");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try (ServletOutputStream outStr = response.getOutputStream();
             BufferedOutputStream buff = new BufferedOutputStream((OutputStream)outStr);){
            buff.write(detail.getBytes(StandardCharsets.UTF_8));
            buff.flush();
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u540c\u6b65\u8bb0\u5f55\u8be6\u60c5\u5931\u8d25" + e.getMessage(), e);
            throw e;
        }
    }
}

