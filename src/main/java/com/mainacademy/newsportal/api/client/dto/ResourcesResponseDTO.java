package com.mainacademy.newsportal.api.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesResponseDTO {

    private String status;
    private List<Resource> sources;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Resource {
        private String id;
        private String name;
        private String url;
        private String category;
        private String language;
        private String country;
    }

}
