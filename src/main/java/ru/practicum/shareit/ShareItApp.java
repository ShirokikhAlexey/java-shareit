package ru.practicum.shareit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.practicum.shareit.db.memory.ItemStorageMemory;
import ru.practicum.shareit.db.memory.UserStorageMemory;

@SpringBootApplication
public class ShareItApp {
	public static ItemStorageMemory itemStorage = new ItemStorageMemory();
	public static UserStorageMemory userStorage = new UserStorageMemory();

	public static void main(String[] args) {
		SpringApplication.run(ShareItApp.class, args);
	}

}
