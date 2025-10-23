/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.util.StringUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.file.test;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value={"/test/file"})
public class FileTestController {
    @Autowired
    private FileService fileService;

    private FileAreaService getFileAreaService(String area) {
        if (StringUtils.isEmpty((String)area)) {
            return this.fileService;
        }
        return this.fileService.area(area);
    }

    @GetMapping(value={"/upload/{path}/{area}", "/upload/{path}"})
    public FileInfo testUpload(@PathVariable(value="path") String path, @PathVariable(value="area", required=false) String area) throws Exception {
        String fieldName = String.format("D:\\Project\\JiuQi\\filetest\\%s", path);
        PathUtils.validatePathManipulation((String)fieldName);
        File file = new File(fieldName);
        try (FileInputStream in = new FileInputStream(file);){
            FileInfo fileInfo = this.getFileAreaService(area).upload(path, in);
            return fileInfo;
        }
    }

    @GetMapping(value={"/getinfo/{key}/{area}", "/getinfo/{key}"})
    public FileInfo testGetInfo(@PathVariable(value="key") String key, @PathVariable(value="area", required=false) String area) {
        return this.getFileAreaService(area).getInfo(key);
    }

    @GetMapping(value={"/rename/{key}/{name}/{area}", "/rename/{key}/{name}"})
    public FileInfo testRename(@PathVariable(value="key") String key, @PathVariable(value="name") String name, @PathVariable(value="area", required=false) String area) {
        return this.getFileAreaService(area).rename(key, name);
    }

    @GetMapping(value={"/delete/{key}/{area}", "/delete/{key}"})
    public FileInfo testDelete(@PathVariable(value="key") String key, @PathVariable(value="area", required=false) String area) {
        return this.getFileAreaService(area).delete(key);
    }

    @GetMapping(value={"/recover/{key}/{area}", "/recover/{key}"})
    public FileInfo testRecover(@PathVariable(value="key") String key, @PathVariable(value="area", required=false) String area) {
        return this.getFileAreaService(area).recover(key);
    }

    @GetMapping(value={"/batchgetinfo/{keys}/{area}", "/batchgetinfo/{keys}"})
    public Map<String, FileInfo> testBatchGetInfo(@PathVariable(value="keys") String keys, @PathVariable(value="area", required=false) String area) {
        return this.getFileAreaService(area).getInfo(Arrays.asList(keys.split(",")));
    }
}

