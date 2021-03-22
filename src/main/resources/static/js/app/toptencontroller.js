'use strict'
var module = angular.module('topten.controllers', []);
module.controller("toptencontroller", [ "$scope", "commonservice",
		function($scope, commonservice) {
			commonservice.getTopTenCountries().then(function() {
				commonservice.getTopTenCountries().then(function(value) {
					$scope.topTenCountries = value.data;
					var obj = value.data;
					document.getElementById('dynamicTopTenTable').style.display = "table-cell";

					var table = document.getElementById('dynamicTopTenTable');

					var itr = Object.keys(obj);
					for (var k = 0; k < itr.length; k++) {
						for (var i = 1; i < obj[itr[k]].length; i++) {
							var row = table.insertRow(-1);

							var cell0 = row.insertCell(-1);
							cell0.innerHTML = itr[k];
							row.appendChild(cell0);
							var cell1 = row.insertCell(-1);
							cell1.innerHTML = obj[itr[k]][i].airportIdentity;
							row.appendChild(cell1);
							var cell2 = row.insertCell(-1);
							cell2.innerHTML = obj[itr[k]][i].airportType;
							row.appendChild(cell2);
							var cell3 = row.insertCell(-1);
							cell3.innerHTML = obj[itr[k]][i].airportName;
							row.appendChild(cell3);
						}
					}
					
				}, function(reason) {
					console.log("Error occured when get service called to fetch top ten countries having maximum airport");
				})
			}, function(reason) {
				console.log("Error occured when calling top ten service.");
			});
			
		} ]);