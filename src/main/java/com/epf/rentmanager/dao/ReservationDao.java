package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong (1, reservation.client_id());
			preparedStatement.setLong (2, reservation.vehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.debut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.fin()));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			int key=-1;
			if (resultSet.next()){
				key = resultSet.getInt(1);
			}return key;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, reservation.id());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			int key=-1;
			if (resultSet.next()){
				key = resultSet.getInt(1);
			}return key;
		}
		catch (SQLException e) {
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		ArrayList<Reservation> reservationsclient = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setLong(1, clientId);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()){
				Long id = resultSet.getLong("id");
				Long vehicle_id = resultSet.getLong("vehicle_id");
				LocalDate debut= resultSet.getDate("debut").toLocalDate();
				LocalDate fin= resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, clientId, vehicle_id, debut, fin);
				reservationsclient.add(reservation);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservationsclient;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		ArrayList<Reservation> reservationsvehicule = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicleId);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()){
				Long id = resultSet.getLong("id");
				Long client_id = resultSet.getLong("client_id");
				LocalDate debut= resultSet.getDate("debut").toLocalDate();
				LocalDate fin= resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id, vehicleId, debut, fin);
				reservationsvehicule.add(reservation);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservationsvehicule;
	}

	public List<Reservation> findAll() throws DaoException {
		ArrayList<Reservation> reservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()){
				Long id = resultSet.getLong("id");
				Long client_id = resultSet.getLong("client_id");
				Long vehicle_id = resultSet.getLong("vehicle_id");
				LocalDate debut= resultSet.getDate("debut").toLocalDate();
				LocalDate fin= resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id, vehicle_id, debut, fin);
				reservations.add(reservation);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}

	public int count() throws DaoException {
		int i =0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
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
