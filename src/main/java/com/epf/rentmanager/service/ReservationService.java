package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

public class ReservationService {

    private ReservationDao ReservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.ReservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }


    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer un véhicule
        try {
            return ReservationDao.create(reservation);
        }
        catch(DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        // TODO: créer un véhicule
        try {
            return ReservationDao.delete(reservation);
        }
        catch(DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation>findResaByClientId(long id) throws ServiceException {
        // TODO: récupérer un véhicule par son id
        try {
            return ReservationDao.findResaByClientId(id);
        }
        catch(DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }
    public List<Reservation>findResaByVehicleId(long id) throws ServiceException {
        // TODO: récupérer un véhicule par son id
        try {
            return ReservationDao.findResaByVehicleId(id);
        }
        catch(DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        // TODO: récupérer tous les clients
        try {
            return ReservationDao.findAll();
        }
        catch(DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public int count() throws ServiceException {
        // TODO: créer un véhicule
        try {
            return ReservationDao.count();
        }
        catch(DaoException e) {
            throw new ServiceException();
        }
    }
    private boolean isUndefined(String str){
        return (str == null && str.trim().isEmpty());
    }

}

