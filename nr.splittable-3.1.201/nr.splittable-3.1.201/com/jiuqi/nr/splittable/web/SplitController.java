/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.internal.springcache.CacheProvider
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.splittable.web;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.internal.springcache.CacheProvider;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.splittable.bean.BaseBook;
import com.jiuqi.nr.splittable.bean.BaseSheet;
import com.jiuqi.nr.splittable.config.SplitGridCacheManagerConfig;
import com.jiuqi.nr.splittable.exception.ExceptionEnum;
import com.jiuqi.nr.splittable.exception.SplitTableException;
import com.jiuqi.nr.splittable.service.AuditService;
import com.jiuqi.nr.splittable.service.SplitGridService;
import com.jiuqi.nr.splittable.util.SerializeUtil;
import com.jiuqi.nr.splittable.web.LinkAndFieldDTO;
import com.jiuqi.nr.splittable.web.LinkAndFieldVO;
import com.jiuqi.nr.splittable.web.Region;
import com.jiuqi.nr.splittable.web.SplitDataPM;
import com.jiuqi.nr.splittable.web.SplitRegionVo;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.grid2.Grid2Data;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/splittable/"})
@Api(tags={"\u79fb\u52a8\u62c6\u8868\u670d\u52a1"})
public class SplitController {
    private static final Logger logger = LoggerFactory.getLogger(SplitController.class);
    private final SplitGridService splitGridService;
    private final AuditService auditService;
    private NedisCache cache;

    @Autowired
    public void setCache(CacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager(SplitGridCacheManagerConfig.NAME).getCache("splittable_title");
    }

    public SplitController(SplitGridService splitGridService, AuditService auditService) {
        this.splitGridService = splitGridService;
        this.auditService = auditService;
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u5b50\u533a\u57df\u6807\u9898\u5217\u8868")
    @PostMapping(value={"area/all-title/get"})
    public List<Region> getGridAreaByKey(@RequestBody SplitDataPM splitDataPM) throws JQException {
        BaseBook baseBook = this.getBaseBookFormCache(splitDataPM.getFormKey());
        Map<Object, Object> sheetTitleMap = new HashMap();
        ArrayList<Region> gridAreaByKey = new ArrayList<Region>();
        sheetTitleMap = baseBook != null ? (baseBook == null ? sheetTitleMap : baseBook.getSheetTitleMap()) : this.splitGridService.getGridAreaByKey(splitDataPM);
        try {
            sheetTitleMap.forEach((key, title) -> gridAreaByKey.add(new Region((String)key, (String)title)));
        }
        catch (SplitTableException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExceptionEnum.SPLIT_FILED_ST_1, "\u83b7\u53d6\u6240\u6709\u533a\u57df\u6807\u9898");
        }
        return gridAreaByKey;
    }

    @ApiOperation(value="\u83b7\u53d6\u5177\u4f53\u5b50\u533a\u57df\u4fe1\u606f")
    @PostMapping(value={"area/get"})
    public String getGridChildAreaByKey(@RequestBody SplitRegionVo splitRegionVo) {
        return this.splitGridService.getGridChildAreaByKey(splitRegionVo.getRegionKey());
    }

    @ApiOperation(value="\u6e05\u9664\u7f13\u5b58\u6570\u636e")
    @GetMapping(value={"cache/clear"})
    public String clear() {
        this.cache.clear();
        return "\u6e05\u9664\u7f13\u5b58";
    }

    @ApiOperation(value="\u83b7\u53d6\u94fe\u63a5\u7684\u65b0\u5750\u6807\u548c\u5b50\u533a\u57df\u7684\u5bf9\u5e94\u5173\u7cfb\uff0c\u4ee5\u53ca\u94fe\u63a5\u7684\u6307\u6807\u4fe1\u606f")
    @PostMapping(value={"audit/region/all-link"})
    public List<LinkAndFieldVO> getLinkAndFieldInSubArea(@RequestBody List<LinkAndFieldDTO> linkAndFieldDTOList) {
        return this.auditService.getLinkAndFieldInSubArea(linkAndFieldDTOList);
    }

    @ApiOperation(value="\u83b7\u53d6cellBook")
    @PostMapping(value={"cell-book/get"})
    public String getCellBook(@RequestBody SplitDataPM splitDataPM) {
        String result = this.getCellBookFromCache("B" + splitDataPM.getFormKey());
        if (null != result) {
            logger.info("\u83b7\u53d6\u7f13\u5b58splittable_CellBook_" + splitDataPM.getFormKey());
            return result;
        }
        BaseBook baseBook = this.getBaseBookFormCache(splitDataPM.getFormKey());
        if (baseBook == null) {
            this.splitGridService.getGridAreaByKey(splitDataPM);
            baseBook = this.getBaseBookFormCache(splitDataPM.getFormKey());
        }
        List<BaseSheet> sheetList = baseBook.getSheetList();
        CellBook cellBook = new CellBook();
        for (BaseSheet baseSheet : sheetList) {
            CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)baseSheet.getGrid2Data(), (CellBook)cellBook, (String)baseSheet.getUuid(), (String)baseSheet.getSheetTitle());
        }
        result = SerializeUtil.serializeCellBook(cellBook, baseBook.getAllOldCellObjList());
        this.cache.put("B" + splitDataPM.getFormKey(), (Object)result);
        logger.info("\u83b7\u53d6\u7f13\u5b58splittable_CellBook_" + splitDataPM.getFormKey());
        return result;
    }

    private BaseBook getBaseBookFormCache(String formKey) {
        Cache.ValueWrapper valueWrapper;
        if (this.cache.exists(formKey) && (valueWrapper = this.cache.get(formKey)) != null) {
            String str = (String)valueWrapper.get();
            return SerializeUtil.deserializeBaseBook(str);
        }
        return null;
    }

    private String getCellBookFromCache(String cellBookKey) {
        Cache.ValueWrapper valueWrapper;
        String temp = null;
        if (this.cache.exists(cellBookKey) && (valueWrapper = this.cache.get(cellBookKey)) != null) {
            temp = (String)valueWrapper.get();
        }
        return temp;
    }
}

