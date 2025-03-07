package com.example.resources;

import com.example.dto.request.RoleRequestDTO;
import com.example.dto.response.RoleResponseDTO;
import com.example.entity.Role;
import com.example.entity.intermediate.RolePermission;

import java.util.*;

public class RoleProvider {

    public static RoleRequestDTO firstPReqDTO = new RoleRequestDTO(
            "ADMIN", "It's an admin",
            new HashSet<>(Set.of(PermissionProvider.singleEntity().getId())));
    public static RoleRequestDTO secondPReqDTO = new RoleRequestDTO(
            "ADMIN", "It's an admin",
            new HashSet<>(Set.of(PermissionProvider.alternativeEntity().getId())));

    public static RoleResponseDTO firstPResDTO = new RoleResponseDTO(
            UUID.fromString("e87ce3ba-fe71-4cf1-b302-94446a3684ca"),
            "ADMIN", "It's an admin",
            new ArrayList<>(List.of(PermissionProvider.singleResponse())));
    public static RoleResponseDTO secondPResDTO = new RoleResponseDTO(
            UUID.fromString("33d6bb03-ae1c-4b0d-8f31-08095452bc40"),
            "USER", "It's an user",
            new ArrayList<>(List.of(PermissionProvider.alternativeResponse())));

    public static List<Role> listEntities() {
        return  new ArrayList<>(List.of(singleEntity(), alternativeEntity()));
    }

    public static List<RoleRequestDTO> listRequest() {
        return List.of(firstPReqDTO, secondPReqDTO);
    }

    public static List<RoleResponseDTO> listResponse() {
        return List.of(firstPResDTO, secondPResDTO);
    }

    public static Role singleEntity() {
        Role firstEntity = new Role(
                UUID.fromString("e87ce3ba-fe71-4cf1-b302-94446a3684ca"),
                "ADMIN", "It's an admin", null, null);
        RolePermission firstRolePermission = new RolePermission(
                null, PermissionProvider.singleEntity());
        firstEntity.setRolePermissions(new HashSet<>(Set.of(firstRolePermission)));
        return firstEntity;
    }

    public static Role alternativeEntity() {
        Role secondEntity = new Role(
                UUID.fromString("33d6bb03-ae1c-4b0d-8f31-08095452bc40"),
                "USER", "It's an user", null, null);
        RolePermission secondRolePermission = new RolePermission(
                null, PermissionProvider.alternativeEntity());
        secondEntity.setRolePermissions(new HashSet<>(Set.of(secondRolePermission)));
        return secondEntity;
    }

    public static RoleRequestDTO singleRequest() {
        return firstPReqDTO;
    }

    public static RoleRequestDTO alternativeRequest() {
        return secondPReqDTO;
    }

    public static RoleResponseDTO singleResponse() {
        return firstPResDTO;
    }

    public static RoleResponseDTO alternativeResponse() {
        return secondPResDTO;
    }
}
