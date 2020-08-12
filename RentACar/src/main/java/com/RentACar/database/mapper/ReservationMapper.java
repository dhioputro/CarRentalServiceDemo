package com.RentACar.database.mapper;

import com.RentACar.database.model.*;
import com.RentACar.payment.Bank;
import com.RentACar.payment.EWallet;
import org.apache.ibatis.annotations.*;

public interface ReservationMapper {

    /* MAKE RESERVATION */
    final String reserveACar = "INSERT INTO trans(id_user, id_car, name_user, carBrand, carModel, location, carPrice, start_date, end_date, totalPrice, payment, status)"+
            " VALUES(#{id_user}, #{id_car}, #{name_user}, #{carBrand}, #{carModel}, #{location}, #{carPrice}, #{start_date}, #{end_date}, #{totalPrice}, #{payment}, #{status})";
    @Insert(reserveACar)
    @Options(useGeneratedKeys = true, keyProperty = "id_trans")
    void reserveCar(Reservation reserve);

    /* GET RESERVATION by ID transaction */
    final String getReserveCar = "SELECT * FROM trans WHERE id_trans=#{id_trans}";
    @Select(getReserveCar)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "id_car", column = "id_car"),
            @Result(property = "carBrand", column = "carBrand"),
            @Result(property = "carModel", column = "carModel"),
            @Result(property = "location", column = "location"),
            @Result(property = "carPrice", column = "carPrice"),
            @Result(property = "start_date", column = "start_date"),
            @Result(property = "end_date", column = "end_date"),
            @Result(property = "totalPrice", column = "totalPrice"),
            @Result(property = "payment", column = "payment"),
            @Result(property = "status", column = "status")
    })
    Reservation getReserveCar(Reservation reservation);

    /* GET EWALLET BALANCE */
    final String getBal = "SELECT balance from ewallet WHERE account= #{account}";
    @Select(getBal)
    @Results(value = {
            @Result(property = "balance", column = "balance")
    })
    double getBal(EWallet eWallet);

    /* UPDATE EWALLET BALANCE */
    final String updateBal = "UPDATE ewallet SET balance=#{balance} WHERE account=#{account}";
    @Update(updateBal)
    int updateBal(EWallet eWallet);

    /* INSERT TRANSACTION TO DB*/
    final String addTransToBank= "INSERT INTO bank(va, payStatus, id_trans ) VALUES(#{va}, #{payStatus}, #{id_trans})";
    @Insert(addTransToBank)
    @Options(useGeneratedKeys = true, keyProperty = "ID")
    void addTransToBank(Bank bank);

    /* GET PAYMENT STATUS FROM BANK */
    final String getPayStatus = "SELECT payStatus from bank WHERE id_trans= #{id_trans}";
    @Select(getPayStatus)
    @Results(value = {
            @Result(property = "payStatus", column = "payStatus")
    })
    String getPayStatus(Bank bank);

    /* UPDATE PAYMENT STATUS IN MP TRANS */
    final String updateTransInMp= "UPDATE trans SET status=#{status} WHERE id_trans=#{id_trans}";
    @Update(updateTransInMp)
    void updateTransInMp(Reservation reservation);

    /* UPDATE CAR STATUS IN MP CAR */
    final String updateCarStatus= "UPDATE cars SET status=#{status} WHERE id_car=#{id_car}";
    @Update(updateCarStatus)
    void updateCarStatus(Car car);

}
