package com.example.unit.mapper;

import com.example.dto.request.AdvertiserRequestDTO;
import com.example.dto.response.AdvertiserResponseDTO;
import com.example.entity.Advertiser;
import com.example.mapper.impl.AdvertiserMapperImpl;
import com.example.resources.AdvertiserProvider;
import com.example.resources.UserProvider;
import com.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvertiserMapperImplTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private AdvertiserMapperImpl mapper;

    @Test
    void toPageResponseDTO() {
        assertEquals(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0),
                mapper.toPageResponseDTO(null));

        assertEquals(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0),
                mapper.toPageResponseDTO(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0)));

        List<Advertiser> entities = AdvertiserProvider.listEntities();
        Page<AdvertiserResponseDTO> page = mapper.toPageResponseDTO(
                new PageImpl<>(entities, Pageable.unpaged(), 2));
        assertNotNull(page);
        assertEquals(Pageable.unpaged(), page.getPageable());
        assertEquals(entities.getFirst().getId(), page.getContent().getFirst().id());
        assertEquals(entities.getFirst().getTitle(), page.getContent().getFirst().title());
        assertEquals(entities.getLast().getId(), page.getContent().getLast().id());
        assertEquals(entities.getLast().getTitle(), page.getContent().getLast().title());
    }

    @Test
    void toResponseDTO() {
        assertNull(mapper.toResponseDTO(null));

        Advertiser entity = AdvertiserProvider.singleEntity();
        AdvertiserResponseDTO responseDTO = mapper.toResponseDTO(entity);
        assertNotNull(responseDTO);
        assertEquals(entity.getId(), responseDTO.id());
        assertEquals(entity.getTitle(), responseDTO.title());
        assertEquals(entity.getDescription(), responseDTO.description());
        assertEquals(entity.getUser().getId(), responseDTO.userId());
    }

    @Test
    void toEntity() {
        assertNull(mapper.toEntity(null));

        AdvertiserRequestDTO advertiserRequestDTO = AdvertiserProvider.singleRequest();
        when(userService.findById(UUID.fromString(advertiserRequestDTO.getUserId())))
                .thenReturn(UserProvider.singleEntity());
        Advertiser entity = mapper.toEntity(advertiserRequestDTO);
        assertNotNull(entity);
        assertEquals(advertiserRequestDTO.getTitle(), entity.getTitle());
        assertEquals(advertiserRequestDTO.getDescription(), entity.getDescription());
        assertEquals(advertiserRequestDTO.getUserId(), entity.getUser().getId().toString());
        verify(userService).findById(UUID.fromString(advertiserRequestDTO.getUserId()));
    }

    @Test
    void toListResponseDTO() {
        assertEquals(Collections.emptyList(), mapper.toListResponseDTO(null));

        assertEquals(Collections.emptyList(), mapper.toListResponseDTO(Collections.emptyList()));

        List<Advertiser> entities = AdvertiserProvider.listEntities();
        List<AdvertiserResponseDTO> responseDTOS = mapper.toListResponseDTO(
                entities);
        assertNotNull(responseDTOS);
        assertEquals(entities.getFirst().getId(),
                responseDTOS.getFirst().id());
        assertEquals(entities.getFirst().getTitle(),
                responseDTOS.getFirst().title());
        assertEquals(entities.getLast().getId(),
                responseDTOS.getLast().id());
        assertEquals(entities.getLast().getTitle(),
                responseDTOS.getLast().title());
    }
}