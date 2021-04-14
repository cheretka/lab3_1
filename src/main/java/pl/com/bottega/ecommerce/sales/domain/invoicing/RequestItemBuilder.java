package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductDataBuilder;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class RequestItemBuilder {
    //    default values for ProductData
    private ProductData productData = new ProductDataBuilder().build();

    private int quantity = 0;

    private Money totalCost =  Money.ZERO;

    public RequestItem build() {
        return new RequestItem(productData, quantity, totalCost);
    }

    public RequestItemBuilder setProductData(ProductData productData) {
        this.productData = productData;
        return this;
    }

    public RequestItemBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public RequestItemBuilder setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
        return this;
    }

}
