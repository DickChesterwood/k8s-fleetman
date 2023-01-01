import { Injectable } from '@angular/core';
import { Vehicle } from './vehicle';
import { Observable ,  Subscription, BehaviorSubject ,  of, interval } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import {  LatLng } from 'leaflet';

@Injectable()
export class VehicleService  {


  constructor(private http: HttpClient) {
  }

  updateAllPositions(data) {
  }

  updateCenterVehicle(centerVehicle: Vehicle) {
  }
}
