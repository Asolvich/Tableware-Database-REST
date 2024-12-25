package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Запускает клиентскую часть приложения для кофемашин
 */
@SpringBootApplication
public class RestApplication {
    private RestTemplate restTemplate;

    /**
     * Точка входа в приложение
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    /**
     * Шаблон для работы с REST API
     * @return REST шаблон
     */
    @Bean
    public RestTemplate restTemplate() {
        this.restTemplate = new RestTemplate();
        return this.restTemplate;
    }

    /**
     * Получает все кофемашины от сервера
     */
    public void createGetRequest(){
        String url = "http://localhost:8090/api/tableware";
        ResponseEntity<Tableware[]> response = restTemplate.getForEntity(url, Tableware[].class);
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response headers:");
        System.out.println(response.getHeaders());
        // Получаем массив объектов Tableware из ответа
        Tableware[] tablewares = response.getBody();
        System.out.println("Response body:");
        // Печатаем каждую кофемашину
        if (tablewares != null) {
            for (Tableware thing : tablewares) {
                System.out.println(thing);
            }
        }
    }

    /**
     * Получает кофемашину от сервера по индексу
     * @param id индекс запрашиваемой кофемашины
     */
    public void createGetByIDRequest(Integer id){
        String url = "http://localhost:8090/api/tableware/" + id;
        try {
            ResponseEntity<Tableware> response = restTemplate.getForEntity(url, Tableware.class);
            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Response headers:");
            System.out.println(response.getHeaders());
            System.out.println("Response body:");
            System.out.println(response.getBody());
        }
        catch (HttpClientErrorException e) {
            System.out.println("Response Code: " + e.getStatusCode());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("Tableware with id " + id + " not found");
            } else {
                throw e;  // rethrow the exception if it's not a 404 error
            }
        }
    }

    /**
     * Отправляет посуду на сервер
     * @param tabelware новая посуда
     */
    public void createPostRequest(Tableware tabelware){
        String url = "http://localhost:8090/api/tableware";
        ObjectMapper mapper = new ObjectMapper();
        try{
            String coffeeMachineJson = mapper.writeValueAsString(tabelware);
            // Устанавливаем заголовки
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Создаем новый HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(coffeeMachineJson, headers);
            // Отправляем запрос
            ResponseEntity<Tableware> response = restTemplate.postForEntity(url, entity, Tableware.class);
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response headers:");
            System.out.println(response.getHeaders());
            System.out.println("Response Body: " + response.getBody());
        }
        catch (JsonProcessingException e){
            System.out.println("Impossible to process JSON file");
        }
    }

    /**
     * Отправляет модифицированную кофемашину на сервер
     * @param id индекс заменяемой кофемашины
     * @param coffeeMachine изменённая кофемашина
     */
    public void createPutRequest(Integer id, Tableware coffeeMachine){
        String url = "http://localhost:8090/api/tableware/" + id;
        ObjectMapper mapper = new ObjectMapper();
        try{
            String coffeeMachineJson = mapper.writeValueAsString(coffeeMachine);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Создаем новый HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(coffeeMachineJson, headers);
            try{
                restTemplate.put(url, entity);
                System.out.println("Successfully edited an object\n" + coffeeMachine);
            }
            catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    System.out.println("Tableware with id " + id + " not found");
                } else {
                    throw e;  // rethrow the exception if it's not a 404 error
                }
            }
        }
        catch(JsonProcessingException e){
            System.out.println("Impossible to process JSON file");
        }
    }

    /**
     * Запрашивает удаление кофемашины на сервере
     * @param id индекс удаляемой кофемашины
     */
    public void createDeleteRequest(Integer id){
        try {
            restTemplate.delete("http://localhost:8090/api/tableware/" + id);
            System.out.println("Tableware with id " + id + " deleted successfully");
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("Tableware with id " + id + " not found");
                System.out.println(e);
            } else {
                throw e;
            }
        }
    }

    /**
     * Запускает работу приложения в командной строке
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("\n------------------------------ Getting all coffee machines -----------------------------");
            createGetRequest();
            System.out.println("\n--------------------------- Getting coffee machine by ID ---------------------------");
            createGetByIDRequest(1);
            System.out.println("\n--------------------------- Posting new coffee machine ---------------------------");
            Tableware newThing = new Tableware("Мандарин", "Стекло", "Стакан", 100, 800);
            createPostRequest(newThing);
            System.out.println("\n--------------------------- Editing coffee machine by ID ---------------------------");
            Tableware thing = new Tableware("c", "c", "c", 2000, 300);
            thing.setId(1);
            createPutRequest(1, thing);
            System.out.println("\n--------------------------- Deleting coffee machine by ID ---------------------------");
            createDeleteRequest(11);
        };
    }
}
