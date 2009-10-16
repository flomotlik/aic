package at.tuwien.aic666.datamodel;

import java.math.BigDecimal;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class Item {

    //Java uses CamelCasing NO Undescore
    @XmlAttribute(name = "id")
    private String productId;
    @XmlTransient
    private Order order;
    private int quantity;
    private BigDecimal singleUnitPrice;

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSingleUnitPrice() {
        return this.singleUnitPrice;
    }

    public void setSingleUnitPrice(BigDecimal singleUnitPrice) {
        this.singleUnitPrice = singleUnitPrice;
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.order = (Order) parent;
    }
}
