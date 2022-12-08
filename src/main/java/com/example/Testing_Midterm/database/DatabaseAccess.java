package com.example.Testing_Midterm.database;


import com.example.Testing_Midterm.beans.SiteUser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {

    private NamedParameterJdbcTemplate jdbc;

    public DatabaseAccess(NamedParameterJdbcTemplate jdbc) {this.jdbc=jdbc;}


    public List<SiteUser> getUsers(){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM users";

        BeanPropertyRowMapper<SiteUser> userMapper =
                new BeanPropertyRowMapper<>(SiteUser.class);

        List<SiteUser> users = jdbc.query(query, namedParameters, userMapper);
        return users;
        }

    public SiteUser getUser(SiteUser user){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM users WHERE name = :name and password = :password";
        parameters
                .addValue("name", user.getName())
                .addValue("password", user.getPassword());
        BeanPropertyRowMapper<SiteUser> mapper = new BeanPropertyRowMapper<>(SiteUser.class);

        try{
            user=jdbc.queryForObject(query, parameters, mapper);
        } catch (EmptyResultDataAccessException ex){
            return null;
        } return user;
    }

    public int addUser(SiteUser user){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "INSERT INTO users (name, password, phone) VALUES (:name, :password, :phone)";

        namedParameters
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("phone", user.getPhone());

        int returnValue = jdbc.update(query, namedParameters);
        return returnValue;
    }
    }




