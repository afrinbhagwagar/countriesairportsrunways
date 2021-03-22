'use strict'
var topTenApp = angular.module('topTenIndex', [ 'ui.bootstrap',
		'topten.controllers', 'common.services' ]);
topTenApp.constant("CONSTANTS", {
	getTopTenCountries : "/countries/airports",
});