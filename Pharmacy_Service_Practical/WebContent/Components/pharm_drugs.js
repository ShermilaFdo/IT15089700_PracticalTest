$(document).ready(function() {

	$("#alertSuccess").hide();
	$("#alertError").hide();
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
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onItemSaveComplete(response, status) {
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
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status) {
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
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidDrugIDSave").val(
					$(this).closest("tr").find('#hidDrugIDUpdate').val());
			$("#drugName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#quantity").val($(this).closest("tr").find('td:eq(1)').text());
			$("#strength").val($(this).closest("tr").find('td:eq(2)').text());
			$("#ExpireDate").val($(this).closest("tr").find('td:eq(3)').text());
			$("#UnitPrice").val($(this).closest("tr").find('td:eq(4)').text());
			$("#typeName").val($(this).closest("tr").find('td:eq(5)').text());			
			$("#categoryName").val($(this).closest("tr").find('td:eq(6)').text());
		});

// CLIENTMODEL=========================================================================
function validateDrugForm() {
	// Drug Name
	if ($("#drugName").val().trim() == "") {
		return "Insert Drug Name.";
	}
	
	// Quantity-----------------------------
	if ($("#quantity").val().trim() == "") {
		return "Insert Drug Quantity.";
	}
	// is numerical value
	var tmpQuantity = $("#quantity").val().trim();
	if (!$.isNumeric(tmpQuantity)) {
		return "Insert a numerical value for Drug Quantity.";
	}
	
	// Strength
	if ($("#strength").val().trim() == "") {
		return "Insert Drug Strength.";
	}
	// Expire Date
	if ($("#ExpireDate").val().trim() == "") {
		return "Insert Expire Date.";
	}	
	
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
	
	// Type Name
	if ($("#typeName").val() == "0") {
		return "Select Type Name.";
	}
	
	// Category Name
	if ($("#categoryName").val() == "0") {
		return "Select Category Name.";
	}
	return true;
}