/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 *  com.jiuqi.nvwa.homepage.bean.HomePage
 *  com.jiuqi.nvwa.homepage.service.IHomePageService
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.portal.auth;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.service.INews2Service;
import com.jiuqi.nr.portal.news2.service.IPortalFileService;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import com.jiuqi.nvwa.homepage.bean.HomePage;
import com.jiuqi.nvwa.homepage.service.IHomePageService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PortalResourcess
extends DefaultResourceCategory {
    public static final String PORTALID = "PortalResourceCategory-213bc9dce325";
    public static final String TITLE = "\u9996\u9875\u8d44\u6e90";
    private static final long serialVersionUID = -3903778562349489386L;
    @Autowired
    private INews2Service news2Service;
    @Autowired
    private IPortalFileService portalFileService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private IHomePageService homePageService;

    public String getId() {
        return PORTALID;
    }

    public String getTitle() {
        return TITLE;
    }

    public int getSeq() {
        return 1000;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public boolean isSupportReject() {
        return true;
    }

    public List<String> getBasePrivilegeIds() {
        return Collections.singletonList("portal_resource_read");
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition() {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem superItem = new PrivilegeDefinitionItem();
        superItem.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        superItem.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)superItem);
        PrivilegeDefinitionItem readItem = new PrivilegeDefinitionItem();
        readItem.setPrivilegeId("portal_resource_read");
        readItem.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)readItem);
        PrivilegeDefinitionItem writeItem = new PrivilegeDefinitionItem();
        writeItem.setPrivilegeId("portal_resource_write");
        writeItem.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)writeItem);
        return list;
    }

    public String toResourceId(String prefix, String objectId) {
        return prefix.concat(objectId);
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        ArrayList<Resource> roots = new ArrayList<Resource>();
        List pageList = this.homePageService.getDesignHomePageList(true);
        for (HomePage page : pageList) {
            roots.add((Resource)ResourceGroupItem.createResourceGroupItem((String)this.toResourceId("page_", page.getHomeId()), (String)page.getTitle(), (boolean)true));
        }
        return roots;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        String prefix;
        ArrayList<Resource> resources = new ArrayList<Resource>();
        switch (prefix = resourceGroupId.substring(0, resourceGroupId.indexOf("_") + 1)) {
            case "page_": {
                String modelTitle;
                String modelResourceId;
                ArrayList<JSONObject> newsModels = new ArrayList<JSONObject>();
                ArrayList<JSONObject> fileModels = new ArrayList<JSONObject>();
                String pageId = resourceGroupId.substring(resourceGroupId.indexOf("_") + 1);
                HomePage homePage = this.homePageService.getHomePageById(pageId, "zh-CN");
                String pageJson = homePage.getTemplate();
                JSONObject jsonObject = new JSONObject(pageJson);
                JSONArray jsonArray = (JSONArray)jsonObject.get("modulesConfig");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject json = (JSONObject)jsonArray.get(i);
                    if ("News".equals(json.get("type"))) {
                        newsModels.add(json);
                        continue;
                    }
                    if ("Uploader".equals(json.get("type"))) {
                        fileModels.add(json);
                        continue;
                    }
                    if (!"TabsContainer".equals(json.get("type"))) continue;
                    resources.add((Resource)ResourceGroupItem.createResourceGroupItem((String)this.toResourceId("group_", homePage.getHomeId()), (String)"\u591a\u9875\u7b7e", (boolean)true));
                }
                for (JSONObject model : newsModels) {
                    modelResourceId = this.toResourceId("model_", pageId + "$" + model.getString("id"));
                    modelTitle = model.getJSONObject("properties").getJSONObject("settings").getString("title");
                    resources.add((Resource)ResourceGroupItem.createResourceGroupItem((String)modelResourceId, (String)modelTitle, (boolean)true));
                }
                for (JSONObject model : fileModels) {
                    modelResourceId = this.toResourceId("model_", pageId + "$" + model.getString("id"));
                    modelTitle = model.getJSONObject("properties").getJSONObject("uploadPro").getJSONObject("uploadPro").getString("title");
                    resources.add((Resource)ResourceGroupItem.createResourceGroupItem((String)modelResourceId, (String)modelTitle, (boolean)true));
                }
                break;
            }
            case "model_": {
                boolean hasDelegateAuth;
                String portalId = resourceGroupId.substring(resourceGroupId.indexOf("_") + 1, resourceGroupId.indexOf("$"));
                String mid = resourceGroupId.substring(resourceGroupId.indexOf("$") + 1);
                List<NewsAbstractInfo> newsInfos = this.news2Service.queryNewsByMidAndPortalId(mid, portalId);
                List<FileImpl> files = this.portalFileService.queryFileByMidAndPortalId(mid, portalId);
                for (NewsAbstractInfo newsAbstractInfo : newsInfos) {
                    if (!newsAbstractInfo.getStartAuth().booleanValue() || !(hasDelegateAuth = this.privilegeService.hasAuth("portal_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)this.toResourceId("item-news_", newsAbstractInfo.getId())))) continue;
                    resources.add((Resource)ResourceItem.createResourceItem((String)this.toResourceId("item-news_", newsAbstractInfo.getId()), (String)newsAbstractInfo.getTitle()));
                }
                for (FileImpl fileImpl : files) {
                    if (!fileImpl.getStartAuth().booleanValue() || !(hasDelegateAuth = this.privilegeService.hasAuth("portal_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)this.toResourceId("item-file_", fileImpl.getId())))) continue;
                    resources.add((Resource)ResourceItem.createResourceItem((String)this.toResourceId("item-file_", fileImpl.getId()), (String)fileImpl.getTitle()));
                }
                break;
            }
            case "group_": {
                String modelResourceId;
                String pageId2 = resourceGroupId.substring(resourceGroupId.indexOf("_") + 1);
                HomePage homePage = this.homePageService.getHomePageById(pageId2, "zh-CN");
                String pageJson2 = homePage.getTemplate();
                JSONObject jsonObject2 = new JSONObject(pageJson2);
                JSONArray jsonArray2 = (JSONArray)jsonObject2.get("modulesConfig");
                ArrayList<JSONObject> groupModels = new ArrayList<JSONObject>();
                for (int i = 0; i < jsonArray2.length(); ++i) {
                    JSONObject json = (JSONObject)jsonArray2.get(i);
                    if (!"TabsContainer".equals(json.get("type"))) continue;
                    groupModels.add(json);
                }
                ArrayList<JSONObject> newsJsons = new ArrayList<JSONObject>();
                ArrayList<JSONObject> fileJsons = new ArrayList<JSONObject>();
                for (JSONObject model : groupModels) {
                    JSONArray muJsonArray = model.getJSONObject("properties").getJSONArray("tabs");
                    for (int i = 0; i < muJsonArray.length(); ++i) {
                        JSONObject json = (JSONObject)muJsonArray.get(i);
                        if ("News".equals(json.get("tabType"))) {
                            newsJsons.add(json);
                            continue;
                        }
                        if (!"Uploader".equals(json.get("tabType"))) continue;
                        fileJsons.add(json);
                    }
                }
                for (JSONObject model : newsJsons) {
                    modelResourceId = this.toResourceId("model_", pageId2 + "$" + model.getString("tabId"));
                    String modelTitle = model.getString("tabTitle");
                    resources.add((Resource)ResourceGroupItem.createResourceGroupItem((String)modelResourceId, (String)modelTitle, (boolean)true));
                }
                for (JSONObject model : fileJsons) {
                    modelResourceId = this.toResourceId("model_", pageId2 + "$" + model.getString("tabId"));
                    String modelTitle = model.getString("tabTitle");
                    resources.add((Resource)ResourceGroupItem.createResourceGroupItem((String)modelResourceId, (String)modelTitle, (boolean)true));
                }
                break;
            }
        }
        return resources;
    }
}

