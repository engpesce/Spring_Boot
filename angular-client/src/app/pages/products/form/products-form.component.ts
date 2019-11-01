import { Component, OnInit } from '@angular/core';
import { ProductDTO } from '../entity/product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductsService } from '../service/products.service';

@Component({
    selector: 'products-form-cmp',
    moduleId: module.id,
    templateUrl: 'products-form.component.html'
})

export class ProductsFormComponent implements OnInit{
    
    public product: ProductDTO = new ProductDTO();
    public title: string;
    public errorMsg: string;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private productsService: ProductsService){
    }

    ngOnInit(){
        
        const id = this.route.snapshot.paramMap.get('id');   

        if (id) {
          this.productsService.getById(parseInt(id)).subscribe(resp => {
            this.product = resp;
    
            this.title = 'Edición de Producto: ' + this.product.id;
          });
        } else {
          this.title = 'Alta de Producto';
        }
    }

    onSubmit() {

      const id = this.route.snapshot.paramMap.get('id');   

      if (id) {
        this.productsService.update(this.product).subscribe(
          response => {
            this.router.navigate(['/products']);
          },
          error => {
            this.errorMsg = 'Ha ocurrido un problema actualizando el producto: ' + error.error;
            console.error(error);
          });

      } else {
        this.productsService.add(this.product).subscribe(
          response => {
            this.router.navigate(['/products']);
          },
          error => {
            this.errorMsg = 'Ha ocurrido un problema guardando el producto: ' + error.error;
            console.error(error);
          });
      } 

    } 

    cleanNotification(){
      this.errorMsg = undefined;
    }
}
