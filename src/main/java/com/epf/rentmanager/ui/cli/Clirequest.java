package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

import static com.epf.rentmanager.utils.IOUtils.print;

public class Clirequest {

    public static void main(String[] args){
        menu();
    }

     public static void menu(){

         ClientService clientService = ClientService.getInstance();
         ReservationService reservationService = ReservationService.getInstance();
         VehicleService vehicleService = VehicleService.getInstance();


        print("Bienvenue. Veuillez entrer un nombre");
        print("1: Ajouter un client");
        print("2: Supprimer un client");
        print("3: Trouver un client");
        print("4: Afficher la liste des clients");
        print("5: Ajouter une réservation");
        print("6: Supprimer une réservation");
        print("7: Trouver une réservation par client");
        print("8: Trouver une réservation par véhicule");
        print("9: Afficher toutes les réservations");
        print("10: Ajouter un véhicule");
        print("11: Supprimer un véhicule");
        print("12: Trouver un véhicule");
        print("13: Afficher tous les véhicules");

         int choix = IOUtils.readInt("");
         long id;
         String nom;
         String prenom;
         String email;
         LocalDate dateNaissance;
         String constructeur;
         String modele;
         Long client_id;
         Long vehicle_id;
         LocalDate debut;
         LocalDate fin;
         Short nb_places;


        switch(choix){
            case 1:
                nom = IOUtils.readString("Nom", true);
                prenom = IOUtils.readString("Prénom", true);
                email = IOUtils.readString("email", true);
                dateNaissance = IOUtils.readDate("date de naissance", true);
                print("Création d'un nouveau client :");
                try{clientService.create(new Client(-1, nom, prenom, email,dateNaissance));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 2:
                print("Supression d'un client :");
                id = IOUtils.readLong("identifiant");
                try {clientService.delete(new Client(id,null,null,null,null));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 3:
                print("Recherche d'un client");
                id = IOUtils.readLong("identifiant");
                try {
                    clientService.findById(id);
                    print(clientService.findById(id).toString());
                }
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 4:
                print("Afficher tous les clients");
                try {clientService.findAll().forEach(element -> print(element.toString()));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 5:
                client_id = IOUtils.readLong("client_id");
                vehicle_id = IOUtils.readLong("vehicle_id");
                debut = IOUtils.readDate("debut", true);
                fin = IOUtils.readDate("fin", true);
                try{reservationService.create(new Reservation(-1l, client_id, vehicle_id, debut, fin));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 6:
                id = IOUtils.readLong("identifiant");
                try{reservationService.delete(new Reservation(id, null, null, null, null));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 7:
                id = IOUtils.readLong("identifiant");
                try{reservationService.findResaByClientId(id);
                    print(reservationService.findResaByClientId(id).toString());
                }
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 8:
                id = IOUtils.readLong("identifiant");
                try{reservationService.findResaByVehicleId(id);
                    print(reservationService.findResaByVehicleId(id).toString());
                }
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 9:
                try{reservationService.findAll().forEach(element -> print(element.toString()));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 10:
                constructeur = IOUtils.readString("constructeur", true);
                modele = IOUtils.readString("modele", true);
                nb_places = IOUtils.readShort("nb_places");
                try{vehicleService.create(new Vehicle(-1l, constructeur, modele, nb_places));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 11:
                id = IOUtils.readLong("identifiant");
                try{vehicleService.delete(new Vehicle(id, null, null, null));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 12:
                id = IOUtils.readLong("identifiant");
                try{vehicleService.findById(id);
                    print(vehicleService.findById(id).toString());
                }
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
            case 13:
                try{vehicleService.findAll().forEach(element -> print(element.toString()));}
                catch(ServiceException e){throw new RuntimeException(e);}
                break;
        }
    }
}
