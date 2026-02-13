package com.inventoryapi.dto;

public class ArticleDTO {
    private Long id;
    private String name;
    private Double unitPrice;
    private Integer stock;
    private Long groupId;

    public ArticleDTO() {}

    public ArticleDTO(Long id, String name, Double unitPrice, Integer stock, Long groupId) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.groupId = groupId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
}