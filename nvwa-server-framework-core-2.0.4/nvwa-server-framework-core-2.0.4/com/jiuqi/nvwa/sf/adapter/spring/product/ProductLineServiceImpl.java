/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.product;

import com.jiuqi.nvwa.sf.adapter.spring.product.IProductLine;
import com.jiuqi.nvwa.sf.adapter.spring.product.IProductLineService;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductLineBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductLineServiceImpl
implements IProductLineService {
    @Autowired(required=false)
    private List<IProductLine> productLines;
    private volatile List<ProductLineBean> productLineBeans;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ProductLineBean> list() {
        if (this.productLineBeans == null) {
            ProductLineServiceImpl productLineServiceImpl = this;
            synchronized (productLineServiceImpl) {
                if (this.productLineBeans == null) {
                    if (this.productLines == null || this.productLines.isEmpty()) {
                        return new ArrayList<ProductLineBean>();
                    }
                    this.productLineBeans = this.productLines.stream().map(productLine -> {
                        ProductLineBean productLineBean = new ProductLineBean();
                        productLineBean.setId(productLine.id());
                        productLineBean.setTitle(productLine.title());
                        productLineBean.setVersion(productLine.version());
                        productLineBean.setBuildTime(productLine.buildTime());
                        return productLineBean;
                    }).collect(Collectors.toList());
                }
            }
        }
        return this.productLineBeans;
    }
}

