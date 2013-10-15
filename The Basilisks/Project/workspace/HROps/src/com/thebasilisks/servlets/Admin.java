package com.thebasilisks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thebasilisks.Application;
import com.thebasilisks.DBConnection;
import com.thebasilisks.Department;
import com.thebasilisks.Employee;
import com.thebasilisks.Position;
import com.thebasilisks.Qualification;

/**
 * Servlet implementation class Admin
 */
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request,response);
	
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		int taskid=Integer.parseInt(request.getParameter("taskid"));
		Connection con=DBConnection.getConnection();
		PreparedStatement st;
		String AllRoles[]={"ADMIN","INTERVIEWER","HR","MANAGER"};  //Keeps Track Of All the Roles
		
		
		if (taskid==100){
			System.out.println("100");
			int appid=Integer.parseInt(request.getParameter("appid"));
			System.out.println(appid);
			Application app=Application.getApplication(appid);
			String oppid=app.getOpportunityID();
			int deptid=-1;
			String deptdesc="";
			try {
				st = con.prepareStatement("SELECT DEPT_ID,DEPT_NAME from HROPS_SCHEMA.DEPARTMENT where DEPT_ID=(SELECT DEPARTMENT_ID from HROPS_SCHEMA.JOB_OPPORTUNITY WHERE OPPORTUNITY_ID=?)");
			
			st.setString(1, oppid);
			ResultSet resultset=st.executeQuery();
			while(resultset.next()){
				deptid=resultset.getInt(1);
				deptdesc=resultset.getString(2);
		     }
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(deptdesc+"<input type='hidden' id='dept_id' name='dept_id' value="+deptid+">");
		}
		else if(taskid==600){
			int deptid=Integer.parseInt(request.getParameter("dept_id"));
			int empid;
			String nameofEmp;
			//con=DBConnection.getConnection();
			int depthead=Department.getDepartment(deptid).getDepartmentHead();
			try {
				st = con.prepareStatement("SELECT EMPLOYEE_ID,NAME from HROPS_SCHEMA.EMPLOYEE where DEPT_ID=?");
				st.setInt(1, deptid);
				ResultSet resultset=st.executeQuery();
				if(depthead == 0)
					out.println("<option value=\"\" selected=\"selected\"></option>");
				while(resultset.next())
				{
					empid=resultset.getInt(1);
					nameofEmp=resultset.getString(2);
					
					if(depthead == empid)
						out.println("<option value=\""+empid+"\" selected=\"selected\">"+nameofEmp+"("+empid+")"+"</option>");
					else
						out.println("<option value=\""+empid+"\">"+nameofEmp+"("+empid+")"+"</option>");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(taskid==1){
			String emp_name;
			int emp_sal;
			String sex;
			int qual_id;
			int dept_id;
			int pos_id;
			int emp_id=0;
			int supervisor_id;
			int emp_Role=0;
			try{
				emp_name=request.getParameter("emp_name");
				emp_sal=Integer.parseInt(request.getParameter("emp_sal"));
				sex=request.getParameter("sex").toUpperCase();
				emp_id=Integer.parseInt(request.getParameter("emp_id"));
				qual_id=Integer.parseInt(request.getParameter("select_qual"));
				dept_id=Integer.parseInt(request.getParameter("select_dept"));
				pos_id=Integer.parseInt(request.getParameter("select_position"));
				supervisor_id=Integer.parseInt(request.getParameter("select_supervisor"));
			    emp_Role=Integer.parseInt(request.getParameter("emp_role"));
				//validation for name
				Pattern pat1=Pattern.compile("[a-zA-Z ]+");
				Matcher matl=pat1.matcher(emp_name);
				if(matl.matches())
				{
					//String sql="INSERT INTO HROPS_SCHEMA.EMPLOYEE(POSITION_ID) VALUES(?,?,?,?,?,?,?,?,?)";
					String sql="UPDATE HROPS_SCHEMA.EMPLOYEE SET POSITION_ID=?,SUPERVISOR=?,SALARY=?,DEPT_ID=?,QUAL_ID=?,GENDER=?,NAME=?,ROLE=? WHERE EMPLOYEE_ID=?";			
					PreparedStatement statement = con.prepareStatement(sql);
					statement.setInt(1, pos_id);
					if (supervisor_id!=-1)
					    statement.setInt(2, supervisor_id);
					else
						statement.setNull(2, Types.INTEGER);
					statement.setInt(3, emp_sal);
					statement.setInt(4, dept_id);
					statement.setInt(5, qual_id);
					statement.setString(6, sex);
					statement.setString(7, emp_name);
					if(emp_Role!=-1)
					  statement.setString(8, AllRoles[emp_Role]);
					else
						statement.setNull(8,Types.INTEGER);
					statement.setInt(9, emp_id);
					statement.executeUpdate();
					out.print(true);
				}
				else
				{
					out.println(false);
				}
			}
			catch(SQLException e){
				out.print(false);
			}
			catch(Exception e)
			{
				out.print(false);
			}
				
		}
		else if(taskid==2)
		{
			try{
			System.out.println("2");
			String dept_name=request.getParameter("deptname");
			String sql="INSERT INTO HROPS_SCHEMA.DEPARTMENT(DEPT_NAME,DEPT_HEAD) VALUES(?,NULL)";
			try {
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setString(1,dept_name);
				statement.executeUpdate();
				/*PreparedStatement statement1 = con.prepareStatement(sql1);
				statement1.setInt(1,3);
				statement1.setInt(2,emp_id);
				statement1.executeUpdate();*/
				}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			}
			catch(Exception e){
				e.printStackTrace();
			}
			out.print("SUCESS");
			System.out.print("Dept Added");
		}
		else if(taskid==3)
		{
			System.out.println("3");
			String qual_desc=request.getParameter("qualname");
			String sql="INSERT INTO HROPS_SCHEMA.QUALIFICATION(QUAL_DESC) VALUES(?)";
			try {
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setString(1,qual_desc);
				statement.executeUpdate();
				out.print(true);
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.print(false);
			}
			 
		}
		else if(taskid==4)
		{
			try{
			String pos_name=request.getParameter("posname");
			String sql="INSERT INTO HROPS_SCHEMA.POSITION(POS_DESC) VALUES(?)";
			try {
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setString(1,pos_name);
				statement.executeUpdate();
				//System.out.print(x);
				out.print(true);
			}
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.print(false);
			}
			}
			catch(Exception e){
				e.printStackTrace();
				out.print(false);
			}
			
			return;
		}
		else if(taskid==5)
		{
			try{
			
			String opp_id=request.getParameter("oppid");
			int dept_id=Integer.parseInt(request.getParameter("dept"));
			int pos_id=Integer.parseInt(request.getParameter("pos"));
			int vacancy=Integer.parseInt(request.getParameter("vacancy"));
			String con_dt=request.getParameter("yyyy")+"-"+request.getParameter("mm")+"-"+request.getParameter("dd");
			Date lastDate = Date.valueOf(con_dt);
			String sql="INSERT INTO HROPS_SCHEMA.JOB_OPPORTUNITY(OPPORTUNITY_ID,DEPARTMENT_ID,POSITION_ID,NO_OF_VACANCIES,LAST_DATE) VALUES(?,?,?,?,?)";
			try {
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setString(1,opp_id);
				statement.setInt(2,dept_id);
				statement.setInt(3,pos_id);
				statement.setInt(4,vacancy);
				statement.setDate(5, lastDate);
				statement.executeUpdate();
				out.print(true);
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				out.print(false);
				e.printStackTrace();
			}
			}
			catch(Exception e){
				out.print(false);
				e.printStackTrace();
			}
			return;
		}
		else if(taskid==6){
			
			int deptid=Integer.parseInt(request.getParameter("select_dept_id"));
			int depthead=Integer.parseInt(request.getParameter("depthead"));
			String sql="UPDATE HROPS_SCHEMA.DEPARTMENT SET DEPT_HEAD=? where DEPT_ID=?";
			try{
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, depthead);
			statement.setInt(2, deptid);
			statement.executeUpdate();
			out.print(true);
			}
			catch(Exception e){
				out.print(false);
				e.printStackTrace();
			}
			}

		else if(taskid==200){
			System.out.println("200");
			int appid=Integer.parseInt(request.getParameter("appid"));
			System.out.println(appid);
			Application app=Application.getApplication(appid);
			String oppid=app.getOpportunityID();
			int posid=-1;
			String posdesc="";
			try {
				st = con.prepareStatement("SELECT POSITION_ID,POS_DESC from HROPS_SCHEMA.POSITION where POSITION_ID=(SELECT POSITION_ID from HROPS_SCHEMA.JOB_OPPORTUNITY WHERE OPPORTUNITY_ID=?)");
			
			st.setString(1, oppid);
			ResultSet resultset=st.executeQuery();
			while(resultset.next()){
				posid=resultset.getInt(1);
				posdesc=resultset.getString(2);
		     }
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(posdesc+"<input type='hidden' id='pos_id' name='pos_id' value="+posid+">");
		}
		else if(taskid==404){
			out.print("<select name=accepted id=accepted onchange=gendetails()>");
			out.print("<option selected>Choose</option>");
			//ArrayList acceptedapp=Application.getApplicationByStatus(APPLICATION.OFFER_ACCEPTED);
			ArrayList acceptedapp=Application.getApplicationsByStatus(10);
			Iterator iter=acceptedapp.iterator();
			Application app;
			while(iter.hasNext()){
				app=(Application)iter.next();
				out.println("<option value="+app.getApplicationID()+">"+"Application id:"+app.getApplicationID()+"</option>");
			}
			
			out.print("</select>&nbsp;&nbsp;");
		}
		else if(taskid==300){
			int empid=Integer.parseInt(request.getParameter("empId"));
			Employee emp=Employee.getEmployee(empid);
			//start creating form
			out.print("<form name=frm_update id=frm_update>");
			//display Employee Name
			out.print("<div id=name_caption name=name_caption>Name</div><div id=name_value name=name_value><input type=text name=emp_name id=emp_name value=\""+((emp.getName()!=null)?emp.getName():"")+"\"></div><br>");
			//store empid in hidden field
			out.print("<input type=hidden name=emp_id id=emp_id value="+empid+">");
			//Display Employee Gender
			if(emp.getGender()!=null){
			if(emp.getGender().equals("M")){
			out.print("<div id=gender_caption name=Gender_caption>Gender</div><div id=gender_value name=gender_value><input type=radio name=sex value=m checked>Male <input type=radio name=sex value=f>Female</div><br>");    
			}
			else{
				out.print("<div id=gender_caption name=Gender_caption>gender</div><div id=gender_value name=gender_value><input type=radio name=sex value=m>Male <input type=radio name=sex value=f checked>Female</div><br>");
			}
		}
			else{
				out.print("<div id=gender_caption name=Gender_caption>gender</div><div id=gender_value name=gender_value><input type=radio name=sex value=m>Male <input type=radio name=sex value=f>Female</div><br>");
			}
			//Show Employee Role
			out.print("<div id=role_caption name=role_caption>Employee Role</div><div id=role_value name=role_value><select name=emp_role id=emp_role>");
			out.print("<option value=-1></option>");
			if(emp.getRole()!=null){
			for(int i=0;i<AllRoles.length;i++){
				   if(AllRoles[i].equals(emp.getRole().toUpperCase())){
					out.print("<option value="+i+" selected=selected>"+AllRoles[i]+"</option>");
				}
				else{
					out.print("<option value="+i+">"+AllRoles[i]+"</option>");
				}
			}
			}
			else{
				for(int i=0;i<AllRoles.length;i++){
					out.print("<option value="+i+">"+AllRoles[i]+"</option>");
				}
			}
			out.print("</select><br>");
			
			//Display Employee salary
			out.print("<div id=salary_caption name=salary_caption>Salary</div><div id=salary_value name=salary_value><input type=text name=emp_sal id=emp_sal value="+((emp.getSalary()!=0)?emp.getSalary():"0")+"></div><br>");
			//Display employee position   
			int curr_pos_id=emp.getPositionId();
			Iterator iter;
			Position pos=new Position();
			HashMap map=pos.getAllPosition();
			iter=map.entrySet().iterator();
			out.print("<label for=\"select_position\">Position : </label><select name=select_position id=select_name>");
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry)iter.next();
				if(curr_pos_id==(Integer)pairs.getKey() && curr_pos_id!=0)
				    out.println("<option value="+pairs.getKey()+" selected=selected>"+pairs.getValue()+"</option>");
				else
					out.println("<option value="+pairs.getKey()+">"+pairs.getValue()+"</option>");
			}
			out.print("</select><br>");
			//Get all Supervisor
			int curr_supervisor=emp.getSupervisor();
			out.print("<label for=\"select_position\">Supervisor : </label><select name=select_supervisor id=select_supervisor>");
			out.println("<option value=-1></option>");
			Employee Allemp=new Employee();
			map=Allemp.getAllEmp();
			iter=map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry)iter.next();
				if(((Integer)pairs.getKey())==emp.getEmployeeID())
					continue;
				if(curr_supervisor==(Integer)pairs.getKey() && curr_supervisor!=0)
				   out.println("<option value="+pairs.getKey()+" selected=selected>"+pairs.getValue()+"</option>");
				else
					out.println("<option value="+pairs.getKey()+">"+pairs.getValue()+"</option>");
			}
			out.print("</select><br>");
			//Show all departments
			int curr_dept=emp.getDeptId();
			out.print("<label for=\"select_position\">Department : </label><select name=select_dept id=select_dept>");
			Department dept=new Department();
			map=dept.getDept();
			iter=map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry)iter.next();
				if(curr_dept==(Integer)pairs.getKey() && curr_dept!=0)
				   out.println("<option value="+pairs.getKey()+" selected=selected>"+pairs.getValue()+"</option>");
				else
					out.println("<option value="+pairs.getKey()+">"+pairs.getValue()+"</option>");
			}
			out.print("</select><br>");
			//show All Qualification
			int curr_qual=emp.getQualId();
			out.print("<label for=\"select_position\">Qualification : </label><select name=select_qual id=select_qual>");
			Qualification qual=new Qualification();
			map=qual.getQual();
			iter=map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry)iter.next();
				if(curr_qual==(Integer)pairs.getKey() && curr_qual!=0)
				   out.println("<option value="+pairs.getKey()+" selected=selected>"+pairs.getValue()+"</option>");
				else
					out.println("<option value="+pairs.getKey()+">"+pairs.getValue()+"</option>");
			}
			out.print("</select><br>");
			out.print("<input type=button value=Update onclick=submitForm()><br>");
			out.print("</form>");
		}
}
}