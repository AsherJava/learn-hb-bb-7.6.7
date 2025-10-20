/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.domain.bizres.BizResDataDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDTO;
import com.jiuqi.va.bizmeta.service.IBizResInfoService;
import com.jiuqi.va.domain.common.R;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/biz/res"})
public class BizResInfoController {
    @Autowired
    private IBizResInfoService bizResInfoService;

    @PostMapping(value={"/info/add"})
    public R add(@RequestParam(value="file") MultipartFile file, BizResInfoDO bizResInfoDO) {
        try {
            UUID uuid = UUID.randomUUID();
            BizResDataDO bizResDataDO = new BizResDataDO();
            bizResDataDO.setId(uuid);
            bizResDataDO.setResfile(file.getBytes());
            bizResInfoDO.setId(uuid);
            bizResInfoDO.setVer(System.currentTimeMillis());
            bizResInfoDO.setResname(file.getOriginalFilename());
            bizResInfoDO.setFilesize((int)(file.getSize() / 1024L));
            bizResInfoDO.setUploadtime(new Date());
            bizResInfoDO.setEtag(DigestUtils.md5DigestAsHex(file.getBytes()));
            return this.bizResInfoService.add(bizResDataDO, bizResInfoDO);
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/info/list"})
    public R list(@RequestBody BizResInfoDTO bizResInfoDTO) {
        return this.bizResInfoService.list(bizResInfoDTO);
    }

    @PostMapping(value={"/info/deletes"})
    public R deletes(@RequestParam List<UUID> ids) {
        return this.bizResInfoService.deletes(ids);
    }

    @GetMapping(value={"/info/downloads"})
    public void downloads(@RequestParam List<UUID> ids) {
        this.bizResInfoService.downloads(ids);
    }
}

