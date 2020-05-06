package model;

import java.sql.*;
import org.json.*;

public class Pharm_Drug {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pharmacy_service_db", "root", "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/////////////// Insert New Drugs - Pharmacist ////////////////
	public String insertDrugs(String name, String quantity, String strength, String expiredate, String unitprice,
			String typename, String categoryname) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			String query = " insert into `drugs`(`drugID`, `drugName`,`quantity`,`strength`,`ExpireDate`,`UnitPrice`,`typeName`,`categoryName`)"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, Integer.parseInt(quantity));			
			preparedStmt.setString(4, strength);			
			preparedStmt.setDate(5, java.sql.Date.valueOf(expiredate));
			preparedStmt.setDouble(6, Double.parseDouble(unitprice));			
			preparedStmt.setString(7, typename);
			preparedStmt.setString(8, categoryname);

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newDrugs = readDrugs();
			output = "{\"status\":\"success\", \"data\": \"" + newDrugs + "\"}";
		}

		catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the drug.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	/////////////// Read Drug Details - Pharmacist ////////////////
	public String readDrugs() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			output = "<table border='1'><tr><th>Drug Name</th><th>Available Quantity</th><th>Strength</th><th>Expire Date</th><th>Unit Price</th><th>Type Name</th><th>Category Name</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from `drugs` order by `drugID`";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String drugID = Integer.toString(rs.getInt("drugID"));
				String drugName = rs.getString("drugName");
				String quantity = Integer.toString(rs.getInt("quantity"));
				String strength = rs.getString("strength");
				String ExpireDate = rs.getString("ExpireDate");
				String UnitPrice = Double.toString(rs.getDouble("UnitPrice"));
				String typeName = rs.getString("typeName");
				String categoryName = rs.getString("categoryName");

				output += "<tr><td><input id='hidDrugIDUpdate' name='hidDrugIDUpdate' type='hidden' value='" + drugID + "'>" + drugName + "</td>";		
				output += "<td>" + quantity + "</td>";
				output += "<td>" + strength + "</td>";
				output += "<td>" + ExpireDate + "</td>";
				output += "<td>" + UnitPrice + "</td>";
				output += "<td>" + typeName + "</td>";
				output += "<td>" + categoryName + "</td>";
				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-drugid='" + drugID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the drug details!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	/////////////// Update Drug Details - Pharmacist ////////////////
	public String updateDrugs(String ID, String name, String quantity, String strength, String expiredate,
			String unitprice, String typename, String categoryname) {
		
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			String query = "UPDATE `drugs` SET `drugName`=?,`quantity`=?,`strength`=?,`ExpireDate`=?, `UnitPrice`=?, `typeName`=?, `categoryName`=? WHERE `drugID`=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setInt(2, Integer.parseInt(quantity));
			preparedStmt.setString(3, strength);
			preparedStmt.setDate(4, java.sql.Date.valueOf(expiredate));			
			preparedStmt.setDouble(5, Double.parseDouble(unitprice));			
			preparedStmt.setString(6, typename);
			preparedStmt.setString(7, categoryname);
			preparedStmt.setInt(8, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newDrugs = readDrugs();
			output = "{\"status\":\"success\", \"data\": \"" + newDrugs + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the drug.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	/////////////// Delete Drug Details - Pharmacist ////////////////
	public String deleteDrugs(String ID) {
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from `drugs` where `drugID`=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newDrugs = readDrugs();
			output = "{\"status\":\"success\", \"data\": \"" + newDrugs + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the drug.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
