import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDTO } from '../entity/product';
import { SERVER_API_URL } from 'app/shared/app.constants';

@Injectable({ providedIn: 'root' })
export class ProductsService {
    private URL = SERVER_API_URL + '/products';
    private URL_MOCK = './assets/products.json';
    
    constructor(private http: HttpClient) {

    }

    getAll(): Observable<HttpResponse<ProductDTO[]>> {
        return this.http.get<ProductDTO[]>(this.URL, { observe: 'response' });
    }

    getAllSimple(): Observable<ProductDTO[]> {
        return this.http.get<ProductDTO[]>(this.URL);
    }

    getById(id: number) {
        const url = `${this.URL}/${id}`;
        return this.http.get<ProductDTO>(url);
    }

    add(element: ProductDTO) {
        return this.http.post<ProductDTO>(this.URL, element);
    }

    update(element: ProductDTO) {
        const url = `${this.URL}/${element.id}`;
        return this.http.put<ProductDTO>(url, element);
    }

    delete(id: number) {
        const url = `${this.URL}/${id}`;
        return this.http.delete<ProductDTO>(url);
    }
}