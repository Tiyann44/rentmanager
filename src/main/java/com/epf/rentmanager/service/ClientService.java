package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		if (isUndefined(client.prenom()) || isUndefined(client.nom())) throw new ServiceException();
		Client upperCasedClient = new Client(client.id(), client.nom().toUpperCase(), client.prenom(), client.email(), client.naissance());
		try {return clientDao.create(upperCasedClient);}
		catch(DaoException e) {throw new ServiceException();}
	}

	public long delete(Client client) throws ServiceException {
		// TODO: créer un client
		try {
			return clientDao.delete(client);
		}
		catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public Optional<Client> findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		try {
			return clientDao.findById(id);
		}
		catch(DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		}
		catch(DaoException e) {
			throw new ServiceException();
		}
	}

	private boolean isUndefined(String str){
		return (str == null && str.trim().isEmpty());
	}
}
