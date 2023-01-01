import { Component, OnInit } from '@angular/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { icon, latLng, Layer, Marker, marker, tileLayer, Map, point, polyline } from 'leaflet';

import { VehicleService } from '../vehicle.service';
import { Vehicle } from '../vehicle';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  constructor(private vehicleService: VehicleService) { }

  markers: Marker[] = [];
  map: Map;
  centerVehicle: string;

  options = {
    layers: [
       tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
                  { maxZoom: 18,
                    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                  })
    ],
    zoom: 16,
    center: latLng(53.375564, -1.506785)
  };

  onMapReady(map: Map) {
    this.map = map;
  }

  ngOnInit() {
  }
}
