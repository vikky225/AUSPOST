package com.au.postcode.lookupservice.util;

import com.au.postcode.lookupservice.dto.PostCodeDetailsDto;
import com.au.postcode.lookupservice.entity.PostCodeDetail;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static PostCodeDetailsDto toDto(PostCodeDetail postCodeDetail){
        PostCodeDetailsDto postCodeDetailsDto = new PostCodeDetailsDto();
         BeanUtils.copyProperties(postCodeDetail,postCodeDetailsDto);
         return postCodeDetailsDto;
    }

    public static PostCodeDetail toEntity(PostCodeDetailsDto postCodeDetailsDto){
        PostCodeDetail postCodeDetail = new PostCodeDetail();
        BeanUtils.copyProperties(postCodeDetailsDto,postCodeDetail);
        return postCodeDetail;
    }


}
