package org.example.model;

import java.util.Date;

public class Buy {
    private long id;
    private long consumerId;
    private long productId;
    private Date date;

    public Buy() {
    }

    public Buy(long id, long consumerId, long productId, Date date) {
        this.id = id;
        this.consumerId = consumerId;
        this.productId = productId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Product[ID:" + id + ", ConsumerId: " + consumerId + "," +
               " ProductId: " + productId + ", Date: " + date +"]";
    }

}
