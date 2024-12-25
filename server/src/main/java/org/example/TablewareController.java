package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tableware")
public class TablewareController {
    @Autowired
    private TablewareService tablewareService;

    // Главная страница
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Страница просмотра всех записей
    @GetMapping("/view")
    public String viewTableware(Model model) {
        List<Tableware> tablewareList = tablewareService.getAll();
        model.addAttribute("tablewareList", tablewareList);
        return "view-tableware";
    }

    // Страница добавления новой записи
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("tableware", new Tableware());
        return "add-tableware";
    }

    @PostMapping("/add")
    public String addTableware(@ModelAttribute @Valid Tableware tableware, Model model) {
        // Проверка, существует ли запись с таким же именем и материалом
        if (tablewareService.isExistByNameAndMaterial(tableware.getName(), tableware.getMaterial())) {
            model.addAttribute("errorMessage", "Запись с таким названием и материалом уже существует.");
            System.out.println("Запись с таким названием и материалом уже существует.");
            return "add-tableware"; // Возвращаем на страницу добавления с ошибкой
        }

        tablewareService.add(tableware);
        return "redirect:/tableware/view";
    }

    // Страница редактирования записи
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Tableware tableware = tablewareService.getById(id);
        model.addAttribute("tableware", tableware);
        return "edit-tableware";
    }

    @PostMapping("/edit/{id}")
    public String updateTableware(@PathVariable int id, @ModelAttribute @Valid Tableware tableware, Model model) {
        Tableware existingTableware = tablewareService.getById(tableware.getId());

        // Проверяем, были ли изменения в данных
        if (existingTableware.getName().equals(tableware.getName()) &&
                existingTableware.getMaterial().equals(tableware.getMaterial()) &&
                existingTableware.getType().equals(tableware.getType()) &&
                existingTableware.getVolumeMl() == tableware.getVolumeMl() &&
                existingTableware.getPriceRub() == tableware.getPriceRub()) {

            model.addAttribute("errorMessage", "Нет изменений для сохранения.");
            System.out.println("Нет изменений для сохранения.");
            return "edit-tableware"; // Возвращаем на страницу редактирования с ошибкой
        }

        tablewareService.update(id, tableware); // Обновляем запись в базе данных
        return "redirect:/tableware/view"; // После успешного обновления, редирект на страницу всех записей
    }

    // Страница удаления записи
    @GetMapping("/delete/{id}")
    public String deleteTableware(@PathVariable int id) {
        tablewareService.delete(id);
        return "redirect:/tableware/view";
    }
    @GetMapping("/search")
    public String searchForm(Model model) {
        List<Tableware> tablewareList = tablewareService.getAll();
        model.addAttribute("tablewareList", tablewareList);
        return "search-form";  // Страница с формой поиска
    }
    @PostMapping("/search")
    public String searchTableware(@RequestParam String type, Model model) {
        List<Tableware> result = tablewareService.searchByType(type);
        model.addAttribute("tablewareList", result);
        return "search-form";
    }

}
