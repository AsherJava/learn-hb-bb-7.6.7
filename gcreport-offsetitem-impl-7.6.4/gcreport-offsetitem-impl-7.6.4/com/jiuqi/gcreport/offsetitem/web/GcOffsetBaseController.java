/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.api.GcOffsetBaseClient
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton
 *  com.jiuqi.gcreport.offsetitem.vo.Button
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.api.GcOffsetBaseClient;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemButton;
import com.jiuqi.gcreport.offsetitem.service.GcOffsetBaseService;
import com.jiuqi.gcreport.offsetitem.vo.Button;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@CrossOrigin
public class GcOffsetBaseController
implements GcOffsetBaseClient {
    @Autowired
    private List<GcOffsetItemButton> gcOffsetItemButtonList;
    @Autowired
    private GcOffsetBaseService baseService;

    public BusinessResponseEntity<List<Map<String, Object>>> listshowTypeConfigForTab(String pageCode, String dataSource) {
        return BusinessResponseEntity.ok(this.baseService.listshowTypeConfigForPage(pageCode, dataSource));
    }

    public BusinessResponseEntity<List<Map<String, Object>>> listShowTypeConfig(String dataSource) {
        return BusinessResponseEntity.ok(this.baseService.listshowTypeConfig(dataSource));
    }

    public List<Button> listShowButton() {
        ArrayList<Button> res = new ArrayList<Button>();
        HashSet<String> set = new HashSet<String>();
        for (GcOffsetItemButton gcOffsetItemButton : this.gcOffsetItemButtonList) {
            Button button = new Button();
            String t = gcOffsetItemButton.title();
            button.setValue(gcOffsetItemButton.code());
            button.setShowType(t);
            button.setIsShow(gcOffsetItemButton.isVisible(null) ? "1" : "0");
            if (set.contains(t)) continue;
            set.add(t);
            res.add(button);
        }
        return res;
    }
}

