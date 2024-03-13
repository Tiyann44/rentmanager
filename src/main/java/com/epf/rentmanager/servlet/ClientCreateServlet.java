package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        ClientService clientService = ClientService.getInstance();
        try{
            clientService.create(new Client(-1l, (request.getParameter("nom")), (request.getParameter("prenom")), request.getParameter("email"), LocalDate.parse(request.getParameter("naissance"))));
        }


        catch(ServiceException e){
            throw new ServletException();
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }
}


