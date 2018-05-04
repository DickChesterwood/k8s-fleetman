import { Injectable } from '@angular/core';
import { Vehicle } from './vehicle';
import { Observable } from 'rxjs/Rx';

import { of } from 'rxjs/observable/of';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable()
export class VehicleService {

   static vehicles: Vehicle[] = [
    { id: 0,
      name: "City Truck",
      lat: 53.376972,
      lng: -1.467061,
      dateAndTime: '30 April 2018 16:20',
      speed: 14.2
    },

    { id: 1,
      name: "Village Truck",
      lat: 53.176972,
      lng: -1.267061,
      dateAndTime: '30 April 2018 16:18',
      speed: 14.2
    }
  ];

  constructor() {
  }

  getSubscription(): Observable<number> {
    return Observable.of("")
                     .switchMap(() => Observable
                     .timer(500)
                     .mapTo(VehicleService.moveRandomVehicle()))
                     .repeat();
  }

  private static moveRandomVehicle(): number {
    let randomId = Math.floor(Math.random() * (this.vehicles.length));
    let vehicle = this.vehicles[randomId];
    vehicle.lat = VehicleService.generateRandomNumberFromRange(53.38653,53.37687);
    vehicle.lng = VehicleService.generateRandomNumberFromRange(-1.49850,-1.46517);
    return vehicle;
  }

  private static generateRandomNumberFromRange(min: number, max: number): number {
    let randomNumber = min + (Math.random() * (max- min);
    return randomNumber;
  }
}
