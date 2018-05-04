import { Vehicle } from './vehicle';
import { InMemoryDbService } from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const vehicles = [
      { name: "City Truck",
        lat: 53.376972,
        lng: -1.467061,
        dateAndTime: '30 April 2018 16:20',
        speed: 14.2
      },

      { name: "Village Truck",
        lat: 53.176972,
        lng: -1.267061,
        dateAndTime: '30 April 2018 16:18',
        speed: 14.2
      }
    ];
    return {vehicles};
  }
}
