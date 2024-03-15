package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString (1, client.nom());
			preparedStatement.setString (2, client.prenom());
			preparedStatement.setString (3, client.email());
			preparedStatement.setDate(4, Date.valueOf(client.naissance()));
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
	
	public long delete(Client client) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setLong(1, client.id());
			preparedStatement.executeUpdate();
			return 1l;
	}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public Optional<Client> findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			if (resultSet.next()){
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				Client client = new Client(id, nom, prenom, email, naissance);
				return Optional.of(client);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return Optional.empty();
	}

	public List<Client> findAll() throws DaoException {
		ArrayList<Client> clients = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()){
				Long id = resultSet.getLong("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				Client client = new Client(id, nom, prenom, email, naissance);
				clients.add(client);
			}
		}
		catch (SQLException e) {
			throw new DaoException();
		}
		return clients;
	}

	public int count() throws DaoException {
		int i =0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);
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
