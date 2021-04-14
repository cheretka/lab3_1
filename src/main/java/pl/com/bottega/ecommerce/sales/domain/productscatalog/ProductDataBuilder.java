package pl.com.bottega.ecommerce.sales.domain.productscatalog;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

public class ProductDataBuilder {
//    default values for ProductData
    private Id productId = Id.generate();
    private Money price = Money.ZERO;

    private String name = "Default product";

    private Date snapshotDate = new Date();

    private ProductType type = ProductType.STANDARD;


    public ProductData build() {
        return new ProductData(productId, price, name, type, snapshotDate);
    }

    public ProductDataBuilder setProductId(Id productId) {
        this.productId = productId;
        return this;
    }

    public ProductDataBuilder setPrice(Money price) {
        this.price = price;
        return this;
    }

    public ProductDataBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductDataBuilder setDate(Date snapshotDate) {
        this.snapshotDate = snapshotDate;
        return this;
    }

    public ProductDataBuilder setType(ProductType productType) {
        this.type = productType;
        return this;
    }
}
