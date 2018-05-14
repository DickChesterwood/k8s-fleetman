import { Component, OnInit } from '@angular/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { icon, latLng, Layer, Marker, marker, tileLayer, Map, point } from 'leaflet';

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
    zoom: 15,
    center: latLng(53.38207,-1.48423)
  };

  onMapReady(map: Map) {
    this.map = map;
  }

  ngOnInit() {
    this.vehicleService.subscription.subscribe(vehicle => {
       if (vehicle == null) return;
       let foundIndex = this.markers.findIndex(existingMarker => existingMarker.options['title'] == vehicle.name);


       if (foundIndex == -1)
       {
         let newMarker = marker([vehicle.lat,vehicle.lng] ,
                                 {
                                   icon: icon( {
                                                 iconSize: [ 25, 41 ],
                                                 iconAnchor: [ 11, 41 ],
                                                 iconUrl: 'assets/marker-icon.png',
                                                 shadowUrl: 'assets/marker-shadow.png'
                                               }),
                                   title: vehicle.name
                                 }).bindTooltip(vehicle.name, {permanent:true,  offset: point({x: 0, y: 0})});
         this.markers.push(newMarker);
       }
       else
       {
        this.markers[foundIndex].setLatLng(latLng(vehicle.lat, vehicle.lng));
       }
       if (this.centerVehicle == vehicle.name) {
         this.map.setView([vehicle.lat,vehicle.lng],
                           this.map.getZoom(), {"animate": true});
       }
     });

     this.vehicleService.centerVehicle.subscribe(vehicle => {
       if (vehicle == null)
       {
         this.centerVehicle = null;
         return;
       }
       this.centerVehicle = vehicle.name;
       this.map.flyTo([vehicle.lat,vehicle.lng],
                         this.map.getZoom(), {
       				   	       "animate": true
       				  });
     });
   }
}
