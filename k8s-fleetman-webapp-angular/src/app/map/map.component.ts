import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  constructor() { }

  options = {
    layers: [
      L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZGlja2NoZXN0ZXJ3b29kIiwiYSI6ImNqNG9kcHI2aDJtN2wzMnBndmU3bWkzdmgifQ.rdSmry2jgNl4jmydiypisQ',
                  { maxZoom: 18,
                    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery Â© <a href="http://mapbox.com">Mapbox</a>',
                    id: 'mapbox.streets',
                    accessToken: 'your.mapbox.access.token' })
    ],
    zoom: 15,
    center: L.latLng(53.38207,-1.48423)
  };

  ngOnInit() {}

}
