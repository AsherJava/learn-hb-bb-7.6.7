/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.JQException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import com.jiuqi.nr.multcheck2.dao.MultcheckItemDao;
import com.jiuqi.nr.multcheck2.dao.MultcheckSchemeDao;
import com.jiuqi.nr.multcheck2.dao.MultcheckSchemeOrgDao;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.web.vo.ResultVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCSchemeServiceImpl
implements IMCSchemeService {
    @Autowired
    private MultcheckSchemeDao schemeDao;
    @Autowired
    private MultcheckSchemeOrgDao orgDao;
    @Autowired
    private MultcheckItemDao itemDao;
    @Autowired
    private IMCEnvService envService;

    @Override
    public void addScheme(MultcheckScheme scheme) {
        this.schemeDao.add(scheme);
    }

    @Override
    public void modifyScheme(MultcheckScheme scheme) {
        this.schemeDao.modify(scheme);
    }

    @Override
    @Transactional
    public void deleteSchemeByKey(String key) {
        this.schemeDao.deleteByKey(key);
        this.orgDao.deleteByScheme(key);
        this.itemDao.deleteByScheme(key);
    }

    @Override
    public void deleteSchemeByFormScheme(String formScheme) {
        List<MultcheckScheme> schemes = this.schemeDao.getByFormScheme(formScheme);
        for (MultcheckScheme scheme : schemes) {
            this.orgDao.deleteByScheme(scheme.getKey());
            this.itemDao.deleteByScheme(scheme.getKey());
        }
        this.schemeDao.deleteByFormScheme(formScheme);
    }

    @Override
    public MultcheckScheme getSchemeByKey(String key) {
        return this.schemeDao.getByKey(key);
    }

    @Override
    public List<MultcheckScheme> getSchemeByKeys(List<String> keys) {
        return this.schemeDao.getByKeys(keys);
    }

    @Override
    public List<MultcheckScheme> getSchemeByForm(String formScheme) {
        return this.schemeDao.getByFormScheme(formScheme);
    }

    @Override
    public List<MultcheckScheme> getSchemeByFSAndOrg(String formScheme, String org) {
        return this.schemeDao.getByFSAndOrg(formScheme, org);
    }

    @Override
    public List<MultcheckScheme> getByFSAndCode(String formScheme, String code) {
        return this.schemeDao.getByFSAndCode(formScheme, code);
    }

    @Override
    public Map<String, List<MultcheckScheme>> getSchemeByTask(String task) {
        return this.schemeDao.getByTask(task);
    }

    @Override
    public void moveScheme(MultcheckScheme source, MultcheckScheme target) throws JQException {
        this.schemeDao.move(source, target);
    }

    @Override
    public List<String> getAllTask() {
        return this.schemeDao.getAllTask();
    }

    @Override
    public List<MultcheckScheme> getAllSchemes() {
        return this.schemeDao.getAllSchemes();
    }

    @Override
    public void deleteOrgByScheme(String key) {
        this.orgDao.deleteByScheme(key);
    }

    @Override
    public void batchAddOrg(List<MultcheckSchemeOrg> orgs) {
        this.orgDao.batchAdd(orgs);
    }

    @Override
    public List<MultcheckSchemeOrg> getOrgListByScheme(String scheme) {
        return this.orgDao.getByScheme(scheme);
    }

    @Override
    public int getOrgCountByScheme(String scheme) {
        return this.orgDao.getCountByScheme(scheme);
    }

    @Override
    public List<MultcheckScheme> fuzzySearchScheme(String keyword) {
        ArrayList<MultcheckScheme> schemes = new ArrayList<MultcheckScheme>();
        HashSet<String> guids = new HashSet<String>();
        List<MultcheckScheme> schemeCodeS = this.schemeDao.fuzzySearchCode(keyword);
        List<MultcheckScheme> schemeTitleS = this.schemeDao.fuzzySearchTitle(keyword);
        if (!CollectionUtils.isEmpty(schemeCodeS)) {
            for (MultcheckScheme scheme : schemeCodeS) {
                if (!guids.add(scheme.getKey())) continue;
                schemes.add(scheme);
            }
        }
        if (!CollectionUtils.isEmpty(schemeTitleS)) {
            for (MultcheckScheme scheme : schemeTitleS) {
                if (!guids.add(scheme.getKey())) continue;
                schemes.add(scheme);
            }
        }
        return schemes;
    }

    @Override
    public void batchModifyOrg(List<MultcheckScheme> schemes) {
        this.schemeDao.batchModifyOrg(schemes);
    }

    @Override
    public List<MultcheckItem> getItemInfoList(String key) {
        return this.itemDao.getInfoByScheme(key);
    }

    @Override
    public List<MultcheckItem> getItemList(String key) {
        return this.itemDao.getByScheme(key);
    }

    @Override
    public ResultVO addItem(MultcheckItem item) {
        List<MultcheckItem> items = this.itemDao.getInfoByScheme(item.getScheme());
        boolean hasSameType = false;
        if (!CollectionUtils.isEmpty(items)) {
            boolean findSameTitle = false;
            for (MultcheckItem m : items) {
                if (!hasSameType) {
                    hasSameType = m.getType().equals(item.getType());
                }
                if (findSameTitle) break;
                findSameTitle = m.getTitle().equals(item.getTitle());
            }
            if (findSameTitle) {
                return ResultVO.error("\u5ba1\u6838\u9879\u540d\u79f0\u4e0d\u80fd\u91cd\u590d");
            }
        }
        item.setKey(UUID.randomUUID().toString());
        item.setOrder(OrderGenerator.newOrder());
        if (!hasSameType) {
            this.envService.getProvider(item.getType()).createCheckItemTables(this.schemeDao.getByKey(item.getScheme()));
        }
        this.itemDao.add(item);
        return ResultVO.success(item.getKey());
    }

    @Override
    @Transactional
    public void batchAddItem(List<MultcheckItem> items, String scheme) {
        this.deleteItemByScheme(scheme);
        this.itemDao.batchAdd(items);
    }

    @Override
    public void deleteItem(String key) {
        this.itemDao.deleteByKey(key);
    }

    @Override
    public void deleteItemByScheme(String scheme) {
        this.itemDao.deleteByScheme(scheme);
    }

    @Override
    public String getItemConfig(String key) {
        return this.itemDao.getConfig(key);
    }

    @Override
    public ResultVO modifyItem(MultcheckItem item) {
        List<MultcheckItem> items = this.itemDao.getInfoByScheme(item.getScheme());
        boolean hasSameType = false;
        if (!CollectionUtils.isEmpty(items)) {
            boolean findSameTitle = false;
            for (MultcheckItem m : items) {
                if (!hasSameType) {
                    hasSameType = m.getType().equals(item.getType());
                }
                if (findSameTitle) break;
                findSameTitle = m.getTitle().equals(item.getTitle()) && !m.getKey().equals(item.getKey());
            }
            if (findSameTitle) {
                return ResultVO.error("\u5ba1\u6838\u9879\u540d\u79f0\u4e0d\u80fd\u91cd\u590d");
            }
        }
        if (!hasSameType) {
            this.envService.getProvider(item.getType()).createCheckItemTables(this.schemeDao.getByKey(item.getScheme()));
        }
        this.itemDao.modify(item);
        return ResultVO.success(item.getKey());
    }

    @Override
    public void moveItem(String source, String target) {
        this.itemDao.move(this.itemDao.getByKey(source), this.itemDao.getByKey(target));
    }

    @Override
    public void addItemsByScheme(MultcheckScheme multcheckScheme) {
        List<IMultcheckItemProvider> providerList = this.envService.getProviderList();
        ArrayList<MultcheckItem> items = new ArrayList<MultcheckItem>();
        for (IMultcheckItemProvider p : providerList) {
            MultCheckItemDTO dto = p.getDefaultCheckItem(multcheckScheme.getFormScheme());
            if (dto == null) continue;
            MultcheckItem item = new MultcheckItem();
            BeanUtils.copyProperties(dto, item);
            item.setKey(UUID.randomUUID().toString());
            item.setScheme(multcheckScheme.getKey());
            item.setOrder(OrderGenerator.newOrder());
            items.add(item);
        }
        if (!CollectionUtils.isEmpty(items)) {
            this.itemDao.batchAdd(items);
        }
    }

    @Override
    public void saveReportDim(String key, String dim) {
        if (StringUtils.hasText(dim)) {
            this.schemeDao.saveReportDim(key, dim);
        }
    }

    @Override
    public String getReportDim(String key) {
        return this.schemeDao.getReportDim(key);
    }
}

