package org.example;

/**
 * Класс Tableware представляет сущность "Посуда" в приложении.
 * Хранит информацию о посуде, включая название, материал, тип, объем и цену.
 */


public class Tableware {
    private int id; // Уникальный идентификатор записи в базе данных
    private String name;
    private String material;
    private String type;
    private int volumeMl;
    private int priceRub;


    // Конструктор по умолчанию
    public Tableware() {}

    /**
     * Конструктор с параметрами для создания объекта Tableware.
     *
     * @param name      Название посуды.
     * @param material  Материал посуды.
     * @param type      Тип посуды.
     * @param volumeMl  Объем посуды в миллилитрах.
     * @param priceRub  Цена посуды в рублях.
     */
    public Tableware(String name, String material, String type, Integer volumeMl, Integer priceRub) {
        this.name = name;
        this.material = material;
        this.type = type;
        this.volumeMl = volumeMl;
        this.priceRub = priceRub;
    }

    /**
     * Получить уникальный идентификатор записи.
     *
     * @return id записи
     */
    public int getId() {return id;}

    /**
     * Установить уникальный идентификатор записи.
     *
     * @param id уникальный идентификатор
     */
    public void setId(int id) {this.id = id;}

    /**
     * Получить название посуды.
     *
     * @return название посуды
     */
    public String getName() {return name;}

    /**
     * Установить название посуды.
     *
     * @param name название посуды
     */
    public void setName(String name) {this.name = name;}

    /**
     * Получить материал посуды.
     *
     * @return материал посуды
     */
    public String getMaterial() {return material;}

    /**
     * Установить материал посуды.
     *
     * @param material материал посуды
     */
    public void setMaterial(String material) {this.material = material;}

    /**
     * Получить тип посуды.
     *
     * @return тип посуды
     */
    public String getType() {return type;}

    /**
     * Установить тип посуды.
     *
     * @param type тип посуды
     */
    public void setType(String type) {this.type = type;}

    /**
     * Получить объем посуды в миллилитрах.
     *
     * @return объем посуды
     */
    public Integer getVolumeMl() {return volumeMl;}

    /**
     * Установить объем посуды в миллилитрах.
     *
     * @param volumeMl объем посуды
     */
    public void setVolumeMl(Integer volumeMl) {this.volumeMl = volumeMl;}

    /**
     * Получить цену посуды в рублях.
     *
     * @return цена посуды
     */
    public Integer getPriceRub() {return priceRub;}

    /**
     * Установить цену посуды в рублях.
     *
     * @param priceRub цена посуды
     */
    public void setPriceRub(Integer priceRub) {this.priceRub = priceRub;}

    /**
     * Переопределение метода toString для удобного отображения объекта.
     *
     * @return строковое представление объекта Tableware
     */
    @Override
    public String toString() {
        return "Tableware{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", material = '" + material + '\'' +
                ", type = '" + type + '\'' +
                ", volumeMl = " + volumeMl +
                ", priceRub = " + priceRub +
                '}';
    }
}
