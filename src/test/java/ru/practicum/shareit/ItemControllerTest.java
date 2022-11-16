package ru.practicum.shareit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ItemService itemService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createTest() throws Exception {
        ItemDto itemDto = new ItemDto("test", "test", true, 1);
        when(itemService.create(Mockito.any(), Mockito.anyInt())).thenReturn(itemDto);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"requestId\": 1, \"name\": \"test\",\"description\": \"test\", \"available\": true}")
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("test"))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    public void updateTest() throws Exception {
        ItemDto itemDto = new ItemDto("test", "test", true, 1);
        when(itemService.update(Mockito.anyInt(), Mockito.any(), Mockito.anyInt())).thenReturn(itemDto);

        mockMvc.perform(patch("/items/{itemId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"available\": true}")
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getTest() throws Exception {
        ItemDto itemDto = new ItemDto("test", "test", true, 1);
        when(itemService.get(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itemDto);

        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTest() throws Exception {
        ItemDto itemDto = new ItemDto("test", "test", true, 1);
        when(itemService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(List.of(itemDto));

        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "-1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "1")
                        .param("size", "-1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "1")
                        .param("size", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSearch() throws Exception {
        ItemDto itemDto = new ItemDto("test", "test", true, 1);
        when(itemService.search(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(List.of(itemDto));

        mockMvc.perform(get("/items/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "-1")
                        .param("text", "test"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/items/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "1")
                        .param("size", "-1")
                        .param("text", "test"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/items/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("from", "1")
                        .param("size", "1")
                        .param("text", "test"))
                .andExpect(status().isOk());
    }

    @Test
    public void addComment() throws Exception {
        CommentDto commentDto = new CommentDto("Test");
        when(itemService.addComment(Mockito.any())).thenReturn(commentDto);

        mockMvc.perform(post("/items/{itemId}/comment", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"Test\"}"))
                .andExpect(status().isOk());
    }
}
