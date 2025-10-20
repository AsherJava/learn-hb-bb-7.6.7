/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.tag.management.dao.TagDefineDao
 *  com.jiuqi.nr.tag.management.dao.TagMappingDao
 *  com.jiuqi.nr.tag.management.entity.ITagDefine
 *  com.jiuqi.nr.tag.management.entityimpl.TagDefine
 *  com.jiuqi.nr.tag.management.entityimpl.TagMapping
 *  com.jiuqi.nr.tag.manager.bean.TagImpl
 *  com.jiuqi.nr.tag.manager.dao.TagDao
 *  com.jiuqi.nr.tag.manager.dao.TagNodeDao
 *  org.springframework.dao.EmptyResultDataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.uselector.module.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.tag.management.dao.TagDefineDao;
import com.jiuqi.nr.tag.management.dao.TagMappingDao;
import com.jiuqi.nr.tag.management.entity.ITagDefine;
import com.jiuqi.nr.tag.management.entityimpl.TagDefine;
import com.jiuqi.nr.tag.management.entityimpl.TagMapping;
import com.jiuqi.nr.tag.manager.bean.TagImpl;
import com.jiuqi.nr.tag.manager.dao.TagDao;
import com.jiuqi.nr.tag.manager.dao.TagNodeDao;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class TagTableUpgrade
implements CustomClassExecutor {
    private final TagDao tagDao = (TagDao)SpringBeanUtils.getBean(TagDao.class);
    private final TagNodeDao tagNodeDao = (TagNodeDao)SpringBeanUtils.getBean(TagNodeDao.class);
    private final TagDefineDao tagDefineDao = (TagDefineDao)SpringBeanUtils.getBean(TagDefineDao.class);
    private final TagMappingDao tagMappingDao = (TagMappingDao)SpringBeanUtils.getBean(TagMappingDao.class);

    public void execute(DataSource dataSource) throws Exception {
        List tags = this.tagDao.findAllTags();
        List tagNodes = this.tagNodeDao.findAllTagNodes();
        for (TagImpl tag : tags) {
            TagDefine tagDefine = new TagDefine();
            tagDefine.setKey(tag.getKey());
            tagDefine.setOwner(tag.getOwner());
            tagDefine.setEntity(this.getProperEntity(tag.getViewKey()));
            tagDefine.setTitle("\u672a\u8bbe\u7f6e\u540d\u79f0");
            if (tag.getTitle() != null && !tag.getTitle().isEmpty()) {
                tagDefine.setTitle(tag.getTitle());
            }
            tagDefine.setIcon("icon nr-iconfont icon-_YJWtubiao25");
            tagDefine.setCategory(tag.getCategory());
            tagDefine.setFormula(tag.getFormula());
            tagDefine.setShared(tag.getShared() != null && tag.getShared() != false);
            tagDefine.setOrder("ZZZZZZZZ");
            if (tag.getOrder() != null && !tag.getOrder().isEmpty()) {
                tagDefine.setOrder(tag.getOrder());
            }
            tagDefine.setDescription(tag.getDescription());
            tagDefine.setRangeModify(tag.getRangeModify() == null || tag.getRangeModify() != false);
            this.tagDefineDao.insertTagDefineRow((ITagDefine)tagDefine);
        }
        this.tagMappingDao.insertTagMappingRows(tagNodes.stream().map(tagNode -> new TagMapping(tagNode.getTgKey(), tagNode.getEntKey())).collect(Collectors.toList()));
    }

    private String getProperEntity(String viewKey) {
        if (viewKey.contains("@")) {
            return viewKey;
        }
        return this.getEntityIdByViewKey(viewKey);
    }

    private String getEntityIdByViewKey(String viewKey) {
        String entityId;
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        String sql = String.format("SELECT EV_ENTITY_ID FROM NR_ENTITY_UPGRADE_VIEW WHERE EV_VIEW_KEY = '%s'", viewKey);
        try {
            entityId = (String)jdbcTemplate.queryForObject(sql, String.class);
        }
        catch (EmptyResultDataAccessException e) {
            entityId = viewKey;
        }
        return entityId;
    }
}

