package com;

import model.Pharm_Drug;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.multipart.FormDataParam;

import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Pharm_Drug")
public class Pharm_DrugService {

	Pharm_Drug drugObj = new Pharm_Drug();

	//////
	///////// Read DRUGS - Pharmacist ////////////////
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String readDrugs() {
		// return "Hello Shermi";
		return drugObj.readDrugs();
	}

	//////
	///////// Insert DRUGS - Pharmacist ////////////////
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertDrugs(@FormParam("drugName") String drugName, @FormParam("quantity") Integer quantity,
			@FormParam("strength") String strength, @FormParam("ExpireDate") String ExpireDate,
			@FormParam("UnitPrice") Double UnitPrice, @FormParam("typeID") Integer typeID,
			@FormParam("categoryID") Integer categoryID) {

		String output = drugObj.insertDrugs(drugName, quantity, strength, ExpireDate, UnitPrice, typeID, categoryID);
		return output;
	}

	//////
	///////// Update DRUGS - Pharmacist ////////////////
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDrugs(String drugData) {
		// Convert the input string to a JSON object
		JsonObject drugObject = new JsonParser().parse(drugData).getAsJsonObject();

		// Read the values from the JSON object
		Integer drugID = drugObject.get("drugID").getAsInt();
		String drugName = drugObject.get("drugName").getAsString();
		Integer quantity = drugObject.get("quantity").getAsInt();
		String strength = drugObject.get("strength").getAsString();
		String ExpireDate = drugObject.get("ExpireDate").getAsString();
		Double UnitPrice = drugObject.get("UnitPrice").getAsDouble();
		Integer typeID = drugObject.get("typeID").getAsInt();
		Integer categoryID = drugObject.get("categoryID").getAsInt();

		String output = drugObj.updateDrugs(drugID, drugName, quantity, strength, ExpireDate, UnitPrice, typeID,
				categoryID);
		return output;
	}

	//////
	///////// Delete DRUGS - Pharmacist ////////////////
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDrugs(String drugData) {
		JsonObject drugObject = new JsonParser().parse(drugData).getAsJsonObject();

		Integer drugID = drugObject.get("drugID").getAsInt();

		String output = drugObj.deleteDrugs(drugID);
		return output;
	}

}
