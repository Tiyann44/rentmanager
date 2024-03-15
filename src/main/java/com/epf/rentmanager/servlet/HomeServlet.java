package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int vehicleCount;
		VehicleService vehicleService = VehicleService.getInstance();
		int clientCount;
		ClientService clientService = ClientService.getInstance();
		int reservationCount;
		ReservationService reservationService = ReservationService.getInstance();

		try{
			vehicleCount = vehicleService.count();
			clientCount = clientService.count();
			reservationCount = reservationService.count();
		}
		catch(ServiceException e){
			throw new ServletException();
		}

		request.setAttribute("vehicleCount", vehicleCount);
		request.setAttribute("clientCount", clientCount);
		request.setAttribute("reservationCount", reservationCount);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
