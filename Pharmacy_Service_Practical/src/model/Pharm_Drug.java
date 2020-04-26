package model;

import java.sql.*;
import org.json.*;

public class Pharm_Drug {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pharmacy_db", "root", "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/////////////// Insert New Drugs - Pharmacist ////////////////
	public String insertDrugs(String name, Integer quantity, String strength, String expiredate, Double unitprice,
			Integer typeid, Integer categoryid) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			String query = " insert into drugs(`drugID`, `drugName`,`quantity`,`strength`,`ExpireDate`,`UnitPrice`,`typeID`,`categoryID`)"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, quantity);
			preparedStmt.setString(4, strength);
			preparedStmt.setDate(5, java.sql.Date.valueOf(expiredate));
			preparedStmt.setDouble(6, unitprice);
			preparedStmt.setInt(7, typeid);
			preparedStmt.setInt(8, categoryid);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Successfully Inserted Drug Details!";
		}

		catch (Exception e) {
			output = "Error while inserting the drug details!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	/////////////// Read Drug Details - Pharmacist ////////////////
	public String readDrugs() {
		String output = "";
		JSONObject json = new JSONObject();

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			String query = "select d.drugID, d.drugName, d.quantity, d.strength, d.ExpireDate, d.UnitPrice, t.typeName, c.categoryName from drugs d, type t, category c where t.typeID=d.typeID and c.categoryID=d.categoryID order by d.drugID";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			JSONArray array = new JSONArray();
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

				JSONObject drug = new JSONObject();

				drug.put("drugID", drugID);
				drug.put("drugName", drugName);
				drug.put("quantity", quantity);
				drug.put("strength", strength);
				drug.put("ExpireDate", ExpireDate);
				drug.put("UnitPrice", UnitPrice);
				drug.put("typeName", typeName);
				drug.put("categoryName", categoryName);
				array.put(drug);
			}

			json.put("List", array);
			con.close();

		} catch (Exception e) {
			output = "Error while reading the drug details!";
			System.err.println(e.getMessage());
		}
		output = json.toString();
		return output;
	}

	/////////////// Update Drug Details - Pharmacist ////////////////
	public String updateDrugs(Integer ID, String name, Integer quantity, String strength, String expiredate,
			Double unitprice, Integer typeid, Integer categoryid) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			String query = "UPDATE drugs SET drugName=?,quantity=?,strength=?,ExpireDate=?, UnitPrice=?, typeID=?, categoryID=? WHERE drugID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setInt(2, quantity);
			preparedStmt.setString(3, strength);
			preparedStmt.setDate(4, java.sql.Date.valueOf(expiredate));
			preparedStmt.setDouble(5, unitprice);
			preparedStmt.setInt(6, typeid);
			preparedStmt.setInt(7, categoryid);
			preparedStmt.setInt(8, ID);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";

		} catch (Exception e) {
			output = "Error while updating the drug details!";
			System.err.println(e.getMessage());
		}
		return output;
	}

	/////////////// Delete Drug Details - Pharmacist ////////////////
	public String deleteDrugs(Integer ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from drugs where drugID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, ID);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while deleting the drug.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
