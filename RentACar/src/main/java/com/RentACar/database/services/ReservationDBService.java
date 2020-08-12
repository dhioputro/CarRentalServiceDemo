package com.RentACar.database.services;

import com.RentACar.database.mapper.ReservationMapper;
import com.RentACar.database.mapper.UserMapper;
import com.RentACar.payment.Bank;
import com.RentACar.payment.EWallet;
import com.RentACar.database.model.Reservation;
import com.RentACar.database.model.User;
import com.google.gson.Gson;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class ReservationDBService {
    UserMapper userMapper;
    ReservationMapper resMapper;
    SqlSession session;

    public void sqlSessionService() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            this.session = sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String userCarReserve(User user) {
        sqlSessionService();
        session.getConfiguration().addMapper(UserMapper.class);
        userMapper = session.getMapper(UserMapper.class);
        String reserveCar = "";
        Gson json = new Gson();

        if(userMapper.getLoginState(user) > 0 && userMapper.getAccStatus(user).equals("user")) {

            session.getConfiguration().addMapper(ReservationMapper.class);
            resMapper = session.getMapper(ReservationMapper.class);
            if(user.getReservation().getPayment().equals("e-wallet")) {

                /* GET VALIDATION FOR EWALLET PAYMENT*/
                EWallet ewallet = new EWallet(user.getPhone());
                ewallet.setBalance(resMapper.getBal(ewallet));

                double balance = ewallet.getBalance() - user.getReservation().getTotalPrice();

                if (balance < 0) {

                    session.commit();
                    session.close();
                    return "Sorry, your balance is not sufficient, please choose another payment";
                } else {

                    /* ADD RESERVATION TO TRANSACTION AND SET STATUS PAYMENT TO PAID*/
                    user.getReservation().setStatus("paid");
                    resMapper.reserveCar(user.getReservation());
                    session.commit();
                    session.close();
                    return json.toJson(user.getReservation());
                }

            } else if(user.getReservation().getPayment().equals("bank")) {

                /* ADD RESERVATION TO TRANSACTION */
                resMapper.reserveCar(user.getReservation());
                long id_trans = user.getReservation().getId_trans();
                String va = "008" +user.getPhone();

                /* ADD TRANSCATION VA TO BANK */
                Bank bank = new Bank(va, id_trans);
                resMapper.addTransToBank(bank);

                /* SET STATUS CAR TO NOT AVAILABLE IN MP */
                user.getCar().setStatus(0);
                resMapper.updateCarStatus(user.getCar());
                System.out.println(user.getCar().getStatus());
                System.out.println(user.getCar().getId_car());

                session.commit();
                session.close();
                return json.toJson(user.getReservation());
            }

        }

        session.commit();
        session.close();
        System.out.println("Value: " +reserveCar);
        return reserveCar;
    }

    public String getReservation(User user) {
        sqlSessionService();
        session.getConfiguration().addMapper(UserMapper.class);
        userMapper = session.getMapper(UserMapper.class);
        String reserveCar = "";
        Gson json = new Gson();
        Reservation reservation;

        if(userMapper.getLoginState(user) > 0 && userMapper.getAccStatus(user).equals("user")) {

            session.getConfiguration().addMapper(ReservationMapper.class);
            resMapper = session.getMapper(ReservationMapper.class);

            /* GET RESERVATION by ID TRANS */
            reservation = resMapper.getReserveCar(user.getReservation());
            System.out.println(user.getReservation().getId_trans());
            String payment = reservation.getPayment();

            /* CHECK PAYMENT STATUS*/
            Bank bank = new Bank();
            bank.setId_trans(reservation.getId_trans());
            System.out.println(payment);
            if(payment.equals("bank") && resMapper.getPayStatus(bank).equals("paid")) {

                /* IF BANK PAYMENT STATUS "PAID" SET ALSO STATUS PAYMENT "PAID" IN TRANSACTION, GET RESERVATION STATUS*/
                user.getReservation().setStatus("paid");
                resMapper.updateTransInMp(user.getReservation());
                System.out.println("bank");
                session.commit();
                session.close();
                return json.toJson(reservation);
            } else if(payment.equals("bank") && resMapper.getPayStatus(bank).equals("unpaid")) {

                session.commit();
                session.close();
                return json.toJson(reservation);
            } else {
                /* IF EWALLET ONLY GET RESERVATION STATUS */
                System.out.println("ewallet");
                session.commit();
                session.close();
                return json.toJson(reservation);
            }

        } else {
            session.commit();
            session.close();
            return reserveCar;
        }

    }
}
