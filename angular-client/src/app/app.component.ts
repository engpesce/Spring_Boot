import { Component } from '@angular/core';
import { ProductsService } from './pages/products/service/products.service';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { CartService } from './pages/carts/service/cart.service';
import { ProcessCartsService } from './pages/process/service/process-carts.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ProductsService, CartService, ProcessCartsService,
    {provide: LocationStrategy, useClass: HashLocationStrategy}]
})

export class AppComponent{}
