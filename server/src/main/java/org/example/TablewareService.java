package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с сущностью Tableware.
 * Служит промежуточным слоем между контроллером и DAO.
 */
@Service
public class TablewareService {
    private final TablewareRepository repository;
    @Autowired
    private TablewareDAO dao;

    public TablewareService(TablewareRepository repository) {
        this.repository = repository;
    }

    /**
     * Получение всех записей из таблицы посуды.
     *
     * @return Список всех объектов Tableware.
     */
    public List<Tableware> getAll() {
        return dao.getAll();
    }

    /**
     * Добавление новой записи о посуде.
     *
     * @param tableware Объект посуды, который будет добавлен в базу.
     */
    @Transactional
    public void add(Tableware tableware) {dao.add(tableware);}

    /**
     * Обновление записи посуды по ID.
     *
     * @param id        Идентификатор посуды, которую нужно обновить.
     * @param tableware Обновленный объект посуды.
     */
    @Transactional
    public void update(int id, Tableware tableware) {
        dao.update(id, tableware);
    }

    /**
     * Удаление записи посуды по ID.
     *
     * @param id Идентификатор посуды, которую нужно удалить.
     */
    public void delete(int id) {
        dao.delete(id);
    }

    /**
     * Проверка существует ли посуда по ID
     *
     * @param id Идентификатор посуды, которую нужно проверить.
     */
    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    /**
     * Поиск посуды по материалу.
     *
     * @param type тип для поиска.
     * @return Список посуды с указанным типом.
     */
    public List<Tableware> searchByType(String type) {
        return dao.searchByType(type);
    }
    /**
     * Поиск посуды по id.
     *
     * @param id id для поиска.
     * @return Список посуды с указанным id.
     */
    public Tableware getById(int id){return dao.getById(id);}

    public boolean isExistByNameAndMaterial(String name, String material) {return dao.isExistByNameAndMaterial(name, material);}

}
