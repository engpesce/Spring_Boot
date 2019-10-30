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
    public isEdition: boolean;

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
            this.isEdition = true;
          });
        } else {
          this.title = 'Alta de Producto';
          this.isEdition = false;
        }
    }

    onSubmit() {

      const id = this.route.snapshot.paramMap.get('id');   

      if (id) {
        this.productsService.update(this.product).subscribe(
          response => {
            //this.router.navigate(['/products']);
            this.router.navigateByUrl('http://localhost:4200/products');
          },
          error => {
            console.error(error);
          });

      } else {
        this.productsService.add(this.product).subscribe(
          response => {
            this.router.navigate(['/products']);
          },
          error => {
            console.error(error);
          });
      } 

    } 
}
