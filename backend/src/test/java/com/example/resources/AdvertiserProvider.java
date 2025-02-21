package com.example.resources;

import com.example.dto.request.AdvertiserRequestDTO;
import com.example.dto.response.AdvertiserResponseDTO;
import com.example.entity.Advertiser;

import java.util.List;
import java.util.UUID;

public class AdvertiserProvider {

    public static Advertiser firstEntity = new Advertiser(
            UUID.fromString("ca6e66dc-900e-42f5-8f4b-c81385d799c3"),
            "Advertiser_1", "Advertiser version 1",
            "https://banner.com/advertiser1", "https://avatar.com/advertiser1",
            "av. root 123", "https://location.com/advertiser1",
            true, true, true, UserProvider.singleEntity());
    public static Advertiser secondEntity = new Advertiser(
            UUID.fromString("91a16ad9-2d6c-4482-9a5c-02f8948afa8d"),
            "Advertiser_2", "Advertiser version 2",
            "https://banner.com/advertiser2", "https://avatar.com/advertiser2",
            "av. root 321", "https://location.com/advertiser2",
            true, true, true, UserProvider.alternativeEntity());

    public static AdvertiserRequestDTO firstPReqDTO = new AdvertiserRequestDTO(
            "Advertiser_1", "Advertiser version 1", "https://banner.com/advertiser1",
            "https://avatar.com/advertiser1", "av. root 123",
            "https://location.com/advertiser1", true,
            UserProvider.singleEntity().getId().toString());
    public static AdvertiserRequestDTO secondPReqDTO = new AdvertiserRequestDTO(
            "Advertiser_2", "Advertiser version 2",
            "https://banner.com/advertiser2", "https://avatar.com/advertiser2",
            "av. root 321", "https://location.com/advertiser2",
            true, UserProvider.alternativeEntity().getId().toString());


    public static AdvertiserResponseDTO firstPResDTO = new AdvertiserResponseDTO(
            UUID.fromString("ca6e66dc-900e-42f5-8f4b-c81385d799c3"),
            "Advertiser_1", "Advertiser version 1",
            "https://banner.com/advertiser1", "https://avatar.com/advertiser1",
            "av. root 123", "https://location.com/advertiser1",
            true, true, true, UserProvider.singleResponse().id(),
            UserProvider.singleResponse().email());
    public static AdvertiserResponseDTO secondPResDTO = new AdvertiserResponseDTO(
            UUID.fromString("91a16ad9-2d6c-4482-9a5c-02f8948afa8d"),
            "Advertiser_2", "Advertiser version 2",
            "https://banner.com/advertiser2", "https://avatar.com/advertiser2",
            "av. root 321", "https://location.com/advertiser2",
            true, true, true, UserProvider.alternativeResponse().id(),
            UserProvider.alternativeResponse().email());

    public static List<Advertiser> listEntities() {
        return List.of(firstEntity, secondEntity);
    }

    public static List<AdvertiserRequestDTO> listRequest() {
        return List.of(firstPReqDTO, secondPReqDTO);
    }

    public static List<AdvertiserResponseDTO> listResponse() {
        return List.of(firstPResDTO, secondPResDTO);
    }

    public static Advertiser singleEntity() {
        return firstEntity;
    }

    public static Advertiser alternativeEntity() {
        return secondEntity;
    }

    public static AdvertiserRequestDTO singleRequest() {
        return firstPReqDTO;
    }

    public static AdvertiserRequestDTO alternativeRequest() {
        return secondPReqDTO;
    }

    public static AdvertiserResponseDTO singleResponse() {
        return firstPResDTO;
    }

    public static AdvertiserResponseDTO alternativeResponse() {
        return secondPResDTO;
    }
}
