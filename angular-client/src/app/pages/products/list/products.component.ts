import { Component, OnInit } from '@angular/core';
import { ProductDTO } from '../entity/product';
import { ProductsService } from '../service/products.service';

@Component({
    selector: 'products-cmp',
    moduleId: module.id,
    templateUrl: 'products.component.html'
})

export class ProductsComponent implements OnInit{
    public products: ProductDTO[] = [];
    public errorMsg: string;

    constructor(private productsService: ProductsService){
    }

    ngOnInit(){
        this.initialize();
    }
  
    initialize() {
      this.productsService.getAll().subscribe(
        resp => {
          if (resp) {
            this.products = resp.body;
          }
        },
        error => {
          console.error(error);
        }

      );
    }

  remove(product: ProductDTO): void {
      this.productsService.delete(product.id).subscribe(
        response => {
          this.initialize();
        },
        error => {
          this.errorMsg = 'Ha ocurrido un problema borrando el producto: ' + error.error;
          console.error(error);
        }

      );
    }

    cleanNotification(){
      this.errorMsg = undefined;
    }
}
