/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.internal.springcache.CacheProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.splittable.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.internal.springcache.CacheProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.splittable.bean.BaseBook;
import com.jiuqi.nr.splittable.bean.BaseSheet;
import com.jiuqi.nr.splittable.bean.CellObj;
import com.jiuqi.nr.splittable.config.SplitGridCacheManagerConfig;
import com.jiuqi.nr.splittable.service.AuditService;
import com.jiuqi.nr.splittable.util.SerializeUtil;
import com.jiuqi.nr.splittable.web.LinkAndFieldDTO;
import com.jiuqi.nr.splittable.web.LinkAndFieldVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl
implements AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final IRunTimeViewController runTimeViewController;
    private NedisCache cache;

    @Autowired
    public void setCache(CacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager(SplitGridCacheManagerConfig.NAME).getCache("splittable_title");
    }

    public AuditServiceImpl(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    @Override
    public List<LinkAndFieldVO> getLinkAndFieldInSubArea(List<LinkAndFieldDTO> linkAndFieldDTOList) {
        Cache.ValueWrapper valueWrapper;
        if (linkAndFieldDTOList.size() < 1) {
            return new ArrayList<LinkAndFieldVO>();
        }
        String formKey = linkAndFieldDTOList.get(0).getFormKey();
        if (this.cache.exists(formKey) && (valueWrapper = this.cache.get(formKey)) != null) {
            String str = (String)valueWrapper.get();
            if (str == null) {
                return this.noSolutionLinkPos(linkAndFieldDTOList);
            }
            return this.find(linkAndFieldDTOList, SerializeUtil.deserializeBaseBook(str));
        }
        return this.noSolutionLinkPos(linkAndFieldDTOList);
    }

    private List<LinkAndFieldVO> find(List<LinkAndFieldDTO> linkAndFieldDTOList, BaseBook baseBook) {
        List<BaseSheet> sheetList = baseBook.getSheetList();
        ArrayList<LinkAndFieldVO> result = new ArrayList<LinkAndFieldVO>();
        for (LinkAndFieldDTO linkAndFieldDTO : linkAndFieldDTOList) {
            String dataLinkKey = linkAndFieldDTO.getDataLinkKey();
            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
            CellObj cellObj = new CellObj(dataLinkDefine.getPosY(), dataLinkDefine.getPosX());
            for (int i = 0; i < sheetList.size(); ++i) {
                BaseSheet baseSheet = sheetList.get(i);
                CellObj[][] newCellXYArray = baseSheet.getNewCellXY();
                List<CellObj> oldCellObjList = baseSheet.getOldCellObjList();
                LinkAndFieldVO linkAndFieldVO = new LinkAndFieldVO();
                linkAndFieldVO.setId(linkAndFieldDTO.getId());
                linkAndFieldVO.setLinkId(dataLinkDefine.getKey());
                linkAndFieldVO.setUniqueCode(dataLinkDefine.getUniqueCode());
                linkAndFieldVO.setOldCell(cellObj);
                linkAndFieldVO.setZbId(linkAndFieldDTO.getFieldKey());
                linkAndFieldVO.setZbTitle(linkAndFieldDTO.getFieldTitle());
                linkAndFieldVO.setRegionId(sheetList.get(i).getUuid());
                if (newCellXYArray != null && cellObj.getPosY() < newCellXYArray.length - 1 && cellObj.getPosX() < newCellXYArray[0].length - 1 && newCellXYArray[cellObj.getPosY()][cellObj.getPosX()] != null) {
                    linkAndFieldVO.setNewCell(newCellXYArray[cellObj.getPosY()][cellObj.getPosX()]);
                    result.add(linkAndFieldVO);
                    continue;
                }
                if (oldCellObjList != null && oldCellObjList.contains(cellObj)) {
                    linkAndFieldVO.setNewCell(cellObj);
                    result.add(linkAndFieldVO);
                    continue;
                }
                if (sheetList.size() != 1) continue;
                linkAndFieldVO.setNewCell(cellObj);
                result.add(linkAndFieldVO);
            }
        }
        return result.stream().filter(res -> res.getNewCell() != null).collect(Collectors.toList());
    }

    private List<LinkAndFieldVO> noSolutionLinkPos(List<LinkAndFieldDTO> linkAndFieldDTOList) {
        ArrayList<LinkAndFieldVO> linkAndFieldVOS = new ArrayList<LinkAndFieldVO>(linkAndFieldDTOList.size());
        List allLinksInForm = this.runTimeViewController.getAllLinksInForm(linkAndFieldDTOList.get(0).getFormKey());
        for (LinkAndFieldDTO linkAndFieldDTO : linkAndFieldDTOList) {
            allLinksInForm.parallelStream().filter(l -> linkAndFieldDTO.getDataLinkKey().equals(l.getKey())).findFirst().ifPresent(dataLinkDefine -> linkAndFieldVOS.add(this.convertVO((DataLinkDefine)dataLinkDefine, linkAndFieldDTO)));
        }
        return linkAndFieldVOS;
    }

    private LinkAndFieldVO convertVO(DataLinkDefine dataLinkDefine, LinkAndFieldDTO linkAndFieldDTO) {
        LinkAndFieldVO linkAndFieldVO = new LinkAndFieldVO();
        linkAndFieldVO.setId(linkAndFieldDTO.getId());
        linkAndFieldVO.setRegionId(dataLinkDefine.getRegionKey());
        linkAndFieldVO.setLinkId(dataLinkDefine.getKey());
        linkAndFieldVO.setUniqueCode(dataLinkDefine.getUniqueCode());
        linkAndFieldVO.setNewCell(new CellObj(dataLinkDefine.getPosY(), dataLinkDefine.getPosX()));
        linkAndFieldVO.setZbId(dataLinkDefine.getLinkExpression());
        linkAndFieldVO.setZbTitle(linkAndFieldDTO.getFieldTitle());
        return linkAndFieldVO;
    }
}

