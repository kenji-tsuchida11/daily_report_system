package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//プロパティ呼び出し
import models.Employee;
//DAO(データベースとやり取りするオブジェクト)呼び出し
import utils.DBUtil;


@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public EmployeesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try{
            //page情報取得しInteger型に変える
            page = Integer.parseInt(request.getParameter("page"));
        //?
        } catch(NumberFormatException e) { }

        //DBへ問合せしリスト形式で取得
        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        // employees_countをDBへ問合せしリスト形式で取得
        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                                       .getSingleResult();

        em.close();

        //DBから取得したemployeeをリクエストスコープにセット
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);

        //?
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        //ビューになるjsp指定、表示
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}