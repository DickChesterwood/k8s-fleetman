import { Injectable } from '@angular/core';
import { Vehicle } from './vehicle';
import { Observable ,  Subscription, BehaviorSubject ,  of, interval } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import {  LatLng } from 'leaflet';

@Injectable()
export class VehicleService  {

  subscription: BehaviorSubject<Vehicle>;
  centerVehicle: BehaviorSubject<Vehicle>;
  centerVehicleHistory: BehaviorSubject<any>;
  timedUpdate: Subscription;
  source = interval(1000);


  constructor(private http: HttpClient) {
    // Store local reference to Observable
    // for use with template ( | async )
    this.subscription = new BehaviorSubject(null);
    this.centerVehicle = new BehaviorSubject(null);
    this.centerVehicleHistory = new BehaviorSubject(null);
    this.timedUpdate = this.source.subscribe(val =>   this.http.get("http://" + window.location.hostname + ":" + window.location.port + "/api/vehicles/")
             .subscribe( data => this.updateAllPositions(data)));
  }

  updateAllPositions(data) {
    data.forEach( (body) => {
          console.log(body);
          let newVehicle = new Vehicle(body.name,
                                 Number(body.lat),
                                 Number(body.longitude),
                                 body.timestamp,
                                Number(body.speed));     
          this.subscription.next(newVehicle);
    });
  }

  updateCenterVehicle(centerVehicle: Vehicle) {
    this.centerVehicle.next(centerVehicle);

    if (centerVehicle == null)
    {
      this.centerVehicleHistory.next(null);
    }
    else
    {
      // call API gateway, get the history for this vehicle.
      this.http.get("http://" + window.location.hostname + ":" + window.location.port + "/api/history/" + centerVehicle.name)
             .subscribe( data => this.centerVehicleHistory.next(data));
    }
  }
}
