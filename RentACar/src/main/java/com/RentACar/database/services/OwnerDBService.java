package com.RentACar.database.services;

import com.RentACar.database.mapper.OwnerMapper;
import com.RentACar.database.model.Car;
import com.RentACar.database.model.Owner;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class OwnerDBService {
    OwnerMapper ownerMapper;
    SqlSession session;

    public void sqlSessionService()  {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            this.session = sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int ownerRegisServ(Owner owner){
        sqlSessionService();
        session.getConfiguration().addMapper(OwnerMapper.class);
        ownerMapper = session.getMapper(OwnerMapper.class);

        int regis = ownerMapper.ownerRegistration(owner);
        session.commit();
        session.close();
        return regis;
    }

    public String ownerLoginServ(Owner owner) {
        sqlSessionService();
        session.getConfiguration().addMapper(OwnerMapper.class);
        ownerMapper = session.getMapper(OwnerMapper.class);
        String getResult = "";

        /* identifikasi apakah login oleh user atau owner*/
        if(owner.getStatus().equals(ownerMapper.getAccStatus(owner))) {

            /* kalau tervalidasi sebagai owner*/
            String mapPass = ownerMapper.ownerLogin(owner.getUsername());
            System.out.println("password :" +owner.getPassword());
            System.out.println("password db :" +mapPass);
            if(owner.getPassword().equals(mapPass)) {
                ownerMapper.setLoginState(owner);
                getResult = ownerMapper.ownerGetName(owner.getUsername());
                session.commit();
                session.close();
                return "Hello " +getResult+ ", Welcome back!";
            } else {
                session.commit();
                session.close();
                return "[ERROR] Your password is incorrect";
            }
        } else {

            /* kalau tervalidasi sebagai user*/
            session.commit();
            session.close();
            return getResult;
        }
    }

    public int ownerLogoutServ(Owner owner) {
        sqlSessionService();
        session.getConfiguration().addMapper(OwnerMapper.class);
        ownerMapper = session.getMapper(OwnerMapper.class);
        int confirmLogout = 0;

        if(ownerMapper.getLoginState(owner) > 0) {
            confirmLogout = ownerMapper.setLoginState(owner);
            session.commit();
            session.close();
            return confirmLogout;
        } else {
            session.commit();
            session.close();
            return confirmLogout;
        }
    }

    /* Owner application features*/

    public int addCarServ(Owner owner) {
        sqlSessionService();
        session.getConfiguration().addMapper(OwnerMapper.class);
        ownerMapper = session.getMapper(OwnerMapper.class);
        int ownerAddCar = 0;

        if(ownerMapper.getLoginState(owner) > 0 && ownerMapper.getAccStatus(owner).equals("owner")){
            ownerAddCar = ownerMapper.addCar(owner.getCar());

            session.commit();
            session.close();
            return ownerAddCar;
        } else {
            session.commit();
            session.close();
            return ownerAddCar;
        }
    }

    public int updateCarServ(Owner owner) {
        sqlSessionService();
        session.getConfiguration().addMapper(OwnerMapper.class);
        ownerMapper = session.getMapper(OwnerMapper.class);
        int ownerUptCar = 0;

        if(ownerMapper.getLoginState(owner) > 0 && ownerMapper.getAccStatus(owner).equals("owner")){
            ownerUptCar = ownerMapper.updateCar(owner.getCar());

            session.commit();
            session.close();
            return ownerUptCar;
        } else {
            session.commit();
            session.close();
            return ownerUptCar;
        }
    }

    public int deleteCarServ(Owner owner) {
        sqlSessionService();
        session.getConfiguration().addMapper(OwnerMapper.class);
        ownerMapper = session.getMapper(OwnerMapper.class);
        int ownerDelCar = 0;

        if(ownerMapper.getLoginState(owner) > 0 && ownerMapper.getAccStatus(owner).equals("owner")){
            ownerDelCar = ownerMapper.deleteCar(owner.getCar());

            session.commit();
            session.close();
            return ownerDelCar;
        } else {
            session.commit();
            session.close();
            return ownerDelCar;
        }
    }

}
