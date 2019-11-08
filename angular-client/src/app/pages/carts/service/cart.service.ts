import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CartDTO } from '../entity/cart';
import { SERVER_API_URL } from 'app/shared/app.constants';
import { ProductDTO } from 'app/pages/products/entity/product';
import { CartProductDTO } from '../entity/cartProduct';

@Injectable({ providedIn: 'root' })
export class CartService {
    private URL = SERVER_API_URL + '/carts';
    
    constructor(private http: HttpClient) {

    }

    getCartById(id: number) {
        const url = `${this.URL}/${id}`;
        return this.http.get<CartDTO>(url);
    }

    getAllCarts(): Observable<CartDTO[]> {
        return this.http.get<CartDTO[]>(this.URL);
    }

    createCart(element: CartDTO): Observable<CartDTO> {
        return this.http.post<CartDTO>(this.URL, element);
    }   

    addProductToCart(id: number, element: CartProductDTO) {

        const url = `${this.URL}/${id}/products`;
        return this.http.post(url, element, {headers: new HttpHeaders({
            'Accept': 'text/html, application/xhtml+xml, */*'
          }),
          responseType: 'text'});
    }

    getProductToCart(id: number, element: ProductDTO): Observable<ProductDTO[]>  {

        const url = `${this.URL}/${id}/products`;
        return this.http.get<ProductDTO[]>(url);
    }

    checkoutCart(id: number) {

        const url = `${this.URL}/${id}/checkout`;
        return this.http.post(url, {headers: new HttpHeaders({
            'Accept': 'text/html, application/xhtml+xml, */*'
          }),
          responseType: 'text'});
    }

    delete(cartId: number, productId: number): Observable<CartDTO> {
        const url = `${this.URL}/${cartId}/products/${productId}`;
        return this.http.delete<CartDTO>(url);
    }
}