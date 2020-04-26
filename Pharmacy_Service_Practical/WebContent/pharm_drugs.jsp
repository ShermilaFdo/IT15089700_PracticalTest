<%@ page import="model.Pharm_Drug"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Pharmacy Service</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/pharm_drugs.js"></script>

</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>Drug Store</h1>

				<form id="formDrug" name="formDrug" method="post" action="pharm_drugs.jsp">
					Drug Name: <input id="drugName" name="drugName" type="text" class="form-control form-control-sm">
					<br>
					Drug Quantity: <input id="quantity" name="quantity" type="text" class="form-control form-control-sm">
					<br>
					Strength: <input id="strength" name="strength" type="text" class="form-control form-control-sm">
					<br>
					Expire Date: <input id="ExpireDate" name="ExpireDate" type="text" class="form-control form-control-sm">
					<br>
					<br>
					Unit Price: <input id="UnitPrice" name="UnitPrice" type="text" class="form-control form-control-sm">
					<br>
					<br>
					Type Name: <input id="typeName" name="typeName" type="text" class="form-control form-control-sm">
					<br>
					<br>
					Category Name: <input id="categoryName" name="categoryName" type="text" class="form-control form-control-sm">
					<br>
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
					<input type="hidden" id="hidDrugIDSave" name="hidDrugIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divDrugsGrid">
					<%
					Pharm_Drug drugObj = new Pharm_Drug();
					out.print(drugObj.readDrugs());
					%>
				</div>

			</div>
		</div>
	</div>


</body>
</html>