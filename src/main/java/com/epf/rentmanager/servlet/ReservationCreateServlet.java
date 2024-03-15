package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        ReservationService reservationService = ReservationService.getInstance();
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(request.getParameter("debut"), formatter);
            LocalDate fin = LocalDate.parse(request.getParameter("fin"), formatter);

            reservationService.create(new Reservation(-1l, Long.parseLong(request.getParameter("client_id")), Long.parseLong(request.getParameter("vehicle_id")), debut, fin));
        }


        catch(ServiceException e){
            throw new ServletException();
        }
        response.sendRedirect(request.getContextPath() + "/rents");
    }
}


