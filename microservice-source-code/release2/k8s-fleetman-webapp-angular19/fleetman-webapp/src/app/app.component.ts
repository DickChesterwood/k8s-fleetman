import { Component } from '@angular/core';
import { HeaderComponent } from './header/header.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { MapComponent } from './map/map.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [HeaderComponent,VehiclesComponent,MapComponent]
})
export class AppComponent {
  title = 'Fleetman Live Tracking';
}
