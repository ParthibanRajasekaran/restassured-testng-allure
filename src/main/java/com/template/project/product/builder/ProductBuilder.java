package com.template.project.product;

public class ProductBuilder {
    private Product product;

    public ProductBuilder() {
        product = new Product();
    }

    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder withPrice(double price) {
        product.setPrice(price);
        return this;
    }

    public Product build() {
        return product;
    }
}

