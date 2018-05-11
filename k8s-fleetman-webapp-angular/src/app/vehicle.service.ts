import { Injectable, OnInit, OnDestroy } from '@angular/core';
import { Vehicle } from './vehicle';
import { Observable ,  Subscription } from 'rxjs';

import { of } from 'rxjs/observable/of';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import {Message} from '@stomp/stompjs';
import {StompService} from '@stomp/ng2-stompjs';

@Injectable()
export class VehicleService implements OnInit, OnDestroy {

  subscription: Subscription;
  private messages: Observable<Message>;
  private subscribed: boolean;

  static vehicles: Vehicle[] = [];

  constructor(private _stompService: StompService) {
    console.log("constructor...............................");
    console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!on init");
    this.subscribed = false;

    // Store local reference to Observable
    // for use with template ( | async )
    this.subscribe();
  }

  subscribe() {

    if (this.subscribed) {
      return;
    }
console.log("subscribing...");
    // Stream of messages
    this.messages = this._stompService.subscribe('/vehiclepositions/messages');

    // Subscribe a function to be run on_next message
    this.subscription = this.messages.subscribe(this.onMessage);

    this.subscribed = true;
  }

  /** Consume a message from the _stompService */
  onMessage = (message: Message) => {

    // Store message in "historic messages" queue
    // TODO this will of course be an update of the vehicle, or a new one if not already here...

    // Log it to the console
    console.log(message);
  }

  private unsubscribe() {
    if (!this.subscribed) {
      return;
    }

    // This will internally unsubscribe from Stomp Broker
    // There are two subscriptions - one created explicitly, the other created in the template by use of 'async'
    this.subscription.unsubscribe();
    this.subscription = null;
    this.messages = null;

    this.subscribed = false;
  }

  ngOnDestroy() {
    this.unsubscribe();
  }

}
