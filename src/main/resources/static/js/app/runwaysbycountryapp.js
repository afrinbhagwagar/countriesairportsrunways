'use strict'
var runwaysByCountryApp = angular.module('runwaysbycountryindex', [ 'ui.bootstrap',
		'runwaysbycountry.controllers', 'common.services' ]);
runwaysByCountryApp.constant("CONSTANTS", {
	getRunwaysByCountry : "/countries/runways",

});