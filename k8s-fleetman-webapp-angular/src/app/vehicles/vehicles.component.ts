import { Component, OnInit } from '@angular/core';
import { Vehicle } from '../vehicle';
import { VehicleService } from '../vehicle.service';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {

  vehicles: Vehicle[] = [];

  constructor(private vehicleService: VehicleService) { }

  ngOnInit() {
    // this.vehicleService.subscription.subscribe(updatedVehicle => {
    //   let foundIndex = this.vehicles.findIndex(existingVehicle => existingVehicle.id == updatedVehicle.id);
    //   if (foundIndex == -1) this.vehicles.push(updatedVehicle)
    //   else this.vehicles[foundIndex] = updatedVehicle;
    // });
  }

}
