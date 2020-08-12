package com.RentACar.database.mapper;

import com.RentACar.database.model.Car;
import com.RentACar.database.model.Owner;
import com.RentACar.database.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    /* USER REGISTRATION */
    final String userRegistration = "INSERT INTO users(name, username, password, status, phone, login, task) VALUES(#{name}, #{username}, #{password}, #{status}, #{phone}, #{login}, #{task})";
    @Insert(userRegistration)
    @Options(useGeneratedKeys = true, keyProperty = "id_user")
    int userRegistration(User user);

    /* USER LOGIN */
    final String userLogin = "SELECT password FROM users WHERE username = #{username};";
    @Select(userLogin)
    @Results(value = {
            @Result(property = "password", column = "password")
    })
    String userLogin(String username);

    /* USER GET NAME */
    final String userGetName = "SELECT name FROM users WHERE username = #{username}";
    @Select(userGetName)
    @Results(value = {
            @Result(property = "name", column = "name")
    })
    String userGetName(String username);

    /* USER GET ACC STATUS */
    final String getAccStatus = "SELECT status FROM users WHERE username = #{username};";
    @Select(getAccStatus)
    @Results(value = {
            @Result(property = "status", column = "status")
    })
    String getAccStatus(User user);

    /* GET LOGIN STATUS */
    final String getLoginState = "SELECT login FROM users WHERE username = #{username};";
    @Select(getLoginState)
    @Results(value = {
            @Result(property = "login", column = "login")
    })
    int getLoginState(User user);

    /* UPDATE LOGIN STATUS */
    final String setLoginState = "UPDATE users SET login = #{login} WHERE username = #{username};";
    @Update(setLoginState)
    int setLoginState(User user);

    /* GET CAR LIST */
    final String searchCarByLocation = "SELECT * FROM cars WHERE location = #{location};";
    @Select(searchCarByLocation)
    @Results(value = {
            @Result(property = "id_car", column = "id_car"),
            @Result(property = "carBrand", column = "carBrand"),
            @Result(property = "carModel", column = "carModel"),
            @Result(property = "carPrice", column = "carPrice"),
            @Result(property = "location", column = "location"),
            @Result(property = "status", column = "status"),
            @Result(property = "id_owner", column = "id_owner")
    })
    List<Car> searchCarByLocation(String location);

}
