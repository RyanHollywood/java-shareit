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
import ru.practicum.shareit.exceptions.errors.ItemNotFound;
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

    @Override
    public ItemDto create(long ownerId, ItemDto itemDto) {

        //using RestTemplate to check if owner is present
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/users/" + ownerId, String.class);
        if (responseEntity.getStatusCode().isError()) {
            log.warn("POST REQUEST UNSUCCESSFUL - ITEM NOT CREATED");
            throw new NoUserFound("NO OWNER WITH ID:" + ownerId + " FOUND");
        }

        itemDto.setId(storage.generateId());
        storage.add(ItemMapper.toItem(itemDto));
        log.debug("POST REQUEST SUCCESSFUL - ITEM ID:" + itemDto.getId() + " CREATED");
        return itemDto;
    }

    @Override
    public ItemDto update(long ownerId, long id, JsonNode object) {
        if (ownerId != storage.get(id).getOwnerId()) {
            log.warn("PATCH REQUEST UNSUCCESSFUL - USER ID:" + ownerId + " NOT OWNER OF ITEM ID:" + id);
            throw new ChangeOwnerAttempt("USER ID:" + ownerId + " NOT OWNER OF ITEM ID:" + id);
        }
        if (object.has("name")) {
            storage.get(id).setName(object.get("name").textValue());
            log.debug("PATCH REQUEST SUCCESSFUL - ITEM ID:" + id + " NAME UPDATED");
        }
        if (object.has("description")) {
            storage.get(id).setDescription(object.get("description").textValue());
            log.debug("PATCH REQUEST SUCCESSFUL - ITEM ID:" + id + " DESCRIPTION UPDATED");
        }
        if (object.has("available")) {
            storage.get(id).setAvailable(object.get("available").asBoolean());
            log.debug("PATCH REQUEST SUCCESSFUL - ITEM ID:" + id + " AVAILABLE STATUS UPDATED");
        }
        return ItemMapper.toItemDto(storage.get(id));
    }

    @Override
    public ItemDto getItem(long ownerId, long id) {
        if (!storage.contains(id)) {
            log.warn("GET REQUEST UNSUCCESSFUL - ITEM ID:" + id + " NOT FOUND");
            throw new ItemNotFound("NO ITEM ID:" + id + " FOUND");
        }
        log.debug("GET REQUEST SUCCESSFUL - ITEM ID:" + id + " FOUND");
        return ItemMapper.toItemDto(storage.get(id));
    }

    @Override
    public Collection<ItemDto> getAll(long ownerId) {
        Set<ItemDto> itemDtoSet = new TreeSet<>((item1, item2) -> (int) (item1.getId() - item2.getId()));
        storage.getAll().stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .map(ItemMapper::toItemDto)
                .forEach(itemDtoSet::add);
        log.debug("GET REQUEST SUCCESSFUL - " + itemDtoSet.size() + " ITEMS FOUND");
        return itemDtoSet;
    }

    @Override
    public Collection<ItemDto> search(long ownerId, String text) {
        Set<ItemDto> itemDtoSet = new TreeSet<>((item1, item2) -> (int) (item1.getId() - item2.getId()));
        if (text.isEmpty()) {
            log.debug("GET REQUEST SUCCESSFUL - ITEM SEARCH RESULT FOR QUERY " + text + " NOT FOUND");
            return itemDtoSet;
        }
        storage.getAll().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .map(ItemMapper::toItemDto)
                .forEach(itemDtoSet::add);
        log.debug("GET REQUEST SUCCESSFUL - ITEM SEARCH RESULT FOR QUERY " + text + " FOUND");
        return itemDtoSet;
    }

    public void delete(long id) {
        if (!storage.contains(id)) {
            log.warn("DELETE REQUEST UNSUCCESSFUL - ITEM ID:" + id + " NOT FOUND");
            throw new ItemNotFound("NO ITEM ID:" + id + " FOUND");
        }
        log.debug("DELETE REQUEST SUCCESSFUL - ITEM ID:" + id + " DELETED");
        storage.delete(id);
    }
}
