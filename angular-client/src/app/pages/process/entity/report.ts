import { ProductDTO } from 'app/pages/products/entity/product';

export class ReportDTO {
    id: number;
    totalCartsProcessed: number;
    totalCartsFailed: number;
    products: ProductDTO[];
    profit: number;
}
