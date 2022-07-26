package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.exceptions.errors.ChangeOwnerAttempt;
import ru.practicum.shareit.exceptions.errors.NoUserFound;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.storage.ItemStorage;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private ItemStorage storage;

    @Autowired
    public ItemServiceImpl(ItemStorage storage) {
        this.storage = storage;
    }

    public ItemDto create(long ownerId, ItemDto itemDto) {

        //using RestTemplate to check if owner is present
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/users/" + ownerId, String.class);
        if (responseEntity.getStatusCode().isError()) {
            throw new NoUserFound("NO SUCH OWNER");
        }

        itemDto.setId(storage.generateId());
        storage.add(ItemMapper.toItem(itemDto));
        return itemDto;
    }

    public ItemDto update(long ownerId, long id, JsonNode object) {
        if (ownerId != storage.get(id).getOwnerId()) {
            throw new ChangeOwnerAttempt("USER NOT OWNER");
        }
        if (object.has("name")) {
            storage.get(id).setName(object.get("name").textValue());
        }
        if (object.has("description")) {
            storage.get(id).setDescription(object.get("description").textValue());
        }
        if (object.has("available")) {
            storage.get(id).setAvailable(object.get("available").asBoolean());
        }
        return ItemMapper.toItemDto(storage.get(id));
    }

    public ItemDto getItem(long ownerId, long id) {
        return ItemMapper.toItemDto(storage.get(id));
    }

    public Collection<ItemDto> getAll(long ownerId) {
        Set<ItemDto> itemDtoSet= new TreeSet<>((item1, item2) -> (int) (item1.getId() - item2.getId()));
        storage.getAll().stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .map(ItemMapper::toItemDto)
                .forEach(itemDtoSet::add);
        return itemDtoSet;
    }

    public Collection<ItemDto> search(long ownerId, String text) {

        Set<ItemDto> itemDtoSet= new TreeSet<>((item1, item2) -> (int) (item1.getId() - item2.getId()));
        if (text.isEmpty()) {
            return itemDtoSet;
        }
        storage.getAll().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .map(ItemMapper::toItemDto)
                .forEach(itemDtoSet::add);
        return itemDtoSet;
    }

    public void delete(long id) {
        storage.delete(id);
    }
}
