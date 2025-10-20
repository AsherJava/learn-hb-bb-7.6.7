/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.dao.IBizResDataDao;
import com.jiuqi.va.bizmeta.dao.IBizResInfoDao;
import com.jiuqi.va.bizmeta.domain.bizres.BizResDataDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDTO;
import com.jiuqi.va.bizmeta.service.IBizResInfoService;
import com.jiuqi.va.bizmeta.service.impl.BizResDataServiceImpl;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BizResInfoServiceImpl
implements IBizResInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizResInfoServiceImpl.class);
    @Autowired
    private IBizResInfoDao bizResInfoDao;
    @Autowired
    private BizResDataServiceImpl bizResDataService;
    @Autowired
    private IBizResDataDao bizResDataDao;
    private static final String UTF_8 = "UTF-8";

    @Override
    public R list(BizResInfoDTO bizResInfoDTO) {
        List<BizResInfoDTO> select = this.bizResInfoDao.list(bizResInfoDTO);
        bizResInfoDTO.setLimit(0);
        bizResInfoDTO.setOffset(0);
        List<BizResInfoDTO> list = this.bizResInfoDao.list(bizResInfoDTO);
        return Objects.requireNonNull(R.ok().put("data", select)).put("count", (Object)list.size());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R add(BizResDataDO bizResDataDO, BizResInfoDO bizResInfoDO) {
        BizResInfoDO bizResInfoDO1 = new BizResInfoDO();
        bizResInfoDO1.setResname(bizResInfoDO.getResname());
        bizResInfoDO1.setGroupname(bizResInfoDO.getGroupname());
        int i = this.bizResInfoDao.selectCount((Object)bizResInfoDO1);
        if (i != 0) {
            return R.error((String)"\u5f53\u524d\u5206\u7ec4\u4e0b\u5df2\u5b58\u5728\u76f8\u540c\u6587\u4ef6\u540d");
        }
        R add = this.bizResDataService.add(bizResDataDO);
        if (add.getCode() == 1) {
            return add;
        }
        return this.bizResInfoDao.insert((Object)bizResInfoDO) == 0 ? R.error((String)"\u8d44\u6e90\u4fe1\u606f\u65b0\u589e\u5931\u8d25") : R.ok();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R deletes(List<UUID> ids) {
        BizResInfoDO bizResInfoDO = new BizResInfoDO();
        BizResDataDO bizResDataDO = new BizResDataDO();
        for (UUID id : ids) {
            bizResInfoDO.setId(id);
            bizResDataDO.setId(id);
            this.bizResInfoDao.delete((Object)bizResInfoDO);
            this.bizResDataDao.delete((Object)bizResDataDO);
        }
        return R.ok();
    }

    @Override
    public void downloads(List<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        if (ids.size() == 1) {
            this.download(ids.get(0));
            return;
        }
        try (ByteArrayOutputStream bas = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream((OutputStream)bas, StandardCharsets.UTF_8);){
            BizResDataDO bizResDataDO = new BizResDataDO();
            BizResInfoDO bizResInfoDO = new BizResInfoDO();
            HashMap<String, Integer> attrs = new HashMap<String, Integer>();
            for (UUID id : ids) {
                String fileName;
                bizResDataDO.setId(id);
                BizResDataDO dataDO = (BizResDataDO)((Object)this.bizResDataDao.selectOne((Object)bizResDataDO));
                if (dataDO == null) continue;
                bizResInfoDO.setId(id);
                BizResInfoDO infoDO = (BizResInfoDO)((Object)this.bizResInfoDao.selectOne((Object)bizResInfoDO));
                if (infoDO == null) continue;
                String name = infoDO.getResname();
                if (attrs.containsKey(name)) {
                    Integer integer = (Integer)attrs.get(name);
                    String[] split = name.split("\\.");
                    fileName = split.length == 2 ? split[0] + " (" + (integer + 1) + ")." + split[1] : name + " (" + (integer + 1) + ")";
                    attrs.put(name, integer + 1);
                    attrs.put(fileName, 0);
                } else {
                    attrs.put(name, 0);
                    fileName = name;
                }
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                zipOutputStream.write(dataDO.getResfile());
                zipOutputStream.flush();
                zipOutputStream.closeEntry();
            }
            zipOutputStream.closeEntry();
            String name = System.currentTimeMillis() + ".zip";
            RequestContextUtil.setResponseContentType((String)"application/x-download");
            RequestContextUtil.setResponseCharacterEncoding((String)UTF_8);
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(name, UTF_8)));
            OutputStream os = RequestContextUtil.getOutputStream();
            os.write(bas.toByteArray());
            os.flush();
            os.close();
        }
        catch (Exception e) {
            LOGGER.error(" \u6279\u91cf\u4e0b\u8f7d\u5931\u8d25\uff1a", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void download(UUID ids) {
        BizResDataDO bizResDataDO = new BizResDataDO();
        bizResDataDO.setId(ids);
        BizResDataDO dataDO = (BizResDataDO)((Object)this.bizResDataDao.selectOne((Object)bizResDataDO));
        BizResInfoDO bizResInfoDO = new BizResInfoDO();
        bizResInfoDO.setId(ids);
        BizResInfoDO infoDO = (BizResInfoDO)((Object)this.bizResInfoDao.selectOne((Object)bizResInfoDO));
        if (dataDO == null || infoDO == null) {
            return;
        }
        byte[] bytes = dataDO.getResfile();
        if (bytes == null) {
            return;
        }
        OutputStream outPutStream = null;
        try {
            RequestContextUtil.setResponseContentType((String)"application/x-msdownload");
            RequestContextUtil.setResponseCharacterEncoding((String)UTF_8);
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(infoDO.getResname(), UTF_8)));
            outPutStream = RequestContextUtil.getOutputStream();
            outPutStream.write(bytes);
        }
        catch (IOException e) {
            LOGGER.error(" \u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25\uff1a", e);
        }
        finally {
            try {
                if (outPutStream != null) {
                    outPutStream.close();
                }
            }
            catch (IOException e) {
                LOGGER.error(" \u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25\uff1a", e);
            }
        }
    }
}

