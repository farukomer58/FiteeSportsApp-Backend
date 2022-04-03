package com.fitee.dto;


import com.fitee.model.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ActivityDto {
    private Long id;
    private String name;
    private String description;
    private String unit;
    private Double quantity;
    private BigDecimal price;
    private String quantity2;
    private BigDecimal price2;
    private Date addedDate;
//    private ImageEntity productImage;
//    private ProductCategoryEntity productCategory;
//    private List<DiscountPriceEntity> discounts;

    private Long ownerId;
    private String supplierPostalCode;
    private String supplierEmail;
    private String supplierName;
    private Long supplier2UserId;

    public void setSupplierData(User supplier) {
        this.ownerId = supplier.getId();

        // TODO: bugfix if no postalcode is available
//        if (supplier.getAddresses().size() > 0) {
//            this.supplierPostalCode = supplier.getAddresses().iterator().next().getPostalCode();
//        } else {
//            this.supplierPostalCode = "1000";
//        }
//
//        this.supplierEmail = supplier.getContactEmail();
//        this.supplierName = supplier.getCompanyName();
//        this.supplier2UserId = supplier.getUser().getId();
    }
}
