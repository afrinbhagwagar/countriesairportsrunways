'use strict'
var module = angular.module('runwaysbycountry.controllers', []);
module.controller("runwaysbycountrycontroller", [ "$scope", "commonservice",
		function($scope, commonservice) {
		console.log("Controller called for runways");
		
		$scope.getRunwaysByCountry = function() {
			commonservice.getRunwaysByCountry().then(function() {
				commonservice.getRunwaysByCountry().then(function(value) {
					$scope.runwaysByCountry = value.data;
					var obj = value.data;
					document.getElementById('dynamicRunwaysByCountry').style.display = "table-cell";

					var table = document.getElementById('dynamicRunwaysByCountry');
					for(var i = 1;i<table.rows.length;){
			            table.deleteRow(i);
			        }
					
					for (var k = 0; k < obj.length; k++) {
						var row = table.insertRow(-1);
						var cell0 = row.insertCell(-1);
						cell0.innerHTML = obj[k].countryCode;
						row.appendChild(cell0);
						var cell1 = row.insertCell(-1);
						cell1.innerHTML = obj[k].countryCode;
						row.appendChild(cell1);
						var cell2 = row.insertCell(-1);
						cell2.innerHTML = obj[k].airportIdentity;
						row.appendChild(cell2);
						var cell3 = row.insertCell(-1);
						cell3.innerHTML = obj[k].airportType;
						row.appendChild(cell3);
						var cell4 = row.insertCell(-1);
						cell4.innerHTML = obj[k].airportName;
						row.appendChild(cell4);
						var cell5 = row.insertCell(-1);
						cell5.innerHTML = obj[k].runwayId;
						row.appendChild(cell5);
						var cell6 = row.insertCell(-1);
						cell6.innerHTML = obj[k].runwaySurface;
						row.appendChild(cell6);
					}
				}, function(reason) {
					console.log("Error occured when getting all pits values function call.");
				});
			}, function(reason) {
				console.log("Error occured when updating pits called.");
			});
		}	
		
		
		
		
} ]);