import { Injectable } from '@angular/core';
import { Vehicle } from './vehicle';
import { Observable ,  Subscription } from 'rxjs';

import { of } from 'rxjs/observable/of';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import {Message} from '@stomp/stompjs';
import {StompService} from '@stomp/ng2-stompjs';

import { from } from 'rxjs/observable/from';

@Injectable()
export class VehicleService  {

  static vehicles: Vehicle[] = [];

  subscription = from(VehicleService.vehicles);

  constructor(private _stompService: StompService) {
    // Store local reference to Observable
    // for use with template ( | async )
    this.subscribe();
  }

  subscribe() {
    // Stream of messages
    var messages = this._stompService.subscribe('/vehiclepositions/messages');

    // Subscribe a function to be run on_next message
    messages.subscribe(this.onMessage);
  }

  /** Consume a message from the _stompService */
  onMessage = (message: Message) => {
    // update vehicle and notify
    VehicleService.vehicles.push( { id: 0,
      name: "City Truck",
      lat: 53.376972,
      lng: -1.467061,
      dateAndTime: '30 April 2018 16:20',
      speed: 14.2
    });

    // Log it to the console
    console.log(message);
  }

}
