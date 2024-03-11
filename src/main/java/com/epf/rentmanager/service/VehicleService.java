package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		if(isUndefined(vehicle.constructeur()))throw new ServiceException();
		try {
			return vehicleDao.create(vehicle);
		}
		catch(DaoException e) {
			throw new ServiceException();
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		try {
			return vehicleDao.delete(vehicle);
		}
		catch(DaoException e) {
			throw new ServiceException();
		}
	}

	public Optional<Vehicle> findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		try {
			return vehicleDao.findById(id);
		}
		catch(DaoException e) {
			throw new ServiceException();
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		List<Vehicle> vehicules = new ArrayList<>();
		try {
			vehicules = vehicleDao.findAll();
		}
		catch(DaoException e) {
			throw new ServiceException();
		}
		return vehicules;
	}
	private boolean isUndefined(String str){
		return (str == null && str.trim().isEmpty());
	}
	
}
