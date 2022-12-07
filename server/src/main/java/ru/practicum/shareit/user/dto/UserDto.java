package ru.practicum.shareit.user.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String email;

    @JsonIgnore
    private List<Item> items;

    public UserDto(Integer id, String name, String email, List<Item> items) {
        this.name = name;
        this.email = email;
        this.items = items;
        this.id = id;
    }

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDto() {

    }

}
