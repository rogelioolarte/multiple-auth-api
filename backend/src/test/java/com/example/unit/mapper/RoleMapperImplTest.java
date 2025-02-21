package com.example.unit.mapper;

import com.example.dto.response.RoleResponseDTO;
import com.example.entity.Role;
import com.example.mapper.impl.PermissionMapperImpl;
import com.example.mapper.impl.RoleMapperImpl;
import com.example.resources.PermissionProvider;
import com.example.resources.RoleProvider;
import com.example.service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleMapperImplTest {

    @Mock
    private PermissionServiceImpl permissionService;

    @Mock
    private PermissionMapperImpl permissionMapper;

    @InjectMocks
    private RoleMapperImpl mapper;

    @Test
    void toPageResponseDTO() {
        assertEquals(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0),
                mapper.toPageResponseDTO(null));

        assertEquals(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0),
                mapper.toPageResponseDTO(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0)));

        Page<RoleResponseDTO> page = mapper.toPageResponseDTO(
                new PageImpl<>(RoleProvider.listEntities(), Pageable.unpaged(), 2));
        assertNotNull(page);
        assertEquals(Pageable.unpaged(), page.getPageable());
        assertEquals(RoleProvider.listEntities().getFirst().getId(),
                page.getContent().getFirst().id());
        assertEquals(RoleProvider.listEntities().getFirst().getRoleName(),
                page.getContent().getFirst().roleName());
        assertEquals(RoleProvider.listEntities().getFirst().getRoleDescription(),
                page.getContent().getFirst().roleDescription());
        assertEquals(RoleProvider.listEntities().getLast().getId(),
                page.getContent().getLast().id());
        assertEquals(RoleProvider.listEntities().getLast().getRoleName(),
                page.getContent().getLast().roleName());
        assertEquals(RoleProvider.listEntities().getLast().getRoleDescription(),
                page.getContent().getLast().roleDescription());
    }

    @Test
    void toResponseDTO() {
        assertNull(mapper.toResponseDTO(null));

        when(permissionMapper.toListResponseDTO(RoleProvider.singleEntity().getPermissions()))
                .thenReturn(new ArrayList<>(List.of(PermissionProvider.singleResponse())));
        RoleResponseDTO responseDTO = mapper.toResponseDTO(RoleProvider.singleEntity());
        assertNotNull(responseDTO);
        assertEquals(RoleProvider.singleEntity().getId(), responseDTO.id());
        assertEquals(RoleProvider.singleEntity().getRoleName(), responseDTO.roleName());
        assertEquals(RoleProvider.singleEntity().getRoleDescription(), responseDTO.roleDescription());
        assertEquals(RoleProvider.singleEntity().getRolePermissions()
                .iterator().next().getPermission().getId(), responseDTO.permissions().getFirst().id());
        verify(permissionMapper).toListResponseDTO(RoleProvider.singleEntity().getPermissions());
    }

    @Test
    void toEntity() {
        assertNull(mapper.toEntity(null));

        when(permissionService.findAllByIdsToSet(Set.of(PermissionProvider.singleEntity().getId())))
                .thenReturn(new HashSet<>(Set.of(PermissionProvider.singleEntity())));
        Role entity = mapper.toEntity(RoleProvider.singleRequest());
        assertNotNull(entity);
        assertEquals(RoleProvider.singleRequest().getRoleName(), entity.getRoleName());
        assertEquals(RoleProvider.singleRequest().getRoleDescription(), entity.getRoleDescription());
        assertEquals(RoleProvider.singleRequest().getPermissions().iterator().next(),
                entity.getRolePermissions().iterator().next().getPermission().getId());
        verify(permissionService).findAllByIdsToSet(Set.of(PermissionProvider.singleEntity().getId()));
    }

    @Test
    void toListResponseDTO() {
        assertEquals(Collections.emptyList(), mapper.toListResponseDTO(null));

        assertEquals(Collections.emptyList(), mapper.toListResponseDTO(Collections.emptyList()));

        List<Role> roles = RoleProvider.listEntities();

        List<RoleResponseDTO> responseDTOes = mapper.toListResponseDTO(List.of(roles.getFirst()));
        assertNotNull(responseDTOes);
        assertEquals(roles.getFirst().getId(),
                responseDTOes.getFirst().id());
        assertEquals(roles.getFirst().getRoleName(),
                responseDTOes.getFirst().roleName());

        List<RoleResponseDTO> responseDTOS = mapper.toListResponseDTO(roles);
        assertNotNull(responseDTOS);
        assertEquals(roles.getFirst().getId(),
                responseDTOS.getFirst().id());
        assertEquals(roles.getFirst().getRoleName(),
                responseDTOS.getFirst().roleName());
        assertEquals(roles.getFirst().getRoleDescription(),
                responseDTOS.getFirst().roleDescription());
        assertEquals(roles.getLast().getId(),
                responseDTOS.getLast().id());
        assertEquals(roles.getLast().getRoleName(),
                responseDTOS.getLast().roleName());
        assertEquals(roles.getLast().getRoleDescription(),
                responseDTOS.getLast().roleDescription());
    }
}