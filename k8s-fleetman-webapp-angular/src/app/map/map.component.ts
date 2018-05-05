import { Component, OnInit } from '@angular/core';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { icon, latLng, Layer, Marker, marker, tileLayer } from 'leaflet';

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

  ngOnInit() {

     this.vehicleService.subscription.subscribe(vehicle => {
       let foundIndex = this.markers.findIndex(existingMarker => existingMarker.options['title'] == vehicle.name);
       let newMarker = marker([vehicle.lat,vehicle.lng] ,
                               {
                                 icon: icon( {
                                               iconSize: [ 25, 41 ],
                                               iconAnchor: [ 11, 41 ],
                                               iconUrl: 'assets/marker-icon.png',
                                               shadowUrl: 'assets/marker-shadow.png'
                                             }),
                                 title: vehicle.name
                               }).bindTooltip(vehicle.name);
      
       if (foundIndex == -1) this.markers.push(newMarker);
       else this.markers[foundIndex] = newMarker;
     });
  }

  // getMarkers() {
  //   return Object.keys(this.markerDictionary).map(function(key){
  //      return this.markerDictionary[key];
  //   });
  // }
}
