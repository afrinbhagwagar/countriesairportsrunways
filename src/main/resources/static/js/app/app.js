'use strict'
var accentureApp = angular.module('accentureindex', [ 'ui.bootstrap',
		'accenture.controllers', 'accenture.services' ]);
accentureApp.constant("CONSTANTS", {
	getTopTenCountries : "/countries/airports",

});