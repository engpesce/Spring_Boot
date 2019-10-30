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


    openDialog(product: ProductDTO): void {
        /*const dialogRef = this.dialog.open(AcreedorDeleteComponent, {
          width: '400px',
          data: { acreedor: acreedor }
        });
    
        dialogRef.afterClosed().subscribe(result => {
          if (result) {
            this.validateDeletion(result);
          }
        });*/
      }
}
