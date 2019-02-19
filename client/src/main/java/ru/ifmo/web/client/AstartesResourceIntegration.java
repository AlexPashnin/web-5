package ru.ifmo.web.client;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.web.database.entity.Astartes;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.MediaType;

@Slf4j
public class AstartesResourceIntegration {
    private final String findAllUrl = "http://localhost:8080/astartes/all";
    private final String filterUrl = "http://localhost:8080/astartes/filter";
    private final String updateUrl = "http://localhost:8080/astartes/update";
    private final String deleteUrl = "http://localhost:8080/astartes/delete";
    private final String createUrl = "http://localhost:8080/astartes/create";
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public List<Astartes> findAll() {
        Client client = Client.create();
        WebResource webResource = client.resource(findAllUrl);
        ClientResponse response =
                webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Astartes>> type = new GenericType<List<Astartes>>() {
        };
        return response.getEntity(type);
    }

    public List<Astartes> findWithFilters(Long id, String name, String title, String position, String planet, Date birthdate) {
        Client client = Client.create();
        WebResource webResource = client.resource(filterUrl);
        if (id != null) {
            webResource = webResource.queryParam("id", id + "");
        }
        if (name != null) {
            webResource = webResource.queryParam("name", name);
        }
        if (title != null) {
            webResource = webResource.queryParam("title", title);
        }
        if (position != null) {
            webResource = webResource.queryParam("position", position);
        }
        if (planet != null) {
            webResource = webResource.queryParam("planet", planet);
        }
        if (birthdate != null) {
            webResource = webResource.queryParam("birthdate", sdf.format(birthdate));
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Astartes>> type = new GenericType<List<Astartes>>() {
        };
        return response.getEntity(type);
    }

    public Long create(String name, String title, String position, String planet, Date birthdate) {
        Client client = Client.create();
        WebResource webResource = client.resource(createUrl);
        if (name != null) {
            webResource = webResource.queryParam("name", name);
        }
        if (title != null) {
            webResource = webResource.queryParam("title", title);
        }
        if (position != null) {
            webResource = webResource.queryParam("position", position);
        }
        if (planet != null) {
            webResource = webResource.queryParam("planet", planet);
        }
        if (birthdate != null) {
            webResource = webResource.queryParam("birthdate", sdf.format(birthdate));
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Long.parseLong(response.getEntity(type));
    }

    public int update(Long id, String name, String title, String position, String planet, Date birthdate) {
        Client client = Client.create();
        WebResource webResource = client.resource(updateUrl);
        if (id != null) {
            webResource = webResource.queryParam("id", id + "");
        }
        if (name != null) {
            webResource = webResource.queryParam("name", name);
        }
        if (title != null) {
            webResource = webResource.queryParam("title", title);
        }
        if (position != null) {
            webResource = webResource.queryParam("position", position);
        }
        if (planet != null) {
            webResource = webResource.queryParam("planet", planet);
        }
        if (birthdate != null) {
            webResource = webResource.queryParam("birthdate", sdf.format(birthdate));
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Integer.parseInt(response.getEntity(type));
    }

    public int delete(Long id) {
        Client client = Client.create();
        WebResource webResource = client.resource(deleteUrl);
        if (id != null) {
            webResource = webResource.queryParam("id", id + "");
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Integer.parseInt(response.getEntity(type));
    }
}
