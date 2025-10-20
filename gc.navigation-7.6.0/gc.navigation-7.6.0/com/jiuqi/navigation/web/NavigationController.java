/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.navigation.web;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.navigation.common.BusinessResponseEntity;
import com.jiuqi.navigation.service.NavigationService;
import com.jiuqi.navigation.vo.NavigationVO;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/navigation"})
public class NavigationController {
    @Autowired
    private NavigationService navigationService;

    @PostMapping(value={"/save"})
    public BusinessResponseEntity<Object> saveConfig(@RequestBody NavigationVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return BusinessResponseEntity.error("", error, "");
        }
        NavigationVO configByCode = this.navigationService.saveConfig(vo);
        return BusinessResponseEntity.ok(configByCode);
    }

    @PostMapping(value={"/update"})
    public BusinessResponseEntity<Object> updateConfig(@RequestBody NavigationVO vo) {
        NavigationVO configByCode = this.navigationService.updateConfig(vo);
        return BusinessResponseEntity.ok(configByCode);
    }

    @GetMapping(value={"/get/{code}"})
    public BusinessResponseEntity<NavigationVO> getConfigByCode(@PathVariable(name="code") String code) {
        NavigationVO navigationVO = this.navigationService.getNavigationConfigByCode(code);
        return BusinessResponseEntity.ok(navigationVO);
    }

    @GetMapping(value={"/pages/config"})
    public String getConfigs() {
        List<NavigationVO> navigationVOS = this.navigationService.getAllPages();
        return JsonUtils.writeValueAsString(navigationVOS);
    }

    @GetMapping(value={"/pages"})
    public BusinessResponseEntity<List<NavigationVO>> getPages() {
        List<NavigationVO> navigationVOS = this.navigationService.getAllPages();
        return BusinessResponseEntity.ok(navigationVOS);
    }

    @PostMapping(value={"/delete/{id}"})
    public BusinessResponseEntity<Object> delete(@PathVariable(value="id") String id) {
        this.navigationService.deleteConfig(id);
        return BusinessResponseEntity.ok();
    }
}

