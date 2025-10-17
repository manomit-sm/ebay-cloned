package com.ebay.backend.mapper;

import com.ebay.backend.dto.request.UserRegistrationRequest;
import com.ebay.backend.dto.response.UserRegistrationResponse;
import com.ebay.backend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User toUserEntity(UserRegistrationRequest userRegistrationRequest, @Context String azureId);

    UserRegistrationResponse toUserResponse(User user);
}
