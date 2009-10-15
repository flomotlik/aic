package at.tuwien.aic666;

import java.math.BigDecimal;

class Item {

    private Order order;
    private int quantity;
    //Java uses CamelCasing NO Undescore
    private String productId;
    private BigDecimal singleUnitPrice;

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSingleUnitPrice() {
        return singleUnitPrice;
    }

    public void setSingleUnitPrice(BigDecimal singleUnitPrice) {
        this.singleUnitPrice = singleUnitPrice;
    }
}
