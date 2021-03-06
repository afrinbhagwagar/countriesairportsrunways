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
							var row = table.insertRow(-1);
							var srno = row.insertCell(-1);
							srno.innerHTML = (k+1);
							row.appendChild(srno);
							var cell0 = row.insertCell(-1);
							cell0.innerHTML = itr[k];
							row.appendChild(cell0);
							var cell1 = row.insertCell(-1);
							cell1.innerHTML = obj[itr[k]].countryName;
							row.appendChild(cell1);
							var cell2 = row.insertCell(-1);
							cell2.innerHTML = obj[itr[k]].totalNoAirports;
							row.appendChild(cell2);
					}
					
				}, function(reason) {
					console.log("Error occured when get service called to fetch top ten countries having maximum airport");
				})
			}, function(reason) {
				console.log("Error occured when calling top ten service.");
			});
			
		} ]);