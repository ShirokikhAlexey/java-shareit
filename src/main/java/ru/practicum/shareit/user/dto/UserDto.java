package ru.practicum.shareit.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    private List<Item> items;

    public UserDto(Integer id, String name, String email, List<Item> items) {
        this.name = name;
        this.email = email;
        this.items = items;
        this.id = id;
    }

}
