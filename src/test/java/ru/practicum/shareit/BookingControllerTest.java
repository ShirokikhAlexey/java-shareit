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
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.util.Status;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private BookingService bookingService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void createTest() throws Exception {
        BookingDto bookingDto = new BookingDto(1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.CURRENT, "test");
        when(bookingService.create(Mockito.any())).thenReturn(bookingDto);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookerId\": 1, \"itemId\": 1, \"from\": \"2022-01-01 00:00:00\",\"to\": \"2022-02-01 00:00:00\", \"status\": \"CURRENT\", \"review\": \"test\"}")
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CURRENT"))
                .andExpect(jsonPath("$.review").value("test"));
    }

    @Test
    public void updateTest() throws Exception {
        BookingDto bookingDto = new BookingDto(1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.APPROVED, "test");
        when(bookingService.changeStatus(1, true, 1)).thenReturn(bookingDto);

        mockMvc.perform(patch("/bookings/{bookingId}", 1)
                        .param("approved", String.valueOf(true))
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    public void getTest() throws Exception {
        BookingDto bookingDto = new BookingDto(1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.APPROVED, "test");
        when(bookingService.get(1, 1)).thenReturn(bookingDto);

        mockMvc.perform(get("/bookings/{bookingId}", 1)
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.review").value("test"));
    }
}
