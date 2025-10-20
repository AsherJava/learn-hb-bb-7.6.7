/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.file.FileInfo
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.Attachment;
import com.jiuqi.nr.file.FileInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);
    @Autowired
    private Attachment attachMentService;
    @Autowired
    private DesignFormDefineService formDefineService;
    private static final String PARTITION = "NR_LINK_TEMP";

    @ApiOperation(value="\u4e0a\u4f20\u9644\u4ef6\u6307\u6807\u9ed8\u8ba4\u6a21\u677f")
    @PostMapping(value={"uploadFile"})
    public String upLoadFile(@RequestParam(value="file") MultipartFile file, String partition) throws JQException {
        String groupKey = null;
        try {
            if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)partition) || partition.equalsIgnoreCase("null")) {
                groupKey = UUIDUtils.getKey();
                partition = PARTITION;
            }
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            if (groupKey == null) {
                String[] split = partition.split("\\|");
                groupKey = split[0];
                partition = split[1];
            }
            groupKey = this.attachMentService.uploadFile(fileName, groupKey, bytes, partition);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return groupKey;
    }

    @ApiOperation(value="\u52a0\u8f7d\u9ed8\u8ba4\u6a21\u677f")
    @GetMapping(value={"getAttaDefault/{attaChmentDefault}"})
    public List<FileInfo> getAttaDefault(@PathVariable(value="attaChmentDefault") String attachemnt) {
        List<FileInfo> fileInGroup = this.attachMentService.getFileInGroup(attachemnt);
        return fileInGroup;
    }

    @ApiOperation(value="\u5220\u9664\u9ed8\u8ba4\u6a21\u677f")
    @GetMapping(value={"deleteAttachment/{key}/{parrent}"})
    public void deleteAttachment(@PathVariable(value="key") String key, @PathVariable(value="parrent") String parrent) throws JQException {
        try {
            List<FileInfo> fileInGroup = this.attachMentService.getFileInGroup(parrent);
            Optional<FileInfo> findFirst = fileInGroup.stream().filter(file -> file.getKey().equals(key)).findFirst();
            if (findFirst.isPresent()) {
                FileInfo fileInfo = findFirst.get();
                this.attachMentService.deleteFile(fileInfo, false);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_134);
        }
    }

    @ApiOperation(value="\u4e0b\u8f7d\u9644\u4ef6")
    @GetMapping(value={"downLoad/{downLoad}/{area}"})
    public void downLoad(@PathVariable(value="downLoad") String key, @PathVariable(value="area") String area, HttpServletResponse response) {
        this.attachMentService.downFild(response, key, area);
    }

    @ApiOperation(value="\u83b7\u53d6\u9644\u4ef6")
    @GetMapping(value={"data-link/attachment/{id}"})
    public String getAttachementOfDataLink(@PathVariable(value="id") String dataLinkKey) throws JQException {
        try {
            byte[] bigData;
            if (StringUtils.isNotEmpty((String)dataLinkKey) && (bigData = this.formDefineService.getBigData(dataLinkKey, "ATTACHMENT")) != null) {
                return DesignFormDefineBigDataUtil.bytesToString((byte[])bigData);
            }
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_143, (Throwable)e);
        }
        return "";
    }
}

