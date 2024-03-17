package com.xsis.movieapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

import static com.xsis.movieapi.util.Utils.parseLocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private Integer id;
    private String title;
    private String description;
    private Float rating;
    private String image;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt != null ? parseLocalDateTime(createdAt) : null;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt != null ? parseLocalDateTime(updatedAt) : null;
    }
}
