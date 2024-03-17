package com.xsis.movieapi.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {
    private Integer id;
    @NotNull
    private String title;
    private String description;
    private Float rating;
    private String image;
}
