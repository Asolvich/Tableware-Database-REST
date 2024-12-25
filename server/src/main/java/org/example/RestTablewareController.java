package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tableware") // Базовый URL для всех запросов
public class RestTablewareController {
    private final TablewareService tablewareService;
    private static final Logger logger = LoggerFactory.getLogger(RestTablewareController.class);

    @Autowired
    public RestTablewareController(TablewareService tablewareService) {
        this.tablewareService = tablewareService;
    }

    /**
     * Получение всех записей о посуде.
     *
     * @return Список всех объектов Tableware.
     */
    // GET - Получить все записи
    @GetMapping
    public List<Tableware> getAll() {
        return tablewareService.getAll();
    }


    /**
     * Получение записи о посуде по ID.
     *
     * @param id Идентификатор посуды.
     * @return Объект Tableware.
     */
    // GET - Получить одну запись по ID
    @GetMapping("/{id}")
    public ResponseEntity<Tableware> getById(@PathVariable("id") int id) {
        Optional<Tableware> tableware = Optional.ofNullable(tablewareService.getById(id));
        return tableware.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Добавление новой записи о посуде.
     *
     * @param tableware Объект посуды.
     * @return Ответ с созданным объектом.
     */
    // POST - Добавить новую запись
    @PostMapping
    public ResponseEntity<Tableware> add(@RequestBody Tableware tableware) {
        tablewareService.add(tableware);
        return ResponseEntity.ok(tableware);
    }

    /**
     * Обновление записи посуды по ID.
     *
     * @param id        Идентификатор посуды, которую нужно обновить.
     * @param tableware Обновленный объект посуды.
     * @return Ответ с обновленным объектом.
     */
    // PUT - Обновить запись
    @PutMapping("/{id}")
    public ResponseEntity<Tableware> update(@PathVariable("id") int id, @RequestBody Tableware tableware) {
        tablewareService.update(id, tableware);
        return ResponseEntity.ok(tableware);
    }

    /**
     * Удаление записи посуды по ID.
     *
     * @param id Идентификатор посуды, которую нужно удалить.
     * @return Ответ с результатом удаления.
     */
    // DELETE - Удалить запись
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableware(@PathVariable("id") int id) {
        System.out.println("Attempting to delete tableware with id: " + id);
        if (tablewareService.existsById(id)) {
            tablewareService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.error("Attempted to delete non-existing coffee machine with id {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Поиск посуды по материалу.
     *
     * @param type Тип для поиска.
     * @return Список посуды с указанным типом.
     */
    @GetMapping("/search")
    public List<Tableware> searchByType(@RequestParam("type") String type) {
        return tablewareService.searchByType(type);
    }

    /**
     * Проверка существования посуды с заданным именем и материалом.
     *
     * @param name     Имя посуды.
     * @param material Материал посуды.
     * @return true, если посуда существует, иначе false.
     */
    @PostMapping("/search")
    public boolean isExistByNameAndMaterial(@RequestParam("name") String name, @RequestParam("material") String material) {
        return tablewareService.isExistByNameAndMaterial(name, material);
    }

}
