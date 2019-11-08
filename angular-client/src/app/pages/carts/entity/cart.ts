import { ProductDTO } from 'app/pages/products/entity/product';

export class CartDTO {
    id: number;
    fullName: string;
    email: string;
    creationDate: Date;
    total: number;
    status: string;

    products: ProductDTO[];
}
