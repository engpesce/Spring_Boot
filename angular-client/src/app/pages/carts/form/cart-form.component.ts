import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartDTO } from '../entity/cart';
import { CartService } from '../service/cart.service';
import { ProductsService } from 'app/pages/products/service/products.service';
import { ProductDTO } from 'app/pages/products/entity/product';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CartDialogComponent } from './cart-dialog.component';
import { CartProductDTO } from '../entity/cartProduct';

@Component({
    selector: 'cart-form-cmp',
    moduleId: module.id,
    templateUrl: 'cart-form.component.html'
})

export class CartFormComponent implements OnInit{
    
    public cart: CartDTO = new CartDTO();
    public products: ProductDTO[] = [];
    public productToAdd: ProductDTO;
    public title: string;
    public errorMsg: string;
    public enableCreation: boolean;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private cartService: CartService,
                private productService: ProductsService, 
                private modalService: NgbModal){
    }

    ngOnInit(){
      let cartCreatedId: string = localStorage.getItem('cartCreatedId');  

        if (cartCreatedId) {
          this.enableCreation = false;
          this.cartService.getCartById(parseInt(cartCreatedId)).subscribe(resp => {
            this.cart = resp;
            this.title = 'Carrito de Compras Existente: ' + this.cart.id;

            this.productService.getAllSimple().subscribe(
              resp => {
                this.products = resp;
              }
            );

          });
          
         
        } else {
          this.enableCreation = true;
          this.title = 'Ingrese con sus datos para comenzar a comprar: ' + this.cart.id;
        }
    }

    onSubmit() {

      this.cartService.createCart(this.cart).subscribe(resp => {
        this.cart = resp;
        this.title = 'Carrito de Compras Creado: ' + this.cart.id;
        localStorage.setItem('cartCreatedId', this.cart.id.toString()); 
        this.enableCreation = false;
      });

    } 

    cleanNotification(){
      this.errorMsg = undefined;
    }

    openModal(product: ProductDTO) {
      const modalRef = this.modalService.open(CartDialogComponent);
      var cartProduct: CartProductDTO = new CartProductDTO();
      cartProduct.productId = product.id;

      modalRef.componentInstance.cartProduct = cartProduct;
      modalRef.result.then((result) => {
        
        let cartCreatedId: string = localStorage.getItem('cartCreatedId');  
        this.cartService.addProductToCart(parseInt(cartCreatedId), result).subscribe(
            resp => {
              this.cartService.getCartById(parseInt(cartCreatedId)).subscribe(resp => {
                this.cart = resp;
              });
          }
        );

      });

    }
}
