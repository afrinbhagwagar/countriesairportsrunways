'use strict'
angular.module('accenture.services', []).factory('accentureservice', ["$http", "CONSTANTS", function($http, CONSTANTS) {
    var service = {};
    service.getTopTenCountries = function() {
        return $http.get(CONSTANTS.getTopTenCountries);
    }
    service.getRunwaysByCountry = function(countryName) {
    	var url = CONSTANTS.getRunwaysByCountry + "?country=" +countryName;
        return $http.get(url);
    }
    
    
    return service;
}]);