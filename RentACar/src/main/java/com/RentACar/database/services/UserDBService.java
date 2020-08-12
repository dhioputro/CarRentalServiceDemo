package com.RentACar.database.services;

import com.RentACar.database.mapper.UserMapper;
import com.RentACar.database.model.Car;
import com.RentACar.database.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserDBService {
    UserMapper userMapper;
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

    public int userRegis(User user){
        sqlSessionService();
        session.getConfiguration().addMapper(UserMapper.class);
        userMapper = session.getMapper(UserMapper.class);
        int regis = 0;
        try{
            regis = userMapper.userRegistration(user);
            session.commit();
            session.close();
            return regis;
        } catch (Exception e) {
            return regis;
        }
    }

    public String userLogin(User user) {
        sqlSessionService();
        session.getConfiguration().addMapper(UserMapper.class);
        userMapper = session.getMapper(UserMapper.class);
        String getResult = "";

        /* identifikasi apakah login oleh user atau owner*/
        if(user.getStatus().equals(userMapper.getAccStatus(user))) {

            /* kalau tervalidasi sebagai user*/
            String mapPass = userMapper.userLogin(user.getUsername());
            System.out.println("password :" +user.getPassword());
            System.out.println("password db :" +mapPass);
            if(user.getPassword().equals(mapPass)) {
                userMapper.setLoginState(user);
                getResult = userMapper.userGetName(user.getUsername());
                session.commit();
                session.close();
                return "Hello " +getResult+ ", Welcome back!";
            } else {
                session.commit();
                session.close();
                return "[ERROR] Your password is incorrect";
            }
        } else {

            /* kalau tervalidasi sebagai owner*/
            session.commit();
            session.close();
            return getResult;
        }
    }

    public int userLogout(User user) {
        sqlSessionService();
        session.getConfiguration().addMapper(UserMapper.class);
        userMapper = session.getMapper(UserMapper.class);
        int confirmLogout = 0;

        if(userMapper.getLoginState(user) > 0) {
            confirmLogout = userMapper.setLoginState(user);
            session.commit();
            session.close();
            return confirmLogout;
        } else {
            session.commit();
            session.close();
            return confirmLogout;
        }
    }

    /* User application features*/

    /* browse car by its location */
    public List<Car> userSearchByLocation(User user) {
        sqlSessionService();
        session.getConfiguration().addMapper(UserMapper.class);
        userMapper = session.getMapper(UserMapper.class);

        if(userMapper.getLoginState(user) > 0 && userMapper.getAccStatus(user).equals("user")) {

            List<Car> carList;
            carList = userMapper.searchCarByLocation(user.getCar().getLocation().toLowerCase());
            if (carList.isEmpty()) {

                session.commit();
                session.close();
                return carList;
            } else {

                session.commit();
                session.close();
                return carList;
            }
        } else {

            session.commit();
            session.close();
            return null;
        }

    }

}
