/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.workbench.common.Result
 *  com.jiuqi.nvwa.workbench.favorites.bean.FavoritesGroup
 *  com.jiuqi.nvwa.workbench.favorites.service.IFavoritesDataService
 *  com.jiuqi.nvwa.workbench.favorites.service.IFavoritesGroupService
 *  com.jiuqi.nvwa.workbench.favorites.web.vo.FavoritesReq
 *  com.jiuqi.nvwa.workbench.myanalysis.bean.dto.MyAnalysisDataDTO
 *  com.jiuqi.nvwa.workbench.myanalysis.service.IMyAnalysisDataService
 *  com.jiuqi.util.StringUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.zbquery.util.QuerySystemOptionUtils;
import com.jiuqi.nvwa.workbench.common.Result;
import com.jiuqi.nvwa.workbench.favorites.bean.FavoritesGroup;
import com.jiuqi.nvwa.workbench.favorites.service.IFavoritesDataService;
import com.jiuqi.nvwa.workbench.favorites.service.IFavoritesGroupService;
import com.jiuqi.nvwa.workbench.favorites.web.vo.FavoritesReq;
import com.jiuqi.nvwa.workbench.myanalysis.bean.dto.MyAnalysisDataDTO;
import com.jiuqi.nvwa.workbench.myanalysis.service.IMyAnalysisDataService;
import com.jiuqi.util.StringUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery/favorite"})
public class ZBQueryFavoriteController {
    @Autowired
    public IFavoritesGroupService favoritesGroupService;
    @Autowired
    public IFavoritesDataService favoritesDataService;
    @Autowired
    private IFavoritesDataService dataService;
    @Autowired
    private IMyAnalysisDataService maDataService;

    @GetMapping(value={"/isFavorite/{zbQueryModelGuid}"})
    public boolean isFavorite(@PathVariable String zbQueryModelGuid) {
        String resourceType = null;
        MyAnalysisDataDTO dto = this.maDataService.getHasExtDataById(zbQueryModelGuid);
        resourceType = dto != null ? "com.jiuqi.nvwa.myanalysis.zbquery" : "com.jiuqi.nr.zbquery.manage";
        List dataList = this.favoritesDataService.findByResource(resourceType, zbQueryModelGuid, NpContextHolder.getContext().getUserId());
        return dataList != null && !dataList.isEmpty();
    }

    @GetMapping(value={"/cancelFavorite/{zbQueryModelGuid}"})
    public void cancelFavorite(@PathVariable String zbQueryModelGuid) {
        String resourceType = null;
        MyAnalysisDataDTO dto = this.maDataService.getHasExtDataById(zbQueryModelGuid);
        resourceType = dto != null ? "com.jiuqi.nvwa.myanalysis.zbquery" : "com.jiuqi.nr.zbquery.manage";
        this.favoritesDataService.deleteByResource(resourceType, zbQueryModelGuid, NpContextHolder.getContext().getUserId());
    }

    @GetMapping(value={"/getGroup"})
    public List<FavoritesGroup> getGroup(String groupId, String creator) {
        return this.favoritesGroupService.findByGroup(groupId, creator);
    }

    @PostMapping(value={"/doFavorite"})
    public Result addData(@RequestBody FavoritesReq data) throws JQException {
        List datas;
        if (StringUtils.isEmpty((String)data.getResourceType()) && StringUtils.isNotEmpty((String)data.getResourceId())) {
            MyAnalysisDataDTO dto = this.maDataService.getHasExtDataById(data.getResourceId());
            if (dto != null) {
                data.setResourceType("com.jiuqi.nvwa.myanalysis.zbquery");
            } else {
                data.setResourceType("com.jiuqi.nr.zbquery.manage");
            }
        }
        if (CollectionUtils.isEmpty(datas = this.dataService.findByResource(data.getResourceType(), data.getResourceId(), NpContextHolder.getContext().getUserId()))) {
            String id = this.dataService.add(data.convertData());
            return Result.success((Object)id, null);
        }
        return Result.error(null, (String)String.format("\u8d44\u6e90%s\u5df2\u6536\u85cf\u8fc7", data.getTitle()));
    }

    @GetMapping(value={"/isEnableFavorites"})
    public boolean enableFavorite() {
        return QuerySystemOptionUtils.isEnableFavorites();
    }
}

