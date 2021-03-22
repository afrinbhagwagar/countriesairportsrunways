'use strict'
angular.module('common.services', []).factory('commonservice', ["$http", "CONSTANTS", function($http, CONSTANTS) {
    var service = {};
    service.getTopTenCountries = function() {
        return $http.get(CONSTANTS.getTopTenCountries);
    }
    service.getRunwaysByCountry = function() {
    	var countryName = document.getElementById('countryEntered').value;
    	var url = CONSTANTS.getRunwaysByCountry + "?country=" +countryName;
        return $http.get(url);
    }
    
    return service;
}]);