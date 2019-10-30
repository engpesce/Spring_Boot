import { Component } from '@angular/core';
import { ProductsService } from './pages/products/service/products.service';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ProductsService,
    {provide: LocationStrategy, useClass: HashLocationStrategy}]
})

export class AppComponent{}
