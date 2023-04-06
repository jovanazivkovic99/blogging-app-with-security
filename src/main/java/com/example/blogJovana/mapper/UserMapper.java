package com.example.blogJovana.mapper;

import com.example.blogJovana.model.User;
import com.example.blogJovana.request.LoginRequest;
import com.example.blogJovana.request.RegistrationRequest;
import com.example.blogJovana.response.RegistrationResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Mapper(uses = PasswordMapper.class, imports = {BCryptPasswordEncoder.class, LocalDateTime.class}, componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "role", expression = "java(User.Role.ROLE_USER)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    User toUser(RegistrationRequest request);

    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "role", expression = "java(User.Role.ROLE_ADMIN)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    User toAdminUser(RegistrationRequest request);

    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    User toUser(LoginRequest request);

    RegistrationResponse toRegistrationResponse(User user);
}
