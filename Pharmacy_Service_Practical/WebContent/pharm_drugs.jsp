<%@ page import="model.Pharm_Drug"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<style>
div#heading {
	padding: 15px;
	text-align: center;
	background: #f0f5f5;
}

div#fieldMargin {
	margin: 10px;
}

div#leftdiv {
	width: 50%;
	float: left;
}

div#rightdiv {
	width: 50%;
	float: right;
}

div#normaldiv {
	margin: 10px;
}

th {
	background: #f0f5f5;
}
</style>

<title>Pharmacy Service</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/pharm_drugs.js"></script>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="//cdn.jsdelivr.net/webshim/1.14.5/polyfiller.js"></script>


</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-9">
				<div id="heading">
					<h1>
						<b>Drug Store</b>
					</h1>
					<br>
				</div>

				<form id="formDrug" name="formDrug" method="post"
					action="pharm_drugs.jsp">
					<div id="normaldiv">
						Drug Name: <input id="drugName" name="drugName" type="text"
							class="form-control form-control-sm">
					</div>

					<div>
						<div id="leftdiv">
							<div id="fieldMargin" class="centered">
								Available Quantity: <input id="quantity" name="quantity"
									type="text" class="form-control form-control-sm">
							</div>
						</div>

						<div id="rightdiv">
							<div id="fieldMargin" class="centered">
								Strength: <input id="strength" name="strength" type="text"
									class="form-control form-control-sm">
							</div>
						</div>
					</div>

					<div>
						<div id="leftdiv">
							<div id="fieldMargin" class="centered">
								Expire Date: <input id="ExpireDate" name="ExpireDate"
									type="date" min=" " class="form-control form-control-sm">
								<br>
							</div>
						</div>

						<div id="rightdiv">
							<div id="fieldMargin" class="centered">
								Unit Price: <input id="UnitPrice" name="UnitPrice" type="text"
									class="form-control form-control-sm"> <br>
							</div>
						</div>
					</div>

					<br>
					<div id="normaldiv">
						Type Name: <select id="typeName" name="typeName" type="text"
							class="form-control form-control-sm">
							<option value="0">--Select Type--</option>
							<option value="OTC">OTC</option>
							<option value="POM">POM</option>
						</select> <br> Category Name: <select id="categoryName"
							name="categoryName" type="text"
							class="form-control form-control-sm">
							<option value="0">--Select Category--</option>
							<option value="Tablet">Tablet</option>
							<option value="Liquid">Liquid</option>
							<option value="Capsule">Capsule</option>
							<option value="Topical_Medicine">Topical_Medicine</option>
							<option value="Drops">Drops</option>
							<option value="Inhaler">Inhaler</option>
							<option value="Injection">Injection</option>
						</select>
					</div>
					<br> <input id="btnSave" name="btnSave" type="button"
						value="Save" class="btn btn-primary"> <input type="hidden"
						id="hidDrugIDSave" name="hidDrugIDSave" value="">
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