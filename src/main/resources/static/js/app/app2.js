'use strict'
var accentureApp2 = angular.module('accentureindex2', [ 'ui.bootstrap',
		'accenture2.controllers', 'accenture.services' ]);
accentureApp2.constant("CONSTANTS", {
	getRunwaysByCountry : "/countries/runways",

});