import {Component, Input, Output, EventEmitter} from '@angular/core';

import {NgbModal, NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import { CartProductDTO } from '../entity/cartProduct';

@Component({
  selector: 'cart-dialog.component',
  templateUrl: './cart-dialog.component.html'
})
export class CartDialogComponent {

  @Input() public cartProduct: CartProductDTO;
  @Output() notifyParent: EventEmitter<any> = new EventEmitter();
  
  constructor(public activeModal: NgbActiveModal) {}

  notify(){
    this.notifyParent.emit(this.cartProduct);
    console.log('Notify clicked...');
    this.activeModal.close(this.cartProduct);
  }

}
