$(document).ready(function() {

	$("#alertSuccess").hide();
	$("#alertError").hide();

	// This helps to show Calendar/datepicker in all browsers.
	// Because some browsers won't show calendar/datepicker
	//Even in Eclipse
	// Also, Format date as YY-MM-DD
	webshims.setOptions('forms-ext', {
		types : 'date'
	});
	webshims.polyfill('forms forms-ext');
	$.webshims.formcfg = {
		en : {
			dFormat : '-',
			dateSigns : '-',
			patterns : {
				d : "yy-mm-dd"
			}
		}
	};

	// Expire date should not be an old date.
	// Therefore only allow from current date to select from calendar	
	var today=getTodayDate();
	document.getElementById("ExpireDate").setAttribute("min", today);

});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateDrugForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidDrugIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "Pharm_DrugsAPI",
		type : type,
		data : $("#formDrug").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onDrugSaveComplete(response.responseText, status);
		}
	});
});

function onDrugSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divDrugsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		} else if (status == "error") {
			$("#alertError").text("Error while saving...");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while saving...");
			$("#alertError").show();
		}

		$("hidDrugIDSave").val("");
		$("#formDrug")[0].reset();
	}
}

// DELETE ===============================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "Pharm_DrugsAPI",
		type : "DELETE",
		data : "drugID=" + $(this).data("drugid"),
		dataType : "text",
		complete : function(response, status) {
			onDrugDeleteComplete(response.responseText, status);
		}
	});
});

function onDrugDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divDrugsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// UPDATE==========================================
$(document)
		.on(
				"click",
				".btnUpdate",
				function(event) {
					$("#hidDrugIDSave").val(
							$(this).closest("tr").find('#hidDrugIDUpdate')
									.val());
					$("#drugName").val(
							$(this).closest("tr").find('td:eq(0)').text());
					$("#quantity").val(
							$(this).closest("tr").find('td:eq(1)').text());
					$("#strength").val(
							$(this).closest("tr").find('td:eq(2)').text());
					$("#ExpireDate").val(
							$(this).closest("tr").find('td:eq(3)').text());
					$("#UnitPrice").val(
							$(this).closest("tr").find('td:eq(4)').text());
					$("#typeName").val(
							$(this).closest("tr").find('td:eq(5)').text());
					$("#categoryName").val(
							$(this).closest("tr").find('td:eq(6)').text());
				});

// CLIENTMODEL=========================================================================
function validateDrugForm() {
	////////////
	// Drug Name
	if ($("#drugName").val().trim() == "") {
		return "Insert Drug Name.";
	}

	////////////
	// Quantity-----------------------------
	if ($("#quantity").val().trim() == "") {
		return "Insert Drug Quantity.";
	}
	if ($("#quantity").val().trim() <= 0) {
		return "Drug Quantity shouldn't be zero or less.";
	}
	// is numerical value
	var tmpQuantity = $("#quantity").val().trim();
	if (!$.isNumeric(tmpQuantity)) {
		return "Insert a numerical value for Drug Quantity.";
	}
	// ----------------------------------------

	////////////
	// Strength
	if ($("#strength").val().trim() == "") {
		return "Insert Drug Strength.";
	}
	
	////////////
	// Expire Date
	if ($("#ExpireDate").val().trim() == "") {
		return "Insert Expire Date.";
	}
	
	//Alert - when trying to update with an old expire date
	var today=getTodayDate();	
	if(($("#ExpireDate").val().trim()) < today) {
		return "Expire Date is already passed.";
	}

	////////////
	// Unit PRICE-------------------------------
	if ($("#UnitPrice").val().trim() == "") {
		return "Insert Unit Price.";
	}
	// is numerical value
	var tmpPrice = $("#UnitPrice").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Unit Price.";
	}
	// convert to decimal price
	$("#UnitPrice").val(parseFloat(tmpPrice).toFixed(2));
	// -----------------------------------------

	////////////
	// Type Name
	if ($("#typeName").val() == "0") {
		return "Select Type Name.";
	}
	
	////////////
	// Category Name
	if ($("#categoryName").val() == "0") {
		return "Select Category Name.";
	}
	return true;
}

function getTodayDate(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1; // Month count starts from 0 to 11
	var yyyy = today.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	return today = yyyy + '-' + mm + '-' + dd;	
}