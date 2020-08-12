package com.RentACar.database.mapper;

import com.RentACar.database.model.Car;
import com.RentACar.database.model.Owner;

import org.apache.ibatis.annotations.*;



public interface OwnerMapper {

    /* OWNER REGISTRATION*/
    final String ownerRegistration = "INSERT INTO owners(name, username, password, status, login, task) VALUES(#{name}, #{username}, #{password}, #{status}, #{login}, #{task})";
    @Insert(ownerRegistration)
    @Options(useGeneratedKeys = true, keyProperty = "id_owner")
    int ownerRegistration(Owner owner);

    /* OWNER LOGIN */
    final String ownerLogin = "SELECT password FROM owners WHERE username = #{username}";
    @Select(ownerLogin)
    @Results(value = {
            @Result(property = "password", column = "password")
    })
    String ownerLogin(String username);

    /* OWNER GET NAME */
    final String ownerGetName = "SELECT name FROM owners WHERE username = #{username}";
    @Select(ownerGetName)
    @Results(value = {
            @Result(property = "name", column = "name")
    })
    String ownerGetName(String username);

    /* OWNER GET ACC STATUS */
    final String getAccStatus = "SELECT status from owners WHERE username = #{username}";
    @Select(getAccStatus)
    @Results(value = {
            @Result(property = "status", column = "status")
    })
    String getAccStatus(Owner owner);

    /* GET LOGIN STATE */
    final String getLoginState = "SELECT login from owners WHERE username = #{username}";
    @Select(getLoginState)
    @Results(value = {
            @Result(property = "login", column = "login")
    })
    int getLoginState(Owner owner);

    /* UPDATE LOGIN STATUS */
    final String setLoginState = "UPDATE owners SET login = #{login} WHERE username = #{username}";
    @Update(setLoginState)
    int setLoginState(Owner owner);

    /* ADD NEW CAR TO DB */
    final String addCar = "INSERT INTO cars(carBrand, carModel, location, carPrice, status, id_owner) VALUES(#{carBrand}, #{carModel}, #{location}, #{carPrice}, #{status}, #{id_owner})";
    @Insert(addCar)
    @Options(useGeneratedKeys = true, keyProperty = "id_car")
    int addCar(Car car);

    /* UPDATE EXISTING CAR */
    final String updateCar = "UPDATE cars SET carBrand=#{carBrand}, carModel=#{carModel}, location=#{location}, carPrice=#{carPrice} WHERE id_car=#{id_car}";
    @Update(updateCar)
    int updateCar(Car car);

    /* DELETE EXISTING CAR */
    final String deleteCar = "DELETE FROM cars WHERE id_car=#{id_car}";
    @Delete(deleteCar)
    int deleteCar(Car car);

}

//    /* owner login recap */
//    final String ownerLoginRecap = "SELECT id_owner, name, status FROM owners WHERE username = #{username}";
//    @Select(ownerLoginRecap)
//    @Results(value = {
//            @Result(property = "id_owner", column = "id_owner"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "status", column = "status"),
//    })
//    Owner ownerLoginRecap(String username);