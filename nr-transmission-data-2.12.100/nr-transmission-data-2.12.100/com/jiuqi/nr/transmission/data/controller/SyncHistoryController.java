/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.transmission.data.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeExportException;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import com.jiuqi.nr.transmission.data.service.ISyncSchemeService;
import com.jiuqi.nr.transmission.data.vo.SyncHistoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/sync/scheme/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u5386\u53f2\u670d\u52a1"})
public class SyncHistoryController {
    @Autowired
    private ISyncHistoryService syncHistoryService;
    @Autowired
    private ISyncSchemeService syncSchemeService;

    @ApiOperation(value="\u67e5\u8be2\u65b9\u6848\u4e0b\u7684\u540c\u6b65\u5386\u53f2")
    @GetMapping(value={"get_history/{schemeKey}"})
    public List<SyncHistoryDTO> getHistoryByScheme(@PathVariable String schemeKey) {
        List<SyncHistoryDTO> list = this.syncHistoryService.getByScheme(schemeKey);
        if (list.size() <= 10) {
            return list;
        }
        return list.subList(0, 10);
    }

    @ApiOperation(value="\u67e5\u8be2\u65b9\u6848\u540c\u6b65\u5386\u53f2")
    @PostMapping(value={"get_history_by_key"})
    public SyncHistoryDTO getHistoryByKey(@RequestParam(value="paramKey", required=false) String key) {
        return this.syncHistoryService.get(key);
    }

    @ApiOperation(value="\u5bfc\u51fa\u540c\u6b65\u8bb0\u5f55\u7684\u6570\u636e\u5305")
    @GetMapping(value={"export_history/{key}"})
    public void exportHistory(@PathVariable String key) {
        this.syncHistoryService.getExportData(key);
        SyncHistoryDTO syncHistoryDTO = this.syncHistoryService.get(key);
        SyncSchemeDTO syncSchemeDTO = this.syncSchemeService.getWithOutParam(syncHistoryDTO.getSchemeKey());
        syncSchemeDTO.setSchemeParam(syncHistoryDTO.getSyncSchemeParamDO());
    }

    @ApiOperation(value="\u67e5\u8be2\u88c5\u5165\u7684\u5386\u53f2")
    @GetMapping(value={"get_import_history"})
    public List<SyncHistoryVO> getImportHistory() {
        List<SyncHistoryDTO> importHistory = this.syncHistoryService.getImport();
        return SyncHistoryVO.toVOListInstance(importHistory);
    }

    @ApiOperation(value="\u5bfc\u51fa\u8be6\u60c5")
    @PostMapping(value={"export_detail"})
    public void exportHistoryDetail(@RequestParam(value="key") String key, HttpServletResponse response) throws JQException {
        try {
            this.syncHistoryService.exportDetail(key, response);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SchemeExportException.EXPORT_HISTORY_DETAIL_ERROR, e.getMessage());
        }
    }
}

