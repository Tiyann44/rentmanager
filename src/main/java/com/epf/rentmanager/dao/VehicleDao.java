package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString (1, vehicle.constructeur());
			preparedStatement.setString (2, vehicle.modele());
			preparedStatement.setShort (3, vehicle.nb_places());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			int key=-1;
			if (resultSet.next()){
				key = resultSet.getInt(1);
			}return key;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicle.id());
			preparedStatement.executeUpdate();
			return 1l;
		}
		catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Optional<Vehicle> findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			if (resultSet.next()){
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				Short nb_places= resultSet.getShort("nb_places");
				Vehicle vehicule = new Vehicle(id, constructeur, modele, nb_places);
				return Optional.of(vehicule);
			}
		}
		catch (SQLException e) {
			throw new DaoException();
		}
		return Optional.empty();
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> vehicules = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()){
				Long id = resultSet.getLong("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				Short nb_places= resultSet.getShort("nb_places");
				Vehicle vehicule = new Vehicle(id, constructeur, modele, nb_places);
				vehicules.add(vehicule);
			}
		}
		catch (SQLException e) {
			throw new DaoException();
		}
		return vehicules;
	}

	public int count() throws DaoException {
		int i =0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()){
				i++;
			}}
			catch (SQLException e) {
				throw new DaoException();
			}
		return i;
	}
		
	}


