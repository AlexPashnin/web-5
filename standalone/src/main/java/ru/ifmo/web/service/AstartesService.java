package ru.ifmo.web.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.web.database.dao.AstartesDAO;
import ru.ifmo.web.database.entity.Astartes;
import ru.ifmo.web.standalone.App;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Data
@Slf4j
@Path("/astartes")
@Produces({MediaType.APPLICATION_JSON})
public class AstartesService {
    private AstartesDAO astartesDAO;

    public AstartesService() throws IOException {
        log.info("Creating service");
        InputStream dsPropsStream = App.class.getClassLoader().getResourceAsStream("datasource.properties");
        Properties dsProps = new Properties();
        dsProps.load(dsPropsStream);
        HikariConfig hikariConfig = new HikariConfig(dsProps);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        this.astartesDAO = new AstartesDAO(dataSource);
    }

    @GET
    @Path("/all")
    public List<Astartes> findAll() throws SQLException {
        return astartesDAO.findAll();
    }

    @GET
    @Path("/filter")
    public List<Astartes> findWithFilters(@QueryParam("id") Long id, @QueryParam("name") String name,
                                          @QueryParam("title") String title, @QueryParam("position") String position,
                                          @QueryParam("planet") String planet, @QueryParam("birthdate") String birthdate) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(birthdate);
        } catch (ParseException e) {
            date = null;
        }
        return astartesDAO.findWithFilters(id, name, title, position, planet, date);
    }

    @PUT
    @Path("/update")
    public String update(@QueryParam("id") Long id, @QueryParam("name") String name,
                         @QueryParam("title") String title, @QueryParam("position") String position,
                         @QueryParam("planet") String planet, @QueryParam("birthdate") String birthdate) throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(birthdate);
        } catch (ParseException e) {
            date = null;
        }
        return astartesDAO.update(id, name, title, position, planet, date) + "";
    }

    @DELETE
    @Path("/delete")
    public String delete(@QueryParam("id") Long id) throws SQLException {
        return astartesDAO.delete(id) + "";
    }

    @POST
    @Path("/create")
    public String create(@QueryParam("name") String name,
                         @QueryParam("title") String title, @QueryParam("position") String position,
                         @QueryParam("planet") String planet, @QueryParam("birthdate") String birthdate) throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(birthdate);
        } catch (ParseException e) {
            date = null;
        }
        return astartesDAO.create(name, title, position, planet, date) + "";
    }
}
