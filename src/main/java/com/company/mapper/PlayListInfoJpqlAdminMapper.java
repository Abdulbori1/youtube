package com.company.mapper;

import java.util.UUID;

public interface PlayListInfoJpqlAdminMapper {
    Integer getPl_id();
    String getPl_name();
    String getPl_description();
    String getPl_status();
    Integer getOrder_num();

    Integer getCh_id();
    String getCh_name();
    Integer getCh_photo_id();

    Integer getPr_id();
    String getPr_name();
    String getPr_surname();
    UUID getPr_photo_id();
}
